
<%--*************************************************************************
 * ��������: CheckUser.jsp
 * ������: Ͷ������ѯ��ʼ�жϽ���
 * ���������: κʿ��
 * �����������: 2002-05-14
 ****************************************************************************--%>
 <%
	String UserCode 		= "";
	String RiskCode 		= "";
	String strResponse 	= "";
	if( session.getValue("UserCode")==null )  //�û����벻����
	{
	  response.sendRedirect("Blank.html"); //�ض����������ִ���ҳ��
	  return ;
	}
	UserCode = (String)session.getValue("UserCode");

	/* �����û������ִ���(sessionֵ) */
	if( session.getValue("RiskCode")==null )  //���ִ��벻����
	{
	  response.sendRedirect("CurrentRiskSet.jsp"); //�ض����������ִ���ҳ��
	  return ;
	}
	RiskCode = (String)session.getValue("RiskCode");
	if (  checkLevel(Power.TB,Power.READ,RiskCode,UserCode) < Power.PERMIT )
	{
			strResponse = "�Բ�������Ȩ��������Ŀ��";
			response.sendRedirect("MessagePage.jsp?Type=P&Picture=F&Content=" + strResponse);
			return;
	}
%>

 