<%@page contentType='text/html;charset=GBK' %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<script>

</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script src="../common/javascript/Common.js"></script> 
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<Script src="PwdInput.js"></script>

<link rel='stylesheet' type='text/css' href='../common/css/Project.css'>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
</head>

<style type="text/css">
</style>
<body>

  <form name="fm"  action="./changPwd.jsp" method=post name=fm target="fraSubmit">
	<TABLE>

      <TR  class= common> 
          <TD  class= title>
            请输入原密码：
          </TD>  
          <TD  class= input>
            <input type="Password" name=oldPwd class=common maxlength= 8>
          </TD>
     </TR>
      <TR  class= common> 
          <TD  class= title>
            请输入新密码：
          </TD>  
          <TD  class= input>
            <input type="Password" name=newPwd class=common maxlength= 8>
          </TD>
     </TR>
      <TR  class= common> 
          <TD  class= title>
            请确认新密码：
          </TD>  
          <TD  class= input>
            <input type="Password" name=confirmPwd class=common maxlength= 8>
          </TD>
     </TR>
     <TR>
   
     </TR>
   </TABLE>
     <INPUT VALUE="确定" class= common TYPE=button onClick="return submitForm();">  
     <INPUT VALUE="重置" class= common TYPE=button onclick="resetForm()">  


 </form>

</body>
</html>
