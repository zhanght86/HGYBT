var turnPage = new turnPageClass();
var showInfo;


function initBox() {
	try {
		//fm.all("InfoType").value = "";
		fm.all("ImportPath").value = "";
		fm.all("FileName").value = "";
		divInsert.style.display = 'none';
		divQuery.style.display = "";
		divQueryALL.style.display = "";
		divCmdButton.style.display = "none";
		divGrid.style.display = "";
		divPage.style.display = "";
	} catch (re) {
		alert("��MissInfoUpInit.jsp --> initBox �����з����쳣:��ʼ���������");
	}
}
//�ύ�����水ť��Ӧ����
function submitFormIn()
{
	var i = 0;
	var vImportFile = fm.all('FileName').value;
	//var infoType = fm.all('InfoType').value;
	//var infoTypeName = fm.all('InfoTypeName').value;
	//if(infoType.trim() == ""){
	//	alert("��ѡ��ȱʧ��Ϣ����");
	//	return false; 
	//}
	//if(infoTypeName.trim() == ""){
	//	alert("��ѡ��ȱʧ��Ϣ����");
	//	return false; 
	//}
	if(vImportFile.trim() == ""){
		alert("��ѡ���ϴ��ļ�");
		return false; 
	}
 
	var ImportPath = 'citiUpload/temp';
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('ImportPath').value = ImportPath;	
	
	fm.action="MissInfoUpSave.jsp?OperType=UP"; 
	fm.submit(); //�ύ
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
function queryItems() {
	//initGrid();//Ϊ��ִ�в��ˡ�����
	var mSqlStr = "SELECT " 
			+ "  am.policyno ������, " 
			+ "  am.InsuredIDType ������֤������, "
			+ "  am.ApplicantIDType  Ͷ����֤������, " 
			+ "  am.ApplicantIdNo  Ͷ����֤����, " 
			+ "  am.ApplicantAcctNO Ͷ���������˺�, "
			+ "  am.ApplicationNO Ͷ������,"
			+ "  am.CustomerNo �ͻ����, "
			+ "  am.CancelReason �˱�ԭ��,"
			+ "  am.PolicyCancelCode ��������,"
			+ "  date8to10(am.MakeDate) ��������"
			+ " FROM AxaMissingInfoForPolicyTemp am";			
	mSqlStr += " ORDER BY am.MakeDate Desc";
	turnPage.queryModal(mSqlStr,QueryGrid,0,0);
	if(QueryGrid.mulLineCount==0){
		alert("�ϴ���ϢΪ�գ��������ϴ���");
  	  }
}

function queryALL() {
	//initGrid();//Ϊ��ִ�в��ˡ�����
	var startDay = fm.StartDay.value;
	var endDay = fm.EndDay.value;
	
	var mSqlStr = "SELECT " 
			+ "  am.policyno ������, " 
			+ "  am.InsuredIDType ������֤������, "
			+ "  am.ApplicantIDType  Ͷ����֤������, " 
			+ "  am.ApplicantIdNo  Ͷ����֤����, " 
			+ "  am.ApplicantAcctNO Ͷ���������˺�, "
			+ "  am.ApplicationNO Ͷ������,"
			+ "  am.CustomerNo �ͻ����, "
			+ "  am.CancelReason �˱�ԭ��,"
			+ "  am.PolicyCancelCode ��������,"
			+ "  date8to10(am.MakeDate) ��������"
			+ " FROM AxaMissingInfo am where 1=1 ";
	if(null!=startDay&&""!=startDay){
		mSqlStr+=" and am.makedate> "+date10to8(startDay);
	}
	if(null!=endDay&&""!=endDay){
		mSqlStr+=" and am.makedate< "+date10to8(endDay);
	}
	mSqlStr += " ORDER BY am.MakeDate Desc";
	turnPage.queryModal(mSqlStr,QueryGrid,0,0);
	if(QueryGrid.mulLineCount==0){
		alert("��ѯ���Ϊ�գ�������ѡ��");
  	  }
	//if(QueryGrid.mulLineCount==0){
		initBox();
	//}
}
function updateClick() {
	if (fillForm()) { 
		//����������Ӧ�Ĵ���
		//alert("fillForm ����");
		divInsert.style.display = '';
		divQuery.style.display = 'none';
		divCmdButton.style.display = 'none';
		divGrid.style.display = 'none';
		divPage.style.display = 'none';
		
		//alert(fm.OperType.value);
		fm.OperType.value='UPDATE';
	}
}

function cancelClick() {
	if(window.confirm("ȷ��ȡ���ϴ���")){
	initBox();
	}
}
function okClick() {
	//����ȷ��ת�Ƶ���ʽ�� 
	if(window.confirm("ȷ���ϴ���")){
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action="MissInfoUpInSave.jsp"; 
		fm.submit(); 
	}
}

function okUpdate(){
	if(!verifyInputThis()){
		return false;
	}
	if (!confirm("��ȷʵ���޸ĸü�¼��")) {
  		return false;
	}
	//fm.action="MissInfoUpSave.jsp?OperType=UPDATE"; 
	var vExtractedDate = trim(fm.iExtractedDate.value);
	var vPolicyNo = trim(fm.iPolicyNo.value);
	var vInsuredIdType = trim(fm.iInsuredIdType.value);
	var vApplicantIdType = trim(fm.iApplicantIdType.value);
	var vApplicantIdNo = trim(fm.iApplicantIdNo.value);
	var vApplicantAcctNo = trim(fm.iApplicantAcctNo.value);
	var vCustomNO = trim(fm.iCustomNO.value);
	var vCancelReason = trim(fm.iCancelReason.value);
	var vApplicateNo = trim(fm.iApplicateNo.value);
	var vCancelCode = trim(fm.iCancelCode.value);
//	alert(fm.OperType.value);
	fm.action="MissInfoUpSave.jsp?OperType=UPDATE&iExtractedDate="+vExtractedDate+"&iPolicyNo="+vPolicyNo+"&iInsuredIdType="
								+vInsuredIdType+"&iApplicantIdType="+vApplicantIdType+"&iApplicantIdNo="+vApplicantIdNo+"&iApplicantAcctNo="
								+vApplicantAcctNo+"&iCustomNO="+vCustomNO+"&iCancelReason="+vCancelReason+"&iApplicateNo="+vApplicateNo+"&iCancelCode="
								+vCancelCode;
	submitForm();
}
function verifyInputThis() {
	var insuredIdType = fm.iInsuredIdType.value;
	var aplicantIdType = fm.iApplicantIdType.value;
	//A����֤��I���֤��P���գ�S����֤��X����
	if("A"!=insuredIdType&&"I"!=insuredIdType&&"P"!=insuredIdType&&"S"!=insuredIdType&&"X"!=insuredIdType){
		alert("֤������ֻ��ΪA(����֤),I(���֤),P(����),S(����֤),X(����)");
		return false;
	}
	if("A"!=aplicantIdType&&"I"!=aplicantIdType&&"P"!=aplicantIdType&&"S"!=aplicantIdType&&"X"!=aplicantIdType){
		alert("֤������ֻ��ΪA(����֤),I(���֤),P(����),S(����֤),X(����)");
		return false;
	}
	return true;
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
}

function cancelUpdate(){
	divInsert.style.display = 'none';
	divQuery.style.display = '';
	divCmdButton.style.display = '';
	divGrid.style.display = '';
	divPage.style.display = '';
//	initBox();
	//alert("before query");
	queryItems();
	
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
	
	var iArray = QueryGrid.getRowData(mCurRowNo);
	if (null == iArray) {
		alert("��������Ϊ�գ�");
		return false;
	}
	
	fm.iPolicyNo.value=iArray[0];
	fm.iInsuredIdType.value=  iArray[1]; 
	fm.iApplicantIdType.value=  iArray[2];
	fm.iApplicantIdNo.value=  iArray[3];
	fm.iApplicantAcctNo.value=  iArray[4];
	fm.iCustomNO.value = iArray[6];
	fm.iApplicateNo.value = iArray[5];
	fm.iCancelReason.value=  iArray[7]; 
	fm.iExtractedDate.value = iArray[9]; 
	fm.iCancelCode.value = iArray[8]; 
	return true;
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
	
	var iArray = QueryGrid.getRowData(mCurRowNo);
	if (iArray == null) {
		alert("��������Ϊ�գ�");
		return false;
	}  
	
	if (!confirm("��ȷʵɾ���ĸü�¼��")) {
		return false;
	}
	
	fm.iPolicyNo.value= iArray[0];
	//alert(fm.iPolicyNo.value);
	fm.action="MissInfoUpSave.jsp?OperType=DELETE&PolicyNo="+fm.iPolicyNo.value; 
	fm.submit();
}

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
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmitIn( FlagStr, content ,Result )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	else
	{ 
		if (Result!=null&&Result!='')
		{
			var iArray;
			//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
			turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
			//�����ѯ����ַ���
			turnPage.strQueryResult  = Result;
			//ʹ��ģ������Դ������д�ڲ��֮ǰ
			turnPage.useSimulation = 1;
			//��ѯ�ɹ������ַ��������ض�ά����
			var tArr = decodeEasyQueryResult(turnPage.strQueryResult,0);
			turnPage.arrDataCacheSet =chooseArray(tArr,[3,0,1,10,8]);
			//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
			turnPage.pageDisplayGrid = LCGrpImportLogGrid;
			//���ò�ѯ��ʼλ��
			turnPage.pageIndex = 0;
			//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
			var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
			//����MULTILINE������ʾ��ѯ���
			displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
			divImport.style.display='';
		}
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		 
	}

	if ("Succ" == FlagStr) {
		divInsert.style.display = 'none';
		divQuery.style.display = "";
		divCmdButton.style.display = "";
		divGrid.style.display = "";
		divPage.style.display = '';
		fm.OperType.value='';
		divQueryALL.style.display = "none";
//		initBox();
		//alert("before query");
		queryItems();
	}
	if ("UPSucc" == FlagStr) {
		divInsert.style.display = 'none';
		divQuery.style.display = "";
		divCmdButton.style.display = "none";
		divGrid.style.display = "";
		divPage.style.display = '';
		fm.OperType.value='';
		divQueryALL.style.display = "";
	}
}