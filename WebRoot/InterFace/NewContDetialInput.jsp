<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�BankQueryInput.jsp
	//�����ܣ�
	//�������ڣ�1011-10-17
	//������  ��ZHJ
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<SCRIPT src="./NewContDetial.js"></SCRIPT>
		<%@include file="./NewContDetialInit.jsp"%>

		<title>�ӿ��ļ���ϸ��Ϣ</title>
	</head>

	<body onload="initForm();">
		<form action="" method=post name=fm target="fraSubmit">
		<table>
				<tr class=common>
				<input class="cssButton" value="�ر�ҳ��" type=button
					onclick="closePage();" />
		</tr>
			</table>

				<table>
					<tr class=common>
						<td class=titleImg>
							������ϸ
						</td>
					</tr>
				</table>
				
                <table>
					<TR>
					<TD class=title>
							��־��
						</TD>
						<TD class=input>
							<Input class="common" readonly name=IFLog >
						</TD>
						
					<TD class=title>
							�ļ�����
						</TD>
						<TD class=input>
							<Input class="common" readonly name=FileName >
						</TD>
						
					<TD class=title>
							��������
						</TD>
						<TD class=input>
							<Input class="common" readonly name=TranDate >
						</TD>
						
					</TR>
					<TR>
					<TD class=title>
							�������
						</TD>
						<TD class=input>
							<Input class="common" readonly name=DealCout >
						</TD>
					<TD class=title>
							ʧ����Ϣ��
						</TD>
						<TD class=input>
							<Input class="common" readonly name=FailCout >
						</TD>
					<TD class=title>
							��������
						</TD>
						<TD class=input>
							<Input class="common" readonly name=MakeDate >
						</TD>
					</TR>
				</table>
				
					<HR>	
					
			
				<span id="spanQueryGrid"></span>
           
                	
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
		</form>
         
       
		<span id="spanCode" style="display: none; position: absolute;" />
	</body>
</html>