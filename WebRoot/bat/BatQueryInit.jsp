<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}

String sStartDay = DateUtil.getCur10Date();
String sEndDay = DateUtil.getCur10Date();

String sArrayName1 = "机构";	 
String sArrayName2 = "日期";	
String sArrayName3 = "时间";	
String sArrayName4 = "类型";	
String sArrayName5 = "状态";	
String sArrayName6 = "结果描述";	


String sArrayLength1 = "30"; 
String sArrayLength2 = "40"; 
String sArrayLength3 = "40"; 
String sArrayLength4 = "48"; 
String sArrayLength5 = "20"; 
String sArrayLength6 = "250";
%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//全局变量，记录登陆管理机构
var cOperator = "<%=cGlobalInput.Operator%>";	//全局变量，记录登录用户


function resetForm(){

	initForm();
	
}

function initForm() {
	fm.StartDay.value = "<%=sStartDay%>";
	fm.EndDay.value = "<%=sEndDay%>";
	initQueryGrid();
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
		mArray[1][0] = "<%=sArrayName1%>";
		mArray[1][1] = "<%=sArrayLength1%>";
		mArray[1][2] = 200;
		mArray[1][3] = 0;
		
		mArray[2] = new Array();
		mArray[2][0] = "<%=sArrayName2%>";
		mArray[2][1] = "<%=sArrayLength2%>";
		mArray[2][2] = 200;
		mArray[2][3] = 0;
		
		mArray[3] = new Array();
		mArray[3][0] = "<%=sArrayName3%>";
		mArray[3][1] = "<%=sArrayLength3%>";
		mArray[3][2] = 200;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "<%=sArrayName4%>";
		mArray[4][1] = "<%=sArrayLength4%>";
		mArray[4][2] = 200;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "<%=sArrayName5%>";
		mArray[5][1] = "<%=sArrayLength5%>";
		mArray[5][2] = 200;
		mArray[5][3] = 0;

		mArray[6] = new Array();
		mArray[6][0] = "<%=sArrayName6%>";
		mArray[6][1] = "<%=sArrayLength6%>";
		mArray[6][2] = 300;
		mArray[6][3] = 0;
		
	
		
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

