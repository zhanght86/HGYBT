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
<SCRIPT src="CGBProCheck.js"></SCRIPT>
<script type="text/javascript">
	
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   
		<%@include file="CGBProCheckInit.jsp"%>  
 
		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();"> 
<form action="./CGBProCheckSave.jsp" method=post name=fm target="fraSubmit">


<%--*************************交易信息*************************--%>
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	<tr>
<td class=title>交易码</td>
<TD class=input readonly=true>
			<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="1013"> 新单核保</option>
			</select></TD>
		
		<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name=ip readonly=true></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name=port readonly=true></td>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="发送投保申请" onclick="submitForm();"></TD> 
	</TR>
</table>
		
<hr>

<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【交易信息】</td>
	</tr> 
	
	<TR class=common>
		<TD class=title>银行端交易日期</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		<TD class=title>交易流水号</TD> 
		<TD class=input><input class=input name=TransRefGUID><input type="hidden" name="InputTransRefGUID"></TD>
		
	</TR>
	
	<TR class=common>
		<td class=title>地区代码</td>
		<TD class=input>
			<input class="codeno" name="RegionCode"
							ondblclick="return showRegionCode()"
							onkeyup="return showRegionCode()">
        	<input  name="RegionCodeName" readonly="readonly" type="text"  size="10"/>
		<td class=title>网点代码</td>
		<TD class=input>
			<input class="codeno" name="Branch"
							ondblclick="return showBranchCode()"
							onkeyup="return showBranchCode()">
        	<input  name="BranchName" readonly="readonly" type="text"  size="20"/>
		</TD>
	</TR>
	
	<TR class=common> 
		<td class=title>柜员代码</td>
		<TD class=input><Input class=input name=Teller readonly=true></TD>
		<td class=title>投保日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=SubmissionDate></TD>
	</TR>
	
	<TR class=common>
		<td class=title>投保书号</td>
		<TD class=input><Input class=input name=HOAppFormNumber id="ProposalNo"></TD>
		<td class=title>保单印刷号</td>
		<TD class=input><Input class=input name=ProviderFormNumber id="PrtNo"></TD>	
	</tr>
	<TR class=common>
		<td class=title>原交易流水号</td>
		<TD class=input><Input class=input name=OriginalTransRefGUID id="OldTransNo"></TD>
	<!--  	<td class=title>原保单印刷号</td>
		<TD class=input><Input class=input name=OriginalProviderFormNumber id="OldPrtNo"></TD>	
	</tr>
		<TR class=common>
		<td class=title>保单号</td>
		<TD class=input><Input class=input name=PolNumber id="ContNo"></TD>
		-->
	</tr>
</table>

<hr>
<%--*************************投保人信息************************--%>
<table class=common align=center >

	<tr>
		<td class=titleImg align=center>【投保人信息】</td>
	</tr>
	
	<TR class=common>
		<td class=title>投保人姓名</td>
		<TD class=input><Input class=input name=AppFullName></TD>
		<td class=title>投保人性别</td>
		<TD class=input>
			<select name="AppGender" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> 男性</option>
				<option value="2"> 女性</option>
				<option value="3"> 其他</option>
			</select></TD>
		
	</tr> 
	
	<TR class=common> 
		<td class=title>投保人证件类型</td>
		<TD class=input>
			<select name="AppGovtIDTC" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 身份证</option>
				<option value="1"> 护照</option>
				<option value="2"> 军官证</option>
				<option value="3"> 士兵证</option>
				<option value="4"> 港澳居民来往大陆通行证</option>
				<option value="5"> 临时身份证</option>
				<option value="6"> 户口本</option>
				<option value="7"> 其他</option>
				<option value="9"> 警官证</option>
			</select></TD>
			
		<td class=title>投保人证件号码</td>
		<TD class=input><Input class=input name=AppGovtID></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人生日</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppBirthDate></TD>
		<td class=title>投保人电子邮箱</td>
		<TD class=input><Input class=input name=AppAddrLine>
		</TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人通讯地址	</td>
		<TD class=input><Input class=input name=AppLine1></TD>
		<td class=title>投保人邮政编码	</td>
		<TD class=input><Input class=input name=AppZip></TD>	
	</tr>
	
	<TR class=common>
		<td class=title>投保人家庭电话</td>
		<TD class=input><Input class=input name=AppDialNumber1></TD>
		<td class=title>投保人移动电话	</td>
		<TD class=input><Input class=input name=AppDialNumber3></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人与被保人的关系</td>
		<TD class=input>
			<select name="AppToInsRelation" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> 配偶关系</option>
				<option value="2"> 父母关系</option>
				<option value="3"> 子女关系</option>
				<option value="4"> 祖父祖母关系</option>
				<option value="5"> 孙子孙女关系</option>
				<option value="6"> 兄弟姐妹关系</option>
				<option value="7"> 其他亲属关系</option>
				<option value="8"> 本人关系</option>
				<option value="9"> 朋友关系</option>	 
			</select></TD>
	</tr>

</table>


<hr>
<%--*************************被保人信息************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【被保人信息】</td>
		<td colspan="2"><input type="checkbox" name=InsFlag value="0" onClick="setInsFlag(this);">
		<font color="red">被保人是否为本人</font></td>
	</tr>
	<TR class=common>
		<td class=title>被保人姓名</td>
		<TD class=input><Input class=input name="InsFullName" id="Ins1"></TD>
		<td class=title>被保人性别</td>
		<TD class=input>
			<select name="InsGender" id="Ins2" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> 男性</option>
				<option value="2"> 女性</option>
				<option value="3"> 其他</option>
			</select></TD>
			<input type="hidden" name = "InsGenderh">
	</tr> 
	
	<TR class=common> 
		<td class=title>被保人证件类型</td>
		<TD class=input>
			<select name="InsGovtIDTC" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 身份证</option>
				<option value="1"> 护照</option>
				<option value="2"> 军官证</option>
				<option value="3"> 士兵证</option>
				<option value="4"> 港澳居民来往大陆通行证</option>
				<option value="5"> 临时身份证</option>
				<option value="6"> 户口本</option>
				<option value="7"> 其他</option>
				<option value="9"> 警官证</option>
			</select></TD>
			<input type="hidden" name = "InsGovtIDTCh">
			<td class=title>被保人证件号码</td>
		<TD class=input><Input class=input id="Ins4" name="InsGovtID"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人生日</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsBirthDate"></TD>
		<td class=title>被保人电子邮箱</td>
		<TD class=input><Input class=input id="Ins6" name="InsAddrLine">
		</TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人通讯地址	</td>
		<TD class=input><Input class=input id="Ins7" name="InsLine1"></TD>
		<td class=title>被保人邮政编码	</td>
		<TD class=input><Input class=input id="Ins8" name="InsZip"></TD>	
	</tr>
	 
	<TR class=common>
		<td class=title>被保人家庭电话</td>
		<TD class=input><Input class=input id="Ins9" name="InsDialNumber1"></TD>
		<td class=title>被保人移动电话	</td>
		<TD class=input><Input class=input id="Ins10" name="InsDialNumber3"></TD>
	</tr>
	 
	<TR class=common>
		<td class=title>健康告知标志</td>  
		<TD class=input> 
			<select name="HealthIndicator" id="Ins11" style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="N"> 无健康告知</option>
						 <option value="Y"> 有健康告知</option>
						</select></TD>
	</tr>

</table>
 

<hr>

<%--*************************受益人信息************************--%>
<table class=common align=center> 
	<tr> 
		<td class=titleImg align=center>【受益人信息】</td>
		<td colspan="2"><input type="checkbox" name=BnfFlag  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator>
		<font color="red">收益人是否为法定(选中为法定)</font></td>
	</tr>    

	<tr >  
		<td>与被保险人关系</td>
		<td>姓名</td>
		<td>性别</td>
		<td>证件类型	</td>
		<td>证件号码	</td>
		<td>受益人生日	</td>
		<td>受益比例</td>
		<td>受益顺序</td>
	</tr>
						
<%--*************受益人1*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> 配偶关系</option>
				<option value="2"> 父母关系</option>
				<option value="3"> 子女关系</option>
				<option value="4"> 祖父祖母关系</option>
				<option value="5"> 孙子孙女关系</option>
				<option value="6"> 兄弟姐妹关系</option>
				<option value="7"> 其他亲属关系</option>
				<option value="8"> 本人关系</option>
				<option value="9"> 朋友关系</option>	  
			</select></td>
		<td><input name="BnfFullName1" type="text" id="BnfReadOnly12" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender1" id="BnfReadOnly13" style="background-color: #D7E1F6">
				<option value="1">男</option>
				<option value="2">女	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 身份证</option>
				<option value="1"> 护照</option>
				<option value="2"> 军官证</option>
				<option value="3"> 士兵证</option>
				<option value="4"> 港澳居民来往大陆通行证</option>
				<option value="5"> 临时身份证</option>
				<option value="6"> 户口本</option>
				<option value="7"> 其他</option>
				<option value="9"> 警官证</option>
			</select></td>
		<td><input name="BnfGovtID1" type="text" id="BnfReadOnly15" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name=BnfBirthDate1 id="BnfReadOnly18" size="10"></TD>
		<td><input name="InterestPercent1" type="text"  id="BnfReadOnly16" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=Sequence1  id="BnfReadOnly17" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
	</tr>
				
<%--*************受益人2*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
				<option value=""></option> 
			    <option value="1"> 配偶关系</option>
				<option value="2"> 父母关系</option>
				<option value="3"> 子女关系</option>
				<option value="4"> 祖父祖母关系</option>
				<option value="5"> 孙子孙女关系</option>
				<option value="6"> 兄弟姐妹关系</option>
				<option value="7"> 其他亲属关系</option>
				<option value="8"> 本人关系</option>
				<option value="9"> 朋友关系</option>	   
			</select></td>
		<td><input name="BnfFullName2" type="text" id="BnfReadOnly22" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1">男</option>
				<option value="2">女	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC2" id="BnfReadOnly24" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 身份证</option>
				<option value="1"> 护照</option>
				<option value="2"> 军官证</option>
				<option value="3"> 士兵证</option>
				<option value="4"> 港澳居民来往大陆通行证</option>
				<option value="5"> 临时身份证</option>
				<option value="6"> 户口本</option>
				<option value="7"> 其他</option>
				<option value="9"> 警官证</option>
			</select></td>
		<td><input name="BnfGovtID2" type="text" id="BnfReadOnly25" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name=BnfBirthDate2 id="BnfReadOnly28" size="10"></TD>
		<td><input name="InterestPercent2" type="text"  id="BnfReadOnly26" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=Sequence2  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
	</tr>
	
<%--*************受益人3*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation3" id="BnfReadOnly31" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> 配偶关系</option>
				<option value="2"> 父母关系</option>
				<option value="3"> 子女关系</option>
				<option value="4"> 祖父祖母关系</option>
				<option value="5"> 孙子孙女关系</option>
				<option value="6"> 兄弟姐妹关系</option>
				<option value="7"> 其他亲属关系</option>
				<option value="8"> 本人关系</option>
				<option value="9"> 朋友关系</option>	  
			</select></td>
		<td><input name="BnfFullName3" type="text" id="BnfReadOnly32" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender3" id="BnfReadOnly33" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1">男</option>
				<option value="2">女	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC3" id="BnfReadOnly34" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 身份证</option>
				<option value="1"> 护照</option>
				<option value="2"> 军官证</option>
				<option value="3"> 士兵证</option>
				<option value="4"> 港澳居民来往大陆通行证</option>
				<option value="5"> 临时身份证</option>
				<option value="6"> 户口本</option>
				<option value="7"> 其他</option> 
				<option value="9"> 警官证</option>
			</select></td>
		<td><input name="BnfGovtID3" type="text" id="BnfReadOnly35" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name=BnfBirthDate3 id="BnfReadOnly38" size="10"></TD>
		<td><input name="InterestPercent3" type="text"  id="BnfReadOnly36" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=Sequence3  id="BnfReadOnly37" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
	</tr>
</table>

<hr>
<%--*************主险信息*************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【主险信息】</td>
	</tr> 
 
	<tr >
		<td>主险代码</td>
		<td>份数</td>
		<td>保费(分)</td>
		<td>保额(分)</td>
		<td>保险年期类型</td>
		<td>保险年期</td>
		<td>缴费年期类型</td>
		<td>缴费年期</td>
	</tr>

	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="ProductCode"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)">
		 		<option value="001">金盛理财全方位二代两全保险（分红型） </option>
            	<option value="003">工银安盛人寿盛世年年年金保险（分红型） </option>
               
            </select></td>
		<td><input name="IntialNumberOfUnits" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode" class="common1">
				<option value=""></option>
				<option value="1"> 保至某确定年领 </option>
                <option value="2"> 年保 </option> 
                <option value="3"> 月保 </option>
                <option value="4"> 日保 </option>
                <option value="5"> 保终身 </option>
                <option value="9"> 其他 </option>
             </select></td>
		<td><input name="Duration" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PaymentDurationMode" class="common1"0>
				<option value=""></option>
				<option value="0"> 季缴 </option>
                <option value="1"> 缴至某确定年龄 </option>
                <option value="2"> 年缴 </option>
                <option value="3"> 月缴 </option>
                <option value="4"> 日缴 </option>
                <option value="5"> 趸缴 </option>
                <option value="6"> 终缴费 </option>
                <option value="7"> 不定期缴 </option>
                <option value="8"> 半年缴 </option>
                <option value="9"> 其他 </option>
             </select></td>
		<td><input name="PaymentDuration" type="text"  size="5" maxlength="5"  /></td>
	</tr>
				
</table>
	

<%--*************附加险101*************--%>	
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【附加险1】</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fRiskCode1"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList1);" ></td>
	</tr>
</table> 

<div id="divRiskList1" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList1"></span></td>
	
	<tr>
		<td>附加险代码</td>
		<td>份数</td>
		<td>保费</td>
		<td>保额</td>
		<td>保险年期类型</td>
		<td>保险年期</td>
		<td>缴费年期类型</td>
		<td>缴费年期</td>
	</tr> 

<%--*************附加险1*************--%>	
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="ProductCode2"  style="background-color: #D7E1F6" onchange="SSRiskFlag2(this.options[this.options.selectedIndex].value)">
					<option value=""></option>
                <option value="101">金盛附加理财全方位二代投资连结保险 (首期追加)</option>
            </select></td>
		<td><input name="IntialNumberOfUnits2" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt2" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt2" type="text"  size="10"/></td>
		<td>    
			<select name="DurationMode2" class="common1">
				<option value=""></option>
				<option value="1"> 保至某确定年领 </option>
                <option value="2"> 年保 </option>
                <option value="3"> 月保 </option>
                <option value="4"> 日保 </option>
                <option value="5"> 保终身 </option>
                <option value="9"> 其他 </option>
             </select></td>
		<td><input name="Duration2" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PaymentDurationMode2" class="common1"0>
				<option value=""></option>
				<option value="0"> 季缴 </option>
                <option value="1"> 缴至某确定年龄 </option>
                <option value="2"> 年缴 </option>
                <option value="3"> 月缴 </option>
                <option value="4"> 日缴 </option>
                <option value="5"> 趸缴 </option>
                <option value="6"> 终缴费 </option>
                <option value="7"> 不定期缴 </option>
                <option value="8"> 半年缴 </option>
                <option value="9"> 其他 </option>
             </select></td>
		<td><input name="PaymentDuration2" type="text"  size="5" maxlength="5"  /></td>
	</tr>	
</table>
</div>




<%--*************附加险*************--%>	
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【附加险2】</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fRiskCode"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList);" ></td>
	</tr>	
</table> 

<div id="divRiskList" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList"></span></td>
	
	<tr>
		<td>附加险代码</td>
		<td>份数</td>
		<td>保费</td>
		<td>保额</td>
		<td>保险年期类型</td>
		<td>保险年期</td>
		<td>缴费年期类型</td>
		<td>缴费年期</td>
	</tr> 

<%--*************附加险1*************--%>	
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="ProductCode1"  style="background-color: #D7E1F6" onchange="SSRiskFlag(this.options[this.options.selectedIndex].value)">
					<option value=""></option>
                <option value="102">金盛附加理财全方位二代投资连结保险(定期追加) </option>
            </select></td>
		<td><input name="IntialNumberOfUnits1" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt1" type="text"  size="10"/></td>
		<td>    
			<select name="DurationMode1" class="common1">
				<option value=""></option>
				<option value="1"> 保至某确定年领 </option>
                <option value="2"> 年保 </option>
                <option value="3"> 月保 </option>
                <option value="4"> 日保 </option>
                <option value="5"> 保终身 </option>
                <option value="9"> 其他 </option>
             </select></td>
		<td><input name="Duration1" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PaymentDurationMode1" class="common1"0>
				<option value=""></option>
				<option value="0"> 季缴 </option>
                <option value="1"> 缴至某确定年龄 </option>
                <option value="2"> 年缴 </option>
                <option value="3"> 月缴 </option>
                <option value="4"> 日缴 </option>
                <option value="5"> 趸缴 </option>
                <option value="6"> 终缴费 </option>
                <option value="7"> 不定期缴 </option>
                <option value="8"> 半年缴 </option>
                <option value="9"> 其他 </option>
             </select></td>
		<td><input name="PaymentDuration1" type="text"  size="5" maxlength="5"  /></td>
	</tr>	
</table>
</div>

	
	
<%--*************附加险*************--%>	
<table class=common align=center>

	<tr> 
		<td class=titleImg align=center>【投资账户】</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="AccountFlag"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList2);"></td>
	</tr>	 
</table> 
<div id="divRiskList2" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList"></span></td>
	</tr>
	<tr>
		<td>投资账户1</td>
		<td>投资比率</td>
		<td>投资账户2</td>
		<td>投资比率</td>
		<td>投资账户3</td>
		<td>投资比率</td>
	</tr>

<%--*************附加险1*************--%>	
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="AccCode1"  style="background-color: #D7E1F6">
					<option value=""></option>
                <option value="U1ZY">卓越投资账户 </option>
                <option value="U2WJ">稳健投资账户 </option>
                <option value="U3AX">安心投资账户 </option>
            </select></td>
		<td bgColor="#F7F7F7"><input name="AllocPercent1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7">
			<select name="AccCode2"  style="background-color: #D7E1F6">
					<option value=""></option>
                <option value="U1ZY">卓越投资账户 </option>
                <option value="U2WJ">稳健投资账户 </option>
                <option value="U3AX">安心投资账户 </option>
            </select></td>
		<td bgColor="#F7F7F7"><input name="AllocPercent2" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7">
			<select name="AccCode3"  style="background-color: #D7E1F6">
					<option value=""></option>
                <option value="U1ZY">卓越投资账户 </option>
                <option value="U2WJ">稳健投资账户 </option>
                <option value="U3AX">安心投资账户 </option>
            </select></td>
		<td bgColor="#F7F7F7"><input name="AllocPercent3" type="text"  size="10"/></td>
		
	</tr>	
</table>
</div>	
	
	
	
	<hr>
<%--*************投保信息*************--%>		
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【投保信息】</td>
	</tr>
	 
	<TR class=common>
		<td class=title>缴费方式</td>
		<TD class=input>  
			<select name=PaymentMode class="common1">
				<option value=""></option>
				<option value="1"> 年缴 </option>
                <option value="2"> 月缴 </option>
                <option value="3"> 半年缴 </option>
                <option value="4"> 季缴 </option>
                <option value="5"> 趸缴 </option>
                <option value="6"> 不定期缴 </option>
                <option value="9"> 其他 </option>
             </select></TD>	
        <td class=title>保单传送方式</td>
		<TD class=input>
			<select name=PolicyDeliveryMethod class="common1">
				<option value=""></option>
                <option value="4"> 银行领取 </option>
             </select></TD>
	</tr>
<tr>

             <td class=title>首次额外追加保费(分)</td>
             <td bgColor="#F7F7F7"><input name="FirstSuperaddAmt" type="text"  size="10" /></td>

		
        <td class=title>银行账号</td>
		<TD class=input><Input class=input name=AccountNumber></TD>
	</tr>
	
	<TR class=common> 
		<td class=title>职业告知标志</td>
		<TD class=input>
			<select name=OccupationIndicator class="common1">
				<option value="N">无职业告知</option>
				<option value="Y">有职业告知</option>           
             </select></TD>
             <td class=title>投资日期标志</td>
		<TD class=input>
			<select name=InvestDateInd class="common1">
				<option value=""></option>
				<option value="1">投保次日</option>
				<option value="2">犹豫期后</option>           
             </select></TD>
		</tr>
		

</table>
		<hr>
<%--*************页面设定*************--%>				
<table class=common align=center>				
					
	<tr> 
		<td><input class="cssButton" type=Button onClick="initBox()" name="Submit3" value=" 清空信息 " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" 自 动 填 数 " onClick="initInputBox()" /></td>
	</tr>

</table>

<hr/>  
<table class=common align=center> 
	<tr>报文信息</tr>   
	<tr>
		<td>
			<textarea rows="30" cols="100" name="xmlContent" id="xmlContent">
			
			</textarea>
		</td>
	</tr> 
</table>
<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'></form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
