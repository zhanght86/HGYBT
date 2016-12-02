
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
		<SCRIPT src="handCreatIF.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="handCreatIFInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./handCreatIFSave.jsp" method=post name=fm
			target="fraSubmit">
			<table>
				<tr class=common>
					<td class=titleImg>
						手动生成接口文件
					</td>
				</tr>
			</table>
<hr>
			<Div id="divYBT" style="display: ''">
				<table class=common >
					<TR>
						<TD class="title">
							接口文件类型：</td>
						<td class="input">
							<input class="codeno" name="FileTypeCode"
								ondblclick="return showFileTypeCode()"
								onkeyup="return showFileTypeCodeKey()"  />
							<input class="codename" name="FileTypeName" readonly="readonly"
								 />
						</td>
					</TR>
					<tr>
						<td class=title>
							起始日期：</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="StartDay"
								elementtype="nacessary" />
							&nbsp;
						</td>
						<td class="title">
							截止日期：</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="EndDay"
								elementtype="nacessary"  />
						</td>
					</tr>
					<TR>
						<td style="width: 113px;">
							<input class="cssbutton" type="button" value="生成接口文件"
								onclick="creatClick();" />
						</td>
					</TR>
				</table>
			</Div>
			
			<span id="spancode" style="display: none; position: absolute;"></span>
		</form>
	</body>
</html>
