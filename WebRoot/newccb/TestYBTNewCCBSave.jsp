<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%> 
<%@page import="java.io.*"%>
<%@page import="java.lang.*"%>
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
<%@page import="com.sinosoft.midplat.newccb.NewCcbConf"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="test.NewCCBTestUI"%> 
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
	//保单号码
	String sContNo = request.getParameter("ContNo");
	//销售人员工号
	String sBO_Sale_Stff_ID=request.getParameter("BkRckrNo");
	//销售人姓名
	String sBO_Sale_Stff_Nm=request.getParameter("BkSaleName");
	//销售人员资格证号
	String sSale_Stff_AICSQCtf_ID=request.getParameter("BkSaleCertNo");
	//客户风险承受能力代码
	String sCst_Rsk_Tlrnc_Cpy_Cd=request.getParameter("Cst_Rsk_Tlrnc_Cpy_Cd");
	//风险测评有效期
	String sRsk_Evlt_AvlDt=request.getParameter("Rsk_Evlt_AvlDt");
	//预算金额
	String sBdgt_Amt=request.getParameter("Bdgt_Amt");
	
	if(!sHOAppFormNumber.equals("")){
		//sHOAppFormNumber=CheckProlNo.retCheck(sHOAppFormNumber);
		}
	//保单印刷号
	String sProviderFormNumber="";
	//if(sFlag.equals("P53819113")){
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
	//投保人证件生效日期
	String sAppGovtEfcDate = request.getParameter("AppGovtEfcDate");
	sAppGovtEfcDate=DateUtil.date10to8(sAppGovtEfcDate);
	//投保人证件失效日期
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
	//经办人姓名
	String sRspbPsn_Nm = request.getParameter("RspbName");
	//投保人与被报人的关系
	String sAppToInsRelation	 = request.getParameter("AppToInsRelation");
	String sAppCountry = request.getParameter("AppCountry");
	String sApplJobCode = request.getParameter("ApplJobCode");
	
	String mPlchd_Prov_Cd = request.getParameter("Plchd_Prov_Cd");
	String mPlchd_City_Cd = request.getParameter("Plchd_City_Cd");
	String mPlchd_CntyAndDstc_Cd = request.getParameter("Plchd_CntyAndDstc_Cd");
	String mPlchd_Dtl_Adr_Cntnt = request.getParameter("Plchd_Dtl_Adr_Cntnt");
	
	//被保人姓名
	String sInsFullName = request.getParameter("InsFullName");
	//被保人姓名拼音全称
	String sRcgn_CPA_FullNm = request.getParameter("Rcgn_CPA_FullNm");
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
	//被保人证件生效日期
	String sInsGovtEfcDate = request.getParameter("InsGovtEfcDate");
	sInsGovtEfcDate=DateUtil.date10to8(sInsGovtEfcDate);
	//被保人证件失效日期
	String sInsGovtExpDate = request.getParameter("InsGovtExpDate");
	sInsGovtExpDate=DateUtil.date10to8(sInsGovtExpDate);
	//被保人电子邮箱
	String sInsAddrLine = request.getParameter("InsAddrLine"); 
	//被保人通讯地址	 
	String sInsLine1 = request.getParameter("InsLine1");
	//被保人邮政编码
	String sInsZip = request.getParameter("InsZip"); 
	//被保人固定电话
	String sInsDialNumber1 = request.getParameter("InsDialNumber1");
	//被保人移动电话
	String sInsDialNumber3 = request.getParameter("InsDialNumber3");
	String sInsCountry = request.getParameter("InsCountry");
	//健康告知标志 
	String sHealthIndicator	 = request.getParameter("HealthIndicator");
	//未成年人累计身故风险保额
	String sInsCovSumAmt = request.getParameter("InsCovSumAmt");
	//被保人职业代码
	String sInsuJobCode = request.getParameter("InsuJobCode");
	//被保人年收入
	String sInsInCome = request.getParameter("InsInCome");
	//被保人前往目的地个数
	String sDesNum = request.getParameter("DesNum");
	//被保人前往目的地
	String sDestinations = request.getParameter("Destinations");
	
	String mRcgn_Prov_Cd = request.getParameter("Rcgn_Prov_Cd");
	String mRcgn_City_Cd = request.getParameter("Rcgn_City_Cd");
	String mRcgn_CntyAndDstc_Cd = request.getParameter("Rcgn_CntyAndDstc_Cd");
	String mRcgn_Dtl_Adr_Cntnt = request.getParameter("Rcgn_Dtl_Adr_Cntnt");
	
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
	//受益人证件生效日期
	String sApplbegindate1= request.getParameter("applbegindate1");
	sApplbegindate1=DateUtil.date10to8(sApplbegindate1);
	//受益人证件失效日期
	String sApplvaliddate1 = request.getParameter("applvaliddate1");
	sApplvaliddate1=DateUtil.date10to8(sApplvaliddate1);
	//受益人地址
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
	//受益人地址
	String sBnfAdress2 = request.getParameter("BnfAdress2");
	//受益人证件生效日期
	String sApplbegindate2 = request.getParameter("applbegindate2");
	sApplbegindate2=DateUtil.date10to8(sApplbegindate2);
	//受益人证件失效日期
	String sApplvaliddate2 = request.getParameter("applvaliddate2");
	sApplvaliddate2=DateUtil.date10to8(sApplvaliddate2);
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
	//受益人地址
	String sBnfAdress3 = request.getParameter("BnfAdress3");
	//受益人证件生效日期
	String sApplbegindate3= request.getParameter("applbegindate3");
	sApplbegindate3=DateUtil.date10to8(sApplbegindate3);
	//受益人证件失效日期
	String sApplvaliddate3 = request.getParameter("applvaliddate3");
	sApplvaliddate3=DateUtil.date10to8(sApplvaliddate3);
	//受益百分数
	String sInterestPercent3 = request.getParameter("InterestPercent3"); 
	//受益顺 序
	String sSequence3 = request.getParameter("Sequence3");
	//受益人与被报人的关系
	String sBnfToInsRelation3 = request.getParameter("BnfToInsRelation3");
	 //受益人类型
    String sBenefiType3 = request.getParameter("BenefiType3");

	//代理保险套餐编号 
	String sAgIns_Pkg_ID = request.getParameter("AgIns_Pkg_ID");
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
	//投保方案信息
	String sIns_Scm_Inf = request.getParameter("Ins_Scm_Inf");
	//可选部分身故保险金额
	String sOpt_Part_DieIns_Amt = request.getParameter("Opt_Part_DieIns_Amt");
	//首次额外追加保费
	String sFTm_Extr_Adl_InsPrem = request.getParameter("FTm_Extr_Adl_InsPrem");
	//紧急联系人与被保人关系
	String sEmgrCtcPsnAndRcReTpCd = request.getParameter("EmgrCtcPsnAndRcReTpCd");
	//紧急联系人
	String sEmgr_CtcPsn = request.getParameter("Emgr_CtcPsn");
	//首次联系电话
	String sEmgr_Ctc_Tel = request.getParameter("Emgr_Ctc_Tel");
	
	
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
		if(sFTm_Extr_Adl_InsPrem == null || "".equals(sFTm_Extr_Adl_InsPrem)){
			sFTm_Extr_Adl_InsPrem="0";
		}
		if(sProductCode1.equals("102") || sProductCode2.equals("101")){
		iPaymentAmt = Integer.valueOf(sModalPremAmt).intValue()+Integer.valueOf(sFTm_Extr_Adl_InsPrem).intValue()+Integer.valueOf(sModalPremAmt1).intValue() +Integer.valueOf(sModalPremAmt2).intValue();
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
		xmlContent +="\t 销售人员代码+"+sBO_Sale_Stff_ID+"\\n";
		xmlContent +="\t 销售人员姓名+"+sBO_Sale_Stff_Nm+"\\n";
		xmlContent +="\t 销售人员资格证号+"+sSale_Stff_AICSQCtf_ID+"\\n";
		
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
		xmlContent += "  经办人姓名: " + sRspbPsn_Nm+ "\\n";	
        xmlContent += "  投保人居民类型: " + sPbDenType+ "\\n";	
        xmlContent += "  投保人职业代码: " + sApplJobCode+ "\\n";	
		
		xmlContent += "三、被保人信息\\n";
		xmlContent += "  被保人姓名: " + sInsFullName;
		xmlContent += "\t\t\t被保人性别: " + sInsGender + "\\n";
		xmlContent += "  被保人证件类型: " + sInsGovtIDTC ;
		xmlContent += "\t\t被保人证件号码: " + sInsGovtID + "\\n";
		xmlContent += "  被保人生日: " + sInsBirthDate ;
		xmlContent += "\t\t被保人电子邮箱 : " + sInsAddrLine + "\\n";		
		xmlContent += "  被保人家庭电话: " + sInsDialNumber1;
		xmlContent += "\t被保人移动电话: " + sInsDialNumber3+ "\\n";
		xmlContent += "\t被保人职业代码: " + sInsuJobCode+ "\\n";
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
		xmlContent += "\t首次额外追加保费: " + sFTm_Extr_Adl_InsPrem + "\\n";
	
String InsuId = NewCcbConf.newInstance().getConf().getRootElement().getChild("bank").getAttributeValue("insu");
		
 System.out.println("************************装载XML************************");		
 //根节点
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
 
 //ElementLis LiBankID = new ElementLis("LiBankID","CCB",TX_HEADER);  
//保险公司代码
 //ElementLis PbInsuId = new ElementLis("PbInsuId",InsuId,TX_HEADER);  
//银行流水号
 //ElementLis BkPlatSeqNo = new ElementLis("BkPlatSeqNo",sTransRefGUID,TX_HEADER);  
// ElementLis BkTxCode = new ElementLis("BkTxCode",sFlag,TX_HEADER);  
 //交易渠道代号
 //ElementLis BkChnlNo = new ElementLis("BkChnlNo","1",TX_HEADER);  
 //原始请求方机构代码
 //ElementLis BkBrchNo = new ElementLis("BkBrchNo",sRegionCode+sBranch,TX_HEADER);  
//交易柜员代码
// ElementLis BkTellerNo = new ElementLis("BkTellerNo",sTeller,TX_HEADER);  
//银行交易日期
 //ElementLis BkPlatDate = new ElementLis("BkPlatDate",sTransExeDate,TX_HEADER);  
//银行交易时间
// ElementLis BkPlatTime = new ElementLis("BkPlatTime",sTransExeTime,TX_HEADER);  
 if(sFlag.equals("P53819113")||sFlag.equals("P53819188")){
	 if(sFlag.equals("P53819113")){
		 SYS_TX_CODE.setText("P53819113");
	 }
	 if(sFlag.equals("P53819188")){
		 SYS_TX_CODE.setText("P53819188");
	 }
//投保人姓名 
ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",sAppFullName,APP_ENTITY);  
//投保人性别 
ElementLis Plchd_Gnd_Cd = new ElementLis("Plchd_Gnd_Cd",sAppGender,APP_ENTITY);	
//投保人出生日期
ElementLis Plchd_Brth_Dt = new ElementLis("Plchd_Brth_Dt",sAppBirthDate,APP_ENTITY);
//投保人证件类型
ElementLis  Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",sAppGovtIDTC,APP_ENTITY);
//投保人证件号码 
ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",sAppGovtID,APP_ENTITY);
//投保人证件生效日期
ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",sAppGovtEfcDate,APP_ENTITY);
//投保人证件失效日期 
ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",sAppGovtTermDate,APP_ENTITY);
//投保人通讯地址
ElementLis Plchd_Comm_Adr = new ElementLis("Plchd_Comm_Adr",sAppLine1,APP_ENTITY);

//投保人单位地址
// ElementLis PbHoldAddr = new ElementLis("PbHoldAddr",sAppWorkAddress,APP_ENTITY);
//投保人单位邮编
// ElementLis PbHoldPost = new ElementLis("PbHoldPost",sAppWorkZipCode,APP_ENTITY);
//投保人邮编
ElementLis Plchd_ZipECD = new ElementLis("Plchd_ZipECD",sAppZip,APP_ENTITY);
//投保人住宅电话 (投保人固定电话)
ElementLis Plchd_Fix_TelNo = new ElementLis("Plchd_Fix_TelNo",sAppDialNumber1,APP_ENTITY);
//投保人单位电话
// ElementLis PbHoldOfficTele = new ElementLis("PbHoldOfficTele",sWorkPhone,APP_ENTITY);
//投保人移动电话
ElementLis Plchd_Move_TelNo = new ElementLis("Plchd_Move_TelNo",sAppDialNumber3,APP_ENTITY);
//投保人邮箱
ElementLis Plchd_Email_Adr = new ElementLis("Plchd_Email_Adr",sAppAddrLine,APP_ENTITY);
//投保人职业类别
ElementLis Plchd_Ocp_Cd = new ElementLis("Plchd_Ocp_Cd",sApplJobCode,APP_ENTITY);
//国籍
ElementLis Plchd_Nat_Cd = new ElementLis("Plchd_Nat_Cd",sAppCountry,APP_ENTITY);
//职业代码
//ElementLis ApplJobCode = new ElementLis("PbHoldOccupCode",sApplJobCode,APP_ENTITY);
		xmlContent +="\t 销售人员代码+"+sBO_Sale_Stff_ID+"\\n";
		xmlContent +="\t 销售人员姓名+"+sBO_Sale_Stff_Nm+"\\n";
		xmlContent +="\t 销售人员资格证号+"+sSale_Stff_AICSQCtf_ID+"\\n";
		
//投保人证件到期日 
//ElementLis PbIdEndDate = new ElementLis("PbIdEndDate",sAppGovtTermDate,APP_ENTITY);
//销售人员姓名
//ElementLis BO_Sale_Stff_Nm = new ElementLis("BO_Sale_Stff_Nm",sBO_Sale_Stff_Nm,APP_ENTITY);
//销售人员代码
//ElementLis BO_Sale_Stff_ID = new ElementLis("BO_Sale_Stff_Nm",sBO_Sale_Stff_ID,APP_ENTITY);
//销售人员资格证件号 
//ElementLis Sale_Stff_AICSQCtf_ID = new ElementLis("Sale_Stff_AICSQCtf_ID",sSale_Stff_AICSQCtf_ID,APP_ENTITY);
//投保人年收入
ElementLis Plchd_Yr_IncmAm = new ElementLis("Plchd_Yr_IncmAm",sPbInCome,APP_ENTITY);
//投保人家庭年收入
ElementLis Fam_Yr_IncmAm = new ElementLis("Fam_Yr_IncmAm",sPbHomeInCome,APP_ENTITY);
//投保人居民类型
ElementLis Rsdnt_TpCd = new ElementLis("Rsdnt_TpCd",sPbDenType,APP_ENTITY);
//投保人婚姻状况
//ElementLis AppMarStat = new ElementLis("AppMarStat","",APP_ENTITY);
//经办人姓名
ElementLis RspbPsn_Nm = new ElementLis("RspbPsn_Nm",sRspbPsn_Nm,APP_ENTITY);
ElementLis  cmPlchd_Prov_Cd= new ElementLis("Plchd_Prov_Cd",mPlchd_Prov_Cd,APP_ENTITY);
ElementLis  cmPlchd_City_Cd= new ElementLis("Plchd_City_Cd",mPlchd_City_Cd,APP_ENTITY);
ElementLis  cmPlchd_CntyAndDstc_Cd= new ElementLis("Plchd_CntyAndDstc_Cd",mPlchd_CntyAndDstc_Cd,APP_ENTITY);
ElementLis  cmPlchd_Dtl_Adr_Cntnt= new ElementLis("Plchd_Dtl_Adr_Cntnt",mPlchd_Dtl_Adr_Cntnt,APP_ENTITY);

//投保人与被保人关系
ElementLis Plchd_And_Rcgn_ReTpCd = new ElementLis("Plchd_And_Rcgn_ReTpCd",sAppToInsRelation,APP_ENTITY);

//被保人信息
ElementLis Rcgn_Nm = new ElementLis("Rcgn_Nm",sInsFullName,APP_ENTITY);
ElementLis Rcgn_CPA_FullNm = new ElementLis("Rcgn_CPA_FullNm",sRcgn_CPA_FullNm,APP_ENTITY);
ElementLis Rcgn_Gnd_Cd = new ElementLis("Rcgn_Gnd_Cd",sInsGender,APP_ENTITY);
ElementLis Rcgn_Brth_Dt = new ElementLis("Rcgn_Brth_Dt",sInsBirthDate,APP_ENTITY); 
ElementLis Rcgn_Crdt_TpCd = new ElementLis("Rcgn_Crdt_TpCd",sInsGovtIDTC,APP_ENTITY);
ElementLis Rcgn_Crdt_No = new ElementLis("Rcgn_Crdt_No",sInsGovtID,APP_ENTITY);
//被保人证件生效日期
ElementLis Rcgn_Crdt_EfDt = new ElementLis("Rcgn_Crdt_EfDt",sInsGovtEfcDate,APP_ENTITY);
//被保人证件失效日期 
ElementLis Rcgn_Crdt_ExpDt = new ElementLis("Rcgn_Crdt_ExpDt",sInsGovtExpDate,APP_ENTITY);
//被保人邮箱
ElementLis Rcgn_Email_Adr = new ElementLis("Rcgn_Email_Adr",sInsAddrLine,APP_ENTITY);
//被保人地址
ElementLis Rcgn_Comm_Adr = new ElementLis("Rcgn_Comm_Adr",sInsLine1,APP_ENTITY);
ElementLis Rcgn_ZipECD = new ElementLis("Rcgn_ZipECD",sInsZip,APP_ENTITY);
//ElementLis InsCompany = new ElementLis("InsCompany","",APP_ENTITY);
//ElementLis InsCall = new ElementLis("InsCall","",APP_ENTITY);
ElementLis Rcgn_Ocp_Cd = new ElementLis("Rcgn_Ocp_Cd",sInsuJobCode,APP_ENTITY);

ElementLis Rcgn_Fix_TelNo = new ElementLis("Rcgn_Fix_TelNo",sInsDialNumber1,APP_ENTITY);
ElementLis Rcgn_Move_TelNo = new ElementLis("Rcgn_Move_TelNo",sInsDialNumber3,APP_ENTITY);
//被保人年收入
ElementLis Rcgn_Yr_IncmAm = new ElementLis("Rcgn_Yr_IncmAm",sInsInCome,APP_ENTITY);
//未成年人累计身故风险保额	
ElementLis Minr_Acm_Cvr = new ElementLis("Minr_Acm_Cvr",sInsCovSumAmt,APP_ENTITY);
// ElementLis LiIdEndDate = new ElementLis("LiIdEndDate",sInsGovtTermDate,APP_ENTITY);
//职业代码
// ElementLis InsuJobCode = new ElementLis("LiRcgnOccupCode",sInsuJobCode,APP_ENTITY);

ElementLis Rcgn_Nat_Cd = new ElementLis("Rcgn_Nat_Cd",sInsCountry,APP_ENTITY);
ElementLis Rcgn_LvFr_Pps_Lnd_Num = new ElementLis("Rcgn_LvFr_Pps_Lnd_Num",sDesNum,APP_ENTITY);
ElementLis Pps_List = new ElementLis("Pps_List",APP_ENTITY);
for(int i=0;i<Integer.parseInt(sDesNum);i++){
ElementLis Pps_Detail = new ElementLis("Pps_Detail",Pps_List);
ElementLis Rcgn_LvFr_Pps_Lnd = new ElementLis("Rcgn_LvFr_Pps_Lnd",sDestinations,Pps_Detail);
		ElementLis  cmRcgn_Prov_Cd= new ElementLis("Rcgn_Prov_Cd",mPlchd_Prov_Cd,APP_ENTITY);
 		ElementLis  cmRcgn_City_Cd= new ElementLis("Rcgn_City_Cd",mPlchd_City_Cd,APP_ENTITY);
 		ElementLis  cmRcgn_CntyAndDstc_Cd= new ElementLis("Rcgn_CntyAndDstc_Cd",mPlchd_CntyAndDstc_Cd,APP_ENTITY);
 		ElementLis  cmRcgn_Dtl_Adr_Cntnt= new ElementLis("Rcgn_Dtl_Adr_Cntnt",mPlchd_Dtl_Adr_Cntnt,APP_ENTITY);
 		
}

//被保人婚姻状况
//ElementLis InsMarStat = new ElementLis("InsMarStat","",APP_ENTITY);

//受益人信息个数
ElementLis Benf_Num = new ElementLis("Benf_Num","",APP_ENTITY);
ElementLis Benf_List = new ElementLis("Benf_List",APP_ENTITY);
int BenefitNum=0;
//第一受益人
if(!(sBnfFullName1.equals(""))){
ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",sBenefiType1,Benf_Detail);
ElementLis Benf_SN = new ElementLis("Benf_SN","",Benf_Detail);
ElementLis Benf_Bnft_Seq = new ElementLis("Benf_Bnft_Seq",sSequence1,Benf_Detail);
ElementLis Benf_Nm = new ElementLis("Benf_Nm",sBnfFullName1,Benf_Detail);
ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",sBnfGender1,Benf_Detail);
ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",sBnfBirthDate1,Benf_Detail); 
ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",sBnfGovtIDTC1,Benf_Detail);
ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",sBnfGovtID1,Benf_Detail);
ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",sApplbegindate1,Benf_Detail);
ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",sApplvaliddate1,Benf_Detail);
ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd","0156",Benf_Detail);
ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",sBnfToInsRelation1,Benf_Detail);
ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",sInterestPercent1,Benf_Detail);
ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",sBnfAdress1,Benf_Detail);
BenefitNum++;
}
//如果有第二受益人 									
if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
	ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
	ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",sBenefiType2,Benf_Detail);
	ElementLis Benf_SN = new ElementLis("Benf_SN","",Benf_Detail);
	ElementLis Benf_Bnft_Seq = new ElementLis("Benf_Bnft_Seq",sSequence2,Benf_Detail);
	ElementLis Benf_Nm = new ElementLis("Benf_Nm",sBnfFullName2,Benf_Detail);
	ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",sBnfGender2,Benf_Detail);
	ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",sBnfBirthDate2,Benf_Detail); 
	ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",sBnfGovtIDTC2,Benf_Detail);
	ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",sBnfGovtID2,Benf_Detail);
	ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",sApplbegindate2,Benf_Detail);
	ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",sApplvaliddate2,Benf_Detail);
	ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd","0156",Benf_Detail);
	ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",sBnfToInsRelation2,Benf_Detail);
	ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",sInterestPercent2,Benf_Detail);
	ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",sBnfAdress2,Benf_Detail);
	BenefitNum++;
}
//如果有第三受益人 									 
if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
	ElementLis Benf_Detail = new ElementLis("Benf_Detail",Benf_List);
	ElementLis AgIns_Benf_TpCd = new ElementLis("AgIns_Benf_TpCd",sBenefiType3,Benf_Detail);
	ElementLis Benf_SN = new ElementLis("Benf_SN","",Benf_Detail);
	ElementLis Benf_Bnft_Seq = new ElementLis("Benf_Bnft_Seq",sSequence3,Benf_Detail);
	ElementLis Benf_Nm = new ElementLis("Benf_Nm",sBnfFullName3,Benf_Detail);
	ElementLis Benf_Gnd_Cd = new ElementLis("Benf_Gnd_Cd",sBnfGender3,Benf_Detail);
	ElementLis Benf_Brth_Dt = new ElementLis("Benf_Brth_Dt",sBnfBirthDate3,Benf_Detail); 
	ElementLis Benf_Crdt_TpCd = new ElementLis("Benf_Crdt_TpCd",sBnfGovtIDTC3,Benf_Detail);
	ElementLis Benf_Crdt_No = new ElementLis("Benf_Crdt_No",sBnfGovtID3,Benf_Detail);
	ElementLis Benf_Crdt_EfDt = new ElementLis("Benf_Crdt_EfDt",sApplbegindate3,Benf_Detail);
	ElementLis Benf_Crdt_ExpDt = new ElementLis("Benf_Crdt_ExpDt",sApplvaliddate3,Benf_Detail);
	ElementLis Benf_Nat_Cd = new ElementLis("Benf_Nat_Cd","0156",Benf_Detail);
	ElementLis Benf_And_Rcgn_ReTpCd = new ElementLis("Benf_And_Rcgn_ReTpCd",sBnfToInsRelation3,Benf_Detail);
	ElementLis Bnft_Pct = new ElementLis("Bnft_Pct",sInterestPercent3,Benf_Detail);
	ElementLis Benf_Comm_Adr = new ElementLis("Benf_Comm_Adr",sBnfAdress3,Benf_Detail);
	BenefitNum++;
}
    String BenitNum=""+BenefitNum;
    Benf_Num.setText(BenitNum);//修改受益人个数。
    
	ElementLis Busi_List = new ElementLis("Busi_List",APP_ENTITY); 
	ElementLis Cvr_Num = new ElementLis("Cvr_Num","1",APP_ENTITY); 
	ElementLis Busi_Detail = new ElementLis("Busi_Detail",Busi_List); 
	//主险信息
	//代理保险套餐编号
	ElementLis AgIns_Pkg_ID = new ElementLis("AgIns_Pkg_ID",sAgIns_Pkg_ID,Busi_Detail); 
	//险种代码
	ElementLis Cvr_ID = new ElementLis("Cvr_ID",sProductCode,Busi_Detail); 
	String sRiskName=null;
	if(sProductCode.equals("0001")){
		sRiskName="中韩智赢财富两全保险（分红型）A款";
	}
	if(sProductCode.equals("0002")){
		sRiskName="中韩智赢财富两全保险（分红型）B款";
	}
	if(sProductCode.equals("0003")){
		sRiskName="中韩卓越财富两全保险（分红型）";
	}
	if(sProductCode.equals("0004")){
		sRiskName="中韩保驾护航两全保险A款";
	}
	if(sProductCode.equals("0005")){
		sRiskName="中韩智赢财富两全保险（分红型）C款";
	}
	if(sProductCode.equals("0006")){
		sRiskName="中韩悦未来年金保险";
	}
	//险种名称
	ElementLis Cvr_Nm = new ElementLis("Cvr_Nm",sRiskName,Busi_Detail); 
	// 投保份数
	ElementLis Ins_Cps = new ElementLis("Ins_Cps",sIntialNumberOfUnits,Busi_Detail);
	//基本保费
	ElementLis InsPrem_Amt = new ElementLis("InsPrem_Amt",sModalPremAmt,Busi_Detail);
	//基本保额
	ElementLis Ins_Cvr = new ElementLis("Ins_Cvr", sInitCovAmt,Busi_Detail);
	//投保方案信息
	ElementLis Ins_Scm_Inf = new ElementLis("Ins_Scm_Inf", sIns_Scm_Inf,Busi_Detail);
	//可选部分身故保险金额
	ElementLis Opt_Part_DieIns_Amt = new ElementLis("Opt_Part_DieIns_Amt",sOpt_Part_DieIns_Amt,Busi_Detail); 
	//首次额外追加保费
	ElementLis FTm_Extr_Adl_InsPrem = new ElementLis("FTm_Extr_Adl_InsPrem",sFTm_Extr_Adl_InsPrem,Busi_Detail); 
	//紧急联系人
	ElementLis Emgr_CtcPsn = new ElementLis("Emgr_CtcPsn",sEmgr_CtcPsn,Busi_Detail); 
	//紧急联系人与被保人关系
	ElementLis EmgrCtcPsnAndRcReTpCd = new ElementLis("EmgrCtcPsnAndRcReTpCd",sEmgrCtcPsnAndRcReTpCd,Busi_Detail); 
	//紧急联系电话
    ElementLis Emgr_Ctc_Tel = new ElementLis("Emgr_Ctc_Tel",sEmgr_Ctc_Tel,Busi_Detail); 
	//银行贷款合同编号
	ElementLis Bnk_Loan_Ctr_ID = new ElementLis("Bnk_Loan_Ctr_ID",Busi_Detail); 
	//银行合同失效日期
	ElementLis Ln_Ctr_ExpDt = new ElementLis("Ln_Ctr_ExpDt",Busi_Detail); 
	//未还贷款金额
	ElementLis Upd_Loan_Amt = new ElementLis("Upd_Loan_Amt",Busi_Detail); 
	//主单保单凭证号码
	ElementLis PrimBlInsPolcyVchr_No = new ElementLis("PrimBlInsPolcyVchr_No",Busi_Detail); 
	//保费缴费方式	
	ElementLis PbPayPeriod = new ElementLis("InsPrem_PyF_MtdCd",sPaymentMode,Busi_Detail);
	//保费缴费期数	
	ElementLis InsPrem_PyF_Prd_Num = new ElementLis("InsPrem_PyF_Prd_Num",sPaymentDuration,Busi_Detail);
	//保费缴费周期代码	
	ElementLis InsPrem_PyF_Cyc_Cd = new ElementLis("InsPrem_PyF_Cyc_Cd","1",Busi_Detail);
	//满期保险金领取方式 和 年金/生存金领取方式等
	ElementLis ExpPrmmRcvModCgyCd = new ElementLis("ExpPrmmRcvModCgyCd","",Busi_Detail); 
	ElementLis SvBnf_Drw_Cyc_Cd = new ElementLis("SvBnf_Drw_Cyc_Cd",sBenefitMode,Busi_Detail); 
	//约定保费垫交方式
	ElementLis ApntInsPremPyAdvnInd = new ElementLis("ApntInsPremPyAdvnInd",sAutoPayFlag,Busi_Detail); 
	//红利领取方式
	ElementLis XtraDvdn_Pcsg_MtdCd = new ElementLis("XtraDvdn_Pcsg_MtdCd",sDivType,Busi_Detail);
	//减额交清标志
	ElementLis RdAmtPyCls_Ind = new ElementLis("RdAmtPyCls_Ind","",Busi_Detail); 

	//年金领取类别代码
	ElementLis Anuty_Drw_CgyCd = new ElementLis("Anuty_Drw_CgyCd",Busi_Detail);
	//年金领取期数/领取年期
	ElementLis Anuty_Drw_Prd_Num = new ElementLis("Anuty_Drw_Prd_Num",sPbDrawAgeTag,Busi_Detail); 
	//年金领取周期代码
	ElementLis Anuty_Drw_Cyc_Cd = new ElementLis("Anuty_Drw_Cyc_Cd",Busi_Detail);
	//保险年期类型
	ElementLis Ins_Yr_Prd_CgyCd = new ElementLis("Ins_Yr_Prd_CgyCd",sDurationMode,Busi_Detail);
	//保险期限
	ElementLis Ins_Ddln = new ElementLis("Ins_Ddln",sDuration,Busi_Detail);
	//保险周期代码
	ElementLis Ins_Cyc_Cd = new ElementLis("Ins_Cyc_Cd",Busi_Detail);
	//投资方式代码
	ElementLis Ivs_MtdCd = new ElementLis("Ivs_MtdCd","",Busi_Detail); 
	//自动续保标志
	ElementLis Auto_RnwCv_Ind = new ElementLis("Auto_RnwCv_Ind","0",Busi_Detail); 
	//争议仲裁方式
	ElementLis Dspt_Pcsg_MtdCd = new ElementLis("Dspt_Pcsg_MtdCd","",Busi_Detail);
	//仲裁机构名称
	ElementLis Dspt_Arbtr_Inst_Nm = new ElementLis("Dspt_Arbtr_Inst_Nm","",Busi_Detail);
	//保留字段
	ElementLis Rsrv_Fld_1 = new ElementLis("Rsrv_Fld_1","",Busi_Detail);
	ElementLis Rsrv_Fld_2 = new ElementLis("Rsrv_Fld_2","",Busi_Detail);
	ElementLis Rsrv_Fld_3 = new ElementLis("Rsrv_Fld_3","",Busi_Detail);
	ElementLis Rsrv_Fld_4 = new ElementLis("Rsrv_Fld_4","",Busi_Detail);
	ElementLis Rsrv_Fld_5 = new ElementLis("Rsrv_Fld_5","",Busi_Detail);
	ElementLis Rsrv_Fld_6 = new ElementLis("Rsrv_Fld_6","",Busi_Detail);
	ElementLis Rsrv_Fld_7 = new ElementLis("Rsrv_Fld_7","",Busi_Detail);
	ElementLis Rsrv_Fld_8 = new ElementLis("Rsrv_Fld_8","",Busi_Detail);
	ElementLis Rsrv_Fld_9 = new ElementLis("Rsrv_Fld_9","",Busi_Detail);
	ElementLis Rsrv_Fld_10 = new ElementLis("Rsrv_Fld_10","",Busi_Detail);
	
	//保单递送方式
//	ElementLis PbSendMode = new ElementLis("PbSendMode",sPolicyDeliveryMethod,APP_ENTITY);
	//起保日期
//	ElementLis PbBeginDate = new ElementLis("PbBeginDate","",APP_ENTITY); 
	//付费方式
//	ElementLis PbInsuPayMode = new ElementLis("PbInsuPayMode",sPaymentMethod,APP_ENTITY); 
	   //缴费帐户
//	ElementLis BkAcctNo1 = new ElementLis("BkAcctNo1",sAccountNumber,APP_ENTITY);
	//特别约定
//	ElementLis LiSpec = new ElementLis("LiSpec",sSpecialClause,APP_ENTITY); 
	//红利分配标记
//	ElementLis LiBonusDistbTag = new ElementLis("LiBonusDistbTag","",APP_ENTITY); 
	
//	ElementLis BkAcctNo3 = new ElementLis("BkAcctNo3","",APP_ENTITY); 
//	ElementLis BkAcctNo2 = new ElementLis("BkAcctNo2","",APP_ENTITY); 
	//缴费年期
//	ElementLis PbPayAgeTag = new ElementLis("PbPayAgeTag",sPaymentDuration,APP_ENTITY);
// 	ElementLis PbPayAge = new ElementLis("PbPayAge","",APP_ENTITY); 
// 	ElementLis PbDrawAge = new ElementLis("PbDrawAge",sPbDrawAge,APP_ENTITY); 
// 	ElementLis PbInsuYear = new ElementLis("PbInsuYear","",APP_ENTITY); 
//	ElementLis BkNum1 = new ElementLis("BkNum1","",APP_ENTITY); 
//    int RiskCount=0;
    //如果有附加险1    
/*	if(!(sProductCode1==null||sProductCode1.equals(""))){
		ElementLis Appd_List = new ElementLis("Appd_List",APP_ENTITY); 
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
	}*/
//如果有附加险2  
/*	if(!(sProductCode2==null||sProductCode2.equals(""))){	
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
	}*/
//	String RiskCt=""+RiskCount;
//	BkNum1.setText(RiskCt);
//投资账户信息
	String sIvsAc_Num="0";
	ElementLis IvsAc_Num= new ElementLis("IvsAc_Num",sIvsAc_Num,Busi_Detail);
	ElementLis PayAcctCode_List= new ElementLis("PayAcctCode_List",Busi_Detail);
	if(!sIvsAc_Num.equals("0")){
		ElementLis PayAcctCode_Detail= new ElementLis("PayAcctCode_Detail",PayAcctCode_List);
		ElementLis ILIVA_ID = new ElementLis("ILIVA_ID",PayAcctCode_Detail);
		ElementLis ILIVA_Nm = new ElementLis("ILIVA_Nm",PayAcctCode_Detail);
		ElementLis Ivs_Tp_Alct_Pctg = new ElementLis("Ivs_Tp_Alct_Pctg",PayAcctCode_Detail);
		ElementLis Adl_Ins_Fee_Alct_Pctg = new ElementLis("Adl_Ins_Fee_Alct_Pctg",PayAcctCode_Detail);
	}
		//健康告知标记
	
	ElementLis Ntf_Itm_Ind = new ElementLis("Ntf_Itm_Ind",sHealthIndicator,APP_ENTITY);
	
	//投保书号
	ElementLis Ins_Bl_Prt_No = new ElementLis("Ins_Bl_Prt_No",sHOAppFormNumber,APP_ENTITY);
	ElementLis BkVchNo = new ElementLis("BkVchNo",sProviderFormNumber,APP_ENTITY);
	//保单密码
	ElementLis InsPolcy_Pswd = new ElementLis("InsPolcy_Pswd","10086sb",APP_ENTITY);
	//发票号
	ElementLis Inv_No = new ElementLis("Inv_No","1008610010",APP_ENTITY);
	
//	ElementLis BkRckrNo = new ElementLis("BkRckrNo",sTeller,APP_ENTITY);

	//建行机构编码
	ElementLis CCBIns_ID2 = new ElementLis("CCBIns_ID","330613535",APP_ENTITY);
//	ElementLis CCBIns_ID2 = new ElementLis("CCBIns_ID","0020000019",APP_ENTITY);
	//一级分行号
	ElementLis Lv1_Br_No = new ElementLis("Lv1_Br_No","110000000",APP_ENTITY);
	//保单领取方式代码
	ElementLis InsPolcy_Rcv_MtdCd = new ElementLis("InsPolcy_Rcv_MtdCd","",APP_ENTITY);
	//保险专案类别代码
	ElementLis Ins_Prj_CgyCd = new ElementLis("Ins_Prj_CgyCd","",APP_ENTITY);
	//见费出单类别代码
	ElementLis PydFeeOutBill_CgyCd = new ElementLis("PydFeeOutBill_CgyCd","",APP_ENTITY);
	//保单实际销售地区代码
	ElementLis InsPolcyActSaleRgonID = new ElementLis("InsPolcyActSaleRgonID","",APP_ENTITY);
	//保险客户名单提供地区代码
	ElementLis Ins_CsLs_Prvd_Rgon_ID = new ElementLis("Ins_CsLs_Prvd_Rgon_ID","",APP_ENTITY);
	//附言备注
	ElementLis Pstcrpt_Rmrk = new ElementLis("Pstcrpt_Rmrk","",APP_ENTITY);
	//保单拟生效日期
	ElementLis InsPolcy_Intnd_EfDt = new ElementLis("InsPolcy_Intnd_EfDt","",APP_ENTITY);
	//网点名称
	ElementLis BO_Nm = new ElementLis("BO_Nm","",APP_ENTITY);
	//网点保险兼职代理业务许可证编码
	ElementLis BOInsPrAgnBsnLcns_ECD = new ElementLis("BOInsPrAgnBsnLcns_ECD","",APP_ENTITY);
	//网点保险兼职代理业务许可证有效日期
	ElementLis BOInsPrAgBsnLcnVld_Dt = new ElementLis("BOInsPrAgBsnLcnVld_Dt","",APP_ENTITY);
	//网点销售人员姓名
	ElementLis BO_Sale_Stff_Nm = new ElementLis("BO_Sale_Stff_Nm",sBO_Sale_Stff_Nm,APP_ENTITY);
	//网店销售人员编号
	ElementLis BO_Sale_Stff_ID = new ElementLis("BO_Sale_Stff_ID",sBO_Sale_Stff_ID,APP_ENTITY);
	//销售人员代理保险从业人员资格证书编号
	ElementLis Sale_Stff_AICSQCtf_ID = new ElementLis("Sale_Stff_AICSQCtf_ID",sSale_Stff_AICSQCtf_ID,APP_ENTITY);
	//保险代理从业人员资格证书有效日期
	ElementLis InsAgnCrStQuaCtVld_Dt = new ElementLis("InsAgnCrStQuaCtVld_Dt","",APP_ENTITY);
	//网点分管代理保险业务人编号
	ElementLis BOIChOfAgInsBsnPnp_ID = new ElementLis("BOIChOfAgInsBsnPnp_ID","",APP_ENTITY);
	//网点分管代理保险业务人姓名
	ElementLis BOIChOfAgInsBsnPnp_Nm = new ElementLis("BOIChOfAgInsBsnPnp_Nm","",APP_ENTITY);
	//续期缴费支付方式代码
	ElementLis Rnew_PyF_PyMd_Cd = new ElementLis("Rnew_PyF_PyMd_Cd","",APP_ENTITY);
	//投保人缴费帐号
	ElementLis Plchd_PyF_AccNo = new ElementLis("Plchd_PyF_AccNo",sAccountNumber,APP_ENTITY);
	//投保人领取帐号
	ElementLis Plchd_Drw_AccNo = new ElementLis("Plchd_Drw_AccNo","",APP_ENTITY);
	//被保人账号
	ElementLis Rcgn_AccNo = new ElementLis("Rcgn_AccNo","",APP_ENTITY);
	//受益人账号
	ElementLis Benf_AccNo = new ElementLis("Benf_AccNo","",APP_ENTITY);
	//保留字段
	ElementLis Rsrv_Fld_11 = new ElementLis("Rsrv_Fld_11","",APP_ENTITY);
	ElementLis Rsrv_Fld_12 = new ElementLis("Rsrv_Fld_12","",APP_ENTITY);
	ElementLis Rsrv_Fld_13 = new ElementLis("Rsrv_Fld_13","",APP_ENTITY);
	ElementLis Rsrv_Fld_14 = new ElementLis("Rsrv_Fld_14","",APP_ENTITY);
	ElementLis Rsrv_Fld_15 = new ElementLis("Rsrv_Fld_15","",APP_ENTITY);
	ElementLis Rsrv_Fld_16 = new ElementLis("Rsrv_Fld_16","",APP_ENTITY);
	ElementLis Rsrv_Fld_17 = new ElementLis("Rsrv_Fld_17","",APP_ENTITY);
	ElementLis Rsrv_Fld_18 = new ElementLis("Rsrv_Fld_18","",APP_ENTITY);
	ElementLis Rsrv_Fld_19 = new ElementLis("Rsrv_Fld_19","",APP_ENTITY);
	ElementLis Rsrv_Fld_20 = new ElementLis("Rsrv_Fld_20","",APP_ENTITY);
	
	ElementLis Cst_Rsk_Tlrnc_Cpy_Cd = new ElementLis("Cst_Rsk_Tlrnc_Cpy_Cd",sCst_Rsk_Tlrnc_Cpy_Cd,APP_ENTITY);
	ElementLis Rsk_Evlt_AvlDt = new ElementLis("Rsk_Evlt_AvlDt",sRsk_Evlt_AvlDt,APP_ENTITY);
	ElementLis Bdgt_Amt = new ElementLis("Bdgt_Amt",sBdgt_Amt,APP_ENTITY);
	
	//地区码
//	ElementLis BkAreaNo = new ElementLis("BkAreaNo",sRegionCode,APP_ENTITY);
	//网点码
//	ElementLis PiManBankNo = new ElementLis("PiManBankNo",sBranch,APP_ENTITY);

						//投资账户
						int iSubAccountCount = 0;
						if(!(sAccCode1==null||sAccCode1.equals("")) || !(sAccCode2==null||sAccCode2.equals("")) || !(sAccCode3==null||sAccCode3.equals("")) || !(sAccCode4==null||sAccCode4.equals("")) || !(sAccCode5==null||sAccCode5.equals(""))){
								ElementLis Invests1 = new ElementLis("Invests",APP_ENTITY);
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
						ElementLis AccountCount = new ElementLis("AccountCount",sSubAccountCount,APP_ENTITY);
						}
						 
 }else if(sFlag.equals("P53819151")){
		SYS_TX_CODE.setText("P53819151");
        //交易渠道
	    String sBkAcctNo=request.getParameter("BkAcctNo");
	    String sBkPayMode=request.getParameter("BkPayMode");
//		ElementLis BkAcctNo = new ElementLis("BkAcctNo",sBkAcctNo,APP_ENTITY);
//		ElementLis BkPayMode = new ElementLis("BkPayMode",sBkPayMode,APP_ENTITY);
		//投保单号
		ElementLis Ins_BillNo = new ElementLis("Ins_BillNo",sHOAppFormNumber,APP_ENTITY);
		//保单号码
		ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",sContNo,APP_ENTITY);
		//投保人姓名
		ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",sAppFullName,APP_ENTITY);
		//投保人证件类型
		ElementLis Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",sAppGovtIDTC,APP_ENTITY);
		//投保人证件号
		ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",sAppGovtID,APP_ENTITY);
		//投保人证件生效日期
		ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",sAppGovtEfcDate,APP_ENTITY);
		//投保人证件失效日期
		ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",sAppGovtTermDate,APP_ENTITY);
		//投保人国籍
		ElementLis Plchd_Nat_Cd = new ElementLis("Plchd_Nat_Cd",sAppCountry,APP_ENTITY);
 }else if(sFlag.equals("P53819152")){
		SYS_TX_CODE.setText("P53819152");
        //交易渠道
	    String sBkAcctNo=request.getParameter("BkAcctNo");
	    String sBkPayMode=request.getParameter("BkPayMode");
//		ElementLis BkAcctNo = new ElementLis("BkAcctNo",sBkAcctNo,APP_ENTITY);
//		ElementLis BkPayMode = new ElementLis("BkPayMode",sBkPayMode,APP_ENTITY);
		//投保人姓名
		ElementLis Plchd_Nm = new ElementLis("Plchd_Nm",sAppFullName,APP_ENTITY);
		//投保人证件类型
		ElementLis Plchd_Crdt_TpCd = new ElementLis("Plchd_Crdt_TpCd",sAppGovtIDTC,APP_ENTITY);
		//投保人证件号
		ElementLis Plchd_Crdt_No = new ElementLis("Plchd_Crdt_No",sAppGovtID,APP_ENTITY);
		//投保人证件生效日期
		ElementLis Plchd_Crdt_EfDt = new ElementLis("Plchd_Crdt_EfDt",sAppGovtEfcDate,APP_ENTITY);
		//投保人证件失效日期
		ElementLis Plchd_Crdt_ExpDt = new ElementLis("Plchd_Crdt_ExpDt",sAppGovtTermDate,APP_ENTITY);
		//投保人国籍
		ElementLis Plchd_Nat_Cd = new ElementLis("Plchd_Nat_Cd",sAppCountry,APP_ENTITY);
		//投保单号
		ElementLis Ins_BillNo = new ElementLis("Ins_BillNo",sHOAppFormNumber,APP_ENTITY);
		//保险缴费金额
		ElementLis Ins_PyF_Amt = new ElementLis("Ins_PyF_Amt","",APP_ENTITY);
		//保费支付方式代码
		ElementLis InsPrem_PyMd_Cd = new ElementLis("InsPrem_PyMd_Cd",sBkPayMode,APP_ENTITY);
		//建行帐号
		ElementLis CCB_AccNo = new ElementLis("CCB_AccNo",sBkAcctNo,APP_ENTITY);
		//保险公司流水号
		ElementLis Ins_Co_Jrnl_No = new ElementLis("Ins_Co_Jrnl_No",sReqsrNo,APP_ENTITY);
 }else if(sFlag.equals("P53819182")){
		SYS_TX_CODE.setText("P53819182");
        //交易渠道
	    String sBkAcctNo=request.getParameter("BkAcctNo");
	    String sBkPayMode=request.getParameter("BkPayMode");
//		ElementLis BkAcctNo = new ElementLis("BkAcctNo",sBkAcctNo,APP_ENTITY);
//		ElementLis BkPayMode = new ElementLis("BkPayMode",sBkPayMode,APP_ENTITY);
		//险种代码
		ElementLis Cvr_ID = new ElementLis("Cvr_ID",sProductCode,APP_ENTITY);
		//保单号
		ElementLis InsPolcy_No = new ElementLis("InsPolcy_No",sContNo,APP_ENTITY);

		//循环记录条数
		ElementLis Rvl_Rcrd_Num = new ElementLis("Rvl_Rcrd_Num","1",APP_ENTITY);
		ElementLis Detail_List = new ElementLis("Detail_List",APP_ENTITY);
		ElementLis Detail = new ElementLis("Detail",Detail_List);
		
		//单证类型
		ElementLis AgIns_Vchr_TpCd = new ElementLis("AgIns_Vchr_TpCd","0101141",Detail);
		//单证号
		ElementLis Ins_IBVoch_ID = new ElementLis("Ins_IBVoch_ID",sProviderFormNumber,Detail);
 }
						Document pXmlDoc = new Document(TX);	
						System.out.println("报文信息：：：");
						JdomUtil.print(pXmlDoc);
		int iPort = Integer.valueOf(sPort).intValue();
		NewCCBTestUI mTestUI = new NewCCBTestUI(sIp,iPort);  
		
			byte[] mOutBytes  = mTestUI.sendRequest(sFlag,pXmlDoc);
		Document mOutXmlDoc = JdomUtil.build(mOutBytes,"UTF-8");
	//	JdomUtil.print(mOutXmlDoc);
		
	//	InputStream inputStream = new ByteArrayInputStream(JdomUtil.toBytes(pXmlDoc));
	////	byte[] mOutBytes = mTestUI.sendRequest(inputStream);
	 
		//Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
			JdomUtil.print(mOutXmlDoc);
		ResultCode = mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_STATUS");
		ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_RESP_DESC");
		PubFun1 pubfun = new PubFun1();
			System.out.println(ResultCode + "  " + ResultInfoDesc);
        System.out.println("返回内容："+ResultInfoDesc);
		if (ResultCode.equals("00")) {
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