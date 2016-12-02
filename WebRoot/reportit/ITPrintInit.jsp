<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
	//程序名称：ITPrintInit.jsp
	//程序功能：保单使用情况报表
	//创建日期：2011-07-26
	//创建人  ：ZHANGHJ
	//更新记录：  更新人    更新日期     更新原因/内容
	
%>
<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}
String sArrayName1 = "保单号";			
String sArrayName2 = "保单号状态";
String sArrayName3 = "城市代码";
String sArrayName4 = "系统代码"; 


String sArrayLength1 = "70"; 
String sArrayLength2 = "60"; 
String sArrayLength3 = "60"; 
String sArrayLength4 = "60"; 

System.out.println("ITPrintInput AgentCom:" + cGlobalInput.AgentCom);
System.out.println("ITPrintInput ComCode:" + cGlobalInput.ComCode);
System.out.println("ITPrintInput ManageCom:" + cGlobalInput.ManageCom);
System.out.println("ITPrintInput Operator:" + cGlobalInput.Operator);
%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
var cManageCom = "<%=cGlobalInput.ManageCom%>"; //全局变量，记录登陆管理机构
var cOperator = "<%=cGlobalInput.Operator%>"; //全局变量，记录登录用户
var cManageComName="<%=cGlobalInput.ManageComName%>";



function initForm() {
    fm.SysFlag.value="";
    fm.SysFlagName.value="***所有系统***";
   // alert(fm.all('FileName').value);
   // var obj = document.getElementById('fm').FileName[1];
    //obj.outerHTML = obj.outerHTML;
     fm.ImportPath.value="";
     document.getElementById('fm').FileName.value="";
   // fm.FileName.value="";
    fm.AreaNo.value="";
    fm.AreaName.value=""; 
    fm.CityNo.value=""; 
    fm.CityName.value="";
    
    fm.ManageCodeName.value=cManageCom+cManageComName;
	fm.ManageCodeNo.value=cManageCom;
	fm.ManageCodeNoOut.value=cManageCom;
	fm.ManageForCheckName.value=cManageCom+cManageComName;
	fm.ManageForCheck.value=cManageCom;
	initBox();
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



function initBox() {
	try {
		fm.all("AreaNo").value = "";
		fm.all("CityNo").value = "";
		fm.all("SysFlag").value = "";
	} catch (re) {
		alert("在ITPrintInit.jsp --> initBox 函数中发生异常:初始化界面错误！");
	}
}
</script>
