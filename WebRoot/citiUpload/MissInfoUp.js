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
		alert("在MissInfoUpInit.jsp --> initBox 函数中发生异常:初始化界面错误！");
	}
}
//提交，保存按钮对应操作
function submitFormIn()
{
	var i = 0;
	var vImportFile = fm.all('FileName').value;
	//var infoType = fm.all('InfoType').value;
	//var infoTypeName = fm.all('InfoTypeName').value;
	//if(infoType.trim() == ""){
	//	alert("请选择缺失信息类型");
	//	return false; 
	//}
	//if(infoTypeName.trim() == ""){
	//	alert("请选择缺失信息类型");
	//	return false; 
	//}
	if(vImportFile.trim() == ""){
		alert("请选择上传文件");
		return false; 
	}
 
	var ImportPath = 'citiUpload/temp';
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('ImportPath').value = ImportPath;	
	
	fm.action="MissInfoUpSave.jsp?OperType=UP"; 
	fm.submit(); //提交
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
function queryItems() {
	//initGrid();//为何执行不了···
	var mSqlStr = "SELECT " 
			+ "  am.policyno 保单号, " 
			+ "  am.InsuredIDType 被保人证件类型, "
			+ "  am.ApplicantIDType  投保人证件类型, " 
			+ "  am.ApplicantIdNo  投保人证件号, " 
			+ "  am.ApplicantAcctNO 投保人银行账号, "
			+ "  am.ApplicationNO 投保单号,"
			+ "  am.CustomerNo 客户编号, "
			+ "  am.CancelReason 退保原因,"
			+ "  am.PolicyCancelCode 撤保代码,"
			+ "  date8to10(am.MakeDate) 操作日期"
			+ " FROM AxaMissingInfoForPolicyTemp am";			
	mSqlStr += " ORDER BY am.MakeDate Desc";
	turnPage.queryModal(mSqlStr,QueryGrid,0,0);
	if(QueryGrid.mulLineCount==0){
		alert("上传信息为空，请重新上传！");
  	  }
}

function queryALL() {
	//initGrid();//为何执行不了···
	var startDay = fm.StartDay.value;
	var endDay = fm.EndDay.value;
	
	var mSqlStr = "SELECT " 
			+ "  am.policyno 保单号, " 
			+ "  am.InsuredIDType 被保人证件类型, "
			+ "  am.ApplicantIDType  投保人证件类型, " 
			+ "  am.ApplicantIdNo  投保人证件号, " 
			+ "  am.ApplicantAcctNO 投保人银行账号, "
			+ "  am.ApplicationNO 投保单号,"
			+ "  am.CustomerNo 客户编号, "
			+ "  am.CancelReason 退保原因,"
			+ "  am.PolicyCancelCode 撤保代码,"
			+ "  date8to10(am.MakeDate) 操作日期"
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
		alert("查询结果为空，请重新选择！");
  	  }
	//if(QueryGrid.mulLineCount==0){
		initBox();
	//}
}
function updateClick() {
	if (fillForm()) { 
		//下面增加相应的代码
		//alert("fillForm 下面");
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
	if(window.confirm("确定取消上传吗？")){
	initBox();
	}
}
function okClick() {
	//数据确认转移到正式表 
	if(window.confirm("确定上传吗？")){
		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
	if (!confirm("您确实想修改该记录吗？")) {
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
	//A出生证，I身份证，P护照，S军人证，X其他
	if("A"!=insuredIdType&&"I"!=insuredIdType&&"P"!=insuredIdType&&"S"!=insuredIdType&&"X"!=insuredIdType){
		alert("证件类型只能为A(出生证),I(身份证),P(护照),S(军人证),X(其他)");
		return false;
	}
	if("A"!=aplicantIdType&&"I"!=aplicantIdType&&"P"!=aplicantIdType&&"S"!=aplicantIdType&&"X"!=aplicantIdType){
		alert("证件类型只能为A(出生证),I(身份证),P(护照),S(军人证),X(其他)");
		return false;
	}
	return true;
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
		alert("请先查询！");
		return false;
	}
   
   var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}
	
	var iArray = QueryGrid.getRowData(mCurRowNo);
	if (null == iArray) {
		alert("该数据项为空！");
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
		alert("请先查询！");
		return false;
	}
	
	var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}
	
	var iArray = QueryGrid.getRowData(mCurRowNo);
	if (iArray == null) {
		alert("该数据项为空！");
		return false;
	}  
	
	if (!confirm("您确实删除改该记录吗？")) {
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
//提交后操作,服务器数据返回后执行的操作
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
			//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
			turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
			//保存查询结果字符串
			turnPage.strQueryResult  = Result;
			//使用模拟数据源，必须写在拆分之前
			turnPage.useSimulation = 1;
			//查询成功则拆分字符串，返回二维数组
			var tArr = decodeEasyQueryResult(turnPage.strQueryResult,0);
			turnPage.arrDataCacheSet =chooseArray(tArr,[3,0,1,10,8]);
			//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
			turnPage.pageDisplayGrid = LCGrpImportLogGrid;
			//设置查询起始位置
			turnPage.pageIndex = 0;
			//在查询结果数组中取出符合页面显示大小设置的数组
			var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
			//调用MULTILINE对象显示查询结果
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