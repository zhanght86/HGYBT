 

<%@page contentType="text/html;charset=GBK" %>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.menumang.*"%>
           
<SCRIPT src="menuGrp.js"></SCRIPT>
                                      
<html>
<body>
<%
    System.out.println("start query...");
    
    String MenuGrpCode = request.getParameter("MenuGrpCode");
    if (MenuGrpCode == null)
      MenuGrpCode = "1998";
      
    System.out.println(MenuGrpCode);
    
  
    System.out.println(MenuGrpCode);
    LDMenuGrpSchema grpSchema = new LDMenuGrpSchema();
    grpSchema.setMenuGrpCode(MenuGrpCode);
    VData inputData = new VData();
    LDMenuQueryBL tMenuQueryBL = new LDMenuQueryBL();
    inputData.add(grpSchema);
    tMenuQueryBL.submitData(inputData,"query");
 
    inputData = tMenuQueryBL.getResult();
    
    LDMenuSet tLDMenuSet = new LDMenuSet();
    tLDMenuSet = (LDMenuSet)inputData.getObjectByObjectName("LDMenuSet",0);
    if (tLDMenuSet == null)
        System.out.println("null");
    else {          
    	LDMenuSchema tLDMenuSchema;
        for (int i = 1; i <= tLDMenuSet.size(); i++) {
            System.out.println(i);
            tLDMenuSchema = (LDMenuSchema)tLDMenuSet.getObj(i);
            String NodeCode = tLDMenuSchema.getNodeCode();
            String NodeName = tLDMenuSchema.getNodeName(); 

	
            System.out.println(NodeCode);
            System.out.println(NodeName); 
                       
          %>
   
 <script language="javascript">    
            parent.fraInterface.menuGrpGrid.addOne("menuGrpGrid");
            parent.fraInterface.menuGrpGrid.setRowColData(<%=i-1%>,1,'<%=NodeName%>');
            parent.fraInterface.menuGrpGrid.setRowColData(<%=i-1%>,2,<%=NodeCode%>);      
                        
</script> 
<%        
       }
   }
    %>	
    
 <%  //调入没有选入菜单组的菜单节点
   LDMenuSchema tLDMenuSchema = new LDMenuSchema();
   inputData.clear();
   inputData.add(tLDMenuSchema);
   tMenuQueryBL.submitData(inputData,"query");
   inputData = tMenuQueryBL.getResult();
   tLDMenuSet = (LDMenuSet)inputData.getObjectByObjectName("LDMenuSet",0);
 
   if (tLDMenuSet == null)
        System.out.println("null");
    else {
    
        for (int i = 1; i <= tLDMenuSet.size()&& i <= 5; i++) {
            System.out.println(i);
            tLDMenuSchema = (LDMenuSchema)tLDMenuSet.getObj(i);
            String NodeCode = tLDMenuSchema.getNodeCode();
            String NodeName = tLDMenuSchema.getNodeName(); 


            System.out.println(NodeCode);
            System.out.println(NodeName); 
          %>
   
            
 <script language="javascript">    
            parent.fraInterface.unSelectMenuGrpGrid.addOne("unSelectMenuGrpGrid");
            parent.fraInterface.unSelectMenuGrpGrid.setRowColData(<%=i-1%>,1,'<%=NodeName%>');
            parent.fraInterface.unSelectMenuGrpGrid.setRowColData(<%=i-1%>,2,<%=NodeCode%>);      
            
            
</script> 
<%        
       }
   }
    %>	    
    
</body>
</html>