<%@page contentType="text/html;charset=GBK"%>
<%
	//�������ƣ�����רԱ�����ύSAVEҳ��
	//�����ܣ�EXCEL�����ύ��AXAAGENT����
	//�������ڣ�2011-09-02
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
	//�������
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
			Content = Content + "�����ϴ�Ͷ���۸���Ϣ! <br />";
		}
		if(tFundPriceConfirm.iSuccNo != 0){
			Content = Content + sDay+"��Ͷ���۸���³ɹ�! <br />";
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
		Content = sDay+"��Ͷ���۸�ȡ�����³ɹ�!";
		
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
