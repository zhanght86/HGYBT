<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%
//�������ƣ�ComQuerySQL.jsp
//�����ܣ�sql�༭ҳ��ģ��
//          SQLID��SQL�Ǳ�����
//�������ڣ�2009-3-10 09:29
//������  ����λ��
%>

<%
//���������²��ֱ༭SQL
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
    //'1'�Ǳ�־λ,�������Ⱦ�����ѯ����ɾ��,modify by hwf at 2009��2��6��
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
