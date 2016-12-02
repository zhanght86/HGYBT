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
  //程序名称：ybtBalanceQueryInput.jsp
  //程序功能：银保通对帐情况查询
  //创建日期：2005-10-17
  //创建人  ：liuyx程序创建
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
%>
<script>
	var tmanageCom="<%=tGI.ManageCom%>"; //记录登陆机构
	var comcode="<%=tGI.ComCode%>";//记录登陆机构
</script>
<head>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="ybtBalanceQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ybtBalanceQueryInit.jsp"%>
  <title>银保通查询</title>
</head>

<body  onload="initForm();initElementtype();" >
  <form method=post name=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入银保通对帐查询条件：</td>
		</tr>
	</table>
	
	<table  class= common align=center>
      	<TR  class= common>
	      	<!--
	      	<td class=title>分公司</td>
	      	<td class=input>
	        	<input type = "hidden" class=codeno name=ManageCom ondblclick="getstr1();return showCodeList('comcode',[this,ManageComName],[0,1],null,str1,1,1,200);" onkeyup="getstr1(); return showCodeListKey('comcode',[this,ManageComName],[0,1],null,str1,'1',1,200);"><input class=codename readonly=true name=ManageComName elementtype=nacessary>
	      	</td>
	      	-->
	      	    
          <TD  class= title>银行代码 </TD>
          <TD  class= input> 
	        	<input name=BankCode class='codeno' ondblclick="return showCodeList('trancom_bank',[this,BankCodeName],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('trancom_bank',[this,BankCodeName],[0,1],null,null,null,1,null,1);"><input class=codename  name=BankCodeName readonly=true elementtype=nacessary>
          </TD>
          
        </TR>
        <input type = "hidden" class=codeno name=ManageCom ondblclick="getstr1();return showCodeList('comcode',[this,ManageComName],[0,1],null,str1,1,1,200);" onkeyup="getstr1(); return showCodeListKey('comcode',[this,ManageComName],[0,1],null,str1,'1',1,200);"><input type = "hidden" class=codename readonly=true name=ManageComName> 
        <!--
        <TR  class= common>
          <TD  class= title> 地区编码</TD>
          <TD  class= input>
            <Input class= common name=ZoneNo>
	        </TD>          
          <TD  class= title>银行网点 </TD>
          <TD  class= input> 
             <Input class= common name=BankNode>
	        </TD> 
	        </TR>
	        --> 
        <TR>
                
          <TD  class= title> 查询起始日期 </TD>
          <TD  class= input> <Input class= "coolDatePicker" dateFormat="short" name=StartTransDate elementtype=nacessary > </TD>
          <TD  class= title> 查询终止日期 </TD>
          <TD  class= input> <Input class= "coolDatePicker" dateFormat="short" name=EndTransDate elementtype=nacessary > </TD>
                
        </TR>
         </table>
         <Input class= common name=ZoneNo type="hidden">
         <Input class= common name=BankNode type="hidden">
          <INPUT VALUE="查  询" class = CssButton TYPE=button onclick="easyQueryClick();"> 
          
           
        <INPUT VALUE="导  出" class=cssbutton TYPE=button onclick="easyQueryPrint(2,'ybtPolGrid','turnPage');"> 
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 对帐信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanybtPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>

      <INPUT VALUE="首  页" class = CssButton TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = CssButton TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class = CssButton TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class = CssButton TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  
  	 </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
        