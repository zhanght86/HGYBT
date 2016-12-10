<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>

<%
	//准备通用参数
	CError cError = new CError();
	String strOperation = "PRINT";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("登录信息没有获取!!!");
		return;
	}

	//准备数据容器信息
	TransferData tTransferData = new TransferData();

	String filePath = request.getRealPath("");
	filePath = filePath.replaceAll("\\\\", "/");
	filePath += "/temp/Midplat_NoRealLO_Report_" + PubFun.getCurrentDate() + ".xls";
	
	String sArea = request.getParameter("AreaNo");
	String sCity = request.getParameter("CityNo");
	String sTranCom = request.getParameter("TranComNo");
	String sAgentCom = request.getParameter("AgentComNo");
	String sAgentCode = request.getParameter("AgentCodeNo");
	String sRiskCode = request.getParameter("RiskCodeNo");
	String sDay = request.getParameter("Day");
    String sManageCodeNo = request.getParameter("ManageCodeNo");
	System.out.println("LO:客户正在提取每日实时数据，查询条件为：1-地区,2-城市,3-银行渠道,4-网点,5-网点专员,6-险种代码,7-开始日期,8-结束日期");
	System.out.println("LO:客户正在提取每日实时数据，查询条件为：1-" + sArea + ",2-"
			+ sCity + ",3-" + sTranCom + ",4-" + sAgentCom + ",5-"
			+ sAgentCode + ",6-" + sRiskCode + ",7-" + sDay
			+ "");
	System.out.println("LO:客户正在提取每日实时数据，文件路径为：" + filePath);

	tTransferData.setNameAndValue("Area", sArea);
	tTransferData.setNameAndValue("City", sCity);
	tTransferData.setNameAndValue("TranCom", sTranCom);
	tTransferData.setNameAndValue("AgentCom", sAgentCom);
	tTransferData.setNameAndValue("AgentCode", sAgentCode);
	tTransferData.setNameAndValue("RiskCode", sRiskCode);
	tTransferData.setNameAndValue("Day", sDay);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("ManageCodeNo", sManageCodeNo);
	System.out.println("NoRealLOPrintSave中的ManageCodeNo"+sManageCodeNo);
	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
	LO_NoReal_PrintBL tLO_NoReal_PrintBL = new LO_NoReal_PrintBL();
	if (!tLO_NoReal_PrintBL.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = tLO_NoReal_PrintBL.mErrors.getFirstError().toString();

	} else {
		FlagStr = "Succ";
		Content = (String) tLO_NoReal_PrintBL.getResult().get(0);
		//Content.replaceAll("\\","/");
		System.out.println(Content);
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




