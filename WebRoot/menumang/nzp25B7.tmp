<%@page import="com.sinosoft.utility.*"%>
<%
//程序名称：menuFunInit.jsp
//程序功能：
//创建日期：2002-10-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<SCRIPT src="Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。

%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
      fm.all("NodeName").value = "";
      fm.all("RunScript").value = "";
  }
  catch(ex)
  {
    alert("在menuFunInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("在menuFunInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initQueryGrpGrid();
  }
  catch(re)
  {
    alert("menuFunInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initQueryGrpGrid()
{


<%
String order = StrTool.unicodeToGBK("序号");
String menuNodeNum = StrTool.unicodeToGBK("菜单节点编码");
String childMenuCount = StrTool.unicodeToGBK("子菜单数");
String fatherMenuNum = StrTool.unicodeToGBK("父菜单ID");
String menuName = StrTool.unicodeToGBK("菜单名称");
String menuMapFile = StrTool.unicodeToGBK("菜单映射文件");
String menuOrder = StrTool.unicodeToGBK("菜单顺序号");
String menuFlag = StrTool.unicodeToGBK("菜单标志");
%>

var _script = document.createElement('script');
_script.setAttribute('charset', 'GBK');

    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="<%= order %>"; //"序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="<%= menuNodeNum %>"; //"菜单ID";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="40px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="<%= childMenuCount %>"; //"子菜单数";    	                //列名
      iArray[2][1]="45px";            		        //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="<%= fatherMenuNum %>"; //"父菜单ID";    	                //列名
      iArray[3][1]="45px";            		        //列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[4]=new Array();
      iArray[4][0]="<%= menuName %>"; //"菜单名称";    	                //列名
      iArray[4][1]="120px";            		        //列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的


      iArray[5]=new Array();
      iArray[5][0]="<%= menuMapFile %>"; //"菜单映射文件";    	                //列名
      iArray[5][1]="240px";            		        //列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[6]=new Array();
      iArray[6][0]="<%= menuOrder %>"; //"菜单顺序号";    	                //列名
      iArray[6][1]="50px";            		        //列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[7]=new Array();
      iArray[7][0]="<%= menuFlag %>"; //"菜单标志";    	                //列名
      iArray[7][1]="50px";            		        //列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;            //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      QueryGrpGrid = new MulLineEnter( "fm" , "QueryGrpGrid" );

      //这些属性必须在loadMulLine前
      QueryGrpGrid.mulLineCount = 3;
      QueryGrpGrid.displayTitle = 1;
      QueryGrpGrid.canChk =0;
      QueryGrpGrid.canSel =1;
      QueryGrpGrid.locked =1;            //是否锁定：1为锁定 0为不锁定
      QueryGrpGrid.hiddenPlus=1;        //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
      QueryGrpGrid.hiddenSubtraction=1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
      QueryGrpGrid.recordNo=0;         //设置序号起始基数为10，如果要分页显示数据有用

      QueryGrpGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }
</script>