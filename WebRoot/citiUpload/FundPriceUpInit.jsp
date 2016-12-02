<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}
System.out.println("ITPrintInput AgentCom:" + cGlobalInput.AgentCom);
System.out.println("ITPrintInput ComCode:" + cGlobalInput.ComCode);
System.out.println("ITPrintInput ManageCom:" + cGlobalInput.ManageCom);
System.out.println("ITPrintInput Operator:" + cGlobalInput.Operator);
%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script src="MissInfoUp.js"></script> 
<script language="JavaScript">

function initForm() {
	initBox();
	initYBTGrid();
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
      iArray[1][0]="账户计价日";         		//列名
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=150;            			//列最大值
      iArray[1][3]=0; 
      
      iArray[2]=new Array();
      iArray[2][0]="账户名称";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="卖出价";         		//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="买入价";         		//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

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