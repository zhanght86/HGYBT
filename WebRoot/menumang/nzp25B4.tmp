
<%@page contentType="text/html;charset=GBK" %>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>

<%@page import="com.sinosoft.lis.menumang.*"%>
           
<SCRIPT src="menuGrp.js"></SCRIPT> 
                         
<html>
<body>                                      

<script language="javascript">    
       
            parent.fraInterface.SelectMenuGrpGrid.setRowColData(<%=0%>,1,<%=0%>);
            parent.fraInterface.SelectMenuGrpGrid.setRowColData(<%=0%>,2,<%=439%>);               
</script> 	    
<%
  int index=0;
  int TempCount=0;    
  String tTempClassNum1[] = request.getParameterValues("unSelectMenuGrpGrid_tempNo");
  String tTempClassNum2[] = request.getParameterValues("SelectMenuGrpGridNo");
  String tNodeCode[] = request.getParameterValues("unSelectMenuGrpGrid_temp2");
  String tNodeName[] = request.getParameterValues("unSelectMenuGrpGrid_temp1");
  
//得到check列的数组
  String tChk[] = request.getParameterValues("InpunSelectMenuGrpGridChk");

  System.out.println("start adding..");
  
  if(tTempClassNum1==null)
      System.out.println("really start adding..");  
      
  if(tTempClassNum1!=null)//如果不是空纪录	
  {
   
   TempCount = tTempClassNum1.length; //记录数      
   int selectMenuNum = tTempClassNum2.length;
   System.out.println("Start Save Count="+TempCount);   
   while(index<TempCount)
    {
    	if(!tChk[index].equals("1")) {
    	    index++;
    	    continue;
    	}
<script language="javascript">    
            parent.fraInterface.selectMenuGrpGrid.addOne("SelectMenuGrpGrid");
            parent.fraInterface.selectMenuGrpGrid.setRowColData(<%=selectMenuNum-1%>,1,'<%=tNodeName[index]%>');
            parent.fraInterface.selectMenuGrpGrid.setRowColData(<%=selectMenuNum-1%>,2,<%=tNodeCode[index]%>);               
</script> 	    

	    
<%	selectMenuNum++;
	index=index+1;          
    }
   }
   
%>  
</html>
</body>