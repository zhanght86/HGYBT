<%@page contentType="text/html;charset=GBK" %>
<!--
*******************************************************
* 程序名称：Menus.jsp
* 程序功能：用户登录信息输入页面
* 创建日期：2002-08-11
* 更新记录：  更新人	更新日期	更新原因/内容
*             朱向峰	2002-08-11	新建
              Dingzhong	2002-10-17	修改
              朱向峰	2004-1-30	修改
*******************************************************
-->
<%@page import="com.sinosoft.utility.StrTool"%>
<%@page import="com.sinosoft.utility.SysConst"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.logon.MenuShow"%>
<%@page import="com.sinosoft.lis.schema.LDUserSchema"%>
<%@page import="com.sinosoft.lis.menumang.LDMenuQueryUI"%>
<script LANGUAGE="JavaScript">
defaultStatus='欢迎使用本系统';
var ls="";
function CreateMenuMain(MNm,No)
{
	var i;
	for(i=1;i<No+1;i++)
	{
		WMnu=MNm+eval(i);
		var y="";	//缩进设置值
		var z=3;	//0基数值，会导致一些设置差异
		var ls_WMnu = WMnu;
		//该用下列循环方式，或许能提高菜单的生成效率
		while(ls_WMnu.indexOf("_")!=-1)
		{
			//设置格式，以"_"划分子菜单
			ls_WMnu=ls_WMnu.slice(ls_WMnu.indexOf("_")+1);
			y += "&nbsp;&nbsp;";
			z += 1;
		}
		var nodecode = eval(WMnu+"[16]");	//节点编码
		var nodesrc = eval(WMnu+"[1]");	//节点连接
		var nodename = eval(WMnu+"[0]");	//节点名
		NOs = eval(WMnu+"[3]");	//节点子节点个数
		if(NOs > 0)
		{
			ls_string = "<table><tr><td class='menu"+z+"' onclick='showMenu("+WMnu+"s);'>"+y+"<img src='../common/images/butCollapse.gif' style='cursor:hand;' id='"+WMnu+"i'><img src='../common/images/menu"+z+".gif'>&nbsp;";
			if(nodesrc!="")
			{
				ls_string += "<a onmousedown=this.className='down' onmouseout='shiftMouseOut()' onmouseover=this.className='over' onclick=changeframe('"+nodecode+"','"+nodesrc+"') style='cursor:hand'>"+nodename+"</a></td></tr></table><div id='"+WMnu+"s' style='display:none'>";
			}
			else
			{
				ls_string += nodename + "</td></tr></table><div id='"+WMnu+"s' style='display:none'>";
			}
			document.write(ls_string);
			ls_string += CreateMenuMain(WMnu+"_",NOs);
			document.write("</div>");
		}
		else
		{
			ls_string = "<table><tr><td class='menu"+z+"'>"+y+"<img src='../common/images/butNull.gif' id='"+WMnu+"i'><img src='../common/images/menu"+z+".gif'>&nbsp;";
			if(nodesrc!="")
			{
				ls_string += "<a onmousedown=this.className='down' onmouseout='shiftMouseOut()' onmouseover=this.className='over' onclick=changeframe('"+nodecode+"','"+nodesrc+"') style='cursor:hand'>"+nodename+"</a></td></tr></table>";
			}
			else
			{
				ls_string += nodename + "</td></tr></table>";
			}
			document.write(ls_string);
		}
	}
	return(ls_string);
	document.close();
}
var MenuHeight=20;
//var MenuHeightChild=20;
//var MenuHeightBottom=20;

//此处用来，当用户离开时销毁该用户在服务器端的状态

function OnCloseWindow()
{
window.open("./close.jsp", "PopupWindow", "top=10000, left=10000, menubar=0, toolbar=0, location=0, directories=0, status=0, scrollbars=0, resizable=0, fullscreen=0");
}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<LINK href="../common/css/otherM.css" rel=stylesheet type=text/css>
<base target="fraInterface">
</head>
<body bgcolor="#EFF1FA" leftmargin="0" topmargin="0" onunload = "OnCloseWindow();">
</body>
<%




//System.out.println("start menu get ...");
int i = 0;
//Vector vector = null;	//装载代码查询结果的对象数组容器
//int intPosition=0;
//int intFieldNum=5;
int[] menuCount = new int[1];
menuCount[0] = 0;
int node_count = 0;
String[][] node = null;	//建立一个二维数组
//String sl = "";
VData tData=new VData();
LDUserSchema tLDUserSchema = new LDUserSchema();
LDMenuQueryUI tLDMenuQueryUI = new LDMenuQueryUI();
MenuShow menuShow = new MenuShow();

String userCode = request.getParameter("userCode");
String Ip = request.getParameter("Ip");
tLDUserSchema.setUserCode(userCode);

if ( userCode.compareTo("001") != 0) {
	tData.add(tLDUserSchema);
}

//调用生成菜单函数
tLDMenuQueryUI.submitData(tData,"query");

StringBuffer menustr = null;
if (tLDMenuQueryUI.mErrors.needDealError())
{
//	System.out.println(tLDMenuQueryUI.mErrors.getFirstError()) ;
	out.println("<script>");

	int totalMenu = 2;
	out.println("var NoOffFirstLineMenus=" + totalMenu + ";");

	menuCount[0]++;
	menustr = new StringBuffer(103);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('重新登录','../logon/logout.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','');");
	out.println(menustr.toString());

	menuCount[0]++;
	menustr = new StringBuffer(114);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('密码修改','../changePwd/PwdInput.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','7777');");
	out.println(menustr.toString());

	out.println("parent.fraQuick.window.location = '../logon/station.jsp';");
	out.println("</script>");
}
else{
//	tData.clear() ;
//	tData = tLDMenuQueryUI.getResult();
	node_count = tLDMenuQueryUI.getResultNum();
	node = new String[node_count][5];

//	String tStr="";
//	tStr = tLDMenuQueryUI.getResultStr();

	StringBuffer tStr = new StringBuffer(tLDMenuQueryUI.getResultStr());
//	sl = tStr;
//	sl += SysConst.RECORDSPLITER;
	tStr.append(SysConst.RECORDSPLITER);

	StringBuffer str = null;
	//根据排序正确的字符串给数组重新赋值
	for (i=0 ; i< node_count ; i++) {
		str = new StringBuffer(256);
		str.append(StrTool.decodeStr(tStr.toString(),SysConst.RECORDSPLITER,i+1));
		for (int j = 0; j < 5; j++){
//			str += "|";
			str.append("|");
			node[i][0] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,1);
			node[i][1] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,2);
			node[i][2] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,3);
			node[i][3] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,4);
			node[i][4] = StrTool.decodeStr(str.toString(),SysConst.PACKAGESPILTER,5);
		}
	}
//	String leafNodeStr = menuShow.getAllLeafNodePath(node,node_count);

	out.println("<script>");
	out.println(menuShow.getMenu(node,0,node_count,menuCount));
	int totalMenu = menuCount[0] + 2;
//	System.out.println("var NoOffFirstLineMenus=" + totalMenu);
	out.println("var NoOffFirstLineMenus=" + totalMenu + ";");

//	System.out.println("complete getMenu");

	menuCount[0]++;
	menustr = new StringBuffer(103);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('重新登录','../logon/logout.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','');");
	out.println(menustr.toString());

	menuCount[0]++;
	menustr = new StringBuffer(114);
	menustr.append("Menu");
	menustr.append(menuCount[0]);
	menustr.append("=new Array('密码修改','../changePwd/PwdInput.jsp','',0,MenuHeight,120,'','','','','','',-1,1,-1,'','7777');");
	out.println(menustr.toString());

	out.println("parent.fraQuick.window.location = '../logon/station.jsp';");
	out.println("</script>");
}
//System.out.println("后台数据准备完毕，并输出！");
%>
<script language="JavaScript" type="text/JavaScript">
//var t = new Date();
//document.write(t);
CreateMenuMain("Menu",NoOffFirstLineMenus);
//var s = new Date();
//document.write(s);

function showMenu(divID){
	var imgname;
	var a;
	if (divID.style.display == ""){
		divID.style.display="none";
		a = divID.id.replace('s','i');
		a = "document.all('"+a+"')";
		whichIm = eval(a);
		whichIm.src = "../common/images/butCollapse.gif";
	}
	else{
		divID.style.display="";
		a = divID.id.replace('s','i');
		a = "document.all('"+a+"')";
		whichIm = eval(a);
		whichIm.src = "../common/images/butExpand.gif";
		if (divID.id.length<=7) closeMenu(divID);	//如果是一级科目才执行closeMenu
	}
}

//循环关闭非打开的一级目录菜单，如果能不用最好
function closeMenu(divID){
	var a;
	divColl = document.all.tags("DIV");	//所有div的个数
	for (i=0; i<divColl.length; i++){
		//判定是否为打开的div，且是一级目录菜单，对节点的长度做了一定的限制，不是很好
		if ((divColl(i).id != divID.id)&&(divColl(i).id.length<=7)) {
			divColl(i).style.display="none";
			a = divColl(i).id.replace('s','i');
			a = "document.all('"+a+"')";
			whichIm = eval(a);
			whichIm.src = "../common/images/butCollapse.gif";
		}
	}
}

function changeframe(nodecode,nodesrc){
        var currElement=event.srcElement;//按钮效果
	changeNode(currElement);//按钮效果
	parent.fraInterface.window.location.href=nodesrc;	//转换连接，比直接用a的方式快一些
	if ((nodecode == null)||(nodecode == '')){
	}
	else{
		parent.fraQuick.location.href="station.jsp?Ip=<%=Ip%>&nodecode="+nodecode;	//修改导航栏的显示信息
	}
}

var currNode;
function changeNode(nodeID)
{
	if(typeof(currNode)!="undefined")
	{
		currNode.className="out";
	}
	currNode=nodeID;
	currNode.className="up";
}

function shiftMouseOut()
{
	var x = event.srcElement;
	if(x!=currNode)
	{
		x.className="";
	}
	else
	{
		x.className="up"
	}
}
</script>