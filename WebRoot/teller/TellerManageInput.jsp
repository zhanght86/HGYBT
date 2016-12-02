<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

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
		<script src="../common/javascript/MulLine.js">
</script>
		<script src="../common/javascript/VerifyInput.js">
</script> 

		<%@include file="TellerManageInit.jsp"%> 
		<script src="TellerQuery.js">
</script>
		 

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />
 
		<title>柜员管理</title>
	</head>

	<body onload="initForm();initElementtype();">
		<form action="./TellerManageSave.jsp" method="post" name="fm"
			target="fraSubmit" >
			<input type="hidden" name="OperType">
			<input type="hidden" name="SRSTellerNo">
			<div id="divAgentCheck" style="display:''">
			<TABLE class="common">
				<TR>
					<TD class="titleImg" align="center">
						银行柜员：
					</TD>
				</TR>
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
				</TABLE> 
				</div>
				<div id="divAgentADD" style="display:'none'">
			<TABLE class="common">
				<TR>
					<TD class="titleImg" align="center">
						银行柜员：
					</TD>
				</TR>
				<tr>
				<td class=title>
						所属银行</TD>
				   <TD class=input >
						<input class="codeno" name="iTranCom" verify="银行代码|NotNull"
							ondblclick="iBankQuery(); "
							onkeyup="iBankQueryKey(); ">
						<input class="codename" name="iTranComName" readonly="true"/>
						</TD>
				
		               	<TD class=title>
							所属地区：</TD>
							<TD class=input>
						<input class="codeno" name="iZoneNo" verify="地区代码|NotNull"
							ondblclick="iZoneQuery(); "
							onkeyup="iZoneQueryKey(); ">
						<input class="codename" name="iZoneName" readonly="true"/>
					</TD>			
				</tr>
				<tr>
					<td class=title>
						柜员姓名</TD>
				   <TD class=input>
						<input class="input" name="iTellerName" />
						</TD>
				   <TD class=title>
						银行柜员号</TD>
				   <TD class=input>
						<input class="input" name="iTellerIDNo" />
					</TD>
				</tr>
				<tr>
					<td class=title>
						资格证类型</TD>
				   <TD class=input>
						<input class="input" name="iQuType" />
						</TD>
				   <TD class=title>
						资格证号</TD>
				   <TD class=input>
						<input class="input" name="iTellerQuNo" />
				   </TD>
				</tr>
				<tr>
					<td class=title>
						资格证生效日期</TD>
				   <TD class=input >
						<input class="coolDatePicker" dateFormat="short"   name="iStartDay"
							elementtype="nacessary" /></TD>
					<td class=title>资格证失效日期</TD>
						
				   <TD class=input >
						<input class="coolDatePicker" dateFormat="short"  name="iEndDay"
							elementtype="nacessary" />
					</td>
				</tr>
				</TABLE> 
				</div>
			
			<div id="divCmdButton" style="display:''">
			<input class="cssButton" value="查询柜员" type="button"
				onclick="MqueryItems();" />
				&nbsp;
				<input class="cssButton" value="增加柜员" type="button"
					onclick="insertClick();" />
				&nbsp;
				<input class="cssButton" value="修改柜员" type="button"
					onclick="updateClick();" />
			&nbsp;
				<input class="cssButton" value="删除柜员" type="button"
					onclick="delItem();" />
			&nbsp;
			<input class="cssButton" value="重 置" type="button"
				onclick="resetInit();" />
				</div>
				<div id="divSubCmdButton" style="display: none">
			<INPUT VALUE="确  定" TYPE=button onclick="okClick()" class="cssButton">
			<INPUT VALUE="退  出" TYPE=button onclick="cancelClick()" class="cssButton">
			</div>
			
			<div id="divGrid" style="display:''">
			<span id="spanQueryGrid"></span>
			</div>
			<div id="divPage" style="display:''">
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
			</div>
		</form> 
		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>
