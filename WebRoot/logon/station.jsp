<%@page contentType="text/html;charset=gb2312" %>
<!--*******************************************************
* �������ƣ�station.jsp
* �����ܣ�ϵͳ����ҳ��
* ��������ˣ������
* ����������ڣ�2005-3-21 14:45
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
    ls = "���ڵ�λ�ã���ҳ";
}
else
{
    if (nodecode.equals("60001"))
    {
        ls = "���ڵ�λ�ã������޸�";
    }
    else
    {
        MenuShow menuShow = new MenuShow();

        ExeSQL exeSQL = new ExeSQL();
        String strNodeCode = exeSQL.getOneValue("SELECT NodeCode FROM LDMenu WHERE NodeCode = '" + nodecode + "'");

        if( strNodeCode == null || strNodeCode.equals("") ) {

            ls = "���ִ���Ĳ˵��ڵ㣬����ϵͳά����Ա��ϵ��";
            strFlag = "01";

        } else {

            ls = menuShow.getStation(nodecode);
            ls = ls.substring(0,ls.length()-3);

            String tSql = "insert into LDUserTrace ( ManageCom,Operator,TraceType,TraceContent,ClientIP,MakeDate,MakeTime ) " +
                "values ( '"+tG.ComCode+"','"+tG.Operator+"','00','"+ls.substring(6)+"','"+Ip+"','"+PubFun.getCurrentDate()+"','"+PubFun.getCurrentTime()+"' )";
            
            ExeSQL tExeSQL = new ExeSQL();
            //ִ���û��켣����
            try
            {
                // ��̩���û��LDUserTrace�����,���в��ټ�¼ modify by hwf at 2008-7-29
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
  alert("���ִ���Ĳ˵��ڵ㣬����ϵͳά����Ա��ϵ��");
}

</script>
<link rel='stylesheet' type='text/css' href='../common/css/other.css'>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0" oncontextmenu="return false">
    <table width=100% height="25" cellspacing="0">
        <tr>
            <th width="10%" align="left" id=menushow style="display:none" name="menushow">
                &nbsp;&nbsp;
                <a href="#" onclick="showmenu()"><img src="../common/images/t_open.gif" width="70" height="13" border="0" title="��ʾ�˵���"></a>
            </th>
            <th width="40%" align="left">&nbsp;&nbsp;<font style="font-size:9pt" color="#FFFFFF">��¼������<%=ManageCom%></font></th>
            <th width="50%" align="right"><font style="font-size:9pt" color="#FFFFFF"><%=ls%>&nbsp;</th>
        </tr>
    </table>
</body>
</html>
