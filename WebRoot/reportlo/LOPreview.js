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
		var vAgentCom = Request.QueryString("AgentCom");
		var vAgentCode = Request.QueryString("AgentCode");
		var vRiskCode = Request.QueryString("RiskCode");
		var vDay = Request.QueryString("Day");
		var vManageCodeNo= Request.QueryString("ManageCodeNo");
		fm.action = "./LOPrintSave.jsp?TranCom="+ vTranCom 
		+ "&AreaNo=" + vAreaNo
		+ "&CityNo=" + vCityNo 
		+ "&AgentCom=" + vAgentCom
		+ "&AgentCode=" + vAgentCode
		+ "&vRiskCode=" + vRiskCode
		+ "&Day=" + vDay
		+ "&ManageCodeNo="+vManageCodeNo; 
		 
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