<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,java.util.*" errorPage="" %>
<%@page import="com.sinosoft.utility.*,com.sinosoft.lis.pubfun.GlobalInput"%>

<%
	GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	if (tG1 == null) {
		session.putValue("GI",null);
		out.println("网页超时，请您重新登录");
	}
		
	String manageCom = tG1.ManageCom;
	String ManageCom = request.getParameter("ManageCom");
	String upAgent   = request.getParameter("upAgent");
	String BankType   = request.getParameter("BankType");
	String BranchType   = request.getParameter("BranchType");
	String ACType   = request.getParameter("ACType");
	//中介协议维护专用，要求已经签订中介协议的中介机构在树状图中不显示，默认为1，显示全部，为0则不显示
	String AllType  = request.getParameter("AllType");
	if(ACType == null || ACType.trim().equals(""))
		ACType = "-1";
	if(ManageCom == null || ManageCom.equals(""))
		ManageCom = manageCom;	
	if(BranchType==null || BranchType.equals(""))
	  BranchType = "3";
	if(AllType == null || AllType.trim().equals(""))
		AllType = "1";
%>
<%@include file="BankMenuMaker.jsp" %>
  
<%
request.setCharacterEncoding("GB2312");

	
/*
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
*/

StringBuffer menu=new StringBuffer();

if (BankType == null) BankType = "";
MenuList(ManageCom,null,null,0,menu,true,BankType,BranchType,ACType,AllType);
%>
<html>
<head>
<title>机构树状图</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="JavaScript" type="text/JavaScript" src="../js/Basic.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../js/MenuStatus.js"></script>
<SCRIPT src="../../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../../common/easyQueryVer3/easyQueryVer3.js"></SCRIPT>
<script language="JavaScript" type="text/JavaScript">
var OverMenu="OverMenu";
var OutMenu="OutMenu";
var path="FullName";
function SubThis(cId){
	if(!sbmt){//Deal with this Event only once;
		sbmt=true;
                findInDoc("Comcode").value=cId;
								findInDoc("menu").submit();
								//top.opener.fm.all('UpAgentCom').value = trim(cId);
                //top.opener.fm.all('selectBank').disabled = false;
                top.opener.afterSelectBank(trim(cId));
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
rs.close();
conn.close();
%>
