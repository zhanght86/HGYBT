
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
	<%@page contentType="text/html;charset=GBK"%>
	<head>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<script src="../common/easyQueryVer3/EasyQueryPrint.js"></script>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<SCRIPT src="IF_NewCont.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="IF_NewContInit.jsp"%>

		<title></title>
	</head>
	<body onload="initForm();initElementtype();">
		<form action="./IF_NewContSave.jsp" method=post name=fm
			target="fraSubmit">
			<table>
				<tr class=common>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divYBT);"></IMG>
					</td>
					<td class=titleImg>
						�ӿ��ļ�����
					</td>
				</tr>
			</table>

			<Div id="divYBT" style="display: ''">

				<TR class=common>
				   <TD class=input>&nbsp;��&nbsp;��&nbsp;��&nbsp;�ڣ�</TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <td class=input>
				   <Input class="coolDatePicker"  dateFormat = "short" name=FileDate verify="����|DATE">
				   &nbsp;&nbsp;<input class="cssbutton" type="button" value="�ļ�����"
				onclick="creatClick();" /></TD>		
				</TR>			
				</table>
				<TR>
			</Div>
		
		<HR>		
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divNewCYBT);">
					</td>
					<td class=titleImg>
						�ļ���ѯ
					</td>
				</tr>
			</table>
			<div id="divNewCYBT" style="display: ''">
		<table class=common >
		<TR>
						<TD class=title>
							��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��
							</TD>
				   <td class=input>
							<input class="codeno" name="Area"
								ondblclick="return showAreaNo()"						
								onkeyup="return showAreaNoKey()" style="width: 51px; "/>
							<input class="codename" name="AreaName" value="***��������***" readonly="readonly" style="width: 113px; "/>						
						</TD>
					<td class=title>
						��ʼ�������ڣ�</TD>
				   <td class=input>
						<input class="coolDatePicker" dateFormat="short" name="StartDay"
							elementtype="nacessary" style="width: 144px; "/>
						</TD>
				   <td class=title> ��ֹ�������ڣ�</TD>
				   <td class=input>
						<input class="coolDatePicker" dateFormat="short" name="EndDay"
							elementtype="nacessary" style="width: 126px; "/>
					</td>
				</tr>
		</table>
		</div>
		<table>
		<TR>	 			
			 	<td>
			 			<input class="cssbutton" type="button" value="��&nbsp;&nbsp;&nbsp;ѯ"
				        onclick="areaQueryClick();">				        
			   </td>
			   <td>
			            <input class="cssbutton" type="button" value="������ϸ"
				        onclick="queryDetail();">
			   </td>
			   <td>
			            <input class="cssbutton" type="button" value="�ļ�����"
				        onclick="downloadThis();">
			   </td>				
			</TR>	
	    </table>
			
				
			<span id="spanQueryGrid"></span>
				
				
				<CENTER>
					<INPUT VALUE="��  ҳ" TYPE=button
						onclick="turnPage.firstPage();" class="cssButton">
					<INPUT VALUE="��һҳ" TYPE=button
						onclick="turnPage.previousPage();"
						class="cssButton">
					<INPUT VALUE="��һҳ" TYPE=button
						onclick="turnPage.nextPage();" class="cssButton">
					<INPUT VALUE="β  ҳ" TYPE=button
						onclick="turnPage.lastPage();" class="cssButton">
				</CENTER>
			
			
			<input type=hidden name=hideOperate value=''>
			<input type=hidden name=currentDate value=''>
			<span id="spancode" style="display: none; position: absolute;"></span>
		</form>
	</body>
</html>
