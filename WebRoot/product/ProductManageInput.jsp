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

		<%@include file="ProductManageInit.jsp"%>
		<script src="./ProductManage.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

		<title>产品查询</title>
	</head>

	<body onload="initForm();initElementtype();">
		<form action="./ProductManageSave.jsp" method="post" name="fm"
			target="fraSubmit">

			<input type="hidden" name="OperType">
			
            <div id="divUpdate" style="display: 'none'">
            <table class=common>
            <tr>
            <td class=titleImg>修改产品信息：</td>
            </tr>
            <tr>
            <TD class=title>
							银行渠道：</TD>
							<td class=input>
							<input class="codeno" name="ProBankCode" readonly="readonly" />		
							<input class="codename" name="UpBank" readonly="readonly" elementtype="nacessary"/>
						</TD>
            <td class=title>产品代码：</td>
            <td class=input>
            <input class=input name="ProCode" readonly="readonly"  id="ProCodeId" elementtype=nacessary>
            </td>
            </tr>
            <tr>
            <td class=title>AS400代码：</td>
            <td class=input>
            <input class=input name="ASCode" readonly="readonly" id="ASCodeId" elementtype=nacessary>
            </td>
            <td class=title>产品名称：</td>
            <td class=input>
            <input class=input name="ProName" readonly="readonly" size=35 id="ProNameId" elementtype=nacessary>
            </td>
            </tr>
            <tr>
            <TD class=title>起始日期：</TD>
							<td class=input>
		                    <Input class="coolDatePicker"   dateFormat = "short" name=StaDate elementtype=nacessary>
		                </TD> 
		               <TD class=title>终止日期：</TD>
							<td class=input>
		                    <Input class="coolDatePicker"   dateFormat = "short" name=EndSDate elementtype=nacessary>
		                 </TD> 
            </tr>
            </table>
            <INPUT VALUE="确  定" TYPE=button onclick="okUpdaClick()"
					class="cssButton">
				<INPUT VALUE="退  出" TYPE=button onclick="cancelUpdaClick()"
					class="cssButton">
            </div>


			<div id="divQuery" style="display: ''">
				<table class=common >
					<tr>
						<td class="titleImg">
							查询条件：
						</td>
					</tr>
					<!-- ************ 第二行 *********** -->

					<TR >
						<TD class=title>
							银行渠道：</TD>
							<TD class=input>
							<input class="codeno" name="BankCode"  
								ondblclick="return showCodeList('trancom_bank',[this,BankName],[0,1],null,null,null,1,null,1);"
								onkeyup="return showCodeListKey('trancom_bank',[this,BankName],[0,1],null,null,null,1,null,1);" />
							<input class="codename" name="BankName" readonly="readonly" />
						</TD>	
						<td class=title>
						产品状态：
						</td>	
						<td class=input>
						<input class="codeno" name="StateCode"  
								ondblclick="return showCodeList('product_state',[this,StateName],[0,1],null,null,null,1,null,1);"
								onkeyup="return showCodeListKey('product_state',[this,StateName],[0,1],null,null,null,1,null,1);" />
						<input class="codename" name="StateName" readonly="readonly" />
						 </td>
					</TR>
				</table>
			</div>
           


			<div id="divInsert" style="display: 'none'">
				<table class="common">
					<tr>
						<td class="titleImg">
							设置产品停售地区：
						</td>
					</tr>
					<TD class=title>
							银行渠道：</TD>
							<td class=input>
							<input class="codeno" name="iBankCode" readonly="readonly" />		
							<input class="codename" name="iBankName" readonly="readonly" elementtype="nacessary"/>
						</TD>
			<td class=title>产品代码：</td>
            <td class=input>
            <input class=input name="iProductCode" readonly="readonly"   elementtype=nacessary>
            </td>
					<TR class=common>
		                    <Input type="hidden"   dateFormat = "short" name=iStartDate >
		                </TD> 
		                    <Input type="hidden" dateFormat = "short" name=iEndDate >
		                 </TD> 
					</TR>
					
					<TR class=common>
						<input type="hidden" name="iComCode">
						<TD class=title>
							公司机构:
							</TD>
							<td class=input>
							<Input class=input name=iComCodeName verify="公司机构|notnull"
								readonly="readonly" elementtype=nacessary>
							<input type=button class="cssButton" value="选择"
								onClick="SelectCom();">
						</TD>
						<input type="hidden" name="iActivityCode" value="1" maxlength="20"/>
					<td class=title>
						系统状态：
						</td>	
						<td class=input>
						<input class="codeno" name="ProStateCode"  
								ondblclick="return showCodeList('prostate_set',[this,ProStateName],[0,1],null,null,null,1,null,1);"
								onkeyup="return showCodeListKey('prostate_set',[this,ProStateName],[0,1],null,null,null,1,null,1);" />
						<input class="codename" name="ProStateName"  readonly="readonly" elementtype=nacessary/>
						 </td>
					</TR>
					<tr>
					<TD class=title>
							AS400代码：</TD>
							<td class=input>
							<input class="codeno" name="iRiskCode" readonly="true"/>
							<input class="codename" name="iRiskName" readonly="true" elementtype="nacessary" style="width: 250px; "/>
						</TD>
					</TR>
				</table>
			</div>

             <div id="divCmdButton" style="display: ''">
				<input value="查 询 " type=button onclick="riskQueryItems()"
					class="cssButton">
				<input value="修改 " type=button onclick="riskUpdateItems()"
					class="cssButton">
				<input class="cssButton" value="重 置" type="reset"
					onclick="resetForm();" />
				<INPUT VALUE="产品停售地区设置" TYPE=button onclick="insertClick()"
					class="cssButton">
			</div>
			<div id="divSubCmdButton" style="display: none">
				<INPUT VALUE="确  定" TYPE=button onclick="okClick()"
					class="cssButton">
				<INPUT VALUE="删除" TYPE=button onclick="delItem()"
					class="cssButton">
				<INPUT VALUE="退  出" TYPE=button onclick="cancelClick()"
					class="cssButton">
			</div>

			<div id="divGrid" style="display: ''">
				<span id="spanQueryGrid"></span>
			</div>
			

			<div id="divPage" style="display: ''">
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
