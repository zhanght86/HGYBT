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

function checkData(){
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	
	if (startDate == null || startDate=="") {
		alert("�����ļ���ʼ���ڲ���Ϊ��");
		return false;
	}
	if (endDate == null || endDate=="") {
		alert("�����ļ���ֹ���ڲ���Ϊ��");
		return false;
	}
	if(startDate>endDate){
		alert("��ʼ���ڲ���������ֹ����");
		return false;
	}
	return true;
}

function typeQueryClick(){
	YBTGrid.clearData();
	var startDate = fm.StartDay.value;
	var endDate = fm.EndDay.value;
	var type = fm.FileTypeCode.value;
	if (!checkData()) {
		return false;
	}
	 var strSQL = "select d.codename,c.Operator,"
    	         +"c.filename,c.filepath,c.RText,c.makedate,c.maketime,''"
    	         +" from ldcode d,CitiIFLog c"   	        
                 +" where d.codetype='c_iffiletype' "
                 +" and d.code=c.IFtype"
                 +" and c.RCode='Y' "
                 +" and c.DelFlag is null "
                 +" and c.makedate >='"+startDate +"'"
                 +" and c.makedate <='"+endDate+"'";
	 
	 if (type != null && ""!=type) {
		 strSQL+= " and c.IFtype='"+type+"' ";
	 }
    strSQL +=" order by c.maketime desc";
    turnPage.queryModal(strSQL,YBTGrid);
    for (i=0; i<YBTGrid.mulLineCount; i++) {
	var test = document.getElementById("spanYBTGrid" + i).children[0];
	test.rows[0].cells[8].innerHTML = "<font size=0.8><a href='./downloadinfo.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,3)+"' target=\"_blank\" >����</a></font>";
	
	}
    if(YBTGrid.mulLineCount==0){
    	  alert('ָ�����ڷ�Χ��û����ؽӿ��ļ�');
    	  }
}
function makeDownLink(){
	for (i=0; i< YBTGrid.mulLineCount; i++) {
		var spanID = document.getElementsByName("YBTGridSpanID")[i].value;
		var test = document.getElementById(spanID).children[0];
		test.rows[0].cells[8].innerHTML = "<font size=0.5><a href='./downloadinfo.jsp?path="+YBTGrid.getRowColData(i,4)+YBTGrid.getRowColData(i,3)+"' target=\"_blank\" >����</a></font>";
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