package com.sinosoft.lis.f1print;

import jxl.format.BorderLineStyle;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.excel.AgentCreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.excel.NodeMapCreatExcel;

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
 
public class NodeMap_PrintBL extends NodeMapCreatExcel {

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private GlobalInput mGlobalInput = new GlobalInput(); // 全局数据

	private TransferData mTransferData = new TransferData();

	private String sArea = "";

	private String sCity = "";
	
	private String sBankCode= "";
	
	private String sManageCodeNo="";
	
	private String filePath = "";

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	SSRS tSSRS1 = new SSRS();

	public NodeMap_PrintBL() {

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
		this.sBankCode = (String) mTransferData.getValueByName("BankCode");
		this.filePath = (String) mTransferData.getValueByName("filePath");
		this.sManageCodeNo = (String) mTransferData.getValueByName("ManageCodeNo");
		
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
		String[] Sheet = { "网点导出表" };
		setSheet(Sheet);

		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = {  "25", "20", "20", "20", "15","15" , "15" , "15", "15", "15", "20"};

		// 设置列头
		String[] head = {  "网点名称", "银行网点编码", "网点编码","专员名称","网点专员编码","城市名称","城市编码","兼业代理资格证","资格证起始日","资格证到期日","网点系统状态","网点销售资格" };
		for (int i = 0; i < 12; i++) {
			setContent(0, i, head[i]);
			setCellFont(0, i, ExcelFont.ARIAL);
			setFontSize(0, i, 10);
			setFontBold(0, i, ExcelFont.Bold);
			setFontAlign(0, i, ExcelAlignment.CENTRE);
			setBorderLineStyle(0, i, BorderLineStyle.THIN);
		}
		sSql += "	SELECT " 
				+ " N.AGENTCOMNAME 网点名称,"
				+ " '0'||N.TRANCOM||'-'||N.ZONENO||'-'||N.NODENO 银行网点代码,"
				+ " N.UNITCODE||'-'||N.AGENTGRADE||'-'||N.AGENTCOM 网点代码,"
				+" N.AGENTNAME 专员名称,"
				+ " N.UNITCODE||'-'||N.AGENTCODEGRADE||'-'||N.AGENTCODE 网点专员代码,"
				+" (select ld.name from ldcom ld where ld.comcode=N.managecom) 城市名称,"
				+ " SUBSTR(N.MANAGECOM,7,3) 城市编码,"
				+ " N.CONAGENTNO 兼业代理资格正,"
				+ " N.CONSTARTDATE 资格正起始日,"
				+ " N.CONENDDATE 资格正到期日,"
				+ " N.STATE 网点系统状态,"
				+ " N.SALEFLAG 网点销售资格"
				+ "  FROM NODEMAP N WHERE 1=1";

		if (sManageCodeNo != null && !"".equals(sManageCodeNo)) {
			sSql += " AND N.MANAGECOM like'" + sManageCodeNo + "%'";
		}
		if (sBankCode != null && !"".equals(sBankCode)) {
			sSql += " AND '0'||N.TRANCOM ='" + sBankCode + "'";
		}
		sSql += "AND N.TYPE !='9' ORDER BY N.MANAGECOM,N.UNITCODE,N.AGENTCOM";

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
		NodeMap_PrintBL tAgent_PrintBL = new NodeMap_PrintBL();
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