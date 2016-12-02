
<%
	//�������ƣ�NoRealFIN_PrintSave.jsp
	//�����ܣ�����ͨÿ��������ϸ����
	//�������ڣ�2011-07-26
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
	filePath += "/temp/Midplat_NoRealFIN_Report_" + PubFun.getCurrentDate() + ".xls";
	
	String sTranCom = request.getParameter("TranCom");
	String sArea = request.getParameter("AreaNo");
	String sCity = request.getParameter("CityNo");
	String sContStatue = request.getParameter("ContStatue");
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
	String sManageCodeNo = request.getParameter("ManageCodeNo");
	String sAgentCom = "";
	String sAgentCode = "";
	String sRiskCode = "";
	
	System.out.println("FIN:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-����,2-����,3-��������,4-����,5-����רԱ,6-���ִ���,7-��ʼ����,8-��������");
	System.out.println("FIN:�ͻ�������ȡÿ��ʵʱ���ݣ���ѯ����Ϊ��1-" + sArea + ",2-"
			+ sCity + ",3-" + sTranCom + ",4-" + sAgentCom + ",5-"
			+ sAgentCode + ",6-" + sRiskCode + ",7-" + sStartDay
			+ ",8-" + sEndDay + "");
	System.out.println("FIN:�ͻ�������ȡÿ��ʵʱ���ݣ��ļ�·��Ϊ��" + filePath);

	tTransferData.setNameAndValue("Area", sArea);
	tTransferData.setNameAndValue("City", sCity);
	tTransferData.setNameAndValue("TranCom", sTranCom);
	tTransferData.setNameAndValue("AgentCom", sAgentCom);
	tTransferData.setNameAndValue("AgentCode", sAgentCode);
	tTransferData.setNameAndValue("RiskCode", sRiskCode);
	tTransferData.setNameAndValue("StartDay", sStartDay);
	tTransferData.setNameAndValue("EndDay", sEndDay);
	tTransferData.setNameAndValue("StartHour", sStartHour);
	tTransferData.setNameAndValue("EndHour", sEndHour);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("ContStatue", sContStatue);
	tTransferData.setNameAndValue("ManageCodeNo", sManageCodeNo);
	System.out.println("NoRealFinPrintSave��ManageCodeNo"+sManageCodeNo);
	VData tVData = new VData(); 
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";  
	String FlagStr = "";
	FIN_NoReal_PrintBL tFIN_NoReal_PrintBL = new FIN_NoReal_PrintBL();
	if (!tFIN_NoReal_PrintBL.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = tFIN_NoReal_PrintBL.mErrors.getFirstError().toString();

	} else {
		FlagStr = "Succ";
		Content = (String) tFIN_NoReal_PrintBL.getResult().get(0);
		//Content.replaceAll("\\","/");
		System.out.println(Content);
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




