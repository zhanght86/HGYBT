var cShowInfo;

function showTranType() {
	fm.tranTypeName.value='';
	return showCodeList('abcbusitype',[fm.tranType,fm.tranTypeName],[0,1],null,null,null,1,null,1);
}
function showTranTypeKey() {
	fm.TranTypeName.value='';
	return showCodeListKey('abcbusitype',[fm.tranType,fm.tranTypeName],[0,1],null,null,null,1,null,1);
}

function deal() {
	if(!verifyDate()) {
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
	cShowInfo = window.showModalDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
}

/**翻页*/
var turnPage = new turnPageClass();

/**结果集*/
var arrDataSet;


/**
 * 查询
 */
function easyQueryClick()
{
	initGrid();
	
	//交易类型
	var tranType = fm.tranType.value;
	//交易日期
	var  tranDate = fm.TranDate.value;
	
	//查询sql
	var strSQL = "select a.blctranno, date8to10(a.trandate),a.appntname as applicantName," 
		+ "(select codename from ldcode where code = a.bak3 and codetype = 'abcbusitype') as businame,"
	    + "a.contno,a.proposalprtno,a.appntname,a.insuredname,a.prem, case a.bak5 when '0' then '成功' when '1' then '失败' end as errorCode,a.bak4 as errorMsg from contblcdtl a"
		+ " where a.trancom = '05' and type='24'";
	if(tranType != "" && tranType != "undifined")
	{
		strSQL += " and a.bak3='" + tranType  + "'";
	}
	if(tranDate != "" && tranDate != "undifined")
	{
		strSQL += " and a.trandate='" + date10to8(tranDate)  + "'";
	}
	
	
	turnPage.queryModal(strSQL, QueryGrid, 0, 0);
}