<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//程序名称：TestYBTContInit.jsp
	//程序功能：会计确认 
	//创建日期：2010-01-20 16:43:36
	//创建人  ：刘张海军
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strAccNo = "6225"+PubFun1.CreateMaxNo("AccNo",16);
	
	 
	String strTransRefGUID = "CGBTest"+pubfun.CreateMaxNo("ProTestTransNo",6);
	String strHOAppFormNumber = "CGBTest"+pubfun.CreateMaxNo("PrtNo",12);
	String strProviderFormNumber = "CGBTest"+pubfun.CreateMaxNo("ContNo",12);

  	 String tIp = "127.0.0.1";
  	 String tPort = "8001";
  	 String tZoneNo = "000006";
  	 String tNodeNo = "000048";

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
		alert("TestYBTContInit.jsp-->InitForm 函数中发生异常:初始化界面错误!");
	}
}



function initContConfirm()
{
try { 
//交易信息
	//银行端交易日期
	 
	fm.OriginalTransRefGUID.value = fm.TransRefGUID.value;
	
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//地区代码
	//fm.RegionCode.value = '00000';
	//网点代码
	//fm.Branch.value = '11111';
	//柜员代码
	fm.Teller.value = 'AXA_PRO_TEST';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	//保单印刷号
	fm.ProviderFormNumber.value = '<%=strProviderFormNumber%>';
   
	//投保人信息
	//投保人姓名
	fm.AppFullName.value = '';
	//投保人性别
	fm.AppGender.value = '';
	//投保人证件类型
	fm.AppGovtIDTC.value = '';
	//投保人证件号码
	fm.AppGovtID.value = '';
	//投保人生日
	fm.AppBirthDate.value = '';
	//投保人电子邮箱
	fm.AppAddrLine.value = '';
	//投保人通讯地址	
	fm.AppLine1.value = '';
	//投保人邮政编码
	fm.AppZip.value = '';
	//投保人家庭电话
	fm.AppDialNumber1.value = '';
	//投保人移动电话
	fm.AppDialNumber3.value = '';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '';
	
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '';
	//被保人性别
	fm.InsGender.value = ''; 
	fm.InsGenderh.value = '';
	//被保人证件类型
	fm.InsGovtIDTC.value = '';
	//被保人证件号码
	fm.InsGovtID.value = '';
	//被保人生日
	fm.InsBirthDate.value = '';
	//被保人电子邮箱
	fm.InsAddrLine.value = '';
	//被保人通讯地址	
	fm.InsLine1.value = ''; 
	//被保人邮政编码
	fm.InsZip.value = '';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '';
	//被保人移动电话
	fm.InsDialNumber3.value = '';
	//健康告知
	fm.HealthIndicator.value = '';
	
//受益人信息1
	//受益人姓名
	fm.BnfFullName1.value = '';
	//受益人性别
	fm.BnfGender1.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC1.value = '';
	//受益人证件号码
	fm.BnfGovtID1.value = '';
	//受益人出生日期
	fm.BnfBirthDate1.value = '';
	//受益百分数
	fm.InterestPercent1.value = '';
	//受益顺序  
	fm.Sequence1.value = ''; 
	//受益人与被报人的关系 
	fm.BnfToInsRelation1.value = '';
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='';  
 
//受益人信息2
	//受益人姓名
	fm.BnfFullName2.value = '';
	//受益人性别
	fm.BnfGender2.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC2.value = '';
	//受益人证件号码
	fm.BnfGovtID2.value = '';
	//受益人出生日期
	fm.BnfBirthDate2.value = '';
	//受益百分数
	fm.InterestPercent2.value = '';
	//受益顺序
	fm.Sequence2.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation2.value = '';
	 
//受益人信息3
	//受益人姓名
	fm.BnfFullName3.value = '';
	//受益人性别
	fm.BnfGender3.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC3.value = '';
	//受益人证件号码 
	fm.BnfGovtID3.value = '';
	//受益人出生日期
	fm.BnfBirthDate3.value = '';
	//受益百分数
	fm.InterestPercent3.value = '';
	//受益顺序 
	fm.Sequence3.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation3.value = '';
	
//主险信息
	//主险代码 
	fm.ProductCode.value = '';
	//份数 
	fm.IntialNumberOfUnits.value = '';
	//保费 
	fm.InitCovAmt.value = '';
	//保额 
	fm.ModalPremAmt.value = '';
	//保险年期类型  
	fm.DurationMode.value = '';
	//保险年期 
	fm.Duration.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '';
	//缴费年期 
	fm.PaymentDuration.value = '';

//附加险信息1
	//主险代码 
	fm.ProductCode1.value = '';
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保费 
	fm.InitCovAmt1.value = '';
	//保额 
	fm.ModalPremAmt1.value = '';
	//保险年期类型 
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';
		
//投资账户节点
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	
//投保信息

	//缴费方式 
	fm.PaymentMode.value = ''; 
	//保单传送方式
	fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccountNumber.value = '';
	//首次追加保费
	fm.FirstSuperaddAmt.value = '';
	//投资日期标志 
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '127.0.0.1';
	fm.port.value = '8001';
	//fm.ip.value = '10.26.5.31';
	fm.tranFlagCode.value = '1113'; 
	//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initContConcel()
{
try { 
//交易信息
	//银行端交易日期
	
	fm.OriginalTransRefGUID.value =  fm.TransRefGUID.value;
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//地区代码
	fm.RegionCode.value = '<%=tZoneNo%>';
	//网点代码
	fm.Branch.value = '<%=tNodeNo%>';
	//柜员代码
	fm.Teller.value = 'AXA_PRO_TEST';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	//保单印刷号
	fm.ProviderFormNumber.value = '';
   
	//投保人信息
	//投保人姓名
	fm.AppFullName.value = '';
	//投保人性别
	fm.AppGender.value = '';
	//投保人证件类型
	fm.AppGovtIDTC.value = '';
	//投保人证件号码
	fm.AppGovtID.value = '';
	//投保人生日
	fm.AppBirthDate.value = '';
	//投保人电子邮箱
	fm.AppAddrLine.value = '';
	//投保人通讯地址	
	fm.AppLine1.value = '';
	//投保人邮政编码
	fm.AppZip.value = '';
	//投保人家庭电话
	fm.AppDialNumber1.value = '';
	//投保人移动电话
	fm.AppDialNumber3.value = '';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '';
	
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '';
	//被保人性别
	fm.InsGender.value = ''; 
	fm.InsGenderh.value = '';
	//被保人证件类型
	fm.InsGovtIDTC.value = '';
	//被保人证件号码
	fm.InsGovtID.value = '';
	//被保人生日
	fm.InsBirthDate.value = '';
	//被保人电子邮箱
	fm.InsAddrLine.value = '';
	//被保人通讯地址	
	fm.InsLine1.value = ''; 
	//被保人邮政编码
	fm.InsZip.value = '';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '';
	//被保人移动电话
	fm.InsDialNumber3.value = '';
	//健康告知
	fm.HealthIndicator.value = '';
	
//受益人信息1
	//受益人姓名
	fm.BnfFullName1.value = '';
	//受益人性别
	fm.BnfGender1.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC1.value = '';
	//受益人证件号码
	fm.BnfGovtID1.value = '';
	//受益人出生日期
	fm.BnfBirthDate1.value = '';
	//受益百分数
	fm.InterestPercent1.value = '';
	//受益顺序  
	fm.Sequence1.value = ''; 
	//受益人与被报人的关系 
	fm.BnfToInsRelation1.value = '';
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='';  
 
//受益人信息2
	//受益人姓名
	fm.BnfFullName2.value = '';
	//受益人性别
	fm.BnfGender2.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC2.value = '';
	//受益人证件号码
	fm.BnfGovtID2.value = '';
	//受益人出生日期
	fm.BnfBirthDate2.value = '';
	//受益百分数
	fm.InterestPercent2.value = '';
	//受益顺序
	fm.Sequence2.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation2.value = '';
	 
//受益人信息3
	//受益人姓名
	fm.BnfFullName3.value = '';
	//受益人性别
	fm.BnfGender3.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC3.value = '';
	//受益人证件号码 
	fm.BnfGovtID3.value = '';
	//受益人出生日期
	fm.BnfBirthDate3.value = '';
	//受益百分数
	fm.InterestPercent3.value = '';
	//受益顺序 
	fm.Sequence3.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation3.value = '';
	
//主险信息
	//主险代码 
	fm.ProductCode.value = '';
	//份数 
	fm.IntialNumberOfUnits.value = '';
	//保费 
	fm.InitCovAmt.value = '';
	//保额 
	fm.ModalPremAmt.value = '';
	//保险年期类型  
	fm.DurationMode.value = '';
	//保险年期 
	fm.Duration.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '';
	//缴费年期 
	fm.PaymentDuration.value = '';

//附加险信息1
	//主险代码 
	fm.ProductCode1.value = '';
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保费 
	fm.InitCovAmt1.value = '';
	//保额 
	fm.ModalPremAmt1.value = '';
	//保险年期类型 
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';
		
//投资账户节点
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	
//投保信息

	//缴费方式 
	fm.PaymentMode.value = ''; 
	//保单传送方式
	fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccountNumber.value = '';
	//首次追加保费
	fm.FirstSuperaddAmt.value = '';
	//投资日期标志 
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value = '';	
	
//
	//fm.ip.value = '127.0.0.1';
	fm.ip.value = '127.0.0.1';
	fm.port.value = '8001';
	fm.tranFlagCode.value = '1115'; 
	//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initBox()
{
try { 
//交易信息
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//地区代码
	fm.RegionCode.value = '';
	//网点代码
	fm.Branch.value = '';
	//柜员代码
	fm.Teller.value = 'AXA_PRO_TEST';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//保单印刷号
	
	fm.ProviderFormNumber.value = '';
   
//投保人信息
	//投保人姓名
	fm.AppFullName.value = '';
	//投保人性别
	fm.AppGender.value = '';
	//投保人证件类型
	fm.AppGovtIDTC.value = '';
	//投保人证件号码
	fm.AppGovtID.value = '';
	//投保人生日
	fm.AppBirthDate.value = '';
	//投保人电子邮箱
	fm.AppAddrLine.value = '';
	//投保人通讯地址	
	fm.AppLine1.value = '';
	//投保人邮政编码
	fm.AppZip.value = '';
	//投保人家庭电话
	fm.AppDialNumber1.value = '';
	//投保人移动电话
	fm.AppDialNumber3.value = '';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '';
	
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '';
	//被保人性别
	fm.InsGender.value = ''; 
	fm.InsGenderh.value = '';
	//被保人证件类型
	fm.InsGovtIDTC.value = '';
	//被保人证件号码
	fm.InsGovtID.value = '';
	//被保人生日
	fm.InsBirthDate.value = '';
	//被保人电子邮箱
	fm.InsAddrLine.value = '';
	//被保人通讯地址	
	fm.InsLine1.value = ''; 
	//被保人邮政编码
	fm.InsZip.value = '';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '';
	//被保人移动电话
	fm.InsDialNumber3.value = '';
	//健康告知
	fm.HealthIndicator.value = '';
	
//受益人信息1
	//受益人姓名
	fm.BnfFullName1.value = '';
	//受益人性别
	fm.BnfGender1.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC1.value = '';
	//受益人证件号码
	fm.BnfGovtID1.value = '';
	//受益人出生日期
	fm.BnfBirthDate1.value = '';
	//受益百分数
	fm.InterestPercent1.value = '';
	//受益顺序  
	fm.Sequence1.value = ''; 
	//受益人与被报人的关系 
	fm.BnfToInsRelation1.value = '';
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='';  
 
//受益人信息2
	//受益人姓名
	fm.BnfFullName2.value = '';
	//受益人性别
	fm.BnfGender2.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC2.value = '';
	//受益人证件号码
	fm.BnfGovtID2.value = '';
	//受益人出生日期
	fm.BnfBirthDate2.value = '';
	//受益百分数
	fm.InterestPercent2.value = '';
	//受益顺序
	fm.Sequence2.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation2.value = '';
	 
//受益人信息3
	//受益人姓名
	fm.BnfFullName3.value = '';
	//受益人性别
	fm.BnfGender3.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC3.value = '';
	//受益人证件号码 
	fm.BnfGovtID3.value = '';
	//受益人出生日期
	fm.BnfBirthDate3.value = '';
	//受益百分数
	fm.InterestPercent3.value = '';
	//受益顺序 
	fm.Sequence3.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation3.value = '';
	
//主险信息
	//主险代码 
	fm.ProductCode.value = '';
	//份数 
	fm.IntialNumberOfUnits.value = '';
	//保费 
	fm.InitCovAmt.value = '';
	//保额 
	fm.ModalPremAmt.value = '';
	//保险年期类型  
	fm.DurationMode.value = '';
	//保险年期 
	fm.Duration.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '';
	//缴费年期 
	fm.PaymentDuration.value = '';

//附加险信息1
	//主险代码 
	fm.ProductCode1.value = '';
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保费 
	fm.InitCovAmt1.value = '';
	//保额 
	fm.ModalPremAmt1.value = '';
	//保险年期类型 
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';
		
//投资账户节点
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	
//投保信息

	//缴费方式 
	fm.PaymentMode.value = ''; 
	//保单传送方式
	fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccountNumber.value = '';
	//首次追加保费
	fm.FirstSuperaddAmt.value = '';
	//投资日期标志 
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '127.0.0.1';
	//fm.ip.value = '<%=tIp%>';
	fm.port.value = '8001';
	fm.tranFlagCode.value = '1013'; 
	//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initInputBox() 
{
	try {
//交易信息
fm.BranchName.value = "广发银行-北京亚运村支行";
	fm.RegionCodeName.value = "北京";
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//隐藏交易流水号
	fm.InputTransRefGUID.value = fm.TransRefGUID.value;
	//地区代码 
	fm.RegionCode.value = '<%=tZoneNo%>';
	//网点代码  
	fm.Branch.value = '<%=tNodeNo%>';
	//柜员代码
	fm.Teller.value = 'AXA_PRO_TEST';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//保单印刷号 
	fm.ProviderFormNumber.value = '';

fm.OriginalTransRefGUID.value = '';	
//fm.OriginalProviderFormNumber.value = '';	
//fm.PolNumber.value = '';
	
	document.getElementById("PrtNo").disabled=true;
	document.getElementById("OldTransNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
   
//投保人信息
	//投保人姓名
	fm.AppFullName.value = '投保人2';
	//投保人性别
	fm.AppGender.value = '1';
	//投保人证件类型
	fm.AppGovtIDTC.value = '0';
	//投保人证件号码
	fm.AppGovtID.value = '110101198001010010';
	//投保人生日
	fm.AppBirthDate.value = '1980-01-01';
	//投保人电子邮箱
	fm.AppAddrLine.value = 'zhangsan@163.com';
	//投保人通讯地址	 
	fm.AppLine1.value = '上海市杨浦区延吉七村'; 
	//投保人邮政编码
	fm.AppZip.value = '666666';
	//投保人家庭电话
	fm.AppDialNumber1.value = '02223558550';
	//投保人移动电话
	fm.AppDialNumber3.value = '15821576966';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '2';
	
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '被保人2';
	//被保人性别
	fm.InsGender.value = '1';
	//被保人证件类型
	fm.InsGovtIDTC.value = '0';
	//被保人证件号码
	fm.InsGovtID.value = '110101198001010037';
	//被保人生日
	fm.InsBirthDate.value = '1980-01-01';
	//被保人电子邮箱
	fm.InsAddrLine.value = 'lisi@163.com';
	//被保人通讯地址	
	fm.InsLine1.value = '天津市和平区同安道'; 
	//被保人邮政编码
	fm.InsZip.value = '222222';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '02153558550';
	//被保人移动电话
	fm.InsDialNumber3.value = '13821576911';
	//健康告知
	fm.HealthIndicator.value = 'N';
	
//受益人信息1 
	//受益人姓名
	fm.BnfFullName1.value = '王五';
	//受益人性别 
	fm.BnfGender1.value = '1';
	//受益人证件类型
	fm.BnfGovtIDTC1.value = '0';
	//受益人证件号码
	fm.BnfGovtID1.value = '220523850917341';
	//受益人出生日期
	fm.BnfBirthDate1.value = '1985-09-17';
	//受益百分数 
	fm.InterestPercent1.value = '100';
	//受益顺序  
	fm.Sequence1.value = '1'; 
	//受益人与被报人的关系  
	fm.BnfToInsRelation1.value = '2';
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='N';  
 
//受益人信息2
	//受益人姓名
	fm.BnfFullName2.value = '';
	//受益人性别
	fm.BnfGender2.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC2.value = '';
	//受益人证件号码
	fm.BnfGovtID2.value = '';
	//受益人出生日期
	fm.BnfBirthDate2.value = '';
	//受益百分数
	fm.InterestPercent2.value = '';
	//受益顺序
	fm.Sequence2.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation2.value = '';
	 
//受益人信息3
	//受益人姓名
	fm.BnfFullName3.value = '';
	//受益人性别
	fm.BnfGender3.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC3.value = '';
	//受益人证件号码 
	fm.BnfGovtID3.value = '';
	//受益人出生日期
	fm.BnfBirthDate3.value = '';
	//受益百分数
	fm.InterestPercent3.value = '';
	//受益顺序 
	fm.Sequence3.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation3.value = '';
	
//主险信息
	//主险代码 
	fm.ProductCode.value = '003';
	//份数 
	fm.IntialNumberOfUnits.value = '1';
	//保额 
	fm.InitCovAmt.value = '1000000';
	//保费 
	fm.ModalPremAmt.value = '298810';
	//保险年期类型  
	fm.DurationMode.value = '1';
	//保险年期 
	fm.Duration.value = '100';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '2';
	//缴费年期 
	fm.PaymentDuration.value = '10';

//附加险信息1
	//主险代码 
	fm.ProductCode1.value = '';
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保费 
	fm.InitCovAmt1.value = '';
	//保额 
	fm.ModalPremAmt1.value = '';
	//保险年期类型 
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';

//投资账户节点
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';

	
//投保信息

	//缴费方式
	fm.PaymentMode.value = '1';
	//保单传送方式
	fm.PolicyDeliveryMethod.value = '4'; 
	//银行账号
	fm.AccountNumber.value = '<%=strAccNo%>';
	//首次追加保费
	fm.FirstSuperaddAmt.value = '';
	//投资日期标志
	fm.InvestDateInd.value = '';
	fm.OccupationIndicator.value='N';
// 
	fm.ip.value = '127.0.0.1';
	//fm.ip.value = '<%=tIp%>';
	fm.port.value = '8001';
	fm.tranFlagCode.value = '1013'; 
//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}

</script>
