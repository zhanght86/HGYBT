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
		<%@include file="./poundageDetailInit.jsp"%>
		<script src="poundageDetail.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

	<title>银保通手续费明细表</title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./poundageDetailSave.jsp" method=post name=fm
			target="fraSubmit">
			<table class=common>
				<tr>
					<td class=titleImg align=center>
						手续费明细表
					</td>
				</tr>
			</table>

			<HR>

			<table class="common">
				<TR class=common>
					<TD class=title>
						起始日期：
						</TD>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay" elementtype="nacessary"/>
					</TD>
					<TD class=title>
						截止日期：
						</TD>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay" elementtype="nacessary"/>
					</TD>
				</TR>			
			</TABLE>
		

			<BR />
 
			<table> 
			<TR class=common>
				<TD width="26%">	<INPUT VALUE="预      览" class="cssbutton" TYPE=button onclick="PreviewQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="生成报表" class="cssbutton" TYPE=button onclick="PrintQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="重      置" class="cssbutton" type="button"  onclick="initBox();"></TD>
				
			</TR>
		</table>
		<input type="hidden" name="PrintFlag">
		</form>
		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>