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
<SCRIPT src="TestYBTCCBCont.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestYBTCCBContInit.jsp"%>  

		<title>����ͨ����Լ����</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./TestYBTCCBSave.jsp" method=post name=fm target="fraSubmit">


<table class=common align=center>
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	
	<TR>
		<td class=title>������</td>
		<td class=input> 
		<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="OPR001">�µ��˱�</option>
			    <option value="OPR011">�µ�����</option>
			</select></TD>
		<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name=ip></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name=port></td>
		
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button 
		value="����Ͷ������" onclick="submitForm();"></TD> 
	</TR>
	
</table>
<hr/>


<%--*************************������Ϣ*************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 
	<TR class=common>
		<TD class=title>���ж˽�������</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		<TD class=title>������ˮ��</TD> 
		<TD class=input><input class=input name=TransRefGUID></TD>
	</TR>
	
	<TR class=common>
        	<td class=title>��������</td>
        	<td> <input  name="RegionCode" />
		</TD>
		<td class=title>�������</td>
		<TD class=input>
			<input name="Branch">
			</TD>
        	
	</TR>
	
	<TR class=common> 
		<td class=title>��Ա����</td>
		<TD class=input><Input class=input name=Teller></TD>
		<td class=title>Ͷ������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=SubmissionDate></TD>
	</TR>
	
	<TR class=common>
		<td class=title>Ͷ�����</td>
		<TD class=input><Input class=input name="HOAppFormNumber" maxlength="16" id="HOAppFormNumber"></TD>
		<td class=title>����ӡˢ��</td>
		<TD class=input><Input class=input name="ProviderFormNumber" maxlength="16" id="ProviderFormNumber"></TD>	
	</TR>
	<TR class=common>
		<td class=title>������Ա����</td>
		<TD class=input><Input class=input name="BkRckrNo" maxlength="16" id="BkRckrNo"></TD>
		<td class=title>������Ա����</td>
		<TD class=input><Input class=input name="BkSaleName" maxlength="16" id="BkSaleName"></TD>	
	</TR>
	<TR class=common>
		<td class=title>ԭ������ˮ��</td>
		<TD class=input><Input class=input name="ReqsrNo" id="OldTranNo"><input type="hidden" name="InputTransrNo"></TD>
	<td class=common >
	<input name="TransMode" type="hidden" style="background-color: #D7E1F6">
		</TD>
			<td class=input >
			<input class=input type="hidden" name=BranthCmp>
			</td>
	</tr>
	<TR class=common>
		<td class=title>������Ա�ʸ�֤��</td>
		<TD class=input><Input class=input name="BkSaleCertNo" maxlength="16" id="BkSaleCertNo"></TD>
    </TR>
</table>


<%--*************************Ͷ������Ϣ************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��Ͷ������Ϣ��</td>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ��������</td>
		<TD class=input><Input class=input name=AppFullName></TD>
		<td class=title>Ͷ�����Ա�</td>
		<TD class=input>
			<select name="AppGender" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> Ů��</option>
			</select></TD>
		
	</tr> 
	
	<TR class=common> 
		<td class=title>Ͷ����֤������</td>
		<TD class=input>
			<select name="AppGovtIDTC" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="A"> �������֤����</option>
				<option value="B"> ����֤</option>
				<option value="C"> ��ž���ְ�ɲ�֤</option>
				<option value="D"> ����֤</option>
				<option value="E"> ��ž�ʿ��֤</option>
				<option value="F"> ���ڲ�</option>
				<option value="G"> (�۰�)����֤��ͨ��֤</option>
				<option value="H"> ̨ͨ��֤��������Ч����֤</option>
				<option value="I">(���)����</option>
				<option value="J"> (�й�)����</option>
				<option value="K">�侯��ְ�ɲ�֤</option>
				<option value="L"> �侯ʿ��֤</option>
				<option value="M">����</option>
				<option value="Z"> ����</option>
			</select></TD>
			
		<td class=title>Ͷ����֤������</td>
		<TD class=input><Input class=input name=AppGovtID></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppBirthDate></TD>
		<td class=title>Ͷ����֤����Ч��</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppGovtTermDate></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ����ͨѶ��ַ	</td>
		<TD class=input><Input class=input name=AppLine1></TD>
		<td class=title>Ͷ������������	</td>
		<TD class=input><Input class=input name=AppZip></TD>	
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ���˼�ͥ�绰</td>
		<TD class=input><Input class=input name=AppDialNumber1></TD>
		<td class=title>Ͷ�����ƶ��绰	</td>
		<TD class=input><Input class=input name=AppDialNumber3></TD>
	</tr>
	<TR class=common>
		<td class=title>Ͷ�����뱻���˹�ϵ</td>
		<TD class=input>
			<select name="AppToInsRelation" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> �ɷ� </option>
				<option value="3"> ���� </option>
				<option value="4"> ���� </option>
				<option value="5">ĸ�� </option>
				<option value="6"> ����</option>
				<option value="7"> Ů�� </option>
				<option value="8">�游 </option>
				<option value="9">��ĸ</option>	 
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
				<option value="26"> �������� </option>
				<option value="27"> ͬ�� </option>	 
				 <option value="28"> ���� </option>
				<option value="29"> ����</option>
				<option value="30"> ���� </option>
			</select></TD>
			<td class=title>Ͷ���˵�������</td>
		<TD class=input><Input class=input name=AppAddrLine></TD>
	</tr>
	<tr class=common>
		<td class=title>Ͷ���˹���</td>
	<td bgColor="#F7F7F7" >
		<select name="AppCountry" id="AppCountry" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			  <option value="0156">�й�</option>
				<option value="--"> ����</option>
				</select>
				</td>
<td class=title>Ͷ����ְҵ����</td>  
		<TD class=input> 
			<select name="ApplJobCode"  style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="3010101"> һ������</option>
						 
						 
			</select>
		</TD>	
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ���˵�λ��ַ</td>
		<TD class=input><Input class=input name=AppWorkAddress></TD>
		<td class=title>Ͷ���˵�λ�ʱ�</td>
		<TD class=input><Input class=input name=AppWorkZipCode></TD>
	</tr>
	<TR class=common>
		<td class=title>Ͷ���˵�λ�绰</td>
		<TD class=input><Input class=input name=WorkPhone></TD>
	</tr>
	<TR class=common>
		<td class=title>Ͷ����������(Ԫ)</td>
		<TD class=input><Input class=input name=PbInCome></TD>
		<td class=title>Ͷ���˼�ͥ������</td>
		<TD class=input><Input class=input name=PbHomeInCome></TD>
	</tr>
	<TR class=common>
		<td class=title>Ͷ���˾�������</td>
		<TD class=input>
		<select name="PbDenType" id="PbDenType" style="background-color: rgb(215, 225, 246);"> 
			<option value=""></option>
			    <option value="1">����</option>
				<option value="2">ũ��</option>
			</select>
		</TD>
	</tr>
</table>



<%--*************************��������Ϣ************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>����������Ϣ��</td>
		<td colspan="2"><input type="checkbox" name=InsFlag value="0" onClick="setInsFlag(this);">
		<font color="red">�������Ƿ�Ϊ����</font></td>
	</tr>
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class=input name="InsFullName" id="Ins1"></TD>
		<td class=title>�������Ա�</td>
		<TD class=input>
			<select name="InsGender" id="Ins2" style="background-color: #D7E1F6">
				<option value=""></option>
			     <option value="1"> ����</option>
				<option value="2"> Ů��</option>
			</select></TD>
			<input type="hidden" name = "InsGenderh">
	</tr> 
	
	<TR class=common> 
		<td class=title>������֤������</td>
		<TD class=input>
			<select name="InsGovtIDTC" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
				    <option value="A"> �������֤����</option>
				<option value="B"> ����֤</option>
				<option value="C"> ��ž���ְ�ɲ�֤</option>
				<option value="D"> ����֤</option>
				<option value="E"> ��ž�ʿ��֤</option>
				<option value="F"> ���ڲ�</option>
				<option value="G"> (�۰�)����֤��ͨ��֤</option>
				<option value="H"> ̨ͨ��֤��������Ч����֤</option>
				<option value="I">(���)����</option>
				<option value="J"> (�й�)����</option>
				<option value="K">�侯��ְ�ɲ�֤</option>
				<option value="L"> �侯ʿ��֤</option>
				<option value="M">����</option>
				<option value="Z"> ����</option>
			</select></TD>
			<input type="hidden" name = "InsGovtIDTCh">
		<td class=title>������֤������</td>
		<TD class=input><Input class=input id="Ins4" name="InsGovtID"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsBirthDate"></TD>

		<td class=title>������֤����Ч��</td>
		<TD class=input><Input class="coolDatePicker" id="Ins12" dateFormat="short" name=InsGovtTermDate></TD>
	</tr>
	
	<TR class=common>
		<td class=title>������ͨѶ��ַ	</td>
		<TD class=input><Input class=input id="Ins7" name="InsLine1"></TD>
		<td class=title>��������������	</td>
		<TD class=input><Input class=input id="Ins8" name="InsZip"></TD>	
	</tr>
	 
	<TR class=common>
		<td class=title>�����˼�ͥ�绰</td>
		<TD class=input><Input class=input id="Ins9" name="InsDialNumber1"></TD>
		<td class=title>�������ƶ��绰	</td>
		<TD class=input><Input class=input id="Ins10" name="InsDialNumber3"></TD>
	</tr>
	 
	<TR class=common>
		<td class=title>������֪��־</td>  
		<TD class=input> 
			<select name="HealthIndicator" id="Ins11" style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="0"> �޽�����֪</option>
						 <option value="1"> �н�����֪</option>
						</select></TD>
	<td class=title>�����˵�������</td>
		<TD class=input><Input class=input id="Ins6" name="InsAddrLine"></TD>
	</tr>
	<tr class=common>
	<td class=title>�����˹���</td>
	<td bgColor="#F7F7F7" >
		<select name="InsCountry" id="InsCountry" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			  <option value="0156"> �й�</option>
				<option value="--"> ����</option>
				</select>
				</td>
<td class=title>δ���걻���������������չ�˾�ۼ���ʱ���(Ԫ)</td>
	<TD class=input><Input class=input name=InsCovSumAmt></TD>
	</tr>
		<tr class=common>
		
<td class=title>������ְҵ����</td>  
		<TD class=input> 
			<select name="InsuJobCode"  id="Ins13" style="background-color: #D7E1F6">  
						 <option value=""></option>
						<option value="3010101"> һ������</option>
						 
			</select>
		</TD>	
	</tr>
</table>
 



<%--*************************��������Ϣ************************--%>
<table class=common align=center> 
	<tr> 
		<td class=titleImg align=center>����������Ϣ��</td>
		<td colspan="2"><input type="checkbox" name=BnfFlag  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator>
		<font color="red">�������Ƿ�Ϊ����(ѡ��Ϊ����)</font></td>
	</tr>    

	<tr >  
		<td>�뱻�����˹�ϵ</td>
		<td>����</td>
		<td>�Ա�</td>
		<td>֤������	</td>
		<td>֤������	</td>
		<td>������ͨѶ��ַ</td>
		<td>����������	</td>
		<td>�������</td>
		<td>����˳��</td>
		<td>����������</td>
	</tr>
						
<%--*************������1*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);"> 
	<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> �ɷ� </option>
				<option value="3"> ���� </option>
				<option value="4"> ���� </option>
				<option value="5">ĸ�� </option>
				<option value="6"> ����</option>
				<option value="7"> Ů�� </option>
				<option value="8">�游 </option>
				<option value="9">��ĸ</option>	 
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
				<option value="26"> �������� </option>
				<option value="27"> ͬ�� </option>	 
				 <option value="28"> ���� </option>
				<option value="29"> ����</option>
				<option value="30"> ���� </option>
			</select></td>
		<td><input name="BnfFullName1" type="text" id="BnfReadOnly12" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender1" id="BnfReadOnly13" style="background-color: #D7E1F6">
			 <option value="1"> ����</option>
				<option value="2"> Ů��</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value=""></option>
			  <option value="A"> �������֤����</option>
				<option value="B"> ����֤</option>
				<option value="C"> ��ž���ְ�ɲ�֤</option>
				<option value="D"> ����֤</option>
				<option value="E"> ��ž�ʿ��֤</option>
				<option value="F"> ���ڲ�</option>
				<option value="G"> (�۰�)����֤��ͨ��֤</option>
				<option value="H"> ̨ͨ��֤��������Ч����֤</option>
				<option value="I">(���)����</option>
				<option value="J"> (�й�)����</option>
				<option value="K">�侯��ְ�ɲ�֤</option>
				<option value="L"> �侯ʿ��֤</option>
				<option value="M">����</option>
				<option value="Z"> ����</option>
			</select></td>
		<td><input name="BnfGovtID1" type="text" id="BnfReadOnly15" class=common/></td>
		<TD><Input class="input"  name="BnfAdress1" ></TD>
		<td><Input class="coolDatePicker" dateFormat="short" name=BnfBirthDate1 id="BnfReadOnly18" size="10"></TD>
		<td><input name="InterestPercent1" type="text"  id="BnfReadOnly16" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=Sequence1  id="BnfReadOnly17" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<td bgColor="#F7F7F7"> 
			<select name=BenefiType1  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0" >����������</option>
				<option value="1" >����������</option>
				<option value="2" >����������</option>
			</select>					
		</td>
	</tr>
				
<%--*************������2*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
		<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> �ɷ� </option>
				<option value="3"> ���� </option>
				<option value="4"> ���� </option>
				<option value="5">ĸ�� </option>
				<option value="6"> ����</option>
				<option value="7"> Ů�� </option>
				<option value="8">�游 </option>
				<option value="9">��ĸ</option>	 
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
				<option value="26"> �������� </option>
				<option value="27"> ͬ�� </option>	 
				 <option value="28"> ���� </option>
				<option value="29"> ����</option>
				<option value="30"> ���� </option>
			</select></td>
		<td><input name="BnfFullName2" type="text" id="BnfReadOnly22" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
				  <option value="1"> ����</option>
				<option value="2"> Ů��</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC2" id="BnfReadOnly24" style="background-color: #D7E1F6">
		<option value=""></option>
			  <option value="A"> �������֤����</option>
				<option value="B"> ����֤</option>
				<option value="C"> ��ž���ְ�ɲ�֤</option>
				<option value="D"> ����֤</option>
				<option value="E"> ��ž�ʿ��֤</option>
				<option value="F"> ���ڲ�</option>
				<option value="G"> (�۰�)����֤��ͨ��֤</option>
				<option value="H"> ̨ͨ��֤��������Ч����֤</option>
				<option value="I">(���)����</option>
				<option value="J"> (�й�)����</option>
				<option value="K">�侯��ְ�ɲ�֤</option>
				<option value="L"> �侯ʿ��֤</option>
				<option value="M">����</option>
				<option value="Z"> ����</option>
			</select></td>
		<td><input name="BnfGovtID2" type="text" id="BnfReadOnly25" class=common/></td>
		<TD><Input class="input"  name="BnfAdress2" ></TD>
		<td><Input class="coolDatePicker" dateFormat="short" name=BnfBirthDate2 id="BnfReadOnly28" size="10"></TD>
		<td><input name="InterestPercent2" type="text"  id="BnfReadOnly26" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=Sequence2  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<td bgColor="#F7F7F7"> 
			<select name=BenefiType2  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0" >����������</option>
				<option value="1" >����������</option>
				<option value="2" >����������</option>
			</select>					
		</td>
	</tr>
	
<%--*************������3*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation3" id="BnfReadOnly31" style="background-color: rgb(215, 225, 246);"> 
		<option value=""></option>
			    <option value="1"> ����</option>
				<option value="2"> �ɷ� </option>
				<option value="3"> ���� </option>
				<option value="4"> ���� </option>
				<option value="5">ĸ�� </option>
				<option value="6"> ����</option>
				<option value="7"> Ů�� </option>
				<option value="8">�游 </option>
				<option value="9">��ĸ</option>	 
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
				<option value="26"> �������� </option>
				<option value="27"> ͬ�� </option>	 
				 <option value="28"> ���� </option>
				<option value="29"> ����</option>
				<option value="30"> ���� </option>
			</select></td>
		<td><input name="BnfFullName3" type="text" id="BnfReadOnly32" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender3" id="BnfReadOnly33" style="background-color: #D7E1F6">
				<option value=""></option>
				 <option value="1"> ����</option>
				<option value="2"> Ů��</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC3" id="BnfReadOnly34" style="background-color: #D7E1F6">
				<option value=""></option>
			  <option value="A"> �������֤����</option>
				<option value="B"> ����֤</option>
				<option value="C"> ��ž���ְ�ɲ�֤</option>
				<option value="D"> ����֤</option>
				<option value="E"> ��ž�ʿ��֤</option>
				<option value="F"> ���ڲ�</option>
				<option value="G"> (�۰�)����֤��ͨ��֤</option>
				<option value="H"> ̨ͨ��֤��������Ч����֤</option>
				<option value="I">(���)����</option>
				<option value="J"> (�й�)����</option>
				<option value="K">�侯��ְ�ɲ�֤</option>
				<option value="L"> �侯ʿ��֤</option>
				<option value="M">����</option>
				<option value="Z"> ����</option>
			</select></td>
		<td><input name="BnfGovtID3" type="text" id="BnfReadOnly35" class=common/></td>
		<TD><Input class="input"  name="BnfAdress3" ></TD>
		<td><Input class="coolDatePicker" dateFormat="short" name=BnfBirthDate3 id="BnfReadOnly38" size="10"></TD>
		<td><input name="InterestPercent3" type="text"  id="BnfReadOnly36" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=Sequence3  id="BnfReadOnly37" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<td bgColor="#F7F7F7"> 
			<select name=BenefiType3  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0" >����������</option>
				<option value="1" >����������</option>
				<option value="2" >����������</option>
			</select>					
		</td>
	</tr>
</table>


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
		<td>������������</td>
		<td>��������</td>
		<td>�ɷ�����</td>
	</tr>
 
	<tr class=common2>
            <td bgColor="#F7F7F7">
			 <select name="ProductCode"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)"> 
            	<option value="0006">�к���δ�������</option>
            	<option value="0005">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
            	<option value="0004">�к����ݻ�����ȫ����A��</option>
            	<option value="0001">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��</option>
                <option value="0002">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="0003">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
            </select>
        	<input  name="RiskCodeName" type="hidden"  size="33"/>
        	<input  name="RiskCode" type="hidden"  size="33"/>
         </td>
		<td><input name="IntialNumberOfUnits" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode" class="common1">
				<option value=""></option>
				<option value="0"> �޹� </option>
				<option value="1"> ����</option>
                <option value="2"> �����ޱ� </option> 
                <option value="3"> ���� </option>
                <option value="4"> ���±� </option>
                <option value="5"> ���챣 </option>
                <option value="6"> ����ĳȷ������ </option>
             </select></td>
		<td><input name="Duration" type="text"  size="3" maxlength="5"/></td>
			<input name="PaymentDurationMode" type="hidden" class="common1">
			<!-- 
				<option value=""></option>
			<option value="0"> �޹� </option>
                <option value="1"> ���� </option>
                <option value="2"> ��� </option>
                <option value="3"> ����ĳȷ������ </option>
                <option value="4"> ������ </option>
                <option value="5"> �����ڽ� </option>
             </select>
              -->
		<td><input name="PaymentDuration" type="text"  size="5" maxlength="5"  /></td>
	</tr>
				
</table>
	


<%--*************������1*************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>����������Ϣ��</td>
	</tr> 
 
	<tr >
		<td>���ִ���</td>
		<td>����</td>
		<td>����(Ԫ)</td>
		<td>����(Ԫ)</td>
		<td>������������</td>
		<td>��������</td>
		<td>�ɷ�����</td>
	</tr>

	<tr class=common2>
		<td bgColor="#F7F7F7">
            <input class="codeno" name="ProductCode1">							
        	<input  name="RiskCodeName1" size="33" />
        	<input  name="RiskCode1" type="hidden"  size="33"/>
         </td> 
		<td><input name="IntialNumberOfUnits1" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt1" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode1" class="common1">
				<option value=""></option>
				<option value="0"> �޹� </option>
				<option value="1"> ������</option>
                <option value="2"> �����ޱ� </option> 
                <option value="3"> ����ĳȷ������ </option>
                <option value="4"> ���±� </option>
                <option value="5"> ���챣 </option>
             </select></td>
		<td><input name="Duration1" type="text"  size="3" maxlength="5"/></td>
			<input name="PaymentDurationMode1" type="hidden" class="common1">
			<!-- 
				<option value=""></option>
			<option value="0"> �޹� </option>
                <option value="1"> ���� </option>
                <option value="2"> ��� </option>
                <option value="3"> ����ĳȷ������ </option>
                <option value="4"> ������ </option>
                <option value="5"> �����ڽ� </option>
             </select>
              -->
             
		<td><input name="PaymentDuration1" type="text"  size="5" maxlength="5"  /></td>
	</tr>
			
	<tr class=common2>
		<td bgColor="#F7F7F7">
             <input class="codeno" name="ProductCode2" type="hidden"
							>
        	<input  name="RiskCodeName2" type="hidden"  size="33"/>
        	<input  name="RiskCode2" type="hidden"  size="33"/>
         </td>
		<td><input name="IntialNumberOfUnits2" type="hidden"  size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt2" type="hidden"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt2" type="hidden"  size="10"/></td>
		<td> 
			<input name="DurationMode2" type="hidden" class="common1">
            </td>
		<td><input name="Duration2" type="hidden" size="3" maxlength="5"/></td>
		<td>
			<input name="PaymentDurationMode2" type="hidden" class="common1">
				</td>
		<td><input name="PaymentDuration2" type="hidden" size="5" maxlength="5"  /></td>
	</tr>	
</table>

<table class=common align=center>
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode1" type="hidden"
							>
			<input class="codename" name="AccCodeName1" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent1" type="hidden"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode2"
							type="hidden"">
			<input class="codename" name="AccCodeName2" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent2" type="hidden"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode3"
							type="hidden"">
			<input class="codename" name="AccCodeName3" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent3" type="hidden"/></td>
		
	</tr>
	<tr class=common2>	
	<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode4"
							type="hidden">
			<input class="codename" name="AccCodeName4" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent4" type="hidden"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode5"
							type="hidden">
			<input class="codename" name="AccCodeName5" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent5" type="hidden"/></td>
	</tr>
</table>
	
	
<%--*************Ͷ����Ϣ*************--%>		
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��Ͷ����Ϣ��</td>
	</tr>
	 
	<TR class=common>
		<td class=title>�ɷѷ�ʽ</td>
		<TD class=input>  
			<select name=PaymentMode class="common1">
				<option value=""></option>
				<option value="-1"> �����ڽ�</option>
				<option value="0">���� </option>
				<option value="1">�½� </option>
                <option value="3"> ���� </option>
                <option value="6"> ���꽻 </option>
                <option value="12"> �꽻</option>
                <option value="98"> ����ĳȷ������</option>
                <option value="99"> �������� </option>
             </select></TD>	
		<td class=title>���ѷ�ʽ</td>
		<TD class=input>
			<select name=PaymentMethod class="common1">
				<option value=""></option>
				<option value="2"> �����۴��� </option>
                <option value="3"> ���п����� </option>
             </select></TD>
	</tr> 
	
	<TR class=common>
		<td class=title>��ȡ��ʽ</td>
		<TD class=input>
			<select name=BenefitMode class="common1">
				<option value=""></option>
				<option value="0"> һ�θ���  </option>
                <option value="1"> �¸���  </option>
                <option value="3"> ������ </option>
                <option value="6"> �������  </option>
                <option value="12"> ����� </option>
             </select></TD>
		<td class=title>������ȡ��ʽ</td>
		<TD class=input>
			<select name=DivType class="common1">
				<option value=""></option>
					<option value="0">ֱ�Ӹ��� </option>
				<option value="1"> �ֽ����� </option>
                <option value="2"> �ۼ���Ϣ </option>
                <option value="3">������� </option>
             </select></TD>
	</tr>
	
	<tr>
		<td class=title>�������ͷ�ʽ</td>
		<TD class=input>
			<select name=PolicyDeliveryMethod class="common1">
				<option value=""></option>
				<option value="1"> �ʼ�</option>
                <option value="2"> ���ӷ��� </option>
                  <option value="3">ָ����̨��ȡ </option>
             </select></TD>
                <td class=title>�Զ��潻��־</td>
		<TD class=input><select name=AutoPayFlag class="common1">
				<option value=""></option>
				<option value="0"> ���Զ��潻</option>
                <option value="1"> �Զ��潻 </option>
             </select></TD>
	</tr>
	
	<TR class=common> 
	 <td class=title>�����˺�</td>
		<TD class=input><Input class=input name=AccountNumber></TD>	
	 <td class=title>�ʻ�����</td>
		<TD class=input><Input class=input name=AcctHolderName></TD>	
	</tr>
		<tr>
		<td class=title>ְҵ��֪��־</td>
		<TD class=input>
			<select name=OccupationIndicator class="common1">
				<option value="N">��ְҵ��֪</option>
				<option value="Y">��ְҵ��֪</option>           
             </select></TD>
             <td class=title>�ر�Լ��</td>
		<TD class=input><Input class=input name=SpecialClause></TD>
			<td bgColor="#F7F7F7"><input type="hidden" name=InvestDateInd class="common1"><input name="FirstSuperaddAmt" type="hidden"  size="10" /></td>
	</tr>
             
    <TR class=common>
		<td class=title>���ڽɷ��ʺ�(�µ�������д)</td>
		<TD class=input><Input class="input"  name="BkAcctNo" ></TD>
		<td class=title>���ڽɷѷ�ʽ(�µ�����ʱѡ��)</td>
		<TD class=input>
			<select name="BkPayMode" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> �ֽ�</option>
				<option value="2"> �۴���</option>
				<option value="3"> ������</option>
				<option value="9"> �Թ����� </option>
			</select>
		</TD>
	</tr> 
 <TR class=common>
		<td class=title>��ȡ��ʼ����</td>
		<TD class=input><Input class="input"  name="PbDrawAge" value="50"></TD>
		<td class=title>��ȡ����</td>
		<TD class=input><Input class="input"  name="PbDrawAgeTag" value="30"></TD>
	</tr>
</table>
		
<%--*************ҳ���趨*************--%>				
<table class=common align=center>				
					
	<tr> 
		<td><input class="cssButton" type=Button onClick="initBox()" name="Submit3" value=" �����Ϣ " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" �� �� �� �� " onClick="initInputBox()" /></td>
	</tr>

</table>

 
<hr/>  
<table class=common align=center>
	<tr>������Ϣ</tr>  
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
