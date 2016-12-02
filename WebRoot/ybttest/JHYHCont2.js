
function TranFlag(c){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '510015'){
		NewContInput();
	}
	if(sTranFlagCode == '510001'){
		NewContConfirm();
	}
}
function NewContInput(){
	 window.location.reload();
		initInputBox();
	document.getElementById("ProviderFormNumber").disabled=false;
	document.getElementById("ReqsrNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConfirm(){
	initContConfirm();
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("ReqsrNo").disabled=false;
	fm.ReqsrNo.value = fm.InputTransrNo.value;
}
function RiskFlag(a){
	var sTranFlagCode = fm.all('ProductCode').value;
	if(sTranFlagCode == '113030'){
		//fm.PayIntv.value = '1';
		
	//份数 
	fm.IntialNumberOfUnits.value = '20';
	 //保额 
	fm.InitCovAmt.value = '';
	//保费
	fm.ModalPremAmt.value = '2000000';
	//保险年期类型
	//fm.InsuYearFlag.value = '4';
	//保险年期 
	//fm.InsuYear.value = '5';
	//缴费年期类型 
	//fm.PayEndYearFlag.value = '';
	//缴费年期 
	//fm.PayEndYear.value = '';
		//document.getElementById("fRiskCode").click();
	//	document.getElementById("AccountFlag").click();
		//document.getElementById("divRiskList").style=="display: ''";
	//	document.getElementById("divRiskList2").style=="display: ''";
		
	//fm.AccCode1.value = 'U1ZY';
	//fm.AllocPercent1.value = '100';
	//fm.InvestDateInd.value = '1';
	}
		if(sTranFlagCode == '222002'){
		
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
	fm.PayEndYearFlag.value = '';
	//缴费年期 
	fm.PayEndYear.value = ''; 
	
	fm.PayIntv.value = '1';

		//document.getElementById("fRiskCode").click();
		//document.getElementById("AccountFlag").click();
		//document.getElementById("divRiskList").style=="display: 'none'";
		//document.getElementById("divRiskList2").style=="display: 'none'";
	}
		
		if(sTranFlagCode == '222003'){
		
	//份数 
	fm.Mult.value = '10';
	//保额 
	fm.Amnt.value = '';
	//保费 
	fm.Prem.value = '10000.00';
	//保险年期类型  
	fm.InsuYearFlag.value = '4';
	//保险年期 
	fm.InsuYear.value = '5';
	//缴费年期类型 
	fm.PayEndYearFlag.value = '4';
	//缴费年期 
	fm.PayEndYear.value = '5'; 
	
	fm.PayIntv.value = '5';

		//document.getElementById("fRiskCode").click();
		//document.getElementById("AccountFlag").click();
		//document.getElementById("divRiskList").style=="display: 'none'";
		//document.getElementById("divRiskList2").style=="display: 'none'";
	}
}
function showRegionCode() {
	fm.RegionCode.value='';
	fm.RegionCodeName.value='';
		fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Region_Code',[fm.RegionCode,fm.RegionCodeName],[0,1],null,' 1=1 And TranCom=#'+ '011' +'#',null,1,null,1);
}

function showBranchCode() {
	fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Branch_Code',[fm.Branch,fm.BranchName],[0,1],null,' 1=1 And TranCom=#'+ '011' +'#' + 'And ZoneNo=#'+ fm.RegionCode.value +'#',null,1,null,1);
}

function showAccCode1() {
	fm.AccCode1.value='';
	fm.AccCodeName1.value='';
	fm.AllocPercent1.value='';
	return showCodeList('account_code',[fm.AccCode1,fm.AccCodeName1],[0,1],null,null,null,1,null,1);
}

function showAccCode1() {
	fm.AccCode1.value='';
	fm.AccCodeName1.value='';
	fm.AllocPercent1.value='';
	return showCodeList('account_code',[fm.AccCode1,fm.AccCodeName1],[0,1],null,null,null,1,null,1);
}

function showAccCode2() {
	fm.AccCode2.value='';
	fm.AccCodeName2.value='';
	fm.AllocPercent2.value='';
	return showCodeList('account_code',[fm.AccCode2,fm.AccCodeName2],[0,1],null,null,null,1,null,1);
}

function showAccCode3() {
	fm.AccCode3.value='';
	fm.AccCodeName3.value='';
	fm.AllocPercent3.value='';
	return showCodeList('account_code',[fm.AccCode3,fm.AccCodeName3],[0,1],null,null,null,1,null,1);
}

function showAccCode4() {
	fm.AccCode4.value='';
	fm.AccCodeName4.value='';
	fm.AllocPercent4.value='';
	return showCodeList('account_code',[fm.AccCode4,fm.AccCodeName4],[0,1],null,null,null,1,null,1);
}

function showAccCode5() {
	fm.AccCode5.value='';
	fm.AccCodeName5.value='';
	fm.AllocPercent5.value='';
	return showCodeList('account_code',[fm.AccCode5,fm.AccCodeName5],[0,1],null,null,null,1,null,1);
}

function showProductCode() {
	clearMainRisk();
	clearRisk1();
	clearRisk2();
	clearAccCount();
	showCodeList('ICBC_MainRiskCode',[fm.ProductCode,fm.RiskCodeName,fm.IntialNumberOfUnits,fm.ModalPremAmt,fm.InitCovAmt,fm.DurationMode,fm.Duration,fm.PaymentDurationMode,fm.PaymentDuration,fm.PaymentMode,fm.RiskCode,fm.AccCode1,fm.AllocPercent1,fm.AccCodeName1,fm.InvestDateInd],[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14],null,null,null,1,null,1);
}

function showProductCode1() {
	
	clearRisk1();
	clearRisk2();
	
	if(!checkRiskCode1()){
		return false;
	}
	var MainRiskCode = trim(fm.RiskCode.value);
	var RiskCode2 = trim(fm.RiskCode2.value);
	if(RiskCode2 ==''){
		RiskCode2=' ';
	}
	showCodeList('ICBC_SubRiskCode',[fm.ProductCode1,fm.RiskCodeName1,fm.IntialNumberOfUnits1,fm.ModalPremAmt1,fm.InitCovAmt1,fm.DurationMode1,fm.Duration1,fm.PaymentDurationMode1,fm.PaymentDuration1,fm.RiskCode1],[0,1,2,3,4,5,6,7,8,9],null,' 1=1 And m.mainriskcode=#'+ MainRiskCode +'#'+' And m.riskcode!=#'+ RiskCode2 +'#' ,null,1,null,1);
}

 
function showProductCode2() {
	clearRisk2();
	if(!checkRiskCode1()){
		return false;
	}
	var MainRiskCode = trim(fm.RiskCode.value);
	var RiskCode1 = trim(fm.RiskCode1.value);
	if(RiskCode1 ==''){
		RiskCode1=' ';
	}
	showCodeList('ICBC_SubRiskCode',[fm.ProductCode2,fm.RiskCodeName2,fm.IntialNumberOfUnits2,fm.ModalPremAmt2,fm.InitCovAmt2,fm.DurationMode2,fm.Duration2,fm.PaymentDurationMode2,fm.PaymentDuration2,fm.RiskCode2],[0,1,2,3,4,5,6,7,8,9],null,' 1=1 And m.mainriskcode=#'+ MainRiskCode +'#'+' And m.riskcode!=#'+ RiskCode1 +'#' ,null,1,null,1);
} 



function showProductCodeKey() {}
function showProductCode1Key() {}
function showProductCode2Key() {}

function checkRiskCode1(){
	var MainRiskCode = trim(fm.RiskCode.value);
	if(MainRiskCode == ''){
		alert('请先选择主险编码');
		return false; 
	}
	var mSqlStr = "select count(1) from lmriskapp s where s.subriskflag='S' and  s.mainriskcode = '"+MainRiskCode+"'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("该险种未有配对附加险!");
			return false; 
	}
		return true;
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
 function showtrancode(){
 	fm.tranFlagCode.value='';
	fm.tranFlagCodeName.value='';
	return showCodeList('servccbtype',[fm.tranFlagCode,fm.tranFlagCodeName],[0,1],null,'',null,1,null,1);
 }
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
	 
	var vTransExeDate = fm.TransExeDate.value; 
	if (vTransExeDate.trim() == ""){    
		alert("银行端交易日期不能为空");
		return false; 
	}   
	  
	var vTransRefGUID = fm.TransRefGUID.value; 
	if (vTransRefGUID.trim() == ""){    
		alert("交易流水号不能为空");
		return false; 
	}   
	
	var vRegionCode = fm.RegionCode.value; 
	if (vRegionCode.trim() == ""){    
		alert("地区代码不能为空");
		return false; 
	} 
	
	var vBranch = fm.Branch.value; 
	if (vBranch.trim() == ""){    
		alert("网点代码不能为空");
		return false; 
	} 
	
	var vTeller = fm.Teller.value; 
	if (vTeller.trim() == ""){    
		alert("柜员代码不能为空");
		return false; 
	}  
	
	var vHOAppFormNumber = fm.HOAppFormNumber.value; 
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

function confirmTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCCBConfirmSave.jsp";
	fm.ConfirmFlag.value = "1";
	fm.submit();	
	fm.action = vOldAction;
}


function cancleTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCCBCancleSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();	
	fm.action = vOldAction;
}

function cancelTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCCBCancleSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();
	
	fm.action = vOldAction;
}


function reprintCont()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCCBRePrtSave.jsp";
	fm.submit();

	fm.action = vOldAction;		
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
			document.getElementById("BnfReadOnly36").readOnly=true; 
			document.getElementById("BnfReadOnly37").disabled=true;
			document.getElementById("BnfReadOnly38").readOnly=true; 
			
			//受益人信息
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
			fm.BnfAdress1.value='';
			//受益百分数
			fm.InterestPercent1.value = '100';
			//受益顺序
			fm.Sequence1.value = '1';  
			//受益人与被报人的关系
			fm.BnfToInsRelation1.value = '';
			//受益人是否为法定标志  
			fm.BeneficiaryIndicator.value='1'; 
			
			//受益人信息
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
			fm.BnfAdress2.value='';
			//受益百分数
			fm.InterestPercent2.value = '';
			//受益顺序
			fm.Sequence2.value = '';  
			//受益人与被报人的关系
			fm.BnfToInsRelation2.value = '';
			//受益人是否为法定标志  
		
			
			//受益人信息
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
			fm.BnfAdress3.value='';
			//受益百分数
			fm.InterestPercent3.value = '';
			//受益顺序
			fm.Sequence3.value = '';  
			//受益人与被报人的关系
			fm.BnfToInsRelation3.value = '';
			//受益人是否为法定标志  
		
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
			document.getElementById("BnfReadOnly36").readOnly=false; 
			document.getElementById("BnfReadOnly37").disabled=false;
			document.getElementById("BnfReadOnly38").readOnly=false; 
			 
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
			fm.BnfAdress1.value='天津市和平区同安道';
			//受益百分数
			fm.InterestPercent1.value = '100'; 
			//受益顺序
			fm.Sequence1.value = '1';
			//受益人与被报人的关系
			fm.BnfToInsRelation1.value = '4';
				//受益人是否为法定标志
			fm.BeneficiaryIndicator.value = '0';
		}
	}
	
	function setInsFlag(InsFlag){
		if(InsFlag.checked == true){
			document.getElementById("Ins1").readOnly=true;  
			document.getElementById("Ins2").disabled=true; 
			document.getElementById("Ins3").disabled=true; 
			document.getElementById("Ins4").readOnly=true;
			document.getElementById("Ins5").readOnly=true;  
			document.getElementById("Ins6").readOnly=true;
			document.getElementById("Ins7").readOnly=true;
			document.getElementById("Ins8").readOnly=true; 
			document.getElementById("Ins9").readOnly=true; 
			document.getElementById("Ins10").readOnly=true; 
			//document.getElementById("Ins11").disabled=true; 
			document.getElementById("Ins12").disabled=true;
			document.getElementById("Ins13").disabled=true;
			document.getElementById("InsCountry").disabled=true;
		

//被保人信息 
	//被保人姓名
	fm.InsFullName.value = fm.AppFullName.value;
	//被保人性别
	fm.InsGender.value = fm.AppGender.value;
	//被保人证件类型
	fm.InsGenderh.value = fm.AppGender.value;
	
	fm.InsGovtIDTC.value = fm.AppGovtIDTC.value;
	fm.InsGovtIDTCh.value = fm.AppGovtIDTC.value ;
	//被保人证件号码
	fm.InsGovtID.value = fm.AppGovtID.value;
	//被保人生日
	fm.InsBirthDate.value = fm.AppBirthDate.value;
	//被保人电子邮箱
	fm.InsAddrLine.value = fm.AppAddrLine.value;
	//被保人通讯地址	
	fm.InsLine1.value = fm.AppLine1.value ;
	//被保人邮政编码
	fm.InsZip.value = fm.AppZip.value;
	//被保人家庭电话 
	fm.InsDialNumber1.value = fm.AppDialNumber1.value;
	//被保人移动电话
	fm.InsDialNumber3.value = fm.AppDialNumber3.value;
	fm.InsCountry.value = fm.AppCountry.value;
	fm.InsuJobCode.value = fm.ApplJobCode.value;
	//健康告知
	fm.HealthIndicator.value = 'N';
	fm.all('AppToInsRelation').value='1'; 
		}  
		else 
		{   
			document.getElementById("Ins1").readOnly=false;
			document.getElementById("Ins2").disabled=false;
			document.getElementById("Ins3").disabled=false;
			document.getElementById("Ins4").readOnly=false;
			document.getElementById("Ins5").readOnly=false;
			document.getElementById("Ins6").readOnly=false;
			document.getElementById("Ins7").readOnly=false;
			document.getElementById("Ins8").readOnly=false;
			document.getElementById("Ins9").readOnly=false;
			document.getElementById("Ins10").readOnly=false;
			
			document.getElementById("Ins12").disabled=false;
			document.getElementById("Ins13").disabled=false;
			document.getElementById("InsCountry").disabled=false;
			
			
			//document.getElementById("Ins11").disabled=false;
					

//被保人信息
	//被保人姓名
	fm.InsFullName.value = '被保人1';
	//被保人性别
	fm.InsGender.value = '1'; 
	//被保人证件类型
	fm.InsGovtIDTC.value = 'B';
	//被保人证件号码
	fm.InsGovtID.value = '3143148';
	//被保人生日
	fm.InsBirthDate.value = '1985-09-17';
	//被保人电子邮箱
	fm.InsAddrLine.value = 'lisi@163.com';
	//被保人通讯地址	
	fm.InsLine1.value = '天津市和平区同安道'; 
	//被保人邮政编码
	fm.InsZip.value = '555555';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '02153558550';
	//被保人移动电话
	fm.InsDialNumber3.value = '13821576911';
	//健康告知
	fm.HealthIndicator.value = 'N';	
	fm.InsCountry.value = '0156';
	fm.InsuJobCode.value = '3010101';
	
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
		fm.InvestDateInd.value = '';
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
	
	
	function clearAccCount(){
		fm.AccCode1.value = '';
		fm.AccCodeName1.value = '';
		fm.AllocPercent1.value = '';
		fm.AccCode2.value = '';
		fm.AccCodeName2.value = '';
		fm.AllocPercent2.value = '';
		fm.AccCode3.value = '';
		fm.AccCodeName3.value = '';
		fm.AllocPercent3.value = '';
		fm.AccCode4.value = '';
		fm.AccCodeName4.value = '';
		fm.AllocPercent4.value = '';
		fm.AccCode5.value = '';
		fm.AccCodeName5.value = '';
		fm.AllocPercent5.value = '';
	}