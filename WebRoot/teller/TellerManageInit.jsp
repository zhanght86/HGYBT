<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.schema.LDComSchema"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>

<%
	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
	if (null == cGlobalInput) {
		cGlobalInput = new GlobalInput();
	}

	String sArrayName1 = "柜员号";	 
	String sArrayName2 = "柜员姓名";	
	String sArrayName3 = "所属银行";	
	String sArrayName4 = "所属地区";
	String sArrayName5 = "柜员资格证类型";
	String sArrayName6 = "柜员资格证号";	
	String sArrayName7 = "资格证生效日期";	
	String sArrayName8 = "资格证失效日期";		
	String sArrayName9 = "银行代码";	
	String sArrayName10 = "地区代码";
	String sArrayName11 = "SRS柜员编号";



	String sArrayLength1 = "100"; 
	String sArrayLength2 = "50"; 
	String sArrayLength3 = "100"; 
	String sArrayLength4 = "40"; 
	String sArrayLength5 = "60"; 
	String sArrayLength6 = "100";
	String sArrayLength7 = "80";
	String sArrayLength8 = "80";  
	String sArrayLength9 = "0"; 
	String sArrayLength10 = "0";
	String sArrayLength11 = "0";

	//-----用户权限------
	String sManageCom = cGlobalInput.ManageCom;
	String sAreaNo = "";
	String sAreaName = "";
	String sCityNo = "";
	String sCityName = "";
	if (!"86".equals(sManageCom)) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(sManageCom);
		if (!tLDComDB.getInfo()) {
			System.out.println("该用户所属管理机构不存在,请确认!");
		}
	}
	//-----用户权限------
%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>"; //全局变量，记录登陆管理机构
var cOperator = "<%=cGlobalInput.Operator%>"; //全局变量，记录登录用户
var cManageComName="<%=cGlobalInput.ManageComName%>";

function initOK() {
	fm.OperType.value = ""; 
	fm.TranCom.value = "";
	fm.TranComName.value = "";
	fm.ZoneNo.value = ""; 
	fm.ZoneName.value = "";
	fm.TellerName.value = "";
	fm.TellerIDNo.value = "";
    fm.SRSTellerNo.value="";
     
	fm.iTranCom.value = "";
	fm.iTranComName.value = "";
	fm.iZoneNo.value = ""; 
	fm.iZoneName.value = "";
	fm.iTellerName.value = "";
	fm.iTellerIDNo.value = "";
	fm.iQuType.value = "";
	fm.iTellerQuNo.value = "";
	fm.iStartDay.value = "";
	fm.iEndDay.value = "";
	

}

function initForm() {
	initGrid();
	//initUserAuthority();
}

function resetInit() {
	fm.OperType.value = ""; 
	fm.TranCom.value = "";
	fm.TranComName.value = "";
	fm.ZoneNo.value = ""; 
	fm.ZoneName.value = "";
	fm.TellerName.value = "";
	fm.TellerIDNo.value = "";
    fm.SRSTellerNo.value="";
     
	fm.iTranCom.value = "";
	fm.iTranComName.value = "";
	fm.iZoneNo.value = ""; 
	fm.iZoneName.value = "";
	fm.iTellerName.value = "";
	fm.iTellerIDNo.value = "";
	fm.iQuType.value = "";
	fm.iTellerQuNo.value = "";
	fm.iStartDay.value = "";
	fm.iEndDay.value = "";
	initGrid();
}

function initGrid() {
	var mArray = new Array();

	try {
		mArray[0] = new Array();
		mArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
		mArray[0][1] = "30px"; //列宽
		mArray[0][2] = 10; //列最大值
		mArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

		mArray[1] = new Array();
		mArray[1][0] = "<%=sArrayName1%>";
		mArray[1][1] = "<%=sArrayLength1%>";
		mArray[1][2] = 30;
		mArray[1][3] = 0;

		mArray[2] = new Array();
		mArray[2][0] = "<%=sArrayName2%>";
		mArray[2][1] = "<%=sArrayLength2%>";
		mArray[2][2] = 30;
		mArray[2][3] = 0;

		mArray[3] = new Array();
		mArray[3][0] = "<%=sArrayName3%>";
		mArray[3][1] = "<%=sArrayLength3%>";
		mArray[3][2] = 30;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "<%=sArrayName4%>";
		mArray[4][1] = "<%=sArrayLength4%>";
		mArray[4][2] = 30;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "<%=sArrayName5%>";
		mArray[5][1] = "<%=sArrayLength5%>";
		mArray[5][2] = 100;
		mArray[5][3] = 0;

		mArray[6] = new Array();
		mArray[6][0] = "<%=sArrayName6%>";
		mArray[6][1] = "<%=sArrayLength6%>";
		mArray[6][2] = 100;
		mArray[6][3] = 0;
 
        mArray[7] = new Array();
		mArray[7][0] = "<%=sArrayName7%>";
		mArray[7][1] = "<%=sArrayLength7%>";
		mArray[7][2] = 0;
		mArray[7][3] = 0;
		
		 mArray[8] = new Array();
		mArray[8][0] = "<%=sArrayName8%>";
		mArray[8][1] = "<%=sArrayLength8%>";
		mArray[8][2] = 0;
		mArray[8][3] = 0;
		
		 mArray[9] = new Array();
		mArray[9][0] = "<%=sArrayName9%>";
		mArray[9][1] = "<%=sArrayLength9%>";
		mArray[9][2] = 0;
		mArray[9][3] = 0;
		
		 mArray[10] = new Array();
		mArray[10][0] = "<%=sArrayName10%>";
		mArray[10][1] = "<%=sArrayLength10%>";
		mArray[10][2] = 0;
		mArray[10][3] = 0;
		
		 mArray[11] = new Array();
		mArray[11][0] = "<%=sArrayName11%>";
		mArray[11][1] = "<%=sArrayLength11%>";
		mArray[11][2] = 0;
		mArray[11][3] = 0;
		
		
		QueryGrid = new MulLineEnter("fm", "QueryGrid");
		QueryGrid.displayTitle = 1;
		QueryGrid.locked = 0;
		QueryGrid.mulLineCount = 0;
		QueryGrid.hiddenPlus = 1;
		QueryGrid.hiddenSubtraction = 1; //是否显示减号  1为不出现  2为出现
		QueryGrid.canSel = 1; //是否显示单选框  0为不出现 1为出现
		QueryGrid.loadMulLine(mArray);
	} catch (ex) {
		alert(ex);
	}
}
</script>