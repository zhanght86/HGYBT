<%@page contentType="text/html;charset=GBK"%>
<% 
String startDay = request.getParameter("StartDay");
String endDay = request.getParameter("EndDay");
%>
<html>
<head>
<title>Sinosoft </title>
<script language="javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.focus();
</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">

	<frame name="VD" src="../common/cvar/CVarData.html">

	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<frame name="fraMenu" scrolling="yes" noresize src="">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./PFTDPreviewInput.jsp?StartDay=<%=startDay%>&EndDay=<%=endDay%>">
		<frame>
    <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
