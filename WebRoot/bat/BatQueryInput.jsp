<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

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
		<script src="../common/easyQueryVer3/EasyQueryPrint.js">
</script>
		<script src="../common/javascript/MulLine.js">
</script>
		<script src="../common/javascript/VerifyInput.js">
</script>
		<script src="../common/javascript/AXANodeMap.js">
</script>

		<%@include file="./BatQueryInit.jsp"%>
		<script src="./BatQuery.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

		<title>批处理查询</title>
	</head>
	
	<body onload="initForm();initElementtype();">
		<form action="." method="post" name="fm">
			<table class="common" >
				<tr>
					<td class="titleImg" align="center">
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divProposalPrt);"></IMG>&nbsp;查&nbsp;询&nbsp;条&nbsp;件
					</td>
				</tr>
				</table>
				<div id="divProposalPrt" style="display:''">
			<table class=common>
				<tr >
					<td class=title>
						批处理机构
						</td>
						<TD class=input>
						<input class="codeno" name="TranCom" 
							ondblclick="return showTranCom()"
							onkeyup="return showTranComKey()">
						<input class="codename" name="TranComName"  readonly="true"/></td>
						<TD class=title>
						批处理类型</td>
						<TD class=input>
						<input class="codeno" name="BalanceFlag" 
							ondblclick="return showBalanceFlag()"
							onkeyup="return showBalanceFlagKey()">
						<input class="codename" name="BalanceFlagName" readonly="true"/></td>
						</Tr>
						<tr>
						<TD class=title>
						状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</td>
						<TD class=input>
						<input class="codeno" name="RCode"  CodeData="0|^0|成功^1|失败" ondblclick="return showCodeListEx('RCodeName',[this,RCodeName],[0,1]);" onkeyup="return showCodeListKeyEx('RCodeName',[this,RCodeName],[0,1]);" />
						<input class="codename"  name="RCodeName" readonly="true" />
					</td>
				</tr>
				</table>
				</div>
				<table class=common>
				<tr>
					<td class="titleImg" align="center">
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divDate);"></IMG>
							处&nbsp;理&nbsp;日&nbsp;期
					</td>
				</tr>
				</table>
				<div id="divDate" style="display:''">
				<table class=common>
				<tr>
					<td class=title>
						开&nbsp;始&nbsp;日&nbsp;期</td>
						<TD class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay"
							elementtype="nacessary" /></td><td class=title>
						 结&nbsp;束&nbsp;日&nbsp;期</td>
						<TD class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay"
							elementtype="nacessary" />
					</td>
				</tr>
				</table>
				</div>

            <table class=common>
			<tr class=common>
			<input class="cssbutton" type="button" value="查&nbsp;&nbsp;&nbsp;&nbsp;询"
				onclick="queryItems();" />
			<input class="cssbutton" type="button" value="重&nbsp;&nbsp;&nbsp;&nbsp; 置" 
				onclick="resetItems();" />
            </tr>
            </table>
			<span id="spanQueryGrid"></span>
 
			<center>
				<input class="cssbutton" value="首页" type="Button"
					onclick="turnPage.firstPage();" />
				<input class="cssbutton" value="上一页" type="Button"
					onclick="turnPage.previousPage();" />
				<input class="cssbutton" value="下一页" type="Button"
					onclick="turnPage.nextPage();" />
				<input class="cssbutton" value="尾页" type="Button"
					onclick="turnPage.lastPage();" />
			</center>
		</form>

		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>