<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%> 
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="java.io.*"%>
<%@page import="org.jdom.Document"%>  
<%@page import="org.jdom.Element"%>  
<%@page import="java.net.ConnectException"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="test.*"%>
<%@page import="test.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="org.jdom.output.*"%>
<%@page import="org.jdom.input.*"%>
<%@page import="org.jdom.Attribute"%> 
<jsp:directive.page import="java.util.HashMap"/> 
<% 
	System.out.println("--> ins");
	String ResultFlag = null;
	String Content = null;
	String ResultMesg = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String uiPrint = null;   
%>

<%
  try {
 
 
	
	
		


//****************附加险信息1*******************附加险信息1*******************
 System.out.println("****************银行端信息1*******************银行端信息1*******************");
	//银行端交易日期
	String sBankDate = request.getParameter("BankDate").trim();
	//银行交易时间    
	String sBankTime = DateUtil.getCur8Time(); 
	//交易流水号
	String sTransrNo = request.getParameter("TransrNo");
	//地区代码
	String sZoneNo = request.getParameter("ZoneNo");
	//网点代码
	String sBrNo = request.getParameter("BrNo");
	//柜员代码
	String sTellerNo = request.getParameter("TellerNo"); 
	//投保日期
	String sPolApplyDate = request.getParameter("PolApplyDate");
	//投保书号
	String sProposalContNo = request.getParameter("ProposalContNo");

	//保单印刷号
	String sPrtNo = request.getParameter("PrtNo");
	//交易流水号
	String sReqsrNo = request.getParameter("ReqsrNo");
	//交易标志
	String sFunctionFlag = "02";
	
System.out.println("****************ip-port-flag*******************ip-port-flag*******************78gyhiuhiuhu");	
String sIp = request.getParameter("ip");
String sPort = request.getParameter("port");
String sFlag = request.getParameter("tranFlagCode");

 


//把保单信息在页面显示
		xmlContent = new String(""); 
   
		xmlContent += "一、交易信息\\n";
		xmlContent += "  银行端日期: " + sBankDate;
		xmlContent += "\t\t交易流水号: " + sTransrNo + "\\n";
		xmlContent += "  地区代码: " + sZoneNo; 
		xmlContent += "\t\t网点代码: " + sBrNo + "\\n"; 
		xmlContent += "  柜员代码: " + sTellerNo;
		xmlContent += "\t\t投保单号: " + sProposalContNo + "\\n"; 
		xmlContent += "  保单印刷号: " + sPrtNo ; 
		xmlContent += "\t\t交易流水号: " + sReqsrNo+ "\\n";

	


		
 System.out.println("************************装载XML************************");
 ElementLis Req = new ElementLis("Req");	
 	ElementLis BankDate = new ElementLis("BankDate",DateUtil.date10to8(sBankDate), Req);
 	ElementLis BankTime = new ElementLis("BankTime", Req);
 	ElementLis TransrNo = new ElementLis("TransrNo",sTransrNo, Req);
 	ElementLis ZoneNo = new ElementLis("ZoneNo",sZoneNo, Req);
 	ElementLis BrNo = new ElementLis("BrNo",sBrNo, Req);
 	ElementLis TellerNo = new ElementLis("TellerNo",sTellerNo, Req);
 	ElementLis FunctionFlag = new ElementLis("FunctionFlag",sFunctionFlag, Req);
 	
 	ElementLis Base = new ElementLis("Base",Req);
 		ElementLis PolApplyDate = new ElementLis("PolApplyDate",DateUtil.date10to8(sPolApplyDate), Base);
 		ElementLis ReqsrNo = new ElementLis("ReqsrNo",sReqsrNo, Base);
 		ElementLis ProposalContNo = new ElementLis("ProposalContNo",sProposalContNo, Base);
 		ElementLis Password = new ElementLis("Password", Base);
 		ElementLis Prem = new ElementLis("Prem", Base);
 
 
 
 
 
 
 		 
 


// System.out.println("************************设置保存路径************************");	
//	StringBuilder mFilePath = new StringBuilder(SysInfo.cHome);
//	System.out.println("mFilePath1************************"+mFilePath+"************************");   
//		int number =(mFilePath.length()-1);
//		int num = (number-8);
//		mFilePath.delete(num, number);
 //	System.out.println("mFilePath2************************"+mFilePath+"************************");  
//		mFilePath.append("msg"); 
				 //.append("1").append('/');
				 //.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
	//	String mInFilePath = mFilePath.toString()+"/icbcTestIn.xml";		
	//	String mOutFilePath = mFilePath.toString()+"/icbcTestReturn.xml";
		 
//System.out.println("************************"+mInFilePath+"************************");   
//System.out.println("************************"+mOutFilePath+"************************");  

 		System.out.println("************************装载Document************************");	
 	    
 		Document pXmlDoc = new Document(Req);	
 		
 		
		int iPort = Integer.valueOf(sPort).intValue();
		byte[] mOutBytes = null;
		
		//LDCodeDB mLDCodeDB = new LDCodeDB();
		//mLDCodeDB.setCode(sPort);
		//mLDCodeDB.setCodeType("port_bank");
		//mLDCodeDB.getInfo();
		//if(mLDCodeDB.getComCode().equals("abc")){
			YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp,iPort);  
		    mOutBytes = mTestUI.sendRequestUI(sFlag, pXmlDoc);
		//}else if(mLDCodeDB.getComCode().equals("bon")){
		//	YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp,iPort);  
		//    mOutBytes = mTestUI.sendRequestUI(sFlag, pXmlDoc);
		//}
		
		System.out.println("************************4************************");	 
		Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
	//	OutputStream mFos = new FileOutputStream(mOutFilePath);
	//	JdomUtil.output(mOutXmlDoc, mFos);
  
		//打印报文内容 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
	   

		JdomUtil.print(mOutXmlDoc);
		 
		ResultFlag = mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Flag");
		ResultMesg = mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Mesg");

System.out.println(ResultFlag + "  " + ResultMesg);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("返回内容："+ResultMesg);
		if (ResultFlag.equals("1234")) { // fail
	        Content = "交易失败：" + ResultMesg ;
	    
            //jsp的String上不能出现"%",否则不能显示内容
	       ResultMesg = ResultMesg.replace("%","");
	      
		} else { 
	      
	       System.out.println("返回内容2："+Content);

	       if (sFlag.trim().equals("02")) {
		      // rPrem = mOutXmlDoc.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("Premium");
		       //RetBaseAmt = result.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("BaseAmt");
		     //  int tPLength = RetPrem.length();
		     
		    //   if(tPLength>2){
		    //      RetPrem = RetPrem.substring(0,tPLength-2);
		    //   }
		   
		       Content = ResultMesg + " 保费是：" + "RetPrem"+"元 ";
		       }

	        System.out.println("-----------开始取数（save页面）----------");
	
		   }  

	} catch (Exception e) {
		e.printStackTrace();
		ResultFlag = "Fail";
		xmlContent= e.getMessage();  
		Content = e.getMessage(); 
	}      
%>   
<script language="javascript">
   parent.fraInterface.afterSubmit("<%=ResultFlag%>", "<%=ResultMesg%>");
   parent.fraInterface.fm.TransrNo.value = '<%=strNewTransNo%>';

   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>