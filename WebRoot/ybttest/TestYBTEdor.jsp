<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
	

<SCRIPT src="TestYBTEdor.js"></SCRIPT>
<script type="text/javascript"></script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestYBTEdorfInit.jsp"%>  

		<title>����ͨ��ȫ����</title>
	</head>

<body onload="initElementtype();initForm();">
<form action="./TestYBTEdorSave.jsp" method=post name=fm target="fraSubmit">
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>�����뽻����Ϣ��</td>
	</tr>
<TR class=common>
		<TD class=title>���ж˽�������</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		
		
		<TD class=title>������ˮ��</TD> 
		<TD class=input><input class=input name=TransNo></TD>
	</TR>
	
	<TR class=common>
		<td class=title>��������</td>
		<TD class=input><Input class=input name="ZoneNo"></TD>
		<td class=title>�������</td>
		<TD class=input><Input class=input name="NodeNo"></TD>
		
	</TR>
	
	
	<TR>
		<TD class=titleImg align=center>�����뱣����Ϣ��</TD>
	</TR>
	<TR class=common>
		
		<TD class=title>������</TD>  
		<TD class=input>
			<input class=input name=PolNumber></font>
		</TD>
		
		<TD class=title>��֤ӡˢ��</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>�����˻�</TD>
		<TD class=input>
			<input class=input name=AccountNumber>
		</TD> 
		
       <TD class=title><span id="AccountNameTitle" style="display:''">�˻�����</span></TD>  
		<TD class=input>
			<span id="AccountName" style="display:''"><input class=input name=AccountForName></span></font>
		</TD>
	 
		
	</TR>
	<tr>
	 <TD class=title><span id="CortransrnoName" style="display:'none'">ԭ������ˮ�ţ�����ʹ�ã�</span></TD>  
		<TD class=input>
			<span id="Cortransrno" style="display:'none'"><input class=input name=CortransrnoNo></span></font>
		</TD>
	</tr>
	
	<TR>
		<TD class=titleImg align=center><span id="appntInfoTitle" style="display:''">Ͷ������Ϣ��</span></TD>
	</TR>
	<TR class=common>
		<TD class=title><span id="appntnameTitle" style="display:''">Ͷ��������</span></TD>  
		<TD class=input>
			<span id="appntnameInput" style="display:''"><input class=input name=AppntName></span></font>
		</TD>
		<td class=title><span id="appntIDTypeTitle" style="display:''">Ͷ����֤������</span></td>
		<TD class=input><span id="appntIDTypeInput" style="display:''">
			<select name="AppGovtIDTC" style="background-color: #D7E1F6">
			    <option value=""></option>
			    <option value="0"> ���֤</option>
				<option value="1"> ����</option>
				<option value="2"> ����֤</option>
				<option value="3"> ʿ��֤</option>
				<option value="4"> ����֤</option>
				<option value="5"> ��ʱ���֤</option>
				<option value="6"> ���ڱ�</option>
				<option value="7"> ����</option>
				<option value="9"> ����֤</option>
			</select></span></TD>
			
		<td class=title><span id="appntIDTitle" style="display:''">Ͷ����֤������</span></td>
		<TD class=input><span id="appntIDInput" style="display:''"><Input class=input name=AppGovtID></TD>
	<TD class=input>
		</TD>
		<TD class=input>
		</TD>
	</TR>
	
	<TR>
		<TD class=titleImg align=center><span id="insureInfoTitle" style="display:''">��������Ϣ��</span></TD>
	</TR>
	<TR class=common>
		<TD class=title><span id="insurenameTitle" style="display:''">����������</span></TD>  
		<TD class=input>
			<span id="insurenameInput" style="display:''"><input class=input name=InsureName></span></font>
		</TD>
		<td class=title><span id="insureIDTypeTitle" style="display:''">������֤������</span></td>
		<TD class=input><span id="insureIDTypeInput" style="display:''">
			<select name="InsureGovtIDTC" style="background-color: #D7E1F6">
	     		<option value=""></option>
			    <option value="0"> ���֤</option>
				<option value="1"> ����</option>
				<option value="2"> ����֤</option>
				<option value="3"> ʿ��֤</option>
				<option value="4"> ����֤</option>
				<option value="5"> ��ʱ���֤</option>
				<option value="6"> ���ڱ�</option>
				<option value="7"> ����</option>
				<option value="9"> ����֤</option>
			</select></span></TD>
			
		<td class=title><span id="insureIDTitle" style="display:''">������֤������</span></td>
		<TD class=input><span id="insureIDInput" style="display:''"><Input class=input name=InsureGovtID></TD>
	<TD class=input>
		</TD>
		<TD class=input>
		</TD>
	</TR>
		
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
		<TD class=input>
			<select name="TranType"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
				<option value="1003"> ��ԥ���˱���ѯ</option>
				<option value="1004"> ��ԥ���˱�</option>
				<option value="1030">��ԥ���˱�����</option>
				<option value="1021">�˱���ѯ</option>
				<option value="1017">�˱�</option>
				<option value="1026">�˱�����</option>
			</select></TD>
			
			
		<td class=title>��������</td>  
		<td class=input> 
			<select name=saleChannel style="background-color:#D7E1F6"  class="common1" >
			    <option value=""></option>
				<option value="0" selected="selected"> �������</option>
         		<option value="1"> �������� </option>
         		<option value="8"> �����ն� </option>
             </select>
		</td>     
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="���ͽ�������" onclick="submitForm();"></TD> 
	</TR>
</table>
<table class=common align = center>
	<tr>������Ϣ</tr>
	<tr>
		<td>
			<textarea rows="30" cols="100" name="xmlBackContent" id="xmlBackContent">
			
			</textarea>
		</td>
	</tr>
</table>


<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
