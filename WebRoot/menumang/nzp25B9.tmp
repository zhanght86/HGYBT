<%@page contentType="text/html;charset=GBK" %>
<%@include file = "../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：menuFunSQL.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：2009-3-10 09:29
//创建人  ：黄位富
%>

<%
//必须在以下部分编辑SQL
if(SQLID.equals("easyQuery1"))
{    	
     
		SQL = " select nodecode,ChildFlag,nodename,parentnodecode,runscript,nodeorder,nodesign "
		    + " from LDMenu order by nodeorder";  
}
else if(SQLID.equals("easyQuery2"))
{    	
 		SQL = " select nodecode,ChildFlag,nodename,parentnodecode,runscript,nodeorder,nodesign from LDMenu "
		    + " where nodename like  '?NodeName?%' "
				+ " order by nodeorder";
}



%>

<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
