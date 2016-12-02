<%
//程序名称：ComClientQuery.js
//程序功能：
//创建日期：2003-1-10
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
  	fm.all('BlacklistNo').value="";
  	fm.all('BlacklistType').value="";
  }
  catch(ex)
  {
    alert("在ComClientQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在ComClientQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initComClientGrid();
  }
  catch(re)
  {
    alert("ComClientQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化ComClientGrid
 ************************************************************
 */
var ComClientGrid;          //定义为全局变量，提供给displayMultiline使用
function initComClientGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="30px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

        iArray[1]=new Array();
		    iArray[1][0]="客户号码";         //列名
		    iArray[1][1]="150px";         //宽度
		    iArray[1][2]=100;         //最大长度
		    iArray[1][3]=0;         //是否允许录入，0--不能，1--允许
		
		    iArray[2]=new Array();
		    iArray[2][0]="客户名称";         //列名
		    iArray[2][1]="150px";         //宽度
		    iArray[2][2]=100;         //最大长度
		    iArray[2][3]=0;         //是否允许录入，0--不能，1--允许
		
		    iArray[3]=new Array();
		    iArray[3][0]="客户类型";         //列名
		    iArray[3][1]="100px";         //宽度
		    iArray[3][2]=100;         //最大长度
		    iArray[3][3]=0;         //是否允许录入，0--不能，1--允许



  
        ComClientGrid = new MulLineEnter( "fm" , "ComClientGrid" ); 

        //这些属性必须在loadMulLine前
        ComClientGrid.mulLineCount = 10;   
        ComClientGrid.displayTitle = 1;
        ComClientGrid.hiddenPlus=1;
        ComClientGrid.hiddenSubtraction=1;
        ComClientGrid.canSel=1; 
        ComClientGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化ComClientGrid时出错："+ ex);
      }
    }


</script>