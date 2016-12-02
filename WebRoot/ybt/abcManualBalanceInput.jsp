<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />

<script src="../common/javascript/Common.js"></script>
<script src="../common/cvar/CCodeOperate.js"></script>
<script src="../common/Calendar/Calendar.js"></script>
<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
<script src="../common/javascript/MulLine.js"></script>
<script src="../common/javascript/VerifyInput.js"></script>

<%@include file="./ManualBalanceInit.jsp"%>
<script src="./ManualBalance.js"></script>

<link href="../common/css/Project.css" rel=stylesheet type=text/css />
<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

<title>ũ���ֹ�����</title>
</head>

<body onload="initForm();initElementtype();">
<form action="abcManualBalanceSave.jsp" method="post" name="fm" target="fraSubmit">
<table class="common">
	<tr>
		<td class="titleImg" align="center">����������</td>
	</tr>
	<tr>   
		<td>
			����<input class="codeno" name="TranCom" verify="���׻���|NotNull" ondblclick="return showCodeList('trancom_bank',[this,TranComName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('trancom_bank',[this,TranComName],[0,1],null,null,null,1,null,1);"><input class="codename" name="TranComName" readonly="true" elementtype="nacessary" />
			
			���״���<input class="codeno" name="FuncFlag"  verify="��������|NotNull"
			ondblclick="return showCodeList('ybttranstype',[this,FuncFlagName],[0,1],null,'othersign = #blc#',null,1,null,1);" 
			onkeyup="return showCodeListKey('ybttranstype',[this,FuncFlagName],[0,1],null,'othersign = #blc#',null,1,null,1);" />
			<input class="codename" name="FuncFlagName" readonly="true" />
	
			����<input name="TranDate" class="coolDatePicker" dateFormat="short" verify="����|NOTNULL&DATE" elementtype="nacessary" />
		</td>
	</tr>
	<tr>
		<td>
			<input class="cssbutton" type="button" value="��ʼ����" onclick="deal();">
		</td>
	</tr>
</table>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray" />
</body>
</html>
