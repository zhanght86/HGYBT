var mOperate="";
var showInfo;

window.onfocus=myonfocus;
var turnPage = new turnPageClass();
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
function resetForm()
{
  try
  {
 //   showDiv(operateButton,"true");
 //   showDiv(inputButton,"false");
    initForm();
  }  
  catch(re)
  {
    alert("��LAComInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ,tBankNode)
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {
    if(fm.hideOperate.value=="DEL||MAIN")
    {content="ɾ���ɹ�!";
    	resetForm();   
    	 queryClick();//�ύ���Զ���ѯ
    	 
    	}
    	else 
    	{
    		   queryClick();//�ύ���Զ���ѯ
    	}
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
   
 
   
  }
}
function getstr1()
{
//str1 ="#1#=#1# and length(trim(comcode))= 4 ";
str1 ="#1#=#1#"; 
}
//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
 initLKAuthorGrid();
 var strSQL = "";
 var strOperate="like";
 strSQL = "  select bankcode,(select codename from ldcode b where b.code = a. bankcode and b.codetype ='trancom_bank'),BankBranch,banknode,managecom,funcflag ,(select codename from ldcode b where b.code = a. funcflag and b.codetype ='ybttranstype') from lktransauthorization a where 1 =1 and managecom like '"+ manageCom +"%%'"//Operator
        strSQL+=  getWherePart( 'BankCode' )
        strSQL+=  getWherePart( 'ManageCom' ) 
		strSQL+=  getWherePart( 'BankBranch' )
		strSQL+=  getWherePart( 'banknode' )
        strSQL+=  getWherePart( 'FuncFlag' )
 strSQL+= " order by bankcode,funcflag";
turnPage.pageLineNum=20;
turnPage.queryModal(strSQL, LKAuthorGrid);

        return true;
}
function queryClick2()
{

 initLKAuthorGrid();
 var sBankCode = fm.BankCode.value;
 var sFuncFlag = fm.FuncFlag.value;
 var strSQL = "";
 var strOperate="like";

strSQL = "  select bankcode,(select codename from ldcode b where b.code = a. bankcode and b.codetype ='trancom_bank'),BankBranch,banknode,managecom,funcflag ,(select codename from ldcode b where b.code = a. funcflag and b.codetype ='ybttranstype') from lktransauthorization a where 1 =1 and managecom like '"+ manageCom +"%%' and BankCode = '" +sBankCode+ "' and FuncFlag ='"+sFuncFlag+"' and BankBranch ='***'"+"and BankNode ='***'"//Operator

var vResult = easyExecSql(strSQL, 1, 0, 1, 1);
if(vResult == null){
	vResult = "0";
}   
	
    if(vResult != "0")  
    { 
        return false;
    } 
        return true;
       
} 

function queryClick3()
{

 initLKAuthorGrid();
 var sBankCode = fm.BankCode.value;
 var sFuncFlag = fm.FuncFlag.value;
 var sBankBranch = fm.BankBranch.value;
 var strSQL = "";
 var strOperate="like";

strSQL = "  select bankcode,(select codename from ldcode b where b.code = a. bankcode and b.codetype ='trancom_bank'),BankBranch,banknode,managecom,funcflag ,(select codename from ldcode b where b.code = a. funcflag and b.codetype ='ybttranstype') from lktransauthorization a where 1 =1 and managecom like '"+ manageCom +"%%' and BankCode = '" +sBankCode+ "' and FuncFlag ='"+sFuncFlag+"' and BankBranch ='"+sBankBranch+"' and banknode='***'"//Operator

var vResult = easyExecSql(strSQL, 1, 0, 1, 1);
if(vResult == null){
	vResult = "0";
}   
	
    if(vResult != "0")  
    { 
        return false;
    } 
        return true;
       
} 


function queryClick4()
{

 initLKAuthorGrid();
 var sBankCode = fm.BankCode.value;
 var sFuncFlag = fm.FuncFlag.value;
 var sBankBranch = fm.BankBranch.value;
 var sBankNode = fm.BankNode.value
 var strSQL = "";
 var strOperate="like";

strSQL = "  select bankcode,(select codename from ldcode b where b.code = a. bankcode and b.codetype ='trancom_bank'),BankBranch,banknode,managecom,funcflag ,(select codename from ldcode b where b.code = a. funcflag and b.codetype ='ybttranstype') from lktransauthorization a where 1 =1 and managecom like '"+ manageCom +"%%' and BankCode = '" +sBankCode+ "' and FuncFlag ='"+sFuncFlag+"' and BankBranch ='"+sBankBranch+"'and BankNode ='"+sBankNode+"'"//Operator
 
var vResult = easyExecSql(strSQL, 1, 0, 1, 1);
if(vResult == null){
	vResult = "0";
}   
	
    if(vResult != "0")  
    { 
        return false;
    } 
        return true;
       
} 
//Click�¼������������ͼƬʱ�����ú���
function addClick()
{  
	var sBankBranch = fm.BankBranch.value;
	var sBankNode = fm.BankNode.value;
	//�жϼ���������Ƿ��Ѿ�����
	if (verifyInput() == false) return false;
    turnPage.queryAllRecordCount=0;
     
     //�����ӵ����Ľ�ֹ���� ���ѯ
   //\\  if(sBankNode.trim() == ""){
	    if(!queryClick2()){
	    	 alert('�������ѽ�ֹ�ý���!');
	    	 return
 	   } 
 //   } 
    
    //����������Ľ�ֹ���� ���ѯ
    if(sBankBranch.trim()=="" && sBankNode.trim() != "" ){
    	alert("��¼�����е�����!");
    	return
    } 
    
     if(sBankNode.trim() != ""){
  	  if(!queryClick3()){
    	 alert('�õ����ѽ�ֹ�ý���!');
    	 return
   	 }
    }
    
    if(!queryClick4()){
    	 alert('�������ѽ�ֹ�ý���!');
    	 return
   	 }
   
  //  if(fm.BankNode.value != '')
    
    
    //alert(turnPage.queryAllRecordCount)
 //   if(turnPage.queryAllRecordCount>0)
  //  {
  //      alert('�ü�¼�Ѿ�����!���ʧ��.')
  //      return;
  //  }
     fm.hideOperate.value="INSERT||MAIN";
   
  
  if (!confirm("��ȷʵ��Ӹĸü�¼��?"))return;
  //alert();
  submitForm();

}
	

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  if (fm.hideOperate.value=="")
  {
    alert("�����������ݶ�ʧ��");
  }
  fm.submit(); //�ύ

}

function delClick()
{
	
	 
	if(turnPage.queryAllRecordCount <= 0) {
		alert("���Ȳ�ѯ��");
		return false;
	}
	
	 fm.hideOperate.value="DEL||MAIN";
	 
	var mCurRowNo = checkedRowNo("LKAuthorGridSel");
	if (-1 == mCurRowNo) {
		alert("��ѡ��һ����¼��");
		return false;
	}
	
	var mRowArr = LKAuthorGrid.getRowData(mCurRowNo);
	if (mRowArr == null) { 
		alert("��������Ϊ�գ�");
		return false;
	} 
	
	if (!confirm("��ȷʵɾ���ĸü�¼��")) {
		return false;
	}
	 
	
	fm.BankCode.value = mRowArr[0];
	fm.BankBranch.value = mRowArr[2];
	fm.BankNode.value = mRowArr[3];  
	fm.FuncFlag.value = mRowArr[5];
  
	  
//	fm.OperType.value = "DELETE";
	submitForm();
}
//����ѡ�е��к�,���radio button
function checkedRowNo(name)
{
  //var isOneMore=false;
  obj=document.all[name]; 

  if(typeof(obj.length)!='undefined')
  {
      for(i=0;i<obj.length;i++)
      {
        if(obj[i].checked)return i;
       //alert(isOneMore)
      }
  }
  else
  {
    if(obj.checked)return 0;
  }


  return -1;
}