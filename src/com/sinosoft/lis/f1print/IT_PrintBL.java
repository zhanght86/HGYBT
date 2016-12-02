package com.sinosoft.lis.f1print;

import jxl.format.BorderLineStyle;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.excel.ITCreatExcel;
import com.sinosoft.midplat.common.DateUtil;

/**
 * <p>
 * ClassName: IT_PrintBL
 * </p>
 * <p>
 * Description: 银保通IT保单使用情况统计报表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: AXA_DB
 * @CreateDate：2011-07-26
 * @ReWriteDate:
 */

public class IT_PrintBL extends ITCreatExcel {

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private TransferData mTransferData = new TransferData();

	private String sArea = "";

	private String sCity = "";

	private String sSysFlag = "";

	private String filePath = "";

	private String sManageCom = "";
	SSRS tSSRS = new SSRS();

	SSRS tSSRS1 = new SSRS();

	public IT_PrintBL() {

	}

	/**
	 * 返回错误
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 打印
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	
	/**
	 * 获取从UI端传入参数信息 包括 
	 * 
	 * @param cInputData
	 *            VData
	 *            地区码、城市码、系统标志、文件路径
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.sArea = (String) mTransferData.getValueByName("Area");
		this.sCity = (String) mTransferData.getValueByName("City");
		this.sManageCom = (String) mTransferData.getValueByName("ManageCom");
		this.filePath = (String) mTransferData.getValueByName("filePath");
		this.sSysFlag = (String) mTransferData.getValueByName("SysFlag");
		return true;
	}

	/**
	 * 返回结果集
	 * 
	 * @return: VData 程序处理结果
	 */
	public VData getResult() {
		return mResult;
	}
	

	/**
	 * 通过SQL获取报表信息，并且将获取的信息放入EXCEL
	 * @author ZHANGHJ
	 * @return boolean
	 */
	private boolean getPrintData() {

		// 设置Excel文件输出的路径
		setFilePath(filePath);
		
		// 设置Excel的Sheet
		String[] Sheet = { "保单使用情况统计表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		

		//设置标题
		String[] title = { "保单使用情况统计表" };
		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 15);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THICK);
		setMergeCells(1, 0, 5, 0);
		setTitle(0, title, 0, 1);
		
		//设置列头
		String[] colSize = { "13",  "13", "13", "13", "13", "13" };
		String[] head = { "", "城市",  "所属系统", "保单号总数", "已使用保单号总数",
				"可使用保单号总数" };
		for (int i = 1; i < 6; i++) {
			setContent(1, i, head[i]);
			setCellFont(1, i, ExcelFont.ARIAL);
			setFontSize(1, i, 10);
			setFontBold(1, i, ExcelFont.No_Bold);
			setFontAlign(1, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(1, i, BorderLineStyle.THICK);
		}
		
		
		
		//报表SQL
		sSql += "	SELECT "
				+ "  N.CITYCODE 城市,"
				+ "  N.SYSFLAG 所属系统,"
				+ "  (SELECT COUNT(*) FROM LKCONTNO N1 WHERE N1.CITYCODE = N.CITYCODE AND N1.SYSFLAG = N.SYSFLAG) 保单号总数,"
				+ "  (SELECT COUNT(*) FROM LKCONTNO N1 WHERE N1.CITYCODE = N.CITYCODE AND N1.SYSFLAG = N.SYSFLAG AND N1.STATUS = '1') 已使用保单号总数,"
				+ "  (SELECT COUNT(*) FROM LKCONTNO N1 WHERE N1.CITYCODE = N.CITYCODE AND N1.SYSFLAG = N.SYSFLAG AND N1.STATUS = '0') 可使用保单号总数"
				+ "  FROM LKCONTNO N WHERE 1=1";

		if (sManageCom != null && !"".equals(sManageCom)) {
			sSql += "AND N.CITYCODE IN (SELECT DD.AXASHORTNAME FROM LDCOM DD WHERE DD.AXASHORTNAME IS NOT NULL AND DD.comcode like '" + sManageCom + "%')";
		}
//		if (sCity != null && !"".equals(sCity)) {
//			
//			String sLDComSql = "SELECT SHORTNAME FROM LDCOM D WHERE D.CITYCODE = '" + sCity + "'";
//			String sShortName = new ExeSQL().getOneValue(sLDComSql);
//			
//			sSql += " AND TRIM(N.CITYCODE) = '" + sShortName + "'";
//		}
		if (sSysFlag != null && !"".equals(sSysFlag)) {
			sSql += " AND TRIM(N.SYSFLAG) = '" + sSysFlag + "'";
		}
		sSql += "  GROUP BY N.CITYCODE, N.SYSFLAG" + "  ORDER BY CITYCODE";

		tSSRS = exeSql.execSQL(sSql);
		
		String[][] print = tSSRS.getAllData();
		
		//合并最下面一行的单元格
		int z = print.length;
		setMergeCells(1, z + 2, 6, z + 2);
		setContent(z + 2, 1,
				DateUtil.getCur10Date() + "  " + DateUtil.getCur8Time());
		setCellFont(z + 2, 1, ExcelFont.ARIAL);
		setFontSize(z + 2, 1, 10);
		setFontBold(z + 2, 1, ExcelFont.No_Bold);
		setFontAlign(z + 2, 1, ExcelAlignment.LEFT);
		setData(0, print, 2, 1);
		setColSize(0, colSize, 0);
		
		try {
			if (!createExcel()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ITPrintBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = "生产保单号使用情况报表时发生异常!";
				this.mErrors.addOneError(tError);
				mResult.clear();
				return false;
			} else {
				mResult.clear();
				mResult.add(filePath);
				mResult.add(gettFile());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		// 准备数据容器信息
		TransferData tTransferData = new TransferData();
		String filePath = "C:/Users/asus/Desktop/aa.xls";

		String sArea = "";
		String sCity = "";
		String sTranCom = "";
		String sAgentCom = "";
		String sAgentCode = "";
		String sRiskCode = "";
		String sStartDay = "";
		String sEndDay = "";
		String sSysFlag = "";
		System.out
				.println("LO:客户正在提取每日实时数据，查询条件为：1-地区,2-城市,3-银行渠道,4-网点,5-网点专员,6-险种代码,7-开始日期,8-结束日期");
		System.out.println("LO:客户正在提取每日实时数据，查询条件为：1-" + sArea + ",2-" + sCity
				+ ",3-" + sTranCom + ",4-" + sAgentCom + ",5-" + sAgentCode
				+ ",6-" + sRiskCode + ",7-" + sStartDay + ",8-" + sEndDay + "");
		System.out.println("LO:客户正在提取每日实时数据，文件路径为：" + filePath);

		tTransferData.setNameAndValue("Area", sArea);
		tTransferData.setNameAndValue("City", sCity);
		tTransferData.setNameAndValue("TranCom", sTranCom);
		tTransferData.setNameAndValue("AgentCom", sAgentCom);
		tTransferData.setNameAndValue("AgentCode", sAgentCode);
		tTransferData.setNameAndValue("RiskCode", sRiskCode);
		tTransferData.setNameAndValue("StartDay", sStartDay);
		tTransferData.setNameAndValue("EndDay", sEndDay);
		tTransferData.setNameAndValue("SysFlag", sSysFlag);
		tTransferData.setNameAndValue("filePath", filePath);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		// tVData.addElement(tG);
		String Content = "";
		String FlagStr = "";
		IT_PrintBL tIT_PrintBL = new IT_PrintBL();
		if (!tIT_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tIT_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tIT_PrintBL.getResult().get(0);
			// Content.replaceAll("\\","/");
			System.out.println(Content);
		}
	}
}