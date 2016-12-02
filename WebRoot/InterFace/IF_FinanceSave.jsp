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

	
	System.out.println("客户正在生成接口文件。。。" );

	tTransferData.setNameAndValue("Day", sDay);

	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
	IF_Finance tIF_Finance = new IF_Finance();
	try{
	if (!tIF_Finance.submitData(tVData)) {
		FlagStr = "Fail";
		Content = tIF_Finance.mErrors.getLastError().toString();

	} else {
		FlagStr = "Succ";
		for (int i=0;i<tIF_Finance.mErrors.getErrorCount();i++){
		Content = tIF_Finance.mErrors.getLastError().toString();
		}
		//Content = "文件生成成功";
		//Content.replaceAll("\\","/");
		//System.out.println(Content);
	}
	}catch(Exception e){
		FlagStr = "Fail";
		for (int i=0;i<tIF_Finance.mErrors.getErrorCount();i++){
		Content = tIF_Finance.mErrors.getLastError().toString();
		}
		
	}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




