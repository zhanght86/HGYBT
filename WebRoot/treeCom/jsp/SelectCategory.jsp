<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,java.util.*" errorPage="" %>
<%@page import="com.sinosoft.utility.*"%>

<%@include file="../../common/jsp/UsrCheck.jsp"%>
<%@include file="MenuMaker.jsp" %>
  
<%
request.setCharacterEncoding("GB2312");
GlobalInput tG = new GlobalInput();

//tG.Operator = "Admin";
//tG.ComCode  = "001";
//session.putValue("GI",tG);

tG=(GlobalInput)session.getValue("GI");
String ManageCom = tG.ComCode;
String ManageComName = " ";


	
//取得登录管理机构名称
try {
	Statement st = conn.createStatement();
	String SQLStr = "SELECT Name FROM ldcom WHERE Comcode='" + ManageCom + "'";
	ResultSet result = st.executeQuery(SQLStr);
	if(result.next())
		ManageComName = result.getString(1).trim();
	else
	  ManageComName = "hehe";
}catch(SQLException e) { out.println(e.toString());}

System.out.println("选择管理机构：" + ManageCom);
if(ManageCom.length() == 2) 
	ManageCom += "        ";
else if(ManageCom.length() == 4)
	ManageCom += "      ";
else if(ManageCom.length() == 8)
	ManageCom += "  ";

StringBuffer menu=new StringBuffer();

menu.append("<table border=1 cellpadding=0 cellspacing=0 class=FixMenu>");
menu.append("<tr><td class=OutMenu onClick=\"SubThis('"+ManageCom+"')\" onMouseOver=\"OverItem(this,'"+ManageComName+"');turnLayer('"+ManageCom+"','show')\" onMouseOut=\"OutItem(this);turnLayer('"+ManageCom+"','hide')\"><img src=\"../pic/more.gif\" align='right'>("+ManageCom+") "+ManageComName+"");
menu.append("<div id=\""+ManageCom+"\" style=\"width:220px; position:absolute; left:118px; top:0px; visibility: hidden;\"onMouseOut=\"turnLayer('"+ManageCom+"','hide')\" >");
MenuList(ManageCom,ManageComName,0,menu,true);
menu.append("</div> </td> </tr> </table>");
//System.out.println(menu.toString());
%>
<html>
<head>
<title>机构树状图</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="JavaScript" type="text/JavaScript" src="../js/Basic.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../js/MenuStatus.js"></script>
<SCRIPT src="../../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript" type="text/JavaScript">
var OverMenu="OverMenu";
var OutMenu="OutMenu";
var path="FullName";
function SubThis(cId){
	if(!sbmt){//Deal with this Event only once;
		sbmt=true;
                findInDoc("Comcode").value=cId;
								findInDoc("menu").submit();
								top.opener.fm.all('ManageCom').value = trim(cId);
                top.opener.fm.all('selectBtn').disabled = false;
                top.close();
	}
}
</script>
<LINK href="../../common/css/Project.css" rel=stylesheet type=text/css>
<link href="../css/MenuTree.css" rel="stylesheet" type="text/css">
</head>
<body>
<div style="position:absolute; left:0px; top:0px; width:98px; height:55px; z-index:1"><table border="1" cellpadding="0" cellspacing="0" class="FixMenu"><%=menu.toString()%></table></div>
<form name="menu" id="menu" action="" method="post"><p>
    <input name="FullName" type="hidden" id="FullName">
    <input name="Comcode" type="hidden" id="Comcode">
</p>
</form>
</body>
</html>
<%
conn.close();
%>
