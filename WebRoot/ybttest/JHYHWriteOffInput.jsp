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
<SCRIPT src="JHYHWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="JHYHWriteOffInit.jsp"%>
  
<title>银保通新契约测试</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./JHYHWriteOffSave.jsp" method=post name=fm target="fraSubmit">
<table class=common align=center>
	<tr>
		<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id='divTranInfoimg' style="cursor: hand;"
							OnClick="showPage(this,divTranInfo);"></IMG>
						请输入交易信息：
					</td>	
	</tr>
	</table>
	<div id="divTranInfo" style="display:''">
	<table class=common align="center">
<TR class=common>
		<TD class=title>银行端交易日期</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		<TD class=title>交易流水号</TD> 
		<TD class=input><input class=input name=TransRefGUID></TD>
	</TR>
	
	<TR class=common>
		<td class=title>地区代码</td>
		<TD class=input><Input class=input name=RegionCode></TD>
		<td class=title>网点代码</td>
		<TD class=input><Input class=input name=Branch></TD>
		
	</TR>
	
	<TR class=common> 
		<td class=title>柜员代码</td>
		<TD class=input><Input class=input name=Teller></TD>
		<TD class=title>原交易流水号</TD> 
		<TD class=input><input class=input name=OldTransRefGUID></TD>
	</TR>
	</table>
	</div>
	<table class=common align="center">
	<TR>
	<td class="titleImg">
					<IMG src="../common/images/butExpand.gif"  id="divContInfoimg" style="cursor: hand;"
							OnClick="showPage(this,divContInfo);"></IMG>
						请输入保单信息(当日撤单，当日冲正，保单重打)：
					</td>	
	</TR>
	</table>
	<div id="divContInfo" style="display:''">
	<table class=common align="center">
	<TR class=common>
		<TD class=title>保单号</TD>
		<TD class=input>
			<input class=input name=PolNumber id=IdPolNumber maxlength="16" elementtype=nacessary><font color="red">（16位数字）</font>
			<input type=hidden name=ContNo>
		</TD> 
			<TD class=title>险种代码</TD>
	<td class=input>
	<input class=input maxlength="6" name=ProductCode ><font color="red">（6位数字）</font>
	</td>
	</TR>
<tr>
		<TD class=title>单证印刷号</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber id=IdProviderFormNumber elementtype=nacessary >
		</TD>
		<TD class=title>保单金额</TD>
	<td class=input>
	<input class=input name=Prem >
	</td>
	</TR>
</table>
</div>

<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr>
	<TR>
		<td class=title>接收报文ip地址</td>
		<td class=input><Input class=input name=ip></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name=port></td>
		<td class=title>交易码</td>
		<TD class=input>
			<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="WTranFlag(this.options[this.options.selectedIndex].value)">
				<option value="510006" selected="selected" > 当日撤单</option>
				<option value="510016"> 保单重打</option>
				<option value="500208"> 当日冲正</option>
			</select></TD> 
	</tr>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="发送请求交易" onclick="submitForm();"></TD> 
	</TR>
</table>
<hr/>
<table class=common>
	<tr>报文内容</tr>
	<tr>
		<td>
			<textarea rows="30" cols="100" name="xmlContent" id="xmlContent">
			</textarea>
		</td>
	</tr>
</table>
<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
