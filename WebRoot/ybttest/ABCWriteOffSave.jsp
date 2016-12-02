<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.ElementLis"%>
<%@page import="test.YD_ABCTestUI"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun1"%> 
<%@page import="java.io.InputStream"%>
<%@page import="org.jdom.Document"%> 
<%@page import="com.sinosoft.midplat.common.DateUtil"%>
<%@page import="com.sinosoft.midplat.common.JdomUtil"%>
<%
	System.out.println("--> ins");
	String FlagStr = null;
	String Content = null;
	String strNewTransNo = null;
	String strContNo = null;
	String xmlContent = null;
	String ResultCode = null;

	String ResultInfoDesc = null; 

	String uiPrint = null;   
%>
<%
    InputStream ins = null;
    System.out.println("--> 1");
	try {
	
	 System.out.println("****************银行端信息1*******************银行端信息1*******************");
	//银行端交易日期
	String sTransExeDate = request.getParameter("TransExeDate").trim();
	//银行交易时间    
	String sTransExeTime = DateUtil.getCur8Time(); 
	//交易流水号
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//地区代码
	String sRegionCode = request.getParameter("RegionCode");
	//网点代码
	String sBranch = request.getParameter("Branch");
	//柜员代码
	String sTeller = request.getParameter("Teller"); 
	//投保书号
	//String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//保单号 
	String sPolNumber = request.getParameter("PolNumber");
	//单证号 
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//原单证号 
	String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");
//投保单号 
	String sAPPDOCNO = request.getParameter("APPDOCNO");
	String sPrem = request.getParameter("Prem");
	
	
	String sIp = request.getParameter("ip");
	String sPort = request.getParameter("port");
		//交易标志
	String sTransNo = request.getParameter("tranFlagCode");
	
	
		//把保单信息在页面显示
		xmlContent = new String("");

   
		xmlContent += "一、交易信息\\n";
		xmlContent += "  银行端日期: " + sTransExeDate;
		xmlContent += "\t\t交易流水号: " + sTransRefGUID + "\\n";
		xmlContent += "  地区代码: " + sRegionCode; 
		xmlContent += "\t\t\t网点代码: " + sBranch + "\\n"; 
		xmlContent += "  柜员代码: " + sTeller; 
		xmlContent += "\t\t\t投保日期: " + sTransRefGUID + "\\n"; 
		xmlContent += "\t保单号: " + sPolNumber + "\\n";
		xmlContent += "  新单证印刷号: " + sProviderFormNumber;
		xmlContent += "\t原单证印刷号: " + sOriginalProviderFormNumber + "\\n";
		
 System.out.println("************************装载XML************************");		 

 ElementLis Req = new ElementLis("Req");	 
 ElementLis TransExeDate = new ElementLis("BankDate",sTransExeDate,Req); 
 ElementLis BankCode = new ElementLis("BankCode","05",Req);  
 ElementLis ZoneNo = new ElementLis("ZoneNo",sRegionCode,Req);  
	ElementLis NodeNo = new ElementLis("BrNo",sBranch,Req);  
  
  ElementLis TellerNo = new ElementLis("TellerNo",sTeller,Req); 
  ElementLis TransRefGUID = new ElementLis("TransrNo",sTransRefGUID,Req); 
  ElementLis FunctionFlag = new ElementLis("FunctionFlag",sTransNo,Req); 
  ElementLis InsuID = new ElementLis("InsuID",Req);
  
	  
	
	
		if(sTransNo.equals("1011")){
		ElementLis NewContPrtNo = new ElementLis("NewContPrtNo",sProviderFormNumber,Req);
		}
		if(sTransNo.equals("03")){
		 ElementLis Base = new ElementLis("Base",Req); 
		 ElementLis ContNo = new ElementLis("ContNo",sPolNumber,Base); 
		 ElementLis ProposalContNo = new ElementLis("ProposalContNo",sAPPDOCNO,Base); 
		 ElementLis Prem = new ElementLis("Prem",sPrem,Base); 
		}
		if(sTransNo.equals("04")){
		 ElementLis ConfirmInfo = new ElementLis("ConfirmInfo",Req); 
         ElementLis ContNo = new ElementLis("ContNo",sPolNumber,ConfirmInfo); 
         ElementLis OldTranNo = new ElementLis("OldTranNo",ConfirmInfo);
		}
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		System.out.println("发送至" + ip + "端口:" + Integer.valueOf(port));
		
		
	
				 //.append("1").append('/');
				 //.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		//String mInFilePath = mFilePath.toString()+"/RetXMLIn.xml";		
		//String mOutFilePath = mFilePath.toString()+"/RetXMLReturn.xml";
		 
//System.out.println("************************"+mInFilePath+"************************");   
//System.out.println("************************"+mOutFilePath+"************************");  

//System.out.println("************************装载Document************************");	
   
 			Document pXmlDoc = new Document(Req);	
//System.out.println("************************1************************");	
	//		FileOutputStream tFos = new FileOutputStream(mInFilePath); 
//System.out.println("************************2************************");	
		//	JdomUtil.output(pXmlDoc, tFos,"GBK");
//System.out.println("************************3************************");		
			    
		
		int iPort = Integer.valueOf(sPort).intValue();
		YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp,iPort);  
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
		byte[] mOutBytes = mTestUI.sendRequestUI(sTransNo, pXmlDoc);
System.out.println("************************4************************");		 
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);  
	//	OutputStream mFos = new FileOutputStream(mOutFilePath);
	//	JdomUtil.output(mOutXmlDoc, mFos); 
 
		//打印报文内容 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
	   
System.out.println("*****************3");


		 
		ResultCode =  mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Flag");
		ResultInfoDesc =mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Desc");

System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("返回内容："+ResultInfoDesc);
		if (ResultCode.equals("0")) { // fail
	        Content = "交易失败：" + ResultInfoDesc ;
	    
            //jsp的String上不能出现"%",否则不能显示内容
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
   parent.fraInterface.fm.TransRefGUID.value = '<%=strNewTransNo%>';

   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
   //parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>
