<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%
		//�������ƣ�
		//�����ܣ�
		//�������ڣ�2002-06-19 11:10:36
		//������  ��CrtHtml���򴴽�
		//���¼�¼��  ������    ��������     ����ԭ��/����
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
			
			  
				<!-- ������Ϣ���� -->
				<table>
					<tr>
						<td>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,ContDIV);">
						</td>
						<td class=titleImg>
							������Ϣ
						</td>
					</tr>
				</table>
				
				<Div id="ContDIV" style="display: ''">
					<table class=common>
						
						<TR class=common>
							<TD class=title>
								Ͷ��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=ProposalContNo >
							</TD>
							<TD class=title>
								������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=ContNo  value="ContNo">
							</TD>
							<TD class=title>
								����ӡˢ��
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PrtNo  value="PrtNo">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=TranCom value="TranCom"><Input class="input" readonly name=TranComName value="TranComName">
							</TD>
							<TD class=title>
								����״̬
							</TD>
							<TD class=input>
								<Input class="input" readonly name=ContState value="ContState">
							</TD>
							<TD class=title>
								����״̬
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BalanceState  value="BalanceState">
							</TD>
							<%-- 
							<TD class=title>
								ǩ��������ˮ
							</TD> 
							<TD class=input>
								<Input class="input" readonly name=TranNo  value="TranNo">
							</TD>
							 --%>
							<Input type="hidden" readonly name=TranNo  value="TranNo">

						</TR>
						
						<TR class=common>
							
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BankNode  value="BankNode">
							</TD>
							<TD class=title>
								���г�����Ա
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Operator  value="Operator">
							</TD>
						</TR>
						
					</TABLE>
					</DIV>
					
					<!-- ��������Ϣ���� -->
				<table>
					<tr>
						<td>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,AgentDIV);">
						</td>
						<td class=titleImg>
							��������Ϣ
						</td>
					</tr>
				</table>
				
				<Div id="AgentDIV" style="display: ''">
					<table class=common>
						<TR class=common>
							<%--<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=AreaNo value="AreaNo"><Input class="input" readonly name=AreaName value="AreaName">
							</TD>
							<TD class=title>
								����
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
								�������
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=ManageCom value="ManageCom"><Input class="input" readonly name=ManageComName value="ManageComName">
							</TD>
							
							<TD class=title>
								����רԱ
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=AgentCode value="AgentCode"><Input class="input" readonly name=AgentCodeName value="AgentCodeName">
							</TD>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=AgentCom value="AgentCom"><Input class="input" readonly name=AgentComName value="AgentComName">
							</TD>
						</TR>
						<Input type="hidden" readonly name=UnitCode value="UnitCode">
						<%--<TR class=common>
							<TD class=title>
								���
							</TD>
							<TD class=input>
								<Input class="input" readonly name=UnitCode value="UnitCode">
							</TD>
						</TR>
					--%></table>
				</Div>

				<!-- ��������Ϣ���� -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divLCInsured);">
						</td>
						<td class=titleImg>
							��������Ϣ
						</td>
					</tr>
				</table>
				<Div id="divLCInsured" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredName value="InsuredName">
							</TD>
							<TD class=title>
								�Ա�
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredSex value="InsuredSex">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredBirthDay value="">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredAge value="InsuredAge">
							</TD>
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input type="hidden" name=InsuredIDType value="InsuredIDType">
								<Input class="input" readonly name=InsuredIDTypeName value="InsuredIDTypeName">
							</TD>
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredIDNo value="InsuredIDNo">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								��ͥ�绰
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredPhone value="InsuredPhone">
							</TD>
							<TD class=title>
								�ƶ��绰
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredMobile value="InsuredMobile">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredEMail value="InsuredEMail">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								��ϵ��ַ
							</TD>
							<TD class=input colspan=3>
								<Input class=common3 readonly name=InsuredAddress value="InsuredAddress">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredZipCode value="InsuredZipCode">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								ְҵ����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuredJobeCode value="InsuredJobeCode">
							</TD>
						</TR>
					</table>
				</Div>
				


<!--Ͷ������Ϣ���� -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divAppnt);">
						</td>
						<td class=titleImg>
							Ͷ������Ϣ
						</td>
					</tr>
				</table>
				<Div id="divAppnt" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntName value="AppntName">
							</TD>
							<TD class=title>
								�Ա�
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntSex value="AppntSex">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntBirthDay value="AAA">
							</TD>
						</TR>
   
						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntAge value="AppntAge">
							</TD>
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=AppntIDType value="AppntIDType">
								<Input class="input" readonly name=AppntIDTypeName value="AppntIDTypeName">
							</TD>
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntIDNo value="AppntIDNo">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								��ͥ�绰
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntPhone value="AppntPhone">
							</TD>
							<TD class=title>
								�ƶ��绰
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntMobile value="AppntMobile">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntEMail value="AppntEMail">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								��ϵ��ַ
							</TD>
							<TD class=input colspan=3>
								<Input class=common3 readonly name=AppntAddress value="AppntAddress">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntZipCode value="AppntZipCode">
							</TD>
						</TR>

						<TR class=common>
							<TD class=title>
								ְҵ����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntJobeCode value="AppntJobeCode">
							</TD>
							<TD class=title>
								�뱻���˵Ĺ�ϵ
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AppntRealToInsured value="AppntRealToInsured">
							</TD>
						</TR>
					</table>
				</Div>

				<!-- ������Ϣ���� -->
				
				<!-- ���� ��Ϣ����-->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divPol);">
						</td>
						<td class=titleImg>
							������Ϣ
						</td>
					</tr>
				</table>
				<Div id="divPol" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								��Ʒ����
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=RiskCode value="RiskCode">
								<Input class="input" readonly name=RiskCodeName value="RiskCodeName">
							</TD>
							
							<TD class=title>
								�ɷѷ�ʽ
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayIntvName value="PayIntvName">
							</TD>
							
							<TD class=title>
								�ɷ���������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYearFlagName value="PayEndYearFlagName">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								�ɷ�����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYear value="PayEndYear">
							</TD>
							
							<TD class=title>
								������������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYearFlagName value="InsuYearFlagName">
							</TD>
							
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYear value="InsuYear">
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Mult value="Mult">
							</TD>
							
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Amnt value="Amnt">Ԫ
							</TD>
							
							<TD class=title>
								���Ѻϼ�
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Prem value="Prem">Ԫ
							</TD>
						</TR>
						
						<TR class=common>
							<TD class=title>
								���ջ�������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=MainPrem value="MainPrem">Ԫ
							</TD>
							
							<TD class=title>
								�ڽ�׷�ӱ���
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AddPrem value="AddPrem">Ԫ
							</TD>
							
							<TD class=title>
								����׷�ӱ���
							</TD>
							<TD class=input>
								<Input class="input" readonly name=FirstAddPrem value="FirstAddPrem">Ԫ
							</TD>
						</TR>
					</table>
				</Div>
				
				<!-- ��������Ϣ����-->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCRisk1Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCRisk1);">
						</td>
						<td class=titleImg>
							��������Ϣ1
						</td>
					</tr>
				</table>
				<Div id="divLCRisk1" style="display: '<%=divLCRisk1%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								��Ʒ����
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=RiskCode1 value="RiskCode1">
								<Input class="input" readonly name=RiskCodeName1 value="RiskCodeName1">
							</TD>														
							
							<TD class=title>
								�ɷ���������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYearFlagName1 value="PayEndYearFlagName1">
							</TD>
							<TD class=title>
								�ɷ�����
							</TD>
							
							<TD class=input>
								<Input class="input" readonly name=PayEndYear1 value="PayEndYear1">
							</TD>			
						</TR>
						
						<TR class=common>
											
							<TD class=title>
								������������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYearFlagName1 value="InsuYearFlagName1">
							</TD>
							
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYear1 value="InsuYear1">
							</TD>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Amnt1 value="Amnt1">Ԫ
							</TD>
							
						</TR>
						
						<TR class=common>							
							
							
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Prem1 value="Prem1">Ԫ
							</TD>
						</TR>
												
					</table>
				</Div>
				
				<!-- ��������Ϣ����-->
				<table>
					<tr>
						<td class=common>							
								<IMG src="../common/images/<%=divLCRisk2Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCRisk2);">
						</td>
						<td class=titleImg>
							��������Ϣ2
						</td>
					</tr>
				</table>
				<Div id="divLCRisk2" style="display: '<%=divLCRisk2%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								��Ʒ����
							</TD>
							<TD class=input>
								<Input class="codeno" readonly name=RiskCode2 value="RiskCode2">
								<Input class="input" readonly name=RiskCodeName2 value="RiskCodeName2">
							</TD>
																			
							<TD class=title>
								�ɷ���������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYearFlagName2 value="PayEndYearFlagName2">
							</TD>
							<TD class=title>
								�ɷ�����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=PayEndYear2 value="PayEndYear2">
							</TD>
						</TR>
						
						<TR class=common>							
							
							<TD class=title>
								������������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=InsuYearFlagName2 value="InsuYearFlagName2">
							</TD>
							
							<TD class=title>
								��������
							</TD>							
							<TD class=input>
								<Input class="input" readonly name=InsuYear2 value="InsuYear2">
							</TD>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Amnt2 value="Amnt2">Ԫ
							</TD>
						</TR>
						
						<TR class=common>							
							
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=Prem2 value="Prem2">Ԫ
							</TD>											
						</TR>
												
					</table>
				</Div>
				
				
				<!-- Ͷ���˻���Ϣ -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divCountImg%>" style="cursor: hand;"
								OnClick="showPage(this,divCount);">
						</td>
						<td class=titleImg>
							Ͷ���˻���Ϣ  	
						</td>
					</tr>
				</table>
				<Div id="divCount" style="display: '<%=divCount%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								׿ԽͶ���˻�
							</TD>
							<TD class=input>
				
								<Input class="input" readonly name=U1ZY value="U1ZY">%
							</TD>
							
							<TD class=title>
								�Ƚ�Ͷ���˻�
							</TD>
							<TD class=input>
							
								<Input class="input" readonly name=U2WJ value="U2WJ">%
							</TD>
							
							<TD class=title>
								����Ͷ���˻�
							</TD>
							<TD class=input>

								<Input class="input" readonly name=U3AX value="U3AX">%
							</TD>
						</TR>
						
						<tr>
							<TD class=title>
								��ȡͶ���˻�
							</TD>
							<TD class=input>
							
								<Input class="input" readonly name=U6JQ value="U6JQ">%
							</TD>
							
							<TD class=title>
								��гͶ���˻�
							</TD>
							<TD class=input>

								<Input class="input" readonly name=U8HX value="U8HX">%
							</TD>
							
							<TD class=title>
								Ͷ�����ڱ�־
							</TD>
							<TD class=input>

								<Input class="input" readonly name=AccTimeFlag value="AccTimeFlag">
							</TD>
						</tr>
						
					</table>
				</div>
				

		<!-- �˻���Ϣ -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
								OnClick="showPage(this,divAccCount);">
						</td>
						<td class=titleImg>
							�˻���Ϣ
						</td>
					</tr>
				</table>
				<Div id="divAccCount" style="display: ''">
					<table class=common>
						<TR class=common>
							<TD class=title>
								�˻�������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AccName value="AccName">
							</TD>
							
							<TD class=title>
								ת���˺�
							</TD>
							<TD class=input>
								<Input class="input" readonly name=AccNo value="AccNo">
							</TD>
						</TR>
					</table>
				</div>

				<!-- ��������Ϣ1 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCBnf1Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCBnf1);">
						</td>
						<td class=titleImg>
							��������Ϣ1
						</td>
					</tr>
				</table>
				<Div id="divLCBnf1" style="display: '<%=divLCBnf1%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfName value="BnfName">
							</TD>
							<TD class=title>
								�Ա�
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfSex value="BnfSex">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfBirthDay value="BnfBirthDay">
							</TD>
						</TR>

						<TR class=common>
								<Input class="input" readonly type="hidden" name=BnfAge value="BnfAge">
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=BnfIDType value="BnfIDType">
								<Input class="input" readonly name=BnfIDTypeName value="BnfIDTypeName">
							</TD>
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfIDNo value="BnfIDNo">
							</TD>
							<TD class=title>
								����˳��
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfGrade value="BnfGrade">
							</TD>
						</TR>

						<TR class=common>
							
						
							<TD class=title>
								�������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfLot value="BnfLot">%
							</TD>

							<TD class=title>
								�뱻���˵Ĺ�ϵ
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfRealToInsured value="BnfRealToInsured">
							</TD>
						</TR>
					</table>
				</Div>
				
				
					<!-- ��������Ϣ2 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCBnf2Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCBnf2);">
						</td>
						<td class=titleImg>
							��������Ϣ2
						</td>
					</tr>
				</table>
				<Div id="divLCBnf2" style="display: '<%=divLCBnf2%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfName2 value="BnfName2">
							</TD>
							<TD class=title>
								�Ա�
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfSex2 value="BnfSex2">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfBirthDay2 value="BnfBirthDay2">
							</TD>
						</TR>

						<TR class=common>
								<Input class="input" type="hidden" readonly name=BnfAge2 value="BnfAge2">
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=BnfIDType2 value="BnfIDType2">
								<Input class="input" readonly name=BnfIDTypeName2 value="BnfIDTypeName2">
							</TD>
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfIDNo2 value="BnfIDNo2">
							</TD>
							<TD class=title>
								����˳��
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfGrade2 value="BnfGrade2">
							</TD>
						</TR>

						<TR class=common>
							
						
							<TD class=title>
								�������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfLot2 value="BnfLot2">%
							</TD>

							<TD class=title>
								�뱻���˵Ĺ�ϵ
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfRealToInsured2 value="BnfRealToInsured2">
							</TD>
						</TR>
					</table>
				</Div>

			
				<!-- ��������Ϣ3 -->
				<table>
					<tr>
						<td class=common>
							<IMG src="../common/images/<%=divLCBnf3Img%>" style="cursor: hand;"
								OnClick="showPage(this,divLCBnf3);">
						</td>
						<td class=titleImg>
							��������Ϣ3
						</td>
					</tr>
				</table>
				<Div id="divLCBnf3" style="display: '<%=divLCBnf3%>'">
					<table class=common>
						<TR class=common>
							<TD class=title>
								����
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfName3 value="BnfName3">
							</TD>
							<TD class=title>
								�Ա�
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfSex3 value="BnfSex3">
							</TD>
							<TD class=title>
								��������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfBirthDay3 value="BnfBirthDay3">
							</TD>
						</TR>

						<TR class=common>
								<Input class="input" type="hidden" readonly name=BnfAge3 value="BnfAge3">
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input type="hidden" readonly name=BnfIDType3 value="BnfIDType3">
								<Input class="input" readonly name=BnfIDTypeName3 value="BnfIDTypeName3">
							</TD>
							<TD class=title>
								֤������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfIDNo3 value="BnfIDNo3">
							</TD>
							<TD class=title>
								����˳��
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfGrade3 value="BnfGrade3">
							</TD>
						</TR>

						<TR class=common>
							
						
							<TD class=title>
								�������
							</TD>
							<TD class=input>
								<Input class="input" readonly name=BnfLot3 value="BnfLot3">%
							</TD>

							<TD class=title>
								�뱻���˵Ĺ�ϵ
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


