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
<SCRIPT src="TestNbcbCont.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestNbcbContInit.jsp"%>  

		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./TestNbcbSave.jsp" method=post name=fm target="fraSubmit">




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
			  <option value="1"> 公民身份证号码</option>
				<option value="2"> 军官证</option>
				<option value="A"> 解放军文职干部证</option>
				<option value="5"> 警官证</option>
				<option value="B"> 解放军士兵证</option>
				<option value="3"> 户口簿</option>
				<option value="H"> (港澳)回乡证及通行证</option>
				<option value="4">护照</option>
				<option value="A">武警文职干部证</option>
				<option value="1"> 武警士兵证</option>
				<option value="8"> 其他</option>
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
			    <option value="5"> 本人</option>
				<option value="1"> 配偶 </option>
				<option value="2"> 父母 </option>
				<option value="3"> 子女</option>
				<option value="4"> 亲属 </option>
				<option value="6"> 其他</option>
				<option value="Z"> 法定 </option>
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
			    <option value="1" selected="selected">城镇</option>
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
			  <option value="1"> 公民身份证号码</option>
				<option value="2"> 军官证</option>
				<option value="A"> 解放军文职干部证</option>
				<option value="5"> 警官证</option>
				<option value="B"> 解放军士兵证</option>
				<option value="3"> 户口簿</option>
				<option value="H"> (港澳)回乡证及通行证</option>
				<option value="4">护照</option>
				<option value="A">武警文职干部证</option>
				<option value="1"> 武警士兵证</option>
				<option value="8"> 其他</option>
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
						 <option value="N"  selected="selected"> 无健康告知</option>
						 <option value="Y"> 有健康告知</option>
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
			    <option value="5"> 本人</option>
				<option value="1"> 配偶 </option>
				<option value="2"> 父母 </option>
				<option value="3"> 子女</option>
				<option value="4"> 亲属 </option>
				<option value="6"> 其他</option>
				<option value="Z"> 法定 </option>
			</select></td>
		<td><input name="BnfFullName1" type="text" id="BnfReadOnly12" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfGender1" id="BnfReadOnly13" style="background-color: #D7E1F6">
			 <option value="1"> 男性</option>
				<option value="2"> 女性</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfGovtIDTC1" id="BnfReadOnly14" style="background-color: #D7E1F6">
			  <option value="1"> 公民身份证号码</option>
				<option value="2"> 军官证</option>
				<option value="A"> 解放军文职干部证</option>
				<option value="5"> 警官证</option>
				<option value="B"> 解放军士兵证</option>
				<option value="3"> 户口簿</option>
				<option value="H"> (港澳)回乡证及通行证</option>
				<option value="4">护照</option>
				<option value="A">武警文职干部证</option>
				<option value="1"> 武警士兵证</option>
				<option value="8"> 其他</option>
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
				<option value="1" >死亡受益人</option>
			</select>					
		</td>
	</tr>
				
<%--*************受益人2*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
			    <option value="5"> 本人</option>
				<option value="1"> 配偶 </option>
				<option value="2"> 父母 </option>
				<option value="3"> 子女</option>
				<option value="4"> 亲属 </option>
				<option value="6"> 其他</option>
				<option value="Z"> 法定 </option>
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
			  <option value="1"> 公民身份证号码</option>
				<option value="2"> 军官证</option>
				<option value="A"> 解放军文职干部证</option>
				<option value="5"> 警官证</option>
				<option value="B"> 解放军士兵证</option>
				<option value="3"> 户口簿</option>
				<option value="H"> (港澳)回乡证及通行证</option>
				<option value="4">护照</option>
				<option value="A">武警文职干部证</option>
				<option value="1"> 武警士兵证</option>
				<option value="8"> 其他</option>
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
				<option value="1" >死亡受益人</option>
			</select>					
		</td>
	</tr>
	
<%--*************受益人3*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfToInsRelation3" id="BnfReadOnly31" style="background-color: rgb(215, 225, 246);"> 
			    <option value="5"> 本人</option>
				<option value="1"> 配偶 </option>
				<option value="2"> 父母 </option>
				<option value="3"> 子女</option>
				<option value="4"> 亲属 </option>
				<option value="6"> 其他</option>
				<option value="Z"> 法定 </option>
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
			  <option value="1"> 公民身份证号码</option>
				<option value="2"> 军官证</option>
				<option value="A"> 解放军文职干部证</option>
				<option value="5"> 警官证</option>
				<option value="B"> 解放军士兵证</option>
				<option value="3"> 户口簿</option>
				<option value="H"> (港澳)回乡证及通行证</option>
				<option value="4">护照</option>
				<option value="A">武警文职干部证</option>
				<option value="1"> 武警士兵证</option>
				<option value="8"> 其他</option>
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
				<option value="1" >死亡受益人</option>
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
            	<option value="03000001">中韩保驾护航两全保险A款</option>
				<option value="03000002">中韩安赢借款人意外伤害保险A款</option>
				<option value="03000003">中韩悦未来年金险</option>
            	<option value="03000004">中韩智赢财富两全保险（分红型）C款</option>
				<option value="03000005">中韩悦无忧两全保险</option>
				<option value="03000007">中韩永利年年年金保险（分红型）</option>
				<option value="03000008">中韩卓越财富两全保险（分红型）</option>
				<option value="03000009">中韩永承终身寿险</option>
				<option value="03000010">中韩创赢财富两全保险（万能型）A款</option>
				<option value="03000011">中韩悦安康两全保险A款</option>
				<option value="03000013">中韩优越财富两全保险</option>
				<option value="03000015">中韩臻佑终身重大疾病保险</option>
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
                <option value="3"> 保至某确定年龄  </option>
                <option value="4"> 按月保 </option>
                <option value="5"> 按天保 </option>
             </select></td>
		<td><input name="Duration" type="text"  size="5" maxlength="5"/></td>
		<td><select name="PaymentDurationMode" class="common1">
				<option value="0">无关</option>
				<option value="8"> 不定期交</option>
				<option value="1">趸交 </option>
				<option value="5">月交 </option>
                <option value="4"> 季交 </option>
                <option value="3"> 半年交 </option>
                <option value="2" selected="selected"> 年交</option>
                <option value="6"> 交至某确定年龄</option>
                <option value="7"> 终生交费 </option>
             </select></td>
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
		<td>保费(分)</td>
		<td>保额(分)</td>
		<td>保险年期类型</td>
		<td>保险年期</td>
		<td>缴费年期类型</td>
		<td>缴费年期</td>
	</tr>
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="ProductCode1"  style="background-color: #D7E1F6">
					<option value=""></option>
                <option value="03000014">中韩附加定盈宝两全保险（万能型）</option>
                <option value="03000012">中韩附加悦安康恶性肿瘤疾病保险A款</option>
            </select></td>
		<td><input name="IntialNumberOfUnits1" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ModalPremAmt1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="InitCovAmt1" type="text"  size="10"/></td>
		<td>    
			<select name="DurationMode1" class="common1">
                <option value=""></option>
				<option value="0"> 无关 </option>
				<option value="1"> 终生</option>
                <option value="2"> 按年限保 </option> 
                <option value="3"> 保至某确定年龄  </option>
                <option value="4"> 按月保 </option>
                <option value="5"> 按天保 </option>
             </select></td>
		<td><input name="Duration1" type="text"  size="5" maxlength="5"/></td>
		<td>
			<select name="PaymentDurationMode1" class="common1"0>
				<option value="0">无关</option>
				<option value="8"> 不定期交</option>
				<option value="1">趸交 </option>
				<option value="5">月交 </option>
                <option value="4"> 季交 </option>
                <option value="3"> 半年交 </option>
                <option value="2" selected="selected"> 年交</option>
                <option value="6"> 交至某确定年龄</option>
                <option value="7"> 终生交费 </option>
             </select></td>
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

<div id="C20001" style="display:'none'">

	<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【贷款信息】</td>
	</tr>
	<TR class=common>
		<td class=title>贷款合同号</td>
		<TD class=input><Input class=input name="ContractNo" id="ContractNo"></TD>
		<td class=title>贷款账号</td>
		<TD class=input>
			<input class=input  name = "LoanAccountNo"></TD>
	</tr> 
	
	<TR class=common> 
		<td class=title>贷款起始日期</td>
		<TD class=input>
			<input  class="coolDatePicker"   dateFormat = "short" name = "LoanStartDate"></TD>
		<td class=title>贷款到期日期</td>
		<TD class=input><Input   class="coolDatePicker"   dateFormat = "short" name="LoanEndDate"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>保险合同生效日期</td>
		<TD class=input><input  class="coolDatePicker"   dateFormat = "short" name = "ContractEffDate"></TD>

		<td class=title>保险合同到期日期</td>
		<TD class=input><Input   class="coolDatePicker"   dateFormat = "short" name="ContractEndDate"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>借款金额	</td>
		<TD class=input><Input class=input name=LoanAmount id="LoanAmount"><font color="red">(单位：元)</font></TD>
		<td class=title>保险金额	</td>
		<TD class=input><input class=input  name = "FaceAmount"><font color="red">(单位：分)</font></TD>	
	</tr>
	
	<TR class=common>
		<td class=title>贷款类型</td>
		<TD class=input>
			<select name=LoanProductCode class="common1">
				<option value="00" selected="selected"> 一般商业贷款 </option>
                <option value="01"> 组合商业贷款 </option>
                <option value="10"> 公积金组合贷款</option>
                <option value="11"> 纯公积金贷款 </option>
                <option value="20"> 贴息助学贷款 </option>
             </select></TD>
	</tr>
</table>
</div>	
	
<%--*************投保信息*************--%>		
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【投保信息】</td>
	</tr>
	 
	<TR class=common>
		<td class=title>缴费方式</td>
		<TD class=input>  
			<select name=PaymentMode class="common1">
				<option value="0">无关</option>
				<option value="8"> 不定期交</option>
				<option value="1">趸交 </option>
				<option value="5">月交 </option>
                <option value="4"> 季交 </option>
                <option value="3"> 半年交 </option>
                <option value="2" selected="selected"> 年交</option>
                <option value="6"> 交至某确定年龄</option>
                <option value="7"> 终生交费 </option>
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
                <option value="1"> 累积生息 </option>
				<option value="2"> 现金领取 </option>
				<option value="3"> 抵交保费 </option>
                <option value="4"> 增额缴清 </option>
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
	<!--	<td class=title>GetTerms</td>
	 	<TD class=input>
			<input  class="coolDatePicker"  id="GetTmersDate"  dateFormat = "short" name = "GetTmersDate"></TD>
	 -->
	 
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
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	
	<TR>
		<td class=title>交易码</td>
		<td class=input> 
		<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="1">新单核保</option>
			    <option value="2">新单出单</option>
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
