<%@page import="javax.faces.component.html.HtmlInputText"%>

<%
	//�������ƣ�ITPrintSave.jsp
	//�����ܣ�����ͨÿ��ʵʱ���ݵ�����
	//�������ڣ�2011-07-26
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.excel.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="org.apache.poi.hssf.usermodel.*"%>


<%
	//׼��ͨ�ò���
	CError cError = new CError();
	String strOperation = "PRINT";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	String sHtml = "";
	File file = null;
	if (tG == null) {
		System.out.println("��¼��Ϣû�л�ȡ!!!");
		return;
	}

	HSSFWorkbook workbook = new HSSFWorkbook();

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();

	String filePath = request.getRealPath("");
	filePath = filePath.replaceAll("\\\\", "/");
	filePath += "/temp/Midplat_NoRealFIN_Report_Preview.xls";
	ExcelUtils eu = new ExcelUtils("FIN", filePath,"NoRealFINPreview");
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
	} 
	System.out.println("sStartHour"+sStartHour);
	System.out.println("sEndHour"+sEndHour); 

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
	System.out.println("NoRealFINPreviewInput.jsp��ManageCodeNo=" + sManageCodeNo);

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
		System.out.println("FIN�������ɳɹ�");
		file = (File) tFIN_NoReal_PrintBL.getResult().get(1);
		workbook = eu.readExcelFile(Content);
		String exceltitle = eu.getFirstRowContent(workbook, 0);
		StringBuffer htmlsource = eu.excelToHtmlJs(workbook, 0);
		StringBuffer htmlbuf = new StringBuffer("");
		htmlbuf.append(eu.headerHtmlStart(exceltitle));
		htmlbuf.append(htmlsource);
		htmlbuf.append(eu.headerHtmlEnd());
		htmlbuf.append(eu.bodyHtml());
		htmlbuf.append(eu.bodyHtmlEnd());
		sHtml = htmlbuf.toString();
		file.delete();
	}
%>

<%=sHtml%>




