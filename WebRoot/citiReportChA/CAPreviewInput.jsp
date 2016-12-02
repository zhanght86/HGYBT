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
	//准备通用参数
	CError cError = new CError();
	String strOperation = "PRINT";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	String sHtml = "";
	File file = null;
	if (tG == null) {
		System.out.println("登录信息没有获取!!!");
		return;
	}

	HSSFWorkbook workbook = new HSSFWorkbook();

	//准备数据容器信息
	TransferData tTransferData = new TransferData();

	String filePath = request.getRealPath("");
	filePath = filePath.replaceAll("\\\\", "/");
	filePath += "/temp/Midplat_CA_Report_Preview.xls";
	ExcelUtils eu = new ExcelUtils("CA", filePath,"CAPreview");
	String startDay = request.getParameter("StartDay");
	String endDay = request.getParameter("EndDay");
	
	System.out.println("startDay:"+startDay+",endDay:"+endDay);
	System.out.println("CA:客户正在提取每日实时数据，文件路径为：" + filePath);

	tTransferData.setNameAndValue("StartDay", startDay);
	tTransferData.setNameAndValue("EndDay", endDay);
	tTransferData.setNameAndValue("filePath", filePath);
	
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
		System.out.println("CA报表生成成功");
		file = (File) channelAchieve.getResult().get(1);
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
