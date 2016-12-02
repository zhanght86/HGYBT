var showInfo;

function checkData(){
	var type = fm.FileTypeCode.value;
	var name = fm.FileTypeName.value;
	var fileName = fm.FileName.value;
	var startDay1 = fm.StartDay1.value;
    var endDay1 = fm.EndDay1.value;
    var startDay2 = fm.StartDay2.value;
    var endDay2 = fm.EndDay2.value;
	if(type == null ||type==""){
		alert("请选择接口文件类型");
		return false;
	}
	if(name == null ||name==""){
		alert("请选择接口文件类型");
		return false;
	}
	if(fileName == null ||fileName==""){
		alert("请选择保单号上传文件");
		return false;
	}
	if (startDay1 == null || startDay1=="") {
		alert("请选择补生产保单的日期区间");
		return false;
	}
	if (endDay1 == null || endDay1=="") {
		alert("请选择补生产保单的日期区间");
		return false;
	}
	if(startDay2 == null ||startDay2==""){
		alert("请选择导出数据的日期区间");
		return false;
	}
	if(endDay2 == null ||endDay2==""){
		alert("请选择导出数据的日期区间");
		return false;
	}
	return true;
}

function creatClick(){
	
	if (!checkData()) {
		return false;
	}
	var mShowMsg = "正在处理，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var type = fm.FileTypeCode.value;
	var startDay1 = fm.StartDay1.value;
	var startDay2 = fm.StartDay2.value;
    var endDay1 = fm.EndDay1.value;
    var endDay2 = fm.EndDay2.value;
   	fm.action="rehandCreatIFSave.jsp?FileTypeCode="+type+"&StartDay1="+startDay1+"&EndDay1="+endDay1+"&StartDay2="+startDay2+"&EndDay2="+endDay2;
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