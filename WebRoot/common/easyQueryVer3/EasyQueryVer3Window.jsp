<%@page contentType="text/html;charset=GBK" %>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<%
//程序名称：EasyQueryVer3Window.jsp
//程序功能：查询等待页面，负责调用后台查询，并接收返回结果
//          必须以window.showModalDialog方式打开这个窗口
//创建日期：2002-10-19
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="./EasyQueryKernel.jsp"%>
<title>正在查询数据</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<%
String strResult = "";

//String strSql = new String (request.getParameter("strSql").getBytes("ISO8859_1"));
String strSql = request.getParameter("strSql");
String strStart = request.getParameter("strStart");
String strLargeFlag = request.getParameter("LargeFlag");
String strLimitFlag = request.getParameter("LimitFlag");

//System.out.println("---EasyQuery BGN---");
try
{
    strResult = easyQueryKernel(strSql, strStart, strLargeFlag, strLimitFlag);
}
catch(Exception ex)
{
    System.out.println("easyQueryKernel throw Errors!\n" + "easyQuerySql:" + strSql +"\nStartRecord:" +strStart);
}
//System.out.println("---EasyQuery END---");

try
{
    //做了一步特殊字符替换，可否考虑先判定是否含有特殊字符，然后再作处理
    //对于有回车的数据取出的可能性太小了
    if(strResult.indexOf("\"")!= -1 || strResult.indexOf("'")!= -1)
    {
        String strPath = application.getRealPath("config//Conversion.config");
        strResult = StrTool.Conversion(strResult, strPath);
    }
}
catch(Exception ex)
{
    System.out.println("not found Conversion.config ");
}

//System.out.println("strResult is : "+strResult);

if (strResult.equals(""))
{
%>
<script language="JavaScript">
if (typeof(window.opener) == "object")
{
    window.opener.afterEasyQueryVer3("Easy Query Faile");
    window.close();
}
else
{
    //window.returnValue = "Easy Query Faile";
    window.returnValue = false;
    window.close();
//  strQueryResult = false;
}
</script>
<%
}
else
{
%>
<script language="JavaScript">
if (typeof(window.opener) == "object")
{
    window.opener.transferQueryResult('<%=strResult%>');
//  window.opener.afterEasyQueryVer3('<%=strResult%>');
    window.opener.afterEasyQueryVer3("ok");
    window.close();
}
else
{
    window.returnValue = '<%=strResult%>';
    window.close();
//  strQueryResult = '<%=strResult%>';
}
</script>
<%
}
%>