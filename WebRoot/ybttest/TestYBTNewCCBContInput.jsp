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
<SCRIPT src="TestYBTNewCCBCont.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestYBTNewCCBContInit.jsp"%>  

		<title>����ͨ����Լ����</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./TestYBTNewCCBSave.jsp" method=post name=fm target="fraSubmit">



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
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="SubmissionDate"></TD>
	</TR>
	
	<TR class=common>
		<td class=title>Ͷ�����</td>
		<TD class=input><Input class=input name="HOAppFormNumber" maxlength="16" id="HOAppFormNumber"></TD>
		<td class=title>����ӡˢ��</td>
		<TD class=input><Input class=input name="ProviderFormNumber" maxlength="16" ><font color="red">(��ӡ����ʱ����)</font></TD>	
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
	<TR class=common>
		<td class=title>��������</td>
		<TD class=input><Input class=input name="ContNo" id="ContNo"><input type="hidden" name="ContNo"><font color="red">(��ӡ����ʱ��д)</font></TD>
		<td class=title>����ƾ֤����</td>
		<TD class=input><Input class=input name="ContType" maxlength="16" id="ContType"><font color="red">(��ӡ����ʱ��д)</TD>
    </TR>

  
	<TR class=common>
		<td class=title>�ͻ����ճ�����������</td>
		<TD class=input>
			<select name="Cst_Rsk_Tlrnc_Cpy_Cd" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="01"> ������</option>
				<option value="02"> ������</option>
				<option value="03"> �Ƚ���</option>
				<option value="04"> ��ȡ��</option>
				<option value="05"> ������ȡ��</option>
			</select></TD>
    </TR>
	<TR class=common>
		<td class=title>���ղ�����Ч��</td>
		<TD class=input><Input class=input name="Rsk_Evlt_AvlDt"></font></TD>
		<td class=title>Ԥ����</td>
		<TD class=input><Input class=input name="Bdgt_Amt"><font color="red"></TD>
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
			    <option value="1010"> �������֤����</option>
				<option value="1022"> ����֤</option>
				<option value="1060"> ѧ��֤-</option>
				<option value="1032"> ����֤</option>
				<option value="1021"> ��ž�ʿ��֤</option>
				<option value="1040"> ���ڲ�</option>
				<option value="1080"> (�۰�)����֤��ͨ��֤</option>
				<option value="1070"> ̨ͨ��֤��������Ч����֤</option>
				<option value="1051">(���)����</option>
				<option value="1052"> (�й�)����</option>
				<option value="1050"> ����</option>
				<option value="1999">��������֤��</option>
				<option value="2999"> ��Թ�����֤��</option>
				<option value="1100">����</option>
				<option value="1011"> ��ʱ�������֤</option>
				<option value="1160"> ��̨��������֤ ̨��֤</option>
			</select></TD>
			
		<td class=title>Ͷ����֤������</td>
		<TD class=input><Input class=input name=AppGovtID></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ��������</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppBirthDate></TD>
		<td class=title>Ͷ����֤����Ч����</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppGovtEfcDate></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ����֤��ʧЧ����</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppGovtTermDate></TD>
		<td class=title>Ͷ����ͨѶ��ַ	</td>
		<TD class=input><Input class=input name=AppLine1></TD>
	</tr>
	
	<TR class=common>
		<td class=title>Ͷ������������	</td>
		<TD class=input><Input class=input name=AppZip></TD>		
		<td class=title>Ͷ���˹̶��绰</td>
		<TD class=input><Input class=input name=AppDialNumber1></TD>
	</tr>
	
		
	
	<TR class=common>
		<td class=title>Ͷ�����뱻���˹�ϵ</td>
		<TD class=input>
			<select name="AppToInsRelation" style="background-color: #D7E1F6" onchange="setFlag();">
				<option value=""></option>
			    <option value="0133043"> ����</option>
				<option value="0133010"> �ɷ� </option>
				<option value="0133010"> ���� </option>
				<option value="0133015"> ���� </option>
				<option value="0133016">ĸ�� </option>
				<option value="0133011"> ����</option>
				<option value="0133012"> Ů�� </option>
				<option value="0133017">�游 </option>
				<option value="0133018">��ĸ</option>	 
				 <option value="0133013"> ����</option>
				<option value="0133014"> ��Ů</option>
				<option value="0133020"> ���</option>
				<option value="0133019"> ���</option>
				<option value="0133035"> �ܵ�</option>	 
				 <option value="0133036"> ����</option>
				<option value="0133021"> �������� </option>
				<option value="0133002"> ͬ�� </option>	 
				 <option value="0133001"> ���� </option>
			</select></TD>
			<td class=title>Ͷ�����ƶ��绰	</td>
			<TD class=input><Input class=input name=AppDialNumber3></TD>			
	</tr>
	<tr class=common>
		<td class=title>Ͷ���˵�������</td>
		<TD class=input><Input class=input name=AppAddrLine></TD>
		<td class=title>Ͷ���˹���</td>
	<td bgColor="#F7F7F7" >
		<select name="AppCountry" id="AppCountry" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			  <option value="0156">�й�</option>
				<option value="--"> ����</option>
				</select>
				</td>
	</tr>
	<TR class=common>
	<td class=title>Ͷ����ְҵ����</td>  
		<TD class=input> 
			<select name="ApplJobCode"  style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="3010101"> һ������</option>
			</select>
		</TD>	
	<td class=title>Ͷ���˾�������</td>
		<TD class=input>
		<select name="PbDenType" id="PbDenType" style="background-color: rgb(215, 225, 246);"> 
			<option value=""></option>
			    <option value="1">����</option>
				<option value="2" selected="selected">ũ��</option>
			</select>
		</TD>
	</tr>
	<TR class=common>
		<td class=title>Ͷ����������</td>
		<TD class=input><Input class=input name=PbInCome>Ԫ</TD>
		<td class=title>Ͷ���˼�ͥ������</td>
		<TD class=input><Input class=input name=PbHomeInCome>Ԫ</TD>
	</tr>
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class=input name=RspbName></TD>
	</tr>
	
	<TR class=common>
		<TD class=title>Ͷ����ʡ����</TD>
		<TD class=input><Input class=input name="Plchd_Prov_Cd"/></TD>
		<TD class=title>Ͷ�����д���</TD>
		<TD class=input><Input class=input name="Plchd_City_Cd"/></TD>
	</TR>
		
		<TR class=common>
		<TD class=title>Ͷ�������ش���</TD>
		<TD class=input><Input class=input name="Plchd_CntyAndDstc_Cd"/></TD>
		<TD class=title>Ͷ������ϸ��ַ����</TD>
		<TD class=input><Input class=input name="Plchd_Dtl_Adr_Cntnt"/></TD>
	</TR>
	
	<TR class=common>
		<td class=title>Ͷ���˹̶��绰��������</td>
		<TD class=input><Input class=input name=PlchdFixTelDmstDstcNo></TD>
		<td class=title>Ͷ�����ƶ��绰��������</td>
		<TD class=input><Input class="input" name=PlchdMoveTelItlDstcNo></TD>
	</tr>
	
	<TR class=common>
		<td class=title> Ͷ���˹��ҵ�������</td>
		<TD class=input><Input class=input name=Plchd_Nat_Cd></TD>
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
		<td class=title>����������ƴ��ȫ��</td>
		<TD class=input><Input class=input name="Rcgn_CPA_FullNm" id="Ins0"></TD>
	</tr> 
	
	<TR class=common> 
		<td class=title>�������Ա�</td>
		<TD class=input>
			<select name="InsGender" id="Ins2" style="background-color: #D7E1F6">
				<option value=""></option>
			     <option value="1"> ����</option>
				<option value="2"> Ů��</option>
			</select>
		</TD>
		<td class=title>������֤������</td>
		<TD class=input>
			<select name="InsGovtIDTC" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1010"> �������֤����</option>
				<option value="1022"> ����֤</option>
				<option value="1060"> ѧ��֤-</option>
				<option value="1032"> ����֤</option>
				<option value="1021"> ��ž�ʿ��֤</option>
				<option value="1040"> ���ڲ�</option>
				<option value="1080"> (�۰�)����֤��ͨ��֤</option>
				<option value="1070"> ̨ͨ��֤��������Ч����֤</option>
				<option value="1051">(���)����</option>
				<option value="1052"> (�й�)����</option>
				<option value="1050"> ����</option>
				<option value="1999">��������֤��</option>
				<option value="2999"> ��Թ�����֤��</option>
				<option value="1100">����</option>
				<option value="1011"> ��ʱ�������֤</option>
				<option value="1160"> ��̨��������֤ ̨��֤</option>
			</select></TD>
	</tr>
	
	<TR class=common>
		<td class=title>����������</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsBirthDate"></TD>
		<td class=title>������֤������</td>
		<TD class=input><Input class=input id="Ins4" name="InsGovtID"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>������֤����Ч����	</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsGovtEfcDate></TD>
		<td class=title>������֤��ʧЧ����	</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsGovtExpDate></TD>
	</tr>
	<TR class=common>
		<td class=title>������ͨѶ��ַ	</td>
		<TD class=input><Input class=input id="Ins7" name="InsLine1"></TD>
		<td class=title>��������������	</td>
		<TD class=input><Input class=input id="Ins8" name="InsZip"></TD>	
	</tr>
	 
	<TR class=common>
		<td class=title>�����˹̶��绰</td>
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
			</select>
		</TD>
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
		<td class=title>������������</td>
		<TD class=input><Input class=input name=InsInCome>Ԫ</TD>
	</tr>
	<tr class=common>
		<td class=title>������ǰ��Ŀ�ĵظ���</td>
		<TD class=input><Input class=input name=DesNum></TD>
		<td class=title>������ǰ��Ŀ�ĵ�</td>
		<TD class=input><Input class=input name=Destinations></TD>
	</tr>
	
	<TR class=common>
		<TD class=title>������ʡ����</TD>
		<TD class=input><Input class=input name="Rcgn_Prov_Cd"/></TD>
		<TD class=title>�������д���</TD>
		<TD class=input><Input class=input name="Rcgn_City_Cd"/></TD>
	</TR>
		
		<TR class=common>
		<TD class=title>���������ش���</TD>
		<TD class=input><Input class=input name="Rcgn_CntyAndDstc_Cd"/></TD>
		<TD class=title>��������ϸ��ַ����</TD>
		<TD class=input><Input class=input name="Rcgn_Dtl_Adr_Cntnt"/></TD>
	</TR>
	
	<TR class=common>
		<td class=title>�����˹̶��绰��������</td>
		<TD class=input><Input class=input name=RcgnFixTelDmst_DstcNo></TD>
		<td class=title>�������ƶ��绰��������</td>
		<TD class=input><Input class="input" name=RcgnMoveTelItnlDstcNo></TD>
	</tr>
	
	<TR class=common>
		<td class=title>�����˹��ҵ�������</td>
		<TD class=input><Input class=input name=Rcgn_Nat_Cd></TD>
	</tr>
	
</table>
 



<%--*************************��������Ϣ************************--%>
<table class=common align=center> 

	<tr> 
		<td class=titleImg align=center>����������Ϣ��</td>
		<td colspan="3"><input type="checkbox" name=BnfFlag  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator>
		<font color="red">�������Ƿ�Ϊ����(ѡ��Ϊ����)</font></td>
		<td>���������������ʹ���</td>
		<td><input  name=AgIns_Benf_TpCd></td>
	</tr>    

	<tr >  
		<td>�뱻�����˹�ϵ</td>
		<td>����</td>
		<td>�Ա�</td>
		<td>֤������	</td>
		<td>֤������	</td>
		<td>������֤����Ч����</td>
		<td>������֤��ʧЧ����</td>
		<td>������ͨѶ��ַ</td>
		<td>��������ϸ��ַ</td>
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
			    <option value="0133043"> ����</option>
				<option value="0133010"> �ɷ� </option>
				<option value="0133010"> ���� </option>
				<option value="0133015"> ���� </option>
				<option value="0133016">ĸ�� </option>
				<option value="0133011"> ����</option>
				<option value="0133012"> Ů�� </option>
				<option value="0133017">�游 </option>
				<option value="0133018">��ĸ</option>	 
				 <option value="0133013"> ����</option>
				<option value="0133014"> ��Ů</option>
				<option value="0133020"> ���</option>
				<option value="0133019"> ���</option>
				<option value="0133035"> �ܵ�</option>	 
				 <option value="0133036"> ����</option>
				<option value="0133021"> �������� </option>
				<option value="0133002"> ͬ�� </option>	 
				 <option value="0133001"> ���� </option>
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
			  <option value="1010"> �������֤����</option>
				<option value="1022"> ����֤</option>
				<option value="1060"> ѧ��֤-</option>
				<option value="1032"> ����֤</option>
				<option value="1021"> ��ž�ʿ��֤</option>
				<option value="1040"> ���ڲ�</option>
				<option value="1080"> (�۰�)����֤��ͨ��֤</option>
				<option value="1070"> ̨ͨ��֤��������Ч����֤</option>
				<option value="1051">(���)����</option>
				<option value="1052"> (�й�)����</option>
				<option value="1050"> ����</option>
				<option value="1999">��������֤��</option>
				<option value="2999"> ��Թ�����֤��</option>
				<option value="1100">����</option>
				<option value="1011"> ��ʱ�������֤</option>
				<option value="1160"> ��̨��������֤ ̨��֤</option>
			</select></td>
		<td><input name="BnfGovtID1" type="text" id="BnfReadOnly15" class=common/></td>
		
	  <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappbgndate1" name="applbegindate1"></td>
      <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappvlddate1" name="applvaliddate1"></td>
		
		<TD><Input class="input"  name="BnfAdress1" ></TD>
		<TD><Input class="input"  name="BnfAdressContent1" ></TD>
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
			      <option value="0133043"> ����</option>
				<option value="0133010"> �ɷ� </option>
				<option value="0133010"> ���� </option>
				<option value="0133015"> ���� </option>
				<option value="0133016">ĸ�� </option>
				<option value="0133011"> ����</option>
				<option value="0133012"> Ů�� </option>
				<option value="0133017">�游 </option>
				<option value="0133018">��ĸ</option>	 
				 <option value="0133013"> ����</option>
				<option value="0133014"> ��Ů</option>
				<option value="0133020"> ���</option>
				<option value="0133019"> ���</option>
				<option value="0133035"> �ܵ�</option>	 
				 <option value="0133036"> ����</option>
				<option value="0133021"> �������� </option>
				<option value="0133002"> ͬ�� </option>	 
				 <option value="0133001"> ���� </option>
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
			  <option value="1010"> �������֤����</option>
				<option value="1022"> ����֤</option>
				<option value="1060"> ѧ��֤-</option>
				<option value="1032"> ����֤</option>
				<option value="1021"> ��ž�ʿ��֤</option>
				<option value="1040"> ���ڲ�</option>
				<option value="1080"> (�۰�)����֤��ͨ��֤</option>
				<option value="1070"> ̨ͨ��֤��������Ч����֤</option>
				<option value="1051">(���)����</option>
				<option value="1052"> (�й�)����</option>
				<option value="1050"> ����</option>
				<option value="1999">��������֤��</option>
				<option value="2999"> ��Թ�����֤��</option>
				<option value="1100">����</option>
				<option value="1011"> ��ʱ�������֤</option>
				<option value="1160"> ��̨��������֤ ̨��֤</option>
			</select></td>
		<td><input name="BnfGovtID2" type="text" id="BnfReadOnly25" class=common/></td>
		
	  <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappbgndate2" name="applbegindate2"></td>
      <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappvlddate2" name="applvaliddate2"></td>
		
		<TD><Input class="input"  name="BnfAdress2" ></TD>
		<TD><Input class="input"  name="BnfAdressContent2" ></TD>
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
			      <option value="0133043"> ����</option>
				<option value="0133010"> �ɷ� </option>
				<option value="0133010"> ���� </option>
				<option value="0133015"> ���� </option>
				<option value="0133016">ĸ�� </option>
				<option value="0133011"> ����</option>
				<option value="0133012"> Ů�� </option>
				<option value="0133017">�游 </option>
				<option value="0133018">��ĸ</option>	 
				 <option value="0133013"> ����</option>
				<option value="0133014"> ��Ů</option>
				<option value="0133020"> ���</option>
				<option value="0133019"> ���</option>
				<option value="0133035"> �ܵ�</option>	 
				 <option value="0133036"> ����</option>
				<option value="0133021"> �������� </option>
				<option value="0133002"> ͬ�� </option>	 
				 <option value="0133001"> ���� </option>
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
			  <option value="1010"> �������֤����</option>
				<option value="1022"> ����֤</option>
				<option value="1060"> ѧ��֤-</option>
				<option value="1032"> ����֤</option>
				<option value="1021"> ��ž�ʿ��֤</option>
				<option value="1040"> ���ڲ�</option>
				<option value="1080"> (�۰�)����֤��ͨ��֤</option>
				<option value="1070"> ̨ͨ��֤��������Ч����֤</option>
				<option value="1051">(���)����</option>
				<option value="1052"> (�й�)����</option>
				<option value="1050"> ����</option>
				<option value="1999">��������֤��</option>
				<option value="2999"> ��Թ�����֤��</option>
				<option value="1100">����</option>
				<option value="1011"> ��ʱ�������֤</option>
				<option value="1160"> ��̨��������֤ ̨��֤</option>
			</select></td>
		<td><input name="BnfGovtID3" type="text" id="BnfReadOnly35" class=common/></td>
		
	  <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappbgndate3" name="applbegindate3"></td>
      <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappvlddate3" name="applvaliddate3"></td>
		
		<TD><Input class="input"  name="BnfAdress3" ></TD>
		<TD><Input class="input"  name="BnfAdressContent3" ></TD>
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
		<td>�������ײͱ��</td>
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
            	<option value="221301">�к���δ�������</option>
            	<option value="231204">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
            	<option value="221201">�к����ݻ�����ȫ����A��</option>
            	<option value="231201">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��</option>
                <option value="231202">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="231203">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
                <option value="231302">�к�������������գ��ֺ��ͣ�</option>
                 <option value="241201">�к���Ӯ�Ƹ���ȫ���գ������ͣ�A��</option>
            </select>
        	<input  name="RiskCodeName" type="hidden"  size="25"/>
        	<input  name="RiskCode" type="hidden"  size="10"/>
         </td>
		<td><input name="AgIns_Pkg_ID" type="text"   size="12"/></td>
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
	<tr >
		<td>Ͷ��������Ϣ</td>
		<td>��ѡ������ʱ��ս��</td>
		<td>�״ζ���׷�ӱ���</td>
		<td>������ϵ��</td>
		<td>������ϵ���뱻���˹�ϵ</td>
		<td>������ϵ�绰</td>
	</tr>
	<tr class=common2>
		<td><Input class=input name=Ins_Scm_Inf></td>
		<TD ><Input class=input name=Opt_Part_DieIns_Amt  type="text"  size="10">��</TD>
		<TD ><Input class=input name=FTm_Extr_Adl_InsPrem type="text"  size="10">��</TD>
		<TD ><Input class=input name=Emgr_CtcPsn type="text"  size="10"></TD>
		<td bgColor="#F7F7F7" >
		<select name="EmgrCtcPsnAndRcReTpCd" id="EmgrCtcPsnAndRcReTpCd" style="background-color: rgb(215, 225, 246);">
		<option value=""></option>
			    <option value="0133043"> ����</option>
				<option value="0133010"> �ɷ� </option>
				<option value="0133010"> ���� </option>
				<option value="0133015"> ���� </option>
				<option value="0133016">ĸ�� </option>
				<option value="0133011"> ����</option>
				<option value="0133012"> Ů�� </option>
				<option value="0133017">�游 </option>
				<option value="0133018">��ĸ</option>	 
				 <option value="0133013"> ����</option>
				<option value="0133014"> ��Ů</option>
				<option value="0133020"> ���</option>
				<option value="0133019"> ���</option>
				<option value="0133035"> �ܵ�</option>	 
				 <option value="0133036"> ����</option>
				<option value="0133021"> �������� </option>
				<option value="0133002"> ͬ�� </option>	 
				 <option value="0133001"> ���� </option>
			</select></td>
		<TD ><Input class=input name=Emgr_Ctc_Tel type="text"  size="18"></TD>
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
				<option value="01"> �����ڽ� </option>
				<option value="02"> ���� </option>
                <option value="03"> ���� </option>
                <option value="04"> ����ĳȷ������ </option>
                <option value="05"> �������� </option>
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

<table class=common align=center>
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	
	<TR>
		<td class=title>������</td>
		<td class=input> 
		<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="P53819113">¼���˱�</option>
			    <option value="P53819188">��ӡͶ����</option>
			    <option value="P53819151">��ѯ����</option>
			    <option value="P53819152">�շ�ǩ��</option>
			    <option value="P53819182">��ӡ����</option>
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
