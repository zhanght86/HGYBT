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
<SCRIPT src="NewABCWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="NewABCWriteOffInit.jsp"%>
  
<title>银保通新契约测试</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./NewABCWriteOffSave.jsp" method=post name=fm target="fraSubmit">
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
			 <select name="ProductCode"  style="background-color: #D7E1F6" > 
            	<option value="0006">中韩悦未来年金保险</option>
            	<option value="0005">中韩智赢财富两全保险（分红型）C款</option>
            	<option value="0004">中韩保驾护航两全保险A款</option>
            	<option value="0001">中韩智赢财富两全保险（分红型）A款</option>
                <option value="0002">中韩智赢财富两全保险（分红型）B款</option>
                <option value="0003">中韩卓越财富两全保险（分红型）</option>
            </select>
	</td>
	</TR>
<tr>
		<TD class=title>单证印刷号</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber id=IdProviderFormNumber elementtype=nacessary >
		</TD>
		<TD class=title>原交易流水号</TD>
	<td class=input>
	<input class=input name=OldTransRefGUID >
	</td>
	</TR>
<tr>
		<TD class=title>投保人姓名</TD>
	<td class=input>
	<input class=input name=ApptName ><font color="red">（当日撤单时填写）</font>
	</td>
		<TD class=title>退保金额</TD>
	<td class=input>
	<input class=input name=ReturnAmnt ><font color="red">（当日撤单时填写）</font>
	</td>
	</TR>
<tr>
		<TD class=title>原交易日期</TD>
	<td class=input>
	<Input class="coolDatePicker"   dateFormat = "short" name=OrgTransDate><font color="red">（取消交易时填写）</font>
	</td>
		<TD class=title>原交易代码</TD>
	<td class=input>
				<select name="tranCode"   style="background-color: #D7E1F6" >
				<option selected="selected" value="1004"> 新单缴费</option>
				<option value="1008"> 续期缴费</option>
			</select>
	<font color="red">（取消交易时填写）</font>
	</td>
	</TR>
	<tr>
		<TD class=title>缴费账户</TD>
	<td class=input>
	<input class=input name=PayAcc ><font color="red">（取消交易时填写）</font>
	</td>
		<TD class=title>缴费金额</TD>
	<td class=input>
	<input class=input name=PayAmt ><font color="red">（取消交易时填写）</font>
	</td>
	</TR>
	<tr>
		<TD class=title>原单证印刷号</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber2 id=IdProviderFormNumber2 ><font color="red">（保单重打时填写）</font>
		</TD>
		<TD class=title>投保单号</TD>
		<TD class=input>
			<input class=input name=ProposalPrtNo id=ProposalPrtNo  ><font color="red">（保单重打时填写）</font>
		</TD>
	</TR>
</table>
</div>

<table class="common" align="center">
				<tr>
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divApplyNoimg"  style="cursor: hand;"
							OnClick="showPage(this,divApplyNo);"></IMG>
						新单试算结果查询：
					</td>	
				</tr>
            </table>
            <div id="divApplyNo" style="display:''">
<table class="common" align=center>
	<tr class=common>
	<TD class=title>试算申请顺序号：</TD>
	<td class=input>
	<input class=input name=ApplyNo >
	</td>
	</tr>
</table>
</div>

<table class="common" align="center">
				<tr>
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divDateimg"  style="cursor: hand;"
							OnClick="showPage(this,divDate);"></IMG>
						绿灯交易字段：
					</td>	
				</tr>
            </table>
            <div id="divDate" style="display:''">
<table class="common" align=center>
	<tr class=common>
	<TD class=title>测试字段</TD>
	<td class=input>
	<input class=input name=Reserve >
	</td>
	</tr>
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
				<option selected="selected" value="1000"> 绿灯交易</option>
				<option value="1010"> 当日撤单</option>
				<option value="1009"> 当日冲正</option>
				<option value="1018"> 保单重打</option>
				<option value="1005"> 试算查询</option>
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
