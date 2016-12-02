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
		alert("起始交易日期不能为空");
		return false;
	}
	if (endDate == null || endDate=="") {
		alert("终止交易日期不能为空");
		return false;
	}
	return true;
}

function easyQueryClick()
{ 
	YBTGrid.clearData();
	var fileDate = fm.FileDate.value;
	var cDate = fm.currentDate.value;
      // 书写SQL语句   
        
    var strSQL = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '文件生成成功' WHEN i.Rcode='N' then '该区域无保单信息' WHEN i.Rcode in ('UE','CE') then '文件生成失败' END,"
    	         +"i.filepath,i.filename,i.makedate,i.maketime,'',i.area,i.rcode"
    	         +" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='fin'"
                 +" and i.makedate = date'"+cDate+"'"
                 +" order by i.logno desc";
    
    
    turnPage.queryModal(strSQL,YBTGrid);
    for (i=0; i<YBTGrid.mulLineCount; i++) {
	var test = document.getElementById("spanYBTGrid" + i).children[0];	
	test.rows[0].cells[8].innerHTML = "<font size=0.8><a href='./download.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,5)+"' target=\"_blank\" >下载</a></font>";
      
	}
  if(YBTGrid.mulLineCount==0){
  alert('未查询到文件');
  }
}

function areaQueryClick(){
	YBTGrid.clearData();
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	var area = fm.Area.value;
	if (!checkData()) {
		return false;
	}
	 var strSQL2 = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '文件生成成功' WHEN i.Rcode='N' then '该区域无保单信息' WHEN i.Rcode in ('UE','CE') then '文件生成失败' END,"
    	         +"i.filepath,i.filename,i.makedate,i.maketime,'',i.area,i.rcode";
    	        
    	 var strSQL =" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='fin'"
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
	turnPage.queryModal(mSqlStr0, YBTGrid,0,0);
	}
	else if(mComInfos==0){
	alert("查询结果为空，请重新选择！");
	}
	else {
	alert("查询结果太大，请重新选择！");
	}
    for (i=0; i<YBTGrid.mulLineCount; i++) {
	var test = document.getElementById("spanYBTGrid" + i).children[0];
	test.rows[0].cells[8].innerHTML = "<font size=0.8><a href='./download.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,5)+"' target=\"_blank\" >下载</a></font>";
	
	}
}
function makeDownLink(){
	for (i=0; i< YBTGrid.mulLineCount; i++) {
		var spanID = document.getElementsByName("YBTGridSpanID")[i].value;
		var test = document.getElementById(spanID).children[0];
		test.rows[0].cells[8].innerHTML = "<font size=0.5><a href='./download.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,5)+"' target=\"_blank\" >下载</a></font>";
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

