<!--
*******************************************************
* 程序名称：LogonInput.jsp
* 程序功能：用户登录信息输入页面
* 创建日期：2002-05-10
* 更新记录：  更新人    更新日期     更新原因/内容
*             朱向峰   2002-05-10    新建
*******************************************************
-->
<%@ page contentType="text/html;charset=GBK" %>
<%
    //清空session对象
	session.putValue("usr" ,"");          //操作信息对象
  	session.putValue("riskcode","");      //险种信息
  	session.putValue("station","");       //区站信息
	session.putValue("stationname","");
%> 

<html>
<head>
<title>系统登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script src="../JavaScript/Common.js"></script> 
<SCRIPT src="../JavaScript/CodeSelect.js"></SCRIPT>
<!-- 页面样式  -->
<link rel='stylesheet' type='text/css' href='../Css/TaikangStandard.css'>
</head>

<script language=javascript>
  function submitForm()
  {
	if (fm.UserCode.value.length == 0){
		alert("请输入用户名.");
		return false;
	}
	if (fm.StationName.value.length == 0){
		alert("请选择登录区站.");
		return false;
	}           
	parent.fraCVar.cvar.initVar();   //清空公用保存域中的变量值
	fm.submit();
	return true;
  }
</script> 

<body background="../images/bgCommon.gif">
<!--登录画面表格--> 

	<table width="90%">
		<tr>
			<td height="40"><img src="../images/cpLogonLine.gif" width="233" height="12"></td>
		</tr>
		<tr>
			<td>
				<table width="451" border="0" cellspacing="0" cellpadding="0" height="204" align="center" >
					<tr>
						<td height="210"  background="../images/bgLogon.gif">
							<form name=fm action=LogonSubmit.jsp method=post target="fraSubmit">
								<table align="right" width="60%" >
									<tr>
										<td colspan=2  class=common>
											<p>&nbsp;</p>
											<p>&nbsp;</p>
										</td>
									</tr>
									<tr>
										<td class=common width="27%">用户代码：</td>
										<td height="30" width="73%">
											<input class=common name=UserCode value="" maxlength=10>
										</td>
									</tr>
									<tr>
										<td class=common width="27%">用户密码：</td>
										<td height="30" width="73%">
											<input class=common type=password name=Password value="">
										</td>
									</tr>
									<tr>
										<td class=common width="27%">登录区站：</td>
										<td height="30" width="73%">
											<input type=hidden  name=StationCode value="">
											<input class="code"  name=StationName value="" onkeyup="return showCodeListKey(this,'station',1,'');"  
											                                               onclick="return showCodeList(this,'station',1,'');">
										</td>
									</tr>
									<tr>
										<td height="45" colspan="2">
											<table width="70%" border="0" cellspacing="0" cellpadding="0">
												<tr align="center">
													<td>
														<img src="../images/butReset.gif" width="65" height="23" onClick="fm.reset()">
													</td>
													<td>
														<img src="../images/butLogon.gif" width="65" height="23" onClick="return submitForm();">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table> 
							    <span id="spanCode"  style="display: none; position:absolute; slategray">
  									<select name=codeselect>
  									</select>
								</span>
							</form>
						</td>
            				</tr>
          			</table>
        		</td>
      		</tr>
      		<tr>
        		<td align="right" height="40" valign="bottom"><img src="../images/cpLogonLine.gif" width="233" height="12"></td>
      		</tr>
	</table>
</body>
</html>
