function WTranFlag(){
	var sTranFlagCode = fm.all('tranFlagCode').value;
	if(sTranFlagCode == 'P53816141'){
		//试算保险产品
		Caculate();
	}
	if(sTranFlagCode == 'P53819142'){
		//当日撤单
		ContConcel();
	}
	else if(sTranFlagCode == 'P53818154'){
		//当日冲正
		ContRollBack();
	}
	else if(sTranFlagCode == 'P53819184'){
		//保单重打
		ContRePrint();
	}
	else if(sTranFlagCode == 'P53818152'){
		//绿灯交易
		GreenTest();
	}else if(sTranFlagCode == 'SPE801'){
		//退保交易传递
		ContCancelBlc();
	}else if(sTranFlagCode == 'P538191A2'){
		//重空核对
		ContCheck();
	}else if(sTranFlagCode == 'BAT900'){
		//批量查询
		BatQuery();
	}else if(sTranFlagCode == 'SPE010'){
		//签约交易
		ContSigal();
	}else if(sTranFlagCode == 'SPE013'){
		//解约交易
		ContDismiss();
	}
}
//失算保险产品
function Caculate(){
	//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//批量查询
function BatQuery(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//签约交易
function ContSigal(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="";
	document.getElementById("divSignalimg").src="../common/images/butExpand.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//解约交易
function ContDismiss(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="";
	document.getElementById("divSignalimg").src="../common/images/butExpand.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
function ContConcel(){
	//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="";
	document.getElementById("divContInfoimg").src="../common/images/butExpand.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=false;
	document.getElementById("ProviderFormNumber").disabled=true;
	//document.getElementById("OriginalProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=false;
	//document.getElementById("HOAppFormNumber").disabled=true;
}
//绿灯测试
function GreenTest(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="";
	document.getElementById("divDateimg").src="../common/images/butExpand.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//退保信息传递
function ContCancelBlc(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="";
	document.getElementById("divCancelTranforimg").src="../common/images/butExpand.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
//重空核对
function ContCheck(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="";
	document.getElementById("divCheckimg").src="../common/images/butExpand.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	
	document.getElementById("OldTransRefGUID").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=true;
	document.getElementById("PolNumber").disabled=true;
}
function ContRollBack(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="";
	document.getElementById("divContInfoimg").src="../common/images/butExpand.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	//原交易流水号
	document.getElementById("OldTransRefGUID").disabled=false;
	//保单号
	document.getElementById("PolNumber").disabled=true;
	//投保单号
	//document.getElementById("HOAppFormNumber").disabled=true;
	//保单印刷号
	document.getElementById("ProviderFormNumber").disabled=true;
		//旧保单印刷号
	//document.getElementById("OriginalProviderFormNumber").disabled=true;
}
function ContRePrint(){
		//报文头信息
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="";
	document.getElementById("divContInfoimg").src="../common/images/butExpand.gif";
	//试算保险产品
	document.getElementById("divCaculate").style.display="none";
	document.getElementById("divCaculateimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="none";
	document.getElementById("divDateimg").src="../common/images/butCollapse.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
	document.getElementById("OldTransRefGUID").disabled=false;
	document.getElementById("PolNumber").disabled=false;
	//document.getElementById("HOAppFormNumber").disabled=true;
	document.getElementById("ProviderFormNumber").disabled=false;
	//document.getElementById("OriginalProviderFormNumber").disabled=true;
}

function WRiskFlag(a){
	//var sPolNumber = fm.all('PolNumber').value;
	var stranFlagCode = fm.all('tranFlagCode').value;
	if(stranFlagCode == '1015'){
		//var vSQL = "select bak2 from cont where state = '2' and contno = '"+ sPolNumber + "'";
		//var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
		//document.getElementById("IdOriginalProviderFormNumber").readOnly="true";
	}
	if(stranFlagCode == '1011'){
		//document.getElementById("IdOriginalProviderFormNumber").readOnly="false";
	}
}

//提交，保存按钮对应操作
function submitForm()
{
 if(!verifyInputForm())
  {
  	return false;
  }
 	document.getElementById("OldTransRefGUID").disabled=false;
	document.getElementById("PolNumber").disabled=false;
	document.getElementById("ProviderFormNumber").disabled=false;
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

function  verifyInputForm(){
	var stranFlagCode = fm.all('tranFlagCode').value;
	if(stranFlagCode == 'P538191A2'){
		  var   num1=fm.BkVchNo1.value;   
		  var   num2=fm.BkVchNo2.value;   
		  if(parseInt(num1)>parseInt(num2))  
		  {   
				alert("重空起始号不能大于结束号!");
				return false; 
		  }
		  if(num1.trim() == "")  
		  {   
			  alert("重空起始号不能为空!");
			  return false; 
		  }
		  if(num2.trim() == "")  
		  {   
			  alert("重空结束号不能为空!");
			  return false; 
		  }
	}
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
	
	//	var vPolNumber = fm.PolNumber.value; 
	//if (vPolNumber.trim() == ""){    
	//	alert("保单号不能为空");
	//	return false; 
	//} 
	
	var vPolNumber = fm.PolNumber.value; 
	var vtranFlagCode = fm.tranFlagCode.value;
	var vOldTransRefGUID = fm.OldTransRefGUID.value;
	var vProviderFormNumber = fm.ProviderFormNumber.value;
	//var vOriginalProviderFormNumber = fm.OriginalProviderFormNumber.value;
	//alert(vtranFlagCode);
	if (vPolNumber.trim() == "" && (vtranFlagCode.trim() == "P53819142")){    
		alert("当日撤单交易时保单号不能为空");
		return false;   
	} 
//	if (vOldTransRefGUID.trim() == "" && (vtranFlagCode.trim() == "P53819142")){    
//		alert("当日撤单交易时原交易流水号不能为空");
//		return false;   
//	} 
		if (vPolNumber.trim() == "" && (vtranFlagCode.trim() == "P53819184")){    
		alert("保单重打交易时保单号不能为空");
		return false;   
	}

		if (vProviderFormNumber.trim() == "" && (vtranFlagCode.trim() == "P53819184")){    
		alert("保单重打交易时单证号不能为空");
		return false;
	}
	 
	return true;
}  
