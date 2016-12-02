<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.schema.LDComSchema"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}


String sManageCom = cGlobalInput.ManageCom;
%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//全局变量，记录登陆管理机构
var cOperator = "<%=cGlobalInput.Operator%>";	//全局变量，记录登录用户
var cManageComName="<%=cGlobalInput.ManageComName%>";
var bArrayName1="银行渠道";
var bArrayName2="银行产品代码";
var bArrayName3="产品代码"; 
var bArrayName4="产品名称";
var bArrayName5="产品起售日期";
var bArrayName6="产品停售日期";
var bArrayName7="产品系统状态";
var bArrayName8="银行代码";
var bArrayName9 = "产品代码";
var bArrayName10 = "机构代码";


var bArrayLength1 = "20"; 
var bArrayLength2 = "20"; 
var bArrayLength3 = "20"; 
var bArrayLength4 = "80"; 
var bArrayLength5 = "30"; 
var bArrayLength6 = "30";
var bArrayLength7 = "20";
var bArrayLength8 = "0";
var bArrayLength9 = "0";
var bArrayLength10 = "0";




function initForm() {
    initOK();
	initGrid();
}

function insertForm(){
	
	fm.BankCode.value="";
	fm.BankName.value="";
	
	 bArrayName1="产品";
     bArrayName2="银行渠道";
     bArrayName3="上级机构";
     bArrayName4="机构名称";
  
     bArrayName5="产品系统状态";
     bArrayName6= "开始日期";
     bArrayName7= "结束日期";
     bArrayName8="银行代码";
     bArrayName9 = "产品代码";
     bArrayName10= "机构代码";
    

     
     bArrayLength1 = "80"; 
     bArrayLength2 = "20"; 
     bArrayLength3 = "20"; 
     bArrayLength4 = "20"; 
     bArrayLength5 = "20";
     bArrayLength6 = "0";
     bArrayLength7 = "0";
     bArrayLength8 = "0";
     bArrayLength9 = "0";
     bArrayLength10 = "0";

     initGrid();
   
}
function initComcode(){
    fm.iComCode.value = cManageCom;
	fm.iComCodeName.value = cManageCom+cManageComName;
}
function initOK() {
	fm.iBankCode.value = "";
	fm.iBankName.value = "";
	fm.iRiskCode.value = "";
	fm.iRiskName.value = "";
	fm.iActivityCode.value = "";
	fm.ProStateCode.value="0";
	fm.ProStateName.value="新增停售";
	fm.iComCode.value = cManageCom;
	fm.iComCodeName.value = cManageCom+cManageComName;
	fm.iStartDate.value = "";
	fm.iEndDate.value = "";
	fm.ProCode.value="";
	fm.iProductCode.value="";
	//fm.iComCodeName.value = "";
	
	   bArrayName1="银行渠道";
       bArrayName2="产品代码";
       bArrayName3="AS400代码";
       bArrayName4="产品名称";
       bArrayName5="产品起售日期";
       bArrayName6="产品停售日期";
       bArrayName7="产品系统状态";
       bArrayName8="银行代码";
       bArrayName9 = "产品代码";
       bArrayName10 = "机构代码";


       bArrayLength1 = "20"; 
       bArrayLength2 = "20"; 
       bArrayLength3 = "20"; 
       bArrayLength4 = "80"; 
       bArrayLength5 = "30"; 
       bArrayLength6 = "30";
       bArrayLength7 = "20";
       bArrayLength8 = "0";
       bArrayLength9 = "0";
       bArrayLength10 = "0";

       initGrid();
} 

 
function initGrid() {
	var mArray = new Array();
 
	try {
		
		mArray[0] = new Array();
		mArray[0][0] = "序号";		//列名（此列为顺序号，列名无意义，而且不显示）
		mArray[0][1] = "30px";		//列宽
		mArray[0][2] = 10;			//列最大值
		mArray[0][3] = 0;				//是否允许输入,1表示允许，0表示不允许

		mArray[1] = new Array();
		mArray[1][0] = bArrayName1;
		mArray[1][1] = bArrayLength1;
		mArray[1][2] = 40;
		mArray[1][3] = 0;
		
		mArray[2] = new Array();
		mArray[2][0] = bArrayName2;
		mArray[2][1] = bArrayLength2;
		mArray[2][2] = 40;
		mArray[2][3] = 0;
		
		mArray[3] = new Array();
		mArray[3][0] = bArrayName3;
		mArray[3][1] = bArrayLength3;
		mArray[3][2] = 40;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = bArrayName4;
		mArray[4][1] = bArrayLength4;
		mArray[4][2] = 40;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = bArrayName5;
		mArray[5][1] = bArrayLength5;
		mArray[5][2] = 40;
		mArray[5][3] = 0;

		mArray[6] = new Array();
		mArray[6][0] = bArrayName6;
		mArray[6][1] = bArrayLength6;
		mArray[6][2] = 40;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = bArrayName7;
		mArray[7][1] = bArrayLength7;
		mArray[7][2] = 200;
		mArray[7][3] = 0;
		
		mArray[8] = new Array();
		mArray[8][0] = bArrayName8;
		mArray[8][1] = bArrayLength8;
		mArray[8][2] = 200;
		mArray[8][3] = 0;
		
		mArray[9] = new Array();
		mArray[9][0] = bArrayName9;
		mArray[9][1] = bArrayLength9;
		mArray[9][2] = 200;
		mArray[9][3] = 0;
		
		mArray[10] = new Array();
		mArray[10][0] = bArrayName10;
		mArray[10][1] = bArrayLength10;
		mArray[10][2] = 200;
		mArray[10][3] = 0;
		
		
		QueryGrid = new MulLineEnter("fm", "QueryGrid");
		QueryGrid.displayTitle = 1;
		QueryGrid.locked = 0; 
		QueryGrid.mulLineCount = 0;
		QueryGrid.hiddenPlus = 1;
		QueryGrid.hiddenSubtraction = 1;
		QueryGrid.canSel = 1;
		QueryGrid.loadMulLine(mArray);
	} catch(ex) {
		alert(ex);
	}
	
	

}
</script>
