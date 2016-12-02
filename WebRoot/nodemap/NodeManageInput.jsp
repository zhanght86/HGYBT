<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

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

<%@include file="./NodeManageInit.jsp"%>
<script src="NodeManage.js"></script>

<link href="../common/css/Project.css" rel=stylesheet type=text/css />
<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

<title>�������</title>
</head>

<body onload="initForm();initElementtype();">
<form action="./NodeManageSave.jsp" method="post" name="fm" target="fraSubmit">
<div align="right">
	<input class="cssButton" value="�� ѯ" type="button" onclick="queryItems();" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input class="cssButton" value="�� ��" type="button" onclick="addItem();" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input class="cssButton" value="�� ��" name="update_bt" type="button" onclick="updateItem();" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input class="cssButton" value="ɾ ��" type="button" onclick="delItem();" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input class="cssButton" value="�� ��" type="reset" onclick="resetForm();" />
	<input type="hidden" name="OperType" />
	<input type="hidden" name="MapNo" />
</div>

<table class="common">
	<tr>
		<td class="titleImg" align="center">���з����㣺</td>
	</tr>
	<tr>
		<td>
			���д���<input class="codeno" name="TranCom" verify="���д���|NotNull" ondblclick="return showCodeList('trancom_bank',[this,TranComName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('trancom_bank',[this,TranComName],[0,1],null,null,null,1,null,1);"><input class="codename" name="TranComName" readonly="true" elementtype="nacessary" />
			&nbsp;&nbsp;
			��������<input class="common" name="NodeNo" verify="������|NotNull"  /><font color="red">���е���+������</font></td>
			<input type="hidden" name="NodeType" value="0" />
		
	</tr>
</table> 

<table class="common">
	<tr>
		<td class="titleImg">���շ�����������</td>
	</tr>
	<tr>
		<td><input type="hidden" name = iManageCom>
			��&nbsp;&nbsp;��<input class="codeno" name="AgentCom1" ondblclick="showAgentCom1();" onkeyup="showAgentCom1Key();" /><input class="codename" name="AgentCom1Name" readonly="true"  />
			
			ʡ&nbsp;&nbsp;��<input class="codeno" name="AgentCom2" ondblclick="showAgentCom2();" onkeyup="showAgentCom2Key();" /><input class="codename" name="AgentCom2Name" readonly="true" />
			
			��&nbsp;&nbsp;��<input class="codeno" name="AgentCom3" ondblclick="showAgentCom3();" onkeyup="showAgentCom3Key();" /><input class="codename" name="AgentCom3Name" readonly="true" />
			
			֧&nbsp;&nbsp;��<input class="codeno" name="AgentCom4" ondblclick="showAgentCom4();" onkeyup="showAgentCom4Key();" /><input class="codename" name="AgentCom4Name" readonly="true"   />
			 
		</td>
	</tr>
	<tr>
		<td>
			
			����<input class="codeno" name="AgentCom5" id="AgentCom5id"  ondblclick="showAgentCom5();" onkeyup="showAgentCom5Key();" /><input class="codename" name="AgentCom5Name" readonly="true" />
			&nbsp;<input type="hidden" name="AgentCom5h"><input type="hidden" name="AgentCom5hh"> 
			������<input class="codeno" name="AgentCom6" id="AgentCom6id" ondblclick="showAgentCom6();" onkeyup="showAgentCom6Key();" /><input class="codename" name="AgentCom6Name" readonly="true" />
			<input type="hidden" name="AgentCom">
			<input type="hidden" name="AgentComName"> 
			&nbsp;
			
		</td>
	</tr>
 
	<tr> 
		<td> 
		<input class="codeno" type = "hidden" name="AgentCode" value='-'"/>
		<input class="codeno" type = "hidden" name="AgentName" value='-'"/>
		<input class="codeno" type = "hidden" name="ManageCom" /> 
		<input class="codeno" type = "hidden" name="ManageComName" />
		   
		<%--
			������<input class="codeno" name="AgentCode" verify="������|NotNull" ondblclick="showAgentCode();" onkeyup="showAgentCodeKey();" /><input class="codename" name="AgentName" readonly="true" />
			&nbsp; 
			�������<input class="codeno" name="ManageCom" verify="�������|NotNull" readonly="true" /><input class="input" size="45" name="ManageComName" readonly="true" />
			--%>
		</td> 
	</tr>

</table>

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
