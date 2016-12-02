<%@ page contentType="text/html;charset=GBK" %>
<!--*******************************************************
* 程序名称：Title.jsp
* 程序功能：系统标题页面
* 最近更新人：朱向峰
* 最近更新日期：2002-08-21
* 				2004-11-3 16:58
*******************************************************-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<!-- 页面样式  -->
<link rel='stylesheet' type='text/css' href='../common/css/Project.css'>
<script language="JavaScript">
function showTitle(){
	parent.fraMain.rows = "0,0,0,0,*";
	parent.fraTalk.rows = "30,*";
}
function showHideFrame(){
	try{
		if(parent.fraSet.cols==	"0%,*,0%"){
			parent.fraSet.cols = "180,*,0%";
			menuPowerImage.src = "../common/images/butHide.gif";
		}
		else if(parent.fraSet.cols=="180,*,0%"){
			parent.fraSet.cols = "0%,*,0%";
			menuPowerImage.src = "../common/images/butShow.gif";
			}
		}
		catch(re){}
	}
</script>
</head>
<style type="text/css">
<!--
	body {FONT-FAMILY: 宋体;font-size:9pt;FONT-WEIGHT: bold}
	td {FONT-FAMILY: 宋体;font-size:9pt;FONT-WEIGHT: bold}
	input {FONT-FAMILY: 宋体;font-size:9pt;FONT-WEIGHT: bold}
-->
</style>
<body text="#000000" leftmargin="0" topmargin="2" marginwidth="0" marginhigh="2">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align=center bgcolor="#94FEFC">
		<tr> 
			<td background="../common/images/bg.gif" width=36 height=72 valign=bottom><img name=menuPowerImage src="../common/images/butHide.gif" onclick="showHideFrame();" width="36" height="16"></td>
			<td width=735 height=72><img src="../common/images/logo.gif" width=735 height=72></td>
			<td width=100%>&nbsp;</td>
		</tr>
	</table>
</body>
</html>