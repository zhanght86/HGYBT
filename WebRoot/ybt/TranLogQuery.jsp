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

<title>������Ϣ��ѯ</title>
</head>

<body onload="initForm();initElementtype();">
<form action="." method="post" name="fm">
<table class="common">
	<tr>
		<td class="titleImg" align="center">ָ����غ��룺</td>
	</tr>
	<tr>
		<td class="title">
			Ͷ����ӡˢ��
			</td>
			<td class = "input">
			<input class="input" name="ProposalPrtNo" />
		</td>
         <td class="title">
			���պ�ͬ��
			</td>
			<td class = "input"><input class="input" name="ContNo" />
		</td>
	</tr>
</table>

<table class="common">
	<tr>
		<td class="titleImg">��ѡ�����ڣ�</td>
	</tr>
	
	<tr>
		<td class="title">
			��ʼʱ��
			</td>
			<td class = "input"><input class="coolDatePicker" dateFormat="short" name="StartDay" elementtype="nacessary" size="10"/>
		</td>
		<td class="title">
			����ʱ��
			</td>
			<td class = "input"><input class="coolDatePicker" dateFormat="short" name="EndDay" elementtype="nacessary" />
		</td>
	</tr>
</table>

<table class="common">
	<tr>
		<td class="titleImg" align="center">����ѡ��</td>
	</tr>
	<tr>
		<td class="title">
			��������
			</td>
			<td class = "input"><input class="codeno" name="FuncFlag" ondblclick="return showCodeList('ybttranstype',[this,FuncFlagName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('ybttranstype',[this,FuncFlagName],[0,1],null,null,null,1,null,1);" /><input name="FuncFlagName" readonly="true" size="12"/>
			</td>
			<td class="title">
			����״̬
			</td>
			<td class = "input"><input class="codeno" name="RCode" ondblclick="return showCodeList('tran_state',[this,RCodeName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('tran_state',[this,RCodeName],[0,1],null,null,null,1,null,1);" /><input  name="RCodeName" readonly="true"  size="12"/>
		
			
		</td>
	</tr>
	<tr>
		<td class="title">
			��������
			</td>
			<td class = "input"><input class="codeno" name="TranCom" ondblclick="return showCodeList('trancom',[this,TranComName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('trancom',[this,TranComName],[0,1],null,null,null,1,null,1);" /><input name="TranComName" readonly="true" size="12"/>
			</td>
		
	</tr>
</table>

<input class="cssbutton" type="button" value="�� ѯ" onclick="queryItems();" />
<input class="cssbutton" type="button" value="�鿴������Ϣ" 
				onclick="requestItems();"/>
<input class="cssbutton" type="button" value="�� ��" onclick="resetItems();" />

<span id="spanQueryGrid"></span>

<center>
	<input class="cssbutton" value="��ҳ" type="Button" onclick="turnPage.firstPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.previousPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.nextPage();" />
	<input class="cssbutton" value="βҳ" type="Button" onclick="turnPage.lastPage();" />
</center>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray" />
</body>
</html>