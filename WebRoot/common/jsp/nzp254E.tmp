
<%
//程序名称：EasyQueryKernel.jsp
//程序功能：EasyQuery查询功能的核心函数
//创建日期：2002-09-28
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容       
%>

<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%!
// 调用EasyQueryUI进行SQL语句提交和数据库查找，返回结果字符串strResult
public String easyQueryKernel(String strSql, String strStart) { 
  String strResult = "";
  String strError = "";
  Integer intStart = new Integer( String.valueOf( strStart ));
  //System.out.println("strSql:" + strSql +"|strStart:" +strStart);

  EasyQueryUI tEasyQueryUI = new EasyQueryUI();
  VData tVData = new VData();
  tVData.add( strSql );
  tVData.add( intStart );
  tEasyQueryUI.submitData( tVData, "QUERY||MAIN" );

  if( tEasyQueryUI.mErrors.needDealError()) {
	  strError = tEasyQueryUI.mErrors.getFirstError();
  } else {
	  tVData.clear() ;
	  tVData = tEasyQueryUI.getResult() ;
	  strResult = ( String )tVData.getObject( 0 );
	  System.out.println(strResult);
  }
  
  return strResult;
}
%>
