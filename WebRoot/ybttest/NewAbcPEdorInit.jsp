<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//程序名称：XBTContInit.jsp
	
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = PubFun1.CreateMaxNo("TransNo",16);
	String strProposalContNo = "2104141"+PubFun1.CreateMaxNo("ProNo",8);
	Element mTestUI = 
		 MidplatConf.newInstance().getConf().getRootElement().getChild("NewABCTestUI");
	 String tIp = "127.0.0.1";
	 String tPort = "35006";
	 String tZoneNo = "11";
	 String tNodeNo = "2401";
%>
<script language="JavaScript">
function initForm()
{
	try 
	{
		initInputBox();
	}
	catch(re)
	{
		alert("NewAbcContInit.jsp-->InitForm11 函数中发生异常:初始化界面错误!");
	}
}



function initInputBox() 
{
	try {
	fm.TranCom.value='AGC';
	fm.TranComName.value='中国农业银行';
	fm.SERIALNO.value='<%=strTransRefGUID%>';
	fm.TRANSDATE.value='<%=strTransExeDate%>';
	fm.PROVCODE.value='11';
	fm.BRANCHNO.value='2401';
	fm.TLID.value='0005';


	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>'; 
	fm.TRANSCODE.value = '1012'; 
	fm.IdKind.value='110023';
	fm.PayeeIdKind.value='110005';

	}
	catch(re)
	{
		alert("NewAbcContInit.jsp-->initInputBox22 函数中发生异常:初始化界面错误!");
	}
}


</script>
