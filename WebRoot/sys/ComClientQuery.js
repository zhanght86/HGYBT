//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

//  initPolGrid();
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
  	alert("��ComClientQuery.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
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
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
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
	var tSel = ComClientGrid.getSelNo();
	
	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				//alert(tSel);
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
	tRow = ComClientGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	return arrSelected;
	arrSelected = new Array();
	var strSQL = "";
	//alert(ComClientGrid.getRowColData(tRow-1,1));
	if (fm.all('BlacklistType').value == "0") 
	{
		strSQL = "select CustomerNo,Name,'���˿ͻ�' from LDPerson where trim(CustomerNo) = '"+ComClientGrid.getRowColData(tRow-1,1)+"'";
		//alert("222");
	}
	else
	{
		strSQL = "select CustomerNo,GrpName,'����ͻ�'	from LDGrp where trim(CustomerNo) = '"+ComClientGrid.getRowColData(tRow-1,1)+"'";
		//alert("333");
	}
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
        //�ж��Ƿ��ѯ�ɹ�
       if (!turnPage.strQueryResult) {
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
	initComClientGrid();
	
	// ��дSQL���
	var strSQL = "";
	//alert(fm.all('BlacklistType').value);
	if (fm.all('BlacklistType').value == "")
	{
        alert("��ѡ��ͻ����ͣ�");
        return false;
		strSQL = "select CustomerNo,Name,'���˿ͻ�' from LDPerson where BlacklistFlag='0' OR BlacklistFlag is null "+" "+ getWherePart( 'BlacklistNo' )+" union select CustomerNo,GrpName,'����ͻ�'	from LDGrp where BlacklistFlag='0' OR BlacklistFlag is null "+" "+ getWherePart( 'BlacklistNo' );
		//alert("111");
	}
	else if (fm.all('BlacklistType').value == "0") 
	{
		strSQL = "select CustomerNo,Name,'���˿ͻ�' from LDPerson where BlacklistFlag='0' OR BlacklistFlag is null "+" "+ getWherePart( 'BlacklistNo' );
		//alert("222");
	}
	else
	{
		strSQL = "select CustomerNo,GrpName,'����ͻ�'	from LDGrp where BlacklistFlag='0' OR BlacklistFlag is null "+" "+ getWherePart( 'BlacklistNo' );	
		//alert("333");
	}
	//strSQL = "select ComCode,Name,Address,Phone,SatrapName from LDCom where 1=1"+" "+ getWherePart( 'ComCode' );			 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�������ͻ����ͣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = ComClientGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
 arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}