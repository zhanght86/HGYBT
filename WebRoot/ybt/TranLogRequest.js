var turnPage = new turnPageClass();
var showInfo;
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
 function ShowBack(){
 if(divShow.style.display!=''){
 divShow.style.display='none';
 fm.ShowButton.value="显示请求报文";
 }else {
  divShow.style.display='';
 fm.ShowButton.value="查看返回报文";
 }
 }
function DownloadRequest()
{       
        var vfileinName = trim(fm.filein.value);
        var vfileoutName= "";
		var url = location.href;  
		if(vfileinName!=""){
		var winQuery = window.open("download.jsp?fileinName="+vfileinName+"&fileoutName="+vfileoutName+"");
		}
		else {
		alert("请求报文路径不合法！暂时不提供下载.");
		}
		//fm.action = 'download.jsp' ;
        //fm.submit();
} 
function DownloadResponse()
{       
        var vfileinName = "";
        var vfileoutName= trim(fm.fileout.value);
		var url = location.href;  
		if(vfileoutName!=""){
		var winQuery = window.open("download.jsp?fileinName="+vfileoutName+"&fileoutName="+vfileoutName+"");
		}else {
		alert("返回报文路径不合法！暂时不提供下载.");
		}
		//fm.action = 'download.jsp' ;
        //fm.submit();
} 




