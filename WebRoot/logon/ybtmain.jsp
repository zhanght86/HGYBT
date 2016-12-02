<!-- 登陆页面 -->
<%@page contentType='text/html;charset=gb2312' %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
session.putValue("GI",null);
%>
<HTML>
<HEAD>
<TITLE> 欢迎使用银保通系统 </TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script src="../common/javascript/Common.js"></script>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<!-- 页面样式  -->
<link rel='stylesheet' type='text/css' href='../common/css/other.css'>
</HEAD>
<script language=javascript>
function submitForm(){
if (!achieveInit()) return false;
	if(fm.UserCode.value.length == 0){
		alert("请输入用户名.");
		return false;
	}
	if(fm.PWD.value.length == 0){
		alert("请输入密码.");
		return false;
	}

	if (fm.StationCode.value.length == 0){
		alert("请选择管理机构.");
		return false;
	}
	fm.ClientURL.value = document.location;
	fm.submit();
	return true;
}
function achieveInit() {
	try {
		var tVD = top.achieveVD;
		var tEX = top.achieveEX;

		if (!(tVD && tEX) || typeof(mCs) == "undefined") {
			top.window.location = "../indexlis.jsp";
			alert("页面初始化未完成，请等待！");
			return false;
		}
	}
	catch(ex) {
		alert("页面初始化未错误!\ntop.window.location = '../indexlis.jsp'");
		return false;
	}
	return true;
}
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function exprint()
{
var keycode = event.keyCode;
if (keycode == "13") 
{
 submitForm();
 }
}
  
</script>
<style type="text/css">
	body {FONT-FAMILY: 宋体;font-size:9pt}
	td {FONT-FAMILY: 宋体;font-size:9pt}
	input {FONT-FAMILY: 宋体;font-size:9pt}
.style1 {
	font-size: 12pt;
	font-weight: bold;
	color: #ffffff;
}
.style3 {color: #000000}
</style>
<BODY style="background-image:url(../common/images/login_bg.jpg);background-repeat:repeat-x" onkeypress = "exprint();">
<body leftmargin="0" topmargin="0">
<center>
<form name="fm" action="./LogonSubmit.jsp" method="post">
<table width="713" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td height="118"><td>
</tr>
<tr>
	<td align="center">
		<table width="100%" height="515" border="0" cellpadding="0" cellspacing="0" style="background-image:url(../common/images/login_03.jpg);background-repeat:no-repeat">
		<tr class= common>
			<td height="218"><td>
		</tr>
		<tr class= common>
			<td align="right"  class="style1" width="45%">用户名：<td>
			<td ><input  name=UserCode class= common type="text" id="UserCode" size="25" maxlength="18" value="001" style="border:1 solid black;background-color:#ffffff;"></td>
		</tr>

		<tr class= common>
			<td  align="right"  class="style1">密码：<td>
			<td ><input name=PWD class= common type="Password" id="PWD2" size="25" maxlength="18"value="001" style=" border:1 solid black;background-color:#ffffff;"></td>
		</tr>
		<tr class= common>
			<td  align="right"  class="style1">登录机构：<td>
			<td ><input name=StationCode type="text" class="code2" id="StationCode" value=86 onDblClick="if (achieveInit()) showCodeList('comcode',[this]);"  onKeyUp="return showCodeListKey('comcode',[this]);" size="25"maxlength="18" ></td>
		</tr>
		<tr class= common>
			<td><td>
			<td align="center"><img src="../common/images/login_button.jpg" alt="登录" style="cursor:hand" onClick="return submitForm();" ><td>
		</tr>
		<tr>
			<td height="118"><td>
		</tr>
		</table>
	<td>
</tr>
<input TYPE="hidden" name="ClientURL" value="">
<span id="spanCode"  style="display: none; position:absolute; slategray;  width: 229px; height: 44px;">
        </span>
</table>
</form> 
</BODY>
</HTML>
<script language="JavaScript">
    try
	{
		document.getElementsByName("UserCode")[0].focus();
    }
    catch (ex) {}
</script>