var turnPage = new turnPageClass();
var showInfo;
var VMaCode;
var VMaCodeName;
function cancelUpdaClick(){
	divQuery.style.display = "";
	divInsert.style.display = "none";
	divCmdButton.style.display = "";
	divGrid.style.display = "";
	divSubCmdButton.style.display = 'none';
	divPage.style.display = "";
	divUpdate.style.display = 'none';
	fm.all("OperType").value="";
	fm.UpBank.value="";
	fm.ASCode.value="";
	initOK();
}
function okUpdaClick(){
    fm.StateCode.value = "";
    fm.BankCode.value = "";
    fm.BankName.value = "";
    fm.StateName.value = "";
    var viStartDate= trim(fm.StaDate.value);
	var viEndDate= trim(fm.EndSDate.value);
    if (viStartDate == "") {
		alert("��Ʒ�������ڲ���Ϊ��!");
		return false;
	}
	//��������
	if (viEndDate == "") {
		alert("��Ʒͣ�����ڲ���Ϊ��!");
		return false;
	}
    fm.all("OperType").value="RISKUPDATE";
    fm.OperType.value = "RISKUPDATE";
	submitForm();
    
}
function riskUpdateItems(){
 var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (null == mRowArr) {
		alert("��������Ϊ�գ�");
		return false;
	}
	//����������Ӧ�Ĵ���
	divQuery.style.display = "none";
	divInsert.style.display = 'none';
	divCmdButton.style.display = "none";
	divGrid.style.display = "none";
	divSubCmdButton.style.display = 'none';
	divPage.style.display = 'none';
	divUpdate.style.display = '';
	
	//fm.ProBankCodeName.value=mRowArr[1];
	fm.ProCode.value=mRowArr[1];
	fm.ASCode.value = mRowArr[2];
	fm.ProName.value = mRowArr[3];
	fm.StaDate.value = mRowArr[4];
	fm.EndSDate.value = mRowArr[5];
	fm.UpBank.value = mRowArr[0];
	if(mRowArr[0]=='��������'){
	fm.ProBankCode.value='011';
	}else if(mRowArr[0]=='�㷢����'){
	fm.ProBankCode.value='012';
	}else {
	fm.ProBankCode.value='--';
	}
	//initGrid();
}


function okClick() {
	if (fm.all("OperType").value != "UPDATE") {
		fm.all("OperType").value = "INSERT";
	}
	if (!verifyInputThis()) {
		return false;
	}
	var vProStateCode = trim(fm.ProStateCode.value);
	if(vProStateCode=="1"){
	var mSqlStr = "select Count(*) from LMRiskmap where codetype= 'risk_com' and comcode LIKE '"+trim(fm.iComCode.value)+"%' and insucode = '"
	               +trim(fm.iProductCode.value)+"' and bankcode = '"+trim(fm.iBankCode.value)+"'";
	var mCountIns = easyExecSql(mSqlStr);
	if (mCountIns=="0"){
		//if (!confirm("�û���ͬһ���������ĸ�����������,��ǰͣ������Ϊ"+mEnddate+",��ȷʵ���޸ĸü�¼��")) {
  		//return false;
	     //  }
	     alert("�û���ͬһ�����µĸ����־�δͣ�ۣ�");
	     return false;
	    // submitForm();
	}
}
	if (fm.all("OperType").value == "INSERT") {
		addItem();
	}
	if (fm.all("OperType").value == "UPDATE") {
		update();
	}
}

//-------�ύУ����-------
function verifyInputThis() {
	var vOperType = trim(fm.OperType.value);
	var viBankCode = trim(fm.iBankCode.value);
	var viBankName = trim(fm.iBankName.value);
	var viRiskCode = trim(fm.iRiskCode.value);
	var viRiskName = trim(fm.iRiskName.value);
	var viActivityCode = trim(fm.iActivityCode.value);
	var viComCode = trim(fm.iComCode.value);
	var vProStateCode = trim(fm.ProStateCode.value);
	
//	//�����
//	if (viActivityCode == "") {
//		alert("��Ʒϵͳ����Ϊ��!");
//		return false;
//	}
	
	//��˾����
	if (viComCode == "") {
		alert("��˾��������Ϊ��!");
		return false;
	}
	if (vProStateCode == "") {
		alert("ϵͳ״̬����Ϊ��!");
		return false;
	}
	if(vProStateCode=="0"){
	if (!confirm("���ú����������ж���ͣ�ۣ���ȷ��Ҫ������")) {
		return false;
	}
	}
	else if(vProStateCode=="1"){
	if (!confirm("���ú����������ж���'ȡ��'ͣ�ۣ���ȷ��Ҫ������")) {
		return false;
	}
	}
	return true;
}
//У����Ч����
function isDigit(s) {
	var patrn = /^[0-9]{1,20}$/;
	if (!patrn.exec(s))
		return false
	return true
}

function insertClick() {
  var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (null == mRowArr) {
		alert("��������Ϊ�գ�");
		return false;
	}
	else{
	var str=mRowArr[6];
	if(str=="ͣ��"){
	alert("�������ѵ��ڣ�");
	return false;
	}
	else if(str=="δ��ͨ"){
	alert("��������δ��ͨ��");
	return false;
	}
	}
	//����������Ӧ�Ĵ���
	if(fillForm()){
	divQuery.style.display = "none";
	divInsert.style.display = '';
	divCmdButton.style.display = "none";
	//divGrid.style.display = "none";
	//comdivGrid.style.display = '';
	divSubCmdButton.style.display = '';
	divPage.style.display = 'none';
	fm.all("OperType").value = "INSERT";
	insertForm();
	queryItems();
	//initGrid();
	}
}


function updateClick() {
	if (fillForm()) { 
		//����������Ӧ�Ĵ���
		divQuery.style.display = "none";
		divInsert.style.display = '';
		divCmdButton.style.display = "none";
		divGrid.style.display = "none";
		divSubCmdButton.style.display = '';
		divPage.style.display = 'none';
		fm.all("OperType").value = "UPDATE"; 
	}
}

function cancelClick() {
	divQuery.style.display = "";
	divInsert.style.display = "none";
	divCmdButton.style.display = "";
	//divGrid.style.display = "";
	divSubCmdButton.style.display = 'none';
	divPage.style.display = "";
	initOK();
	//initForm();
	//queryItems();
}

function resetForm() {
	initForm();
}


function riskQueryItems(){
	initGrid();
	var vBankCode = trim(fm.BankCode.value);	
	var vStateCode = trim(fm.StateCode.value); 
	
//	var mSqlStr = "SELECT " 
//			+ "  (SELECT DC.CODENAME FROM LDCODE DC WHERE DC.codetype='trancom_bank' and DC.CODE=B.BANKCODE) ��������, "	    
//			+ "  B.BankInsuCode ��Ʒ����,"
//			+ "  B.InsuCode AS400����, "
//			+ "  (SELECT LM.RISKNAME FROM LMRISKAPP LM WHERE LM.RISKCODE=B.INSUCODE) ��Ʒ, "
//			+ "  date8to10(B.StartDate) ��Ʒ��������, "
//			+ "  date8to10(B.EndDate) ��Ʒͣ������, "
//			+ "  B.State ��Ʒϵͳ״̬, "
//			+ "  B.BankCode ���д��� "
//			+ " FROM LDBankMap B" + " WHERE " + "  1 = 1  "
	
//	add by fzg 2012-2-13 ������bankandinsumap��������Ӧ�Ĳ�ѯ��ʽҲ���ˡ�
	var mSqlStr = "SELECT" 
		+ "  (SELECT DC.CODENAME FROM LDCODE DC WHERE DC.codetype='trancom_bank' and DC.CODE=B.TranCom) ��������, "	    
		+ "  B.Tran_Code ��Ʒ����,"
		+ "  B.Insu_Code AS400����, "
		+ "  (SELECT LM.RISKNAME FROM LMRISKAPP LM WHERE LM.RISKCODE=B.INSU_CODE) ��Ʒ, "
		+ "  date8to10(B.BAK1) ��Ʒ��������, "
		+ "  date8to10(B.BAK2) ��Ʒͣ������, "
		+ "  CASE WHEN to_number(to_char(sysdate, 'yyyymmdd')) < to_number(B.BAK1) THEN 'δ��ͨ' WHEN to_number(to_char(sysdate, 'yyyymmdd')) >= to_number(B.BAK2) THEN 'ͣ��' ELSE '����' END ��Ʒϵͳ״̬,"
		+ "  B.TranCom ���д��� "
		+ " FROM bankandinsumap B" + " WHERE " + "  1 = 1 AND B.CODETYPE='RiskCode' AND  EXISTS (SELECT 'X' FROM LMRIsKAPP M WHERE M.RISKCODE= B.INSU_CODE AND M.SUBRISKFLAG !='S')"

	if (vBankCode != "") {
		mSqlStr += " AND B.TranCom ='" + vBankCode + "'";
	} 
	if(vStateCode !=""){
	if(vStateCode == "0"){
	    mSqlStr += " AND to_number(to_char(sysdate, 'yyyymmdd')) < to_number(B.BAK2) AND to_number(to_char(sysdate, 'yyyymmdd')) >= to_number(B.BAK1)";
	    }else if(vStateCode == "1"){
	    mSqlStr += " AND to_number(to_char(sysdate, 'yyyymmdd')) < to_number(B.BAK1)";
	    }else if(vStateCode == "2"){
	    mSqlStr += " AND to_number(to_char(sysdate, 'yyyymmdd')) >= to_number(B.BAK2)";	
	    }
	}
	if(fm.OperType.value = "RISKUPDATE"){
		var mASCode = fm.ASCode.value;
	    var mUpBank = fm.UpBank.value;
	    var mProCode = fm.ProCode.value;
	    if(mUpBank!=""){
	    mSqlStr += " AND B.TRANCOM in (select code from ldcode where codetype='bat_com' and codename='" + mUpBank + "')";
	    }
	    if(mASCode!=""){
	    mSqlStr += " AND B.INSU_CODE ='" + mASCode + "'";
	    }
	     if(mProCode!=""){
	    mSqlStr += " AND B.tran_code ='" + mProCode + "'";
	    }
	}
	mSqlStr += " ORDER BY B.BAK2,B.BAK1,B.TRANCOM,B.Tran_Code";
	
	
//	add by fzg 2012-2-14 У������Ϊ��ʱ������ʾ��Ϣ��
	var mResult = easyExecSql(mSqlStr);
	if(mResult==null){
		alert("��Ʒ��ϢΪ��!");
		return false;
	}
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);

}

//��ѯ�¼�
function queryItems() {

	initGrid();

	var vRiskCode = trim(fm.iRiskCode.value);
	var vBankCode = trim(fm.iBankCode.value);
	var viProductCode = trim(fm.iProductCode.value);
	var viRiskName = trim(fm.iRiskName.value);

	var mSqlStr = "SELECT " 
			+ "  (SELECT LM.RISKNAME FROM LMRISKAPP LM WHERE LM.RISKNAME='"+viRiskName+"') ��Ʒ, "
			+ "  (SELECT DC.CODENAME FROM LDCODE DC WHERE DC.codetype='trancom_bank' and DC.CODE=D.BANKCODE) ��������, "			
			+ "  (SELECT LD.NAME FROM LDCOM LD WHERE LD.COMCODE=(SELECT LD.UPCOMCODE FROM LDCOM LD WHERE LD.COMCODE=D.ComCODE)) ��������, "
			+ "  (SELECT LD.NAME FROM LDCOM LD WHERE LD.COMCODE=D.ComCODE) ��������, "			
			+ "  CASE WHEN D.ActivityCode = '1' THEN '����'  WHEN D.ActivityCode = '2' THEN 'ͣ��' WHEN D.ActivityCode = '3' THEN 'δ��ͨ' ELSE '--' END ��Ʒϵͳ״̬,"
			+ "  date8to10(D.StartDate) ��ʼ����, "
			+ "  date8to10(D.EndDate) ��ֹ����, "
			
			+ "  D.BankCode ������������, "
			+ "  D.InsuCode ��Ʒ����, "
			+ "  D.COMCode �������� "
			+ " FROM LMRiskMap D" + " WHERE " + "  1 = 1  "
            + " AND D.INSUCODE ='" + viProductCode + "'"
	        + " AND D.BANKCODE ='" + vBankCode + "'";
	
	
	mSqlStr += " ORDER BY D.COMCODE";
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);
}


function addItem() {
	
	fm.OperType.value = "INSERT";
	submitForm();
}

//�޸��¼�
function update() {
	
	fm.OperType.value = "UPDATE";
	submitForm();
}


function fillForm() {
	if(turnPage.queryAllRecordCount <= 0) {
		alert("���Ȳ�ѯ��");
		return false;
	}
   
   var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
	
	var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (null == mRowArr) {
		alert("��������Ϊ�գ�");
		return false;
	}
	
	fm.iBankName.value=  mRowArr[0];
	fm.iBankCode.value=  mRowArr[7]; 
	fm.iRiskCode.value=  mRowArr[2];
	fm.iRiskName.value=  mRowArr[3];
	fm.iEndDate.value=   mRowArr[5];
	fm.iProductCode.value = mRowArr[1];
	var date = new Date();
	fm.iStartDate.value=ChangeTimeToString(date);
	
	//fm.iComGrade.value = mRowArr[9];
	return true;
}
//ת����YYYY-MM-DD����
                function ChangeTimeToString(DateIn)
                {
                    var Year=0;
                    var Month=0;
                    var Day=0;
                    //��ʼ��ʱ��
                    Year      = DateIn.getFullYear();
                    Month     = DateIn.getMonth()+1;
                    Day       = DateIn.getDate();
                    CurrentDate = Year + "-";
                    if (Month >= 10 )
                    {
                        CurrentDate = CurrentDate + Month + "-";
                    }
                    else
                    {
                        CurrentDate = CurrentDate + "0" + Month + "-";
                    }
                    if (Day >= 10 )
                    {
                        CurrentDate = CurrentDate + Day ;
                    }
                    else
                    {
                        CurrentDate = CurrentDate + "0" + Day ;
                    }
                    return CurrentDate;
                }

function submitUpdate() {


	
	if (!confirm("�û���ͬһ���������ĸ�����������,��ǰͣ������Ϊ��ȷʵ���޸ĸü�¼��")) {
  		return false;
	}
	
	fm.OperType.value = "UPDATE";
	submitForm();

}

function delItem() {
	var date = new Date();
	fm.iStartDate.value=ChangeTimeToString(date);
	var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
	
	var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (mRowArr == null) {
		alert("��������Ϊ�գ�");
		return false;
	}  
	
	if (!confirm("��ȷʵɾ���ĸü�¼��")) {
		return false;
	}
	
	fm.iComCode.value=  mRowArr[9];
	fm.iRiskCode.value = mRowArr[8];
	fm.iBankCode.value= mRowArr[7];

	
	fm.OperType.value = "DELETE";
	submitForm();
}

function submitForm() {
	if ("" == fm.OperType.value) {
		alert("�������Ͳ���Ϊ�գ�");
		return false;
	}
	VMaCodeName=fm.iComCodeName.value;
	VMaCode=fm.iComCode.value;
	var mShowMsg = "���ڴ��������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.submit();
	
	//add by fzg 2012-2-14----���������������ò�Ʒ�������
	var date = new Date();
	fm.iStartDate.value=ChangeTimeToString(date);
	//fm.iActivityCode.value="";

} 

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(mFlag, mMessage) {


	showInfo.close();
	
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + mMessage;
	showModalDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	
	if ("Succ" == mFlag) {
		
		if(fm.OperType.value == "DELETE"){
		    initComcode();
			queryItems();
		}else if(fm.OperType.value == "RISKUPDATE"){
			divGrid.style.display = "";
			riskQueryItems();	//�ύ���Զ���ѯ
		}else {
		divGrid.style.display = "";
			queryItems();	//�ύ���Զ���ѯ
		}
	} 
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


function showiAreaNo() {  
	fm.iAreaName.value="";
	showCodeList('Area_Print',[fm.iAreaNo,fm.iAreaName],[0,1],null,null,null,1,null,1);
} 
//ѡ������ʱ�������¼�
function showAreaNo() {  
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value="";
	showCodeList('Area_User',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
}  
//ѡ������ʱ�������¼�
function showAreaNoKey() {
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value=""; 
	showCodeListKey('Area_User',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
}

//ѡ�����ʱ�������¼�
function showCityNo() {
	var vAreaNo = trim(fm.AreaNo.value);
	if("" != vAreaNo){
		
		if("86" == vAreaNo){
			alert("ѡ���ܹ�˾ʱ,����ѡ�����!");
			fm.CityNo.value="";
			fm.CityName.value="";
			return false;
		}
		
		var mSqlStr = "select count(1) from LDCOM D where D.AREAID = '"+vAreaNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("������Ŀǰ�޻���!");
			fm.CityNo.value="";
			fm.CityName.value="";

			return false;
		}
			fm.CityName.value="";
			
			
		showCodeList('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,' 1=1 And AreaId=#'+ vAreaNo +'#',null,1,null,1);

	}
	else if("" == vAreaNo){
				fm.CityName.value="";
		
		showCodeList('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,null,null,1,null,1);

	}
}
//ѡ�����ʱ�������¼�
function showCityNoKey() {
var vAreaNo = trim(fm.AreaNo.value);
	if("" != vAreaNo){
		if("86" == vAreaNo){
			alert("ѡ���ܹ�˾ʱ,����ѡ�����!");
			fm.CityNo.value="";
			fm.CityName.value="";
			return false;
		}
		var mSqlStr = "select count(1) from LDCOM D where D.AREAID = '"+vAreaNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("������Ŀǰ�޻���!");
			fm.CityNo.value="";
			fm.CityName.value="";
			return false;
		}
			fm.CityName.value="";
		showCodeListKey('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,' 1=1 And AreaId=#'+ vAreaNo +'#',null,1,null,1);

	}
	else if("" == vAreaNo){
		fm.CityName.value="";
		showCodeListKey('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,null,null,1,null,1);
	}
}


//��ѯ�¼�
function querySubmit() {

	initGrid();

	var vAreaNo = trim(fm.iAreaNo.value);
	var vComGrade = trim(fm.iComGrade.value);
	var vOutComCode = trim(fm.iOutComCode.value);
	var vName = trim(fm.iName.value);

	var mSqlStr = "SELECT " 
			+ "  D.AREANAME     ������������, " 
			+ "  D.OUTCOMCODE      ��������, "
			+ "  D.NAME     	��������, " 
			+ "  D.SHORTNAME     ������д, " 
			+ "  (SELECT LD.CODENAME FROM LDCODE LD WHERE CODETYPE='com_grade' and LD.CODE=D.COMGRADE) �������, "
			+ "  (SELECT DD.COMCODE||DD.NAME FROM LDCOM DD WHERE DD.COMCODE=D.UPCOMCODE) �ϼ�����,"
			+ "  D.AREAID �������,"
			+ "  D.COMCODE ��������,"
			+ "  D.UPCOMCODE �ϼ�����, "
			+ "  D.COMGRADE ���������� "
			+ " FROM LDCOM D" + " WHERE " + "  1 = 1  "

	if (vAreaNo != "") {
		mSqlStr += " AND D.AreaID ='" + vAreaNo + "'";
	}
	if (vComGrade != "") {
		mSqlStr += " AND D.ComGrade ='" + vComGrade + "'";
	}
	if (vOutComCode != "") {
		mSqlStr += " AND D.OutComCode ='" + vOutComCode + "'";
	}
	if (vName != "") {
		mSqlStr += " AND D.Name ='" + vName + "'";
	}
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);
}


/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( Result )
{
	var arrResult = new Array();

	if( Result != null )
	{
		 var mSqlStr = "select D.NAME from LDCOM D where D.comcode = '" + Result + "'";
		 var mComInfos = easyExecSql(mSqlStr);
	  	 fm.iComCodeName.value = Result+mComInfos;
	  	 fm.iComCode.value = Result;
	}
}
function ConcelCom()
{
	  	 fm.iComCodeName.value = '';
	  	 fm.iComCode.value = '';
	  	 
}
function SelectCom(){
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
