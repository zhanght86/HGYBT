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
<SCRIPT src="TestYBTCont.js"></SCRIPT>
<script type="text/javascript">

function setRiskFlag(c){
		if(c.checked == true){
			
			fm.all('hiddenBnf').value='0';
		    //alert(fm.all('hiddenBnf').value);
		}else{ 
			fm.all('hiddenBnf').value='1';
			//alert(fm.all('hiddenBnf').value);
		}
	}


	function setBnfFlag(BnfFlag){ 
		if(BnfFlag.checked == true){
			document.getElementById("BnfReadOnly11").disabled=true; 
			document.getElementById("BnfReadOnly12").readOnly=true; 
			document.getElementById("BnfReadOnly13").disabled=true; 
			document.getElementById("BnfReadOnly14").disabled=true; 
			document.getElementById("BnfReadOnly15").readOnly=true; 
			document.getElementById("BnfReadOnly16").readOnly=true; 
			document.getElementById("BnfReadOnly17").disabled=true;
			document.getElementById("BnfReadOnly18").readOnly=true; 
			document.getElementById("BnfReadOnly21").disabled=true; 
			document.getElementById("BnfReadOnly22").readOnly=true; 
			document.getElementById("BnfReadOnly23").disabled=true; 
			document.getElementById("BnfReadOnly24").disabled=true; 
			document.getElementById("BnfReadOnly25").readOnly=true; 
			document.getElementById("BnfReadOnly26").readOnly=true; 
			document.getElementById("BnfReadOnly27").disabled=true;
			document.getElementById("BnfReadOnly28").readOnly=true; 
			document.getElementById("BnfReadOnly31").disabled=true; 
			document.getElementById("BnfReadOnly32").readOnly=true; 
			document.getElementById("BnfReadOnly33").disabled=true; 
			document.getElementById("BnfReadOnly34").disabled=true; 
			document.getElementById("BnfReadOnly35").readOnly=true; 
			document.getElementById("BnfReadOnly36").readOnly=true; 
			document.getElementById("BnfReadOnly37").disabled=true;
			document.getElementById("BnfReadOnly38").readOnly=true; 
			
			//受益人信息
			//受益人姓名
			fm.BnfFullName1.value = ''; 
			//受益人性别
			fm.BnfGender1.value = '';
			//受益人证件类型
			fm.BnfGovtIDTC1.value = '';
			//受益人证件号码
			fm.BnfGovtID1.value = '';
			//受益人出生日期
			fm.BnfBirthDate1.value = '';
			//受益百分数
			fm.InterestPercent1.value = '';
			//受益顺序
			fm.Sequence1.value = '';  
			//受益人与被报人的关系
			fm.BnfToInsRelation1.value = '';
			//受益人是否为法定标志  
			fm.BeneficiaryIndicator.value='Y'; 
			
			//受益人信息
			//受益人姓名
			fm.BnfFullName2.value = ''; 
			//受益人性别
			fm.BnfGender2.value = '';
			//受益人证件类型
			fm.BnfGovtIDTC2.value = '';
			//受益人证件号码
			fm.BnfGovtID2.value = '';
			//受益人出生日期
			fm.BnfBirthDate2.value = '';
			//受益百分数
			fm.InterestPercent2.value = '';
			//受益顺序
			fm.Sequence2.value = '';  
			//受益人与被报人的关系
			fm.BnfToInsRelation2.value = '';
			//受益人是否为法定标志  
		
			
			//受益人信息
			//受益人姓名
			fm.BnfFullName3.value = ''; 
			//受益人性别
			fm.BnfGender3.value = '';
			//受益人证件类型
			fm.BnfGovtIDTC3.value = '';
			//受益人证件号码
			fm.BnfGovtID3.value = '';
			//受益人出生日期
			fm.BnfBirthDate3.value = '';
			//受益百分数
			fm.InterestPercent3.value = '';
			//受益顺序
			fm.Sequence3.value = '';  
			//受益人与被报人的关系
			fm.BnfToInsRelation3.value = '';
			//受益人是否为法定标志  
		
		}else{ 
			document.getElementById("BnfReadOnly11").disabled=false; 
			document.getElementById("BnfReadOnly12").readOnly=false; 
			document.getElementById("BnfReadOnly13").disabled=false; 
			document.getElementById("BnfReadOnly14").disabled=false; 
			document.getElementById("BnfReadOnly15").readOnly=false; 
			document.getElementById("BnfReadOnly16").readOnly=false;
			document.getElementById("BnfReadOnly17").disabled=false; 
			document.getElementById("BnfReadOnly18").readOnly=false; 
			document.getElementById("BnfReadOnly21").disabled=false; 
			document.getElementById("BnfReadOnly22").readOnly=false; 
			document.getElementById("BnfReadOnly23").disabled=false; 
			document.getElementById("BnfReadOnly24").disabled=false; 
			document.getElementById("BnfReadOnly25").readOnly=false; 
			document.getElementById("BnfReadOnly26").readOnly=false; 
			document.getElementById("BnfReadOnly27").disabled=false;
			document.getElementById("BnfReadOnly28").readOnly=false;  
			document.getElementById("BnfReadOnly31").disabled=false; 
			document.getElementById("BnfReadOnly32").readOnly=false; 
			document.getElementById("BnfReadOnly33").disabled=false; 
			document.getElementById("BnfReadOnly34").disabled=false; 
			document.getElementById("BnfReadOnly35").readOnly=false; 
			document.getElementById("BnfReadOnly36").readOnly=false; 
			document.getElementById("BnfReadOnly37").disabled=false;
			document.getElementById("BnfReadOnly38").readOnly=false; 
			 
			//受益人信息1
			//受益人姓名
			fm.BnfFullName1.value = '王五';
			//受益人性别
			fm.BnfGender1.value = '1';
			//受益人证件类型
			fm.BnfGovtIDTC1.value = '0';
			//受益人证件号码
			fm.BnfGovtID1.value = '220523850917341';
			//受益人出生日期
			fm.BnfBirthDate1.value = '1985-09-17';
			//受益百分数
			fm.InterestPercent1.value = '100'; 
			//受益顺序
			fm.Sequence1.value = '1';
			//受益人与被报人的关系
			fm.BnfToInsRelation1.value = '1';
				//受益人是否为法定标志
			fm.BeneficiaryIndicator.value = 'N';
		}
	}
	
	function setInsFlag(InsFlag){
		if(InsFlag.checked == true){
		  
			document.getElementById("Ins1").readOnly=true;  
			document.getElementById("Ins2").disabled=true; 
			document.getElementById("Ins3").disabled=true; 
			document.getElementById("Ins4").readOnly=true;
			document.getElementById("Ins5").readOnly=true;  
			document.getElementById("Ins6").readOnly=true;
			document.getElementById("Ins7").readOnly=true;
			document.getElementById("Ins8").readOnly=true; 
			document.getElementById("Ins9").readOnly=true; 
			document.getElementById("Ins10").readOnly=true; 
			document.getElementById("Ins11").disabled=true; 
		

//被保人信息 
	//被保人姓名
	fm.InsFullName.value = fm.AppFullName.value
	//被保人性别
	fm.InsGender.value = fm.AppGender.value
	//被保人证件类型
	fm.InsGenderh.value = fm.AppGender.value
	
	fm.InsGovtIDTC.value = fm.AppGovtIDTC.value
	fm.InsGovtIDTCh.value = fm.AppGovtIDTC.value 
	//被保人证件号码
	fm.InsGovtID.value = fm.AppGovtID.value
	//被保人生日
	fm.InsBirthDate.value = fm.AppBirthDate.value
	//被保人电子邮箱
	fm.InsAddrLine.value = fm.AppAddrLine.value
	//被保人通讯地址	
	fm.InsLine1.value = fm.AppLine1.value 
	//被保人邮政编码
	fm.InsZip.value = fm.AppZip.value
	//被保人家庭电话 
	fm.InsDialNumber1.value = fm.AppDialNumber1.value
	//被保人移动电话
	fm.InsDialNumber3.value = fm.AppDialNumber3.value
	//健康告知
	fm.HealthIndicator.value = 'N';
	fm.all('AppToInsRelation').value='8'; 
		}  
		else 
		{   
			document.getElementById("Ins1").readOnly=false;
			document.getElementById("Ins2").disabled=false;
			document.getElementById("Ins3").disabled=false;
			document.getElementById("Ins4").readOnly=false;
			document.getElementById("Ins5").readOnly=false;
			document.getElementById("Ins6").readOnly=false;
			document.getElementById("Ins7").readOnly=false;
			document.getElementById("Ins8").readOnly=false;
			document.getElementById("Ins9").readOnly=false;
			document.getElementById("Ins10").readOnly=false;
			document.getElementById("Ins11").disabled=false;

//被保人信息
	//被保人姓名
	fm.InsFullName.value = '李四';
	//被保人性别
	fm.InsGender.value = '1'; 
	//被保人证件类型
	fm.InsGovtIDTC.value = '0';
	//被保人证件号码
	fm.InsGovtID.value = '220523850917341';
	//被保人生日
	fm.InsBirthDate.value = '1985-09-17';
	//被保人电子邮箱
	fm.InsAddrLine.value = 'lisi@163.com';
	//被保人通讯地址	
	fm.InsLine1.value = '天津市和平区同安道'; 
	//被保人邮政编码
	fm.InsZip.value = '222222';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '02153558550';
	//被保人移动电话
	fm.InsDialNumber3.value = '13821576911';
	//被保人国籍
	fm.InsNationality.value='中国';
	fm.InsNationalityNo.value = '156';
	//健康告知
	fm.HealthIndicator.value = 'N';	
	
		} 
	}
	
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="TestYBTContInit.jsp"%>  

		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./TestYBTSave.jsp" method=post name=fm target="fraSubmit">


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
		<TD class=input><Input class=input name=RegionCode></TD>
		<td class=title>网点代码</td>
		<TD class=input><Input class=input name=Branch></TD>
		
	</TR>
	
	<TR class=common> 
		<td class=title>销售人员工号</td>
		<TD class=input><Input class=input name=Teller></TD>
		<td class=title>投保日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=SubmissionDate></TD>
	</TR>
	
	<TR class=common>
		<td class=title>投保书号</td>
		<TD class=input><Input class=input name=HOAppFormNumber></TD>
		<td class=title>保单印刷号</td>
		<TD class=input><Input class=input name=ProviderFormNumber></TD>	
	</tr>
	<TR class=common>
		<td class=title>销售人员资格证书编号</td>
		<TD class=input><Input class=input name=BankManagerAgentId></TD>
		<td class=title>营销人员名称</td>
		<TD class=input><Input class=input name=BankManagerName></TD>	
	</tr>
	
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
				<option value="4"> 回乡证</option>
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
		<td class=title>职业及代码</td>
		<TD class=input><Input class=input name=AppJobCode></TD>
		<td class=title>工作单位</td>
		<TD class=input><Input class=input name=AppCompany></TD>
	</tr>
	
	<TR class=common>
		<td class=title>国籍</td>
		<TD class=input>
		<input class="codeno" name="AppNationalityNo" verify="投保人国籍|NotNull" ondblclick="return showCodeList('nativeplace',[this,AppNationality],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('nativeplace',[this,AppNationality],[0,1],null,null,null,1,null,1);"><input class="codename" name="AppNationality" readonly="true" elementtype="nacessary" />
		</td>
	</tr>
	
	<TR class=common>
		<td class=title>身高</td>
		<TD class=input><Input class=input name=AppStature verify="投保人身高|NUM">CM</TD>
		<td class=title>体重</td>
		<TD class=input><Input class=input name=AppWeight verify="投保人体重|NUM">KG</TD>
	</tr>
	
	<TR class=common> 
		<td class=title>年收入</td>
		<TD class=input><Input class=input name=AppEstSalary verify="投保人收入|NUM">分</TD>
		<td class=title>身份证有效期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=AppGovtTermDate></TD>
	</TR>
	
    <TR class=common> 
		<td class=title>家庭年收入</td>
		<TD class=input><Input class=input name=FamilyEstSalary verify="投保人收入|NUM">分</TD>
		<td class=title>投保人居民类型</td>
		<TD class=input><select name="PbDenType" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="1">城镇</option>
				<option value="2">农村</option>
			</select></TD>
	</TR>
	
	<TR class=common>
		<td class=title>投保人是被保人的</td>
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
				<option value="4"> 回乡证</option>
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
		<td class=title>职业及代码</td>
		<TD class=input><Input class=input name=InsJobCode></TD>
		<td class=title>工作单位</td>
		<TD class=input><Input class=input name=InsCompany></TD>
	</tr>
	
	<TR class=common>
		<td class=title>国籍</td>
		<TD class=input>
		<input class="codeno" name="InsNationalityNo" verify="被保人国籍|NotNull" ondblclick="return showCodeList('nativeplace',[this,InsNationality],[0,1],null,null,null,1,null,1);" onkeyup="return showCodeListKey('nativeplace',[this,InsNationality],[0,1],null,null,null,1,null,1);"><input class="codename" name="InsNationality" readonly="true" elementtype="nacessary" />
		</TD>
	</tr>
	
	<TR class=common>
		<td class=title>身高</td>
		<TD class=input><Input class=input name=InsStature verify="被保人身高|NUM">CM</TD>
		<td class=title>体重	</td>
		<TD class=input><Input class=input name=InsWeight verify="被保人体重|NUM">KG</TD>
	</tr>
	
	<TR class=common> 
		<td class=title>年收入</td>
		<TD class=input><Input class=input name=InsEstSalary  verify="被保人收入|NUM">分</TD>
		<td class=title>身份证有效期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=InsGovtTermDate></TD>
	</TR>
	 
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
				<option value="4"> 回乡证</option>
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
				<option value="4"> 回乡证</option>
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
				<option value="4"> 回乡证</option>
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
				<option value=""></option>
				<option value="010">中韩永利年年年金保险（分红型）</option>
				<option value="009">中韩悦未来年金险</option>
				<option value="008">中韩安赢借款人意外伤害保险A款</option>
				<option value="007">中韩智赢财富两全保险（分红型）C款</option>
				<option value="006">中韩保驾护航两全保险A款</option>
            	<option value="001">中韩智赢财富两全保险（分红型）A款</option>
                <option value="002">中韩智赢财富两全保险（分红型）B款</option>
                <option value="003">中韩卓越财富两全保险（分红型）</option>
                <option value="005">中韩永裕丰年年金保险（分红型）</option>
                <option value="004">中韩安赢借款人意外伤害保险</option>
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
	


<%--*************附加险*************--%>	
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【附加险】</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList);"></td>
	</tr>	
</table> 

<div id="divRiskList" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList">
	
	<tr >
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
			<select name="ProductCode1"  style="background-color: #D7E1F6">
					<option value=""></option>
            	<option value="001">中韩金满仓两全保险（分红型）</option>
                <option value="002">中韩金如意两全保险（分红型）</option>
                <option value="003">暂未增加</option>
                <option value="004">暂未增加 </option>
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
   
<%--*************附加险代码*************--%>		
	<tr class=common2>
		<td bgColor="#F7F7F7">
			<select name="ProductCode2"  style="background-color: #D7E1F6">
				<option value=""></option>
            	<option value="001">中韩智赢财富两全保险（分红型）A款</option>
                <option value="002">中韩智赢财富两全保险（分红型）B款</option>
                <option value="003">中韩卓越财富两全保险（分红型）</option>
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
		<TD class=input><Input class=input name=LoanAmount id="LoanAmount"><font color="red">(单位：分)</font></TD>
		<td class=title>保险金额	</td>
		<TD class=input><input class=input  name = "FaceAmount"><font color="red">(单位：分)</font></TD>	
	</tr>
	
	<TR class=common>
		<td class=title>贷款类型</td>
		<TD class=input>
			<select name=LoanProductCode class="common1">
				<option value=""></option>
				<option value="1"> 短期个人自用车贷款 </option>
                <option value="3"> 短期个人综合消费贷款 </option>
                <option value="4"> 短期个人信用贷款</option>
                <option value="5"> 短期个人质押贷款 </option>
                <option value="6"> 短期个人房屋抵押贷款 </option>
                <option value="7"> 短期个人经营贷款 </option>
                <option value="8"> 中长期个人经营贷款 </option>
                <option value="9"> 短期下岗失业人员小额担保贷款 </option>
                <option value="10"> 中央贴息国家助学贷款 </option>
                <option value="11"> 地方贴息国家助学贷款 </option>
                <option value="12"> 一般商业性助学助学贷款 </option>
                <option value="13"> 中长期下岗失业人员小额担保贷款 </option>
                <option value="14"> 短期个人商用车贷款</option>
                <option value="15"> 个人小额贷款 </option>
                <option value="17"> 个人一手住房购置贷款（含自建房） </option>
                <option value="18"> 个人商用房贷款 </option>
                <option value="19"> 个人住房装修贷款 </option>
                <option value="20"> 个人家居消费贷款 </option>
                <option value="21"> 个人二手住房购置贷款 </option>
                <option value="23"> 其他个人住房贷款 </option>
                <option value="26"> 中长期个人自用车贷款 </option>
                <option value="27"> 中长期个人大额耐用品贷款</option>
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
				<option value=""></option>
				<option value="1"> 年缴 </option>
                <option value="2"> 月缴 </option>
                <option value="3"> 半年缴 </option>
                <option value="4"> 季缴 </option>
                <option value="5"> 趸缴 </option>
                <option value="6"> 不定期缴 </option>
                <option value="9"> 其他 </option>
             </select></TD>	
		<td class=title>缴费形式(频次)</td>
		<TD class=input>
			<select name=PaymentMethod class="common1">
				<option value=""></option>
				<option value="1"> 银行转账 </option>
                <option value="2"> 支票 </option>
                <option value="3"> 银行代扣 </option>
                <option value="4"> 现金 </option>
                <option value="5"> 现金送款簿 </option>
                <option value="6"> 内部转账 </option>
                <option value="7"> Pos收款 </option>
                <option value="9"> 其他 </option>
             </select></TD>
	</tr> 
	
	<TR class=common>
		<td class=title>领取方式</td>
		<TD class=input>
			<select name=BenefitMode class="common1">
				<option value=""></option>
				<option value="1"> 年领 </option>
                <option value="2"> 月领 </option>
                <option value="3"> 趸领 </option>
                <option value="4"> 季领 </option>
                <option value="5"> 任意方式 </option>
                <option value="6"> 每三年领 </option>
                <option value="7"> 半年龄 </option>
                <option value="8"> 无关 </option>
                <option value="9"> 其他 </option>
                <option value="10"> 累积生息 </option>
                <option value="11"> 直接领取 </option>
                <option value="12"> 抵交保费 </option>
                <option value="13"> 现金领取 </option>
             </select></TD>
		<td class=title>红利领取方式</td>
		<TD class=input>
			<select name=DivType class="common1">
				<option value=""></option>
				<option value="1"> 累积生息 </option>
                <option value="2"> 领取现金 </option>
                <option value="3"> 抵交保费 </option>
                <option value="4"> 增额交清 </option>
                <option value="5"> 无红利 </option>
                <option value="6"> 增额红利 </option>
                <option value="9"> 其他 </option>
             </select></TD>
	</tr>
	
	<tr>
		<td class=title>保单传送方式</td>
		<TD class=input>
			<select name=PolicyDeliveryMethod class="common1">
				<option value=""></option>
				<option value="1"> 部门发送 </option>
                <option value="2"> 邮寄或专递 </option>
                <option value="3"> 上门递送 </option>
                <option value="4"> 银行领取 </option>
                <option value="5"> 区域中心渠道银行出单 </option>
                <option value="6"> 区域中心渠道保险公司出单 </option>
                <option value="9"> 其他 </option>
             </select></TD>
        <td class=title>银行账号</td>
		<TD class=input><Input class=input name=AccountNumber></TD>	
       
	</tr>
	
	<TR class=common> 
	 <td class=title>帐户姓名</td>
		<TD class=input><Input class=input name=AcctHolderName></TD>	
		<td class=title>特别约定</td>
		<TD class=input><Input class=input name=SpecialClause></TD>
	</tr>
		<tr>
		<td class=title>职业告知标志</td>
		<TD class=input>
			<select name=OccupationIndicator class="common1">
				<option value="N">无职业告知</option>
				<option value="Y">有职业告知</option>           
             </select></TD>
        <td class=title>领取年期/年龄类型</td>
		<TD class=input>
			<select name=PayOutDurationMode  class="common1">
				<option value=""></option>
				<option value="1">领至某确定年龄</option>
				<option value="2">年领</option>
				<option value="3">月领</option> 
				<option value="4">日领</option> 
				<option value="5">一次性领取</option> 
				<option value="6">不定期领</option> 
				<option value="9">其他</option> 
             </select></TD>
	</tr>
	
	<TR class=common> 
	 <td class=title>领取起始年龄</td>
		<TD class=input><Input class=input name=PayoutStart></TD>	
		<td class=title>领取年期</td>
		<TD class=input><Input class=input name=PayoutDuration></TD>
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
		<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name=ip></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name=port></td>
		<td class=title>交易类型</td>
		<td class=input> 
			<select name=tranFlagCode class="common1">
				<option value="1013"> 新单承保</option>
                <option value="1012"> 保费试算 </option>
             </select>
		</td>  
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="发送投保申请" onclick="submitForm();"></TD> 
	</TR>
</table>
<hr/>

<%    
String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
StringBuilder inFilePath = new StringBuilder("/msg/")
					//.append("msg/")  
					//.append("1").append('/')
					//.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append("icbcTestIn.xml");  
StringBuilder reFilePath = new StringBuilder("/msg/")
					//.append("msg/")     
					//.append("1").append('/')
					//.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"))
					.append("icbcTestReturn.xml");
			
					
					   
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
