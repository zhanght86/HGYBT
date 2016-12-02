<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：BankQueryInput.jsp
	//程序功能：
	//创建日期：1011-10-17
	//创建人  ：ZHJ
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<SCRIPT src="./NewContDetial.js"></SCRIPT>
		<%@include file="./NewContDetialInit.jsp"%>

		<title>接口文件明细信息</title>
	</head>

	<body onload="initForm();">
		<form action="" method=post name=fm target="fraSubmit">
		<table>
				<tr class=common>
				<input class="cssButton" value="关闭页面" type=button
					onclick="closePage();" />
		</tr>
			</table>

				<table>
					<tr class=common>
						<td class=titleImg>
							处理明细
						</td>
					</tr>
				</table>
				
                <table>
					<TR>
					<TD class=title>
							日志号
						</TD>
						<TD class=input>
							<Input class="common" readonly name=IFLog >
						</TD>
						
					<TD class=title>
							文件名称
						</TD>
						<TD class=input>
							<Input class="common" readonly name=FileName >
						</TD>
						
					<TD class=title>
							交易日期
						</TD>
						<TD class=input>
							<Input class="common" readonly name=TranDate >
						</TD>
						
					</TR>
					<TR>
					<TD class=title>
							处理笔数
						</TD>
						<TD class=input>
							<Input class="common" readonly name=DealCout >
						</TD>
					<TD class=title>
							失败信息数
						</TD>
						<TD class=input>
							<Input class="common" readonly name=FailCout >
						</TD>
					<TD class=title>
							生成日期
						</TD>
						<TD class=input>
							<Input class="common" readonly name=MakeDate >
						</TD>
					</TR>
				</table>
				
					<HR>	
					
			
				<span id="spanQueryGrid"></span>
           
                	
				<center>
				<input class="cssbutton" value="首页" type="Button"
					onclick="turnPage.firstPage();" />
				<input class="cssbutton" value="上一页" type="Button"
					onclick="turnPage.previousPage();" />
				<input class="cssbutton" value="下一页" type="Button"
					onclick="turnPage.nextPage();" />
				<input class="cssbutton" value="尾页" type="Button"
					onclick="turnPage.lastPage();" />
			    </center>
		</form>
         
       
		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>