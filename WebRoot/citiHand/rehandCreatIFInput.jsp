<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%@page contentType="text/html;charset=GBK"%>
	<head>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<SCRIPT src="rehandCreatIF.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="rehandCreatIFInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./rehandCreatIFSave.jsp" method=post name=fm
			target="fraSubmit" ENCTYPE="multipart/form-data">
			<table>
				<tr class=common>
					<td class=titleImg>
						�ӿ��ļ�������
					</td>
				</tr>
			</table>
<hr>
			<Div id="divYBT" style="display: ''">
					<TR class=input>
						<TD class="title">
							&nbsp;�ӿ��ļ����ͣ�</td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;
						<td class="input">
							<input class="codeno" name="FileTypeCode"
								ondblclick="return showFileTypeCode()"
								onkeyup="return showFileTypeCodeKey()"  />
							<input class="codename" name="FileTypeName" readonly="readonly"
								 />
						</td>&ensp;
						&ensp;&ensp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<td class=title>
							�������ϴ���</td>
					
					<TD class=title>
						<input type="hidden" name="ImportPath">
						<Input type="file" name="FileName" size=30 >
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="./RehandCreate_Upload.xls">ģ������
					<IMG src="../common/images/excel.gif" border="1"
						></IMG></a>
				</TD> 					
					</TR>
						<table class=common >
						<tr class=common>
					<td class=titleImg>
						�����ɱ���
					</td>
				</tr>
					<tr class=common>
						<td class=title>
							��ʼ���ڣ�</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="StartDay1"
								elementtype="nacessary" />
							&nbsp;
						</td>
						<td class="title">
							��ֹ���ڣ�</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="EndDay1"
								elementtype="nacessary"  />
						</td>
					</tr>
					<tr class=common>
					<td class=titleImg>
						��������
					</td>
				</tr>
					<tr class=common>
						<td class=title>
							��ʼ���ڣ�</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="StartDay2"
								elementtype="nacessary"  />
						</td>
						<td class="title">
							��ֹ���ڣ�</td>
						<td class="input">
							<input class="coolDatePicker" dateFormat="short" name="EndDay2"
								elementtype="nacessary"  />
						</td>
					</tr>
					</table>
					<table class=common>
					<TR class=common>
						<td class=common>
							<input class="cssbutton" type="button" value="���ɽӿ��ļ�"
								onclick="creatClick();" />
						</td>
					</TR>
				</table>
			</Div>
			<span id="spancode" style="display: none; position: absolute;"/>
		</form>
	</body>
</html>
