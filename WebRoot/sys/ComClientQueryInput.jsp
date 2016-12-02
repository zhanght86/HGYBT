<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ComClientQueryInput.jsp
//程序功能：
//创建日期：2003-1-10
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
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
  <title>普通客户查询</title>
</head>
<body  onload="initForm();" >
  <form action="./ComClientQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <table  class= common>
  <TR  class= common>
    <TD  class= title>
      客户号码
    </TD>
    <TD  class= input>
      <Input class= common name=BlacklistNo >
    </TD>
    <TD  class= title>
      客户类型
    </TD>
    <TD  class= input>
      <!--Input class= common name=BlacklistType -->
      <Input class= code name=BlacklistType verify="客户类型" CodeData="0|^0|个人客户^1|集体客户" ondblClick="showCodeListEx('ClientType',[this],[0,1]);" onkeyup="showCodeListKeyEx('ClientType',[this],[0,1]);">       
    </TD>
  </TR>
</table>

          <INPUT class=cssbutton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> 
          <INPUT class=cssbutton VALUE="返  回" TYPE=button onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComClientGrid);">
    		</td>
    		<td class= titleImg>
    			 客户信息
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
      <INPUT class=cssbutton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssbutton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssbutton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class=cssbutton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"> 			
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
