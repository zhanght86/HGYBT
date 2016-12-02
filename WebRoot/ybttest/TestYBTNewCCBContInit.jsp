<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<% 
	//程序名称：TestYBTContInit.jsp
	//程序功能：
	//创建日期：2010-01-20 16:43:36
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
	String strHOAppFormNumber = "2104121"+pubfun.CreateMaxNo("PrtNo",8);
	String InsNumber=pubfun.CreateMaxNo("InsNumber",3);
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
		alert("TestYBTNewCCBContInit.jsp-->InitForm 函数中发生异常:初始化界面错误!");
	}
}



function initInputBox() 
{
	try {
	//交易信息
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//地区代码 
	fm.RegionCode.value = '3306';
	//网点代码  
	fm.Branch.value = '13535';
	//柜员代码
	fm.Teller.value = '3104417';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//原交易流水号
	fm.InputTransrNo.value = fm.TransRefGUID.value;
	
	fm.ReqsrNo.value ='';
	//保单印刷号
	fm.ProviderFormNumber.value = '';
	//交易渠道
	fm.TransMode.value=  '00';
	//保险分公司代码
	fm.BranthCmp.value='310IF10016';
	//销售人员工号
	fm.BkRckrNo.value=  '1008611';
	//销售人员姓名
	fm.BkSaleName.value='朴槿惠';
	//销售人员资格证书编号
	fm.BkSaleCertNo.value='1008610010';
	
	fm.Cst_Rsk_Tlrnc_Cpy_Cd.value = '01';
	fm.Rsk_Evlt_AvlDt.value = '20160707';
	fm.Bdgt_Amt.value = '10000.00';
   
//投保人信息
	//投保人姓名
	fm.AppFullName.value = '投保人8';
	//投保人性别
	fm.AppGender.value = '1';
	//投保人证件类型
	fm.AppGovtIDTC.value = '1022';
	//投保人证件号码
	fm.AppGovtID.value = '3103148222222';
	//投保人生日
	fm.AppBirthDate.value = '1965-09-17';
	//投保人证件生效日期
	fm.AppGovtEfcDate.value = '2005-09-17';
	//投保人证件失效日期
	fm.AppGovtTermDate.value = '2020-09-17';
	//投保人电子邮箱
	fm.AppAddrLine.value = 'zhangsan@163.com';
	//投保人通讯地址	 
	fm.AppLine1.value = '上海市杨浦区延吉七村'; 
	//投保人年收入
	fm.PbInCome.value = '15000000';
	//投保人家庭年收入
	fm.PbHomeInCome.value = '25000000';	
	//投保人邮政编码
	fm.AppZip.value = '333333';
	//投保人固定电话
	fm.AppDialNumber1.value = '02123558550';
	//投保人移动电话
	fm.AppDialNumber3.value = '15821576966';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '0133015';
	//经办人姓名
 	fm.RspbName.value = '毛泽西';
	
	fm.AppCountry.value='0156';
	fm.ApplJobCode.value='3010101';
	
	fm.Plchd_Prov_Cd.value='340000';
	fm.Plchd_City_Cd.value='340100';
	fm.Plchd_CntyAndDstc_Cd.value='340103';
	fm.Plchd_Dtl_Adr_Cntnt.value='长江南路305号';
	
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '被保人'+<%=InsNumber%>;
	//被保人姓名姓名全称
	fm.Rcgn_CPA_FullNm.value = 'Bei Baoren'+<%=InsNumber%>;
	//被保人性别
	fm.InsGender.value = '1';
	//被保人证件类型
	fm.InsGovtIDTC.value = '1022';
	//被保人证件号码
	fm.InsGovtID.value = '314314232323238';
	//被保人生日
	fm.InsBirthDate.value = '1985-09-17';
	//被保人证件生效日期
	fm.InsGovtEfcDate.value = '2005-09-17';
	//被保人证件失效日期
	fm.InsGovtExpDate.value = '2020-09-17';
	//被保人电子邮箱
	fm.InsAddrLine.value = 'lisi@163.com';
	//被保人通讯地址	
	fm.InsLine1.value = '天津市和平区同安道'; 
	//被保人邮政编码
	fm.InsZip.value = '555555';
	//被保人固定电话 
	fm.InsDialNumber1.value = '02153558550';
	//被保人移动电话
	fm.InsDialNumber3.value = '13821576911';
	//被保人前往目的地个数
	fm.DesNum.value = '1';
	//被保人移动电话
	fm.Destinations.value = '青岛';
	//被保人年收入
	fm.InsInCome.value = '12000000';
	//健康告知
	fm.HealthIndicator.value = '0';
	fm.InsCountry.value='0156';
	fm.InsuJobCode.value='3010101';
	
	fm.Rcgn_Prov_Cd.value='340000';
	fm.Rcgn_City_Cd.value='340100';
	fm.Rcgn_CntyAndDstc_Cd.value='340103';
	fm.Rcgn_Dtl_Adr_Cntnt.value='长江南路305号';
	
	//代理保险受益人类型代码
	fm.AgIns_Benf_TpCd.value = '1';
//受益人信息1 
	//受益人姓名
	fm.BnfFullName1.value = '王五';
	//受益人性别 
	fm.BnfGender1.value = '1';
	//受益人证件类型
	fm.BnfGovtIDTC1.value = '1022';
	//受益人证件号码
	fm.BnfGovtID1.value = '3103168';
	//受益人证件生效日期 
	fm.bnfappbgndate1.value = '1990-01-01';
	//受益人证件失效日期 
	fm.bnfappvlddate1.value = '2020-01-01';
	//受益人出生日期
	fm.BnfBirthDate1.value = '1985-09-17';
	//受益人通讯地址
	fm.BnfAdress1.value = '天津市和平区同安道';
	//受益百分数 
	fm.InterestPercent1.value = '100';
	//受益顺序  
	fm.Sequence1.value = '1'; 
	//受益人与被报人的关系  
	fm.BnfToInsRelation1.value = '4';
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='0';  
	//受益人类型
	fm.BenefiType1.value='1';
 
//受益人信息2
	//受益人姓名
	fm.BnfFullName2.value = '';
	//受益人性别
	fm.BnfGender2.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC2.value = '';
	//受益人证件号码
	fm.BnfGovtID2.value = '';
	//受益人证件生效日期 
	fm.bnfappbgndate2.value = '';
	//受益人证件失效日期 
	fm.bnfappvlddate2.value = '';
	//受益人出生日期
	fm.BnfBirthDate2.value = '';
	//受益人通讯地址
	fm.BnfAdress2.value = '';
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
	//受益人证件生效日期 
	fm.bnfappbgndate3.value = '';
	//受益人证件失效日期 
	fm.bnfappvlddate3.value = '';
	//受益人通讯地址
	fm.BnfAdress3.value = '';
	//受益人出生日期
	fm.BnfBirthDate3.value = '';
	
	//受益百分数
	fm.InterestPercent3.value = '';
	//受益顺序 
	fm.Sequence3.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation3.value = '';
	
	//投保方案信息 
	fm.Ins_Scm_Inf.value = '无投保方案信息';
	//代理保险套餐编号 
	fm.AgIns_Pkg_ID.value = 'C2';
	//可选部分身故金额 
	fm.Opt_Part_DieIns_Amt.value = '1000000';
	//首次额外追加保费 
	fm.FTm_Extr_Adl_InsPrem.value = '80000';
	//紧急联系人 
	fm.Emgr_CtcPsn.value = '霍建华';
	//紧急联系人 
	fm.Emgr_Ctc_Tel.value = '13812345678';
	//紧急联系人与被报人的关系
	fm.AppToInsRelation.value = '28';

	//代理保险套餐编号 
	fm.AgIns_Pkg_ID.value = '';
//主险信息
	//主险代码 
	fm.ProductCode.value = '241201';
	//主险名称
	fm.RiskCodeName.value = '中韩创赢财富两全保险（万能型）A款';
	//份数 
	fm.IntialNumberOfUnits.value = '1';
	//保额 
	fm.InitCovAmt.value = '30000';
	//保费 
	fm.ModalPremAmt.value = '30000';
	//保险年期类型  
	fm.DurationMode.value = '2';
	//保险年期 
	fm.Duration.value = '10';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '5';
	//缴费年期 
	fm.PaymentDuration.value = '1';
	
	fm.AutoPayFlag.value = '0';

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
	
		
//附加险信息2
	//主险代码 
	fm.ProductCode2.value = '';
	//份数 
	fm.IntialNumberOfUnits2.value = '';
	//保费 
	fm.InitCovAmt2.value = '';
	//保额  
	fm.ModalPremAmt2.value = '';
	//保险年期类型 
	fm.DurationMode2.value = '';
	//保险年期 
	fm.Duration2.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode2.value = '';
	//缴费年期 
	fm.PaymentDuration2.value = '';
	
//投资账户节点
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	fm.AccCode4.value = '';
	fm.AllocPercent4.value = '';
	fm.AccCode5.value = '';
	fm.AllocPercent5.value = '';
	
	
	
//投保信息

	//缴费方式 
	fm.PaymentMode.value = '02';
	//付费方式
	fm.PaymentMethod.value = '3';     
	//领取方式  
	fm.BenefitMode.value = '3'; 
	//红利领取方式  
	fm.DivType.value = '2';
	//保单传送方式
	fm.PolicyDeliveryMethod.value = '1'; 
	//银行账号  
	fm.AccountNumber.value = '01234567890123';
	fm.AcctHolderName.value = fm.AppFullName.value;
	//特别约定 
	fm.SpecialClause.value = 'SpecialClause';	
	fm.OccupationIndicator.value='N';
	//投资日期标志
	fm.InvestDateInd.value = '4';
	//首次追加保费
	fm.FirstSuperaddAmt.value = '';
	
// 
	fm.ip.value = '127.0.0.1';
	fm.port.value = '39871';
	fm.tranFlagCode.value = 'P53819113'; 
	}
	catch(re)
	{
		alert("TestYBTNewCCBContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}


function initBox()
{
try { 
//交易信息
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//地区代码 
	fm.RegionCode.value = '310999';
	//网点代码  
	fm.Branch.value = '322989036';
	//柜员代码
	fm.Teller.value = '3104417';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//保单印刷号
	fm.ProviderFormNumber.value = '';
	//销售人员工号
	fm.BkRckrNo.value=  '';
	//销售人员姓名
	fm.BkSaleName.value='';
	//销售人员资格证书编号
	fm.BkSaleName.value='';
   
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
	//投保人固定电话
	fm.AppDialNumber1.value = '';
	//投保人移动电话
	fm.AppDialNumber3.value = '';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '';
	//经办人姓名
	fm.RspbName.value = '';
	//被保人前往目的地个数
	fm.DesNum.value = '';
	//被保人移动电话
	fm.Destinations.value = '';
	//投保人年收入
	fm.PbInCome.value = '';
	//投保人家庭年收入
	fm.PbHomeInCome.value = '';
	
	fm.Plchd_Prov_Cd.value='';
	fm.Plchd_City_Cd.value='';
	fm.Plchd_CntyAndDstc_Cd.value='';
	fm.Plchd_Dtl_Adr_Cntnt.value='';
	
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '';
	//被保人姓名
	fm.Rcgn_CPA_FullNm.value = '';
	//被保人性别
	fm.InsGender.value = ''; 
//	fm.InsGenderh.value = '';
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
	//被保人固定电话 
	fm.InsDialNumber1.value = '';
	//被保人移动电话
	fm.InsDialNumber3.value = '';
	//健康告知
	fm.HealthIndicator.value = '';
	
	fm.Plchd_Prov_Cd.value='340000';
	fm.Plchd_City_Cd.value='340100';
	fm.Plchd_CntyAndDstc_Cd.value='340103';
	fm.Plchd_Dtl_Adr_Cntnt.value='长江南路305号';

	//代理保险受益人类型代码
	fm.AgIns_Benf_TpCd.value = '';
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

	//投保方案信息 
	fm.Ins_Scm_Inf.value = '';
	//代理保险套餐编号 
	fm.AgIns_Pkg_ID.value = '';
	//可选部分身故金额 
	fm.Opt_Part_DieIns_Amt.value = '';
	//首次额外追加保费 
	fm.FTm_Extr_Adl_InsPrem.value = '';
	//紧急联系人 
	fm.Emgr_CtcPsn.value = '';
	//紧急联系人 
	fm.Emgr_Ctc_Tel.value = '';
	
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
		
//附加险信息2
	//主险代码 
	fm.ProductCode2.value = '';
	//份数 
	fm.IntialNumberOfUnits2.value = '';
	//保费 
	fm.InitCovAmt2.value = '';
	//保额  
	fm.ModalPremAmt2.value = '';
	//保险年期类型 
	fm.DurationMode2.value = '';
	//保险年期 
	fm.Duration2.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode2.value = '';
	//缴费年期 
	fm.PaymentDuration2.value = '';
	
	fm.AutoPayFlag.value = '0';
//投资账户节点
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.AccCode2.value = '';
	fm.AllocPercent2.value = '';
	fm.AccCode3.value = '';
	fm.AllocPercent3.value = '';
	fm.AccCode4.value = '';
	fm.AllocPercent4.value = '';
	fm.AccCode5.value = '';
	fm.AllocPercent5.value = '';
	
//投保信息

	//缴费方式 
	fm.PaymentMode.value = '';
	//缴费形式(频次)
	fm.PaymentMethod.value = '';     
	//领取方式  
	fm.BenefitMode.value = ''; 
	//红利领取方式  
	fm.DivType.value = '';
	//保单传送方式
	fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccountNumber.value = '';
	//帐户姓名
	fm.AcctHolderName.value = '';
	//特别约定 
	fm.SpecialClause.value = '';
	fm.OccupationIndicator.value = '';	
	//投资日期标志 
	fm.InvestDateInd.value = '';
	//首次追加保费
	fm.FirstSuperaddAmt.value = '';
	
//
	fm.ip.value = '127.0.0.1';
	fm.port.value = '39871'; 
	fm.tranFlagCode.value = 'P53819113'; 
	}
	catch(re)
	{
		alert("TestYBTCNewCBContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}
	function initContConfirm()
{
try { 
//交易信息
	fm.ReqsrNo.value = fm.InputTransrNo.value;
	//银行端交易日期
	fm.TransRefGUID.value = '<%=pubfun.CreateMaxNo("TransNo",16)%>';
	}
	catch(re)
	{
		alert("TestYBTNewCCBContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}



</script>
