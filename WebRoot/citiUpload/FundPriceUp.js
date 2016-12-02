var turnPage = new turnPageClass();
var showInfo;


function initBox() {
	try {
		fm.all("flag").value = "cancel";
		fm.all("ImportPath").value = "";
		fm.all("FileName").value = "";
		//fm.all("InfoTypeName").value = "";
	} catch (re) {
		alert("在FundPriceInit.jsp --> initBox 函数中发生异常:初始化界面错误！");
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
	
	fm.action="FundPriceUpSave.jsp"; 
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
		easyQuery();
		initBox();
		fm.all('flag').value="submit";
	}
	if ("SuccUP" == FlagStr) {
		easyQuery();
		initBox();
		fm.all('flag').value="cancel";
	}
}
function makeUpdate(){
	if(fm.all('flag').value=="cancel"){
		alert("请先上传投连账户价格信息");
		return false;
	}
	if(window.confirm("确定更新吗？")){
		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action="FundPriceUpInSave.jsp?flag=submit"; 
		fm.submit(); 
		initBox();
	}//else{
	//	cancelUpdate();
	//}
}
function cancelUpdate(){
	//alert(fm.all('flag').value);
	if(fm.all('flag').value=="cancel"){
		alert("请先上传投连账户价格信息");
		return false;
	}
	if(window.confirm("取消更新吗？")){
		var showStr="正在取消数据更新，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action="FundPriceUpInSave.jsp?flag=cancel"; 
		fm.submit(); 
		initBox();
	}//else{
	//	makeUpdate();
	//}
}

function easyQuery(){
	YBTGrid.clearData();
	if (!verifyData()) {
		return false;
	}
	 var strSQL = "select fp.PRICEEFFECTIVEDATE,fp.fundcode,"
    	         +"trunc(fp.BIDPRICE,4),trunc(fp.OFFERPRICE,4)"
    	         +" from FundPriceUpTemp fp"	        
	 			 +" where fp.PRICEEFFECTIVEDATE = (select max(priceeffectivedate)from FundPriceUpTemp)";
    strSQL +=" order by fp.PRICEEFFECTIVEDATE desc";
    turnPage.queryModal(strSQL,YBTGrid);
   // if(YBTGrid.mulLineCount==0){
   //	  alert('未查询到文件');
   // 	  }
}