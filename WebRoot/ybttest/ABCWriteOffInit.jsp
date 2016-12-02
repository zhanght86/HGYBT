<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//程序名称：TestYBTWriteOffInit.jsp
	//程序功能：会计确认   
	//创建日期：2011-06-14 16:43:36
	//创建人  ：张海军
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
	String strProviderFormNumber = "BP"+PubFun1.CreateMaxNo("ContPrtNo",9);
	Element mTestUI = 
 		 MidplatConf.newInstance().getConf().getRootElement().getChild("TestUI");
 	 String tIp = mTestUI.getAttributeValue("ip");
 	 String tPort = mTestUI.getAttributeValue("port");
  	 String tZoneNo = mTestUI.getAttributeValue("ZoneNo");
  	 String tNodeNo = mTestUI.getAttributeValue("NodeNo");
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
		alert("TestYBTContInit.jsp-->InitForm 函数中发生异常:初始化界面错误!");
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
	fm.RegionCode.value = '';
	//网点代码
	fm.Branch.value = '';
	//柜员代码
	fm.Teller.value = '';
	//保单号
	fm.PolNumber.value = '';
 	//单证印刷号 
	fm.ProviderFormNumber.value = strProviderFormNumber;
	//原单证印刷号 
	//fm.OriginalProviderFormNumber.value = '';  

//
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = '03'; 
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
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
	//地区代码 名称
	//fm.RegionCodeName.value = '上海分公司';
	//网点代码  
	fm.Branch.value = '<%=tNodeNo%>';
	//网点代码 名称 
	//fm.BranchName.value = '测试网点';
	//柜员代码
	fm.Teller.value = '00001';
	
	//保单印刷号 
	fm.PolNumber.value = '';
	//单证印刷号 
	fm.ProviderFormNumber.value = '<%=strProviderFormNumber%>';
	//原单证印刷号 
	//fm.OriginalProviderFormNumber.value = '';
//document.getElementById("IdProviderFormNumber").disabled=true;
//document.getElementById("APPDOCNOId").disabled=true;
	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>';
	fm.tranFlagCode.value = '03';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}


</script>
