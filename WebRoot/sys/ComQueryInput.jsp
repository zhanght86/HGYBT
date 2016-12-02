<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ComQueryInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:45
//创建人  ：CrtHtml程序创建
//更新记录：更新人    更新日期     更新原因/内容
//          黄位富   2009-02-06    根据光大永明调整界面  
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
   <title>机构信息 </title>
</head>
<body  onload="initForm();" >
  <form action="./ComQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <table>
      <tr>
      <td>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid1);">
      </td>
      <td class= titleImg>
        请您输入查询条件：
      </td>
    	</tr>
    </table>
  <Div  id= "divComGrid1" style= "display: ''">
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
            <Input class= common name=OutComCode>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            管理机构名称
          </TD>
          <TD  class= input>
            <Input class= common name=Name>
          </TD>
          <TD  class= title>
            短名称
          </TD>
          <TD  class= input>
            <Input class= common name=ShortName >
          </TD>
        </TR>
        <!--
        <TR  class= common>       
          <TD  class= title>
            级别
          </TD>
          <TD  class= input>
            <Input class=codeno name=ComGrade ondblclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onkeyup="return showCodeListKey('comlevel',[this,ComGradeName],[0,1]);" ><input name=ComGradeName class="codename" elementtype=nacessary readonly=true> 
          </TD>     	
        </TR>
        -->
        <TR  class= common>
        	<!--
        	<TD  class= title>
            地区类别
          </TD> 
          <TD  class= input> 
            <Input class=codeno name=ComCitySize ondblclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onkeyup="return showCodeListKey('comcitysize',[this,ComCitySizeName],[0,1]);" ><input name=ComCitySizeName class="codename" elementtype=nacessary readonly=true> 
          </TD>
          -->
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
          	<Input class=common name=UpComCode>
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
        <!--
      	<TR>
          <TD  class= title>
            标志
          </TD>
          <TD  class= input>
            <Input class= common name=Sign >
          </TD>
          <TD  class= title>
            直属属性
          </TD>
          <TD  class= input>
            <Input class=codeno name=IsDirUnder verify="直属属性|notnull&code:comdirectattr" ondblclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onkeyup="return showCodeListKey('comdirectattr',[this,ComAreaTypeName],[0,1]);" ><input name=IsDirUnderName class="codename" readonly=true> 
          </TD>
        </TR>
        -->
      </table>
    </Div>
          <INPUT VALUE="查  询" TYPE=button onclick="easyQueryClick();" class="cssButton">
          <INPUT VALUE="返  回" TYPE=button onclick="returnParent();" class="cssButton">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid);">
    		</td>
    		<td class= titleImg>
    			 机构信息结果
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
      <INPUT VALUE="首  页" TYPE=button onclick="turnPage.firstPage();" class="cssButton">
      <INPUT VALUE="上一页" TYPE=button onclick="turnPage.previousPage();" class="cssButton">
      <INPUT VALUE="下一页" TYPE=button onclick="turnPage.nextPage();" class="cssButton">
      <INPUT VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();" class="cssButton">
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
