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
<SCRIPT src="CGBProCheck.js"></SCRIPT>
<script type="text/javascript">
	
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   
		<%@include file="CGBProCheckInit.jsp"%>  
 
		<title>����ͨ����Լ����</title>
	</head>
<body onload="initElementtype();initForm();"> 
<form action="./CGBProCheckSave.jsp" method=post name=fm target="fraSubmit">


<%--*************************������Ϣ*************************--%>
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	<tr>
<td class=title>������</td>
<TD class=input readonly=true>
			<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="1013"> �µ��˱�</option>
			</select></TD>
		
		<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name=ip readonly=true></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name=port readonly=true></td>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="����Ͷ������" onclick="submitForm();"></TD> 
	</TR>
</table>
		
<hr>

<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 
	
	<TR class=common>
		<TD class=title>���ж˽�������</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		<TD class=title>������ˮ��</TD> 
		<TD class=input><input class=input name=TransRefGUID><input type="hidden" name="InputTransRefGUID"></TD>
		
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
		<TD class=input><Input class=input name=HOAppFormNumber id="ProposalNo"></TD>
		<td class=title>����ӡˢ��</td>
		<TD class=input><Input class=input name=ProviderFormNumber id="PrtNo"></TD>	
	</tr>
	<TR class=common>
		<td class=title>ԭ������ˮ��</td>
		<TD class=input><Input class=input name=OriginalTransRefGUID id="OldTransNo"></TD>
	<!--  	<td class=title>ԭ����ӡˢ��</td>
		<TD class=input><Input class=input name=OriginalProviderFormNumber id="OldPrtNo"></TD>	
	</tr>
		<TR class=common>
		<td class=title>������</td>
		<TD class=input><Input class=input name=PolNumber id="ContNo"></TD>
		-->
	</tr>
</table>

<hr>
<%--*************************Ͷ������Ϣ************************--%>
<table class=common align=center >

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
		<td class=title>Ͷ���˵�������</td>
		<TD class=input><Input class=input name=AppAddrLine>
		</TD>
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
		<td class=title>�����˵�������</td>
		<TD class=input><Input class=input id="Ins6" name="InsAddrLine">
		</TD>
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
	</tr>

</table>
 

<hr>

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

<hr>
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
			<select name="ProductCode"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)">
		 		<option value="001">��ʢ���ȫ��λ������ȫ���գ��ֺ��ͣ� </option>
            	<option value="003">������ʢ����ʢ����������գ��ֺ��ͣ� </option>
               
            </select></td>
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
			<select name="PaymentDurationMode" class="common1"0>
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
	

<%--*************������101*************--%>	
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>��������1��</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fRiskCode1"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList1);" ></td>
	</tr>
</table> 

<div id="divRiskList1" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList1"></span></td>
	
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

<%--*************������1*************--%>	
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="ProductCode2"  style="background-color: #D7E1F6" onchange="SSRiskFlag2(this.options[this.options.selectedIndex].value)">
					<option value=""></option>
                <option value="101">��ʢ�������ȫ��λ����Ͷ�����ᱣ�� (����׷��)</option>
            </select></td>
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
			<select name="PaymentDurationMode2" class="common1"0>
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
</div>




<%--*************������*************--%>	
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������2��</td>	
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
			<span id="spanBnfList"></span></td>
	
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

<%--*************������1*************--%>	
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="ProductCode1"  style="background-color: #D7E1F6" onchange="SSRiskFlag(this.options[this.options.selectedIndex].value)">
					<option value=""></option>
                <option value="102">��ʢ�������ȫ��λ����Ͷ�����ᱣ��(����׷��) </option>
            </select></td>
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
			<select name="PaymentDurationMode1" class="common1"0>
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
</table>
</div>

	
	
<%--*************������*************--%>	
<table class=common align=center>

	<tr> 
		<td class=titleImg align=center>��Ͷ���˻���</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="AccountFlag"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList2);"></td>
	</tr>	 
</table> 
<div id="divRiskList2" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList"></span></td>
	</tr>
	<tr>
		<td>Ͷ���˻�1</td>
		<td>Ͷ�ʱ���</td>
		<td>Ͷ���˻�2</td>
		<td>Ͷ�ʱ���</td>
		<td>Ͷ���˻�3</td>
		<td>Ͷ�ʱ���</td>
	</tr>

<%--*************������1*************--%>	
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="AccCode1"  style="background-color: #D7E1F6">
					<option value=""></option>
                <option value="U1ZY">׿ԽͶ���˻� </option>
                <option value="U2WJ">�Ƚ�Ͷ���˻� </option>
                <option value="U3AX">����Ͷ���˻� </option>
            </select></td>
		<td bgColor="#F7F7F7"><input name="AllocPercent1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7">
			<select name="AccCode2"  style="background-color: #D7E1F6">
					<option value=""></option>
                <option value="U1ZY">׿ԽͶ���˻� </option>
                <option value="U2WJ">�Ƚ�Ͷ���˻� </option>
                <option value="U3AX">����Ͷ���˻� </option>
            </select></td>
		<td bgColor="#F7F7F7"><input name="AllocPercent2" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7">
			<select name="AccCode3"  style="background-color: #D7E1F6">
					<option value=""></option>
                <option value="U1ZY">׿ԽͶ���˻� </option>
                <option value="U2WJ">�Ƚ�Ͷ���˻� </option>
                <option value="U3AX">����Ͷ���˻� </option>
            </select></td>
		<td bgColor="#F7F7F7"><input name="AllocPercent3" type="text"  size="10"/></td>
		
	</tr>	
</table>
</div>	
	
	
	
	<hr>
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
        <td class=title>�������ͷ�ʽ</td>
		<TD class=input>
			<select name=PolicyDeliveryMethod class="common1">
				<option value=""></option>
                <option value="4"> ������ȡ </option>
             </select></TD>
	</tr>
<tr>

             <td class=title>�״ζ���׷�ӱ���(��)</td>
             <td bgColor="#F7F7F7"><input name="FirstSuperaddAmt" type="text"  size="10" /></td>

		
        <td class=title>�����˺�</td>
		<TD class=input><Input class=input name=AccountNumber></TD>
	</tr>
	
	<TR class=common> 
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
		

</table>
		<hr>
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
