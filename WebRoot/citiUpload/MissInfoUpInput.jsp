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

		<%@include file="./MissInfoUpInit.jsp"%>
		<script src="MissInfoUp.js"></script> 

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />
 
		<title>保单缺失信息上传</title>
	</head>

	<body onload="initForm();initElementtype();"> 
		<form action="./MissInfoUpSave.jsp" method="post" name="fm"
			target="fraSubmit" ENCTYPE="multipart/form-data">
			
			<input type="hidden" name="OperType">
			
			<div id="divQuery" style="display: ''">
			<TABLE class="common">
				<TR>
					<TD class="titleImg" align="center">
						保单缺失信息：
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
					<Input type="file" name="FileName" >
					<input class="cssButton" value="文件上传" name="InAgent" type="button"
					onclick="submitFormIn()" />	&nbsp;&nbsp;&nbsp;&nbsp;<a href="./MissInfo_Upload.xls">模板下载
					<IMG src="../common/images/excel.gif"   border="1"
						></IMG>	</a>	
			</TD> 				
				</TR>
				
			</div>	
				<div id="divQueryALL" style="display: ''">
			<TABLE class="common">
			<tr>
				<td class=title>
						开始日期：</td>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay"
							/>
						&nbsp; 
					</td>
					<td class=title>
						截止日期：
						</td>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay"
							 />
						&nbsp; 
					</td>
				</tr>
				<tr>
						<td>
						<INPUT VALUE="查询" TYPE=button onclick="queryALL()"
						class="cssButton">
						<INPUT VALUE="重置" TYPE=button onclick="initBox()"
						class="cssButton">
						</td>
					</tr>
				
		</TABLE> 
		</div>
			
			<div id="divInsert" style="display: 'none'">
				<table class="common">
					<tr>
						<td class="titleImg">
							信息明细：
						</td>
					</tr>
					<tr class=common>
					<TD class=title>
						导出日期：
						<input class="coolDatePicker" dateFormat="short" name="iExtractedDate" />
					</TD>
					<TD class=title>
						保单号：
						<input class="input" name="iPolicyNo" readonly="TRUE" />
					</TD>
					</tr>
					<TR class=common>
						<TD class=title>
							被保人证件类型：
							<input class="input" name="iInsuredIdType" maxlength="20" />
						</TD>
						<TD class=title>
							投保人证件类型：
							<input class="input" name="iApplicantIdType" maxlength="20" />
						</TD>
					</TR>
					<TR class=common>
						<TD class=title>
							投保人证件号码：
							<input class="input" name="iApplicantIdNo" maxlength="20" />
						</TD>
						<TD class=title>
							投保人银行账号：
							<input class="input" name="iApplicantAcctNo" maxlength="20" />
						</TD>					
					</TR>
					<TR class=common>
						<TD class=title>
							客户编号：
							<input class="input" name="iCustomNO" maxlength="20" />
						</TD>
						<TD class=title>
							退保理由：
							<input class="input" name="iCancelReason" maxlength="20" />
						</TD>					
					</TR>
					<TR class=common>
						<TD class=title>
							投保单号：
							<input class="input" name="iApplicateNo" maxlength="20" />
						</TD>
						<TD class=title>
							退保理由编码：
							<input class="input" name="iCancelCode" maxlength="20" />
						</TD>					
					</TR>
					<tr>
						<td>
						<INPUT VALUE="确  定" TYPE=button onclick="okUpdate()"
						class="cssButton">
						<INPUT VALUE="退  出" TYPE=button onclick="cancelUpdate()"
						class="cssButton">
						</td>
					</tr>
				</table>
			</div>
			
			<div id="divCmdButton" style="display: ''">				
				<INPUT VALUE="修改" TYPE=button onclick="updateClick()"
					class="cssButton">
				<INPUT VALUE="删除" TYPE=button onclick="delItem()"
					class="cssButton">
				<INPUT VALUE="确定上传" TYPE=button onclick="okClick()"
					class="cssButton">
				<INPUT VALUE="取消上传" TYPE=button onclick="cancelClick()"
					class="cssButton">
			</div>
			<Div id="divGrid" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanQueryGrid"> </span>
						</td>
					</tr>
				</table>
			</Div>
				<br>
			<div id="divPage" style="display: ''">
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
			</div>		 
			</form>

		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>
