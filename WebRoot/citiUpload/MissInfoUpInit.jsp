<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}

String day = DateUtil.getCur10Date();
//System.out.println("ITPrintInput AgentCom:" + cGlobalInput.AgentCom);
//System.out.println("ITPrintInput ComCode:" + cGlobalInput.ComCode);
//System.out.println("ITPrintInput ManageCom:" + cGlobalInput.ManageCom);
//System.out.println("ITPrintInput Operator:" + cGlobalInput.Operator);
%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script src="MissInfoUp.js"></script> 
<script language="JavaScript">

function initForm() {
	fm.StartDay.value ="<%=day%>";
	fm.EndDay.value="<%=day%>";
	initBox();
	initQueryGrid();
}

function initQueryGrid()
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
      iArray[1][0]="保单号";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=150;            			//列最大值
      iArray[1][3]=0; 
      
      iArray[2]=new Array();
      iArray[2][0]="被保人证件类型";         		//列名
      iArray[2][1]="90px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人证件类型";         		//列名
      iArray[3][1]="90px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="投保人证件号";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="投保人银行账号";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;
      
      iArray[6]=new Array();
      iArray[6][0]="投保单号";         		//列名
      iArray[6][1]="150px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="客户编号";         		//列名
      iArray[7][1]="120px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;
      
      iArray[8]=new Array();
      iArray[8][0]="退保原因";         		//列名
      iArray[8][1]="150px";            		//列宽
      iArray[8][2]=300;            			//列最大值退保理由编码
      iArray[8][3]=0;
      
      iArray[9]=new Array();
      iArray[9][0]="退保理由编码";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=300;            			//列最大值
      iArray[9][3]=0;
      
      iArray[10]=new Array();
      iArray[10][0]="上传日期";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;

     QueryGrid = new MulLineEnter( "fm" , "QueryGrid" ); 

    //这些属性必须在loadMulLine前
    QueryGrid.displayTitle = 1;
    QueryGrid.locked=0;
    QueryGrid.mulLineCount = 0;
    QueryGrid.hiddenPlus=1;
    QueryGrid.hiddenSubtraction=1;
    QueryGrid.canSel=1;
    QueryGrid.loadMulLine(iArray);  
  
      }
      catch(ex)
      {
        alert(ex);
      } 
}

</script>