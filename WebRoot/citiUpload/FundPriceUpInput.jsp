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
 
		<title>ȱʧ��Ϣ�ϴ�</title>
	</head>

	<body onload="initForm();initElementtype();"> 
		<form action="./FundPriceUpSave.jsp" method="post" name="fm"
			target="fraSubmit" ENCTYPE="multipart/form-data">

			<TABLE class="common">
				<TR>
					<TD class="titleImg" align="center">
						����Ͷ���˻��۸�
					</TD>
				</TR> 
            </TABLE>
            <hr>
				<TR>
					<!-- ************CodeSelect ȱʧ��Ϣ����*********** -->
				<!--  	<TD class=title>
						ȱʧ��Ϣ���ͣ�
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
					<input class="cssButton" value="�ļ��ϴ�" name="InAgent" type="button"
					onclick="submitFormIn()" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="./FundPrice_Upload.xls">ģ������
					<IMG src="../common/images/excel.gif"  border="1"
						></IMG>
				</a>
					</TD> 				
				</TR>
				<table class=common>
				<tr class=common>
				<td>
				<input class="cssbutton" type="button" value="ȷ�ϸ���"
				onclick="makeUpdate();" />
				<input class="cssbutton" type="button" value="ȡ������"
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
			</Div>
					</form>

		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>
