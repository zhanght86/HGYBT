var turnPage = new turnPageClass();
var showInfo;


function initBox() {
	try {
		fm.all("flag").value = "cancel";
		fm.all("ImportPath").value = "";
		fm.all("FileName").value = "";
		//fm.all("InfoTypeName").value = "";
	} catch (re) {
		alert("��FundPriceInit.jsp --> initBox �����з����쳣:��ʼ���������");
	}
}
//�ύ�����水ť��Ӧ����
function submitFormIn()
{
	var i = 0;
	var vImportFile = fm.all('FileName').value;
	//var infoType = fm.all('InfoType').value;
	//var infoTypeName = fm.all('InfoTypeName').value;
	//if(infoType.trim() == ""){
	//	alert("��ѡ��ȱʧ��Ϣ����");
	//	return false; 
	//}
	//if(infoTypeName.trim() == ""){
	//	alert("��ѡ��ȱʧ��Ϣ����");
	//	return false; 
	//}
	if(vImportFile.trim() == ""){
		alert("��ѡ���ϴ��ļ�");
		return false; 
	}
	
	var ImportPath = 'citiUpload/temp';
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('ImportPath').value = ImportPath;
	
	fm.action="FundPriceUpSave.jsp"; 
	fm.submit(); //�ύ
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
	return true;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmitIn( FlagStr, content ,Result )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	else
	{ 
		if (Result!=null&&Result!='')
		{
			var iArray;
			//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
			turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
			//�����ѯ����ַ���
			turnPage.strQueryResult  = Result;
			//ʹ��ģ������Դ������д�ڲ��֮ǰ
			turnPage.useSimulation = 1;
			//��ѯ�ɹ������ַ��������ض�ά����
			var tArr = decodeEasyQueryResult(turnPage.strQueryResult,0);
			turnPage.arrDataCacheSet =chooseArray(tArr,[3,0,1,10,8]);
			//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
			turnPage.pageDisplayGrid = LCGrpImportLogGrid;
			//���ò�ѯ��ʼλ��
			turnPage.pageIndex = 0;
			//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
			var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
			//����MULTILINE������ʾ��ѯ���
			displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
			divImport.style.display='';
		}
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		
	}

	if ("Succ" == FlagStr) {
		easyQuery();
		initBox();
		fm.all('flag').value="submit";
	}
	if ("SuccUP" == FlagStr) {
		easyQuery();
		initBox();
		fm.all('flag').value="cancel";
	}
}
function makeUpdate(){
	if(fm.all('flag').value=="cancel"){
		alert("�����ϴ�Ͷ���˻��۸���Ϣ");
		return false;
	}
	if(window.confirm("ȷ��������")){
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action="FundPriceUpInSave.jsp?flag=submit"; 
		fm.submit(); 
		initBox();
	}//else{
	//	cancelUpdate();
	//}
}
function cancelUpdate(){
	//alert(fm.all('flag').value);
	if(fm.all('flag').value=="cancel"){
		alert("�����ϴ�Ͷ���˻��۸���Ϣ");
		return false;
	}
	if(window.confirm("ȡ��������")){
		var showStr="����ȡ�����ݸ��£������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action="FundPriceUpInSave.jsp?flag=cancel"; 
		fm.submit(); 
		initBox();
	}//else{
	//	makeUpdate();
	//}
}

function easyQuery(){
	YBTGrid.clearData();
	if (!verifyData()) {
		return false;
	}
	 var strSQL = "select fp.PRICEEFFECTIVEDATE,fp.fundcode,"
    	         +"trunc(fp.BIDPRICE,4),trunc(fp.OFFERPRICE,4)"
    	         +" from FundPriceUpTemp fp"	        
	 			 +" where fp.PRICEEFFECTIVEDATE = (select max(priceeffectivedate)from FundPriceUpTemp)";
    strSQL +=" order by fp.PRICEEFFECTIVEDATE desc";
    turnPage.queryModal(strSQL,YBTGrid);
   // if(YBTGrid.mulLineCount==0){
   //	  alert('δ��ѯ���ļ�');
   // 	  }
}