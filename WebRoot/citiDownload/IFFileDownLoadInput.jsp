
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
		<SCRIPT src="IFFileDown.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="IFFileDownLoadInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="" method=post name=fm
			target="fraSubmit">
		<table>
				<tr>
					<td class=titleImg>
						文件查询
					</td>
				</tr>
			</table>
			<hr>
		<table class=common>
		<TR>
						<TD class="title">
							接口文件类型：</td>
						<td class=input>
							<input class="codeno" name="FileTypeCode"
								ondblclick="return showFileTypeCode()"						
								onkeyup="return showFileTypeCodeKey()" />
							<input class="codename" name="FileTypeName" readonly="readonly" />	
						</td>
		</TR>
		<tr>
					<td class=title>
						起始日期：
						</td>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay"
							elementtype="nacessary" />
						&nbsp; 
					</td>
					<td class="title">
						终止日期：</td>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay"
							elementtype="nacessary" />
					</td>
				</tr>
		<TR>	 			
			 			<td class=button>
			 			<input class="cssbutton" type="button" value="查&nbsp;&nbsp;&nbsp;询"
				onclick="typeQueryClick();" />
				           </td>	
			</TR>	
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
