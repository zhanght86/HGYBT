
<%
//程序名称：NewEasyQuery.jsp
//程序功能：查询等待页面
//创建日期：2002-09-28
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="./EasyQueryKernel.jsp"%>


<%
  String tSQL = request.getParameter( "sql" );
  String strStart = request.getParameter( "strStart" );
  System.out.println("EasyQuery Start:"+tSQL + ":" + strStart);	
							
  String tResult = easyQueryKernel(tSQL, strStart);
  System.out.println("EasyQuery End:"+tResult);	
			
  if (tResult == "") {
%>
  <script language="javascript">   
    window.opener.queryFaile();	
    window.close();			   				   		
  </script>
<%			
  } else {
%>
  <script language="javascript">
  var strResult = '<%=tResult%>';	 
			   		
  //用window.open打开时用以下代码		   					   		
  window.opener.transferQueryResult( strResult ); 
  window.opener.afterEasyQuery( strResult );	
  
  //alert("Query Faile!");
  window.close();		   				   		
  </script>
<%
  }		
%>
