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
function beforeSubmit()
{   
	if (!verifyInput2()) {
		return false;
	}
	return true;
}
function easyQueryClick()
{ 
	YBTGrid.clearData();
	var fileDate = fm.FileDate.value;
      // ��дSQL���   
        
    var strSQL = "select filename,uploaddate,contenttype,filesize,remark,path from lduploadfile"; 
    if(fileDate != ""){
    	strSQL += " where uploaddate = date'"+fileDate+"'";
    }
    
    turnPage.queryModal(strSQL,YBTGrid);
    for (i=0; i<YBTGrid.mulLineCount; i++) {
	var test = document.getElementById("spanYBTGrid" + i).children[0];
	test.rows[0].cells[7].innerHTML = "<font size=0.5><a href='./download.jsp?path=uploadFiles/ComCode/"+YBTGrid.getRowColData(i,1)+"' target=\"_blank\" >����</a></font>";
  }
  if(YBTGrid.mulLineCount==0){
  alert('δ��ѯ���ļ�');
  }
}
function makeDownLink(){
	for (i=0; i< YBTGrid.mulLineCount; i++) {
		var spanID = document.getElementsByName("YBTGridSpanID")[i].value;
		var test = document.getElementById(spanID).children[0];
		test.rows[0].cells[7].innerHTML = "<font size=0.5><a href='./download.jsp?path=uploadFiles/ComCode/" +YBTGrid.getRowColData(i,1) + "' target=\"_blank\" >����</a></font>";
	}
}

