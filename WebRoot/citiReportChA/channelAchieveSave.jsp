<%@page contentType="text/html;charset=GBK"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.citireport.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>

<%
	//׼��ͨ�ò���
	CError cError = new CError();
	String strOperation = "PRINT";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("��¼��Ϣû�л�ȡ!!!");
		return;
	}

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();

	String filePath = request.getRealPath("");
	filePath = filePath.replaceAll("\\\\", "/");
	filePath += "/temp/Midplat_CA_Report_" + PubFun.getCurrentDate() + ".xls";
	
	String startDay = request.getParameter("StartDay");
	String endDay = request.getParameter("EndDay");
	
	System.out.println("startDay:"+startDay+",endDay:"+endDay);
	System.out.println("CA:�ͻ�������ȡÿ��ʵʱ���ݣ��ļ�·��Ϊ��" + filePath);

	tTransferData.setNameAndValue("StartDay", startDay);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("EndDay", endDay);
	VData tVData = new VData(); 
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";  
	String FlagStr = "";
	ChannelAchieve channelAchieve = new ChannelAchieve();
	if (!channelAchieve.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = channelAchieve.mErrors.getFirstError().toString();

	} else {
		FlagStr = "Succ";
		Content = (String) channelAchieve.getResult().get(0);
		System.out.println(Content);
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
