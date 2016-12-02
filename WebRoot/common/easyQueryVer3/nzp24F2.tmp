<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.io.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="./EasyQueryKernel.jsp"%>
<%
//程序名称：EasyQueryXML.jsp
//程序功能：查询等待页面，负责调用后台查询，并接收返回结果
//创建日期：2005-6-3 8:48
//创建人  ：涂强
//修改人  ：朱向峰  2006-4-13   设置Referer＆Host的校验
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);


String strResult = "";

String tReferer = request.getHeader("Referer");//获得申请此页的url信息
//System.out.println(request.getHeader("Referer"));
String tHost = "http://" + request.getHeader("host");//获取应用的url信息
String tHost1 = "https://" + request.getHeader("host");//获取应用的url信息
//System.out.println(tHost);

//校验请求此页面的url是否是应用框架内的页面
//System.out.println("tReferer:" + tReferer);
//System.out.println("tHost:" + tHost);
//System.out.println("tHost1:" + tHost1);
if(tReferer == null || (!tReferer.startsWith(tHost) && !tReferer.startsWith(tHost1)))
{
    System.out.println("不是一个地址的请求服务");
%>
<script language=javascript>
session = null;
try
{
    CollectGarbage();
}
catch(ex)
{
    alert(ex.message);
}
top.window.location ="../../indexlis.jsp";
</script>
<%
//  response.sendRedirect("../../indexlis.jsp");
}
else
{
    InputStream ins = request.getInputStream();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    int nChar = 0;

    nChar = ins.read();
    while( nChar != -1 ) {
        baos.write(nChar);
        nChar = ins.read();
    }

    /* Kevin 2006-08-04
     * The default encoding used by XMLHTTP is UTF-8
     *
     * Pay attention, don't use getParameter().
     * Because that you have to encode the query string in application/x-www-form-urlencoded form in js
     * to make sure you can get the correct content here, and I can't find a method to encode Chinese
     * Character well in js, so I use getInputStream() instead.
     *
     * BTW: You can find VBScript that can perform this task in the internet.
     *
     */

    String[] strParams = new String(baos.toByteArray(), "UTF-8").split("&");

    //设置request的接受字符集
    String strSql = strParams[0];
    String strStart =strParams[1];
    String strLargeFlag = strParams[2];
    String strLimitFlag = strParams[3];
    if(strSql.indexOf(".jsp")!=-1)
    {
    EasyQuerySql tEasyQuerySql=new EasyQuerySql();
    if (tEasyQuerySql.parsePara(strSql))
    {
    //System.out.println("EASYQUERY RAW SQL==="+strSql);
    String jspName=tEasyQuerySql.getJspName();
    //System.out.println("EASYQUERY JSP'S Name IS===="+jspName);
    request.setAttribute("EASYQUERYSQLID",tEasyQuerySql.getSqlId());
    for (int i = 0; i < tEasyQuerySql.getParaCount(); i++)
    {
        request.setAttribute(tEasyQuerySql.getParaName(i).toUpperCase(),tEasyQuerySql.getParaValue(i));
    }
  %>
  <jsp:include page="<%= jspName%>" />

  <%
    strSql=(String)request.getAttribute("EASYQUERYSQL");

    //System.out.println(strSql);
    strSql=tEasyQuerySql.convertToValue(strSql);
    //System.out.println("===EASYQUERY CONVERT SQL==="+strSql);
    }
    }
    try
    {
        strResult = easyQueryKernel(strSql, strStart, strLargeFlag, strLimitFlag);
    }
    catch(Exception ex)
    {
        System.out.println("easyQueryKernel throw Errors!\n" + "easyQuerySql:" + strSql +"\nStartRecord:" +strStart);
    }

    try
    {
        //做了一步特殊字符替换，可否考虑先判定是否含有特殊字符，然后再作处理
        //对于有回车的数据取出的可能性太小了
        if(strResult.indexOf("\"")!= -1 || strResult.indexOf("'")!= -1 || strResult.indexOf("\n")!= -1)
        {
            String strPath = application.getRealPath("config//Conversion.config");
            strResult = StrTool.Conversion(strResult, strPath);
        }
    }
    catch(Exception ex)
    {
        System.out.println("not found Conversion.config ");
    }
}
%>
<%=strResult%>
