//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if(mOperate=="")
	{
		addClick();
	}
	if (mOperate=="DELETE||COM")
	{
		var DelFlg = fm.all('DelFlg').value;
		if (DelFlg != '1')
		{
			alert("���Ȳ�ѯ");
			return;
		}
		fm.all('DelFlg').value = '';//�ÿ�
	}
  else if (!beforeSubmit())
    return false;
	
  /*	if (!changeGroup())
  		return false;
    if(mOperate=="INSERT||MAIN")
    {
    	if(!CheckBranchValid())
    		return false;
    }
  */  
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fm.fmtransact.value=mOperate;
  //  if (fm.hideOperate.value=="")
  //  {
  //    alert("�����������ݶ�ʧ��");
  //  }
  //showSubmitFrame(mDebug);
  //fm.fmtransact.value = "INSERT||COM";
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

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
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
	  initForm();
  }
  catch(re)
  {
  	alert("��OLDCom.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    //window.location="../common/html/Blank.html";
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  var ComCode;
  var UpComCode;
  ComCode = fm.all('ComCode').value;
  
  UpComCode = fm.all('UpComCode').value;
  if (UpComCode == null || UpComCode == "")
     UpComCode = "86";
  if (ComCode != '86') 
  {
  	if (UpComCode == null || UpComCode == '')
  	{
  		alert("�ϼ�����������Ϊ��");
  		return;
  	}
  } 

  //��Ӳ���
  if (!verifyInput()) 
    return false;
 
  if (trim(mOperate) == 'INSERT||COM')
  {
    //У����������Ƿ����
    
	  var mySql = new SqlClass();
    mySql.setJspName("../../sys/ComQuerySQL.jsp"); //ͨ���µ�SQL������ѯ 	
    mySql.setSqlId("easyQuery3");
    mySql.getWherePart("ComCode");
    var strQueryResult  = easyQueryVer3( mySql.getString(), 1, 1, 1);  

    if (strQueryResult) 
    {  	
  		alert('�ù�����������Ѵ��ڣ�');
  		fm.all('ComCode').value = '';
  		return false;
  	}
  }
  
  return true;
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else 
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
function limitNum(str) 
{ 
  var pattern = /^([0-9.]+)$/; 

  if (pattern.test(str)) 
  { 
  	return true;
  } else 
  { 
    return false;
  } 
} 

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
	var ComCode = fm.all('ComCode').value;
	if (!limitNum(ComCode))
	{
		alert("�����������������");
	  return; 
  }
  //����������Ӧ�Ĵ���
  mOperate="INSERT||COM";
  //showDiv(operateButton,"false");
  //showDiv(inputButton,"true");
  //fm.fmtransact.value = "INSERT||COM";
  submitForm();
}

function delClick()
{
	var ComCode = fm.all('ComCode').value;
	if (ComCode == '86')
	{
		alert("86���ܲ�����,Ҫɾ������ϵ����Ա");
		return;
	}
	 //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
    mOperate = "DELETE||COM";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("��ȡ�����޸Ĳ�����");
  }
  resetForm();
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
	
	var ComCode = fm.all('ComCode').value;
	if (!limitNum(ComCode))
	{
		alert("�����������������");
	  return; 
  }
  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
  
    mOperate = "UPDATE||COM";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("��ȡ�����޸Ĳ�����");
  }
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  mOperate="QUERY||MAIN";
  showInfo=window.open("./ComQuery.html", "", "toolbar=no,status=no,menubar=no,location=no,resizable=yes,directories=no,scrollbars=yes,copyhistory=yesm,width=1024,height=768");
  //showInfo=window.open("./ComQuery.html");
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




/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		fm.all( 'ComCode' ).value = arrResult[0][0];
		fm.all('OutComCode').value = arrResult[0][1];
		fm.all('Name').value = arrResult[0][2];
		fm.all('ShortName').value = arrResult[0][3];
		fm.all('Address').value = arrResult[0][4];
		fm.all('ZipCode').value = arrResult[0][5];
		fm.all('Phone').value = arrResult[0][6];
		fm.all('Fax').value = arrResult[0][7];
		fm.all('EMail').value = arrResult[0][8];
		fm.all('WebAddress').value = arrResult[0][9];
		//fm.all('SatrapName').value = arrResult[0][10];
		//fm.all('Sign').value = arrResult[0][11];
		//fm.all('ComAreaType').value = arrResult[0][12];
		//fm.all('ComCitySize').value = arrResult[0][12];
	  fm.all('UpComCode').value = arrResult[0][10];
	  //ɾ����־,����Ҫɾ�������ݱ����ǲ�ѯ���صĽ��
	  fm.all('DelFlg').value = arrResult[0][11];
	  //fm.all('ComGrade').value = arrResult[0][14];
	  //fm.all('IsDirUnder').value = arrResult[0][15];
	  
	  //showOneCodeName('ComAreaType','ComAreaType','ComAreaTypeName');
	  //showOneCodeName('ComCitySize','ComCitySize','ComCitySizeName');
	  //showOneCodeName('ComLevel','ComGrade','ComGradeName');
	  //showOneCodeName('comdirectattr','IsDirUnder','IsDirUnderName');
	}
}

function SelectCom(){
    showInfo=window.open('../treeCom/jsp/SelectSysCategory.jsp','newwindow','height=300, width=600, top='+(screen.availHeight-300)/2+',left='+(screen.availWidth-600)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
  }