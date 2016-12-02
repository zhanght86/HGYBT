<%
//*******************************************************
//* 程序名称：OperateButton.jsp
//* 程序功能：页面中一般的增加，修改，删除，查询 按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*
//******************************************************
%>
<span id="operateButton" >
	<table  class=common align=center>
		<tr align=right>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return submitForm();">
			</td>
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
			</td>			
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">
			</td>		
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">
			</td>
		</tr>
	</table>
</span>
