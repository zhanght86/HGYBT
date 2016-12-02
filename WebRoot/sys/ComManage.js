var turnPage = new turnPageClass();
var showInfo;

function okClick() {

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
	var vComGrade = trim(fm.iComGrade.value);
	var vAreaNo = trim(fm.iAreaNo.value);
	var vComCode = trim(fm.iComCode.value);
	var vName = trim(fm.iName.value);
	var vShortName = trim(fm.iShortName.value);
	var vUpComCode = trim(fm.UpComCode.value);
	var vOutComCode = trim(fm.iOutComCode.value);
	
	//�������
	if (vComGrade == "") {
		alert("�������������Ϊ��!");
		return false;
	}
	
	//��������
	if (vOutComCode == "") {
		alert("�������벻��Ϊ��!");
		return false;
	}
	if (vShortName == "" && vComGrade == '04') {
		alert("������д����Ϊ��!");
		return false;
	}
	if (vShortName != "" && vShortName.length > 5) {
		alert("������д���Ȳ��ܳ���5λ!");
		return false;
	}
	if (vUpComCode == ""&& vComGrade != "01") {
		alert("�ϼ���������Ϊ��!");
		return false;
	}
	
	if (vComGrade == "01") {
		fm.iAreaNo.value= "";
		fm.iAreaName.value = "";
		fm.UpComCode.value = "";
		fm.UpComCodeName.value = "";
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
	//����������Ӧ�Ĵ���
	divQuery.style.display = "none";
	divInsert.style.display = '';
	divCmdButton.style.display = "none";
	divGrid.style.display = "none";
	divSubCmdButton.style.display = '';
	divPage.style.display = 'none';
	fm.all("OperType").value = "INSERT";
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
		var viComGradeId=document.getElementById("iComGradeId");
        var viComGradeNameId=document.getElementById("iComGradeNameId");
        var vUpComCodeId=document.getElementById("UpComCodeId");
        var vUpComCodeNameId=document.getElementById("UpComCodeNameId");
        var viOutComCodeId=document.getElementById("iOutComCodeId");
        viComGradeId.disabled=true;
        viComGradeNameId.disabled=true;
        vUpComCodeId.disabled=true;
        vUpComCodeNameId.disabled=true;
        viOutComCodeId.disabled=true;
		fm.all("OperType").value = "UPDATE"; 
	}
}

function cancelClick() {
	divQuery.style.display = "";
	divInsert.style.display = "none";
	divCmdButton.style.display = "";
	divGrid.style.display = "";
	divSubCmdButton.style.display = 'none';
	divPage.style.display = "";
	initOK();

	initForm();
	queryItems();
}

function resetForm() {
	initForm();
}

//��ѯ�¼�
function queryItems() {

	initGrid();

	var vAreaNo = trim(fm.AreaNo.value);
	var vComGrade = trim(fm.ComGrade.value);
	var vOutComCode = trim(fm.OutComCode.value);
	var vName = trim(fm.Name.value);
	var vPrivNo= trim(fm.PrivNo.value);
	var vAreaForShort= trim(fm.AreaForShort.value);
	var mSqlStr2 = "SELECT " 
			+ "  D.OUTCOMCODE   �����������, "
			+ "  D.NAME     	�����������, " 
			+ "  (SELECT CODENAME FROM LDCODE LD WHERE LD.CODE=D.COMGRADE AND CODETYPE='com_grade')  ��������, "
			+ "  (SELECT LC.NAME FROM LDCOM LC WHERE LC.COMCODE=D.UPCOMCODE)  �ϼ��������,"
			+ "  D.SHORTNAME �������,"
			+ "  D.UPCOMCODE �ϼ���������,"
			+ "  D.COMGRADE �����������"
			;
			var mSqlStr=
			"  FROM LDCOM D" + " WHERE " + "  1 = 1  ";

	if (vAreaNo != "") {
		mSqlStr += " AND SUBSTR(D.comcode,1,4) ='" + vAreaNo + "'";
	}
	if (vPrivNo != "") {
		mSqlStr += " AND SUBSTR(D.comcode,1,6) ='" + vPrivNo + "'";
	}
	if (vComGrade != "") {
		mSqlStr += " AND D.ComGrade ='" + vComGrade + "'";
	}
	if (vOutComCode != "") {
		mSqlStr += " AND D.OutComCode like '" + vOutComCode + "%'";
	}
	if (vName != "") {
		mSqlStr += " AND D.Name like'" + vName + "%'";
	}
	if (vAreaForShort != "") {
		mSqlStr += " AND D.shortname like'" + vAreaForShort + "%'";
	}
	mSqlStr += " ORDER BY D.COMGRADE,D.COMCODE";
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
    divPage.style.display = "";
}


function addItem() {
   
	if (!verifyInputThis()) {
		return false;
	}
	fm.OperType.value = "INSERT";
	submitForm();
}

//�޸��¼�
function update() {
	if(!verifyInputThis()){
		return false;	
	}
	fm.OperType.value = "UPDATE";
	submitUpdate();
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
	//mRowArr[3]���ϼ�����������ƣ�
	fm.iComGrade.value=mRowArr[6];
	fm.iComGradeName.value=mRowArr[2];
	fm.UpComCode.value =mRowArr[5];
	fm.UpComCodeName.value=mRowArr[3];
	fm.iOutComCode.value=mRowArr[0];
	fm.iName.value=mRowArr[1];
	fm.iShortName.value=mRowArr[4];
	return true;
}

function submitUpdate() {
	if (!confirm("��ȷʵ���޸ĸü�¼��")) {
  		return false;
	}
   var viComGradeId=document.getElementById("iComGradeId");
   var viComGradeNameId=document.getElementById("iComGradeNameId");
   var vUpComCodeId=document.getElementById("UpComCodeId");
   var vUpComCodeNameId=document.getElementById("UpComCodeNameId");
   var viOutComCodeId=document.getElementById("iOutComCodeId");
    viComGradeId.disabled=false;
    viComGradeNameId.disabled=false;
    vUpComCodeId.disabled=false;
    vUpComCodeNameId.disabled=false;
    viOutComCodeId.disabled=false;
	fm.OperType.value = "UPDATE";
	submitForm();
}

function delItem() {
	
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
	if (mRowArr == null) {
		alert("��������Ϊ�գ�");
		return false;
	}  
	
	if (!confirm("��ȷʵɾ���ĸü�¼��")) {
		return false;
	}
	
	fm.iOutComCode.value=  mRowArr[0];
	
	fm.OperType.value = "DELETE";
	submitForm();
}

function submitForm() {
	if ("" == fm.OperType.value) {
		alert("�������Ͳ���Ϊ�գ�");
		return false;
	}
	
	var mShowMsg = "���ڴ��������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.submit();
	if(fm.OperType.value == "DELETE"){
		fm.iComCode.value = "";
	}
} 

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(mFlag, mMessage) {
	showInfo.close();
	
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + mMessage;
	showModalDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	
	if ("Succ" == mFlag) {
		
		if(fm.OperType.value == "DELETE"){
			queryItems();
		}else{
			divGrid.style.display = "";
			querySubmit();	//�ύ���Զ���ѯ
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
    fm.PrivNo.value="";
	fm.PrivName.value="";
    fm.AreaNo.value="";
	fm.AreaName.value="";
	fm.AreaForShort.value="";
	showCodeList('Area_UserH',[fm.AreaNo,fm.AreaName],[0,1],null,' 1=1 And COMGRADE=#02#',null,1,null,1);
}  
//ѡ������ʱ�������¼�
function showAreaNoKey() {
    fm.PrivNo.value="";
	fm.PrivName.value="";
    fm.AreaNo.value="";
	fm.AreaName.value="";
	fm.AreaForShort.value="";
	showCodeListKey('Area_UserH',[fm.AreaNo,fm.AreaName],[0,1],null,' 1=1 And COMGRADE=#02#',null,1,null,1);
}
//ѡ�����������𴥷����¼�
function showComGradeKey() {
    fm.iOutComCode.value="";
    fm.UpComCode.value="";
	fm.UpComCodeName.value="";
    fm.iComGradeName.value="";
	fm.iComGrade.value="";
	showCodeListKey('com_grade',[fm.iComGrade,fm.iComGradeName],[0,1],null,' 1=1 and code!=#01# ',null,1,null,1);
    //var viComGrade=fm.iComGrade.value;
    //if(viComGrade=="02"){
    //var vNoticeId=document.getElementById("iNoticeId");
    //vNoticeId.style.display='none';
    //fm.iNoticeName.value="��8602��ͷ��";
   // }
}
function showComGrade() {
    fm.iOutComCode.value="";
    fm.UpComCode.value="";
	fm.UpComCodeName.value="";
    fm.iComGradeName.value="";
	fm.iComGrade.value="";
   // var vNoticeId=document.getElementById("iNoticeId");
   // vNoticeId.style.display='none';
	showCodeList('com_grade',[fm.iComGrade,fm.iComGradeName],[0,1],null,' 1=1 and code!=#01# ',null,1,null,1);
   // var viComGrade=fm.iComGrade.value;
    //if(viComGrade=="02"){
    //vNoticeId.style.display='';
   // fm.iNoticeName.value="��8602��ͷ��";
   // }
}
//ѡ��ʡ�ݴ������¼�
function showPrivNo() {  
    var vAreaNo=trim(fm.AreaNo.value);
    if(vAreaNo==""||vAreaNo==null){
    fm.PrivNo.value="";
	fm.PrivName.value="";
	showCodeList('Area_UserH',[fm.PrivNo,fm.PrivName],[0,1],null,' 1=1 And COMGRADE=#03#',null,1,null,1);
    }
    else {
    fm.PrivNo.value="";
	fm.PrivName.value="";
	showCodeList('Area_UserH',[fm.PrivNo,fm.PrivName],[0,1],null,' 1=1 And COMGRADE=#03# and substr(comcode,1,4)=#'+vAreaNo+'#',null,1,null,1);
    }
} 
function showPrivNoKey() {
	var vAreaNo=trim(fm.AreaNo.value);
    if(vAreaNo==""||vAreaNo==null){
    fm.PrivNo.value="";
	fm.PrivName.value="";
	showCodeListKey('Area_UserH',[fm.PrivNo,fm.PrivName],[0,1],null,' COMGRADE=#03#',null,1,null,1);
    }
    else {
    fm.PrivNo.value="";
	fm.PrivName.value="";
	showCodeListKey('Area_UserH',[fm.PrivNo,fm.PrivName],[0,1],null,' 1=1 And COMGRADE=#03# and substr(comcode,1,4)=#'+vAreaNo+'#',null,1,null,1);
    }
}
//��ѯ�ϼ���˾
	function showUpCompanyKey(){
	fm.iOutComCode.value="";
	var viComGrade=trim(fm.iComGrade.value);
    if(viComGrade==""||viComGrade==null){
    
    fm.UpComCode.value="";
	fm.UpComCodeName.value="";
	showCodeListKey('com_upcom',[fm.UpComCode,fm.UpComCodeName],[0,1],null,null,null,1,null,1);
    }
    else {
    viComGrade-=1;
    fm.PrivNo.value="";
	fm.PrivName.value="";
	showCodeListKey('com_upcom',[fm.UpComCode,fm.UpComCodeName],[0,1],null,' 1=1 And COMGRADE=#0'+viComGrade+'#',null,1,null,1);
    }
	}

	function showUpCompany() {  
	fm.iOutComCode.value="";
   var viComGrade=trim(fm.iComGrade.value);
    if(viComGrade==""||viComGrade==null){
  
    fm.UpComCode.value="";
	fm.UpComCodeName.value="";
	showCodeList('com_upcom',[fm.UpComCode,fm.UpComCodeName],[0,1],null,null,null,1,null,1);
    }
    else {
    viComGrade-=1;
    fm.PrivNo.value="";
	fm.PrivName.value="";
	showCodeList('com_upcom',[fm.UpComCode,fm.UpComCodeName],[0,1],null,' 1=1 And COMGRADE=#0'+viComGrade+'#',null,1,null,1);
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
			+ "  D.OUTCOMCODE   �����������, "
			+ "  D.NAME     	�����������, " 
			+ "  (SELECT CODENAME FROM LDCODE LD WHERE LD.CODE=D.COMGRADE AND CODETYPE='com_grade')  ��������, "
			+ "  (SELECT LC.NAME FROM LDCOM LC WHERE LC.COMCODE=D.UPCOMCODE)  �ϼ��������,"
			+ "  D.SHORTNAME �������,"
			+ "  D.UPCOMCODE �ϼ���������,"
			+ "  D.COMGRADE �����������"
			+"  FROM LDCOM D" + " WHERE " + "  1 = 1  ";
	

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
	  	 fm.UpComCodeName.value = Result+mComInfos;
	  	 fm.UpComCode.value = Result;
	}
}
function ConcelCom()
{
	  	 fm.UpComCodeName.value = '';
	  	 fm.UpComCode.value = '';
	  	 
}
function SelectCom(){
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
