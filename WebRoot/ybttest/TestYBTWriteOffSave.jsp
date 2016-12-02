<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="test.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="org.jdom.Document"%>  
<%@page import="org.jdom.Element"%>
<%@page import="java.net.ConnectException"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.sinosoft.midplat.common.*"%>
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
	String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//保单号 
	String sPolNumber = request.getParameter("PolNumber");
	//单证号 
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//原单证号 
	String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");

	
	
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
		xmlContent += "  投保书号: " + sHOAppFormNumber;
		xmlContent += "\t保单号: " + sPolNumber + "\\n";
		xmlContent += "  单证印刷号: " + sProviderFormNumber;
		xmlContent += "\t原单证印刷号: " + sOriginalProviderFormNumber + "\\n";
		
 System.out.println("************************装载XML************************");		 

 ElementLis TXLife = new ElementLis("TXLife");	 
	ElementLis TXLifeRequest = new ElementLis("TXLifeRequest",TXLife);  
		ElementLis TransRefGUID = new ElementLis("TransRefGUID",sTransRefGUID,TXLifeRequest);    
		ElementLis TransExeDate = new ElementLis("TransExeDate",sTransExeDate,TXLifeRequest); 
		ElementLis TransExeTime = new ElementLis("TransExeTime",sTransExeTime,TXLifeRequest);	
		ElementLis OLifE = new ElementLis("OLifE",TXLifeRequest); 
			ElementLis HoldingH1 = new ElementLis("Holding","id","Holding_1",OLifE);
				ElementLis Policy = new ElementLis("Policy",HoldingH1);  
					ElementLis PolNumber = new ElementLis("PolNumber",sPolNumber,Policy); 
					ElementLis ApplicationInfo = new ElementLis("ApplicationInfo",Policy);
						ElementLis HOAppFormNumber = new ElementLis("HOAppFormNumber",sHOAppFormNumber,ApplicationInfo);
						  
			ElementLis OLifEExtension1 = new ElementLis("OLifEExtension","VendorCode","1",TXLifeRequest); 
			ElementLis RegionCode = new ElementLis("RegionCode",sRegionCode,OLifEExtension1);
			ElementLis Branch = new ElementLis("Branch",sBranch,OLifEExtension1);
			ElementLis Teller = new ElementLis("Teller",sTeller,OLifEExtension1);
			ElementLis TransNo = new ElementLis("TransNo",sTransNo,OLifEExtension1);
		
		ElementLis FormInstance = new ElementLis("FormInstance","id","Form_2",OLifE);
				ElementLis FormName = new ElementLis("FormName","2",FormInstance);
				ElementLis ProviderFormNumber = new ElementLis("ProviderFormNumber",sProviderFormNumber,FormInstance);
				ElementLis OriginalProviderFormNumber = new ElementLis("OriginalProviderFormNumber",sOriginalProviderFormNumber,FormInstance);
		String ip = request.getParameter("ip");
		String port = request.getParameter("port"); 
		System.out.println("发送至" + ip + "端口:" + Integer.valueOf(port).intValue());
		
		
	//System.out.println("************************设置保存路径************************");	
	//StringBuilder mFilePath = new StringBuilder(SysInfo.cHome);
	//System.out.println("mFilePath1************************"+mFilePath+"************************");   
	//	int number =(mFilePath.length()-1);
	//	int num = (number-8);
	//	mFilePath.delete(num, number);
 	//System.out.println("mFilePath2************************"+mFilePath+"************************");  
	//	mFilePath.append("msg"); 
				 //.append("1").append('/');
				 //.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
	//	String mInFilePath = mFilePath.toString()+"/RetXMLIn.xml";		
	//	String mOutFilePath = mFilePath.toString()+"/RetXMLReturn.xml";
		 
//System.out.println("************************"+mInFilePath+"************************");   
//System.out.println("************************"+mOutFilePath+"************************");  

System.out.println("************************装载Document************************");	
   
 			Document pXmlDoc = new Document(TXLife);	
//System.out.println("************************1************************");	
//			FileOutputStream tFos = new FileOutputStream(mInFilePath); 
//System.out.println("************************2************************");	
//			JdomUtil.output(pXmlDoc, tFos,"GBK");
//System.out.println("************************3************************");		
			    
		
		int iPort = Integer.valueOf(sPort).intValue();
		ICBCTestUI mTestUI = new ICBCTestUI(sIp,iPort); 
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


		 
		ResultCode = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChildTextTrim("ResultCode");
		ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("TXLifeResponse").getChild("TransResult").getChild("ResultInfo").getChildTextTrim("ResultInfoDesc");

System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("返回内容："+ResultInfoDesc);
		if (ResultCode.equals("1234")) { // fail
	        Content = "交易失败：" + ResultInfoDesc ;
	    
            //jsp的String上不能出现"%",否则不能显示内容
	       ResultInfoDesc = ResultInfoDesc.replace("%","");
	       
		} else { 
	      
	       System.out.println("返回内容2："+Content);
 
	       if (sTransNo.trim().equals("1013")) {
		      // rPrem = mOutXmlDoc.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("Premium");
		       //RetBaseAmt = result.getRootElement().getChild("K_BI").getChild("Info").getChildTextTrim("BaseAmt");
		     //  int tPLength = RetPrem.length();
		     
		    //   if(tPLength>2){
		    //      RetPrem = RetPrem.substring(0,tPLength-2);
		    //   }
		   
		       Content = ResultInfoDesc + " 保费是：" + "RetPrem"+"元 ";
		       }

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
