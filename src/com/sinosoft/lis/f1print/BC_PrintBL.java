package com.sinosoft.lis.f1print;

import jxl.format.Colour;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.print.MatrixPrint;
import com.sinosoft.lis.excel.BCCreatExcel;
import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelBorderLineStyle;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.midplat.common.DateUtil;

/**
 * <p>
 * ClassName: BC_PrintBL
 * </p>
 * <p>
 * Description: 银保通每日实时数据导出表
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

public class BC_PrintBL extends BCCreatExcel {

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private GlobalInput mGlobalInput = new GlobalInput(); // 全局数据

	private TransferData mTransferData = new TransferData();

	private String sArea = "";

	private String sCity = "";

	private String sTranCom = "";

	private String sAgentCom = "";

	private String sAgentCode = "";

	private String sRiskCode = "";

	private String sStartDay = "";

	private String sEndDay = "";

	private String filePath = "";
	
	private String sManageCodeNo="";

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	SSRS tSSRS1 = new SSRS();

	public BC_PrintBL() {

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
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.sManageCodeNo = (String) mTransferData.getValueByName("ManageCodeNo");
		this.sArea = (String) mTransferData.getValueByName("Area");
		this.sCity = (String) mTransferData.getValueByName("City");
		this.sTranCom = (String) mTransferData.getValueByName("TranCom");
		this.sAgentCom = (String) mTransferData.getValueByName("AgentCom");
		this.sAgentCode = (String) mTransferData.getValueByName("AgentCode");
		this.sRiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.sStartDay = (String) mTransferData.getValueByName("StartDay");
		this.sEndDay = (String) mTransferData.getValueByName("EndDay");
		this.filePath = (String) mTransferData.getValueByName("filePath");

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
	 * 打印数据
	 * 
	 * @author JiaoYF
	 * @return boolean
	 */
	private boolean getPrintData() {

		// 设置Excel文件输出的路径
		setFilePath(filePath);
		// 设置Excel的Sheet
		String[] Sheet = { "银保通业绩明细报表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "10", "10", "10", "9", "9", "10", "9", "10",
				"10", "10", "10", "10", "10", "10", "10", "10", "10"};

		// 设置标题
		String[] title = { "银保通业绩明细报表" };
		setMergeCells(0, 0, 16, 0);
		setTitleBold(0, ExcelFont.No_Bold);
		setTitleFont(0, ExcelFont.ARIAL);
		setTitleFontSize(0, 16);
		setTitleAlign(0, ExcelAlignment.CENTRE);
		setTitleBorder(0, ExcelBorder.ALL);
		setTitleBorderStyle(BorderLineStyle.THIN);
	
		
		
		setMergeCells(0, 1, 3, 1);
		setContent(1, 0, "报帐日期区间自：");
		setCellFont(1, 0, ExcelFont.ARIAL);
		setFontSize(1, 0, 12);
		setFontBold(1, 0, ExcelFont.No_Bold);
		setFontAlign(1, 0, ExcelAlignment.LEFT);
		
		setMergeCells(4, 1, 7, 1);
		setContent(1, 4, this.sStartDay);
		setCellFont(1, 4, ExcelFont.ARIAL);
		setFontSize(1, 4, 12);
		setFontBold(1, 4, ExcelFont.No_Bold);
		setFontAlign(1, 4, ExcelAlignment.LEFT);

		setMergeCells(8, 1, 11, 1);
		setContent(1, 8, "报帐日期区间止：");
		setCellFont(1, 8, ExcelFont.ARIAL);
		setFontSize(1, 8, 12);
		setFontBold(1, 8, ExcelFont.No_Bold);
		setFontAlign(1, 8, ExcelAlignment.LEFT);
		

		setMergeCells(12, 1, 16, 1);
		setContent(1, 12, this.sEndDay);
		setCellFont(1, 12, ExcelFont.ARIAL);
		setFontSize(1, 12, 12);
		setFontBold(1, 12, ExcelFont.No_Bold);
		setFontAlign(1, 12, ExcelAlignment.LEFT);

		for(int i=0;i<=16;i++){
			setBorderLineStyle(1, i, BorderLineStyle.THIN);
		}

		
		// 设置列头
		String[] head = { "管理机构", "银行", "网点代码", "网点编号","网点名称", "FA代码",
				"FA姓名", "保单编号", "险种", "报账日期", "核保日期", "主险基本保险费",
				"期交追加保险费", "趸交追加保险费", "附加险保险费", "总保费"};
		for (int i = 0; i < 16; i++) {
			setContent(2, i, head[i]);
			setCellFont(2, i, ExcelFont.ARIAL);
			setFontSize(2, i, 10);
			setFontBold(2, i, ExcelFont.Bold);
			setFontAlign(2, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(2, i, BorderLineStyle.THIN);
		}
		 sSql += "	SELECT  "
		       + "  D.NAME 管理机构,"
		       + "  CASE WHEN c.bankcode = '011' THEN 'ICBC' WHEN c.bankcode = '012' THEN 'CGB' ELSE '--' END 银行,"
		       + "  TRIM(C.AXANODEMAP)  网点编号,"
			   + "  TRIM(C.AXAAGENTCOM) 编号,"
			   + "  C.AGENTCOMNAME 销售网点,"
			   + "  TRIM(C.AXAAGENTCODE) 代理人编号,"
		       + "  C.AGENTNAME FA姓名,"
		       + "  TRIM(C.CONTNO) 保单编号,"
		       + "  P.RISKALIAS 险种,"
		       + "  TO_CHAR(C.MODIFYDATE,'YYYY-MM-DD')||' '||C.MODIFYTIME 报账日期,"
		       + "  TO_CHAR(C.MAKEDATE,'YYYY-MM-DD')||' '||C.MAKETIME 核保日期,"
		       + "  C.MAINPOLPREM 主险基本保险费,"
		       + "  nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END 定期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0) 期交追加保险费,"
		       + "  (select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END 首期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual) 趸交追加保险费,"
		       + "  nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)','NHYrider(lpsm)','HYG3rider(lpsm)') THEN 0 ELSE sp.prem END FROM lcpol sp WHERE (sp.polno=c.contno||'-1')),0)  附加保险费,"
		       + "  C.PREM 总保费" 
		  	   + " FROM LCCONT C, LCPOL P ,LDCOM D"
		 	   + " WHERE "
		       + " TRIM(C.MANAGECOM) = D.COMCODE"
		       + " AND C.CONTNO||'-0' = P.POLNO"
		       + " AND C.APPFLAG IN ('1','B')"
		       + " AND C.UWFLAG = '9'";
		       

		if (sManageCodeNo != null && !"".equals(sManageCodeNo)) {
			sSql += " and c.managecom like '" + sManageCodeNo + "%' ";
		}
		if (sTranCom != null && !"".equals(sTranCom)) {
			sSql += " and trim(c.bankcode) = '" + sTranCom + "' ";
		}
		if (sAgentCom != null && !"".equals(sAgentCom)) {
			sSql += " and c.agentcom = '" + sAgentCom + "' ";
		}
		if (sAgentCode != null && !"".equals(sAgentCode)) {
			sSql += " and c.agentcode = '" + sAgentCode + "' ";
		}
		if (sRiskCode != null && !"".equals(sRiskCode)) {
			sSql += " and p.riskcode = '" + sRiskCode + "' ";
		}
		if (sStartDay != null && !"".equals(sStartDay)) {
			sSql += "  AND p.MAKEDATE >= DATE'" + sStartDay + "' ";
		}
		if (sEndDay != null && !"".equals(sEndDay)) {
			sSql += " AND p.MAKEDATE <= DATE'" + sEndDay + "' ";
		}
	

		sSql += " UNION "

		     + " SELECT "
		     + " '总计:共'||count(D.COMCODE)||'条',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " '',"
		     + " SUM(C.MAINPOLPREM),"
		     + " SUM(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END 定期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0)),"
		     + " SUM((select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END 首期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual)),"
		     + " SUM(nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)','NHYrider(lpsm)','HYG3rider(lpsm)') THEN 0 ELSE sp.prem END FROM lcpol sp WHERE (sp.polno=c.contno||'-1')),0)),"
		     + " SUM(C.PREM)"
		       + " FROM LCCONT C, LCPOL P ,LDCOM D"
		 	   + " WHERE "
		       + " TRIM(C.MANAGECOM) = D.COMCODE"
		       + " AND C.CONTNO||'-0' = P.POLNO"
		       + " AND C.APPFLAG IN ('1','B')"
		       + " AND C.UWFLAG = '9'";
		    

		if (sManageCodeNo != null && !"".equals(sManageCodeNo)) {
			sSql += " and c.managecom like '" + sManageCodeNo + "%' ";
		}
		if (sTranCom != null && !"".equals(sTranCom)) {
			sSql += " and trim(c.bankcode) = '" + sTranCom + "' ";
		}
		if (sAgentCom != null && !"".equals(sAgentCom)) {
			sSql += " and c.agentcom = '" + sAgentCom + "' ";
		}
		if (sAgentCode != null && !"".equals(sAgentCode)) {
			sSql += " and c.agentcode = '" + sAgentCode + "' ";
		}
		if (sRiskCode != null && !"".equals(sRiskCode)) {
			sSql += " and p.riskcode = '" + sRiskCode + "' ";
		}
		if (sStartDay != null && !"".equals(sStartDay)) {
			sSql += "  AND p.MAKEDATE >= DATE'" + sStartDay + "' ";
		}
		if (sEndDay != null && !"".equals(sEndDay)) {
			sSql += " AND p.MAKEDATE <= DATE'" + sEndDay + "' ";
		}
		sSql+= " order by  报账日期";
		
		tSSRS = exeSql.execSQL(sSql);
		setTitle(title);
		String[][] print = tSSRS.getAllData();


		
		for (int i = 0; i < print.length; i++) {
			for (int j = 0; j < print[i].length; j++) {
			//	System.out.println(print[i][j]);
				if (print[i][j] == null || "null".equals(print[i][j])) {
				//	System.out.println("aaa");
					print[i][j] = "";

				}

			}

		}

		int mergeColumns = 1;
		// setDateByFixArrays(0, print, 4, 0, mergeColumns,"合计;全国");
		//setColorFlag("LO");
		setData(0, print, 3, 0);
		setColSize(0, colSize, 0);
		
		try {
			if (!createExcel()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = "@GMRSelGatherBL020@";
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
		String sStartDay = "2011-08-01";
		String sEndDay = "2012-09-10";

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
		tTransferData.setNameAndValue("filePath", filePath);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		// tVData.addElement(tG);
		String Content = "";
		String FlagStr = "";
		BC_PrintBL tBC_PrintBL = new BC_PrintBL();
		if (!tBC_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tBC_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tBC_PrintBL.getResult().get(0);
			// Content.replaceAll("\\","/");
			System.out.println(Content);
		}
	}
}