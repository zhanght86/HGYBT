
<%
//程序名称：UnLockTable.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  GlobalInput tG = new GlobalInput();	
  tG = ( GlobalInput )session.getValue( "GI" );
		
  //锁印刷号
  LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
  tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
  tLDSysTraceSchema.setCreatePos(request.getParameter("CreatePos"));
  tLDSysTraceSchema.setPolState(request.getParameter("PolState"));
  LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
  inLDSysTraceSet.add(tLDSysTraceSchema);
  VData VData3 = new VData();
  VData3.add(tG);
  VData3.add(inLDSysTraceSet);
  
  LockTableUI LockTableUI1 = new LockTableUI();
  if (!LockTableUI1.submitData(VData3, request.getParameter("Action"))) {
    VData rVData = LockTableUI1.getResult();
    System.out.println("LockTable Failed! " + (String)rVData.get(0));
  }
  else {
    System.out.println("UnLockTable Succed!");
  }
%>

<script>
  window.close();
</script>
