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

		<%@include file="./ITPrintInit.jsp"%>
		<script src="ITPrint.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

		<title>����ʹ�����ͳ�Ʊ�</title>
		
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./ITPrintSave.jsp" method=post name=fm id=fm
			target="fraSubmit" ENCTYPE="multipart/form-data">
			<input type="hidden" name="ManageCodeNoOut">
			<input type="hidden" name="PrintFlag">
			<input type="hidden" name="SelectFlag">
			<table>
				<tr class=common>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divYBT1);"></IMG>
					</td>
					<td class=titleImg>
						������ͳ�Ʊ���
					</td>
				</tr>
			</table>
				<Div id="divYBT1" style="display: ''">
            <table>
                 	<TD width="26%">
						<INPUT VALUE="���ܱ���" class="cssbutton" TYPE=button
							onclick="ITPrintPreviewQuery();">
					</TD>
					<TD width="26%">
						<INPUT VALUE="���ɱ���" class="cssbutton" TYPE=button
							onclick="ITPrintQuery();">
					</TD>
</table>
   </div>
<hr>
			<table >
				<tr class=common>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divYBT);"></IMG>
					</td>
					<td class=titleImg>
						��������������
					</td>
				</tr>
			</table>
			<Div id="divYBT" style="display: ''">
			<table class=common>
			<TR class=common>
      <input type="hidden" name="ManageCodeNo">
		               	<TD class=title>
							���������</TD>
						<td class=input >
							<Input class=input name=ManageCodeName verify="�������|notnull"
								readonly="readonly" >
							<input type=button class="cssButton" value="ѡ��"
								onClick="SelectCom();">
						</TD>
				<TD class=input>
					&nbsp;&nbsp;<input type="hidden" name=ImportPath>
					<Input type="file" name="FileName" size=30>
				</TD>
				<TD width="26%">
				<input class="cssButton" value="��������" name="InAgent" type="button"
					onclick="submitFormIn()" />
					<a href="./Policy_RLS.xls">ģ������
					<IMG src="../common/images/excel.gif"  border="1"
						></IMG></a></TD>
				</TR>
				</TABLE>
			</Div>
			<hr>
			<table>
			<tr class=common>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divCheck);"></IMG>
					</td>
					<td class=titleImg>
						������ѯ
					</td>
				</tr>
			
		</table>
<Div id="divCheck" style="display: ''">
			<table class=common >

				<TR class=common>
						<input class="codeno" name="AreaNo" type="hidden"
							ondblclick="return showAreaNo()" onkeyup="return showAreaNoKey()">
						<input class="codename" name="AreaName" type="hidden" readonly="readonly"
							value="***��������***" />
					</TD>
					<!-- ************CodeSelect ����ѡ���*********** -->
						<input class="codeno" name="CityNo" type="hidden""
							ondblclick="return showCityNo();"
							onkeyup="return showCityNoKey();">
						<input class="codename" name="CityName"  type="hidden" readonly="readonly"
							value="***���г���***" />
					</TD>
				</TR>

				<!-- ************ ��һ�� *********** -->
				<TR class=common>
				<input type="hidden" name="ManageForCheck">
		               	<TD class=title>
							���������</TD>
						<td class=input >
							<Input class=input name=ManageForCheckName verify="�������|notnull"
								readonly="readonly" >
							<input type=button class="cssButton" value="ѡ��"
								onClick="Select();">
						</TD>
					<TD class=title>
						����ϵͳ��</td>
		                <TD class=input>
						<input class="codeno" name="SysFlag"
							ondblclick="return showCodeList('SysFlag_IT',[this,SysFlagName],[0,1],null,null,null,1,null,1);"
							onkeyup="return showCodeListKey('SysFlag_IT',[this,SysFlagName],[0,1],null,null,null,1,null,1);">
						<input class="codename" name="SysFlagName" value="***����ϵͳ***"
							readonly="readonly" />
					</TD>
				</TR>
			<tr class=common>
			<td class=title>
					������:
					</td>
			<td class=input>
			<Input class=input name=ShitNoSta />
			</td>
			<TD class=title>
						������״̬��</td>
		                <TD class=input>
		                <input class="codeno" name="ShitState"
							ondblclick="return showCodeList('shit_state',[this,ShitStateName],[0,1],null,null,null,1,null,1);"
							onkeyup="return showCodeListKey('shit_state',[this,ShitStateName],[0,1],null,null,null,1,null,1);">
						<input class="codename" name="ShitStateName" value="***����״̬***"
							readonly="readonly" />
					</TD>
			</tr>
			</table>
			</div>
			<hr>
			<table>
				<TR class=common>
				<TD width="26%">
						<INPUT VALUE="������ϸ��ѯ" class="cssbutton" TYPE=button
							onclick="ITItemQuery();">
					</TD>
					<TD width="26%">
						<INPUT VALUE="&nbsp;��      ��" TYPE=button class="cssbutton" onclick="resetform()">
					</TD>

				</TR>
			</table>
		
			 	<div id="divGrid" style="display:''">
				<span id="spanQueryGrid"></span>
			</div>	 
			 <div id="divPage" style="display:''">
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
		<span id="spanCode" style="display: none; position: absolute;"></span>
	</body>
</html>
