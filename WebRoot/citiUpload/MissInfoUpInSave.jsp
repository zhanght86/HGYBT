<%@page contentType="text/html;charset=GBK"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.citi.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.*"%>
  
<%	
	//�������
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
			Content = Content + "�����ϴ�����ȱʧ��Ϣ! <br />";
		}
		if(tFundPriceConfirm.iSuccNo != 0){
			Content = Content + "�ɹ�����"
			+ tFundPriceConfirm.iSuccNo + "������ȱʧ��Ϣ! <br />";
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
