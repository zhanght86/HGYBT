//���¼�¼�� showAllMenuInUnselect()��showMenuGrp()
//������:  ����
//��������:  2005-5-4  
//����ԭ��/���ݣ�����ҳ��Ȩ�޲˵�

var turnPage = new turnPageClass();

function queryClick()
{
	var UserCode = fm.all("UserCode").value;
	var ComCode = fm.all("ComCode").value;
	var len = ComCode.length;
	//alert(len);
	if(UserCode=="") 
  {
  	alert("��������Ҫ��ѯ�Ĺ���Ա���룬�ٽ��в�ѯ��");
   	return ;
  }
	
	
	// ��дSQL���
	var sqlStr="select Operator,MenuGrpCode,MenuGrpName,MenuGrpDescription " +
					  " from LDMenuGrp where Operator = '" + UserCode + "' and Operator != '001'";
	//var sqlStr="select Operator,MenuGrpCode,MenuGrpName,MenuGrpDescription " +
	//					 " from LDMenuGrp where Operator = '" + UserCode + "' and MenuGrpCode in " +
	//					 " (select MenuGrpCode from LDUserTOMenuGrp where usercode in " +
	//					 " (select usercode from lduser where comcode like '" + ComCode.substring(0,len-2) + "%%'" +
	//					 " and comcode != '" + ComCode.substring(0,len-2) + "'))";
	//alert(sqlStr);
						 
	turnPage.queryModal(sqlStr, QueryGrpGrid);
}


//�ύǰ���б�Ҫ�ļ��
function DataCopy()
{
	//�õ���ѡ�е�����
	getInput();
	//alert("1111111111111");
	if(fm.all("MenuGrpCode").value == "" || fm.all("MenuGrpCode").value == null)
	{
		alert("���������µĲ˵�������룡");
		return;
	}
	//���̨�ύ����
  submitForm();
    
}


//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm.submit(); //�ύ
}

function afterSubmit(FlagStr)
{
    showInfo.close();
    
    if (FlagStr == "success")
    	alert("���Ʋ˵���ɹ���");
    else
    	alert("���Ʋ˵���ʧ�ܣ����ܵ�ԭ���Ǵ˲˵����Ѵ��ڣ�");  
    	
    clear();	
}

function getInput()
{
	var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<QueryGrpGrid.mulLineCount; i++) 
  {
   if (QueryGrpGrid.getSelNo(i)) 
   { 
      checkFlag = QueryGrpGrid.getSelNo();
      break;
   }
  }
  
  if (checkFlag != 0) 
  { 
    var	Action = QueryGrpGrid.getRowColData(checkFlag - 1, 2); 	
    //alert("ѡ�еĲ˵�������룺" + Action);
    fm.all("Action").value = Action;
  }
  else 
  {
    alert("����ѡ��һ��Ҫ���ƵĲ˵�����Ϣ��"); 
  }
}
  
function clear()
{
	fm.all("MenuGrpName").value = "";
	fm.all("MenuGrpCode").value = "";
	fm.all("MenuGrpDescription").value = "";
	fm.all("MenuSign").value = "";
}










