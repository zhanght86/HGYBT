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


function checkData(){
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	var type = fm.FileTypeCode.value;
	if(type == null ||type==""){
		alert("请选择接口文件类型");
		return false;
	}
	if (startDate == null || startDate=="") {
		alert("起始日期不能为空");
		return false;
	}
	if (endDate == null || endDate=="") {
		alert("终止日期不能为空");
		return false;
	}
	return true;
}

function creatClick(){
	
	//YBTGrid.clearData();	
		if (!checkData()) {
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

function showFileTypeCode() {
	fm.FileTypeCode.value="";
	fm.FileTypeName.value="";
	
	showCodeList('c_iffiletype',[fm.FileTypeCode,fm.FileTypeName],[0,1],null,null,null,1,null,1);
} 

function showFileTypeCodeKey(){
	fm.FileTypeCode.value="";
	fm.FileTypeName.value="";
	showCodeListKey('c_iffiletype',[fm.FileTypeCode,fm.FileTypeName],[0,1],null,null,null,1,null,1);
}

