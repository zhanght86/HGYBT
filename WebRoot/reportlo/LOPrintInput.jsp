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
		<script src="../common/javascript/AXANodeMap.js">
</script>
		<%@include file="./LOPrintInit.jsp"%>
		<script src="LOPrint.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

<title>����ͨÿ��ʵʱ���ݵ�����</title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./LOPrintSave.jsp" method="post" name="fm"
			target="fraSubmit">
			<input type="hidden" name=UserAuthority>
			<input type="hidden" name=ZoneNo>
			<input type="hidden" name=ZoneName>
			<table class=common>
				<tr>
					<td class=titleImg align=center>
						����ͨÿ��ʵʱ���ݵ�����
					</td>
				</tr>
			</table>

			<HR>

			<table class="common">

				<!-- ************ ��һ�� *********** -->
				<TR class=common>
					<TD class=title>
						����������</td>
		                <TD class=input>
						<input class="codeno" name="TranCom"
							ondblclick="LOBankQuery()"
							onkeyup="LOBankQueryKey()">
						<input class="codename" name="TranComName" value="***��������***"
							readonly="readonly"/>
					</TD>		
					<!-- ************CodeSelect ����ѡ���*********** -->
					<input type="hidden" name="ManageCodeNo">
		               	<TD class=title>
							���������</TD>
						<td class=input >
							<Input class=input name=ManageCodeName verify="�������|notnull"
								readonly="readonly" >
							<input type=button class="cssButton" value="ѡ��"
								onClick="SelectCom();">
						</TD>
				
						<input class="codeno" name="AreaNo" type="hidden"
							ondblclick="return showAreaNo()"
							onkeyup="return showAreaNoKey()"
							id = "AreaNoId";
							>
						<input class="codename" name="AreaName" value="***��������***"
						type="hidden"	readonly="readonly"/>
			
					<!-- ************CodeSelect ����ѡ���*********** -->
					
				</TR>

				<!-- ************ �ڶ��� *********** -->
				<TR class=common>
						<input class="codeno" name="CityNo" type="hidden"
							ondblclick="return showCityNo();"
							onkeyup="return showCityNoKey();"
							id = "CityNoId";
							>
						<input class="codename" name="CityName" value="***���г���***"
						type="hidden"	readonly="readonly" id = "CityNameId";/>
					</TD>
					<TD class=title>
						����רԱ��</td>
		                <TD class=input>
					<input class="codeno" name="AgentCodeNo"
							ondblclick="return showAgentCodeNo();"
							ondblclick="return showAgentNoCodeKey();">
						<input class="codename" name="AgentCodeName" value="***����רԱ***"
							readonly="readonly" />
					</TD>
					<TD class=title>
						��&nbsp;&nbsp;&nbsp;&nbsp;�㣺</td>
		                <TD class=input>
						<input class="codeno" name="AgentComNo"
							ondblclick="return showAgentNo();"
							ondblclick="return showAgentNoKey();"/>
						<input class="codename" name="AgentComName" value="***��������***"
							readonly="readonly"/>
					</TD>
				</TR>

				<!-- ************ ������ *********** -->
				<TR class=common>
					
					<TD class=title>
						���ִ��룺</td>
		                <TD class=input>
						<input class="codeno" name="RiskCodeNo"
						ondblclick="ShowRiskCodeList();"
							onkeyup="ShowRiskCodeKey();">
						<input class="codename" name="RiskCodeName" value="***��������***"
							readonly="readonly" />
					</TD>
					<TD class=title>
						�������ڣ�</td>
		                <TD class=input>
						<input class="coolDatePicker" dateFormat="short" name="Day"/>
					</TD>
				</TR>


			</TABLE>
		

			<BR />
 
			<table> 
			<TR class=common>
				<TD width="26%">	<INPUT VALUE="Ԥ      ��" class="cssbutton" TYPE=button onclick="LOPrintPreviewQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="���ɱ���" class="cssbutton" TYPE=button onclick="LOPrintQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="��      ��" class="cssbutton" type="button"  onclick="initBox();"></TD>
				
			</TR>
		</table>
		<input type="hidden" name="PrintFlag">
		</form>
		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>