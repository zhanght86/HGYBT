<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ComClientQueryInput.jsp
//�����ܣ�
//�������ڣ�2003-1-10
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./ComClientQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ComClientQueryInit.jsp"%>
  <title>��ͨ�ͻ���ѯ</title>
</head>
<body  onload="initForm();" >
  <form action="./ComClientQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <table  class= common>
  <TR  class= common>
    <TD  class= title>
      �ͻ�����
    </TD>
    <TD  class= input>
      <Input class= common name=BlacklistNo >
    </TD>
    <TD  class= title>
      �ͻ�����
    </TD>
    <TD  class= input>
      <!--Input class= common name=BlacklistType -->
      <Input class= code name=BlacklistType verify="�ͻ�����" CodeData="0|^0|���˿ͻ�^1|����ͻ�" ondblClick="showCodeListEx('ClientType',[this],[0,1]);" onkeyup="showCodeListKeyEx('ClientType',[this],[0,1]);">       
    </TD>
  </TR>
</table>

          <INPUT class=cssbutton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();"> 
          <INPUT class=cssbutton VALUE="��  ��" TYPE=button onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComClientGrid);">
    		</td>
    		<td class= titleImg>
    			 �ͻ���Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divComClientGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanComClientGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT class=cssbutton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssbutton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssbutton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class=cssbutton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"> 			
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
