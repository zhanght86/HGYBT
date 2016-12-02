var showInfo="";

Request = {  
    QueryString : function(item){  
       var svalue = location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)","i"));  
        return svalue ? svalue[1] : svalue;  
   }  
}  
function DownloadThis()
{
		var url = location.href;  
		var vAreaNo = Request.QueryString("AreaNo");
		var vCityNo = Request.QueryString("CityNo");
		var vSysFlag = Request.QueryString("SysFlag");
		var vManageCodeNo= Request.QueryString("ManageCodeNo");
		fm.action = './ITPrintSave.jsp?AreaNo=' + vAreaNo + '&CityNo=' + vCityNo + '&SysFlag=' + vSysFlag+'&ManageCodeNo='+vManageCodeNo;
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
        fm.action="../common/ExcelFileDownload.jsp?filePath="+Content;    
        fm.submit();
    }

}
function  loadwindow(){
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
}

