//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var arrResult;
function FINPrintQuery() {
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
function FINPrintPreviewQuery() {
	var vAreaNo = trim(fm.AreaNo.value);
	var vCityNo = trim(fm.CityNo.value);
	
	var vTranCom = trim(fm.TranCom.value);
	var vContStatue = trim(fm.ContStatue.value);
    var vStartDay = fm.StartDay.value;
	var vEndDay = fm.EndDay.value;
	var vStartHour = fm.StartHour.value;
	var vEndHour = fm.EndHour.value;
	var vManageCodeNo=fm.ManageCodeNo.value;
	
	
	var myDate = new Date();
	var vTime = myDate.getTime();       
	fm.PrintFlag.value="preview";
	if (!beforeSubmit()) {
		return false; 
	} 
	
	var mSqlStr1 ="select count(*) ";
	      mSqlStr1+=" FROM LCCONT C, LCPOL P ";
          mSqlStr1+=   " where C.CONTNO || '-0' = P.polno ";
          mSqlStr1+=   " AND (c.axauwflag in ('03', '09', '11', 'X', 'Y')) ";
          if(vManageCodeNo!=""){
          mSqlStr1+= " AND C.managecom like '"+vManageCodeNo+"%' ";
          }
          if(vTranCom!=""){
          mSqlStr1+= " AND trim(c.bankcode)= '"+vTranCom+"' ";
          }
           if(vContStatue!=""){
          mSqlStr1+= " AND C.axauwflag = '"+vContStatue+"' ";
          }
           if((vStartDay!="")&&(vStartHour!="")){
          mSqlStr1+= " AND (TO_CHAR(C.MAKEDATE, 'YYYY-MM-DD') || ' ' || C.MAKETIME) >= '";
           mSqlStr1+=vStartDay+" "+vStartHour+":00:00'";
          }
           if((vEndDay!="")&&(vEndHour!="")){
          mSqlStr1+= " AND (TO_CHAR(C.MAKEDATE, 'YYYY-MM-DD') || ' ' || C.MAKETIME) <= '";
           mSqlStr1+=vEndDay+" "+vEndHour+":00:00'";
          }
    var mComInfos = easyExecSql(mSqlStr1); 
  if(mComInfos>2000){
	alert("���̫����ֱ�ӵ��'���ɱ���'");
		return false;
	}
	
		var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		
	var vTranCom = trim(fm.TranCom.value);
	var vContStatue = trim(fm.ContStatue.value);
    var vStartDay = fm.StartDay.value;
	var vEndDay = fm.EndDay.value;
	var vStartHour = fm.StartHour.value;
	var vEndHour = fm.EndHour.value;
	var vManageCodeNo=fm.ManageCodeNo.value;
	
	var winQuery = window.open("NoRealFINPreview.jsp?TranCom="+ vTranCom 
		+ "&AreaNo=" + vAreaNo
		+ "&CityNo=" + vCityNo 
		+ "&ContStatue=" + vContStatue
		+ "&StartDay=" + vStartDay
		+ "&EndDay=" + vEndDay
		+ "&StartHour=" + vStartHour
		+ "&EndHour=" + vEndHour
		+"&ManageCodeNo="+vManageCodeNo);
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
	var vStartHour = fm.StartHour.value;
	var vEndHour = fm.EndHour.value;

	if (vStartDay == null || vStartDay=="") {
		alert("��ʼ���ڲ���Ϊ��");
		return false;
	}
	if (vEndDay == null || vEndDay =="") {
		alert("�������ڲ���Ϊ��");
		return false;
	}
	if(vStartDay > vEndDay){
		alert("��ʼ���ڲ��ܴ��ڽ�������");
		return false;
	}
	if(vStartDay == vEndDay){
			if(vStartHour >= vEndHour){
				alert("ͬһ����,��ʼʱ�䲻�ܴ��ڻ��ߵ��ڽ���ʱ��");
				return false;
			}
		}
	return true;
}

function showContStatue(){
	fm.ContStatue.value="";
	fm.ContStatueName.value="";
	showCodeList('noreal_state',[fm.ContStatue,fm.ContStatueName],[0,1],null,' 1=1 And OTHERSIGN=#'+ 'report' +'#',null,1,null,1);
}

function showContStatueKey(){
	fm.ContStatue.value="";
	fm.ContStatueName.value="";
	showCodeListKey('noreal_state',[fm.ContStatue,fm.ContStatueName],[0,1],null,' 1=1 And OTHERSIGN=#'+ 'report' +'#',null,1,null,1);
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
    fm.action="./NoRealFINPrintSave.jsp"; 
}