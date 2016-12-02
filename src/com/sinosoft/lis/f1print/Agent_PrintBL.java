package com.sinosoft.lis.f1print;

import jxl.format.BorderLineStyle;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.excel.AgentCreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelFont;

/**
 * <p>
 * ClassName: Agent_PrintBL
 * </p>
 * <p>
 * Description: 代理人导出
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

public class Agent_PrintBL extends AgentCreatExcel {

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private GlobalInput mGlobalInput = new GlobalInput(); // 全局数据

	private TransferData mTransferData = new TransferData();

	private String sArea = "";

	private String sCity = "";

	private String filePath = "";
	
    private String sManageCodeNo = "";

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	SSRS tSSRS1 = new SSRS();

	public Agent_PrintBL() {

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

		this.sArea = (String) mTransferData.getValueByName("Area");
		this.sCity = (String) mTransferData.getValueByName("City");
		this.filePath = (String) mTransferData.getValueByName("filePath");
        this.sManageCodeNo =(String) mTransferData.getValueByName("ManageCodeNo");
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
	 * @author zhanghj
	 * @return boolean
	 */
	private boolean getPrintData() {

		// 设置Excel文件输出的路径
		setFilePath(filePath);
		// 设置Excel的Sheet
		String[] Sheet = { "网点专员导出表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = {  "12", "12","12", "40" };

		// 设置列头
		String[] head = {"城市编码",  "网点专员编码", "网点专员名称" };
		for (int i = 0; i < 3; i++) {
			setContent(0, i, head[i]);
			setCellFont(0, i, ExcelFont.ARIAL);
			setFontSize(0, i, 10);
			setFontBold(0, i, ExcelFont.Bold); 
			setFontAlign(0, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(0, i, BorderLineStyle.THIN);
		}
		sSql += "	 SELECT " 
				+ "  SUBSTR(A.MANAGECOM,7,3) 城市代码," 
			    + "  A.AGENTCODE 网点专员代码,"
				+ "  A.AGENTNAME 网点专员代码" + "  FROM AXAAGENT A WHERE 1=1";

		if (sManageCodeNo != null && !"".equals(sManageCodeNo)) {
			sSql += " AND A.MANAGECOM like'" + sManageCodeNo + "%'";
		}
		sSql += " ORDER BY A.MANAGECOM,A.AGENTCODE";

		tSSRS = exeSql.execSQL(sSql);

		String[][] print = tSSRS.getAllData();

		setData(0, print, 1, 0);
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

		String sArea = "03";
		String sCity = "300";
		String sTranCom = "";
		String sAgentCom = "";
		String sAgentCode = "";
		String sRiskCode = "";
		String sStartDay = "";
		String sEndDay = "";

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
		Agent_PrintBL tAgent_PrintBL = new Agent_PrintBL();
		if (!tAgent_PrintBL.submitData(tVData, "download")) {
			FlagStr = "Fail";
			Content = tAgent_PrintBL.mErrors.getFirstError().toString();

		} else {
			FlagStr = "Succ";
			Content = (String) tAgent_PrintBL.getResult().get(0);
			// Content.replaceAll("\\","/");
			System.out.println(Content);
		}
	}
}