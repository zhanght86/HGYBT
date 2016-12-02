<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LDMenuGrpSchema"%>
<%@page import="com.sinosoft.lis.menumang.LDMenuGrpNewUI"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
		String FlagStr = "Fail";
		//选中的菜单组编码
		String mMenuGrpCode = request.getParameter("Action");

    //String UserCode = request.getParameter("UserCode");		
    //准备菜单组数据  	
		String MenuGrpCode = request.getParameter("MenuGrpCode");   //录入的菜单组编码
		String MenuGrpName = request.getParameter("MenuGrpName");
		String MenuGrpDescription = request.getParameter("MenuGrpDescription");
		String MenuSign = request.getParameter("MenuSign");
		String Operator = request.getParameter("Operator");
		//String Operator = tG1.Operator;
		String curDate = PubFun.getCurrentDate();
		String curTime = PubFun.getCurrentTime();

		//String menuSetString = request.getParameter("hideString");
		//String removeSetString = request.getParameter("hideRemoveString");

		LDMenuGrpSchema tGrpSchema = new LDMenuGrpSchema();
		tGrpSchema.setMenuGrpCode(MenuGrpCode);
		tGrpSchema.setMenuGrpName(MenuGrpName);
		tGrpSchema.setMenuGrpDescription(MenuGrpDescription);
		tGrpSchema.setMenuSign(MenuSign);
		tGrpSchema.setOperator(Operator);
		tGrpSchema.setMakeTime(curTime); 
		tGrpSchema.setMakeDate(curDate); 
		
		LDMenuGrpNewUI tLDMenuGrpNewUI = new LDMenuGrpNewUI();
		VData tData = new VData();
		//tData.add(menuSetString);
		//tData.add(removeSetString);
		tData.add(tGrpSchema);
		tData.add(mMenuGrpCode);
		if (tLDMenuGrpNewUI.submitData(tData))
			FlagStr = "success";
		else
			FlagStr = "fail";
%>
<script>
parent.fraInterface.afterSubmit("<%=FlagStr%>");
</script>