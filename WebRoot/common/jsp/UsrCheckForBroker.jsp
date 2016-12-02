<!--
*******************************************************
* 程序名称：UsrCheckForBroker.jsp
* 程序功能：兼业用户信息校验页面
* 创建日期：2006-11-29
* 更新记录：  更新人    更新日期     更新原因/内容
*******************************************************
-->
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
try
{
	if (session == null) {
		System.out.println("session is null");
%>
<script language=javascript>
try
{
	top.window.location ="../indexncl.jsp";
}
catch (exception)
{
	top.window.location ="../indexncl.jsp";
}
</script>
<%
		return;
	}
	GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	if (tG1 == null) {
		session.putValue("GI",null);
		out.println("网页超时，请您重新登录");
%>
<script language=javascript>
try
{
	top.window.location ="../indexncl.jsp";
}
catch (exception)
{
	top.window.location ="../indexncl.jsp";
}
</script>
<%
		return;
	}
	String  userCode = tG1.Operator;
	String comCode =tG1.ComCode;
	String manageCom = tG1.ManageCom;
	if ((userCode.length()==0) || (userCode.compareTo("")==0)||(comCode.length()==0) || (comCode.compareTo("")==0) ||(manageCom.length()==0) || (manageCom.compareTo("") == 0))
	{
		session.putValue("GI",null);
		String ContentErr = " 请您重新登录！";
		System.out.println(ContentErr);
%>
<script language=javascript>
try
{
	top.window.location ="../indexncl.jsp";
}
catch (exception)
{
	top.window.location ="../indexncl.jsp";
}
</script>
<%
		return;
	}
}
catch(Exception exception)
{
	String ContentErr = " exception:请您重新登录！";
	System.out.println(ContentErr);
	out.println("网页超时，请您重新登录");
%>
<script language=javascript>
top.window.location ="../indexncl.jsp";
</script>
<%
	return;
}
%>