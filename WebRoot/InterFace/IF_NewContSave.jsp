<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.InterFace.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>

<%
	//׼��ͨ�ò���
	CError cError = new CError();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("��¼��Ϣû�л�ȡ!!!");
		return;
	}

	//׼������������Ϣ
	TransferData tTransferData = new TransferData();
	
	String sDay = request.getParameter("FileDate");
	
	System.out.println("�ͻ��������ɽӿ��ļ�������" +sDay);
	
	System.out.println("�ͻ��������ɽӿ��ļ�������" );

	tTransferData.setNameAndValue("Day", sDay);

	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
	IF_NewCont tIF_NewCont = new IF_NewCont();
	try{
	if (!tIF_NewCont.submitData(tVData)) {
		FlagStr = "Fail";
		Content = tIF_NewCont.mErrors.getLastError().toString();

	} else {
		FlagStr = "Succ";
		for (int i=0;i<tIF_NewCont.mErrors.getErrorCount();i++){
		Content = tIF_NewCont.mErrors.getLastError().toString();
		}
		//Content = "�����ļ��ɹ�";
		//Content = "niujhji";
		//Content.replaceAll("\\","/");
		//System.out.println(Content);
	}
	}catch(Exception e){
		FlagStr = "Fail";
		for (int i=0;i<tIF_NewCont.mErrors.getErrorCount();i++){
		Content = tIF_NewCont.mErrors.getLastError().toString();
		}
		//Content = "niammm";
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




