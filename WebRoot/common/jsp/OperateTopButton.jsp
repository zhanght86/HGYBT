<%
//*******************************************************
//* 程序名称：OperateButton.jsp
//* 程序功能：页面中一般的增加，修改，删除，查询 按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*
//******************************************************
%>
<table class="common" align=center>
	<tr>
		<td class=button width="33%">
			<INPUT class=cssButton VALUE="取  消"  TYPE=button onclick="return cancelForm();">
		</td>
		<td class=button width="33%">
			<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">
		</td>
		<td class=button width="33%">
			<INPUT class=cssButton VALUE="保  存"  TYPE=button onclick="return submitForm();">
		</td>
	</tr>
</table>