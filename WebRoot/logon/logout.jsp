<%@ page contentType="text/html;charset=GBK" %>
<%
//******************************************************
// �������ƣ�Logout.jsp
// ������:��
// ��������ˣ�DingZhong
// ����������ڣ�2002-12-22
//******************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.logon.logoutUI"%>
<%
session.invalidate();
//System.out.println("start logout");
//System.out.println("start clear data...");
try {
	GlobalInput tG1 = new GlobalInput();
	tG1 = (GlobalInput)session.getValue("GI");
//	System.out.println(tG1.ComCode);
	//VData inputData = new VData();
	//inputData.addElement(tG1);
	//logoutUI tlogoutUI = new logoutUI(); 
//	tlogoutUI.submitData(inputData,"LogOutProcess");
//	System.out.println("completed clear data");
}
catch (Exception exception)
{
	System.out.println("Log out error ...");
}
%>
<script language=javascript>
session = null;
//top.window.location ="../indexlis.jsp";
//����ϵͳ���ҵ��ͬʱ֧��
top.window.navigate(top.window.location);
</script>  