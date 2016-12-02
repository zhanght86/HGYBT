<%@include file="../jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>


<%
  try{
   System.out.println("start");
   XmlExport xe = new XmlExport();
   xe.createDocument("CardAppReport.vts", "printer");
   ListTable lt = new ListTable();
   lt.setName("Info");
   for(int nIndex = 0; nIndex < turnPage.arrRowData.lenght; nIndex ++) {
     lt.add( turnPage.arrRowData[nIndex] );
     
   }
    values = new String[nLen];
    values[0] = "AppTime";
    values[1] = "AppCom";
    values[2] = "CertifyCode";
   
   xe.addListTable(lt, values);
   
   if (txmlExport==null)
   {
     System.out.println("null");
   }
   session.putValue("PrintStream", xe.getInputStream());
   System.out.println("put session value");
   response.sendRedirect("../../f1print/GetF1Print.jsp");
  }catch(Exception ex){
		
		Content = strOperation+"Ê§°Ü£¬Ô­ÒòÊÇ:" ;
  }
	
%>
<html>
<%@page contentType="text/html;charset=gb2312" %>
<script language="javascript">	
	alert("<%=Content%>");
	top.close();
	
	//window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");	
	
</script>
</html>
<%
  }
%>