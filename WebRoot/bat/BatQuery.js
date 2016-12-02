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
		alert("������ѡ�����������!")
		return false;
	}
	return showCodeList('blctranstype',[fm.BalanceFlag,fm.BalanceFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}
function showBalanceFlagKey() {
	var vTranCom = trim(fm.TranCom.value);
	if(vTranCom == ''){
		alert("������ѡ�����������!")
		return false;
	}
	return showCodeListKey('blctranstype',[fm.BalanceFlag,fm.BalanceFlagName],[0,1],null,' 1=1 And COMCODE=#'+ vTranCom +'#',null,1,null,1);
}



//�ύǰ��У��
function beforeSubmit() {
	if (!verifyData()) {
		return false;
	}
	return true;
}

//�ύǰ��У��
function verifyData() {
	
	return true;
}


//����ѡ�е��к�,���radio button
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


//��ѯ��ť�������¼�
function queryItems() {
	initQueryGrid();

	var mTranCom = trim(fm.TranCom.value);
	var mBalanceFlag = trim(fm.BalanceFlag.value);
	var mRCode = trim(fm.RCode.value);
	var mStartDay = date10to8(fm.StartDay.value);
	var mEndDay = date10to8(fm.EndDay.value);


	var mSqlStr2 = "SELECT  " +
		" (SELECT D.CODENAME FROM LDCODE D WHERE D.CODETYPE='trancom' AND D.CODE=B.TRANCOM) ����, " +
		" date8to10(B.MAKEDATE) ����������, " +
		" time6to8(B.MAKETIME) ������ʱ��, " +
		" (SELECT D.CODENAME FROM LDCODE D WHERE D.CODETYPE='blctranstype' AND D.CODE=B.funcFlag) ����, " +
		" CASE WHEN B.RCODE='0' THEN '�ɹ�' WHEN B.RCODE!='0' THEN 'ʧ��'  ELSE '-' END ״̬, " +
		" B.RTEXT ���  " ;
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
			alert("��ʼ���ڲ���Ϊ��");
			return false;
		}
		if ("" == mEndDay) {
			alert("�������ڲ���Ϊ��");
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
	alert("��ѯ���Ϊ�գ�������ѡ��");
	}
	else {
	alert("��ѯ���̫��������ѡ��");
	}
}


//ѡ�����д������
function SelectBankCom(){
	var vTranCom = fm.TranCom.value;
	if(vTranCom == ''){
		alert("����ѡ��������!");
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



