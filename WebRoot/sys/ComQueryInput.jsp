<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ComQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:45
//������  ��CrtHtml���򴴽�
//���¼�¼��������    ��������     ����ԭ��/����
//          ��λ��   2009-02-06    ���ݹ��������������  
%>
<html>
<head>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
   <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="./ComQuery.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <%@include file="./ComQueryInit.jsp"%>
   <title>������Ϣ </title>
</head>
<body  onload="initForm();" >
  <form action="./ComQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <table>
      <tr>
      <td>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid1);">
      </td>
      <td class= titleImg>
        ���������ѯ������
      </td>
    	</tr>
    </table>
  <Div  id= "divComGrid1" style= "display: ''">
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
            <Input class= common name=OutComCode>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �����������
          </TD>
          <TD  class= input>
            <Input class= common name=Name>
          </TD>
          <TD  class= title>
            ������
          </TD>
          <TD  class= input>
            <Input class= common name=ShortName >
          </TD>
        </TR>
        <!--
        <TR  class= common>       
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class=codeno name=ComGrade ondblclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onkeyup="return showCodeListKey('comlevel',[this,ComGradeName],[0,1]);" ><input name=ComGradeName class="codename" elementtype=nacessary readonly=true> 
          </TD>     	
        </TR>
        -->
        <TR  class= common>
        	<!--
        	<TD  class= title>
            �������
          </TD> 
          <TD  class= input> 
            <Input class=codeno name=ComCitySize ondblclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onkeyup="return showCodeListKey('comcitysize',[this,ComCitySizeName],[0,1]);" ><input name=ComCitySizeName class="codename" elementtype=nacessary readonly=true> 
          </TD>
          -->
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
          	<Input class=common name=UpComCode>
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
        <!--
      	<TR>
          <TD  class= title>
            ��־
          </TD>
          <TD  class= input>
            <Input class= common name=Sign >
          </TD>
          <TD  class= title>
            ֱ������
          </TD>
          <TD  class= input>
            <Input class=codeno name=IsDirUnder verify="ֱ������|notnull&code:comdirectattr" ondblclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onkeyup="return showCodeListKey('comdirectattr',[this,ComAreaTypeName],[0,1]);" ><input name=IsDirUnderName class="codename" readonly=true> 
          </TD>
        </TR>
        -->
      </table>
    </Div>
          <INPUT VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton">
          <INPUT VALUE="��  ��" TYPE=button onclick="returnParent();" class="cssButton">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ���
    		</td>
    	</tr>
    </table>
  	<Div  id= "divComGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanComGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton">
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
