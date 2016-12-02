<%
//*******************************************************
//* 程序名称：OperateButton.jsp
//* 程序功能：页面中一般的增加，修改，删除，查询 按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*
//******************************************************
%>
<span id="operateButton">
	<table class="common" align=center>
		<tr align=right>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="增  加"  TYPE=button onclick="return addClick();">		
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();">
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="查  询"  TYPE=button onclick="return queryClick();">
			</td>
		</tr>
	</table>
</span>