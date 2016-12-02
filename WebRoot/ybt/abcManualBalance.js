var cShowInfo;

function deal() {
	if(!verifyInput()) {
		return false;
	} 
	
	var mShowMsg = "正在处理，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg;
	cShowInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.submit();
}

function afterSubmit(pResultMsg) {
	cShowInfo.close();
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + pResultMsg;
	showModalDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
}