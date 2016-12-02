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
<%@page import="com.sinosoft.midplat.jhyh.JhyhConf"%>
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
	//原交易流水号
	String sOldTransRefGUID = request.getParameter("OldTransRefGUID");
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
	
		System.out.println("sProviderFormNumber"+sProviderFormNumber);
	//if(!sProviderFormNumber.equals("")){
	//	sProviderFormNumber=CheckProlNo.ReturnCheck(sProviderFormNumber);
	//	}
	System.out.println("1111111");
	
	
	//原单证号 
	//String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");
	//if(sProviderFormNumber!=null){
	//sOriginalProviderFormNumber=CheckProlNo.ReturnCheck(sOriginalProviderFormNumber);
	//}
	//险种代码
	String sProductCode = request.getParameter("ProductCode");
	//	保单保费	
	String sPrem = request.getParameter("Prem");
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
 String InsuId = JhyhConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
 	ElementLis Transaction = new ElementLis("Transaction");	 
 	ElementLis Transaction_Header = new ElementLis("Transaction_Header",Transaction); 
 	ElementLis LiBankID = new ElementLis("LiBankID","JHYH",Transaction_Header);  
//保险公司代码
	 ElementLis PbInsuId = new ElementLis("PbInsuId",InsuId,Transaction_Header);  
//银行流水号
		ElementLis BkPlatSeqNo = new ElementLis("BkPlatSeqNo",sTransRefGUID,Transaction_Header); 

		ElementLis BkTxCode = new ElementLis("BkTxCode",sTransNo,Transaction_Header);
		 //交易渠道代号
		 ElementLis BkChnlNo = new ElementLis("BkChnlNo","1",Transaction_Header);  
//		ElementLis ZoneNo = new ElementLis("ZoneNo",sRegionCode,Transaction_Header);
		ElementLis BkBrchNo = new ElementLis("BkBrchNo",sRegionCode+sBranch,Transaction_Header);
		ElementLis BkTellerNo = new ElementLis("BkTellerNo",sTeller,Transaction_Header);
		ElementLis BkPlatDate = new ElementLis("BkPlatDate",sTransExeDate,Transaction_Header); 
		ElementLis BkPlatTime = new ElementLis("BkPlatTime",sTransExeTime,Transaction_Header);	
		 ElementLis Transaction_Body = new ElementLis("Transaction_Body",Transaction);  
		if(sTransNo.equals("510006")){//当日撤单
			ElementLis PbInsuType = new ElementLis("PbInsuType",sProductCode,Transaction_Body);
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sPolNumber,Transaction_Body); 
			ElementLis BkOthOldSeq = new ElementLis("BkOthOldSeq",sOldTransRefGUID,Transaction_Body); 
			ElementLis LiInsuSlipPWD = new ElementLis("LiInsuSlipPWD","",Transaction_Body);
			ElementLis BkTotAmt = new ElementLis("BkTotAmt",sPrem,Transaction_Body);
		}else if(sTransNo.equals("500208")){//当日冲正
			ElementLis BkOldSeq = new ElementLis("BkOldSeq",sOldTransRefGUID,Transaction_Body);
			ElementLis BkOthTxCode = new ElementLis("BkOthTxCode","",Transaction_Body);
		}else if(sTransNo.equals("510016")){//保单重打
			ElementLis PbInsuType = new ElementLis("PbInsuType",sProductCode,Transaction_Body);
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sPolNumber,Transaction_Body); 
			ElementLis BkOthOldSeq = new ElementLis("BkOthOldSeq",sOldTransRefGUID,Transaction_Body);
			ElementLis BkVchNo = new ElementLis("BkVchNo",sProviderFormNumber,Transaction_Body); 
			ElementLis PiFlagVchNo = new ElementLis("PiFlagVchNo","",Transaction_Body);
			ElementLis PiTrfVchNo = new ElementLis("PiTrfVchNo",sPrem,Transaction_Body);
		}
		
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		System.out.println("发送至" + ip + "端口:" + Integer.valueOf(port).intValue());
	
//System.out.println("************************装载Document************************");	
 			Document pXmlDoc = new Document(Transaction);	
 			JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		JHYHTestUI mTestUI = new JHYHTestUI(sIp,iPort); 
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
	byte[] mOutBytes=null;
	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
		 mOutBytes = mTestUI.sendRequest(inputStream);
System.out.println("************************4************************");		 
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);  
		//JdomUtil.print(mOutXmlDoc);
 
		//打印报文内容 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
System.out.println("*****************3");
ResultCode = mOutXmlDoc.getRootElement().getChild("Transaction_Header").getChild("Tran_Response").getChildTextTrim("BkOthRetCode");
ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("Transaction_Header").getChild("Tran_Response").getChildTextTrim("BkOthRetMsg");
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
		       Content = ResultInfoDesc + " 保费是：" + sPrem+"元 ";
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
