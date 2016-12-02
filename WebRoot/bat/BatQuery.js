var turnPage = new turnPageClass();
var showInfo;

function showTranCom() {
	fm.TranComName.value='';
	fm.BalanceFlag.value='';
	fm.BalanceFlagName.value='';
	return showCodeList('trancom_bank',[fm.TranCom,fm.TranComName],[0,1],null,null,null,1,null,1);
}
function showTranComKey() {
	fm.TranComName.value='';
	fm.BalanceFlag.value='';
	fm.BalanceFlagName.value='';
	return showCodeListKey('trancom_bank',[fm.TranCom,fm.TranComName],[0,1],null,null,null,1,null,1);
}

function showBalanceFlag() {
	var vTranCom = trim(fm.TranCom.value);
	if(vTranCom == ''){
		alert("请首先选择批处理机构!")
		return false;
	}
	return showCodeList('blctranstype',[fm.BalanceFlag,fm.BalanceFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}
function showBalanceFlagKey() {
	var vTranCom = trim(fm.TranCom.value);
	if(vTranCom == ''){
		alert("请首先选择批处理机构!")
		return false;
	}
	return showCodeListKey('blctranstype',[fm.BalanceFlag,fm.BalanceFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}



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


//查询按钮触发的事件
function queryItems() {
	initQueryGrid();

	var mTranCom = trim(fm.TranCom.value);
	var mBalanceFlag = trim(fm.BalanceFlag.value);
	var mRCode = trim(fm.RCode.value);
	var mStartDay = date10to8(fm.StartDay.value);
	var mEndDay = date10to8(fm.EndDay.value);


	var mSqlStr2 = "SELECT  " +
		" (SELECT D.CODENAME FROM LDCODE D WHERE D.CODETYPE='trancom' AND D.CODE=B.TRANCOM) 机构, " +
		" date8to10(B.MAKEDATE) 批处理日期, " +
		" time6to8(B.MAKETIME) 批处理时间, " +
		" (SELECT D.CODENAME FROM LDCODE D WHERE D.CODETYPE='blctranstype' AND D.CODE=B.funcFlag) 类型, " +
		" CASE WHEN B.RCODE='0' THEN '成功' WHEN B.RCODE!='0' THEN '失败'  ELSE '-' END 状态, " +
		" B.RTEXT 结果  " ;
		var mSqlStr ="FROM TranLog B " +
		" WHERE 1= 1 and  b.funcFlag in ('1005','1006','2005','1201','1202','1203','1204','1104','1205')";
	
	if ("" != mTranCom || "" != mBalanceFlag|| "" != mRCode) {
		if("" != mTranCom){
					mSqlStr += " AND B.TRANCOM = '"+mTranCom+"'";
		}
		if("" != mBalanceFlag){
					mSqlStr += " AND B.FUNCFLAG = '"+mBalanceFlag+"'";
		}
		if("" != mRCode){
					mSqlStr += " AND B.RCODE = '"+mRCode+"'";
		}
	}

		if ("" == mStartDay) {
			alert("开始日期不能为空");
			return false;
		}
		if ("" == mEndDay) {
			alert("结束日期不能为空");
			return false;
		}
		mSqlStr += " and B.MAKEDATE between " + mStartDay + " and " + mEndDay + "";

	
	mSqlStr += " order by B.makedate,B.maketime desc";

var mSqlStr0=mSqlStr2+mSqlStr;
    var mSqlStr1 = "select count(*) "+mSqlStr;
    var mComInfos = easyExecSql(mSqlStr1); 
    if(mComInfos > 0 && mComInfos < 5000){
	turnPage.queryModal(mSqlStr0, QueryGrid, 0, 0);
	}
	else if(mComInfos==0){
	alert("查询结果为空，请重新选择！");
	}
	else {
	alert("查询结果太大，请重新选择！");
	}
}


//选择银行代理机构
function SelectBankCom(){
	var vTranCom = fm.TranCom.value;
	if(vTranCom == ''){
		alert("请先选择交易银行!");
		return false;
	}
	var winQuery = window.open("../sys/BankQuery.jsp?TranCom="+vTranCom);
}

function ConcelCom()
{
	fm.ZoneNo.value = "";
	fm.ZoneName.value = "";
	  	 
}

function resetItems() {

	fm.TranCom.value='';
	fm.TranComName.value='';
	fm.BalanceFlag.value='';
	fm.BalanceFlagName.value='';
	fm.RCode.value='';
	fm.RCodeName.value='';
	
	initQueryGrid();
	
	}



