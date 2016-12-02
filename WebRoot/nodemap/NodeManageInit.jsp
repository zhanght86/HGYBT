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
	fm.iManageCom.value = cManageCom;
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
		mArray[1][0] = "银行";
		mArray[1][1] = "30px";
		mArray[1][2] = 200;
		mArray[1][3] = 0;

		mArray[2] = new Array();
		mArray[2][0] = "交易网点";
		mArray[2][1] = "70px";
		mArray[2][2] = 200;
		mArray[2][3] = 0;

		mArray[3] = new Array();
		mArray[3][0] = "银代机构";
		mArray[3][1] = "150px";
		mArray[3][2] = 200;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "银代机构名称";
		mArray[4][1] = "250px";
		mArray[4][2] = 300;
		mArray[4][3] = 0;
		
		mArray[5] = new Array();
		mArray[5][0] = "管理机构";
		mArray[5][1] = "70px";
		mArray[5][2] = 200;
		mArray[5][3] = 0;
		
		mArray[6] = new Array();
		mArray[6][0] = "管理机构姓名";
		mArray[6][1] = "200px";
		mArray[6][2] = 200;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "映射号";
		mArray[7][1] = "50px";
		mArray[7][2] = 200;
		mArray[7][3] = 0;
		
	
		
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
