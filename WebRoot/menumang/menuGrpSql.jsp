<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：
//创建人  ：
%>

<%
//必须在以下部分编辑SQL
if(SQLID.equals("menuGrp0"))
{
	SQL="select MenuGrpName,MenuGrpCode,MenuGrpDescription,MenuSign,Operator from LDMenuGrp where 1=1";
	SQL+=getWherePart( request, "MenuGrpName" );
	SQL+=getWherePart( request, "MenuGrpCode" );
	SQL+=getWherePart( request, "MenuGrpDescription" );
	SQL+=getWherePart( request, "MenuSign" );

}
if(SQLID.equals("menuGrp1"))
{
	SQL="select ParentNodeCode,ChildFlag,nodename,nodecode,NodeSign from LDMenu order by nodeorder";
}
if(SQLID.equals("menuGrp2"))
{

        String Operator=(String)request.getAttribute("OPERATOR");
        System.out.println("aaaaaaaaaaaaaaaa");
         System.out.println(Operator);       
        if(Operator.equals("001"))
        {
	    SQL="select nodecode from LDMenu order by nodeorder	";
	}
	else
	{
	    SQL="select nodecode from LDMenu 	";
        SQL += " where nodecode in (select nodecode from ldmenugrptomenu ";
        SQL += " where menuGrpCode in (select menuGrpCode from ldusertomenugrp where 1=1";
        SQL += getWherePart( request, "usercode","Operator" );
        SQL += ")) order by nodeorder";
  	
	}    
}
if(SQLID.equals("menuGrp4"))
{
	SQL="select ParentNodeCode,ChildFlag,nodename,nodecode,NodeSign from LDMenu order by nodeorder";
}

if(SQLID.equals("menuGrp5"))
{



	    SQL="select nodecode from LDMenu where nodecode in ";
        SQL += " (select nodecode from ldmenuGrpTomenu where 1=1 ";
         SQL += getWherePart( request, "menuGrpCode" );
        SQL += ") ";
  
}

%>

<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
