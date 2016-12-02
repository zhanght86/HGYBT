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

		<%@include file="ComManageInit.jsp"%>
		<script src="./ComManage.js">
</script>

		<link href="../common/css/Project.css" rel=stylesheet type=text/css />
		<link href="../common/css/mulLine.css" rel=stylesheet type=text/css />

		<title>��������</title>
	</head>

	<body onload="initForm();initElementtype();">
		<form action="./ComManageSave.jsp" method="post" name="fm"
			target="fraSubmit">

			<input type="hidden" name="OperType">
            <input type="hidden" name="iAreaNo">
            <input type="hidden" name="iAreaName">
			<div id="divQuery" style="display: ''">
				<table class="common">
					<tr>
						<td class="titleImg">
							��ѯ������Ϣ��
						</td>
					</tr>
					<TR class=common>
						<TD class=title>
							����������룺</TD>
							<td class=input>
							<input class="input" name="OutComCode" />
						</TD>
						<TD class=title>
							����������ƣ�</TD>
							<td class=input>
							<input class="input" name="Name" />
						</TD>
					</TR>
					<!-- ************ �ڶ��� *********** -->

					<TR class=common>
						<TD class=title>
							��������</TD>
							<td class=input>
							<input class="codeno" name="ComGrade"
								ondblclick="return showCodeList('com_grade',[this,ComGradeName],[0,1],null,null,null,1,null,1);"
								onkeyup="return showCodeListKey('com_grade',[this,ComGradeName],[0,1],null,null,null,1,null,1);" />
							<input class="codename" name="ComGradeName" readonly="true" />
						</TD>
						<TD class=title>
							����������</TD>
							<td class=input>
							<input class="codeno" name="AreaNo"
								ondblclick="return showAreaNo()"
								onkeyup="return showAreaNoKey()">
							<input class="codename" name="AreaName" readonly="readonly" />
						</TD>
					</TR>
					<TR class=common>
						<TD class=title>
							ʡ�ݣ�</TD>
							<td class=input>
							<input class="codeno" name="PrivNo"
								ondblclick="return showPrivNo();"
								onkeyup="return showPrivNokey();" />
							<input class="codename" name="PrivName" readonly="true" />
						</TD>
						<TD class=title>
							������ƣ�</TD>
							<td class=input>
							<input class="input" name="AreaForShort"
								/>
						</TD>
					</TR>
					
					
					
				</table>
			</div>


			<div id="divInsert" style="display: 'none'">
				<table class="common">
					<tr>
						<td class="titleImg">
							���������Ϣ��
						</td>
					</tr>
					
					<TR class=common>
						<TD class=title>
						�����������</TD>
							<td class=input>
						<input class="codeno" name="iComGrade" id="iComGradeId"
							ondblclick="return showComGrade();"
							onkeyup="return showComGradeKey(); " />
						<input class="codename" name="iComGradeName" id="iComGradeNameId" readonly="true" elementtype="nacessary"
							 />
					</TD>
						<TD class=title>
							�ϼ�������</TD>
							<td class=input>
								<input class="codeno" name="UpComCode" id="UpComCodeId"
							ondblclick="return showUpCompany();"
							onkeyup="return showUpCompanyKey();" />
						<input class="codename" name="UpComCodeName" id="UpComCodeNameId" readonly="true" elementtype="nacessary"
							 />
						</TD>
					</TR>
					<TR class=common>
					<input type="hidden" name="iComCode">
						<TD class=title>
							����������룺</TD>
							<td class=input>
							<input class="input" name="iOutComCode" id="iOutComCodeId" maxlength="9"
								style="overflow-x: visible; "
								nacessary"  onkeyup="value=value.replace(/[^\d]/g,'') "
								onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
								elementtype="nacessary" id="iCityNoID" />
							<!--<input class="readonly" readonly="readonly" name="iNoticeName" id="iNoticeId">  -->
						</TD>
						<TD class=title>
							����������ƣ�</TD>
							<td class=input>
							<input class="input" name="iName" maxlength="20"
								elementtype="nacessary" />
						</TD>
					</TR>
					<TD class=title>
							������ƣ�</TD>
						<td class=input>
							<input class="input"  maxlength="5" id=iShortNameId name="iShortName" maxlength="20"
								 />
						</TD>
					</TR>
				</table>
			</div>




			<div id="divCmdButton" style="display: ''">
				<input value="��ѯ����" type=button onclick="queryItems()"
					class="cssButton">
				<INPUT VALUE="���ӻ���" TYPE=button onclick="insertClick()"
					class="cssButton">
				<INPUT VALUE="���»���" TYPE=button onclick="updateClick()"
					class="cssButton">
				<INPUT VALUE="ɾ������" TYPE=button onclick="delItem()"
					class="cssButton">
				<input class="cssButton" value="�� ��" type="reset"
					onclick="resetForm();" />
			</div>

			<div id="divSubCmdButton" style="display: none">
				<INPUT VALUE="ȷ  ��" TYPE=button onclick="okClick()"
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
