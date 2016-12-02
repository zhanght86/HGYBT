<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-16 17:44:40
//创建人  ：CrtHtml程序创建
//更新记录：更新人    更新日期     更新原因/内容
//          黄位富   2009-02-06    根据光大永明调整界面  
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
        	 管理机构信息
       	</td>
    	</tr>
    </table>
    <Div  id= "divCom1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
           <TD  class= title>
            管理机构代码
          </TD>
          <TD  class= input>
            <Input class='codeno' name=ComCode ondblclick="return showCodeList('comcode',[this,ComCodeName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ComCodeName],[0,1]);" ><input name=ComCodeName class="codename"  readonly=true> 

          </TD>
          <TD  class= title>
            对外公布的机构代码
          </TD>
          <TD  class= input>
            <Input class= common name=OutComCode elementtype=nacessary verify="对外公布的机构代码|notnull">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            管理机构名称
          </TD>
          <TD  class= input>
            <Input class= common name=Name elementtype=nacessary verify="管理机构名称|notnull">
          </TD>
          <TD  class= title>
            短名称
          </TD>
          <TD  class= input>
            <Input class= common name=ShortName >
          </TD>
        </TR>
 
        <TR  class= common>

          <TD  class= title>
            机构邮编
          </TD>
          <TD  class= input>
            <Input class= common name=ZipCode >
          </TD>
          <TD  class= title>
          	上级机构
          </TD>
          <TD  class= input>
          	86
          	<!-- 这边只有86,而且只有两层结构,总公司 分公司 -->
          	
          	<Input type=hidden name=UpComCode value="86">
          	<!--
          	<input type=hidden class="cssButton" value="选择" onClick="SelectCom();">
          	-->
      
			</TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            机构地址
          </TD>
          <TD  class= input>
            <Input class= common name=Address >
          </TD>
          <TD  class= title>
            机构电话
          </TD>
          <TD  class= input>
            <Input class= common name=Phone >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            机构传真
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
            网址
          </TD>
          <TD  class= input>
            <Input class= common name=WebAddress >
          </TD>
        <!--
          <TD  class= title>
            主管人姓名
          </TD>
          <TD  class= input>
            <Input class= common name=SatrapName >
          </TD>
        -->
        </TR>
      	<TR>
      	<!--
          <TD  class= title>
            标志
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
	  			<INPUT class=cssButton VALUE="查  询"  TYPE=button onclick="return queryClick();">
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="增  加"  TYPE=button onclick="return addClick();">		
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="删  除"  TYPE=button onclick="return delClick();">		
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();">
	  		</td>
	  		<td class=button width="10%">
	  			<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">
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
