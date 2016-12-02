/*****************************************************************
 *               Program NAME: 选取代码并显示                       
 *                 programmer: 
 *                Create DATE: 
 *             Create address: Beijing                       
 *                Modify DATE:                               
 *             Modify address:                               
 *****************************************************************
 *                                                
 *         通用代码查询处理页面,包含在隐藏的框架中,输入
 *     过程中要显示代码清单时调用此页面
 * 
 *****************************************************************
 */

mVs=parent.VD.gVCode;                         //得到存放编码数据的内存引用,要求CVarData.js必须要在一个名称为VD的帧中
mCs=parent.VD.gVSwitch;                       //得到存放编码数据的内存引用,要求CVarData.js必须要在一个名称为VD的帧中

var _Code_FIELDDELIMITER    = "|";            //域之间的分隔符
var _Code_RECORDDELIMITER   = "^";            //记录之间的分隔符

/*************************************************************
 *                      显示编码（该函数为显示编码的入口函数
 *  参数  ：  Field 触发事件的控件;
 *            strCodeName 编码名称;
 *            arrShowCodeObj 将编码显示到对应的控件上
 *            arrShowCodeOrder 编码显示对应控件和编码顺序的对应关系
 *            arrShowCodeFrame 编码显示对应的Frame指针
 *  返回值：  如果没有返回false,否则返回true
 *************************************************************
 */
function showCodeList(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame)
{
  	var ex,ey;
    var tCode;
	if (arrShowCodeObj != null){
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
//  	alert(cObj.offsetTop);
//  	alert(cObj.offsetLeft);
//  	alert(cObj.name);
//  	ey=cObj.offsetTop + cObj.clientHeight+ 5; 
//  	ex=cObj.offsetLeft;
//  	alert(ex);
//  	alert(ey);
    if (arrShowCodeOrder == null)
    {
      arrShowCodeOrder = [0];
    }
    if (objShowCodeFrame == null)
    {
      objShowCodeFrame = parent.fraInterface;//window.self;
    }
	  //将一些参数放到客户端代码区
	  mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
	  mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
    mCs.updateVar("ShowCodeX","0",ex);
    mCs.updateVar("ShowCodeY","0",ey);
    mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame的指针
	}
  
	tCode=searchCode(strCodeName);                         //从代码区读取代码


	if (tCode == false && arrShowCodeObj != null){
		requestServer(strCodeName);                          //请求服务器端，读取数据
		return false;
	}

	showCodeList1(tCode,strCodeName);

	return true;
}

/*************************************************************
 *                     初始化编码
 *  参数  ：  strCodeName：编码名称
 *  返回值：  boolean   true：找到所需的代码   false：未找到
 *************************************************************
 */
function initializeCode(strCodeName)
{
	var i,i1,j,j1;
  var strValue;                         //存放服务器端返回的代码数据
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //存放初始化变量时用
  var t_Str;

  clearShowCodeError();

  strValue  = getCodeValue(strCodeName);              //得到服务器端读取的数据

  arrRecord = strValue.split(_Code_RECORDDELIMITER);  //拆分字符串，形成返回的数组

  t_Str     = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);

  if (t_Str!="0")                                     //如果不为0表示服务器端执行发生错误
  {
    mCs.updateVar("ShowCodeError",getStr(arrRecord[0],2,_Code_FIELDDELIMITER));   //将错误保存到该变量中
    mCs.updateVar("ShowCodeErrorCode",t_Str);   //将错误保存到该变量中
    return false;   
  }

  i1=arrRecord.length;
  for (i=1;i<i1;i++)
  {
    arrField  = arrRecord[i].split(_Code_FIELDDELIMITER); //拆分字符串,将每个纪录拆分为一个数组
    j1=arrField.length;
    arrCode[i-1] = new Array();
    for (j=0;j<j1;j++)
    {
      arrCode[i-1][j] = arrField[j];
    }
  }
  mVs.addVar(strCodeName,"",arrCode);                 //无论是否有数据从服务器端得到,都设置该变量

  return true; 
}



/*************************************************************
 *                     得到编码值串
 *  参数  ：  strCodeName：编码名称
 *  返回值：  string     ：编码值串
 *************************************************************
 */
function getCodeValue(strCodeName) 
{
  var reStr;
  //try
  //    {
      reStr= parent.EX.fm.all("txtVarData").value;     //从父frame中取得从服务器端取得的值
    //}
    //catch(ex)
    //{}
    return reStr;                                       
}
  
/*************************************************************
 *                     请求服务器
 *  参数  ：  intElementIndex 需要显示编码的控件的索引号;
 *            strCodeName 编码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 编码所属区站；
 *            ex,ey 显示的位置.
 *  返回值：  无
 *************************************************************
 */
function requestServer(strCodeName)
{
  var objFrame;
  objFrame=mCs.getVar("ShowCodeFrame");
  	//请求服务器
//  	try                 
//  	{
	  	parent.EX.fm.txtCodeName.value   = strCodeName;          //编码名称
	  	parent.EX.fm.txtFrameName.value  = objFrame.name;        //Frame的名字  
	  	parent.EX.fm.txtVarData.value    = "";                   //返回时需要的空间
	  	parent.EX.fm.txtOther.value      = "";     //提交时的其他数据
	  	parent.EX.fm.submit();
//	}
//	catch(exception){}
}

/*************************************************************
 *                 下拉框的方式显示编码列表
 *  参数  ：  arrCode     包含该类型编码的所有编码信息的数组;
 *            strCodeName 需要显示的代码名称
 *  返回值：  无
 *************************************************************
 */
function showCodeList1(arrCode,strCodeName) 
{
  	var strValue;
  	var flag=false;
  	var strText;
    var arrCount;
	  var fm;
	  
    arrCount=arrCode.length;

	  fm=mCs.getVar("ShowCodeFrame");

	
  	strText="<select name=codeselect style='width:350px' size=8  onchange=setFieldValue(this,'"+strCodeName+"')>";

  	for(i=0;i<arrCount;i++)
  	{  
    	flag=true;               
      strText=strText+"<option value="+arrCode[i][0]+">";
      strText=strText+arrCode[i][0]+"-"+arrCode[i][1];
      strText=strText+"</option>";
  	}
  	strText=strText+"</select>"

  	if(flag)
  	{
    	document.all("spanCode").innerHTML =strText;
    	document.all("spanCode").style.left=mCs.getVar("ShowCodeX");    //读取公共变量区的坐标X
    	document.all("spanCode").style.top=mCs.getVar("ShowCodeY");     //读取公共变量区的坐标Y
    	document.all("spanCode").style.display ='';
  	}
  	else
  	{
    	document.all("spanCode").style.display ='none';
  	}
}

/*************************************************************
 *                     为控件赋值
 *  参数  ：  Field 需要赋值的控件
 *  返回值：  无
 *************************************************************
 */ 
function setFieldValue(Field,strCodeName)
{  
  var tFldCode;               //为一个代码表的纪录，如001,总公司,总公司信息
  var tArrDisplayObj;         //需要显示到的对象
  var tArrDisplayOrder;       //显示的顺序
  var i,iMax;
  
  tFldCode = getOneCode(strCodeName,Field.value);   //得到一个代码纪录
  tArrDisplayObj   = mCs.getVar("ShowCodeObj");     //得到需要显示的对象
  tArrDisplayOrder = mCs.getVar("ShowCodeOrder");   //得到显示时对应的顺序
  iMax = tArrDisplayObj.length;
  try
  {
    for (i=0;i<iMax;i++)
    {
      tArrDisplayObj[i].value = tFldCode[tArrDisplayOrder[i]];  //根据显示顺序设置显示对象
    }
  }
  catch(exception){}
	document.all("spanCode").style.display ='none'; 
}
 
/*************************************************************
 *              从Code内存中读取该Code的信息
 *  参数  ：  strCodeName:Code的类型(名称)
 *            strCode    :Code的编码
 *            index 控件的索引号
 *  返回值：  无
 *************************************************************
 */
function getOneCode(strCodeName,strCode)
{
  var tArrCode;
  var i,iMax;
  var tArrReturn;
  tArrCode = mVs.getVar(strCodeName);
  iMax     = tArrCode.length;
  
  for (i=0 ; i<iMax;i++)
  {
    if (tArrCode[i][0]==strCode)
    {
      tArrReturn = tArrCode[i];
      break;
    }
  }
  return tArrReturn;
}


/*************************************************************
 *                      查找编码
 *  参数  ：  strValue：编码名称
 *  返回值：  string  ：code 或 false
 *************************************************************
 */
function searchCode(strValue)
{
  return mVs.getVar(strValue);      //取得编码，如果没有找到，返回-1
}

/*************************************************************
 *                     清空错误信息
 *  参数  ：  没有
 *  返回值：  没有
 *************************************************************
 */
function clearShowCodeError()
{
  mCs.updateVar("ShowCodeError","","");       //清空错误信息
  mCs.updateVar("ShowCodeErrorCode","","");   //清空错误信息
}

/*************************************************************
 *                     增加一个锁
 *  参数  ：  没有
 *  返回值：  没有
 *************************************************************
 */
function addLock(strLockName)
{
  return mCs.addVar(strLockName,"1","Locked");
}

/*************************************************************
 *                     增加一个锁
 *  参数  ：  没有
 *  返回值：  没有
 *************************************************************
 */
function deleteLock(strLockName)
{
  return mCs.deleteVar(strLockName);
}
