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
<SCRIPT src="TestYBTWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="TestYBTWriteOffInit.jsp"%>

<title>����ͨ����Լ����</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./TestYBTWriteOffSave.jsp" method=post name=fm target="fraSubmit">
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
			<input class=input name=PolNumber elementtype=nacessary>
			<input type=hidden name=ContNo>
		</TD> 
		<TD class=title>Ͷ�����</TD>  
		<TD class=input>
			<input class=input name=HOAppFormNumber ><font color="red"></font>
		</TD>
	</TR>
		<TR class=common>
		<TD class=title>��֤ӡˢ��</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber>
		</TD> 
		<TD class=title>ԭ��֤ӡˢ��</TD>  
		<TD class=input>
			<input class=input name=OriginalProviderFormNumber ><font color="red"></font>
		</TD>
	</TR>
</table>

<table class=common align=center>

	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	
	<TR>
		<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name=ip></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name=port></td>
		<td class=title>������</td> 
		<td class=input> 
		<input class="codeno" name="tranFlagCode" 
			   ondblclick="return showCodeList('funcflag_3',[this,tranFlagCodeName],[0,1],null,null,null,1,null,1);" 
			   onkeyup="return showCodeListKey('funcflag_3',[this,tranFlagCodeName],[0,1],null,null,null,1,null,1);" />
		<input class="codename" name="tranFlagCodeName" readonly="true" />
		</td>   
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="����Ͷ������" onclick="submitForm();"></TD> 
	</TR>
</table>
<hr/>
<a target="_blank" class="smallLink" href="../msg/RetXMLIn.xml">���ͱ�������</a>
<a target="_blank" class="smallLink" href="../msg/RetXMLReturn.xml">���ر�������</a>
<hr/>  

<table class=common>
	<caption>���ͱ�������</caption>
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
