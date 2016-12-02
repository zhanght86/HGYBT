<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
<SCRIPT src="/common/javascript/Common.js"></SCRIPT>
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
    fm.all('StartDate').value = '';
    fm.all('EndDate').value = '';
	  fm.all('BankCode').value = '';
	  fm.all('BankName').value = '';
	  fm.all('ContNo').value = '';
  }
  catch(ex)
  {
  	<%
  		String errorMes = "在YBTContInfoQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!";
  	%>
    alert("<%= errorMes %>");
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
    alert("在YBTContInfoQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("YBTContInfoQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initybtPolGrid()
{       

<%
String order = "序号";
String ProposalPrtNo = "投保单号";
String contno = "保单号";
String appntname = "投保人姓名";
String insuredname = "被保人姓名";
String ProductId = "险种编码";
//String paymode = "缴费方式";
String Bak1 = "备用1";
String prem = " 保费(元)";
String amnt = " 保额(元)";
String State = "保单状态";
String SignDate = "签单日期";
String TranCom = "交易机构";
String NodeNo = "交易网点";
String agentname ="代理机构名称";
String BankOperator ="柜员代码";
%>
	                        
    var iArray = new Array();    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="<%= order %>"; //序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

     
     
      iArray[1]=new Array();
      iArray[1][0]="<%= contno %>"; //保单号";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=110;            			//列最大值
      iArray[1][3]=0;  

      iArray[2]=new Array();
      iArray[2][0]="<%=ProposalPrtNo %>"; //投保单号";         		//列名
      iArray[2][1]="80";            		//列宽
      iArray[2][2]=110;            			//列最大值
      iArray[2][3]=0;  

      iArray[3]=new Array();
      iArray[3][0]="<%= appntname %>"; //投保人姓名";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=90;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="<%= insuredname %>"; //被保人姓名";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=110;            			//列最大值
      iArray[4][3]=0;  
      
      iArray[5]=new Array();
      iArray[5][0]="<%= ProductId %>"; //险种编码";         		//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[6]=new Array();
      iArray[6][0]="<%= amnt %>"; //保额";         		//列名
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=70;            			//列最大值
      iArray[6][3]=0;  
   
      iArray[7]=new Array();
      iArray[7][0]="<%= prem %>"; //保费";         		//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=110;            			//列最大值
      iArray[7][3]=0; 
      
      iArray[8]=new Array();
      iArray[8][0]="<%= State %>"; //保单状态";         		//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=110;            			//列最大值
      iArray[8][3]=0; 
      
      iArray[9]=new Array();
      iArray[9][0]="<%= SignDate %>"; //原保单生效日期  现签单日期";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=110;            			//列最大值
      iArray[9][3]=0;  
      
      iArray[10]=new Array();
      iArray[10][0]="<%=TranCom%>"; //出单银行";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=160;            			//列最大值
      iArray[10][3]=0;  
      

      iArray[11]=new Array();
      iArray[11][0]="<%= NodeNo %>"; //出单网点";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=110;            			//列最大值
      iArray[11][3]=0;  
      
      iArray[12]=new Array();
      iArray[12][0]="<%= agentname %>"; //客户经理 代理人";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=110;            			//列最大值
      iArray[12][3]=0;  
      
      iArray[13]=new Array();
      iArray[13][0]="<%= BankOperator %>"; //柜员代码";         		//列名
      iArray[13][1]="60px";            		//列宽
      iArray[13][2]=110;            			//列最大值
      iArray[13][3]=0;  
      
      ybtPolGrid1 = new MulLineEnter( "fm" , "ybtPolGrid1" ); 
      //这些属性必须在loadMulLine前
      ybtPolGrid1.mulLineCount = 0;   
      ybtPolGrid1.displayTitle = 1;
      ybtPolGrid1.locked = 1;
      ybtPolGrid1.canSel = 1;
      ybtPolGrid1.hiddenPlus = 1;
      ybtPolGrid1.hiddenSubtraction = 1;
      ybtPolGrid1.loadMulLine(iArray); 
      
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>     