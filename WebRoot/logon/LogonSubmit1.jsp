<style type="text/css">
<!--
.style4 {color: #FF0000}
.style5 {
	font-weight: bold;
	font-size: 36px;
	color: #CC3333;
	font-family: "方正舒体", "华文隶书", "隶书";
}
-->
</style>

<%@ page contentType="text/html;charset=GBK" %>
<%
//******************************************************
// 程序名称：LogonSubmit.jsp
// 程序功能:：处理用户登录提交
// 最近更新人：DingZhong
// 最近更新日期：2002-10-15
//******************************************************//
%>
<%@page import="com.sinosoft.lis.db.LDUserLogDB"%>
<%@page import="com.sinosoft.lis.encrypt.LisIDEA"%>
<%@page import="com.sinosoft.lis.logon.logoutUI"%>
<%@page import="com.sinosoft.lis.menumang.LDUserAgentUI"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun1"%>
<%@page import="com.sinosoft.lis.schema.LDUserSchema"%>
<%@page import="com.sinosoft.lis.vschema.LDUserSet"%>
<%@page import="com.sinosoft.utility.VData"%>
<%
boolean bSuccess = false;
//用户名称和密码
String UserCode = request.getParameter("UserCode");

if( UserCode != null && !UserCode.equals("") )
{
  UserCode = UserCode.trim();
}
String Password = request.getParameter("PWD");
LisIDEA tIdea = new LisIDEA();
Password = tIdea.encryptString(Password);
//String StationCode = request.getParameter("StationCode");
String ClientURL = request.getParameter("ClientURL");	//LQ 2004-04-19
//System.out.println("Password:" + Password + "1");

//用户IP
String Ip = request.getRemoteAddr();
//System.out.println("++++++++++++++++++IP :" + Ip);

//增加校验码校验 2006-12-11 zhoulei
String rand = (String)session.getAttribute("NumCode");
String input = request.getParameter("NumCode");
if (!rand.equals(input))
{
%>
<script language=javascript>
alert("验证码输入不正确!");
parent.window.location = "../broker/indexncl.jsp";
</script>
<%
}

String ls;	//返回的字符串
VData tVData = new VData();
LDUserAgentUI tLDUserAgentUI = new LDUserAgentUI();

if (Password.length() == 0 || UserCode.length() == 0)
{
	bSuccess = false;
}
else
{
	LDUserSchema tLDUserSchema = new LDUserSchema();
	tLDUserSchema.setUserCode(UserCode);
	tLDUserSchema.setPassword(Password);
//	tLDUserSchema.setComCode(StationCode);
	tVData.add(tLDUserSchema);
	bSuccess=tLDUserAgentUI.submitData(tVData,"query");
}
String title = UserCode + "您好，欢迎登录本系统！";
GlobalInput tG = new GlobalInput();
tG.Operator = UserCode;

if(bSuccess == true)
{
    //取出兼业管理机构和兼业代理机构
    tVData.clear();
    tVData = tLDUserAgentUI.getResult();
    LDUserSet tLDUserSet = (LDUserSet) tVData.getObjectByObjectName("LDUserSet", 0);
    if (tLDUserSet.size() < 1)
    {
	    session.putValue("GI",null);
%>
<script language=javascript>
alert("用户名/密码输入不正确!");
parent.window.location = "../broker/indexncl.jsp";
</script>
<%
    }
    tG.AgentCom  = tLDUserSet.get(1).getAgentCom();
    tG.ManageCom = tLDUserSet.get(1).getComCode();
    tG.ComCode = tG.ManageCom;
    
    session.putValue("GI",tG);
    session.putValue("ClientURL",ClientURL);	//LQ 2004-04-19

    GlobalInput tG1 = new GlobalInput();
    tG1=(GlobalInput)session.getValue("GI");
//    System.out.println("Current getAgentCom is "+tG1.AgentCom);
//    System.out.println("Current getManageCom is "+tG1.ManageCom);
//    System.out.println("Current getComCode is "+tG1.ComCode);
    
	//register info into lduserlog
	LDUserLogDB tLDUserLogDB = new LDUserLogDB();
	String maxno = PubFun.getSeqNo("SEQ_LOGNO");
	if("-1".equals(maxno))
	{
		session.putValue("GI",null);
	}
	tLDUserLogDB.setLogNo(maxno);
	tLDUserLogDB.setManageCom(tLDUserSet.get(1).getComCode());
	tLDUserLogDB.setOperator(UserCode);
	tLDUserLogDB.setCientIP(Ip);
	tLDUserLogDB.setMakeDate(PubFun.getCurrentDate());
	tLDUserLogDB.setMakeTime(PubFun.getCurrentTime());
	tLDUserLogDB.insert();

	//进行解锁操作
    //System.out.println("start unlock operate...");
	//VData inputData = new VData();
	//inputData.addElement(tG1);
	//logoutUI tlogoutUI = new logoutUI();
	//tlogoutUI.submitData(inputData,"LogOutProcess");
	//System.out.println("completed clear data");
%>
<script language=javascript>
if(parent.fraMain.rows == "0,0,0,0,*")
	parent.document.frames('fraTitle').showTitle();
if(parent.fraSet.cols==	"0%,*,0%")
	parent.document.frames('fraTitle').showHideFrame();
parent.fraMenu.window.location="./menu.jsp?userCode=<%=UserCode%>&Ip=<%=Ip%>";
</script>
<%
}
else
{
	session.putValue("GI",null);
%>
<script language=javascript>
alert("用户名/密码输入不正确!");
parent.window.location = "../broker/indexncl.jsp";
</script>
<%
}
%>

<script src="../common/javascript/Common.js"></script>
<script src="../common/cvar/CCodeOperate.js"></script>
<script src="../common/javascript/EasyQuery.js"></script>
<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>

<body onload="initName()">

	<div>
		<center><iframe src="../broker/whatsnewforbroker.xml" frameborder="no" scrolling="no" height="90%" width="100%"></iframe></center>
	</div>

<form name="fm">
	<input type="hidden" name="VirtualCode" value="0" />
	<input type="hidden" name="VirtualName" value="" />
	
</form>
</body>

<script language="javascript">
	function initName()
	{
//		showOneCodeName('province','VirtualCode','VirtualName');
//		showOneCodeName('city','VirtualCode','VirtualName');
//		showOneCodeName('district','VirtualCode','VirtualName');
//		showOneCodeName('incomeway','VirtualCode','VirtualName');
//		showOneCodeName('comcode','VirtualCode','VirtualName');
//		showOneCodeName('sellType','VirtualCode','VirtualName');
//		showOneCodeName('vipvalue','VirtualCode','VirtualName');
//		showOneCodeName('IDType','VirtualCode','VirtualName');
//		showOneCodeName('Sex','VirtualCode','VirtualName');
//		showOneCodeName('Marriage','VirtualCode','VirtualName');
//		showOneCodeName('NativePlace','VirtualCode','VirtualName');
//		showOneCodeName('OccupationCode','VirtualCode','VirtualName');
//		showOneCodeName('LicenseType','VirtualCode','VirtualName');
//		showOneCodeName('paymode','VirtualCode','VirtualName');
//		showOneCodeName('continuepaymode','VirtualCode','VirtualName');
//		showOneCodeName('bank','VirtualCode','VirtualName');
//		showOneCodeName('SequenceNo','VirtualCode','VirtualName');
//		showOneCodeName('vipvalue','VirtualCode','VirtualName');
//		showOneCodeName('Relation','VirtualCode','VirtualName');
	}
	
</script>
	