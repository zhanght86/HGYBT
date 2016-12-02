<%@page contentType="text/html;charset=GBK"%>
<% 
String sTranCom = request.getParameter("TranCom");
String sArea = request.getParameter("AreaNo");
String sCity = request.getParameter("CityNo");
String sContStatue = request.getParameter("ContStatue");
String sStartDay = request.getParameter("StartDay");
String sEndDay = request.getParameter("EndDay");
String sStartHour = request.getParameter("StartHour");
String sEndHour = request.getParameter("EndHour");
String sManageCodeNo = request.getParameter("ManageCodeNo");
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
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">

	<frame name="VD" src="../common/cvar/CVarData.html">

	<frame name="EX" src="../common/cvar/CExec.jsp">
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0" rows="*">
		<frame name="fraMenu" scrolling="yes" noresize src="">
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./FINPreviewInput.jsp?TranCom=<%=sTranCom%>&AreaNo=<%=sArea%>&CityNo=<%=sCity%>&ContStatue=<%=sContStatue%>&StartDay=<%=sStartDay%>&EndDay=<%=sEndDay%>&StartHour=<%=sStartHour%>&EndHour=<%=sEndHour%>&ManageCodeNo=<%=sManageCodeNo%>">
		<frame>
    <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
