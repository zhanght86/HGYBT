<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>
<%	
	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
	if (null == cGlobalInput) {
		cGlobalInput = new GlobalInput();
	}
	//-----用户权限------
	String sManageCom = cGlobalInput.ManageCom;
	if (!"86".equals(sManageCom)) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(sManageCom);
		if (!tLDComDB.getInfo()) {
			System.out.println("该用户所属管理机构不存在,请确认!");
		}
	}	
	String sDay = DateUtil.getCur10Date();
%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//全局变量，记录登陆管理机构
var cOperator = "<%=cGlobalInput.Operator%>";	//全局变量，记录登录用户
function initForm() {
	initBox();
}

function initBox() {
	try {
		fm.all("StartDay").value = "<%=sDay%>";
		fm.all("EndDay").value = "<%=sDay%>";
	} catch (re) { 
		alert("在policyFundTDInit.jsp --> initBox 函数中发生异常:初始化界面错误！");
	}
}
</script>
