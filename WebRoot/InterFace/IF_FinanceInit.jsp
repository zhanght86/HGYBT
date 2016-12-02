<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%
//程序名称：LAComInInit.jsp
//程序功能：
//创建日期：2009-09-30
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     String gToday = PubFun.getCurrentDate(); //添加页面控件的初始化。
    GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
     String sStartDay = DateUtil.getCur10Date();
 	 String sEndDay = DateUtil.getCur10Date();
%>                            

<script language="JavaScript">
	

function initForm()
{
  try
  { 
	fm.StartDay.value = "<%=sStartDay%>";
    fm.EndDay.value = "<%=sEndDay%>";
    fm.currentDate.value = "<%=sStartDay%>";
    initYBTGrid();
  }
  catch(ex)
  {
    alert("YBTdownloadInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initYBTGrid()
{
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=20;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="区域";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="40px";            		//列宽
      iArray[1][2]=50;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[1][7]="downLoad";

      iArray[2]=new Array();
      iArray[2][0]="交易日期";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="接口文件状态";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="文件路径";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="文件名";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="生成日期";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="生成时间";         		//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="下载";         		//列名
      iArray[8][1]="30px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="区域代码";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="文件状态标志";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

     YBTGrid = new MulLineEnter( "fm" , "YBTGrid" ); 

    //这些属性必须在loadMulLine前
    YBTGrid.mulLineCount = 0;   
    YBTGrid.displayTitle = 1;
    YBTGrid.locked=1;
    YBTGrid.hiddenPlus=1;
    YBTGrid.hiddenSubtraction=1;
    YBTGrid.canSel=0;
    YBTGrid.canChk=0;
    YBTGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      } 
}
</script>