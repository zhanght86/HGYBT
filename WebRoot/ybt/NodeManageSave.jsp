<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.exception.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.manage.NodeManageUI"%>
   
<%
Logger cLogger = Logger.getLogger(getClass());
cLogger.info("into NodeManageSave.jsp...");

GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");

//操作类型(INSERT/UPDATE/DELETE)
String mOperType = request.getParameter("OperType").trim();
String mNodeType = request.getParameter("NodeType").trim();
String mTranCom = request.getParameter("TranCom").trim();
String mNodeNo = request.getParameter("NodeNo").trim();
String mAgentCom = request.getParameter("AgentCom").trim();
String mAgentComName = request.getParameter("AgentComName").trim();
String mAgentCode = request.getParameter("AgentCode").trim();
String mAgentName = request.getParameter("AgentName").trim();
if("".equals(mAgentCode) || mAgentCode==null){
mAgentCode="-";
} 
if("".equals(mAgentName) || mAgentName==null){
mAgentName="-";  
}
String mManageCom = request.getParameter("ManageCom").trim();
String mManageComName = request.getParameter("ManageComName").trim();
String mMapNo = request.getParameter("MapNo").trim();
System.out.println("mAgentCom:"+mAgentCom+"mAgentComName:"+mAgentComName+"mAgentCode:"+mAgentCode+"mAgentName:"+mAgentName+"mManageCom:"+mManageCom+"mManageComName:"+mManageComName);
String mFlag = null;
String mMessage = null;


try {
	NodeManageUI tNodeManageUI = new NodeManageUI(cGlobalInput);
	if ("INSERT".equals(mOperType)) {
		tNodeManageUI.insertNode(
			mNodeType
			, mTranCom, mNodeNo
			, mAgentCom, mAgentComName, mAgentCode, mAgentName, mManageCom);
	} else if ("UPDATE".equals(mOperType)) {
		tNodeManageUI.updateNode(
			mMapNo
			, mTranCom, mNodeNo
			, mAgentCom, mAgentComName, mAgentCode, mAgentName, mManageCom);
	} else if ("DELETE".equals(mOperType)) {
		tNodeManageUI.deleteNode(mMapNo);
	} else {
		throw new MidplatException("操作码有误！"+mOperType);
	}
	
	mFlag = "Succ";
	mMessage = "操作成功！";
} catch (Exception ex) {
	System.out.println("操作网点页面时" + ex.getMessage());
	cLogger.info("操作网点页面时" + ex.getMessage());
	mFlag = "Fail";
	mMessage = "操作失败：" + ex.getMessage();
} 

cLogger.info("out NodeManageSave.jsp!");
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=mFlag%>", "<%=mMessage%>");
</script>
</html>