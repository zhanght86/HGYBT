<%@page contentType="text/html;charset=gb2312" %>
<!--*******************************************************
* 程序名称：station.jsp
* 程序功能：系统标题页面
* 最近更新人：朱向峰
* 最近更新日期：2005-3-21 14:45
*******************************************************-->
<%@page import="com.sinosoft.lis.logon.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%
String nodecode = request.getParameter("nodecode");
String Ip = request.getParameter("Ip");
String ls="";
String strFlag = "00";

GlobalInput tG = (GlobalInput)session.getValue("GI");
String ManageCom = tG.ManageCom;

if ((nodecode==null) || (nodecode.trim().equals("")))
{
    ls = "现在的位置：首页";
}
else
{
    if (nodecode.equals("60001"))
    {
        ls = "现在的位置：密码修改";
    }
    else
    {
        MenuShow menuShow = new MenuShow();

        ExeSQL exeSQL = new ExeSQL();
        String strNodeCode = exeSQL.getOneValue("SELECT NodeCode FROM LDMenu WHERE NodeCode = '" + nodecode + "'");

        if( strNodeCode == null || strNodeCode.equals("") ) {

            ls = "发现错误的菜单节点，请与系统维护人员联系。";
            strFlag = "01";

        } else {

            ls = menuShow.getStation(nodecode);
            ls = ls.substring(0,ls.length()-3);

            String tSql = "insert into LDUserTrace ( ManageCom,Operator,TraceType,TraceContent,ClientIP,MakeDate,MakeTime ) " +
                "values ( '"+tG.ComCode+"','"+tG.Operator+"','00','"+ls.substring(6)+"','"+Ip+"','"+PubFun.getCurrentDate()+"','"+PubFun.getCurrentTime()+"' )";
            
            ExeSQL tExeSQL = new ExeSQL();
            //执行用户轨迹操作
            try
            {
                // 瑞泰这边没有LDUserTrace这个表,所有不再记录 modify by hwf at 2008-7-29
                //tExeSQL.execUpdateSQL(tSql);
            }
            catch(Exception ex)
            {
                System.out.println("dsfjklfajslkd");
                ex.printStackTrace();
            }
        }
    }
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="JavaScript">
function showmenu()
{
    if(parent.fraSet.cols=="0%,*,0%")
    {
        document.all("menushow").style.display = "none";
        parent.fraSet.cols = "180,*,0%";
    }
}

if ( "01" == "<%= strFlag %>" )
{
  alert("发现错误的菜单节点，请与系统维护人员联系。");
}

</script>
<link rel='stylesheet' type='text/css' href='../common/css/other.css'>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0" oncontextmenu="return false">
    <table width=100% height="25" cellspacing="0">
        <tr>
            <th width="10%" align="left" id=menushow style="display:none" name="menushow">
                &nbsp;&nbsp;
                <a href="#" onclick="showmenu()"><img src="../common/images/t_open.gif" width="70" height="13" border="0" title="显示菜单栏"></a>
            </th>
            <th width="40%" align="left">&nbsp;&nbsp;<font style="font-size:9pt" color="#FFFFFF">登录机构：<%=ManageCom%></font></th>
            <th width="50%" align="right"><font style="font-size:9pt" color="#FFFFFF"><%=ls%>&nbsp;</th>
        </tr>
    </table>
</body>
</html>
