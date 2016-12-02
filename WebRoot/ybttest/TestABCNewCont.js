function ShowRelaToInsured() {
		fm.RelaToInsured.value="";
		fm.RelaToInsuredName.value="";		
		showCodeList('relation',[fm.RelaToInsured,fm.RelaToInsuredName],[0,1],null,null,null,1,null,1);
}
function ShowRelationToInsured1() {
	fm.RelationToInsured1.value="";
	fm.RelationToInsuredName1.value="";		
	showCodeList('relation',[fm.RelationToInsured1,fm.RelationToInsuredName1],[0,1],null,null,null,1,null,1);
}
function ShowRelationToInsured2() {
	fm.RelationToInsured2.value="";
	fm.RelationToInsuredName2.value="";		
	showCodeList('relation',[fm.RelationToInsured2,fm.RelationToInsuredName2],[0,1],null,null,null,1,null,1);
}
function ShowRelationToInsured3() {
	fm.RelationToInsured3.value="";
	fm.RelationToInsuredName3.value="";		
	showCodeList('relation',[fm.RelationToInsured3,fm.RelationToInsuredName3],[0,1],null,null,null,1,null,1);
}
function ShowAccBankNAME(){
	fm.AccBankNAME.value="";
	fm.AccBankNAMEName.value="";		
	showCodeList('accbankname',[fm.AccBankNAME,fm.AccBankNAMEName],[0,1],null,null,null,1,null,1);
}
function ShowJobCode(){
	fm.JobCode.value="";
	fm.JobCodeName.value="";		
	showCodeList('jobcode',[fm.JobCode,fm.JobCodeName],[0,1],null,null,null,1,null,1);
}
function BShowJobCode(){
	fm.BJobCode.value="";
	fm.BJobCodeName.value="";		
	showCodeList('jobcode',[fm.BJobCode,fm.BJobCodeName],[0,1],null,null,null,1,null,1);
}
function ShowIDTypeCode(){
	fm.IDType.value="";
	fm.IDTypeName.value="";		
	showCodeList('idtype',[fm.IDType,fm.IDTypeName],[0,1],null,null,null,1,null,1);
}
function BShowIDTypeCode(){
	fm.BIDType.value="";
	fm.BIDTypeName.value="";		
	showCodeList('idtype',[fm.BIDType,fm.BIDTypeName],[0,1],null,null,null,1,null,1);
}
function ShowIDTypeCode1(){
	fm.IDType1.value="";
	fm.IDTypeName1.value="";		
	showCodeList('idtype',[fm.IDType1,fm.IDTypeName1],[0,1],null,null,null,1,null,1);
}
function ShowIDTypeCode2(){
	fm.IDType2.value="";
	fm.IDTypeName2.value="";		
	showCodeList('idtype',[fm.IDType2,fm.IDTypeName2],[0,1],null,null,null,1,null,1);
}
function ShowIDTypeCode3(){
	fm.IDType3.value="";
	fm.IDTypeName3.value="";		
	showCodeList('idtype',[fm.IDType3,fm.IDTypeName3],[0,1],null,null,null,1,null,1);
}

function TranFlag(c){
	var sTranFlagCode = fm.FunctionFlag.value;
	if(sTranFlagCode == '01'){
		NewContInput();
	}
	if(sTranFlagCode == '02'){
		NewContConfirm();
	}
}

function NewContInput(){
	
	 window.location.reload();

	initInputBox();
	document.getElementById("ReqsrNo").disabled=true;
	document.getElementById("02Prem").disabled=true;
}

function NewContConfirm(){
	initContConfirm();
	document.getElementById("ReqsrNo").disabled=false;
	document.getElementById("02Prem").disabled=false;
}


function setBnfFlag(BnfFlag){ 
		if(BnfFlag.checked == true){
			
			document.getElementById("BnfReadOnly1").readOnly=true; 
			document.getElementById("BnfReadOnly2").readOnly=true; 
			document.getElementById("BnfReadOnly3").readOnly=true; 
			document.getElementById("BnfReadOnly4").readOnly=true; 
			document.getElementById("BnfReadOnly5").readOnly=true; 
			document.getElementById("BnfReadOnly6").readOnly=true; 
			document.getElementById("BnfReadOnly7").readOnly=true;
			document.getElementById("BnfReadOnly8").readOnly=true; 
			document.getElementById("BnfReadOnly9").readOnly=true; 
			document.getElementById("BnfReadOnly10").readOnly=true; 
			document.getElementById("BnfReadOnly11").readOnly=true; 
			document.getElementById("BnfReadOnly12").readOnly=true; 
			document.getElementById("BnfReadOnly13").readOnly=true; 
			document.getElementById("BnfReadOnly14").readOnly=true; 
			document.getElementById("BnfReadOnly15").readOnly=true; 
			document.getElementById("BnfReadOnly16").readOnly=true; 
			document.getElementById("BnfReadOnly17").readOnly=true;
			document.getElementById("BnfReadOnly18").readOnly=true; 
			document.getElementById("BnfReadOnly19").readOnly=true; 
			document.getElementById("BnfReadOnly20").readOnly=true; 
			document.getElementById("BnfReadOnly21").readOnly=true; 
			document.getElementById("BnfReadOnly22").readOnly=true; 
			document.getElementById("BnfReadOnly23").readOnly=true; 
			document.getElementById("BnfReadOnly24").readOnly=true; 
			document.getElementById("BnfReadOnly25").readOnly=true; 
			document.getElementById("BnfReadOnly26").readOnly=true; 
			document.getElementById("BnfReadOnly27").readOnly=true;
			document.getElementById("BnfReadOnly28").readOnly=true; 
			document.getElementById("BnfReadOnly29").readOnly=true; 
			document.getElementById("BnfReadOnly30").readOnly=true; 
			
			
				//受益人信息
			//受益人类型
			fm.Type1.value='1';
			//受益人姓名
			fm.Name1.value = '法定';
			//受益人性别 
			fm.Sex1.value = '';
			//受益人出生日期
			fm.Birthday1.value = '';
			//受益人证件类型
			fm.IDType1.value = '';
			fm.IDTypeName1.value='';
			//受益人证件号码
			fm.IDNo1.value = '';
			//受益人与被保人的关系  
			fm.RelationToInsured1.value='';	
			fm.RelationToInsuredName1.value='';
			//受益顺序
			fm.BnfGrade1.value = ''; 	
			//受益比例 
			fm.BnfLot1.value = '100';
			//通讯地址
			fm.Address1.value='';
			//受益人是否为法定标志 
			fm.BeneficiaryIndicator.value='Y';
		
		}else{ 
			
			
			document.getElementById("BnfReadOnly1").readOnly=false; 
			document.getElementById("BnfReadOnly2").readOnly=false; 
			document.getElementById("BnfReadOnly3").readOnly=false; 
			document.getElementById("BnfReadOnly4").readOnly=false; 
			document.getElementById("BnfReadOnly5").readOnly=false; 
			document.getElementById("BnfReadOnly6").readOnly=false; 
			document.getElementById("BnfReadOnly7").readOnly=false;
			document.getElementById("BnfReadOnly8").readOnly=false; 
			document.getElementById("BnfReadOnly9").readOnly=false; 
			document.getElementById("BnfReadOnly10").readOnly=false; 
			document.getElementById("BnfReadOnly11").readOnly=false; 
			document.getElementById("BnfReadOnly12").readOnly=false; 
			document.getElementById("BnfReadOnly13").readOnly=false; 
			document.getElementById("BnfReadOnly14").readOnly=false; 
			document.getElementById("BnfReadOnly15").readOnly=false; 
			document.getElementById("BnfReadOnly16").readOnly=false; 
			document.getElementById("BnfReadOnly17").readOnly=false;
			document.getElementById("BnfReadOnly18").readOnly=false; 
			document.getElementById("BnfReadOnly19").readOnly=false; 
			document.getElementById("BnfReadOnly20").readOnly=false; 
			document.getElementById("BnfReadOnly21").readOnly=false; 
			document.getElementById("BnfReadOnly22").readOnly=false; 
			document.getElementById("BnfReadOnly23").readOnly=false; 
			document.getElementById("BnfReadOnly24").readOnly=false; 
			document.getElementById("BnfReadOnly25").readOnly=false; 
			document.getElementById("BnfReadOnly26").readOnly=false; 
			document.getElementById("BnfReadOnly27").readOnly=false;
			document.getElementById("BnfReadOnly28").readOnly=false; 
			document.getElementById("BnfReadOnly29").readOnly=false; 
			document.getElementById("BnfReadOnly30").readOnly=false; 
			
		
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
		}
	}
	
	function setInsFlag(InsFlag){
		if(InsFlag.checked == true){
			
			var vRelaToInsured = fm.RelaToInsured.value;
			if(vRelaToInsured!='1'){
				alert("投保人信息项中的与被保人关系为本人时才可选择此项");
				InsFlag.checked=false;
				return false;
			}
			
		//	showPage(this,insured);
		  
//被保人信息 
			fm.BName.value = fm.Name.value;
			//性别
			fm.BSex.value = fm.Sex.value;
			//证件类型
			fm.BIDType.value = fm.IDType.value;
			//证件号码
			fm.BIDNo.value = fm.IDNo.value;
			//生日
			fm.BBirthday.value = fm.Birthday.value;
			//联系电话           
			fm.BPhone.value=fm.Phone.value;
			//手机号        
			fm.BMobile.value=fm.Mobile.value;
			//通讯地址            
			fm.BAddress.value=fm.Address.value;
			//通讯邮政编码        
			fm.BZipCode.value=fm.ZipCode.value;
			//国籍代码
			fm.BCounty.value=fm.County.value;	
			//证件有效期          
			fm.BValidYear.value=fm.ValidYear.value
			//电子邮件            
			fm.BEmail.value=fm.Email.value;
			//职业代码            
			fm.BJobCode.value=fm.JobCode.value;
			fm.BJobCodeName.value=fm.JobCodeName.value;
			
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
			
		}  
		else 
		{   
		//showPage(this,insured);
			

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
		} 
	}



//提交，保存按钮对应操作
function submitForm()
{ 	 
  if(!verifyInput())
  {
  	return false;
  }
  
  fm.TransrNo.value=fm.NewTransNo.value;
  var sTranFlagCode = fm.all('FunctionFlag').value;
	if(sTranFlagCode == '01'){
	fm.action = "./TestABCNewContSave.jsp";
	}
	if(sTranFlagCode == '02'){

	fm.action = "./TestABCNewContConfirmSave.jsp";
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
	 
	var vTransExeDate = fm.BankDate.value; 
	if (vTransExeDate.trim() == ""){    
		alert("银行端交易日期不能为空");
		return false; 
	}   
	  
	var vTransRefGUID = fm.TransrNo.value; 
	if (vTransRefGUID.trim() == ""){    
		alert("交易流水号不能为空");
		return false; 
	}   
	
	var vRegionCode = fm.ZoneNo.value; 
	if (vRegionCode.trim() == ""){    
		alert("地区代码不能为空");
		return false; 
	} 
	
	var vBranch = fm.BrNo.value; 
	if (vBranch.trim() == ""){    
		alert("网点代码不能为空");
		return false; 
	} 
	
	var vTeller = fm.TellerNo.value; 
	if (vTeller.trim() == ""){    
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



