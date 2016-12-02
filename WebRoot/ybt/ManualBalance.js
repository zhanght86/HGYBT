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
		alert("������ѡ���������!")
		return false;
	}
	return showCodeList('blctranstype',[fm.FuncFlag,fm.FuncFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}
function showBalanceFlagKey() {
	var vTranCom = trim(fm.TranCom.value);
	if(vTranCom == ''){
		alert("������ѡ���������!")
		return false;
	}
	return showCodeListKey('blctranstype',[fm.FuncFlag,fm.FuncFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}

function deal() {
	if(!verifyDate()) {
		return false;
	} 
	
	var mShowMsg = "���ڴ��������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
		alert("��ѡ���������!");
		return false;
	}
	if(vFuncFlag == ''){
		alert("��ѡ��������!");
		return false;
	}
	if(vTranDate == ''){
		alert("��ѡ���������!");
		return false;
	}
	return true;
	
}