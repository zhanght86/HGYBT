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

<%@include file="./abcHPResultInit.jsp"%>
<script src="./abcHPResult.js"></script>

<link href="../common/css/Project.css" rel=stylesheet type=text/css />
<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

<title>�ֹ�����</title>
</head>

<body onload="initForm();initElementtype();">
<form action="AbcHPResultSave.jsp" method="post" name="fm" target="fraSubmit">
	<table class="common" >
		<tr>
			<td class="titleImg" align="center">
				<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
				OnClick="showPage(this,divProposalPrt);"></IMG>��ѯ����
			</td>
		</tr>
	</table>
	
	<table  class=common>
	<tr>   
		<td class="title">ҵ�����</td>
		<td><input class="codeno" name="tranType"
			ondblclick="return showTranType()" onkeyup="return showTranTypeKey()"><input class="codename" name="tranTypeName" readonly="true" />
		</td>
			
		<td class="title">����</td>
			<td><input name="TranDate" class="coolDatePicker" dateFormat="short"  />
		</td>
	</tr>
	</table>
	
	<table>
		<tr>
			<td>
				 <INPUT VALUE="��  ѯ" class = CssButton TYPE=button  onclick="easyQueryClick();"> 
			</td>
		</tr>
	</table>

	<span id="spanAbcHPResultGrid" ></span> 
	
    <center>
	<input class="cssbutton" value="��ҳ" type="Button" onclick="turnPage.firstPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.previousPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.nextPage();" />
	<input class="cssbutton" value="βҳ" type="Button" onclick="turnPage.lastPage();" />
</center>
  </div>
  
  
</form>

<span id="spanCode" style="display: none; position:absolute; slategray" />
</body>
</html>
