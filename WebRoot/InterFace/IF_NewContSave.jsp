<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.InterFace.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>

<%
	//准备通用参数
	CError cError = new CError();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("登录信息没有获取!!!");
		return;
	}

	//准备数据容器信息
	TransferData tTransferData = new TransferData();
	
	String sDay = request.getParameter("FileDate");
	
	System.out.println("客户正在生成接口文件。。。" +sDay);
	
	System.out.println("客户正在生成接口文件。。。" );

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
		//Content = "生成文件成功";
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




