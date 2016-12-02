<!-- 登陆页面 -->
<%@page contentType='text/html;charset=gb2312' %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
session.putValue("GI",null);
%>
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script src="../common/javascript/Common.js"></script>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<!-- 页面样式  -->
<link rel='stylesheet' type='text/css' href='../common/css/other.css'>
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

			//alert("tVD:" + tVD + "\ntEX:" + tEX + "\nmCs:" + typeof(mCs));
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
function changeImg(flag) {
	var fm = document.forms[0];
	if (flag == 0)
		fm.loginImg.src = "../common/images/Login_I_7_2_2.gif";
	else
		fm.loginImg.src = "../common/images/Login_I_7_2.gif";
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
}
.style3 {color: #000000}
</style>
</head>
<HTML>
<HEAD>
<meta http-equiv="content-type" content="text/html; charset=gb2312">
<body bgcolor="#ffffff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onkeypress = "exprint();">
<!-- ImageReady Slices (Login_I.jpeg) -->
<form name="fm" action="./LogonSubmit.jsp" method="post">
<TABLE WIDTH=866 BORDER=0 CELLPADDING=0 CELLSPACING=0 align="center">
	<TR>
		<TD COLSPAN=4>
			<IMG SRC="../common/images/Login_I_02_1.gif" WIDTH=866 HEIGHT=340 ALT=""></TD>
	</TR>
	<TR>
		<TD ROWSPAN=5>
			<IMG SRC="../common/images/Login_I_02_2.gif" WIDTH=345 HEIGHT=303 ALT=""></TD>
		<TD width="142" height="47"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../common/images/Login_I_02_3.gif">
        <tr>
          <td><div align="right" class="style1">用 户 名</div></td>
        </tr>
      </table></TD>
		<TD width="208" height="47"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../common/images/Login_I_02_3.gif">
        <tr>
         <td  align="center"><input name=UserCode class="common2" type="text" id="UserCode" size="18" maxlength="18" value=""> </td>
        </tr>
      </table></TD>
		<TD ROWSPAN=5>
			<IMG SRC="../common/images/Login_I_02_5.gif" WIDTH=171 HEIGHT=303 ALT=""></TD>
	</TR>
	<TR>
		<TD width="142" height="37"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../common/images/Login_I_02_6.gif">
        <tr>
          <td><div align="right" class="style1 style3"><strong>密&nbsp;&nbsp;&nbsp;&nbsp;码</strong></div></td>
        </tr>
      </table></TD>
		<TD width="208" height="37"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../common/images/Login_I_02_7.gif">
        <tr>
                     <td align="center"><input name=PWD class="common2" type="Password" id="PWD2" size="18" maxlength="18"value=""></td>
        </tr>
      </table></TD>
	</TR>
	<TR>
		<TD width="142" height="39"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../common/images/Login_I_02_8.gif">
        <tr>
          <td><div align="right" class="style1 style3"><strong>登录机构</strong></div></td>
        </tr>
      </table></TD>
		<TD width="208" height="39"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="../common/images/Login_I_02_9.gif">
        <tr>
          <td align="center"><input name=StationCode type="text" class="code2" id="StationCode" value=01 onDblClick="if (achieveInit()) showCodeList('comcode',[this]);"  onKeyUp="return showCodeListKey('comcode',[this]);" size="18"maxlength="18"></td>
        </tr>
      </table></TD>
	</TR>
	<TR>
		<TD ROWSPAN=2>			<img src="../common/images/Login_I_02_10.gif" width=142 height=180 alt=""></TD>

    <TD> <IMG SRC="../common/images/Login_I_7_1.gif" WIDTH=89 HEIGHT=50 ALT=""><img name="loginImg" src="../common/images/Login_I_7_2.gif" width=119 height=50 alt="" style="cursor:hand" onClick="return submitForm();" onMouseOver="JavaScript:changeImg(0);" onMouseOut="JavaScript:changeImg(1);" ></TD>
    <TD<tr><td colspan="2"><small><font face="Verdana"><input TYPE="hidden" name="ClientURL" value=""><br></font></small></td> </TD>
	</TR>
	<TR> 
		<TD>
			<IMG SRC="../common/images/Login_I_02_12.gif" WIDTH=208 HEIGHT=130 ALT=""></TD>
	</TR>
</TABLE>
<!--input style="height: 0px;width: 0px" type="submit" value="" onClick="return submitForm();"-->
<!-- End ImageReady Slices -->
<!--添加层-->
		<span id="spanCode"  style="display: none; position:absolute; slategray; left: 736px; top: 264px; width: 229px; height: 44px;">
        </span>
</form> </td>
  </tr>
</table>
</body>
</html>

<!-- XinYQ added on 2006-05-13 : 自动设置用户名为焦点 -->
<script language="JavaScript">
    try
	{
		document.getElementsByName("UserCode")[0].focus();
    }
    catch (ex) {}
</script>