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
      alert( "�ر�ҳ��ʱ�����쳣:" + ex );
    }
   
  } 
 function ShowBack(){
 if(divShow.style.display!=''){
 divShow.style.display='none';
 fm.ShowButton.value="��ʾ������";
 }else {
  divShow.style.display='';
 fm.ShowButton.value="�鿴���ر���";
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
		alert("������·�����Ϸ�����ʱ���ṩ����.");
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
		alert("���ر���·�����Ϸ�����ʱ���ṩ����.");
		}
		//fm.action = 'download.jsp' ;
        //fm.submit();
} 




