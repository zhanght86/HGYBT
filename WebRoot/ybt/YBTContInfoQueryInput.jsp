<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
   
%>   

<script>
  var manageCom="<%=tG.ManageCom%>"; //��¼��½����
  var comCode = <%=tG.ComCode%>
</script>
<html>
<%
  //�������ƣ�YBTContInfoQueryInput.jsp
  //�����ܣ�
  //�������ڣ�2009-09-24
  //������  ��liuq ���򴴽�
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="YBTContInfoQueryInput.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="YBTContInfoQueryInit.jsp"%>
  <title>����ͨ������Ϣ��ѯ</title>
</head>

<body  onload="initForm();initElementtype();" >
  <form method=post name=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table >
    	<tr>
	   		<td class= titleImg align= center>�������ѯ����&nbsp;&nbsp;&nbsp;</td>
	  	</tr>
  	</table>
	
	  <table  class= common align=center>
	  	  <TR  class= common>
	        <td  class= titleImg colspan = 2> 
		      	 ��������ѯ��
			    </td>
		    </TR>
      	<TR class= common>
           <TD class= title>������� </TD>
           <TD class= input> 
             <Input name=BankCode class='codeno' ondblclick="return showCodeList('trancom_bank',[this,BankName],[0,1]);" onkeyup="return showCodeListKey('trancom_bank',[this,BankName],[0,1]);"><input name=BankName class='codename' readonly=true >       
           </TD>     
        </TR>
        
        <TR class= common>          
           <TD class= title> ��ʼ���� </TD>
           <TD class= input> <Input class= "coolDatePicker" dateFormat="short" name=StartDate elementtype=nacessary> </TD>
           <TD class= title> ��ֹ���� </TD>
           <TD class= input><Input class= "coolDatePicker" dateFormat="short" name=EndDate elementtype=nacessary></TD>
        </TR>
   	
	      <TR class= common>
	        <td class= titleImg colspan = 2> 
		      	 �����뱣������Ϊ��ѯ������
			    </td>
		    </TR>
		  	<TR class = common>
			    <TD class = title>
			     	 �����ţ�
			    </TD>
	        <TD class= input>
	          <Input class="input" name=ContNo elementtype=nacessary>
	        </TD>   
	      </TR>
	  </table>
   
          <INPUT VALUE="��  ѯ" class = CssButton TYPE=button onclick="easyQueryClick();"> 
          <input class="cssbutton" type="button" value="������ϸ"
				onclick="queryDetail();" />
          <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="return resetForm();">
            <!--  
          <INPUT VALUE="��  ��" class=cssbutton TYPE=button onclick="NCExecel();"> 
         -->
          <!-- 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
    -->
  	<Div  id= "divLCPol1" style= "display: ''" align = left>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanybtPolGrid1" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
     
    <center>
	<input class="cssbutton" value="��ҳ" type="Button" onclick="turnPage.firstPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.previousPage();" />
	<input class="cssbutton" value="��һҳ" type="Button" onclick="turnPage.nextPage();" />
	<input class="cssbutton" value="βҳ" type="Button" onclick="turnPage.lastPage();" />
</center>
  </div>
  	 </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
 