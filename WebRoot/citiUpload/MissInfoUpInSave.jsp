<%@page contentType="text/html;charset=GBK"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.citi.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
  
<%	
	//输出参数
	CErrors tError = null;
	String tRela = "";
	String FlagStr = "Fail";
	String Content = "";
	String Result = "";
	String tBatchNo = "";
	String flag = request.getParameter("flag");
//	System.out.println(flag+"------------------------------");
	boolean res = false;	
	MissInfoConfirm tFundPriceConfirm = new MissInfoConfirm();
		
	String errMess = "";
	res = tFundPriceConfirm.submitData();
	if (res) {
		Content = "";
		if(tFundPriceConfirm.iSuccNo == 0){
			Content = Content + "请先上传保单缺失信息! <br />";
		}
		if(tFundPriceConfirm.iSuccNo != 0){
			Content = Content + "成功更新"
			+ tFundPriceConfirm.iSuccNo + "条保单缺失信息! <br />";
		}
		
		if (tFundPriceConfirm.mErrors.needDealError()) {
			Content = Content + ""
					+ tFundPriceConfirm.mErrors.getLastError();
		}
		
		FlagStr = "UPSucc";
		System.out.println("---aaa");
	} else {
		tError = tFundPriceConfirm.mErrors;
		errMess = tError.getLastError();
		System.out.println("---ccc");
		Content = errMess;
		FlagStr = "Fail";
	}
%>
<script language="javascript">
parent.fraInterface.afterSubmitIn("<%=FlagStr%>", "<%=Content%>", "",
	"<%=tBatchNo%>");
</script>
