//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var arrResult;

function BCPrintQuery() {
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
    alert("��������Ϊ�գ�");
    }
	else if(vReturn != null ) {
	 var mSqlStr = "select D.NAME from LDCOM D where D.comcode = '" + vReturn + "'";
		 var mComInfos = easyExecSql(mSqlStr);
		fm.ManageCodeName.value=vReturn+mComInfos;
		fm.ManageCodeNo.value=vReturn;
	} 
}
function BCPrintPreviewQuery() {
	var vTranCom = trim(fm.TranCom.value);
	var vAreaNo = trim(fm.AreaNo.value);
	var vCityNo = trim(fm.CityNo.value);
	var vAgentCom = trim(fm.AgentComNo.value);
	var vAgentCode = trim(fm.AgentCodeNo.value);
	var vRiskCode = trim(fm.RiskCodeNo.value);
	var vStartDay = fm.StartDay.value;
	var vEndDay = fm.EndDay.value;
	var myDate = new Date();
	var vTime = myDate.getTime();     
	var vManageCodeNo= trim(fm.ManageCodeNo.value); 
	fm.PrintFlag.value="preview";
	if (!beforeSubmit()) {
		return false; 
	} 
	var mSqlStr1 ="select count(*) ";
	      mSqlStr1+=" FROM LCCONT C, LCPOL P, LDCOM D ";
          mSqlStr1+=   " WHERE TRIM(C.MANAGECOM) = D.COMCODE ";
          mSqlStr1+= " AND C.CONTNO || '-0' = P.POLNO ";
          mSqlStr1+= " AND C.APPFLAG IN ('1', 'B') ";
          if(vManageCodeNo!=""){
          mSqlStr1+= " AND C.managecom like '"+vManageCodeNo+"%' ";
          }
          if(vTranCom!=""){
          mSqlStr1+= " AND trim(c.bankcode)= '"+vTranCom+"' ";
          }
          if(vAgentCom!=""){
          mSqlStr1+= " AND C.agentcom ='"+vAgentCom+"' ";
          }
          if(vAgentCode!=""){
          mSqlStr1+= " AND C.agentcode ='"+vAgentCode+"' ";
          }
          if(vRiskCode!=""){
          mSqlStr1+= " AND P.riskcode ='"+vRiskCode+"' ";
          }
           if(vStartDay!=""){
          mSqlStr1+= " AND P.MAKEDATE >= DATE '"+vStartDay+"' ";
          }
           if(vEndDay!=""){
          mSqlStr1+= " AND P.MAKEDATE <= DATE '"+vEndDay+"' ";
          }
    var mComInfos = easyExecSql(mSqlStr1); 
  if(mComInfos>1000){
	alert("���̫����ֱ�ӵ��'���ɱ���'");
		return false;
	}
		var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 
	var winQuery = window.open("BCPreview.jsp?TranCom="+ vTranCom 
		+ "&AreaNo=" + vAreaNo
		+ "&CityNo=" + vCityNo 
		+ "&AgentCom=" + vAgentCom
		+ "&AgentCode=" + vAgentCode
		+ "&RiskCode=" + vRiskCode
		+ "&StartDay=" + vStartDay
		+ "&EndDay=" + vEndDay
		+"&ManageCodeNo="+vManageCodeNo+"");
		 showInfo.close();
}

//�ύǰ��У��
function beforeSubmit() {
	if (!verifyData()) {
		return false;
	}
	return true;
}


//�ύǰ��У��
function verifyData() {
	
	var vStartDay = fm.StartDay.value;
	var vEndDay = fm.EndDay.value;

	if (vStartDay == null || vStartDay=="") {
		alert("��ʼ���ڲ���Ϊ��");
		return false;
	}
	if (vEndDay == null || vEndDay =="") {
		alert("�������ڲ���Ϊ��");
		return false;
	}
	var patrn = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;
	if (!patrn.exec(vStartDay)){
		alert("��ʼ���ڲ��Ϸ�");
		return false
	}
	if (!patrn.exec(vEndDay)){
		alert("�������ڲ��Ϸ�");
		return false
	}
	return true;
}


/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
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
    fm.action="./BCPrintSave.jsp"; 
}
function BCshowAgentNo() {
	var vAgentCodeNo = trim(fm.AgentCodeNo.value);
	var vTranCom = trim(fm.TranCom.value);
	
	if("" == vTranCom){
		alert("����ѡ��������!");
		return false;
	}
	
	if("" == vAreaNo && "" == vCityNo && "" == vAgentCodeNo ){
		var mSqlStr = "select count(1) from NODEMAP A where A.TRANCOM = "+vTranCom+"";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("��������������!");
			fm.AgentComNo.value="";
			fm.AgentComName.value=""; 
			return false; 
		}
			fm.AgentComNo.value="";
		    fm.AgentComName.value=""; 
			showCodeList('AgentCom_NodeMap',[fm.AgentComNo,fm.AgentComName],[0,1],null,' 1=1 and TRANCOM = #'+ vTranCom +'#' ,null,1,null,1);
	}
	
	else if( "" != vAgentCodeNo){
		var mSqlStr = "select count(1) from NODEMAP A where A.AGENTCODE = '"+vAgentCodeNo + "' AND A.TRANCOM = "+vTranCom+"";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("��רԱ�¸�����Ŀǰ������!")
			fm.AgentComNo.value="";
			fm.AgentComName.value=""; 
			return false; 
		}
		fm.AgentComNo.value="";
		fm.AgentComName.value=""; 
		showCodeList('AgentCom_NodeMap',[fm.AgentComNo,fm.AgentComName],[0,1],null,' 1=1 and AGENTCODE = #'+ vAgentCodeNo +'# and TRANCOM = #'+ vTranCom +'#' ,null,1,null,1);
	}

}
/**
�����������ʱ�������¼�

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
			alert("������Ŀǰ������!");
			return false;
		}
			showCodeList('MainRiskCode_Print',[fm.RiskCodeNo,fm.RiskCodeName],[0,1],null,' 1=1 And a.TRANCOM=#'+ vTranCom +'#',null,1,null,1);
		}
         else {
         alert("��ѡ�����У�");
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
			alert("������Ŀǰ������!");
			return false;
		}
      showCodeListKey('MainRiskCode_Print',[fm.RiskCodeNo,fm.RiskCodeName],[0,1],null,' 1=1 And a.TRANCOM=#'+ vTranCom +'#',null,1,null,1)
		}
         else {
         alert("��ѡ�����У�");
         return false;
          }
}


