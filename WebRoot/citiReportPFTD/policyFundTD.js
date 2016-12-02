//               该文件中包含客户端需要处理的函数和事件
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

	if (vStartDay == null || vStartDay=="") {
		alert("请选择导出数据日期范围");
		return false;
	}
	if (vEndDay == null || vEndDay=="") {
		alert("请选择导出数据日期范围");
		return false;
	}	
	return true;
}


/**
 * 提交后操作, 服务器数据返回后执行的操作
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