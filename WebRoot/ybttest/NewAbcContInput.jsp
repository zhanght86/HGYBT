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
<SCRIPT src="NewAbcCont.js"></SCRIPT>
<script type="text/javascript">
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="NewAbcContInit.jsp"%>  

		<title>����ͨ����Լ����</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./NewAbcSave.jsp" method=post name=fm target="fraSubmit">



<hr/>


<%--*************************������Ϣ*************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 
	<TR class=common>
		<TD class=title>Ͷ������</TD>
		<TD class=input><Input class=input name="POLICYAPPLYSERIAL"/></TD>
		<TD class=title>����ӡˢ��</TD>
		<TD class=input><Input class=input name="VCHNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>��������</TD> 
		 <TD class=input><Input class="codeno" name=TranCom value="05" readonly="readonly"></TD>
		 <TD class=title>Ͷ������</TD>
		 <TD class=input><Input class="coolDatePicker" dateFormat = "short" name="APPLYDATE" /></TD>
	</TR>
	<TR class=common>
		<TD class=title>���н�����ˮ��</TD>
		<TD class=input><Input class=input name="SERIALNO"/><input type="hidden" name="InputTransrNo"></TD>
		<TD class=title>��������˳���</TD>
		<TD class=input><Input class=input name="APPNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>���ж˽�������</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat = "short" name="TRANSDATE" /></TD>
	</TR>
	<TR class=common>
		<TD class=title>��������</TD>
		<TD class=input><Input class=input name="ProvCode"/></TD>
		<TD class=title>�������</TD>
		<TD class=input><Input class=input name="BRANCHNO"/></TD>
	</TR>
	<TR class=common>
	<TD class=title>��Ա</TD>
		<TD class=input><Input class=input name="TLID"/></TD>
		</TR>
	<TR class=common>
		<TD class=title>Ӫ��Ա��</TD>
		<TD class=input><Input class=input name="SALER"/></TD>
		<TD class=title>��Ա�ʸ�֤���</TD>
		<TD class=input><Input class=input name="SALERCERTNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>�����ҵ�������֤���</TD>
		<TD class=input><Input class=input name="BRANCHCERTNO"/></TD>
		<TD class=title>��������</TD>
		<TD class=input><Input class=input name="BRANCHNAME"/></TD>
	</TR>
		<TR class=common>
		<TD class=title>���ڽɷ��ʻ�����</TD>
		<TD class=input><Input class=input name="CONACCNAME"/></TD>
		<TD class=title>���ڽɷ��˺�</TD>
		<TD class=input><Input class=input name="CONACCNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>ԭ������ˮ��</TD>
		<TD class=input><Input class=input name="ReqsrNo" id="OldTranNo"/></TD>
		<TD class=title>���ڽɷ��ʺ�</TD>
		<TD class=input><Input class=input name="PayAccount" id="PayAccountID"/><font color="red">(�б�ʱ¼��)</font></TD>
	</TR>
	</TR>
	<%-- **************������Ϣ****** --%>	
</table>



<%--*************************Ͷ������Ϣ************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��Ͷ������Ϣ��</td>
	</tr>
	
	<TR class=common>
		<TD class=title>Ͷ����֤������</TD>
		<TD class=input>
			<select name="APPLIDKIND" style="background-color: #D7E1F6" >
				<option value="110001">�������֤                 </option>
				<option value="110002">�غž������֤             </option>
				<option value="110003">��ʱ�������֤             </option>
				<option value="110004">�غ���ʱ�������֤         </option>
				<option value="110005">���ڲ�                     </option>
				<option value="110006">�غŻ��ڲ�                 </option>
				<option value="110011">���ݸɲ�����֤             </option>
				<option value="110012">�غ����ݸɲ�����֤         </option>
				<option value="110013">��������֤                 </option>
				<option value="110014">�غž�������֤             </option>
				<option value="110015">��ְ�ɲ�����֤             </option>
				<option value="110016">�غ���ְ�ɲ�����֤         </option>
				<option value="110017">����ԺУѧԱ֤             </option>
				<option value="110018">�غž���ԺУѧԱ֤         </option>
				<option value="110019">�۰ľ��������ڵ�ͨ��֤     </option>
				<option value="110020">�غŸ۰ľ��������ڵ�ͨ��֤ </option>
				<option value="110021">̨�����������½ͨ��֤     </option>
				<option value="110022">�غ�̨�����������½ͨ��֤ </option>
				<option value="110023">�л����񹲺͹�����         </option>
				<option value="110024">�غ��л����񹲺͹�����     </option>
				<option value="110025">�������                   </option>
				<option value="110026">�غ��������               </option>
				<option value="110027">����֤                     </option>
				<option value="110028">�غž���֤                 </option>
				<option value="110029">��ְ�ɲ�֤                 </option>
				<option value="110030">�غ���ְ�ɲ�֤             </option>
				<option value="110031">����֤                     </option>
				<option value="110032">�غž���֤                 </option>
				<option value="110033">����ʿ��֤                 </option>
				<option value="110034">�غž���ʿ��֤             </option>
				<option value="110035">�侯ʿ��֤                 </option>
				<option value="110036">�غ��侯ʿ��֤             </option>
				<option value="119998">ϵͳʹ�õĸ���֤��ʶ���ʶ </option>
				<option value="119999">��������֤��ʶ���ʶ       </option>
			</select>
		</TD>
		<TD class=title>Ͷ����֤������</TD>
		<TD class=input><Input class=input name="APPLIDCODE"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ����֤����Ч����</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="APPLBEGINDATE"></TD>
		<TD class=title>Ͷ����֤����ֹ����</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="APPLINVALIDDATE"></TD>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ��������</TD>
		<TD class=input><Input class=input name="APPLNAME"></TD>
		<TD class=title>Ͷ�����Ա�</td>
		<TD class=input>
			<select name="APPLSEX" style="background-color: #D7E1F6">
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
			</select>
		</TD>
	</TR> 
	<TR class=common>
		<TD class=title>Ͷ���˳�������</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="APPLBIRTHDAY"></TD>
		<TD class=title>Ͷ���˹���</TD>
		<TD class=input>
			<select name="APPLCOUNTRY" style="background-color: #D7E1F6">
				<option value="156">�й�</option>
				<option value="344">�й����</option>
				<option value="158">�й�̨��</option>
				<option value="446">�й�����</option>
			</select>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ����ͨѶ��ַ</TD>
		<TD class=input><Input class=input name="APPLADDRESS"/></TD>
		<TD class=title>ʡ(ֱϽ��)</TD>
		<TD class=input><Input class=input name="APPLPROV"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ������������</TD>
		<TD class=input><Input class=input name="APPLZIPCODE"/></TD>
		<TD class=title>Ͷ���˵�������</TD>
		<TD class=input><Input class=input name="APPLEMAIL"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ���˹̶��绰</TD>
		<TD class=input><Input class=input name="APPLPHONE"/></TD>
		<TD class=title>Ͷ�����ƶ��绰</TD>
		<TD class=input><Input class=input name="APPLMOBILE"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ����������</TD>
		<TD class=input><Input class=input name="APPLANNUALINCOME"/>Ԫ</TD>
		<TD class=title>Ͷ���˾�������</TD>
		<TD class=input><select name="PbDenType" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0">����</option>
				<option value="1">ũ��</option>
			</select></TD>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ����ְҵ����</TD>
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
	</TR>
	<TR class=common>
		<TD class=title>Ͷ�����뱻�����˹�ϵ</TD>
		<TD class=input>
<!-- 	<select name="APPLRELATOINSURED" style="background-color: #D7E1F6" onchange="WAppFlag(this.options[this.options.selectedIndex].value)"> -->		
	 	<select name="APPLRELATOINSURED" style="background-color: #D7E1F6" onchange="setFlag();"> 
				<option value="00"> ����</option>
				<option value="02"> �ɷ�</option>
				<option value="02"> ����</option>
				<option value="01"> ����</option>
				<option value="01"> ĸ��</option>	
				<option value="03"> ����</option>	 
				<option value="03"> Ů��</option>
				<option value="08"> �游</option>
				<option value="09"> ��ĸ</option>
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
		</TD>
	</TR>
</table>



<%--*************************��������Ϣ************************--%>
<table class=common align=center>

	<TR>
		<td class=titleImg align=center>����������Ϣ��</td>
		<td colspan="2"><input type="checkbox" name=InsFlag value="0" onClick="setInsFlag(this);">
		<font color="red">�������Ƿ�Ϊ����</font></td>
	</TR>
	
	<TR class=common>
		<TD class=title>������֤������</TD>
		<TD class=input>
			<select name="INSUIDKIND" style="background-color: #D7E1F6"  id="Ins1">
				<option value="110001">�������֤                 </option>
				<option value="110002">�غž������֤             </option>
				<option value="110003">��ʱ�������֤             </option>
				<option value="110004">�غ���ʱ�������֤         </option>
				<option value="110005">���ڲ�                     </option>
				<option value="110006">�غŻ��ڲ�                 </option>
				<option value="110011">���ݸɲ�����֤             </option>
				<option value="110012">�غ����ݸɲ�����֤         </option>
				<option value="110013">��������֤                 </option>
				<option value="110014">�غž�������֤             </option>
				<option value="110015">��ְ�ɲ�����֤             </option>
				<option value="110016">�غ���ְ�ɲ�����֤         </option>
				<option value="110017">����ԺУѧԱ֤             </option>
				<option value="110018">�غž���ԺУѧԱ֤         </option>
				<option value="110019">�۰ľ��������ڵ�ͨ��֤     </option>
				<option value="110020">�غŸ۰ľ��������ڵ�ͨ��֤ </option>
				<option value="110021">̨�����������½ͨ��֤     </option>
				<option value="110022">�غ�̨�����������½ͨ��֤ </option>
				<option value="110023">�л����񹲺͹�����         </option>
				<option value="110024">�غ��л����񹲺͹�����     </option>
				<option value="110025">�������                   </option>
				<option value="110026">�غ��������               </option>
				<option value="110027">����֤                     </option>
				<option value="110028">�غž���֤                 </option>
				<option value="110029">��ְ�ɲ�֤                 </option>
				<option value="110030">�غ���ְ�ɲ�֤             </option>
				<option value="110031">����֤                     </option>
				<option value="110032">�غž���֤                 </option>
				<option value="110033">����ʿ��֤                 </option>
				<option value="110034">�غž���ʿ��֤             </option>
				<option value="110035">�侯ʿ��֤                 </option>
				<option value="110036">�غ��侯ʿ��֤             </option>
				<option value="119998">ϵͳʹ�õĸ���֤��ʶ���ʶ </option>
				<option value="119999">��������֤��ʶ���ʶ       </option>
			</select>
		</TD>
		<TD class=title>������֤������</TD>
		<TD class=input><Input class=input name="INSUIDCODE" id="Ins2"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>������֤����Ч����</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="INSUBEGINDATE" id="Ins3"></TD>
		<TD class=title>������֤����ֹ����</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="INSUINVALIDDATE" id="Ins4"></TD>
	</TR>
	<TR class=common>
		<TD class=title>����������</TD>
		<TD class=input><Input class=input name="INSUNAME" id="Ins5"></TD>
		<TD class=title>�������Ա�</td>
		<TD class=input>
			<select name="INSUSEX" style="background-color: #D7E1F6" id="Ins6">
			    <option value="0"> ����</option>
				<option value="1"> Ů��</option>
			</select>
		</TD>
	</TR> 
	<TR class=common>
		<TD class=title>�����˳�������</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="INSUBIRTHDAY" id="Ins7"/></TD>
		<TD class=title>�����˹���</TD>
		<TD class=input>
			<select name="INSUCOUNTRY" style="background-color: #D7E1F6" id="Ins8">
				<option value="156">�й�</option>
				<option value="344">�й����</option>
				<option value="158">�й�̨��</option>
				<option value="446">�й�����</option>
			</select>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>������ͨѶ��ַ</TD>
		<TD class=input><Input class=input name="INSUADDRESS" id="Ins9"/></TD>
		<TD class=title>ʡ(ֱϽ��)</TD>
		<TD class=input><Input class=input name="INSUPROV" id="Ins10"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>��������������</TD>
		<TD class=input><Input class=input name="INSUZIPCODE" id="Ins11"/></TD>
		<TD class=title>�����˵�������</TD>
		<TD class=input><Input class=input name="INSUEMAIL" id="Ins12"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>�����˹̶��绰</TD>
		<TD class=input><Input class=input name="INSUPHONE" id="Ins13"/></TD>
		<TD class=title>�������ƶ��绰</TD>
		<TD class=input><Input class=input name="INSUMOBILE" id="Ins14"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>������ְҵ����</TD>
		<TD class=input>
		<select name="INSUJOBCODE" id="Ins16" style="background-color: #D7E1F6">  
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
	</TR>
	<TR class=common>
		<TD class=title>������������</TD>
		<TD class=input><Input class=input name="INSUANNUALINCOME" id="Ins17"/>��</TD>
		<TD class=title>�����˸�֪</TD>
		<TD class=input>
			<select name="INSUNOTICE" style="background-color: #D7E1F6" id="Ins18">
				<option value="1">��</option>
				<option value="0">��</option>
			</select>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>�������Ƿ�Σ��ְҵ</TD>
		<TD class=input>
			<select name="INSUISRISKJOB" style="background-color: #D7E1F6" id="Ins19">
				<option value="1">��</option>
				<option value="0">��</option>
			</select>
		</TD>
		<TD class=title>�����˽�����֪</TD>
		<TD class=input>
			<select name="INSUHEALTHNOTICE" style="background-color: #D7E1F6" id="Ins20">
				<option value="1">��</option>
				<option value="0">��</option>
			</select>
		</TD>
	</TR>
</table>

<%--*************************��������Ϣ************************--%>
<table class=common align=center> 
	<TR> 
		<td class=titleImg align=right>����������Ϣ��</td>
		<td colspan="2"><input type="checkbox" name=BnfFlag  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator>
		<font color="red">�������Ƿ�Ϊ����(ѡ��Ϊ����)</font></td>
	</TR>    
	<TR >  
		<td>�뱻���˹�ϵ</td>
		<td>���������</td>
		<td>����</td>
		<td>�Ա�</td>
		<td>֤������</td>
		<td>֤������</td>
		<td>�����˳�������</td>
		<td>�������</td>
		<td>����˳��</td>
		<td>֤����Ч��</td>
	</TR>
						
<%--*************������1*************--%>
<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);" > 
			    <option value="00"> ����</option>
				<option value="02"> �ɷ�</option>
				<option value="02"> ����</option>
				<option value="01"> ����</option>
				<option value="01"> ĸ��</option>
				<option value="03"> ����</option>
				<option value="03"> Ů��</option>
				<option value="08"> �游</option>
				<option value="09"> ��ĸ</option>
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
			<select name="BNFTYPE1" id="BnfReadOnly12" style="background-color: #D7E1F6">
				  <option value=""></option>
			    <option value="0">����������</option>
				<option value="1"> ���������</option>
			</select>
		<td><input name="BNFNAME1" type="text" id="BnfReadOnly13" class=common  size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEX1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value="0">��</option>
				<option value="1">Ů</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BNFIDKIND1" id="BnfReadOnly15" style="background-color: #D7E1F6">
				<option value=""></option>
			  <option value="110001">�������֤                </option>
				<option value="110005">�غž������֤            </option>
				<option value="110013">��ʱ�������֤            </option>
				<option value="110017">�غ���ʱ�������֤        </option>
				<option value="110019">���ڲ�                    </option>
				<option value="110023">�غŻ��ڲ�                </option>
				<option value="110025">���ݸɲ�����֤            </option>
				<option value="110029">�غ����ݸɲ�����֤        </option>
				<option value="110035">��������֤                </option>
				<option value="110001">�غž�������֤            </option>
				<option value="110005">��ְ�ɲ�����֤            </option>
				<option value="110013">�غ���ְ�ɲ�����֤        </option>
				<option value="110017">����ԺУѧԱ֤            </option>
				<option value="110019">�غž���ԺУѧԱ֤        </option>
				<option value="110023">�۰ľ��������ڵ�ͨ��֤    </option>
				<option value="110025">�غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110029">̨�����������½ͨ��֤    </option>
				<option value="110035">�غ�̨�����������½ͨ��֤</option>
				<option value="110001">�л����񹲺͹�����        </option>
				<option value="110005">�غ��л����񹲺͹�����    </option>
				<option value="110013">�������                  </option>
				<option value="110017">�غ��������              </option>
				<option value="110019">����֤                    </option>
				<option value="110023">�غž���֤                </option>
				<option value="110025">��ְ�ɲ�֤                </option>
				<option value="110029">�غ���ְ�ɲ�֤            </option>
				<option value="110035">����֤                    </option>
				<option value="110001">�غž���֤                </option>
				<option value="110005">����ʿ��֤                </option>
				<option value="110013">�غž���ʿ��֤            </option>
				<option value="110017">�侯ʿ��֤                </option>
				<option value="110019">�غ��侯ʿ��֤            </option>
				<option value="110023">ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="110025">��������֤��ʶ���ʶ      </option>
			</select></td>
		<td><input name="BNFIDCODE1" type="text" id="BnfReadOnly16" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BNFBIRTHDAY1" id="BnfReadOnly18"></TD>
		<td><input name="BNFPROP1" type="text"  id="BnfReadOnly17" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEQUENCE1"  id="BnfReadOnly18" style="background-color: #D7E1F6">
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
			<td><input name="BnfValidYear1" type="text" id="BnfReadOnly19"/></td>
	</TR>
				
<%--*************������2*************--%>
	<TR class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured2" id="BnfReadOnly20" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="00"> ����</option>
				<option value="02"> �ɷ�</option>
				<option value="02"> ����</option>
				<option value="01"> ����</option>
				<option value="01"> ĸ��</option>
				<option value="03"> ����</option>
				<option value="03"> Ů��</option>
				<option value="08"> �游</option>
				<option value="09"> ��ĸ</option>
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
		<td bgColor="#F7F7F7" >
			<select name="BNFTYPE2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
				<option value=""></option>
				<option value="1">���������</option> 
				<option value="0">����������</option>
			</select></td>
		<td><input name="BNFNAME2" type="text" id="BnfReadOnly22" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEX2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0">��</option>
				<option value="1">Ů	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BNFIDKIND2" id="BnfReadOnly24" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="110001">�������֤                </option>
				<option value="110005">�غž������֤            </option>
				<option value="110013">��ʱ�������֤            </option>
				<option value="110017">�غ���ʱ�������֤        </option>
				<option value="110019">���ڲ�                    </option>
				<option value="110023">�غŻ��ڲ�                </option>
				<option value="110025">���ݸɲ�����֤            </option>
				<option value="110029">�غ����ݸɲ�����֤        </option>
				<option value="110035">��������֤                </option>
				<option value="110001">�غž�������֤            </option>
				<option value="110005">��ְ�ɲ�����֤            </option>
				<option value="110013">�غ���ְ�ɲ�����֤        </option>
				<option value="110017">����ԺУѧԱ֤            </option>
				<option value="110019">�غž���ԺУѧԱ֤        </option>
				<option value="110023">�۰ľ��������ڵ�ͨ��֤    </option>
				<option value="110025">�غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110029">̨�����������½ͨ��֤    </option>
				<option value="110035">�غ�̨�����������½ͨ��֤</option>
				<option value="110001">�л����񹲺͹�����        </option>
				<option value="110005">�غ��л����񹲺͹�����    </option>
				<option value="110013">�������                  </option>
				<option value="110017">�غ��������              </option>
				<option value="110019">����֤                    </option>
				<option value="110023">�غž���֤                </option>
				<option value="110025">��ְ�ɲ�֤                </option>
				<option value="110029">�غ���ְ�ɲ�֤            </option>
				<option value="110035">����֤                    </option>
				<option value="110001">�غž���֤                </option>
				<option value="110005">����ʿ��֤                </option>
				<option value="110013">�غž���ʿ��֤            </option>
				<option value="110017">�侯ʿ��֤                </option>
				<option value="110019">�غ��侯ʿ��֤            </option>
				<option value="110023">ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="110025">��������֤��ʶ���ʶ      </option>
			</select></td>
		<td><input name="BNFIDCODE2" type="text" id="BnfReadOnly25" class=common/></td>
		
		<td><Input class="coolDatePicker" dateFormat="short" name="BNFBIRTHDAY2" id="BnfReadOnly28" size="10"></TD>
		<td><input name="BNFPROP2" type="text"  id="BnfReadOnly26" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEQUENCE2"  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<td><input name="BnfValidYear2" type="text"/></td>
	</TR>
	
<%--*************������3*************--%>
	<TR class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured3" id="BnfReadOnly27" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="00"> ����</option>
				<option value="02"> �ɷ�</option>
				<option value="02"> ����</option>
				<option value="01"> ����</option>
				<option value="01"> ĸ��</option>
				<option value="03"> ����</option>
				<option value="03"> Ů��</option>
				<option value="08"> �游</option>
				<option value="09"> ��ĸ</option>
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
		<td bgColor="#F7F7F7" >
			<select name="BNFTYPE3" id="BnfReadOnly28" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
				<option value="1">���������</option> 
				<option value="0">����������</option>	   
			</select></td>
		<td><input name="BNFNAME3" type="text" id="BnfReadOnly29" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEX3" id="BnfReadOnly30" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0">��</option>
				<option value="1">Ů	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BNFIDKIND3" id="BnfReadOnly31" style="background-color: #D7E1F6">
				<option value=""></option>
			<option value="110001">�������֤                </option>
				<option value="110005">�غž������֤            </option>
				<option value="110013">��ʱ�������֤            </option>
				<option value="110017">�غ���ʱ�������֤        </option>
				<option value="110019">���ڲ�                    </option>
				<option value="110023">�غŻ��ڲ�                </option>
				<option value="110025">���ݸɲ�����֤            </option>
				<option value="110029">�غ����ݸɲ�����֤        </option>
				<option value="110035">��������֤                </option>
				<option value="110001">�غž�������֤            </option>
				<option value="110005">��ְ�ɲ�����֤            </option>
				<option value="110013">�غ���ְ�ɲ�����֤        </option>
				<option value="110017">����ԺУѧԱ֤            </option>
				<option value="110019">�غž���ԺУѧԱ֤        </option>
				<option value="110023">�۰ľ��������ڵ�ͨ��֤    </option>
				<option value="110025">�غŸ۰ľ��������ڵ�ͨ��֤</option>
				<option value="110029">̨�����������½ͨ��֤    </option>
				<option value="110035">�غ�̨�����������½ͨ��֤</option>
				<option value="110001">�л����񹲺͹�����        </option>
				<option value="110005">�غ��л����񹲺͹�����    </option>
				<option value="110013">�������                  </option>
				<option value="110017">�غ��������              </option>
				<option value="110019">����֤                    </option>
				<option value="110023">�غž���֤                </option>
				<option value="110025">��ְ�ɲ�֤                </option>
				<option value="110029">�غ���ְ�ɲ�֤            </option>
				<option value="110035">����֤                    </option>
				<option value="110001">�غž���֤                </option>
				<option value="110005">����ʿ��֤                </option>
				<option value="110013">�غž���ʿ��֤            </option>
				<option value="110017">�侯ʿ��֤                </option>
				<option value="110019">�غ��侯ʿ��֤            </option>
				<option value="110023">ϵͳʹ�õĸ���֤��ʶ���ʶ</option>
				<option value="110025">��������֤��ʶ���ʶ      </option>
			</select></td>
		<td><input name="BNFIDCODE3" type="text" id="BnfReadOnly32" class=common/></td>
		
		<td><Input class="coolDatePicker" dateFormat="short" name="BNFBIRTHDAY3" id="BnfReadOnly33" size="10"></TD>
		<td><input name="BNFPROP3" type="text"  id="BnfReadOnly34" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEQUENCE3"  id="BnfReadOnly35" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >��һ����˳��</option>
				<option value="2" >�ڶ�����˳��</option>
				<option value="3" >��������˳��</option>
			</select>					
		</td>
		<td><input name="BnfValidYear3" type="text"/></td>
	</TR>
</table>


<%--*************������Ϣ*************--%>
<table class=common align=center>

	<TR>
		<td class=titleImg align=center>��������Ϣ��</td>
	</TR> 
 
	<TR >
		<td>���մ���</td>
		<td><div>����</div></td>
		<td><div>����(Ԫ)</div></td>
		<td>����(Ԫ)</td>
		<td>�ɷѷ�ʽ</td>
		<td>������ȡ��ʽ</td>
		<td>�������������־</td>
		<td>������������</td>
		<td>�ɷ����������־</td>
		<td>�ɷ���������</td> 
	</TR>
 
	<TR class=common2>
		<td bgColor="#F7F7F7">
            <select name="RISKSRISKCODE"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)"> 
            	  <option value="231204">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
                <option value="231202">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="231203">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
                <option value="221201">�к����ݻ�����ȫ����A��</option>
                <option value="231302">�к�������������գ��ֺ��ͣ�</option>
                <option value="211902">�к���Ӯ����������˺�����A��</option>
                 <option value="241201">�к���Ӯ�Ƹ���ȫ���գ������ͣ�A��</option>
                <option value="221206">�к���Խ�Ƹ���ȫ����</option>
            </select> 
        	<input  name="RiskCodeName" readonly="readonly"  type="hidden" size="33"/>
         </td>
		<td><input name="RISKSSHARE"    size="3"/></td>
		<td bgColor="#F7F7F7"><input name="RISKSPREM"   size="10"/></td>
		<td bgColor="#F7F7F7"><input name="RISKSAMNT" type="text"  size="10"/></td>
		<TD><select name="PayType" class="common1">
			         <option value=""></option>
			         <option value="1"> ���� </option>
               <option value="2"> �½� </option>
               <option value="3"> ���� </option>
               <option value="4"> ���꽻 </option>
               <option value="5"> �꽻 </option>
               <option value="6"> ���� </option>
               <option value="7"> ������ </option>
               <option value="0"> ������ </option>
	    </select></TD>
		<TD><select name="BonusGetMode" class="common1">
			   <option value=""></option>
			         <option value="0"> ֱ�Ӹ��� </option>
			         <option value="1"> �ֽ����� </option>
               <option value="2"> �ۻ���Ϣ </option>
               <option value="3"> ����� </option>
	    </select></TD>
		<td> 
			<select name="RISKSINSUDUETYPE" style="background-color: #D7E1F6">
                <option value="2"> �±�</option> 
                <option value="4"> �걣</option>
                <option value="5"> ����ĳȷ������</option>
                <option value="6"> ������</option>
             </select></td>
		<td><input name="RISKSINSUDUEDATE" type="text"  size="3" maxlength="5"/></td>
		
		 <td>
			<select name="RISKSPAYDUETYPE" style="background-color: #D7E1F6">
				<option value="0"> ���� </option>
				<option value="2"> �½� </option>
                <option value="4"> ��� </option>
                 <option value="1"> ����ĳȷ������ </option>
                <option value="5"> ���� </option>
             </select></td>
		<td><input name="RISKSPAYDUEDATE" type="text"  size="5" maxlength="5"  /></td> 
	</TR>		
</table>

<%--*************��������Ϣ*************--%>
<table class=common align=center>

	<TR>
		<td class=titleImg align=center>����������Ϣ��</td>
	</TR> 
 
	<TR >
		<td>�����մ���</td>
		<td><div>����</div></td>
		<td><div>����(Ԫ)</div></td>
		<td>����(Ԫ)</td>
		<td>�ɷѷ�ʽ</td>
		<td>������ȡ��ʽ</td>
		<td>�������������־</td>
		<td>������������</td>
		<td>�ɷ����������־</td>
		<td>�ɷ���������</td> 
	</TR>
 
	<TR class=common2>
		<td bgColor="#F7F7F7">
            <select name="ADDTRISKCODE1"  style="background-color: #D7E1F6" >          	
                		<option value=""></option>
                <option value="145201">�к����Ӷ�ӯ����ȫ���գ������ͣ�</option>
            </select> 
        	<input  name="RiskCodeName" readonly="readonly"  type="hidden" size="33"/>
         </td>
		<td><input name="ADDTSHARE1"    size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ADDTPREM1"   size="10"/></td>
		<td bgColor="#F7F7F7"><input name="ADDTAMNT1" type="text"  size="10"/></td>
		<TD><select name="ADDTPAYTYPE1" class="common1">
			         <option value=""></option>
			         <option value="1"> ���� </option>
               <option value="2"> �½� </option>
               <option value="3"> ���� </option>
               <option value="4"> ���꽻 </option>
               <option value="5"> �꽻 </option>
               <option value="6"> ���� </option>
               <option value="7"> ������ </option>
               <option value="0"> ������ </option>
	    </select></TD>
		<TD>
		<select name="BonusGetMode" class="common1">
			   <option value=""></option>
			         <option value="0"> ֱ�Ӹ��� </option>
			         <option value="1"> �ֽ����� </option>
               <option value="2"> �ۻ���Ϣ </option>
               <option value="3"> ����� </option>
	    </select>
	    </TD>
		<td> 
			<select name="ADDTINSUDUETYPE1" style="background-color: #D7E1F6">
                <option value="2"> �±�</option> 
                <option value="4"> �걣</option>
                <option value="5"> ����ĳȷ������</option>
                <option value="6"> ������</option>
             </select></td>
		<td><input name="ADDTINSUDUEDATE1" type="text"  size="3" maxlength="5"/></td>
		
		 <td>
			<select name="ADDTPAYDUETYPE1" style="background-color: #D7E1F6">
				<option value="0"> ���� </option>
				<option value="2"> �½� </option>
                <option value="4"> ��� </option>
                 <option value="1"> ����ĳȷ������ </option>
                <option value="5"> ���� </option>
             </select></td>
		<td><input name="ADDTPAYDUEDATE1" type="text"  size="5" maxlength="5"  /></td> 
	</TR>	
</table>



<%--*************������Ϣ*************--%>
<div id="C20001" style="display:'none'">
<table class=common align=center>
<TR>
		<td class=titleImg align=center>��������Ϣ��</td>
	</TR> 
<TR class=common>
		<td class=title>�����������</td>
		<TD class=input><Input class=input name="LoanCom" maxlength="25"></TD>
		<td class=title>�����ͬ��</td>
		<TD class=input><Input class=input name="LoanContractNo" maxlength="25"></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>������ʼ���� </td>
		<TD class=input><Input class=coolDatePicker name="LoanStartDate"></TD>
		<td class=title>������ֹ����</td>
		<TD class=input><Input class=coolDatePicker name="LoanEndDate" ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>������</td>
		<TD class=input><Input class=input name="LoanContractAmt" maxlength="25">��</TD>
		<td class=title>��������</td>
		<TD class=input>
			<select name="LoanType" style="background-color: #D7E1F6">
				<option value="00"> һ����ҵ���� </option>
				<option value="01"> �����ҵ���� </option>
                <option value="10"> ��������ϴ��� </option>
                 <option value="11"> ����������� </option>
                <option value="20"> ��Ϣ��ѧ���� </option>
             </select></TD>
	</TR>
	<TR class=common>
		<TD class=title>������ʼ����</TD>
			 <TD class=input><Input class="coolDatePicker" dateFormat = "short" name="RISKSBEGINDATE" /></TD>			
		<TD class=title>���ս�������</TD>
			 <TD class=input><Input class="coolDatePicker" dateFormat = "short" name="RISKSENDDATE" /></TD>	
	</TR>
</table>
</div>
<%--*************ҳ���趨*************--%>				
<table class=common align=center>				
					
	<TR> 
		<td><input class="cssButton" type=Button onClick="initBox()" name="Submit3" value=" �����Ϣ " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" �� �� �� �� " onClick="initInputBox()" /></td>
	</TR>

</table>

 <table class=common align=center>
	<tr>
		<td class=titleImg align=center><br>������ѡ�</td>
	</tr> 
	
	<TR>
		<td class=title>������</td>
		
		<TD class=input><select name="TRANSCODE" style="background-color: #D7E1F6"  onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option value="1002">��������</option>
			    <option value="1004">�����б�</option>
			</select></TD>
		<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name="ip"></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name="port"></td>
		
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button 
		value="����Ͷ������" onclick="submitForm();"></TD> 
	</TR>
</table>
<hr/>  
<table class=common align=center>
	<TR>������Ϣ</TR>  
	<TR>
		<td>
			<textarea rows="30" cols="100" name="xmlContent" id="xmlContent">
			
			</textarea>
		</td>
	</TR>  
</table>
<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'></form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
