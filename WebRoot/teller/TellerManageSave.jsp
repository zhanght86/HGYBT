<%@page import="com.sinosoft.lis.schema.AxaAgentSchema"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.exception.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.manage.TellerManageUI"%>
<%@page import="com.sinosoft.utility.TransferData"%>

<%

	Logger cLogger = Logger.getLogger(getClass());
	
	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");

	String sOperator = "";
	String sManageCom = "";
	sOperator = cGlobalInput.Operator;
	sManageCom = cGlobalInput.ManageCom;
	

	//操作类型(INSERT/UPDATE/DELETE)
	String mOperType = request.getParameter("OperType");
	String miTranCom = request.getParameter("iTranCom"); 
	String miZoneNo = request.getParameter("iZoneNo");
	String miTellerName= request.getParameter("iTellerName");
	String miTellerIDNo = request.getParameter("iTellerIDNo");
	String miQuType = request.getParameter("iQuType");
	String miTellerQuNo = request.getParameter("iTellerQuNo");
	String miStartDay = request.getParameter("iStartDay");
	String miEndDay = request.getParameter("iEndDay");
	String mSRSTellerNo = request.getParameter("SRSTellerNo");


	System.out.println("mOperType-" + mOperType + " miTranCom-" + miTranCom
			+ " miZoneNo=" + miZoneNo + " miTellerName=" + miTellerName
			+ " miTellerIDNo=" + miTellerIDNo + " miQuType=" + miQuType
			+ " miTellerQuNo=" + miTellerQuNo+ " miStartDay=" + miStartDay+ " miEndDay=" + miEndDay
			+ " mSRSTellerNo=" + mSRSTellerNo);
	
	TransferData vTransferData = new TransferData();
	vTransferData.setNameAndValue("TranCom", miTranCom);
	vTransferData.setNameAndValue("ZoneNo", miZoneNo);
	vTransferData.setNameAndValue("TellerName", miTellerName);
	vTransferData.setNameAndValue("TellerIDNo", miTellerIDNo);
	vTransferData.setNameAndValue("QuType", miQuType);
	vTransferData.setNameAndValue("TellerQuNo", miTellerQuNo);
	vTransferData.setNameAndValue("StartDay", miStartDay);
	vTransferData.setNameAndValue("EndDay", miEndDay);
	vTransferData.setNameAndValue("SRSTellerNo", mSRSTellerNo);


	String mFlag = "";
	String mMessage = "";
	try {
		if (miStartDay.compareTo(miEndDay)>0){
			throw new MidplatException("生效日期不能晚于失效日期!");
		}
		TellerManageUI tTellerManageUI = new TellerManageUI(vTransferData);
		if ("INSERT".equals(mOperType)) {
			tTellerManageUI.insert();
		} else if ("UPDATE".equals(mOperType)) {
			tTellerManageUI.update();
		} else if ("DELETE".equals(mOperType)) {
			tTellerManageUI.delete();
		} else {
			throw new MidplatException("操作码有误！" + mOperType);
		}

		mFlag = "Succ"; 
		mMessage = "操作成功！";
	} catch (Exception ex) {
		cLogger.error("操作失败！", ex);

		mFlag = "Fail";
		mMessage = "操作失败：" + ex.getMessage();
	}

	cLogger.info("out TellerManageSave.jsp!");
%>
<html>
	<script language="javascript">
parent.fraInterface.afterSubmit("<%=mFlag%>", "<%=mMessage%>");
</script>
</html>