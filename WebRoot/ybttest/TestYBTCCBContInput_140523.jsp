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
<SCRIPT src="TestYBTCCBCont.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestYBTCCBContInit.jsp"%>  

		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./TestYBTCCBSave.jsp" method=post name=fm target="fraSubmit">


<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	
	<TR>
		<td class=title>交易码</td>
		<td class=input> 
		<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="OPR001">新单核保</option>
			    <option value="OPR011">新单出单</option>
			</select></TD>
		<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name=ip></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name=port></td>
		
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
		<TD class=title>银行端交易日期</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=TransExeDate></TD>
		<TD class=title>交易流水号</TD> 
		<TD class=input><input class=input name=TransRefGUID></TD>
	</TR>
	
	<TR class=common>
        	<td class=title>地区代码</td>
        	<td> <input  name="RegionCode" />
		</TD>
		<td class=title>网点代码</td>
		<TD class=input>
			<input name="Branch">
			</TD>
        	
	</TR>
	
	<TR class=common> 
		<td class=title>柜员代码</td>
		<TD class=input><Input class=input name=Teller></TD>
		<td class=title>投保日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=SubmissionDate></TD>
	</TR>
	
	<TR class=common>
		<td class=title>投保书号</td>
		<TD class=input><Input class=input name="HOAppFormNumber" maxlength="16" id="HOAppFormNumber"></TD>
		<td class=title>保单印刷号</td>
		<TD class=input><Input class=input name="ProviderFormNumber" maxlength="16" id="ProviderFormNumber"></TD>	
	</TR>
	<TR class=common>
		<td class=title>销售人员工号</td>
		<TD class=input><Input class=input name="BkRckrNo" maxlength="16" id="BkRckrNo"></TD>
		<td class=title>销售人员姓名</td>
		<TD class=input><Input class=input name="BkSaleName" maxlength="16" id="BkSaleName"></TD>	
	</TR>
	<TR class=common>
		<td class=title>原交易流水号</td>
		<TD class=input><Input class=input name="ReqsrNo" id="OldTranNo"><input type="hidden" name="InputTransrNo"></TD>
	<td class=common >
	<input name="TransMode" type="hidden" style="background-color: #D7E1F6">
		</TD>
			<td class=input >
			<input class=input type="hidden" name=BranthCmp>
			</td>
	</tr>
	<TR class=common>
		<td class=title>销售人员资格证号</td>
		<TD class=input><Input class=input name="BkSaleCertNo" maxlength="16" id="BkSaleCertNo"></TD>
    </TR>
</table>


<%--*************************投保人信息************************--%>
<table class=common align=center>

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
			</select></TD>
		
	</tr> 
	
	<TR class=common> 
		<td class=title>投保人证件类型</td>
		<TD class=input>
			<select name="AppGovtIDTC" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="A"> 公民身份证号码</option>
				<option value="B"> 军官证</option>
				<option value="C"> 解放军文职干部证</option>
				<option value="D"> 警官证</option>
				<option value="E"> 解放军士兵证</option>
				<option value="F"> 户口簿</option>
				<option value="G"> (港澳)回乡证及通行证</option>
				<option value="H"> 台通行证及其他有效旅行证</option>
				<option value="I">(外国)护照</option>
				<option value="J"> (中国)护照</option>
				<option value="K">武警文职干部证</option>
				<option value="L"> 武警士兵证</option>
				<option value="M">驾照</option>
				<option value="Z"> 其他</option>
			</select></TD>
			
		<td class=title>投保人证件号码</td>
		<TD class=input><Input class=input name=AppGovtID></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人生日</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppBirthDate></TD>
		<td class=title>投保人证件有效期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppGovtTermDate></TD>
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
		<td class=title>投保人与被保人关系</td>
		<TD class=input>
			<select name="AppToInsRelation" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> 本人</option>
				<option value="2"> 丈夫 </option>
				<option value="3"> 妻子 </option>
				<option value="4"> 父亲 </option>
				<option value="5">母亲 </option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿 </option>
				<option value="8">祖父 </option>
				<option value="9">祖母</option>	 
				 <option value="10"> 孙子</option>
				<option value="11"> 孙女</option>
				<option value="12"> 外祖父</option>
				<option value="13"> 外祖母</option>
				<option value="14"> 外孙</option>
				<option value="15"> 外孙女</option>
				<option value="16"> 哥哥</option>
				<option value="17"> 姐姐</option>
				<option value="18"> 弟弟</option>	 
				 <option value="19"> 妹妹</option>
				<option value="20"> 公公</option>
				<option value="21"> 婆婆</option>
				<option value="22"> 儿媳</option>
				<option value="23"> 岳父</option>
				<option value="24"> 岳母</option>
				<option value="25"> 女婿</option>
				<option value="26"> 其它亲属 </option>
				<option value="27"> 同事 </option>	 
				 <option value="28"> 朋友 </option>
				<option value="29"> 雇主</option>
				<option value="30"> 其它 </option>
			</select></TD>
			<td class=title>投保人电子邮箱</td>
		<TD class=input><Input class=input name=AppAddrLine></TD>
	</tr>
	<tr class=common>
		<td class=title>投保人国籍</td>
	<td bgColor="#F7F7F7" >
		<select name="AppCountry" id="AppCountry" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			  <option value="0156">中国</option>
				<option value="--"> 其他</option>
				</select>
				</td>
<td class=title>投保人职业代码</td>  
		<TD class=input> 
			<select name="ApplJobCode"  style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="3010101"> 一般内勤</option>
						 
						 
			</select>
		</TD>	
	</tr>
	
	<TR class=common>
		<td class=title>投保人单位地址</td>
		<TD class=input><Input class=input name=AppWorkAddress></TD>
		<td class=title>投保人单位邮编</td>
		<TD class=input><Input class=input name=AppWorkZipCode></TD>
	</tr>
	<TR class=common>
		<td class=title>投保人单位电话</td>
		<TD class=input><Input class=input name=WorkPhone></TD>
	</tr>
	<TR class=common>
		<td class=title>投保人年收入(元)</td>
		<TD class=input><Input class=input name=PbInCome></TD>
		<td class=title>投保人家庭年收入</td>
		<TD class=input><Input class=input name=PbHomeInCome></TD>
	</tr>
	<TR class=common>
		<td class=title>投保人居民类型</td>
		<TD class=input>
		<select name="PbDenType" id="PbDenType" style="background-color: rgb(215, 225, 246);"> 
			<option value=""></option>
			    <option value="1">城镇</option>
				<option value="2">农村</option>
			</select>
		</TD>
	</tr>
</table>



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
			</select></TD>
			<input type="hidden" name = "InsGenderh">
	</tr> 
	
	<TR class=common> 
		<td class=title>被保人证件类型</td>
		<TD class=input>
			<select name="InsGovtIDTC" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
				    <option value="A"> 公民身份证号码</option>
				<option value="B"> 军官证</option>
				<option value="C"> 解放军文职干部证</option>
				<option value="D"> 警官证</option>
				<option value="E"> 解放军士兵证</option>
				<option value="F"> 户口簿</option>
				<option value="G"> (港澳)回乡证及通行证</option>
				<option value="H"> 台通行证及其他有效旅行证</option>
				<option value="I">(外国)护照</option>
				<option value="J"> (中国)护照</option>
				<option value="K">武警文职干部证</option>
				<option value="L"> 武警士兵证</option>
				<option value="M">驾照</option>
				<option value="Z"> 其他</option>
			</select></TD>
			<input type="hidden" name = "InsGovtIDTCh">
		<td class=title>被保人证件号码</td>
		<TD class=input><Input class=input id="Ins4" name="InsGovtID"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人生日</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsBirthDate"></TD>

		<td class=title>被保人证件有效期</td>
		<TD class=input><Input class="coolDatePicker" id="Ins12" dateFormat="short" name=InsGovtTermDate></TD>
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
						 <option value="0"> 无健康告知</option>
						 <option value="1"> 有健康告知</option>
						</select></TD>
	<td class=title>被保人电子邮箱</td>
		<TD class=input><Input class=input id="Ins6" name="InsAddrLine"></TD>
	</tr>
	<tr class=common>
	<td class=title>被保人国籍</td>
	<td bgColor="#F7F7F7" >
		<select name="InsCountry" id="InsCountry" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			  <option value="0156"> 中国</option>
				<option value="--"> 其他</option>
				</select>
				</td>
<td class=title>未成年被保险人在其他保险公司累计身故保额(元)</td>
	<TD class=input><Input class=input name=InsCovSumAmt></TD>
	</tr>
		<tr class=common>
		
<td class=title>被保人职业代码</td>  
		<TD class=input> 
			<select name="InsuJobCode"  id="Ins13" style="background-color: #D7E1F6">  
						 <option value=""></option>
						<option value="3010101"> 一般内勤</option>
						 
			</select>
		</TD>	
	</tr>
</table>
 



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
		<td>受益人通讯地址</td>
		<td>受益人生日	</td>
		<td>受益比例</td>
		<td>受益顺序</td>
		<td>受益人类型</td>
	</tr>
						
<%--*************受益人1*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);"> 
	<option value=""></option>
			    <option value="1"> 本人</option>
				<option value="2"> 丈夫 </option>
				<option value="3"> 妻子 </option>
				<option value="4"> 父亲 </option>
				<option value="5">母亲 </option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿 </option>
				<option value="8">祖父 </option>
				<option value="9">祖母</option>	 
				 <option value="10"> 孙子</option>
				<option value="11"> 孙女</option>
				<option value="12"> 外祖父</option>
				<option value="13"> 外祖母</option>
				<option value="14"> 外孙</option>
				<option value="15"> 外孙女</option>
				<option value="16"> 哥哥</option>
				<option value="17"> 姐姐</option>
				<option value="18"> 弟弟</option>	 
				 <option value="19"> 妹妹</option>
				<option value="20"> 公公</option>
				<option value="21"> 婆婆</option>
				<option value="22"> 儿媳</option>
				<option value="23"> 岳父</option>
				<option value="24"> 岳母</option>
				<option value="25"> 女婿</option>
				<option value="26"> 其它亲属 </option>
				<option value="27"> 同事 </option>	 
				 <option value="28"> 朋友 </option>
				<option value="29"> 雇主</option>
				<option value="30"> 其它 </option>
			</select></td>
		<td><input name="BnfFullName1" type="text" id="BnfReadOnly12" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender1" id="BnfReadOnly13" style="background-color: #D7E1F6">
			 <option value="1"> 男性</option>
				<option value="2"> 女性</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value=""></option>
			  <option value="A"> 公民身份证号码</option>
				<option value="B"> 军官证</option>
				<option value="C"> 解放军文职干部证</option>
				<option value="D"> 警官证</option>
				<option value="E"> 解放军士兵证</option>
				<option value="F"> 户口簿</option>
				<option value="G"> (港澳)回乡证及通行证</option>
				<option value="H"> 台通行证及其他有效旅行证</option>
				<option value="I">(外国)护照</option>
				<option value="J"> (中国)护照</option>
				<option value="K">武警文职干部证</option>
				<option value="L"> 武警士兵证</option>
				<option value="M">驾照</option>
				<option value="Z"> 其他</option>
			</select></td>
		<td><input name="BnfGovtID1" type="text" id="BnfReadOnly15" class=common/></td>
		<TD><Input class="input"  name="BnfAdress1" ></TD>
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
		<td bgColor="#F7F7F7"> 
			<select name=BenefiType1  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0" >生存受益人</option>
				<option value="1" >死亡受益人</option>
				<option value="2" >红利受益人</option>
			</select>					
		</td>
	</tr>
				
<%--*************受益人2*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
		<option value=""></option>
			    <option value="1"> 本人</option>
				<option value="2"> 丈夫 </option>
				<option value="3"> 妻子 </option>
				<option value="4"> 父亲 </option>
				<option value="5">母亲 </option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿 </option>
				<option value="8">祖父 </option>
				<option value="9">祖母</option>	 
				 <option value="10"> 孙子</option>
				<option value="11"> 孙女</option>
				<option value="12"> 外祖父</option>
				<option value="13"> 外祖母</option>
				<option value="14"> 外孙</option>
				<option value="15"> 外孙女</option>
				<option value="16"> 哥哥</option>
				<option value="17"> 姐姐</option>
				<option value="18"> 弟弟</option>	 
				 <option value="19"> 妹妹</option>
				<option value="20"> 公公</option>
				<option value="21"> 婆婆</option>
				<option value="22"> 儿媳</option>
				<option value="23"> 岳父</option>
				<option value="24"> 岳母</option>
				<option value="25"> 女婿</option>
				<option value="26"> 其它亲属 </option>
				<option value="27"> 同事 </option>	 
				 <option value="28"> 朋友 </option>
				<option value="29"> 雇主</option>
				<option value="30"> 其它 </option>
			</select></td>
		<td><input name="BnfFullName2" type="text" id="BnfReadOnly22" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
				  <option value="1"> 男性</option>
				<option value="2"> 女性</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC2" id="BnfReadOnly24" style="background-color: #D7E1F6">
		<option value=""></option>
			  <option value="A"> 公民身份证号码</option>
				<option value="B"> 军官证</option>
				<option value="C"> 解放军文职干部证</option>
				<option value="D"> 警官证</option>
				<option value="E"> 解放军士兵证</option>
				<option value="F"> 户口簿</option>
				<option value="G"> (港澳)回乡证及通行证</option>
				<option value="H"> 台通行证及其他有效旅行证</option>
				<option value="I">(外国)护照</option>
				<option value="J"> (中国)护照</option>
				<option value="K">武警文职干部证</option>
				<option value="L"> 武警士兵证</option>
				<option value="M">驾照</option>
				<option value="Z"> 其他</option>
			</select></td>
		<td><input name="BnfGovtID2" type="text" id="BnfReadOnly25" class=common/></td>
		<TD><Input class="input"  name="BnfAdress2" ></TD>
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
		<td bgColor="#F7F7F7"> 
			<select name=BenefiType2  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0" >生存受益人</option>
				<option value="1" >死亡受益人</option>
				<option value="2" >红利受益人</option>
			</select>					
		</td>
	</tr>
	
<%--*************受益人3*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation3" id="BnfReadOnly31" style="background-color: rgb(215, 225, 246);"> 
		<option value=""></option>
			    <option value="1"> 本人</option>
				<option value="2"> 丈夫 </option>
				<option value="3"> 妻子 </option>
				<option value="4"> 父亲 </option>
				<option value="5">母亲 </option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿 </option>
				<option value="8">祖父 </option>
				<option value="9">祖母</option>	 
				 <option value="10"> 孙子</option>
				<option value="11"> 孙女</option>
				<option value="12"> 外祖父</option>
				<option value="13"> 外祖母</option>
				<option value="14"> 外孙</option>
				<option value="15"> 外孙女</option>
				<option value="16"> 哥哥</option>
				<option value="17"> 姐姐</option>
				<option value="18"> 弟弟</option>	 
				 <option value="19"> 妹妹</option>
				<option value="20"> 公公</option>
				<option value="21"> 婆婆</option>
				<option value="22"> 儿媳</option>
				<option value="23"> 岳父</option>
				<option value="24"> 岳母</option>
				<option value="25"> 女婿</option>
				<option value="26"> 其它亲属 </option>
				<option value="27"> 同事 </option>	 
				 <option value="28"> 朋友 </option>
				<option value="29"> 雇主</option>
				<option value="30"> 其它 </option>
			</select></td>
		<td><input name="BnfFullName3" type="text" id="BnfReadOnly32" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender3" id="BnfReadOnly33" style="background-color: #D7E1F6">
				<option value=""></option>
				 <option value="1"> 男性</option>
				<option value="2"> 女性</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC3" id="BnfReadOnly34" style="background-color: #D7E1F6">
				<option value=""></option>
			  <option value="A"> 公民身份证号码</option>
				<option value="B"> 军官证</option>
				<option value="C"> 解放军文职干部证</option>
				<option value="D"> 警官证</option>
				<option value="E"> 解放军士兵证</option>
				<option value="F"> 户口簿</option>
				<option value="G"> (港澳)回乡证及通行证</option>
				<option value="H"> 台通行证及其他有效旅行证</option>
				<option value="I">(外国)护照</option>
				<option value="J"> (中国)护照</option>
				<option value="K">武警文职干部证</option>
				<option value="L"> 武警士兵证</option>
				<option value="M">驾照</option>
				<option value="Z"> 其他</option>
			</select></td>
		<td><input name="BnfGovtID3" type="text" id="BnfReadOnly35" class=common/></td>
		<TD><Input class="input"  name="BnfAdress3" ></TD>
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
		<td bgColor="#F7F7F7"> 
			<select name=BenefiType3  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0" >生存受益人</option>
				<option value="1" >死亡受益人</option>
				<option value="2" >红利受益人</option>
			</select>					
		</td>
	</tr>
</table>


<%--*************主险信息*************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【主险信息】</td>
	</tr> 
 
	<tr >
		<td>主险代码</td>
		<td>份数</td>
		<td>保费(元)</td>
		<td>保额(元)</td>
		<td>保险年期类型</td>
		<td>保险年期</td>
		<td>缴费年期</td>
	</tr>
 
	<tr class=common2>
            <td bgColor="#F7F7F7">
			 <select name="ProductCode"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)"> 
            	<option value="0006">中韩悦未来年金保险</option>
            	<option value="0005">中韩智赢财富两全保险（分红型）C款</option>
            	<option value="0004">中韩保驾护航两全保险A款</option>
            	<option value="0001">中韩智赢财富两全保险（分红型）A款</option>
                <option value="0002">中韩智赢财富两全保险（分红型）B款</option>
                <option value="0003">中韩卓越财富两全保险（分红型）</option>
            </select>
        	<input  name="RiskCodeName" type="hidden"  size="33"/>
        	<input  name="RiskCode" type="hidden"  size="33"/>
         </td>
		<td><input name="IntialNumberOfUnits" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode" class="common1">
				<option value=""></option>
				<option value="0"> 无关 </option>
				<option value="1"> 终生</option>
                <option value="2"> 按年限保 </option> 
                <option value="3"> 按季 </option>
                <option value="4"> 按月保 </option>
                <option value="5"> 按天保 </option>
                <option value="6"> 保至某确定年龄 </option>
             </select></td>
		<td><input name="Duration" type="text"  size="3" maxlength="5"/></td>
			<input name="PaymentDurationMode" type="hidden" class="common1">
			<!-- 
				<option value=""></option>
			<option value="0"> 无关 </option>
                <option value="1"> 趸缴 </option>
                <option value="2"> 年缴 </option>
                <option value="3"> 缴至某确定年龄 </option>
                <option value="4"> 终生缴 </option>
                <option value="5"> 不定期缴 </option>
             </select>
              -->
		<td><input name="PaymentDuration" type="text"  size="5" maxlength="5"  /></td>
	</tr>
				
</table>
	


<%--*************附加险1*************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【附加险信息】</td>
	</tr> 
 
	<tr >
		<td>险种代码</td>
		<td>份数</td>
		<td>保费(元)</td>
		<td>保额(元)</td>
		<td>保险年期类型</td>
		<td>保险年期</td>
		<td>缴费年期</td>
	</tr>

	<tr class=common2>
		<td bgColor="#F7F7F7">
            <input class="codeno" name="ProductCode1">							
        	<input  name="RiskCodeName1" size="33" />
        	<input  name="RiskCode1" type="hidden"  size="33"/>
         </td> 
		<td><input name="IntialNumberOfUnits1" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt1" type="text"  size="10"/></td>
		<td> 
			<select name="DurationMode1" class="common1">
				<option value=""></option>
				<option value="0"> 无关 </option>
				<option value="1"> 保终生</option>
                <option value="2"> 按年限保 </option> 
                <option value="3"> 保至某确定年龄 </option>
                <option value="4"> 按月保 </option>
                <option value="5"> 按天保 </option>
             </select></td>
		<td><input name="Duration1" type="text"  size="3" maxlength="5"/></td>
			<input name="PaymentDurationMode1" type="hidden" class="common1">
			<!-- 
				<option value=""></option>
			<option value="0"> 无关 </option>
                <option value="1"> 趸缴 </option>
                <option value="2"> 年缴 </option>
                <option value="3"> 缴至某确定年龄 </option>
                <option value="4"> 终生缴 </option>
                <option value="5"> 不定期缴 </option>
             </select>
              -->
             
		<td><input name="PaymentDuration1" type="text"  size="5" maxlength="5"  /></td>
	</tr>
			
	<tr class=common2>
		<td bgColor="#F7F7F7">
             <input class="codeno" name="ProductCode2" type="hidden"
							>
        	<input  name="RiskCodeName2" type="hidden"  size="33"/>
        	<input  name="RiskCode2" type="hidden"  size="33"/>
         </td>
		<td><input name="IntialNumberOfUnits2" type="hidden"  size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt2" type="hidden"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt2" type="hidden"  size="10"/></td>
		<td> 
			<input name="DurationMode2" type="hidden" class="common1">
            </td>
		<td><input name="Duration2" type="hidden" size="3" maxlength="5"/></td>
		<td>
			<input name="PaymentDurationMode2" type="hidden" class="common1">
				</td>
		<td><input name="PaymentDuration2" type="hidden" size="5" maxlength="5"  /></td>
	</tr>	
</table>

<table class=common align=center>
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode1" type="hidden"
							>
			<input class="codename" name="AccCodeName1" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent1" type="hidden"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode2"
							type="hidden"">
			<input class="codename" name="AccCodeName2" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent2" type="hidden"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode3"
							type="hidden"">
			<input class="codename" name="AccCodeName3" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent3" type="hidden"/></td>
		
	</tr>
	<tr class=common2>	
	<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode4"
							type="hidden">
			<input class="codename" name="AccCodeName4" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent4" type="hidden"/></td>
		<td bgColor="#F7F7F7">
			<input class="codeno" name="AccCode5"
							type="hidden">
			<input class="codename" name="AccCodeName5" type="hidden"/>
		</td>
		<td bgColor="#F7F7F7"><input name="AllocPercent5" type="hidden"/></td>
	</tr>
</table>
	
	
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
				<option value="-1"> 不定期交</option>
				<option value="0">趸交 </option>
				<option value="1">月交 </option>
                <option value="3"> 季交 </option>
                <option value="6"> 半年交 </option>
                <option value="12"> 年交</option>
                <option value="98"> 交至某确定年龄</option>
                <option value="99"> 终生交费 </option>
             </select></TD>	
		<td class=title>付费方式</td>
		<TD class=input>
			<select name=PaymentMethod class="common1">
				<option value=""></option>
				<option value="2"> 银行折代扣 </option>
                <option value="3"> 银行卡代扣 </option>
             </select></TD>
	</tr> 
	
	<TR class=common>
		<td class=title>领取方式</td>
		<TD class=input>
			<select name=BenefitMode class="common1">
				<option value=""></option>
				<option value="0"> 一次给付  </option>
                <option value="1"> 月给付  </option>
                <option value="3"> 季给付 </option>
                <option value="6"> 半年给付  </option>
                <option value="12"> 年给付 </option>
             </select></TD>
		<td class=title>红利领取方式</td>
		<TD class=input>
			<select name=DivType class="common1">
				<option value=""></option>
					<option value="0">直接给付 </option>
				<option value="1"> 抵交保费 </option>
                <option value="2"> 累计生息 </option>
                <option value="3">增额缴清 </option>
             </select></TD>
	</tr>
	
	<tr>
		<td class=title>保单传送方式</td>
		<TD class=input>
			<select name=PolicyDeliveryMethod class="common1">
				<option value=""></option>
				<option value="1"> 邮寄</option>
                <option value="2"> 电子发送 </option>
                  <option value="3">指定柜台领取 </option>
             </select></TD>
                <td class=title>自动垫交标志</td>
		<TD class=input><select name=AutoPayFlag class="common1">
				<option value=""></option>
				<option value="0"> 不自动垫交</option>
                <option value="1"> 自动垫交 </option>
             </select></TD>
	</tr>
	
	<TR class=common> 
	 <td class=title>银行账号</td>
		<TD class=input><Input class=input name=AccountNumber></TD>	
	 <td class=title>帐户姓名</td>
		<TD class=input><Input class=input name=AcctHolderName></TD>	
	</tr>
		<tr>
		<td class=title>职业告知标志</td>
		<TD class=input>
			<select name=OccupationIndicator class="common1">
				<option value="N">无职业告知</option>
				<option value="Y">有职业告知</option>           
             </select></TD>
             <td class=title>特别约定</td>
		<TD class=input><Input class=input name=SpecialClause></TD>
			<td bgColor="#F7F7F7"><input type="hidden" name=InvestDateInd class="common1"><input name="FirstSuperaddAmt" type="hidden"  size="10" /></td>
	</tr>
             
    <TR class=common>
		<td class=title>首期缴费帐号(新单出单填写)</td>
		<TD class=input><Input class="input"  name="BkAcctNo" ></TD>
		<td class=title>首期缴费方式(新单出单时选择)</td>
		<TD class=input>
			<select name="BkPayMode" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> 现金</option>
				<option value="2"> 折代扣</option>
				<option value="3"> 卡代扣</option>
				<option value="9"> 对公代扣 </option>
			</select>
		</TD>
	</tr> 
 <TR class=common>
		<td class=title>领取起始年龄</td>
		<TD class=input><Input class="input"  name="PbDrawAge" value="50"></TD>
		<td class=title>领取年期</td>
		<TD class=input><Input class="input"  name="PbDrawAgeTag" value="30"></TD>
	</tr>
</table>
		
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
