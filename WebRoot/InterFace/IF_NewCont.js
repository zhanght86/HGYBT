//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


var mDebug="1";
var mOperate="";
var showInfo;
var updateAgentCom;
var strRT=""; //�洢�����˴����¼
var old_AgentGroup="";
var new_AgentGroup="";
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//�ύǰ��У�顢����

function verifyData(){
	var fileDate = fm.FileDate.value;
	
	if (fileDate == null || fileDate=="") {
		alert("�������ڲ���Ϊ��");
		return false;
	}
	
	return true;
}

function checkData(){
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	
	if (startDate == null || startDate=="") {
		alert("��ʼ�������ڲ���Ϊ��");
		return false;
	}
	if (endDate == null || endDate=="") {
		alert("��ֹ�������ڲ���Ϊ��");
		return false;
	}
	return true;
}

function easyQueryClick()
{ 
	QueryGrid.clearData();
	var fileDate = fm.FileDate.value;
	var cDate = fm.currentDate.value;
      // ��дSQL���   
        
    var strSQL = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '�ļ����ɳɹ�' WHEN i.Rcode='N' then '�������ޱ�����Ϣ' WHEN i.Rcode in ('UE','CE') then '�ļ�����ʧ��' END,"
    	         +" CASE when i.BAK2 is null or i.bak2=0 then '' else '��'||i.bak2||'��������Ϣ' end, "
    	         +"i.filename,i.makedate,i.maketime,i.area,i.rcode,i.LogNo,i.filepath"
    	         +" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='newcont'"
                 +" and i.makedate = date'"+cDate+"'"
                 +" order by i.logno desc";
    
    
    turnPage.queryModal(strSQL, QueryGrid, 0, 0);
    //for (i=0; i<QueryGrid.mulLineCount; i++) {
	//var test = document.getElementById("spanQueryGrid" + i).children[0];	
	//test.rows[0].cells[9].innerHTML = "<font size=0.8><a href='./download.jsp?path="+QueryGrid.getRowColData(i,4)+QueryGrid.getRowColData(i,5)+"' target=\"_blank\" >����</a></font>";
      
	//}
  if(QueryGrid.mulLineCount==0){
  alert('δ��ѯ���ļ�');
  }
}

function areaQueryClick(){
	QueryGrid.clearData();
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	var area = fm.Area.value;
	if (!checkData()) {
		return false;
	}
  
	 var strSQL2 = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '�ļ����ɳɹ�' WHEN i.Rcode='N' then '�������ޱ�����Ϣ' WHEN i.Rcode in ('UE','CE') then '�ļ�����ʧ��' END,"
    	         +" CASE when i.BAK2 is null or i.bak2=0 then '' else '��'||i.bak2||'��������Ϣ' end, "
    	         +"i.filename,i.makedate,i.maketime,i.area,i.rcode,i.LogNo,i.filepath";
    	         
    	   var strSQL =" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='newcont'"
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
	turnPage.queryModal(mSqlStr0, QueryGrid, 0, 0);
	}
	else if(mComInfos==0){
	alert("��ѯ���Ϊ�գ�������ѡ��");
	}
	else {
	alert("��ѯ���̫��������ѡ��");
	}
   // for (i=0; i<QueryGrid.mulLineCount; i++) {
	//var test = document.getElementById("spanQueryGrid" + i).children[0];
	//test.rows[0].cells[9].innerHTML = "<font size=0.8><a href='./download.jsp?path="+QueryGrid.getRowColData(i,4)+QueryGrid.getRowColData(i,5)+"' target=\"_blank\" >����</a></font>";
	
	//}
}
function makeDownLink(){
	for (i=0; i< QueryGrid.mulLineCount; i++) {
		var spanID = document.getElementsByName("QueryGridSpanID")[i].value;
		var test = document.getElementById(spanID).children[0];
		test.rows[0].cells[9].innerHTML = "<font size=0.5><a href='./download.jsp?path="+QueryGrid.getRowColData(i,4)+QueryGrid.getRowColData(i,5)+"' target=\"_blank\" >����</a></font>";
	}
}

function creatClick(){
	
	//YBTGrid.clearData();
	var fileDate = fm.FileDate.value;
	
		if (!verifyData()) {
		return false;
	}
	var mShowMsg = "���ڴ��������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.action="IF_NewContSave.jsp?FileDate="+fileDate;    
	fm.submit();
}



/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
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

function afterDown(downFlagStr,downContent)
{
	//showInfo.close();
	
    if (downFlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + downContent;
        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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

function queryDetail() {
	
	if(turnPage.queryAllRecordCount <= 0) {
		alert("���Ȳ�ѯ��");
		return false;
	}
   
   var mCurRowNo= checkedRowNo("QueryGridSel"); 

	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
	 
	var mRowArr = QueryGrid.getRowData(mCurRowNo);

	if (null == mRowArr) {
		alert("��������Ϊ�գ�");
		return false;
	}
	             
    var vLogNo = mRowArr[9]; 
	
	var mSqlStr = "select count(1) from IFdetail B where B.IFLOGNO = '"+vLogNo + "'";
		var mComInfos = easyExecSql(mSqlStr); 
		if(mComInfos == '0'){
			alert("��ѯ�����쳣!");
			return false;
		}

	if(vLogNo == false){
		return false;
	}
	if (vLogNo != false && vLogNo != ""){
	    nQuery = window.open("NewContDetial.jsp?LogNo=" + vLogNo +"");
	}
}


//����ѡ�е��к�,���radio button
function checkedRowNo(name) {
	
	var obj = document.all[name];

	if ("undefined" == typeof(obj)) {
		return -1;
	} else if ("undefined" == typeof(obj.length)) {
		return 0;
	}
	
	for (i = 0; i < obj.length; i++) {
		if(obj[i].checked) {
			return i; 
		}
	} 
	
	return -1;
}

function downloadThis(){
	if(turnPage.queryAllRecordCount <= 0) {
		alert("���Ȳ�ѯ��");
		return false;
	}
   
   var mCurRowNo = checkedRowNo("QueryGridSel");
	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
	
	var mRowArr = QueryGrid.getRowData(mCurRowNo);
	if (null == mRowArr) {
		alert("��������Ϊ�գ�");
		return false;
	}
	//var mCurRowNo= checkedRowNo("QueryGridSel");
	//var mRowArr = QueryGrid.getRowData(mCurRowNo);
	var Content = mRowArr[10]+mRowArr[4]; 
	
	//alert(Content);
	//test(Content);
	
	//alert(Content);
	fm.action="../common/FileDownload.jsp?filePath="+Content;    
    fm.submit();
}

