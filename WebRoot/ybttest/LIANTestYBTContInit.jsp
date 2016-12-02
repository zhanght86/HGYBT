<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//程序名称：LIANTestYBTContInit.jsp
	//程序功能：会计确认 
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strBankDate = DateUtil.getCur10Date();
	String strTransrNo = PubFun1.CreateMaxNo("TransNo",16);
	String strProposalContNo = "2105131"+PubFun1.CreateMaxNo("ProNo",8);
	String strPrtNo = "";
	String strAppntName = "Appnt"+PubFun1.CreateMaxNo("AppntName",5);
	String strInsuName = "Insu"+PubFun1.CreateMaxNo("InsuName",5);
	Element mTestUI = 
  		 MidplatConf.newInstance().getConf().getRootElement().getChild("ABCTestUI");
  	 String tIp = mTestUI.getAttributeValue("ip");
  	 String tPort = mTestUI.getAttributeValue("port");
  	 String tPortName = mTestUI.getAttributeValue("portname");
  	 
%>
<script language="JavaScript">
function initForm()
{
	try 
	{
		initInputBox();
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->InitForm 函数中发生异常:初始化界面错误!");
	}
}



function initContConfirm()
{
try { 
//交易信息
	
	
	fm.ReqsrNo.value = fm.InputTransrNo.value;
	
	//银行端交易日期
	fm.BankDate.value = '<%=strBankDate%>';
	//交易流水号
	fm.TransrNo.value = '<%=PubFun1.CreateMaxNo("TransrNo",16)%>';
	//地区代码
	//fm.ZoneNo.value = '1101';
	//网点代码
	//fm.BrNo.value = '0102';
	//柜员代码
	//fm.TellerNo.value = '00001';
	//投保日期 
	//fm.PolApplyDate.value = '';
	//投保书号
	//fm.ProposalContNo.value = '';
	//保单印刷号
	fm.PrtNo.value = '';
   
	//投保人信息
	//投保人姓名
	fm.ApplName.value = '';
	//投保人性别
	fm.ApplSex.value = '';
	//投保人证件类型
	fm.ApplIDType.value = '';
	//投保人证件号码
	fm.ApplIDNo.value = '';
	//投保人生日
	fm.ApplBirthday.value = '';
	//投保人电子邮箱
	fm.ApplEmail.value = '';
	//投保人通讯地址	
	fm.ApplAddress.value = '';
	//投保人邮政编码
	fm.ApplZipCode.value = '';
	//投保人家庭电话
	fm.ApplPhone.value = '';
	//投保人移动电话
	fm.ApplMobile.value = '';
	//投保人与被报人的关系
	fm.ApplRelaToInsured.value = '';
	//投保人职业代码
	fm.ApplJobCode.value = '';
	fm.ApplValidYear.value='';
	
//被保人信息
	//被保人姓名
	fm.InsuName.value = '';
	//被保人性别
	fm.InsuSex.value = ''; 
	fm.InsuSexh.value = '';
	//被保人证件类型
	fm.InsuIDType.value = '';
	fm.InsuIDTypeh.value = '';
	//被保人证件号码
	fm.InsuIDNo.value = '';
	//被保人生日
	fm.InsuBirthday.value = '';
	//被保人电子邮箱
	fm.InsuEmail.value = '';
	//被保人通讯地址	
	fm.InsuAddress.value = ''; 
	//被保人邮政编码
	fm.InsuZipCode.value = '';
	//被保人家庭电话 
	fm.InsuPhone.value = '';
	//被保人移动电话
	fm.InsuMobile.value = '';
	//健康告知
	fm.HealthFlag.value = '';
	//被保人职业代码
	fm.InsuJobCode.value = '';
	fm.InsuValidYear.value='';
	
//受益人信息1
    //受益人类型
	fm.BnfType1.value = '';
	//受益人姓名
	fm.BnfName1.value = '';
	//受益人性别
	fm.BnfSex1.value = '';
	//受益人证件类型
	fm.BnfIDType1.value = '';
	//受益人证件号码
	fm.BnfIDNo1.value = '';
	//受益人出生日期
	fm.BnfBirthday1.value = '';
	//受益百分数
	fm.BnfBnfLot1.value = '';
	//受益顺序  
	fm.BnfBnfGrade1.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured1.value = '';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='';  
 
//受益人信息2
	//受益人类型
	fm.BnfType2.value = '';
	//受益人姓名
	fm.BnfName2.value = '';
	//受益人性别
	fm.BnfSex2.value = '';
	//受益人证件类型
	fm.BnfIDType2.value = '';
	//受益人证件号码
	fm.BnfIDNo2.value = '';
	//受益人出生日期
	fm.BnfBirthday2.value = '';
	//受益百分数
	fm.BnfBnfLot2.value = '';
	//受益顺序  
	fm.BnfBnfGrade2.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured2.value = '';
	 
//受益人信息3
	//受益人类型
	fm.BnfType3.value = '';
	//受益人姓名
	fm.BnfName3.value = '';
	//受益人性别
	fm.BnfSex3.value = '';
	//受益人证件类型
	fm.BnfIDType3.value = '';
	//受益人证件号码
	fm.BnfIDNo3.value = '';
	//受益人出生日期
	fm.BnfBirthday3.value = '';
	//受益百分数
	fm.BnfBnfLot3.value = '';
	//受益顺序  
	fm.BnfBnfGrade3.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured3.value = '';
	
//主险信息
	//主险代码 
	fm.Code.value = '';
	//份数 
	fm.Mult.value = '';
	//保费 
	fm.Prem.value = '';
	//保额 
	fm.Amnt.value = '';
	//保险年期类型  
	fm.InsuYearFlag.value = '';
	//保险年期 
	fm.InsuYear.value = '';
	//缴费年期类型 
	fm.PayEndYearFlag.value = '';
	//缴费年期 
	fm.PayEndYear.value = '';

//附加险信息1
	//主险代码 
	//fm.Code1.value = '';
	//份数 
	//fm.Mult1.value = '';
	//保费 
	//fm.Prem1.value = '';
	//保额 
	//fm.Amnt1.value = '';
	//保险年期类型  
	//fm.InsuYearFlag1.value = '';
	//保险年期 
	//fm.InsuYear1.value = '';
	//缴费年期类型 
	//fm.PayEndYearFlag1.value = '';
	//缴费年期 
	//fm.PayEndYear1.value = '';
		
//投资账户节点
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';
	
//投保信息

	//缴费方式 
	fm.PayIntv.value = ''; 
	//保单传送方式
	//fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccNo.value = '';
	//银行账号姓名
	fm.AccName.value = '';
	//红利领取方式
	fm.BonusGetMode.value = '';
	//首次追加保费
	//fm.FirstSuperaddAmt.value = '';
	//投资日期标志 
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '02'; 
	//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initContConcel()
{
try { 
//交易信息
	//银行端交易日期
	
	fm.OriginalTransrNo.value = fm.InputTransrNo.value;
	fm.BankDate.value = '<%=strBankDate%>';
	//交易流水号
	fm.TransrNo.value = '<%=strTransrNo%>';
	//地区代码
	fm.ZoneNo.value = '1101';
	//网点代码
	fm.BrNo.value = '0102';
	//柜员代码
	fm.TellerNo.value = '00001';
	//投保日期 
	fm.PolApplyDate.value = '<%=strBankDate%>';
	//投保书号
	//保单印刷号
	fm.PrtNo.value = '<%=strPrtNo%>';
   
	//投保人信息
	//投保人姓名
	fm.ApplName.value = '';
	//投保人性别
	fm.ApplSex.value = '';
	//投保人证件类型
	fm.ApplIDType.value = '';
	//投保人证件号码
	fm.ApplIDNo.value = '';
	//投保人生日
	fm.ApplBirthday.value = '';
	//投保人电子邮箱
	fm.ApplEmail.value = '';
	//投保人通讯地址	
	fm.ApplAddress.value = '';
	//投保人邮政编码
	fm.ApplZipCode.value = '';
	//投保人家庭电话
	fm.ApplPhone.value = '';
	//投保人移动电话
	fm.ApplMobile.value = '';
	//投保人与被报人的关系
	fm.ApplRelaToInsured.value = '';
	//投保人职业代码
	fm.ApplJobCode.value = '';
	
//被保人信息
	//被保人姓名
	fm.InsuName.value = '';
	//被保人性别
	fm.InsuSex.value = ''; 
	fm.InsuSexh.value = '';
	//被保人证件类型
	fm.InsuIDType.value = '';
	fm.InsuIDTypeh.value = '';
	//被保人证件号码
	fm.InsuIDNo.value = '';
	//被保人生日
	fm.InsuBirthday.value = '';
	//被保人电子邮箱
	fm.InsuEmail.value = '';
	//被保人通讯地址	
	fm.InsuAddress.value = ''; 
	//被保人邮政编码
	fm.InsuZipCode.value = '';
	//被保人家庭电话 
	fm.InsuPhone.value = '';
	//被保人移动电话
	fm.InsuMobile.value = '';
	//健康告知
	fm.HealthFlag.value = '';
	//被保人职业代码
	fm.InsuJobCode.value = '';
	
//受益人信息1
    //受益人类型
	fm.BnfType1.value = '';
	//受益人姓名
	fm.BnfName1.value = '';
	//受益人性别
	fm.BnfSex1.value = '';
	//受益人证件类型
	fm.BnfIDType1.value = '';
	//受益人证件号码
	fm.BnfIDNo1.value = '';
	//受益人出生日期
	fm.BnfBirthday1.value = '';
	//受益百分数
	fm.BnfBnfLot1.value = '';
	//受益顺序  
	fm.BnfBnfGrade1.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured1.value = '';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='';  
 
//受益人信息2
	//受益人类型
	fm.BnfType2.value = '';
	//受益人姓名
	fm.BnfName2.value = '';
	//受益人性别
	fm.BnfSex2.value = '';
	//受益人证件类型
	fm.BnfIDType2.value = '';
	//受益人证件号码
	fm.BnfIDNo2.value = '';
	//受益人出生日期
	fm.BnfBirthday2.value = '';
	//受益百分数
	fm.BnfBnfLot2.value = '';
	//受益顺序  
	fm.BnfBnfGrade2.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured2.value = '';
	 
//受益人信息3
	//受益人类型
	fm.BnfType3.value = '';
	//受益人姓名
	fm.BnfName3.value = '';
	//受益人性别
	fm.BnfSex3.value = '';
	//受益人证件类型
	fm.BnfIDType3.value = '';
	//受益人证件号码
	fm.BnfIDNo3.value = '';
	//受益人出生日期
	fm.BnfBirthday3.value = '';
	//受益百分数
	fm.BnfBnfLot3.value = '';
	//受益顺序  
	fm.BnfBnfGrade3.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured3.value = '';
	
//主险信息
	//主险代码 
	fm.Code.value = '';
	//份数 
	fm.Mult.value = '';
	//保费 
	fm.Prem.value = '';
	//保额 
	fm.Amnt.value = '';
	//保险年期类型  
	fm.InsuYearFlag.value = '';
	//保险年期 
	fm.InsuYear.value = '';
	//缴费年期类型 
	fm.PayEndYearFlag.value = '';
	//缴费年期 
	fm.PayEndYear.value = '';

//附加险信息1
	//主险代码 
	fm.Code1.value = '';
	//份数 
	fm.Mult1.value = '';
	//保费 
	fm.Prem1.value = '';
	//保额 
	fm.Amnt1.value = '';
	//保险年期类型  
	fm.InsuYearFlag1.value = '';
	//保险年期 
	fm.InsuYear1.value = '';
	//缴费年期类型 
	fm.PayEndYearFlag1.value = '';
	//缴费年期 
	fm.PayEndYear1.value = '';
		
//投资账户节点
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';
	
//投保信息

	//缴费方式 
	fm.PayIntv.value = ''; 
	//保单传送方式
	//fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccNo.value = '';
	//银行账号姓名
	fm.AccName.value = '';
	//红利领取方式
	fm.BonusGetMode.value = '';
	//首次追加保费
	//fm.FirstSuperaddAmt.value = '';
	//投资日期标志 
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '03'; 
	//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initBox()
{
try { 	
//交易信息
	//银行端交易日期
	fm.BankDate.value = '<%=strBankDate%>';
	//交易流水号
	fm.TransrNo.value= '<%=strTransrNo%>';
	//地区代码
	fm.ZoneNo.value = '';
	//网点代码
	fm.BrNo.value = '';
	//柜员代码
	fm.TellerNo.value = '';
	//投保日期 
	fm.PolApplyDate.value = '<%=strBankDate%>';
	//投保书号
	fm.ProposalContNo.value = '<%= strProposalContNo%>';
	//保单印刷号
	
	fm.PrtNo.value = '';
   
//投保人信息
	//投保人姓名
	fm.ApplName.value = '';
	//投保人性别
	fm.ApplSex.value = '';
	//投保人证件类型
	fm.ApplIDType.value = '';
	//投保人证件号码
	fm.ApplIDNo.value = '';
	//投保人生日
	fm.ApplBirthday.value = '';
	//投保人电子邮箱
	fm.ApplEmail.value = '';
	//投保人通讯地址	
	fm.ApplAddress.value = '';
	//投保人邮政编码
	fm.ApplZipCode.value = '';
	//投保人家庭电话
	fm.ApplPhone.value = '';
	//投保人移动电话
	fm.ApplMobile.value = '';
	//投保人与被报人的关系
	fm.ApplRelaToInsured.value = '';
	//投保人职业代码
	fm.ApplJobCode.value = '';
	
//被保人信息
	//被保人姓名
	fm.InsuName.value = '';
	//被保人性别
	fm.InsuSex.value = ''; 
	fm.InsuSexh.value = '';
	//被保人证件类型
	fm.InsuIDType.value = '';
	fm.InsuIDTypeh.value = '';
	//被保人证件号码
	fm.InsuIDNo.value = '';
	//被保人生日
	fm.InsuBirthday.value = '';
	//被保人电子邮箱
	fm.InsuEmail.value = '';
	//被保人通讯地址	
	fm.InsuAddress.value = ''; 
	//被保人邮政编码
	fm.InsuZipCode.value = '';
	//被保人家庭电话 
	fm.InsuPhone.value = '';
	//被保人移动电话
	fm.InsuMobile.value = '';
	//健康告知
	fm.HealthFlag.value = '';
	//被保人职业代码
	fm.InsuJobCode.value = '';
	
//受益人信息1
    //受益人类型
	fm.BnfType1.value = '';
	//受益人姓名
	fm.BnfName1.value = '';
	//受益人性别
	fm.BnfSex1.value = '';
	//受益人证件类型
	fm.BnfIDType1.value = '';
	//受益人证件号码
	fm.BnfIDNo1.value = '';
	//受益人出生日期
	fm.BnfBirthday1.value = '';
	//受益百分数
	fm.BnfBnfLot1.value = '';
	//受益顺序  
	fm.BnfBnfGrade1.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured1.value = '';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='';  
 
//受益人信息2
	//受益人类型
	fm.BnfType2.value = '';
	//受益人姓名
	fm.BnfName2.value = '';
	//受益人性别
	fm.BnfSex2.value = '';
	//受益人证件类型
	fm.BnfIDType2.value = '';
	//受益人证件号码
	fm.BnfIDNo2.value = '';
	//受益人出生日期
	fm.BnfBirthday2.value = '';
	//受益百分数
	fm.BnfBnfLot2.value = '';
	//受益顺序  
	fm.BnfBnfGrade2.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured2.value = '';
	 
//受益人信息3
	//受益人类型
	fm.BnfType3.value = '';
	//受益人姓名
	fm.BnfName3.value = '';
	//受益人性别
	fm.BnfSex3.value = '';
	//受益人证件类型
	fm.BnfIDType3.value = '';
	//受益人证件号码
	fm.BnfIDNo3.value = '';
	//受益人出生日期
	fm.BnfBirthday3.value = '';
	//受益百分数
	fm.BnfBnfLot3.value = '';
	//受益顺序  
	fm.BnfBnfGrade3.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured3.value = '';
	
//主险信息
	//主险代码 
	fm.Code.value = '';
	//份数 
	fm.Mult.value = '';
	//保费 
	fm.Prem.value = '';
	//保额 
	fm.Amnt.value = '';
	//保险年期类型  
	fm.InsuYearFlag.value = '';
	//保险年期 
	fm.InsuYear.value = '';
	//缴费年期类型 
	fm.PayEndYearFlag.value = '';
	//缴费年期 
	fm.PayEndYear.value = '';

//附加险信息1
	//主险代码 
	//fm.Code1.value = '';
	//份数 
	//fm.Mult1.value = '';
	//保费 
	//fm.Prem1.value = '';
	//保额 
	//fm.Amnt1.value = '';
	//保险年期类型  
	//fm.InsuYearFlag1.value = '';
	//保险年期 
	//fm.InsuYear1.value = '';
	//缴费年期类型 
	//fm.PayEndYearFlag1.value = '';
	//缴费年期 
	//fm.PayEndYear1.value = '';
		
//投资账户节点
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';
	
//投保信息

	//缴费方式 
	fm.PayIntv.value = ''; 
	//保单传送方式
	//fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccNo.value = '';
	//银行账号姓名
	fm.AccName.value = '';
	//红利领取方式
	fm.BonusGetMode.value = '';
	//首次追加保费
	//fm.FirstSuperaddAmt.value = '';
	//投资日期标志 
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '01'; 
	//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initInputBox() 
{
	try {
//交易信息
	//银行端交易日期
	fm.BankDate.value = '<%=strBankDate%>';
	//交易流水号
	fm.TransrNo.value = '<%=strTransrNo%>';
	//隐藏交易流水号
	fm.InputTransrNo.value = fm.TransrNo.value;
	//地区代码
	fm.ZoneNo.value = '10011';
	//网点代码
	fm.BrNo.value = '14800';
	//柜员代码
	fm.TellerNo.value = '00001';
	//投保日期 
	fm.PolApplyDate.value = '<%=strBankDate%>';
	//投保书号
	fm.ProposalContNo.value = '<%=strProposalContNo%>';
	//保单印刷号 
	fm.PrtNo.value = '';

    fm.ReqsrNo.value = '';	
//fm.OriginalProviderFormNumber.value = '';	
//fm.PolNumber.value = '';
	
	//document.getElementById("PrtNo").disabled=true;
	document.getElementById("ReqsrNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
   
//投保人信息
	//投保人姓名
	fm.ApplName.value = '<%=strAppntName%>';
	//投保人性别
	fm.ApplSex.value = '0';
	//投保人证件类型
	fm.ApplIDType.value = '110001';
	//投保人证件号码
	fm.ApplIDNo.value = '220523198509173414';
	//投保人生日
	fm.ApplBirthday.value = '1985-09-17';
	//投保人电子邮箱
	fm.ApplEmail.value = 'zhangsan@163.com';
	//投保人通讯地址	 
	fm.ApplAddress.value = '上海市杨浦区延吉七村'; 
	//投保人邮政编码
	fm.ApplZipCode.value = '666666';
	//投保人家庭电话
	fm.ApplPhone.value = '02223558550';
	//投保人移动电话
	fm.ApplMobile.value = '15821576966';
	//投保人与被报人的关系
	fm.ApplRelaToInsured.value = '4';
	//投保人职业代码
	fm.ApplJobCode.value = '01';
	//投保人证件有效期
	fm.ApplValidYear.value='2020-12-31';
	
//被保人信息
	//被保人姓名
	fm.InsuName.value = '<%=strInsuName%>';
	//被保人性别
	fm.InsuSex.value = '0';
	//被保人证件类型
	fm.InsuIDType.value = '110001';
	//被保人证件号码
	fm.InsuIDNo.value = '220523850917341';
	//被保人生日
	fm.InsuBirthday.value = '1985-09-17';
	//被保人电子邮箱
	fm.InsuEmail.value = 'lisi@163.com';
	//被保人通讯地址	
	fm.InsuAddress.value = '天津市和平区同安道'; 
	//被保人邮政编码
	fm.InsuZipCode.value = '222222';
	//被保人家庭电话 
	fm.InsuPhone.value = '02153558550';
	//被保人移动电话
	fm.InsuMobile.value = '13821576911';
	//健康告知
	fm.HealthFlag.value = '0';
	//被保人职业代码
	fm.InsuJobCode.value = '01';
	//被保人证件有效期
	fm.InsuValidYear.value='2020-12-31';
	
	
//受益人信息1 
	//受益人类型
	fm.BnfType1.value = '1';
	//受益人姓名
	fm.BnfName1.value = '王五';
	//受益人性别 
	fm.BnfSex1.value = '0';
	//受益人证件类型
	fm.BnfIDType1.value = '110001';
	//受益人证件号码
	fm.BnfIDNo1.value = '220523850917341';
	//受益人出生日期
	fm.BnfBirthday1.value = '1985-09-17';
	//受益百分数 
	fm.BnfBnfLot1.value = '100';
	//受益顺序  
	fm.BnfBnfGrade1.value = '1'; 
	//受益人与被报人的关系  
	fm.BnfRelationToInsured1.value = '2';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//受益人证件类型有效期
	fm.BnfValidYear1.value = '2020-09-16'; 
 
//受益人信息2
	//受益人类型
	fm.BnfType2.value = '';
	//受益人姓名
	fm.BnfName2.value = '';
	//受益人性别
	fm.BnfSex2.value = '';
	//受益人证件类型
	fm.BnfIDType2.value = '';
	//受益人证件号码
	fm.BnfIDNo2.value = '';
	//受益人出生日期
	fm.BnfBirthday2.value = '';
	//受益百分数
	fm.BnfBnfLot2.value = '';
	//受益顺序  
	fm.BnfBnfGrade2.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured2.value = '';
	 
//受益人信息3
	//受益人类型
	fm.BnfType3.value = '';
	//受益人姓名
	fm.BnfName3.value = '';
	//受益人性别
	fm.BnfSex3.value = '';
	//受益人证件类型
	fm.BnfIDType3.value = '';
	//受益人证件号码
	fm.BnfIDNo3.value = '';
	//受益人出生日期
	fm.BnfBirthday3.value = '';
	//受益百分数
	fm.BnfBnfLot3.value = '';
	//受益顺序  
	fm.BnfBnfGrade3.value = ''; 
	//受益人与被报人的关系 
	fm.BnfRelationToInsured3.value = '';
	
//主险信息
	//主险代码 
	fm.Code.value = '231201';
	//份数 
	fm.Mult.value = '30';
	 //保额 
	fm.Amnt.value = '';
	//保费
	fm.Prem.value = '30000.00';
	//保险年期类型
	fm.InsuYearFlag.value = '4';
	//保险年期 
	fm.InsuYear.value = '5';
	//缴费年期类型 
	fm.PayEndYearFlag.value = '0';
	//缴费年期 
	fm.PayEndYear.value = '1000';
	fm.PayIntv.value = '1';

//附加险信息1
	//主险代码 
	//fm.Code1.value = '';
	//份数 
	//fm.Mult1.value = '';
	//保费 
	//fm.Prem1.value = '';
	//保额 
	//fm.Amnt1.value = '';
	//保险年期类型 
	//fm.InsuYearFlag1.value = '';
	//保险年期 
	///fm.InsuYear1.value = '';
	//缴费年期类型 
	//fm.PayEndYearFlag1.value = '';
	//缴费年期 
	//fm.PayEndYear1.value = '';

//投资账户节点
	//fm.AccNo1.value = '';
	//fm.Rate1.value = '';
	//fm.AccNo2.value = '';
	//fm.Rate2.value = '';
	//fm.AccNo3.value = '';
	//fm.Rate3.value = '';

	
//投保信息

	//缴费方式
	fm.PayIntv.value = '1';
	//保单传送方式
	//fm.PolicyDeliveryMethod.value = '4'; 
	//银行账号
	fm.AccNo.value = '01234567890123';
	//银行账号姓名
	fm.AccName.value = '<%=strAppntName%>';
	//红利领取方式
	fm.BonusGetMode.value = '1';
	//首次追加保费
	//fm.FirstSuperaddAmt.value = '';
	//投资日期标志
	//fm.InvestDateInd.value = '';
	//fm.OccupationIndicator.value='N';
// 
	//fm.ip.value = '127.0.0.1';
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.portName.value = '<%=tPortName%>';
	fm.tranFlagCode.value = '01'; 
//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("LIANTestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}

</script>
