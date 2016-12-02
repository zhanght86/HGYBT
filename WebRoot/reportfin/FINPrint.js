//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var arrResult;
function FINPrintQuery() {
	fm.PrintFlag.value="print";
	if (!beforeSubmit()) {
		return false;
	}
	var showStr="正在生成报表，可能生成时间有点长，请耐心等待！";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
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
function FINPrintPreviewQuery() {
	var vTranCom = trim(fm.TranCom.value);
	var vAreaNo = trim(fm.AreaNo.value);
	var vCityNo = trim(fm.CityNo.value);
	var vContStatue = trim(fm.ContStatue.value);
		var vStartDay = fm.StartDay.value;
	var vEndDay = fm.EndDay.value;
	var vStartHour = fm.StartHour.value;
	var vEndHour = fm.EndHour.value;
	var myDate = new Date();
	var vTime = myDate.getTime();       
	fm.PrintFlag.value="preview";
	var vManageCodeNo= trim(fm.ManageCodeNo.value); 

	if (!beforeSubmit()) {
		return false; 
	}
		var mSqlStr1 ="select count(*) ";
	      mSqlStr1+=" FROM LCCONT C, LCPOL P ";
          mSqlStr1+=   " where C.CONTNO || '-0' = P.polno ";
          mSqlStr1+= "AND (C.NOREALFLAG = 'N'  " 
          mSqlStr1+= "AND C.APPFLAG in ('B', '1', '0')) ";
          if(vManageCodeNo!=""){
          mSqlStr1+= " AND C.managecom like '"+vManageCodeNo+"%' ";
          }
          if(vTranCom!=""){
          mSqlStr1+= " AND trim(c.bankcode)= '"+vTranCom+"' ";
          }
           if(vContStatue!=""){
          mSqlStr1+= " AND C.UWFLAG = '"+vContStatue+"' ";
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
  if(mComInfos>200){
	alert("结果太大，请直接点击'生成报表'");
		return false;
	}
		var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	var winQuery = window.open("FINPreview.jsp?TranCom="+ vTranCom 
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

//提交前的校验
function beforeSubmit() {
	if (!verifyData()) {
		return false;
	}
	
	return true;
}


//提交前的校验
function verifyData() {
	
	
	var vStartDay = fm.StartDay.value;
	var vEndDay = fm.EndDay.value;
	var vStartHour = fm.StartHour.value;
	var vEndHour = fm.EndHour.value;

	if (vStartDay == null || vStartDay=="") {
		alert("开始日期不能为空");
		return false;
	}
	if (vEndDay == null || vEndDay =="") {
		alert("结束日期不能为空");
		return false;
	}
	if(vStartDay > vEndDay){
		alert("开始日期不能大于结束日期");
		return false;
	}
	if(vStartDay == vEndDay){
			if(vStartHour >= vEndHour){
				alert("同一日期,起始时间不能大于或者等于结束时间");
				return false;
			}
		}
	return true;
}


function showContStatue(){
	fm.ContStatue.value="";
	fm.ContStatueName.value="";
	showCodeList('cont_statue',[fm.ContStatue,fm.ContStatueName],[0,1],null,' 1=1 And OTHERSIGN=#'+ 'report' +'#',null,1,null,1);
}

function showContStatueKey(){
	fm.ContStatue.value="";
	fm.ContStatueName.value="";
	showCodeListKey('cont_statue',[fm.ContStatue,fm.ContStatueName],[0,1],null,' 1=1 And OTHERSIGN=#'+ 'report' +'#',null,1,null,1);
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr,Content)
{ 
   if(showInfo!=""||showInfo!=null){
   showInfo.close();
   }
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
    fm.action="./FINPrintSave.jsp"; 
}