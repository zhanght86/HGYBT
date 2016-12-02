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
	String strHOAppFormNumber = "1003"+PubFun1.CreateMaxNo("PrtNo",8);
	String strProviderFormNumber = "2003"+PubFun1.CreateMaxNo("ContPrtNo",8);
	String strAccNo = "6225"+PubFun1.CreateMaxNo("AccNo",8);
	
	
	 
  	 String transNo = PubFun1.CreateMaxNo("TransNo",16);

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

function initInputBox() 
{
	try {
	//银行端交易日期
	fm.TransExeDate.value='<%=strTransExeDate%>';
	
	//交易流水号
	fm.TransNo.value = '<%=transNo%>';


	//地区代码 
	fm.ZoneNo.value='10011';
	
	//网点代码  
	fm.NodeNo.value='14800';
	
	//ip地址
	fm.ip.value='127.0.0.1';
	//端口
	fm.port.value='35000';
	
	fm.AccountNumber.value='01234567890123';
	fm.AccountForName.value='张三';
	
	fm.AppntName.value='张三';
	fm.AppGovtIDTC.value='0';
	fm.AppGovtID.value='220523198509173414';
	
	fm.InsureName.value='李四';
	fm.InsureGovtIDTC.value='0';
	fm.InsureGovtID.value='220523850917341';
	
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}


function initContConfirm()
{
try { 

document.getElementById("OriginTransNo").disabled=false;
	document.getElementById("PrintNo").disabled=false;
	document.getElementById("Password").disabled=false;
	document.getElementById("Premium0").disabled=false;
//交易信息
   fm.TranCode.value='1002';
	//银行端交易日期
	fm.TranDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransNo.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//柜员代码
	fm.TellerNo.value = '00001';
	//投保日期 
	fm.ApplyDate.value = '<%=strTransExeDate%>';
	//投保书号
	//fm.ApplyNo.value = '<%="C"+PubFun1.CreateMaxNo("PrtNo",9)%>';
	fm.Channel.value = '1';
	

	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->NewContConfirm 函数中发生异常:初始化界面错误!");
	}
}




	function initBox(){
	
	try {
//交易信息

   fm.TranCode.value='1001';
	//银行端交易日期
	fm.TranDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransNo.value = '<%=PubFun1.CreateMaxNo("TransNo",16)%>';
	//隐藏交易流水号
//	fm.InputTransRefGUID.value = fm.TransRefGUID.value;
	//地区代码 
	fm.ZoneNo.value='';
	//网点代码  
	fm.BrNo.value='';
	//柜员代码
	fm.TellerNo.value = '';
	//投保日期 
	fm.ApplyDate.value = '<%=strTransExeDate%>';
	//投保书号
	fm.ApplyNo.value = '<%="YBTTEST"+PubFun1.CreateMaxNo("PrtNo",9)%>';
	fm.Channel.value = '';

   
//投保人信息
	//姓名
	fm.Name.value = '';
	//性别
	fm.Sex.value = '';
	//证件类型
	fm.IDType.value = '';
	//证件号码
	fm.IDNo.value = '';
	//生日
	fm.Birthday.value = '';
	//身份证件生效日期
	fm.IDStartDate.value='';
	//身份证件有效期到期日
	fm.IDEndDate.value='';
	//婚姻状态  
	fm.Marriage.value='';
	//国籍代码
	fm.Nationality.value='';
	//住宅电话            
	fm.HomePhone.value='';
	//办公电话            
	fm.OfficePhone.value='';
	//移动电话            
	fm.MobilePhone.value='';
	//地址                
	fm.MailAddr.value='';
	//邮政编码            
	fm.MailZipCode.value='';
	//通讯地址            
	fm.HomeAddr.value='';
	//通讯邮政编码        
	fm.HomeZipCode.value='';
	//电子邮件            
	fm.Email.value='';
	//职业代码            
	fm.JobCode.value='';
	//年收入              
	fm.Income.value='';
	//公司名称            
	fm.WorkCompany.value='';
	//与被保人关系        
	fm.RelaToInsured.value='';
	
	
//被保人信息
			//姓名
			fm.BName.value = '';
			//性别
			fm.BSex.value = '';
			//证件类型
			fm.BIDType.value = '';
			//证件号码
			fm.BIDNo.value = '';
			//生日
			fm.BBirthday.value = '';
			//身份证件生效日期
			fm.BIDStartDate.value='';
			//身份证件有效期到期日
			fm.BIDEndDate.value='';
			//婚姻状态  
			fm.BMarriage.value='';
			//国籍代码
			fm.BNationality.value='';
			//住宅电话            
			fm.BHomePhone.value='';
			//办公电话            
			fm.BOfficePhone.value='';
			//移动电话            
			fm.BMobilePhone.value='';
			//地址                
			fm.BMailAddr.value='';
			//邮政编码            
			fm.BMailZipCode.value='';
			//通讯地址            
			fm.BHomeAddr.value='';
			//通讯邮政编码        
			fm.BHomeZipCode.value='';
			//电子邮件            
			fm.BEmail.value='';
			//职业代码            
			fm.BJobCode.value='';
			//年收入              
			fm.BIncome.value='';
			//公司名称            
			fm.BWorkCompany.value='';
	
//受益人信息1 
	//受益人类型
	fm.Type1.value='';
	//受益人姓名
	fm.Name1.value = '';
	//受益人性别 
	fm.Sex1.value = '';
	//受益人出生日期
	fm.Birthday1.value = '';
	//受益人证件类型
	fm.IDType1.value = '';
	//受益人证件号码
	fm.IDNo1.value = '';
	//身份证件生效日期
	fm.IDStartDate1.value='';
	//身份证件有效期到期日
	fm.IDEndDate1.value='';
	//国籍代码
	fm.Nationality1.value='';
	//地址                
	fm.HomeAddr1.value='';
	//职业代码            
	fm.JobCode1.value='';
	//移动电话            
	fm.MobilePhone1.value='';
	//受益人与被保人的关系  
	fm.RelationToInsured1.value='';	
	//受益百分数 
	fm.Percent1.value = '';
	//受益序号
	fm.Sequence1.value = ''; 	
	//受益顺序
	fm.Order1.value = ''; 	
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='N';  
 
	
//主险信息
	//主险代码 
	fm.Code.value = '';
	fm.CodeName.value='';
	//份数 
	fm.Unit.value = '';
	//保额 
	fm.InsuAmount.value = '';
	//保费 
	fm.Premium.value = '';
	//缴费年期类型 
	fm.PayEndYearFlag.value = '';
	//缴费年期 
	fm.PayEndYear.value = '';
	//保险年期类型  
	fm.InsuYearFlag.value = '';
	//保险年期 
	fm.InsuYear.value = '';
	


//投资账户节点
	
//投保信息

	fm.SellTeller.value='';
	fm.SellTellerName.value='';
	fm.SellCertID.value='';
	fm.CertIDValidDate.value='';
	fm.PayIntv.value='';
	fm.PayMethod.value='';
	fm.SendMethod.value='';
	fm.BonusPayMode.value='';
	fm.HealthFlag.value='';
	fm.OccupationFlag.value='';
	fm.BonusGetMode.value='';
	fm.AutoPayFlag.value='';
	fm.SubFlag.value='';
	fm.AutoRenewFlag.value='';
	fm.GetYearFlag.value='';
	fm.GetYear.value='';
	fm.NorskPrem.value='';
	fm.NorskAmt.value='';
	fm.DealType.value='';
	fm.ArbitrationInst.value='';
	fm.FirstRate.value='';
	fm.SureRate.value='';
	fm.InvestDateInd.value='';
	fm.FullBonusGetMode.value='';
	
	fm.GetAccBankName.value='';
	fm.GetAccName.value='';
	fm.GetAcc.value='';
	
	fm.PayAccBankName.value='';
	fm.PayAccName.value='';
	fm.PayAcc.value='';
	
	fm.SpecContent.value='';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
	}


</script>
