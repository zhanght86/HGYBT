<%@page import="javax.faces.component.html.HtmlInputText"%>

<%
	//程序名称：ITPrintSave.jsp
	//程序功能：银保通每日非实时数据导出表
	//创建日期：2011-07-26
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
	filePath += "/temp/Midplat_LONoReal_Report_Preview.xls";
	ExcelUtils eu = new ExcelUtils("LO", filePath,"NoRealLOPreview");
	String sArea = request.getParameter("AreaNo");
	String sCity = request.getParameter("CityNo");
	String sTranCom = request.getParameter("TranCom");
	String sAgentCom = request.getParameter("AgentCom");
	String sAgentCode = request.getParameter("AgentCode");
	String sRiskCode = request.getParameter("RiskCode");
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
	System.out.println("BCPreviewInput.jsp中ManageCodeNo=" + sManageCodeNo);
	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
	LO_NoReal_PrintBL tLO_NoReal_PrintBL = new LO_NoReal_PrintBL();
	if (!tLO_NoReal_PrintBL.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = tLO_NoReal_PrintBL.mErrors.getFirstError().toString();
		System.out.println("LO报表生成失败");
	} else { 
		FlagStr = "Succ";
		Content = (String) tLO_NoReal_PrintBL.getResult().get(0);
		System.out.println("LO报表生成成功");
		file = (File) tLO_NoReal_PrintBL.getResult().get(1);
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




