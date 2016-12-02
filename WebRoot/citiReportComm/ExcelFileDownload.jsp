<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.jspsmart.upload.*"%>
<%@page import="java.io.*"%>
<%
SmartUpload su = new SmartUpload();
su.initialize(pageContext);
su.setContentDisposition(null);
out.clear();
out = pageContext.pushBody();
su.downloadFile(request.getParameter("filePath"));
System.out.println(request.getParameter("filePath"));
java.io.File tFile=new java.io.File(request.getParameter("filePath"));
tFile.delete();
%>
<html>
</html> 