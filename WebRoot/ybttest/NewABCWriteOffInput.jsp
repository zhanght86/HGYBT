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
<SCRIPT src="NewABCWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="NewABCWriteOffInit.jsp"%>
  
<title>����ͨ����Լ����</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./NewABCWriteOffSave.jsp" method=post name=fm target="fraSubmit">
<table class=common align=center>
	<tr>
		<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id='divTranInfoimg' style="cursor: hand;"
							OnClick="showPage(this,divTranInfo);"></IMG>
						�����뽻����Ϣ��
					</td>	
	</tr>
	</table>
	<div id="divTranInfo" style="display:''">
	<table class=common align="center">
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
	</table>
	</div>
	<table class=common align="center">
	<TR>
	<td class="titleImg">
					<IMG src="../common/images/butExpand.gif"  id="divContInfoimg" style="cursor: hand;"
							OnClick="showPage(this,divContInfo);"></IMG>
						�����뱣����Ϣ(���ճ��������ճ����������ش�)��
					</td>	
	</TR>
	</table>
	<div id="divContInfo" style="display:''">
	<table class=common align="center">
	<TR class=common>
		<TD class=title>������</TD>
		<TD class=input>
			<input class=input name=PolNumber id=IdPolNumber maxlength="16" elementtype=nacessary><font color="red">��16λ���֣�</font>
			<input type=hidden name=ContNo>
		</TD> 
			<TD class=title>���ִ���</TD>
	<td class=input>
			 <select name="ProductCode"  style="background-color: #D7E1F6" > 
            	<option value="0006">�к���δ�������</option>
            	<option value="0005">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
            	<option value="0004">�к����ݻ�����ȫ����A��</option>
            	<option value="0001">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��</option>
                <option value="0002">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="0003">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
            </select>
	</td>
	</TR>
<tr>
		<TD class=title>��֤ӡˢ��</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber id=IdProviderFormNumber elementtype=nacessary >
		</TD>
		<TD class=title>ԭ������ˮ��</TD>
	<td class=input>
	<input class=input name=OldTransRefGUID >
	</td>
	</TR>
<tr>
		<TD class=title>Ͷ��������</TD>
	<td class=input>
	<input class=input name=ApptName ><font color="red">�����ճ���ʱ��д��</font>
	</td>
		<TD class=title>�˱����</TD>
	<td class=input>
	<input class=input name=ReturnAmnt ><font color="red">�����ճ���ʱ��д��</font>
	</td>
	</TR>
<tr>
		<TD class=title>ԭ��������</TD>
	<td class=input>
	<Input class="coolDatePicker"   dateFormat = "short" name=OrgTransDate><font color="red">��ȡ������ʱ��д��</font>
	</td>
		<TD class=title>ԭ���״���</TD>
	<td class=input>
				<select name="tranCode"   style="background-color: #D7E1F6" >
				<option selected="selected" value="1004"> �µ��ɷ�</option>
				<option value="1008"> ���ڽɷ�</option>
			</select>
	<font color="red">��ȡ������ʱ��д��</font>
	</td>
	</TR>
	<tr>
		<TD class=title>�ɷ��˻�</TD>
	<td class=input>
	<input class=input name=PayAcc ><font color="red">��ȡ������ʱ��д��</font>
	</td>
		<TD class=title>�ɷѽ��</TD>
	<td class=input>
	<input class=input name=PayAmt ><font color="red">��ȡ������ʱ��д��</font>
	</td>
	</TR>
	<tr>
		<TD class=title>ԭ��֤ӡˢ��</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber2 id=IdProviderFormNumber2 ><font color="red">�������ش�ʱ��д��</font>
		</TD>
		<TD class=title>Ͷ������</TD>
		<TD class=input>
			<input class=input name=ProposalPrtNo id=ProposalPrtNo  ><font color="red">�������ش�ʱ��д��</font>
		</TD>
	</TR>
</table>
</div>

<table class="common" align="center">
				<tr>
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divApplyNoimg"  style="cursor: hand;"
							OnClick="showPage(this,divApplyNo);"></IMG>
						�µ���������ѯ��
					</td>	
				</tr>
            </table>
            <div id="divApplyNo" style="display:''">
<table class="common" align=center>
	<tr class=common>
	<TD class=title>��������˳��ţ�</TD>
	<td class=input>
	<input class=input name=ApplyNo >
	</td>
	</tr>
</table>
</div>

<table class="common" align="center">
				<tr>
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divDateimg"  style="cursor: hand;"
							OnClick="showPage(this,divDate);"></IMG>
						�̵ƽ����ֶΣ�
					</td>	
				</tr>
            </table>
            <div id="divDate" style="display:''">
<table class="common" align=center>
	<tr class=common>
	<TD class=title>�����ֶ�</TD>
	<td class=input>
	<input class=input name=Reserve >
	</td>
	</tr>
</table>
</div>

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
		<TD class=input>
			<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="WTranFlag(this.options[this.options.selectedIndex].value)">
				<option selected="selected" value="1000"> �̵ƽ���</option>
				<option value="1010"> ���ճ���</option>
				<option value="1009"> ���ճ���</option>
				<option value="1018"> �����ش�</option>
				<option value="1005"> �����ѯ</option>
			</select></TD> 
	</tr>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="����������" onclick="submitForm();"></TD> 
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
