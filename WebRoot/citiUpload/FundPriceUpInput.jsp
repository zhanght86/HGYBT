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

		<%@include file="./FundPriceUpInit.jsp"%>
		<script src="FundPriceUp.js"></script> 

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />
 
		<title>缺失信息上传</title>
	</head>

	<body onload="initForm();initElementtype();"> 
		<form action="./FundPriceUpSave.jsp" method="post" name="fm"
			target="fraSubmit" ENCTYPE="multipart/form-data">

			<TABLE class="common">
				<TR>
					<TD class="titleImg" align="center">
						导入投连账户价格：
					</TD>
				</TR> 
            </TABLE>
            <hr>
				<TR>
					<!-- ************CodeSelect 缺失信息类型*********** -->
				<!--  	<TD class=title>
						缺失信息类型：
						<input class="codeno" name="InfoType"
							ondblclick="return showCodeList('c_missinfotype',[this,InfoTypeName],[0,1],null,null,null,1,null,1);"
							onkeyup="return showCodeListKey('c_missinfotype',[this,InfoTypeName],[0,1],null,null,null,1,null,1);" />
						<input class="codename" name="InfoTypeName" readonly="true" />
					</TD>
				-->
					</TR>
					<TR>	
					<TD class=title>
					<input type="hidden" name="ImportPath">
					<input type="hidden" name="flag">
					<Input type="file" name="FileName" size=30>
					<input class="cssButton" value="文件上传" name="InAgent" type="button"
					onclick="submitFormIn()" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="./FundPrice_Upload.xls">模板下载
					<IMG src="../common/images/excel.gif"  border="1"
						></IMG>
				</a>
					</TD> 				
				</TR>
				<table class=common>
				<tr class=common>
				<td>
				<input class="cssbutton" type="button" value="确认更新"
				onclick="makeUpdate();" />
				<input class="cssbutton" type="button" value="取消更新"
				onclick="cancelUpdate();" />
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
						onclick="turnPage.firstPage();" class="cssButton">
					<INPUT VALUE="上一页" TYPE=button
						onclick="turnPage.previousPage();"
						class="cssButton">
					<INPUT VALUE="下一页" TYPE=button
						onclick="turnPage.nextPage();" class="cssButton">
					<INPUT VALUE="尾  页" TYPE=button
						onclick="turnPage.lastPage();" class="cssButton">
				</CENTER>
			</Div>
					</form>

		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>
