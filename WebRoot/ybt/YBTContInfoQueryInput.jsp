<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
   
%>   

<script>
  var manageCom="<%=tG.ManageCom%>"; //记录登陆机构
  var comCode = <%=tG.ComCode%>
</script>
<html>
<%
  //程序名称：YBTContInfoQueryInput.jsp
  //程序功能：
  //创建日期：2009-09-24
  //创建人  ：liuq 程序创建
  //更新记录：  更新人    更新日期     更新原因/内容
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
  <title>银保通保单信息查询</title>
</head>

<body  onload="initForm();initElementtype();" >
  <form method=post name=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table >
    	<tr>
	   		<td class= titleImg align= center>请输入查询条件&nbsp;&nbsp;&nbsp;</td>
	  	</tr>
  	</table>
	
	  <table  class= common align=center>
	  	  <TR  class= common>
	        <td  class= titleImg colspan = 2> 
		      	 多条件查询：
			    </td>
		    </TR>
      	<TR class= common>
           <TD class= title>银行类别 </TD>
           <TD class= input> 
             <Input name=BankCode class='codeno' ondblclick="return showCodeList('trancom_bank',[this,BankName],[0,1]);" onkeyup="return showCodeListKey('trancom_bank',[this,BankName],[0,1]);"><input name=BankName class='codename' readonly=true >       
           </TD>     
        </TR>
        
        <TR class= common>          
           <TD class= title> 起始日期 </TD>
           <TD class= input> <Input class= "coolDatePicker" dateFormat="short" name=StartDate elementtype=nacessary> </TD>
           <TD class= title> 终止日期 </TD>
           <TD class= input><Input class= "coolDatePicker" dateFormat="short" name=EndDate elementtype=nacessary></TD>
        </TR>
   	
	      <TR class= common>
	        <td class= titleImg colspan = 2> 
		      	 或输入保单号作为查询条件：
			    </td>
		    </TR>
		  	<TR class = common>
			    <TD class = title>
			     	 保单号：
			    </TD>
	        <TD class= input>
	          <Input class="input" name=ContNo elementtype=nacessary>
	        </TD>   
	      </TR>
	  </table>
   
          <INPUT VALUE="查  询" class = CssButton TYPE=button onclick="easyQueryClick();"> 
          <input class="cssbutton" type="button" value="保单明细"
				onclick="queryDetail();" />
          <INPUT class=cssButton VALUE="重  置" TYPE=button onclick="return resetForm();">
            <!--  
          <INPUT VALUE="导  出" class=cssbutton TYPE=button onclick="NCExecel();"> 
         -->
          <!-- 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 保单信息
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
	<input class="cssbutton" value="首页" type="Button" onclick="turnPage.firstPage();" />
	<input class="cssbutton" value="上一页" type="Button" onclick="turnPage.previousPage();" />
	<input class="cssbutton" value="下一页" type="Button" onclick="turnPage.nextPage();" />
	<input class="cssbutton" value="尾页" type="Button" onclick="turnPage.lastPage();" />
</center>
  </div>
  	 </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
 