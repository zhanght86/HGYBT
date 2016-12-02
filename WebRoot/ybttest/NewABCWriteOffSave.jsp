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
<%@page import="com.sinosoft.midplat.net.*"%>
<%@page import="com.sinosoft.midplat.newabc.NewAbcConf"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
	sTransExeDate=DateUtil.date10to8(sTransExeDate);

	//银行交易时间    
	String sTransExeTime = DateUtil.getCur8Time(); 
	sTransExeTime=DateUtil.time8to6(sTransExeTime);
	//交易流水号
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//保单重打时用的原交易流水号
	String sTransRefGUID2 = request.getParameter("OldTransRefGUID");

	//地区代码
	String sRegionCode = request.getParameter("RegionCode");
	//网点代码
	String sBranch = request.getParameter("Branch");
	//柜员代码
	String sTeller = request.getParameter("Teller"); 
	//投保单号
	//String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//保单号 
	String sPolNumber = request.getParameter("PolNumber");
	System.out.println("1111111111111111111111");
	//单证号 
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//原单证号  重打
	String sProviderFormNumber2 = request.getParameter("ProviderFormNumber2");
	//投保单号  重打
	String sProposalPrtNo = request.getParameter("ProposalPrtNo");
	//投保人姓名
	String sApptName = request.getParameter("ApptName");
	
		System.out.println("sProviderFormNumber"+sProviderFormNumber);
	//if(!sProviderFormNumber.equals("")){
	//	sProviderFormNumber=CheckProlNo.ReturnCheck(sProviderFormNumber);
	//	}
	System.out.println("1111111");

	//性别
	String sAppGender = request.getParameter("Gender");
	
	//原单证号 
	//String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");
	//if(sProviderFormNumber!=null){
	//sOriginalProviderFormNumber=CheckProlNo.ReturnCheck(sOriginalProviderFormNumber);
	//}
	//险种代码
	String sProductCode = request.getParameter("ProductCode");

	//性别
	String sGender = request.getParameter("Gender");
	//保费 
	String sModalPremAmt = request.getParameter("ModalPremAmt"); 
	//保额
	String sInitCovAmt = request.getParameter("InitCovAmt"); 
	//保险年期类型
	String sDurationMode = request.getParameter("DurationMode"); 
	//保险年期 
	String sDuration = request.getParameter("Duration"); 
	//缴费年期类型
	String sPaymentDurationMode = request.getParameter("PaymentDurationMode");
	//缴费年期类型
	String sPaymentDuration = request.getParameter("PaymentDuration");
	//试算保费缴费周期代码
	String sPaymentMode = request.getParameter("PaymentMode");
	//试算结果查询
	String sApplyNo = request.getParameter("ApplyNo");
	//绿灯测试使用
	String sReserve = request.getParameter("Reserve");

	//	保单保费	
	String sReturnAmnt = request.getParameter("ReturnAmnt");
	//	原交易日期 冲正	
	String sOrgTransDate = request.getParameter("OrgTransDate");
	sOrgTransDate=DateUtil.time8to6(sOrgTransDate);
	//	原交易编码 冲正	
	String sTransCode = request.getParameter("tranCode");
	//	缴费金额 冲正	
	String sPayAmt = request.getParameter("PayAmt");
	//	缴费帐号 冲正	
	String sPayAcc = request.getParameter("PayAcc");
	System.out.println("222222222222222222222");
	
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
		xmlContent += "\t\t\t投保日期: " + sTransExeDate + "\\n"; 
		xmlContent += "\t保单号: " + sPolNumber + "\\n";
		xmlContent += "  单证印刷号: " + sProviderFormNumber+ "\\n";
		//xmlContent += "\t原单证印刷号: " + sOriginalProviderFormNumber ;
		
 System.out.println("************************装载XML************************");	
 String InsuId = NewAbcConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
 Element mABCB2I = new Element("ABCB2I"); 
  ElementLis mHeader = new ElementLis("Header",mABCB2I);	

	ElementLis mSerialNo = new ElementLis("SerialNo",sTransRefGUID,mHeader);
	ElementLis mInsuSerial = new ElementLis("InsuSerial",sTransRefGUID,mHeader);
	ElementLis mTransDate = new ElementLis("TransDate",sTransExeDate,mHeader);
	ElementLis mTransTime = new ElementLis("TransTime",sTransExeTime,mHeader);
	ElementLis mBankCode = new ElementLis("BankCode",null,mHeader);
	ElementLis mCorpNo = new ElementLis("CorpNo","",mHeader);
	ElementLis mTransCode = new ElementLis("TransCode",sTransNo,mHeader);
	ElementLis mTransSide = new ElementLis("TransSide","1",mHeader);
	ElementLis mEntrustWay = new ElementLis("EntrustWay",null,mHeader);
	ElementLis mProvCode = new ElementLis("ProvCode",sRegionCode,mHeader);
	ElementLis mBranchNo = new ElementLis("BranchNo",sBranch,mHeader);
	ElementLis mTlid = new ElementLis("Tlid","10010",mHeader);

ElementLis mApp = new ElementLis("App",mABCB2I);
ElementLis mReq = new ElementLis("Req",mApp);
 	 
		if(sTransNo.equals("1010")){//当日撤单
			ElementLis RiskCode = new ElementLis("RiskCode",sProductCode,mReq);
			ElementLis ProdCode = new ElementLis("ProdCode","",mReq);
			ElementLis PolicyNo = new ElementLis("PolicyNo",sPolNumber,mReq); 
			ElementLis VchNo = new ElementLis("VchNo",sProviderFormNumber,mReq); 
			ElementLis ApptName = new ElementLis("TbrName",sApptName,mReq); 
			ElementLis TbrIdKind = new ElementLis("TbrIdKind","",mReq); 
			ElementLis TbrIdCode = new ElementLis("TbrIdCode","",mReq); 
			ElementLis PayAcc = new ElementLis("PayAcc",sPayAcc,mReq);
			ElementLis PayAmt = new ElementLis("PayAmt",sPayAmt,mReq);
			ElementLis OrgSerialNo = new ElementLis("OrgSerialNo",sTransRefGUID2,mReq); 
		}else if(sTransNo.equals("1009")){//自动冲正
			ElementLis OrgSerialNo = new ElementLis("OrgSerialNo",sTransRefGUID2,mReq);
			ElementLis OrgTransDate = new ElementLis("OrgTransDate",sOrgTransDate,mReq);
			ElementLis TransCode = new ElementLis("TransCode",sTransCode,mReq);
			ElementLis ProdCode = new ElementLis("ProdCode","123456",mReq);
			ElementLis PolicyNo = new ElementLis("PolicyNo",sPolNumber,mReq);
			ElementLis PayAcc = new ElementLis("PayAcc",sPayAcc,mReq);
			ElementLis PayAmt = new ElementLis("PayAmt",sPayAmt,mReq);
		}else if(sTransNo.equals("1018")){//保单重打
			ElementLis RiskCode = new ElementLis("RiskCode",sProductCode,mReq);
			ElementLis ProposalPrtNo = new ElementLis("PolicyApplyNo",sProposalPrtNo,mReq); 
			ElementLis OldVchNo = new ElementLis("OldVchNo",sProviderFormNumber2,mReq); 
			ElementLis NewVchNo = new ElementLis("NewVchNo",sProviderFormNumber,mReq); 
		}else if(sTransNo.equals("1005")){//试算结果查询
			ElementLis ApplyNo = new ElementLis("ApplyNo",sApplyNo,mReq);
		}else if(sTransNo.equals("1000")){//绿灯交易
			ElementLis Reserve = new ElementLis("Reserve",sReserve,mReq);
		}
		
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		System.out.println("发送至" + ip + "端口:" + Integer.valueOf(port).intValue());
	
//System.out.println("************************装载Document************************");	
		String mABCB2IS=JdomUtil.toString(mABCB2I);
		mABCB2IS=mABCB2IS.substring(mABCB2IS.indexOf("<ABCB2I>"));
		System.out.println("-----------------------------------------------"+mABCB2IS);
 			Document pXmlDoc = new Document(mABCB2I);	
 			JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		NewABCTest mTest = new NewABCTest(sIp,iPort); 
        byte[] mABCB2IB=mABCB2IS.getBytes();
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
//	byte[] mOutBytes=null;
//	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
		 Document mOutXmlDoc = mTest.sendRequest(sTransNo,mABCB2IB);
System.out.println("************************4************************");		 
//		Document mOutXmlDoc = JdomUtil.build(mOutBytes);  
		//JdomUtil.print(mOutXmlDoc);
 
		//打印报文内容 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
System.out.println("*****************3");
ResultCode = mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetCode");
ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("Header").getChildTextTrim("RetMsg");
System.out.println(ResultCode + "  " + ResultInfoDesc);
		strNewTransNo = PubFun1.CreateMaxNo("TransNo",16);
	//	System.out.println("strNewTransNo = " + strNewTransNo);
        System.out.println("返回内容："+ResultInfoDesc);
		if (ResultCode.equals("0")) { // fail
	       ResultInfoDesc = ResultInfoDesc.replace("%","%25");
	        Content = "交易失败：" + ResultInfoDesc ;
            //jsp的String上不能出现"%",否则不能显示内容
		} else { 
	       System.out.println("返回内容2："+Content);
	       if (sTransNo.trim().equals("27")) {
		       Content = ResultInfoDesc + " 保费是：" + sReturnAmnt+"元 ";
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
