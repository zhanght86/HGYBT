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

	String sManageCom = "";
	String sOperator = "";
	CError cError = new CError();
	String sHtml = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if (tG == null) {
		System.out.println("��¼��Ϣû�л�ȡ!!!");
		return;
	} else if (tG != null) {
		sManageCom = tG.ManageCom;
		sOperator = tG.Operator;
	}

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();

	String filePath = request.getRealPath("");
	filePath = filePath.replaceAll("\\\\", "/");
	filePath += "/temp/Midplat_IT_Report" + ".xls";

	String sArea = request.getParameter("AreaNo");
	String sCity = request.getParameter("CityNo");
	String sSysFlag = request.getParameter("SysFlag");
	String sPrintFlag = request.getParameter("PrintFlag");
    String sManageCodeNo = request.getParameter("ManageCodeNo");
	System.out.println("IT:" + sOperator
			+ "�ͻ�������ȡ����ʹ�����ͳ�Ʊ���ѯ����Ϊ��1-����,2-����,3-ϵͳ��־");
	System.out.println("IT:" + sOperator + "�ͻ�������ȡ����ʹ�����ͳ�Ʊ���ѯ����Ϊ��1-"
			+ sArea + ",2-" + sCity + ",3-" + sSysFlag + "");
	System.out.println("IT:�ͻ�������ȡ����ʹ�����ͳ�Ʊ��ļ�·��Ϊ��" + filePath);
	
	tTransferData.setNameAndValue("Area", sArea);
	tTransferData.setNameAndValue("City", sCity);
	tTransferData.setNameAndValue("SysFlag", sSysFlag);
	tTransferData.setNameAndValue("filePath", filePath);
	tTransferData.setNameAndValue("ManageCom", sManageCodeNo);
	System.out.println("ITPrintSave��ManageCodeNo������������"+sManageCodeNo);
	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
	IT_PrintBL tIT_PrintBL = new IT_PrintBL();
	if (!tIT_PrintBL.submitData(tVData, "download")) {
		FlagStr = "Fail";
		Content = tIT_PrintBL.mErrors.getFirstError().toString();
		System.out.println("IT��������ʧ��" + sPrintFlag);

	} else {
		FlagStr = "Succ";
		Content = (String) tIT_PrintBL.getResult().get(0);
		System.out.println("IT�������ɳɹ�" + sPrintFlag);
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
