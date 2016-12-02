<%
//更新记录： insert，delete
//更新人:  周磊
//更新日期:  2005-5-4  
//更新原因/内容：增加菜单标志。
%>

<%@page contentType="text/html;charset=GBK" %>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.menumang.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.sql.*"%>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>

<html>
<head>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
</head>
<body>
  <%
     System.out.println("start jsp");

     String FlagStr = "Fail";
     String actionStr = request.getParameter("Action");
 System.out.println("$$$actionStr:" + actionStr);
     int action = -1;
     if (actionStr.compareTo("query") == 0)
         action = 2;
     if (actionStr.compareTo("insert") == 0)
         action = 0;
     if (actionStr.compareTo("delete") == 0)
         action = 3;

     switch (action) {
       case 1:
       case 0:  //insert
     System.out.println("########################");
         String NodeName = request.getParameter("NodeName");
         String RunScript = request.getParameter("RunScript");
         String ischild = request.getParameter("isChild");
         String ischild2 = request.getParameter("isChild2"); //2005
         String tRadio[] = request.getParameterValues("InpQueryGrpGridSel");
         if (tRadio == null){
           System.out.println("tRadio is null");
           FlagStr = "Fail";
         }

         if (tRadio != null) {
           System.out.println("tRadio is not null");
           int index = 0;
           for (; index< tRadio.length;index++) {
             if(tRadio[index].equals("1"))
               break;
           }
           if (index == tRadio.length) {
             System.out.println("没有选中对象!");
             FlagStr = "Fail";
           }
           else{
             String tNodeCode[] = request.getParameterValues("QueryGrpGrid1");
             String tParentCode[] = request.getParameterValues("QueryGrpGrid3");
             String NodeCode = tNodeCode[index];
             String ParentCode = tParentCode[index];

          System.out.println("@@@@selNodeCode::"+NodeCode);
          System.out.println("@@@@ischild::"+ischild);

             LDMenuSchema tLDMenuSchema = new LDMenuSchema();
             tLDMenuSchema.setNodeName(StrTool.unicodeToGBK(NodeName));
             tLDMenuSchema.setRunScript(RunScript);
             tLDMenuSchema.setChildFlag("0");
             if (ischild.equalsIgnoreCase("true")){
               tLDMenuSchema.setParentNodeCode(NodeCode);
             }else{
               tLDMenuSchema.setParentNodeCode(ParentCode);
             }
             //页面权限菜单判断2005
             if (ischild2.equalsIgnoreCase("true")){
							 //1为一般菜单叶子，2为页面权限菜单叶子2005             
               tLDMenuSchema.setNodeSign("2");													
               }
             LDMenuFunUI tLDMenuFunUI = new LDMenuFunUI();
             TransferData tTransferData = new TransferData();
             tTransferData.setNameAndValue("ischild",ischild);
             tTransferData.setNameAndValue("ischild2",ischild2);
             tTransferData.setNameAndValue("NodeCode",NodeCode);
             tTransferData.setNameAndValue("RunScript",RunScript);
             VData tData = new VData();
             tData.add(tLDMenuSchema);
             tData.add(tTransferData);
             if(tLDMenuFunUI.submitData(tData,"insert")){
                 FlagStr = "success";}
	         else{
	             FlagStr = "fail";}
	        }
       }
       break;

       case 2:
           FlagStr = "success";
           break;

       case 3:
         System.out.println("here");
           String tRadio2[] = request.getParameterValues("InpQueryGrpGridSel");
           if (tRadio2 == null){
             System.out.println("tRadio is null");
             FlagStr = "Fail";
           }
           if (tRadio2 != null) {
               System.out.println("tRadio is not null");
               int index = 0;
               for (; index< tRadio2.length;index++) {
                 if(tRadio2[index].equals("1"))
                   break;
                   }
               if (index == tRadio2.length) {
                   System.out.println("没有选中对象!");
               }
               else{
               String tNodeCode2[] = request.getParameterValues("QueryGrpGrid1");
               String NodeCode = tNodeCode2[index];

            System.out.println("jsp--NodeCode:" + NodeCode);
               String tNodeCode3[] = request.getParameterValues("QueryGrpGrid2");
               String ChildFlag = tNodeCode3[index];

            System.out.println("jsp--ChildFlag:" + ChildFlag);
               String tNodeCode4[] = request.getParameterValues("QueryGrpGrid3");
               String ParentNodeCode = tNodeCode4[index];
               
            System.out.println("jsp--ParentNodeCode:" + ParentNodeCode);
            
            //获得菜单标志2005
               String tNodeCode5[] = request.getParameterValues("QueryGrpGrid7");
               String NodeSign = tNodeCode5[index];
            System.out.println("jsp--NodeSign:" + NodeSign);
               LDMenuSchema tLDMenuSchema = new LDMenuSchema();
               tLDMenuSchema.setNodeCode(NodeCode);
               tLDMenuSchema.setParentNodeCode(ParentNodeCode);
               tLDMenuSchema.setChildFlag(ChildFlag);
               tLDMenuSchema.setNodeSign(NodeSign);
               LDMenuFunUI tLDMenuFunUI = new LDMenuFunUI();
               VData tData = new VData();
               tData.add(tLDMenuSchema);
               if(tLDMenuFunUI.submitData(tData,"delete")){
                  FlagStr = "success";}
	           else{
                  FlagStr = "fail";}
	        }
     }
      break;
 }
 System.out.println("FlagStr : " + FlagStr);
 System.out.println("end of jsp");

%>

<script>
	parent.fraInterface.afterSubmit("<%=FlagStr%>");
</script>

</body>
</html>