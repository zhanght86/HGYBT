<!--
*******************************************************
* 程序名称：AccessCheck.jsp
* 程序功能：页面访问校验页面
* 创建日期：2002-11-25
* 更新记录：  更新人    更新日期     更新原因/内容
*******************************************************
-->
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.access.*"%>
<%
try
{
//	System.out.println("do page access check");
	StringBuffer strURL = request.getRequestURL();
//	System.out.println("strURL :" + strURL.toString());
	GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	String  userCode = tG1.Operator;
	Access tAccess = new Access();
	boolean canAccess = tAccess.canAccess(userCode,strURL.toString());
	if (!canAccess)
	{
//		System.out.println("session is null");
		out.println("您无权访问此网页");
		return;
	}
}
catch(Exception exception){
	String ContentErr = " exception:请您重新登录！";
	System.out.println(ContentErr);
	out.println("网页出错，请您重新登录");
%>
<script language=javascript>
	top.window.location ="../indexlis.jsp";
</script>
<%
	return;
}
%>