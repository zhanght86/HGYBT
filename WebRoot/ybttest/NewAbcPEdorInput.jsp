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
<SCRIPT src="NewAbcPEdor.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="NewAbcPEdorInit.jsp"%>  

		<title>����ͨ����</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./NewAbcPEdorSave.jsp" method=post name=fm target="fraSubmit">


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
		<TD class=title>���ж˽�������</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat = "short" name="TRANSDATE" /></TD>
	</TR>
	<TR class=common>
		<TD class=title>���н�����ˮ��</TD>
		<TD class=input><Input class=input name="SERIALNO"/></TD>
		<TD class=title>��Ա</TD>
		<TD class=input><Input class=input name="TLID"/></TD>
		
	</TR>
	<TR class=common>
		<TD class=title>��������</TD>
		<TD class=input><Input class=input name="PROVCODE"/></TD>
		<TD class=title>�������</TD>
		<TD class=input><Input class=input name="BRANCHNO"/></TD>
		
	</TR>	
	<TR>
		
	</TR>
	<TR class=common>
		<TD class=title>������</TD>
		<TD class=input><Input class=input name="PolicyNo"/></TD>
		<TD class=title>����ӡˢ��</TD>
		<TD class=input><Input class=input name="PrintCode"/></TD>
		
	</TR>
	
	<TR class=common>
		<TD class=title>����������</TD>
		<TD class=input><Input class=input name="ClientName"/></TD>
		<TD class=title>���ִ���</TD>
		<td class="input">
            <select name="Riskcode"  style="background-color: #D7E1F6" > 
                <option value="211902">�к���Ӯ����������˺�����A��</option>
            	  <option value="231204">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C��</option>
            	  <option value="231201">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A��</option>
                <option value="231202">�к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B��</option>
                <option value="231203">�к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ�</option>
                <option value="211901">�к���Ӯ����������˺�����</option>
                <option value="221201">�к����ݻ�����ȫ����A��</option>
            </select> 
		</td>
	</TR>
	<TR class=common>
		<TD class=title>Ͷ����֤������</TD>
		<TD class=input>
			<select name="IdKind" style="background-color: #D7E1F6" >
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
		<TD class=input><Input class=input name="IdCode"/></TD>
	</TR>
	
	
	<TR class=common>
		<TD class=title>���������</TD>
		<TD class=input><Input class=input name="PayeetName"/></TD>
		<TD class=title>����˿���</TD>
		<TD class=input><Input class=input name="PayAcc"/></TD>
		
	</TR>
	<TR class=common>
		<TD class=title>�����֤������</TD>
		<TD class=input>
			<select name="PayeeIdKind" style="background-color: #D7E1F6" >
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
		<TD class=title>�����֤������</TD>
		<TD class=input><Input class=input name="PayeeIdCode"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>����</TD>
		<TD class=input><Input class=input name="Amt"/></TD>
		<TD class=title>ҵ������</TD>
		<TD class=input>
			<select name="BusiType" style="background-color: #D7E1F6" >
				<option value="01">�̳�</option>
				<option value="02">���ڸ���</option>
				<option value="03">�˱�</option>
			</select>
		</TD>
		
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
	<tr>
		<td class=titleImg align=center>������ѡ�</td>
	</tr> 
	
	<TR>
		<td class=title>������</td>
		
		<TD class=input><select name="TRANSCODE" style="background-color: #D7E1F6">
			    <option value="1012">��ȫ��ѯ</option>
			    <option value="1013">��ȫ����</option>
			    <option value="1014">��ȫ����״̬��ѯ</option>
			</select></TD>
		<td class=title>���ձ���ip��ַ</td>
			<td class=input><Input class=input name="ip"></td> 
		<td class=title>�˿�</td>
		<td class=input><Input class=input name="port"></td>
		
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button 
		value="���ͽ�������" onclick="submitForm();"></TD> 
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
