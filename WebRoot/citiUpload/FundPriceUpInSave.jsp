<%@page contentType="text/html;charset=GBK"%>
<%
	//程序名称：网点专员批量提交SAVE页面
	//程序功能：EXCEL批量提交到AXAAGENT表中
	//创建日期：2011-09-02
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.citi.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>  
<%	
	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "Fail";
	String Content = "";
	String Result = "";
	String tBatchNo = "";
	String flag = request.getParameter("flag");
	
	String sDay = DateUtil.getCur10Date();
	
	System.out.println(flag+"------------------------------");
	boolean res = false;	
	FundPriceConfirm tFundPriceConfirm = new FundPriceConfirm();
	
	if("submit".equals(flag)){		
	String errMess = "";
	res = tFundPriceConfirm.submitData();
	if (res) {
		Content = "";
		if(tFundPriceConfirm.iSuccNo == 0){
			Content = Content + "请先上传投连价格信息! <br />";
		}
		if(tFundPriceConfirm.iSuccNo != 0){
			Content = Content + sDay+"的投连价格更新成功! <br />";
		}
		
		if (tFundPriceConfirm.mErrors.needDealError()) {
			Content = Content + ""
					+ tFundPriceConfirm.mErrors.getLastError();
		}
		
		FlagStr = "SuccUP";
		System.out.println("---aaa");
	} else {
		tError = tFundPriceConfirm.mErrors;
		errMess = tError.getLastError();
		System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}
	}else if("cancel".equals(flag)){
		String errMess = "";
	res = tFundPriceConfirm.cancelDate();
	if (res) {
		Content = sDay+"的投连价格取消更新成功!";
		
		if (tFundPriceConfirm.mErrors.needDealError()) {
			Content = Content + ""
					+ tFundPriceConfirm.mErrors.getLastError();
		}
		
		FlagStr = "SuccUP";
		System.out.println("---aaa");
	} else {
		tError = tFundPriceConfirm.mErrors;
		errMess = tError.getLastError();
		System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}
	}
%>
<script language="javascript">
parent.fraInterface.afterSubmitIn("<%=FlagStr%>", "<%=Content%>", "",
	"<%=tBatchNo%>");
</script>
