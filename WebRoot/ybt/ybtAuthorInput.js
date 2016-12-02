var mOperate="";
var showInfo;

window.onfocus=myonfocus;
var turnPage = new turnPageClass();
//使得从该窗口弹出的窗口能够聚焦
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
    alert("在LAComInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}
//提交后操作,服务器数据返回后执行的操作
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
    {content="删除成功!";
    	resetForm();   
    	 queryClick();//提交后自动查询
    	 
    	}
    	else 
    	{
    		   queryClick();//提交后自动查询
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
//Click事件，当点击“查询”图片时触发该函数
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
//Click事件，当点击增加图片时触发该函数
function addClick()
{  
	var sBankBranch = fm.BankBranch.value;
	var sBankNode = fm.BankNode.value;
	//判断加入的主键是否已经存在
	if (verifyInput() == false) return false;
    turnPage.queryAllRecordCount=0;
     
     //如果添加地区的禁止交易 则查询
   //\\  if(sBankNode.trim() == ""){
	    if(!queryClick2()){
	    	 alert('该银行已禁止该交易!');
	    	 return
 	   } 
 //   } 
    
    //如果添加网点的禁止交易 则查询
    if(sBankBranch.trim()=="" && sBankNode.trim() != "" ){
    	alert("请录入银行地区码!");
    	return
    } 
    
     if(sBankNode.trim() != ""){
  	  if(!queryClick3()){
    	 alert('该地区已禁止该交易!');
    	 return
   	 }
    }
    
    if(!queryClick4()){
    	 alert('该网点已禁止该交易!');
    	 return
   	 }
   
  //  if(fm.BankNode.value != '')
    
    
    //alert(turnPage.queryAllRecordCount)
 //   if(turnPage.queryAllRecordCount>0)
  //  {
  //      alert('该记录已经存在!添加失败.')
  //      return;
  //  }
     fm.hideOperate.value="INSERT||MAIN";
   
  
  if (!confirm("您确实添加改该记录吗?"))return;
  //alert();
  submitForm();

}
	

//提交，保存按钮对应操作
function submitForm()
{
	var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  if (fm.hideOperate.value=="")
  {
    alert("操作控制数据丢失！");
  }
  fm.submit(); //提交

}

function delClick()
{
	
	 
	if(turnPage.queryAllRecordCount <= 0) {
		alert("请先查询！");
		return false;
	}
	
	 fm.hideOperate.value="DEL||MAIN";
	 
	var mCurRowNo = checkedRowNo("LKAuthorGridSel");
	if (-1 == mCurRowNo) {
		alert("请选中一条记录！");
		return false;
	}
	
	var mRowArr = LKAuthorGrid.getRowData(mCurRowNo);
	if (mRowArr == null) { 
		alert("该数据项为空！");
		return false;
	} 
	
	if (!confirm("您确实删除改该记录吗？")) {
		return false;
	}
	 
	
	fm.BankCode.value = mRowArr[0];
	fm.BankBranch.value = mRowArr[2];
	fm.BankNode.value = mRowArr[3];  
	fm.FuncFlag.value = mRowArr[5];
  
	  
//	fm.OperType.value = "DELETE";
	submitForm();
}
//返回选中的行号,这对radio button
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