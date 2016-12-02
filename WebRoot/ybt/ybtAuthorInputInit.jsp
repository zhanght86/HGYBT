<%@page contentType="text/html;charset=GBK"%>
<%
  //程序名称：ybtAuthorInputInit.jsp
  //程序功能：银保通交易控制
  //创建日期：2006-03-13 15:12:33
  //创建人  ：liuyx程序创建
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT><%//添加页面控件的初始化。%>
<script language="JavaScript">
function initInpBox()
{
  try
  { 
    fm.all('ManageCom').value = '8621';
    fm.all('BankCode').value = '';
   fm.all('BankBranch').value = '';
    fm.all('BankNode').value = '';
    fm.all('FuncFlag').value = '';
    fm.all('BankCodeName').value = '';
    fm.all('FuncFlagName').value = '';
    
   
  }
  catch(ex)
  {
    alert("在LAComInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
function initSelBox()
{
  try
  {
  
  }
  catch(ex)
  {
    alert("在LAComInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    
    initInpBox();    
    
    initSelBox();
    initLKAuthorGrid();
    
    
  }
  catch(re)
  {
    alert("LAComInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
var LKAuthorGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initLKAuthorGrid()
{
    var iArray = new Array();
    //var i11Array = getAgentGradeStr();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="银行代码";         		//列名
      iArray[1][1]="50px";            		//列宽 
      iArray[1][2]=200;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
 	  iArray[2]=new Array();
      iArray[2][0]="银行名称";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

 	  iArray[3]=new Array();
      iArray[3][0]="银行地区码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;   
                 			//是否允许输入,1表示允许，0表示不允许
      iArray[4]=new Array();
      iArray[4][0]="银行网点码";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="机构代码";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="禁止交易类型";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[7]=new Array();
      iArray[7][0]="禁止交易名称";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    
      LKAuthorGrid = new MulLineEnter( "fm" , "LKAuthorGrid" );
      //这些属性必须在loadMulLine前
      LKAuthorGrid.displayTitle = 1;
      LKAuthorGrid.locked = 0;
      LKAuthorGrid.mulLineCount = 0;
      LKAuthorGrid.hiddenPlus = 1;
      LKAuthorGrid.hiddenSubtraction = 1;
      LKAuthorGrid.canSel = 1;

      LKAuthorGrid.loadMulLine(iArray);


     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
