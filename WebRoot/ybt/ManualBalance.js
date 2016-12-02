var cShowInfo;

function showTranCom() {
	fm.TranComName.value='';
	fm.FuncFlag.value='';
	fm.FuncFlagName.value='';
	return showCodeList('trancom_bank',[fm.TranCom,fm.TranComName],[0,1],null,null,null,1,null,1);
}
function showTranComKey() {
	fm.TranComName.value='';
	fm.FuncFlag.value='';
	fm.FuncFlagName.value='';
	return showCodeListKey('trancom_bank',[fm.TranCom,fm.TranComName],[0,1],null,null,null,1,null,1);
}

function showBalanceFlag() {
	var vTranCom = trim(fm.TranCom.value);
	if(vTranCom == ''){
		alert("请首先选择对账银行!")
		return false;
	}
	return showCodeList('blctranstype',[fm.FuncFlag,fm.FuncFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}
function showBalanceFlagKey() {
	var vTranCom = trim(fm.TranCom.value);
	if(vTranCom == ''){
		alert("请首先选择对账银行!")
		return false;
	}
	return showCodeListKey('blctranstype',[fm.FuncFlag,fm.FuncFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}

function deal() {
	if(!verifyDate()) {
		return false;
	} 
	
	var mShowMsg = "正在处理，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg;
	cShowInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.submit();
}

function afterSubmit(pResultMsg) {
	cShowInfo.close();
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + pResultMsg;
	showModalDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
}

function verifyDate(){
	var vTranCom = trim(fm.TranCom.value);
	var vFuncFlag = trim(fm.FuncFlag.value);
	var vTranDate = trim(fm.TranDate.value);
	
	
	if(vTranCom == ''){
		alert("请选择对账银行!");
		return false;
	}
	if(vFuncFlag == ''){
		alert("请选择交易类型!");
		return false;
	}
	if(vTranDate == ''){
		alert("请选择对账日期!");
		return false;
	}
	return true;
	
}