<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="org.jdom.Document"%>
<%@page import="org.jdom.Element"%>
<%@page import="java.net.ConnectException"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="test.*"%>
<%@page import="test.*"%>
<%@page import="org.jdom.output.*"%>
<%@page import="org.jdom.input.*"%>
<%@page import="org.jdom.Attribute"%>
<jsp:directive.page import="java.util.HashMap" />
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
		System.out
				.println("****************银行端信息1*******************银行端信息1*******************");
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
		String sContPrtNo = request.getParameter("PrtNo");
		//交易标志
		String sFunctionFlag = "01";
		//代收付账户姓名
		String sAccName = request.getParameter("AccName");
		//银行帐号
		String sAccNo = request.getParameter("AccNo");

		//****************投保人信息*******************投保人信息*******************
		System.out
				.println("****************投保人信息*******************投保人信息*******************");
		//投保人姓名
		String sApplName = request.getParameter("ApplName");
		//投保人性别
		String sApplSex = request.getParameter("ApplSex");
		//投保人证件类型
		String sApplIDType = request.getParameter("ApplIDType");
		//投保人证件号码
		String sApplIDNo = request.getParameter("ApplIDNo");
		//投保人生日
		String sApplBirthday = request.getParameter("ApplBirthday");
		//投保人电子邮箱
		String sApplEmail = request.getParameter("ApplEmail");
		//投保人通讯地址	
		String sApplAddress = request.getParameter("ApplAddress");
		//投保人邮政编码
		String sApplZipCode = request.getParameter("ApplZipCode");
		//投保人家庭电话
		String sApplPhone = request.getParameter("ApplPhone");
		//投保人移动电话
		String sApplMobile = request.getParameter("ApplMobile");
		//投保人与被报人的关系
		String sApplRelaToInsured = request
				.getParameter("ApplRelaToInsured");
		//投保人证件有效期
		String sApplValidYear = request.getParameter("ApplValidYear");
		//投保人职业代码
		String sApplJobCode = request.getParameter("ApplJobCode");

		//****************被保人信息*******************被保人信息*******************
		System.out
				.println("****************被保人信息*******************被保人信息*******************");
		//被保人姓名
		String sInsuName = request.getParameter("InsuName");
		//被保人性别
		String sInsuSex = request.getParameter("InsuSex");
		String sInsuSexh = request.getParameter("InsuSexh");
		//被保人证件类型
		System.out.println("-----" + sInsuSex);
		if (sInsuSex == null)
			sInsuSex = sInsuSexh;

		System.out.println("-----" + sInsuSexh);

		String sInsuIDType = request.getParameter("InsuIDType");
		String sInsuIDTypeh = request.getParameter("InsuIDTypeh");

		if (sInsuIDType == null)
			sInsuIDType = sInsuIDTypeh;

		if (sInsuIDType == null)
			sInsuIDType = sApplIDType;
		System.out.println("-----" + sInsuSex);
		//被保人证件号码
		String sInsuIDNo = request.getParameter("InsuIDNo");
		//被保人生日
		String sInsuBirthday = request.getParameter("InsuBirthday");
		//被保人电子邮箱
		String sInsuEmail = request.getParameter("InsuEmail");
		//被保人通讯地址	 
		String sInsuAddress = request.getParameter("InsuAddress");
		//被保人邮政编码
		String sInsuZipCode = request.getParameter("InsuZipCode");
		//被保人家庭电话
		String sInsuPhone = request.getParameter("InsuPhone");
		//被保人移动电话
		String sInsuMobile = request.getParameter("InsuMobile");
		//被保人证件有效期
		String sInsuValidYear = request.getParameter("InsuValidYear");
		//被保人职业代码
		String sInsuJobCode = request.getParameter("InsuJobCode");
		//健康告知标志 
		String sHealthFlag = request.getParameter("HealthFlag");
		if (sHealthFlag == null)
			sHealthFlag = "N";

		//****************受益人信息1*******************受益人信息1*******************
		System.out
				.println("****************受益人信息1*******************受益人信息1*******************");
		//受益人个数
		String sBnfsCount = request.getParameter("BnfsCount");

		//受益人类型
		String sBnfType1 = request.getParameter("BnfType1");
		//受益人姓名 
		String sBnfName1 = request.getParameter("BnfName1");
		//受益人性别
		String sBnfSex1 = request.getParameter("BnfSex1");
		//受益人证件类型
		String sBnfIDType1 = request.getParameter("BnfIDType1");
		//受益人证件号码
		String sBnfIDNo1 = request.getParameter("BnfIDNo1");
		//受益人生日
		String sBnfBirthday1 = request.getParameter("BnfBirthday1");
		//受益百分数
		String sBnfBnfLot1 = request.getParameter("BnfBnfLot1");
		//受益顺序
		String sBnfBnfGrade1 = request.getParameter("BnfBnfGrade1");
		//受益人与被保人的关系
		String sBnfRelationToInsured1 = request
				.getParameter("BnfRelationToInsured1");
		//受益证件有效期
		String sBnfValidYear1 = request.getParameter("BnfValidYear1");
		
		//法定标志
		//String sBeneficiaryIndicator = request.getParameter("BeneficiaryIndicator");
		//System.out.println("收益人是否为法"+sBeneficiaryIndicator);

		//****************受益人信息2*******************受益人信息2*******************
		System.out
				.println("****************受益人信息2*******************受益人信息2*******************");
		//受益人类型
		String sBnfType2 = request.getParameter("BnfType2");
		//受益人姓名 
		String sBnfName2 = request.getParameter("BnfName2");
		//受益人性别
		String sBnfSex2 = request.getParameter("BnfSex2");
		//受益人证件类型
		String sBnfIDType2 = request.getParameter("BnfIDType2");
		//受益人证件号码
		String sBnfIDNo2 = request.getParameter("BnfIDNo2");
		//受益人生日
		String sBnfBirthday2 = request.getParameter("BnfBirthday2");
		//受益百分数
		String sBnfBnfLot2 = request.getParameter("BnfBnfLot2");
		//受益顺序
		String sBnfBnfGrade2 = request.getParameter("BnfBnfGrade2");
		//受益人与被保人的关系
		String sBnfRelationToInsured2 = request
				.getParameter("BnfRelationToInsured2");
				//受益证件有效期
		String sBnfValidYear2 = request.getParameter("BnfValidYear2");

		//****************受益人信息3*******************受益人信息3*******************
		System.out
				.println("****************受益人信息3*******************受益人信息3*******************");
		//受益人类型
		String sBnfType3 = request.getParameter("BnfType3");
		//受益人姓名 
		String sBnfName3 = request.getParameter("BnfName3");
		//受益人性别
		String sBnfSex3 = request.getParameter("BnfSex3");
		//受益人证件类型
		String sBnfIDType3 = request.getParameter("BnfIDType3");
		//受益人证件号码
		String sBnfIDNo3 = request.getParameter("BnfIDNo3");
		//受益人生日
		String sBnfBirthday3 = request.getParameter("BnfBirthday3");
		//受益百分数
		String sBnfBnfLot3 = request.getParameter("BnfBnfLot3");
		//受益顺序
		String sBnfBnfGrade3 = request.getParameter("BnfBnfGrade3");
		//受益人与被保人的关系
		String sBnfRelationToInsured3 = request
				.getParameter("BnfRelationToInsured3");
		//受益证件有效期
		String sBnfValidYear3 = request.getParameter("BnfValidYear3");

		//****************主险信息*******************主险信息*******************
		System.out
				.println("****************主险信息*******************主险信息*******************");
		//险种代码 
		String sCode = request.getParameter("Code");
		//主险代码 
	//	String sMainRiskCode = request.getParameter("Code");
		//份数 
		String sMult = request.getParameter("Mult");
		//保额
		String sAmnt = request.getParameter("Amnt");
		//保费 
		String sPrem = request.getParameter("Prem");
		//保险年期类型
		String sInsuYearFlag = request.getParameter("InsuYearFlag");
		//保险年期 
		String sInsuYear = request.getParameter("InsuYear");
		//缴费年期类型
		String sPayEndYearFlag = request.getParameter("PayEndYearFlag");
		//缴费年期 
		String sPayEndYear = request.getParameter("PayEndYear");
		//领取年期类型
		String sGetYearFlag = request.getParameter("GetYearFlag");
		//领取年期 
		String sGetYear = request.getParameter("GetYear");
		//红利领取方式
		String sBonusGetMode = request.getParameter("BonusGetMode");

		//****************附加险信息1********************附加险信息1*******************
		System.out
				.println("****************附加险信息1*******************附加险信息1*******************");
		//险种代码 
		String sCode1 = request.getParameter("Code1");
		//主险代码 
		String sMainRiskCode1 = request.getParameter("MainRiskCode1");
		//份数 
		String sMult1 = request.getParameter("Mult1");
		//保额
		String sAmnt1 = request.getParameter("Amnt1");
		//保费 
		String sPrem1 = request.getParameter("Prem1");
		//保险年期类型
		String sInsuYearFlag1 = request.getParameter("InsuYearFlag1");
		//保险年期 
		String sInsuYear1 = request.getParameter("InsuYear1");
		//缴费年期类型
		String sPayEndYearFlag1 = request
				.getParameter("PayEndYearFlag1");
		//缴费年期 
		String sPayEndYear1 = request.getParameter("PayEndYear1");
		//领取年期类型
		String sGetYearFlag1 = request.getParameter("GetYearFlag1");
		//领取年期 
		String sGetYear1 = request.getParameter("GetYear1");
		//红利领取方式
		String sBonusGetMode1 = request.getParameter("BonusGetMode1");

		//****************投资账户节点*******************投资账户节点*******************
		System.out
				.println("****************投资账户节点*******************投资账户节点*******************");

		String sAccNo1 = request.getParameter("AccNo1");
		String sRate1 = request.getParameter("Rate1");
		String sAccNo2 = request.getParameter("AccNo2");
		String sRate2 = request.getParameter("Rate2");
		String sAccNo3 = request.getParameter("AccNo3");
		String sRate3 = request.getParameter("Rate3");

		//****************投保信息*******************投保信息*******************	
		System.out
				.println("****************投保信息*******************投保信息*******************");
		//缴费方式
		String sPayIntv = request.getParameter("PayIntv");
		//银行帐户
		//String sBankAccNo = request.getParameter("BankAccNo");
		//缴费方式
		//String sPayIntv = request.getParameter("PayIntv");
		//保单传送方式
		//String sPolicyDeliveryMethod = request.getParameter("PolicyDeliveryMethod"); 
		//帐户姓名
		//String sAcctHolderName = request.getParameter("AcctHolderName");
		//银行账号
		//String sAccountNumber = request.getParameter("AccountNumber"); 
		//职业告知标志
		//String sOccupationIndicator = request.getParameter("OccupationIndicator");
		//投资日期标志
		//String sInvestDateInd = request.getParameter("InvestDateInd");
		//首次额外追加保费
		//String sFirstSuperaddAmt = request.getParameter("FirstSuperaddAmt");

		//int iPaymentAmt = 0;
		//if(sProductCode.equals("001")){
		//	if(sModalPremAmt == null || "".equals(sModalPremAmt)){
		//		sModalPremAmt="0";
		//	}
		//	iPaymentAmt = Integer.valueOf(sModalPremAmt);
		//}else if(sProductCode.equals("002")){
		//	if(sModalPremAmt == null || "".equals(sModalPremAmt)){
		//		sModalPremAmt="0";
		//	}
		//	
		//	if(sModalPremAmt1 == null || "".equals(sModalPremAmt1)){
		//		sModalPremAmt1="0";
		//	}
		//	if(sFirstSuperaddAmt == null || "".equals(sFirstSuperaddAmt)){
		//		sFirstSuperaddAmt="0";
		//	}
		//	if(sProductCode1.equals("101")){
		//	iPaymentAmt = Integer.valueOf(sModalPremAmt)+Integer.valueOf(sFirstSuperaddAmt)+Integer.valueOf(sModalPremAmt1);
		//	}else{
		//		iPaymentAmt = Integer.valueOf(sModalPremAmt);
		//	}
		//}
		//String sPaymentAmt = String.valueOf(iPaymentAmt);
		
		System.out
				.println("****************贷款信息*******************贷款信息*******************");
		//贷款合同号
		String sLoanNo = request.getParameter("LoanNo");
		//贷款机构
		String sLoanBank = request.getParameter("LoanBank");
		//贷款日期
		String sLoanDate = request.getParameter("LoanDate");
		//贷款到期日
		String sLoanEndDate = request.getParameter("LoanEndDate");
		//贷款种类
		String sLoanType = request.getParameter("LoanType");
		//贷款账号
		String sLoanAccNo = request.getParameter("LoanAccNo");
		//贷款金额
		String sLoanPrem = request.getParameter("LoanPrem");
		//保险起始日
		String sInsuDate = request.getParameter("InsuDate");
		//保险期满日
		String sInsuEndDate = request.getParameter("InsuEndDate");
		

		//****************ip-port-flag*******************ip-port-flag*******************	
		System.out
				.println("****************ip-port-flag*******************ip-port-flag*******************");
		String sIp = request.getParameter("ip");
		String sPort = request.getParameter("port");
		String sFlag = request.getParameter("tranFlagCode");

		//把保单信息在页面显示
		xmlContent = new String("");

		xmlContent += "一、交易信息\\n";
		xmlContent += "  银行端日期: " + sBankDate + "\\n";
		xmlContent += "  地区代码: " + sZoneNo;
		xmlContent += "\t\t网点代码: " + sBrNo + "\\n";
		xmlContent += "  柜员代码: " + sTellerNo;
		xmlContent += "\t\t交易流水号: " + sTransrNo + "\\n";

		xmlContent += "二、保单信息\\n";
		xmlContent += "  投保单号: " + sProposalContNo;
		xmlContent += "\t\t投保日期: " + sPolApplyDate + "\\n";
		//xmlContent += "  帐户姓名: " + sAccName;
		xmlContent += "  银行帐户: " + sAccNo + "\\n";
		//xmlContent += "  保单印刷号: " + request.getParameter("PrtNo") ;
		//xmlContent += "  \t总保费: " + request.getParameter("TotalPrem") + "\\n\\n";

		xmlContent += "三、投保人信息\\n";
		xmlContent += "  投保人姓名: " + sApplName;
		xmlContent += "\t\t投保人性别: " + sApplSex + "\\n";
		xmlContent += "  投保人生日: " + sApplBirthday;
		xmlContent += "\t\t投保人证件类型: " + sApplIDType + "\\n";
		xmlContent += "  投保人证件号码: " + sApplIDNo;
		xmlContent += "\t\t投保人电子邮箱 : " + sApplEmail + "\\n";
		xmlContent += "  投保人家庭电话: " + sApplPhone;
		xmlContent += "\t\t投保人移动电话: " + sApplMobile + "\\n";
		xmlContent += "  投保人通信地址: " + sApplAddress;
		xmlContent += "\t\t投保人邮编: " + sApplZipCode + "\\n";
		xmlContent += "  投保人与被报人的关系: " + sApplRelaToInsured + "\\n";

		xmlContent += "四、被保人信息\\n";
		xmlContent += "  被保人姓名: " + sInsuName;
		xmlContent += "\t\t被保人性别: " + sInsuSex + "\\n";
		xmlContent += "  被保人生日: " + sInsuBirthday;
		xmlContent += "\t\t被保人证件类型: " + sInsuIDType + "\\n";
		xmlContent += "  被保人证件号码: " + sInsuIDNo;
		xmlContent += "\t\t被保人通讯地址: " + sInsuAddress + "\\n";
		xmlContent += "  被保人通讯邮编: " + sInsuZipCode;
		xmlContent += "\t\t被保人固定电话: " + sInsuPhone + "\\n";
		xmlContent += "  被保人移动电话: " + sInsuMobile;
		xmlContent += "\t\t被保人电子邮箱: " + sInsuEmail + "\\n";
		xmlContent += "  健康告知: " + sHealthFlag + "\\n";

		xmlContent += "五、受益人信息\\n";

		xmlContent += "1、受益人信息1\\n";
		xmlContent += "  受益人类型: " + sBnfType1 + "\\n";
		xmlContent += "  受益人姓名: " + sBnfName1;
		xmlContent += "\t\t受益人性别: " + sBnfSex1 + "\\n";
		xmlContent += "  受益人证件类型: " + sBnfIDType1;
		xmlContent += "\t\t受益人证件号码: " + sBnfIDNo1 + "\\n";
		xmlContent += "  受益人生日: " + sBnfBirthday1;
		xmlContent += "\t\t收益比例 : " + sBnfBnfLot1 + "\\n";
		xmlContent += "  受益顺序: " + sBnfBnfGrade1;
		xmlContent += "\t\t受益人与被保人关系: " + sBnfRelationToInsured1
				+ "\\n";

		if (!(sBnfName2 == null || sBnfName2.equals(""))) {
			xmlContent += "2、受益人信息2\\n";
			xmlContent += "  受益人类型: " + sBnfType2 + "\\n";
			xmlContent += "  受益人姓名: " + sBnfName2;
			xmlContent += "\t\t受益人性别: " + sBnfSex2 + "\\n";
			xmlContent += "  受益人证件类型: " + sBnfIDType2;
			xmlContent += "\t\t受益人证件号码: " + sBnfIDNo2 + "\\n";
			xmlContent += "  受益人生日: " + sBnfBirthday2;
			xmlContent += "\t\t收益比例 : " + sBnfBnfLot2 + "\\n";
			xmlContent += "  受益顺序: " + sBnfBnfGrade2;
			xmlContent += "\t\t受益人与被保人关系: " + sBnfRelationToInsured2
					+ "\\n";
		}

		if (!(sBnfName3 == null || sBnfName3.equals(""))) {
			xmlContent += "3、受益人信息3\\n";
			xmlContent += "  受益人类型: " + sBnfType3 + "\\n";
			xmlContent += "  受益人姓名: " + sBnfName3;
			xmlContent += "\t\t受益人性别: " + sBnfSex3 + "\\n";
			xmlContent += "  受益人证件类型: " + sBnfIDType3;
			xmlContent += "\t\t受益人证件号码: " + sBnfIDNo3 + "\\n";
			xmlContent += "  受益人生日: " + sBnfBirthday3;
			xmlContent += "\t\t收益比例 : " + sBnfBnfLot3 + "\\n";
			xmlContent += "  受益顺序: " + sBnfBnfGrade3;
			xmlContent += "\t\t受益人与被保人关系: " + sBnfRelationToInsured3
					+ "\\n";
		}

		xmlContent += "六、主险信息\\n";

		xmlContent += "1、主险信息\\n";
		xmlContent += "  险种代码: " + sCode;
		xmlContent += "\t\t主险代码: " + sCode + "\\n";
		xmlContent += "  份数: " + sMult;
		xmlContent += "\t\t保费: " + sPrem + "\\n";
		xmlContent += "  保额: " + sAmnt;
		xmlContent += "\t\t保险年期类型: " + sInsuYearFlag + "\\n";
		xmlContent += "  保险年期 : " + sInsuYear;
		xmlContent += "\t\t缴费年期类型: " + sPayEndYearFlag + "\\n";
		xmlContent += "  缴费年期: " + sPayEndYear;
		xmlContent += "\t\t领取年期类型: " + sGetYearFlag + "\\n";
		xmlContent += "  领取年期: " + sGetYear;
		xmlContent += "\t\t红利领取方式: " + sBonusGetMode + "\\n";
		xmlContent += "  缴费方式: " + sPayIntv + "\\n";

		if (!(sCode1 == null || sCode1.equals(""))) {
			xmlContent += "2、附加险信息1\\n";
			xmlContent += "  附加险险种代码: " + sCode1;
			xmlContent += "\t\t主险代码: " + sCode + "\\n";
			xmlContent += "  份数: " + sMult1;
			xmlContent += "\t\t保费: " + sPrem1 + "\\n";
			xmlContent += "  保额: " + sAmnt1;
			xmlContent += "\t\t保险年期类型: " + sInsuYearFlag1 + "\\n";
			xmlContent += "  保险年期 : " + sInsuYear1;
			xmlContent += "\t\t缴费年期类型: " + sPayEndYearFlag1 + "\\n";
			xmlContent += "  缴费年期: " + sPayEndYear1;
			xmlContent += "\t\t领取年期类型: " + sGetYearFlag1 + "\\n";
			xmlContent += "  领取年期: " + sGetYear1;
			xmlContent += "\t\t红利领取方式: " + sBonusGetMode1 + "\\n";

		}

		//if(!(sAccNo1==null||sAccNo1.equals(""))){
		//xmlContent += "3、投资账户信息\\n";
		//xmlContent += "  投资账户1: " + sAccNo1;
		//xmlContent += "\t\t投资比率: " + sRate1 ;
		//xmlContent += "  投资账户2: " + sAccNo2 ; 
		//xmlContent += "\t\t投资比率: " + sRate2 + "\\n";  
		//xmlContent += "  投资账户3: " + sAccNo3 ;
		//xmlContent += "\t\t投资比率: " + sRate3 + "\\n";
		//} 

		System.out
				.println("************************装载XML************************");

		ElementLis Req = new ElementLis("Req");

		ElementLis TransrNo = new ElementLis("TransrNo", sTransrNo, Req);
		ElementLis ZoneNo = new ElementLis("ZoneNo", sZoneNo, Req);
		ElementLis BrNo = new ElementLis("BrNo", sBrNo, Req);
		ElementLis BankDate = new ElementLis("BankDate",
				DateUtil.date10to8(sBankDate), Req);
		ElementLis BankTime = new ElementLis("BankTime", sBankTime, Req);
		ElementLis BankCode = new ElementLis("BankCode", "05", Req);
		ElementLis TellerNo = new ElementLis("TellerNo", sTellerNo, Req);
		ElementLis InsuID = new ElementLis("InsuID", "01", Req);
		ElementLis FunctionFlag = new ElementLis("FunctionFlag",
				sFunctionFlag, Req);

		ElementLis Base = new ElementLis("Base", Req);
		ElementLis ProposalContNo = new ElementLis("ProposalContNo",
				sProposalContNo, Base);
		ElementLis PrtNo = new ElementLis("PrtNo",
				sContPrtNo, Base);
				
				System.out.println("11111111111111111:"+sContPrtNo);
		ElementLis PolApplyDate = new ElementLis("PolApplyDate",
				DateUtil.date10to8(sPolApplyDate), Base);
		
		ElementLis AccName = new ElementLis("AccName", sAccName, Base);
		ElementLis AccNo = new ElementLis("AccNo", sAccNo, Base);
		ElementLis AccBankNAME = new ElementLis("AccBankNAME", Base);
		ElementLis BankAccNo = new ElementLis("BankAccNo", Base);
		ElementLis SpecContent = new ElementLis("SpecContent", Base);
		ElementLis Appl = new ElementLis("Appl", Req);
		ElementLis ApplName = new ElementLis("Name", sApplName, Appl);
		ElementLis ApplSex = new ElementLis("Sex", sApplSex, Appl);
		ElementLis ApplBirthday = new ElementLis("Birthday",
				DateUtil.date10to8(sApplBirthday), Appl);
		ElementLis ApplIDType = new ElementLis("IDType", sApplIDType,
				Appl);
		ElementLis ApplIDNo = new ElementLis("IDNo", sApplIDNo, Appl);
		ElementLis ApplAddress = new ElementLis("Address",
				sApplAddress, Appl);
		ElementLis ApplZipCode = new ElementLis("ZipCode",
				sApplZipCode, Appl);
		ElementLis ApplPhone = new ElementLis("Phone", sApplPhone, Appl);
		ElementLis ApplMobile = new ElementLis("Mobile", sApplMobile,
				Appl);
		ElementLis ApplEmail = new ElementLis("Email", sApplEmail, Appl);
		ElementLis ApplValidYear = new ElementLis("ValidYear",
				DateUtil.date10to8(sApplValidYear), Appl);
		ElementLis ApplRelaToInsured = new ElementLis("RelaToInsured",
				sApplRelaToInsured, Appl);
		ElementLis ApplJobCode = new ElementLis("JobCode",
				sApplJobCode, Appl);

		ElementLis Insu = new ElementLis("Insu", Req);
		ElementLis InsuName = new ElementLis("Name", sInsuName, Insu);
		ElementLis InsuSex = new ElementLis("Sex", sInsuSex, Insu);
		ElementLis InsuBirthday = new ElementLis("Birthday",
				DateUtil.date10to8(sInsuBirthday), Insu);
		ElementLis InsuIDType = new ElementLis("IDType", sInsuIDType,
				Insu);
		ElementLis InsuIDNo = new ElementLis("IDNo", sInsuIDNo, Insu);

		ElementLis InsuAddress = new ElementLis("Address",
				sInsuAddress, Insu);
		ElementLis InsuZipCode = new ElementLis("ZipCode",
				sInsuZipCode, Insu);
		ElementLis InsuPhone = new ElementLis("Phone", sInsuPhone, Insu);
		ElementLis InsuMobile = new ElementLis("Mobile", sInsuMobile,
				Insu);
		ElementLis InsuEmail = new ElementLis("Email", sInsuEmail, Insu);
		ElementLis InsuValidYear = new ElementLis("ValidYear",
				DateUtil.date10to8(sInsuValidYear), Insu);
		ElementLis InsuJobCode = new ElementLis("JobCode",
				sInsuJobCode, Insu);

		ElementLis Bnfs = new ElementLis("Bnfs", Req);
		ElementLis BnfsCount = new ElementLis("Count", "0", Bnfs);

		if (!(sBnfName1== null || sBnfName1.equals(""))) {
			BnfsCount.setText("1");
			
			ElementLis Bnf1 = new ElementLis("Bnf", Bnfs);
			ElementLis BnfName1 = new ElementLis("Name", sBnfName1,
					Bnf1);
			ElementLis BnfSex1 = new ElementLis("Sex", sBnfSex1, Bnf1);
			ElementLis BnfBirthday1 = new ElementLis("Birthday",
					DateUtil.date10to8(sBnfBirthday1), Bnf1);
			ElementLis BnfIDType1 = new ElementLis("IDType",
					sBnfIDType1, Bnf1);
			ElementLis BnfIDNo1 = new ElementLis("IDNo", sBnfIDNo1,
					Bnf1);
			ElementLis BnfType1 = new ElementLis("Type", sBnfType1,
					Bnf1);
			ElementLis BnfBnfLot1 = new ElementLis("BnfLot",
					sBnfBnfLot1, Bnf1);
			ElementLis BnfBnfGrade1 = new ElementLis("BnfGrade",
					sBnfBnfGrade1, Bnf1);
			ElementLis BnfRelationToInsured1 = new ElementLis(
					"RelationToInsured", sBnfRelationToInsured1, Bnf1);
			ElementLis BnfValidYear1 = new ElementLis(
					"ValidYear", DateUtil.date10to8(sBnfValidYear1), Bnf1);
			
			//如果有第二受益人
			if (!(sBnfName2 == null || sBnfName2.equals(""))) {
				BnfsCount.setText("2");
			
				ElementLis Bnf2 = new ElementLis("Bnf", Bnfs);
				ElementLis BnfName2 = new ElementLis("Name", sBnfName2,
						Bnf2);
				ElementLis BnfSex2 = new ElementLis("Sex", sBnfSex2,
						Bnf2);
				ElementLis BnfBirthday2 = new ElementLis("Birthday",
						DateUtil.date10to8(sBnfBirthday2), Bnf2);
				ElementLis BnfIDType2 = new ElementLis("IDType",
						sBnfIDType2, Bnf2);
				ElementLis BnfIDNo2 = new ElementLis("IDNo", sBnfIDNo2,
						Bnf2);
				ElementLis BnfType2 = new ElementLis("Type", sBnfType2,
						Bnf2);
				ElementLis BnfBnfLot2 = new ElementLis("BnfLot",
						sBnfBnfLot2, Bnf2);
				ElementLis BnfBnfGrade2 = new ElementLis("BnfGrade",
						sBnfBnfGrade2, Bnf2);
				ElementLis BnfRelationToInsured2 = new ElementLis(
						"RelationToInsured", sBnfRelationToInsured2,
						Bnf2);
				ElementLis BnfValidYear2 = new ElementLis(
					"ValidYear", DateUtil.date10to8(sBnfValidYear2), Bnf2);
			}
			//如果有第三受益人 	
			if (!(sBnfName3 == null || sBnfName3.equals(""))) {
				BnfsCount.setText("3");
				
				ElementLis Bnf3 = new ElementLis("Bnf", Bnfs);
				ElementLis BnfName3 = new ElementLis("Name", sBnfName3,
						Bnf3);
				ElementLis BnfSex3 = new ElementLis("Sex", sBnfSex3,
						Bnf3);
				ElementLis BnfBirthday3 = new ElementLis("Birthday",
						DateUtil.date10to8(sBnfBirthday3), Bnf3);
				ElementLis BnfIDType3 = new ElementLis("IDType",
						sBnfIDType3, Bnf3);
				ElementLis BnfIDNo3 = new ElementLis("IDNo", sBnfIDNo3,
						Bnf3);
				ElementLis BnfType3 = new ElementLis("Type", sBnfType3,
						Bnf3);
				ElementLis BnfBnfLot3 = new ElementLis("BnfLot",
						sBnfBnfLot3, Bnf3);
				ElementLis BnfBnfGrade3 = new ElementLis("BnfGrade",
						sBnfBnfGrade3, Bnf3);
				ElementLis BnfRelationToInsured3 = new ElementLis(
						"RelationToInsured", sBnfRelationToInsured3,
						Bnf3);
						
				ElementLis BnfValidYear3 = new ElementLis(
					"ValidYear", DateUtil.date10to8(sBnfValidYear3), Bnf3);
			}
		}

		ElementLis Risks = new ElementLis("Risks", Req);
		ElementLis Risk = new ElementLis("Risk", Risks);
		ElementLis PayIntv = new ElementLis("PayIntv", sPayIntv, Risk);
		ElementLis MainRiskCode = new ElementLis("MainRiskCode", sCode,
				Risk);
		ElementLis Code = new ElementLis("Code", sCode, Risk);
		ElementLis Mult = new ElementLis("Mult", sMult, Risk);
		ElementLis PayEndYearFlag = new ElementLis("PayEndYearFlag",
				sPayEndYearFlag, Risk);
		ElementLis PayEndYear = new ElementLis("PayEndYear",
				sPayEndYear, Risk);
		ElementLis InsuYearFlag = new ElementLis("InsuYearFlag",
				sInsuYearFlag, Risk);
		ElementLis InsuYear = new ElementLis("InsuYear", sInsuYear,
				Risk);
		ElementLis Prem = new ElementLis("Prem", sPrem, Risk);
		ElementLis Amnt = new ElementLis("Amnt", sAmnt, Risk);
		ElementLis GetYearFlag = new ElementLis("GetYearFlag",
				sGetYearFlag, Risk);
		ElementLis GetYear = new ElementLis("GetYear", sGetYear, Risk);
		ElementLis BonusGetMode = new ElementLis("BonusGetMode",
				sBonusGetMode, Risk);
		ElementLis HealthFlag = new ElementLis("HealthFlag",
				sHealthFlag, Risk);

		//如果有附加险1  
		if (!(sCode1 == null || sCode1.equals(""))) {
			ElementLis Risk1 = new ElementLis("Risk", Risks);
			ElementLis Code1 = new ElementLis("Code", sCode1, Risk1);
			ElementLis MainRiskCode1 = new ElementLis("MainRiskCode",
					sMainRiskCode1, Risk1);
			ElementLis Mult1 = new ElementLis("Mult", sMult1, Risk1);
			ElementLis Amnt1 = new ElementLis("Amnt", sAmnt1, Risk1);
			ElementLis Prem1 = new ElementLis("Prem", sPrem1, Risk1);
			ElementLis InsuYearFlag1 = new ElementLis("InsuYearFlag",
					sInsuYearFlag1, Risk1);
			ElementLis InsuYear1 = new ElementLis("InsuYear",
					sInsuYear1, Risk1);
			ElementLis PayEndYearFlag1 = new ElementLis(
					"PayEndYearFlag", sPayEndYearFlag1, Risk1);
			ElementLis PayEndYear1 = new ElementLis("PayEndYear",
					sPayEndYear1, Risk1);
			ElementLis GetYearFlag1 = new ElementLis("GetYearFlag",
					sGetYearFlag1, Risk1);
			ElementLis GetYear1 = new ElementLis("GetYear", sGetYear1,
					Risk1);
			ElementLis BoundGetMode1 = new ElementLis("BoundGetMode",
					sBonusGetMode1, Risk1);

		}
		//贷款信息
		ElementLis Loan = new ElementLis("Loan", Req);
		ElementLis LoanNo = new ElementLis("LoanNo", sLoanNo, Loan);
		ElementLis LoanBank = new ElementLis("LoanBank", sLoanBank, Loan);
		ElementLis LoanDate = new ElementLis("LoanDate", DateUtil.date10to8(sLoanDate), Loan);
		ElementLis LoanEndDate = new ElementLis("LoanEndDate", DateUtil.date10to8(sLoanEndDate), Loan);
		ElementLis LoanType = new ElementLis("LoanType", sLoanType, Loan);
		ElementLis LoanAccNo = new ElementLis("AccNo", sLoanAccNo, Loan);
		ElementLis LoanPrem = new ElementLis("LoanPrem", sLoanPrem, Loan);
		ElementLis InsuDate = new ElementLis("InsuDate", DateUtil.date10to8(sInsuDate), Loan);
		ElementLis InsuEndDate = new ElementLis("InsuEndDate", DateUtil.date10to8(sInsuEndDate), Loan);
		
		//账户信息	
		ElementLis Accts = new ElementLis("Accts", Req);
		ElementLis RiskCode = new ElementLis("RiskCode");
		ElementLis Count = new ElementLis("Count");
		RiskCode.setText("");
		Count.setText("0");
		Accts.addContent(RiskCode);
		Accts.addContent(Count);

		// System.out.println("************************设置保存路径************************");	
		//	StringBuilder mFilePath = new StringBuilder(SysInfo.cHome);
		//	System.out.println("mFilePath1************************"+mFilePath+"************************");   
		//		int number =(mFilePath.length()-1);
		//		int num = (number-8);
		//		mFilePath.delete(num, number);
		//	System.out.println("mFilePath2************************"+mFilePath+"************************");  
		//		mFilePath.append("msg"); 
		//				 .append("1").append('/');
		//				 .append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		//		String mInFilePath = mFilePath.toString()+"/icbcTestIn.xml";		
		//		String mOutFilePath = mFilePath.toString()+"/icbcTestReturn.xml";

		//System.out.println("************************"+mInFilePath+"************************");   
		//System.out.println("************************"+mOutFilePath+"************************");  

		System.out
				.println("************************装载Document************************");

		Document pXmlDoc = new Document(Req);

		int iPort = Integer.valueOf(sPort).intValue();
		byte[] mOutBytes = null;


			YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp, iPort);
			mOutBytes = mTestUI.sendRequestUI(sFlag, pXmlDoc);

		System.out
				.println("************************4************************");
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		//	OutputStream mFos = new FileOutputStream(mOutFilePath);
		//	JdomUtil.output(mOutXmlDoc, mFos);

		//打印报文内容 
		//uiPrint = JdomUtil.toStringFmt(pXmlDoc);
		//System.out.println(uiPrint);		
		//	mFos.flush();
		//	mFos.close();

		System.out.println("*****************3");

		ResultFlag = mOutXmlDoc.getRootElement().getChild("RetData")
				.getChildTextTrim("Flag");
		ResultMesg = mOutXmlDoc.getRootElement().getChild("RetData")
				.getChildTextTrim("Mesg");

		System.out.println(ResultFlag + "  " + ResultMesg);

		//	System.out.println("strNewTransNo = " + strNewTransNo);
		System.out.println("返回内容：" + ResultMesg);
		if (ResultFlag.equals("0")) { // fail
			Content = "交易失败：" + ResultMesg;
			strNewTransNo = PubFun1.CreateMaxNo("TransNo", 16);
			//jsp的String上不能出现"%",否则不能显示内容
			ResultMesg = ResultMesg.replace("%", "");

		} else {

			System.out.println("返回内容2：" + ResultMesg);
			strNewTransNo = sTransrNo;
			
			Content = ResultMesg;
			
			System.out.println("-----------开始取数（save页面）----------");

		}

	} catch (Exception e) {
		e.printStackTrace();
		ResultFlag = "Fail";
		xmlContent = e.getMessage();
		Content = e.getMessage();
	}
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=ResultFlag%>", "<%=Content%>");
parent.fraInterface.fm.TransrNo.value = '<%=strNewTransNo%>';

parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>';
//parent.fraInterface.fm.ContNo.value = 'strContNo';
</script>