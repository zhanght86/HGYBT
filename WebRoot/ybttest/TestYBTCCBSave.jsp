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
<%@page import="com.sinosoft.midplat.ccb.CcbConf"%>
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
	
	//投保人单位地址	
	String sAppWorkAddress = request.getParameter("AppWorkAddress");
	//投保人单位邮政编码
	String sAppWorkZipCode = request.getParameter("AppWorkZipCode"); 
	 //投保人单位电话 WorkPhone
	String sWorkPhone = request.getParameter("WorkPhone");
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
	//未成年人累计身故风险保额
	String sInsCovSumAmt = request.getParameter("InsCovSumAmt");
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
	
String InsuId = CcbConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
		
 System.out.println("************************装载XML************************");		
 //根节点
 ElementLis Transaction = new ElementLis("Transaction");	 
 ElementLis Transaction_Header = new ElementLis("Transaction_Header",Transaction);  
 ElementLis LiBankID = new ElementLis("LiBankID","CCB",Transaction_Header);  
//保险公司代码
 ElementLis PbInsuId = new ElementLis("PbInsuId",InsuId,Transaction_Header);  
//银行流水号
 ElementLis BkPlatSeqNo = new ElementLis("BkPlatSeqNo",sTransRefGUID,Transaction_Header);  
 ElementLis BkTxCode = new ElementLis("BkTxCode",sFlag,Transaction_Header);  
 //交易渠道代号
 ElementLis BkChnlNo = new ElementLis("BkChnlNo","1",Transaction_Header);  
 //原始请求方机构代码
 ElementLis BkBrchNo = new ElementLis("BkBrchNo",sRegionCode+sBranch,Transaction_Header);  
//交易柜员代码
 ElementLis BkTellerNo = new ElementLis("BkTellerNo",sTeller,Transaction_Header);  
//银行交易日期
 ElementLis BkPlatDate = new ElementLis("BkPlatDate",sTransExeDate,Transaction_Header);  
//银行交易时间
 ElementLis BkPlatTime = new ElementLis("BkPlatTime",sTransExeTime,Transaction_Header);  
 ElementLis Transaction_Body = new ElementLis("Transaction_Body",Transaction);  
 if(sFlag.equals("OPR001")){
//投保人姓名 
ElementLis PbHoldName = new ElementLis("PbHoldName",sAppFullName,Transaction_Body);  
//投保人性别 
ElementLis PbHoldSex = new ElementLis("PbHoldSex",sAppGender,Transaction_Body);	
//投保人出生日期
ElementLis PbHoldBirdy = new ElementLis("PbHoldBirdy",sAppBirthDate,Transaction_Body);
//投保人证件类型
ElementLis  PbHoldIdType = new ElementLis("PbHoldIdType",sAppGovtIDTC,Transaction_Body);
//投保人证件号码 
ElementLis PbHoldId = new ElementLis("PbHoldId",sAppGovtID,Transaction_Body);
//投保人通讯地址
ElementLis PbHoldHomeAddr = new ElementLis("PbHoldHomeAddr",sAppLine1,Transaction_Body);

//投保人单位地址
ElementLis PbHoldAddr = new ElementLis("PbHoldAddr",sAppWorkAddress,Transaction_Body);
//投保人单位邮编
ElementLis PbHoldPost = new ElementLis("PbHoldPost",sAppWorkZipCode,Transaction_Body);
//投保人邮编
ElementLis PbHoldHomePost = new ElementLis("PbHoldHomePost",sAppZip,Transaction_Body);
//投保人住宅电话 (投保人固定电话)
ElementLis PbHoldHomeTele = new ElementLis("PbHoldHomeTele",sAppDialNumber1,Transaction_Body);
//投保人单位电话
ElementLis PbHoldOfficTele = new ElementLis("PbHoldOfficTele",sWorkPhone,Transaction_Body);
//投保人移动电话
ElementLis PbHoldMobl = new ElementLis("PbHoldMobl",sAppDialNumber3,Transaction_Body);
//投保人邮箱
ElementLis PbHoldEmail = new ElementLis("PbHoldEmail",sAppAddrLine,Transaction_Body);
//投保人职业类别
ElementLis PbHoldOccupCode = new ElementLis("PbHoldOccupCode","1601001",Transaction_Body);
//国籍
ElementLis PbNationality = new ElementLis("PbNationality",sAppCountry,Transaction_Body);
//职业代码
ElementLis ApplJobCode = new ElementLis("PbHoldOccupCode",sApplJobCode,Transaction_Body);
		xmlContent +="\t 销售人员代码+"+sBkRckrNo+"\\n";
		xmlContent +="\t 销售人员姓名+"+sBkSaleName+"\\n";
		xmlContent +="\t 销售人员资格证号+"+sBkSaleCertNo+"\\n";
		
//投保人证件到期日 
ElementLis PbIdEndDate = new ElementLis("PbIdEndDate",sAppGovtTermDate,Transaction_Body);
//销售人员姓名
ElementLis BkSaleName = new ElementLis("BkSaleName",sBkSaleName,Transaction_Body);
//销售人员代码
ElementLis BkRckrNo = new ElementLis("BkRckrNo",sBkRckrNo,Transaction_Body);
//销售人员资格证件号 
ElementLis BkSaleCertNo = new ElementLis("BkSaleCertNo",sBkSaleCertNo,Transaction_Body);
//投保人年收入
ElementLis PbInCome = new ElementLis("PbInCome",sPbInCome,Transaction_Body);
//投保人家庭年收入
ElementLis PbHomeInCome = new ElementLis("PbHomeInCome",sPbHomeInCome,Transaction_Body);
//投保人居民类型
ElementLis PbDenType = new ElementLis("PbDenType",sPbDenType,Transaction_Body);
//投保人婚姻状况
//ElementLis AppMarStat = new ElementLis("AppMarStat","",Transaction_Body);
//投保人与被保人关系
ElementLis PbHoldRcgnRela = new ElementLis("PbHoldRcgnRela",sAppToInsRelation,Transaction_Body);

//被保人信息
ElementLis LiRcgnName = new ElementLis("LiRcgnName",sInsFullName,Transaction_Body);
ElementLis LiRcgnSex = new ElementLis("LiRcgnSex",sInsGender,Transaction_Body);
ElementLis LiRcgnBirdy = new ElementLis("LiRcgnBirdy",sInsBirthDate,Transaction_Body); 
ElementLis LiRcgnIdType = new ElementLis("LiRcgnIdType",sInsGovtIDTC,Transaction_Body);
ElementLis LiRcgnId = new ElementLis("LiRcgnId",sInsGovtID,Transaction_Body);
//被保人地址
ElementLis LiRcgnAddr = new ElementLis("LiRcgnAddr",sInsLine1,Transaction_Body);
ElementLis LiRcgnPost = new ElementLis("LiRcgnPost",sInsZip,Transaction_Body);
//ElementLis InsCompany = new ElementLis("InsCompany","",Transaction_Body);
//ElementLis InsCall = new ElementLis("InsCall","",Transaction_Body);
ElementLis LiRcgnOccupCode = new ElementLis("LiRcgnOccupCode","1601001",Transaction_Body);

//被保人年收入
ElementLis LiRcgnTele = new ElementLis("LiRcgnTele",sInsDialNumber1,Transaction_Body);
ElementLis LiRcgnMobl = new ElementLis("LiRcgnMobl",sInsDialNumber3,Transaction_Body);
ElementLis LiIdEndDate = new ElementLis("LiIdEndDate",sInsGovtTermDate,Transaction_Body);
//职业代码
ElementLis InsuJobCode = new ElementLis("LiRcgnOccupCode",sInsuJobCode,Transaction_Body);


ElementLis LiNationality = new ElementLis("LiNationality",sInsCountry,Transaction_Body);
//被保人婚姻状况
//ElementLis InsMarStat = new ElementLis("InsMarStat","",Transaction_Body);

//受益人信息
ElementLis PbBenfNum = new ElementLis("PbBenfNum","",Transaction_Body);
ElementLis Benf_List = new ElementLis("Benf_List",Transaction_Body);
int BenefitNum=0;
//第一受益人
if(!(sBnfFullName1.equals(""))){
ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
ElementLis PbBenfType = new ElementLis("PbBenfType",sBenefiType1,Benf_Detail);
ElementLis PbBenfSer = new ElementLis("PbBenfSer","",Benf_Detail);
ElementLis PbBenfSequ = new ElementLis("PbBenfSequ",sSequence1,Benf_Detail);
ElementLis PbBenfName = new ElementLis("PbBenfName",sBnfFullName1,Benf_Detail);
ElementLis PbBenfSex = new ElementLis("PbBenfSex",sBnfGender1,Benf_Detail);
ElementLis PbBenfBirdy = new ElementLis("PbBenfBirdy",sBnfBirthDate1,Benf_Detail); 
ElementLis PbBenfIdType = new ElementLis("PbBenfIdType",sBnfGovtIDTC1,Benf_Detail);
ElementLis PbBenfId = new ElementLis("PbBenfId",sBnfGovtID1,Benf_Detail);
ElementLis PbBenfIdStartDate = new ElementLis("PbBenfIdStartDate","",Benf_Detail);
ElementLis PbBenfIdEndDate = new ElementLis("PbBenfIdEndDate","",Benf_Detail);
ElementLis PbBenfNational = new ElementLis("PbBenfNational","0156",Benf_Detail);
ElementLis PbBenfHoldRela = new ElementLis("PbBenfHoldRela",sBnfToInsRelation1,Benf_Detail);
ElementLis PbBenfProp = new ElementLis("PbBenfProp",sInterestPercent1,Benf_Detail);
ElementLis PbBenfAddr = new ElementLis("PbBenfAddr",sBnfAdress1,Benf_Detail);
BenefitNum++;
}
//如果有第二受益人 									
if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
	ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
	ElementLis PbBenfType = new ElementLis("PbBenfType",sBenefiType2,Benf_Detail);
	ElementLis PbBenfSer = new ElementLis("PbBenfSer","",Benf_Detail);
	ElementLis PbBenfSequ = new ElementLis("PbBenfSequ",sSequence2,Benf_Detail);
	ElementLis PbBenfName = new ElementLis("PbBenfName",sBnfFullName2,Benf_Detail);
	ElementLis PbBenfSex = new ElementLis("PbBenfSex",sBnfGender2,Benf_Detail);
	ElementLis PbBenfBirdy = new ElementLis("PbBenfBirdy",sBnfBirthDate2,Benf_Detail); 
	ElementLis PbBenfIdType = new ElementLis("PbBenfIdType",sBnfGovtIDTC2,Benf_Detail);
	ElementLis PbBenfId = new ElementLis("PbBenfId",sBnfGovtID2,Benf_Detail);
	ElementLis PbBenfIdStartDate = new ElementLis("PbBenfIdStartDate","",Benf_Detail);
	ElementLis PbBenfIdEndDate = new ElementLis("PbBenfIdEndDate","",Benf_Detail);
	ElementLis PbBenfNational = new ElementLis("PbBenfNational","0156",Benf_Detail);
	ElementLis PbBenfHoldRela = new ElementLis("PbBenfHoldRela",sBnfToInsRelation2,Benf_Detail);
	ElementLis PbBenfProp = new ElementLis("PbBenfProp",sInterestPercent2,Benf_Detail);
	ElementLis PbBenfAddr = new ElementLis("PbBenfAddr",sBnfAdress2,Benf_Detail);
	BenefitNum++;
}
//如果有第三受益人 									 
if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
	ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
	ElementLis PbBenfType = new ElementLis("PbBenfType",sBenefiType3,Benf_Detail);
	ElementLis PbBenfSer = new ElementLis("PbBenfSer","",Benf_Detail);
	ElementLis PbBenfSequ = new ElementLis("PbBenfSequ",sSequence3,Benf_Detail);
	ElementLis PbBenfName = new ElementLis("PbBenfName",sBnfFullName3,Benf_Detail);
	ElementLis PbBenfSex = new ElementLis("PbBenfSex",sBnfGender3,Benf_Detail);
	ElementLis PbBenfBirdy = new ElementLis("PbBenfBirdy",sBnfBirthDate3,Benf_Detail); 
	ElementLis PbBenfIdType = new ElementLis("PbBenfIdType",sBnfGovtIDTC3,Benf_Detail);
	ElementLis PbBenfId = new ElementLis("PbBenfId",sBnfGovtID3,Benf_Detail);
	ElementLis PbBenfIdStartDate = new ElementLis("PbBenfIdStartDate","",Benf_Detail);
	ElementLis PbBenfIdEndDate = new ElementLis("PbBenfIdEndDate","",Benf_Detail);
	ElementLis PbBenfNational = new ElementLis("PbBenfNational","0156",Benf_Detail);
	ElementLis PbBenfHoldRela = new ElementLis("PbBenfHoldRela",sBnfToInsRelation3,Benf_Detail);
	ElementLis PbBenfProp = new ElementLis("PbBenfProp",sInterestPercent3,Benf_Detail);
	ElementLis PbBenfAddr = new ElementLis("PbBenfAddr",sBnfAdress3,Benf_Detail);
	BenefitNum++;
}
    String BenitNum=""+BenefitNum;
    PbBenfNum.setText(BenitNum);//修改受益人个数。
    
	//主险信息
	//险种代码
	ElementLis PbInsuType = new ElementLis("PbInsuType",sProductCode,Transaction_Body); 
	//险种名称
	ElementLis PbInsuName = new ElementLis("PbInsuName","",Transaction_Body); 
	// 投保份数
	ElementLis PbSlipNumb = new ElementLis("PbSlipNumb",sIntialNumberOfUnits,Transaction_Body);
	//基本保费
	ElementLis PbInsuExp = new ElementLis("PbInsuExp",sModalPremAmt,Transaction_Body);
	//基本保额
	ElementLis PbInsuAmt = new ElementLis("PbInsuAmt", sInitCovAmt,Transaction_Body);
	//可选部分身故保险金额
	ElementLis PbMainInsuExp = new ElementLis("PbMainInsuExp","",Transaction_Body); 
	//首次额外追加保费
	ElementLis PbInsuExpAppd = new ElementLis("PbInsuExpAppd","",Transaction_Body); 
	//保单递送方式
	ElementLis PbSendMode = new ElementLis("PbSendMode",sPolicyDeliveryMethod,Transaction_Body);
	
	//起保日期
	ElementLis PbBeginDate = new ElementLis("PbBeginDate","",Transaction_Body); 
	//付费方式
	ElementLis PbInsuPayMode = new ElementLis("PbInsuPayMode",sPaymentMethod,Transaction_Body); 
	   //缴费帐户
	ElementLis BkAcctNo1 = new ElementLis("BkAcctNo1",sAccountNumber,Transaction_Body);
	//缴费方式	
	ElementLis PbPayPeriod = new ElementLis("PbPayPeriod",sPaymentMode,Transaction_Body);
	//特别约定
	ElementLis LiSpec = new ElementLis("LiSpec",sSpecialClause,Transaction_Body); 
	//满期保险金领取方式 和 年金/生存金领取方式等
	ElementLis LiExpireInsuDrawMode = new ElementLis("LiExpireInsuDrawMode","",Transaction_Body); 
	ElementLis LiRenteDrawMode = new ElementLis("LiRenteDrawMode",sBenefitMode,Transaction_Body); 
	//保单递送方式
	ElementLis PbAutoPayTag = new ElementLis("PbAutoPayTag",sAutoPayFlag,Transaction_Body); 
	//红利分配标记
	ElementLis LiBonusDistbTag = new ElementLis("LiBonusDistbTag","",Transaction_Body); 
	//红利领取方式
	ElementLis LiBonusGetMode = new ElementLis("LiBonusGetMode",sDivType,Transaction_Body);
	
	ElementLis BkAcctNo3 = new ElementLis("BkAcctNo3","",Transaction_Body); 
	ElementLis BkAcctNo2 = new ElementLis("BkAcctNo2","",Transaction_Body); 
	ElementLis LiPayOffTag = new ElementLis("LiPayOffTag","",Transaction_Body); 
	//健康告知标记
	ElementLis LiHealthTag = new ElementLis("LiHealthTag",sHealthIndicator,Transaction_Body);
	//缴费年期
	ElementLis PbPayAgeTag = new ElementLis("PbPayAgeTag",sPaymentDuration,Transaction_Body);
	
	ElementLis PbPayAge = new ElementLis("PbPayAge","",Transaction_Body); 
	
	ElementLis PbDrawAgeTag = new ElementLis("PbDrawAgeTag",sPbDrawAgeTag,Transaction_Body); 
	ElementLis PbDrawAge = new ElementLis("PbDrawAge",sPbDrawAge,Transaction_Body); 
	//保险年期类型
	ElementLis PbInsuYearFlag = new ElementLis("PbInsuYearFlag",sDurationMode,Transaction_Body);
	//保险期限
	ElementLis LiInsuPeriod = new ElementLis("LiInsuPeriod",sDuration,Transaction_Body);
	ElementLis PbInsuYear = new ElementLis("PbInsuYear","",Transaction_Body); 
	ElementLis LiImpawnTag = new ElementLis("LiImpawnTag","",Transaction_Body); 
	ElementLis BkNum1 = new ElementLis("BkNum1","",Transaction_Body); 
	ElementLis Appd_List = new ElementLis("Appd_List",Transaction_Body); 
    int RiskCount=0;
    //如果有附加险1    
	if(!(sProductCode1==null||sProductCode1.equals(""))){
		RiskCount++;
		ElementLis Appd_Detail = new ElementLis("Appd_Detail",Appd_List);
		ElementLis LiAppdInsuType = new ElementLis("LiAppdInsuType",sProductCode1,Appd_Detail);
		
		ElementLis LiAppdInsuName = new ElementLis("LiAppdInsuName","",Appd_Detail);
		//投保份数
		ElementLis LiAppdInsuNumb = new ElementLis("LiAppdInsuNumb",sIntialNumberOfUnits1,Appd_Detail);	
		ElementLis LiAppdInsuExp = new ElementLis("LiAppdInsuExp",sModalPremAmt1,Appd_Detail);
		ElementLis LiAppdInsuAmot = new ElementLis("LiAppdInsuAmot",sInitCovAmt1,Appd_Detail);
		
		ElementLis LiAppdInsuTerm = new ElementLis("LiAppdInsuTerm",sDuration1,Appd_Detail);
	
		ElementLis LiAppdInsuPayTerm = new ElementLis("LiAppdInsuPayTerm",sPaymentDuration1,Appd_Detail);
		
		ElementLis LiBackTag = new ElementLis("LiBackTag","",Appd_Detail);
		
		ElementLis PiReserve = new ElementLis("PiReserve","",Appd_Detail);
	}
//如果有附加险2  
	if(!(sProductCode2==null||sProductCode2.equals(""))){	
		RiskCount++;
		ElementLis Appd_Detail = new ElementLis("Appd_Detail",Appd_List);
		ElementLis LiAppdInsuType = new ElementLis("LiAppdInsuType",sProductCode2,Appd_Detail);
		
		ElementLis LiAppdInsuName = new ElementLis("LiAppdInsuName","",Appd_Detail);
		//投保份数
		ElementLis LiAppdInsuNumb = new ElementLis("LiAppdInsuNumb",sIntialNumberOfUnits2,Appd_Detail);	
		ElementLis LiAppdInsuExp = new ElementLis("LiAppdInsuExp",sModalPremAmt2,Appd_Detail);
		ElementLis LiAppdInsuAmot = new ElementLis("LiAppdInsuAmot",sInitCovAmt2,Appd_Detail);
		
		ElementLis LiAppdInsuTerm = new ElementLis("LiAppdInsuTerm",sDuration2,Appd_Detail);
	
		ElementLis LiAppdInsuPayTerm = new ElementLis("LiAppdInsuPayTerm",sPaymentDuration2,Appd_Detail);
		
		ElementLis LiBackTag = new ElementLis("LiBackTag","",Appd_Detail);
		
		ElementLis PiReserve = new ElementLis("PiReserve","",Appd_Detail);
	}
	String RiskCt=""+RiskCount;
	BkNum1.setText(RiskCt);
	
	//投保书号
	ElementLis PbApplNo = new ElementLis("PbApplNo",sHOAppFormNumber,Transaction_Body);
	ElementLis BkVchNo = new ElementLis("BkVchNo",sProviderFormNumber,Transaction_Body);
	ElementLis LiInsuSlipPWD = new ElementLis("LiInsuSlipPWD","",Transaction_Body);
	
	ElementLis PiTrfVchNo = new ElementLis("PiTrfVchNo","",Transaction_Body);
	
//	ElementLis BkRckrNo = new ElementLis("BkRckrNo",sTeller,Transaction_Body);
	//争议仲裁方式
	ElementLis PiZyzcfs = new ElementLis("PiZyzcfs","",Transaction_Body);
	//仲裁机构名称
	ElementLis PiZcinst = new ElementLis("PiZcinst","",Transaction_Body);
	//未成年人累计身故风险保额	
	ElementLis PiZxbe20 = new ElementLis("PiZxbe20",sInsCovSumAmt,Transaction_Body);
	
	//地区码
	ElementLis BkAreaNo = new ElementLis("BkAreaNo",sRegionCode,Transaction_Body);
	//网点码
	ElementLis PiManBankNo = new ElementLis("PiManBankNo",sBranch,Transaction_Body);
	ElementLis PbRemark1 = new ElementLis("PbRemark1","",Transaction_Body);
	ElementLis PbRemark2 = new ElementLis("PbRemark2","",Transaction_Body);
	ElementLis PbRemark3 = new ElementLis("PbRemark3","",Transaction_Body);
	ElementLis PbRemark4 = new ElementLis("PbRemark4","",Transaction_Body);
	ElementLis PbRemark5 = new ElementLis("PbRemark5","",Transaction_Body);
	ElementLis PbRemark6 = new ElementLis("PbRemark6","",Transaction_Body);
						//投资账户
						int iSubAccountCount = 0;
						if(!(sAccCode1==null||sAccCode1.equals("")) || !(sAccCode2==null||sAccCode2.equals("")) || !(sAccCode3==null||sAccCode3.equals("")) || !(sAccCode4==null||sAccCode4.equals("")) || !(sAccCode5==null||sAccCode5.equals(""))){
								ElementLis Invests1 = new ElementLis("Invests",Transaction_Body);
							if(!(sAccCode1==null||sAccCode1.equals(""))){
								ElementLis Invest1 = new ElementLis("Invest",Invests1);
								ElementLis Code1 = new ElementLis("Code",sAccCode1,Invest1);
								ElementLis Name1 = new ElementLis("Name",sAccCodeName1,Invest1);
								ElementLis Rate1 = new ElementLis("Rate",sAllocPercent1,Invest1);
								ElementLis SupAddRate1 = new ElementLis("SupAddRate","",Invest1);
								iSubAccountCount++;
							}
							if(!(sAccCode2==null||sAccCode2.equals(""))){
								ElementLis Invest2 = new ElementLis("Invest",Invests1);
								ElementLis Code2 = new ElementLis("Code",sAccCode2,Invest2);
								ElementLis Name2 = new ElementLis("Name",sAccCodeName2,Invest2);
								ElementLis Rate2 = new ElementLis("Rate",sAllocPercent2,Invest2);
								ElementLis SupAddRate2 = new ElementLis("SupAddRate","",Invest2);
								iSubAccountCount++;
							}
							if(!(sAccCode3==null||sAccCode3.equals(""))){
								ElementLis Invest3 = new ElementLis("Invest",Invests1);
								ElementLis Code3 = new ElementLis("Code",sAccCode3,Invest3);
								ElementLis Name3 = new ElementLis("Name",sAccCodeName3,Invest3);
								ElementLis Rate3 = new ElementLis("Rate",sAllocPercent3,Invest3);
								ElementLis SupAddRate1 = new ElementLis("SupAddRate","",Invest3);
								iSubAccountCount++;
							}
							if(!(sAccCode4==null||sAccCode4.equals(""))){
								ElementLis Invest4 = new ElementLis("Invest",Invests1);
								ElementLis Code4 = new ElementLis("Code",sAccCode4,Invest4);
								ElementLis Name4 = new ElementLis("Name",sAccCodeName4,Invest4);
								ElementLis Rate4 = new ElementLis("Rate",sAllocPercent4,Invest4);
								ElementLis SupAddRate4 = new ElementLis("SupAddRate","",Invest4);
									iSubAccountCount++;
								}
							if(!(sAccCode5==null||sAccCode5.equals(""))){
								ElementLis Invest5 = new ElementLis("Invest",Invests1);
								ElementLis Code5 = new ElementLis("Code",sAccCode5,Invest5);
								ElementLis Name5 = new ElementLis("Name",sAccCodeName5,Invest5);
								ElementLis Rate5 = new ElementLis("Rate",sAllocPercent5,Invest5);
								ElementLis SupAddRate5 = new ElementLis("SupAddRate","",Invest5);
									iSubAccountCount++;
								}
							int CountVlaue=0;
							CountVlaue=Integer.valueOf(sAllocPercent1).intValue()+Integer.valueOf(sAllocPercent2).intValue()+Integer.valueOf(sAllocPercent3).intValue()+Integer.valueOf(sAllocPercent5).intValue()+Integer.valueOf(sAllocPercent5).intValue();
							String CValue=""+CountVlaue;
							ElementLis SumRate = new ElementLis("SumRate",CValue,Invests1);
							String sSubAccountCount = String.valueOf(iSubAccountCount);
						ElementLis AccountCount = new ElementLis("AccountCount",sSubAccountCount,Transaction_Body);
						}
						 
 }else if(sFlag.equals("OPR011")){
        //交易渠道
	    String sBkAcctNo=request.getParameter("BkAcctNo");
	    String sBkPayMode=request.getParameter("BkPayMode");
		ElementLis BkAcctNo = new ElementLis("BkAcctNo",sBkAcctNo,Transaction_Body);
		ElementLis BkPayMode = new ElementLis("BkPayMode",sBkPayMode,Transaction_Body);
		ElementLis BkOthOldSeq = new ElementLis("BkOthOldSeq",sReqsrNo,Transaction_Body);
 }
						Document pXmlDoc = new Document(Transaction);	
						System.out.println("报文信息：：：");
						JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		CCBTestUI mTestUI = new CCBTestUI(sIp,iPort);  
		InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
		byte[] mOutBytes = mTestUI.sendRequest(inputStream);
	 
		Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
			JdomUtil.print(mOutXmlDoc);
		ResultCode = mOutXmlDoc.getRootElement().getChild("Transaction_Header").getChild("Tran_Response").getChildTextTrim("BkOthRetCode");
		ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("Transaction_Header").getChild("Tran_Response").getChildTextTrim("BkOthRetMsg");
		PubFun1 pubfun = new PubFun1();
			System.out.println(ResultCode + "  " + ResultInfoDesc);
        System.out.println("返回内容："+ResultInfoDesc);
		if (ResultCode.equals("00000")) {
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