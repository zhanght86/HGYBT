<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />

		<script src="../common/javascript/Common.js">
</script>
		<script src="../common/cvar/CCodeOperate.js">
</script>
		<script src="../common/Calendar/Calendar.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryCache.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryVer3.js">
</script>
		<script src="../common/easyQueryVer3/EasyQueryPrint.js">
</script>
		<script src="../common/javascript/MulLine.js">
</script>
		<script src="../common/javascript/VerifyInput.js">
</script>
		<script src="../common/javascript/AXANodeMap.js">
</script>

		<%@include file="./TellerQueryInit.jsp"%>
		<script src="./TellerQuery.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

		<title>柜员信息查询</title>
	</head>
	
	<body onload="initForm();initElementtype();">
		<form action="." method="post" name="fm">
		<input type="hidden" name=UserAuthority>
			
			<table class="common">
				<tr>
					<td class="titleImg" align="center">
					<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divQuickCheck);"></IMG>
						查询条件
					</td>
				</tr>
				 </table>
				<div id="divQuickCheck" style="display:''">
				<table class="common">  
				<tr>
				<td class=title>
						所属银行</TD>
				   <TD class=input >
						<input class="codeno" name="TranCom" verify="银行代码|NotNull"
							ondblclick="LOBankQuery(); "
							onkeyup="LOBankQueryKey(); ">
						<input class="codename" name="TranComName" readonly="true"/>
						</TD>
				
		               	<TD class=title>
							所属地区：</TD>
							<TD class=input>
						<input class="codeno" name="ZoneNo" verify="地区代码|NotNull"
							ondblclick="LOZoneQuery(); "
							onkeyup="LOZoneQueryKey(); ">
						<input class="codename" name="ZoneName" readonly="true"/>
						</TD>			
					</tr>
					<tr>
					<td class=title>
						柜员姓名</TD>
				   <TD class=input>
						<input class="input" name="TellerName" />
						</TD>
				   <TD class=title>
						银行柜员号</TD>
				   <TD class=input>
						<input class="input" name="TellerIDNo" />
						</TD>
						</tr>
					</table>
				</div>
					<table class="common">
					<tr>
					<td class="titleImg" align="center">
					<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,QUQuickCheck);"></IMG>
						资格证失效日期范围查询：</TD>
				</tr>
				</table>
				<div id="QUQuickCheck" style="display:''">
				<table class="common">  
					<tr>
					<td class=title>
						起始日期</TD>
				   <TD class=input >
						<input class="coolDatePicker" dateFormat="short"   name="StartDay"
							/></TD>
					<td class=title>结束日期</TD>
						
				   <TD class=input >
						<input class="coolDatePicker" dateFormat="short"  name="EndDay"
							/>
					</td>
				</tr>	
					
				</table>
				</div>
					
            
			<input class="cssbutton" type="button" value="查&nbsp;&nbsp;&nbsp;&nbsp;询"
				onclick="queryItems();" />
			
			<input class="cssbutton" type="button" value="重&nbsp;&nbsp;&nbsp;&nbsp; 置" 
				onclick="resetItems();" />

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