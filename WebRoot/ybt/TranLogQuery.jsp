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
<script src="../common/easyQueryVer3/EasyQueryPrint.js"></script>
<script src="../common/javascript/MulLine.js"></script>
<script src="../common/javascript/VerifyInput.js"></script>

<%@include file="./TranLogQueryInit.jsp"%>
<script src="./TranLogQuery.js"></script>

<link href="../common/css/Project.css" rel=stylesheet type=text/css />
<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

<title>交易信息查询</title>
</head>

<body onload="initForm();initElementtype();">
<form action="." method="post" name="fm">
<table class="common">
	<tr>
		<td class="titleImg" align="center">指定相关号码：</td>
	</tr>
	<tr>
		<td class="title">
			投保单印刷号
			</td>
			<td class = "input">
			<input class="input" name="ProposalPrtNo" />
		</td>
         <td class="title">
			保险合同号
			</td>
			<td class = "input"><input class="input" name="ContNo" />
		</td>
	</tr>
</table>

<table class="common">
	<tr>
		<td class="titleImg">或选择日期：</td>
	</tr>
	
	<tr>
		<td class="title">
			开始时间
			</td>
			<td class = "input"><input class="coolDatePicker" dateFormat="short" name="StartDay" elementtype="nacessary" size="10"/>
		</td>
		<td class="title">
			结束时间
			</td>
			<td class = "input"><input class="coolDatePicker" dateFormat="short" name="EndDay" elementtype="nacessary" />
		</td>
	</tr>
</table>

<table class="common">
	<tr>
		<td class="titleImg" align="center">【可选】</td>
	</tr>
	<tr>
		<td class="title">
			交易类型
			</td>
			<td class = "input"><input class="codeno" name="FuncFlag" ondblclick="return showCodeList('ybttranstype',[this,FuncFlagName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('ybttranstype',[this,FuncFlagName],[0,1],null,null,null,1,null,1);" /><input name="FuncFlagName" readonly="true" size="12"/>
			</td>
			<td class="title">
			交易状态
			</td>
			<td class = "input"><input class="codeno" name="RCode" ondblclick="return showCodeList('tran_state',[this,RCodeName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('tran_state',[this,RCodeName],[0,1],null,null,null,1,null,1);" /><input  name="RCodeName" readonly="true"  size="12"/>
		
			
		</td>
	</tr>
	<tr>
		<td class="title">
			交易银行
			</td>
			<td class = "input"><input class="codeno" name="TranCom" ondblclick="return showCodeList('trancom',[this,TranComName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('trancom',[this,TranComName],[0,1],null,null,null,1,null,1);" /><input name="TranComName" readonly="true" size="12"/>
			</td>
		
	</tr>
</table>

<input class="cssbutton" type="button" value="查 询" onclick="queryItems();" />
<input class="cssbutton" type="button" value="查看报文信息" 
				onclick="requestItems();"/>
<input class="cssbutton" type="button" value="重 置" onclick="resetItems();" />

<span id="spanQueryGrid"></span>

<center>
	<input class="cssbutton" value="首页" type="Button" onclick="turnPage.firstPage();" />
	<input class="cssbutton" value="上一页" type="Button" onclick="turnPage.previousPage();" />
	<input class="cssbutton" value="下一页" type="Button" onclick="turnPage.nextPage();" />
	<input class="cssbutton" value="尾页" type="Button" onclick="turnPage.lastPage();" />
</center>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray" />
</body>
</html>