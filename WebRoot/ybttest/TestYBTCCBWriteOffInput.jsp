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
<SCRIPT src="TestYBTCCBWriteOff.js"></SCRIPT>


<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="TestYBTCCBWriteOffInit.jsp"%>
  
<title>银保通新契约测试</title>
</head>

<body onload="initElementtype();initForm();">
<form action="./TestYBTCCBWriteOffSave.jsp" method=post name=fm target="fraSubmit">
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
	<TD class=title>预留字段1</TD>
	<td class=input>
	<input class=input name=BkDetail2 >
	</td>
		<TD class=title>预留字段2</TD>
	<td class=input>
	<input class=input name=BkDetail3 >
	</td>
	</tr>
</table>
</div>

<table class="common" align="center">
				<tr>
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divCheckimg" style="cursor: hand;"
							OnClick="showPage(this,divCheck);"></IMG>
						重空核对：
					</td>	
				</tr>
            </table>
            <div id="divCheck" style="display:''">
<table class="common" align=center>
<tr class=common>
		<TD class=title align=center>重空类型</TD>
<TD class=input>
			<select name="CheckCode"   style="background-color: #D7E1F6" >
				<option value="010001000001">保单编码</option>
				<option value="010001000002">现金价值单编码</option>
				<option value="010001000003">批单编码</option>
				<option value="010001000004">发票编码</option>
			</select></TD> 
			</tr>
	<tr class=common>
	<TD class=title>重空起始号</TD>
	<td class=input>
	<input class=input name=BkVchNo1 >
	</td>
		<TD class=title>重空结束号</TD>
	<td class=input>
	<input class=input name=BkVchNo2 >
	</td>
	</tr>
</table>
</div>
<!-- 退保信息传递 -->
<table class="common" align="center">
				<tr class="common" align="center"> 
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divCancelTranforimg" style="cursor: hand;"
							OnClick="showPage(this,divCancelTranfor);"></IMG>
						退保信息传递：
					</td>	
				</tr>
            </table>
            <div id="divCancelTranfor" style="display:''">
<table class="common" align=center >
<tr class=common align="left">
		<TD class=title>退保日期</TD>
	<TD class=input ><Input class="coolDatePicker"   dateFormat = "short" name=TransCancelDate></TD>
	<TD class=title></TD>
	<TD class=input ></TD>
	</tr>
</table>
</div>

<!-- 签约解约交易 -->
<table class="common" align="center">
				<tr class="common" align="center"> 
					<td class="titleImg">
					<IMG src="../common/images/butExpand.gif" id="divSignalimg"  style="cursor: hand;"
							OnClick="showPage(this,divSignal);"></IMG>
						签约解约交易：
					</td>	
				</tr>
            </table>
            <div id="divSignal" style="display:''">
<table class="common" align=center >
<tr class=common >
	<TD class=title>签约保单号</TD>
	<td class=input>
	<input class=input name=ContCodeNo  elementtype=nacessary><font color="red">（签约解约必填）</font>
	</td>
	<TD class=title>代发代扣标志</TD>
	<TD class=input>
			<select name="FSType"  elementtype=nacessary style="background-color: #D7E1F6" >
				<option value="1">代发</option>
				<option value="2">代扣</option>
				<option value="3">代发及代扣</option>
			</select><font color="red">（签约解约必填）</font></TD> 
	</tr>
	<tr class=common >
		<TD class=title>客户姓名（投保人）</TD>
	<td class=input>
	<input class=input name=ClientName elementtype=nacessary><font color="red" >（签约解约必填）</font>
	</td>
	<TD class=title>投保人帐号</TD>
	<td class=input>
	<input class=input name=ClientAccNo ><font color="red">（签约必填）</font>
	</td>
	</tr>
	<tr class=common >
		<TD class=title>批单印刷号</TD>
	<td class=input>
	<input class=input name=MessPrtNo elementtype=nacessary><font color="red">（签约解约必填）</font>
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
				<option selected="selected" value="OPR999"> 绿灯交易</option>
				<option value="OPR911"> 当日撤单</option>
				<option value="SPE802"> 当日冲正</option>
				<option value="SPE002"> 保单重打</option>
				<option value="SPE801">退保交易信息查询</option>
				<option value="VCH102"> 重空交易</option>
				<option value="BAT900"> 批量查询</option>
				<option value="SPE010"> 签约交易</option>
				<option value="SPE013"> 解约交易</option>
			</select></TD> 
	</tr>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="发送投保申请" onclick="submitForm();"></TD> 
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
