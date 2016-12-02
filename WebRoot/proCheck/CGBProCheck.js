function showRegionCode() {
	fm.RegionCode.value='';
	fm.RegionCodeName.value='';
		fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Region_Code',[fm.RegionCode,fm.RegionCodeName],[0,1],null,' 1=1 And TranCom=#'+ '012' +'#',null,1,null,1);
}

function showBranchCode() {
	fm.Branch.value='';
	fm.BranchName.value='';
	return showCodeList('Branch_Code',[fm.Branch,fm.BranchName],[0,1],null,' 1=1 And TranCom=#'+ '012' +'#' + 'And ZoneNo=#'+ fm.RegionCode.value +'#',null,1,null,1);
}

function TranFlag(c){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '1013'){
		NewContInput();
	}
	if(sTranFlagCode == '1113'){
		NewContConfirm();
	}
	if(sTranFlagCode == '1115'){
		NewContConcel();
	}
	if(sTranFlagCode == '1011'){
		ContRePrint();
	}
	if(sTranFlagCode == '1015'){
		ContConcel();
	}
}

function RiskFlag(a){
	var sTranFlagCode = fm.all('ProductCode').value;
	if(sTranFlagCode == '001'){
	//份数 
	fm.IntialNumberOfUnits.value = '';
	 //保额 
	fm.InitCovAmt.value = '2000000';
	//保费
	fm.ModalPremAmt.value = '356840';
	//保险年期类型
	fm.DurationMode.value = '1';
	//保险年期 
	fm.Duration.value = '100';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '2';
	//缴费年期 
	fm.PaymentDuration.value = '5';
	

		
		document.getElementById("fRiskCode").click();
		document.getElementById("fRiskCode1").click();
		document.getElementById("AccountFlag").click();
		document.getElementById("divRiskList").style=="display: ''";
		document.getElementById("divRiskList2").style=="display: ''";
		
	fm.AccCode1.value = 'U1ZY';
	fm.AllocPercent1.value = '100';
	fm.InvestDateInd.value = '1';
	}
		if(sTranFlagCode == '003'){
		
	//份数 
	fm.IntialNumberOfUnits.value = '';
	//保额 
	fm.InitCovAmt.value = '1000000';
	//保费 
	fm.ModalPremAmt.value = '297250';
	//保险年期类型  
	fm.DurationMode.value = '1';
	//保险年期 
	fm.Duration.value = '100';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '2';
	//缴费年期 
	
	
	fm.ProductCode1.value = '';
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保额 
	fm.InitCovAmt1.value = '';
	//保费 
	fm.ModalPremAmt1.value = '';
	//保险年期类型  
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';	
	
	fm.PaymentDuration.value = '';	
	fm.AccCode1.value = '';
	fm.AllocPercent1.value = '';
	fm.InvestDateInd.value = '';
		document.getElementById("fRiskCode").click();
		document.getElementById("fRiskCode1").click();
		document.getElementById("AccountFlag").click();
		document.getElementById("divRiskList").style=="display: 'none'";
		document.getElementById("divRiskList2").style=="display: 'none'";
	}
}


function SSRiskFlag(a){
	var sTranFlagCode1 = fm.all('ProductCode1').value;
	if(sTranFlagCode1 == '102'){
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	 //保额 
	fm.InitCovAmt1.value = '';
	//保费
	fm.ModalPremAmt1.value = '5000000';
	//保险年期类型
	fm.DurationMode1.value = fm.DurationMode.value;
	//保险年期 
	fm.Duration1.value = fm.Duration.value;
	//缴费年期类型 
	fm.PaymentDurationMode1.value = fm.PaymentDurationMode.value;
	//缴费年期 
	fm.PaymentDuration1.value = fm.PaymentDuration.value;

	}
		if(sTranFlagCode1 == ''){
		
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保额 
	fm.InitCovAmt1.value = '';
	//保费 
	fm.ModalPremAmt1.value = '';
	//保险年期类型  
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';	
	
	}
}

function SSRiskFlag2(a){
	var sTranFlagCode2 = fm.all('ProductCode2').value;
	if(sTranFlagCode2 == '101'){
	//份数 
	fm.IntialNumberOfUnits2.value = '';
	 //保额 
	fm.InitCovAmt2.value = '';
	//保费
	fm.ModalPremAmt2.value = '5000000';
	//保险年期类型
	fm.DurationMode2.value = fm.DurationMode.value;
	//保险年期 
	fm.Duration2.value = fm.Duration.value;
	//缴费年期类型 
	fm.PaymentDurationMode2.value = '5';
	//缴费年期 
	fm.PaymentDuration2.value = '999';

	}
		if(sTranFlagCode2 == ''){
		
	//份数 
	fm.IntialNumberOfUnits2.value = '';
	//保额 
	fm.InitCovAmt2.value = '';
	//保费 
	fm.ModalPremAmt2.value = '';
	//保险年期类型  
	fm.DurationMode2.value = '';
	//保险年期 
	fm.Duration2.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode2.value = '';
	//缴费年期 
	fm.PaymentDuration2.value = '';	
	
	}
}


function NewContInput(){
	 window.location.reload();

	initInputBox();
	document.getElementById("PrtNo").disabled=true;
	document.getElementById("OldTransNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConfirm(){
	initContConfirm();
	document.getElementById("PrtNo").disabled=false;
	document.getElementById("OldTransNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConcel(){
	initContConcel();
	document.getElementById("PrtNo").disabled=true;
	document.getElementById("OldTransNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}
function ContRePrint(){
	alert("ContRePrint");
}

function ContConcel(){
	alert("ContConcel");
}




function setRiskFlag(c){
		if(c.checked == true){
			
			fm.all('hiddenBnf').value='0';
		    //alert(fm.all('hiddenBnf').value);
		}else{ 
			fm.all('hiddenBnf').value='1';
			//alert(fm.all('hiddenBnf').value);
		}
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
			fm.BnfFullName1.value = '法定'; 
			//受益人性别
			fm.BnfGender1.value = '';
			//受益人证件类型
			fm.BnfGovtIDTC1.value = '';
			//受益人证件号码
			fm.BnfGovtID1.value = '';
			//受益人出生日期
			fm.BnfBirthDate1.value = '';
			//受益百分数
			fm.InterestPercent1.value = '100';
			//受益顺序
			fm.Sequence1.value = '1';  
			//受益人与被报人的关系
			fm.BnfToInsRelation1.value = '';
			//受益人是否为法定标志  
			fm.BeneficiaryIndicator.value='Y'; 
			
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
			//受益百分数
			fm.InterestPercent1.value = '100'; 
			//受益顺序
			fm.Sequence1.value = '1';
			//受益人与被报人的关系
			fm.BnfToInsRelation1.value = '1';
				//受益人是否为法定标志
			fm.BeneficiaryIndicator.value = 'N';
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
		

//被保人信息 
	//被保人姓名
	fm.InsFullName.value = fm.AppFullName.value
	//被保人性别
	fm.InsGender.value = fm.AppGender.value
	//被保人证件类型
	fm.InsGenderh.value = fm.AppGender.value
	
	fm.InsGovtIDTC.value = fm.AppGovtIDTC.value
	fm.InsGovtIDTCh.value = fm.AppGovtIDTC.value 
	//被保人证件号码
	fm.InsGovtID.value = fm.AppGovtID.value
	//被保人生日
	fm.InsBirthDate.value = fm.AppBirthDate.value
	//被保人电子邮箱
	fm.InsAddrLine.value = fm.AppAddrLine.value
	//被保人通讯地址	
	fm.InsLine1.value = fm.AppLine1.value 
	//被保人邮政编码
	fm.InsZip.value = fm.AppZip.value
	//被保人家庭电话 
	fm.InsDialNumber1.value = fm.AppDialNumber1.value
	//被保人移动电话
	fm.InsDialNumber3.value = fm.AppDialNumber3.value
	//健康告知
	fm.HealthIndicator.value = 'N';
	fm.all('AppToInsRelation').value='8'; 
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
			//document.getElementById("Ins11").disabled=false;

//被保人信息
	//被保人姓名
	fm.InsFullName.value = '李四';
	//被保人性别
	fm.InsGender.value = '1'; 
	//被保人证件类型
	fm.InsGovtIDTC.value = '0';
	//被保人证件号码
	fm.InsGovtID.value = '220523850917341';
	//被保人生日
	fm.InsBirthDate.value = '1985-09-17';
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
 
  
  var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '1013'){
	fm.action = "./CGBProCheckSave.jsp";
	}
	if(sTranFlagCode == '1113'){
	fm.action = "./TestYBTConfirmSave.jsp";
	}
	if(sTranFlagCode == '1115'){
	fm.action = "./TestYBTNewContConcelSave.jsp";
	}
	if(sTranFlagCode == '1011'){
	fm.action = "./TestYBTRePrintSave.jsp";
	}
	if(sTranFlagCode == '1015'){
	fm.action = "./TestYBTConcelSave.jsp";
	}
 
  
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
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
	
	fm.action = "./TestYBTConfirmSave.jsp";
	fm.ConfirmFlag.value = "1";
	fm.submit();	
	fm.action = vOldAction;
}


function cancleTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCancleSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();	
	fm.action = vOldAction;
}

function cancelTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTCancleSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();
	
	fm.action = vOldAction;
}


function reprintCont()
{
	var vOldAction = fm.action;
	
	fm.action = "./TestYBTRePrtSave.jsp";
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
