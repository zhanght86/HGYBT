<%@page contentType="text/html;charset=GBK"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.citireport.*"%>
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
	filePath += "/temp/Midplat_PFTD_Report_" + PubFun.getCurrentDate() + ".xls";
	
	String startDay = request.getParameter("StartDay");
	String endDay = request.getParameter("EndDay");
	
	System.out.println("startDay:"+startDay+",endDay:"+endDay);
	System.out.println("PFTD:客户正在提取每日实时数据，文件路径为：" + filePath);

	tTransferData.setNameAndValue("StartDay", startDay);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("EndDay", endDay);
	VData tVData = new VData(); 
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";  
	String FlagStr = "";
	PolicyFundTD policyFund = new PolicyFundTD();
	if (!policyFund.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = policyFund.mErrors.getFirstError().toString();

	} else {
		FlagStr = "Succ";
		Content = (String) policyFund.getResult().get(0);
		//Content.replaceAll("\\","/");
		System.out.println(Content);
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
