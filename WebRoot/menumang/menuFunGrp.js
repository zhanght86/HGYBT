//���¼�¼�� queryClick(),fillUserGrid(),insertClick()
//������:  ����
//��������:  2005-5-4  
//����ԭ��/���ݣ����Ӳ˵�����ʱ���߼��жϡ�

var showInfo;

//���ƽ����ϵ�mulLine����ʾ����
var mulLineShowCount = 15;

var selectArray = new Array();
var unselectArray = new Array();

var userArray = new Array();
var userArrayLen = 0; 
var userCurPage = 0;	

var removeArray = new Array() //���²˵����е�ɾ����¼
var showDiv = "false";

var turnPage = new turnPageClass();   

//��ѯ���г����в˵���¼
function queryClick()
{
	fm.all("checkbox1").checked = false;
	fm.all("checkbox2").checked = false;  
	if(fm.all("NodeName").value.length ==0)
	{
	  var mySql = new SqlClass();
	  mySql.setJspName("../../menumang/menuFunSQL.jsp"); //ͨ���µ�SQL������ѯ 	
    mySql.setSqlId("easyQuery1");   
	}	
	else 
	{
	  var NodeName = fm.all("NodeName").value;
		var mySql = new SqlClass();
	  mySql.setJspName("../../menumang/menuFunSQL.jsp"); //ͨ���µ�SQL������ѯ 	
    mySql.setSqlId("easyQuery2");   
    mySql.addPara("NodeName", NodeName);     
	}
  turnPage.useSimulation = 1; 
	var strTemp = easyQueryVer3(mySql.getString(), 1, 0, 1);
	if (strTemp.length )
	{
      var tempArray = new Array;
      tempArray = decodeEasyQueryResult(strTemp);  
      var times = 1;
      while (times * 200 < turnPage.queryAllRecordCount)
        times = times + 1;      
      for ( var i = 2; i <= times; i++)
      {
    	  var strTemp1 = easyQueryVer3(mySql.getString(),1,0, (i-1)*200 + 1);
	      var sss = strTemp1.substring(6,strTemp1.length);
        strTemp = strTemp + '^' + sss; 
      }       
      userArray = decodeEasyQueryResult(strTemp);
      userArrayLen = userArray.length;
      fillUserGrid(); 
	}
	else
	{
	  initQueryGrpGrid();
	  alert("û�в�ѯ����Ҫ�Ľ��!");
	}
}  

function fillUserGrid()
{
   QueryGrpGrid.clearData("QueryGrpGrid");

   for (var i = 0; i < mulLineShowCount; i++) {

        QueryGrpGrid.addOne("QueryGrpGrid");
   	var offset = i  + userCurPage*mulLineShowCount;

   	if (offset < userArrayLen) {
   	    QueryGrpGrid.setRowColData(i,1,userArray[offset][0]);
   	    QueryGrpGrid.setRowColData(i,2,userArray[offset][1]);
   	    QueryGrpGrid.setRowColData(i,3,userArray[offset][3]);
   	    QueryGrpGrid.setRowColData(i,4,userArray[offset][2]);
   	    QueryGrpGrid.setRowColData(i,5,userArray[offset][4]);
   	    QueryGrpGrid.setRowColData(i,6,userArray[offset][5]);
   	    QueryGrpGrid.setRowColData(i,7,userArray[offset][6]);//�˵���־
//   	    alert(userArray[offset][4]);
   	} else {
   	    QueryGrpGrid.setRowColData(i,1,"");
   	    QueryGrpGrid.setRowColData(i,2,"");
        QueryGrpGrid.setRowColData(i,3,"");
   	    QueryGrpGrid.setRowColData(i,4,"");
   	    QueryGrpGrid.setRowColData(i,5,"");
   	    QueryGrpGrid.setRowColData(i,6,"");
   	    QueryGrpGrid.setRowColData(i,7,"");//�˵���־
   	}  	        
   }

   //����Ĵ�����Ϊ��ʹ��ҳʱ�������ȷ��ʾ
   for (var i = 0; i < mulLineShowCount; i++) {
		var offset = i  + userCurPage*mulLineShowCount;
        fm.all("QueryGrpGridNo")[i].value = offset + 1;
   }
		
}

function userFirstPage()
{
	if (userArrayLen == 0)
	    return;
	    
	userCurPage = 0;
	fillUserGrid();
}

function userLastPage()
{
	if (userArrayLen == 0)
	    return;
	    
	while ((userCurPage + 1)*mulLineShowCount < userArrayLen)
	    userCurPage++;

	fillUserGrid();
}


function userPageDown()
{
	if (userArrayLen == 0)
	    return;
	    
    if (userArrayLen <= (userCurPage + 1) * mulLineShowCount) {
    	alert("�Ѵ�βҳ");
    } else {
        userCurPage++;
        fillUserGrid();
    }
}

function userPageUp()
{
	if (userArrayLen == 0)
	    return;

    if (userCurPage == 0) {
    	alert("�ѵ���ҳ");
    } else {
        userCurPage--;
        fillUserGrid();
    }
}

function submitForm()
{
  var i = 0;
  var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.submit(); //�ύ
  fm.all("RunScript").value ="";
  //queryClick();
}

function insertClick()
{
	//ȡ�ò˵���־
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if (selMenuGrpNo == 0) 
	{
		alert("���ѯ����ѡ��һ����¼");
		return;
	}
	var selMenu = selMenuGrpNo - 1;
	var NodeSign = QueryGrpGrid.getRowColData(selMenu,7);
	var RunScript = QueryGrpGrid.getRowColData(selMenu,5);
//	alert(NodeSign);
//  alert(RunScript);
 	if (fm.all("NodeName").value == ""){
 		alert ("������˵��ڵ�����!");
 	}
 	//�ų�ҳ�湦�ܲ˵�ͬ�����뵽һ��˵�2005
	else 
	{
		if (NodeSign != 2 && fm.all("checkbox2").checked == true){
			if(fm.all("checkbox1").checked == false){
				alert("ҳ�湦�ܲ˵�����ͬ�����뵽һ��˵�,������ѡ��");
				}
			else {
				//�ж��Ƿ�ĩ���˵�
				if(NodeSign != 1 && RunScript==null){
					alert("ҳ�湦�ܲ˵������¼����뵽��Ҷ�Ӳ˵�,������ѡ��");
					}
				else{
					fm.all("Action").value = "insert";   
				   //��Ϊ�Ӳ˵�����(��ѡ����ͬ���˵�����)     	
				   if(fm.all("checkbox1").checked == true) 
				      {     	
				      	fm.all("isChild").value = "true";
				      }      
				   else
				      {fm.all("isChild").value = "false";}
				   //�Ƿ���Ϊҳ��Ȩ�޲˵����� 2005  	
				   if(fm.all("checkbox2").checked == true) 
				      {     	
				      	fm.all("isChild2").value = "true";
				      }      
				   else
				      {fm.all("isChild2").value = "false";}            
				   submitForm();
					}					
				}
			}
			else {
				if (NodeSign == 2 && fm.all("checkbox1").checked == true){
					alert("ҳ�湦�ܲ˵���������Ӳ˵�,������ѡ��");
					}
				else{		 
				   fm.all("Action").value = "insert";   
				   //��Ϊ�Ӳ˵�����(��ѡ����ͬ���˵�����)     	
				   if(fm.all("checkbox1").checked == true) 
				      {     	
				      	fm.all("isChild").value = "true";
				      }      
				   else
				      {fm.all("isChild").value = "false";}
				   //�Ƿ���Ϊҳ��Ȩ�޲˵����� 2005  	
				   if(fm.all("checkbox2").checked == true) 
				      {     	
				      	fm.all("isChild2").value = "true";
				      }      
				   else
				      {fm.all("isChild2").value = "false";}            
				   submitForm();
			    }
				}
  }
}

function deleteClick() 
{
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
        if (selMenuGrpNo == 0) {
	  alert("����û��ѡ����Ҫɾ���Ĳ˵�");
	  return;
	}    	
	if (!confirm("��ȷʵҪɾ������˵���"))
	  return;
	
	fm.all("Action").value = "delete";

    submitForm();    	
}

function afterSubmit(FlagStr)
{
    showInfo.close();	
       
    if (fm.all('action').value == "insert") {
        if (FlagStr == "success")
            alert("���Ӳ˵��ɹ���");
        else
            alert("���Ӳ˵�ʧ�ܣ����ܵ�ԭ���Ǵ˲˵��Ѵ���");
    }
    
    if (fm.all('action').value == "delete") {
        if (FlagStr == "success")
            alert("ɾ���˵��ɹ���");
        else
            alert("ɾ���˵�ʧ��,ԭ������ǲ���ɾ�����Ӳ˵��Ĳ˵�!");
    }		
}







