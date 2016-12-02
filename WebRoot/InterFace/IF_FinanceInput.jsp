
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%@page contentType="text/html;charset=GBK"%>
	<head>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<SCRIPT src="IF_Finance.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="IF_FinanceInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./IF_FinanceSave.jsp" method=post name=fm
			target="fraSubmit">
			<table>
				<tr class=common>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divYBT);"></IMG>
					</td>
					<td class=titleImg>
						接口文件生成
					</td>
				</tr>
			</table>
			<Div id="divYBT" style="display: ''">
				<TR class=common>
				   <TD class=title width="30%">&nbsp;交&nbsp;易&nbsp;日&nbsp;期：</td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<td class=input size=30 >
				   <Input class="coolDatePicker"  dateFormat = "short" name=FileDate verify="日期|DATE">
				   &nbsp;&nbsp;<input class="cssbutton" type="button" value="文件生成"
				onclick="creatClick();" /></TD>		
				</TR>			
			</Div>
		
		<HR>		
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divIFQuery);">
					</td>
					<td class=titleImg>
						文件查询
					</td>
				</tr>
			</table>
			<div  id="divIFQuery" style="display: ''">
		<table class=common >
		<TR>
						<TD class=title>
							区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域：
							</td>
						<td class=input>
							<input class="codeno" name="Area"
								ondblclick="return showAreaNo()"						
								onkeyup="return showAreaNoKey()" style="width: 51px; "/>
							<input class="codename" name="AreaName" value="***所有区域***" readonly="readonly" style="width: 113px; "/>						
						</TD>
					<td class=title>
						起始生成日期：
						</td>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay"
							elementtype="nacessary" style="width: 144px; "/>
							</td>
						<td class=title>
						&nbsp; 终止生成日期：
						</td>
						<td class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay"
							elementtype="nacessary" style="width: 126px; "/>
					</td>
				</tr>
				</table>
				</div>
		<TR>	 			
			 			<input class="cssbutton" type="button" value="查&nbsp;&nbsp;&nbsp;询"
				onclick="areaQueryClick();" />
				           </td>				
			</TR>	
			<Div id="divYBTGrid" style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanYBTGrid"> </span>
						</td>
					</tr>
				</table>
				<br>
				<CENTER>
					<INPUT VALUE="首  页" TYPE=button
						onclick="turnPage.firstPage();makeDownLink();" class="cssButton">
					<INPUT VALUE="上一页" TYPE=button
						onclick="turnPage.previousPage();makeDownLink();"
						class="cssButton">
					<INPUT VALUE="下一页" TYPE=button
						onclick="turnPage.nextPage();makeDownLink();" class="cssButton">
					<INPUT VALUE="尾  页" TYPE=button
						onclick="turnPage.lastPage();makeDownLink();" class="cssButton">
				</CENTER>
			</Div>
			<br>
			<table class="common" align=left>
			</table>
			<input type=hidden name=hideOperate value=''>
			<input type=hidden name=currentDate value=''>
			<span id="spancode" style="display: none; position: absolute;"></span>
		</form>
	</body>
</html>
