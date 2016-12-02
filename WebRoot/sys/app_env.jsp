<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<html>
	<head>
		<title>Application environment</title>
	</head>
	
	<body>
		<%
		Logger cLogger = Logger.getLogger(getClass()); 
		cLogger.info("Into app_env.jsp...");
		%>
		
		Now: <%=DateUtil.getCurDateTime()%>
		<h1>Application environment</h1>
		<hr />
		<h2>app setting</h2>
		------
		<h3>MIDPLAT_HOME = <%=SysInfo.cHome%></h3>
		<h3>classes.path = <%=SysInfo.cBasePath%></h3>
		
		<%cLogger.info("Out app_env.jsp!");%>
	</body>
</html>