<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：ComQuerySQL.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：2009-3-10 09:29
//创建人  ：黄位富
%>

<%
//必须在以下部分编辑SQL
if(SQLID.equals("easyQuery1"))
{    	
     
		SQL = "select ComCode,Name,Address,Phone,SatrapName from LDCom where 1=1"
	      + getWherePart(request, "ComCode") 
	      + getWherePart(request, "OutComCode") 
	      + getWherePart(request, "Name") 
	      + getWherePart(request, "ShortName") 
	      + getWherePart(request, "Address") 
	      + getWherePart(request, "ZipCode") 
	      + getWherePart(request, "Phone") 
	      + getWherePart(request, "Fax") 
	      + getWherePart(request, "EMail") 
	      + getWherePart(request, "WebAddress") 
	      + getWherePart(request, "UpComCode")   
	      + " order by ComCode";
}
else if(SQLID.equals("easyQuery2"))
{    	
    //'1'是标志位,必须是先经过查询才能删除,modify by hwf at 2009年2月6日
		SQL = " select ComCode,OutComCode,Name,ShortName,Address,ZipCode,Phone,Fax,EMail,WebAddress,UpComCode,'1' "
		    + " from LDCom where ComCode='?ComCode?'"; 
}
else if(SQLID.equals("easyQuery3"))
{
       
    
    SQL = "select Comcode from LDCom where 1=1 "
	      + getWherePart(request, "ComCode");
}


%>

<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
