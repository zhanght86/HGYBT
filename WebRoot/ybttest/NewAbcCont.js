function WBnfFlag(c){
	
	if(c == '2'){
			fm.BnfFlag.checked = true;
			setBnfFlag(fm.BnfFlag);
			
	}
	//else{
	//fm.BnfFlag.checked = false;
	//		setBnfFlag(fm.BnfFlag);
	//}
}
function WAppFlag(c){
	
	if(c == '1'){
			fm.InsFlag.checked = true;
			setInsFlag(fm.InsFlag);	
	}
	else{
	fm.InsFlag.checked = false;
			setInsFlag(fm.InsFlag);}
}
function showTranCom() {

	fm.TranCom.value = "";
	fm.TranComName.value = "";
	
	showCodeList('trancom_bank',[fm.TranCom,fm.TranComName,fm.BanknodeCheck,fm.BanknodeCheckName],[0,1,2,3],null,null,null,1,null,1);
} 





function showProductCode() {
var vtrancom=fm.TranCom.value;
if(vtrancom==""||vtrancom==null){
    	alert("请先选择交易银行!");
		return false;
    }
    fm.RISKSRISKCODE.value = '';
		fm.RiskCodeName.value = '';
		fm.RISKSPRODUCTCODE.value = '';
	//clearMainRisk();
	//clearRisk1();
	//clearRisk2();
	//clearAccCount();
	//showCodeList('ICBC_MainRiskCode',[fm.ProductCode,fm.RiskCodeName,fm.IntialNumberOfUnits,fm.ModalPremAmt,fm.InitCovAmt,fm.DurationMode,fm.Duration,fm.PaymentDurationMode,fm.PaymentDuration,fm.PaymentMode,fm.RiskCode,fm.AccCode1,fm.AllocPercent1,fm.AccCodeName1,fm.InvestDateInd],[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14],null,null,null,1,null,1);
showCodeList('ICBC_MainRiskCode',[fm.ProductCode,fm.RiskCodeName],[0,1],null,'TranCom=#'+ vtrancom +'#',null,1,null,1);
}






function showProductCodeKey() {}
function showProductCode1Key() {}
function showProductCode2Key() {}

function checkRiskCode1(){
	var MainRiskCode = trim(fm.RISKSRISKCODE.value);
	if(MainRiskCode == ''){
		alert('请先选择主险编码');
		return false; 
	}
		return true;
}


	function setInsFlag(InsFlag){
		if(InsFlag.checked == true){
		  
			document.getElementById("Ins1").readOnly=true;  
			document.getElementById("Ins2").readOnly=true; 
			document.getElementById("Ins3").readOnly=true; 
			document.getElementById("Ins4").readOnly=true;
			document.getElementById("Ins5").readOnly=true;  
			document.getElementById("Ins6").readOnly=true;
			document.getElementById("Ins7").readOnly=true;
			document.getElementById("Ins8").readOnly=true; 
			document.getElementById("Ins9").readOnly=true; 
			document.getElementById("Ins10").readOnly=true; 
			document.getElementById("Ins11").readOnly=true; 
			document.getElementById("Ins12").readOnly=true;
			document.getElementById("Ins13").readOnly=true;  
			document.getElementById("Ins14").readOnly=true;
			document.getElementById("Ins16").readOnly=true; 
			document.getElementById("Ins17").readOnly=true; 
			document.getElementById("Ins18").readOnly=true; 
			document.getElementById("Ins19").readOnly=true; 
			document.getElementById("Ins20").readOnly=true;
	
	//被保人
	fm.INSUIDKIND.value=fm.APPLIDKIND.value;
	fm.INSUIDCODE.value=fm.APPLIDCODE.value;
	fm.INSUBEGINDATE.value=fm.APPLBEGINDATE.value;
	fm.INSUINVALIDDATE.value=fm.APPLINVALIDDATE.value;
	fm.INSUNAME.value=fm.APPLNAME.value;
	fm.INSUSEX.value=fm.APPLSEX.value;
	fm.INSUBIRTHDAY.value=fm.APPLBIRTHDAY.value;
	fm.INSUCOUNTRY.value=fm.APPLCOUNTRY.value;
	fm.INSUADDRESS.value=fm.APPLADDRESS.value;
	fm.INSUPROV.value=fm.APPLPROV.value;
	fm.INSUZIPCODE.value=fm.APPLZIPCODE.value;
	fm.INSUEMAIL.value=fm.APPLEMAIL.value;
	fm.INSUPHONE.value=fm.APPLPHONE.value;
	fm.INSUMOBILE.value=fm.APPLMOBILE.value;
	fm.INSUJOBCODE.value=fm.ApplJobCode.value;
	
	fm.APPLRELATOINSURED.value='01';
	
	fm.INSUNOTICE.value='0';
	fm.INSUANNUALINCOME.value=fm.APPLANNUALINCOME.value;	
	fm.INSUISRISKJOB.value='0';
	fm.INSUHEALTHNOTICE.value='0';

	fm.all('APPLRELATOINSURED').value='01'; 
		}  
		else 
		{   
			document.getElementById("Ins1").readOnly=false;  
			document.getElementById("Ins2").readOnly=false; 
			document.getElementById("Ins3").readOnly=false; 
			document.getElementById("Ins4").readOnly=false;
			document.getElementById("Ins5").readOnly=false;  
			document.getElementById("Ins6").readOnly=false;
			document.getElementById("Ins7").readOnly=false;
			document.getElementById("Ins8").readOnly=false; 
			document.getElementById("Ins9").readOnly=false; 
			document.getElementById("Ins10").readOnly=false; 
			document.getElementById("Ins11").readOnly=false; 
			document.getElementById("Ins12").readOnly=false;
			document.getElementById("Ins13").readOnly=false;  
			document.getElementById("Ins14").readOnly=false;
			document.getElementById("Ins16").readOnly=false; 
			document.getElementById("Ins17").readOnly=false; 
			document.getElementById("Ins18").readOnly=false; 
			document.getElementById("Ins19").readOnly=false; 
			document.getElementById("Ins20").readOnly=false;

//被保人
	fm.INSUIDKIND.value='110013';
	fm.INSUIDCODE.value='13213091039';
	fm.INSUBEGINDATE.value='1991-10-10';
	fm.INSUINVALIDDATE.value='2019-10-10';
	fm.INSUNAME.value='周敏';
	fm.INSUSEX.value='0';
	fm.INSUBIRTHDAY.value='1991-10-10';
	fm.INSUCOUNTRY.value='156';
	fm.INSUADDRESS.value='浙江省杭州市UDC时代大厦23楼';
	fm.INSUPROV.value='浙江省';
	fm.INSUZIPCODE.value='100001';
	fm.INSUEMAIL.value='zhouliu@sina.com';
	fm.INSUPHONE.value='12312512000';
	fm.INSUMOBILE.value='29902392000';
	
	fm.INSUJOBTYPE.value='01';
	fm.INSUJOBCODE.value='01';
	fm.INSUNOTICE.value='0';
	fm.INSUANNUALINCOME.value='10000000000000';	
	fm.INSUISRISKJOB.value='0';
	fm.INSUHEALTHNOTICE.value='0';
	
		} 
	}

function afterCodeSelect( cCodeName, Field )
{
	if(cCodeName=='Relation'&&Field.value=='5')
	{
		fm.InsuredName.value= fm.AppntName.value;
		fm.InsuredSex.value= fm.AppntSex.value;
		fm.InsuredBirthday.value= fm.AppntBirthday.value;
		fm.InsuredIDType.value= fm.AppntIDType.value;
		fm.InsuredIDNo.value= fm.AppntIDNo.value;
		fm.InsuredNationality.value= fm.AppntNationality.value;
		fm.InsuredJobCode.value= fm.AppntJobCode.value;
		fm.InsuredHomeAddress.value= fm.AppntHomeAddress.value;
		fm.InsuredHomeZipCode.value= fm.AppntHomeZipCode.value;
		fm.InsuredEmail.value= fm.AppntEmail.value;
		fm.InsuredHomePhone=fm.AppntPhone;
	}
}




//提交，保存按钮对应操作
function submitForm()
{ 
	 
  if(!verifyInput())
  {
  	return false;
  }
  
 
  
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}
 //报文头数据校验
function  verifyInput(){
	var vIp = fm.ip.value; 
	if (vIp.trim() == ""){
		alert("接收报文ip地址不能为空");
		return false;
	}
	
	var vPort = fm.port.value; 
	if (vPort.trim() == ""){    
		alert("接收报文端口不能为空");
		return false; 
	}
	 
	var vTransExeDate = fm.TRANSDATE.value; 
	if (vTransExeDate.trim() == ""){    
		alert("银行端交易日期不能为空");
		return false; 
	}   
	  
	var vTransRefGUID = fm.SERIALNO.value; 
	if (vTransRefGUID.trim() == ""){    
		alert("交易流水号不能为空");
		return false; 
	}   
	
//	var vRegionCode = fm.RegionCode.value; 
//	if (vRegionCode.trim() == ""){    
//		alert("地区代码不能为空");
//		return false; 
//	} 
	
	var vBranch = fm.BRANCHNO.value; 
	if (vBranch.trim() == ""){    
		alert("网点代码不能为空");
		return false; 
	} 
	
	var vTeller = fm.TLID.value; 
	if (vTeller.trim() == ""){    
		alert("柜员代码不能为空");
		return false; 
	}  
	//var vAGENTID= fm.AGENTID.value; 
	//if (vAGENTID.trim() == ""){    
	//	alert("网点编号不能为空");
	//	return false; 
	//}  
	var vHOAppFormNumber = fm.POLICYAPPLYSERIAL.value; 
	if (vHOAppFormNumber.trim() == ""){    
		alert("投保书号不能为空");
		return false;  
	} 
	 
	return true;
}  
 
//提交后操作,服务器数据返回后执行的操作

 

function afterSubmit(ResultCode,ResultInfoDesc)
{ 	
	  showInfo.close(); 
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +ResultInfoDesc;   
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	  		  
}  






function queryPrtNo()
{
	var vBankCode = fm.BankCode.value;
	var vZoneNo = fm.ZoneNo.value;
	var vBrNo = fm.BrNo.value;
	
	if( vBankCode == "" || vZoneNo == "" || vBrNo == "" ) {
		alert("请输入银行网点信息！");
		return false;
	}
	
	var vSQL = "SELECT AgentCom FROM LKCodeMapping WHERE BankCode = '" + vBankCode
			+ "' AND ZoneNo = '" + vZoneNo
			+ "' AND BankNode = '" + vBrNo + "'";
			
	var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("该银行网点信息不存在！");
		return false;
	}
	
	// 查询到对应的单证接收机构
	var vAgentCom = "F" + vResult[1][0];
	
	// 注意SQL语句的写法，单证有可能直接发放到网点上，也有可能只发放到上级网点上。所以，此处用LIKE操作。
	vSQL = "SELECT MIN(StartNO) FROM LZCard WHERE CertifyCode IN ('109001') AND '" + vAgentCom + "' LIKE ReceiveCom || '%%'";
	
	vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("没有查询到该网点关联的印刷号，请直接在后台查询！");
		return false;
	}
	
	// 将查询得到的保单印刷号赋值给对应的输入框
	fm.PrtNo.value = vResult[1][0];
	return true;
}

	function setBnfFlag(BnfFlag){ 
		if(BnfFlag.checked == true){
			document.getElementById("BnfReadOnly11").disabled=true; 
			document.getElementById("BnfReadOnly12").readOnly=true; 
			document.getElementById("BnfReadOnly13").disabled=true; 
			document.getElementById("BnfReadOnly14").disabled=true; 
			document.getElementById("BnfReadOnly15").readOnly=true; 
			document.getElementById("BnfReadOnly16").readOnly=true; 
			document.getElementById("BnfReadOnly17").disabled=true;
			document.getElementById("BnfReadOnly18").readOnly=true; 
			document.getElementById("BnfReadOnly21").disabled=true; 
			document.getElementById("BnfReadOnly22").readOnly=true; 
			document.getElementById("BnfReadOnly23").disabled=true; 
			document.getElementById("BnfReadOnly24").disabled=true; 
			document.getElementById("BnfReadOnly25").readOnly=true; 
			document.getElementById("BnfReadOnly26").readOnly=true; 
			document.getElementById("BnfReadOnly27").disabled=true;
			document.getElementById("BnfReadOnly28").readOnly=true; 
			document.getElementById("BnfReadOnly31").disabled=true; 
			document.getElementById("BnfReadOnly32").readOnly=true; 
			document.getElementById("BnfReadOnly33").disabled=true; 
			document.getElementById("BnfReadOnly34").disabled=true; 
			document.getElementById("BnfReadOnly35").readOnly=true; 
			//受益人信息1 
	//受益人类型
	fm.BNFTYPE1.value = '';
	//受益人姓名
	fm.BNFNAME1.value = '';
	//受益人性别 
	fm.BNFSEX1.value = '';
	//受益人证件类型
	fm.BNFIDKIND1.value = '';
	//受益人证件号码
	fm.BNFIDCODE1.value = '';
	//受益人出生日期
	fm.BNFBIRTHDAY1.value = '';
	//受益百分数 
	fm.BNFPROP1.value = '';
	//受益顺序  
	fm.BNFSEQUENCE1.value = ''; 
	//受益人与被报人的关系  
	fm.BnfRelationToInsured1.value = '';
	//受益人是否为法定标志 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//受益人证件类型有效期
	fm.BnfValidYear1.value = ''; 
 
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
			  
		
		}else{ 
			document.getElementById("BnfReadOnly11").disabled=false; 
			document.getElementById("BnfReadOnly12").readOnly=false; 
			document.getElementById("BnfReadOnly13").disabled=false; 
			document.getElementById("BnfReadOnly14").disabled=false; 
			document.getElementById("BnfReadOnly15").readOnly=false; 
			document.getElementById("BnfReadOnly16").readOnly=false;
			document.getElementById("BnfReadOnly17").disabled=false; 
			document.getElementById("BnfReadOnly18").readOnly=false; 
			document.getElementById("BnfReadOnly21").disabled=false; 
			document.getElementById("BnfReadOnly22").readOnly=false; 
			document.getElementById("BnfReadOnly23").disabled=false; 
			document.getElementById("BnfReadOnly24").disabled=false; 
			document.getElementById("BnfReadOnly25").readOnly=false; 
			document.getElementById("BnfReadOnly26").readOnly=false; 
			document.getElementById("BnfReadOnly27").disabled=false;
			document.getElementById("BnfReadOnly28").readOnly=false;  
			document.getElementById("BnfReadOnly31").disabled=false; 
			document.getElementById("BnfReadOnly32").readOnly=false; 
			document.getElementById("BnfReadOnly33").disabled=false; 
			document.getElementById("BnfReadOnly34").disabled=false; 
			document.getElementById("BnfReadOnly35").readOnly=false; 
			//受益人信息1 
	   //受益人类型
	  fm.BNFTYPE1.value = '1';
	  //受益人姓名
	  fm.BNFNAME1.value = '王五';
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
  	fm.BnfRelationToInsured1.value = '2';
	  //受益人是否为法定标志 
	  //fm.BeneficiaryIndicator.value='N'; 
	
	  //受益人证件类型有效期
	  fm.BnfValidYear1.value = '2030-09-16'; 
		}
	}
	

	function clearMainRisk(){
		fm.RiskCode.value = '';
		fm.RiskCodeName.value = '';
		fm.ProductCode.value = '';
		fm.IntialNumberOfUnits.value = '';
		fm.InitCovAmt.value = '';
		fm.ModalPremAmt.value = '';
		fm.DurationMode.value = '';
		fm.Duration.value = '';
		fm.PaymentDurationMode.value = '';
		fm.PaymentDuration.value = '';
		fm.PaymentMode.value = '';
		//fm.InvestDateInd.value = '';
	}
	
	
	function clearRisk1(){
		fm.RiskCode1.value = '';
		fm.RiskCodeName1.value = '';
		fm.ProductCode1.value = '';
		fm.IntialNumberOfUnits1.value = '';
		fm.InitCovAmt1.value = '';
		fm.ModalPremAmt1.value = '';
		fm.DurationMode1.value = '';
		fm.Duration1.value = '';
		fm.PaymentDurationMode1.value = '';
		fm.PaymentDuration1.value = '';
	}
	
	
	function clearRisk2(){
		fm.RiskCode2.value = '';
		fm.RiskCodeName2.value = '';
		fm.ProductCode2.value = '';
		fm.IntialNumberOfUnits2.value = '';
		fm.InitCovAmt2.value = '';
		fm.ModalPremAmt2.value = '';
		fm.DurationMode2.value = '';
		fm.Duration2.value = '';
		fm.PaymentDurationMode2.value = '';
		fm.PaymentDuration2.value = '';
	}
	
function TranFlag(c){
	var sTranFlagCode = fm.all('TRANSCODE').value;
	if(sTranFlagCode == '1002'){
		NewContInput();
	}
	if(sTranFlagCode == '1004'){
		NewContConfirm();
	}
}
function NewContConfirm(){

	//document.getElementById("PrtNo").disabled=false;
	document.getElementById("OldTranNo").disabled=false;
	document.getElementById("PayAccountID").disabled=false;
	//document.getElementById("ContNo").disabled=true;
	
	fm.ReqsrNo.value = fm.InputTransrNo.value;
}

function NewContInput(){
	 window.location.reload();
	initInputBox();
	document.getElementById("OldTranNo").disabled=true;
	document.getElementById("PayAccountID").disabled=false;
}


function RiskFlag(a){
	var sTranFlagCode = fm.all('RISKSRISKCODE').value;
//	if(sTranFlagCode == '005'){	
//		document.getElementById("C20001").style.display='none'; 
//		fm.PayOutDurationMode.value='2';
//		fm.PayoutStart.value='60';
//		fm.PayoutDuration.value='20';
//		
//		fm.PaymentMode.value='1';
//		fm.PaymentDuration.value='20';
//		
//		document.getElementById("BnfFlag").checked = false;
//		setBnfFlag(document.getElementById("BnfFlag"));
//		
////		document.getElementById("InsFlag").checked = false;
////		setInsFlag(document.getElementById("InsFlag"));
//		
//	}else 
	if(sTranFlagCode == '211901'){	
		document.getElementById("C20001").style.display=''; 
		fm.LoanContractNo.value='20130129';
		fm.LoanStartDate.value=fm.TRANSDATE.value;
		fm.LoanEndDate.value=fm.TRANSDATE.value;
//		fm.LoanAccountNo.value='6227001700130252734';
		fm.LoanContractAmt.value='100000000';
//		fm.FaceAmount.value='100000000';
		fm.RISKSBEGINDATE.value=fm.TRANSDATE.value;
		fm.RISKSENDDATE.value=fm.TRANSDATE.value;

		fm.RISKSPREM.value='50000';
		fm.RISKSAMNT.value='100000000';
		fm.RISKSINSUDUEDATE.value='1';
		
		fm.LoanType.value='00';
		
		document.getElementById("BnfFlag").checked = true;
		setBnfFlag(document.getElementById("BnfFlag"));
		
//		document.getElementById("InsFlag").checked = true;
//		setInsFlag(document.getElementById("InsFlag"));
		
	}else if(sTranFlagCode == '211902'){	
		document.getElementById("C20001").style.display=''; 
		fm.LoanContractNo.value='20131201';
		fm.LoanStartDate.value=fm.TRANSDATE.value;
		fm.LoanEndDate.value=fm.TRANSDATE.value;
//		fm.LoanAccountNo.value='6227001700130252734';
		fm.LoanContractAmt.value='100000000';
//		fm.FaceAmount.value='100000000';
		fm.RISKSBEGINDATE.value=fm.TRANSDATE.value;
		fm.RISKSENDDATE.value=fm.TRANSDATE.value;
		
		fm.RISKSPREM.value='50000';
		fm.RISKSAMNT.value='100000000';
		fm.RISKSINSUDUEDATE.value='1';
		
		fm.LoanType.value='00';
		
		document.getElementById("BnfFlag").checked = true;
		setBnfFlag(document.getElementById("BnfFlag"));
		
//		document.getElementById("InsFlag").checked = true;
//		setInsFlag(document.getElementById("InsFlag"));
		
	}else{		
		document.getElementById("C20001").style.display='none'; 
//		fm.PayOutDurationMode.value='';
//		fm.PayoutStart.value='';
//		fm.PayoutDuration.value='';
//		
		fm.PayType.value='1';
		fm.RISKSPAYDUEDATE.value='1000';
		
		document.getElementById("BnfFlag").checked = false;
		setBnfFlag(document.getElementById("BnfFlag"));
		
//		document.getElementById("InsFlag").checked = false;
//		setInsFlag(document.getElementById("InsFlag"));
	}
}
