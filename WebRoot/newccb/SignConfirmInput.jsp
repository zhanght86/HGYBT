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
<SCRIPT src="QueryRenewalFee.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="SignConfirmInit.jsp"%>  

		<title>����ͨ����</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./SignConfirmSave.jsp" method=post name=fm target="fraSubmit">


<table class=common align=center>
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	
	<TR>
		<td class=title>������</td>
		
		<TD class=input><select name="TRANSCODE" style="background-color: #D7E1F6">
			    <option value="P538191B3">ȷ�ϵǼ�̨��</option>
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


<%--*************************������Ϣ*************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>��������Ϣ��</td>
	</tr> 
	<TR class=common>
		<TD class=title>��������</TD> 
		 <TD class=input><Input class="codeno" name=TranCom ondblclick="return showTranCom()"
							onkeyup="return showTranCom()">
							<input  name="TranComName" readonly="readonly" type="text"  size="20"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>������ˮ��</TD>
		<TD class=input><Input class=input name="mSvPt_Jrnl_No"/></TD>
		<TD class=title>����ʱ��</TD>
		<TD class=title><Input class=input name="mSYS_REQ_TIME"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>����Ա�����</TD>
		<TD class=input><Input class=input name="mCCB_EmpID"/></TD>
		<TD class=title>�����</TD>
		<TD class=input><Input class=input name="mCCBIns_ID"/></TD>
	</TR>
	<TR class=common>	
		<TD class=title>���չ�˾��ˮ��</TD>
		<TD class=input><Input class=input name="Ins_Co_Jrnl_No"/></TD>
		<TD class=title>�������ʹ���</TD>
		<TD class=input><Input class=input name="Chnl_TpCd"/></TD>
	</TR>
	
	
	<TR class=common>	
		<TD class=title>���л�������</TD>
		<TD class=input><Input class=input name="CCBIns_Nm"/></TD>
		<TD class=title>���л������</TD>
		<TD class=input><Input class=input name="CCBIns_ID"/></TD>
	</TR>
	
	<TR class=common>	
		<TD class=title>��ϵ�绰</TD>
		<TD class=input><Input class=input name="Ctc_Tel"/></TD>
		<TD class=title>��һ����������</TD>
		<TD class=input><Input class=input name="Fst_Benf_Nm"/></TD>
	</TR>
		<TR class=common>	
		<TD class=title>������</TD>
		<TD class=input><Input class=input name="HdCg"/></TD>
		<TD class=title>�ͻ������������л������</TD>
		<TD class=input><Input class=input name="CstMgr_Blng_CCBIns_ID"/></TD>
	</TR>
</table>

		
<%--*************ҳ���趨*************--%>				
<table class=common align=center>				
					
	<TR> 
		<td><input class="cssButton" type=Button onClick="initBox()" name="Submit3" value=" �����Ϣ " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" �� �� �� �� " onClick="initInputBox()" /></td>
	</TR>

</table>

 
<hr/>  
<table class=common align=center>
	<TR><TD>������Ϣ</TD></TR>  
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
