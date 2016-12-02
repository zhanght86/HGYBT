<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%
		//程序名称：
		//程序功能：
		//创建日期：2002-06-19 11:10:36
		//创建人  ：CrtHtml程序创建
		//更新记录：  更新人    更新日期     更新原因/内容
	%>
	<%
	%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>

		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="ContDetialInit.jsp"%>
		<SCRIPT src="ContDetial.js"></SCRIPT>
  
	</head>

	<body onload="initForm(); ">
		<form action="" method=post name=fm
			target="fraSubmit">
			<Div id="divButton" style="display: 'none'">
				<%@include file="../common/jsp/OperateButton.jsp"%>
				<%@include file="../common/jsp/InputButton.jsp"%>
			</DIV>
			
			  
				<!-- 保单信息部分 -->
				<table>
					<tr>
						<td>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,ContDIV);">
						</td>
						<td class=titleImg>
							保单信息
						</td>
					</tr>
				</table>
				
				<Div id="ContDIV" style="display: ''">
					<table class=common>
						
						<TR class=common>
							<TD class=title>
								投保单号码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=ProposalContNo >
							</TD>
							<TD class=title>
								保单号
							</TD>
							<TD class=input>
								<Input class="input" readonly name=ContNo  value="ContNo">
							</TD>
							<TD class=title>
								保单印刷号
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PrtNo  value="PrtNo">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								交易银行
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=TranCom value="TranCom"><Input class="input" readonly name=TranComName value="TranComName">
							</TD>
							<TD class=title>
								保单状态
							</TD>
							<TD class=input>
								<Input class="input" readonly name=ContState value="ContState">
							</TD>
							<TD class=title>
								对账状态
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BalanceState  value="BalanceState">
							</TD>
							<%-- 
							<TD class=title>
								签单银行流水
							</TD> 
							<TD class=input>
								<Input class="input" readonly name=TranNo  value="TranNo">
							</TD>
							 --%>
							<Input type="hidden" readonly name=TranNo  value="TranNo">

						</TR>
						
						<TR class=common>
							
							<TD class=title>
								银行网点
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BankNode  value="BankNode">
							</TD>
							<TD class=title>
								银行出单柜员
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Operator  value="Operator">
							</TD>
						</TR>
						
					</TABLE>
					</DIV>
					
					<!-- 代理人信息部分 -->
				<table>
					<tr>
						<td>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,AgentDIV);">
						</td>
						<td class=titleImg>
							代理人信息
						</td>
					</tr>
				</table>
				
				<Div id="AgentDIV" style="display: ''">
					<table class=common>
						<TR class=common>
							<%--<TD class=title>
								地区
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=AreaNo value="AreaNo"><Input class="input" readonly name=AreaName value="AreaName">
							</TD>
							<TD class=title>
								城市
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=CityNo value="CityNo"><Input class="input" readonly name=CityName value="CityName">
							</TD>
							
							--%>
							<Input type="hidden" readonly name=TranNo  value="AreaNo">
							<Input type="hidden" readonly name=TranNo  value="CityNo">
							<Input type="hidden" readonly name=TranNo  value="AreaName">
							<Input type="hidden" readonly name=TranNo  value="CityName">
							<TD class=title>
								管理机构
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=ManageCom value="ManageCom"><Input class="input" readonly name=ManageComName value="ManageComName">
							</TD>
							
							<TD class=title>
								网点专员
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=AgentCode value="AgentCode"><Input class="input" readonly name=AgentCodeName value="AgentCodeName">
							</TD>
							<TD class=title>
								网点
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=AgentCom value="AgentCom"><Input class="input" readonly name=AgentComName value="AgentComName">
							</TD>
						</TR>
						<Input type="hidden" readonly name=UnitCode value="UnitCode">
						<%--<TR class=common>
							<TD class=title>
								组别
							</TD>
							<TD class=input>
								<Input class="input" readonly name=UnitCode value="UnitCode">
							</TD>
						</TR>
					--%></table>
				</Div>

				<!-- 被保人信息部分 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divLCInsured);">
						</td>
						<td class=titleImg>
							被保人信息
						</td>
					</tr>
				</table>
				<Div id="divLCInsured" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								姓名
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredName value="InsuredName">
							</TD>
							<TD class=title>
								性别
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredSex value="InsuredSex">
							</TD>
							<TD class=title>
								出生日期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredBirthDay value="">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								年龄
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredAge value="InsuredAge">
							</TD>
							<TD class=title>
								证件类型
							</TD>
							<TD class=input>
								<Input type="hidden" name=InsuredIDType value="InsuredIDType">
								<Input class="input" readonly name=InsuredIDTypeName value="InsuredIDTypeName">
							</TD>
							<TD class=title>
								证件号码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredIDNo value="InsuredIDNo">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								家庭电话
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredPhone value="InsuredPhone">
							</TD>
							<TD class=title>
								移动电话
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredMobile value="InsuredMobile">
							</TD>
							<TD class=title>
								电子邮箱
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredEMail value="InsuredEMail">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								联系地址
							</TD>
							<TD class=input colspan=3>
								<Input class=common3 readonly name=InsuredAddress value="InsuredAddress">
							</TD>
							<TD class=title>
								邮政编码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredZipCode value="InsuredZipCode">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								职业代码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredJobeCode value="InsuredJobeCode">
							</TD>
						</TR>
					</table>
				</Div>
				


<!--投保人信息部分 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divAppnt);">
						</td>
						<td class=titleImg>
							投保人信息
						</td>
					</tr>
				</table>
				<Div id="divAppnt" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								姓名
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntName value="AppntName">
							</TD>
							<TD class=title>
								性别
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntSex value="AppntSex">
							</TD>
							<TD class=title>
								出生日期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntBirthDay value="AAA">
							</TD>
						</TR>
   
						<TR class=common>
							<TD class=title>
								年龄
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntAge value="AppntAge">
							</TD>
							<TD class=title>
								证件类型
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=AppntIDType value="AppntIDType">
								<Input class="input" readonly name=AppntIDTypeName value="AppntIDTypeName">
							</TD>
							<TD class=title>
								证件号码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntIDNo value="AppntIDNo">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								家庭电话
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntPhone value="AppntPhone">
							</TD>
							<TD class=title>
								移动电话
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntMobile value="AppntMobile">
							</TD>
							<TD class=title>
								电子邮箱
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntEMail value="AppntEMail">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								联系地址
							</TD>
							<TD class=input colspan=3>
								<Input class=common3 readonly name=AppntAddress value="AppntAddress">
							</TD>
							<TD class=title>
								邮政编码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntZipCode value="AppntZipCode">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								职业代码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntJobeCode value="AppntJobeCode">
							</TD>
							<TD class=title>
								与被保人的关系
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntRealToInsured value="AppntRealToInsured">
							</TD>
						</TR>
					</table>
				</Div>

				<!-- 险种信息部分 -->
				
				<!-- 主险 信息部分-->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divPol);">
						</td>
						<td class=titleImg>
							主险信息
						</td>
					</tr>
				</table>
				<Div id="divPol" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								产品名称
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=RiskCode value="RiskCode">
								<Input class="input" readonly name=RiskCodeName value="RiskCodeName">
							</TD>
							
							<TD class=title>
								缴费方式
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayIntvName value="PayIntvName">
							</TD>
							
							<TD class=title>
								缴费年期类型
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYearFlagName value="PayEndYearFlagName">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								缴费年期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYear value="PayEndYear">
							</TD>
							
							<TD class=title>
								保险年期类型
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYearFlagName value="InsuYearFlagName">
							</TD>
							
							<TD class=title>
								保险年期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYear value="InsuYear">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								份数
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Mult value="Mult">
							</TD>
							
							<TD class=title>
								保额
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Amnt value="Amnt">元
							</TD>
							
							<TD class=title>
								保费合计
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Prem value="Prem">元
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								主险基本保费
							</TD>
							<TD class=input>
								<Input class="input" readonly name=MainPrem value="MainPrem">元
							</TD>
							
							<TD class=title>
								期交追加保费
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AddPrem value="AddPrem">元
							</TD>
							
							<TD class=title>
								趸交追加保费
							</TD>
							<TD class=input>
								<Input class="input" readonly name=FirstAddPrem value="FirstAddPrem">元
							</TD>
						</TR>
					</table>
				</Div>
				
				<!-- 附加险信息部分-->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCRisk1Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCRisk1);">
						</td>
						<td class=titleImg>
							附加险信息1
						</td>
					</tr>
				</table>
				<Div id="divLCRisk1" style="display: '<%=divLCRisk1%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								产品名称
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=RiskCode1 value="RiskCode1">
								<Input class="input" readonly name=RiskCodeName1 value="RiskCodeName1">
							</TD>														
							
							<TD class=title>
								缴费年期类型
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYearFlagName1 value="PayEndYearFlagName1">
							</TD>
							<TD class=title>
								缴费年期
							</TD>
							
							<TD class=input>
								<Input class="input" readonly name=PayEndYear1 value="PayEndYear1">
							</TD>			
						</TR>
						
						<TR class=common>
											
							<TD class=title>
								保险年期类型
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYearFlagName1 value="InsuYearFlagName1">
							</TD>
							
							<TD class=title>
								保险年期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYear1 value="InsuYear1">
							</TD>
							<TD class=title>
								保额
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Amnt1 value="Amnt1">元
							</TD>
							
						</TR>
						
						<TR class=common>							
							
							
							<TD class=title>
								保费
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Prem1 value="Prem1">元
							</TD>
						</TR>
												
					</table>
				</Div>
				
				<!-- 附加险信息部分-->
				<table>
					<tr>
						<td class=common>							
								<IMG src="../common/images/<%=divLCRisk2Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCRisk2);">
						</td>
						<td class=titleImg>
							附加险信息2
						</td>
					</tr>
				</table>
				<Div id="divLCRisk2" style="display: '<%=divLCRisk2%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								产品名称
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=RiskCode2 value="RiskCode2">
								<Input class="input" readonly name=RiskCodeName2 value="RiskCodeName2">
							</TD>
																			
							<TD class=title>
								缴费年期类型
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYearFlagName2 value="PayEndYearFlagName2">
							</TD>
							<TD class=title>
								缴费年期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYear2 value="PayEndYear2">
							</TD>
						</TR>
						
						<TR class=common>							
							
							<TD class=title>
								保险年期类型
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYearFlagName2 value="InsuYearFlagName2">
							</TD>
							
							<TD class=title>
								保险年期
							</TD>							
							<TD class=input>
								<Input class="input" readonly name=InsuYear2 value="InsuYear2">
							</TD>
							<TD class=title>
								保额
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Amnt2 value="Amnt2">元
							</TD>
						</TR>
						
						<TR class=common>							
							
							<TD class=title>
								保费
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Prem2 value="Prem2">元
							</TD>											
						</TR>
												
					</table>
				</Div>
				
				
				<!-- 投资账户信息 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divCountImg%>" style="cursor: hand;"
								OnClick="showPage(this,divCount);">
						</td>
						<td class=titleImg>
							投资账户信息  	
						</td>
					</tr>
				</table>
				<Div id="divCount" style="display: '<%=divCount%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								卓越投资账户
							</TD>
							<TD class=input>
				
								<Input class="input" readonly name=U1ZY value="U1ZY">%
							</TD>
							
							<TD class=title>
								稳健投资账户
							</TD>
							<TD class=input>
							
								<Input class="input" readonly name=U2WJ value="U2WJ">%
							</TD>
							
							<TD class=title>
								安心投资账户
							</TD>
							<TD class=input>

								<Input class="input" readonly name=U3AX value="U3AX">%
							</TD>
						</TR>
						
						<tr>
							<TD class=title>
								进取投资账户
							</TD>
							<TD class=input>
							
								<Input class="input" readonly name=U6JQ value="U6JQ">%
							</TD>
							
							<TD class=title>
								和谐投资账户
							</TD>
							<TD class=input>

								<Input class="input" readonly name=U8HX value="U8HX">%
							</TD>
							
							<TD class=title>
								投资日期标志
							</TD>
							<TD class=input>

								<Input class="input" readonly name=AccTimeFlag value="AccTimeFlag">
							</TD>
						</tr>
						
					</table>
				</div>
				

		<!-- 账户信息 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divAccCount);">
						</td>
						<td class=titleImg>
							账户信息
						</td>
					</tr>
				</table>
				<Div id="divAccCount" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								账户所有人
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AccName value="AccName">
							</TD>
							
							<TD class=title>
								转账账号
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AccNo value="AccNo">
							</TD>
						</TR>
					</table>
				</div>

				<!-- 受益人信息1 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCBnf1Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCBnf1);">
						</td>
						<td class=titleImg>
							受益人信息1
						</td>
					</tr>
				</table>
				<Div id="divLCBnf1" style="display: '<%=divLCBnf1%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								姓名
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfName value="BnfName">
							</TD>
							<TD class=title>
								性别
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfSex value="BnfSex">
							</TD>
							<TD class=title>
								出生日期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfBirthDay value="BnfBirthDay">
							</TD>
						</TR>

						<TR class=common>
								<Input class="input" readonly type="hidden" name=BnfAge value="BnfAge">
							<TD class=title>
								证件类型
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=BnfIDType value="BnfIDType">
								<Input class="input" readonly name=BnfIDTypeName value="BnfIDTypeName">
							</TD>
							<TD class=title>
								证件号码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfIDNo value="BnfIDNo">
							</TD>
							<TD class=title>
								受益顺序
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfGrade value="BnfGrade">
							</TD>
						</TR>

						<TR class=common>
							
						
							<TD class=title>
								受益比率
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfLot value="BnfLot">%
							</TD>

							<TD class=title>
								与被保人的关系
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfRealToInsured value="BnfRealToInsured">
							</TD>
						</TR>
					</table>
				</Div>
				
				
					<!-- 受益人信息2 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCBnf2Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCBnf2);">
						</td>
						<td class=titleImg>
							受益人信息2
						</td>
					</tr>
				</table>
				<Div id="divLCBnf2" style="display: '<%=divLCBnf2%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								姓名
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfName2 value="BnfName2">
							</TD>
							<TD class=title>
								性别
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfSex2 value="BnfSex2">
							</TD>
							<TD class=title>
								出生日期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfBirthDay2 value="BnfBirthDay2">
							</TD>
						</TR>

						<TR class=common>
								<Input class="input" type="hidden" readonly name=BnfAge2 value="BnfAge2">
							<TD class=title>
								证件类型
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=BnfIDType2 value="BnfIDType2">
								<Input class="input" readonly name=BnfIDTypeName2 value="BnfIDTypeName2">
							</TD>
							<TD class=title>
								证件号码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfIDNo2 value="BnfIDNo2">
							</TD>
							<TD class=title>
								受益顺序
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfGrade2 value="BnfGrade2">
							</TD>
						</TR>

						<TR class=common>
							
						
							<TD class=title>
								受益比率
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfLot2 value="BnfLot2">%
							</TD>

							<TD class=title>
								与被保人的关系
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfRealToInsured2 value="BnfRealToInsured2">
							</TD>
						</TR>
					</table>
				</Div>

			
				<!-- 受益人信息3 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCBnf3Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCBnf3);">
						</td>
						<td class=titleImg>
							受益人信息3
						</td>
					</tr>
				</table>
				<Div id="divLCBnf3" style="display: '<%=divLCBnf3%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								姓名
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfName3 value="BnfName3">
							</TD>
							<TD class=title>
								性别
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfSex3 value="BnfSex3">
							</TD>
							<TD class=title>
								出生日期
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfBirthDay3 value="BnfBirthDay3">
							</TD>
						</TR>

						<TR class=common>
								<Input class="input" type="hidden" readonly name=BnfAge3 value="BnfAge3">
							<TD class=title>
								证件类型
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=BnfIDType3 value="BnfIDType3">
								<Input class="input" readonly name=BnfIDTypeName3 value="BnfIDTypeName3">
							</TD>
							<TD class=title>
								证件号码
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfIDNo3 value="BnfIDNo3">
							</TD>
							<TD class=title>
								受益顺序
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfGrade3 value="BnfGrade3">
							</TD>
						</TR>

						<TR class=common>
							
						
							<TD class=title>
								受益比率
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfLot3 value="BnfLot3">%
							</TD>

							<TD class=title>
								与被保人的关系
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfRealToInsured3 value="BnfRealToInsured3">
							</TD>
						</TR>
					</table>
				</Div>
		</form>
		<span id="spanCode" style="display: none; position: absolute;"></span>
		<span id="spanApprove" style="display: none; position: relative;"></span>
	</body>
</html>


