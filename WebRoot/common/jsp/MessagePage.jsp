<%@page contentType="text/html;charset=GBK" %>
<%@include file="UsrCheck.jsp"%>
<%
//�������ƣ�MessagePage.jsp
//�����ܣ���Ϣ��ʾҳ��
//�������ڣ�2002-05-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//            ŷ����   2002-05-10    �޸�
//            ��λ��   2009-02-13    �޸�
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.StrTool"%>
<html>
<head>
<title>��Ϣ����</title>
<link rel="stylesheet" type="text/css" href="../css/Project.css">
<%
String SUCCESS = "S";	//�ɹ�
String FAILURE = "F";	//ʧ��
String COMMON = "C";	//һ����Ϣ
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
	Content = "ҳ�泬ʱ�������µ�¼.";
}
/*********************************************************************/

String strPicture ="";
%>
</head>
<body class="interface">
<h1><center>ϵͳ��Ϣ</center></h1>
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
if (Content.indexOf("����") == -1)
{
%>
	<center>
		<input type=button class=common id=butSubmit value="ȷ ��" onclick="window.close()" tabIndex=0>
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