
function WTranFlag(c){
	//var sPolNumber = fm.all('PolNumber').value;
	var stranFlagCode = fm.all('tranFlagCode').value;
	if(stranFlagCode == '03'){
		document.getElementById('div1').style.display="none";
		document.getElementById('div11').style.display="none";
		document.getElementById('div2').style.display="";
		document.getElementById('div22').style.display="";
		document.getElementById('div3').style.display="";
		document.getElementById('div33').style.display="";
		fm.tranFlagCode.value = '03';
	}
	if(stranFlagCode == '1011'){
		//var vSQL = "select bak2 from cont where state = '2' and contno = '"+ sPolNumber + "'";
		//var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
		document.getElementById('div1').style.display="";
		document.getElementById('div2').style.display="none";
		document.getElementById('div11').style.display="";
		document.getElementById('div22').style.display="none";
		 //document.getElementById('APPDOCNOId').disabled=true;
		//document.getElementById('IdProviderFormNumber').disabled=false;
		fm.tranFlagCode.value = '1011';
	}
	if(stranFlagCode == '04'){
	document.getElementById('div2').style.display="none";
	document.getElementById('div3').style.display="none";
	document.getElementById('div33').style.display="none";
	document.getElementById('div1').style.display="none";
	document.getElementById('div22').style.display="none";
	document.getElementById('div11').style.display="none";
	 //document.getElementById('IdProviderFormNumber').disabled=true;
		//document.getElementById('APPDOCNOId').disabled=false;
		fm.tranFlagCode.value = '04';
	}
}

//提交，保存按钮对应操作
function submitForm()
{

 if(!verifyInputForm())
  {
  	return false;
  }
var sTranFlagCode = fm.all('tranFlagCode').value;
	
	fm.action = "./ABCWriteOffSave.jsp";
	
 
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作




function afterSubmit( FlagStr, content,strContNo,strCValiDate,strPayMode,strAppntName,strAppntSex,strAppntNo,strAppntBirthday,strInsuredName,strInsuredNo,strSex,strBirthday,strBnfLot,strName,strBnfGrade,strRiskName,strDuration,strPaymentDuration,strInitCovAmt,strPrem,strRiskName1,strDuration1,strPaymentDuration1,strInitCovAmt1,strPrem1,strRiskName2,strDuration2,strPaymentDuration2,strInitCovAmt2,strPrem2)
{ 	
	  showInfo.close();
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 if(FlagStr==1)
	 {
	 // alert("投保人，被保人信息");
	//  alert(strContNo);
	//  alert(strCValiDate);
	//  alert(strPayMode);
	//  alert(strAppntName);
	 // alert(strAppntSex);
	 // alert(strAppntNo);
	//  alert(strAppntBirthday);
	//  alert(strInsuredName);
	 // alert(strInsuredNo);
	 // alert(strSex);
	//  alert(strBirthday);
	//  alert(strBnfLot);
	//  alert(strName);
	 // alert(strBnfGrade);
	  
	 // alert("主险信息"); 
	 // alert(strRiskName);
	//  alert(strDuration);
	 // alert(strPaymentDuration);
	//  alert(strInitCovAmt);
	 // alert(strPrem);
	  
	//  alert("附加险1信息");
	//  alert(strRiskName1); 
	//  alert(strDuration1);
	//  alert(strPaymentDuration1);
	//  alert(strInitCovAmt1);
	//  alert(strPrem1);
	  
	//  alert("附加险2信息");
	//  alert(strRiskName2);
	 // alert(strDuration2);
	//  alert(strPaymentDuration2);
	 // alert(strInitCovAmt2);
	  //alert(strPrem2);
	  
	 // alert("结束");
	//  window.open("./showpolinfoInput.jsp?mContNo="+strContNo+"&mCValiDate="+strCValiDate+"&mPayMode="+strPayMode+"&mAppntName="+strAppntName+"&mAppntSex="+strAppntSex+"&mAppntNo="+strAppntNo+"&mAppntBirthday="+strAppntBirthday+"&mInsuredName="+strInsuredName+"&mInsuredNo="+strInsuredNo+"&mSex="+strSex+"&mBirthday="+strBirthday+"&mBnfLot="+strBnfLot+"&mName="+strName+"&mBnfGrade="+strBnfGrade+"&mRiskName="+strRiskName+"&mDuration="+strDuration+"&mPaymentDuration="+strPaymentDuration+"&mInitCovAmt="+strInitCovAmt+"&mPrem="+strPrem+"&mRiskName1="+strRiskName1+"&mDuration1="+strDuration1+"&mPaymentDuration1="+strPaymentDuration1+"&mInitCovAmt1="+strInitCovAmt1+"&mPrem1="+strPrem1+"&mRiskName2="+strRiskName2+"&mDuration2="+strDuration2+"&mPaymentDuration2="+strPaymentDuration2+"&mInitCovAmt2="+strInitCovAmt2+"&mPrem2="+strPrem2+"");
	}
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

function  verifyInputForm(){
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
	
	var vtranFlagCode = fm.tranFlagCode.value; 
	if (vtranFlagCode.trim() == ""){    
		alert("交易码不能为空");
		return false; 
	} 
	
	var vTeller = fm.Teller.value; 
	if (vTeller.trim() == ""){    
		alert("柜员代码不能为空");
		return false; 
	}  
	
	var vPolNumber = fm.PolNumber.value; 
	if (vPolNumber.trim() == ""){    
		alert("保单号不能为空");
		return false; 
	} 
	
	var stranFlagCode = fm.all('tranFlagCode').value;
	var vAPPDOCNO = fm.APPDOCNO.value; 
	if(stranFlagCode == '03'){
		if (vAPPDOCNO.trim() == ""){    
			alert("投保单号不能为空");
			return false; 
		} 
	}
	
	//var vPolNumber = fm.PolNumber.value; 
	//var vtranFlagCode = fm.tranFlagCode.value;
	//var vProviderFormNumber = fm.ProviderFormNumber.value;
	//var vAPPDOCNO = fm.APPDOCNO.value;
	//var vOriginalProviderFormNumber = fm.OriginalProviderFormNumber.value;
	

	//	if (vProviderFormNumber.trim() == "" && (vtranFlagCode.trim() == "1011")){    
	//	alert("保单重打交易时单证号不能为空");
	//	return false;
	//}
	//	 if (vAPPDOCNO.trim() == "" && (vtranFlagCode.trim() == "1012")){    
	//	alert("保单自动冲正交易时投保单号不能为空");
	//	return false;  
	//}
	 
	return true;
}  
