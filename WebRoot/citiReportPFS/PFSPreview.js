Request = {  
    QueryString : function(item){  
       var svalue = location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)","i"));  
        return svalue ? svalue[1] : svalue;  
   }  
}  

function DownloadThis()
{
		var url = location.href;  
	
		var startDay = Request.QueryString("StartDay");
		var endDay = Request.QueryString("EndDay");
		fm.action = "./policyFundSSave.jsp?StartDay="+ startDay 
		+ "&EndDay=" + endDay+ ""; 
		 
        fm.submit();
} 

function returnParent()
{

    try
    { 
    	top.window.close();
    }
    catch(ex)
    {
      alert( "关闭页面时发生异常:" + ex );
    }
   
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
        fm.action="../citiReportComm/ExcelFileDownload.jsp?filePath="+Content;    
        fm.submit();
    }

}