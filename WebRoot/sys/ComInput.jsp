<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-16 17:44:40
//������  ��CrtHtml���򴴽�
//���¼�¼��������    ��������     ����ԭ��/����
//          ��λ��   2009-02-06    ���ݹ��������������  
%>
<%@page contentType="text/html;charset=GBK" %>
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ComInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <%@include file="ComInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
	
    <form action="./ComSave.jsp" method=post name=fm target="fraSubmit">
    <table>
    	<tr>
    		<td>
    		   <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
        	 ���������Ϣ
       	</td>
    	</tr>
    </table>
    <Div  id= "divCom1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
           <TD  class= title>
            �����������
          </TD>
          <TD  class= input>
            <Input class='codeno' name=ComCode ondblclick="return showCodeList('comcode',[this,ComCodeName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ComCodeName],[0,1]);" ><input name=ComCodeName class="codename"  readonly=true> 

          </TD>
          <TD  class= title>
            ���⹫���Ļ�������
          </TD>
          <TD  class= input>
            <Input class= common name=OutComCode elementtype=nacessary verify="���⹫���Ļ�������|notnull">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �����������
          </TD>
          <TD  class= input>
            <Input class= common name=Name elementtype=nacessary verify="�����������|notnull">
          </TD>
          <TD  class= title>
            ������
          </TD>
          <TD  class= input>
            <Input class= common name=ShortName >
          </TD>
        </TR>
 
        <TR  class= common>

          <TD  class= title>
            �����ʱ�
          </TD>
          <TD  class= input>
            <Input class= common name=ZipCode >
          </TD>
          <TD  class= title>
          	�ϼ�����
          </TD>
          <TD  class= input>
          	86
          	<!-- ���ֻ��86,����ֻ������ṹ,�ܹ�˾ �ֹ�˾ -->
          	
          	<Input type=hidden name=UpComCode value="86">
          	<!--
          	<input type=hidden class="cssButton" value="ѡ��" onClick="SelectCom();">
          	-->
      
			</TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ������ַ
          </TD>
          <TD  class= input>
            <Input class= common name=Address >
          </TD>
          <TD  class= title>
            �����绰
          </TD>
          <TD  class= input>
            <Input class= common name=Phone >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name=Fax >
          </TD>
          <TD  class= title>
            EMail
          </TD>
          <TD  class= input>
            <Input class= common name=EMail >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��ַ
          </TD>
          <TD  class= input>
            <Input class= common name=WebAddress >
          </TD>
        <!--
          <TD  class= title>
            ����������
          </TD>
          <TD  class= input>
            <Input class= common name=SatrapName >
          </TD>
        -->
        </TR>
      	<TR>
      	<!--
          <TD  class= title>
            ��־
          </TD>
          <TD  class= input>
            <Input class= common name=Sign >
          </TD>
         -->

        </TR>
      </table>
      	<table class="common" align=center>
	  	<tr align=center>
			<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return addClick();">		
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="ɾ  ��"  TYPE=button onclick="return delClick();">		
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();">
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return resetForm();">
	  		</td>
	  		<td class=button >
	  			&nbsp;&nbsp;
	  		</td>
	  	</tr>
	  </table>
    </Div>
    <input type=hidden name=DelFlg>
    <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
