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
<jsp:directive.page import="java.util.HashMap"/>
<%@page import="com.sinosoft.midplat.nbcb.NbcbConf"%>
<% 
	String ResultCode = null;
	String Content = null;
	String ResultInfoDesc = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String strNewHOAppFormNumber = null;
	String strNewProviderFormNumber = null;
	String uiPrint = null;   
%>
<%
////modefied by zhoupan 20120710
  try {
	  String sIp = request.getParameter("ip");
	  String sPort = request.getParameter("port");
	  String sFlag = request.getParameter("tranFlagCode");
	//银行端交易日期
	String sTransExeDate = request.getParameter("TransExeDate");
	sTransExeDate=DateUtil.date10to8(sTransExeDate);
	//银行交易时间    
	String sTransExeTime = DateUtil.getCur8Time(); 
	sTransExeTime=DateUtil.time8to6(sTransExeTime);
	//交易流水号
	String sTransRefGUID = request.getParameter("TransRefGUID");
	//旧交易流水号
	String sReqsrNo = request.getParameter("ReqsrNo");
	//地区代码
	String sRegionCode = request.getParameter("RegionCode").trim();
	//网点代码
	String sBranch = request.getParameter("Branch").trim();
	//柜员代码
	String sTeller = request.getParameter("Teller"); 
	//交易渠道
	String sTransMode=request.getParameter("TransMode");
	//保险分公司编码
	String sBranthCmp=request.getParameter("BranthCmp");
	//投保日期
	String sSubmissionDate = request.getParameter("SubmissionDate");
	sSubmissionDate=DateUtil.date10to8(sSubmissionDate);
	//投保书号
	String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//销售人员工号
	String sBkRckrNo=request.getParameter("BkRckrNo");
	//销售人姓名
	String sBkSaleName=request.getParameter("BkSaleName");
	//销售人员资格证号
	String sBkSaleCertNo=request.getParameter("BkSaleCertNo");
	
	if(!sHOAppFormNumber.equals("")){
		//sHOAppFormNumber=CheckProlNo.retCheck(sHOAppFormNumber);
		}
	//保单印刷号
	String sProviderFormNumber="";
	//if(sFlag.equals("OPR001")){
		 sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//	if(!sProviderFormNumber.equals("")){
	//		sProviderFormNumber = CheckProlNo.ReturnCheck(sProviderFormNumber);
	//		}
	//}
	
	//投保人姓名
	String sAppFullName = request.getParameter("AppFullName");
	//投保人性别
	String sAppGender = request.getParameter("AppGender");
	//投保人证件类型
	String sAppGovtIDTC = request.getParameter("AppGovtIDTC");
	//投保人证件号码
	String sAppGovtID = request.getParameter("AppGovtID");
	//投保人生日
	String sAppBirthDate = request.getParameter("AppBirthDate");
	sAppBirthDate=DateUtil.date10to8(sAppBirthDate);
	//投保人证件有效期
	String sAppGovtTermDate = request.getParameter("AppGovtTermDate");
	sAppGovtTermDate=DateUtil.date10to8(sAppGovtTermDate);
	//投保人电子邮箱
	String sAppAddrLine = request.getParameter("AppAddrLine");
	//投保人通讯地址	
	String sAppLine1 = request.getParameter("AppLine1");
	//投保人邮政编码
	String sAppZip = request.getParameter("AppZip");
	//投保人家庭电话
	String sAppDialNumber1 = request.getParameter("AppDialNumber1");
	//投保人移动电话
	String sAppDialNumber3 = request.getParameter("AppDialNumber3");
	//投保人年收入
	String sPbInCome = request.getParameter("PbInCome");
	//投保人家庭年收入
	String sPbHomeInCome = request.getParameter("PbHomeInCome");
	//投保人居民类型
	String sPbDenType = request.getParameter("PbDenType");
	//投保人与被报人的关系
	String sAppToInsRelation	 = request.getParameter("AppToInsRelation");
	String sAppCountry = request.getParameter("AppCountry");
	String sApplJobCode = request.getParameter("ApplJobCode");
	
	//被保人姓名
	String sInsFullName = request.getParameter("InsFullName");
	//被保人性别
	String sInsGender = request.getParameter("InsGender");
	String sInsGenderh = request.getParameter("InsGenderh");
	//被保人证件类型
	System.out.println("-----"+sInsGender);
	if(sInsGender == null) sInsGender = sInsGenderh;
	String sInsGovtIDTCh = request.getParameter("sInsGovtIDTCh");
	String sInsGovtIDTC = request.getParameter("InsGovtIDTC");
	if(sInsGovtIDTC == null) sInsGovtIDTC = sInsGovtIDTCh;
	
	if(sInsGovtIDTC == null) sInsGovtIDTC = sAppGovtIDTC;
	//被保人证件号码
	String sInsGovtID = request.getParameter("InsGovtID"); 
	//被保人生日
	String sInsBirthDate = request.getParameter("InsBirthDate");
	sInsBirthDate=DateUtil.date10to8(sInsBirthDate);
	//被保人证件有效期
	String sInsGovtTermDate = request.getParameter("InsGovtTermDate");
	sInsGovtTermDate=DateUtil.date10to8(sInsGovtTermDate);
	//被保人电子邮箱
	String sInsAddrLine = request.getParameter("InsAddrLine"); 
	//被保人通讯地址	 
	String sInsLine1 = request.getParameter("InsLine1");
	//被保人邮政编码
	String sInsZip = request.getParameter("InsZip"); 
	//被保人家庭电话
	String sInsDialNumber1 = request.getParameter("InsDialNumber1");
	//被保人移动电话
	String sInsDialNumber3 = request.getParameter("InsDialNumber3");
	String sInsCountry = request.getParameter("InsCountry");
	//健康告知标志 
	String sHealthIndicator	 = request.getParameter("HealthIndicator");

	String sInsuJobCode = request.getParameter("InsuJobCode");
	
	if(sHealthIndicator == null) sHealthIndicator = "N";
	//受益人姓名 
	String sBnfFullName1 = request.getParameter("BnfFullName1");
	//受益人性别
	String sBnfGender1 = request.getParameter("BnfGender1");
	//受益人证件类型
	String sBnfGovtIDTC1 = request.getParameter("BnfGovtIDTC1");
	//受益人证件号码
	String sBnfGovtID1 = request.getParameter("BnfGovtID1"); 
	//受益人生日
	String sBnfBirthDate1 = request.getParameter("BnfBirthDate1"); 
	sBnfBirthDate1=DateUtil.date10to8(sBnfBirthDate1);
	//受益人证件有效期
	String sBnfAdress1 = request.getParameter("BnfAdress1");
	//受益百分数
	String sInterestPercent1 = request.getParameter("InterestPercent1"); 
	//受益顺 序
	String sSequence1 = request.getParameter("Sequence1");
	//受益人与被报人的关系
	String sBnfToInsRelation1 = request.getParameter("BnfToInsRelation1");
	//收益人是否为法定
	String sBeneficiaryIndicator = request.getParameter("BeneficiaryIndicator");
    //受益人类型
    String sBenefiType1 = request.getParameter("BenefiType1");
	
	//受益人姓 名
	String sBnfFullName2 = request.getParameter("BnfFullName2");
	//受益人性别
	String sBnfGender2 = request.getParameter("BnfGender2");
	//受益人证件类型
	String sBnfGovtIDTC2 = request.getParameter("BnfGovtIDTC2");
	//受益人证件号码
	String sBnfGovtID2 = request.getParameter("BnfGovtID2"); 
	//受益人生日
	String sBnfBirthDate2 = request.getParameter("BnfBirthDate2"); 
	sBnfBirthDate2=DateUtil.date10to8(sBnfBirthDate2);
	//受益人证件有效期
	String sBnfAdress2 = request.getParameter("BnfAdress2");
	//受益百分数
	String sInterestPercent2 = request.getParameter("InterestPercent2"); 
	//受益顺 序
	String sSequence2 = request.getParameter("Sequence2");
	//受益人与被报人的关系
	String sBnfToInsRelation2 = request.getParameter("BnfToInsRelation2");
	 //受益人类型
    String sBenefiType2 = request.getParameter("BenefiType2");
	
	//受益人姓名
	String sBnfFullName3 = request.getParameter("BnfFullName3");
	//受益人性别
	String sBnfGender3 = request.getParameter("BnfGender3");
	//受益人证件类型
	String sBnfGovtIDTC3 = request.getParameter("BnfGovtIDTC3");
	//受益人证件号码
	String sBnfGovtID3 = request.getParameter("BnfGovtID3"); 
	//受益人生日
	String sBnfBirthDate3 = request.getParameter("BnfBirthDate3"); 
	sBnfBirthDate3=DateUtil.date10to8(sBnfBirthDate3);
	//受益人证件有效期
	String sBnfAdress3 = request.getParameter("BnfAdress3");
	//受益百分数
	String sInterestPercent3 = request.getParameter("InterestPercent3"); 
	//受益顺 序
	String sSequence3 = request.getParameter("Sequence3");
	//受益人与被报人的关系
	String sBnfToInsRelation3 = request.getParameter("BnfToInsRelation3");
	 //受益人类型
    String sBenefiType3 = request.getParameter("BenefiType3");

	//主险代码 
	String sProductCode = request.getParameter("ProductCode");
	//份数 
	String sIntialNumberOfUnits = request.getParameter("IntialNumberOfUnits");
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
	//缴费年期 
	String sPaymentDuration = request.getParameter("PaymentDuration"); 
	
	//贷款合同号
	String sContractNo = request.getParameter("ContractNo");
	//贷款账号
	String sLoanAccountNo = request.getParameter("LoanAccountNo");
	//借款金额
	String sLoanAmount = request.getParameter("LoanAmount");
	//保险金额
	String sFaceAmount = request.getParameter("FaceAmount");
	//贷款起始日期
	String sLoanStartDate = request.getParameter("LoanStartDate");
	sLoanStartDate=DateUtil.date10to8(sLoanStartDate);
	//贷款到期日期
	String sLoanEndDate = request.getParameter("LoanEndDate");
	sLoanEndDate=DateUtil.date10to8(sLoanEndDate);
	//保险合同生效日期
	String sContractEffDate = request.getParameter("ContractEffDate");
	sContractEffDate=DateUtil.date10to8(sContractEffDate);
	//保险合同到期日期
	String sContractEndDate = request.getParameter("ContractEndDate");
	sContractEndDate=DateUtil.date10to8(sContractEndDate);
	//贷款种类
	String sLoanProductCode = request.getParameter("LoanProductCode");
	
//****************附加险信息1********************附加险信息1*******************
 System.out.println("****************附加险信息1*******************附加险信息1*******************");
	//主险代码 
	String sProductCode1 = request.getParameter("ProductCode1");
	//份数 
	String sIntialNumberOfUnits1 = request.getParameter("IntialNumberOfUnits1");
	//保费 
	String sModalPremAmt1 = request.getParameter("ModalPremAmt1"); 
	//保额
	String sInitCovAmt1 = request.getParameter("InitCovAmt1");
	//保险年期类型
	String sDurationMode1 = request.getParameter("DurationMode1"); 
	//保险年期 
	String sDuration1 = request.getParameter("Duration1"); 
	//缴费年期类型
	String sPaymentDurationMode1 = request.getParameter("PaymentDurationMode1");
	//缴费年期 
	String sPaymentDuration1 = request.getParameter("PaymentDuration1"); 
	
	String sPbDrawAge=request.getParameter("PbDrawAge");

	//主险代码 
	String sProductCode2 = request.getParameter("ProductCode2");
	//份数 
	String sIntialNumberOfUnits2 = request.getParameter("IntialNumberOfUnits2");
	//保费 
	String sModalPremAmt2 = request.getParameter("ModalPremAmt2"); 
	//保额
	String sInitCovAmt2 = request.getParameter("InitCovAmt2");
	
	//保险年期类型
	String sDurationMode2 = request.getParameter("DurationMode2"); 
	//保险年期 
	String sDuration2 = request.getParameter("Duration2"); 
	//缴费年期类型
	String sPaymentDurationMode2 = request.getParameter("PaymentDurationMode2");
	//缴费年期 
	String sPaymentDuration2 = request.getParameter("PaymentDuration2"); 
		
		String sAccCode1 = request.getParameter("AccCode1");
		String sAccCodeName1 = request.getParameter("AccCodeName1");
		String sAllocPercent1 = request.getParameter("AllocPercent1");
		String sAccCode2 = request.getParameter("AccCode2");
		String sAccCodeName2 = request.getParameter("AccCodeName2");
		String sAllocPercent2 = request.getParameter("AllocPercent2");
		String sAccCode3 = request.getParameter("AccCode3");
		String sAccCodeName3 = request.getParameter("AccCodeName3");
		String sAllocPercent3 = request.getParameter("AllocPercent3");	
		String sAccCode4 = request.getParameter("AccCode4");
		String sAccCodeName4 = request.getParameter("AccCodeName4");
		String sAllocPercent4 = request.getParameter("AllocPercent4");	
		String sAccCode5 = request.getParameter("AccCode5");
		String sAccCodeName5 = request.getParameter("AccCodeName5");
		String sAllocPercent5 = request.getParameter("AllocPercent5");	

	//缴费方式
	String sPaymentMode = request.getParameter("PaymentMode");
	//缴费形式(频次)
	String sPaymentMethod = request.getParameter("PaymentMethod");
	//领取方式
	String sBenefitMode = request.getParameter("BenefitMode");
	//红利领取方式
	String sDivType = request.getParameter("DivType"); 
	//保单传送方式
	String sPolicyDeliveryMethod = request.getParameter("PolicyDeliveryMethod"); 
	//自动垫交标志
	String sAutoPayFlag =request.getParameter("AutoPayFlag");
	
	//银行账号
	String sAccountNumber = request.getParameter("AccountNumber"); 
	//帐户姓名
	String sAcctHolderName = request.getParameter("AcctHolderName");
    String sPbDrawAgeTag=request.getParameter("PbDrawAgeTag");
	String sSpecialClause = request.getParameter("SpecialClause");
	String sOccupationIndicator = request.getParameter("OccupationIndicator");
	//投资日期标志
	String sInvestDateInd = request.getParameter("InvestDateInd");
	//首次额外追加保费
	String sFirstSuperaddAmt = request.getParameter("FirstSuperaddAmt");
	
	
	int iPaymentAmt = 0;
	if(sProductCode.equals("001")){
		if(sModalPremAmt == null || "".equals(sModalPremAmt)){
			sModalPremAmt="0";
		}
		iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue();
	}else if(sProductCode.equals("002")){
		if(sModalPremAmt == null || "".equals(sModalPremAmt)){
			sModalPremAmt="0";
		}
		
		if(sModalPremAmt1 == null || "".equals(sModalPremAmt1)){
			sModalPremAmt1="0";
		}
		if(sModalPremAmt2 == null || "".equals(sModalPremAmt2)){
			sModalPremAmt2="0";
		}
		if(sFirstSuperaddAmt == null || "".equals(sFirstSuperaddAmt)){
			sFirstSuperaddAmt="0";
		}
		if(sProductCode1.equals("102") || sProductCode2.equals("101")){
		iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue()+Integer.valueOf(sFirstSuperaddAmt).intValue()+Integer.valueOf(sModalPremAmt1).intValue() +Integer.valueOf(sModalPremAmt2).intValue();
		}else{
			iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue();
		}
	}
	String sPaymentAmt = String.valueOf(iPaymentAmt);	
	


//把保单信息在页面显示
		xmlContent = new String(""); 
   
		xmlContent += "一、交易信息\\n";
		xmlContent += "  银行端日期: " + sTransExeDate;
		xmlContent += "\t\t交易流水号: " + sTransRefGUID + "\\n";
		xmlContent += "  地区代码: " + sRegionCode; 
		xmlContent += "\t\t\t网点代码: " + sBranch + "\\n"; 
		xmlContent += "  柜员代码: " + sTeller;
		xmlContent += "\t\t\t投保日期: " + sSubmissionDate + "\\n"; 
		xmlContent += "  投保书号: " + sHOAppFormNumber;
		xmlContent += "\t保单印刷号: " + sProviderFormNumber + "\\n";
		xmlContent +="\t 销售人员代码+"+sBkRckrNo+"\\n";
		xmlContent +="\t 销售人员姓名+"+sBkSaleName+"\\n";
		xmlContent +="\t 销售人员资格证号+"+sBkSaleCertNo+"\\n";
		
		xmlContent += "二、投保人信息\\n";
		xmlContent += "  投保人姓名: " + sAppFullName;
		xmlContent += "\t\t\t投保人性别: " + sAppGender + "\\n";
		xmlContent += "  投保人证件类型: " + sAppGovtIDTC ;
		xmlContent += "\t\t投保人证件号码: " + sAppGovtID + "\\n";
		xmlContent += "  投保人生日: " + sAppBirthDate ;
		xmlContent += "\t\t投保人电子邮箱 : " + sAppAddrLine + "\\n";		
		xmlContent += "  投保人家庭电话: " + sAppDialNumber1;
		xmlContent += "\t投保人移动电话: " + sAppDialNumber3+ "\\n";
		xmlContent += "  投保人与被报人的关系: " + sAppToInsRelation+ "\\n";		
		xmlContent += "  投保人年收入: " + sPbInCome+ "\\n";		
		xmlContent += "  投保人家庭年收入: " + sPbHomeInCome+ "\\n";	
        xmlContent += "  投保人居民类型: " + sPbDenType+ "\\n";	
		
		xmlContent += "三、被保人信息\\n";
		xmlContent += "  被保人姓名: " + sInsFullName;
		xmlContent += "\t\t\t被保人性别: " + sInsGender + "\\n";
		xmlContent += "  被保人证件类型: " + sInsGovtIDTC ;
		xmlContent += "\t\t被保人证件号码: " + sInsGovtID + "\\n";
		xmlContent += "  被保人生日: " + sInsBirthDate ;
		xmlContent += "\t\t被保人电子邮箱 : " + sInsAddrLine + "\\n";		
		xmlContent += "  被保人家庭电话: " + sInsDialNumber1;
		xmlContent += "\t被保人移动电话: " + sInsDialNumber3+ "\\n";
		xmlContent += "  健康告知: " + sHealthIndicator+ "\\n";	
 
 
		xmlContent += "四、受益人信息\\n";
		xmlContent += "1、受益人信息1\\n";
		xmlContent += "  受益人姓名: " + sBnfFullName1;
		xmlContent += "\t\t\t受益人性别: " + sBnfGender1 + "\\n";
		xmlContent += "  受益人证件类型: " + sBnfGovtIDTC1 ;
		xmlContent += "\t\t受益人证件号码: " + sBnfGovtID1 + "\\n";
		xmlContent += "  受益人生日: " + sBnfBirthDate1 ;
		xmlContent += "\t\t收益比例 : " + sInterestPercent1 + "\\n";		
		xmlContent += "  受益顺序: " + sSequence1+ "\\n";	

		if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
		xmlContent += "2、受益人信息2\\n";
		xmlContent += "  受益人姓名: " + sBnfFullName2;
		xmlContent += "\t\t\t受益人性别: " + sBnfGender2 + "\\n";
		xmlContent += "  受益人证件类型: " + sBnfGovtIDTC2 ;
		xmlContent += "\t\t受益人证件号码: " + sBnfGovtID2 + "\\n";
		xmlContent += "  受益人生日: " + sBnfBirthDate2 ;
		xmlContent += "\t\t收益比例 : " + sInterestPercent2 + "\\n";		
		xmlContent += "  受益顺序: " + sSequence2+ "\\n";
		}
		
		if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
		xmlContent += "3、受益人信息3\\n";
		xmlContent += "  受益人姓名: " + sBnfFullName3;
		xmlContent += "\t\t\t受益人性别: " + sBnfGender3 + "\\n";
		xmlContent += "  受益人证件类型: " + sBnfGovtIDTC3 ;
		xmlContent += "\t\t受益人证件号码: " + sBnfGovtID3 + "\\n";
		xmlContent += "  受益人生日: " + sBnfBirthDate3 ;
		xmlContent += "\t\t收益比例 : " + sInterestPercent3 + "\\n";		
		xmlContent += "  受益顺序: " + sSequence3+ "\\n";
		}
		
		xmlContent += "五、主险信息\\n";
		xmlContent += "1、主险信息\\n";
		xmlContent += "  主险代码: " + sProductCode;
		xmlContent += "\t\t\t份数: " + sIntialNumberOfUnits ;
		xmlContent += "\t\t保费(分): " +  sModalPremAmt;  
		xmlContent += "\t\t保额(分): " + sInitCovAmt + "\\n";  
		xmlContent += "  保险年期类型: " + sDurationMode ;
		xmlContent += "\t\t保险年期 : " + sDuration ;		
		xmlContent += "\t\t缴费年期类型: " + sPaymentDurationMode ;		
		xmlContent += "\t\t缴费年期: " + sPaymentDuration + "\\n";	
		 
		if(!(sProductCode1==null||sProductCode1.equals(""))){
		xmlContent += "2、附加险信息1\\n";
		xmlContent += "  附加险代码: " + sProductCode1;
		xmlContent += "\t\t\t份数: " + sIntialNumberOfUnits1 ;
		xmlContent += "\t\t保费(分): " + sInitCovAmt1 ; 
		xmlContent += "\t\t保额(分): " + sModalPremAmt1 + "\\n";  
		xmlContent += "  保险年期类型: " + sDurationMode1 ;
		xmlContent += "\t\t保险年期 : " + sDuration1 ;		
		xmlContent += "\t\t缴费年期类型: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t缴费年期: " + sPaymentDuration1 + "\\n";
		}
		   
		if(!(sProductCode2==null||sProductCode2.equals(""))){
		xmlContent += "3、附加险信息2\\n";
		xmlContent += "  附加险代码: " + sProductCode2;
		xmlContent += "\t\t\t份数: " + sIntialNumberOfUnits2 ;
		xmlContent += "\t\t保费(分): " + sInitCovAmt2 ; 
		xmlContent += "\t\t保额(分): " + sModalPremAmt1 + "\\n";  
		xmlContent += "  保险年期类型: " + sDurationMode2 ;
		xmlContent += "\t\t保险年期 : " + sDuration2 ;		
		xmlContent += "\t\t缴费年期类型: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t缴费年期: " + sPaymentDuration2 + "\\n";
		} 
		
		if(!(sAccCode1==null||sAccCode1.equals(""))){
			xmlContent += "3、投资账户信息\\n";
			xmlContent += "  投资账户1: " + sAccCode1;
			xmlContent += "\t\t\t投资比率: " + sAllocPercent1 ;
			xmlContent += "\t\t投资账户2: " + sAccCode2 ; 
			xmlContent += "\t\t投资比率: " + sAllocPercent2 + "\\n";  
			xmlContent += "  投资账户3: " + sAccCode3 ;
			xmlContent += "\t\t投资比率: " + sAllocPercent3 + "\\n";
			xmlContent += "  投资账户4: " + sAccCode4 ;
			xmlContent += "\t\t投资比率: " + sAllocPercent4 + "\\n";
			xmlContent += "  投资账户5: " + sAccCode5 ;
			xmlContent += "\t\t投资比率: " + sAllocPercent5 + "\\n";
			} 
		
		xmlContent += "六、投保信息\\n"; 
		xmlContent += "  缴费方式: " + sPaymentMode;
		xmlContent += "\t\t缴费形式: " + sPaymentMethod ; 
		xmlContent += "\t\t领取方式: " + sBenefitMode ;  
		xmlContent += "\t\t红利领取方式: " + sDivType + "\\n";  
		xmlContent += "  保单传送方式: " + sPolicyDeliveryMethod ;
		xmlContent += "\t\t银行账号 : " + sAccountNumber;	
		xmlContent += "\t账号姓名 : " + sAcctHolderName ;	
		xmlContent += "\t\t特别约定: " + sSpecialClause + "\\n";	
		xmlContent += "\t\t投资日期标志 : " + sInvestDateInd;	
		xmlContent += "\t首次额外追加保费: " + sFirstSuperaddAmt + "\\n";
	
String InsuId = NbcbConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
		
 System.out.println("************************装载XML************************");		
//根节点
ElementLis REQUEST = new ElementLis("REQUEST");
ElementLis DIST = new ElementLis("DIST",REQUEST);	

ElementLis BANK = new ElementLis("BANK","NBYH",DIST);
ElementLis ZONE = new ElementLis("ZONE",sRegionCode,DIST);
ElementLis DEPT = new ElementLis("DEPT",sBranch,DIST);
ElementLis TELLER = new ElementLis("TELLER",sTeller,DIST);
ElementLis FINANCIALID = new ElementLis("FINANCIALID",sBkRckrNo,DIST);
ElementLis FINANCIALNAME = new ElementLis("FINANCIALNAME",sBkSaleName,DIST);

ElementLis BUSI = new ElementLis("BUSI",REQUEST);
ElementLis TRSDATE = new ElementLis("TRSDATE",sTransExeDate,BUSI);
ElementLis TRANS = new ElementLis("TRANS",sTransRefGUID,BUSI);

ElementLis CONTENT = new ElementLis("CONTENT",BUSI);

if(sFlag.equals("1")){
ElementLis CATE = new ElementLis("CATE","2",BUSI);
ElementLis SUBJECT = new ElementLis("SUBJECT","1",BUSI);
ElementLis MAIN = new ElementLis("MAIN",CONTENT);
ElementLis APPNO = new ElementLis("APPNO",sHOAppFormNumber,MAIN);
ElementLis CONTPRTNO = new ElementLis("BILL_USED",sProviderFormNumber,MAIN);
ElementLis APPDATE = new ElementLis("APPDATE",sSubmissionDate,MAIN);
ElementLis PAYACC = new ElementLis("PAYACC",sAccountNumber,MAIN);
ElementLis DELIVER = new ElementLis("DELIVER","1",MAIN);
ElementLis CALL_T = new ElementLis("CALL_T",MAIN);
ElementLis SPEC = new ElementLis("SPEC",MAIN);
//投保人
ElementLis TBR = new ElementLis("TBR",CONTENT);
ElementLis tNAME = new ElementLis("NAME",sAppFullName,TBR);
ElementLis tSEX = new ElementLis("SEX",sAppGender,TBR);
ElementLis tBIRTH = new ElementLis("BIRTH",sAppBirthDate,TBR);
ElementLis tIDTYPE = new ElementLis("IDTYPE",sAppGovtIDTC,TBR);
ElementLis tIDNO = new ElementLis("IDNO",sAppGovtID,TBR);
ElementLis tIDVALIDATE = new ElementLis("IDVALIDATE",sAppGovtTermDate,TBR);//未确定 证件有效日期
ElementLis tADDR = new ElementLis("ADDR",sAppLine1,TBR);
ElementLis tZIP = new ElementLis("ZIP",sAppZip,TBR);
ElementLis tTEL = new ElementLis("TEL",sAppDialNumber1,TBR);
ElementLis tMP = new ElementLis("MP",sAppDialNumber3,TBR);
ElementLis tLITTLE_MOBILE = new ElementLis("LITTLE_MOBILE",TBR);
ElementLis tEMAIL = new ElementLis("EMAIL",sAppAddrLine,TBR);
ElementLis tBBR_RELA = new ElementLis("BBR_RELA",sAppToInsRelation,TBR);
ElementLis tOccupation = new ElementLis("Occupation",TBR);
ElementLis tIncomeYear = new ElementLis("IncomeYear",sPbInCome,TBR);
ElementLis tFamilyEstSalary = new ElementLis("FamilyEstSalary",sPbHomeInCome,TBR);
ElementLis tResident = new ElementLis("Resident",sPbDenType,TBR);
//被保人
ElementLis BBR = new ElementLis("BBR",CONTENT);
ElementLis bNAME = new ElementLis("NAME",sInsFullName,BBR);
ElementLis bSEX = new ElementLis("SEX",sInsGender,BBR);
ElementLis bBIRTH = new ElementLis("BIRTH",sInsBirthDate,BBR);
ElementLis bIDTYPE = new ElementLis("IDTYPE",sInsGovtIDTC,BBR);
ElementLis bIDNO = new ElementLis("IDNO",sInsGovtID,BBR);
ElementLis bIDVALIDATE = new ElementLis("IDVALIDATE",sInsGovtTermDate,BBR);//未确定
ElementLis bADDR = new ElementLis("ADDR",sInsLine1,BBR);
ElementLis bZIP = new ElementLis("ZIP",sInsZip,BBR);
ElementLis bTEL = new ElementLis("TEL",sInsDialNumber1,BBR);
ElementLis bLITTLE_MOBILE = new ElementLis("LITTLE_MOBILE",BBR);
ElementLis bEMAIL = new ElementLis("EMAIL",sInsAddrLine,BBR);
//法定受益人
if(sBeneficiaryIndicator.equals("Y")){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",SYR);
	ElementLis sSEX = new ElementLis("SEX",SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",SYR);
	ElementLis sIDNO = new ElementLis("IDNO",SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",SYR);//未确定
	ElementLis sORDER = new ElementLis("ORDER",SYR);
	ElementLis sRATIO = new ElementLis("RATIO",SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA","Z",SYR);//必要未加
}else if(!(sBnfFullName1==null||sBnfFullName1.equals(""))){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",sBnfFullName1,SYR);
	ElementLis sSEX = new ElementLis("SEX",sBnfGender1,SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",sBnfBirthDate1,SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",sBnfGovtIDTC1,SYR);
	ElementLis sIDNO = new ElementLis("IDNO",sBnfGovtID1,SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",sBnfAdress1,SYR);//未确定
	ElementLis sORDER = new ElementLis("ORDER",sSequence1,SYR);
	ElementLis sRATIO = new ElementLis("RATIO",sInterestPercent1,SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA",sBnfToInsRelation1,SYR);//必要未加
}
ElementLis bMP = new ElementLis("MP",sInsDialNumber3,BBR);
//受益人
if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",sBnfFullName2,SYR);
	ElementLis sSEX = new ElementLis("SEX",sBnfGender2,SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",sBnfBirthDate2,SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",sBnfGovtIDTC2,SYR);
	ElementLis sIDNO = new ElementLis("IDNO",sBnfGovtID2,SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",sBnfAdress2,SYR);//未确定
	ElementLis sORDER = new ElementLis("ORDER",sSequence2,SYR);
	ElementLis sRATIO = new ElementLis("RATIO",sInterestPercent2,SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA",sBnfToInsRelation2,SYR);//必要未加
}
//受益人
if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
	ElementLis SYR = new ElementLis("SYR",CONTENT);
	ElementLis sNAME = new ElementLis("NAME",sBnfFullName3,SYR);
	ElementLis sSEX = new ElementLis("SEX",sBnfGender3,SYR);
	ElementLis sBIRTH = new ElementLis("BIRTH",sBnfBirthDate3,SYR);
	ElementLis sIDTYPE = new ElementLis("IDTYPE",sBnfGovtIDTC3,SYR);
	ElementLis sIDNO = new ElementLis("IDNO",sBnfGovtID3,SYR);
	ElementLis sIDVALIDATE = new ElementLis("IDVALIDATE",sBnfAdress3,SYR);//未确定
	ElementLis sORDER = new ElementLis("ORDER",sSequence3,SYR);
	ElementLis sRATIO = new ElementLis("RATIO",sInterestPercent3,SYR);
	ElementLis sBBR_RELA = new ElementLis("BBR_RELA",sBnfToInsRelation3,SYR);//必要未加
}
//险种
ElementLis PTS = new ElementLis("PTS",CONTENT);
ElementLis PT = new ElementLis("PT",PTS);
ElementLis ID = new ElementLis("ID",sProductCode,PT);
ElementLis FLAG = new ElementLis("FLAG","1",PT);//主附险标志
ElementLis UNIT = new ElementLis("UNIT",sIntialNumberOfUnits,PT);
ElementLis CRG_T = new ElementLis("CRG_T",sPaymentMode,PT);
ElementLis CRG_Y = new ElementLis("CRG_Y",sPaymentDuration,PT);
ElementLis COVER_T = new ElementLis("COVER_T",sDurationMode,PT);
ElementLis COVER_Y = new ElementLis("COVER_Y",sDuration,PT);
ElementLis PREMIUM = new ElementLis("PREMIUM",sModalPremAmt,PT);
ElementLis AMNT = new ElementLis("AMNT",sInitCovAmt,PT);
ElementLis DRAW_T = new ElementLis("DRAW_T",PT);
ElementLis DRAW_FST = new ElementLis("DRAW_FST",PT);
ElementLis DRAW_LST = new ElementLis("DRAW_LST",PT);
ElementLis HLLQ_T = new ElementLis("HLLQ_T",sDivType,PT);
ElementLis INVEST = new ElementLis("INVEST",PT);
ElementLis NVEST_ACC_TYPE = new ElementLis("NVEST_ACC_TYPE",INVEST);
ElementLis INVEST_ACC = new ElementLis("INVEST_ACC",INVEST);
ElementLis ACC_ID = new ElementLis("ACC_ID",INVEST_ACC);
ElementLis RATE = new ElementLis("RATE",INVEST_ACC);

//附加险1
if(!sProductCode1.equals("") && sProductCode1 !=null){
	ElementLis PT1 = new ElementLis("PT",PTS);
	ElementLis ID1 = new ElementLis("ID",sProductCode1,PT1);
	ElementLis FLAG1 = new ElementLis("FLAG","0",PT1);//主附险标志
	ElementLis UNIT1 = new ElementLis("UNIT",sIntialNumberOfUnits1,PT1);
	ElementLis CRG_T1 = new ElementLis("CRG_T",sPaymentDurationMode1,PT1);
	ElementLis CRG_Y1 = new ElementLis("CRG_Y",sPaymentDuration1,PT1);
	ElementLis COVER_T1 = new ElementLis("COVER_T",sDurationMode1,PT1);
	ElementLis COVER_Y1 = new ElementLis("COVER_Y",sDuration1,PT1);
	ElementLis PREMIUM1 = new ElementLis("PREMIUM",sModalPremAmt1,PT1);
	ElementLis AMNT1 = new ElementLis("AMNT",sInitCovAmt1,PT1);
	ElementLis DRAW_T1 = new ElementLis("DRAW_T",PT1);
	ElementLis DRAW_FST1 = new ElementLis("DRAW_FST",PT1);
	ElementLis DRAW_LST1 = new ElementLis("DRAW_LST",PT1);
	ElementLis HLLQ_T1 = new ElementLis("HLLQ_T",sDivType,PT1);
	ElementLis INVEST1 = new ElementLis("INVEST",PT1);
	ElementLis NVEST_ACC_TYPE1 = new ElementLis("NVEST_ACC_TYPE",INVEST1);
	ElementLis INVEST_ACC1 = new ElementLis("INVEST_ACC",INVEST1);
	ElementLis ACC_ID1 = new ElementLis("ACC_ID",INVEST_ACC1);
	ElementLis RATE1 = new ElementLis("RATE",INVEST_ACC1);
}

ElementLis HEALTH = new ElementLis("HEALTH",CONTENT);
ElementLis NOTICE = new ElementLis("NOTICE",sHealthIndicator,HEALTH);

ElementLis BILL = new ElementLis("BILL",CONTENT);
ElementLis BILL_USED = new ElementLis("BILL_USED",BILL);
ElementLis BILL_T = new ElementLis("BILL_T",BILL);

ElementLis LFBF = new ElementLis("LFBF",CONTENT);
ElementLis LifeBnft = new ElementLis("LifeBnft",LFBF);
ElementLis Nation = new ElementLis("Nation",LifeBnft);
ElementLis Relation = new ElementLis("Relation",LifeBnft);
ElementLis Province = new ElementLis("Province",LifeBnft);
ElementLis IdNo = new ElementLis("IdNo",LifeBnft);
ElementLis BirthDate = new ElementLis("BirthDate",LifeBnft);
ElementLis Name = new ElementLis("Name",LifeBnft);
ElementLis Sex = new ElementLis("Sex",LifeBnft);
ElementLis HomeAdrress = new ElementLis("HomeAdrress",LifeBnft);
ElementLis ContactZip = new ElementLis("ContactZip",LifeBnft);
ElementLis ContactPhone = new ElementLis("ContactPhone",LifeBnft);
ElementLis Email = new ElementLis("Email",LifeBnft);
ElementLis Occupation = new ElementLis("Occupation",LifeBnft);

ElementLis LifeBnftAssign = new ElementLis("LifeBnftAssign",LFBF);
ElementLis Beneficiary = new ElementLis("Beneficiary",LifeBnftAssign);
ElementLis BnftType = new ElementLis("BnftType",Beneficiary);
ElementLis BnftNo = new ElementLis("BnftNo",Beneficiary);
ElementLis Percent = new ElementLis("Percent",Beneficiary);

ElementLis AXDGZ = new ElementLis("AXDGZ",CONTENT);
ElementLis LoanNo = new ElementLis("LoanNo",sContractNo,AXDGZ);
ElementLis LoanBank = new ElementLis("LoanBank",AXDGZ);
ElementLis LoanDate = new ElementLis("LoanDate",sLoanStartDate,AXDGZ);
ElementLis LoanEndDate = new ElementLis("LoanEndDate",sLoanEndDate,AXDGZ);
ElementLis LoanType = new ElementLis("LoanType",sLoanProductCode,AXDGZ);
ElementLis AccNo = new ElementLis("AccNo",sLoanAccountNo,AXDGZ);
ElementLis LoanPrem = new ElementLis("LoanPrem",sLoanAmount,AXDGZ);
ElementLis InsuDate = new ElementLis("InsuDate",sContractEffDate,AXDGZ);
ElementLis InsuEndDate = new ElementLis("InsuEndDate",sContractEndDate,AXDGZ);
}else if(sFlag.equals("2")){
	ElementLis CATE = new ElementLis("CATE","102",BUSI);
	ElementLis SUBJECT = new ElementLis("SUBJECT","1",BUSI);
ElementLis OLDTRANS = new ElementLis("OLDTRANS",sReqsrNo,CONTENT);
ElementLis APPNO = new ElementLis("APPNO",sHOAppFormNumber,CONTENT);
}
	Document pXmlDoc = new Document(REQUEST);	
    JdomUtil.print(pXmlDoc);
	System.out.println("报文信息：：：");
	JdomUtil.print(pXmlDoc);
	int iPort = Integer.valueOf(sPort).intValue();
	NCCBTestUI mTestUI = new NCCBTestUI(sIp,iPort);  
	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
	byte[] mOutBytes = mTestUI.sendRequest(inputStream);
	Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
	
	JdomUtil.print(mOutXmlDoc);
	ResultCode = mOutXmlDoc.getRootElement().getChild("BUSI").getChild("CONTENT").getChildTextTrim("REJECT_CODE");
	ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("BUSI").getChild("CONTENT").getChildTextTrim("REJECT_DESC");
	PubFun1 pubfun = new PubFun1();
	if (ResultCode.equals("1")) {
		strNewTransNo = pubfun.CreateMaxNo("TransNo", 16);
		strNewHOAppFormNumber = "2"+pubfun.CreateMaxNo("PrtNo",9);
	      System.out.println("-----------开始取数（save页面）----------");
	} else {
		strNewTransNo = sTransRefGUID;
	    ResultInfoDesc = ResultInfoDesc.replace("%","%25");
		Content = "交易失败：" + ResultInfoDesc ;
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