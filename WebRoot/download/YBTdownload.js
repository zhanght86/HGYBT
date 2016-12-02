//               该文件中包含客户端需要处理的函数和事件

var mDebug="1";
var mOperate="";
var showInfo;
var updateAgentCom;
var strRT=""; //存储代理人代码记录
var old_AgentGroup="";
var new_AgentGroup="";
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//提交前的校验、计算
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
      // 书写SQL语句   
        
    var strSQL = "select filename,uploaddate,contenttype,filesize,remark,path from lduploadfile"; 
    if(fileDate != ""){
    	strSQL += " where uploaddate = date'"+fileDate+"'";
    }
    
    turnPage.queryModal(strSQL,YBTGrid);
    for (i=0; i<YBTGrid.mulLineCount; i++) {
	var test = document.getElementById("spanYBTGrid" + i).children[0];
	test.rows[0].cells[7].innerHTML = "<font size=0.5><a href='./download.jsp?path=uploadFiles/ComCode/"+YBTGrid.getRowColData(i,1)+"' target=\"_blank\" >下载</a></font>";
  }
  if(YBTGrid.mulLineCount==0){
  alert('未查询到文件');
  }
}
function makeDownLink(){
	for (i=0; i< YBTGrid.mulLineCount; i++) {
		var spanID = document.getElementsByName("YBTGridSpanID")[i].value;
		var test = document.getElementById(spanID).children[0];
		test.rows[0].cells[7].innerHTML = "<font size=0.5><a href='./download.jsp?path=uploadFiles/ComCode/" +YBTGrid.getRowColData(i,1) + "' target=\"_blank\" >下载</a></font>";
	}
}

