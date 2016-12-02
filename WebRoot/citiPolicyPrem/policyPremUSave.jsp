<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.InterFace.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.citi.*"%>

<%
	//准备通用参数
	CError cError = new CError();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	if (tG == null) {
		System.out.println("登录信息没有获取!!!");
		return;
	}

	//准备数据容器信息
	TransferData tTransferData = new TransferData();
	
	String policyNo = request.getParameter("PolicyNo");
	String costAmount = request.getParameter("CostAmount");
	String premType = request.getParameter("PremType");
	String premName = request.getParameter("PremName");
	String policyYear = request.getParameter("PolicyYear");
	String productType = request.getParameter("ProductType");
	String productName = request.getParameter("ProductName");
	String tranDay = request.getParameter("TranDay");

	
	tTransferData.setNameAndValue("PolicyNo", policyNo);
	tTransferData.setNameAndValue("CostAmount", costAmount);
	tTransferData.setNameAndValue("PremType", premType);
	tTransferData.setNameAndValue("PremName", premName);
	tTransferData.setNameAndValue("PolicyYear", policyYear);
	tTransferData.setNameAndValue("ProductType", productType);
	tTransferData.setNameAndValue("ProductName", productName);
	tTransferData.setNameAndValue("TranDay", tranDay);

	VData tVData = new VData();
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	String Content = "";
	String FlagStr = "";
	PolicyPremU pp= new PolicyPremU(tVData);
	try{
		pp.submitData();
		FlagStr = "Succ";		
		Content = "调整成功";
		}catch(Exception e){
		FlagStr = "Fail";
		Content = pp.mErrors.getFirstError().toString();
		//Content = e.getMessage().toString();
		//Content = "调整失败";
		} 
	//if (!pp.submitData()) {
	//	FlagStr = "Fail";
	//	Content = pp.mErrors.getLastError().toString();
	//} else {
		
		//Content.replaceAll("\\","/");
		//System.out.println(Content);
	//}
%>
<html>
	<script language=jscript.encode>
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>




