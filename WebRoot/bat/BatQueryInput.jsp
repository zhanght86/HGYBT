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

		<title>�������ѯ</title>
	</head>
	
	<body onload="initForm();initElementtype();">
		<form action="." method="post" name="fm">
			<table class="common" >
				<tr>
					<td class="titleImg" align="center">
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divProposalPrt);"></IMG>&nbsp;��&nbsp;ѯ&nbsp;��&nbsp;��
					</td>
				</tr>
				</table>
				<div id="divProposalPrt" style="display:''">
			<table class=common>
				<tr >
					<td class=title>
						���������
						</td>
						<TD class=input>
						<input class="codeno" name="TranCom" 
							ondblclick="return showTranCom()"
							onkeyup="return showTranComKey()">
						<input class="codename" name="TranComName"  readonly="true"/></td>
						<TD class=title>
						����������</td>
						<TD class=input>
						<input class="codeno" name="BalanceFlag" 
							ondblclick="return showBalanceFlag()"
							onkeyup="return showBalanceFlagKey()">
						<input class="codename" name="BalanceFlagName" readonly="true"/></td>
						</Tr>
						<tr>
						<TD class=title>
						״&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;̬</td>
						<TD class=input>
						<input class="codeno" name="RCode"  CodeData="0|^0|�ɹ�^1|ʧ��" ondblclick="return showCodeListEx('RCodeName',[this,RCodeName],[0,1]);" onkeyup="return showCodeListKeyEx('RCodeName',[this,RCodeName],[0,1]);" />
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
							��&nbsp;��&nbsp;��&nbsp;��
					</td>
				</tr>
				</table>
				<div id="divDate" style="display:''">
				<table class=common>
				<tr>
					<td class=title>
						��&nbsp;ʼ&nbsp;��&nbsp;��</td>
						<TD class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay"
							elementtype="nacessary" /></td><td class=title>
						 ��&nbsp;��&nbsp;��&nbsp;��</td>
						<TD class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay"
							elementtype="nacessary" />
					</td>
				</tr>
				</table>
				</div>

            <table class=common>
			<tr class=common>
			<input class="cssbutton" type="button" value="��&nbsp;&nbsp;&nbsp;&nbsp;ѯ"
				onclick="queryItems();" />
			<input class="cssbutton" type="button" value="��&nbsp;&nbsp;&nbsp;&nbsp; ��" 
				onclick="resetItems();" />
            </tr>
            </table>
			<span id="spanQueryGrid"></span>
 
			<center>
				<input class="cssbutton" value="��ҳ" type="Button"
					onclick="turnPage.firstPage();" />
				<input class="cssbutton" value="��һҳ" type="Button"
					onclick="turnPage.previousPage();" />
				<input class="cssbutton" value="��һҳ" type="Button"
					onclick="turnPage.nextPage();" />
				<input class="cssbutton" value="βҳ" type="Button"
					onclick="turnPage.lastPage();" />
			</center>
		</form>

		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>