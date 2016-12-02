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
<SCRIPT src="TestYBTNewCCBCont.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestYBTNewCCBContInit.jsp"%>  

		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./TestYBTNewCCBSave.jsp" method=post name=fm target="fraSubmit">



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
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="SubmissionDate"></TD>
	</TR>
	
	<TR class=common>
		<td class=title>投保书号</td>
		<TD class=input><Input class=input name="HOAppFormNumber" maxlength="16" id="HOAppFormNumber"></TD>
		<td class=title>保单印刷号</td>
		<TD class=input><Input class=input name="ProviderFormNumber" maxlength="16" ><font color="red">(打印保单时必填)</font></TD>	
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
	<TR class=common>
		<td class=title>保单号码</td>
		<TD class=input><Input class=input name="ContNo" id="ContNo"><input type="hidden" name="ContNo"><font color="red">(打印保单时填写)</font></TD>
		<td class=title>保险凭证类型</td>
		<TD class=input><Input class=input name="ContType" maxlength="16" id="ContType"><font color="red">(打印保单时填写)</TD>
    </TR>

  
	<TR class=common>
		<td class=title>客户风险承受能力代码</td>
		<TD class=input>
			<select name="Cst_Rsk_Tlrnc_Cpy_Cd" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="01"> 保守型</option>
				<option value="02"> 收益型</option>
				<option value="03"> 稳健型</option>
				<option value="04"> 进取型</option>
				<option value="05"> 积极进取型</option>
			</select></TD>
    </TR>
	<TR class=common>
		<td class=title>风险测评有效期</td>
		<TD class=input><Input class=input name="Rsk_Evlt_AvlDt"></font></TD>
		<td class=title>预算金额</td>
		<TD class=input><Input class=input name="Bdgt_Amt"><font color="red"></TD>
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
			    <option value="1010"> 公民身份证号码</option>
				<option value="1022"> 军官证</option>
				<option value="1060"> 学生证-</option>
				<option value="1032"> 警官证</option>
				<option value="1021"> 解放军士兵证</option>
				<option value="1040"> 户口簿</option>
				<option value="1080"> (港澳)回乡证及通行证</option>
				<option value="1070"> 台通行证及其他有效旅行证</option>
				<option value="1051">(外国)护照</option>
				<option value="1052"> (中国)护照</option>
				<option value="1050"> 护照</option>
				<option value="1999">个人其他证件</option>
				<option value="2999"> 武对公其他证件</option>
				<option value="1100">驾照</option>
				<option value="1011"> 临时居民身份证</option>
				<option value="1160"> 临台湾居民身份证 台胞证</option>
			</select></TD>
			
		<td class=title>投保人证件号码</td>
		<TD class=input><Input class=input name=AppGovtID></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人生日</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppBirthDate></TD>
		<td class=title>投保人证件生效日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppGovtEfcDate></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人证件失效日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppGovtTermDate></TD>
		<td class=title>投保人通讯地址	</td>
		<TD class=input><Input class=input name=AppLine1></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人邮政编码	</td>
		<TD class=input><Input class=input name=AppZip></TD>		
		<td class=title>投保人固定电话</td>
		<TD class=input><Input class=input name=AppDialNumber1></TD>
	</tr>
	
		
	
	<TR class=common>
		<td class=title>投保人与被保人关系</td>
		<TD class=input>
			<select name="AppToInsRelation" style="background-color: #D7E1F6" onchange="setFlag();">
				<option value=""></option>
			    <option value="0133043"> 本人</option>
				<option value="0133010"> 丈夫 </option>
				<option value="0133010"> 妻子 </option>
				<option value="0133015"> 父亲 </option>
				<option value="0133016">母亲 </option>
				<option value="0133011"> 儿子</option>
				<option value="0133012"> 女儿 </option>
				<option value="0133017">祖父 </option>
				<option value="0133018">祖母</option>	 
				 <option value="0133013"> 孙子</option>
				<option value="0133014"> 孙女</option>
				<option value="0133020"> 哥哥</option>
				<option value="0133019"> 姐姐</option>
				<option value="0133035"> 弟弟</option>	 
				 <option value="0133036"> 妹妹</option>
				<option value="0133021"> 其它亲属 </option>
				<option value="0133002"> 同事 </option>	 
				 <option value="0133001"> 朋友 </option>
			</select></TD>
			<td class=title>投保人移动电话	</td>
			<TD class=input><Input class=input name=AppDialNumber3></TD>			
	</tr>
	<tr class=common>
		<td class=title>投保人电子邮箱</td>
		<TD class=input><Input class=input name=AppAddrLine></TD>
		<td class=title>投保人国籍</td>
	<td bgColor="#F7F7F7" >
		<select name="AppCountry" id="AppCountry" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			  <option value="0156">中国</option>
				<option value="--"> 其他</option>
				</select>
				</td>
	</tr>
	<TR class=common>
	<td class=title>投保人职业代码</td>  
		<TD class=input> 
			<select name="ApplJobCode"  style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="3010101"> 一般内勤</option>
			</select>
		</TD>	
	<td class=title>投保人居民类型</td>
		<TD class=input>
		<select name="PbDenType" id="PbDenType" style="background-color: rgb(215, 225, 246);"> 
			<option value=""></option>
			    <option value="1">城镇</option>
				<option value="2" selected="selected">农村</option>
			</select>
		</TD>
	</tr>
	<TR class=common>
		<td class=title>投保人年收入</td>
		<TD class=input><Input class=input name=PbInCome>元</TD>
		<td class=title>投保人家庭年收入</td>
		<TD class=input><Input class=input name=PbHomeInCome>元</TD>
	</tr>
	<TR class=common>
		<td class=title>经办人姓名</td>
		<TD class=input><Input class=input name=RspbName></TD>
	</tr>
	
	<TR class=common>
		<TD class=title>投保人省代码</TD>
		<TD class=input><Input class=input name="Plchd_Prov_Cd"/></TD>
		<TD class=title>投保人市代码</TD>
		<TD class=input><Input class=input name="Plchd_City_Cd"/></TD>
	</TR>
		
		<TR class=common>
		<TD class=title>投保人区县代码</TD>
		<TD class=input><Input class=input name="Plchd_CntyAndDstc_Cd"/></TD>
		<TD class=title>投保人详细地址内容</TD>
		<TD class=input><Input class=input name="Plchd_Dtl_Adr_Cntnt"/></TD>
	</TR>
	
	<TR class=common>
		<td class=title>投保人固定电话国内区号</td>
		<TD class=input><Input class=input name=PlchdFixTelDmstDstcNo></TD>
		<td class=title>投保人移动电话国际区号</td>
		<TD class=input><Input class="input" name=PlchdMoveTelItlDstcNo></TD>
	</tr>
	
	<TR class=common>
		<td class=title> 投保人国家地区代码</td>
		<TD class=input><Input class=input name=Plchd_Nat_Cd></TD>
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
		<td class=title>被保人姓名拼音全称</td>
		<TD class=input><Input class=input name="Rcgn_CPA_FullNm" id="Ins0"></TD>
	</tr> 
	
	<TR class=common> 
		<td class=title>被保人性别</td>
		<TD class=input>
			<select name="InsGender" id="Ins2" style="background-color: #D7E1F6">
				<option value=""></option>
			     <option value="1"> 男性</option>
				<option value="2"> 女性</option>
			</select>
		</TD>
		<td class=title>被保人证件类型</td>
		<TD class=input>
			<select name="InsGovtIDTC" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1010"> 公民身份证号码</option>
				<option value="1022"> 军官证</option>
				<option value="1060"> 学生证-</option>
				<option value="1032"> 警官证</option>
				<option value="1021"> 解放军士兵证</option>
				<option value="1040"> 户口簿</option>
				<option value="1080"> (港澳)回乡证及通行证</option>
				<option value="1070"> 台通行证及其他有效旅行证</option>
				<option value="1051">(外国)护照</option>
				<option value="1052"> (中国)护照</option>
				<option value="1050"> 护照</option>
				<option value="1999">个人其他证件</option>
				<option value="2999"> 武对公其他证件</option>
				<option value="1100">驾照</option>
				<option value="1011"> 临时居民身份证</option>
				<option value="1160"> 临台湾居民身份证 台胞证</option>
			</select></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人生日</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsBirthDate"></TD>
		<td class=title>被保人证件号码</td>
		<TD class=input><Input class=input id="Ins4" name="InsGovtID"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人证件生效日期	</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsGovtEfcDate></TD>
		<td class=title>被保人证件失效日期	</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsGovtExpDate></TD>
	</tr>
	<TR class=common>
		<td class=title>被保人通讯地址	</td>
		<TD class=input><Input class=input id="Ins7" name="InsLine1"></TD>
		<td class=title>被保人邮政编码	</td>
		<TD class=input><Input class=input id="Ins8" name="InsZip"></TD>	
	</tr>
	 
	<TR class=common>
		<td class=title>被保人固定电话</td>
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
			</select>
		</TD>
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
		<td class=title>被保人年收入</td>
		<TD class=input><Input class=input name=InsInCome>元</TD>
	</tr>
	<tr class=common>
		<td class=title>被保人前往目的地个数</td>
		<TD class=input><Input class=input name=DesNum></TD>
		<td class=title>被保人前往目的地</td>
		<TD class=input><Input class=input name=Destinations></TD>
	</tr>
	
	<TR class=common>
		<TD class=title>被保人省代码</TD>
		<TD class=input><Input class=input name="Rcgn_Prov_Cd"/></TD>
		<TD class=title>被保人市代码</TD>
		<TD class=input><Input class=input name="Rcgn_City_Cd"/></TD>
	</TR>
		
		<TR class=common>
		<TD class=title>被保人区县代码</TD>
		<TD class=input><Input class=input name="Rcgn_CntyAndDstc_Cd"/></TD>
		<TD class=title>被保人详细地址内容</TD>
		<TD class=input><Input class=input name="Rcgn_Dtl_Adr_Cntnt"/></TD>
	</TR>
	
	<TR class=common>
		<td class=title>被保人固定电话国内区号</td>
		<TD class=input><Input class=input name=RcgnFixTelDmst_DstcNo></TD>
		<td class=title>被保人移动电话国际区号</td>
		<TD class=input><Input class="input" name=RcgnMoveTelItnlDstcNo></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人国家地区代码</td>
		<TD class=input><Input class=input name=Rcgn_Nat_Cd></TD>
	</tr>
	
</table>
 



<%--*************************受益人信息************************--%>
<table class=common align=center> 

	<tr> 
		<td class=titleImg align=center>【受益人信息】</td>
		<td colspan="3"><input type="checkbox" name=BnfFlag  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator>
		<font color="red">收益人是否为法定(选中为法定)</font></td>
		<td>代理保险受益人类型代码</td>
		<td><input  name=AgIns_Benf_TpCd></td>
	</tr>    

	<tr >  
		<td>与被保险人关系</td>
		<td>姓名</td>
		<td>性别</td>
		<td>证件类型	</td>
		<td>证件号码	</td>
		<td>受益人证件生效日期</td>
		<td>受益人证件失效日期</td>
		<td>受益人通讯地址</td>
		<td>受益人详细地址</td>
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
			    <option value="0133043"> 本人</option>
				<option value="0133010"> 丈夫 </option>
				<option value="0133010"> 妻子 </option>
				<option value="0133015"> 父亲 </option>
				<option value="0133016">母亲 </option>
				<option value="0133011"> 儿子</option>
				<option value="0133012"> 女儿 </option>
				<option value="0133017">祖父 </option>
				<option value="0133018">祖母</option>	 
				 <option value="0133013"> 孙子</option>
				<option value="0133014"> 孙女</option>
				<option value="0133020"> 哥哥</option>
				<option value="0133019"> 姐姐</option>
				<option value="0133035"> 弟弟</option>	 
				 <option value="0133036"> 妹妹</option>
				<option value="0133021"> 其它亲属 </option>
				<option value="0133002"> 同事 </option>	 
				 <option value="0133001"> 朋友 </option>
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
			  <option value="1010"> 公民身份证号码</option>
				<option value="1022"> 军官证</option>
				<option value="1060"> 学生证-</option>
				<option value="1032"> 警官证</option>
				<option value="1021"> 解放军士兵证</option>
				<option value="1040"> 户口簿</option>
				<option value="1080"> (港澳)回乡证及通行证</option>
				<option value="1070"> 台通行证及其他有效旅行证</option>
				<option value="1051">(外国)护照</option>
				<option value="1052"> (中国)护照</option>
				<option value="1050"> 护照</option>
				<option value="1999">个人其他证件</option>
				<option value="2999"> 武对公其他证件</option>
				<option value="1100">驾照</option>
				<option value="1011"> 临时居民身份证</option>
				<option value="1160"> 临台湾居民身份证 台胞证</option>
			</select></td>
		<td><input name="BnfGovtID1" type="text" id="BnfReadOnly15" class=common/></td>
		
	  <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappbgndate1" name="applbegindate1"></td>
      <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappvlddate1" name="applvaliddate1"></td>
		
		<TD><Input class="input"  name="BnfAdress1" ></TD>
		<TD><Input class="input"  name="BnfAdressContent1" ></TD>
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
			      <option value="0133043"> 本人</option>
				<option value="0133010"> 丈夫 </option>
				<option value="0133010"> 妻子 </option>
				<option value="0133015"> 父亲 </option>
				<option value="0133016">母亲 </option>
				<option value="0133011"> 儿子</option>
				<option value="0133012"> 女儿 </option>
				<option value="0133017">祖父 </option>
				<option value="0133018">祖母</option>	 
				 <option value="0133013"> 孙子</option>
				<option value="0133014"> 孙女</option>
				<option value="0133020"> 哥哥</option>
				<option value="0133019"> 姐姐</option>
				<option value="0133035"> 弟弟</option>	 
				 <option value="0133036"> 妹妹</option>
				<option value="0133021"> 其它亲属 </option>
				<option value="0133002"> 同事 </option>	 
				 <option value="0133001"> 朋友 </option>
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
			  <option value="1010"> 公民身份证号码</option>
				<option value="1022"> 军官证</option>
				<option value="1060"> 学生证-</option>
				<option value="1032"> 警官证</option>
				<option value="1021"> 解放军士兵证</option>
				<option value="1040"> 户口簿</option>
				<option value="1080"> (港澳)回乡证及通行证</option>
				<option value="1070"> 台通行证及其他有效旅行证</option>
				<option value="1051">(外国)护照</option>
				<option value="1052"> (中国)护照</option>
				<option value="1050"> 护照</option>
				<option value="1999">个人其他证件</option>
				<option value="2999"> 武对公其他证件</option>
				<option value="1100">驾照</option>
				<option value="1011"> 临时居民身份证</option>
				<option value="1160"> 临台湾居民身份证 台胞证</option>
			</select></td>
		<td><input name="BnfGovtID2" type="text" id="BnfReadOnly25" class=common/></td>
		
	  <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappbgndate2" name="applbegindate2"></td>
      <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappvlddate2" name="applvaliddate2"></td>
		
		<TD><Input class="input"  name="BnfAdress2" ></TD>
		<TD><Input class="input"  name="BnfAdressContent2" ></TD>
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
			      <option value="0133043"> 本人</option>
				<option value="0133010"> 丈夫 </option>
				<option value="0133010"> 妻子 </option>
				<option value="0133015"> 父亲 </option>
				<option value="0133016">母亲 </option>
				<option value="0133011"> 儿子</option>
				<option value="0133012"> 女儿 </option>
				<option value="0133017">祖父 </option>
				<option value="0133018">祖母</option>	 
				 <option value="0133013"> 孙子</option>
				<option value="0133014"> 孙女</option>
				<option value="0133020"> 哥哥</option>
				<option value="0133019"> 姐姐</option>
				<option value="0133035"> 弟弟</option>	 
				 <option value="0133036"> 妹妹</option>
				<option value="0133021"> 其它亲属 </option>
				<option value="0133002"> 同事 </option>	 
				 <option value="0133001"> 朋友 </option>
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
			  <option value="1010"> 公民身份证号码</option>
				<option value="1022"> 军官证</option>
				<option value="1060"> 学生证-</option>
				<option value="1032"> 警官证</option>
				<option value="1021"> 解放军士兵证</option>
				<option value="1040"> 户口簿</option>
				<option value="1080"> (港澳)回乡证及通行证</option>
				<option value="1070"> 台通行证及其他有效旅行证</option>
				<option value="1051">(外国)护照</option>
				<option value="1052"> (中国)护照</option>
				<option value="1050"> 护照</option>
				<option value="1999">个人其他证件</option>
				<option value="2999"> 武对公其他证件</option>
				<option value="1100">驾照</option>
				<option value="1011"> 临时居民身份证</option>
				<option value="1160"> 临台湾居民身份证 台胞证</option>
			</select></td>
		<td><input name="BnfGovtID3" type="text" id="BnfReadOnly35" class=common/></td>
		
	  <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappbgndate3" name="applbegindate3"></td>
      <td class=input><Input class="coolDatePicker" dateFormat="short"  id="bnfappvlddate3" name="applvaliddate3"></td>
		
		<TD><Input class="input"  name="BnfAdress3" ></TD>
		<TD><Input class="input"  name="BnfAdressContent3" ></TD>
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
		<td>代理保险套餐编号</td>
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
            	<option value="221301">中韩悦未来年金险</option>
            	<option value="231204">中韩智赢财富两全保险（分红型）C款</option>
            	<option value="221201">中韩保驾护航两全保险A款</option>
            	<option value="231201">中韩智赢财富两全保险（分红型）A款</option>
                <option value="231202">中韩智赢财富两全保险（分红型）B款</option>
                <option value="231203">中韩卓越财富两全保险（分红型）</option>
                <option value="231302">中韩永利年年年金保险（分红型）</option>
                 <option value="241201">中韩创赢财富两全保险（万能型）A款</option>
            </select>
        	<input  name="RiskCodeName" type="hidden"  size="25"/>
        	<input  name="RiskCode" type="hidden"  size="10"/>
         </td>
		<td><input name="AgIns_Pkg_ID" type="text"   size="12"/></td>
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
	<tr >
		<td>投保方案信息</td>
		<td>可选部分身故保险金额</td>
		<td>首次额外追加保费</td>
		<td>紧急联系人</td>
		<td>紧急联系人与被保人关系</td>
		<td>紧急联系电话</td>
	</tr>
	<tr class=common2>
		<td><Input class=input name=Ins_Scm_Inf></td>
		<TD ><Input class=input name=Opt_Part_DieIns_Amt  type="text"  size="10">分</TD>
		<TD ><Input class=input name=FTm_Extr_Adl_InsPrem type="text"  size="10">分</TD>
		<TD ><Input class=input name=Emgr_CtcPsn type="text"  size="10"></TD>
		<td bgColor="#F7F7F7" >
		<select name="EmgrCtcPsnAndRcReTpCd" id="EmgrCtcPsnAndRcReTpCd" style="background-color: rgb(215, 225, 246);">
		<option value=""></option>
			    <option value="0133043"> 本人</option>
				<option value="0133010"> 丈夫 </option>
				<option value="0133010"> 妻子 </option>
				<option value="0133015"> 父亲 </option>
				<option value="0133016">母亲 </option>
				<option value="0133011"> 儿子</option>
				<option value="0133012"> 女儿 </option>
				<option value="0133017">祖父 </option>
				<option value="0133018">祖母</option>	 
				 <option value="0133013"> 孙子</option>
				<option value="0133014"> 孙女</option>
				<option value="0133020"> 哥哥</option>
				<option value="0133019"> 姐姐</option>
				<option value="0133035"> 弟弟</option>	 
				 <option value="0133036"> 妹妹</option>
				<option value="0133021"> 其它亲属 </option>
				<option value="0133002"> 同事 </option>	 
				 <option value="0133001"> 朋友 </option>
			</select></td>
		<TD ><Input class=input name=Emgr_Ctc_Tel type="text"  size="18"></TD>
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
				<option value="01"> 不定期交 </option>
				<option value="02"> 趸交 </option>
                <option value="03"> 周期 </option>
                <option value="04"> 交至某确定年龄 </option>
                <option value="05"> 终生交费 </option>
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

<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	
	<TR>
		<td class=title>交易码</td>
		<td class=input> 
		<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="P53819113">录单核保</option>
			    <option value="P53819188">打印投保单</option>
			    <option value="P53819151">查询保费</option>
			    <option value="P53819152">收费签单</option>
			    <option value="P53819182">打印保单</option>
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
