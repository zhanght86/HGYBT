function RiskFlag(a){
	var sTranFlagCode = fm.all('ProductCode').value;
	if(sTranFlagCode == '005'){	
		document.getElementById("C20001").style.display='none'; 
		fm.PayOutDurationMode.value='2';
		fm.PayoutStart.value='60';
		fm.PayoutDuration.value='20';
		
		fm.PaymentMode.value='1';
		fm.PaymentDuration.value='20';
		
		document.getElementById("BnfFlag").checked = false;
		setBnfFlag(document.getElementById("BnfFlag"));
		
//		document.getElementById("InsFlag").checked = false;
//		setInsFlag(document.getElementById("InsFlag"));
		
	}else if(sTranFlagCode == '004'){	
		document.getElementById("C20001").style.display=''; 
		fm.ContractNo.value='20130129';
		fm.LoanStartDate.value=fm.TransExeDate.value;
		fm.LoanEndDate.value=fm.TransExeDate.value;
		fm.LoanAccountNo.value='6227001700130252734';
		fm.LoanAmount.value='100000000';
		fm.FaceAmount.value='100000000';
		fm.ContractEffDate.value=fm.TransExeDate.value;
		fm.ContractEndDate.value=fm.TransExeDate.value;
		
		fm.ModalPremAmt.value='50000';
		fm.InitCovAmt.value='100000000';
		fm.Duration.value='1';
		
		fm.LoanProductCode.value='17';
		
		document.getElementById("BnfFlag").checked = true;
		setBnfFlag(document.getElementById("BnfFlag"));
		
//		document.getElementById("InsFlag").checked = true;
//		setInsFlag(document.getElementById("InsFlag"));
		
	}else if(sTranFlagCode == '008'){	
		document.getElementById("C20001").style.display=''; 
		fm.ContractNo.value='20131201';
		fm.LoanStartDate.value=fm.TransExeDate.value;
		fm.LoanEndDate.value=fm.TransExeDate.value;
		fm.LoanAccountNo.value='6227001700130252734';
		fm.LoanAmount.value='100000000';
		fm.FaceAmount.value='100000000';
		fm.ContractEffDate.value=fm.TransExeDate.value;
		fm.ContractEndDate.value=fm.TransExeDate.value;
		
		fm.ModalPremAmt.value='50000';
		fm.InitCovAmt.value='100000000';
		fm.Duration.value='1';
		
		fm.LoanProductCode.value='17';
		
		document.getElementById("BnfFlag").checked = true;
		setBnfFlag(document.getElementById("BnfFlag"));
		
//		document.getElementById("InsFlag").checked = true;
//		setInsFlag(document.getElementById("InsFlag"));
		
	}else{		
		document.getElementById("C20001").style.display='none'; 
		fm.PayOutDurationMode.value='';
		fm.PayoutStart.value='';
		fm.PayoutDuration.value='';
		
		fm.PaymentMode.value='5';
		fm.PaymentDuration.value='1000';
		
		document.getElementById("BnfFlag").checked = false;
		setBnfFlag(document.getElementById("BnfFlag"));
		
//		document.getElementById("InsFlag").checked = false;
//		setInsFlag(document.getElementById("InsFlag"));
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
	
  if(!verifyInput2())
  {
  	return false;
  }
  
 
  
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //提交
}
 
function  verifyInput2(){

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
	 if(!verifyInput() ) {
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
