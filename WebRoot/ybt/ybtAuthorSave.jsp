<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
  //�������ƣ�ybtAuthorSave.jsp
  //�����ܣ�
  //�������ڣ�2006-03-14
  //������  :
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.ybt.bl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //������Ϣ������У�鴦��
  //�������
  LKTransAuthorizationSchema tLKTransAuthorizationSchema = new LKTransAuthorizationSchema();
  //�������
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
 
 
  
  // ׼���������� VData
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
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  if (!FlagStr.equals("Fail")) {
    tError = tYBTLKAuthorUI.mErrors;
    if (!tError.needDealError()) {
    
      Content = " ����ɹ�! ";
      FlagStr = "Succ";
    }
    else {
      Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tBankNode%>");
</script></html>
