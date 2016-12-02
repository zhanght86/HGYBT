<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();

	
	Element mTestUI = 
  		 MidplatConf.newInstance().getConf().getRootElement().getChild("ABCTestUI");
  	 String tIp = mTestUI.getAttributeValue("ip");
  	 String tPort = mTestUI.getAttributeValue("port");
  	 
  	 String transrNo = "YBTTEST"+PubFun1.CreateMaxNo("TransrNo",9);
  	//String effectiveDate = DateUtilZR.nextDay(DateUtil.getCur10Date());

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
	
	//document.getElementById("OriginTransNo").disabled=true;
	//document.getElementById("PrintNo").disabled=true;
	//document.getElementById("Password").disabled=true;
	//document.getElementById("Premium0").disabled=true;
	
	//-------交易信息
    fm.FunctionFlag.value='01';
	//银行端交易日期
	fm.BankDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransrNo.value = '<%=transrNo%>';
	//隐藏交易流水号
	fm.NewTransNo.value = fm.TransrNo.value;
	//地区代码 
	fm.ZoneNo.value='0021';
	//fm.ZoneNoName.value = '上海';
	//网点代码  
	fm.BrNo.value='ABCOT01';
	//fm.BrNoName.value = '中国农业银行-上海支行';
	//柜员代码
	fm.TellerNo.value = 'ABCCT01';
	
	
	//--------投保信息
	//投保单号
	fm.ProposalContNo.value = '<%="C"+PubFun1.CreateMaxNo("APPNo",9)%>';
	//投保日期 
	fm.PolApplyDate.value = '<%=strTransExeDate%>';
	//代收付账户姓名
	fm.AccName.value='张三';
	//代收付银行名称
	//fm.AccBankNAME.value='01';
	//fm.AccBankNAMEName.value='中国人民银行';
	//代收付银行账户
	//fm.BankAccNo.value='6227001700130252734';
	//交费形式
	fm.PayMode.value='0';
	//交费帐号
	fm.AccNo.value='6227001700130252734';
	//特别约定
	fm.SpecContent.value='';
	//保单密码
	//fm.Password.value='Password';
	//保单印刷号码
	fm.PrtNo.value='P12345678';

   
//投保人信息
	//姓名
	fm.Name.value = '张三';
	//性别
	fm.Sex.value = '0';
	//证件类型
	fm.IDType.value = '110001';
	fm.IDTypeName.value = '居民身份证';
	//证件号码
	fm.IDNo.value = '110101198001010013';
	//生日
	fm.Birthday.value = '1980-01-01';
	//联系电话           
	fm.Phone.value='021-2121211';
	//手机号        
	fm.Mobile.value='13636440432';
	//通讯地址            
	fm.Address.value='上海市安澜路8号6楼';
	//通讯邮政编码        
	fm.ZipCode.value='121212';
	//与被保人关系        
	fm.RelaToInsured.value='3';
	fm.RelaToInsuredName.value='妻子';
	//国籍代码
	fm.County.value='CHN';	
	//证件有效期          
	fm.ValidYear.value='20';
	//电子邮件            
	fm.Email.value='915543374@qq.com';
	//职业代码            
	fm.JobCode.value='01';
	fm.JobCodeName.value='国家机关、党群组织、企业、事业单位人员';
	
	
//被保人信息
		fm.BName.value = '测试被保人';
		//性别
		fm.BSex.value = '1';
		//证件类型
		fm.BIDType.value = '110001';
		fm.BIDTypeName.value = '居民身份证';
		//证件号码
		fm.BIDNo.value = '110101198903030029';
		//生日
		fm.BBirthday.value = '1989-03-03';
		//联系电话           
		fm.BPhone.value='021-2121211';
		//手机号        
		fm.BMobile.value='13636440432';
		//通讯地址            
		fm.BAddress.value='上海市陆家浜路90号';
		//通讯邮政编码        
		fm.BZipCode.value='121212';
		//国籍代码
		fm.BCounty.value='CHN';	
		//证件有效期          
		fm.BValidYear.value='20';
		//电子邮件            
		fm.BEmail.value='121212121@qq.com';
		//职业代码            
		fm.BJobCode.value='01';
		fm.BJobCodeName.value='国家机关、党群组织、企业、事业单位人员';
			
	
//受益人信息1 
		//受益人类型
		fm.Type1.value='1';
		//受益人姓名
		fm.Name1.value = '测试受益人';
		//受益人性别 
		fm.Sex1.value = '0';
		//受益人出生日期
		fm.Birthday1.value = '2010-09-17';
		//受益人证件类型
		fm.IDType1.value = '110001';
		fm.IDTypeName1.value = '居民身份证';
		//受益人证件号码
		fm.IDNo1.value = '110101201009170013';
		//受益人与被保人的关系  
		fm.RelationToInsured1.value='04';	
		fm.RelationToInsuredName1.value='父亲';
		//受益顺序
		fm.BnfGrade1.value = '1'; 	
		//受益比例 
		fm.BnfLot1.value = '100';
		//通讯地址
		fm.Address1.value='上海市龙阳路2277号永达国际大厦6楼';
		//受益人是否为法定标志 
		fm.BeneficiaryIndicator.value='N'; 
		
	
//主险信息
	//主险代码 
	fm.Code.value = '241201';
	//fm.RiskPassword.value='Password';
	//保费 
	fm.Prem.value = '100000';
	//保额 
	fm.Amnt.value = '';
	//fm.Rate.value='30';
	fm.CValiDate.value='<%=strTransExeDate%>';
	//份数 
	fm.Mult.value = '';
	//交费方式
	fm.PayIntv.value='5';
	//保险期间
	fm.Years.value='10';
	//缴费年期类型 
	fm.PayEndYearFlag.value = '4';
	//缴费年期 
	fm.PayEndYear.value = '10';
	//保险年期类型  
	fm.InsuYearFlag.value = '4';
	//保险年期 
	fm.InsuYear.value = '10';
	//健康告知标识
	fm.HealthFlag.value='0';
	//是否同意现金价值自动垫交保险费
	//fm.IsCashAutoPay.value='0';
	//犹豫期内的资金是否参与投资
	//fm.IsCashJoinInvest.value='0';


//投资账户节点
	

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}


function initContConfirm()
{
try { 
	fm.ReqsrNo.value=fm.TransrNo.value;

	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->NewContConfirm 函数中发生异常:初始化界面错误!");
	}
}

	function initBox(){
	
	try {
	
	//-------交易信息
    fm.FunctionFlag.value='01';
	//银行端交易日期
	fm.BankDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransrNo.value = '<%=transrNo%>';
	//隐藏交易流水号
	fm.NewTransNo.value = fm.TransrNo.value;
	//地区代码 
	fm.ZoneNo.value='';
	//fm.ZoneNoName.value = '';
	//网点代码  
	fm.BrNo.value='';
	//fm.BrNoName.value = '';
	//柜员代码
	fm.TellerNo.value = '';
	
	
	//--------投保信息
	//投保单号
	fm.ProposalContNo.value = '';
	//投保日期 
	fm.PolApplyDate.value = '';
	//账户姓名
	fm.AccName.value='';
	//代收付银行名称
	//fm.AccBankNAME.value='';
	//fm.AccBankNAMEName.value='';
	//代收付银行账户
	//fm.BankAccNo.value='';
	//交费形式
	fm.PayMode.value='';
	//交费帐号
	fm.AccNo.value='';
	//特别约定
	fm.SpecContent.value='SpecContent';
	//保单密码
	//fm.Password.value='';
	//保单印刷号码
	fm.PrtNo.value='';

   
//投保人信息
	//姓名
	fm.Name.value = '';
	//性别
	fm.Sex.value = '';
	//证件类型
	fm.IDType.value = '';
	fm.IDTypeName.value = '';
	//证件号码
	fm.IDNo.value = '';
	//生日
	fm.Birthday.value = '';
	//联系电话           
	fm.Phone.value='';
	//手机号        
	fm.Mobile.value='';
	//通讯地址            
	fm.Address.value='';
	//通讯邮政编码        
	fm.ZipCode.value='';
	//与被保人关系        
	fm.RelaToInsured.value='';
	fm.RelaToInsuredName.value='';
	//国籍代码
	fm.County.value='';	
	//证件有效期          
	fm.ValidYear.value='';
	//电子邮件            
	fm.Email.value='';
	//职业代码            
	fm.JobCode.value='';
	fm.JobCodeName.value='';
	
	
	
	
//被保人信息
		fm.BName.value = '';
		//性别
		fm.BSex.value = '';
		//证件类型
		fm.BIDType.value = '';
		fm.BIDTypeName.value = '';
		//证件号码
		fm.BIDNo.value = '';
		//生日
		fm.BBirthday.value = '';
		//联系电话           
		fm.BPhone.value='';
		//手机号        
		fm.BMobile.value='';
		//通讯地址            
		fm.BAddress.value='';
		//通讯邮政编码        
		fm.BZipCode.value='';
		//国籍代码
		fm.BCounty.value='';	
		//证件有效期          
		fm.BValidYear.value='';
		//电子邮件            
		fm.BEmail.value='';
		//职业代码            
		fm.BJobCode.value='';
		fm.BJobCodeName.value='';
			
	
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
		fm.IDTypeName1.value = '';
		//受益人证件号码
		fm.IDNo1.value = '';
		//受益人与被保人的关系  
		fm.RelationToInsured1.value='';	
		fm.RelationToInsuredName1.value='';
		//受益顺序
		fm.BnfGrade1.value = ''; 	
		//受益比例 
		fm.BnfLot1.value = '';
		//通讯地址
		fm.Address1.value='';
		//受益人是否为法定标志 
		fm.BeneficiaryIndicator.value=''; 
		
		fm.Type2.value='';
		fm.Name2.value = '';
		fm.Sex2.value = '';
		fm.Birthday2.value = '';
		fm.IDType2.value = '';
		fm.IDTypeName2.value = '';
		fm.IDNo2.value = '';
		fm.RelationToInsured2.value='';	
		fm.RelationToInsuredName2.value='';
		fm.BnfGrade2.value = ''; 	
		fm.BnfLot2.value = '';
		fm.Address2.value='';
		
		fm.Type3.value='';
		fm.Name3.value = '';
		fm.Sex3.value = '';
		fm.Birthday3.value = '';
		fm.IDType3.value = '';
		fm.IDTypeName3.value = '';
		fm.IDNo3.value = '';
		fm.RelationToInsured3.value='';	
		fm.RelationToInsuredName3.value='';
		fm.BnfGrade3.value = ''; 	
		fm.BnfLot3.value = '';
		fm.Address3.value='';
	
//主险信息
	fm.Code.value = '';
	//fm.RiskPassword.value='';
	fm.Prem.value = '';
	fm.Amnt.value = '';
	//fm.Rate.value='';
	fm.CValiDate.value='';
	fm.Mult.value = '';
	fm.PayIntv.value='';
	fm.Years.value='';
	fm.PayEndYearFlag.value = '';
	fm.PayEndYear.value = '';
	fm.InsuYearFlag.value = '';
	fm.InsuYear.value = '';
	fm.GetYearFlag.value='';
	fm.GetYear.value='';
	fm.HealthFlag.value='';
	//fm.IsCashAutoPay.value='';
	//fm.IsCashJoinInvest.value='';
	
//附加险信息
	fm.Code1.value = '';
	fm.Prem1.value = '';
	fm.Amnt1.value = '';
	fm.CValiDate1.value='';
	fm.Mult1.value = '';
	fm.PayIntv1.value='';
	fm.Years1.value='';
	fm.PayEndYearFlag1.value = '';
	fm.PayEndYear1.value = '';
	fm.InsuYearFlag1.value = '';
	fm.InsuYear1.value = '';
	fm.GetYearFlag1.value='';
	fm.GetYear1.value='';
	
	fm.Code2.value = '';
	fm.Prem2.value = '';
	fm.Amnt2.value = '';
	fm.CValiDate2.value='';
	fm.Mult2.value = '';
	fm.PayIntv2.value='';
	fm.Years2.value='';
	fm.PayEndYearFlag2.value = '';
	fm.PayEndYear2.value = '';
	fm.InsuYearFlag2.value = '';
	fm.InsuYear2.value = '';
	fm.GetYearFlag2.value='';
	fm.GetYear2.value='';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
	}


</script>
