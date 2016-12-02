<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>
<%
	//程序名称：NoRealLOPrintInit.jsp
	//程序功能：银保通每日实时数据导出表
	//创建日期：2011-07-26
	//创建人  ：ZHANGHJ
	//更新记录：  更新人    更新日期     更新原因/内容

	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
	if (null == cGlobalInput) {
		cGlobalInput = new GlobalInput();
	}
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
	String sDay = DateUtil.getCur10Date();
%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//全局变量，记录登陆管理机构
var cOperator = "<%=cGlobalInput.Operator%>";	//全局变量，记录登录用户
var cManageComName="<%=cGlobalInput.ManageComName%>";
function initUserAuthority() {
	if(cManageCom.length == 2){
		fm.UserAuthority.value = 1;
		document.getElementById("AreaNoId").readOnly=false;
		document.getElementById("CityNoId").readOnly=false;
	}
	if(cManageCom.length >= 4){
		fm.UserAuthority.value = 2;
		fm.AreaNo.value = "<%=sAreaNo%>";
		fm.AreaName.value = "<%=sAreaName%>";
		document.getElementById("AreaNoId").readOnly=true;
		document.getElementById("CityNoId").readOnly=false;
	}
	if(cManageCom.length >=7){
		fm.UserAuthority.value = 4;
		fm.CityNo.value = "<%=sCityNo%>";
		fm.CityName.value = "<%=sCityName%>";
		document.getElementById("AreaNoId").readOnly=true;
		document.getElementById("CityNoId").readOnly=true;

	}
}

function initForm() {
    fm.ManageCodeName.value=cManageCom+cManageComName;
	fm.ManageCodeNo.value=cManageCom;
	fm.all("Day").value = "<%=sDay%>";
	initUserAuthority();
}



function initBox() {
	try {
		fm.all("TranCom").value = "";
		fm.all("TranComName").value = "***所有渠道***";
		fm.all("AreaNo").value = "";
		fm.all("AreaName").value = "***所有区域***";
		fm.all("CityNo").value = "";
		fm.all("CityName").value = "***所有城市***";
		fm.all("AgentComNo").value = "";
		fm.all("AgentComName").value = "***所有网点***";
		fm.all("AgentCodeNo").value = "";
		fm.all("AgentCodeName").value = "***所有专员***";
		fm.all("RiskCodeNo").value = "";
		fm.all("RiskCodeName").value = "***所有险种***";
		fm.all("Day").value = "<%=sDay%>";
		initUserAuthority();
	} catch (re) {
		alert("在NoRealLOPrintInit.jsp --> initBox 函数中发生异常:初始化界面错误！");
	}
}
</script>
