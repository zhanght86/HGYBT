<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%> 
<%@page import="java.io.*"%>
<%@page import="org.jdom.Document"%>  
<%@page import="org.jdom.Element"%>  
<%@page import="java.net.ConnectException"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="test.*"%>
<%@page import="com.sinosoft.midplat.net.*"%>
<%@page import="org.jdom.output.*"%>
<%@page import="org.jdom.input.*"%>
<%@page import="org.jdom.Attribute"%> 
<%@page import="test.NewABCTest" %>
<jsp:directive.page import="java.util.HashMap"/>
<% 
	String ResultCode = null;
	String Content = null;
	String ResultInfoDesc = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String strNewHOAppFormNumber = null;
	String strNewProviderFormNumber = null;
	String uiPrint = null;   
	String strNewInsurialno = null;
%>

<%
  try {
 
	//头节点
	request.setCharacterEncoding("GBK");
	String sTRANSCODE = request.getParameter("TRANSCODE");
	
	String sTranCom = request.getParameter("TranCom");
	String sSERIALNO = request.getParameter("SERIALNO");
	String sTRANSDATE = DateUtil.date10to8(request.getParameter("TRANSDATE"));
	String sTRANSTIME = DateUtil.time8to6(DateUtil.getCur8Time());
	String sBRANCHNO = request.getParameter("BRANCHNO");
	String sPROVCODE = request.getParameter("PROVCODE");
	String sTLID = request.getParameter("TLID");
	
	String sPolicyNo = request.getParameter("PolicyNo");
	String sPrintCode = request.getParameter("PrintCode");
	String sClientName = request.getParameter("ClientName");
	String sIdKind = request.getParameter("IdKind");
	String sIdCode = request.getParameter("IdCode");
	String sPayeetName = request.getParameter("PayeetName");
	String sPayAcc = request.getParameter("PayAcc");
	String sPayeeIdKind = request.getParameter("PayeeIdKind");
	String sPayeeIdCode = request.getParameter("PayeeIdCode");
	
	String sAmt = request.getParameter("Amt");
	String sBusiType = request.getParameter("BusiType");
	
	String sIp = request.getParameter("ip");
	String sPort = request.getParameter("port");
 
//把保单信息在页面显示
		xmlContent = new String(""); 
   
		xmlContent += "一、交易信息\\n";
		xmlContent += "  银行端日期: " + sTRANSDATE;
		xmlContent += "\t\t交易流水号: " + sSERIALNO + "\\n";
		xmlContent += "\t\t\t银行网点代码: " + sBRANCHNO + "\\n"; 
		xmlContent += "  柜员代码: " + sTLID;
	
 System.out.println("************************装载XML************************");		 
	Element mABCB2I = new Element("ABCB2I"); 
 	ElementLis mHeader = new ElementLis("Header",mABCB2I);	
	 	ElementLis mSerialNo = new ElementLis("SerialNo",sSERIALNO,mHeader);
	 	ElementLis mInsuSerial = new ElementLis("InsuSerial","",mHeader);
	 	ElementLis mTransDate = new ElementLis("TransDate",sTRANSDATE,mHeader);
	 	ElementLis mTransTime = new ElementLis("TransTime",sTRANSTIME,mHeader);
	 	ElementLis mBankCode = new ElementLis("BankCode","AGC",mHeader);
	 	ElementLis mCorpNo = new ElementLis("CorpNo","",mHeader);
	 	ElementLis mTransCode = new ElementLis("TransCode",sTRANSCODE,mHeader);
	 	ElementLis mTransSide = new ElementLis("TransSide","1",mHeader);
	 	ElementLis mEntrustWay = new ElementLis("EntrustWay",null,mHeader);
	 	ElementLis mProvCode = new ElementLis("ProvCode",sPROVCODE,mHeader);
	 	ElementLis mBranchNo = new ElementLis("BranchNo",sBRANCHNO,mHeader);
	 	ElementLis mTlid = new ElementLis("Tlid",sTLID,mHeader);

	 	ElementLis mApp = new ElementLis("App",mABCB2I);
	 	ElementLis mReq = new ElementLis("Req",mApp);
 		
 		ElementLis msPolicyNo = new ElementLis("PolicyNo",sPolicyNo,mReq);
 		ElementLis msPrintCode = new ElementLis("PrintCode",sPrintCode,mReq);
 		ElementLis msClientName = new ElementLis("ClientName",sClientName,mReq);
 		
 		ElementLis msIdKind = new ElementLis("IdKind",sIdKind,mReq);
 		ElementLis msIdCode = new ElementLis("IdCode",sIdCode,mReq);
 		ElementLis msPayeetName = new ElementLis("PayeetName",sPayeetName,mReq);
 		ElementLis msPayAcc = new ElementLis("PayAcc",sPayAcc,mReq);
 		ElementLis msPayeeIdKind = new ElementLis("PayeeIdKind",sPayeeIdKind,mReq);
 		ElementLis msPayeeIdCode = new ElementLis("PayeeIdCode",sPayeeIdCode,mReq);
 		
 		ElementLis msAmt = new ElementLis("Amt",sAmt,mReq);
 		ElementLis msBusiType = new ElementLis("BusiType",sBusiType,mReq);
 		
		JdomUtil.print(mABCB2I);
		System.out.println("-----------------------------------------------");
 		//Document pXmlDoc = new Document(mABCB2I);	

		//JdomUtil.print(pXmlDoc);
	
		String mABCB2IS=JdomUtil.toString(mABCB2I);
		mABCB2IS=mABCB2IS.substring(mABCB2IS.indexOf("<ABCB2I>"));
		
		int iPort = Integer.parseInt(sPort);
		NewABCTest test = new NewABCTest(sIp, iPort); 
		byte[] mABCB2IB=mABCB2IS.getBytes();
		
		Document mOutXmlDoc    = test.sendRequest(sTRANSCODE,mABCB2IB);
		
		ResultCode =  mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetCode");
		ResultInfoDesc =mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetMsg");
		PubFun1 pubfun = new PubFun1();
		System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = pubfun.CreateMaxNo("SERIALNO",16);

        System.out.println("返回内容："+ResultInfoDesc);
		if (!(ResultCode.equals("000000"))) {
	        Content = "交易失败：" + ResultInfoDesc ;
	       ResultInfoDesc = ResultInfoDesc.replace("%","");
	      
		} else {
		
	        System.out.println("-----------开始取数（save页面）----------");
	
		   }  

	} catch (Exception e) {
		e.printStackTrace();
		ResultCode = "Fail";
		xmlContent= e.getMessage();  
		Content = e.getMessage(); 
	}      
%>   
<script language="javascript">
   parent.fraInterface.afterSubmit("<%=ResultCode%>", "<%=ResultInfoDesc%>");
   parent.fraInterface.fm.SERIALNO.value = '<%=strNewTransNo%>';
   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>