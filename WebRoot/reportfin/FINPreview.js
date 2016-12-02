Request = {  
    QueryString : function(item){  
       var svalue = location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)","i"));  
        return svalue ? svalue[1] : svalue;  
   }  
}  

function DownloadThis()
{
		var url = location.href;  
	
		var vTranCom = Request.QueryString("TranCom");
		var vAreaNo = Request.QueryString("AreaNo");
		var vCityNo = Request.QueryString("CityNo");
		var sContStatue = Request.QueryString("ContStatue");
		var vStartDay = Request.QueryString("StartDay");
		var vEndDay = Request.QueryString("EndDay");
		var vStartHour = Request.QueryString("StartHour"); 
		var vEndHour = Request.QueryString("EndHour");
		var vManageCodeNo= Request.QueryString("ManageCodeNo"); 
				
	
		fm.action = "./FINPrintSave.jsp?TranCom="+ vTranCom 
		+ "&AreaNo=" + vAreaNo
		+ "&CityNo=" + vCityNo 
		+ "&ContStatue=" + sContStatue
		+ "&StartHour=" + vStartHour
		+ "&EndHour=" + vEndHour
		+ "&StartDay=" + vStartDay 
		+ "&EndDay=" + vEndDay 
		+"&ManageCodeNo="+vManageCodeNo+ ""; 
		 
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