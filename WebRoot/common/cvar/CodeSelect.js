/*****************************************************************
 *               Program NAME: 选取代码并显示                       
 *                 programmer: Ouyangsheng                    
 *                Create DATE: 2002.05.09                    
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

var arrayAllCodes =new Array();       //存放所有代码的数组
var arrayOneCode  =new Array();       //存放一种类型代码的数组
var arrayLxCode;                      //存放最近取得代码类型
var arrayBmCode   =new Array();       //存放最近代码的代码
var arrayMCode    =new Array();       //存放最近代码的显示值
var arrayCodeOther=new Array();       //存放最近代码的其它值
var showFirstIndex=0;
               
               
/*************************************************************
 *                     初始化代码
 *  参数  ：  strCodeName：代码类型(Lx_Code)
 *  返回值：  boolean   true：找到所需的代码   false：未找到
 *************************************************************
 */
function initializeCode(strCodeName)
{
	var i;
  	var strValue;
  	var arrayField;
  	var arrayBmCodeTemp   =new Array();
  	var arrayMCodeTemp    =new Array();
  	var arrayCodeOtherTemp=new Array();
    
    
  	for(i=0;i<arrayAllCodes.length;i++)
  	{
  		if( arrayAllCodes[i]["LxCode"] == strCodeName)  //在数组中已经存在
  		{
  			arrayLxCode=arrayAllCodes[i]["LxCode"];
  			arrayBmCode=arrayAllCodes[i]["BmCode"];
  			arrayMCode=arrayAllCodes[i]["MCode"];
  			arrayCodeOther=arrayAllCodes[i]["CodeOther"];
  			return true;
  		}
  	}
  
  	strValue=getCodeValue(strCodeName);     
  	
  	if(strValue==null) return false;
  	if(strValue=="-1")
  	{
  		//alert("代码查询失败,请找系统管理员处理!");
  		return false;
  	}
  	if(strValue=="100")
  	{
  		//alert("没有定义这种代码");
  		return false;
  	} 
  	
  	arrayField=strValue.split("&");                     //拆分字符串
  	i=0;
  
  	while(i<arrayField.length)
  	{
    	var arrayTemp=arrayField[i].split("=");
    	var strFieldName=arrayTemp[0];
    	var strFieldValue=arrayTemp[1];
    	var strFieldOther="";
    	
    	if( arrayTemp.length>2 ) strFieldOther=arrayTemp[2];
    	
    	arrayBmCodeTemp[i]=strFieldName;
    	arrayMCodeTemp[strFieldName]=strFieldValue;
    	arrayCodeOtherTemp[strFieldName]=strFieldOther;
    	i++;
  	}                       
  	                                          
  	arrayLxCode=strCodeName;                       //付给当前代码数组
  	arrayBmCode=arrayBmCodeTemp;
  	arrayMCode=arrayMCodeTemp;
  	arrayCodeOther=arrayCodeOtherTemp;
  
  	arrayOneCode=new Array();                      //将当前值加入保存所有已查过代码的数组中
	arrayOneCode["LxCode"]=strCodeName;
	arrayOneCode["BmCode"]=arrayBmCode;
	arrayOneCode["MCode"]=arrayMCode;
	arrayOneCode["CodeOther"]=arrayCodeOther;
	i=arrayAllCodes.length;
	arrayAllCodes[i]=arrayOneCode;
	
  	return true; 
}

/*************************************************************
 *                      查找代码
 *  参数  ：  strValue：代码值(Bm_Code)
 *  返回值：  string  ：code 或 null
 *************************************************************
 */
function searchCode(strValue)
{
	var i = 0;
  	
  	for(i=0;i<arrayBmCode.length;i++)
  	{
    	if(arrayBmCode[i]==strValue) return strValue;
    	if(arrayMCode[arrayBmCode[i]]==strValue) return arrayBmCode[i];
  	}
	
	return null;
}

/*************************************************************
 *                      查找代码(键盘按键事件)
 *  参数  ：  Field 需要显示代码的控件;
 *            strCodeName 代码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 代码所属区站.
 *  返回值：  string  ：code 或 null
 *************************************************************
 */
function showCodeListKey(Field,strCodeName,intFunctionIndex,stationCode)
{
  	var ex,ey,i,intElementIndex;
  	eobj = window.event;
    key  = eobj.keyCode;

    //alert(eobj.keyCode);
    if (  document.all("spanCode").style.display=="" && key == 13)
    {
       document.all("codeselect").focus();
       document.all("codeselect").onclick();
    }
    
    if (  document.all("spanCode").style.display=="none" && key == 40)
    {
  	  try
  	  {
  	    var posLeft, posTop;
        var oParent;
        
        oParent = Field;
        
        posLeft = 0;
        posTop = oParent.offsetHeight;
  	    do
        { 
          //alert("name:" + oParent.tagName.toLowerCase( ) + "  len:" + oParent.offsetLeft);
          if ( oParent.tagName.toLowerCase( ) != "tr" && oParent.tagName.toLowerCase( ) != "form" && oParent.tagName.toLowerCase( ) != "span" && oParent.tagName.toLowerCase( ) != "div")
          {
            posLeft += parseInt(oParent.offsetLeft);
            posTop  += parseInt(oParent.offsetTop);
          }
          
          oParent = oParent.parentElement;
        } 
        while( oParent.tagName.toLowerCase( ) != "body" );
        
        //alert("document.body.scrollTop" + fraTitle.documnet.body.offsetWidth);
  	    
        ex = posLeft;
        ey = posTop - 5;
        
  	    for(i=0;i<fm.elements.length;i++) //查找fm里的元素
  	    {  
      	  if(fm.elements[i]==Field) 
      	  {
      	    intElementIndex=i;
      	  }
  	    }       
  	  }
  	  catch(ex)
  	  {
        //alert(ex);
      }
      
      //提交代码名称及信息
      getCode(intElementIndex,strCodeName,intFunctionIndex,stationCode,ex,ey);
      
    }
    else if ( key == 40 && document.all("spanCode").style.display=="")
    {
      document.all("codeselect").focus();
      document.all("codeselect").children[showFirstIndex].selected=true;
      //showFirstIndex += showFirstIndex;
    }    
    
    if (  document.all("spanCode").style.display=="none" && (key >= 48 || key==8 || key==46))
    {
  	  try
  	  {
  	    var posLeft, posTop;
        var oParent;
        
        oParent = Field;
        
        posLeft = 0;
        posTop = oParent.offsetHeight;
  	    do
        { 
          //alert("name:" + oParent.tagName.toLowerCase( ) + "  len:" + oParent.offsetLeft);
          if ( oParent.tagName.toLowerCase( ) != "tr" && oParent.tagName.toLowerCase( ) != "form" && oParent.tagName.toLowerCase( ) != "span" && oParent.tagName.toLowerCase( ) != "div")
          {
            posLeft += parseInt(oParent.offsetLeft);
            posTop  += parseInt(oParent.offsetTop);
          }
          
          oParent = oParent.parentElement;
        } 
        while( oParent.tagName.toLowerCase( ) != "body" );
        
        //alert("document.body.scrollTop" + fraTitle.documnet.body.offsetWidth);
  	    
        ex = posLeft;
        ey = posTop - 5;
        
  	    for(i=0;i<fm.elements.length;i++) //查找fm里的元素
  	    {  
      	  if(fm.elements[i]==Field) 
      	  {
      	    intElementIndex=i;
      	  }
  	    }       
  	  }
  	  catch(ex)
  	  {
        //alert(ex);
      }
      
      //提交代码名称及信息
      getCode(intElementIndex,strCodeName,intFunctionIndex,stationCode,ex,ey);
      
      if ( Field.value != null)   
      {
      	i=0;  
      	//alert(arrayBmCode.length);
      	for(i=0;i<arrayBmCode.length;i++)
  	    { 
  	      var t_len = trim(Field.value).length;            	
  		  if( arrayBmCode[i].substring(0,t_len) == trim(Field.value))                         //若是代码值已存在于数组中
  		  {      		
  			showFirstIndex = i;               
  			document.all("codeselect").children[showFirstIndex].selected=true;
  			return;
  			
  		  }  	
  	    }  	    
      }
    }
    else if ( document.all("spanCode").style.display=="" && (key >= 48 || key==8 || key==46))
    {
      if ( Field.value != null)   
      {
      	i=0;  
      	//alert(arrayBmCode.length);
      	for(i=0;i<arrayBmCode.length;i++)
  	    { 
  	      var t_len = trim(Field.value).length;            	
  		  if( arrayBmCode[i].substring(0,t_len) == trim(Field.value))                         //若是代码值已存在于数组中
  		  {      		
  			showFirstIndex = i;               
  			document.all("codeselect").children[showFirstIndex].selected=true;
  			return;
  			
  		  }  	
  	    }  	    
      }
    }
}

/*************************************************************
 *                      查找代码(鼠标单击事件)
 *  参数  ：  Field 需要显示代码的控件;
 *            strCodeName 代码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 代码所属区站.
 *  返回值：  string  ：code 或 null
 *************************************************************
 */
function showCodeList(Field,strCodeName,intFunctionIndex,stationCode)
{
  	var ex,ey,i,intElementIndex;
    
  	try
  	{
  	  var posLeft, posTop;
      var oParent;
      
      oParent = Field;
      
      posLeft = 0;
      posTop = oParent.offsetHeight;
  	  do
       { 
          //alert("name:" + oParent.tagName.toLowerCase( ) + "  len:" + oParent.offsetLeft);
          if ( oParent.tagName.toLowerCase( ) != "tr" && oParent.tagName.toLowerCase( ) != "form" && oParent.tagName.toLowerCase( ) != "span" && oParent.tagName.toLowerCase( ) != "div")
          {
            posLeft += parseInt(oParent.offsetLeft);
            posTop  += parseInt(oParent.offsetTop);
          }
          
          oParent = oParent.parentElement;
        } 
        while( oParent.tagName.toLowerCase( ) != "body" );
        
        //alert("document.body.scrollTop" + fraTitle.documnet.body.offsetWidth);
  	    
        ex = posLeft;
        ey = posTop - 5;
      
  	  //ex = window.event.clientX + document.body.scrollLeft;  //得到事件的坐标x
  	  //ey = window.event.clientY + document.body.scrollTop;   //得到事件的坐标y
  	
  	  for(i=0;i<fm.elements.length;i++) //查找fm里的元素
  	  {
    	if(fm.elements[i]==Field) 
    	{
    	  intElementIndex=i;
    	}
  	  }       
  	}
  	catch(ex)
  	{
      //alert(ex);
    }
    
    //提交代码名称及信息
    getCode(intElementIndex,strCodeName,intFunctionIndex,stationCode,ex,ey);
}

/*************************************************************
 *                     得到代码值串
 *  参数  ：  strCodeName：代码类型(Lx_Code)
 *  返回值：  string     ：代码值串
 *************************************************************
 */
function getCodeValue(strCodeName) 
{
  	var i,j;
  
	try
    {
      reStr= parent.Code.fm.all(strCodeName).value;     //从父frame中取得从服务器端取得的值
    }
    catch(ex)
    {}
    return reStr;                                       
}
  
/*************************************************************
 *                     请求服务器
 *  参数  ：  intElementIndex 需要显示代码的控件的索引号;
 *            strCodeName 代码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 代码所属区站；
 *            ex,ey 显示的位置.
 *  返回值：  无
 *************************************************************
 */
function getCode(intElementIndex,strCodeName,intFunctionIndex,stationCode,ex,ey)
{
	var i;     
    
  	for(i=0;i<arrayAllCodes.length;i++)
  	{             	
  		if( arrayAllCodes[i]["LxCode"] == strCodeName)                         //若是代码值已存在于数组中
  		{      		
  			showCodeByList(intElementIndex,strCodeName,intFunctionIndex,ex,ey);//直接显示
  			return;
  		}  	
  	}
  	
  	//请求服务器
  	try                 
  	{
	  	parent.EX.fm.codename.value         = strCodeName;          //代码名称
	  	parent.EX.fm.currentRisk.value      = "" ;                  //当前险种值,暂赋空值
	  	parent.EX.fm.framename.value        = window.self.name;     //Frame的名字  
	  	parent.EX.fm.intElementIndex.value  = intElementIndex;      //输入域在整个Form中的序号
	  	parent.EX.fm.intFunctionIndex.value = intFunctionIndex;     //1--给前一个域赋值 2--给后一个域赋值
	  	parent.EX.fm.ex.value               = ex;                   //鼠标坐标x
	  	parent.EX.fm.ey.value               = ey;                   //鼠标坐标y
	  	parent.EX.fm.stationCode.value      = stationCode;          //区站信息
	  	parent.EX.fm.submit();                                      //提交
	}
	catch(exception){}
  
}

/*************************************************************
 *                 在非录入情况下请求服务器
 *  参数  ：  intElementIndex 需要显示代码的控件的索引号;
 *            strCodeName 代码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 代码所属区站；
 *            ex,ey 显示的位置.
 *  返回值：  无
 *************************************************************
 */
function getCodeNEnter(Field,strCodeName,iIndex,intFunctionIndex,stationCode)
{
	var i;     
	var j;
	var tIndex;

	if (Field==null)
	{
		Field=fm.elements[iIndex];
		tIndex = iIndex;
    }
    else
    {
      for(i=0;i<fm.elements.length;i++) //查找fm里的元素
  	  {
    	if(fm.elements[i]==Field) 
    	{
    	  tIndex=i;
    	}
  	  }   
  	}
  	
  	i=0  
  	for(i=0;i<arrayAllCodes.length;i++)
  	{     		          	
  		if( arrayAllCodes[i]["LxCode"] == strCodeName)                         //若是代码值已存在于数组中
  		{      		
  			j=0;
  			//alert(strCodeName + arrayAllCodes[i]["BmCode"][1] +"a"+ Field.value+"a");
  			for(j=0;j<arrayAllCodes[i]["BmCode"].length;j++)
  	        {
  	        	//alert(arrayAllCodes[i]["BmCode"][j] + Field.value);
  	        	var tStr1=arrayAllCodes[i]["BmCode"][j];
  	        	var tStr2=Field.value;
  	        	if (tStr1==tStr2)
  	        	{ 
  	        	  setFieldValue(Field,tIndex,intFunctionIndex);
  	        	  return;
  	        	}
  	        }
  			
  		}  	
  	}
  	
  	//请求服务器
  	try                 
  	{
	  	parent.Code.fm.codename.value         = strCodeName;          //代码名称
	  	parent.Code.fm.currentRisk.value      = "" ;                  //当前险种值,暂赋空值
	  	parent.Code.fm.framename.value        = window.self.name;     //Frame的名字  
	  	parent.Code.fm.intElementIndex.value  = tIndex;               //输入域在整个Form中的序号
	  	parent.Code.fm.intFunctionIndex.value = intFunctionIndex;     //1--给前一个域赋值 2--给后一个域赋值
	  	parent.Code.fm.ex.value               = 0;                   //鼠标坐标x
	  	parent.Code.fm.ey.value               = 0;                   //鼠标坐标y
	  	parent.Code.fm.stationCode.value      = stationCode;          //区站信息
	  	parent.Code.fm.submit();                                      //提交
	}
	catch(exception){}
}

/*************************************************************
 *                 下拉框的方式显示代码列表
 *  参数  ：  intElementIndex 需要显示代码的控件的索引号;
 *            strCodeName 代码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            ex,ey 显示的位置.
 *  返回值：  无
 *************************************************************
 */
function showCodeByList(intElementIndex,strCodeName,intFunctionIndex,ex,ey) 
{
  	var strValue;
  	var flag=false;
  	var strText;
  
  	if(!initializeCode(strCodeName) ) return ;    //代码初始化,功能详见前面函数注释
  
  	strText="<select name=codeselect style='width:250px' size=8  onkeyup=\"if (window.event.keyCode==13){setFieldValue(this,"+intElementIndex+","+intFunctionIndex+");}\"" +
  	        "onclick=\"setFieldValue(this,"+intElementIndex+","+intFunctionIndex+")\""+
  	        "onblur=\"return closeCodeList();\">";
  	strValue=trim(fm.elements[intElementIndex].value) ;
  
  	for(i=0;i<arrayBmCode.length;i++)
  	{  
    	flag=true;               
      	strText=strText+"<option value="+arrayBmCode[i]+">";
      	strText=strText+arrayBmCode[i]+"-"+arrayMCode[arrayBmCode[i]];
      	strText=strText+"</option>";
  	}
  	strText=strText+"</select>"

  	if(flag)
  	{
    	document.all("spanCode").innerHTML =strText;
    	document.all("spanCode").style.left=ex;
    	document.all("spanCode").style.top=ey;
    	document.all("spanCode").style.display ='';
  	}
  	else
  	{
    	document.all("spanCode").style.display ='none';
  	}
}

/*************************************************************
 *                     为控件赋值
 *  参数  ： 无
 *  返回值： 无
 *************************************************************
 */ 
function closeCodeList()
{
    showFirstIndex=0;
    document.all("spanCode").style.display ='none';
}

/*************************************************************
 *                     为控件赋值
 *  参数  ：  Field 需要赋值的控件
 *            index 控件的索引号
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个.
 *  返回值：  无
 *************************************************************
 */ 
function setFieldValue(Field,index,intFunctionIndex)
{  
	var curCodeCode="";
  	var curCodeOther="";
  	
  	showFirstIndex=0;
  	
  	if(intFunctionIndex==1)
  	{
    	setPreviousFieldValue(Field,index);
    	curCodeCode = fm.elements[index-1].value;
    	try{ fm.elements[index].onchange();fm.elements[index-1].onchange();  }
    	catch(ex1){}
  	}
  	else if (intFunctionIndex==2)
  	{
    	setNextFieldValue(Field,index);
    	curCodeCode = fm.elements[index].value;
    	try{ fm.elements[index].onchange(); fm.elements[index+1].onchange(); }
    	catch(ex2){}
  	}
  	else
  	{
        setThisFieldValue(Field,index);
    	curCodeCode = fm.elements[index].value;
    	try
    	{ 
    	    fm.elements[index].onchange(); 
    	     //fm.elements[index+1].onchange(); 
    	}
    	catch(ex2){}
    }
  
  	try
  	{ 
    	curCodeOther=arrayCodeOther[curCodeCode];
    	if( curCodeCode!="" && fm.elements[index].name=="ClauseCode" )
    	{
      		fm.elements[index+1].value = curCodeOther;
    	}
    	else
    	{
      		//eval("setDefaultValue(fm.elements[index].name,index,curCodeOther);"); 
      		//setDefaultValue(fm.elements[index].name,index,curCodeOther);
    	}
  	} 
  	catch(exception1){}
   
}
 
/*************************************************************
 *              为给对应的Field及前一个域赋值
 *  参数  ：  Field 需要赋值的控件
 *            index 控件的索引号
 *  返回值：  无
 *************************************************************
 */
function setPreviousFieldValue(Field,Index)
{
	var strValue=trim(Field.value);
  	var strCode=searchCode(strValue);
  	if( strCode!=null)
  	{
    	fm.elements[Index].value=arrayMCode[strCode];
    	fm.elements[Index-1].value=strCode;
    	fm.elements[Index].focus();
    	fm.elements[Index].select();
  	}
  	else if(strValue!="" )
  	{
    	fm.elements[Index].focus();
    	fm.elements[Index].select();
  	}
  	else
  	{
    	fm.elements[Index].value="";
    	fm.elements[Index-1].value="";
  	}
  	document.all("spanCode").style.display ='none'; 
}

/*************************************************************
 *               给对应的Field及下一个域赋值
 *  参数  ：  Field 需要赋值的控件
 *            index 控件的索引号
 *  返回值：  无
 *************************************************************
 */
function setNextFieldValue(Field,Index)
{
  var strValue=trim(Field.value);
  var strCode=searchCode(strValue);
  if( strCode!=null )
  {
    fm.elements[Index].value=strCode;
    fm.elements[Index+1].value=arrayMCode[strCode];
    fm.elements[Index].focus();
    fm.elements[Index].select();
  }
  else if(strValue!="" )
  {
    fm.elements[Index].focus();
    fm.elements[Index].select();  
  }
  else
  {
    fm.elements[Index].value="";
    fm.elements[Index+1].value="";
  }
  document.all("spanCode").style.display ='none';
}

/*************************************************************
 *               给对应的Field域赋值
 *  参数  ：  Field 需要赋值的控件
 *            index 控件的索引号
 *  返回值：  无
 *************************************************************
 */    
function setThisFieldValue(Field,Index)
{
  var strValue=trim(Field.value);
  var strCode=searchCode(strValue);
  if( strCode!=null )
  {
    fm.elements[Index].value=strCode;
    //fm.elements[Index+1].value=arrayMCode[strCode];
    fm.elements[Index].focus();
    fm.elements[Index].select();
  }
  else if(strValue!="" )
  {
    fm.elements[Index].focus();
    fm.elements[Index].select();   
  }
  else
  {
    fm.elements[Index].value="";
    //fm.elements[Index+1].value="";
  }
  document.all("spanCode").style.display ='none';
} 
