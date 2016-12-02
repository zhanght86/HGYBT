<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：2006-04-25
//创建人  ：常亮
%>

<%
//必须在以下部分编辑SQL
if(SQLID.equals("UserAddInput_1"))
{
	SQL="select MenuGrpCode,MenuGrpDescription from LDMenuGrp where Operator='?Operator?' and trim(MenuGrpCode) in  ( select trim(MenuGrpCode) from LDUserToMenuGrp where UserCode='?UserCode?' )" ;
}

if(SQLID.equals("UserAddInput_2"))
{
	SQL="select MenuGrpCode,MenuGrpDescription from LDMenuGrp where Operator='?Operator?' and trim(MenuGrpCode) not in  ( select trim(MenuGrpCode) from LDUserToMenuGrp where UserCode='?UserCode?' )" ;
}

if(SQLID.equals("UserAddInput_3"))
{
	SQL="select ParentNodeCode,ChildFlag,nodename,nodecode from LDMenu "
		  +"where NodeCode in (select NodeCode from LDMenuGrpToMenu "
		  +"where trim(MenuGrpCode) = '?MenuGrpCode?') order by nodeorder";
}

if(SQLID.equals("UserAddInput_4"))
{
	SQL="select ParentNodeCode,ChildFlag,nodename,nodecode from LDMenu where NodeCode in (select NodeCode from LDMenuGrpToMenu where trim(MenuGrpCode) = '?MenuGrpCode?') order by nodeorder ";
}

if(SQLID.equals("UserAddInput_5"))
{
	SQL="select MenuGrpCode,MenuGrpDescription from LDMenuGrp where  Operator = '?Operator?'";
}

if(SQLID.equals("UserAddInput_6"))
{
	SQL="select UserName from LDUser where UserCode ='?UserCode?'";
}
%>

<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>