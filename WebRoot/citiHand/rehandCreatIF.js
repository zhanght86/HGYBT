var showInfo;

function checkData(){
	var type = fm.FileTypeCode.value;
	var name = fm.FileTypeName.value;
	var fileName = fm.FileName.value;
	var startDay1 = fm.StartDay1.value;
    var endDay1 = fm.EndDay1.value;
    var startDay2 = fm.StartDay2.value;
    var endDay2 = fm.EndDay2.value;
	if(type == null ||type==""){
		alert("��ѡ��ӿ��ļ�����");
		return false;
	}
	if(name == null ||name==""){
		alert("��ѡ��ӿ��ļ�����");
		return false;
	}
	if(fileName == null ||fileName==""){
		alert("��ѡ�񱣵����ϴ��ļ�");
		return false;
	}
	if (startDay1 == null || startDay1=="") {
		alert("��ѡ��������������������");
		return false;
	}
	if (endDay1 == null || endDay1=="") {
		alert("��ѡ��������������������");
		return false;
	}
	if(startDay2 == null ||startDay2==""){
		alert("��ѡ�񵼳����ݵ���������");
		return false;
	}
	if(endDay2 == null ||endDay2==""){
		alert("��ѡ�񵼳����ݵ���������");
		return false;
	}
	return true;
}

function creatClick(){
	
	if (!checkData()) {
		return false;
	}
	var mShowMsg = "���ڴ��������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var mUrlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + mShowMsg ;
	showInfo = window.showModelessDialog(mUrlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var type = fm.FileTypeCode.value;
	var startDay1 = fm.StartDay1.value;
	var startDay2 = fm.StartDay2.value;
    var endDay1 = fm.EndDay1.value;
    var endDay2 = fm.EndDay2.value;
   	fm.action="rehandCreatIFSave.jsp?FileTypeCode="+type+"&StartDay1="+startDay1+"&EndDay1="+endDay1+"&StartDay2="+startDay2+"&EndDay2="+endDay2;
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