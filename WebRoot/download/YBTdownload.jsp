
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
		<SCRIPT src="YBTdownload.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="YBTdownloadInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./LAComQuerysave.jsp" method=post name=fm
			target="fraSubmit">
			<table>
				<tr class=common>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divYBT);"></IMG>
					</td>
					<td class=titleImg>
						查询条件
					</td>
				</tr>
			</table>

			<Div id="divYBT" style="display: ''">

				<table class=common>
					<TR>
						<td class=title>
							日期
						</td>
						<TD class=input>
							<Input class='coolDatePicker' name=FileDate
								verify="日期|DATE" dateFormat='short'>
						</TD>
					</TR>

				</table>
			</Div>
			<input type=hidden name=ACType value='01'>
			<INPUT VALUE="查  询" TYPE=button onclick="easyQueryClick();"
				class=cssbutton>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divYBTGrid);">
					</td>
					<td class=titleImg>
						查询结果
					</td>
				</tr>
			</table>
			<Div id="divYBTGrid" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanYBTGrid"> </span>
						</td>
					</tr>
				</table>
				<br>
				<CENTER>
					<INPUT VALUE="首  页" TYPE=button
						onclick="turnPage.firstPage();makeDownLink();" class="cssButton">
					<INPUT VALUE="上一页" TYPE=button
						onclick="turnPage.previousPage();makeDownLink();"
						class="cssButton">
					<INPUT VALUE="下一页" TYPE=button
						onclick="turnPage.nextPage();makeDownLink();" class="cssButton">
					<INPUT VALUE="尾  页" TYPE=button
						onclick="turnPage.lastPage();makeDownLink();" class="cssButton">
				</CENTER>
			</Div>
			<br>
			<table class="common" align=left>
			</table>
			<input type=hidden name=hideOperate value=''>
			<span id="spancode" style="display: none; position: absolute;"></span>
		</form>
	</body>
</html>
