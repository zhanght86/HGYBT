//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var tArr;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  //initPolGrid();
  //showSubmitFrame(mDebug);
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  }
  else
  {
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��OLDComQuery.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  //showDiv(operateButton,"false");
  //showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function returnParent()
{
  var arrReturn = new Array();
	var tSel = ComGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		top.close();
	  //alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	}
	else
	{
  		try
			{				
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
			top.close();
	}
}

function getQueryResult() 
{
	var arrSelected = null;
	var tRow = ComGrid.getSelNo();
	if( tRow == 0 || tRow == null)
		return arrSelected;
	arrSelected = new Array();
	var ComCode =  ComGrid.getRowColData(tRow-1,1);

	
	var mySql = new SqlClass();
  mySql.setJspName("../../sys/ComQuerySQL.jsp"); //ͨ���µ�SQL������ѯ 	
  mySql.setSqlId("easyQuery2");
  
  mySql.addPara("ComCode",ComCode);
 	
	turnPage.strQueryResult =  easyQueryVer3(mySql.getString(), 1, 0, 1); 
	
	
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) 
	{
		alert("��ѯʧ�ܣ�");
		return false;
	}
	//��ѯ�ɹ������ַ��������ض�ά����
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	return arrSelected;
}




// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initComGrid();
	var mySql = new SqlClass();
  mySql.setJspName("../../sys/ComQuerySQL.jsp"); //ͨ���µ�SQL������ѯ 	
  mySql.setSqlId("easyQuery1");
  
  mySql.getWherePart("ComCode");     
  mySql.getWherePart("OutComCode");  
  mySql.getWherePart("Name");        
  mySql.getWherePart("ShortName");   
  mySql.getWherePart("Address");     
  mySql.getWherePart("ZipCode");     
  mySql.getWherePart("Phone");       
  mySql.getWherePart("Fax");         
  mySql.getWherePart("EMail");       
  mySql.getWherePart("WebAddress");        
  mySql.getWherePart("UpComCode");   
	turnPage.strQueryResult = turnPage.queryModal(mySql.getString(), ComGrid);
	 
}