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
	var type = fm.FileTypeCode.value;
	if(type == null ||type==""){
		alert("��ѡ��ӿ��ļ�����");
		return false;
	}
	if (startDate == null || startDate=="") {
		alert("��ʼ���ڲ���Ϊ��");
		return false;
	}
	if (endDate == null || endDate=="") {
		alert("��ֹ���ڲ���Ϊ��");
		return false;
	}
	return true;
}

function creatClick(){
	
	//YBTGrid.clearData();	
		if (!checkData()) {
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

