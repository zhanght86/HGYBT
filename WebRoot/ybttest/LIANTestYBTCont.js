function TranFlag(c){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == '01'){
		NewContInput();
	}
	if(sTranFlagCode == '02'){
		NewContConfirm();
	}
	//if(sTranFlagCode == '03'){
	//	NewContConcel();
	//}
	if(sTranFlagCode == '03'){
		ContConcel();
	}
}

function RiskFlag(a){
	var sTranFlagCode = fm.all('Code').value;
	if(sTranFlagCode == '211901'){	
	document.getElementById("divOwnlist1").style.display='';
	fm.LoanNo.value='20130129';
	fm.LoanBank.value='中国农业银行';
	fm.LoanDate.value=fm.BankDate.value;
	fm.LoanEndDate.value=fm.BankDate.value;
	fm.LoanType.value='00';
	fm.LoanAccNo.value='6227001700130252734';
	fm.LoanPrem.value='1000000';
	fm.InsuDate.value=fm.BankDate.value;
	fm.InsuEndDate.value=fm.BankDate.value;
	
	fm.Prem.value='500';
	fm.Amnt.value='1000000';
	fm.InsuYear.value='1';
	
	document.getElementById("BnfFlag").checked = true;
	setBnfFlag(document.getElementById("BnfFlag"));
	

	}else  if(sTranFlagCode == '211902'){	
		document.getElementById("divOwnlist1").style.display='';
		fm.LoanNo.value='2014010600000001';
		fm.LoanBank.value='中国农业银行';
		fm.LoanDate.value=fm.BankDate.value;
		fm.LoanEndDate.value=fm.BankDate.value;
		fm.LoanType.value='00';
		fm.LoanAccNo.value='6227001700130252734';
		fm.LoanPrem.value='1000000';
		fm.InsuDate.value=fm.BankDate.value;
		fm.InsuEndDate.value=fm.BankDate.value;
		
		fm.Prem.value='500';
		fm.Amnt.value='1000000';
		fm.InsuYear.value='1';
		
		document.getElementById("BnfFlag").checked = true;
		setBnfFlag(document.getElementById("BnfFlag"));
		

		}else{		
		document.getElementById("divOwnlist1").style.display='none';
		fm.LoanNo.value='';
		fm.LoanBank.value='';
		fm.LoanDate.value='';
		fm.LoanEndDate.value='';
		fm.LoanType.value='';
		fm.LoanAccNo.value='';
		fm.LoanPrem.value='';
		fm.InsuDate.value='';
		fm.InsuEndDate.value='';
		
		fm.Prem.value = '30000.00';
		fm.Amnt.value='';
		fm.InsuYear.value='5';
		
		document.getElementById("BnfFlag").checked = false;
		setBnfFlag(document.getElementById("BnfFlag"));
	}

}


function SSRiskFlag(a){
	var sTranFlagCode1 = fm.all('Code1').value;
	if(sTranFlagCode1 == '101'){
	//份数 
	fm.Mult1.value = '';
	 //保额 
	fm.Amnt1.value = '';
	//保费
	fm.Prem1.value = '5000000';
	//保险年期类型
	fm.InsuYearFlag1.value = fm.InsuYearFlag.value;
	//保险年期 
	fm.InsuYear1.value = fm.InsuYear.value;
	//缴费年期类型 
	fm.PayEndYearFlag1.value = fm.PayEndYearFlag.value;
	//缴费年期 
	fm.PayEndYear1.value = fm.PayEndYear.value;

	}
		if(sTranFlagCode1 == ''){
		
	//份数 
	fm.Mult1.value = '';
	//保额 
	fm.Amnt1.value = '';
	//保费 
	fm.Prem1.value = '';
	//保险年期类型  
	fm.InsuYearFlag1.value = '';
	//保险年期 
	fm.InsuYear1.value = '';
	//缴费年期类型 
	fm.PayEndYearFlag1.value = '';
	//缴费年期 
	fm.PayEndYear1.value = '';	
	
	}
}


function NewContInput(){
	 window.location.reload();

	initInputBox();
	//document.getElementById("PrtNo").disabled=false;
	document.getElementById("ReqsrNo").disabled=true;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
}

function NewContConfirm(){
	initContConfirm();
	//document.getElementById("PrtNo").disabled=false;
	document.getElementById("ReqsrNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
	fm.ReqsrNo.value = fm.InputTransrNo.value;
}

//function NewContConcel(){
	//initContConcel();
	//document.getElementById("PrtNo").disabled=true;
	//document.getElementById("OldTransNo").disabled=false;
	//document.getElementById("OldPrtNo").disabled=true;
	//document.getElementById("ContNo").disabled=true;
//}
function ContRePrint(){
	alert("ContRePrint");
}

function ContConcel(){
    initContConcel();
	//document.getElementById("PrtNo").disabled=true;
	document.getElementById("ReqsrNo").disabled=false;
	//alert("ContConcel");
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
			
			
			//受益人信息
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
			//fm.BeneficiaryIndicator.value='Y'; 
			
			fm.BnfValidYear1.value = ''; 
			
			//受益人信息
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
			fm.BnfValidYear2.value = ''; 
			
		
			
			//受益人信息
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
			
			fm.BnfValidYear3.value = ''; 
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
			//document.getElementById("BnfReadOnly37").disabled=true;
			document.getElementById("BnfReadOnly38").readOnly=true; 
			document.getElementById("BnfReadOnly39").disabled=true;
			document.getElementById("BnfReadOnly40").disabled=true;
			document.getElementById("BnfReadOnly41").disabled=true;
			 
		
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
			//document.getElementById("BnfReadOnly37").disabled=false;
			document.getElementById("BnfReadOnly38").readOnly=false; 
			document.getElementById("BnfReadOnly39").disabled=false;
			document.getElementById("BnfReadOnly40").disabled=false;
			document.getElementById("BnfReadOnly41").disabled=false;
			 
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
			//fm.BeneficiaryIndicator.value = 'N';
			
			fm.BnfValidYear1.value = '2020-09-16'; 
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
			document.getElementById("Ins11").disabled=true; 
			document.getElementById("Ins12").readOnly=true;
		

//被保人信息 
	//被保人姓名
	fm.InsuName.value = fm.ApplName.value;
	//被保人性别
	fm.InsuSex.value = fm.ApplSex.value;
	fm.InsuSexh.value = fm.ApplSex.value;
	//被保人证件类型
	fm.InsuIDType.value = fm.ApplIDType.value;
	fm.InsuIDTypeh.value = fm.ApplIDType.value ;
	//被保人证件号码
	fm.InsuIDNo.value = fm.ApplIDNo.value;
	//被保人生日
	fm.InsuBirthday.value = fm.ApplBirthday.value;
	//被保人电子邮箱
	fm.InsuEmail.value = fm.ApplEmail.value;
	//被保人通讯地址	
	fm.InsuAddress.value = fm.ApplAddress.value;
	//被保人邮政编码
	fm.InsuZipCode.value = fm.ApplZipCode.value;
	//被保人家庭电话 
	fm.InsuPhone.value = fm.ApplPhone.value;
	//被保人移动电话
	fm.InsuMobile.value = fm.ApplMobile.value;
	//被保人职业代码
	fm.InsuJobCode.value = fm.ApplJobCode.value;
	
	//健康告知
	fm.HealthFlag.value = 'N';
	fm.ApplRelaToInsured.value = '1';
	fm.all('ApplRelaToInsured').value='1'; 
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
			document.getElementById("Ins11").disabled=false;
			document.getElementById("Ins12").readOnly=false;

//被保人信息
	//被保人姓名
			//被保人姓名
			fm.InsuName.value = '被保人1';
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
			fm.HealthFlag.value = 'N';
			//被保人职业代码
			fm.InsuJobCode.value = '01';
	
		} 
	}



function afterCodeSelect( cCodeName, Field )
{  
	if(cCodeName=='Relation'&&Field.value=='5')
	{
		fm.InsuName.value= fm.ApplName.value;
		fm.InsuSex.value= fm.ApplSex.value;
		fm.InsuBirthday.value= fm.ApplBirthday.value;
		fm.InsuIDType.value= fm.ApplIDType.value;
		fm.InsuIDNo.value= fm.ApplIDNo.value;
		//fm.InsuredNationality.value= fm.AppntNationality.value;
		fm.InsuAddress.value= fm.ApplAddress.value;
		fm.InsuZipCode.value= fm.ApplZipCode.value;
		fm.InsuEmail.value= fm.ApplEmail.value;
		fm.InsuPhone.value=fm.ApplPhone.value;
		fm.InsuMobile.value=fm.ApplMobile.value;
		fm.InsuJobCode.value = fm.ApplJobCode.value;

	}
}

//提交，保存按钮对应操作
function submitForm()
{
	// alert("a");
  if(!verifyInput())
  {
  	return false;
  }
 
  
  var sTranFlagCode = fm.all('tranFlagCode').value;
  
	if(sTranFlagCode == '01'){
	fm.action = "./LIANTestYBTSave.jsp";
	}
	if(sTranFlagCode == '02'){
	fm.action = "./LIANTestYBTConfirmSave.jsp";
	}
	//if(sTranFlagCode == '1115'){
	//fm.action = "./LIANTestYBTNewContConcelSave.jsp";
	//}
	//if(sTranFlagCode == '1011'){
	//fm.action = "./TestYBTRePrintSave.jsp";
	//}
	if(sTranFlagCode == '03'){
	fm.action = "./LIANTestYBTContConcelSave.jsp";
	}
 
  	//alert("a");
  	
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  //alert("a");

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
	 
	var vBankDate = fm.BankDate.value; 
	if (vBankDate.trim() == ""){    
		alert("银行端交易日期不能为空");
		return false; 
	}   
	  
	var vTransrNo = fm.TransrNo.value; 
	if (vTransrNo.trim() == ""){    
		alert("交易流水号不能为空");
		return false; 
	}   
	
	var vZoneNo = fm.ZoneNo.value; 
	if (vZoneNo.trim() == ""){    
		alert("地区代码不能为空");
		return false; 
	} 
	
	var vBrNo = fm.BrNo.value; 
	if (vBrNo.trim() == ""){    
		alert("网点代码不能为空");
		return false; 
	} 
	
	var vTellerNo = fm.TellerNo.value; 
	if (vTellerNo.trim() == ""){    
		alert("柜员代码不能为空");
		return false; 
	}  
	
	var vProposalContNo = fm.ProposalContNo.value; 
	if (vProposalContNo.trim() == ""){    
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
	
	fm.action = "./LIANTestYBTConfirmSave.jsp";
	fm.ConfirmFlag.value = "1";
	fm.submit();	
	fm.action = vOldAction;
}


function cancleTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./LIANTestYBTContConcelSave.jsp";
	fm.ConfirmFlag.value = "0";
	fm.submit();	
	fm.action = vOldAction;
}

function cancelTransaction()
{
	var vOldAction = fm.action;
	
	fm.action = "./LIANTestYBTContConcelSave.jsp";
	fm.ConfirmFlag.value = "0";
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
