<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：EasyQueryFunc.jsp
//程序功能：EasyQuery查询功能的辅助函数
//
//创建日期：2006-04-21
//创建人  ：莫郊
%>
<%!
public String getWherePart(HttpServletRequest request,String fieldName)
{
	return getWherePart(request,fieldName,fieldName,"=","0");
}
public String getWherePart(HttpServletRequest request,String fieldName, String controlName)
{
	return getWherePart(request,fieldName,controlName,"=","0");
}

public String getWherePart(HttpServletRequest request,String fieldName, String controlName, String strOperate)
{
	return getWherePart(request,fieldName,controlName,strOperate,"0");
}

//函数名称：getWherePart
//参数说明：
//request:传入JSP保留字即可
//fieldName:数据库中的字段名称
//controlName:页面中传来的值的名称
//strOperate：操作符号，如=,like,>,>=,<,<=
//fieldType:"0"表示值是字符类型，"1"表示是数字类型
//返回值：sql的WHERE中的一个字句
public String getWherePart(HttpServletRequest request,String fieldName, String controlName, String strOperate, String fieldType)
{
	String strWherePart = "";
	String value = "";
	if(controlName == null || controlName.equals("")) controlName = fieldName;
	value=(String)request.getAttribute(controlName.toUpperCase());
	if(value == null || value.equals("")) return strWherePart;
	if(fieldType == null || fieldType.equals("")) fieldType = "0";
	if(strOperate == null || strOperate .equals("")) strOperate = "=";
	if(fieldType.equals("0"))
	{
		// 0:字符型
		if(strOperate.toUpperCase().equals("LIKE"))
		{
			strWherePart = " and " + fieldName.trim() + " like '" + value.trim() + "%'";
		}
		else
		  if(strOperate.toUpperCase().equals("IN"))
		  {
		  System.out.println("*********"+value.trim());
		   strWherePart = " and " + fieldName.trim() + "  in  " + value.trim() ;
		  }
		else
		{
			strWherePart = " and " + fieldName.trim() +  strOperate.trim() + "'" +  value.trim() + "' ";
		}
	}
	if(fieldType.equals("1"))
	{
		// 1:数字型
		strWherePart = " and " + fieldName.trim() + strOperate.trim() + value.trim() + " ";
	}
	return strWherePart;
}
public String getValue(HttpServletRequest request,String fieldName)
{
  String value=(String)request.getAttribute(fieldName.toUpperCase());
  if(value==null||value=="")
   {
   return "''";
   }
  else
   return  "'"+value.trim()+"'" ;
}
public String getManageComLimit(HttpSession session,String fieldName)
{
	GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
	return " and "+ fieldName + " like '"+ tGI.ManageCom+ "%'";
}

%>

<%
String SQLID="";
String SQL="";
SQLID=(String)request.getAttribute("EASYQUERYSQLID");
System.out.println("SQLID:"+SQLID);
%>