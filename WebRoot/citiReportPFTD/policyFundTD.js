//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var arrResult;
function PrintQuery() {
	fm.PrintFlag.value="print";
	if (!beforeSubmit()) {
		return false;
	}
	fm.submit();
}   

function PreviewQuery() {
	var vStartDay = fm.StartDay.value;
	var vEndDay = fm.EndDay.value; 
	fm.PrintFlag.value="preview";
	if (!beforeSubmit()) {
		return false; 
	}
	var winQuery = window.open("PFTDPreview.jsp?StartDay="+ vStartDay 
		+ "&EndDay=" + vEndDay
		+"");
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

	if (vStartDay == null || vStartDay=="") {
		alert("��ѡ�񵼳��������ڷ�Χ");
		return false;
	}
	if (vEndDay == null || vEndDay=="") {
		alert("��ѡ�񵼳��������ڷ�Χ");
		return false;
	}	
	return true;
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
    fm.action="./policyFundTDSave.jsp"; 
}