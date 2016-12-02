var turnPage = new turnPageClass();
var showInfo;

function resetForm() {
	initForm();
}

function queryItems() {
	fm.update_bt.value = "�� ��";
	
	initGrid();
	
	setAgentCom();
	
	if (""==fm.TranCom.value && ""==fm.AgentCom.value) {
		alert("��ѡ�����л�����������");
		return false;
	}
	
	var mSqlStr = "select a.TranCom, a.NodeNo, a.AgentCom, a.AgentComName, a.ManageCom,  b.ManageComName, a.MapNo"
		+  " from NodeMap a, Agent b where a.AgentCom=b.AgentCom"
		+ getWherePart('a.TranCom', 'TranCom')
		+ getWherePart('a.NodeNo', 'NodeNo')  
		+ getWherePart('a.Type', 'NodeType')
		+ getWherePart('a.AgentCom', 'AgentCom', 'like')
		//+ getWherePart('b.AgentCode', 'AgentCode')
		+ " and a.ManageCom like '" + cManageCom + "%'"
		+ " order by a.MapNo desc";
	 
	turnPage = new turnPageClass();
	turnPage.pageLineNum = 20;
	turnPage.queryModal(mSqlStr, QueryGrid);
}

function addItem() {
	if ("1"==fm.TranCom.value){
		if (fm.NodeNo.value.trim().length!=10){
		//	alert(fm.NodeNo.value); 
		//	alert(fm.NodeNo.value.trim().length);
			alert("����������������");  
			return false;
		}	 
		if("�޷���"	== fm.AgentCom5Name.value){
				alert(" δѡ��������ܲ�����");  
			  return false;
			 ; 
		}
		if("�޴�����" == fm.AgentCom6Name.value && "�޷���"	== fm.AgentCom5Name.value ){
				alert(" δѡ���������ܲ�����");  
			  return false;
			 ; 
		}

	}
	if ("3"==fm.TranCom.value){
		if (fm.NodeNo.value.trim().length!=9){
			alert("����������������");
			return false;
		}	
	}
	if ("4"==fm.TranCom.value){
		if (fm.NodeNo.value.trim().length!=6){
			alert("����������������");
			return false;
		}	
	}
	

	if (fm.NodeNo.value.trim()==""){
			alert("�����뽻�����㣡");
			return false;
	}
	if (fm.AgentCom5.value.trim()==""){
			alert("��ѡ�����");
			return false;
	}
	
	fm.update_bt.value = "�� ��";
	
	setAgentCom();
	changeAgentCode()
	if(!verifyInput()) {
		return false;
	}
	
	fm.OperType.value = "INSERT";
	submitForm();
}

function updateItem() {
	if ("�� ��" == fm.update_bt.value) {
		return fillForm();
	} else {
		return submitUpdate();
	}
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
	 
	fm.TranCom.value = mRowArr[0];
	fm.TranComName.value = easyExecSql("select CodeName from ldcode where CodeType='trancom_bank' and Code='"+ fm.TranCom.value +"'");
	fm.NodeNo.value = mRowArr[1];
	if (mRowArr[2].length >= 8) {
		fm.AgentCom1.value = mRowArr[2].substring(0, 3);
		fm.AgentCom1Name.value = easyExecSql("select AgentComName from Agent where AgentCom='"+fm.AgentCom1.value+"'");
	}
	if (mRowArr[2].length >= 10) {
		fm.AgentCom2.value = mRowArr[2].substring(0, 5);
		fm.AgentCom2Name.value = easyExecSql("select AgentComName from Agent where AgentCom='"+fm.AgentCom2.value+"'");
	}
	if (mRowArr[2].length >= 10) {
		fm.AgentCom3.value = mRowArr[2].substring(0, 7);
		fm.AgentCom3Name.value = easyExecSql("select AgentComName from Agent where AgentCom='"+fm.AgentCom3.value+"'");
	} 
	
	if (mRowArr[2].length >= 10) {
		fm.AgentCom4.value = mRowArr[2].substring(0, 9);
		fm.AgentCom4Name.value = easyExecSql("select AgentComName from Agent where AgentCom='"+fm.AgentCom4.value+"'");
	} 
	 
	if (mRowArr[2].length >= 11) {
		fm.AgentCom5.value = mRowArr[2].substring(0, 11);
		//fm.AgentCom5Name.value = easyExecSql("select AgentComName from Agent where AgentCom='"+fm.AgentCom5.value+"'");
		var AgentCom5NameFill = easyExecSql("select AgentComName from Agent where AgentCom='"+fm.AgentCom5.value+"'");
	  
		if(AgentCom5NameFill == null){
			fm.AgentCom5Name.value = "�޷���"; 
		}else if(AgentCom5NameFill != null)
		{
			fm.AgentCom5Name.value = AgentCom5NameFill;
		} 
	}    
	 
	if (mRowArr[2].length >= 13) { 
		alert(mRowArr[2].length);
		fm.AgentCom6.value = mRowArr[2].substring(0, 13);
		fm.AgentCom6Name.value = easyExecSql("select AgentComName from Agent where AgentCom='"+fm.AgentCom6.value+"'");
	}  
 
//	fm.AgentCode.value = mRowArr[4];
//	fm.AgentName.value = mRowArr[5];
//alert(mRowArr[4]);
//alert(mRowArr[5]);
//alert(mRowArr[6]);   
	fm.ManageCom.value = mRowArr[4];
	fm.ManageComName.value = mRowArr[5];
	fm.MapNo.value = mRowArr[6];
	
	fm.update_bt.value = "�����޸�";
}

function submitUpdate() {
	
	if ("1"==fm.TranCom.value){
		if (fm.NodeNo.value.trim().length!=10){
		//	alert(fm.NodeNo.value); 
		//	alert(fm.NodeNo.value.trim().length);
			alert("����������������");  
			return false;
		}	 
		if("�޷���"	== fm.AgentCom5Name.value){
				alert(" δѡ��������ܲ�����");  
			  return false;
			 ; 
		}
		if("�޴�����" == fm.AgentCom6Name.value && "�޷���"	== fm.AgentCom5Name.value ){
				alert(" δѡ���������ܲ�����");  
			  return false;
			 ; 
		}

	}
	if ("3"==fm.TranCom.value){
		if (fm.NodeNo.value.trim().length!=9){
			alert("����������������");
			return false;
		}	
	}
	if ("4"==fm.TranCom.value){
		if (fm.NodeNo.value.trim().length!=6){
			alert("����������������");
			return false;
		}	
	}
	

	if (fm.NodeNo.value.trim()==""){
			alert("�����뽻�����㣡");
			return false;
	}
	if (fm.AgentCom5.value.trim()==""){
			alert("��ѡ�����");
			return false;
	}
	
	setAgentCom();
	changeAgentCode(); 
	if (!verifyInput()) {
		return false;
	}
	
	if (!confirm("��ȷʵ���޸ĸü�¼��")) {
  		return false;
	}
	
	fm.OperType.value = "UPDATE";
	submitForm();
	
	fm.update_bt.value = "�� ��";
}

function delItem() {
	fm.update_bt.value = "�� ��";
	
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
	
	fm.MapNo.value = mRowArr[6];
	  
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
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(pFlag, pMessage) {
	showInfo.close();
	
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + pMessage;
	showModalDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	if ("Succ" == pFlag) {
		queryItems();	//�ύ���Զ���ѯ
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

function setAgentCom() { 
	if(fm.AgentCom6.value == ""){	 
		fm.AgentCom.value = fm.AgentCom5.value;
		fm.AgentComName.value = fm.AgentCom5Name.value;
	}else if(fm.AgentCom6.value != ""){	 
		fm.AgentCom.value = fm.AgentCom6.value;
		fm.AgentComName.value = fm.AgentCom6Name.value;
	}
   
	 
	//alert(fm.AgentCom.value+'---------'+fm.AgentComName.value);
	if ("" == fm.AgentCom.value) {
		fm.AgentCom.value = fm.AgentCom3.value;
		fm.AgentComName.value = fm.AgentCom3Name.value;
	}
	if ("" == fm.AgentCom.value) {
		fm.AgentCom.value = fm.AgentCom2.value;
		fm.AgentComName.value = fm.AgentCom2Name.value;
	}
	if ("" == fm.AgentCom.value) {
		fm.AgentCom.value = fm.AgentCom1.value;
		fm.AgentComName.value = fm.AgentCom1Name.value;
	}
}
 
function showAgentCom1() {
	showCodeList('AgentCom-',[fm.AgentCom1,fm.AgentCom1Name],[0,1],null,'type=#3# and length(AgentCom)<=3',null,1,null,1);
	//fm.AgentCom1Name.value = "";
	fm.AgentCom2.value = ""; 
	fm.AgentCom2Name.value = "";
	fm.AgentCom3.value = "";
	fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = "";
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
} 

function showAgentCom1Key() { 
	showCodeListKey('AgentCom-',[fm.AgentCom1,fm.AgentCom1Name],[0,1],null,'type=#3# and length(AgentCom)<=3',null,1,null,1);

	//fm.AgentCom1Name.value = "";
	fm.AgentCom2.value = "";
	fm.AgentCom2Name.value = "";
	fm.AgentCom3.value = "";
	fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = "";
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
	
}

function showAgentCom2() {
	showCodeList('AgentCom',[fm.AgentCom2,fm.AgentCom2Name],[0,1],null,'AgentCom like #'+fm.AgentCom1.value+'__#',null,1,null,1);
	//fm.AgentCom2Name.value = "";
	fm.AgentCom3.value = "";
	fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = "";
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
}
function showAgentCom2Key() {
	showCodeListKey('AgentCom',[fm.AgentCom2,fm.AgentCom2Name],[0,1],null,'AgentCom like #'+fm.AgentCom1.value+'__#',null,1,null,1);
	//fm.AgentCom2Name.value = "";
	fm.AgentCom3.value = "";
	fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = "";
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
}

function showAgentCom3() {
	showCodeList('AgentCom',[fm.AgentCom3,fm.AgentCom3Name],[0,1],null,'AgentCom like #'+fm.AgentCom2.value+'__#',null,1,null,1);
	//fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = "";
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
}
function showAgentCom3Key() {
	showCodeListKey('AgentCom',[fm.AgentCom3,fm.AgentCom3Name],[0,1],null,'AgentCom like #'+fm.AgentCom2.value+'__#',null,1,null,1);

	//fm.AgentCom3Name.value = "";
	fm.AgentCom4.value = "";
	fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = "";
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
}

function showAgentCom4() {
	showCodeList('AgentCom',[fm.AgentCom4,fm.AgentCom4Name],[0,1],null,'AgentCom like #'+fm.AgentCom3.value+'__#',null,1,350);
	//fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = "";
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
	
}   
function showAgentCom4Key() {

	showCodeListKey('AgentCom',[fm.AgentCom4,fm.AgentCom4Name],[0,1],null,'AgentCom like #'+fm.AgentCom3.value+'__#',null,1,350);
	//fm.AgentCom4Name.value = "";
	fm.AgentCom5.value = "";
	fm.AgentCom5Name.value = "";
	fm.AgentCom6.value = ""; 
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
	
}

function showAgentCom5() {

	var mSqlStr = "select AgentCom, AgentComName from Agent where AgentCom like '"+fm.AgentCom4.value+"__' and state = 1 and ManageCom like '"+ fm.iManageCom.value+ "%'";
	var mComInfos = easyExecSql(mSqlStr);
	 
	if(mComInfos == null){ 
		//alert(mComInfos + "�޷���");
		fm.AgentCom5.value = fm.AgentCom4.value + "00";
		//alert(fm.AgentCom5.value);  
		fm.AgentCom5Name.value = "�޷���";  
		fm.AgentCom6.value = ""; 
		fm.AgentCom6Name.value = "";
		fm.AgentCode.value = ""; 
		fm.AgentName.value = ""; 
		fm.ManageCom.value = "";
		fm.ManageComName.value = "";
	  
	
	}else if(mComInfos != null)
	{
		
		showCodeList('AgentCom',[fm.AgentCom5,fm.AgentCom5Name],[0,1],null,'AgentCom like #'+fm.AgentCom4.value+'__#'+ 'and state = 1',null,1,350);
		 
		//fm.AgentCom5Name.value = "";
		fm.AgentCom6.value = "";
		fm.AgentCom6Name.value = "";
		fm.AgentCode.value = "";
		fm.AgentName.value = "";
		fm.ManageCom.value = "";
		fm.ManageComName.value = "";
	} 
}
function showAgentCom5Key() {
	var mSqlStr = "select AgentCom, AgentComName from Agent where AgentCom like '"+fm.AgentCom4.value+"__' and ManageCom like '"+ fm.iManageCom.value+ "%'";
	var mComInfos = easyExecSql(mSqlStr);
	
	if(mComInfos == null){  
		//alert(mComInfos + "�޷���");

		fm.AgentCom5.value = fm.AgentCom4.value + "00";  
		//alert(fm.AgentCom5.value); 
		fm.AgentCom5Name.value = "�޷���";
		fm.AgentCom6.value = ""; 
		fm.AgentCom6Name.value = "";
		fm.AgentCode.value = "";
		fm.AgentName.value = ""; 
		fm.ManageCom.value = "";
		fm.ManageComName.value = "";
		//alert(mComInfos);
	
}else if(mComInfos != null)
	{
		
		showCodeListKey('AgentCom',[fm.AgentCom5,fm.AgentCom5Name],[0,1],null,'AgentCom like #'+fm.AgentCom4.value+'__#' + 'and state = 1',null,1,350);
		//fm.AgentCom5Name.value = "";
		fm.AgentCom6.value = ""; 
		fm.AgentCom6Name.value = "";
		fm.AgentCode.value = "";
		fm.AgentName.value = "";
		fm.ManageCom.value = "";
		fm.ManageComName.value = "";
	}
}

function showAgentCom6() {

	if(fm.AgentCom5Name.value == "�޷���"){ 	 
	showCodeList('AgentCom',[fm.AgentCom6,fm.AgentCom6Name],[0,1],null,'AgentCom like #'+fm.AgentCom5.value+'__#'+ 'and state = 1',null,1,350);
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = ""; 
	fm.AgentName.value = "";
	fm.ManageCom.value = ""; 
	fm.ManageComName.value = "";
	}else if(fm.AgentCom5Name.value == "" && fm.AgentCom5.value == ""){

		showCodeList('AgentCom',[fm.AgentCom6,fm.AgentCom6Name],[0,1],null,'AgentCom like #'+fm.AgentCom4.value+"00"+'__#'+ 'and state = 1',null,1,350);
			fm.AgentCom6Name.value = "";
			fm.AgentCode.value = ""; 
			fm.AgentName.value = "";  
			fm.ManageCom.value = ""; 
			fm.ManageComName.value = "";   
		 
	}
	else if(fm.AgentCom5Name.value != "" && fm.AgentCom5.value != ""){
	var mSqlStr = "select AgentCom, AgentComName from Agent where AgentCom like '"+fm.AgentCom5.value+"__' and ManageCom like '"+ fm.iManageCom.value+ "%'";
	var mComInfos = easyExecSql(mSqlStr);
	if(mComInfos == null){  

		fm.AgentCom6.value = ""; 
		fm.AgentCom6Name.value = "�޴�����";
		fm.AgentCode.value = "";
		fm.AgentName.value = ""; 
		fm.ManageCom.value = ""; 
		fm.ManageComName.value = "";
		
	
}else if(mComInfos != null)
	{	
	showCodeList('AgentCom',[fm.AgentCom6,fm.AgentCom6Name],[0,1],null,'AgentCom like #'+fm.AgentCom5.value+'__#'+ 'and state = 1',null,1,350);
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
	}
	} 
} 
function showAgentCom6Key() {

	if(fm.AgentCom5Name.value == "�޷���"){ 	 
	showCodeList('AgentCom',[fm.AgentCom6,fm.AgentCom6Name],[0,1],null,'AgentCom like #'+fm.AgentCom5.value+'__#'+ 'and state = 1',null,1,350);
	fm.AgentCom6Name.value = "";
	fm.AgentCode.value = "";
	fm.AgentName.value = ""; 
	fm.ManageCom.value = "";  
	fm.ManageComName.value = ""; 
	}else if(fm.AgentCom5Name.value == "" && fm.AgentCom5.value == ""){
		
		showCodeListKey('AgentCom',[fm.AgentCom6,fm.AgentCom6Name],[0,1],null,'AgentCom like #'+fm.AgentCom4.value+"00"+'__#'+ 'and state = 1',null,1,350);
			fm.AgentCom6Name.value = "";
			fm.AgentCode.value = ""; 
			fm.AgentName.value = "";
			fm.ManageCom.value = ""; 
			fm.ManageComName.value = "";
		
	}
	else if(fm.AgentCom5Name.value != "" && fm.AgentCom5.value != ""){
	var mSqlStr = "select AgentCom, AgentComName from Agent where AgentCom like '"+fm.AgentCom5.value+"__' and ManageCom like '"+ fm.iManageCom.value+ "%'";
	var mComInfos = easyExecSql(mSqlStr);
	if(mComInfos == null){  
		
		fm.AgentCom6.value = ""; 
		fm.AgentCom6Name.value = "�޴�����";
		fm.AgentCode.value = "";
		fm.AgentName.value = ""; 
		fm.ManageCom.value = "";
		fm.ManageComName.value = "";
		
	
		}else if(mComInfos != null)
		{	
			showCodeListKey('AgentCom',[fm.AgentCom6,fm.AgentCom6Name],[0,1],null,'AgentCom like #'+fm.AgentCom5.value+'__#'+ 'and state = 1',null,1,350);
			fm.AgentCom6Name.value = "";
			fm.AgentCode.value = ""; 
			fm.AgentName.value = "";
			fm.ManageCom.value = "";
			fm.ManageComName.value = "";
			}
			} 
}

function showAgentCode() {
	if(fm.AgentCom6.value == ""){
	showCodeList('Agent',[fm.AgentCode,fm.AgentName],[0,1],null,'AgentCom=#'+fm.AgentCom5.value+'#'+ 'and state = 1',null,1,null,1);
	
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
	}else if(fm.AgentCom6.value != "")
{
	showCodeList('Agent',[fm.AgentCode,fm.AgentName],[0,1],null,'AgentCom=#'+fm.AgentCom6.value+'#'+ 'and state = 1',null,1,null,1);
	
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
	}
}
function showAgentCodeKey() {
	if(fm.AgentCom6.value == ""){
	showCodeListKey('Agent',[fm.AgentCode,fm.AgentName],[0,1],null,'AgentCom=#'+fm.AgentCom5.value+'#'+ 'and state = 1',null,1,null,1);
	
	fm.AgentName.value = "";
	fm.ManageCom.value = ""; 
	fm.ManageComName.value = "";
	}else if(fm.AgentCom6.value != "")
{
	showCodeListKey('Agent',[fm.AgentCode,fm.AgentName],[0,1],null,'AgentCom=#'+fm.AgentCom6.value+'#'+ 'and state = 1',null,1,null,1);
	
	fm.AgentName.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = ""; 
	}
}

function changeAgentCode() {
	if(fm.AgentCom6.value != ""){
	var mSqlStr = "select ManageCom, ManageComName from Agent where AgentCom='"+fm.AgentCom6.value+"'";
	var mComInfos = easyExecSql(mSqlStr); 
	
	fm.ManageCom.value = mComInfos[0][0];
	fm.ManageComName.value = mComInfos[0][1];
	}else if(fm.AgentCom6.value == ""){
	var mSqlStr = "select ManageCom, ManageComName from Agent where AgentCom='"+fm.AgentCom5.value+"'";
	var mComInfos = easyExecSql(mSqlStr); 
	
	fm.ManageCom.value = mComInfos[0][0];
	fm.ManageComName.value = mComInfos[0][1];
	} 
//	alert(fm.ManageCom.value);  
//	alert(fm.ManageComName.value);
}