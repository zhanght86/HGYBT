<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>


<%

session.removeAttribute("SQLString");
if(SQLID.equals("notContQuery"))
{
      	SQL= "  select c.contno ,c.ProposalPrtNo ,c.AppntName ,"
          +" c.InsuredName ,c.ProductId , c.Bak1 ,"
          +" c.amnt/100  ,c.prem/100  , "
          +" case when c.State = '0' then '录单' "
          +" when c.State = '2' then '签单' "
          +" when c.State = '3' then '撤单' else '' end, "
          +" c.SignDate  ,c.trancom ,c.nodeno  ,c.agentname, c.Bak2  "
          +" from cont c " 
          +" where 1=1 and c.state in ('0','2','3') ";
					  
         
        
        
}

else if(SQLID.equals("ContQuery"))
{
	  SQL= "  select c.contno ,c.ProposalPrtNo ,c.AppntName ,"
          +" c.InsuredName ,c.ProductId , c.Bak1 ,"
          +" c.amnt/100  ,c.prem/100  , "
          +" case when c.State = '0' then '录单' "
          +" when c.State = '2' then '签单' "
          +" when c.State = '3' then '撤单' else '' end, "
          +" c.SignDate  ,c.trancom ,c.nodeno  ,c.agentname, c.Bak2  "
          +" from cont c "
          +" where 1=1 and c.state in ('0','2','3') ";
								 
}
session.setAttribute("SQLString",SQL);
%>


<%

request.setAttribute("EASYQUERYSQL",SQL);
session.setAttribute("SQLString",SQL);
%>

