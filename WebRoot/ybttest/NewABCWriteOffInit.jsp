<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//程序名称：NewABCWriteOffInit.jsp
	//程序功能：   
	//创建日期：2014-08-20 12:43:36
	//创建人  ：李路
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
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
		alert("NewABCWriteOffInit.jsp-->InitForm 函数中发生异常:初始化界面错误!");
	}
}

function initBox()
{
try { 
//交易信息
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//地区代码
	fm.RegionCode.value = '11';
	//网点代码
	fm.Branch.value = '2401';
	//柜员代码
	fm.Teller.value = '';
	//保单号
	fm.PolNumber.value = '';
 	//单证印刷号 
	fm.ProviderFormNumber.value = strProviderFormNumber;
 	
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="";
	document.getElementById("divDateimg").src="../common/images/butExpand.gif";
	//试算结果查询
	document.getElementById("divApplyNo").style.display="none";
	document.getElementById("divApplyNoimg").src="../common/images/butExpand.gif";
	//重空核对信息
	document.getElementById("divCheck").style.display="none";
	document.getElementById("divCheckimg").src="../common/images/butCollapse.gif";
	//退保信息传递
	document.getElementById("divCancelTranfor").style.display="none";
	document.getElementById("divCancelTranforimg").src="../common/images/butCollapse.gif";
	//签约解约交易
	document.getElementById("divSignal").style.display="none";
	document.getElementById("divSignalimg").src="../common/images/butCollapse.gif";
//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = '1000'; 
	}
	catch(re)
	{
		alert("NewABCWriteOffInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initInputBox() 
{
	try {
//交易信息
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//地区代码 
	fm.RegionCode.value = '<%=tZoneNo%>';
	//网点代码  
	fm.Branch.value = '<%=tNodeNo%>';
	//柜员代码
	fm.Teller.value = '00001';
	//保单印刷号 
	fm.PolNumber.value = '';
	//单证印刷号 
	fm.ProviderFormNumber.value = '';
	
	document.getElementById("divTranInfo").style.display="";
	document.getElementById("divTranInfoimg").src="../common/images/butExpand.gif";
	//保单信息
	document.getElementById("divContInfo").style.display="none";
	document.getElementById("divContInfoimg").src="../common/images/butCollapse.gif";
	//绿灯交易信息
	document.getElementById("divDate").style.display="";
	document.getElementById("divDateimg").src="../common/images/butExpand.gif";
	//试算结果查询
	document.getElementById("divApplyNo").style.display="none";
	document.getElementById("divApplyNoimg").src="../common/images/butCollapse.gif";

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = '1000';
	}
	catch(re)
	{
		alert("NewABCWriteOffInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}


</script>
