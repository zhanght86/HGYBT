<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<html>
	<head>
		<title>jsp test</title>
	</head>
	
	<body>
		<%
		Logger cLogger = Logger.getLogger(getClass()); 
		cLogger.info("Into test.jsp...");
		%>
		
		<h1>Jsp is working well!</h1>
		Now: <%=DateUtil.getCurDateTime()%>
		
		<%cLogger.info("Out test.jsp!");%>
	</body>
</html>