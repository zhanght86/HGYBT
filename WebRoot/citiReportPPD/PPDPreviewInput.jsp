<%@page import="javax.faces.component.html.HtmlInputText"%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.citireport.*"%>
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
	filePath += "/temp/Midplat_PPD_Report_Preview.xls";
	ExcelUtils eu = new ExcelUtils("PPD", filePath,"PPDPreview");
	String startDay = request.getParameter("StartDay");
	String endDay = request.getParameter("EndDay");
	
	System.out.println("startDay:"+startDay+",endDay:"+endDay);
	System.out.println("PPD:�ͻ�������ȡÿ��ʵʱ���ݣ��ļ�·��Ϊ��" + filePath);

	tTransferData.setNameAndValue("StartDay", startDay);
	tTransferData.setNameAndValue("EndDay", endDay);
	tTransferData.setNameAndValue("filePath", filePath);
	
	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";  
	String FlagStr = "";
	PremPoundageDetail premPoundageD = new PremPoundageDetail();
	if (!premPoundageD.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = premPoundageD.mErrors.getFirstError().toString();

	} else {
		FlagStr = "Succ";
		Content = (String) premPoundageD.getResult().get(0);
		System.out.println("PPD�������ɳɹ�");
		file = (File) premPoundageD.getResult().get(1);
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