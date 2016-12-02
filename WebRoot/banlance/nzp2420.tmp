<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
//返回按钮初始化
var str = "";
// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    fm.all('BankCode').value = '';
    //fm.all('ZoneNo').value = '';
    //fm.all('BankNode').value = '';
    fm.all('StartTransDate').value = '';
    fm.all('EndTransDate').value = '';
    fm.all('ManageCom').value = '';
    
  }
  catch(ex)
  {
    alert("在YBTBalanceQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在YBTBalanceQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initybtPolGrid();
  }
  catch(re)
  {
    alert("YBTBalanceQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initybtPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="银行名称";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     

      iArray[2]=new Array();
      iArray[2][0]="地区编码";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

     
      /*
      iArray[3]=new Array();
      iArray[3][0]="银行网点";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      */
      iArray[3]=new Array();
      // 对帐明细交易日期修改为对帐文件交易日期,modify by hwf at 2008年12月9日
      // iArray[4][0]="对帐明细交易日期";         		//列名
      iArray[3][0]="对帐文件交易日期";  
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      
      iArray[4]=new Array();
      // 明细交易结果修改为对帐文件结果,modify by hwf at 2008年12月9日
      // iArray[5][0]="明细交易结果";         		//列名
      iArray[4][0]="对帐文件结果";
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;  
      
      //iArray[6]=new Array();
      //iArray[6][0]="分公司名称";         		//列名
      //iArray[6][1]="80px";            		//列宽
      //iArray[6][2]=100;            			//列最大值
      //iArray[6][3]=0;  
      
      
      ybtPolGrid = new MulLineEnter( "fm" , "ybtPolGrid" ); 
      //这些属性必须在loadMulLine前
      ybtPolGrid.mulLineCount = 10;   
      ybtPolGrid.displayTitle = 1;
      ybtPolGrid.locked = 1;
      ybtPolGrid.canSel = 1;
      ybtPolGrid.hiddenPlus = 1;
      ybtPolGrid.hiddenSubtraction = 1;
      ybtPolGrid.loadMulLine(iArray); 
      
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>     