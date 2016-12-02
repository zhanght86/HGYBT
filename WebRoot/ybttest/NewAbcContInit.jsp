<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//程序名称：XBTContInit.jsp
	
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = PubFun1.CreateMaxNo("TransNo",16);
  String strProposalContNo = "2104141"+PubFun1.CreateMaxNo("ProNo",8);
	Element mTestUI = 
 		 MidplatConf.newInstance().getConf().getRootElement().getChild("NewABCTestUI");
// 	 String tIp = mTestUI.getAttributeValue("ip");
// 	 String tPort = mTestUI.getAttributeValue("port");
//  	 String tZoneNo = mTestUI.getAttributeValue("ZoneNo");
//  	 String tNodeNo = mTestUI.getAttributeValue("NodeNo");
	String tIp="127.0.0.1";
	String tPort="35006";
	String tZoneNo="11";
	String tNodeNo="0102";
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
		alert("NewAbcContInit.jsp-->InitForm11 函数中发生异常:初始化界面错误!");
	}
}



function initInputBox() 
{
	try {
	fm.TranCom.value='05';
	fm.APPNO.value='<%=strTransRefGUID%>';
	fm.SERIALNO.value='<%=strTransRefGUID%>';
	//隐藏交易流水号
	fm.InputTransrNo.value = '<%=strTransRefGUID%>';
	fm.POLICYAPPLYSERIAL.value='<%=strProposalContNo%>';
	fm.TRANSDATE.value='<%=strTransExeDate%>';
	fm.BRANCHNO.value='0102';
	fm.TLID.value='0005';
		
	fm.CONACCNAME.value='李克弱';
	fm.CONACCNO.value='8989898989898';
	fm.APPLYDATE.value='<%=strTransExeDate%>';
	fm.SALER.value='张三';
	fm.SALERCERTNO.value='10209129301230';
	fm.BRANCHCERTNO.value='1203120999';
	fm.BRANCHNAME.value='农业银行测试网点';
	fm.VCHNO.value='0000000093';
	fm.ProvCode.value='11';
	//投保人
  document.getElementById("OldTranNo").disabled=true;
  document.getElementById("PayAccountID").disabled=true;
	fm.APPLIDKIND.value='110013';
	fm.APPLIDCODE.value='100101008611';
	fm.APPLBEGINDATE.value='1985-09-17';
	fm.APPLINVALIDDATE.value='2019-12-11';
	fm.APPLNAME.value='毛泽西';
	fm.APPLSEX.value='0';
	fm.APPLBIRTHDAY.value='1965-09-17';
	fm.APPLCOUNTRY.value='156';
	fm.APPLADDRESS.value='浙江省杭州市UDC时代大厦23楼';
	fm.APPLPROV.value='浙江省';
	fm.APPLZIPCODE.value='100001';
	fm.APPLEMAIL.value='maozexi@sina.com';
	fm.APPLPHONE.value='10112481294';
	fm.APPLMOBILE.value='18919082301';
	fm.APPLANNUALINCOME.value='1000000';
	fm.APPLRELATOINSURED.value='04';
	fm.ApplJobCode.value='09';
	fm.PbDenType.value='1';
	
	//被保人
	fm.INSUIDKIND.value='110013';
	fm.INSUIDCODE.value='100861001022';
	fm.INSUBEGINDATE.value='1991-10-10';
	fm.INSUINVALIDDATE.value='2019-10-10';
	fm.INSUNAME.value='习远平';
	fm.INSUSEX.value='0';
	fm.INSUBIRTHDAY.value='1991-10-10';
	fm.INSUCOUNTRY.value='156';
	fm.INSUADDRESS.value='浙江省杭州市UDC时代大厦23楼';
	fm.INSUPROV.value='浙江省';
	fm.INSUZIPCODE.value='100001';
	fm.INSUEMAIL.value='xiyuanping@sina.com';
	fm.INSUPHONE.value='10112512000';
	fm.INSUMOBILE.value='18902392000';
	fm.INSUJOBCODE.value='08'
	fm.INSUNOTICE.value='0';
	fm.INSUANNUALINCOME.value='1000000';	
	fm.INSUISRISKJOB.value='0';
	fm.INSUHEALTHNOTICE.value='0';
	
	
//受益人信息1 
	//受益人类型
	fm.BNFTYPE1.value = '1';
	//受益人姓名
	fm.BNFNAME1.value = '邓大平';
	//受益人性别 
	fm.BNFSEX1.value = '0';
	//受益人证件类型
	fm.BNFIDKIND1.value = '110005';
	//受益人证件号码
	fm.BNFIDCODE1.value = '220523850917341';
	//受益人出生日期
	fm.BNFBIRTHDAY1.value = '1988-09-17';
	//受益百分数 
	fm.BNFPROP1.value = '100';
	//受益顺序  
	fm.BNFSEQUENCE1.value = '1'; 
	//受益人与被报人的关系  
	fm.BnfRelationToInsured1.value = '02';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//受益人证件类型有效期
	fm.BnfValidYear1.value = '2030-09-16'; 
 
//受益人信息2
//受益人信息1 
	//受益人类型
	fm.BNFTYPE2.value = '';
	//受益人姓名
	fm.BNFNAME2.value = '';
	//受益人性别 
	fm.BNFSEX2.value = '';
	//受益人证件类型
	fm.BNFIDKIND2.value = '';
	//受益人证件号码
	fm.BNFIDCODE2.value = '';
	//受益人出生日期
	fm.BNFBIRTHDAY2.value = '';
	//受益百分数 
	fm.BNFPROP2.value = '';
	//受益顺序  
	fm.BNFSEQUENCE2.value = ''; 
	//受益人与被报人的关系  
	fm.BnfRelationToInsured2.value = '';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//受益人证件类型有效期
	fm.BnfValidYear2.value = ''; 
	 
//受益人信息3
	//受益人类型
	fm.BNFTYPE3.value = '';
	//受益人姓名
	fm.BNFNAME3.value = '';
	//受益人性别 
	fm.BNFSEX3.value = '';
	//受益人证件类型
	fm.BNFIDKIND3.value = '';
	//受益人证件号码
	fm.BNFIDCODE3.value = '';
	//受益人出生日期
	fm.BNFBIRTHDAY3.value = '';
	//受益百分数 
	fm.BNFPROP3.value = '';
	//受益顺序  
	fm.BNFSEQUENCE3.value = ''; 
	//受益人与被报人的关系  
	fm.BnfRelationToInsured3.value = '';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//受益人证件类型有效期
	fm.BnfValidYear3.value = ''; 

	//主险
	fm.RISKSRISKCODE.value='221206';
	fm.RISKSSHARE.value='1';
	fm.RISKSPREM.value='30000.00';
	fm.RISKSAMNT.value='';
	fm.RISKSINSUDUETYPE.value='4';
	fm.RISKSINSUDUEDATE.value='10';
	fm.RISKSPAYDUETYPE.value='0';
	fm.RISKSPAYDUEDATE.value='1000';
	fm.BonusGetMode.value='2';
	fm.PayType.value='1';
	//贷款
    fm.LoanCom.value = '1200292038';
    fm.LoanContractNo.value = '201200000219';
    fm.LoanStartDate.value = '2012-12-11';
    fm.LoanEndDate.value = '2015-12-11';
    fm.LoanContractAmt.value = '2000000';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>'; 
	fm.TRANSCODE.value = '1002'; 

	}
	catch(re)
	{
		alert("NewAbcContInit.jsp-->initInputBox22 函数中发生异常:初始化界面错误!");
	}
}



function initBox(){
	try{
	fm.TranCom.value='05';
	fm.APPNO.value=fm.SERIALNO.value='<%=strTransRefGUID%>';

	fm.TRANSDATE.value='<%=strTransExeDate%>';
	fm.BRANCHNO.value='0102';
	fm.TLID.value='0005';
		
	fm.CONACCNAME.value='';
	fm.CONACCNO.value='';
	fm.APPLYDATE.value='';
	fm.SALER.value='';
	fm.SALERCERTNO.value='';
	fm.BRANCHCERTNO.value='';
	fm.BRANCHNAME.value='';
	fm.ProvCode.value='';
	fm.VCHNO.value='';
	//投保人

	fm.APPLIDKIND.value='';
	fm.APPLIDCODE.value='';
	fm.APPLBEGINDATE.value='';
	fm.APPLINVALIDDATE.value='';
	fm.APPLNAME.value=''
	fm.APPLSEX.value='';
	fm.APPLBIRTHDAY.value='';
	fm.APPLCOUNTRY.value='';
	fm.APPLADDRESS.value='';
	fm.APPLPROV.value='';
	fm.APPLZIPCODE.value='';
	fm.APPLEMAIL.value='';
	fm.APPLPHONE.value='';
	fm.APPLMOBILE.value='';
	fm.APPLANNUALINCOME.value='';
	fm.ApplJobCode.value='';
	fm.APPLRELATOINSURED.value='';
	
	//被保人
	fm.INSUIDKIND.value='';
	fm.INSUIDCODE.value='';
	fm.INSUBEGINDATE.value='';
	fm.INSUINVALIDDATE.value='';
	fm.INSUNAME.value='';
	fm.INSUSEX.value='';
	fm.INSUBIRTHDAY.value='';
	fm.INSUCOUNTRY.value='';
	fm.INSUADDRESS.value='';
	fm.INSUPROV.value='';
	fm.INSUZIPCODE.value='';
	fm.INSUEMAIL.value='';
	fm.INSUPHONE.value='';
	fm.INSUMOBILE.value='';
	
	fm.INSUJOBCODE.value='';
	fm.INSUNOTICE.value='';
	fm.INSUANNUALINCOME.value='';	
	fm.INSUISRISKJOB.value='';
	fm.INSUHEALTHNOTICE.value='';


	//主险
	fm.RISKSRISKCODE.value='221206';
	fm.RISKSSHARE.value='';
	fm.RISKSPREM.value='';
	fm.RISKSAMNT.value='';
	fm.RISKSINSUDUETYPE.value='';
	fm.RISKSINSUDUEDATE.value='';
	fm.RISKSPAYDUETYPE.value='';
	fm.RISKSPAYDUEDATE.value='';
	//贷款
    fm.LoanCom.value = '';
    fm.LoanContractNo.value = '';
    fm.LoanStartDate.value = '';
    fm.LoanEndDate.value = '';
    fm.LoanContractAmt.value = '';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>'; 
	fm.TRANSCODE.value = '1004'; 
		
	}catch(re){
		alert("NewAbcContInit.jsp-->initBox()函数中发生异常：初始化错误！");
	}

}

function initContConfirm()
{
	try{
	fm.ReqsrNo.value = fm.InputTransrNo.value;
	fm.TranCom.value='05';
	fm.APPNO.value=fm.SERIALNO.value='<%=PubFun1.CreateMaxNo("TransNo",16)%>';

	fm.TRANSDATE.value='<%=strTransExeDate%>';
	fm.ProvCode.value='11';
	fm.BRANCHNO.value='0102';
	fm.TLID.value='0005';
  fm.PayAccount.value='6228480150218987623';		
	fm.CONACCNAME.value='';
	fm.CONACCNO.value='';
	fm.APPLYDATE.value='';
	fm.SALER.value='';
	fm.SALERCERTNO.value='';
	fm.BRANCHCERTNO.value='';
	fm.BRANCHNAME.value='';
	fm.VCHNO.value='';
	//投保人

	fm.APPLIDKIND.value='';
	fm.APPLIDCODE.value='';
	fm.APPLBEGINDATE.value='';
	fm.APPLINVALIDDATE.value='';
	fm.APPLNAME.value=''
	fm.APPLSEX.value='';
	fm.APPLBIRTHDAY.value='';
	fm.APPLCOUNTRY.value='';
	fm.APPLADDRESS.value='';
	fm.APPLPROV.value='';
	fm.APPLZIPCODE.value='';
	fm.APPLEMAIL.value='';
	fm.APPLPHONE.value='';
	fm.APPLMOBILE.value='';
	fm.APPLANNUALINCOME.value='';
	fm.ApplJobCode.value='';
	fm.APPLRELATOINSURED.value='';
	
	//被保人
	fm.INSUIDKIND.value='';
	fm.INSUIDCODE.value='';
	fm.INSUBEGINDATE.value='';
	fm.INSUINVALIDDATE.value='';
	fm.INSUNAME.value='';
	fm.INSUSEX.value='';
	fm.INSUBIRTHDAY.value='';
	fm.INSUCOUNTRY.value='';
	fm.INSUADDRESS.value='';
	fm.INSUPROV.value='';
	fm.INSUZIPCODE.value='';
	fm.INSUEMAIL.value='';
	fm.INSUPHONE.value='';
	fm.INSUMOBILE.value='';
	
	fm.INSUJOBCODE.value='';
	fm.INSUNOTICE.value='';
	fm.INSUANNUALINCOME.value='';	
	fm.INSUISRISKJOB.value='';
	fm.INSUHEALTHNOTICE.value='';


	//主险
	fm.RISKSRISKCODE.value='221206';
	fm.RISKSSHARE.value='';
	fm.RISKSPREM.value='';
	fm.RISKSAMNT.value='';
	fm.RISKSINSUDUETYPE.value='';
	fm.RISKSINSUDUEDATE.value='';
	fm.RISKSPAYDUETYPE.value='';
	fm.RISKSPAYDUEDATE.value='';
	//贷款
    fm.LoanCom.value = '';
    fm.LoanContractNo.value = '';
    fm.LoanStartDate.value = '';
    fm.LoanEndDate.value = '';
    fm.LoanContractAmt.value = '';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>'; 
	fm.TRANSCODE.value = '1004'; 
		
	}catch(re){
		alert("initContConfirm()函数中发生异常：初始化错误！");
	}

}
    

</script>
