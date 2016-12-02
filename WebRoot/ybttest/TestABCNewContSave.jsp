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
<%@page import="test.*"%>
<%@page import="test.*"%>
<%@page import="org.jdom.output.*"%>
<%@page import="org.jdom.input.*"%>
<%@page import="org.jdom.Attribute"%> 
<jsp:directive.page import="java.util.HashMap"/> 
<% 
	System.out.println("--> TestABCNewContSave.jsp");
	String ResultCode = null;
	String Content = null;
	String ResultInfoDesc = null; 
	String strNewTransNo = null;
	String xmlContent = null;
	String uiPrint = null;   
	
	
%>

<%
  try {
 
 System.out.println("****************银行端信息1*******************银行端信息1*******************");
	//*银行交易日期
	String BankDate	= request.getParameter("BankDate");
		BankDate = DateUtil.date10to8(BankDate);
	//*银行交易时间
	String BankTime	= String.valueOf(DateUtil.getCur6Time()); 
	//*银行代码
	String BankCode	= request.getParameter("BankCode");
	//*地区代码
	String ZoneNo	= request.getParameter("ZoneNo");
	//*网点代码
	String BrNo	= request.getParameter("BrNo");
	//*柜员代码
	String TellerNo	= request.getParameter("TellerNo");
	//*交易流水号
	String TransrNo	= request.getParameter("TransrNo");
	//*处理标志
	String FunctionFlag	= request.getParameter("FunctionFlag");
	//*保险公司代码
	String InsuID	= request.getParameter("InsuID");
	//*投保单号
	String ProposalContNo	= request.getParameter("ProposalContNo");
	//*投保日期
	String PolApplyDate	= request.getParameter("PolApplyDate");
		PolApplyDate = DateUtil.date10to8(PolApplyDate);
	//代收付账户姓名
	String AccName	= request.getParameter("AccName");
	//代收付银行名称
	//String AccBankNAME	= request.getParameter("AccBankNAME");
	//代收付银行账户
	//String BankAccNo	= request.getParameter("BankAccNo");
	//交费形式
	String PayMode	= request.getParameter("PayMode");
	//交费帐号
	String AccNo	= request.getParameter("AccNo");
	//特别约定
	String SpecContent	= request.getParameter("SpecContent");
	//*保单密码
	//String Password	= request.getParameter("Password");
	//*保单印刷号码
	String PrtNo	= request.getParameter("PrtNo");
	
	
//****************投保人信息*******************投保人信息*******************
 System.out.println("****************投保人信息*******************投保人信息*******************");
	//*投保人名称
	String Name	= request.getParameter("Name");
	//*投保人性别
	String Sex	= request.getParameter("Sex");
	//*投保人出生日期
	String Birthday	= request.getParameter("Birthday");
		Birthday = DateUtil.date10to8(Birthday);
	//*投保人证件类型
	String IDType	= request.getParameter("IDType");
	//*投保人证件号码
	String IDNo	= request.getParameter("IDNo");
	//投保人联系电话
	String Phone	= request.getParameter("Phone");
	//投保人手机号
	String Mobile	= request.getParameter("Mobile");
	//投保人通讯地址
	String Address	= request.getParameter("Address");
	//投保人通讯邮编
	String ZipCode	= request.getParameter("ZipCode");
	//与被保人关系
	String RelaToInsured	= request.getParameter("RelaToInsured");
	//投保人国籍
	String County	= request.getParameter("County");
	//投保人证件有效期
	String ValidYear	= request.getParameter("ValidYear");
	//投保人Email
	String Email	= request.getParameter("Email");
	//投保人职业代码
	String JobCode	= request.getParameter("JobCode");
	
	
	
	
//****************被保人信息*******************被保人信息*******************
 System.out.println("****************被保人信息*******************被保人信息*******************");
	//*被保人名称
	String BName	= request.getParameter("BName");
	//*被保人性别
	String BSex	= request.getParameter("BSex");
	//*被保人出生日期
	String BBirthday	= request.getParameter("BBirthday");
		BBirthday = DateUtil.date10to8(BBirthday);
	//*被保人证件类型
	String BIDType	= request.getParameter("BIDType");
	//*被保人证件号码
	String BIDNo	= request.getParameter("BIDNo");
	//被保人联系电话
	String BPhone	= request.getParameter("BPhone");
	//被保人手机号
	String BMobile	= request.getParameter("BMobile");
	//被保人通讯地址
	String BAddress	= request.getParameter("BAddress");
	//被保人通讯邮编
	String BZipCode	= request.getParameter("BZipCode");
	//被保人国籍
	String BCounty	= request.getParameter("BCounty");
	//被保人证件有效期
	String BValidYear	= request.getParameter("BValidYear");
	//被保人Email
	String BEmail	= request.getParameter("BEmail");
	//被保人职业代码
	String BJobCode	= request.getParameter("BJobCode");
	
//****************受益人信息1*******************受益人信息1*******************
 System.out.println("****************受益人信息1*******************受益人信息1*******************");
	//受益人类型
	String Type1	= request.getParameter("Type1");
	//受益人姓名
	String Name1	= request.getParameter("Name1");
	//受益人性别
	String Sex1	= request.getParameter("Sex1");
	//受益人出生日期
	String Birthday1	= request.getParameter("Birthday1");
		   Birthday1    = DateUtil.date10to8(Birthday1);
	///受益人证件类型
	String IDType1	= request.getParameter("IDType1");
	//受益人证件号码
	String IDNo1	= request.getParameter("IDNo1");
	//受益人与被保人关系
	String RelationToInsured1	= request.getParameter("RelationToInsured1");
	//受益人受益顺序
	String BnfGrade1	= request.getParameter("BnfGrade1");
	//受益人受益比例
	String BnfLot1	= request.getParameter("BnfLot1");
	//受益人通讯地址
	String Address1	= request.getParameter("Address1");
	//是否法定
	String sIndicator = request.getParameter("BeneficiaryIndicator");
	
System.out.println("收益人是否为法:"+sIndicator);
//****************受益人信息2*******************受益人信息2*******************
 System.out.println("****************受益人信息2*******************受益人信息2*******************");
	String Type2	= request.getParameter("Type2");
	String Name2	= request.getParameter("Name2");
	String Sex2	= request.getParameter("Sex2");
	String Birthday2	= request.getParameter("Birthday2");
	 Birthday2    = DateUtil.date10to8(Birthday2);
	String IDType2	= request.getParameter("IDType2");
	String IDNo2	= request.getParameter("IDNo2");
	String RelationToInsured2	= request.getParameter("RelationToInsured2");
	String BnfGrade2	= request.getParameter("BnfGrade2");
	String BnfLot2	= request.getParameter("BnfLot2");
	String Address2	= request.getParameter("Address2");
//****************受益人信息3*******************受益人信息3*******************
 System.out.println("****************受益人信息3*******************受益人信息3*******************");
	String Type3	= request.getParameter("Type3");
	String Name3	= request.getParameter("Name3");
	String Sex3	= request.getParameter("Sex3");
	String Birthday3	= request.getParameter("Birthday3");
		Birthday3    = DateUtil.date10to8(Birthday3);
	String IDType3	= request.getParameter("IDType3");
	String IDNo3	= request.getParameter("IDNo3");
	String RelationToInsured3	= request.getParameter("RelationToInsured3");
	String BnfGrade3	= request.getParameter("BnfGrade3");
	String BnfLot3	= request.getParameter("BnfLot3");
	String Address3	= request.getParameter("Address3");

//****************主险信息*******************主险信息*******************
 System.out.println("****************主险信息*******************主险信息*******************");
	//险种代码
	String Code	= request.getParameter("Code");
	//主险代码
	String MainCode	= request.getParameter("Code");
	//保险费
	String Prem	= request.getParameter("Prem");
	//保额
	String Amnt	= request.getParameter("Amnt");
	//起保日期
	String CValiDate	= request.getParameter("CValiDate");
		CValiDate    = DateUtil.date10to8(CValiDate);
	//投保份数
	String Mult	= request.getParameter("Mult");
	//交费方式
	String PayIntv	= request.getParameter("PayIntv");
	//保险期间
	String Years	= request.getParameter("Years");
	//交费年期年龄标志
	String PayEndYearFlag	= request.getParameter("PayEndYearFlag");
	//交费年期/年龄
	String PayEndYear	= request.getParameter("PayEndYear");
	//保障年期类型
	String InsuYearFlag	= request.getParameter("InsuYearFlag");
	//保障年期
	String InsuYear	= request.getParameter("InsuYear");
	//领取年期年龄标志
	String GetYearFlag	= request.getParameter("GetYearFlag");
	//领取年期/年龄
	String GetYear	= request.getParameter("GetYear");
	//红利领取方式
	String BonusGetMode	= request.getParameter("BonusGetMode");
	//满期保险金领取方式
	String FullBonusGetMode	= request.getParameter("FullBonusGetMode");
	//健康告知标志
	String HealthFlag	= request.getParameter("HealthFlag");
	
	
//****************附加险信息1********************附加险信息1*******************
System.out.println("****************附加险信息1*******************附加险信息1*******************");
	String Code1	= request.getParameter("Code1");
	String MainCode1	= request.getParameter("Code1");
	String Prem1	= request.getParameter("Prem1");
	String Amnt1	= request.getParameter("Amnt1");
	String CValiDate1	= request.getParameter("CValiDate1");
		CValiDate1    = DateUtil.date10to8(CValiDate1);
	String Mult1	= request.getParameter("Mult1");
	String PayIntv1	= request.getParameter("PayIntv1");
	String Years1	= request.getParameter("Years1");
	String PayEndYearFlag1	= request.getParameter("PayEndYearFlag1");
	String PayEndYear1	= request.getParameter("PayEndYear1");
	String InsuYearFlag1	= request.getParameter("InsuYearFlag1");
	String InsuYear1	= request.getParameter("InsuYear1");
	String GetYearFlag1	= request.getParameter("GetYearFlag1");
	String GetYear1	= request.getParameter("GetYear1");
	String BonusGetMode1	= request.getParameter("BonusGetMode1");
	String FullBonusGetMode1	= request.getParameter("FullBonusGetMode1");
	String HealthFlag1	= request.getParameter("HealthFlag1");
	
	//****************附加险信息2********************附加险信息2*******************
	
System.out.println("****************附加险信息2*******************附加险信息2*******************");
	String Code2	= request.getParameter("Code2");
	String MainCode2	= request.getParameter("Code2");
	String Prem2	= request.getParameter("Prem2");
	String Amnt2	= request.getParameter("Amnt2");
	String CValiDate2	= request.getParameter("CValiDate2");
		CValiDate2    = DateUtil.date10to8(CValiDate2);
	String Mult2	= request.getParameter("Mult2");
	String PayIntv2	= request.getParameter("PayIntv2");
	String Years2	= request.getParameter("Years2");
	String PayEndYearFlag2	= request.getParameter("PayEndYearFlag2");
	String PayEndYear2	= request.getParameter("PayEndYear2");
	String InsuYearFlag2	= request.getParameter("InsuYearFlag2");
	String InsuYear2	= request.getParameter("InsuYear2");
	String GetYearFlag2	= request.getParameter("GetYearFlag2");
	String GetYear2	= request.getParameter("GetYear2");
	String BonusGetMode2	= request.getParameter("BonusGetMode2");
	String FullBonusGetMode2	= request.getParameter("FullBonusGetMode2");
	String HealthFlag2	= request.getParameter("HealthFlag2");
	
//****************投资账户节点*******************投资账户节点*******************
 System.out.println("****************投资账户节点*******************投资账户节点*******************");

//****************投保信息*******************投保信息*******************	
 System.out.println("****************投保信息*******************投保信息*******************");
	
	
//****************ip-port-flag*******************ip-port-flag*******************	
System.out.println("****************ip-port*******************ip-port*******************");	
String sIp = request.getParameter("ip");
String sPort = request.getParameter("port");



//把保单信息在页面显示
		xmlContent = new String(""); 

		
 System.out.println("************************装载XML************************");		 
 //ElementLis aaaa = new ElementLis("","","",);  
 //ElementLis  aaaa= new ElementLis("",);
 //ElementLis aaaa = new ElementLis("",""); 
 ElementLis Req = new ElementLis("Req");	 	  
	ElementLis BankDateEle = new ElementLis("BankDate",BankDate,Req);    
	ElementLis BankTimeEle = new ElementLis("BankTime",BankTime,Req);   
	ElementLis BankCodeEle = new ElementLis("BankCode",BankCode,Req);   
	ElementLis ZoneNoEle = new ElementLis("ZoneNo",ZoneNo,Req);
	ElementLis BrNoEle = new ElementLis("BrNo",BrNo,Req);
	ElementLis TellerNoEle = new ElementLis("TellerNo",TellerNo,Req);
	ElementLis TransrNoEle = new ElementLis("TransrNo",TransrNo,Req);
	ElementLis FunctionFlagEle = new ElementLis("FunctionFlag",FunctionFlag,Req);
	ElementLis InsuIDEle = new ElementLis("InsuID",InsuID,Req);
		ElementLis Base = new ElementLis("Base",Req);
			ElementLis ProposalContNoEle = new ElementLis("ProposalContNo",ProposalContNo,Base);
			ElementLis PolApplyDateEle = new ElementLis("PolApplyDate",PolApplyDate,Base);
			ElementLis PayModeEle = new ElementLis("PayMode",PayMode,Base);
			ElementLis AccNameEle = new ElementLis("AccName",AccName,Base);
			ElementLis AccNoEle = new ElementLis("AccNo",AccNo,Base);
			ElementLis PrtNoEle = new ElementLis("PrtNo",PrtNo,Base);
			ElementLis SpecContentEle = new ElementLis("SpecContent",SpecContent,Base);

	
	//投保人信息	
	ElementLis Appl = new ElementLis("Appl",Req); 
		ElementLis NameEle = new ElementLis("Name",Name,Appl); 
		ElementLis SexEle = new ElementLis("Sex",Sex,Appl);
		ElementLis BirthdayEle = new ElementLis("Birthday",Birthday,Appl);
		ElementLis IDTypeEle = new ElementLis("IDType",IDType,Appl);
		ElementLis IDNoEle = new ElementLis("IDNo",IDNo,Appl);
		ElementLis PhoneEle = new ElementLis("Phone",Phone,Appl);
		ElementLis MobileEle = new ElementLis("Mobile",Mobile,Appl);
		ElementLis AddressEle = new ElementLis("Address",Address,Appl);
		ElementLis ZipCodeEle = new ElementLis("ZipCode",ZipCode,Appl);
		ElementLis RelaToInsuredEle = new ElementLis("RelaToInsured",RelaToInsured,Appl);
		ElementLis CountyEle = new ElementLis("County",County,Appl);
		ElementLis ValidYearEle = new ElementLis("ValidYear",ValidYear,Appl);
		ElementLis EmailEle = new ElementLis("Email",Email,Appl);
		ElementLis JobCodeEle = new ElementLis("JobCode",JobCode,Appl);
		
		
	//被保人	
	ElementLis Insu = new ElementLis("Insu",Req); 
		ElementLis BNameEle = new ElementLis("Name",BName,Insu); 
		ElementLis BSexEle = new ElementLis("Sex",BSex,Insu);
		ElementLis BBirthdayEle = new ElementLis("Birthday",BBirthday,Insu);
		ElementLis BIDTypeEle = new ElementLis("IDType",BIDType,Insu);
		ElementLis BIDNoEle = new ElementLis("IDNo",BIDNo,Insu);
		ElementLis BPhoneEle = new ElementLis("Phone",BPhone,Insu);
		ElementLis BMobileEle = new ElementLis("Mobile",BMobile,Insu);
		ElementLis BAddressEle = new ElementLis("Address",BAddress,Insu);
		ElementLis BZipCodeEle = new ElementLis("ZipCode",BZipCode,Insu);
		ElementLis BCountyEle = new ElementLis("County",BCounty,Insu);
		ElementLis BValidYearEle = new ElementLis("ValidYear",BValidYear,Insu);
		ElementLis BEmailEle = new ElementLis("Email",BEmail,Insu);
		ElementLis BJobCodeEle = new ElementLis("JobCode",BJobCode,Insu);
		
		//受益人
	ElementLis Bnfs = new ElementLis("Bnfs",Req); 
	ElementLis CountEle = new ElementLis("Count","0",Bnfs);
	ElementLis Bnf1 = new ElementLis("Bnf",Bnfs);
		if(sIndicator.equals("N")){
		CountEle.setText("1");
		ElementLis Type1Ele = new ElementLis("Type",Type1,Bnf1); 
				ElementLis Name1Ele = new ElementLis("Name",Name1,Bnf1); 
				ElementLis Sex1Ele = new ElementLis("Sex",Sex1,Bnf1); 
				ElementLis Birthday1Ele = new ElementLis("Birthday",Birthday1,Bnf1); 
				ElementLis IDType1Ele = new ElementLis("IDType",IDType1,Bnf1); 
				ElementLis IDNo1Ele = new ElementLis("IDNo",IDNo1,Bnf1); 
				ElementLis RelationToInsured1Ele = new ElementLis("RelationToInsured",RelationToInsured1,Bnf1); 
				ElementLis BnfGrade1Ele = new ElementLis("BnfGrade",BnfGrade1,Bnf1); 
				ElementLis BnfLot1Ele = new ElementLis("BnfLot",BnfLot1,Bnf1); 
				ElementLis Address1Ele = new ElementLis("Address",Address1,Bnf1); 
		}
							
//如果有第二受益人 
		if(!(Name2==null||Name2.equals(""))){
		CountEle.setText("2");
		ElementLis Bnf2 = new ElementLis("Bnf",Bnfs);			  
				ElementLis Type2Ele = new ElementLis("Type",Type2,Bnf2); 
				ElementLis Name2Ele = new ElementLis("Name",Name2,Bnf2); 
				ElementLis Sex2Ele = new ElementLis("Sex",Sex2,Bnf2); 
				ElementLis Birthday2Ele = new ElementLis("Birthday",Birthday2,Bnf2); 
				ElementLis IDType2Ele = new ElementLis("IDType",IDType2,Bnf2); 
				ElementLis IDNo2Ele = new ElementLis("IDNo",IDNo2,Bnf2); 
				ElementLis RelationToInsured2Ele = new ElementLis("RelationToInsured",RelationToInsured2,Bnf2); 
				ElementLis BnfGrade2Ele = new ElementLis("BnfGrade",BnfGrade2,Bnf2); 
				ElementLis BnfLot2Ele = new ElementLis("BnfLot",BnfLot2,Bnf2); 
				ElementLis Address2Ele = new ElementLis("Address",Address2,Bnf2); 
		
		}

//如果有第三受益人 
	if(!(Name3==null||Name3.equals(""))){
		CountEle.setText("3");
			ElementLis Bnf3 = new ElementLis("Bnf",Bnfs);			  
				ElementLis Type3Ele = new ElementLis("Type",Type3,Bnf3); 
				ElementLis Name3Ele = new ElementLis("Name",Name3,Bnf3); 
				ElementLis Sex3Ele = new ElementLis("Sex",Sex3,Bnf3); 
				ElementLis Birthday3Ele = new ElementLis("Birthday",Birthday3,Bnf3); 
				ElementLis IDType3Ele = new ElementLis("IDType",IDType3,Bnf3); 
				ElementLis IDNo3Ele = new ElementLis("IDNo",IDNo3,Bnf3); 
				ElementLis RelationToInsured3Ele = new ElementLis("RelationToInsured",RelationToInsured3,Bnf3); 
				ElementLis BnfGrade3Ele = new ElementLis("BnfGrade",BnfGrade3,Bnf3); 
				ElementLis BnfLot3Ele = new ElementLis("BnfLot",BnfLot3,Bnf3); 
				ElementLis Address3Ele = new ElementLis("Address",Address3,Bnf3); 
		
		}
		
	
	// 险种信息
		ElementLis RisksEle = new ElementLis("Risks",Req); 
			ElementLis RisksCountEle = new ElementLis("Count","1",RisksEle);
				ElementLis RiskEle = new ElementLis("Risk",RisksEle); 	
					ElementLis CodeEle = new ElementLis("Code",Code,RiskEle); 	
					ElementLis MainRiskCodeEle = new ElementLis("MainRiskCode",MainCode,RiskEle); 
					ElementLis PremEle = new ElementLis("Prem",Prem,RiskEle); 
					ElementLis AmntEle = new ElementLis("Amnt",Amnt,RiskEle); 
					ElementLis CValiDateEle = new ElementLis("CValiDate",CValiDate,RiskEle); 
					ElementLis MultEle = new ElementLis("Mult",Mult,RiskEle); 
					ElementLis PayIntvEle = new ElementLis("PayIntv",PayIntv,RiskEle); 
					ElementLis YearsEle = new ElementLis("Years",Years,RiskEle);
					ElementLis PayEndYearFlagEle = new ElementLis("PayEndYearFlag",PayEndYearFlag,RiskEle);
					ElementLis PayEndYearEle = new ElementLis("PayEndYear",PayEndYear,RiskEle);
					ElementLis InsuYearFlagEle = new ElementLis("InsuYearFlag",InsuYearFlag,RiskEle);
					ElementLis InsuYearEle = new ElementLis("InsuYear",InsuYear,RiskEle);
					ElementLis GetYearFlagEle = new ElementLis("GetYearFlag",GetYearFlag,RiskEle);
					ElementLis GetYearEle = new ElementLis("GetYear",GetYear,RiskEle);
					ElementLis BonusGetModeEle = new ElementLis("BonusGetMode",BonusGetMode,RiskEle);
					ElementLis FullBonusGetModeEle = new ElementLis("FullBonusGetMode",FullBonusGetMode,RiskEle);
					ElementLis HealthFlagEle = new ElementLis("HealthFlag",HealthFlag,RiskEle);
					
								 
//如果有附加险1    
		if(!(Code1==null||Code1.equals(""))){
			ElementLis RiskEle1 = new ElementLis("Risk",RisksEle);
				ElementLis CodeEle1 = new ElementLis("Code",Code1,RiskEle1); 	
					ElementLis MainRiskCodeEle1 = new ElementLis("MainRiskCode",MainCode,RiskEle1); 
					ElementLis PremEle1 = new ElementLis("Prem",Prem1,RiskEle1); 
					ElementLis AmntEle1 = new ElementLis("Amnt",Amnt1,RiskEle1); 
					ElementLis CValiDateEle1 = new ElementLis("CValiDate",CValiDate1,RiskEle1); 
					ElementLis MultEle1 = new ElementLis("Mult",Mult1,RiskEle1); 
					ElementLis PayIntvEle1 = new ElementLis("PayIntv",PayIntv1,RiskEle1); 
					ElementLis YearsEle1 = new ElementLis("Years",Years1,RiskEle1);
					ElementLis PayEndYearFlagEle1 = new ElementLis("PayEndYearFlag",PayEndYearFlag1,RiskEle1);
					ElementLis PayEndYearEle1 = new ElementLis("PayEndYear",PayEndYear1,RiskEle1);
					ElementLis InsuYearFlagEle1 = new ElementLis("InsuYearFlag",InsuYearFlag1,RiskEle1);
					ElementLis InsuYearEle1 = new ElementLis("InsuYear",InsuYear1,RiskEle1);
					ElementLis GetYearFlagEle1 = new ElementLis("GetYearFlag",GetYearFlag1,RiskEle1);
					ElementLis GetYearEle1 = new ElementLis("GetYear",GetYear1,RiskEle1);
					ElementLis BonusGetModeEle1 = new ElementLis("BonusGetMode",BonusGetMode1,RiskEle1);
					ElementLis FullBonusGetModeEle1 = new ElementLis("FullBonusGetMode",FullBonusGetMode1,RiskEle1);
					ElementLis HealthFlagEle1 = new ElementLis("HealthFlag",HealthFlag1,RiskEle1);
			} 
//如果有附加险2    
		if(!(Code2==null||Code2.equals(""))){
			ElementLis RiskEle2 = new ElementLis("Risk",RisksEle);
				ElementLis CodeEle2 = new ElementLis("Code",Code2,RiskEle2); 	
					ElementLis MainRiskCodeEle2 = new ElementLis("MainRiskCode",MainCode,RiskEle2); 
					ElementLis PremEle2 = new ElementLis("Prem",Prem2,RiskEle2); 
					ElementLis AmntEle2 = new ElementLis("Amnt",Amnt2,RiskEle2); 
					ElementLis CValiDateEle2 = new ElementLis("CValiDate",CValiDate2,RiskEle2); 
					ElementLis MultEle2 = new ElementLis("Mult",Mult2,RiskEle2); 
					ElementLis PayIntvEle2 = new ElementLis("PayIntv",PayIntv2,RiskEle2); 
					ElementLis YearsEle2 = new ElementLis("Years",Years2,RiskEle2);
					ElementLis PayEndYearFlagEle2 = new ElementLis("PayEndYearFlag",PayEndYearFlag2,RiskEle2);
					ElementLis PayEndYearEle2 = new ElementLis("PayEndYear",PayEndYear2,RiskEle2);
					ElementLis InsuYearFlagEle2 = new ElementLis("InsuYearFlag",InsuYearFlag2,RiskEle2);
					ElementLis InsuYearEle2 = new ElementLis("InsuYear",InsuYear2,RiskEle2);
					ElementLis GetYearFlagEle2 = new ElementLis("GetYearFlag",GetYearFlag2,RiskEle2);
					ElementLis GetYearEle2 = new ElementLis("GetYear",GetYear2,RiskEle2);
					ElementLis BonusGetModeEle2 = new ElementLis("BonusGetMode",BonusGetMode2,RiskEle2);
					ElementLis FullBonusGetModeEle2 = new ElementLis("FullBonusGetMode",FullBonusGetMode2,RiskEle2);
					ElementLis HealthFlagEle2 = new ElementLis("HealthFlag",HealthFlag2,RiskEle2);
			} 

System.out.println("************************装载Document************************");	
    
 			Document pXmlDoc = new Document(Req);	
System.out.println("************pXmlDoc***********打印装载报文Start************************");	 			
JdomUtil.print(pXmlDoc);
System.out.println("************************打印装载报文End************************");	
		int iPort = Integer.valueOf(sPort);
		YD_ABCTestUI mTestUI = new YD_ABCTestUI(sIp,iPort);  
		
		byte[] mInClearBodyBytes = JdomUtil.toBytes(pXmlDoc);
		byte[] mOutBytes = mTestUI.sendRequest(FunctionFlag, new ByteArrayInputStream(mInClearBodyBytes));
		
		Document mOutXmlDoc = JdomUtil.build(mOutBytes); 
System.out.println("*****************mOutXmlDoc-------");

JdomUtil.print(mOutXmlDoc);
		 
		ResultCode = mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Flag");
		ResultInfoDesc = mOutXmlDoc.getRootElement().getChild("RetData").getChildTextTrim("Mesg");

System.out.println(ResultCode + " ---------------- " + ResultInfoDesc);
System.out.println("返回内容："+ResultInfoDesc);
		if (ResultCode.equals("0")) { // fail农行的0 Flag表示失败
	        Content = "交易失败：" + ResultInfoDesc ;
	        strNewTransNo = "YBTTEST"+PubFun1.CreateMaxNo("TransNo",9);
            //jsp的String上不能出现"%",否则不能显示内容
	       ResultInfoDesc = ResultInfoDesc.replace("%","");
	       ResultInfoDesc = ResultInfoDesc.replace(" ","");
	      ResultInfoDesc = StringTool.toHTMLValueFormat(ResultInfoDesc);
		} else { 
		String returnProposalContNo = mOutXmlDoc.getRootElement().getChild("Base").getChildTextTrim("ProposalContNo");
		ResultInfoDesc+="保险单号码为："+returnProposalContNo;
	       strNewTransNo = "YBTTEST"+PubFun1.CreateMaxNo("TransNo",9);
	      
	        System.out.println("-----------返回结束（save页面）----------");
		   }  
	} catch (Exception e) {
		e.printStackTrace();
		ResultCode = "Fail";
		xmlContent+= e.getMessage();  
		Content = e.getMessage(); 
	}      
%>   
<script language="javascript">
   parent.fraInterface.afterSubmit("<%=ResultCode%>", "<%=ResultInfoDesc%>");
   parent.fraInterface.fm.NewTransNo.value = '<%=strNewTransNo%>';

   parent.fraInterface.fm.xmlContent.value = '<%=xmlContent.toString()%>'; 
</script>