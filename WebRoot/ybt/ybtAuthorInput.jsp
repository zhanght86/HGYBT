<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
  //�������ƣ�ybtAuthorInput.jsp
  //�����ܣ�����ͨ���׿���
  //�������ڣ�2006-03-08
  //������  �����򴴽�
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%

GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

%>
<script>

var manageCom="<%=tGI.ManageCom%>"; //��¼��½����
var comcode="<%=tGI.ComCode%>";//��¼��½����
</script>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js">
	</SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js">
	</SCRIPT><SCRIPT src="../common/javascript/Common.js">
	</SCRIPT><SCRIPT src="../common/cvar/CCodeOperate.js">
	</SCRIPT><SCRIPT src="../common/javascript/MulLine.js">
	</SCRIPT><SCRIPT src="../common/javascript/VerifyInput.js">
	</SCRIPT><SCRIPT src="ybtAuthorInput.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ybtAuthorInputInit.jsp"%>
<title></title>
</head>

<body onload="initForm();initElementtype();">
<form action="./ybtAuthorSave.jsp" method=post name=fm target="fraSubmit">
<!--%@include file="../common/jsp/OperateAgentButton.jsp"%-->
<!--%@include file="../common/jsp/InputButton.jsp"%-->
<span id="operateButton">
  <table class="common" align=center>
    <tr align=right>
      <td class=button>&nbsp;&nbsp;</td>
      <td class=button width="10%">
        <INPUT class=cssButton VALUE="��  ѯ" name="query" TYPE=button onclick="return queryClick();">
      </td>
      <td class=button width="10%">
        <INPUT class=cssButton VALUE="��  ��" name="add" TYPE=button onclick="return addClick();">
      </td>
      <td class=button width="10%">
        <INPUT class=cssButton VALUE="ɾ  ��" name="del" TYPE=button onclick="return delClick();">
      </td>
      <td class=button width="10%">
        <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="return resetForm();">
      </td>
    </tr>
  </table>
</span>

 <table class=common>
    <tr class=common><!--
     
      <td class=title>�ֹ�˾</td>
      <td class=input>
        <input class=codeno  verify="�ֹ�˾|NotNull" name=ManageCom ondblclick="getstr1();return showCodeList('ybt_managecom',[this,ManageComName],[0,1],null,str1,'1',1,200);" onkeyup="getstr1(); return showCodeListKey('ybt_managecom',[this,ManageComName],[0,1],null,str1,'1',1,200);"><input class=codename  readonly=true name=ManageComName elementtype=nacessary>
      </td>    
       -->
       
       <td class=title>���д���</td>
      <td class=input>
        <input class=codeno  verify="���д���|NotNull" name=BankCode ondblclick="return showCodeList('trancom_bank',[this,BankCodeName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('trancom_bank',[this,BankCodeName],[0,1],null,null,null,1,null,1);"><input class=codename  name=BankCodeName readonly=true >
      </td>  
       <td class=title>��������</td>
      <td class=input>
        <input class=codeno  verify="��������|NotNull" name=FuncFlag ondblclick="showCodeList('ybttranstype',[this,FuncFlagName],[0,1]);" onkeyup="showCodeListKey('ybttranstype',[this,FuncFlagName],[0,1]);"><input class=codename  readonly=true name=FuncFlagName >
      </td> 
      <input type="hidden" name =ManageCom>
       </tr>
     

     
   <tr class=common>
     	<td class=title>���е�����</td>
		<TD class=input><Input class=input name=BankBranch></TD>
		<td class=title>����������</td>
		<TD class=input><Input class=input name=BankNode></TD>
    </tr>   
</table>
<table>
  <tr>
    <td class=common>
      <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLACom);">
    </td>
    <td class=titleImg>����ͨ����Ȩ��</td>
  </tr>
</table>
<Div id="divLACom" style="display: ''">
  <table class=common>
    <tr class=common>
      <td text-align: left colSpan=1>
        <span id="spanLKAuthorGrid">        </span>
      </td>
    </tr>
  </table>
</div>
<center>
	<input class="cssbutton" value="��ҳ" type="Button" onclick="turnPage.firstPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.previousPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.nextPage();" />
	<input class="cssbutton" value="βҳ" type="Button" onclick="turnPage.lastPage();" />
</center>
<input type=hidden name=hideOperate value=''>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>