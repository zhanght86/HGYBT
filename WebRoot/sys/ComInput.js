//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
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

//提交，保存按钮对应操作
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
			alert("请先查询");
			return;
		}
		fm.all('DelFlg').value = '';//置空
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
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fm.fmtransact.value=mOperate;
  //  if (fm.hideOperate.value=="")
  //  {
  //    alert("操作控制数据丢失！");
  //  }
  //showSubmitFrame(mDebug);
  //fm.fmtransact.value = "INSERT||COM";
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
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
    //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
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
  	alert("在OLDCom.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

//取消按钮对应操作
function cancelForm()
{
    //window.location="../common/html/Blank.html";
    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
}

//提交前的校验、计算
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
  		alert("上级机构不允许为空");
  		return;
  	}
  } 

  //添加操作
  if (!verifyInput()) 
    return false;
 
  if (trim(mOperate) == 'INSERT||COM')
  {
    //校验机构代码是否存在
    
	  var mySql = new SqlClass();
    mySql.setJspName("../../sys/ComQuerySQL.jsp"); //通过新的SQL方法查询 	
    mySql.setSqlId("easyQuery3");
    mySql.getWherePart("ComCode");
    var strQueryResult  = easyQueryVer3( mySql.getString(), 1, 1, 1);  

    if (strQueryResult) 
    {  	
  		alert('该管理机构代码已存在！');
  		fm.all('ComCode').value = '';
  		return false;
  	}
  }
  
  return true;
}


//显示frmSubmit框架，用来调试
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

//Click事件，当点击增加图片时触发该函数
function addClick()
{
	var ComCode = fm.all('ComCode').value;
	if (!limitNum(ComCode))
	{
		alert("机构编码必须是数字");
	  return; 
  }
  //下面增加相应的代码
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
		alert("86是总部编码,要删除请联系管理员");
		return;
	}
	 //下面增加相应的代码
  if (confirm("您确实想删除该记录吗?"))
  {
    mOperate = "DELETE||COM";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("您取消了修改操作！");
  }
  resetForm();
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	
	var ComCode = fm.all('ComCode').value;
	if (!limitNum(ComCode))
	{
		alert("机构编码必须是数字");
	  return; 
  }
  //下面增加相应的代码
  if (confirm("您确实想修改该记录吗?"))
  {
  
    mOperate = "UPDATE||COM";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("您取消了修改操作！");
  }
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  mOperate="QUERY||MAIN";
  showInfo=window.open("./ComQuery.html", "", "toolbar=no,status=no,menubar=no,location=no,resizable=yes,directories=no,scrollbars=yes,copyhistory=yesm,width=1024,height=768");
  //showInfo=window.open("./ComQuery.html");
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
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
	  //删除标志,所有要删除的数据必须是查询返回的结果
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