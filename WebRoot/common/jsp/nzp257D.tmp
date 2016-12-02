<%
//*******************************************************
//* 程序名称：inputButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="inputButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				&nbsp;<!--<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">-->
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="保  存"  TYPE=button onclick="return submitForm();">
			</td>
		</tr>
	</table>
</div>

<div id="inputQuest" style="display:'none'">
  <input type="button" class=cssButton name="Input" value="上一步" onClick="returnparent()" class=cssButton>
  <div id="inputQuestIn" style="display: 'none'">
	  <input type="button" class=cssButton name="Input" value="问题件录入" onClick="QuestInput()" class=cssButton>
  </div>
	<INPUT class=cssButton VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
	<INPUT class=cssButton VALUE=录入完毕 TYPE=button onclick="inputConfirm(1);">
	<!--input type="button" class=cssButton name="Input" value="强制解除锁定" onClick="unLockTable();" class=cssButton-->
</div>


<div id="modifyButton" style="display:'none'">
	  	<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();" style="float: right">
			<!--INPUT class=cssButton VALUE="删  除"  TYPE=button onclick="return deleteClick();" style="float: right"-->
		<!--input type="button" class=cssButton name="Input" value="强制解除锁定" onClick="unLockTable();" class=cssButton-->
</div>


<div  id= "divInputContButton" style= "display:'none'" style="float: left">
  <INPUT class=cssButton VALUE="受益人影像定位" TYPE=button onclick="gotoBnf();">
  <INPUT class=cssButton id="riskbutton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
  <INPUT class=cssButton id="riskbutton1" VALUE="上一步" TYPE=button onclick="returnparent();">
  <INPUT class=cssButton id="riskbutton2" VALUE="录入完毕"  TYPE=button onclick="inputConfirm(1);">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>

<div  id= "divInputContButtonBQ" style= "display:'none'" style="float: left">
  <INPUT class=cssButton id="riskbutton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
  <INPUT class=cssButton id="riskbutton1" VALUE=" 返 回 " TYPE=button onclick="returnparent();">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>
<!--add by yaory 2005-7-11-14:14 -->

<!--end add by yaory-->
<div  id= "divGrpContButton" style= "display:'none'" style="float: left">
  <INPUT class=cssButton id="GrpContButton2" VALUE="上一步" TYPE=button onclick="returnparent();">
  <INPUT class=cssButton id="GrpContButton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
	<INPUT class=cssButton id="GrpContButton1" VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>

<div  id= "divUWContButton" style= "display:'none'" style="float: left">
  <INPUT class=cssButton id="backbutton" VALUE="上一步" TYPE=button onclick="returnparent();">
</div>
<div id = "divApproveContButton" style = "display:'none'" style="float: left">
      <INPUT class=cssButton id="riskbutton4" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton id="riskbutton5" VALUE="上一步" TYPE=button onclick="returnparent();">
    	<INPUT class=cssButton id="riskbutton6" VALUE="复核完毕" TYPE=button onclick="inputConfirm(2);">
    	<!--INPUT class=cssButton id="riskbutton7" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>
<div id = "divApproveModifyContButton" style = "display:'none'" style="float:left">
	<INPUT class=cssButton id="riskbutton21" VALUE="上一步" TYPE=button onclick="returnparent();">
	<!--
	<INPUT class=cssButton id="riskbutton22" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
	-->
	<INPUT class=cssButton id="riskbutton23" VALUE="复核修改完毕" TYPE=button onclick="inputConfirm(3);">
	<!--INPUT class=cssButton id="riskbutton7" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>


<div  id= "divBqNSButton" style= "display:'none'" style="float: left">
  <!--<INPUT class=cssButton id="riskbutton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">-->
  <INPUT class=cssButton id="riskbutton1" VALUE="上一步" TYPE=button onclick="returnparent();">
</div>

<%
//*******************************************************
//* 程序名称：queryButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="queryButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="查  询"  TYPE=button onclick="return queryClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* 程序名称：updateButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="updateButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* 程序名称：deleteButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="deleteButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="删  除"  TYPE=button onclick="return deleteClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* 程序名称：autoMoveButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove(''+param+'');" class=cssButton>
	<input type="button" name="autoMoveInput" value="上一步" onclick="returnparent();" class=cssButton>
        <input type=hidden  id=""  name="autoMoveFlag">
        <input type=hidden  id=""  name="autoMoveValue">
        <input type=hidden id="" name="pagename" value="">
</div>

<%
//*******************************************************
//* 程序名称：autoMoveButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="inputQuestButton" style="display: none">
	<input type="button" name="Input" value="问题件录入" onClick="QuestInput()" class=cssButton>
</div>
