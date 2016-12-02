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

<title>农行手工对账</title>
</head>

<body onload="initForm();initElementtype();">
<form action="abcManualBalanceSave.jsp" method="post" name="fm" target="fraSubmit">
<table class="common">
	<tr>
		<td class="titleImg" align="center">对账条件：</td>
	</tr>
	<tr>   
		<td>
			银行<input class="codeno" name="TranCom" verify="交易机构|NotNull" ondblclick="return showCodeList('trancom_bank',[this,TranComName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('trancom_bank',[this,TranComName],[0,1],null,null,null,1,null,1);"><input class="codename" name="TranComName" readonly="true" elementtype="nacessary" />
			
			交易代码<input class="codeno" name="FuncFlag"  verify="交易类型|NotNull"
			ondblclick="return showCodeList('ybttranstype',[this,FuncFlagName],[0,1],null,'othersign = #blc#',null,1,null,1);" 
			onkeyup="return showCodeListKey('ybttranstype',[this,FuncFlagName],[0,1],null,'othersign = #blc#',null,1,null,1);" />
			<input class="codename" name="FuncFlagName" readonly="true" />
	
			日期<input name="TranDate" class="coolDatePicker" dateFormat="short" verify="日期|NOTNULL&DATE" elementtype="nacessary" />
		</td>
	</tr>
	<tr>
		<td>
			<input class="cssbutton" type="button" value="开始对账" onclick="deal();">
		</td>
	</tr>
</table>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray" />
</body>
</html>
