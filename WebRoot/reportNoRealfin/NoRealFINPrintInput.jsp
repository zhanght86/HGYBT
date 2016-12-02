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
		<%@include file="./NoRealFINPrintInit.jsp"%>
		<script src="NoRealFINPrint.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

	<title>银保通每日非实时销售明细报表</title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./NoRealFINPrintSave.jsp" method=post name=fm
			target="fraSubmit">
			<input type="hidden" name=UserAuthority>
			<input type="hidden" name=ZoneNo>
			<input type="hidden" name=ZoneName>
			<input type="hidden" name=AgentCodeNo>
			<input type="hidden" name=AgentCodeName>
			<input type="hidden" name=AgentComNo>
			<input type="hidden" name=AgentComName>
			<table class=common>
				<tr>
					<td class=titleImg align=center>
						银保通每日非实时销售明细报表
					</td>
				</tr>
			</table>

			<HR>

			<table class="common" align="center">

			<!-- ************ 第一行 *********** -->
				<TR class=common>
					<TD class=title>
						银行渠道：</td>
		                <TD class=input>
						<input class="codeno" name="TranCom"
							ondblclick="return showTranCom()"
							onkeyup="return showTranComKey()">
						<input class="codename" name="TranComName" value="***所有渠道***"
							readonly="readonly"/>
					</TD>	
					<input type="hidden" name="ManageCodeNo">
		               	<TD class=title>
							管理机构：</TD>
						<td class=input >
							<Input class=input name=ManageCodeName verify="管理机构|notnull"
								readonly="readonly" >
							<input type=button class="cssButton" value="选择"
								onClick="SelectCom();">
						</TD>	
					<!-- ************CodeSelect 区域选择框*********** -->
				
						<input class="codeno" name="AreaNo" type="hidden"
							ondblclick="return showAreaNo()"
							onkeyup="return showAreaNoKey()"
							id = "AreaNoId";
							>
						<input class="codename" name="AreaName" type="hidden" value="***所有区域***"
							readonly="readonly"/>
					</TD>
					<!-- ************CodeSelect 城市选择框*********** -->
					
				</TR>

				<!-- ************ 第二行 *********** -->
				

		<!-- ************ 第四行 *********** -->
				<TR class=common>
					<TD class=title>
						开始日期：</TD><td class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay"/>
						<select name="StartHour"  value="00" style="background-color: #D7E1F6">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option> 
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select>
					点

					</TD>
					<TD class=title>
						结束日期：</TD><td class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay"/>
						<select name="EndHour"  style="background-color: #D7E1F6">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
					</select>
					点
					</TD>
				</TR>
                <TR class=common>
						<input class="codeno" name="CityNo" type="hidden"
							ondblclick="return showCityNo();"
							onkeyup="return showCityNoKey();"
							id = "CityNoId";
							>
						<input class="codename" type="hidden" name="CityName" value="***所有城市***"
							readonly="readonly" id = "CityNameId";/>
					<TD class=title>
						保单状态：</td>
		                <TD class=input>
						<input class="codeno" name="ContStatue"
							ondblclick="return showContStatue();"
							onkeyup="return showContStatueKey();">
						<input class="codename" name="ContStatueName" value="***所有状态***"
							readonly="readonly"/>
					</TD>
				</TR>
			</TABLE>
		

			<BR />
 
			<table > 
			<TR class=common >
				<TD width="26%">	<INPUT VALUE="预      览" class="cssbutton" TYPE=button onclick="FINPrintPreviewQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="生成报表" class="cssbutton" TYPE=button onclick="FINPrintQuery();"></TD> 
				<TD  width="26%">	<INPUT VALUE="重      置" class="cssbutton" type="button"  onclick="initBox();"></TD>
				
			</TR>
		</table>
		<input type="hidden" name="PrintFlag">
		</form>
		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>