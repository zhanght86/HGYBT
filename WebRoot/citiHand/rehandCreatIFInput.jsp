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
		<SCRIPT src="rehandCreatIF.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="rehandCreatIFInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./rehandCreatIFSave.jsp" method=post name=fm
			target="fraSubmit" ENCTYPE="multipart/form-data">
			<table>
				<tr class=common>
					<td class=titleImg>
						接口文件补生成
					</td>
				</tr>
			</table>
<hr>
			<Div id="divYBT" style="display: ''">
					<TR class=input>
						<TD class="title">
							&nbsp;接口文件类型：</td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;
						<td class="input">
							<input class="codeno" name="FileTypeCode"
								ondblclick="return showFileTypeCode()"
								onkeyup="return showFileTypeCodeKey()"  />
							<input class="codename" name="FileTypeName" readonly="readonly"
								 />
						</td>&ensp;
						&ensp;&ensp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<td class=title>
							保单号上传：</td>
					
					<TD class=title>
						<input type="hidden" name="ImportPath">
						<Input type="file" name="FileName" size=30 >
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="./RehandCreate_Upload.xls">模板下载
					<IMG src="../common/images/excel.gif" border="1"
						></IMG></a>
				</TD> 					
					</TR>
						<table class=common >
						<tr class=common>
					<td class=titleImg>
						补生成保单
					</td>
				</tr>
					<tr class=common>
						<td class=title>
							起始日期：</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="StartDay1"
								elementtype="nacessary" />
							&nbsp;
						</td>
						<td class="title">
							截止日期：</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="EndDay1"
								elementtype="nacessary"  />
						</td>
					</tr>
					<tr class=common>
					<td class=titleImg>
						导出数据
					</td>
				</tr>
					<tr class=common>
						<td class=title>
							起始日期：</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="StartDay2"
								elementtype="nacessary"  />
						</td>
						<td class="title">
							截止日期：</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="EndDay2"
								elementtype="nacessary"  />
						</td>
					</tr>
					</table>
					<table class=common>
					<TR class=common>
						<td class=common>
							<input class="cssbutton" type="button" value="生成接口文件"
								onclick="creatClick();" />
						</td>
					</TR>
				</table>
			</Div>
			<span id="spancode" style="display: none; position: absolute;"/>
		</form>
	</body>
</html>
