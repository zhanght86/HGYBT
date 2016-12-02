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
	  initQueryGrid();
  }
  catch(ex)
  {
    alert("YBTdownloadInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initQueryGrid() {

    var mArray = new Array();
	
	try {
		mArray[0] = new Array();
		mArray[0][0] = "序号";		//列名
		mArray[0][1] = "30px";		//列宽
		mArray[0][2] = 10;			//列最大值
		mArray[0][3] = 0;		    	//是否允许输入,1表示允许，0表示不允许
		
		mArray[1] = new Array();
		mArray[1][0] = "区域";
		mArray[1][1] = "40px";
		mArray[1][2] = 50;
		mArray[1][3] = 0;
		
		mArray[2] = new Array();
		mArray[2][0] = "交易日期";
		mArray[2][1] = "60px";
		mArray[2][2] = 100;
		mArray[2][3] = 0;
		
		mArray[3] = new Array();
		mArray[3][0] = "接口文件状态";
		mArray[3][1] = "80px";
		mArray[3][2] = 100;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "文件详情";
		mArray[4][1] = "120px";
		mArray[4][2] = 200;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "文件名";
		mArray[5][1] = "90px";
		mArray[5][2] = 200;
		mArray[5][3] = 0;

		mArray[6] = new Array();
		mArray[6][0] = "生成日期";
		mArray[6][1] = "60px";
		mArray[6][2] = 200;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "生成时间";
		mArray[7][1] = "50px";
		mArray[7][2] = 200;
		mArray[7][3] = 0;
	
		// mArray[8]=new Array();
    //  mArray[8][0]="下载";         		
    //  mArray[8][1]="30px";            		
   //   mArray[8][2]=200;            			
   //   mArray[8][3]=0;              			
      
      mArray[8]=new Array();
      mArray[8][0]="区域代码";         		
      mArray[8][1]="0px";            		
      mArray[8][2]=200;            			
      mArray[8][3]=0;              			
      
      mArray[9]=new Array();
      mArray[9][0]="文件状态标志";         	
      mArray[9][1]="0px";            		
      mArray[9][2]=200;            			
      mArray[9][3]=0; 
      
      mArray[10]=new Array();
      mArray[10][0]="LogNo";         	
      mArray[10][1]="0px";            		
      mArray[10][2]=200;            	
      mArray[10][3]=0; 
      
      mArray[11]=new Array();
      mArray[11][0]="文件路径";         	
      mArray[11][1]="0px";            		
      mArray[11][2]=200;            	
      mArray[11][3]=0; 
		

		QueryGrid = new MulLineEnter("fm", "QueryGrid");
		QueryGrid.displayTitle = 1;
		QueryGrid.locked = 0;
		QueryGrid.mulLineCount = 0;
		QueryGrid.hiddenPlus = 1;
		QueryGrid.hiddenSubtraction = 1;
		QueryGrid.canSel = 1;
		QueryGrid.loadMulLine(mArray); 
      }
      catch(ex)
      {
        alert(ex);
      } 
}
</script>