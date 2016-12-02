<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}
%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//全局变量，记录登陆管理机构
var cOperator = "<%=cGlobalInput.Operator%>";	//全局变量，记录登录用户

function initForm() {
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
		mArray[1][0] = "批次号";
		mArray[1][1] = "80px";
		mArray[1][2] = 200;
		mArray[1][3] = 0;

		mArray[2] = new Array();
		mArray[2][0] = "交易日期";
		mArray[2][1] = "100px";
		mArray[2][2] = 200;
		mArray[2][3] = 0;

		mArray[3] = new Array();
		mArray[3][0] = "申请人姓名";
		mArray[3][1] = "100px";
		mArray[3][2] = 200;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "交易类型";
		mArray[4][1] = "80px";
		mArray[4][2] = 200;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "保单号";
		mArray[5][1] = "100px";
		mArray[5][2] = 300;
		mArray[5][3] = 0;
		
		mArray[6] = new Array();
		mArray[6][0] = "投保单号";
		mArray[6][1] = "100px";
		mArray[6][2] = 200;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "投保人姓名";
		mArray[7][1] = "100px";
		mArray[7][2] = 200;
		mArray[7][3] = 0;
		
		mArray[8] = new Array();
		mArray[8][0] = "被保人姓名";
		mArray[8][1] = "100px";
		mArray[8][2] = 200;
		mArray[8][3] = 0;

		mArray[9] = new Array();
		mArray[9][0] = "领取金额";
		mArray[9][1] = "100px";
		mArray[9][2] = 200;
		mArray[9][3] = 0;

		mArray[10] = new Array();
		mArray[10][0] = "处理结果";
		mArray[10][1] = "100px";
		mArray[10][2] = 200;
		mArray[10][3] = 0;

		mArray[11] = new Array();
		mArray[11][0] = "描述信息";
		mArray[11][1] = "100px";
		mArray[11][2] = 200;
		mArray[11][3] = 0;
		
	
		
		QueryGrid = new MulLineEnter("fm", "AbcHPResultGrid");
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
