//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug="1";
var mOperate="";
var showInfo;
var updateAgentCom;
var strRT=""; //�洢�����˴����¼
var old_AgentGroup="";
var new_AgentGroup="";
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//�ύǰ��У�顢����
function initBox(){
	fm.PolicyNo.value = "";
	fm.CostAmount.value = "";
	fm.PremType.value = "";
	fm.PremName.value = "";
	fm.PolicyYear.value ="";
	fm.ProductType.value ="";
	fm.ProductName.value ="";
}
function reset(){
	initBox();
}
//У����Ч����
function isDigit(s) {
	var patrn = /^[0-9]{1,3}$/;
	if (!patrn.exec(s))
		return false
	return true
}
//���������Ч����
function isPrem(s) {
	var patrn = /^[0-9]{0,16}[.]{0,1}[0-9]{0,4}$/;
	if (!patrn.exec(s))
		return false
	return true
}
function checkData(){
	var policyNo = fm.PolicyNo.value;
	var costAmount = fm.CostAmount.value;
	var premType = fm.PremType.value;
	var premName = fm.PremName.value;
	var policyYear = fm.PolicyYear.value;
	var productType = fm.ProductType.value;
	var productName = fm.ProductName.value;
	var tranDay = fm.TranDay.value;
	
	if(policyNo == null ||policyNo==""){
		alert("�����뱣����");
		return false;
	}
	if (costAmount == null || costAmount=="") {
		alert("��������ý��");
		return false;
	}
	if(!isPrem(costAmount)){
		alert("���ý����������");
		return false;
	}
	if (premType == null || premType=="") {
		alert("��ѡ���������");
		return false;
	}
	if (premName == null || premName=="") {
		alert("��ѡ�񱣷�����");
		return false;
	}
	if (policyYear == null || policyYear=="") {
		alert("�����뱣�����");
		return false;
	}
	if(!isDigit(policyYear)){
		alert("���������������");
		return false;
	}
	if (productType == null || productType=="") {
		alert("��ѡ���Ʒ����");
		return false;
	}
	if (productName == null || productName=="") {
		alert("��ѡ���Ʒ����");
		return false;
	}
	if (tranDay == null || tranDay=="") {
		alert("��ѡ��������");
		return false;
	}
	return true;
}

function creatClick(){
	
		if (!checkData()) {
		return false;
	}
	var mShowMsg = "���ڴ��������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.submit();
}



/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr,Content)
{
	showInfo.close();
	
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }
    else
    {
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
        initBox();
    
    } 
}

function showPremTypeCode() {
	fm.PremType.value="";
	fm.PremName.value="";
	
	showCodeList('c_premtype',[fm.PremType,fm.PremName],[0,1],null,null,null,1,null,1);
} 

function showPremTypeCodeKey(){
	fm.PremType.value="";
	fm.PremName.value="";
	showCodeListKey('c_premtype',[fm.PremType,fm.PremName],[0,1],null,null,null,1,null,1);
}

function showProTypeCode() {
	fm.ProductType.value="";
	fm.ProductName.value="";
	
	showCodeList('citi_procode',[fm.ProductType,fm.ProductName],[0,1],null,null,null,1,null,1);
} 

function showProTypeCodeKey(){
	fm.ProductType.value="";
	fm.ProductName.value="";
	showCodeListKey('citi_procode',[fm.ProductType,fm.ProductName],[0,1],null,null,null,1,null,1);
}