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
<SCRIPT src="TestABCNewCont.js"></SCRIPT>
<script type="text/javascript">
	
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   
		<%@include file="TestABCNewContInit.jsp"%>  
 
		<title>����ͨ����Լ����</title>
	</head>
<body onload="initElementtype();initForm();"> 
<form action="./TestABCNewContSave.jsp" method=post name=fm target="fraSubmit">


<%--*************************���׹�����Ϣ*************************--%>
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	<tr>
<td class=title>�����־</td>
<TD class=input><select name="FunctionFlag" style="background-color: #D7E1F6;width: 90px;" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="01"> �±�����</option>
				<option value="02"> �±�����</option>
			</select></TD>
		
		<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name=ip></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name=port></td>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="���ͽ���" onclick="submitForm();"></TD> 
	</TR>
</table>
		
<hr>

<%--*************************������Ϣ************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 
	
	<TR class=common>
		<TD class=title>���ж˽�������</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=BankDate></TD>
		<TD class=title>������ˮ��</TD> 
		<TD class=input><input class=input name=TransrNo></TD>
		<input type=hidden name=NewTransNo>
	</TR>
	
	<TR class=common>
		<td class=title>��������</td>
		<TD class=input>
			<input class="input" name="ZoneNo"/>
			<!-- 
							ondblclick="return showRegionCode()">
        	<input  name="ZoneNoName"  type="text"  size="11"/></TD>
        	 -->
		<td class=title>�������</td>
		<TD class=input>
			<input class="input" name="BrNo">
			<!-- 
							ondblclick="return showBranchCode()">
        	<input  name="BrNoName" type="text"  size="11"/>
        	 -->
		</TD>
	</TR>
	
	<TR class=common> 
		<td class=title>��Ա����</td>
		<TD class=input><Input class=input name=TellerNo></TD>
		<!-- ���д��� -->
		<Input type=hidden name=BankCode value="05">
		<!-- ���չ�˾���� -->
		<Input type=hidden name=InsuID value="01">
	</tr>	
	
	<TR class=common>
		<td class=title>Ͷ��������ˮ��</td>
		<TD class=input><Input class=input  name=ReqsrNo><font color=red>�±����ѱ�����</font></TD>
		<td class=title>���� </td>
		<TD class=input><Input class=input  name=Prem02><font color=red>�±����ѱ�����</font>
		</TD>
	</tr>
</table>

<hr>
<%--*************Ͷ����Ϣ*************--%>		
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��Ͷ����Ϣ��</td>
	</tr>
	
		<TR class=common>
		<TD class=title>Ͷ������</TD> 
		<TD class=input><input class=input name=ProposalContNo></TD>	
		<td class=title>Ͷ������</td>
		<TD class=input><Input class=coolDatePicker  dateFormat="short" name=PolApplyDate></TD>	
	</TR>
	<!-- 
	<TR class=common> 		
		<td class=title>���ո���������</td>
		<TD class=input><Input class=codeno  name=AccBankNAME 
							ondblclick="return ShowAccBankNAME()"
							onkeyup="return ShowAccBankNAME()">
        	<input  name="AccBankNAMEName" readonly="readonly" type="text"  size="11"/></TD>
	</TR>
	 -->
	 <!-- 
	<TR class=common> 		
		<td class=title>���ո������˻�</td>
		<TD class=input><Input class=input name=BankAccNo></TD>
	</TR>
	 -->
	<TR class=common> 	
	<td class=title>�˻�����</td>
		<TD class=input><Input class=input name=AccName></TD>	
		<td class=title>�����ʺ�</td>
		<TD class=input><Input class=input name=AccNo></TD>
		<!-- <td class=title>�������� </td>
		<TD class=input><Input class=input name=Password></TD>
		 -->
		
	</TR>	
		
	<TR class=common> 		
	 <td class=title>����ӡˢ����</td>
		<TD class=input><Input class=input name=PrtNo></TD>
		<td class=title>������ʽ</td>
		<TD class=input>
			<select name=PayMode class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0">�ֽ� </option>
                <option value="1">���п� </option>
                <option value="2">�˻� </option>
             </select></TD>	
           
	</TR>
	<TR class=common> 		
	 	
             <td class=title>�ر�Լ��</td>
			<TD class=input>
			<Input class=input name=SpecContent></TD>
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
		<TD class=input><Input class=input name=Name></TD>
		<td class=title>Ͷ�����Ա�</td>
		<TD class=input>
			<select name="Sex" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></TD>
		
	</tr> 

	<TR class=common>
		<td class=title>��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday></TD>
		<td class=title>Ͷ������ϵ�绰 </td>
		<TD class=input><Input class=input  name=Phone>
		</TD>
	</tr>
	<TR class=common> 
		<td class=title>Ͷ����֤������</td>
		<TD class=input><Input class=codeno name=IDType 
							ondblclick="return ShowIDTypeCode()"
							onkeyup="return ShowIDTypeCode()">
        	<input  name="IDTypeName" readonly="readonly" type="text"  size="11"/>
			</TD>
			
		<td class=title>Ͷ����֤������</td>
		<TD class=input><Input class=input name=IDNo></TD>
	</tr>
	<TR class=common>
		<td class=title>Ͷ�����ֻ���</td>
		<TD class=input><Input class=input name=Mobile></TD>
		<td class=title>Ͷ����ͨѶ��ַ</td>
		<TD class=input><Input class=input name=Address></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ����ͨѶ�ʱ�</td>
		<TD class=input><Input class=input name=ZipCode></TD>
		<td class=title>Ͷ���˹���</td>
		<TD class=input><Input class=input name=County></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ����֤����Ч��</td>
		<TD class=input><Input class=input name=ValidYear></TD>
		<td class=title>Ͷ����Email</td>
		<TD class=input><Input class=input name=Email></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ����ְҵ����</td>
		<TD class=input><Input class=codeno name=JobCode 
							ondblclick="return ShowJobCode()"
							onkeyup="return ShowJobCode()">
        	<input  name="JobCodeName" readonly="readonly" type="text"  size="11"/></TD>
		<td class=title>�뱻���˹�ϵ</td>
		<TD class=input>
			<input class="codeno" name="RelaToInsured"
							ondblclick="return ShowRelaToInsured()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelaToInsuredName" readonly="readonly" type="text"  size="10"/></TD>
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
		<td class=title></td>
		<TD class=input></TD>
		<td class=title></td>
		<TD class=input></TD>	
	</tr> 
</table>	
<div id="insured" name="insured" style="display: ''">
<table class=common align=center>
	
	
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class=input name=BName id='Ins1'></TD>
		<td class=title>�������Ա�</td>
		<TD class=input>
			<select name="BSex" style="background-color: #D7E1F6;width: 90px;" id='Ins2'>
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></TD>
		
	</tr> 

	<TR class=common>
		<td class=title>��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=BBirthday id='Ins3'></TD>
		<td class=title>��������ϵ�绰 </td>
		<TD class=input><Input class=input  name=BPhone id='Ins13'>
		</TD>
	</tr>
	<TR class=common> 
		<td class=title>������֤������</td>
		<TD class=input>
			<Input class=codeno name=BIDType id='Ins4'
							ondblclick="return BShowIDTypeCode()"
							onkeyup="return BShowIDTypeCode()">
        	<input  name="BIDTypeName" readonly="readonly" type="text"  size="11"/>
			</TD>
			
		<td class=title>������֤������</td>
		<TD class=input><Input class=input name=BIDNo id='Ins5'></TD>
	</tr>
	<TR class=common>
		<td class=title>�������ֻ���</td>
		<TD class=input><Input class=input name=BMobile id='Ins6'></TD>
		<td class=title>������ͨѶ��ַ</td>
		<TD class=input><Input class=input name=BAddress id='Ins7'></TD>
	</tr>
	
	<TR class=common>
		<td class=title>������ͨѶ�ʱ�</td>
		<TD class=input><Input class=input name=BZipCode id='Ins8'></TD>
		<td class=title>�����˹���</td>
		<TD class=input><Input class=input name=BCounty id='Ins9'></TD>
	</tr>
	
	<TR class=common>
		<td class=title>������֤����Ч��</td>
		<TD class=input><Input class=input name=BValidYear id='Ins10'></TD>
		<td class=title>������Email</td>
		<TD class=input><Input class=input name=BEmail id='Ins11'></TD>
	</tr>
	<TR class=common>
		<td class=title>������ְҵ����</td>
		<TD class=input><Input class=codeno name=BJobCode id='Ins12'
							ondblclick="return BShowJobCode()"
							onkeyup="return BShowJobCode()">
        	<input  name="BJobCodeName" readonly="readonly" type="text"  size="11"/></TD>
	</tr>

</table>
</div>

<hr>

<%--*************************������1��Ϣ************************--%>
<table class=common align=center> 
	<tr> 
		<td class=titleImg align=center>��������1��Ϣ��</td>
		<td colspan="2"><input type="checkbox" name=Indicator  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator >
		<font color="red">�������Ƿ�Ϊ����(ѡ��Ϊ����)</font></td>
	</tr>    

	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><select name="Type1" id="BnfReadOnly1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0"> ����������</option>
			    <option value="1"> ���������</option>
			</select></TD>
		<td class=title>����</td>
		<TD class=input>
			<Input class=input name=Name1 id="BnfReadOnly2">
			</TD>		
			
	</TR> 
	
	<TR class=common> 
		<td class=title>�Ա�</td>
		<TD class=input>
			<select name="Sex1" id="BnfReadOnly3" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></TD>
			
		<td class=title>��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday1 id="BnfReadOnly4"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>���֤������</td>
		<TD class=input><Input class=codeno name=IDType1 id='BnfReadOnly5'
							ondblclick="return ShowIDTypeCode1()"
							onkeyup="return ShowIDTypeCode1()">
        	<input  name="IDTypeName1" readonly="readonly" type="text"  size="11"/>
			</TD>
		<td class=title>���֤������ </td>
		<TD class=input><Input class=input name=IDNo1 id="BnfReadOnly6">
		</TD>
	</tr>
		
	<TR class=common>
		<td class=title>�뱻�����˹�ϵ</td>
		<TD class=input>
			<input class="codeno" name="RelationToInsured1" id="BnfReadOnly7"
							ondblclick="return ShowRelationToInsured1()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelationToInsuredName1" readonly="readonly" type="text"  size="10" /></TD>
        	<td class=title>��ַ</td>
		<TD class=input><Input class=input name=Address1 id="BnfReadOnly8"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>����˳�� </td>
		<TD class=input><Input class=input name=BnfGrade1 id="BnfReadOnly9"></TD>
		<td class=title>�������</td>
		<TD class=input><Input class=input name=BnfLot1 id="BnfReadOnly10">%</TD>		
	</tr>
	
</table>

<%--*************************������2��Ϣ************************--%>
<table class=common align=center>
	<tr> 
		<td class=titleImg align=center>��������2��Ϣ��</td>
	</tr> 
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fBnflist2"
						  	  style="cursor:hand;" OnClick="showPage(this,divBnflist2);" ></td>
	</tr>
</table> 

<div id="divBnflist2" style="display: 'none'"> 
<table class=common align=center> 
	   

	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><select name="Type2" id="BnfReadOnly11" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0"> ����������</option>
			    <option value="1"> ���������</option>
			</select></TD>
		<td class=title>����</td>
		<TD class=input>
			<Input class=input name=Name2 id="BnfReadOnly12">
			</TD>		
	</TR> 
	
	<TR class=common> 
		<td class=title>�Ա�</td>
		<TD class=input>
			<select name="Sex2" id="BnfReadOnly13" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></TD>
			
		<td class=title>��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday2 id="BnfReadOnly14"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>���֤������</td>
		<TD class=input><Input class=codeno name=IDType2 id='BnfReadOnly15'
							ondblclick="return ShowIDTypeCode2()"
							onkeyup="return ShowIDTypeCode2()">
        	<input  name="IDTypeName2" readonly="readonly" type="text"  size="11"/>
			</TD>
		<td class=title>���֤������ </td>
		<TD class=input><Input class=input name=IDNo2 id="BnfReadOnly16">
		</TD>
	</tr>
		
	<TR class=common>
		<td class=title>�뱻�����˹�ϵ</td>
		<TD class=input>
			<input class="codeno" name="RelationToInsured2" id="BnfReadOnly17"
							ondblclick="return ShowRelationToInsured1()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelationToInsuredName2" readonly="readonly" type="text"  size="10" /></TD>
        	<td class=title>��ַ</td>
		<TD class=input><Input class=input name=Address2 id="BnfReadOnly18"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>����˳�� </td>
		<TD class=input><Input class=input name=BnfGrade2 id="BnfReadOnly19"></TD>
		<td class=title>�������</td>
		<TD class=input><Input class=input name=BnfLot2 id="BnfReadOnly20">%</TD>		
	</tr>
	
</table>
</div>
<%--*************************������3��Ϣ************************--%>
<table class=common align=center>
	<tr> 
		<td class=titleImg align=center>��������3��Ϣ��</td>
	</tr>  	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif"
						  	  style="cursor:hand;" OnClick="showPage(this,divBnflist3);" ></td>
	</tr>
</table> 
<div id="divBnflist3" style="display: 'none'"> 

<table class=common align=center> 
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><select name="Type3" id="BnfReadOnly21" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0"> ����������</option>
			    <option value="1"> ���������</option>
			</select></TD>
		<td class=title>����</td>
		<TD class=input>
			<Input class=input name=Name3 id="BnfReadOnly22">
			</TD>		
	</TR> 
	
	<TR class=common> 
		<td class=title>�Ա�</td>
		<TD class=input>
			<select name="Sex3" id="BnfReadOnly23" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
				<option value="2"> ����</option>
			</select></TD>
			
		<td class=title>��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday3 id="BnfReadOnly24"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>���֤������</td>
		<TD class=input><Input class=codeno name=IDType3 id='BnfReadOnly25'
							ondblclick="return ShowIDTypeCode3()"
							onkeyup="return ShowIDTypeCode3()">
        	<input  name="IDTypeName3" readonly="readonly" type="text"  size="11"/>
			</TD>
		<td class=title>���֤������ </td>
		<TD class=input><Input class=input name=IDNo3 id="BnfReadOnly26">
		</TD>
	</tr>
		
	<TR class=common>
		<td class=title>�뱻�����˹�ϵ</td>
		<TD class=input>
			<input class="codeno" name="RelationToInsured3" id="BnfReadOnly27"
							ondblclick="return ShowRelationToInsured1()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelationToInsuredName3" readonly="readonly" type="text"  size="10" /></TD>
        	<td class=title>��ַ</td>
		<TD class=input><Input class=input name=Address3 id="BnfReadOnly28"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>����˳�� </td>
		<TD class=input><Input class=input name=BnfGrade3 id="BnfReadOnly29"></TD>
		<td class=title>�������</td>
		<TD class=input><Input class=input name=BnfLot3 id="BnfReadOnly30">%</TD>		
	</tr>
	
</table>
</div>
<hr>

<%--*************������Ϣ*************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 

	<tr class=common2>
	<td class=title>���ִ���</td>
		<td bgColor="#F7F7F7">
		<select name="Code"  style="background-color: #D7E1F6">
				<option value=""></option>
            	<option value="231201">�к�BB��ȫ���գ��ֺ��ͣ�A��</option>
                <option value="231202">�к�BB��ȫ���գ��ֺ��ͣ�B��</option>
                <option value="231203">�к�CC��ȫ���գ��ֺ��ͣ�</option>
            </select>
			</td>
		<!-- <td class=title>��������</td>
		<TD class=input><Input class=input name=RiskPassword></TD>
		 -->
		 <td class=title>���շ� </td>
		<TD class=input><Input class=input name=Prem ></TD>
	</tr>
	
	<TR class=common>
		
		<td class=title>����</td>
		<TD class=input><Input class=input name=Amnt ></TD>		
		<td class=title>������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=CValiDate ></TD>	
	</tr>
	
	<TR class=common>
		<!-- <td class=title>���ʻ򽻷ѱ�׼ </td>
		<TD class=input><Input class=input name=Rate >%</TD>
		 -->
			
		<td class=title>Ͷ������ </td>
		<TD class=input><Input class=input name=Mult ></TD>
		<td class=title>���ѷ�ʽ</td>
		<TD class=input><select name="PayIntv" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �½� </option>
                <option value="3"> ���� </option>
                <option value="4"> ���꽻 </option>
                <option value="5"> �꽻 </option>
                <option value="6"> ������ </option>
             </select></TD>	
	</tr>
	
	<TR class=common>
		
		<td class=title>�������������־</td>
		<TD class=input><select name="PayEndYearFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
             </select></TD>		
             <td class=title>��������/���� </td>
		<TD class=input><Input class=input name=PayEndYear ></TD>
	</tr>
		
	<TR class=common>
		
		<td class=title>������������</td>
		<TD class=input><select name="InsuYearFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
                <option value="5"> ������ </option>
             </select></TD>		
             <td class=title>�������� </td>
		<TD class=input><Input class=input name=InsuYear ></TD>
	</tr>
	
	 
	<TR class=common>
		<td class=title>��ȡ���������־</td>
		<TD class=input><select name="GetYearFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
             </select></TD>		
             <td class=title>��ȡ����/���� </td>
		<TD class=input><Input class=input name=GetYear ></TD>
	</tr>
	 
	<TR class=common>
		<!-- 
		<td class=title>�Զ��潻���</td>
		<TD class=input><select name="AutoPayFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> ���潻 </option>
                <option value="1"> �潻 </option>
             </select></TD>		
              -->
             <td class=title>������ȡ��ʽ </td>
		<TD class=input>
		<select name="BonusGetMode" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �ۻ���Ϣ </option>
                <option value="2"> ��ȡ�ֽ� </option>
                <option value="3"> �ֽ����� </option>
                <option value="4"> ���� </option>
                <option value="5"> ����� </option>
             </select></TD>
              <td class=title>���ڱ��ս���ȡ��ʽ</td>
		<TD class=input><select name="FullBonusGetMode" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> һ��ͳһ���� </option>
                <option value="2"> �����ʽ��ȡ </option>
             </select></TD>
	</tr>
	<!-- 
	<TR class=common>
		
		<td class=title>���������� </td>
		<TD class=input><select name="BonusPayMode" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �������� </option>
                <option value="1"> �޺������� </option>
             </select></TD>
         
		<td class=title>�������</td>
		<TD class=input><select name="SubFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> δ����� </option>
                <option value="1"> ����� </option>
             </select></TD>		
	</tr>
	
	<TR class=common>
		
		<td class=title>�Ƿ�ͬ���ֽ��ֵ�Զ��潻���շ�</td>
		<TD class=input><select name="IsCashAutoPay" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>		
             <td class=title>��ԥ���ڵ��ʽ��Ƿ����Ͷ�� </td>
		<TD class=input><select name="IsCashJoinInvest" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>
	</tr>
	-->
	<TR class=common>
		 <td class=title>�����ڼ� </td>
		<TD class=input><Input class=input name=Years ></TD>
		<td class=title>������֪��־</td>
		<TD class=input><select name="HealthFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>		
            
	</tr>
	
	<!-- 
	<TR class=common>
		
		
		<td class=title>��ʼ������</td>
		<TD class=input><Input class="input"  name=FirstRate ></TD>	
		 	
	</tr>
	-->
	<!-- 
	<TR class=common>
		<td class=title>��֤���� </td>
		<TD class=input><Input class=input name=SureRate ></TD>
		<td class=title>����ֹ��</td>
		<TD class=input><Input class="input"  name=EndDate ></TD>		
	</tr>
	 
	<TR class=common>
		<td class=title>�ۿ��� </td>
		<TD class=input><Input class=input name=PayYear ></TD>
		<td class=title>�ۿ�ʱ��</td>
		<TD class=input><Input class="input"  name=PayDate ></TD>		
	</tr>
	-->
</table>
	

<%--*************������1*************--%>	
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


	<tr class=common2>
	<td class=title>���ִ���</td>
		<td bgColor="#F7F7F7">
		<select name="Code1"  style="background-color: #D7E1F6">
					<option value=""></option>
            	<option value="001">�к���������ȫ���գ��ֺ��ͣ�</option>
                <option value="002">�к���������ȫ���գ��ֺ��ͣ�</option>
                <option value="003">��δ����</option>
                <option value="004">��δ���� </option>
            </select></td>
            <!--  
		<td class=title>��������</td>
		<TD class=input><Input class=input name=Password1></TD>
		-->
		<td class=title>���շ� </td>
		<TD class=input><Input class=input name=Prem1 ></TD>
	</tr>
	
	<TR class=common>
		
		<td class=title>����</td>
		<TD class=input><Input class=input name=Amnt1 ></TD>	
		<td class=title>������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=CValiDate1 ></TD>		
	</tr>
	
	<TR class=common>
		<!-- <td class=title>���ʻ򽻷ѱ�׼ </td>
		<TD class=input><Input class=input name=Rate1 >%</TD>
		 -->
			
		<td class=title>Ͷ������ </td>
		<TD class=input><Input class=input name=Mult1 ></TD>
		<td class=title>���ѷ�ʽ</td>
		<TD class=input><select name="PayIntv1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �½� </option>
                <option value="3"> ���� </option>
                <option value="4"> ���꽻 </option>
                <option value="5"> �꽻 </option>
                <option value="6"> ������ </option>
             </select></TD>	
	</tr>
	
	<TR class=common>
		
		<td class=title>�������������־</td>
		<TD class=input><select name="PayEndYearFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
             </select></TD>		
             <td class=title>��������/���� </td>
		<TD class=input><Input class=input name=PayEndYear1 ></TD>
	</tr>
		
	<TR class=common>
		
		<td class=title>������������</td>
		<TD class=input><select name="InsuYearFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
                <option value="5"> ������ </option>
             </select></TD>		
             <td class=title>�������� </td>
		<TD class=input><Input class=input name=InsuYear1 ></TD>
	</tr>
	
	 
	<TR class=common>
		
		<td class=title>��ȡ���������־</td>
		<TD class=input><select name="GetYearFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
             </select></TD>		
             <td class=title>��ȡ����/���� </td>
		<TD class=input><Input class=input name=GetYear1 ></TD>
	</tr>
	 
	 
	<TR class=common>
		<!--
		<td class=title>�Զ��潻���</td>
		<TD class=input><select name="AutoPayFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> ���潻 </option>
                <option value="1"> �潻 </option>
             </select></TD>	
             -->	
             <td class=title>������ȡ��ʽ </td>
		<TD class=input>
		<select name="BonusGetMode1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �ۻ���Ϣ </option>
                <option value="2"> ��ȡ�ֽ� </option>
                <option value="3"> �ֽ����� </option>
                <option value="4"> ���� </option>
                <option value="5"> ����� </option>
             </select></TD>
             <td class=title>���ڱ��ս���ȡ��ʽ</td>
		<TD class=input><select name="FullBonusGetMode1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> һ��ͳһ���� </option>
                <option value="2"> �����ʽ��ȡ </option>
             </select></TD>
	</tr>
	<!-- 
	<TR class=common>
		
		<td class=title>���������� </td>
		<TD class=input><select name="BonusPayMode1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �������� </option>
                <option value="1"> �޺������� </option>
             </select></TD>
         
		<td class=title>�������</td>
		<TD class=input><select name="SubFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> δ����� </option>
                <option value="1"> ����� </option>
             </select></TD>		
	</tr>
	
	<TR class=common>
		
		<td class=title>�Ƿ�ͬ���ֽ��ֵ�Զ��潻���շ�</td>
		<TD class=input><select name="IsCashAutoPay1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>		
             <td class=title>��ԥ���ڵ��ʽ��Ƿ����Ͷ�� </td>
		<TD class=input><select name="IsCashJoinInvest1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>
	</tr>
	-->
	<TR class=common>
		 <td class=title>�����ڼ� </td>
		<TD class=input><Input class=input name=Years1 ></TD>
		<td class=title>������֪��־</td>
		<TD class=input><select name="HealthFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>		
             
	</tr>
	
	<TR class=common>
		
		<!-- 
		<td class=title>��ʼ������</td>
		<TD class=input><Input class="input"  name=FirstRate1 ></TD>	
		 -->	
	</tr>
	<!-- 
	<TR class=common>
		<td class=title>��֤���� </td>
		<TD class=input><Input class=input name=SureRate1 ></TD>
		<td class=title>����ֹ��</td>
		<TD class=input><Input class="input"  name=EndDate1 ></TD>		
	</tr>
	 
	<TR class=common>
		<td class=title>�ۿ��� </td>
		<TD class=input><Input class=input name=PayYear1 ></TD>
		<td class=title>�ۿ�ʱ��</td>
		<TD class=input><Input class="input"  name=PayDate1 ></TD>		
	</tr>
	-->
</table>
</div>




<%--*************������2*************--%>	

<table class=common align=center>
	<tr>
		<td class=titleImg align=center>��������2��</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fRiskCode2"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList2);" ></td>
	</tr>
</table> 

<div id="divRiskList2" style="display: 'none'"> 
<table class=common align=center>
	
	<tr class=common2>
	<td class=title>���ִ���</td>
		<td bgColor="#F7F7F7">
		<select name="Code2"  style="background-color: #D7E1F6">
					<option value=""></option>
            	<option value="001">�к���������ȫ���գ��ֺ��ͣ�</option>
                <option value="002">�к���������ȫ���գ��ֺ��ͣ�</option>
                <option value="003">��δ����</option>
                <option value="004">��δ���� </option>
            </select></td>
            <!-- 
		<td class=title>��������</td>
		<TD class=input><Input class=input name=Password2></TD>
		 -->
		 <td class=title>���շ� </td>
		<TD class=input><Input class=input name=Prem2 ></TD>
	</tr>
	
	<TR class=common>
		
		<td class=title>����</td>
		<TD class=input><Input class=input name=Amnt2 ></TD>		
		<td class=title>������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=CValiDate2 ></TD>		
	</tr>
	
	<TR class=common>
		<!-- <td class=title>���ʻ򽻷ѱ�׼ </td>
		<TD class=input><Input class=input name=Rate2 >%</TD>
		 -->
		
		<td class=title>Ͷ������ </td>
		<TD class=input><Input class=input name=Mult2 ></TD>
		<td class=title>���ѷ�ʽ</td>
		<TD class=input><select name="PayIntv2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �½� </option>
                <option value="3"> ���� </option>
                <option value="4"> ���꽻 </option>
                <option value="5"> �꽻 </option>
                <option value="6"> ������ </option>
             </select></TD>	
	</tr>
	
	<TR class=common>
		
		<td class=title>�������������־</td>
		<TD class=input><select name="PayEndYearFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
             </select></TD>		
             <td class=title>��������/���� </td>
		<TD class=input><Input class=input name=PayEndYear2 ></TD>
	</tr>
		
	<TR class=common>
		
		<td class=title>������������</td>
		<TD class=input><select name="InsuYearFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
                <option value="5"> ������ </option>
             </select></TD>		
             <td class=title>�������� </td>
		<TD class=input><Input class=input name=InsuYear2 ></TD>
	</tr>
	
	
	<TR class=common>
		
		<td class=title>��ȡ���������־</td>
		<TD class=input><select name="GetYearFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> ���� </option>
                <option value="2"> �� </option>
                <option value="3"> ��</option>
                <option value="4"> �� </option>
             </select></TD>		
             <td class=title>��ȡ����/���� </td>
		<TD class=input><Input class=input name=GetYear2 ></TD>
	</tr>
	 
	<TR class=common>
		<!-- 
		<td class=title>�Զ��潻���</td>
		<TD class=input><select name="AutoPayFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> ���潻 </option>
                <option value="1"> �潻 </option>
             </select></TD>		
              -->
             <td class=title>������ȡ��ʽ </td>
		<TD class=input>
		<select name="BonusGetMode2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �ۻ���Ϣ </option>
                <option value="2"> ��ȡ�ֽ� </option>
                <option value="3"> �ֽ����� </option>
                <option value="4"> ���� </option>
                <option value="5"> ����� </option>
             </select></TD>
             <td class=title>���ڱ��ս���ȡ��ʽ</td>
		<TD class=input><select name="FullBonusGetMode2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> һ��ͳһ���� </option>
                <option value="2"> �����ʽ��ȡ </option>
             </select></TD>
	</tr>
	<!-- 
	<TR class=common>
		
		<td class=title>���������� </td>
		<TD class=input><select name="BonusPayMode2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �������� </option>
                <option value="1"> �޺������� </option>
             </select></TD>
         
		<td class=title>�������</td>
		<TD class=input><select name="SubFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> δ����� </option>
                <option value="1"> ����� </option>
             </select></TD>		
	</tr>
	
	<TR class=common>
		
		<td class=title>�Ƿ�ͬ���ֽ��ֵ�Զ��潻���շ�</td>
		<TD class=input><select name="IsCashAutoPay2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>		
             <td class=title>��ԥ���ڵ��ʽ��Ƿ����Ͷ�� </td>
		<TD class=input><select name="IsCashJoinInvest2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>
	</tr>
	-->
	
	<TR class=common>
		 <td class=title>�����ڼ� </td>
		<TD class=input><Input class=input name=Years2 ></TD>
		<td class=title>������֪��־</td>
		<TD class=input><select name="HealthFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> �� </option>
                <option value="1"> �� </option>
             </select></TD>		
             
	</tr>
	
	<!-- 
	<TR class=common>
		
		
		<td class=title>��ʼ������</td>
		<TD class=input><Input class="input"  name=FirstRate2 ></TD>	
		 	
	</tr>
	-->
	<!-- 
	<TR class=common>
		<td class=title>��֤���� </td>
		<TD class=input><Input class=input name=SureRate2 ></TD>
		<td class=title>����ֹ��</td>
		<TD class=input><Input class="input"  name=EndDate2 ></TD>		
	</tr>
	 
	<TR class=common>
		<td class=title>�ۿ��� </td>
		<TD class=input><Input class=input name=PayYear2 ></TD>
		<td class=title>�ۿ�ʱ��</td>
		<TD class=input><Input class="input"  name=PayDate2 ></TD>		
	</tr>
	-->
</table>
</div>
<!-- 
<hr/>
<table class=common align=center>

	<tr> 
		<td class=titleImg align=center>��������Ϣ��</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="AccountFlag"
						  	  style="cursor:hand;" OnClick="showPage(this,divLoanList);"></td>
	</tr>	 
</table> 
<div id="divLoanList" style="display: 'none'"> 
<table class=common align=center>
	<TR class=common>
		<td class=title>�����ͬ�� </td>
		<TD class=input><Input class=input name=LoanNo ></TD>
		<td class=title>�������</td>
		<TD class=input><Input class="input"  name=LoanBank ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>�������� </td>
		<TD class=input><Input class=input name=LoanDate ></TD>
		<td class=title>�������</td>
		<TD class=input><Input class="input"  name=LoanEndDate ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>�������� </td>
		<TD class=input><Input class=input name=LoanType ></TD>
		<td class=title>�����˺�</td>
		<TD class=input><Input class="input"  name=LoanAccNo ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>������ </td>
		<TD class=input><Input class=input name=LoanPrem ></TD>
		<td class=title>������ʼ��</td>
		<TD class=input><Input class="input"  name=InsuDate ></TD>		
	</tr>
	<TR class=common>
		<td class=title>���������� </td>
		<TD class=input><Input class=input name=InsuEndDate ></TD>		
	</tr>
</table>
</div>	

	
<table class=common align=center>

	<tr> 
		<td class=titleImg align=center>��Ͷ���˻���</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="AccountFlag"
						  	  style="cursor:hand;" OnClick="showPage(this,divAccList);"></td>
	</tr>	 
</table> 
<div id="divAccList" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList"></span></td>
	</tr>
	<tr>
		<td>Ͷ���ʻ����</td>
		<td>Ͷ���ʻ����</td>
		<td>Ͷ���ʻ�����</td>
	</tr>

 
<%--*************Ͷ���˻�һ*************--%>	
  
	<tr class=common2>
		<td bgColor="#F7F7F7"><input class="codeno" name="AccNo1"
							ondblclick="return showAccCode1();">
			<input class="codename" name="AccCodeName1" readonly="true"/></td>
		<td bgColor="#F7F7F7"><input name="AccFee1" type="text"  /></td>
		<td bgColor="#F7F7F7"><input name="AccRate1" type="text" />%</td>
	</tr>	
	
	<tr class=common2>
		<td bgColor="#F7F7F7"><input class="codeno" name="AccNo2"
							ondblclick="return showAccCode2();">
			<input class="codename" name="AccCodeName2" readonly="true"/></td>
		<td bgColor="#F7F7F7"><input name="AccFee2" type="text"   /></td>
		<td bgColor="#F7F7F7"><input name="AccRate2" type="text"  />%</td>
	</tr>	
	
	<tr class=common2>
		<td bgColor="#F7F7F7"><input class="codeno" name="AccNo3"
							ondblclick="return showAccCode3();">
			<input class="codename" name="AccCodeName3" readonly="true"/></td>
		<td bgColor="#F7F7F7"><input name="AccFee3" type="text"  /></td>
		<td bgColor="#F7F7F7"><input name="AccRate3" type="text"  />%</td>
	</tr>	
</table>
</div>	

<hr>
 -->

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
