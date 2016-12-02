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
<% 
	System.out.println("--> ins");
	String ResultCode = null;
	String Content = null;
	String ResultInfoDesc = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String uiPrint = null;   
%>

<%
	try {
 
 
	
	
		


//****************附加险信息1*******************附加险信息1*******************
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
	//投保日期
	String sSubmissionDate = request.getParameter("SubmissionDate");
	//投保书号
	String sHOAppFormNumber = request.getParameter("HOAppFormNumber");
	//保单印刷号
	String sProviderFormNumber = request.getParameter("ProviderFormNumber");
	//资格证
	String sBankManagerAgentId = request.getParameter("BankManagerAgentId");
	//销售人员姓名
	String sBankManagerName = request.getParameter("BankManagerName");
	//交易标志
	String sTransNo = "1013";
	//渠道类型 0 柜台，1 网银，8 自助终端
	String sSourceType = request.getParameter("saleChannel");
	
//****************投保人信息*******************投保人信息*******************
 System.out.println("****************投保人信息*******************投保人信息*******************");
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
	//投保人与被报人的关系
	String sAppToInsRelation	 = request.getParameter("AppToInsRelation");
	 System.out.println("投保人与被报人的关系dfsfsddfwqwe"+sAppToInsRelation);
	//投保人职业代码
	String sAppJobCode= request.getParameter("AppJobCode");
	//投保人公司
	String sAppCompany= request.getParameter("AppCompany");
	//投保人身高
	String sAppStature= request.getParameter("AppStature");
	//投保人体重
	String sAppWeight= request.getParameter("AppWeight");
	//投保人国籍
	String sAppNationalityNo= request.getParameter("AppNationalityNo");
	//投保人身份证有效期
	String sAppGovtTermDate= request.getParameter("AppGovtTermDate");
	//投保人年收入
	String sAppEstSalary= request.getParameter("AppEstSalary");
	//投保人家庭年收入
	String sFamilyEstSalary= request.getParameter("FamilyEstSalary");
	//投保人年收入
	String sPbDenType= request.getParameter("PbDenType");
	
//****************被保人信息*******************被保人信息*******************
 System.out.println("****************被保人信息*******************被保人信息*******************");
	//被保人姓名
	String sInsFullName = request.getParameter("InsFullName");
	//被保人性别
	String sInsGender = request.getParameter("InsGender");
	String sInsGenderh = request.getParameter("InsGenderh");
	//被保人证件类型
	System.out.println("-----"+sInsGender);
	if(sInsGender == null) sInsGender = sInsGenderh;
  
	  
System.out.println("-----"+sInsGenderh); 
	
	String sInsGovtIDTCh = request.getParameter("sInsGovtIDTCh");
	String sInsGovtIDTC = request.getParameter("InsGovtIDTC");
	if(sInsGovtIDTC == null) sInsGovtIDTC = sInsGovtIDTCh;
	
	if(sInsGovtIDTC == null) sInsGovtIDTC = sAppGovtIDTC;
	System.out.println("-----"+sInsGender); 
	//被保人证件号码
	String sInsGovtID = request.getParameter("InsGovtID"); 
	//被保人生日
	String sInsBirthDate = request.getParameter("InsBirthDate");
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
	//投保人职业代码
	String sInsJobCode= request.getParameter("InsJobCode");
	//投保人公司
	String sInsCompany= request.getParameter("InsCompany");
	//投保人身高
	String sInsStature= request.getParameter("InsStature");
	//投保人体重
	String sInsWeight= request.getParameter("InsWeight");
	//投保人国籍
	String sInsNationalityNo= request.getParameter("InsNationalityNo");
	//投保人身份证有效期
	String sInsGovtTermDate= request.getParameter("InsGovtTermDate");
	//投保人年收入
	String sInsEstSalary= request.getParameter("InsEstSalary");
	//健康告知标志 
	String sHealthIndicator	 = request.getParameter("HealthIndicator");
	if(sHealthIndicator == null) sHealthIndicator = "N";
	
//****************受益人信息1*******************受益人信息1*******************
 System.out.println("****************受益人信息1*******************受益人信息1*******************");
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
	//受益百分数
	String sInterestPercent1 = request.getParameter("InterestPercent1"); 
	//受益顺 序
	String sSequence1 = request.getParameter("Sequence1");
	//受益人与被报人的关系
	String sBnfToInsRelation1 = request.getParameter("BnfToInsRelation1");
	//收益人是否为法定
	String sBeneficiaryIndicator = request.getParameter("BeneficiaryIndicator");
System.out.println("收益人是否为法"+sBeneficiaryIndicator);
//****************受益人信息2*******************受益人信息2*******************
 System.out.println("****************受益人信息2*******************受益人信息2*******************");
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
	//受益百分数
	String sInterestPercent2 = request.getParameter("InterestPercent2"); 
	//受益顺 序
	String sSequence2 = request.getParameter("Sequence2");
	//受益人与被报人的关系
	String sBnfToInsRelation2 = request.getParameter("BnfToInsRelation2");

//****************受益人信息3*******************受益人信息3*******************
 System.out.println("****************受益人信息3*******************受益人信息3*******************");
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
	//受益百分数
	String sInterestPercent3 = request.getParameter("InterestPercent3"); 
	//受益顺 序
	String sSequence3 = request.getParameter("Sequence3");
	//受益人与被报人的关系
	String sBnfToInsRelation3 = request.getParameter("BnfToInsRelation3");

//****************主险信息*******************主险信息*******************
 System.out.println("****************主险信息*******************主险信息*******************");
	//主险代码 
	String sProductCode = request.getParameter("ProductCode");
	//份数 
	String sIntialNumberOfUnits = request.getParameter("IntialNumberOfUnits");
	//保费 
	String sInitCovAmt = request.getParameter("InitCovAmt"); 
	//保额
	String sModalPremAmt = request.getParameter("ModalPremAmt"); 
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
	String sInitCovAmt1 = request.getParameter("InitCovAmt1");
	//保额
	String sModalPremAmt1 = request.getParameter("ModalPremAmt1"); 
	//保险年期类型
	String sDurationMode1 = request.getParameter("DurationMode1"); 
	//保险年期 
	String sDuration1 = request.getParameter("Duration1"); 
	//缴费年期类型
	String sPaymentDurationMode1 = request.getParameter("PaymentDurationMode1");
	//缴费年期 
	String sPaymentDuration1 = request.getParameter("PaymentDuration1"); 
	
//****************附加险信息2*******************附加险信息2*******************
 System.out.println("****************附加险信息2*******************附加险信息2*******************");
	//主险代码 
	String sProductCode2 = request.getParameter("ProductCode2");
	//份数 
	String sIntialNumberOfUnits2 = request.getParameter("IntialNumberOfUnits2");
	//保费 
	String sInitCovAmt2 = request.getParameter("InitCovAmt2");
	//保额
	String sModalPremAmt2 = request.getParameter("ModalPremAmt2"); 
	//保险年期类型
	String sDurationMode2 = request.getParameter("DurationMode2"); 
	//保险年期 
	String sDuration2 = request.getParameter("Duration2"); 
	//缴费年期类型
	String sPaymentDurationMode2 = request.getParameter("PaymentDurationMode2");
	//缴费年期 
	String sPaymentDuration2 = request.getParameter("PaymentDuration2"); 
//****************投保信息*******************投保信息*******************	
 System.out.println("****************投保信息*******************投保信息*******************");
	//缴费方式
	String sPaymentMode = request.getParameter("PaymentMode");
	//缴费形式(频次)
	String sPaymentMethod = request.getParameter("PaymentMethod");
	//领取方式
	String sBenefitMode = request.getParameter("BenefitMode");
	//红利领取方式
	String sDivType = request.getParameter("DivType"); 
	System.out.println("**(((((((((((((((((((((())))))))))))))))))))))))*****"+sDivType);
	//保单传送方式
	String sPolicyDeliveryMethod = request.getParameter("PolicyDeliveryMethod"); 
	//银行账号
	String sAccountNumber = request.getParameter("AccountNumber"); 
	//帐户姓名
	String sAcctHolderName = request.getParameter("AcctHolderName");
System.out.println("*******"+sAcctHolderName);
	//特别约定
	String sSpecialClause = request.getParameter("SpecialClause");
	String sOccupationIndicator = request.getParameter("OccupationIndicator");
	
	//年金险的几个特殊的字段
	String sPayOutDurationMode = request.getParameter("PayOutDurationMode");
	String sPayoutStart = request.getParameter("PayoutStart");
	String sPayoutDuration = request.getParameter("PayoutDuration");
	
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
	//贷款到期日期
	String sLoanEndDate = request.getParameter("LoanEndDate");
	//保险合同生效日期
	String sContractEffDate = request.getParameter("ContractEffDate");
	//保险合同到期日期
	String sContractEndDate = request.getParameter("ContractEndDate");
	
	//贷款种类
	String sLoanProductCode = request.getParameter("LoanProductCode");
	
//****************ip-port-flag*******************ip-port-flag*******************	
System.out.println("****************ip-port-flag*******************ip-port-flag*******************===="+sAppEstSalary);	
String sIp = request.getParameter("ip");
String sPort = request.getParameter("port");
String sFlag = request.getParameter("tranFlagCode");

 


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
		
		xmlContent += "二、投保人信息\\n";
		xmlContent += "  投保人姓名: " + sAppFullName;
		xmlContent += "\t\t\t投保人性别: " + sAppGender + "\\n";
		xmlContent += "  投保人证件类型: " + sAppGovtIDTC ;
		xmlContent += "\t\t投保人证件号码: " + sAppGovtID + "\\n";
		xmlContent += "  投保人生日: " + sAppBirthDate ;
		xmlContent += "\t\t投保人电子邮箱 : " + sAppAddrLine + "\\n";		
		xmlContent += "  投保人家庭电话: " + sAppDialNumber1;
		xmlContent += "\t投保人移动电话: " + sAppDialNumber3+ "\\n";
		xmlContent += "  投保人是被保人的: " + sAppToInsRelation+ "\\n";		
  xmlContent += "  投保人职业代码: " + sAppJobCode;
  xmlContent += "  投保人公司: " + sAppCompany+ "\\n";
  xmlContent += "  投保人身高: " + sAppStature;
  xmlContent += "  投保人体重: " + sAppWeight+ "\\n";
  xmlContent += "  投保人国籍: " + sAppNationalityNo+ "\\n";
  xmlContent += "  投保人身份证有效期: " + sAppGovtTermDate;
  xmlContent += "  投保人年收入: " + sAppEstSalary+ "\\n";
  xmlContent += "  投保人家庭年收入: " + sFamilyEstSalary+ "\\n";
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
 xmlContent += "  被保人职业代码: " + sInsJobCode;
  xmlContent += "  被保人公司: " + sInsCompany+ "\\n";
  xmlContent += "  被保人身高: " + sInsStature;
  xmlContent += "  被保人体重: " + sInsWeight+ "\\n";
  xmlContent += "  被保人国籍: " + sInsNationalityNo+ "\\n";
  xmlContent += "  被保人身份证有效期: " + sInsGovtTermDate;
  xmlContent += "  被保人年收入: " + sInsEstSalary+ "\\n";
 
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
		xmlContent += "\t\t保费: " +  sModalPremAmt;  
		xmlContent += "\t\t保额: " + sInitCovAmt + "\\n";  
		xmlContent += "  保险年期类型: " + sDurationMode ;
		xmlContent += "\t\t保险年期 : " + sDuration ;		
		xmlContent += "\t\t缴费年期类型: " + sPaymentDurationMode ;		
		xmlContent += "\t\t缴费年期: " + sPaymentDuration + "\\n";	
		 
		if(!(sProductCode1==null||sProductCode1.equals(""))){
		xmlContent += "2、附加险信息1\\n";
		xmlContent += "  附加险代码: " + sProductCode1;
		xmlContent += "\t\t\t份数: " + sIntialNumberOfUnits1 ;
		xmlContent += "\t\t保费: " + sInitCovAmt1 ; 
		xmlContent += "\t\t保额: " + sModalPremAmt1 + "\\n";  
		xmlContent += "  保险年期类型: " + sDurationMode1 ;
		xmlContent += "\t\t保险年期 : " + sDuration1 ;		
		xmlContent += "\t\t缴费年期类型: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t缴费年期: " + sPaymentDuration1 + "\\n";
		}
		   
		if(!(sProductCode2==null||sProductCode2.equals(""))){
		xmlContent += "3、附加险信息2\\n";
		xmlContent += "  附加险代码: " + sProductCode2;
		xmlContent += "\t\t\t份数: " + sIntialNumberOfUnits2 ;
		xmlContent += "\t\t保费: " + sInitCovAmt2 ; 
		xmlContent += "\t\t保额: " + sModalPremAmt1 + "\\n";  
		xmlContent += "  保险年期类型: " + sDurationMode2 ;
		xmlContent += "\t\t保险年期 : " + sDuration2 ;		
		xmlContent += "\t\t缴费年期类型: " + sPaymentDurationMode1 ;		
		xmlContent += "\t\t缴费年期: " + sPaymentDuration2 + "\\n";
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


		
 System.out.println("************************装载XML************************");		 
 //ElementLis aaaa = new ElementLis("","","",);  
 //ElementLis  aaaa= new ElementLis("",);
 //ElementLis aaaa = new ElementLis("",""); 
 ElementLis TXLife = new ElementLis("TXLife");	 
	ElementLis TXLifeRequest = new ElementLis("TXLifeRequest",TXLife);  
		ElementLis TransRefGUID = new ElementLis("TransRefGUID",sTransRefGUID,TXLifeRequest);    
		ElementLis TransExeDate = new ElementLis("TransExeDate",sTransExeDate,TXLifeRequest); 
		ElementLis TransExeTime = new ElementLis("TransExeTime",sTransExeTime,TXLifeRequest);	
		ElementLis OLifE = new ElementLis("OLifE",TXLifeRequest); 
	ElementLis HoldingH1 = new ElementLis("Holding","id","Holding_1",OLifE);
		ElementLis Policy = new ElementLis("Policy",HoldingH1);       
			ElementLis ProductCode = new ElementLis("ProductCode",sProductCode,Policy); 
			ElementLis PaymentMode = new ElementLis("PaymentMode",sPaymentMode,"tc",sPaymentMode,Policy);
			ElementLis PaymentMethod = new ElementLis("PaymentMethod",sPaymentMethod,"tc",sPaymentMethod,Policy);
			ElementLis AcctHolderName = new ElementLis("AcctHolderName",sAcctHolderName,Policy);
			ElementLis AccountNumber = new ElementLis("AccountNumber",sAccountNumber,Policy);
			//资格证号
			ElementLis BankManagerAgentId = new ElementLis("BankManagerAgentId",sBankManagerAgentId,Policy);
			//销售人员姓名
			ElementLis BankManagerName = new ElementLis("BankManagerName",sBankManagerName,Policy);
			ElementLis Life = new ElementLis("Life",Policy); 
				ElementLis DivType = new ElementLis("DivType",sDivType,"tc",sDivType,Life);
				
				ElementLis Coverage = new ElementLis("Coverage","id","Cov_1",Life);
					ElementLis ProductCodeMain = new ElementLis("ProductCode",sProductCode,Coverage);
					ElementLis IndicatorCode = new ElementLis("IndicatorCode","1","tc","1",Coverage);
					ElementLis InitCovAmt = new ElementLis("InitCovAmt",sInitCovAmt,Coverage);
					ElementLis IntialNumberOfUnits = new ElementLis("IntialNumberOfUnits",sIntialNumberOfUnits,Coverage);
					ElementLis ModalPremAmt = new ElementLis("ModalPremAmt",sModalPremAmt,Coverage);
					ElementLis BenefitMode = new ElementLis("BenefitMode",sBenefitMode,"tc",sBenefitMode,Coverage);
					ElementLis OLifEExtension10 = new ElementLis("OLifEExtension","VendorCode","10",Coverage);
						ElementLis PaymentDurationMode = new ElementLis("PaymentDurationMode",sPaymentDurationMode,"tc",sPaymentDurationMode,OLifEExtension10);
						ElementLis PaymentDuration = new ElementLis("PaymentDuration",sPaymentDuration,OLifEExtension10);
						ElementLis DurationMode = new ElementLis("DurationMode",sDurationMode,"tc",sDurationMode,OLifEExtension10);
						ElementLis Duration = new ElementLis("Duration",sDuration,OLifEExtension10);
						
						//封装年金险的几个特殊的节点 20130408
						ElementLis PayOutDurationMode = new ElementLis("PayOutDurationMode",sPayOutDurationMode,OLifEExtension10);
						ElementLis PayoutStart = new ElementLis("PayoutStart",sPayoutStart,OLifEExtension10);
						ElementLis PayoutDuration = new ElementLis("PayoutDuration",sPayoutDuration,OLifEExtension10);
						 
//如果有附加险1    
				if(!(sProductCode1==null||sProductCode1.equals(""))){	
				ElementLis Coverage1 = new ElementLis("Coverage","id","Cov_2",Life);
					ElementLis ProductCodeMain1 = new ElementLis("ProductCode",sProductCode1,Coverage1);
					ElementLis IndicatorCode1 = new ElementLis("IndicatorCode","2","tc","2",Coverage1);
					ElementLis InitCovAmt1 = new ElementLis("InitCovAmt",sInitCovAmt1,Coverage1);
					ElementLis IntialNumberOfUnits1 = new ElementLis("IntialNumberOfUnits",sIntialNumberOfUnits1,Coverage1);
					ElementLis ModalPremAmt1 = new ElementLis("ModalPremAmt",sModalPremAmt1,Coverage1);
					ElementLis BenefitMode1 = new ElementLis("BenefitMode",sBenefitMode,"tc",sBenefitMode,Coverage1);
					ElementLis OLifEExtension11 = new ElementLis("OLifEExtension","VendorCode","11",Coverage1);
						ElementLis PaymentDurationMode1 = new ElementLis("PaymentDurationMode",sPaymentDurationMode1,"tc",sPaymentDurationMode1,OLifEExtension11);
						ElementLis PaymentDuration1 = new ElementLis("PaymentDuration",sPaymentDuration1,OLifEExtension11);
						ElementLis DurationMode1 = new ElementLis("DurationMode",sDurationMode1,"tc",sDurationMode1,OLifEExtension11);
						ElementLis Duration1 = new ElementLis("Duration",sDuration1,OLifEExtension11);						
				}
//如果有附加险2  
				if(!(sProductCode2==null||sProductCode2.equals(""))){	
				ElementLis Coverage2 = new ElementLis("Coverage","id","Cov_3",Life);
					ElementLis ProductCodeMain2 = new ElementLis("ProductCode",sProductCode2,Coverage2);
					ElementLis IndicatorCode2 = new ElementLis("IndicatorCode","2","tc","2",Coverage2);
					ElementLis InitCovAmt2 = new ElementLis("InitCovAmt",sInitCovAmt2,Coverage2);
					ElementLis IntialNumberOfUnits2 = new ElementLis("IntialNumberOfUnits",sIntialNumberOfUnits2,Coverage2);
					ElementLis ModalPremAmt2 = new ElementLis("ModalPremAmt",sModalPremAmt2,Coverage2);
					ElementLis BenefitMode2 = new ElementLis("BenefitMode",sBenefitMode,"tc",sBenefitMode,Coverage2);
					ElementLis OLifEExtension12 = new ElementLis("OLifEExtension","VendorCode","12",Coverage2);
						ElementLis PaymentDurationMode2 = new ElementLis("PaymentDurationMode",sPaymentDurationMode2,"tc",sPaymentDurationMode2,OLifEExtension12);
						ElementLis PaymentDuration2 = new ElementLis("PaymentDuration",sPaymentDuration2,OLifEExtension12);
						ElementLis DurationMode2 = new ElementLis("DurationMode",sDurationMode2,"tc",sDurationMode2,OLifEExtension12);
						ElementLis Duration2 = new ElementLis("Duration",sDuration2,OLifEExtension12);						
				}
											    
			ElementLis ApplicationInfo = new ElementLis("ApplicationInfo",Policy);
				ElementLis HOAppFormNumber = new ElementLis("HOAppFormNumber",sHOAppFormNumber,ApplicationInfo);
				ElementLis SubmissionDate = new ElementLis("SubmissionDate",sSubmissionDate,ApplicationInfo);
			ElementLis OLifEExtension2 = new ElementLis("OLifEExtension","VendorCode","2",Policy);
				ElementLis PolicyDeliveryMethod = new ElementLis("PolicyDeliveryMethod",sPolicyDeliveryMethod,OLifEExtension2);
				ElementLis SpecialClause = new ElementLis("SpecialClause",sSpecialClause,OLifEExtension2);
				ElementLis HealthIndicator = new ElementLis("HealthIndicator",sHealthIndicator,OLifEExtension2);
				ElementLis OccupationIndicator = new ElementLis("OccupationIndicator",sOccupationIndicator,OLifEExtension2);
				ElementLis BeneficiaryIndicator = new ElementLis("BeneficiaryIndicator",sBeneficiaryIndicator,OLifEExtension2);
				
				//贷款信息
				ElementLis ContractNo = new ElementLis("ContractNo",sContractNo,OLifEExtension2);
							ElementLis LoanAccountNo = new ElementLis("LoanAccountNo",sLoanAccountNo,OLifEExtension2);
							ElementLis LoanAmount = new ElementLis("LoanAmount",sLoanAmount,OLifEExtension2);
							ElementLis FaceAmount = new ElementLis("FaceAmount",sFaceAmount,OLifEExtension2);
							ElementLis LoanStartDate = new ElementLis("LoanStartDate",sLoanStartDate,OLifEExtension2);
							ElementLis LoanEndDate = new ElementLis("LoanEndDate",sLoanEndDate,OLifEExtension2);
							ElementLis ContractEffDate = new ElementLis("ContractEffDate",sContractEffDate,OLifEExtension2);
							ElementLis ContractEndDate = new ElementLis("ContractEndDate",sContractEndDate,OLifEExtension2);
							
							ElementLis LoanProductCode = new ElementLis("LoanProductCode",sLoanProductCode,OLifEExtension2);
							
				
	ElementLis HoldingA1 = new ElementLis("Holding","id","Acct_1",OLifE); 
		ElementLis Banking = new ElementLis("Banking",HoldingA1);	
			 AccountNumber = new ElementLis("AccountNumber",sAccountNumber,Banking);	
		
			 
	ElementLis Party1 = new ElementLis("Party","id","Party_1",OLifE);
		ElementLis AppFullName = new ElementLis("FullName",sAppFullName,Party1);
		ElementLis AppGovtID = new ElementLis("GovtID",sAppGovtID,Party1);
		ElementLis AppGovtIDTC = new ElementLis("GovtIDTC",sAppGovtIDTC,"tc",sAppGovtIDTC,Party1);
		ElementLis AppGovtTermDate = new ElementLis("GovtTermDate",sAppGovtTermDate,Party1);
		ElementLis AppPerson = new ElementLis("Person",Party1);
			ElementLis AppGender = new ElementLis("Gender",sAppGender,"tc",sAppGender,AppPerson);
			ElementLis AppBirthDate = new ElementLis("BirthDate",sAppBirthDate,AppPerson);
			ElementLis AppNationalityNo = new ElementLis("Nationality",sAppNationalityNo,AppPerson);
			ElementLis AppOccupationType = new ElementLis("OccupationType",sAppJobCode,AppPerson);
			ElementLis AppCompany = new ElementLis("Company",sAppCompany,AppPerson);
			ElementLis AppStature = new ElementLis("Stature",sAppStature,AppPerson);
			ElementLis AppWeight = new ElementLis("Weight",sAppWeight,AppPerson);
			ElementLis AppEstSalary = new ElementLis("EstSalary",sAppEstSalary,AppPerson);
			//投保人家庭年收入
			ElementLis FamilyEstSalary = new ElementLis("FamilyEstSalary",sFamilyEstSalary,AppPerson);
			//投保人居民类型
			ElementLis LiveZone = new ElementLis("LiveZone",sPbDenType,AppPerson);
			
		    ElementLis AppAddress = new ElementLis("Address","id","Address_2",Party1);
			ElementLis AppAddressTypeCode = new ElementLis("AddressTypeCode","1","id","1",AppAddress);
			ElementLis AppLine1 = new ElementLis("Line1",sAppLine1,AppAddress);
			ElementLis AppZip = new ElementLis("Zip",sAppZip,AppAddress);
		    ElementLis AppPhone1 = new ElementLis("Phone","id","Phone_1",Party1);
			ElementLis AppPhoneTypeCode1 = new ElementLis("PhoneTypeCode","1","tc","1",AppPhone1);
			ElementLis AppDialNumber1 = new ElementLis("DialNumber",sAppDialNumber1,AppPhone1);
		    ElementLis AppPhone3 = new ElementLis("Phone","id","Phone_3",Party1);
			ElementLis AppPhoneTypeCode3 = new ElementLis("PhoneTypeCode","3","tc","3",AppPhone3);
			ElementLis AppDialNumber3 = new ElementLis("DialNumber",sAppDialNumber3,AppPhone3);
		    ElementLis AppEMailAddress = new ElementLis("EMailAddress","id","EMailAddress_1",Party1);	
			ElementLis AppAddrLine = new ElementLis("AddrLine",sAppAddrLine,AppEMailAddress);
	        ElementLis Relation_1 = new ElementLis("Holding_1","Party_1","Relation_1","4","6","80",OLifE);	
	
	ElementLis Party2 = new ElementLis("Party","id","Party_2",OLifE);
		ElementLis InsFullName = new ElementLis("FullName",sInsFullName,Party2);
		ElementLis InsGovtID = new ElementLis("GovtID",sInsGovtID,Party2);
		ElementLis InsGovtIDTC = new ElementLis("GovtIDTC",sInsGovtIDTC,"tc",sInsGovtIDTC,Party2);
		ElementLis InsGovtTermDate = new ElementLis("GovtTermDate",sInsGovtTermDate,Party2);
		ElementLis InsPerson = new ElementLis("Person",Party2);
			ElementLis InsGender = new ElementLis("Gender",sInsGender,"tc",sInsGender,InsPerson);
			ElementLis InsBirthDate = new ElementLis("BirthDate",sInsBirthDate,InsPerson); 
			ElementLis InsNationalityNo = new ElementLis("Nationality",sInsNationalityNo,InsPerson);
			ElementLis InsOccupationType = new ElementLis("OccupationType",sInsJobCode,InsPerson);
			ElementLis InsCompany = new ElementLis("Company",sInsCompany,InsPerson);
			ElementLis InsStature = new ElementLis("Stature",sInsStature,InsPerson);
			ElementLis InsWeight = new ElementLis("Weight",sInsWeight,InsPerson);
			ElementLis InsEstSalary = new ElementLis("EstSalary",sInsEstSalary,InsPerson);
			
		ElementLis InsAddress = new ElementLis("Address","id","Address_2",Party2);
			ElementLis InsAddressTypeCode = new ElementLis("AddressTypeCode","1","id","1",InsAddress);
			ElementLis InsLine1 = new ElementLis("Line1",sInsLine1,InsAddress);
			ElementLis InsZip = new ElementLis("Zip",sInsZip,InsAddress);
		ElementLis InsPhone1 = new ElementLis("Phone","id","Phone_1",Party2);
			ElementLis InsPhoneTypeCode1 = new ElementLis("PhoneTypeCode","1","tc","1",InsPhone1);
			ElementLis InsDialNumber1 = new ElementLis("DialNumber",sInsDialNumber1,InsPhone1);
		ElementLis InsPhone3 = new ElementLis("Phone","id","Phone_3",Party2);
			ElementLis InsPhoneTypeCode3 = new ElementLis("PhoneTypeCode","3","tc","3",InsPhone3);
			ElementLis InsDialNumber3 = new ElementLis("DialNumber",sInsDialNumber3,InsPhone3);
		ElementLis InsEMailAddress = new ElementLis("EMailAddress","id","EMailAddress_1",Party2);	
			ElementLis InsAddrLine = new ElementLis("AddrLine",sInsAddrLine,InsEMailAddress); 
		ElementLis OLifEExtension200 = new ElementLis("OLifEExtension","VendorCode","200",Party2);
			ElementLis InsHealthIndicator = new ElementLis("HealthIndicator",sHealthIndicator,OLifEExtension200);
	ElementLis Relation_2 = new ElementLis("Holding_1","Party_2","Relation_2","4","6","81",OLifE);
            System.out.println("背包:"+sAppToInsRelation);
	ElementLis Relation_4 = new ElementLis("Party_2","Party_1","Relation_4","6","6",sAppToInsRelation,OLifE);	
	
	if(!sBeneficiaryIndicator.equals("Y")){
					
	ElementLis Party3 = new ElementLis("Party","id","Party_3",OLifE);
		ElementLis BnfFullName1 = new ElementLis("FullName",sBnfFullName1,Party3);
		ElementLis BnfGovtID1 = new ElementLis("GovtID",sBnfGovtID1,Party3);
		ElementLis BnfGovtIDTC1 = new ElementLis("GovtIDTC",sBnfGovtIDTC1,"tc",sBnfGovtIDTC1,Party3);
		ElementLis BnfPerson1 = new ElementLis("Person",Party3);
			ElementLis BnfGender1 = new ElementLis("Gender",sBnfGender1,"tc",sBnfGender1,BnfPerson1);
			ElementLis BnfBirthDate1 = new ElementLis("BirthDate",sBnfBirthDate1,BnfPerson1); 
	ElementLis Relation_3 = new ElementLis("Holding_1","Party_3","Relation_3","4","6","82",OLifE);
		ElementLis InterestPercent1 = new ElementLis("InterestPercent",sInterestPercent1,Relation_3);
		ElementLis Sequence1 = new ElementLis("Sequence",sSequence1,Relation_3);
	ElementLis Relation_5 = new ElementLis("Party_2","Party_3","Relation_4","4","6",sBnfToInsRelation1,OLifE);
	}
//如果有第二受益人 									
	if(!(sBnfFullName2==null||sBnfFullName2.equals(""))){
	ElementLis Party4 = new ElementLis("Party","id","Party_4",OLifE);
		ElementLis BnfFullName2 = new ElementLis("FullName",sBnfFullName2,Party4);
		ElementLis BnfGovtID2 = new ElementLis("GovtID",sBnfGovtID2,Party4);
		ElementLis BnfGovtIDTC2 = new ElementLis("GovtIDTC",sBnfGovtIDTC2,"tc",sBnfGovtIDTC2,Party4);
		ElementLis BnfPerson2 = new ElementLis("Person",Party4);
			ElementLis BnfGender2 = new ElementLis("Gender",sBnfGender2,"tc",sBnfGender2,BnfPerson2);
			ElementLis BnfBirthDate2 = new ElementLis("BirthDate",sBnfBirthDate2,BnfPerson2); 	
	ElementLis Relation_6 = new ElementLis("Holding_1","Party_4","Relation_6","4","6","82",OLifE);
		ElementLis InterestPercent2 = new ElementLis("InterestPercent",sInterestPercent2,Relation_6);
		ElementLis Sequence2 = new ElementLis("Sequence",sSequence2,Relation_6);
	ElementLis Relation_7 = new ElementLis("Party_2","Party_4","Relation_7","4","6",sBnfToInsRelation2,OLifE);
	}
//如果有第三受益人 									 
	if(!(sBnfFullName3==null||sBnfFullName3.equals(""))){
	ElementLis Party5 = new ElementLis("Party","id","Party_5",OLifE);
		ElementLis BnfFullName3 = new ElementLis("FullName",sBnfFullName3,Party5);
		ElementLis BnfGovtID3 = new ElementLis("GovtID",sBnfGovtID3,Party5);
		ElementLis BnfGovtIDTC3 = new ElementLis("GovtIDTC",sBnfGovtIDTC3,"tc",sBnfGovtIDTC3,Party5);
		ElementLis BnfPerson3 = new ElementLis("Person",Party5);
			ElementLis BnfGender3 = new ElementLis("Gender",sBnfGender3,"tc",sBnfGender3,BnfPerson3);
			ElementLis BnfBirthDate3 = new ElementLis("BirthDate",sBnfBirthDate3,BnfPerson3); 	
	ElementLis Relation_8 = new ElementLis("Holding_1","Party_5","Relation_8","4","6","82",OLifE);
		ElementLis InterestPercent3 = new ElementLis("InterestPercent",sInterestPercent3,Relation_8);
		ElementLis Sequence3 = new ElementLis("Sequence",sSequence3,Relation_8);
	ElementLis Relation_9 = new ElementLis("Party_2","Party_5","Relation_9","4","6",sBnfToInsRelation3,OLifE);
	}
	 
	
	
	
	ElementLis FormInstance = new ElementLis("FormInstance","id","Form_2",OLifE);
		ElementLis FormName = new ElementLis("FormName","2",FormInstance);
		ElementLis ProviderFormNumber = new ElementLis("ProviderFormNumber",sProviderFormNumber,FormInstance);
		ElementLis OLifEExtension1 = new ElementLis("OLifEExtension","VendorCode","1",TXLifeRequest); 
	ElementLis RegionCode = new ElementLis("RegionCode",sRegionCode,OLifEExtension1);
	ElementLis Branch = new ElementLis("Branch",sBranch,OLifEExtension1);
	ElementLis Teller = new ElementLis("Teller",sTeller,OLifEExtension1);
	ElementLis TransNo = new ElementLis("TransNo",sTransNo,OLifEExtension1);
	ElementLis SourceType = new ElementLis("SourceType",sSourceType,"tc",sSourceType,OLifEExtension1);
 //	TXLifeRequest.addContent(TransRefGUID);                 
//	TransRefGUID.setText(sTransRefGUID);                        
//	TransExeDate.setText(sTransRefGUID);            
// 	TransExeTime.setText(sTransRefGUID);  

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
   
 			Document pXmlDoc = new Document(TXLife);	
 			JdomUtil.print(pXmlDoc);
//System.out.println("************************1************************");	
	//		FileOutputStream tFos = new FileOutputStream(mInFilePath); 
//System.out.println("************************2************************");	
	//JdomUtil.output(pXmlDoc, tFos,"GBK");
//System.out.println("************************3************************");		
	    
		
		int iPort = Integer.valueOf(sPort).intValue();
		ICBCTestUI mTestUI = new ICBCTestUI(sIp,iPort);  
//		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(sFlag, mIs);
		byte[] mOutBytes = mTestUI.sendRequestUI(sFlag, pXmlDoc);
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

	       if (sFlag.trim().equals("1013")) {
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