<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//�������ƣ�ybtBalanceQuerySQL.jsp
//�����ܣ�sql�༭ҳ��ģ��
//          SQLID��SQL�Ǳ�����
//�������ڣ�2008-8-27 09:29
//������  ��yeyl
%>

<%
//���������²��ֱ༭SQL
if(SQLID.equals("easyQuery1"))
{    	
        
				
		SQL = " select "
				+ " (select codename from ldcode where codetype='trancom_bank' and code=a.trancom), "
				+ " a.NodeNo, a.trandate,"
				+ " case rcode when 1 then '�ɹ�' when 0 then 'ʧ��' end,"
				+ " (select name from ldcom where comcode=a.manageCom)"
				+ " from contblcdtl a where "
				+ "  ManageCom like '?managecom?%'"
				+ getWherePart(request, "a.trancom")
				
				+ getWherePart(request, "a.trandate")
				+ " and a.trandate between '?StartDate?' and '?EndDate?' "
				// �������һ�ζ��˵���ˮ��
				+ " and a.ContNo=(select ContNo from contblcdtl where rownum = 1  and trandate=a.trandate and trancom=a.trancom and nodeno=a.nodeno)"
				+ " order by trandate";
	        
}

%>
<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>
