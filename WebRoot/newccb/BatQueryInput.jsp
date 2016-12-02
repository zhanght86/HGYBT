<%@page contentType="text/html;charset=GBK"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"
	type="text/javascript"></SCRIPT>
<SCRIPT src="BatQuery.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="BatQueryInit.jsp"%>  

		<title>银保通测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./BatQuerySave.jsp" method=post name=fm target="fraSubmit">


<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	
	<TR>
		<td class=title>交易码</td>
		
		<TD class=input><select name="TRANSCODE" style="background-color: #D7E1F6">
			    <option value="P53818102">批量查询</option>
			</select></TD>
		<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name="ip"></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name="port"></td>
		
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button 
		value="发送投保申请" onclick="submitForm();"></TD> 
	</TR>
</table>
<hr/>


<%--*************************交易信息*************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【交易信息】</td>
	</tr> 
	<TR class=common>
		<TD class=title>交易银行</TD> 
		 <TD class=input><Input class="codeno" name=TranCom ondblclick="return showTranCom()"
							onkeyup="return showTranCom()">
							<input  name="TranComName" readonly="readonly" type="text"  size="20"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>服务方流水号</TD>
		<TD class=input><Input class=input name="mSvPt_Jrnl_No"/></TD>
		<TD class=title>交易时间</TD>
		<TD class=title><Input class=input name="mSYS_REQ_TIME"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>建行员工编号</TD>
		<TD class=input><Input class=input name="mCCB_EmpID"/>必填</TD>
		<TD class=title>网点号</TD>
		<TD class=input><Input class=input name="mCCBIns_ID"/>必填</TD>
	</TR>
	<TR class=common>
		<TD class=title>代理保险剩余批量代扣包个数</TD>
		<TD class=input><Input class=input name="mAgInSr_BtWthldBag_Num"/>必填</TD>
		<TD class=title>已处理代扣批量包个数</TD>
		<TD class=input><Input class=input name="mAlrdy_Pcsg_ADdBhBgNum"/>必填</TD>
	</TR>
	<TR class=common>
		<TD class=title>代理保险剩余批量代付包个数</TD>
		<TD class=input><Input class=input name="mAgInSr_BtSbPyBag_Num"/>必填</TD>
		<TD class=title>已处理代付批量包个数</TD>
		<TD class=input><Input class=input name="mAlrdy_Pcsg_SPyBhBgNum"/>必填</TD>
	</TR>
</table>

		
<%--*************页面设定*************--%>				
<table class=common align=center>				
					
	<TR> 
		<td><input class="cssButton" type=Button onClick="initBox()" name="Submit3" value=" 清空信息 " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" 自 动 填 数 " onClick="initInputBox()" /></td>
	</TR>

</table>

 
<hr/>  
<table class=common align=center>
	<TR><TD>报文信息</TD></TR>  
	<TR>
		<td>
			<textarea rows="30" cols="100" name="xmlContent" id="xmlContent">
			
			</textarea>
		</td>
	</TR>  
</table>
<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'></form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
