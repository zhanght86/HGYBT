
<%
	//�������ƣ�LO_PrintSave.jsp
	//�����ܣ�����ͨÿ��ʵʱ���ݵ�����
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
	filePath += "/temp/Midplat_Banc_Report_" + PubFun.getCurrentDate() + ".xls";
	
	String sArea = request.getParameter("AreaNo");
	String sCity = request.getParameter("CityNo");
	String sManageCodeNo =request.getParameter("ManageCodeNo");
	String sTranCom = request.getParameter("TranComNo");
	String sAgentCom = request.getParameter("AgentComNo");
	String sAgentCode = request.getParameter("AgentCodeNo");
	String sRiskCode = request.getParameter("RiskCodeNo");
	String sStartDay = request.getParameter("StartDay");
	String sEndDay = request.getParameter("EndDay");

	System.out.println("LO:�ͻ�������ȡ����ͨҵ����ϸ������ѯ����Ϊ��1-����,2-����,3-��������,4-����,5-����רԱ,6-���ִ���,7-��ʼ����,8-��������");
	System.out.println("LO:�ͻ�������ȡ����ͨҵ����ϸ������ѯ����Ϊ��1-" + sArea + ",2-"
			+ sCity + ",3-" + sTranCom + ",4-" + sAgentCom + ",5-"
			+ sAgentCode + ",6-" + sRiskCode + ",7-" + sStartDay
			+ ",8-" + sEndDay + "");
	System.out.println("LO:�ͻ�������ȡ����ͨҵ����ϸ�����ļ�·��Ϊ��" + filePath);

	tTransferData.setNameAndValue("Area", sArea);
	tTransferData.setNameAndValue("City", sCity);
	tTransferData.setNameAndValue("TranCom", sTranCom);
	tTransferData.setNameAndValue("AgentCom", sAgentCom);
	tTransferData.setNameAndValue("AgentCode", sAgentCode);
	tTransferData.setNameAndValue("RiskCode", sRiskCode);
	tTransferData.setNameAndValue("StartDay", sStartDay);
	tTransferData.setNameAndValue("EndDay", sEndDay);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("ManageCodeNo", sManageCodeNo);
	System.out.println("BCPrintSave�е�ManageCodeNo��" + sManageCodeNo);
	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
	BC_PrintBL tBC_PrintBL = new BC_PrintBL();
	if (!tBC_PrintBL.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = tBC_PrintBL.mErrors.getFirstError().toString();

	} else {
		FlagStr = "Succ";
		Content = (String) tBC_PrintBL.getResult().get(0);
		//Content.replaceAll("\\","/");
		System.out.println(Content);
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




