
<%--*************************************************************************
 * 程序名称: CheckUser.jsp
 * 程序功能: 投保单查询初始判断界面
 * 最近更新人: 魏士鑫
 * 最近更新日期: 2002-05-14
 ****************************************************************************--%>
 <%
	String UserCode 		= "";
	String RiskCode 		= "";
	String strResponse 	= "";
	if( session.getValue("UserCode")==null )  //用户代码不存在
	{
	  response.sendRedirect("Blank.html"); //重定向到设置险种代码页面
	  return ;
	}
	UserCode = (String)session.getValue("UserCode");

	/* 检验用户的险种代码(session值) */
	if( session.getValue("RiskCode")==null )  //险种代码不存在
	{
	  response.sendRedirect("CurrentRiskSet.jsp"); //重定向到设置险种代码页面
	  return ;
	}
	RiskCode = (String)session.getValue("RiskCode");
	if (  checkLevel(Power.TB,Power.READ,RiskCode,UserCode) < Power.PERMIT )
	{
			strResponse = "对不起，你无权操作该项目。";
			response.sendRedirect("MessagePage.jsp?Type=P&Picture=F&Content=" + strResponse);
			return;
	}
%>

 