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
<SCRIPT src="TestYBTCCBWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="TestYBTCCBWriteOffInit.jsp"%>
  
<title>����ͨ����Լ����</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./TestYBTCCBWriteOffSave.jsp" method=post name=fm target="fraSubmit">
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
		<TD class=title>ԭ������ˮ��</TD> 
		<TD class=input><input class=input name=OldTransRefGUID></TD>
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
	<input class=input maxlength="6" name=ProductCode ><font color="red">��6λ���֣�</font>
	</td>
	</TR>
<tr>
		<TD class=title>��֤ӡˢ��</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber id=IdProviderFormNumber elementtype=nacessary >
		</TD>
		<TD class=title>�������</TD>
	<td class=input>
	<input class=input name=Prem >
	</td>
	</TR>
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
	<TD class=title>Ԥ���ֶ�1</TD>
	<td class=input>
	<input class=input name=BkDetail2 >
	</td>
		<TD class=title>Ԥ���ֶ�2</TD>
	<td class=input>
	<input class=input name=BkDetail3 >
	</td>
	</tr>
</table>
</div>

<table class="common" align="center">
				<tr>
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divCheckimg" style="cursor: hand;"
							OnClick="showPage(this,divCheck);"></IMG>
						�ؿպ˶ԣ�
					</td>	
				</tr>
            </table>
            <div id="divCheck" style="display:''">
<table class="common" align=center>
<tr class=common>
		<TD class=title align=center>�ؿ�����</TD>
<TD class=input>
			<select name="CheckCode"   style="background-color: #D7E1F6" >
				<option value="010001000001">��������</option>
				<option value="010001000002">�ֽ��ֵ������</option>
				<option value="010001000003">��������</option>
				<option value="010001000004">��Ʊ����</option>
			</select></TD> 
			</tr>
	<tr class=common>
	<TD class=title>�ؿ���ʼ��</TD>
	<td class=input>
	<input class=input name=BkVchNo1 >
	</td>
		<TD class=title>�ؿս�����</TD>
	<td class=input>
	<input class=input name=BkVchNo2 >
	</td>
	</tr>
</table>
</div>
<!-- �˱���Ϣ���� -->
<table class="common" align="center">
				<tr class="common" align="center"> 
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divCancelTranforimg" style="cursor: hand;"
							OnClick="showPage(this,divCancelTranfor);"></IMG>
						�˱���Ϣ���ݣ�
					</td>	
				</tr>
            </table>
            <div id="divCancelTranfor" style="display:''">
<table class="common" align=center >
<tr class=common align="left">
		<TD class=title>�˱�����</TD>
	<TD class=input ><Input class="coolDatePicker"   dateFormat = "short" name=TransCancelDate></TD>
	<TD class=title></TD>
	<TD class=input ></TD>
	</tr>
</table>
</div>

<!-- ǩԼ��Լ���� -->
<table class="common" align="center">
				<tr class="common" align="center"> 
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divSignalimg"  style="cursor: hand;"
							OnClick="showPage(this,divSignal);"></IMG>
						ǩԼ��Լ���ף�
					</td>	
				</tr>
            </table>
            <div id="divSignal" style="display:''">
<table class="common" align=center >
<tr class=common >
	<TD class=title>ǩԼ������</TD>
	<td class=input>
	<input class=input name=ContCodeNo  elementtype=nacessary><font color="red">��ǩԼ��Լ���</font>
	</td>
	<TD class=title>�������۱�־</TD>
	<TD class=input>
			<select name="FSType"  elementtype=nacessary style="background-color: #D7E1F6" >
				<option value="1">����</option>
				<option value="2">����</option>
				<option value="3">����������</option>
			</select><font color="red">��ǩԼ��Լ���</font></TD> 
	</tr>
	<tr class=common >
		<TD class=title>�ͻ�������Ͷ���ˣ�</TD>
	<td class=input>
	<input class=input name=ClientName elementtype=nacessary><font color="red" >��ǩԼ��Լ���</font>
	</td>
	<TD class=title>Ͷ�����ʺ�</TD>
	<td class=input>
	<input class=input name=ClientAccNo ><font color="red">��ǩԼ���</font>
	</td>
	</tr>
	<tr class=common >
		<TD class=title>����ӡˢ��</TD>
	<td class=input>
	<input class=input name=MessPrtNo elementtype=nacessary><font color="red">��ǩԼ��Լ���</font>
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
				<option selected="selected" value="OPR999"> �̵ƽ���</option>
				<option value="OPR911"> ���ճ���</option>
				<option value="SPE802"> ���ճ���</option>
				<option value="SPE002"> �����ش�</option>
				<option value="SPE801">�˱�������Ϣ��ѯ</option>
				<option value="VCH102"> �ؿս���</option>
				<option value="BAT900"> ������ѯ</option>
				<option value="SPE010"> ǩԼ����</option>
				<option value="SPE013"> ��Լ����</option>
			</select></TD> 
	</tr>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="����Ͷ������" onclick="submitForm();"></TD> 
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
