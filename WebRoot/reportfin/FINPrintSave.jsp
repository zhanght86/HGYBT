
<%
	//程序名称：FIN_PrintSave.jsp
	//程序功能：银保通每日销售明细报表
	//创建日期：2011-07-26
%>

<%@page contentType="text/html;charset=GBK"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
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
	filePath += "/temp/Midplat_FIN_Report_" + PubFun.getCurrentDate() + ".xls";
	
	String sTranCom = request.getParameter("TranCom");
	String sArea = request.getParameter("AreaNo");
	String sCity = request.getParameter("CityNo");
	String sContStatue = request.getParameter("ContStatue");
	String sManageCodeNo = request.getParameter("ManageCodeNo");
	String sStartDay = request.getParameter("StartDay");
	String sEndDay = request.getParameter("EndDay");
	String sStartHour = request.getParameter("StartHour");
	String sEndHour = request.getParameter("EndHour");
	if(sStartHour!=null && !"".equals(sStartHour)){
		sStartHour = sStartHour+":00:00";
	}
	if(sEndHour!=null && !"".equals(sEndHour)){
		sEndHour = sEndHour+":00:00";
		if(sEndHour.equals("24:00:00")){
			sEndHour="23:59:59";
		}
	} 
	System.out.println("sStartHour"+sStartHour);
	System.out.println("sEndHour"+sEndHour); 
	
	   
	String sAgentCom = "";
	String sAgentCode = "";
	String sRiskCode = "";
	
	System.out.println("FIN:客户正在提取每日实时数据，查询条件为：1-地区,2-城市,3-银行渠道,4-网点,5-网点专员,6-险种代码,7-开始日期,8-结束日期");
	System.out.println("FIN:客户正在提取每日实时数据，查询条件为：1-" + sArea + ",2-"
			+ sCity + ",3-" + sTranCom + ",4-" + sAgentCom + ",5-"
			+ sAgentCode + ",6-" + sRiskCode + ",7-" + sStartDay
			+ ",8-" + sEndDay + "");
	System.out.println("FIN:客户正在提取每日实时数据，文件路径为：" + filePath);

	tTransferData.setNameAndValue("Area", sArea);
	tTransferData.setNameAndValue("City", sCity);
	tTransferData.setNameAndValue("TranCom", sTranCom);
	tTransferData.setNameAndValue("AgentCom", sAgentCom);
	tTransferData.setNameAndValue("AgentCode", sAgentCode);
	tTransferData.setNameAndValue("RiskCode", sRiskCode);
	tTransferData.setNameAndValue("StartDay", sStartDay);
	tTransferData.setNameAndValue("EndDay", sEndDay);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("ContStatue", sContStatue);
	tTransferData.setNameAndValue("StartHour", sStartHour);
	tTransferData.setNameAndValue("EndHour", sEndHour);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("ManageCodeNo", sManageCodeNo);
	System.out.println("FINPrintSave中的ManageCodeNo="+sManageCodeNo);
	VData tVData = new VData(); 
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";  
	String FlagStr = "";
	FIN_PrintBL tFIN_PrintBL = new FIN_PrintBL();
	if (!tFIN_PrintBL.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = tFIN_PrintBL.mErrors.getFirstError().toString();

	} else {
		FlagStr = "Succ";
		Content = (String) tFIN_PrintBL.getResult().get(0);
		//Content.replaceAll("\\","/");
		System.out.println(Content);
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




