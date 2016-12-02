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
<SCRIPT src="QueryRenewalFee.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="SearchInsuInformationInit.jsp"%>  

		<title>银保通测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./SearchInsuInformationSave.jsp" method=post name=fm target="fraSubmit">


<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	
	<TR>
		<td class=title>交易码</td>
		
		<TD class=input><select name="TRANSCODE" style="background-color: #D7E1F6">
			    <option value="P538191F1">查询保险公司巡点员信息业务</option>
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
		<TD class=input><Input class=input name="mCCB_EmpID"/></TD>
		<TD class=title>网点号</TD>
		<TD class=input><Input class=input name="mCCBIns_ID"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>证件类型代码</TD>
		<TD class=input>
			<select name="mCrdt_TpCd" style="background-color: #D7E1F6">
			    <option value="2999">其他证件（对公）</option>
				<option value="1010">居民身份证</option>
				<option value="1011">临时居民身份证</option>
				<option value="1020">军人身份证件</option>
				<option value="1030">武警身份证件</option>
				<option value="1040">户口簿</option>
				<option value="1052">外国护照</option>
				<option value="1070">港澳居民往来内地通行证</option>
				<option value="1080">台湾居民来往大陆通行证</option>
				<option value="1120">外国人居留证</option>
				<option value="1999">其他证件（个人）</option>
				<option value="2010">营业执照</option>
				<option value="2020">组织机构代码证</option>
				<option value="2030">社会团体法人登记证书</option>
				<option value="2040">事业法人登记证书</option>
				<option value="2090">税务登记证</option>
			</select>
		</TD>
		<TD class=title>保险公司派驻人员姓名</TD>
		<TD class=input><Input class=input name="mIns_Co_Acrdt_Stff_Nm"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>证件号码</TD>
		<TD class=input><Input class=input name="mCrdt_No"/></TD>
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
