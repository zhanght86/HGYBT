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
	YBTGrid.clearData();
	var fileDate = fm.FileDate.value;
	var cDate = fm.currentDate.value;
      // ��дSQL���   
        
    var strSQL = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '�ļ����ɳɹ�' WHEN i.Rcode='N' then '�������ޱ�����Ϣ' WHEN i.Rcode in ('UE','CE') then '�ļ�����ʧ��' END,"
    	         +"i.filepath,i.filename,i.makedate,i.maketime,'',i.area,i.rcode"
    	         +" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='fin'"
                 +" and i.makedate = date'"+cDate+"'"
                 +" order by i.logno desc";
    
    
    turnPage.queryModal(strSQL,YBTGrid);
    for (i=0; i<YBTGrid.mulLineCount; i++) {
	var test = document.getElementById("spanYBTGrid" + i).children[0];	
	test.rows[0].cells[8].innerHTML = "<font size=0.8><a href='./download.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,5)+"' target=\"_blank\" >����</a></font>";
      
	}
  if(YBTGrid.mulLineCount==0){
  alert('δ��ѯ���ļ�');
  }
}

function areaQueryClick(){
	YBTGrid.clearData();
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	var area = fm.Area.value;
	if (!checkData()) {
		return false;
	}
	 var strSQL2 = "select d.codename,i.Trandate,"
    	         +"CASE WHEN i.RCode='Y' THEN '�ļ����ɳɹ�' WHEN i.Rcode='N' then '�������ޱ�����Ϣ' WHEN i.Rcode in ('UE','CE') then '�ļ�����ʧ��' END,"
    	         +"i.filepath,i.filename,i.makedate,i.maketime,'',i.area,i.rcode";
    	        
    	 var strSQL =" from ldcode d,ifLog i"   	        
                 +" where d.codetype='rls_area' "
                 +" and d.code=i.area"
                 +" and i.Rcode='Y' "
                 +" and i.IFTYPE='fin'"
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
	turnPage.queryModal(mSqlStr0, YBTGrid,0,0);
	}
	else if(mComInfos==0){
	alert("��ѯ���Ϊ�գ�������ѡ��");
	}
	else {
	alert("��ѯ���̫��������ѡ��");
	}
    for (i=0; i<YBTGrid.mulLineCount; i++) {
	var test = document.getElementById("spanYBTGrid" + i).children[0];
	test.rows[0].cells[8].innerHTML = "<font size=0.8><a href='./download.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,5)+"' target=\"_blank\" >����</a></font>";
	
	}
}
function makeDownLink(){
	for (i=0; i< YBTGrid.mulLineCount; i++) {
		var spanID = document.getElementsByName("YBTGridSpanID")[i].value;
		var test = document.getElementById(spanID).children[0];
		test.rows[0].cells[8].innerHTML = "<font size=0.5><a href='./download.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,5)+"' target=\"_blank\" >����</a></font>";
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

