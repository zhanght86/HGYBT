//               该文件中包含客户端需要处理的函数和事件


var mDebug="1";
var mOperate="";
var showInfo;
var updateAgentCom;
var strRT=""; //存储代理人代码记录
var old_AgentGroup="";
var new_AgentGroup="";
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//提交前的校验、计算

function verifyData(){
	var fileDate = fm.FileDate.value;
	
	if (fileDate == null || fileDate=="") {
		alert("交易日期不能为空");
		return false;
	}
	
	return true;
}

function checkData(){
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	
	if (startDate == null || startDate=="") {
		alert("起始生成日期不能为空");
		return false;
	}
	if (endDate == null || endDate=="") {
		alert("终止生成日期不能为空");
		return false;
	}
	return true;
}

function easyQueryClick()
{ 
	QueryGrid.clearData();
	var fileDate = fm.FileDate.value;
	var cDate = fm.currentDate.value;
      // 书写SQL语句   
        
    var strSQL = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '文件生成成功' WHEN i.Rcode='N' then '该区域无保单信息' WHEN i.Rcode in ('UE','CE') then '文件生成失败' END,"
    	         +" CASE when i.BAK2 is null or i.bak2=0 then '' else '有'||i.bak2||'条错误信息' end, "
    	         +"i.filename,i.makedate,i.maketime,i.area,i.rcode,i.LogNo,i.filepath"
    	         +" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='newcont'"
                 +" and i.makedate = date'"+cDate+"'"
                 +" order by i.logno desc";
    
    
    turnPage.queryModal(strSQL, QueryGrid, 0, 0);
    //for (i=0; i<QueryGrid.mulLineCount; i++) {
	//var test = document.getElementById("spanQueryGrid" + i).children[0];	
	//test.rows[0].cells[9].innerHTML = "<font size=0.8><a href='./download.jsp?path="+QueryGrid.getRowColData(i,4)+QueryGrid.getRowColData(i,5)+"' target=\"_blank\" >下载</a></font>";
      
	//}
  if(QueryGrid.mulLineCount==0){
  alert('未查询到文件');
  }
}

function areaQueryClick(){
	QueryGrid.clearData();
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	var area = fm.Area.value;
	if (!checkData()) {
		return false;
	}
  
	 var strSQL2 = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '文件生成成功' WHEN i.Rcode='N' then '该区域无保单信息' WHEN i.Rcode in ('UE','CE') then '文件生成失败' END,"
    	         +" CASE when i.BAK2 is null or i.bak2=0 then '' else '有'||i.bak2||'条错误信息' end, "
    	         +"i.filename,i.makedate,i.maketime,i.area,i.rcode,i.LogNo,i.filepath";
    	         
    	   var strSQL =" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='newcont'"
                 +" and i.makedate >= DATE'"+startDate +"'"
                 +" and i.makedate <=DATE'"+endDate+"'";
                     
	 
	 if (area != null && ""!=area) {
		 strSQL+= " and i.area='"+area+"' ";
	 }
   strSQL +=" order by i.logno desc";
	 
	 var mSqlStr0=strSQL2+strSQL;
    var mSqlStr1 = "select count(*) "+strSQL;
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
   // for (i=0; i<QueryGrid.mulLineCount; i++) {
	//var test = document.getElementById("spanQueryGrid" + i).children[0];
	//test.rows[0].cells[9].innerHTML = "<font size=0.8><a href='./download.jsp?path="+QueryGrid.getRowColData(i,4)+QueryGrid.getRowColData(i,5)+"' target=\"_blank\" >下载</a></font>";
	
	//}
}
function makeDownLink(){
	for (i=0; i< QueryGrid.mulLineCount; i++) {
		var spanID = document.getElementsByName("QueryGridSpanID")[i].value;
		var test = document.getElementById(spanID).children[0];
		test.rows[0].cells[9].innerHTML = "<font size=0.5><a href='./download.jsp?path="+QueryGrid.getRowColData(i,4)+QueryGrid.getRowColData(i,5)+"' target=\"_blank\" >下载</a></font>";
	}
}

function creatClick(){
	
	//YBTGrid.clearData();
	var fileDate = fm.FileDate.value;
	
		if (!verifyData()) {
		return false;
	}
	var mShowMsg = "正在处理，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.action="IF_NewContSave.jsp?FileDate="+fileDate;    
	fm.submit();
}



/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr,Content)
{
	showInfo.close();
	 
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }
    else
    {
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
        easyQueryClick();
    }
}

function afterDown(downFlagStr,downContent)
{
	//showInfo.close();
	
    if (downFlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + downContent;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }
   
}

function showAreaNo() {
	fm.Area.value="";
	fm.AreaName.value="";
	
	showCodeList('rls_area',[fm.Area,fm.AreaName],[0,1],null,null,null,1,null,1);
} 

function showAreaNoKey(){
	fm.Area.value="";
	fm.AreaName.value="";
	showCodeListKey('rls_area',[fm.Area,fm.AreaName],[0,1],null,null,null,1,null,1);
}

function queryDetail() {
	
	if(turnPage.queryAllRecordCount <= 0) {
		alert("请先查询！");
		return false;
	}
   
   var mCurRowNo= checkedRowNo("QueryGridSel"); 

	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}
	 
	var mRowArr = QueryGrid.getRowData(mCurRowNo);

	if (null == mRowArr) {
		alert("该数据项为空！");
		return false;
	}
	             
    var vLogNo = mRowArr[9]; 
	
	var mSqlStr = "select count(1) from IFdetail B where B.IFLOGNO = '"+vLogNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("查询数据异常!");
			return false;
		}

	if(vLogNo == false){
		return false;
	}
	if (vLogNo != false && vLogNo != ""){
	    nQuery = window.open("NewContDetial.jsp?LogNo=" + vLogNo +"");
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

function downloadThis(){
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
	//var mCurRowNo= checkedRowNo("QueryGridSel");
	//var mRowArr = QueryGrid.getRowData(mCurRowNo);
	var Content = mRowArr[10]+mRowArr[4]; 
	
	//alert(Content);
	//test(Content);
	
	//alert(Content);
	fm.action="../common/FileDownload.jsp?filePath="+Content;    
    fm.submit();
}

