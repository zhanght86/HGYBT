<%
//*******************************************************
//* �������ƣ�inputButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="inputButton" style="display: none">
<table class="common" align=center>
  <tr>
    <td class=button >
      &nbsp;&nbsp;
    </td>   
     <td class=button width="10%">
      <img class=button alt="��һ��" src='../common/images/butPreviousStep.gif' 
				onmouseover="return changeImage(this,'../common/images/butPreviousStepOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butPreviousStep.gif');"
				onclick="return returnparent();"></img>
    </td> 
    
    <td class=button width="10%">
      <img class=button alt="����" src='../common/images/butReset.gif' 
				onmouseover="return changeImage(this,'../common/images/butResetOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butReset.gif');"
				onclick="return resetForm();"></img>
    </td>
    
    <td class=button width="10%">
      <img id="saveImg" class=button alt="����" src='../common/images/butSave.gif' 
				onmouseover="return changeImage(this,'../common/images/butSaveOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butSave.gif');"
				onclick="return submitForm();"></img>							
    </td>
  </tr>
</table>

<div id="inputQuest" style="display: ''">   
  <input type="button" name="Input" value="�����¼��" onClick="QuestInput()">
  <input type="button" name="Input" value="ǿ�ƽ������" onClick="unLockTable();">
</div>

</div>

<%
//*******************************************************
//* �������ƣ�queryButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="queryButton" style="display: none">
<table class="common" align=center>
  <tr>
    <td class=button >
      &nbsp;&nbsp;
    </td>    
    
    <td class=button width="10%">
      <img class=button alt="��ѯ" src='../common/images/butQuery.gif' 
				onmouseover="return changeImage(this,'../common/images/butQueryOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butQuery.gif');"
				onclick="return queryClick();"></img>
    </td>
  </tr>
</table>
</div>

<%
//*******************************************************
//* �������ƣ�updateButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="updateButton" style="display: none">
<table class="common" align=center>
  <tr>
    <td class=button >
      &nbsp;&nbsp;
    </td>    
    
    <td class=button width="10%">
      <img class=button alt="�޸�" src='../common/images/butUpdate.gif' 
				onmouseover="return changeImage(this,'../common/images/butUpdateOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butUpdate.gif');"
				onclick="return updateClick();"></img>
    </td>
  </tr>
</table>
</div>

<%
//*******************************************************
//* �������ƣ�deleteButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="deleteButton" style="display: none">
<table class="common" align=center>
  <tr>
    <td class=button >
      &nbsp;&nbsp;
    </td>    
    
    <td class=button width="10%">
      <img class=button alt="ɾ��" src='../common/images/butDelete.gif' 
				onmouseover="return changeImage(this,'../common/images/butDeleteOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butDelete.gif');"
				onclick="return deleteClick();"></img>
    </td>
  </tr>
</table>
</div>

<%
//*******************************************************
//* �������ƣ�autoMoveButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="autoMoveButton" style="display: none">
  <input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove();">
</div>

<%
//*******************************************************
//* �������ƣ�autoMoveButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="inputQuestButton" style="display: none">   
  <input type="button" name="Input" value="�����¼��" onClick="QuestInput()">
</div>