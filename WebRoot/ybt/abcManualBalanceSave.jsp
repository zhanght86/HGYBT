<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%@page import="java.util.HashMap"%>

<%@page import="org.apache.log4j.Logger"%>
<%@page import="org.jdom.Element"%>
<%@page import="org.jdom.xpath.XPath"%>

<%@page import="com.sinosoft.midplat.bat.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>

<%
	Logger cLogger = Logger.getLogger(getClass());
	cLogger.info("into ManualBalanceSave.jsp...");
	
	GlobalInput cGlobalInput = (GlobalInput) session.getAttribute("GI");
	  
	String mTranCom = request.getParameter("TranCom");
	String mFuncFlag = request.getParameter("FuncFlag");
	String mTranDate = DateUtil.date10to8(request.getParameter("TranDate"));
	
	String mClassName = XPath.newInstance(
		"//batch[com='"+mTranCom+"' and funcFlag='"+mFuncFlag+"']/class").valueOf(
			BatConf.newInstance().getConf());
	cLogger.info(mClassName + ": " + mTranCom + "-" + mFuncFlag + "-" + mTranDate);
	
	String mResultMsg = null;
	if (null==mClassName || "".equals(mClassName)) {
		mResultMsg = "暂不支持该银行！";
	} else {
		Balance tBalance = (Balance)Class.forName(mClassName).newInstance();
		tBalance.setDate(mTranDate);
		tBalance.run();
		mResultMsg = tBalance.getResultMsg();
	}
	
	cLogger.info("out ManualBalanceSave.jsp...");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=mResultMsg%>");
</script>
</html>