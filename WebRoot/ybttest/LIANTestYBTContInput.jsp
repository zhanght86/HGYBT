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
<SCRIPT src="LIANTestYBTCont.js"></SCRIPT>
<script type="text/javascript">
	
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   
		<%@include file="LIANTestYBTContInit.jsp"%>  
 
		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();"> 
<form action="./LIANTestYBTSave.jsp" method=post name=fm target="fraSubmit">


<%--*************************交易信息*************************--%>
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	<tr>
<td class=title>交易码</td>
<TD class=input>
			<select name="tranFlagCode"   style="background-color: #D7E1F6" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="01"> 新单核保</option>
				<option value="02"> 新单出单</option>
					<!--<option value="03"> 当日撤单</option>-->
				<!--  option value="1011"> 新单重打</option>
				<option value="03"> 当日撤单</option>-->
			</select></TD>
		
			<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name=ip></td> 
			
			<td class=title>交易银行</td>
			<td class=input><input class="codeno" name="port" verify="交易银行|NotNull" readonly="readonly">
				<input class="codename" name="portName" readonly="true" elementtype="nacessary" /></td> 
			
			&nbsp;&nbsp;
	<TR class=common>
		<TD class=input width="26%"><input class="cssButton" type="button" name="Submit1" value="发送投保申请" onClick="submitForm()"/></TD> 
	</TR>
</table>
		
<hr>

<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【交易信息】</td>
	</tr> 
	
	<TR class=common>
		<TD class=title>银行端交易日期</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=BankDate></TD>
		<TD class=title>交易流水号</TD> 
		<TD class=input><input class=input name="TransrNo"><input type="hidden" name="InputTransrNo"></TD>
	</TR>
	
	<TR class=common>
		<td class=title>地区代码</td>
		<TD class=input><Input class=input name="ZoneNo"></TD>
		<td class=title>网点代码</td>
		<TD class=input><Input class=input name="BrNo"></TD>
	</TR>
	
	<TR class=common> 
		<td class=title>柜员代码</td>
		<TD class=input><Input class=input name="TellerNo"></TD>
		<td class=title>投保日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=PolApplyDate></TD>
	</TR>
	
	<TR class=common>
		<td class=title>投保书号</td>
		<TD class=input><Input class=input name="ProposalContNo" id="ProposalNo"></TD>
		<td class=title>保单印刷号</td>
		<TD class=input><Input class=input name="PrtNo" id="PrtNo"></TD>
	</TR>
	
	<TR class=common>
		<td class=title>原交易流水号</td>
		<TD class=input><Input class=input name="ReqsrNo" id="OldTranNo"></TD>
		 <!--
			 <td class=title>原保单印刷号</td>
				<TD class=input><Input class=input name=OriginalProviderFormNumber id="OldPrtNo"></TD>	
			</tr>
				<TR class=common>
				<td class=title>保单号</td>
				<TD class=input><Input class=input name=PolNumber id="ContNo"></TD>	
			</tr>
		-->
	</TR>
	
	
</table>

<hr>
<%--*************************投保人信息************************--%>
<table class=common align=center >

	<tr>
		<td class=titleImg align=center>【投保人信息】</td>
	</tr>
	
	<TR class=common>
		<td class=title>投保人姓名</td>
		<TD class=input><Input class=input name=ApplName></TD>
		<td class=title>投保人性别</td>
		<TD class=input>
			<select name="ApplSex" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 其他</option>
			</select></TD>
		
	</tr> 
	
	<TR class=common> 
		<td class=title>投保人证件类型</td>
		<TD class=input>
			<select name="ApplIDType" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> 居民身份证</option>
				<option value="110002"> 重号居民身份证</option>
				<option value="110003"> 临时居民身份证</option>
				<option value="110004"> 重号临时居民身份证</option>
				<option value="110005"> 户口簿</option>
				<option value="110006"> 重号户口簿</option>
				<option value="110011"> 离休干部荣誉证</option>
				<option value="110012"> 重号离休干部荣誉证</option>
				<option value="110013"> 军官退休证</option>
				<option value="110014"> 重号军官退休证</option>
				<option value="110015"> 文职干部退休证</option>
				<option value="110016"> 重号文职干部退休证</option>
				<option value="110017"> 军事院校学员证</option>
				<option value="110018"> 重号军事院校学员证</option>
				<option value="110019"> 港澳居民往来内地通行证</option>
				<option value="110020"> 重号港澳居民往来内地通行证</option>
				<option value="110021"> 台湾居民来往大陆通行证</option>
				<option value="110022"> 重号台湾居民来往大陆通行证</option>
				<option value="110023"> 中华人民共和国护照</option>
				<option value="110024"> 重号中华人民共和国护照</option>
				<option value="110025"> 外国护照</option>
				<option value="110026"> 重号外国护照</option>
				<option value="110027"> 军官证</option>
				<option value="110028"> 重号军官证</option>
				<option value="110029"> 文职干部证</option>
				<option value="110030"> 重号文职干部证</option>
				<option value="110031"> 警官证</option>
				<option value="110032"> 重号警官证</option>
				<option value="110033"> 军人士兵证</option>
				<option value="110034"> 重号军人士兵证</option>
				<option value="110035"> 武警士兵证</option>
				<option value="110036"> 重号武警士兵证</option>
				<option value="119998"> 系统使用的个人证件识别标识</option>
				<option value="119999"> 其它个人证件识别标识</option>
				
			</select></TD>
			
		<td class=title>投保人证件号码</td>
		<TD class=input><Input class=input name="ApplIDNo"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人生日</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="ApplBirthday"></TD>
		<td class=title>投保人电子邮箱</td>
		<TD class=input><Input class=input name="ApplEmail">
		</TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人通讯地址	</td>
		<TD class=input><Input class=input name="ApplAddress"></TD>
		<td class=title>投保人邮政编码	</td>
		<TD class=input><Input class=input name="ApplZipCode"></TD>	
	</tr>
	
	<TR class=common>
		<td class=title>投保人家庭电话</td>
		<TD class=input><Input class=input name="ApplPhone"></TD>
		<td class=title>投保人移动电话	</td>
		<TD class=input><Input class=input name="ApplMobile"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人与被保人的关系</td>
		<TD class=input>
			<select name="ApplRelaToInsured" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1"> 本身</option>
				<option value="2"> 丈夫</option>
				<option value="3"> 妻子</option>
				<option value="4"> 父亲</option>
				<option value="5"> 母亲</option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿</option>
				<option value="8"> 祖父</option>
				<option value="9"> 祖母</option>
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
				<option value="26"> 其他亲戚</option>
				<option value="27"> 同事</option>
				<option value="28"> 朋友</option>
				<option value="29"> 雇主</option>
				<option value="30"> 其他</option>	 
			</select></TD>
		<td class=title>投保人职业代码</td>  
		<TD class=input> 
			<select name="ApplJobCode"  style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="01"> 国家机关、党群组织、企业、事业单位人员</option>
						 <option value="02"> 卫生专业技术人员</option>
						 <option value="03"> 金融业务人员</option>
						 <option value="04"> 法律专业人员</option>
						 <option value="05"> 教学人员</option>
						 <option value="06"> 新闻出版及文学艺术工作人员</option>
						 <option value="07"> 宗教职业者</option>
						 <option value="08"> 邮政和电信业务人员</option>
						 <option value="09"> 商业、服务业人员</option>
						 <option value="10"> 农、林、牧、渔、水利业生产人员</option>
						 <option value="11"> 运输人员</option>
						 <option value="12"> 地质勘测人员</option>
						 <option value="13"> 工程施工人员</option>
						 <option value="14"> 加工制造、检验及计量人员</option>
						 <option value="15"> 军人</option>
						 <option value="16"> 无业</option>
				
			</select>
		</TD>	
		
	</tr>
	
	<TR class=common>
		<td class=title>投保人证件有效期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="ApplValidYear"></TD>
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
		<TD class=input><Input class=input name="InsuName" id="Ins1"></TD>
		<td class=title>被保人性别</td>
		<TD class=input>
			<select name="InsuSex" id="Ins2" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 其他</option>
			</select></TD>
			<input type="hidden" name = "InsuSexh">
	</tr> 
	
	<TR class=common> 
		<td class=title>被保人证件类型</td>
		<TD class=input>
			<select name="InsuIDType" id="Ins3" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> 居民身份证</option>
				<option value="110002"> 重号居民身份证</option>
				<option value="110003"> 临时居民身份证</option>
				<option value="110004"> 重号临时居民身份证</option>
				<option value="110005"> 户口簿</option>
				<option value="110006"> 重号户口簿</option>
				<option value="110011"> 离休干部荣誉证</option>
				<option value="110012"> 重号离休干部荣誉证</option>
				<option value="110013"> 军官退休证</option>
				<option value="110014"> 重号军官退休证</option>
				<option value="110015"> 文职干部退休证</option>
				<option value="110016"> 重号文职干部退休证</option>
				<option value="110017"> 军事院校学员证</option>
				<option value="110018"> 重号军事院校学员证</option>
				<option value="110019"> 港澳居民往来内地通行证</option>
				<option value="110020"> 重号港澳居民往来内地通行证</option>
				<option value="110021"> 台湾居民来往大陆通行证</option>
				<option value="110022"> 重号台湾居民来往大陆通行证</option>
				<option value="110023"> 中华人民共和国护照</option>
				<option value="110024"> 重号中华人民共和国护照</option>
				<option value="110025"> 外国护照</option>
				<option value="110026"> 重号外国护照</option>
				<option value="110027"> 军官证</option>
				<option value="110028"> 重号军官证</option>
				<option value="110029"> 文职干部证</option>
				<option value="110030"> 重号文职干部证</option>
				<option value="110031"> 警官证</option>
				<option value="110032"> 重号警官证</option>
				<option value="110033"> 军人士兵证</option>
				<option value="110034"> 重号军人士兵证</option>
				<option value="110035"> 武警士兵证</option>
				<option value="110036"> 重号武警士兵证</option>
				<option value="119998"> 系统使用的个人证件识别标识</option>
				<option value="119999"> 其它个人证件识别标识</option>
			</select></TD>
			<input type="hidden" name = "InsuIDTypeh">
			<td class=title>被保人证件号码</td>
		<TD class=input><Input class=input id="Ins4" name="InsuIDNo"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人生日</td>
		<TD class=input><Input class="coolDatePicker" id="Ins5" dateFormat="short" name="InsuBirthday"></TD>
		<td class=title>被保人电子邮箱</td>
		<TD class=input><Input class=input id="Ins6" name="InsuEmail">
		</TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人通讯地址	</td>
		<TD class=input><Input class=input id="Ins7" name="InsuAddress"></TD>
		<td class=title>被保人邮政编码	</td>
		<TD class=input><Input class=input id="Ins8" name="InsuZipCode"></TD>	
	</tr>
	 
	<TR class=common>
		<td class=title>被保人家庭电话</td>
		<TD class=input><Input class=input id="Ins9" name="InsuPhone"></TD>
		<td class=title>被保人移动电话	</td>
		<TD class=input><Input class=input id="Ins10" name="InsuMobile"></TD>
	</tr>
	 
	<TR class=common>
		<td class=title>健康告知标志</td>  
		<TD class=input> 
			<select name="HealthFlag" id="Ins11" style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="0"> 无健康告知</option>
						 <option value="1"> 有健康告知</option>
						</select></TD>
		<td class=title>被保人职业代码</td>  
		<TD class=input> 
			<select name="InsuJobCode" id="Ins12" style="background-color: #D7E1F6">  
						 <option value=""></option>
						 <option value="01"> 国家机关、党群组织、企业、事业单位人员</option>
						 <option value="02"> 卫生专业技术人员</option>
						 <option value="03"> 金融业务人员</option>
						 <option value="04"> 法律专业人员</option>
						 <option value="05"> 教学人员</option>
						 <option value="06"> 新闻出版及文学艺术工作人员</option>
						 <option value="07"> 宗教职业者</option>
						 <option value="08"> 邮政和电信业务人员</option>
						 <option value="09"> 商业、服务业人员</option>
						 <option value="10"> 农、林、牧、渔、水利业生产人员</option>
						 <option value="11"> 运输人员</option>
						 <option value="12"> 地质勘测人员</option>
						 <option value="13"> 工程施工人员</option>
						 <option value="14"> 加工制造、检验及计量人员</option>
						 <option value="15"> 军人</option>
						 <option value="16"> 无业</option>
					
			</select>
		</TD>
	</tr>
	<TR class=common>
		<td class=title>被保人证件有效期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="InsuValidYear"></TD>
	</tr>

</table>
 

<hr>

<%--*************************受益人信息************************--%>
<table class=common align=center> 
	<tr> 
		<td class=titleImg align=center>【受益人信息】</td>
		 <td colspan="2"><input type="checkbox" id = "BnfFlag" name="BnfFlag"  onClick="setBnfFlag(this);"><%-- 
	<input type="hidden" name="BeneficiaryIndicator"> 
		 --%><font color="red">受益人是否为法定(选中为法定)</font></td> 
	</tr>    

	<tr >  
		<td>与被保险人关系</td>
		<td>收益类别</td>
		<td>姓名</td>
		<td>性别</td>
		<td>证件类型	</td>
		<td>证件号码	</td>
		<td>受益人生日	</td>
		<td>受益比例</td>
		<td>受益顺序</td>
		<td>证件有效期</td>
	</tr>
						
<%--*************受益人1*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> 本身</option>
				<option value="2"> 丈夫</option>
				<option value="3"> 妻子</option>
				<option value="4"> 父亲</option>
				<option value="5"> 母亲</option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿</option>
				<option value="8"> 祖父</option>
				<option value="9"> 祖母</option>
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
				<option value="26"> 其他亲戚</option>
				<option value="27"> 同事</option>
				<option value="28"> 朋友</option>
				<option value="29"> 雇主</option>
				<option value="30"> 其他</option>	  
			</select>
		</td>
		<td>
			<select name="BnfType1" id="BnfReadOnly39" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 生存受益人</option>
				<option value="1"> 身故受益人</option>
			</select>
		</td>
		<td><input name="BnfName1" type="text" id="BnfReadOnly12" class=common size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfSex1" id="BnfReadOnly13" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 其他</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfIDType1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> 居民身份证</option>
				<option value="110002"> 重号居民身份证</option>
				<option value="110003"> 临时居民身份证</option>
				<option value="110004"> 重号临时居民身份证</option>
				<option value="110005"> 户口簿</option>
				<option value="110006"> 重号户口簿</option>
				<option value="110011"> 离休干部荣誉证</option>
				<option value="110012"> 重号离休干部荣誉证</option>
				<option value="110013"> 军官退休证</option>
				<option value="110014"> 重号军官退休证</option>
				<option value="110015"> 文职干部退休证</option>
				<option value="110016"> 重号文职干部退休证</option>
				<option value="110017"> 军事院校学员证</option>
				<option value="110018"> 重号军事院校学员证</option>
				<option value="110019"> 港澳居民往来内地通行证</option>
				<option value="110020"> 重号港澳居民往来内地通行证</option>
				<option value="110021"> 台湾居民来往大陆通行证</option>
				<option value="110022"> 重号台湾居民来往大陆通行证</option>
				<option value="110023"> 中华人民共和国护照</option>
				<option value="110024"> 重号中华人民共和国护照</option>
				<option value="110025"> 外国护照</option>
				<option value="110026"> 重号外国护照</option>
				<option value="110027"> 军官证</option>
				<option value="110028"> 重号军官证</option>
				<option value="110029"> 文职干部证</option>
				<option value="110030"> 重号文职干部证</option>
				<option value="110031"> 警官证</option>
				<option value="110032"> 重号警官证</option>
				<option value="110033"> 军人士兵证</option>
				<option value="110034"> 重号军人士兵证</option>
				<option value="110035"> 武警士兵证</option>
				<option value="110036"> 重号武警士兵证</option>
				<option value="119998"> 系统使用的个人证件识别标识</option>
				<option value="119999"> 其它个人证件识别标识</option>
			</select></td>
		<td><input name="BnfIDNo1" type="text" id="BnfReadOnly15" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BnfBirthday1" id="BnfReadOnly18" size="10"></TD>
		<td><input name="BnfBnfLot1" type="text"  id="BnfReadOnly16" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=BnfBnfGrade1  id="BnfReadOnly17" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="BnfValidYear1"></TD>
	</tr>
				
<%--*************受益人2*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
				<option value=""></option> 
			    <option value="1"> 本身</option>
				<option value="2"> 丈夫</option>
				<option value="3"> 妻子</option>
				<option value="4"> 父亲</option>
				<option value="5"> 母亲</option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿</option>
				<option value="8"> 祖父</option>
				<option value="9"> 祖母</option>
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
				<option value="26"> 其他亲戚</option>
				<option value="27"> 同事</option>
				<option value="28"> 朋友</option>
				<option value="29"> 雇主</option>
				<option value="30"> 其他</option>	   
			</select></td>
		<td><select name="BnfType2" id="BnfReadOnly40" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 生存受益人</option>
				<option value="1"> 身故受益人</option>
		</select></td>
		<td><input name="BnfName2" type="text" id="BnfReadOnly22" class=common size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfSex2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 其他</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfIDType2" id="BnfReadOnly24" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> 居民身份证</option>
				<option value="110002"> 重号居民身份证</option>
				<option value="110003"> 临时居民身份证</option>
				<option value="110004"> 重号临时居民身份证</option>
				<option value="110005"> 户口簿</option>
				<option value="110006"> 重号户口簿</option>
				<option value="110011"> 离休干部荣誉证</option>
				<option value="110012"> 重号离休干部荣誉证</option>
				<option value="110013"> 军官退休证</option>
				<option value="110014"> 重号军官退休证</option>
				<option value="110015"> 文职干部退休证</option>
				<option value="110016"> 重号文职干部退休证</option>
				<option value="110017"> 军事院校学员证</option>
				<option value="110018"> 重号军事院校学员证</option>
				<option value="110019"> 港澳居民往来内地通行证</option>
				<option value="110020"> 重号港澳居民往来内地通行证</option>
				<option value="110021"> 台湾居民来往大陆通行证</option>
				<option value="110022"> 重号台湾居民来往大陆通行证</option>
				<option value="110023"> 中华人民共和国护照</option>
				<option value="110024"> 重号中华人民共和国护照</option>
				<option value="110025"> 外国护照</option>
				<option value="110026"> 重号外国护照</option>
				<option value="110027"> 军官证</option>
				<option value="110028"> 重号军官证</option>
				<option value="110029"> 文职干部证</option>
				<option value="110030"> 重号文职干部证</option>
				<option value="110031"> 警官证</option>
				<option value="110032"> 重号警官证</option>
				<option value="110033"> 军人士兵证</option>
				<option value="110034"> 重号军人士兵证</option>
				<option value="110035"> 武警士兵证</option>
				<option value="110036"> 重号武警士兵证</option>
				<option value="119998"> 系统使用的个人证件识别标识</option>
				<option value="119999"> 其它个人证件识别标识</option>
			</select></td>
		<td><input name="BnfIDNo2" type="text" id="BnfReadOnly25" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BnfBirthday2" id="BnfReadOnly28" size="10"></TD>
		<td><input name="BnfBnfLot2" type="text"  id="BnfReadOnly26" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfBnfGrade2"  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="BnfValidYear2"></TD>
	</tr>
	
<%--*************受益人3*************--%>
	<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured3" id="BnfReadOnly31" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="1"> 本身</option>
				<option value="2"> 丈夫</option>
				<option value="3"> 妻子</option>
				<option value="4"> 父亲</option>
				<option value="5"> 母亲</option>
				<option value="6"> 儿子</option>
				<option value="7"> 女儿</option>
				<option value="8"> 祖父</option>
				<option value="9"> 祖母</option>
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
				<option value="26"> 其他亲戚</option>
				<option value="27"> 同事</option>
				<option value="28"> 朋友</option>
				<option value="29"> 雇主</option>
				<option value="30"> 其他</option>	  
			</select></td>
		<td><select name="BnfType3" id="BnfReadOnly41" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 生存受益人</option>
				<option value="1"> 身故受益人</option>
		</select></td>
		<td><input name="BnfName3" type="text" id="BnfReadOnly32" class=common size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BnfSex3" id="BnfReadOnly33" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 其他</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BnfIDType3" id="BnfReadOnly34" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="110001"> 居民身份证</option>
				<option value="110002"> 重号居民身份证</option>
				<option value="110003"> 临时居民身份证</option>
				<option value="110004"> 重号临时居民身份证</option>
				<option value="110005"> 户口簿</option>
				<option value="110006"> 重号户口簿</option>
				<option value="110011"> 离休干部荣誉证</option>
				<option value="110012"> 重号离休干部荣誉证</option>
				<option value="110013"> 军官退休证</option>
				<option value="110014"> 重号军官退休证</option>
				<option value="110015"> 文职干部退休证</option>
				<option value="110016"> 重号文职干部退休证</option>
				<option value="110017"> 军事院校学员证</option>
				<option value="110018"> 重号军事院校学员证</option>
				<option value="110019"> 港澳居民往来内地通行证</option>
				<option value="110020"> 重号港澳居民往来内地通行证</option>
				<option value="110021"> 台湾居民来往大陆通行证</option>
				<option value="110022"> 重号台湾居民来往大陆通行证</option>
				<option value="110023"> 中华人民共和国护照</option>
				<option value="110024"> 重号中华人民共和国护照</option>
				<option value="110025"> 外国护照</option>
				<option value="110026"> 重号外国护照</option>
				<option value="110027"> 军官证</option>
				<option value="110028"> 重号军官证</option>
				<option value="110029"> 文职干部证</option>
				<option value="110030"> 重号文职干部证</option>
				<option value="110031"> 警官证</option>
				<option value="110032"> 重号警官证</option>
				<option value="110033"> 军人士兵证</option>
				<option value="110034"> 重号军人士兵证</option>
				<option value="110035"> 武警士兵证</option>
				<option value="110036"> 重号武警士兵证</option>
				<option value="119998"> 系统使用的个人证件识别标识</option>
				<option value="119999"> 其它个人证件识别标识</option>
			</select></td>
		<td><input name="BnfIDNo3" type="text" id="BnfReadOnly35" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BnfBirthday3" id="BnfReadOnly38" size="10"></TD>
		<td><input name="BnfBnfLot3" type="text"  id="BnfReadOnly36" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name=BnfBnfGrade3  id="BnfBnfGrade3" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short"  name="BnfValidYear3"></TD>
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
		<td>保费(元)</td>
		<td>保额(元)</td>
		<td>缴费方式</td>
		<td>红利领取方式</td>
		<td>保险年期类型</td>
		<td>保险年期</td>
		<td>缴费年期类型</td>
		<td>缴费年期</td>
	</tr>

	<tr class=common2>
		<td bgColor="#F7F7F7">
			 <select name="Code"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)"> 
            	<option value="211902">中韩安赢借款人意外伤害保险A款</option>
            	<option value="231204">中韩智赢财富两全保险（分红型）C款</option>
            	<option value="231201">中韩智赢财富两全保险（分红型）A款</option>
                <option value="231202">中韩智赢财富两全保险（分红型）B款</option>
                <option value="231203">中韩卓越财富两全保险（分红型）</option>
                <option value="211901">中韩安赢借款人意外伤害保险</option>
                <option value="221201">中韩保驾护航两全保险A款</option>
            </select>
        </td>
		<td><input name="Mult" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="Prem" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="Amnt" type="text"  size="10"/></td>
		<TD>  
			<select name="PayIntv" class="common1">
				<option value=""></option>
				<option value="1"> 趸交 </option>
                <option value="2"> 月交 </option>
                <option value="3"> 季交 </option>
                <option value="4"> 半年交 </option>
                <option value="5"> 年交 </option>
                <option value="6"> 不定期 </option>
             </select></TD>	
             <TD><select name="BonusGetMode" class="common1">
			   <option value=""></option>
			   <option value="0"> 无 </option>
			   <option value="1"> 积累生息 </option>
               <option value="2"> 领取现金 </option>
               <option value="3"> 抵交保费 </option>
               <option value="4"> 其他 </option>
               <option value="5"> 增额交清 </option>
	    </select></TD>
		<td> 
		
			<select name="InsuYearFlag" class="common1">
				<option value=""></option>
				<option value="1"> 保至某确定年领 </option>
                <option value="2"> 月保 </option>
                <option value="3"> 日保 </option>
                <option value="4"> 年保 </option>
                
             </select></td>
		<td><input name="InsuYear" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PayEndYearFlag" class="common1">
				<option value=""></option>
				   <option value="0"> 趸交 </option>
                <option value="1"> 缴至某确定年龄 </option>
                <option value="2"> 月缴 </option>
                <option value="3"> 日缴 </option>
                <option value="4"> 年缴 </option>
               
             </select></td>
		<td><input name="PayEndYear" type="text"  size="5" maxlength="5"  /></td>
	</tr>
				
</table>
	


<%--*************附加险*************
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【附加险】</td>	
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
			<span id="spanBnfList"></span>
		</td>
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
	

	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="Code1"  style="background-color: #D7E1F6" onchange="SSRiskFlag(this.options[this.options.selectedIndex].value)">
					<option value=""></option>
                <option value="101">利安附加保险 </option>
            </select></td>
		<td><input name="Mult1" type="text"   size="3"/></td>
		<td bgColor="#F7F7F7"><input name="Prem1" type="text"  size="10"/></td>
		<td bgColor="#F7F7F7"><input name="Amnt1" type="text"  size="10"/></td>
		<td>    
			<select name="InsuYearFlag1" class="common1">
				<option value=""></option>
				<option value="1"> 保至某确定年领 </option>
                <option value="2"> 月保 </option>
                <option value="3"> 日保 </option>
                <option value="4"> 年保 </option>
                
             </select></td>
		<td><input name="InsuYear1" type="text"  size="3" maxlength="5"/></td>
		<td>
			<select name="PayEndYearFlag1" class="common1"0>
				<option value=""></option>
				<option value="1"> 缴至某确定年龄 </option>
                <option value="2"> 月缴 </option>
                <option value="3"> 日缴 </option>
                <option value="4"> 年缴 </option>
             </select></td>
		<td><input name="PayEndYear1" type="text"  size="5" maxlength="5"  /></td>
	</tr>	
</table>
</div>
	--%>
	<hr>
<%--*************投保信息*************--%>		
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【投保信息】</td>
	</tr>
<%--	 
	<TR class=common>
		<td class=title>交费方式</td>
		<TD class=input>  
			<select name=PayIntv class="common1">
				<option value=""></option>
				<option value="1"> 趸交 </option>
                <option value="2"> 月交 </option>
                <option value="3"> 季交 </option>
                <option value="4"> 半年交 </option>
                <option value="5"> 年交 </option>
                <option value="6"> 不定期 </option>
             </select></TD>	
        
	</tr>
	--%>
     <tr>
        <td class=title>银行账号</td>
		<TD class=input><Input class=input name=AccNo></TD>
	</tr>
	<tr>
        <td class=title>银行账号姓名</td>
		<TD class=input><Input class=input name=AccName></TD>
	</tr>
	<%--
	<tr>
        <td class=title>红利领取方式</td>
		<TD><select name="BonusGetMode" class="common1">
			   <option value=""></option>
			   <option value="0"> 无 </option>
			   <option value="1"> 积累生息 </option>
               <option value="2"> 领取现金 </option>
               <option value="3"> 抵交保费 </option>
               <option value="4"> 其他 </option>
               <option value="5"> 增额交清 </option>
	    </select></TD>
	</tr>
	--%>
</table>

<div id="divOwnlist1" style="display: 'none'"> 
<hr/>

<table class=common align=center>
	<tr> 
		<td class=titleImg align=center>【贷款信息】</td>
	</tr>  	
</table> 



<table class=common align=center> 

	<TR class=common>
		<td class=title>贷款合同号</td>
		<TD class=input><Input class=input name=LoanNo  ></TD>
		<td class=title>贷款机构</td>
		<TD class=input><Input class=input name=LoanBank ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款日期 </td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=LoanDate  ></TD>
		<td class=title>贷款到期日</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=LoanEndDate  ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款种类</td>
		<TD class=input>
		<select name="LoanType" class="common1">
			   <option value=""></option>
			   <option value="00">一般商业贷款 </option>
			   <option value="01">组合商业贷款 </option>
               <option value="10">公积金组合贷款 </option>
               <option value="11">纯公积金贷款 </option>
               <option value="20">贴息助学贷款 </option>
	    </select></TD>
		<td class=title>贷款账号</td>
		<TD class=input><Input class=input name=LoanAccNo  ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款金额 (元)</td>
		<TD class=input><Input class=input name=LoanPrem  ></TD>
		<td class=title>保险起始日</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsuDate  ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>保险期满日 </td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsuEndDate  ></TD>
	</tr>
	
</table>
</div>
<hr>
<%--*************页面设定*************--%>				
<table class=common align=center>				
					
	<tr> 
		<td><input class="cssButton" type="button" onClick="initBox()" name="Submit3" value=" 清空信息 " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" 自 动 填 数 " onClick="initInputBox()" /></td>
	</tr>

</table>

 

<hr/>

<%    
String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
StringBuilder inFilePath = new StringBuilder("/msg/")
					//.append("msg/")  
					//.append("1").append('/')
					//.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append("abcTestIn.xml");  
StringBuilder reFilePath = new StringBuilder("/msg/")
					//.append("msg/")     
					//.append("1").append('/')
					//.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append("abcTestReturn.xml");
			
					
					   
%>     
<a target="_blank" class="smallLink" href="<%=inFilePath%>?thisTime=<%=DateUtil.getCurDateTime()%>">发送报文内容</a> 
<a target="_blank" class="smallLink" href="<%=reFilePath%>?thisTime=<%=DateUtil.getCurDateTime()%>">返回报文内容</a>
<hr/>  
<table class=common align=center> 
	<caption>发送报文信息</caption>   
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
