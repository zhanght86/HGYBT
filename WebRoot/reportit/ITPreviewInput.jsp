<%@page import="javax.faces.component.html.HtmlInputText"%>

<%
	//程序名称：ITPrintSave.jsp
	//程序功能：银保通每日实时数据导出表
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
	String sManageCom = "";
	String sOperator = "";
	CError cError = new CError();
	String sHtml = "";
	String tBatchNo="";
	File file = null;
	GlobalInput tG = new GlobalInput();

	HSSFWorkbook workbook = new HSSFWorkbook();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("登录信息没有获取!!!");
		return;
	} else if (tG != null) {
		sManageCom = tG.ManageCom;
		sOperator = tG.Operator;
	}

	//准备数据容器信息
	TransferData tTransferData = new TransferData();

	String filePath = request.getRealPath("");
	filePath = filePath.replaceAll("\\\\", "/");
	filePath += "/temp/Midplat_IT_Report_Preview" + ".xls";
	ExcelUtils eu = new ExcelUtils("IT", filePath,"ITPreview");
	String sArea = request.getParameter("AreaNo");
	String sCity = request.getParameter("CityNo");
	String sSysFlag = request.getParameter("SysFlag");
	String sManageCodeNo = request.getParameter("ManageCodeNo");
	String sPrintFlag = "preview";

	System.out.println("IT:" + sOperator
			+ "客户正在提取保单使用情况统计表，查询条件为：1-地区,2-城市,3-系统标志");
	System.out.println("IT:" + sOperator + "客户正在提取保单使用情况统计表，查询条件为：1-"
			+ sArea + ",2-" + sCity + ",3-" + sSysFlag + "");
	System.out.println("IT:客户正在提取保单使用情况统计表，文件路径为：" + filePath);

	tTransferData.setNameAndValue("Area", sArea);
	tTransferData.setNameAndValue("City", sCity);
	tTransferData.setNameAndValue("ManageCom", sManageCodeNo);
	tTransferData.setNameAndValue("SysFlag", sSysFlag);
	tTransferData.setNameAndValue("filePath", filePath);

	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
    System.out.println("ITPreviewInput中的ManageCodeNo---------"+sManageCodeNo);
	IT_PrintBL tIT_PrintBL = new IT_PrintBL();
	if (!tIT_PrintBL.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = tIT_PrintBL.mErrors.getFirstError().toString();
		System.out.println("IT报表生成失败");

	} else {
		FlagStr = "Succ";
		Content = (String) tIT_PrintBL.getResult().get(0);
		System.out.println("IT报表生成成功");
		file = (File) tIT_PrintBL.getResult().get(1);
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


