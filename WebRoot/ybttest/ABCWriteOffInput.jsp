<%@page contentType="text/html;charset=GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"
	type="text/javascript"></SCRIPT>
<SCRIPT src="ABCWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ABCWriteOffInit.jsp"%>
  
<title>����ͨ����Լ����</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./ABCWriteOffSave.jsp" method=post name=fm target="fraSubmit">
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>�����뽻����Ϣ��</td>
	</tr>
	
<TR class=common>
		<TD class=title>���ж˽�������</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		<TD class=title>������ˮ��</TD> 
		<TD class=input><input class=input name=TransRefGUID></TD>
</TR>
	
	<TR class=common>
		<td class=title>��������</td>
		<TD class=input><Input class=input name=RegionCode></TD>
		<td class=title>�������</td>
		<TD class=input><Input class=input name=Branch></TD>
		
	</TR>
	
	<TR class=common> 
		<td class=title>��Ա����</td>
		<TD class=input><Input class=input name=Teller></TD>
		
	</TR>
	<TR>
		<TD class=titleImg align=center>�����뱣����Ϣ��</TD>
	</TR>
	<TR class=common>
		<TD class=title>������</TD>
		<TD class=input>
			<input class=input name=PolNumber id=IdPolNumber  elementtype=nacessary>
			<input type=hidden name=ContNo>
		</TD> 

		<TD class=title><div id=div1   style=display:none>�µ�֤ӡˢ��(�����ش�ʱ����)</div></TD>
		<TD class=input><div id=div11   style=display:none>
			<input class=input name=ProviderFormNumber id=IdProviderFormNumber ></div>
		</TD>
	</TR>
	 <tr>
		<TD class=title ><div id=div3> ����</div></TD>  
		<TD class=input><div id=div33>
			<input class=input name=Prem id=IdOriginalProviderFormNumber><font color="red"></font></div>
		</TD>
		<TD class=title><div id=div2 >Ͷ������</div></TD>  
		<TD class=input><div id=div22 >
			<input class=input name=APPDOCNO id=APPDOCNOId elementtype=nacessary><font color="red"></font></div>
		</TD>
	</TR>
</table>

<table class=common align=center>

	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr>
	
	<TR>
	<td class=title>������</td>
		<TD class=input>
			<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="WTranFlag(this.options[this.options.selectedIndex].value)">
				<option value="03"> ���ճ���</option>
				<option value="04"> �Զ�����</option>
			</select></TD> 
		<td class=title>���ձ���ip��ַ</td>
		<td class=input><Input class=input name=ip></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name=port></td>
		
	</tr>
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="��������" onclick="submitForm();"></TD> 
	</TR>
</table>
<hr/>

<table class=common>
	<tr>��������</tr>
	<tr>
		<td>
			<textarea rows="30" cols="100" name="xmlContent" id="xmlContent">
			
			</textarea>
		</td>
	</tr>
</table>
<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
