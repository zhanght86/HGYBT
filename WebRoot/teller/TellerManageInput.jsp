<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />

		<script src="../common/javascript/Common.js">
</script>
		<script src="../common/cvar/CCodeOperate.js">
</script>
		<script src="../common/Calendar/Calendar.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryCache.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryVer3.js">
</script>
		<script src="../common/javascript/MulLine.js">
</script>
		<script src="../common/javascript/VerifyInput.js">
</script> 

		<%@include file="TellerManageInit.jsp"%> 
		<script src="TellerQuery.js">
</script>
		 

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />
 
		<title>��Ա����</title>
	</head>

	<body onload="initForm();initElementtype();">
		<form action="./TellerManageSave.jsp" method="post" name="fm"
			target="fraSubmit" >
			<input type="hidden" name="OperType">
			<input type="hidden" name="SRSTellerNo">
			<div id="divAgentCheck" style="display:''">
			<TABLE class="common">
				<TR>
					<TD class="titleImg" align="center">
						���й�Ա��
					</TD>
				</TR>
				<tr>
				<td class=title>
						��������</TD>
				   <TD class=input >
						<input class="codeno" name="TranCom" verify="���д���|NotNull"
							ondblclick="LOBankQuery(); "
							onkeyup="LOBankQueryKey(); ">
						<input class="codename" name="TranComName" readonly="true"/>
						</TD>
				
		               	<TD class=title>
							����������</TD>
							<TD class=input>
						<input class="codeno" name="ZoneNo" verify="��������|NotNull"
							ondblclick="LOZoneQuery(); "
							onkeyup="LOZoneQueryKey(); ">
						<input class="codename" name="ZoneName" readonly="true"/>
						</TD>			
					</tr>
					<tr>
					<td class=title>
						��Ա����</TD>
				   <TD class=input>
						<input class="input" name="TellerName" />
						</TD>
				   <TD class=title>
						���й�Ա��</TD>
				   <TD class=input>
						<input class="input" name="TellerIDNo" />
						</TD>
				</tr>							
				</TABLE> 
				</div>
				<div id="divAgentADD" style="display:'none'">
			<TABLE class="common">
				<TR>
					<TD class="titleImg" align="center">
						���й�Ա��
					</TD>
				</TR>
				<tr>
				<td class=title>
						��������</TD>
				   <TD class=input >
						<input class="codeno" name="iTranCom" verify="���д���|NotNull"
							ondblclick="iBankQuery(); "
							onkeyup="iBankQueryKey(); ">
						<input class="codename" name="iTranComName" readonly="true"/>
						</TD>
				
		               	<TD class=title>
							����������</TD>
							<TD class=input>
						<input class="codeno" name="iZoneNo" verify="��������|NotNull"
							ondblclick="iZoneQuery(); "
							onkeyup="iZoneQueryKey(); ">
						<input class="codename" name="iZoneName" readonly="true"/>
					</TD>			
				</tr>
				<tr>
					<td class=title>
						��Ա����</TD>
				   <TD class=input>
						<input class="input" name="iTellerName" />
						</TD>
				   <TD class=title>
						���й�Ա��</TD>
				   <TD class=input>
						<input class="input" name="iTellerIDNo" />
					</TD>
				</tr>
				<tr>
					<td class=title>
						�ʸ�֤����</TD>
				   <TD class=input>
						<input class="input" name="iQuType" />
						</TD>
				   <TD class=title>
						�ʸ�֤��</TD>
				   <TD class=input>
						<input class="input" name="iTellerQuNo" />
				   </TD>
				</tr>
				<tr>
					<td class=title>
						�ʸ�֤��Ч����</TD>
				   <TD class=input >
						<input class="coolDatePicker" dateFormat="short"   name="iStartDay"
							elementtype="nacessary" /></TD>
					<td class=title>�ʸ�֤ʧЧ����</TD>
						
				   <TD class=input >
						<input class="coolDatePicker" dateFormat="short"  name="iEndDay"
							elementtype="nacessary" />
					</td>
				</tr>
				</TABLE> 
				</div>
			
			<div id="divCmdButton" style="display:''">
			<input class="cssButton" value="��ѯ��Ա" type="button"
				onclick="MqueryItems();" />
				&nbsp;
				<input class="cssButton" value="���ӹ�Ա" type="button"
					onclick="insertClick();" />
				&nbsp;
				<input class="cssButton" value="�޸Ĺ�Ա" type="button"
					onclick="updateClick();" />
			&nbsp;
				<input class="cssButton" value="ɾ����Ա" type="button"
					onclick="delItem();" />
			&nbsp;
			<input class="cssButton" value="�� ��" type="button"
				onclick="resetInit();" />
				</div>
				<div id="divSubCmdButton" style="display: none">
			<INPUT VALUE="ȷ  ��" TYPE=button onclick="okClick()" class="cssButton">
			<INPUT VALUE="��  ��" TYPE=button onclick="cancelClick()" class="cssButton">
			</div>
			
			<div id="divGrid" style="display:''">
			<span id="spanQueryGrid"></span>
			</div>
			<div id="divPage" style="display:''">
			<center>
				<input class="cssbutton" value="��ҳ" type="Button"
					onclick="turnPage.firstPage();" />
				<input class="cssbutton" value="��һҳ" type="Button"
					onclick="turnPage.previousPage();" />
				<input class="cssbutton" value="��һҳ" type="Button"
					onclick="turnPage.nextPage();" />
				<input class="cssbutton" value="βҳ" type="Button"
					onclick="turnPage.lastPage();" />
			</center>
			</div>
		</form> 
		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>
