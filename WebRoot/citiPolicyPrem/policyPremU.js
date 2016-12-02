//               该文件中包含客户端需要处理的函数和事件
var mDebug="1";
var mOperate="";
var showInfo;
var updateAgentCom;
var strRT=""; //存储代理人代码记录
var old_AgentGroup="";
var new_AgentGroup="";
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//提交前的校验、计算
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
//校验有效数字
function isDigit(s) {
	var patrn = /^[0-9]{1,3}$/;
	if (!patrn.exec(s))
		return false
	return true
}
//检验费用有效数字
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
		alert("请输入保单号");
		return false;
	}
	if (costAmount == null || costAmount=="") {
		alert("请输入费用金额");
		return false;
	}
	if(!isPrem(costAmount)){
		alert("费用金额输入有误");
		return false;
	}
	if (premType == null || premType=="") {
		alert("请选择费用类型");
		return false;
	}
	if (premName == null || premName=="") {
		alert("请选择保费类型");
		return false;
	}
	if (policyYear == null || policyYear=="") {
		alert("请输入保单年度");
		return false;
	}
	if(!isDigit(policyYear)){
		alert("保单年度输入有误");
		return false;
	}
	if (productType == null || productType=="") {
		alert("请选择产品类型");
		return false;
	}
	if (productName == null || productName=="") {
		alert("请选择产品类型");
		return false;
	}
	if (tranDay == null || tranDay=="") {
		alert("请选择交易日期");
		return false;
	}
	return true;
}

function creatClick(){
	
		if (!checkData()) {
		return false;
	}
	var mShowMsg = "正在处理，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.submit();
}



/**
 * 提交后操作, 服务器数据返回后执行的操作
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