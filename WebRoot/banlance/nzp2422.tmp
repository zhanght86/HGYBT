<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//程序名称：ybtBalanceQuerySQL.jsp
//程序功能：sql编辑页面模版
//          SQLID和SQL是保留字
//创建日期：2008-8-27 09:29
//创建人  ：yeyl
%>

<%
//必须在以下部分编辑SQL
if(SQLID.equals("easyQuery1"))
{    	
        
				
		SQL = " select "
				+ " (select codename from ldcode where codetype='trancom_bank' and code=a.trancom), "
				+ " a.NodeNo, a.trandate,"
				+ " case rcode when 1 then '成功' when 0 then '失败' end,"
				+ " (select name from ldcom where comcode=a.manageCom)"
				+ " from contblcdtl a where "
				+ "  ManageCom like '?managecom?%'"
				+ getWherePart(request, "a.trancom")
				
				+ getWherePart(request, "a.trandate")
				+ " and a.trandate between '?StartDate?' and '?EndDate?' "
				// 今天最后一次对账的流水号
				+ " and a.ContNo=(select ContNo from contblcdtl where rownum = 1  and trandate=a.trandate and trancom=a.trancom and nodeno=a.nodeno)"
				+ " order by trandate";
	        
}

%>
<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
