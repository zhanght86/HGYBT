<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ComInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:40
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%> 
<%@page import="com.sinosoft.lis.schema.*"%> 
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.sys.*"%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//������Ϣ������У�鴦�� 
//�������
LDComSchema tLDComSchema   = new LDComSchema();

ComUI tComUI = new ComUI();

//�������
CErrors tError = null; 

String tRela  = "";                
String FlagStr = "";
String Content = "";
String transact = "";

transact = request.getParameter("fmtransact");
System.out.println("------transact:"+transact);
tLDComSchema.setComCode(request.getParameter("ComCode"));
tLDComSchema.setOutComCode(request.getParameter("OutComCode"));
tLDComSchema.setName(request.getParameter("Name"));
System.out.println("��������==="+request.getParameter("Name"));
tLDComSchema.setShortName(request.getParameter("ShortName"));
tLDComSchema.setUpComCode(request.getParameter("UpComCode"));
tLDComSchema.setAddress(request.getParameter("Address"));
tLDComSchema.setZipCode(request.getParameter("ZipCode"));
tLDComSchema.setPhone(request.getParameter("Phone"));
tLDComSchema.setFax(request.getParameter("Fax"));
tLDComSchema.setEMail(request.getParameter("EMail"));
tLDComSchema.setWebAddress(request.getParameter("WebAddress"));


try {
	// ׼���������� VData
	VData tVData = new VData();
	tVData.addElement(tLDComSchema);
	tComUI.submitData(tVData,transact);
}
catch(Exception ex) {
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}
  
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
if (FlagStr=="") {
	tError = tComUI.mErrors; 
	if (!tError.needDealError()) {                          
		Content = " ����ɹ�! ";
		FlagStr = "Success";
	}
	else {
		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}
}
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>