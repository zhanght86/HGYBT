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
<%@page import="com.sinosoft.midplat.newccb.NewCcbConf"%>
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
	//退保交易信息传递
	String sTransCancelDate = request.getParameter("TransCancelDate").trim();
	sTransCancelDate=DateUtil.date10to8(sTransCancelDate);
	//银行交易时间    
	String sTransExeTime = DateUtil.getCur8Time(); 
	sTransExeTime=DateUtil.time8to6(sTransExeTime);
	//交易流水号
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//保单重大时用的原交易流水号
	String sTransRefGUID2 = request.getParameter("TransRefGUID2");
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
	
	//
	String sIns_Age = request.getParameter("Ins_Age");
		System.out.println("sProviderFormNumber"+sProviderFormNumber);
	//if(!sProviderFormNumber.equals("")){
	//	sProviderFormNumber=CheckProlNo.ReturnCheck(sProviderFormNumber);
	//	}
	System.out.println("1111111");
	//代理保险套餐编号 
	String sAgIns_Pkg_ID = request.getParameter("AgIns_Pkg_ID");
	String sAgIns_Pkg_ID2 = request.getParameter("AgIns_Pkg_ID2");
	//性别
	String sAppGender = request.getParameter("Gender");
	
	//原单证号 
	//String sOriginalProviderFormNumber = request.getParameter("OriginalProviderFormNumber");
	//if(sProviderFormNumber!=null){
	//sOriginalProviderFormNumber=CheckProlNo.ReturnCheck(sOriginalProviderFormNumber);
	//}
	//险种代码
	String sProductCode = request.getParameter("ProductCode");
	//试算险种代码
	String sProductCode2 = request.getParameter("ProductCode2");
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
	//绿灯测试使用
	String sBkDetail2 = request.getParameter("BkDetail2");
	String sBkDetail3 = request.getParameter("BkDetail3");
	//重空核对使用
	String sCheckCode = request.getParameter("CheckCode");
	String sBkVchNo1 = request.getParameter("BkVchNo1");
	String sBkVchNo2 = request.getParameter("BkVchNo2");
	//签约解约使用
	String sContCodeNo= request.getParameter("ContCodeNo");
	String sFSType = request.getParameter("FSType");
	String sClientName= request.getParameter("ClientName");
	String sClientAccNo = request.getParameter("ClientAccNo");
	String sMessPrtNo= request.getParameter("MessPrtNo");
	//	保单保费	
	String sReturnAmnt = request.getParameter("ReturnAmnt");
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
 String InsuId = NewCcbConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
 	ElementLis TX = new ElementLis("TX");	 
 	ElementLis TX_HEADER = new ElementLis("TX_HEADER",TX); 
 	 ElementLis SYS_HDR_LEN = new ElementLis("SYS_HDR_LEN","0",TX_HEADER);  
 	 ElementLis SYS_PKG_VRSN = new ElementLis("SYS_PKG_VRSN","01",TX_HEADER);  
 	 ElementLis SYS_TTL_LEN = new ElementLis("SYS_TTL_LEN","0",TX_HEADER);  
 	 ElementLis SYS_REQ_SEC_ID = new ElementLis("SYS_REQ_SEC_ID","420020",TX_HEADER);  
 	 ElementLis SYS_SND_SEC_ID = new ElementLis("SYS_SND_SEC_ID","108011",TX_HEADER);  
 	 ElementLis SYS_TX_CODE = new ElementLis("SYS_TX_CODE",TX_HEADER);  
 	 ElementLis SYS_TX_VRSN = new ElementLis("SYS_TX_VRSN","01",TX_HEADER);  
 	 ElementLis SYS_TX_TYPE = new ElementLis("SYS_TX_TYPE","020000",TX_HEADER);  
 	 ElementLis SYS_RESERVED = new ElementLis("SYS_RESERVED","0",TX_HEADER);  
 	 ElementLis SYS_EVT_TRACE_ID = new ElementLis("SYS_EVT_TRACE_ID",TX_HEADER);  
 	 ElementLis SYS_SND_SERIAL_NO = new ElementLis("SYS_SND_SERIAL_NO","",TX_HEADER);  
 	 ElementLis SYS_PKG_TYPE = new ElementLis("SYS_PKG_TYPE","1",TX_HEADER);  
 	 ElementLis SYS_MSG_LEN = new ElementLis("SYS_MSG_LEN","",TX_HEADER);  
 	 ElementLis SYS_IS_ENCRYPTED = new ElementLis("SYS_IS_ENCRYPTED","3",TX_HEADER);  
 	 ElementLis SYS_ENCRYPT_TYPE = new ElementLis("SYS_ENCRYPT_TYPE","3",TX_HEADER);  
 	 ElementLis SYS_COMPRESS_TYPE = new ElementLis("SYS_COMPRESS_TYPE","0",TX_HEADER);  
 	 ElementLis SYS_EMB_MSG_LEN = new ElementLis("SYS_EMB_MSG_LEN","0",TX_HEADER);  
 	 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
 	 Date date=new Date();
 	 ElementLis SYS_REQ_TIME = new ElementLis("SYS_REQ_TIME",sdf.format(date),TX_HEADER);  
 	 ElementLis SYS_TIME_LEFT = new ElementLis("SYS_TIME_LEFT",TX_HEADER);  
 	 ElementLis SYS_PKG_STS_TYPE = new ElementLis("SYS_PKG_STS_TYPE","00",TX_HEADER);  
 	 
 	 ElementLis TX_BODY = new ElementLis("TX_BODY",TX);  
 	 
 	 ElementLis COMMON = new ElementLis("COMMON",TX_BODY);  
 	 ElementLis FILE_LIST_PACK = new ElementLis("FILE_LIST_PACK",COMMON);  
 	 ElementLis FILE_NUM = new ElementLis("FILE_NUM","0",FILE_LIST_PACK);  
 	 ElementLis FILE_MODE = new ElementLis("FILE_MODE","0",FILE_LIST_PACK);  
 	 ElementLis FILE_NODE = new ElementLis("FILE_NODE",FILE_LIST_PACK);  
 	 ElementLis FILE_NAME_PACK = new ElementLis("FILE_NAME_PACK",FILE_LIST_PACK);  
 	 ElementLis FILE_PATH_PACK = new ElementLis("FILE_PATH_PACK",FILE_LIST_PACK);  
 	 
 	 ElementLis ENTITY = new ElementLis("ENTITY",TX_BODY);  
 	 
 	 ElementLis COM_ENTITY = new ElementLis("COM_ENTITY",ENTITY);  
 	 ElementLis Inst_Eng_ShrtNm = new ElementLis("Inst_Eng_ShrtNm","CCB",COM_ENTITY);  
 	 ElementLis Ins_Co_ID = new ElementLis("Ins_Co_ID",InsuId,COM_ENTITY);  
 	 ElementLis SvPt_Jrnl_No = new ElementLis("SvPt_Jrnl_No",sTransRefGUID,COM_ENTITY);  
 	 ElementLis TXN_ITT_CHNL_ID = new ElementLis("TXN_ITT_CHNL_ID","",COM_ENTITY);  
 	 ElementLis TXN_ITT_CHNL_CGY_CODE = new ElementLis("TXN_ITT_CHNL_CGY_CODE","20170029",COM_ENTITY);  
 	 ElementLis CCBIns_ID = new ElementLis("CCBIns_ID","330613535",COM_ENTITY);  
 	// ElementLis CCBIns_ID = new ElementLis("CCBIns_ID","0020000019",COM_ENTITY);  
 	 ElementLis CCB_EmpID = new ElementLis("CCB_EmpID","330613535351",COM_ENTITY);  
 	//  ElementLis CCB_EmpID = new ElementLis("CCB_EmpID","00200000191",COM_ENTITY);  
 	 ElementLis OprgDay_Prd = new ElementLis("OprgDay_Prd",sTransExeDate,COM_ENTITY);  
 	 ElementLis LNG_ID = new ElementLis("LNG_ID","zh-cn",COM_ENTITY);  
 	 
 	 ElementLis APP_ENTITY = new ElementLis("APP_ENTITY",ENTITY);  
 	
		if(sTransNo.equals("P53816141")){//失算保险产品
			 SYS_TX_CODE.setText("P53816141");
			ElementLis Cvr_ID = new ElementLis("Cvr_ID",sProductCode2,APP_ENTITY);
			ElementLis AgIns_Pkg_ID = new ElementLis("AgIns_Pkg_ID",sAgIns_Pkg_ID,APP_ENTITY); 
			ElementLis Gnd_Cd = new ElementLis("Gnd_Cd",sGender,APP_ENTITY); 
			ElementLis Ins_Age = new ElementLis("Ins_Age",sIns_Age,APP_ENTITY);
			ElementLis InsPrem_PyF_Cyc_Cd = new ElementLis("InsPrem_PyF_Cyc_Cd",sPaymentMode,APP_ENTITY);
			ElementLis InsPrem_PyF_Ddln = new ElementLis("InsPrem_PyF_Ddln",sPaymentDuration,APP_ENTITY);
			ElementLis Ins_Ddln = new ElementLis("Ins_Ddln",sDuration,APP_ENTITY);
			ElementLis MainIns_Cvr = new ElementLis("MainIns_Cvr",sInitCovAmt,APP_ENTITY);
			ElementLis InsPrem_Amt = new ElementLis("InsPrem_Amt",sModalPremAmt,APP_ENTITY);
		}else if(sTransNo.equals("P53819142")){//当日撤单
			 SYS_TX_CODE.setText("P53819142");
			ElementLis Cvr_ID = new ElementLis("Cvr_ID",sProductCode,APP_ENTITY);
			ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",sPolNumber,APP_ENTITY); 
			ElementLis Ins_Co_Jrnl_No = new ElementLis("Ins_Co_Jrnl_No",sOldTransRefGUID,APP_ENTITY); 
			ElementLis CCB_AccNo = new ElementLis("CCB_AccNo","6226003157963328",APP_ENTITY);
			ElementLis CnclIns_Amt = new ElementLis("CnclIns_Amt",sReturnAmnt,APP_ENTITY);
			ElementLis AgIns_Pkg_ID = new ElementLis("AgIns_Pkg_ID",sAgIns_Pkg_ID2,APP_ENTITY);
		}else if(sTransNo.equals("P53818154")){//自动冲正
			 SYS_TX_CODE.setText("P53818154");
			ElementLis Orig_TxnSrlNo = new ElementLis("Orig_TxnSrlNo",sOldTransRefGUID,APP_ENTITY);
			ElementLis Txn_Cd = new ElementLis("Txn_Cd","",APP_ENTITY);
		}else if(sTransNo.equals("P53819184")){//保单重打
			 SYS_TX_CODE.setText("P53819184");
			ElementLis Cvr_ID = new ElementLis("Cvr_ID",sProductCode,APP_ENTITY);
			ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",sPolNumber,APP_ENTITY); 
			ElementLis Ins_Co_Jrnl_No = new ElementLis("Ins_Co_Jrnl_No",sTransRefGUID2,APP_ENTITY);
			ElementLis Rvl_Rcrd_Num = new ElementLis("Rvl_Rcrd_Num","1",APP_ENTITY);
			ElementLis Detail_List = new ElementLis("Detail_List",APP_ENTITY);
			ElementLis Detail = new ElementLis("Detail",Detail_List);
			ElementLis Mod_Af_Ins_IBVoch_ID = new ElementLis("Mod_Af_Ins_IBVoch_ID",sProviderFormNumber,Detail); 
			ElementLis AgIns_Vchr_TpCd = new ElementLis("AgIns_Vchr_TpCd","1",Detail); 
		}else if(sTransNo.equals("P53818152")){//绿灯交易
			 SYS_TX_CODE.setText("P53818152");
			ElementLis LnkInTst_Fld_1 = new ElementLis("LnkInTst_Fld_1",sBkDetail2,APP_ENTITY);
			ElementLis LnkInTst_Fld_2 = new ElementLis("LnkInTst_Fld_2",sBkDetail3,APP_ENTITY);
		}else if(sTransNo.equals("P538191A2")){//重空交易
			 SYS_TX_CODE.setText("P538191A2");
			ElementLis Ins_IBVoch_Tp_ECD = new ElementLis("Ins_IBVoch_Tp_ECD",sCheckCode,APP_ENTITY);
			ElementLis Ins_IBVoch_Beg_ID = new ElementLis("Ins_IBVoch_Beg_ID",sBkVchNo1,APP_ENTITY);
			ElementLis Ins_IBVoch_End_ID = new ElementLis("Ins_IBVoch_End_ID",sBkVchNo2,APP_ENTITY);
		}else if(sTransNo.equals("SPE801")){//退保交易信息查询
			 SYS_TX_CODE.setText("SPE801");
			ElementLis PbBatChgBegDate = new ElementLis("PbBatChgBegDate",sTransCancelDate,APP_ENTITY);
		}else if(sTransNo.equals("SPE010")){//签约交易
			 SYS_TX_CODE.setText("SPE010");
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sContCodeNo,APP_ENTITY);
			ElementLis PbHoldName = new ElementLis("PbHoldName",sClientName,APP_ENTITY);
			ElementLis BkAcctNo1 = new ElementLis("BkAcctNo1",sClientAccNo,APP_ENTITY);
			ElementLis BkTxType = new ElementLis("BkTxType",sFSType,APP_ENTITY);
			ElementLis LiRatifyPrtId = new ElementLis("LiRatifyPrtId",sMessPrtNo,APP_ENTITY);
		}else if(sTransNo.equals("SPE013")){//解约交易
			 SYS_TX_CODE.setText("SPE013");
			ElementLis PbInsuSlipNo = new ElementLis("PbInsuSlipNo",sContCodeNo,APP_ENTITY);
			ElementLis PbHoldName = new ElementLis("PbHoldName",sClientName,APP_ENTITY);
			ElementLis BkTxType = new ElementLis("BkTxType",sFSType,APP_ENTITY);
			ElementLis LiRatifyPrtId = new ElementLis("LiRatifyPrtId",sMessPrtNo,APP_ENTITY);
		}else if(sTransNo.equals("BAT900")){//批量交易查询
			
		}
		
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		System.out.println("发送至" + ip + "端口:" + Integer.valueOf(port).intValue());
	
//System.out.println("************************装载Document************************");	
 			Document pXmlDoc = new Document(TX);	
 			JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		NewCCBTestUI mTestUI = new NewCCBTestUI(sIp,iPort); 
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
//	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
		byte[] mOutBytes  = mTestUI.sendRequest(sTransNo,pXmlDoc);
		Document mOutXmlDoc = JdomUtil.build(mOutBytes,"UTF-8");
		//JdomUtil.print(mOutXmlDoc);
 
		//打印报文内容 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
	//	mFos.flush();  
	//	mFos.close(); 
System.out.println("*****************3");
ResultCode = mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_STATUS");
ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_RESP_DESC");
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
