<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
	

<SCRIPT src="TestYBTEdor.js"></SCRIPT>
<script type="text/javascript"></script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestYBTEdorfInit.jsp"%>  

		<title>银保通保全测试</title>
	</head>

<body onload="initElementtype();initForm();">
<form action="./TestYBTEdorSave.jsp" method=post name=fm target="fraSubmit">
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>请输入交易信息：</td>
	</tr>
<TR class=common>
		<TD class=title>银行端交易日期</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		
		
		<TD class=title>交易流水号</TD> 
		<TD class=input><input class=input name=TransNo></TD>
	</TR>
	
	<TR class=common>
		<td class=title>地区代码</td>
		<TD class=input><Input class=input name="ZoneNo"></TD>
		<td class=title>网点代码</td>
		<TD class=input><Input class=input name="NodeNo"></TD>
		
	</TR>
	
	
	<TR>
		<TD class=titleImg align=center>请输入保单信息：</TD>
	</TR>
	<TR class=common>
		
		<TD class=title>保单号</TD>  
		<TD class=input>
			<input class=input name=PolNumber></font>
		</TD>
		
		<TD class=title>单证印刷号</TD>
		<TD class=input>
			<input class=input name=ProviderFormNumber>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>银行账户</TD>
		<TD class=input>
			<input class=input name=AccountNumber>
		</TD> 
		
       <TD class=title><span id="AccountNameTitle" style="display:''">账户姓名</span></TD>  
		<TD class=input>
			<span id="AccountName" style="display:''"><input class=input name=AccountForName></span></font>
		</TD>
	 
		
	</TR>
	<tr>
	 <TD class=title><span id="CortransrnoName" style="display:'none'">原交易流水号（冲正使用）</span></TD>  
		<TD class=input>
			<span id="Cortransrno" style="display:'none'"><input class=input name=CortransrnoNo></span></font>
		</TD>
	</tr>
	
	<TR>
		<TD class=titleImg align=center><span id="appntInfoTitle" style="display:''">投保人信息：</span></TD>
	</TR>
	<TR class=common>
		<TD class=title><span id="appntnameTitle" style="display:''">投保人姓名</span></TD>  
		<TD class=input>
			<span id="appntnameInput" style="display:''"><input class=input name=AppntName></span></font>
		</TD>
		<td class=title><span id="appntIDTypeTitle" style="display:''">投保人证件类型</span></td>
		<TD class=input><span id="appntIDTypeInput" style="display:''">
			<select name="AppGovtIDTC" style="background-color: #D7E1F6">
			    <option value=""></option>
			    <option value="0"> 身份证</option>
				<option value="1"> 护照</option>
				<option value="2"> 军官证</option>
				<option value="3"> 士兵证</option>
				<option value="4"> 回乡证</option>
				<option value="5"> 临时身份证</option>
				<option value="6"> 户口本</option>
				<option value="7"> 其他</option>
				<option value="9"> 警官证</option>
			</select></span></TD>
			
		<td class=title><span id="appntIDTitle" style="display:''">投保人证件号码</span></td>
		<TD class=input><span id="appntIDInput" style="display:''"><Input class=input name=AppGovtID></TD>
	<TD class=input>
		</TD>
		<TD class=input>
		</TD>
	</TR>
	
	<TR>
		<TD class=titleImg align=center><span id="insureInfoTitle" style="display:''">被保人信息：</span></TD>
	</TR>
	<TR class=common>
		<TD class=title><span id="insurenameTitle" style="display:''">被保人姓名</span></TD>  
		<TD class=input>
			<span id="insurenameInput" style="display:''"><input class=input name=InsureName></span></font>
		</TD>
		<td class=title><span id="insureIDTypeTitle" style="display:''">被保人证件类型</span></td>
		<TD class=input><span id="insureIDTypeInput" style="display:''">
			<select name="InsureGovtIDTC" style="background-color: #D7E1F6">
	     		<option value=""></option>
			    <option value="0"> 身份证</option>
				<option value="1"> 护照</option>
				<option value="2"> 军官证</option>
				<option value="3"> 士兵证</option>
				<option value="4"> 回乡证</option>
				<option value="5"> 临时身份证</option>
				<option value="6"> 户口本</option>
				<option value="7"> 其他</option>
				<option value="9"> 警官证</option>
			</select></span></TD>
			
		<td class=title><span id="insureIDTitle" style="display:''">被保人证件号码</span></td>
		<TD class=input><span id="insureIDInput" style="display:''"><Input class=input name=InsureGovtID></TD>
	<TD class=input>
		</TD>
		<TD class=input>
		</TD>
	</TR>
		
	</TR>
</table>

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
			<select name="TranType"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
				<option value="1003"> 犹豫期退保查询</option>
				<option value="1004"> 犹豫期退保</option>
				<option value="1030">犹豫期退保冲正</option>
				<option value="1021">退保查询</option>
				<option value="1017">退保</option>
				<option value="1026">退保冲正</option>
			</select></TD>
			
			
		<td class=title>交易渠道</td>  
		<td class=input> 
			<select name=saleChannel style="background-color:#D7E1F6"  class="common1" >
			    <option value=""></option>
				<option value="0" selected="selected"> 柜面出单</option>
         		<option value="1"> 网银出单 </option>
         		<option value="8"> 自助终端 </option>
             </select>
		</td>     
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="发送交易申请" onclick="submitForm();"></TD> 
	</TR>
</table>
<table class=common align = center>
	<tr>返回信息</tr>
	<tr>
		<td>
			<textarea rows="30" cols="100" name="xmlBackContent" id="xmlBackContent">
			
			</textarea>
		</td>
	</tr>
</table>


<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'>
</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
