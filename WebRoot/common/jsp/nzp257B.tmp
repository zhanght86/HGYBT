  
<%
//程序名称：
//程序功能：页面从新定向
//创建日期：2002-03-12
//创建人  ：yt
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.io.File"%>

<%

String ShowJspPage   = request.getParameter("pageName");           //当前险种的编辑页面
                                                        //当前系统暂时定为该页面

//浏览重定向到对应险种的编辑页面 
//String PathName = ( new File(request.getPathTranslated()) ).getParent();     //取当前路径
//File inputFile  = new File(PathName,ShowJspPage);                            //得到对应的文件对象

//if( inputFile.exists() )
//{
//  System.out.println(ShowJspPage);
  System.out.println(ShowJspPage);
  response.sendRedirect(ShowJspPage);
//}
//else 
//{    
//  String Content = "没有相应的页面" ;
//%>  
                         
