
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%@page contentType="text/html;charset=GBK"%>
	<head>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<SCRIPT src="./policyPrem.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="policyPremInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="" method=post name=fm
			target="fraSubmit">
			<table>
				<tr class=common>
					<td class=titleImg>
						��ѯ����
					</td>
				</tr>
			</table>
<hr>
			<Div id="divYBT" style="display: ''">
				<table class=common >
				<TR>
					<td class=title>
						�����ţ�</TD>
							<td class=input>
						<input class="input" name="PolicyNo" />
					</td>
						<TD class="title">
							�������ͣ�
							</TD>
							<td class=input>
							<input class="codeno" name="PremTypeCode"
								ondblclick="return showPremTypeCode()"
								onkeyup="return showPremTypeCodeKey()" />
							<input class="codename" name="PremTypeName" readonly="readonly"
								/>
						</td>
					</TR>
					<TR>
					<TD class="title">
							 ��Ʒ���ͣ�</TD>
							<td class=input>
							<input class="codeno" name="ProductType"
								ondblclick="return showProTypeCode()"
								onkeyup="return showProTypeCodeKey()"  />
							<input class="codename" name="ProductName" readonly="readonly" />
						</td>					
						<td class=title>
							�������ڣ�</TD>
							<td class=input>
							<input class="coolDatePicker" dateFormat="short" name="TranDay"
								 />
							&nbsp;
						</td>
					</tr>
				</table>
				<input class="cssbutton" type="button" value="��&nbsp;&nbsp;&nbsp;&nbsp;ѯ"
								onclick="creatClick();" />
				<input class="cssbutton" type="button"
				value="��&nbsp;&nbsp;&nbsp;&nbsp; ��" onclick="resetItems();" />
			</Div>
			<Div id="divGrid" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanQueryGrid"> </span>
						</td>
					</tr>
				</table>
			</Div>
			<CENTER>
					<INPUT VALUE="��  ҳ" TYPE=button
						onclick="turnPage.firstPage();" class="cssButton">
					<INPUT VALUE="��һҳ" TYPE=button
						onclick="turnPage.previousPage();"
						class="cssButton">
					<INPUT VALUE="��һҳ" TYPE=button
						onclick="turnPage.nextPage();" class="cssButton">
					<INPUT VALUE="β  ҳ" TYPE=button
						onclick="turnPage.lastPage();" class="cssButton">
				</CENTER>
			<span id="spancode" style="display: none; position: absolute;"></span>
		</form>
	</body>
</html>
