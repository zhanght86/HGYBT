<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LDMenuGrpSchema"%>
<%@page import="com.sinosoft.lis.menumang.LDMenuGrpUI"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
//System.out.println("start jsp");
String FlagStr = "Fail";
String actionStr = request.getParameter("Action");
System.out.println("actionStr:" + actionStr);
int action = -1;
if (actionStr.compareTo("query") == 0)
	action = 2;
if (actionStr.compareTo("insert") == 0)
	action = 0;
if (actionStr.compareTo("update") == 0)
	action = 1;
if (actionStr.compareTo("delete") == 0)
	action = 3;
//System.out.println("action num: " + action);

switch (action)
{
	case 1:
	
	case 0:  
		//insert
		String Operator = request.getParameter("Operator");
		String MenuGrpCode = request.getParameter("MenuGrpCode");
		String MenuGrpName = request.getParameter("MenuGrpName");
		String MenuSign = request.getParameter("MenuSign");
		String MenuGrpDescription = request.getParameter("MenuGrpDescription");

//		System.out.println("MenuGrpCode:" + MenuGrpCode);
//		System.out.println("MenuGrpDescription:" + MenuGrpCode);
//		System.out.println("Operator:" + Operator);

		String menuSetString = request.getParameter("hideString");
		System.out.println("menuSetString : " + menuSetString);

		String removeSetString = request.getParameter("hideRemoveString");
		System.out.println("hideRemoveString is " + removeSetString);

		LDMenuGrpSchema tGrpSchema = new LDMenuGrpSchema();
		tGrpSchema.setMenuGrpCode(StrTool.unicodeToGBK(MenuGrpCode));
		tGrpSchema.setMenuGrpName(StrTool.unicodeToGBK(MenuGrpName));
		tGrpSchema.setMenuGrpDescription(StrTool.unicodeToGBK(MenuGrpDescription));
		tGrpSchema.setMenuSign(StrTool.unicodeToGBK(MenuSign));
		tGrpSchema.setOperator(StrTool.unicodeToGBK(Operator));
	//	if (action == 0)
	//	{
			String curDate = PubFun.getCurrentDate();
			String curTime = PubFun.getCurrentTime();
			tGrpSchema.setMakeTime(curTime);
			tGrpSchema.setMakeDate(curDate);
	//	}  

		LDMenuGrpUI tLDMenuGrpUI = new LDMenuGrpUI();
		VData tData = new VData();
		tData.add(menuSetString);
		tData.add(removeSetString);
		tData.add(tGrpSchema);
		if (tLDMenuGrpUI.submitData(tData,actionStr))
			FlagStr = "success";
		else
			FlagStr = "fail";
//		System.out.println("FlagStr : " + FlagStr);
		break;

	case 2:
		FlagStr = "success";
		break;

	case 3:
//		System.out.println("here");
		tGrpSchema = new LDMenuGrpSchema();
		String tRadio[] = request.getParameterValues("InpQueryGrpGridSel");

//		if (tRadio == null)
//			System.out.println("tRadio is null");

		if (tRadio != null)
		{
//			System.out.println("tRadio is not null");
			int index = 0;
			for (; index< tRadio.length;index++)
			{
				if(tRadio[index].equals("1"))
					break;
			}
			if (index == tRadio.length)
			{
				System.out.println("没有选中对象!");
			}
			else{
				String tMenuGrpName[] = request.getParameterValues("QueryGrpGrid1");
				String tMenuGrpCode[] = request.getParameterValues("QueryGrpGrid2");
				String tMenuSign[] = request.getParameterValues("QueryGrpGrid4");
				String tMenuGrpDescription[] = request.getParameterValues("QueryGrpGrid3");
				tGrpSchema.setMenuGrpCode(tMenuGrpCode[index]);
				tGrpSchema.setMenuGrpName(StrTool.unicodeToGBK(tMenuGrpName[index]));
				tGrpSchema.setMenuGrpDescription(StrTool.unicodeToGBK(tMenuGrpDescription[index]));
				tGrpSchema.setMenuSign(tMenuSign[index]);
			}
			tLDMenuGrpUI = new LDMenuGrpUI();
			tData = new VData();
			tData.add(tGrpSchema);
			if (tLDMenuGrpUI.submitData(tData,actionStr))
				FlagStr = "success";
			else
				FlagStr = "fail";
		}
		break;
}
//System.out.println(FlagStr);
//System.out.println("end of jsp");
%>
<script>
parent.fraInterface.afterSubmit("<%=FlagStr%>");
</script>