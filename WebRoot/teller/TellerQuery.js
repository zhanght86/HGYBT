var turnPage = new turnPageClass();
var showInfo;


//提交前的校验
function beforeSubmit() {
	if (!verifyData()) {
		return false;
	}
	return true;
}

//提交前的校验
function verifyData() {
	
	return true;
}


/**
点击银行渠道时触发的事件

*/
function LOBankQuery() {
fm.TranCom.value="";
fm.TranComName.value="";
showCodeList('trancom_bank',[fm.TranCom,fm.TranComName],[0,1],null,null,null,1,null,1);
}
function LOBankQueryKey() {
fm.TranCom.value="";
fm.TranComName.value="";
showCodeListKey('bat_com',[fm.TranCom,fm.TranComName],[0,1],null,null,null,1,null,1);
}


function LOZoneQuery() {
fm.ZoneNo.value="";
fm.ZoneName.value="";
var vTranCom=trim(fm.TranCom.value);
if("" != vTranCom){
showCodeList('Zone_No',[fm.ZoneNo,fm.ZoneName],[0,1],null,' 1=1 And TRANCOM=#'+ vTranCom +'#',null,1,null,1);
    }else{
    	alert("请选择银行！");
         return false;
    }
}
function LOZoneQueryKey() {
fm.ZoneNo.value="";
fm.ZoneName.value="";
var vTranCom=trim(fm.TranCom.value);
if("" != vTranCom){
showCodeList('Zone_No',[fm.ZoneNo,fm.ZoneName],[0,1],null,' 1=1 And TRANCOM=#'+ vTranCom +'#',null,1,null,1);
    }else{
    	alert("请选择银行！");
         return false;
    }
}

/**
点击修改界面银行渠道时触发的事件

*/
function iBankQuery() {
fm.iTranCom.value="";
fm.iTranComName.value="";
showCodeList('trancom_bank',[fm.iTranCom,fm.iTranComName],[0,1],null,null,null,1,null,1);
}
function iBankQueryKey() {
fm.iTranCom.value="";
fm.iTranComName.value="";
showCodeListKey('bat_com',[fm.iTranCom,fm.iTranComName],[0,1],null,null,null,1,null,1);
}


function iZoneQuery() {
fm.iZoneNo.value="";
fm.iZoneName.value="";
var vTranCom=trim(fm.iTranCom.value);
if("" != vTranCom){
showCodeList('Zone_No',[fm.iZoneNo,fm.iZoneName],[0,1],null,' 1=1 And TRANCOM=#'+ vTranCom +'#',null,1,null,1);
    }else{
    	alert("请选择银行！");
         return false;
    }
}
function iZoneQueryKey() {
fm.iZoneNo.value="";
fm.iZoneName.value="";
var vTranCom=trim(fm.iTranCom.value);
if("" != vTranCom){
showCodeList('Zone_No',[fm.iZoneNo,fm.iZoneName],[0,1],null,' 1=1 And TRANCOM=#'+ vTranCom +'#',null,1,null,1);
    }else{
    	alert("请选择银行！");
         return false;
    }
}

//查询页面查询按钮触发的事件
function queryItems() {
	

	initQueryGrid();
	
	var mTranCom = trim(fm.TranCom.value);
	var mZoneNo = trim(fm.ZoneNo.value);
	var mTellerName = trim(fm.TellerName.value);
	var mTellerIDNo = trim(fm.TellerIDNo.value);
	var mStartDay=trim(fm.StartDay.value);
	var mEndDay=trim(fm.EndDay.value);

var mSqlStr = "SELECT  " +
		" C.TellerNo 柜员号, " +
		" C.TellerName 柜员姓名, " +
		" (Select D.CodeName from Ldcode D where D.codetype='trancom_bank' and D.code=C.TranCom) 所属银行, " +
		" (Select B.ZoneName from lkbankcom B where B.ZoneNo=C.ZoneNo) 所属地区, " +
		" C.QuType 柜员资格证类型, " +
		" C.TellerQuNo 柜员资格证号, " +
		" to_char(C.StartDate,'yyyy-MM-dd') 资格证生效日, " +
		" to_char(C.EndDate,'yyyy-MM-dd') 资格证失效日, " +
		" C.TranCom 所属银行, " +
		" C.ZoneNo 所属地区 "+
		" FROM LdTeller C where 1=1 "; 
		
	if ("" != mTranCom) {
	    mSqlStr+="and C.TranCom = '"+mTranCom+"' ";
	}
	if("" !=mZoneNo){
		mSqlStr+="and C.ZoneNo = '"+mZoneNo+"' ";
	}
	
	if("" !=mTellerName){
		mSqlStr+="and C.TellerName = '"+mTellerName+"' ";
	}
	
	if("" !=mTellerIDNo){
		mSqlStr+="and C.TellerNo = '"+mTellerIDNo+"' ";
	}
	
	if("" !=mStartDay){
		mSqlStr+="and C.EndDate >= '"+mStartDay+"' ";
	}
	
	if("" !=mEndDay){
		mSqlStr+="and C.EndDate <= '"+mEndDay+"' ";
	}
	
	mSqlStr += " order by C.Trancom,C.ZoneNo,C.TellerNo";
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);
	
}


function resetItems() {

	resetForm();
	initQueryGrid();
	
	}

function insertClick()
{
	var viTranCom=document.getElementById("iTranCom");
    var viTranComName=document.getElementById("iTranComName");
    var viZoneNo=document.getElementById("iZoneNo");
    var viZoneName=document.getElementById("iZoneName");
    var viTellerIDNo=document.getElementById("iTellerIDNo");
    viTranCom.disabled=false;
    viTranComName.disabled=false;
    viZoneNo.disabled=false;
    viZoneName.disabled=false;
    viTellerIDNo.disabled=false;
    fm.iTranCom.value="";
    fm.iTranComName.value="";
    fm.iZoneNo.value="";
    fm.iZoneName.value="";
    fm.iTellerName.value="";
    fm.iTellerIDNo.value="";
   // var viComCodeNameId=document.getElementById("iComCodeNameId");
   // var viAgentCodeNoId=document.getElementById("iAgentCodeNoId");
   // var viChooseId=document.getElementById("iChooseId");
    //viComCodeNameId.disabled=false;
   // viAgentCodeNoId.disabled=false;
   // viChooseId.style.display='';
	//下面增加相应的代码
    divAgentCheck.style.display='none';
    divAgentADD.style.display='';
    divCmdButton.style.display = "none";
	divGrid.style.display = "none";
	divSubCmdButton.style.display = '';
	divPage.style.display = 'none';
	initOK();
	fm.all("OperType").value = "INSERT";
}

function updateClick()
{  
    var viTranCom=document.getElementById("iTranCom");
    var viTranComName=document.getElementById("iTranComName");
    var viZoneNo=document.getElementById("iZoneNo");
    var viZoneName=document.getElementById("iZoneName");
    var viTellerIDNo=document.getElementById("iTellerIDNo");
    viTranCom.disabled=true;
    viTranComName.disabled=true;
    viZoneNo.disabled=true;
    viZoneName.disabled=true;
    viTellerIDNo.disabled=true;
	if(fillForm()){
	//下面增加相应的代码
	divAgentCheck.style.display='none';
    divAgentADD.style.display='';
    divCmdButton.style.display ='none';
	divGrid.style.display = 'none';
	divSubCmdButton.style.display = '';
	divPage.style.display = 'none';
		fm.all("OperType").value = "UPDATE";
		//document.getElementById("TranComID").readOnly=false;
	}
} 

//维护页面查询按钮触发的事件
function MqueryItems() {
	

	initGrid();
	
	var mTranCom = trim(fm.TranCom.value);
	var mZoneNo = trim(fm.ZoneNo.value);
	var mTellerName = trim(fm.TellerName.value);
	var mTellerIDNo = trim(fm.TellerIDNo.value);

var mSqlStr = "SELECT  " +
		" C.TellerNo 柜员号, " +
		" C.TellerName 柜员姓名, " +
		" (Select D.CodeName from Ldcode D where D.codetype='trancom_bank' and D.code=C.TranCom) 所属银行, " +
		" (Select B.ZoneName from lkbankcom B where B.ZoneNo=C.ZoneNo) 所属地区, " +
		" C.QuType 柜员资格证类型, " +
		" C.TellerQuNo 柜员资格证号, " +
		" to_char(C.StartDate,'yyyy-MM-dd') 资格证生效日, " +
		" to_char(C.EndDate,'yyyy-MM-dd') 资格证失效日, " +
		" C.TranCom 所属银行, " +
		" C.ZoneNo 所属地区, "+
		" C.SRSTellerNo SRS柜员编号 "+
		" FROM LdTeller C "; 
		
	if ("" != mTranCom) {
	    mSqlStr+="Where C.TranCom = '"+mTranCom+"' ";
	}
	if("" !=mZoneNo){
		mSqlStr+="and C.ZoneNo = '"+mZoneNo+"' ";
	}
	
	if("" !=mTellerName && "" != mTranCom){
		mSqlStr+="and C.TellerName = '"+mTellerName+"' ";
	}else if("" !=mTellerName){
		 mSqlStr+="Where C.TellerName = '"+mTellerName+"' ";
	}
	
	if("" !=mTellerIDNo && ("" != mTranCom||"" !=mTellerName)){
		mSqlStr+="and C.TellerNo = '"+mTellerIDNo+"' ";
	}else if("" !=mTellerIDNo){
		 mSqlStr+="Where C.TellerNo = '"+mTellerIDNo+"' ";
	}
	mSqlStr += " order by C.Trancom,C.ZoneNo,C.TellerNo";
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);
	
}

function fillForm() {
	
	if(turnPage.queryAllRecordCount <= 0) {
		alert("请先查询！");
		return false;
	}

   var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}

	var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (null == mRowArr) {
		alert("该数据项为空！");
		return false;
	} 
	   
	//fm.update_bt.value = "保存修改";
	fm.iTellerIDNo.value = mRowArr[0];
	fm.iTellerName.value = mRowArr[1];
	fm.iTranComName.value = mRowArr[2];
	fm.iZoneName.value = mRowArr[3];
	fm.iQuType.value = mRowArr[4];
	fm.iTellerQuNo.value = mRowArr[5];
	fm.iStartDay.value = mRowArr[6];
	fm.iEndDay.value = mRowArr[7];
	fm.iTranCom.value = mRowArr[8];
	fm.iZoneNo.value = mRowArr[9];
	fm.SRSTellerNo.value=mRowArr[10];
    return true;
} 

//返回选中的行号,这对radio button
function checkedRowNo(name) {
	var obj = document.all[name];
	
	if ("undefined" == typeof(obj)) {
		return -1;
	} else if ("undefined" == typeof(obj.length)) {
		return 0;
	}
	
	for (i = 0; i < obj.length; i++) {
		if(obj[i].checked) {
			return i;
		}
	} 
	
	return -1;
}

function okClick()
{
 
	if (fm.all("OperType").value == "INSERT")
	{
		addItem();
	}
	if (fm.all("OperType").value == "UPDATE")
	{
		submitUpdate();
	}
}

function addItem() {
	if (!verifyInputThis()) {
		return false;
	}
	if (!confirm("您确认添加改该记录吗？")) {
		return false;
	}
	fm.OperType.value = "INSERT";
	submitForm();
}

function submitUpdate() {
	if (!verifyInputThis()) {
		return false;
	}
	
	if (!confirm("您确实想修改该记录吗？")) {
  		return false;
	}

	fm.OperType.value = "UPDATE";
	submitForm();
	
	//fm.update_bt.value = "修 改";
}

function submitForm() {
	if ("" == fm.OperType.value) {
		alert("操作类型不能为空！");
		return false;
	}
	var mShowMsg = "正在处理，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm.submit();
}

function verifyInputThis(){
    
	var vTranCom = trim(fm.iTranCom.value);
	var viZoneNo = trim(fm.iZoneNo.value);
	var viTellerIDNo = trim(fm.iTellerIDNo.value);
	var viQuType = trim(fm.iQuType.value);
	var viTellerQuNo = trim(fm.iTellerQuNo.value);
	var viStartDay = trim(fm.iStartDay.value);
	var viEndDay = trim(fm.iEndDay.value);
	var viTellerName = trim(fm.iTellerName.value);

	if(vTranCom == ""){
		alert("所属银行不能为空!");
		return false;
	}
	if(viZoneNo == ""){
		alert("所属地区不能为空!");
		return false;
	}
	if(viTellerName == ""){
		alert("柜员姓名不能为空!");
		return false;
	}
	if(viTellerIDNo == ""){
		alert("银行柜员编号不能为空!");
		return false;
	}
	if(viQuType != "01"){
		alert("资格证类型只能为01!");
		return false;
	}
	if(viTellerQuNo == ""){
		alert("资格证号不能为空!");
		return false;
	}
	if(viEndDay == ""){
		alert("失效日期不能为空!");
		return false;
	}

	return true;
} 

function afterSubmit(pFlag, pMessage) {
	showInfo.close();
	
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + pMessage;
	showModalDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	if ("Succ" == pFlag) {
	divGrid.style.display = "";
	divPage.style.display ="";
	MqueryItems();	//提交后自动查询
	} 
}

function cancelClick()
{  
    divAgentCheck.style.display='';
    divAgentADD.style.display='none';
    divCmdButton.style.display = "";
	divGrid.style.display = "";
	divSubCmdButton.style.display = 'none';
	divPage.style.display = '';
	initOK();
	initForm();
}

function delItem() {
	if(turnPage.queryAllRecordCount <= 0) {
		alert("请先查询！");
		return false;
	}
	var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}
	
	var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (mRowArr == null) {
		alert("该数据项为空！");
		return false;
	} 
	
	if (!confirm("您确实删除该记录吗？")) {
		return false;
	}
	fm.SRSTellerNo.value=mRowArr[10];
	fm.OperType.value = "DELETE";
	submitForm(); 
}
