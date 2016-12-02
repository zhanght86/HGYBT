<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：menuGrp.jsp
//程序功能：菜单组的输入
//创建日期：2002-10-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
    GlobalInput tG1 = new GlobalInput();
	tG1=(GlobalInput)session.getValue("GI");
	String Operator = tG1.Operator;;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="menuFunGrp.js"></SCRIPT>
  <script src="treeMenu.js"></script>
  <%@include file="menuFunInit.jsp"%>

</head>
<body  onload="initForm();" >

<form action="./menuFunMan.jsp" method=post name=fm target="fraSubmit">
   <table  >
    	<tr>
		<TD  class= input style= "display: none">
         <Input class="code" name=Action>
         class= input style= "display: none">
         <Input class="code" name=isChild>
         class= input style= "display: none">
         <Input class="code" name=isChild2>//2005
       </TD>
	   </tr>
      <TR  class= common>
          <TD  class= title> 菜单节点名称</TD>
          <TD  class= input><Input class=common name=NodeName > </TD>
          <TD class= title> 映射文件</TD>
          <TD  class= input> <Input class=common name=RunScript  ></TD>
      </TR> 
    </Table>
  <input type="checkbox" name="checkbox1" value="1"><b>作为子菜单插入(不选则按照同级菜单插入)</b>
  &nbsp;&nbsp;
  <input type="checkbox" name="checkbox2" value="1"><b>作为页面权限菜单插入</b>
  <p>
  <Div id= divCmdButton style="display: ''">
     <INPUT VALUE="查询菜单" TYPE=button class="cssButton" onclick="queryClick()">
     <INPUT VALUE="增加菜单" TYPE=button class="cssButton" onclick="insertClick()">
     <INPUT VALUE="删除菜单" TYPE=button class="cssButton" onclick="deleteClick()">
  </Div>

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
      <td class= titleImg>
    	 菜单列表
      </td>

    </table>

     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanQueryGrpGrid" ></span>
		</td>
	</tr>
     </table>
        <INPUT VALUE="首  页" TYPE=button  class="cssButton" onclick="userFirstPage()">
        <INPUT VALUE="上一页" TYPE=button  class="cssButton" onclick="userPageUp()">
        <INPUT VALUE="下一页" TYPE=button  class="cssButton" onclick="userPageDown()">
        <INPUT VALUE="尾  页" TYPE=button  class="cssButton" onclick="userLastPage()">
</div>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
