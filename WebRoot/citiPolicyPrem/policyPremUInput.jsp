
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
		<SCRIPT src="policyPremU.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="policyPremUInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./policyPremUSave.jsp" method=post name=fm
			target="fraSubmit">
			<table>
				<tr class=common>
					<td class=titleImg>
						保单保费调整
					</td>
				</tr>
			</table>
			<hr>
				<table class=common >
					<TR>
						<td class=title>
						保单号：</TD>
							<td class=input>
						<input class="input" name="PolicyNo" elementtype="nacessary"/>
					</td>
					<td class="title">
						费用金额：</TD>
							<td class=input>
						<input class="title" name="CostAmount" elementtype="nacessary"/>
					</td>
					</TR>
					<tr>
					<TD class="title">
							保费类型：</TD>
							<td class=input>
							<input class="codeno" name="PremType"
								ondblclick="return showPremTypeCode()"
								onkeyup="return showPremTypeCodeKey()" />
							<input class="codename" name="PremName" readonly="readonly"
								elementtype="nacessary" />
					</td>
					<td class="title">
						保单年度：</TD>
							<td class=input>
						<input class="title" name="PolicyYear" elementtype="nacessary"/>
					</td>
					</tr>
					<tr>
						<TD class="title">
							 产品类型：</TD>
							<td class=input>
							<input class="codeno" name="ProductType"
								ondblclick="return showProTypeCode()"
								onkeyup="return showProTypeCodeKey()"  />
							<input class="codename" name="ProductName" readonly="readonly"
								elementtype="nacessary" />
						</td>
						<td class=title>
							交易日期：</TD>
							<td class=input>
							<input class="coolDatePicker" dateFormat="short" name="TranDay"
								 style="width: 152px;" elementtype="nacessary"/>
							&nbsp;
						</td>
					</tr>
					<TR>
						<td style="width: 113px;">
							<input class="cssbutton" type="button" value="确定"
								onclick="creatClick();" />
							<input class="cssbutton" type="button" value="重置"
								onclick="reset();" />
						</td>
					</TR>
				</table>

			<span id="spancode" style="display: none; position: absolute;"></span>
		</form>
	</body>
</html>
