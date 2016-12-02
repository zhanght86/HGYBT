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
		<script src="../common/javascript/AXANodeMap.js">
</script>
		<%@include file="./LOPrintInit.jsp"%>
		<script src="LOPrint.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

<title>银保通每日实时数据导出表</title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./LOPrintSave.jsp" method="post" name="fm"
			target="fraSubmit">
			<input type="hidden" name=UserAuthority>
			<input type="hidden" name=ZoneNo>
			<input type="hidden" name=ZoneName>
			<table class=common>
				<tr>
					<td class=titleImg align=center>
						银保通每日实时数据导出表
					</td>
				</tr>
			</table>

			<HR>

			<table class="common">

				<!-- ************ 第一行 *********** -->
				<TR class=common>
					<TD class=title>
						银行渠道：</td>
		                <TD class=input>
						<input class="codeno" name="TranCom"
							ondblclick="LOBankQuery()"
							onkeyup="LOBankQueryKey()">
						<input class="codename" name="TranComName" value="***所有渠道***"
							readonly="readonly"/>
					</TD>		
					<!-- ************CodeSelect 区域选择框*********** -->
					<input type="hidden" name="ManageCodeNo">
		               	<TD class=title>
							管理机构：</TD>
						<td class=input >
							<Input class=input name=ManageCodeName verify="管理机构|notnull"
								readonly="readonly" >
							<input type=button class="cssButton" value="选择"
								onClick="SelectCom();">
						</TD>
				
						<input class="codeno" name="AreaNo" type="hidden"
							ondblclick="return showAreaNo()"
							onkeyup="return showAreaNoKey()"
							id = "AreaNoId";
							>
						<input class="codename" name="AreaName" value="***所有区域***"
						type="hidden"	readonly="readonly"/>
			
					<!-- ************CodeSelect 城市选择框*********** -->
					
				</TR>

				<!-- ************ 第二行 *********** -->
				<TR class=common>
						<input class="codeno" name="CityNo" type="hidden"
							ondblclick="return showCityNo();"
							onkeyup="return showCityNoKey();"
							id = "CityNoId";
							>
						<input class="codename" name="CityName" value="***所有城市***"
						type="hidden"	readonly="readonly" id = "CityNameId";/>
					</TD>
					<TD class=title>
						网点专员：</td>
		                <TD class=input>
					<input class="codeno" name="AgentCodeNo"
							ondblclick="return showAgentCodeNo();"
							ondblclick="return showAgentNoCodeKey();">
						<input class="codename" name="AgentCodeName" value="***所有专员***"
							readonly="readonly" />
					</TD>
					<TD class=title>
						网&nbsp;&nbsp;&nbsp;&nbsp;点：</td>
		                <TD class=input>
						<input class="codeno" name="AgentComNo"
							ondblclick="return showAgentNo();"
							ondblclick="return showAgentNoKey();"/>
						<input class="codename" name="AgentComName" value="***所有网点***"
							readonly="readonly"/>
					</TD>
				</TR>

				<!-- ************ 第三行 *********** -->
				<TR class=common>
					
					<TD class=title>
						险种代码：</td>
		                <TD class=input>
						<input class="codeno" name="RiskCodeNo"
						ondblclick="ShowRiskCodeList();"
							onkeyup="ShowRiskCodeKey();">
						<input class="codename" name="RiskCodeName" value="***所有险种***"
							readonly="readonly" />
					</TD>
					<TD class=title>
						销售日期：</td>
		                <TD class=input>
						<input class="coolDatePicker" dateFormat="short" name="Day"/>
					</TD>
				</TR>


			</TABLE>
		

			<BR />
 
			<table> 
			<TR class=common>
				<TD width="26%">	<INPUT VALUE="预      览" class="cssbutton" TYPE=button onclick="LOPrintPreviewQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="生成报表" class="cssbutton" TYPE=button onclick="LOPrintQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="重      置" class="cssbutton" type="button"  onclick="initBox();"></TD>
				
			</TR>
		</table>
		<input type="hidden" name="PrintFlag">
		</form>
		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>