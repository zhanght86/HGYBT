<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-13
 * @direction: 关闭系统时使会话失效, 节省服务器资源
 ******************************************************************************/
%>

<%
    session.invalidate();
    System.out.println("\t@> 销毁 session, 退出系统 ****************************************");
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>退出系统</title>
</head>
<body topmargin="0" ondragstart="return false" oncontextmenu="return false">
    <br><center><font size="2">正在退出，请稍候...</font></center>
</body>
</html>
<script language="JavaScript">try{top.window.close();}catch(ex){}</script>
