<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<html>
	<head>
		<title>System run environment</title>
	</head>
	
	<body>
		Now: <%=DateUtil.getCurDateTime()%>
		<h1>System run environment</h1>
		<hr />
		<h2>operator system</h2>
		------
		<h3>os = <%=System.getProperty("os.arch")%> <%=System.getProperty("os.name")%> <%=System.getProperty("os.version")%></h3>
		<hr />
		<h2>user</h2>
		------
		<h3>user.country = <%=System.getProperty("user.country")%></h3>
		<h3>user.language = <%=System.getProperty("user.language")%></h3>
		<h3>user.timezone = <%=System.getProperty("user.timezone")%></h3>
		<h3>user.name = <%=System.getProperty("user.name")%></h3>
		<h3>user.home = <%=System.getProperty("user.home")%></h3>
		<h3>user.dir = <%=System.getProperty("user.dir")%></h3>
		<hr />
		<h2>java</h2>
		------
		<h3>java.vendor = <%=System.getProperty("java.vendor")%></h3>
		<h3>java.home = <%=System.getProperty("java.home")%></h3>
		<h3>java.version = <%=System.getProperty("java.version")%></h3>
		<h3>java.class.version = <%=System.getProperty("java.class.version")%></h3>
		<h3>java.class.path = <%=System.getProperty("java.class.path")%></h3>
		<h3>file.encoding = <%=System.getProperty("file.encoding")%></h3>
		<hr />
		<h2>run</h2>
		------
		<h3>Free Memory = <%=Runtime.getRuntime().freeMemory()/(1024*1024)%>M</h3>
		<h3>Max  Memory = <%=Runtime.getRuntime().maxMemory()/(1024*1024)%>M</h3>
		<h3>Total Memory = <%=Runtime.getRuntime().totalMemory()/(1024*1024)%>M</h3>
		<h3>Available Processors = <%=Runtime.getRuntime().availableProcessors()%></h3>
	</body>
</html>