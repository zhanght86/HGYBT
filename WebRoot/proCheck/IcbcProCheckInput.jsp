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
<SCRIPT src="IcbcProCheck.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="IcbcProCheckInit.jsp"%>  

		<title>����������֤</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./IcbcProCheckSave.jsp" method=post name=fm target="fraSubmit">


<table class=common align=center>
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	
	<TR>
		<td class=title>������</td>
		<td class=input> 
			<Input class="codeno" name=tranFlagCode	readonly=true>
			<input class="codename" name=tranFlagCodeName  readonly=true>
		</td>  
		<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name=ip readonly=true></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name=port readonly=true></td>
		
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
		<TD class=input>
			<input class="codeno" name="RegionCode"
							ondblclick="return showRegionCode()"
							onkeyup="return showRegionCode()">
        	<input  name="RegionCodeName" readonly="readonly" type="text"  size="10"/>
		<td class=title>�������</td>
		<TD class=input>
			<input class="codeno" name="Branch"
							ondblclick="return showBranchCode()"
							onkeyup="return showBranchCode()">
        	<input  name="BranchName" readonly="readonly" type="text"  size="20"/>
		</TD>
	</TR>
	
	<TR class=common> 
		<td class=title>��Ա����</td>
		<TD class=input><Input class=input name=Teller readonly=true></TD>
		<td class=title>Ͷ������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=SubmissionDate></TD>
	</TR>
	
	<TR class=common>
		<td class=title>Ͷ�����</td>
		<TD class=input><Input class=input name=HOAppFormNumber><font color="red">(���׳ɹ���+1)</font></TD>
		<td class=title>����ӡˢ��</td>
		<TD class=input><Input class=input name=ProviderFormNumber><font color="red">(���׳ɹ���+1)</font></TD>	
	</tr>
	
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
				<option value="3"> ����</option>
			</select></TD>
		
	</tr> 
	
	<TR class=common> 
		<td class=title>Ͷ����֤������</td>
		<TD class=input>
			<select name="AppGovtIDTC" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ���֤</option>
				<option value="1"> ����</option>
				<option value="2"> ����֤</option>
				<option value="3"> ʿ��֤</option>
				<option value="4"> �۰ľ���������½ͨ��֤</option>
				<option value="5"> ��ʱ���֤</option>
				<option value="6"> ���ڱ�</option>
				<option value="7"> ����</option>
				<option value="9"> ����֤</option>
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
		<td class=title>Ͷ�����뱻���˵Ĺ�ϵ</td>
		<TD class=input>
			<select name="AppToInsRelation" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> ��ż��ϵ</option>
				<option value="2"> ��ĸ��ϵ</option>
				<option value="3"> ��Ů��ϵ</option>
				<option value="4"> �游��ĸ��ϵ</option>
				<option value="5"> ������Ů��ϵ</option>
				<option value="6"> �ֵܽ��ù�ϵ</option>
				<option value="7"> ����������ϵ</option>
				<option value="8"> ���˹�ϵ</option>
				<option value="9"> ���ѹ�ϵ</option>	 
			</select></TD>
			<td class=title>Ͷ���˵�������</td>
		<TD class=input><Input class=input name=AppAddrLine>
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
				<option value="3"> ����</option>
			</select></TD>
			<input type="hidden" name = "InsGenderh">
	</tr> 
	
	<TR class=common> 
		<td class=title>������֤������</td>
		<TD class=input>
			<select name="InsGovtIDTC" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ���֤</option>
				<option value="1"> ����</option>
				<option value="2"> ����֤</option>
				<option value="3"> ʿ��֤</option>
				<option value="4"> �۰ľ���������½ͨ��֤</option>
				<option value="5"> ��ʱ���֤</option>
				<option value="6"> ���ڱ�</option>
				<option value="7"> ����</option>
				<option value="9"> ����֤</option>
			</select></TD>
			<input type="hidden" name = "InsGovtIDTCh">
		<td class=title>������֤������</td>
		<TD class=input><Input class=input id="Ins4" name="InsGovtID"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsBirthDate"></TD>

		<td class=title>������֤����Ч��</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsGovtTermDate></TD>
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
						 <option value="N"> �޽�����֪</option>
						 <option value="Y"> �н�����֪</option>
						</select></TD>
	<td class=title>�����˵�������</td>
		<TD class=input><Input class=input id="Ins6" name="InsAddrLine"></TD>
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
		<td>֤����Ч��</td>
		<td>����������	</td>
		<td>�������</td>
		<td>����˳��</td>
	</tr>
						
<%--*************������1*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> ��ż��ϵ</option>
				<option value="2"> ��ĸ��ϵ</option>
				<option value="3"> ��Ů��ϵ</option>
				<option value="4"> �游��ĸ��ϵ</option>
				<option value="5"> ������Ů��ϵ</option>
				<option value="6"> �ֵܽ��ù�ϵ</option>
				<option value="7"> ����������ϵ</option>
				<option value="8"> ���˹�ϵ</option>
				<option value="9"> ���ѹ�ϵ</option>	  
			</select></td>
		<td><input name="BnfFullName1" type="text" id="BnfReadOnly12" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender1" id="BnfReadOnly13" style="background-color: #D7E1F6">
				<option value="1">��</option>
				<option value="2">Ů	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ���֤</option>
				<option value="1"> ����</option>
				<option value="2"> ����֤</option>
				<option value="3"> ʿ��֤</option>
				<option value="4"> �۰ľ���������½ͨ��֤</option>
				<option value="5"> ��ʱ���֤</option>
				<option value="6"> ���ڱ�</option>
				<option value="7"> ����</option>
				<option value="9"> ����֤</option>
			</select></td>
		<td><input name="BnfGovtID1" type="text" id="BnfReadOnly15" class=common/></td>
		<TD><Input class="coolDatePicker" dateFormat="short" name="BnfGovtTermDate1" size="10"> </TD>
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
	</tr>
				
<%--*************������2*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
				<option value=""></option> 
			    <option value="1"> ��ż��ϵ</option>
				<option value="2"> ��ĸ��ϵ</option>
				<option value="3"> ��Ů��ϵ</option>
				<option value="4"> �游��ĸ��ϵ</option>
				<option value="5"> ������Ů��ϵ</option>
				<option value="6"> �ֵܽ��ù�ϵ</option>
				<option value="7"> ����������ϵ</option>
				<option value="8"> ���˹�ϵ</option>
				<option value="9"> ���ѹ�ϵ</option>	   
			</select></td>
		<td><input name="BnfFullName2" type="text" id="BnfReadOnly22" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1">��</option>
				<option value="2">Ů	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC2" id="BnfReadOnly24" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ���֤</option>
				<option value="1"> ����</option>
				<option value="2"> ����֤</option>
				<option value="3"> ʿ��֤</option>
				<option value="4"> �۰ľ���������½ͨ��֤</option>
				<option value="5"> ��ʱ���֤</option>
				<option value="6"> ���ڱ�</option>
				<option value="7"> ����</option>
				<option value="9"> ����֤</option>
			</select></td>
		<td><input name="BnfGovtID2" type="text" id="BnfReadOnly25" class=common/></td>
		<TD><Input class="coolDatePicker" dateFormat="short" name="BnfGovtTermDate2" size="10"> </TD>
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
	</tr>
	
<%--*************������3*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation3" id="BnfReadOnly31" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> ��ż��ϵ</option>
				<option value="2"> ��ĸ��ϵ</option>
				<option value="3"> ��Ů��ϵ</option>
				<option value="4"> �游��ĸ��ϵ</option>
				<option value="5"> ������Ů��ϵ</option>
				<option value="6"> �ֵܽ��ù�ϵ</option>
				<option value="7"> ����������ϵ</option>
				<option value="8"> ���˹�ϵ</option>
				<option value="9"> ���ѹ�ϵ</option>	  
			</select></td>
		<td><input name="BnfFullName3" type="text" id="BnfReadOnly32" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender3" id="BnfReadOnly33" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1">��</option>
				<option value="2">Ů	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC3" id="BnfReadOnly34" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> ���֤</option>
				<option value="1"> ����</option>
				<option value="2"> ����֤</option>
				<option value="3"> ʿ��֤</option>
				<option value="4"> �۰ľ���������½ͨ��֤</option>
				<option value="5"> ��ʱ���֤</option>
				<option value="6"> ���ڱ�</option>
				<option value="7"> ����</option> 
				<option value="9"> ����֤</option>
			</select></td>
		<td><input name="BnfGovtID3" type="text" id="BnfReadOnly35" class=common/></td>
		<TD><Input class="coolDatePicker" dateFormat="short" name="BnfGovtTermDate3" size="10"> </TD>
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
		<td>����(��)</td>
		<td>����(��)</td>
		<td>������������</td>
		<td>��������</td>
		<td>�ɷ���������</td>
		<td>�ɷ�����</td>
	</tr>
 
	<tr class=common2>
		<td bgColor="#F7F7F7">
            <input class="codeno" name="ProductCode" 
            ondblclick="return showProductCode()"
            onkeyup="return showProductCodeKey()"> 
        	<input  name="RiskCodeName" readonly="readonly" type="text"  size="33"/>
        	<input  name="RiskCode" type="hidden"  size="33"/>
         </td>
		<td><input name="IntialNumberOfUnits" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode" class="common1">
				<option value=""></option>
				<option value="1"> ����ĳȷ������ </option>
                <option value="2"> �걣 </option> 
                <option value="3"> �±� </option>
                <option value="4"> �ձ� </option>
                <option value="5"> ������ </option>
                <option value="9"> ���� </option>
             </select></td>
		<td><input name="Duration" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PaymentDurationMode" class="common1">
				<option value=""></option>
				<option value="0"> ���� </option>
                <option value="1"> ����ĳȷ������ </option>
                <option value="2"> ��� </option>
                <option value="3"> �½� </option>
                <option value="4"> �ս� </option>
                <option value="5"> ���� </option>
                <option value="6"> �սɷ� </option>
                <option value="7"> �����ڽ� </option>
                <option value="8"> ����� </option>
                <option value="9"> ���� </option>
             </select></td>
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
		<td>����(��)</td>
		<td>����(��)</td>
		<td>������������</td>
		<td>��������</td>
		<td>�ɷ���������</td>
		<td>�ɷ�����</td>
	</tr>

	<tr class=common2>
		<td bgColor="#F7F7F7">
            <input class="codeno" name="ProductCode1"
							ondblclick="return showProductCode1()"
							onkeyup="return showProductCode1Key()">
        	<input  name="RiskCodeName1" readonly="readonly" type="text"  size="33"/>
        	<input  name="RiskCode1" type="hidden"  size="33"/>
         </td> 
		<td><input name="IntialNumberOfUnits1" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt1" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode1" class="common1">
				<option value=""></option>
				<option value="1"> ����ĳȷ������ </option>
                <option value="2"> �걣 </option> 
                <option value="3"> �±� </option>
                <option value="4"> �ձ� </option>
                <option value="5"> ������ </option>
                <option value="9"> ���� </option>
             </select></td>
		<td><input name="Duration1" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PaymentDurationMode1" class="common1">
				<option value=""></option>
				<option value="0"> ���� </option>
                <option value="1"> ����ĳȷ������ </option>
                <option value="2"> ��� </option>
                <option value="3"> �½� </option>
                <option value="4"> �ս� </option>
                <option value="5"> ���� </option>
                <option value="6"> �սɷ� </option>
                <option value="7"> �����ڽ� </option>
                <option value="8"> ����� </option>
                <option value="9"> ���� </option>
             </select></td>
		<td><input name="PaymentDuration1" type="text"  size="5" maxlength="5"  /></td>
	</tr>
			
	<tr class=common2>
		<td bgColor="#F7F7F7">
             <input class="codeno" name="ProductCode2"
							ondblclick="return showProductCode2()"
							onkeyup="return showProductCode2Key()">
        	<input  name="RiskCodeName2" readonly="readonly" type="text"  size="33"/>
        	<input  name="RiskCode2" type="hidden"  size="33"/>
         </td>
		<td><input name="IntialNumberOfUnits2" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt2" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt2" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode2" class="common1">
				<option value=""></option>
				<option value="1"> ����ĳȷ������ </option>
                <option value="2"> �걣 </option> 
                <option value="3"> �±� </option>
                <option value="4"> �ձ� </option>
                <option value="5"> ������ </option>
                <option value="9"> ���� </option>
             </select></td>
		<td><input name="Duration2" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PaymentDurationMode2" class="common1">
				<option value=""></option>
				<option value="0"> ���� </option>
                <option value="1"> ����ĳȷ������ </option>
                <option value="2"> ��� </option>
                <option value="3"> �½� </option>
                <option value="4"> �ս� </option>
                <option value="5"> ���� </option>
                <option value="6"> �սɷ� </option>
                <option value="7"> �����ڽ� </option>
                <option value="8"> ����� </option>
                <option value="9"> ���� </option>
             </select></td>
		<td><input name="PaymentDuration2" type="text"  size="5" maxlength="5"  /></td>
	</tr>	
</table>


<table class=common align=center>

	<tr> 
		<td class=titleImg align=center>��Ͷ���˻���</td>	
	</tr> 	
</table> 

<table class=common align=center>
	<tr>
		<td>Ͷ���˻�1</td>
		<td>Ͷ�ʱ���</td>
		<td>Ͷ���˻�2</td>
		<td>Ͷ�ʱ���</td>
		<td>Ͷ���˻�3</td>
		<td>Ͷ�ʱ���</td>
	</tr>
	
	<tr class=common2>
	
		
	
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode1"
							ondblclick="return showAccCode1();">
			<input class="codename" name="AccCodeName1" readonly="true"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode2"
							ondblclick="return showAccCode2();">
			<input class="codename" name="AccCodeName2" readonly="true"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent2" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode3"
							ondblclick="return showAccCode3();">
			<input class="codename" name="AccCodeName3" readonly="true"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent3" type="text"  size="10"/></td>
		
	</tr>
		<tr>
		<td>Ͷ���˻�4</td>
		<td>Ͷ�ʱ���</td>
		<td>Ͷ���˻�5</td>
		<td>Ͷ�ʱ���</td>
	</tr>
	<tr class=common2>	
	<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode4"
							ondblclick="return showAccCode4();">
			<input class="codename" name="AccCodeName4" readonly="true"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent4" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode5"
							ondblclick="return showAccCode5();">
			<input class="codename" name="AccCodeName5" readonly="true"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent5" type="text"  size="10"/></td>
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
				<option value="1"> ��� </option>
                <option value="2"> �½� </option>
                <option value="3"> ����� </option>
                <option value="4"> ���� </option>
                <option value="5"> ���� </option>
                <option value="6"> �����ڽ� </option>
                <option value="9"> ���� </option>
             </select></TD>	
		<td class=title>�ɷ���ʽ(Ƶ��)</td>
		<TD class=input>
			<select name=PaymentMethod class="common1">
				<option value=""></option>
				<option value="1"> ����ת�� </option>
                <option value="2"> ֧Ʊ </option>
                <option value="3"> ���д��� </option>
                <option value="4"> �ֽ� </option>
                <option value="5"> �ֽ��Ϳ </option>
                <option value="6"> �ڲ�ת�� </option>
                <option value="7"> Pos�տ� </option>
                <option value="9"> ���� </option>
             </select></TD>
	</tr> 
	
	<TR class=common>
		<td class=title>��ȡ��ʽ</td>
		<TD class=input>
			<select name=BenefitMode class="common1">
				<option value=""></option>
				<option value="1"> ���� </option>
                <option value="2"> ���� </option>
                <option value="3"> ���� </option>
                <option value="4"> ���� </option>
                <option value="5"> ���ⷽʽ </option>
                <option value="6"> ÿ������ </option>
                <option value="7"> ������ </option>
                <option value="8"> �޹� </option>
                <option value="9"> ���� </option>
                <option value="10"> �ۻ���Ϣ </option>
                <option value="11"> ֱ����ȡ </option>
                <option value="12"> �ֽ����� </option>
                <option value="13"> �ֽ���ȡ </option>
             </select></TD>
		<td class=title>������ȡ��ʽ</td>
		<TD class=input>
			<select name=DivType class="common1">
				<option value=""></option>
				<option value="1"> �ۻ���Ϣ </option>
                <option value="2"> ��ȡ�ֽ� </option>
                <option value="3"> �ֽ����� </option>
                <option value="4"> ����� </option>
                <option value="5"> �޺��� </option>
                <option value="6"> ������� </option>
                <option value="9"> ���� </option>
             </select></TD>
	</tr>
	
	<tr>
		<td class=title>�������ͷ�ʽ</td>
		<TD class=input>
			<select name=PolicyDeliveryMethod class="common1">
				<option value=""></option>
				<option value="1"> ���ŷ��� </option>
                <option value="2"> �ʼĻ�ר�� </option>
                <option value="3"> ���ŵ��� </option>
                <option value="4"> ������ȡ </option>
                <option value="5"> ���������������г��� </option>
                <option value="6"> ���������������չ�˾���� </option>
                <option value="9"> ���� </option>
             </select></TD>
        <td class=title>�����˺�</td>
		<TD class=input><Input class=input name=AccountNumber></TD>	
       
	</tr>
	
	<TR class=common> 
	 <td class=title>�ʻ�����</td>
		<TD class=input><Input class=input name=AcctHolderName></TD>	
		<td class=title>�ر�Լ��</td>
		<TD class=input><Input class=input name=SpecialClause></TD>
	</tr>
		<tr>
		<td class=title>ְҵ��֪��־</td>
		<TD class=input>
			<select name=OccupationIndicator class="common1">
				<option value="N">��ְҵ��֪</option>
				<option value="Y">��ְҵ��֪</option>           
             </select></TD>
        <td class=title>Ͷ�����ڱ�־</td>
		<TD class=input>
			<select name=InvestDateInd class="common1">
				<option value=""></option>
				<option value="1">Ͷ������</option>
				<option value="2">��ԥ�ں�</option>          
             </select></TD>
	</tr>
	<tr>
             <td class=title>�״ζ���׷�ӱ���(��)</td>
             <td bgColor="#F7F7F7"><input name="FirstSuperaddAmt" type="text"  size="10" /></td>
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
