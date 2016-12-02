<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
  //程序名称：ybtAuthorSave.jsp
  //程序功能：
  //创建日期：2006-03-14
  //创建人  :
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.ybt.bl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //接收信息，并作校验处理。
  //输入参数
  LKTransAuthorizationSchema tLKTransAuthorizationSchema = new LKTransAuthorizationSchema();
  //输出参数
  CErrors tError = null;
  String tOperate = request.getParameter("hideOperate");
  tOperate = tOperate.trim();
  String tRela = "";
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput) session.getValue("GI");
  String BankCode = request.getParameter("BankCode");
  String BankBranch = request.getParameter("BankBranch");
  String BankNode = request.getParameter("BankNode");
  String ManageCom = request.getParameter("ManageCom");
 // String ManageCom = "8621"; 
  String FuncFlag = request.getParameter("FuncFlag");
  
  if (BankNode == null || BankNode.trim()== "") BankNode = "***";
  if (BankBranch == null || BankBranch.trim() == "") BankBranch = "***"; //ZoneNo ;
   
 
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("BankCode", BankCode);
  tTransferData.setNameAndValue("BankBranch", BankBranch);
  tTransferData.setNameAndValue("BankNode", BankNode);
  tTransferData.setNameAndValue("ManageCom", ManageCom);
  tTransferData.setNameAndValue("FuncFlag", FuncFlag);
 
 
  
  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr = "";
  tVData.add(tTransferData);
  tVData.add(tG);
  String tBankNode = "";
  System.out.println("%%%%%%%%%%%%%%");
  YBTLKAuthorUI tYBTLKAuthorUI = new YBTLKAuthorUI();
  try {
    System.out.println("this will save the data!!!");
    tYBTLKAuthorUI.submitData(tVData, tOperate);
  }
  catch (Exception ex) {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  if (!FlagStr.equals("Fail")) {
    tError = tYBTLKAuthorUI.mErrors;
    if (!tError.needDealError()) {
    
      Content = " 保存成功! ";
      FlagStr = "Succ";
    }
    else {
      Content = " 保存失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
  //添加各种预处理
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tBankNode%>");
</script></html>
