//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var arrResult;
//提交，保存按钮对应操作
function submitFormIn()
{
	var i = 0;
	var vImportFile = fm.all('FileName').value;
	var vManageCodeNo=trim(fm.ManageCodeNo.value);
	if(vImportFile.trim() == ""){
		alert("请选择上传文件!");
		return false; 
	}
	if(vManageCodeNo.length!=9){
		alert("请选择市级管理机构再上传文件!");
		return false; 
	}
	
	var ImportPath = 'agent/temp';
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('ImportPath').value = ImportPath;
	
	var vManageCodeNo = trim(fm.ManageCodeNo.value);
	var vAreaNo = vManageCodeNo.substring(2,4);
	var vCityNo = vManageCodeNo.substring(6,9);
	var vSysFlag = trim(fm.SysFlag.value);
	fm.action="ITPrintInSave.jsp?AreaNo=" + vAreaNo + "&CityNo=" + vCityNo+ "&SysFlag=" + vSysFlag+"&ManageCodeNo="+vManageCodeNo; 
	fm.submit(); //提交
}
//查询事件
function ITItemQuery() {
	
	initGrid();
    var vManageCodeNo = trim(fm.ManageForCheck.value);
    var vSysFlag = trim(fm.SysFlag.value);
	var vShitNoSta = trim(fm.ShitNoSta.value);
	var vShitState = trim(fm.ShitState.value);
	var mSqlStr2 = "SELECT  contno 保单号, case when status='0' then '未使用' ";
	    mSqlStr2+=" when status ='1' then '已使用' else '--' end  保单状态,"; 
		mSqlStr2 +=" citycode  城市代码,sysflag 系统代码 ";
	var mSqlStr= " from lkcontno lk where 1=1";
	if(vSysFlag!=""){
          mSqlStr+= " AND lk.SYSFLAG LIKE '"+vSysFlag+"%' ";
          }
          if(vShitNoSta!=""){
          mSqlStr+= " AND lk.CONTNO LIKE '"+vShitNoSta+"%' ";
          }
          	if(vShitState!=""){
          mSqlStr+= " AND lk.STATUS = '"+vShitState+"' ";
          }
          if(vManageCodeNo!=""){
          mSqlStr+= " AND citycode in (select axashortname from ldcom ";
          mSqlStr+="  where comcode like '"+vManageCodeNo+"%') ";
          }
          mSqlStr += " ORDER BY LK.CONTNO ";
          var mSqlStr0=mSqlStr2+mSqlStr;
    var mSqlStr1 = "select count(*) "+mSqlStr;
    var mComInfos = easyExecSql(mSqlStr1); 
    if(mComInfos > 0 ){
	turnPage.queryModal(mSqlStr0, QueryGrid, 0, 0);
	}
	else if(mComInfos==0){
	alert("查询结果为空，请重新选择！");
	}
}



function ITPrintQuery() {
	fm.PrintFlag.value="print";
	if (!beforeSubmit()) {
		return false;
	}
	var vSysFlag = "";
	var vManageCodeNo = trim(fm.ManageCodeNoOut.value);
	var vAreaNo = "";
	var vCityNo = "";
	fm.action="ITPrintSave.jsp?AreaNo=" + vAreaNo + "&CityNo=" + vCityNo + "&SysFlag=" + vSysFlag+"&ManageCodeNo="+vManageCodeNo; 
	fm.submit();
}

function ITPrintPreviewQuery() {
	var vSysFlag = "";
	var myDate = new Date();
	var vTime = myDate.getTime();  
	var vManageCodeNo= trim(fm.ManageCodeNoOut.value);    
	var vAreaNo = "";
	var vCityNo = "";
	fm.PrintFlag.value="preview";
	if (!beforeSubmit()) {
		return false;
	} 
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	
	var winQuery = window.open("ITPreview.jsp?AreaNo=" + vAreaNo+ "&CityNo=" + vCityNo + "&SysFlag=" +vSysFlag+"&ManageCodeNo="+vManageCodeNo+"");
    showInfo.close();

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


//选择区域时出发的事件
function showAreaNo() {
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value="";
	showCodeList('Area_Print',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
} 
//选择区域时出发的事件
function showAreaNoKey() {
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value=""; 
	showCodeListKey('Area_Print',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
}

//选择城市时触发的事件
function showCityNo() {
	var vAreaNo = trim(fm.AreaNo.value);
	if("" != vAreaNo){
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

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr,Content)
{
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }
    else
    {
        fm.action="../common/ExcelFileDownload.jsp?filePath="+Content;    
        fm.submit();
    }
    fm.action="./ITPrintSave.jsp"; 
}
/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmitforPrew(FlagStr,Content)
{
alert("1");
   showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }
}
function SelectCom(){
    fm.SelectFlag.value='0';
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
  function Select(){
    fm.SelectFlag.value='1';
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
  function afterQuery(vReturn) {
   var sFlag=trim(fm.SelectFlag.value);
    if(vReturn==null){
    alert("机构不能为空！");
    }
    
	else if(vReturn != null&& sFlag=='0') {
	 var mSqlStr = "select D.NAME from LDCOM D where D.comcode = '" + vReturn + "'";
		 var mComInfos = easyExecSql(mSqlStr);
		fm.ManageCodeName.value=vReturn+mComInfos;
		fm.ManageCodeNo.value=vReturn;
	} 
	else if(vReturn != null&& sFlag=='1') {
	 var mSqlStr = "select D.NAME from LDCOM D where D.comcode = '" + vReturn + "'";
		 var mComInfos = easyExecSql(mSqlStr);
		fm.ManageForCheckName.value=vReturn+mComInfos;
		fm.ManageForCheck.value=vReturn;
	} 
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
		
		if ("Succ" == FlagStr) {
		//queryItems();	//提交后自动查询
	}
	}
}
function resetform(){
fm.reset();
initForm() ;
}