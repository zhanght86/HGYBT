<!--
*******************************************************
* �������ƣ�LogonInput.jsp
* �����ܣ��û���¼��Ϣ����ҳ��
* �������ڣ�2002-05-10
* ���¼�¼��  ������    ��������     ����ԭ��/����
*             �����   2002-05-10    �½�
*******************************************************
-->
<%@ page contentType="text/html;charset=GBK" %>
<%
    //���session����
	session.putValue("usr" ,"");          //������Ϣ����
  	session.putValue("riskcode","");      //������Ϣ
  	session.putValue("station","");       //��վ��Ϣ
	session.putValue("stationname","");
%> 

<html>
<head>
<title>ϵͳ��¼</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script src="../JavaScript/Common.js"></script> 
<SCRIPT src="../JavaScript/CodeSelect.js"></SCRIPT>
<!-- ҳ����ʽ  -->
<link rel='stylesheet' type='text/css' href='../Css/TaikangStandard.css'>
</head>

<script language=javascript>
  function submitForm()
  {
	if (fm.UserCode.value.length == 0){
		alert("�������û���.");
		return false;
	}
	if (fm.StationName.value.length == 0){
		alert("��ѡ���¼��վ.");
		return false;
	}           
	parent.fraCVar.cvar.initVar();   //��չ��ñ������еı���ֵ
	fm.submit();
	return true;
  }
</script> 

<body background="../images/bgCommon.gif">
<!--��¼������--> 

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
										<td class=common width="27%">�û����룺</td>
										<td height="30" width="73%">
											<input class=common name=UserCode value="" maxlength=10>
										</td>
									</tr>
									<tr>
										<td class=common width="27%">�û����룺</td>
										<td height="30" width="73%">
											<input class=common type=password name=Password value="">
										</td>
									</tr>
									<tr>
										<td class=common width="27%">��¼��վ��</td>
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
