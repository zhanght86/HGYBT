//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var arrResult;
//�ύ�����水ť��Ӧ����
function submitFormIn()
{
	var i = 0;
	var vImportFile = fm.all('FileName').value;
	var vManageCodeNo=trim(fm.ManageCodeNo.value);
	if(vImportFile.trim() == ""){
		alert("��ѡ���ϴ��ļ�!");
		return false; 
	}
	if(vManageCodeNo.length!=9){
		alert("��ѡ���м�����������ϴ��ļ�!");
		return false; 
	}
	
	var ImportPath = 'agent/temp';
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('ImportPath').value = ImportPath;
	
	var vManageCodeNo = trim(fm.ManageCodeNo.value);
	var vAreaNo = vManageCodeNo.substring(2,4);
	var vCityNo = vManageCodeNo.substring(6,9);
	var vSysFlag = trim(fm.SysFlag.value);
	fm.action="ITPrintInSave.jsp?AreaNo=" + vAreaNo + "&CityNo=" + vCityNo+ "&SysFlag=" + vSysFlag+"&ManageCodeNo="+vManageCodeNo; 
	fm.submit(); //�ύ
}
//��ѯ�¼�
function ITItemQuery() {
	
	initGrid();
    var vManageCodeNo = trim(fm.ManageForCheck.value);
    var vSysFlag = trim(fm.SysFlag.value);
	var vShitNoSta = trim(fm.ShitNoSta.value);
	var vShitState = trim(fm.ShitState.value);
	var mSqlStr2 = "SELECT  contno ������, case when status='0' then 'δʹ��' ";
	    mSqlStr2+=" when status ='1' then '��ʹ��' else '--' end  ����״̬,"; 
		mSqlStr2 +=" citycode  ���д���,sysflag ϵͳ���� ";
	var mSqlStr= " from lkcontno lk where 1=1";
	if(vSysFlag!=""){
          mSqlStr+= " AND lk.SYSFLAG LIKE '"+vSysFlag+"%' ";
          }
          if(vShitNoSta!=""){
          mSqlStr+= " AND lk.CONTNO LIKE '"+vShitNoSta+"%' ";
          }
          	if(vShitState!=""){
          mSqlStr+= " AND lk.STATUS = '"+vShitState+"' ";
          }
          if(vManageCodeNo!=""){
          mSqlStr+= " AND citycode in (select axashortname from ldcom ";
          mSqlStr+="  where comcode like '"+vManageCodeNo+"%') ";
          }
          mSqlStr += " ORDER BY LK.CONTNO ";
          var mSqlStr0=mSqlStr2+mSqlStr;
    var mSqlStr1 = "select count(*) "+mSqlStr;
    var mComInfos = easyExecSql(mSqlStr1); 
    if(mComInfos > 0 ){
	turnPage.queryModal(mSqlStr0, QueryGrid, 0, 0);
	}
	else if(mComInfos==0){
	alert("��ѯ���Ϊ�գ�������ѡ��");
	}
}



function ITPrintQuery() {
	fm.PrintFlag.value="print";
	if (!beforeSubmit()) {
		return false;
	}
	var vSysFlag = "";
	var vManageCodeNo = trim(fm.ManageCodeNoOut.value);
	var vAreaNo = "";
	var vCityNo = "";
	fm.action="ITPrintSave.jsp?AreaNo=" + vAreaNo + "&CityNo=" + vCityNo + "&SysFlag=" + vSysFlag+"&ManageCodeNo="+vManageCodeNo; 
	fm.submit();
}

function ITPrintPreviewQuery() {
	var vSysFlag = "";
	var myDate = new Date();
	var vTime = myDate.getTime();  
	var vManageCodeNo= trim(fm.ManageCodeNoOut.value);    
	var vAreaNo = "";
	var vCityNo = "";
	fm.PrintFlag.value="preview";
	if (!beforeSubmit()) {
		return false;
	} 
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	
	var winQuery = window.open("ITPreview.jsp?AreaNo=" + vAreaNo+ "&CityNo=" + vCityNo + "&SysFlag=" +vSysFlag+"&ManageCodeNo="+vManageCodeNo+"");
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
	
	return true;
}


//ѡ������ʱ�������¼�
function showAreaNo() {
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value="";
	showCodeList('Area_Print',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
} 
//ѡ������ʱ�������¼�
function showAreaNoKey() {
	fm.AreaName.value="";
	fm.CityNo.value=""; 
	fm.CityName.value=""; 
	showCodeListKey('Area_Print',[fm.AreaNo,fm.AreaName],[0,1],null,null,null,1,null,1);
}

//ѡ�����ʱ�������¼�
function showCityNo() {
	var vAreaNo = trim(fm.AreaNo.value);
	if("" != vAreaNo){
		var mSqlStr = "select count(1) from LDCOM D where D.AREAID = '"+vAreaNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){ 
			alert("������Ŀǰ�޻���!");
			fm.CityNo.value="";
			fm.CityName.value="";
			return false;
		}
			fm.CityName.value="";
		showCodeList('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,' 1=1 And AreaId=#'+ vAreaNo +'#',null,1,null,1);

	}
	else if("" == vAreaNo){
		fm.CityName.value="";
		showCodeList('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,null,null,1,null,1);

	}
}
//ѡ�����ʱ�������¼�
function showCityNoKey() {
var vAreaNo = trim(fm.AreaNo.value);
	if("" != vAreaNo){
		var mSqlStr = "select count(1) from LDCOM D where D.AREAID = '"+vAreaNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){ 
			alert("������Ŀǰ�޻���!");
			fm.CityNo.value="";
			fm.CityName.value="";
			return false;
		}
			fm.CityName.value="";
		showCodeListKey('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,' 1=1 And AreaId=#'+ vAreaNo +'#',null,1,null,1);

	}
	else if("" == vAreaNo){
		fm.CityName.value="";
		showCodeListKey('Area_CityNo',[fm.CityNo,fm.CityName],[0,1],null,null,null,1,null,1);

	}
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
    fm.action="./ITPrintSave.jsp"; 
}
/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmitforPrew(FlagStr,Content)
{
alert("1");
   showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }
}
function SelectCom(){
    fm.SelectFlag.value='0';
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
  function Select(){
    fm.SelectFlag.value='1';
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }
  function afterQuery(vReturn) {
   var sFlag=trim(fm.SelectFlag.value);
    if(vReturn==null){
    alert("��������Ϊ�գ�");
    }
    
	else if(vReturn != null&& sFlag=='0') {
	 var mSqlStr = "select D.NAME from LDCOM D where D.comcode = '" + vReturn + "'";
		 var mComInfos = easyExecSql(mSqlStr);
		fm.ManageCodeName.value=vReturn+mComInfos;
		fm.ManageCodeNo.value=vReturn;
	} 
	else if(vReturn != null&& sFlag=='1') {
	 var mSqlStr = "select D.NAME from LDCOM D where D.comcode = '" + vReturn + "'";
		 var mComInfos = easyExecSql(mSqlStr);
		fm.ManageForCheckName.value=vReturn+mComInfos;
		fm.ManageForCheck.value=vReturn;
	} 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmitIn( FlagStr, content ,Result )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	else
	{
		if (Result!=null&&Result!='')
		{
			var iArray;
			//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
			turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
			//�����ѯ����ַ���
			turnPage.strQueryResult  = Result;
			//ʹ��ģ������Դ������д�ڲ��֮ǰ
			turnPage.useSimulation = 1;
			//��ѯ�ɹ������ַ��������ض�ά����
			var tArr = decodeEasyQueryResult(turnPage.strQueryResult,0);
			turnPage.arrDataCacheSet =chooseArray(tArr,[3,0,1,10,8]);
			//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
			turnPage.pageDisplayGrid = LCGrpImportLogGrid;
			//���ò�ѯ��ʼλ��
			turnPage.pageIndex = 0;
			//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
			var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
			//����MULTILINE������ʾ��ѯ���
			displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
			divImport.style.display='';
		}
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		
		if ("Succ" == FlagStr) {
		//queryItems();	//�ύ���Զ���ѯ
	}
	}
}
function resetform(){
fm.reset();
initForm() ;
}