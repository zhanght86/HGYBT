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
<SCRIPT src="LIANTestYBTCont.js"></SCRIPT>
<script type="text/javascript">
	
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   
		<%@include file="LIANTestYBTContInit.jsp"%>  
 
		<title>����ͨ����Լ����</title>
	</head>
<body onload="initElementtype();initForm();"> 
<form action="./LIANTestYBTSave.jsp" method=post name=fm target="fraSubmit">


<%--*************************������Ϣ*************************--%>
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	<tr>
<td class=title>������</td>
<TD class=input>
			<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="01"> �µ��˱�</option>
				<option value="02"> �µ�����</option>
					<!--<option value="03"> ���ճ���</option>-->
				<!--  option value="1011"> �µ��ش�</option>
				<option value="03"> ���ճ���</option>-->
			</select></TD>
		
			<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name=ip></td> 
			
			<td class=title>��������</td>
			<td class=input><input class="codeno" name="port" verify="��������|NotNull" readonly="readonly">
				<input class="codename" name="portName" readonly="true" elementtype="nacessary" /></td> 
			
			&nbsp;&nbsp;
	<TR class=common>
		<TD class=input width="26%"><input class="cssButton" type="button" name="Submit1" value="����Ͷ������" onClick="submitForm()"/></TD> 
	</TR>
</table>
		
<hr>

<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 
	
	<TR class=common>
		<TD class=title>���ж˽�������</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=BankDate></TD>
		<TD class=title>������ˮ��</TD> 
		<TD class=input><input class=input name="TransrNo"><input type="hidden" name="InputTransrNo"></TD>
	</TR>
	
	<TR class=common>
		<td class=title>��������</td>
		<TD class=input><Input class=input name="ZoneNo"></TD>
		<td class=title>�������</td>
		<TD class=input><Input class=input name="BrNo"></TD>
	</TR>
	
	<TR class=common> 
		<td class=title>��Ա����</td>
		<TD class=input><Input class=input name="TellerNo"></TD>
		<td class=title>Ͷ������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=PolApplyDate></TD>
	</TR>
	
	<TR class=common>
		<td class=title>Ͷ�����</td>
		<TD class=input><Input class=input name="ProposalContNo" id="ProposalNo"></TD>
		<td class=title>����ӡˢ��</td>
		<TD class=input><Input class=input name="PrtNo" id="PrtNo"></TD>
	</TR>
	
	<TR class=common>
		<td class=title>ԭ������ˮ��</td>
		<TD class=input><Input class=input name="ReqsrNo" id="OldTranNo"></TD>
		 <!--
			 <td class=title>ԭ����ӡˢ��</td>
				<TD class=input><Input class=input name=OriginalProviderFormNumber id="OldPrtNo"></TD>	
			</tr>
				<TR class=common>
				<td class=title>������</td>
				<TD class=input><Input class=input name=PolNumber id="ContNo"></TD>	
			</tr>
		-->
	</TR>
	
	
</table>

<hr>
<%--*************************Ͷ������Ϣ************************--%>
<table class=common align=center >

	<tr>
		<td class=titleImg align=center>��Ͷ������Ϣ��</td>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ��������</td>
		<TD class=input><Input class=input name=ApplName></TD>
		<td class=title>Ͷ�����Ա�</td>
		<TD class=input>
			<select name="ApplSex" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></TD>
		
	</tr> 
	
	<TR class=common> 
		<td class=title>Ͷ����֤������</td>
		<TD class=input>
			<select name="ApplIDType" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> �������֤</option>
				<option value="110002"> �غž������֤</option>
				<option value="110003"> ��ʱ�������֤</option>
				<option value="110004"> �غ���ʱ�������֤</option>
				<option value="110005"> ���ڲ�</option>
				<option value="110006"> �غŻ��ڲ�</option>
				<option value="110011"> ���ݸɲ�����֤</option>
				<option value="110012"> �غ����ݸɲ�����֤</option>
				<option value="110013"> ��������֤</option>
				<option value="110014"> �غž�������֤</option>
				<option value="110015"> ��ְ�ɲ�����֤</option>
				<option value="110016"> �غ���ְ�ɲ�����֤</option>
				<option value="110017"> ����ԺУѧԱ֤</option>
				<option value="110018"> �غž���ԺУѧԱ֤</option>
				<option value="110019"> �۰ľ��������ڵ�ͨ��֤</option>
				<option value="110020"> �غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110021"> ̨�����������½ͨ��֤</option>
				<option value="110022"> �غ�̨�����������½ͨ��֤</option>
				<option value="110023"> �л����񹲺͹�����</option>
				<option value="110024"> �غ��л����񹲺͹�����</option>
				<option value="110025"> �������</option>
				<option value="110026"> �غ��������</option>
				<option value="110027"> ����֤</option>
				<option value="110028"> �غž���֤</option>
				<option value="110029"> ��ְ�ɲ�֤</option>
				<option value="110030"> �غ���ְ�ɲ�֤</option>
				<option value="110031"> ����֤</option>
				<option value="110032"> �غž���֤</option>
				<option value="110033"> ����ʿ��֤</option>
				<option value="110034"> �غž���ʿ��֤</option>
				<option value="110035"> �侯ʿ��֤</option>
				<option value="110036"> �غ��侯ʿ��֤</option>
				<option value="119998"> ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="119999"> ��������֤��ʶ���ʶ</option>
				
			</select></TD>
			
		<td class=title>Ͷ����֤������</td>
		<TD class=input><Input class=input name="ApplIDNo"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="ApplBirthday"></TD>
		<td class=title>Ͷ���˵�������</td>
		<TD class=input><Input class=input name="ApplEmail">
		</TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ����ͨѶ��ַ	</td>
		<TD class=input><Input class=input name="ApplAddress"></TD>
		<td class=title>Ͷ������������	</td>
		<TD class=input><Input class=input name="ApplZipCode"></TD>	
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ���˼�ͥ�绰</td>
		<TD class=input><Input class=input name="ApplPhone"></TD>
		<td class=title>Ͷ�����ƶ��绰	</td>
		<TD class=input><Input class=input name="ApplMobile"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ�����뱻���˵Ĺ�ϵ</td>
		<TD class=input>
			<select name="ApplRelaToInsured" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> �ɷ�</option>
				<option value="3"> ����</option>
				<option value="4"> ����</option>
				<option value="5"> ĸ��</option>
				<option value="6"> ����</option>
				<option value="7"> Ů��</option>
				<option value="8"> �游</option>
				<option value="9"> ��ĸ</option>
				<option value="10"> ����</option>
				<option value="11"> ��Ů</option>
				<option value="12"> ���游</option>
				<option value="13"> ����ĸ</option>
				<option value="14"> ����</option>
				<option value="15"> ����Ů</option>
				<option value="16"> ���</option>
				<option value="17"> ���</option>
				<option value="18"> �ܵ�</option>
				<option value="19"> ����</option>
				<option value="20"> ����</option>
				<option value="21"> ����</option>
				<option value="22"> ��ϱ</option>
				<option value="23"> ����</option>
				<option value="24"> ��ĸ</option>
				<option value="25"> Ů��</option>
				<option value="26"> ��������</option>
				<option value="27"> ͬ��</option>
				<option value="28"> ����</option>
				<option value="29"> ����</option>
				<option value="30"> ����</option>	 
			</select></TD>
		<td class=title>Ͷ����ְҵ����</td>  
		<TD class=input> 
			<select name="ApplJobCode"  style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="01"> ���һ��ء���Ⱥ��֯����ҵ����ҵ��λ��Ա</option>
						 <option value="02"> ����רҵ������Ա</option>
						 <option value="03"> ����ҵ����Ա</option>
						 <option value="04"> ����רҵ��Ա</option>
						 <option value="05"> ��ѧ��Ա</option>
						 <option value="06"> ���ų��漰��ѧ����������Ա</option>
						 <option value="07"> �ڽ�ְҵ��</option>
						 <option value="08"> �����͵���ҵ����Ա</option>
						 <option value="09"> ��ҵ������ҵ��Ա</option>
						 <option value="10"> ũ���֡������桢ˮ��ҵ������Ա</option>
						 <option value="11"> ������Ա</option>
						 <option value="12"> ���ʿ�����Ա</option>
						 <option value="13"> ����ʩ����Ա</option>
						 <option value="14"> �ӹ����졢���鼰������Ա</option>
						 <option value="15"> ����</option>
						 <option value="16"> ��ҵ</option>
				
			</select>
		</TD>	
		
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ����֤����Ч��</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="ApplValidYear"></TD>
	</tr>

</table>


<hr>
<%--*************************��������Ϣ************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>����������Ϣ��</td>
		<td colspan="2"><input type="checkbox" name=InsFlag value="0" onClick="setInsFlag(this);">
		<font color="red">�������Ƿ�Ϊ����</font></td>
	</tr>
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class=input name="InsuName" id="Ins1"></TD>
		<td class=title>�������Ա�</td>
		<TD class=input>
			<select name="InsuSex" id="Ins2" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></TD>
			<input type="hidden" name = "InsuSexh">
	</tr> 
	
	<TR class=common> 
		<td class=title>������֤������</td>
		<TD class=input>
			<select name="InsuIDType" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> �������֤</option>
				<option value="110002"> �غž������֤</option>
				<option value="110003"> ��ʱ�������֤</option>
				<option value="110004"> �غ���ʱ�������֤</option>
				<option value="110005"> ���ڲ�</option>
				<option value="110006"> �غŻ��ڲ�</option>
				<option value="110011"> ���ݸɲ�����֤</option>
				<option value="110012"> �غ����ݸɲ�����֤</option>
				<option value="110013"> ��������֤</option>
				<option value="110014"> �غž�������֤</option>
				<option value="110015"> ��ְ�ɲ�����֤</option>
				<option value="110016"> �غ���ְ�ɲ�����֤</option>
				<option value="110017"> ����ԺУѧԱ֤</option>
				<option value="110018"> �غž���ԺУѧԱ֤</option>
				<option value="110019"> �۰ľ��������ڵ�ͨ��֤</option>
				<option value="110020"> �غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110021"> ̨�����������½ͨ��֤</option>
				<option value="110022"> �غ�̨�����������½ͨ��֤</option>
				<option value="110023"> �л����񹲺͹�����</option>
				<option value="110024"> �غ��л����񹲺͹�����</option>
				<option value="110025"> �������</option>
				<option value="110026"> �غ��������</option>
				<option value="110027"> ����֤</option>
				<option value="110028"> �غž���֤</option>
				<option value="110029"> ��ְ�ɲ�֤</option>
				<option value="110030"> �غ���ְ�ɲ�֤</option>
				<option value="110031"> ����֤</option>
				<option value="110032"> �غž���֤</option>
				<option value="110033"> ����ʿ��֤</option>
				<option value="110034"> �غž���ʿ��֤</option>
				<option value="110035"> �侯ʿ��֤</option>
				<option value="110036"> �غ��侯ʿ��֤</option>
				<option value="119998"> ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="119999"> ��������֤��ʶ���ʶ</option>
			</select></TD>
			<input type="hidden" name = "InsuIDTypeh">
			<td class=title>������֤������</td>
		<TD class=input><Input class=input id="Ins4" name="InsuIDNo"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsuBirthday"></TD>
		<td class=title>�����˵�������</td>
		<TD class=input><Input class=input id="Ins6" name="InsuEmail">
		</TD>
	</tr>
	
	<TR class=common>
		<td class=title>������ͨѶ��ַ	</td>
		<TD class=input><Input class=input id="Ins7" name="InsuAddress"></TD>
		<td class=title>��������������	</td>
		<TD class=input><Input class=input id="Ins8" name="InsuZipCode"></TD>	
	</tr>
	 
	<TR class=common>
		<td class=title>�����˼�ͥ�绰</td>
		<TD class=input><Input class=input id="Ins9" name="InsuPhone"></TD>
		<td class=title>�������ƶ��绰	</td>
		<TD class=input><Input class=input id="Ins10" name="InsuMobile"></TD>
	</tr>
	 
	<TR class=common>
		<td class=title>������֪��־</td>  
		<TD class=input> 
			<select name="HealthFlag" id="Ins11" style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="0"> �޽�����֪</option>
						 <option value="1"> �н�����֪</option>
						</select></TD>
		<td class=title>������ְҵ����</td>  
		<TD class=input> 
			<select name="InsuJobCode" id="Ins12" style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="01"> ���һ��ء���Ⱥ��֯����ҵ����ҵ��λ��Ա</option>
						 <option value="02"> ����רҵ������Ա</option>
						 <option value="03"> ����ҵ����Ա</option>
						 <option value="04"> ����רҵ��Ա</option>
						 <option value="05"> ��ѧ��Ա</option>
						 <option value="06"> ���ų��漰��ѧ����������Ա</option>
						 <option value="07"> �ڽ�ְҵ��</option>
						 <option value="08"> �����͵���ҵ����Ա</option>
						 <option value="09"> ��ҵ������ҵ��Ա</option>
						 <option value="10"> ũ���֡������桢ˮ��ҵ������Ա</option>
						 <option value="11"> ������Ա</option>
						 <option value="12"> ���ʿ�����Ա</option>
						 <option value="13"> ����ʩ����Ա</option>
						 <option value="14"> �ӹ����졢���鼰������Ա</option>
						 <option value="15"> ����</option>
						 <option value="16"> ��ҵ</option>
					
			</select>
		</TD>
	</tr>
	<TR class=common>
		<td class=title>������֤����Ч��</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="InsuValidYear"></TD>
	</tr>

</table>
 

<hr>

<%--*************************��������Ϣ************************--%>
<table class=common align=center> 
	<tr> 
		<td class=titleImg align=center>����������Ϣ��</td>
		 <td colspan="2"><input type="checkbox" id = "BnfFlag" name="BnfFlag"  onClick="setBnfFlag(this);"><%-- 
	<input type="hidden" name="BeneficiaryIndicator"> 
		 --%><font color="red">�������Ƿ�Ϊ����(ѡ��Ϊ����)</font></td> 
	</tr>    

	<tr >  
		<td>�뱻�����˹�ϵ</td>
		<td>�������</td>
		<td>����</td>
		<td>�Ա�</td>
		<td>֤������	</td>
		<td>֤������	</td>
		<td>����������	</td>
		<td>�������</td>
		<td>����˳��</td>
		<td>֤����Ч��</td>
	</tr>
						
<%--*************������1*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> �ɷ�</option>
				<option value="3"> ����</option>
				<option value="4"> ����</option>
				<option value="5"> ĸ��</option>
				<option value="6"> ����</option>
				<option value="7"> Ů��</option>
				<option value="8"> �游</option>
				<option value="9"> ��ĸ</option>
				<option value="10"> ����</option>
				<option value="11"> ��Ů</option>
				<option value="12"> ���游</option>
				<option value="13"> ����ĸ</option>
				<option value="14"> ����</option>
				<option value="15"> ����Ů</option>
				<option value="16"> ���</option>
				<option value="17"> ���</option>
				<option value="18"> �ܵ�</option>
				<option value="19"> ����</option>
				<option value="20"> ����</option>
				<option value="21"> ����</option>
				<option value="22"> ��ϱ</option>
				<option value="23"> ����</option>
				<option value="24"> ��ĸ</option>
				<option value="25"> Ů��</option>
				<option value="26"> ��������</option>
				<option value="27"> ͬ��</option>
				<option value="28"> ����</option>
				<option value="29"> ����</option>
				<option value="30"> ����</option>	  
			</select>
		</td>
		<td>
			<select name="BnfType1" id="BnfReadOnly39" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����������</option>
				<option value="1"> ���������</option>
			</select>
		</td>
		<td><input name="BnfName1" type="text" id="BnfReadOnly12" class=common size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfSex1" id="BnfReadOnly13" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfIDType1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> �������֤</option>
				<option value="110002"> �غž������֤</option>
				<option value="110003"> ��ʱ�������֤</option>
				<option value="110004"> �غ���ʱ�������֤</option>
				<option value="110005"> ���ڲ�</option>
				<option value="110006"> �غŻ��ڲ�</option>
				<option value="110011"> ���ݸɲ�����֤</option>
				<option value="110012"> �غ����ݸɲ�����֤</option>
				<option value="110013"> ��������֤</option>
				<option value="110014"> �غž�������֤</option>
				<option value="110015"> ��ְ�ɲ�����֤</option>
				<option value="110016"> �غ���ְ�ɲ�����֤</option>
				<option value="110017"> ����ԺУѧԱ֤</option>
				<option value="110018"> �غž���ԺУѧԱ֤</option>
				<option value="110019"> �۰ľ��������ڵ�ͨ��֤</option>
				<option value="110020"> �غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110021"> ̨�����������½ͨ��֤</option>
				<option value="110022"> �غ�̨�����������½ͨ��֤</option>
				<option value="110023"> �л����񹲺͹�����</option>
				<option value="110024"> �غ��л����񹲺͹�����</option>
				<option value="110025"> �������</option>
				<option value="110026"> �غ��������</option>
				<option value="110027"> ����֤</option>
				<option value="110028"> �غž���֤</option>
				<option value="110029"> ��ְ�ɲ�֤</option>
				<option value="110030"> �غ���ְ�ɲ�֤</option>
				<option value="110031"> ����֤</option>
				<option value="110032"> �غž���֤</option>
				<option value="110033"> ����ʿ��֤</option>
				<option value="110034"> �غž���ʿ��֤</option>
				<option value="110035"> �侯ʿ��֤</option>
				<option value="110036"> �غ��侯ʿ��֤</option>
				<option value="119998"> ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="119999"> ��������֤��ʶ���ʶ</option>
			</select></td>
		<td><input name="BnfIDNo1" type="text" id="BnfReadOnly15" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BnfBirthday1" id="BnfReadOnly18" size="10"></TD>
		<td><input name="BnfBnfLot1" type="text"  id="BnfReadOnly16" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=BnfBnfGrade1  id="BnfReadOnly17" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="BnfValidYear1"></TD>
	</tr>
				
<%--*************������2*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
				<option value=""></option> 
			    <option value="1"> ����</option>
				<option value="2"> �ɷ�</option>
				<option value="3"> ����</option>
				<option value="4"> ����</option>
				<option value="5"> ĸ��</option>
				<option value="6"> ����</option>
				<option value="7"> Ů��</option>
				<option value="8"> �游</option>
				<option value="9"> ��ĸ</option>
				<option value="10"> ����</option>
				<option value="11"> ��Ů</option>
				<option value="12"> ���游</option>
				<option value="13"> ����ĸ</option>
				<option value="14"> ����</option>
				<option value="15"> ����Ů</option>
				<option value="16"> ���</option>
				<option value="17"> ���</option>
				<option value="18"> �ܵ�</option>
				<option value="19"> ����</option>
				<option value="20"> ����</option>
				<option value="21"> ����</option>
				<option value="22"> ��ϱ</option>
				<option value="23"> ����</option>
				<option value="24"> ��ĸ</option>
				<option value="25"> Ů��</option>
				<option value="26"> ��������</option>
				<option value="27"> ͬ��</option>
				<option value="28"> ����</option>
				<option value="29"> ����</option>
				<option value="30"> ����</option>	   
			</select></td>
		<td><select name="BnfType2" id="BnfReadOnly40" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����������</option>
				<option value="1"> ���������</option>
		</select></td>
		<td><input name="BnfName2" type="text" id="BnfReadOnly22" class=common size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfSex2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfIDType2" id="BnfReadOnly24" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> �������֤</option>
				<option value="110002"> �غž������֤</option>
				<option value="110003"> ��ʱ�������֤</option>
				<option value="110004"> �غ���ʱ�������֤</option>
				<option value="110005"> ���ڲ�</option>
				<option value="110006"> �غŻ��ڲ�</option>
				<option value="110011"> ���ݸɲ�����֤</option>
				<option value="110012"> �غ����ݸɲ�����֤</option>
				<option value="110013"> ��������֤</option>
				<option value="110014"> �غž�������֤</option>
				<option value="110015"> ��ְ�ɲ�����֤</option>
				<option value="110016"> �غ���ְ�ɲ�����֤</option>
				<option value="110017"> ����ԺУѧԱ֤</option>
				<option value="110018"> �غž���ԺУѧԱ֤</option>
				<option value="110019"> �۰ľ��������ڵ�ͨ��֤</option>
				<option value="110020"> �غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110021"> ̨�����������½ͨ��֤</option>
				<option value="110022"> �غ�̨�����������½ͨ��֤</option>
				<option value="110023"> �л����񹲺͹�����</option>
				<option value="110024"> �غ��л����񹲺͹�����</option>
				<option value="110025"> �������</option>
				<option value="110026"> �غ��������</option>
				<option value="110027"> ����֤</option>
				<option value="110028"> �غž���֤</option>
				<option value="110029"> ��ְ�ɲ�֤</option>
				<option value="110030"> �غ���ְ�ɲ�֤</option>
				<option value="110031"> ����֤</option>
				<option value="110032"> �غž���֤</option>
				<option value="110033"> ����ʿ��֤</option>
				<option value="110034"> �غž���ʿ��֤</option>
				<option value="110035"> �侯ʿ��֤</option>
				<option value="110036"> �غ��侯ʿ��֤</option>
				<option value="119998"> ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="119999"> ��������֤��ʶ���ʶ</option>
			</select></td>
		<td><input name="BnfIDNo2" type="text" id="BnfReadOnly25" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BnfBirthday2" id="BnfReadOnly28" size="10"></TD>
		<td><input name="BnfBnfLot2" type="text"  id="BnfReadOnly26" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfBnfGrade2"  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="BnfValidYear2"></TD>
	</tr>
	
<%--*************������3*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured3" id="BnfReadOnly31" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> �ɷ�</option>
				<option value="3"> ����</option>
				<option value="4"> ����</option>
				<option value="5"> ĸ��</option>
				<option value="6"> ����</option>
				<option value="7"> Ů��</option>
				<option value="8"> �游</option>
				<option value="9"> ��ĸ</option>
				<option value="10"> ����</option>
				<option value="11"> ��Ů</option>
				<option value="12"> ���游</option>
				<option value="13"> ����ĸ</option>
				<option value="14"> ����</option>
				<option value="15"> ����Ů</option>
				<option value="16"> ���</option>
				<option value="17"> ���</option>
				<option value="18"> �ܵ�</option>
				<option value="19"> ����</option>
				<option value="20"> ����</option>
				<option value="21"> ����</option>
				<option value="22"> ��ϱ</option>
				<option value="23"> ����</option>
				<option value="24"> ��ĸ</option>
				<option value="25"> Ů��</option>
				<option value="26"> ��������</option>
				<option value="27"> ͬ��</option>
				<option value="28"> ����</option>
				<option value="29"> ����</option>
				<option value="30"> ����</option>	  
			</select></td>
		<td><select name="BnfType3" id="BnfReadOnly41" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����������</option>
				<option value="1"> ���������</option>
		</select></td>
		<td><input name="BnfName3" type="text" id="BnfReadOnly32" class=common size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfSex3" id="BnfReadOnly33" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfIDType3" id="BnfReadOnly34" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> �������֤</option>
				<option value="110002"> �غž������֤</option>
				<option value="110003"> ��ʱ�������֤</option>
				<option value="110004"> �غ���ʱ�������֤</option>
				<option value="110005"> ���ڲ�</option>
				<option value="110006"> �غŻ��ڲ�</option>
				<option value="110011"> ���ݸɲ�����֤</option>
				<option value="110012"> �غ����ݸɲ�����֤</option>
				<option value="110013"> ��������֤</option>
				<option value="110014"> �غž�������֤</option>
				<option value="110015"> ��ְ�ɲ�����֤</option>
				<option value="110016"> �غ���ְ�ɲ�����֤</option>
				<option value="110017"> ����ԺУѧԱ֤</option>
				<option value="110018"> �غž���ԺУѧԱ֤</option>
				<option value="110019"> �۰ľ��������ڵ�ͨ��֤</option>
				<option value="110020"> �غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110021"> ̨�����������½ͨ��֤</option>
				<option value="110022"> �غ�̨�����������½ͨ��֤</option>
				<option value="110023"> �л����񹲺͹�����</option>
				<option value="110024"> �غ��л����񹲺͹�����</option>
				<option value="110025"> �������</option>
				<option value="110026"> �غ��������</option>
				<option value="110027"> ����֤</option>
				<option value="110028"> �غž���֤</option>
				<option value="110029"> ��ְ�ɲ�֤</option>
				<option value="110030"> �غ���ְ�ɲ�֤</option>
				<option value="110031"> ����֤</option>
				<option value="110032"> �غž���֤</option>
				<option value="110033"> ����ʿ��֤</option>
				<option value="110034"> �غž���ʿ��֤</option>
				<option value="110035"> �侯ʿ��֤</option>
				<option value="110036"> �غ��侯ʿ��֤</option>
				<option value="119998"> ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="119999"> ��������֤��ʶ���ʶ</option>
			</select></td>
		<td><input name="BnfIDNo3" type="text" id="BnfReadOnly35" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BnfBirthday3" id="BnfReadOnly38" size="10"></TD>
		<td><input name="BnfBnfLot3" type="text"  id="BnfReadOnly36" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=BnfBnfGrade3  id="BnfBnfGrade3" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="BnfValidYear3"></TD>
	</tr>
</table>

<hr>
<%--*************������Ϣ*************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 
 
	<tr >
		<td>���մ���</td>
		<td>����</td>
		<td>����(Ԫ)</td>
		<td>����(Ԫ)</td>
		<td>�ɷѷ�ʽ</td>
		<td>������ȡ��ʽ</td>
		<td>������������</td>
		<td>��������</td>
		<td>�ɷ���������</td>
		<td>�ɷ�����</td>
	</tr>

	<tr class=common2>
		<td bgColor="#F7F7F7">
			 <select name="Code"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)"> 
            	<option value="211902">�к���Ӯ����������˺�����A��</option>
            	<option value="231204">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
            	<option value="231201">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��</option>
                <option value="231202">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="231203">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
                <option value="211901">�к���Ӯ����������˺�����</option>
                <option value="221201">�к����ݻ�����ȫ����A��</option>
            </select>
        </td>
		<td><input name="Mult" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="Prem" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="Amnt" type="text"  size="10"/></td>
		<TD>  
			<select name="PayIntv" class="common1">
				<option value=""></option>
				<option value="1"> ���� </option>
                <option value="2"> �½� </option>
                <option value="3"> ���� </option>
                <option value="4"> ���꽻 </option>
                <option value="5"> �꽻 </option>
                <option value="6"> ������ </option>
             </select></TD>	
             <TD><select name="BonusGetMode" class="common1">
			   <option value=""></option>
			   <option value="0"> �� </option>
			   <option value="1"> ������Ϣ </option>
               <option value="2"> ��ȡ�ֽ� </option>
               <option value="3"> �ֽ����� </option>
               <option value="4"> ���� </option>
               <option value="5"> ����� </option>
	    </select></TD>
		<td> 
		
			<select name="InsuYearFlag" class="common1">
				<option value=""></option>
				<option value="1"> ����ĳȷ������ </option>
                <option value="2"> �±� </option>
                <option value="3"> �ձ� </option>
                <option value="4"> �걣 </option>
                
             </select></td>
		<td><input name="InsuYear" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PayEndYearFlag" class="common1">
				<option value=""></option>
				   <option value="0"> ���� </option>
                <option value="1"> ����ĳȷ������ </option>
                <option value="2"> �½� </option>
                <option value="3"> �ս� </option>
                <option value="4"> ��� </option>
               
             </select></td>
		<td><input name="PayEndYear" type="text"  size="5" maxlength="5"  /></td>
	</tr>
				
</table>
	


<%--*************������*************
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>�������ա�</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fRiskCode"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList);" ></td>
	</tr>	
</table> 

<div id="divRiskList" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList"></span>
		</td>
	<tr>
		<td>�����մ���</td>
		<td>����</td>
		<td>����</td>
		<td>����</td>
		<td>������������</td>
		<td>��������</td>
		<td>�ɷ���������</td>
		<td>�ɷ�����</td>
	</tr> 
	

	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="Code1"  style="background-color: #D7E1F6" onchange="SSRiskFlag(this.options[this.options.selectedIndex].value)">
					<option value=""></option>
                <option value="101">�������ӱ��� </option>
            </select></td>
		<td><input name="Mult1" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="Prem1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="Amnt1" type="text"  size="10"/></td>
		<td>    
			<select name="InsuYearFlag1" class="common1">
				<option value=""></option>
				<option value="1"> ����ĳȷ������ </option>
                <option value="2"> �±� </option>
                <option value="3"> �ձ� </option>
                <option value="4"> �걣 </option>
                
             </select></td>
		<td><input name="InsuYear1" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PayEndYearFlag1" class="common1"0>
				<option value=""></option>
				<option value="1"> ����ĳȷ������ </option>
                <option value="2"> �½� </option>
                <option value="3"> �ս� </option>
                <option value="4"> ��� </option>
             </select></td>
		<td><input name="PayEndYear1" type="text"  size="5" maxlength="5"  /></td>
	</tr>	
</table>
</div>
	--%>
	<hr>
<%--*************Ͷ����Ϣ*************--%>		
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��Ͷ����Ϣ��</td>
	</tr>
<%--	 
	<TR class=common>
		<td class=title>���ѷ�ʽ</td>
		<TD class=input>  
			<select name=PayIntv class="common1">
				<option value=""></option>
				<option value="1"> ���� </option>
                <option value="2"> �½� </option>
                <option value="3"> ���� </option>
                <option value="4"> ���꽻 </option>
                <option value="5"> �꽻 </option>
                <option value="6"> ������ </option>
             </select></TD>	
        
	</tr>
	--%>
     <tr>
        <td class=title>�����˺�</td>
		<TD class=input><Input class=input name=AccNo></TD>
	</tr>
	<tr>
        <td class=title>�����˺�����</td>
		<TD class=input><Input class=input name=AccName></TD>
	</tr>
	<%--
	<tr>
        <td class=title>������ȡ��ʽ</td>
		<TD><select name="BonusGetMode" class="common1">
			   <option value=""></option>
			   <option value="0"> �� </option>
			   <option value="1"> ������Ϣ </option>
               <option value="2"> ��ȡ�ֽ� </option>
               <option value="3"> �ֽ����� </option>
               <option value="4"> ���� </option>
               <option value="5"> ����� </option>
	    </select></TD>
	</tr>
	--%>
</table>

<div id="divOwnlist1" style="display: 'none'"> 
<hr/>

<table class=common align=center>
	<tr> 
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr>  	
</table> 



<table class=common align=center> 

	<TR class=common>
		<td class=title>�����ͬ��</td>
		<TD class=input><Input class=input name=LoanNo  ></TD>
		<td class=title>�������</td>
		<TD class=input><Input class=input name=LoanBank ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>�������� </td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=LoanDate  ></TD>
		<td class=title>�������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=LoanEndDate  ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>��������</td>
		<TD class=input>
		<select name="LoanType" class="common1">
			   <option value=""></option>
			   <option value="00">һ����ҵ���� </option>
			   <option value="01">�����ҵ���� </option>
               <option value="10">��������ϴ��� </option>
               <option value="11">����������� </option>
               <option value="20">��Ϣ��ѧ���� </option>
	    </select></TD>
		<td class=title>�����˺�</td>
		<TD class=input><Input class=input name=LoanAccNo  ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>������ (Ԫ)</td>
		<TD class=input><Input class=input name=LoanPrem  ></TD>
		<td class=title>������ʼ��</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsuDate  ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>���������� </td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsuEndDate  ></TD>
	</tr>
	
</table>
</div>
<hr>
<%--*************ҳ���趨*************--%>				
<table class=common align=center>				
					
	<tr> 
		<td><input class="cssButton" type="button" onClick="initBox()" name="Submit3" value=" �����Ϣ " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" �� �� �� �� " onClick="initInputBox()" /></td>
	</tr>

</table>

 

<hr/>

<%    
String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
StringBuilder inFilePath = new StringBuilder("/msg/")
					//.append("msg/")  
					//.append("1").append('/')
					//.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append("abcTestIn.xml");  
StringBuilder reFilePath = new StringBuilder("/msg/")
					//.append("msg/")     
					//.append("1").append('/')
					//.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append("abcTestReturn.xml");
			
					
					   
%>     
<a target="_blank" class="smallLink" href="<%=inFilePath%>?thisTime=<%=DateUtil.getCurDateTime()%>">���ͱ�������</a> 
<a target="_blank" class="smallLink" href="<%=reFilePath%>?thisTime=<%=DateUtil.getCurDateTime()%>">���ر�������</a>
<hr/>  
<table class=common align=center> 
	<caption>���ͱ�����Ϣ</caption>   
	<tr>
		<td>
			<textarea rows="30" cols="100" name="xmlContent" id="xmlContent">
			
			</textarea>
		</td>
	</tr> 
</table>
<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'></form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
