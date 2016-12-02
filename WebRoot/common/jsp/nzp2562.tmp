<!--
*******************************************************
* 程序名称：Message.jsp
* 程序功能：信息显示页面
* 创建日期：2002-05-10
* 更新记录：  更新人    更新日期     更新原因/内容
*             欧阳晟   2002-05-20    修改
*******************************************************
-->
<%@ page contentType="text/html;charset=GBK" %>

<html>
<head>
  <title>信息反馈</title>
  <!--公用函数-->
  <script src="../javaScript/Common.js"></script>
  <%--页面样式--%>
  <link rel="stylesheet" type="text/css" href="../css/Project.css">
<%
  String WINDOW     = "W";  //窗口
  String PAGE       = "P";  //页面

  String SUCCESS    = "S";  //成功
  String FAILURE    = "F";  //失败
  String COMMON     = "C";  //一般信息

  /* 得到编辑类型 */
  String Type       = request.getParameter("Type");
  String type       = request.getParameter("type");
  String Content    = request.getParameter("Content");
  String content    = request.getParameter("content");
  String Picture    = request.getParameter("Picture");
  String picture    = request.getParameter("picture");

  String strType    = "";
  String strContent = "";

  if(Content!=null)
    strContent = Content;
  else if(content!=null)
    strContent = content;
  else
    strContent = "";

  if(Type!=null)
    strType = Type;
  else if(type!=null)
    strType = type;
  else
    strType = PAGE;

  if(Picture!=null)
    Picture = Picture;
  else if(picture!=null)
    Picture = picture;
  else
    Picture = COMMON;

  if(Type!=null)
    strType = Type;
  else if(type!=null)
    strType = type;
  else
    strType = PAGE;

  if(strType.toUpperCase().equals(WINDOW))
    strType = WINDOW;
  else
    strType = PAGE;
%>

</head>
<body class="interface">
<%
  if(strType.equalsIgnoreCase(WINDOW))
  {
    out.println("<script language='javascript'>");
    out.println("   errorMessage('" + strContent + "');");
    out.println("</script>");
  }
  else
  {
	out.println("<script language='javascript'>");
    out.println("  top.fraInterface.location='MessagePage.jsp?Content=" + strContent +"&Picture=" + Picture +"'");
    out.println("</script>");
  }
%>
</body>
</html>