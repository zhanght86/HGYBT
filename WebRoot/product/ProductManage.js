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
		alert("产品起售日期不能为空!");
		return false;
	}
	//结束日期
	if (viEndDate == "") {
		alert("产品停售日期不能为空!");
		return false;
	}
    fm.all("OperType").value="RISKUPDATE";
    fm.OperType.value = "RISKUPDATE";
	submitForm();
    
}
function riskUpdateItems(){
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
	//下面增加相应的代码
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
	if(mRowArr[0]=='工商银行'){
	fm.ProBankCode.value='011';
	}else if(mRowArr[0]=='广发银行'){
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
		//if (!confirm("该机构同一银行渠道的该险种已配置,当前停售日期为"+mEnddate+",您确实想修改该记录吗？")) {
  		//return false;
	     //  }
	     alert("该机构同一银行下的该险种均未停售！");
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

//-------提交校验类-------
function verifyInputThis() {
	var vOperType = trim(fm.OperType.value);
	var viBankCode = trim(fm.iBankCode.value);
	var viBankName = trim(fm.iBankName.value);
	var viRiskCode = trim(fm.iRiskCode.value);
	var viRiskName = trim(fm.iRiskName.value);
	var viActivityCode = trim(fm.iActivityCode.value);
	var viComCode = trim(fm.iComCode.value);
	var vProStateCode = trim(fm.ProStateCode.value);
	
//	//活动代码
//	if (viActivityCode == "") {
//		alert("产品系统不能为空!");
//		return false;
//	}
	
	//公司机构
	if (viComCode == "") {
		alert("公司机构不能为空!");
		return false;
	}
	if (vProStateCode == "") {
		alert("系统状态不能为空!");
		return false;
	}
	if(vProStateCode=="0"){
	if (!confirm("设置后其所属城市都会停售，你确定要设置吗？")) {
		return false;
	}
	}
	else if(vProStateCode=="1"){
	if (!confirm("设置后其所属城市都会'取消'停售，你确定要设置吗？")) {
		return false;
	}
	}
	return true;
}
//校验有效数字
function isDigit(s) {
	var patrn = /^[0-9]{1,20}$/;
	if (!patrn.exec(s))
		return false
	return true
}

function insertClick() {
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
	else{
	var str=mRowArr[6];
	if(str=="停售"){
	alert("该险种已到期！");
	return false;
	}
	else if(str=="未开通"){
	alert("该险种尚未开通！");
	return false;
	}
	}
	//下面增加相应的代码
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
		//下面增加相应的代码
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
//			+ "  (SELECT DC.CODENAME FROM LDCODE DC WHERE DC.codetype='trancom_bank' and DC.CODE=B.BANKCODE) 银行渠道, "	    
//			+ "  B.BankInsuCode 产品代码,"
//			+ "  B.InsuCode AS400代码, "
//			+ "  (SELECT LM.RISKNAME FROM LMRISKAPP LM WHERE LM.RISKCODE=B.INSUCODE) 产品, "
//			+ "  date8to10(B.StartDate) 产品起售日期, "
//			+ "  date8to10(B.EndDate) 产品停售日期, "
//			+ "  B.State 产品系统状态, "
//			+ "  B.BankCode 银行代码 "
//			+ " FROM LDBankMap B" + " WHERE " + "  1 = 1  "
	
//	add by fzg 2012-2-13 新增表bankandinsumap，所以相应的查询方式也改了。
	var mSqlStr = "SELECT" 
		+ "  (SELECT DC.CODENAME FROM LDCODE DC WHERE DC.codetype='trancom_bank' and DC.CODE=B.TranCom) 银行渠道, "	    
		+ "  B.Tran_Code 产品代码,"
		+ "  B.Insu_Code AS400代码, "
		+ "  (SELECT LM.RISKNAME FROM LMRISKAPP LM WHERE LM.RISKCODE=B.INSU_CODE) 产品, "
		+ "  date8to10(B.BAK1) 产品起售日期, "
		+ "  date8to10(B.BAK2) 产品停售日期, "
		+ "  CASE WHEN to_number(to_char(sysdate, 'yyyymmdd')) < to_number(B.BAK1) THEN '未开通' WHEN to_number(to_char(sysdate, 'yyyymmdd')) >= to_number(B.BAK2) THEN '停售' ELSE '在售' END 产品系统状态,"
		+ "  B.TranCom 银行代码 "
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
	
	
//	add by fzg 2012-2-14 校验结果集为空时给出提示信息。
	var mResult = easyExecSql(mSqlStr);
	if(mResult==null){
		alert("产品信息为空!");
		return false;
	}
	turnPage.queryModal(mSqlStr, QueryGrid, 0, 0);

}

//查询事件
function queryItems() {

	initGrid();

	var vRiskCode = trim(fm.iRiskCode.value);
	var vBankCode = trim(fm.iBankCode.value);
	var viProductCode = trim(fm.iProductCode.value);
	var viRiskName = trim(fm.iRiskName.value);

	var mSqlStr = "SELECT " 
			+ "  (SELECT LM.RISKNAME FROM LMRISKAPP LM WHERE LM.RISKNAME='"+viRiskName+"') 产品, "
			+ "  (SELECT DC.CODENAME FROM LDCODE DC WHERE DC.codetype='trancom_bank' and DC.CODE=D.BANKCODE) 银行渠道, "			
			+ "  (SELECT LD.NAME FROM LDCOM LD WHERE LD.COMCODE=(SELECT LD.UPCOMCODE FROM LDCOM LD WHERE LD.COMCODE=D.ComCODE)) 区域名称, "
			+ "  (SELECT LD.NAME FROM LDCOM LD WHERE LD.COMCODE=D.ComCODE) 机构名称, "			
			+ "  CASE WHEN D.ActivityCode = '1' THEN '在售'  WHEN D.ActivityCode = '2' THEN '停售' WHEN D.ActivityCode = '3' THEN '未开通' ELSE '--' END 产品系统状态,"
			+ "  date8to10(D.StartDate) 起始日期, "
			+ "  date8to10(D.EndDate) 终止日期, "
			
			+ "  D.BankCode 银行渠道代码, "
			+ "  D.InsuCode 产品代码, "
			+ "  D.COMCode 机构代码 "
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

//修改事件
function update() {
	
	fm.OperType.value = "UPDATE";
	submitForm();
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
//转换成YYYY-MM-DD日期
                function ChangeTimeToString(DateIn)
                {
                    var Year=0;
                    var Month=0;
                    var Day=0;
                    //初始化时间
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


	
	if (!confirm("该机构同一银行渠道的该险种已配置,当前停售日期为您确实想修改该记录吗？")) {
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
		alert("请选中一条记录！");
		return false;
	}
	
	var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (mRowArr == null) {
		alert("该数据项为空！");
		return false;
	}  
	
	if (!confirm("您确实删除改该记录吗？")) {
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
		alert("操作类型不能为空！");
		return false;
	}
	VMaCodeName=fm.iComCodeName.value;
	VMaCode=fm.iComCode.value;
	var mShowMsg = "正在处理，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.submit();
	
	//add by fzg 2012-2-14----这样可以连续设置产品销售许可
	var date = new Date();
	fm.iStartDate.value=ChangeTimeToString(date);
	//fm.iActivityCode.value="";

} 

//提交后操作,服务器数据返回后执行的操作
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
			riskQueryItems();	//提交后自动查询
		}else {
		divGrid.style.display = "";
			queryItems();	//提交后自动查询
		}
	} 
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


function showiAreaNo() {  
	fm.iAreaName.value="";
	showCodeList('Area_Print',[fm.iAreaNo,fm.iAreaName],[0,1],null,null,null,1,null,1);
} 
//选择区域时出发的事件
function showAreaNo() {  
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value="";
	showCodeList('Area_User',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
}  
//选择区域时出发的事件
function showAreaNoKey() {
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value=""; 
	showCodeListKey('Area_User',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
}

//选择城市时触发的事件
function showCityNo() {
	var vAreaNo = trim(fm.AreaNo.value);
	if("" != vAreaNo){
		
		if("86" == vAreaNo){
			alert("选择总公司时,无需选择城市!");
			fm.CityNo.value="";
			fm.CityName.value="";
			return false;
		}
		
		var mSqlStr = "select count(1) from LDCOM D where D.AREAID = '"+vAreaNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("该区域目前无机构!");
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
//选择城市时触发的事件
function showCityNoKey() {
var vAreaNo = trim(fm.AreaNo.value);
	if("" != vAreaNo){
		if("86" == vAreaNo){
			alert("选择总公司时,无需选择城市!");
			fm.CityNo.value="";
			fm.CityName.value="";
			return false;
		}
		var mSqlStr = "select count(1) from LDCOM D where D.AREAID = '"+vAreaNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("该区域目前无机构!");
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


//查询事件
function querySubmit() {

	initGrid();

	var vAreaNo = trim(fm.iAreaNo.value);
	var vComGrade = trim(fm.iComGrade.value);
	var vOutComCode = trim(fm.iOutComCode.value);
	var vName = trim(fm.iName.value);

	var mSqlStr = "SELECT " 
			+ "  D.AREANAME     机构所属区域, " 
			+ "  D.OUTCOMCODE      机构编码, "
			+ "  D.NAME     	机构名称, " 
			+ "  D.SHORTNAME     机构缩写, " 
			+ "  (SELECT LD.CODENAME FROM LDCODE LD WHERE CODETYPE='com_grade' and LD.CODE=D.COMGRADE) 机构类别, "
			+ "  (SELECT DD.COMCODE||DD.NAME FROM LDCOM DD WHERE DD.COMCODE=D.UPCOMCODE) 上级机构,"
			+ "  D.AREAID 地区编号,"
			+ "  D.COMCODE 机构编码,"
			+ "  D.UPCOMCODE 上级机构, "
			+ "  D.COMGRADE 机构类别代码 "
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
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
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
