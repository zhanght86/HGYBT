<%@page contentType="text/html;charset=GBK" %>
<%@include file="UsrCheck.jsp"%>
<%
//程序名称：MessagePage.jsp
//程序功能：信息显示页面
//创建日期：2002-05-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//            欧阳晟   2002-05-10    修改
//            黄位富   2009-02-13    修改
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.StrTool"%>
<html>
<head>
<title>信息反馈</title>
<link rel="stylesheet" type="text/css" href="../css/Project.css">
<%
String SUCCESS = "S";	//成功
String FAILURE = "F";	//失败
String COMMON = "C";	//一般信息
String Picture = request.getParameter("picture");
String Content = StrTool.unicodeToGBK(request.getParameter("content"));
/*****************added by dingzhong for time out check*********************/
boolean bIsOutTime = false;
if (session == null)
{
	bIsOutTime = true;
}
else
{
	GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	if (tG1 == null)
	{
		bIsOutTime = true;
	}
	else
	{
		String userCode = tG1.Operator;
		String comCode = tG1.ComCode;
		String manageCom = tG1.ManageCom;

		if ((userCode.length()==0) || (userCode.compareTo("")==0)||(comCode.length()==0) || (comCode.compareTo("")==0) ||(manageCom.length()==0) || (manageCom.compareTo("") == 0))
		{
			bIsOutTime = true;
		}
	}
}
if (bIsOutTime)
{
	Content = "页面超时，请重新登录.";
}
/*********************************************************************/

String strPicture ="";
%>
</head>
<body class="interface">
<h1><center>系统信息</center></h1>
<br>
<%
if(Picture==null)
{
	Picture = COMMON;
}

if(Picture.equalsIgnoreCase(SUCCESS))
{
	strPicture ="success.gif";
}
else if (Picture.equalsIgnoreCase(FAILURE))
{
	strPicture ="failure.gif";
}
else
{
	strPicture ="common.gif";
}
%>
	<table>
		<tr>
			<td>
				<img src='../images/<%=strPicture%>'>
			</td>
			<td class="common">
				<%=Content%>
			</td>
		</tr>
	</table>
<%
if (Content.indexOf("正在") == -1)
{
%>
	<center>
		<input type=button class=common id=butSubmit value="确 定" onclick="window.close()" tabIndex=0>
	</center>
<%
}
%>
<script language=JavaScript>
ini = new Date().getTime();
var pc = 0;

function load()
{
	pc += 1;
	lpc.style.width = pc + "%";
	time = setTimeout("load()",30);
	if (pc > 100)
	{
		pc=0;
	}
}

function loaded()
{
	fim = new Date().getTime();
	dif = fim - ini;
	ld.style.display = 'none';
	body.style.backgroundColor = 'silver';
	q.innerHTML = dif/1000;
	page.style.display = '';
}

function Show()
{
	if (txt.style.display == "none")
	{
		txt.style.display = "";
	}
	else
	{
		txt.style.display = "none";
	}
}

try
{
	window.butSubmit.focus();
}
catch(e)
{}
</script>
</body>
</html>