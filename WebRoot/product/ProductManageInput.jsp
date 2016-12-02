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

		<title>��Ʒ��ѯ</title>
	</head>

	<body onload="initForm();initElementtype();">
		<form action="./ProductManageSave.jsp" method="post" name="fm"
			target="fraSubmit">

			<input type="hidden" name="OperType">
			
            <div id="divUpdate" style="display: 'none'">
            <table class=common>
            <tr>
            <td class=titleImg>�޸Ĳ�Ʒ��Ϣ��</td>
            </tr>
            <tr>
            <TD class=title>
							����������</TD>
							<td class=input>
							<input class="codeno" name="ProBankCode" readonly="readonly" />		
							<input class="codename" name="UpBank" readonly="readonly" elementtype="nacessary"/>
						</TD>
            <td class=title>��Ʒ���룺</td>
            <td class=input>
            <input class=input name="ProCode" readonly="readonly"  id="ProCodeId" elementtype=nacessary>
            </td>
            </tr>
            <tr>
            <td class=title>AS400���룺</td>
            <td class=input>
            <input class=input name="ASCode" readonly="readonly" id="ASCodeId" elementtype=nacessary>
            </td>
            <td class=title>��Ʒ���ƣ�</td>
            <td class=input>
            <input class=input name="ProName" readonly="readonly" size=35 id="ProNameId" elementtype=nacessary>
            </td>
            </tr>
            <tr>
            <TD class=title>��ʼ���ڣ�</TD>
							<td class=input>
		                    <Input class="coolDatePicker"   dateFormat = "short" name=StaDate elementtype=nacessary>
		                </TD> 
		               <TD class=title>��ֹ���ڣ�</TD>
							<td class=input>
		                    <Input class="coolDatePicker"   dateFormat = "short" name=EndSDate elementtype=nacessary>
		                 </TD> 
            </tr>
            </table>
            <INPUT VALUE="ȷ  ��" TYPE=button onclick="okUpdaClick()"
					class="cssButton">
				<INPUT VALUE="��  ��" TYPE=button onclick="cancelUpdaClick()"
					class="cssButton">
            </div>


			<div id="divQuery" style="display: ''">
				<table class=common >
					<tr>
						<td class="titleImg">
							��ѯ������
						</td>
					</tr>
					<!-- ************ �ڶ��� *********** -->

					<TR >
						<TD class=title>
							����������</TD>
							<TD class=input>
							<input class="codeno" name="BankCode"  
								ondblclick="return showCodeList('trancom_bank',[this,BankName],[0,1],null,null,null,1,null,1);"
								onkeyup="return showCodeListKey('trancom_bank',[this,BankName],[0,1],null,null,null,1,null,1);" />
							<input class="codename" name="BankName" readonly="readonly" />
						</TD>	
						<td class=title>
						��Ʒ״̬��
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
							���ò�Ʒͣ�۵�����
						</td>
					</tr>
					<TD class=title>
							����������</TD>
							<td class=input>
							<input class="codeno" name="iBankCode" readonly="readonly" />		
							<input class="codename" name="iBankName" readonly="readonly" elementtype="nacessary"/>
						</TD>
			<td class=title>��Ʒ���룺</td>
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
							��˾����:
							</TD>
							<td class=input>
							<Input class=input name=iComCodeName verify="��˾����|notnull"
								readonly="readonly" elementtype=nacessary>
							<input type=button class="cssButton" value="ѡ��"
								onClick="SelectCom();">
						</TD>
						<input type="hidden" name="iActivityCode" value="1" maxlength="20"/>
					<td class=title>
						ϵͳ״̬��
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
							AS400���룺</TD>
							<td class=input>
							<input class="codeno" name="iRiskCode" readonly="true"/>
							<input class="codename" name="iRiskName" readonly="true" elementtype="nacessary" style="width: 250px; "/>
						</TD>
					</TR>
				</table>
			</div>

             <div id="divCmdButton" style="display: ''">
				<input value="�� ѯ " type=button onclick="riskQueryItems()"
					class="cssButton">
				<input value="�޸� " type=button onclick="riskUpdateItems()"
					class="cssButton">
				<input class="cssButton" value="�� ��" type="reset"
					onclick="resetForm();" />
				<INPUT VALUE="��Ʒͣ�۵�������" TYPE=button onclick="insertClick()"
					class="cssButton">
			</div>
			<div id="divSubCmdButton" style="display: none">
				<INPUT VALUE="ȷ  ��" TYPE=button onclick="okClick()"
					class="cssButton">
				<INPUT VALUE="ɾ��" TYPE=button onclick="delItem()"
					class="cssButton">
				<INPUT VALUE="��  ��" TYPE=button onclick="cancelClick()"
					class="cssButton">
			</div>

			<div id="divGrid" style="display: ''">
				<span id="spanQueryGrid"></span>
			</div>
			

			<div id="divPage" style="display: ''">
				<center>
					<input class="cssbutton" value="��ҳ" type="Button"
						onclick="turnPage.firstPage();" />
					<input class="cssbutton" value="��һҳ" type="Button"
						onclick="turnPage.previousPage();" />
					<input class="cssbutton" value="��һҳ" type="Button"
						onclick="turnPage.nextPage();" />
					<input class="cssbutton" value="βҳ" type="Button"
						onclick="turnPage.lastPage();" />
				</center>
			</div>
		</form>

		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>
