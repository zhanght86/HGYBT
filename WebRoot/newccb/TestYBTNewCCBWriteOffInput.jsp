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
<SCRIPT src="TestYBTNewCCBWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="TestYBTNewCCBWriteOffInit.jsp"%>
  
<title>����ͨ����Լ����</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./TestYBTNewCCBWriteOffSave.jsp" method=post name=fm target="fraSubmit">
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
			 <select name="ProductCode"  style="background-color: #D7E1F6" > 
            		<option value="221301">�к���δ�������</option>
            	<option value="231204">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
            	<option value="221201">�к����ݻ�����ȫ����A��</option>
            	<option value="231201">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��</option>
                <option value="231202">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="231203">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
                <option value="231302">�к�������������գ��ֺ��ͣ�</option>
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
	<input class=input name=TransRefGUID2 ><font color="red">*������Ϊ�գ�</font>
	</td>
	</TR>
<tr>
		<TD class=title>�������ײͱ��</TD>
		<TD class=input>
			<input class=input name=AgIns_Pkg_ID2  id=AgIns_Pkg_ID2><font color="red"></font>
		</TD>
		<TD class=title>�˱����</TD>
	<td class=input>
	<input class=input name=ReturnAmnt ><font color="red">�����ճ���ʱ��д��</font>
	</td>
	</TR>
</table>
</div>
	<table class=common align="center">
	<TR>
	<td class="titleImg">
					<IMG src="../common/images/butExpand.gif"  id="divCaculateimg" style="cursor: hand;"
							OnClick="showPage(this,divCaculate);"></IMG>
						�����뱣����Ϣ(���㱣�ղ�Ʒ)��
					</td>	
	</TR>
	</table>
	<div id="divCaculate" style="display:''">
	<table class=common align="center">
	<TR class=common>
		<TD class=title>�������ײͱ��</TD>
		<td><input class=input  name="AgIns_Pkg_ID" type="text"   size="12"/></td>
		<TD class=title>���ִ���</TD>
		<td class=input>
			 <select name="ProductCode2"  style="background-color: #D7E1F6" > 
            		<option value="221301">�к���δ�������</option>
            	<option value="231204">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
            	<option value="221201">�к����ݻ�����ȫ����A��</option>
            	<option value="231201">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��</option>
                <option value="231202">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="231203">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
                <option value="231302">�к�������������գ��ֺ��ͣ�</option>
            </select>
		</td>

	</TR>
<tr>
		<TD class=title>�Ա����</TD>
		<TD class=input>
			<select name="Gender" id="Ins2" style="background-color: #D7E1F6">
				<option value=""></option>
			     <option value="1"> ����</option>
				<option value="2"> Ů��</option>
			</select>
		</TD> 
		<TD class=title>����</TD>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt" type="text"  size="20"/></td>

	</TR>
<tr>

		<TD class=title>����</TD>
		<td bgColor="#F7F7F7"><input name="InitCovAmt" type="text"  size="20"/></td>
		<td class=title>�ɷѷ�ʽ</td>
		<TD class=input>  
			<select name=PaymentMode class="common1">
				<option value=""></option>
				<option value="9999"> �����ڽ� </option>
				<option value="0100"> ���� </option>
                <option value="0203"> ���� </option>
                <option value="0401"> ����ĳȷ������ </option>
                <option value="0501"> �������� </option>
             </select></TD>

	</TR>
<tr>
		<TD class=title>��������</TD>
		<td><input name="Duration" type="text"  size="10" maxlength="10"/></td>
		<TD class=title>�ɷ�����</TD>
		<td><input name="PaymentDuration" type="text"  size="10" maxlength="10"  /></td>
	</TR>
	
	<tr>
		<TD class=title>��������</TD>
		<td><input name="Ins_Age" type="text"  size="10" maxlength="10"/></td>
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
				<option value="1">��������</option>
				<option value="2">��������</option>
			</select></TD> 
			</tr>
	<tr class=common>
	<TD class=title>�ؿ���ʼ��</TD>
	<td class=input>
	<input class=input name=BkVchNo1  elementtype=nacessary><font color="red">�����
	</td>
		<TD class=title>�ؿս�����</TD>
	<td class=input>
	<input class=input name=BkVchNo2 elementtype=nacessary><font color="red">�����
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
				<option selected="selected" value="P53818152"> �̵ƽ���</option>
				<option value="P53816141"> ���㱣�ղ�Ʒ</option>
				<option value="P53819142"> ���ճ���</option>
				<option value="P53818154"> ���ճ���</option>
				<option value="P53819184"> �����ش�</option>
				<option value="P538191A2"> �ؿս���</option>
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
