//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var arrResult;


/**
 * 点击【预览】按钮触发的事件
*/
function LOPrintPreviewQuery() {
	var vTranCom = trim(fm.TranCom.value);
	var vAreaNo = trim(fm.AreaNo.value);
	var vCityNo = trim(fm.CityNo.value);
	var vAgentCom = trim(fm.AgentComNo.value);
	var vAgentCode = trim(fm.AgentCodeNo.value);
	var vRiskCode = trim(fm.RiskCodeNo.value);
	var vDay = fm.Day.value;
	var myDate = new Date();
	var vTime = myDate.getTime();     
	var vManageCodeNo= trim(fm.ManageCodeNo.value); 
 
	fm.PrintFlag.value="preview";
	if (!beforeSubmit()) {
		return false; 
	} 
		var vTranCom = trim(fm.TranCom.value);
	var vAgentCom = trim(fm.AgentComNo.value);
	var vAgentCode = trim(fm.AgentCodeNo.value);
	var vRiskCode = trim(fm.RiskCodeNo.value);
	var vDay = fm.Day.value;
	var vManageCodeNo= trim(fm.ManageCodeNo.value); 
	 var mSqlStr1 = "select count(*) ";
     mSqlStr1 += "from lccont c,lcpol p,lcinsured i,lcaddress d,lcaddress d2,lcappnt ap";
     mSqlStr1 +=" where c.contno = p.contno"
     mSqlStr1 +=" and p.riskcode = p.kindcode";
   mSqlStr1 +=" and c.contno = i.contno";
   mSqlStr1 +=" and i.insuredno = d.customerno";
   mSqlStr1 +=" and i.addressno = d.addressno";
   mSqlStr1 +=" and c.contno = ap.contno";
   mSqlStr1 +=" and ap.appntno = d2.customerno";
   mSqlStr1 +=" and ap.addressno = d2.addressno";
   mSqlStr1 +=" and c.norealflag = 'Y'";
   mSqlStr1 +=" and ((c.appflag = 'B' AND C.UWFLAG = '6') OR";
       mSqlStr1 +=" (C.APPFLAG = '0' AND C.STATE = 'D') OR";
       mSqlStr1 +=" (C.APPFLAG = '1'  AND C.UWFLAG = '6') )";
        if(vManageCodeNo!=""){
          mSqlStr1+= " AND C.managecom like '"+vManageCodeNo+"%' ";
          }
          if(vTranCom!=""){
          mSqlStr1+= " AND trim(c.bankcode)= '"+vTranCom+"' ";
          }
           if(vAgentCom!=""){
          mSqlStr1+= " AND C.agentcom = '"+vAgentCom+"' ";
          }
           if(vAgentCode!=""){
          mSqlStr1+= " AND C.AgentCode = '"+vAgentCode+"' ";
          }
           if(vRiskCode!=""){
          mSqlStr1+= " AND P.riskcode = '"+vRiskCode+"' ";
          }
           if(vDay!=""){
          mSqlStr1+= " AND c.UWDATE = DATE'"+vDay+"' OR ";
          mSqlStr1+="c.axauwflag is null or ";
          mSqlStr1+="c.axauwflag not in ('03', '09', '11', 'X', 'Y'))";
          }
    var mComInfos = easyExecSql(mSqlStr1); 
    if(mComInfos>2000){
	alert("结果太大，请直接点击'生成报表'");
		return false;
	}
		var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	var winQuery = window.open("NoRealLOPreview.jsp?TranCom="+ vTranCom 
		+ "&AreaNo=" + vAreaNo
		+ "&CityNo=" + vCityNo 
		+ "&AgentCom=" + vAgentCom
		+ "&AgentCode=" + vAgentCode
		+ "&RiskCode=" + vRiskCode
		+ "&Day=" + vDay
		+"&ManageCodeNo="+vManageCodeNo);
		 showInfo.close();
}

/**
 * 点击【生成报表】按钮触发的事件
*/
function LOPrintQuery() {
	fm.PrintFlag.value="print";
	if (!beforeSubmit()) {
		return false;
	}
	fm.submit();
} 
function SelectCom(){
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
  
  function afterQuery(vReturn) {
    if(vReturn==null){
    alert("机构不能为空！");
    }
	else if(vReturn != null ) {
	 var mSqlStr = "select D.NAME from LDCOM D where D.comcode = '" + vReturn + "'";
		 var mComInfos = easyExecSql(mSqlStr);
		fm.ManageCodeName.value=vReturn+mComInfos;
		fm.ManageCodeNo.value=vReturn;
	} 
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
	var vDay = fm.Day.value;
	if (vDay == null || vDay=="") {
		alert("销售日期不能为空");
		return false;
	}
	var patrn = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;
	if (!patrn.exec(vDay)){
		alert("销售日期不合法");
		return false
	}
	
	return true;
}
/**
点击银行渠道时触发的事件
*/
function LOBankQuery() {
fm.TranCom.value="";
fm.TranComName.value="";
fm.RiskCodeNo.value="";
fm.RiskCodeName.value="";
return showTranCom();
}
function LOBankQueryKey() {
fm.TranCom.value="";
fm.TranComName.value="";
fm.RiskCodeNo.value="";
fm.RiskCodeName.value="";
return showTranComKey();
}
function ShowRiskCodeList()
{
    fm.RiskCodeNo.value="";
    fm.RiskCodeName.value="";
    var vTranCom=trim(fm.TranCom.value);
	if("" != vTranCom){
		var mSqlStr ="select count(*) from lmriskapp M,bankandinsumap p where 1=1 And p.TRANCOM="+vTranCom+" and M.subriskflag = 'M' and P.INSU_CODE=M.RISKCODE ";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){ 
			alert("该银行目前无险种!");
			return false;
		}
			showCodeList('MainRiskCode_Print',[fm.RiskCodeNo,fm.RiskCodeName],[0,1],null,' 1=1 And a.TRANCOM=#'+ vTranCom +'#',null,1,null,1);
		}
         else {
         alert("请选择银行！");
         return false;
          }
}
function ShowRiskCodeKey(){
 fm.RiskCodeNo.value="";
 fm.RiskCodeName.value="";
 var vTranCom=trim(fm.TranCom.value);
	if("" != vTranCom){
		var mSqlStr ="select count(*) from lmriskapp M,bankandinsumap p where 1=1 And p.TRANCOM="+vTranCom+" and M.subriskflag = 'M' and P.INSU_CODE=M.RISKCODE ";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){ 
			alert("该银行目前无险种!");
			return false;
		}
      showCodeListKey('MainRiskCode_Print',[fm.RiskCodeNo,fm.RiskCodeName],[0,1],null,' 1=1 And a.TRANCOM=#'+ vTranCom +'#',null,1,null,1)
		}
         else {
         alert("请选择银行！");
         return false;
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
    fm.action="./NoRealLOPrintSave.jsp"; 
}