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
<SCRIPT src="TestABCNewCont.js"></SCRIPT>
<script type="text/javascript">
	
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   
		<%@include file="TestABCNewContInit.jsp"%>  
 
		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();"> 
<form action="./TestABCNewContSave.jsp" method=post name=fm target="fraSubmit">


<%--*************************交易公共信息*************************--%>
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	<tr>
<td class=title>处理标志</td>
<TD class=input><select name="FunctionFlag" style="background-color: #D7E1F6;width: 90px;" onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option selected="selected" value="01"> 新保试算</option>
				<option value="02"> 新保交费</option>
			</select></TD>
		
		<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name=ip></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name=port></td>
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button value="发送交易" onclick="submitForm();"></TD> 
	</TR>
</table>
		
<hr>

<%--*************************交易信息************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【交易信息】</td>
	</tr> 
	
	<TR class=common>
		<TD class=title>银行端交易日期</TD> 
		<TD class=input><Input class="coolDatePicker"   dateFormat = "short" name=BankDate></TD>
		<TD class=title>交易流水号</TD> 
		<TD class=input><input class=input name=TransrNo></TD>
		<input type=hidden name=NewTransNo>
	</TR>
	
	<TR class=common>
		<td class=title>地区代码</td>
		<TD class=input>
			<input class="input" name="ZoneNo"/>
			<!-- 
							ondblclick="return showRegionCode()">
        	<input  name="ZoneNoName"  type="text"  size="11"/></TD>
        	 -->
		<td class=title>网点代码</td>
		<TD class=input>
			<input class="input" name="BrNo">
			<!-- 
							ondblclick="return showBranchCode()">
        	<input  name="BrNoName" type="text"  size="11"/>
        	 -->
		</TD>
	</TR>
	
	<TR class=common> 
		<td class=title>柜员代码</td>
		<TD class=input><Input class=input name=TellerNo></TD>
		<!-- 银行代码 -->
		<Input type=hidden name=BankCode value="05">
		<!-- 保险公司代码 -->
		<Input type=hidden name=InsuID value="01">
	</tr>	
	
	<TR class=common>
		<td class=title>投保申请流水号</td>
		<TD class=input><Input class=input  name=ReqsrNo><font color=red>新保交费必填项</font></TD>
		<td class=title>保费 </td>
		<TD class=input><Input class=input  name=Prem02><font color=red>新保交费必填项</font>
		</TD>
	</tr>
</table>

<hr>
<%--*************投保信息*************--%>		
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【投保信息】</td>
	</tr>
	
		<TR class=common>
		<TD class=title>投保单号</TD> 
		<TD class=input><input class=input name=ProposalContNo></TD>	
		<td class=title>投保日期</td>
		<TD class=input><Input class=coolDatePicker  dateFormat="short" name=PolApplyDate></TD>	
	</TR>
	<!-- 
	<TR class=common> 		
		<td class=title>代收付银行名称</td>
		<TD class=input><Input class=codeno  name=AccBankNAME 
							ondblclick="return ShowAccBankNAME()"
							onkeyup="return ShowAccBankNAME()">
        	<input  name="AccBankNAMEName" readonly="readonly" type="text"  size="11"/></TD>
	</TR>
	 -->
	 <!-- 
	<TR class=common> 		
		<td class=title>代收付银行账户</td>
		<TD class=input><Input class=input name=BankAccNo></TD>
	</TR>
	 -->
	<TR class=common> 	
	<td class=title>账户姓名</td>
		<TD class=input><Input class=input name=AccName></TD>	
		<td class=title>交费帐号</td>
		<TD class=input><Input class=input name=AccNo></TD>
		<!-- <td class=title>保单密码 </td>
		<TD class=input><Input class=input name=Password></TD>
		 -->
		
	</TR>	
		
	<TR class=common> 		
	 <td class=title>保单印刷号码</td>
		<TD class=input><Input class=input name=PrtNo></TD>
		<td class=title>交费形式</td>
		<TD class=input>
			<select name=PayMode class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0">现金 </option>
                <option value="1">银行卡 </option>
                <option value="2">账户 </option>
             </select></TD>	
           
	</TR>
	<TR class=common> 		
	 	
             <td class=title>特别约定</td>
			<TD class=input>
			<Input class=input name=SpecContent></TD>
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
		<TD class=input><Input class=input name=Name></TD>
		<td class=title>投保人性别</td>
		<TD class=input>
			<select name="Sex" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 不详</option>
			</select></TD>
		
	</tr> 

	<TR class=common>
		<td class=title>出生日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday></TD>
		<td class=title>投保人联系电话 </td>
		<TD class=input><Input class=input  name=Phone>
		</TD>
	</tr>
	<TR class=common> 
		<td class=title>投保人证件类型</td>
		<TD class=input><Input class=codeno name=IDType 
							ondblclick="return ShowIDTypeCode()"
							onkeyup="return ShowIDTypeCode()">
        	<input  name="IDTypeName" readonly="readonly" type="text"  size="11"/>
			</TD>
			
		<td class=title>投保人证件号码</td>
		<TD class=input><Input class=input name=IDNo></TD>
	</tr>
	<TR class=common>
		<td class=title>投保人手机号</td>
		<TD class=input><Input class=input name=Mobile></TD>
		<td class=title>投保人通讯地址</td>
		<TD class=input><Input class=input name=Address></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人通讯邮编</td>
		<TD class=input><Input class=input name=ZipCode></TD>
		<td class=title>投保人国籍</td>
		<TD class=input><Input class=input name=County></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人证件有效期</td>
		<TD class=input><Input class=input name=ValidYear></TD>
		<td class=title>投保人Email</td>
		<TD class=input><Input class=input name=Email></TD>
	</tr>
	
	<TR class=common>
		<td class=title>投保人职业代码</td>
		<TD class=input><Input class=codeno name=JobCode 
							ondblclick="return ShowJobCode()"
							onkeyup="return ShowJobCode()">
        	<input  name="JobCodeName" readonly="readonly" type="text"  size="11"/></TD>
		<td class=title>与被保人关系</td>
		<TD class=input>
			<input class="codeno" name="RelaToInsured"
							ondblclick="return ShowRelaToInsured()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelaToInsuredName" readonly="readonly" type="text"  size="10"/></TD>
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
		<td class=title></td>
		<TD class=input></TD>
		<td class=title></td>
		<TD class=input></TD>	
	</tr> 
</table>	
<div id="insured" name="insured" style="display: ''">
<table class=common align=center>
	
	
	<TR class=common>
		<td class=title>被保人姓名</td>
		<TD class=input><Input class=input name=BName id='Ins1'></TD>
		<td class=title>被保人性别</td>
		<TD class=input>
			<select name="BSex" style="background-color: #D7E1F6;width: 90px;" id='Ins2'>
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 不详</option>
			</select></TD>
		
	</tr> 

	<TR class=common>
		<td class=title>出生日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=BBirthday id='Ins3'></TD>
		<td class=title>被保人联系电话 </td>
		<TD class=input><Input class=input  name=BPhone id='Ins13'>
		</TD>
	</tr>
	<TR class=common> 
		<td class=title>被保人证件类型</td>
		<TD class=input>
			<Input class=codeno name=BIDType id='Ins4'
							ondblclick="return BShowIDTypeCode()"
							onkeyup="return BShowIDTypeCode()">
        	<input  name="BIDTypeName" readonly="readonly" type="text"  size="11"/>
			</TD>
			
		<td class=title>被保人证件号码</td>
		<TD class=input><Input class=input name=BIDNo id='Ins5'></TD>
	</tr>
	<TR class=common>
		<td class=title>被保人手机号</td>
		<TD class=input><Input class=input name=BMobile id='Ins6'></TD>
		<td class=title>被保人通讯地址</td>
		<TD class=input><Input class=input name=BAddress id='Ins7'></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人通讯邮编</td>
		<TD class=input><Input class=input name=BZipCode id='Ins8'></TD>
		<td class=title>被保人国籍</td>
		<TD class=input><Input class=input name=BCounty id='Ins9'></TD>
	</tr>
	
	<TR class=common>
		<td class=title>被保人证件有效期</td>
		<TD class=input><Input class=input name=BValidYear id='Ins10'></TD>
		<td class=title>被保人Email</td>
		<TD class=input><Input class=input name=BEmail id='Ins11'></TD>
	</tr>
	<TR class=common>
		<td class=title>被保人职业代码</td>
		<TD class=input><Input class=codeno name=BJobCode id='Ins12'
							ondblclick="return BShowJobCode()"
							onkeyup="return BShowJobCode()">
        	<input  name="BJobCodeName" readonly="readonly" type="text"  size="11"/></TD>
	</tr>

</table>
</div>

<hr>

<%--*************************受益人1信息************************--%>
<table class=common align=center> 
	<tr> 
		<td class=titleImg align=center>【受益人1信息】</td>
		<td colspan="2"><input type="checkbox" name=Indicator  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator >
		<font color="red">受益人是否为法定(选中为法定)</font></td>
	</tr>    

	<TR class=common>
		<td class=title>受益人类型</td>
		<TD class=input><select name="Type1" id="BnfReadOnly1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0"> 生存受益人</option>
			    <option value="1"> 身故受益人</option>
			</select></TD>
		<td class=title>姓名</td>
		<TD class=input>
			<Input class=input name=Name1 id="BnfReadOnly2">
			</TD>		
			
	</TR> 
	
	<TR class=common> 
		<td class=title>性别</td>
		<TD class=input>
			<select name="Sex1" id="BnfReadOnly3" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 不详</option>
			</select></TD>
			
		<td class=title>出生日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday1 id="BnfReadOnly4"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>身份证件类型</td>
		<TD class=input><Input class=codeno name=IDType1 id='BnfReadOnly5'
							ondblclick="return ShowIDTypeCode1()"
							onkeyup="return ShowIDTypeCode1()">
        	<input  name="IDTypeName1" readonly="readonly" type="text"  size="11"/>
			</TD>
		<td class=title>身份证件号码 </td>
		<TD class=input><Input class=input name=IDNo1 id="BnfReadOnly6">
		</TD>
	</tr>
		
	<TR class=common>
		<td class=title>与被保险人关系</td>
		<TD class=input>
			<input class="codeno" name="RelationToInsured1" id="BnfReadOnly7"
							ondblclick="return ShowRelationToInsured1()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelationToInsuredName1" readonly="readonly" type="text"  size="10" /></TD>
        	<td class=title>地址</td>
		<TD class=input><Input class=input name=Address1 id="BnfReadOnly8"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>受益顺序 </td>
		<TD class=input><Input class=input name=BnfGrade1 id="BnfReadOnly9"></TD>
		<td class=title>受益比例</td>
		<TD class=input><Input class=input name=BnfLot1 id="BnfReadOnly10">%</TD>		
	</tr>
	
</table>

<%--*************************受益人2信息************************--%>
<table class=common align=center>
	<tr> 
		<td class=titleImg align=center>【受益人2信息】</td>
	</tr> 
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fBnflist2"
						  	  style="cursor:hand;" OnClick="showPage(this,divBnflist2);" ></td>
	</tr>
</table> 

<div id="divBnflist2" style="display: 'none'"> 
<table class=common align=center> 
	   

	<TR class=common>
		<td class=title>受益人类型</td>
		<TD class=input><select name="Type2" id="BnfReadOnly11" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0"> 生存受益人</option>
			    <option value="1"> 身故受益人</option>
			</select></TD>
		<td class=title>姓名</td>
		<TD class=input>
			<Input class=input name=Name2 id="BnfReadOnly12">
			</TD>		
	</TR> 
	
	<TR class=common> 
		<td class=title>性别</td>
		<TD class=input>
			<select name="Sex2" id="BnfReadOnly13" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 不详</option>
			</select></TD>
			
		<td class=title>出生日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday2 id="BnfReadOnly14"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>身份证件类型</td>
		<TD class=input><Input class=codeno name=IDType2 id='BnfReadOnly15'
							ondblclick="return ShowIDTypeCode2()"
							onkeyup="return ShowIDTypeCode2()">
        	<input  name="IDTypeName2" readonly="readonly" type="text"  size="11"/>
			</TD>
		<td class=title>身份证件号码 </td>
		<TD class=input><Input class=input name=IDNo2 id="BnfReadOnly16">
		</TD>
	</tr>
		
	<TR class=common>
		<td class=title>与被保险人关系</td>
		<TD class=input>
			<input class="codeno" name="RelationToInsured2" id="BnfReadOnly17"
							ondblclick="return ShowRelationToInsured1()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelationToInsuredName2" readonly="readonly" type="text"  size="10" /></TD>
        	<td class=title>地址</td>
		<TD class=input><Input class=input name=Address2 id="BnfReadOnly18"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>受益顺序 </td>
		<TD class=input><Input class=input name=BnfGrade2 id="BnfReadOnly19"></TD>
		<td class=title>受益比例</td>
		<TD class=input><Input class=input name=BnfLot2 id="BnfReadOnly20">%</TD>		
	</tr>
	
</table>
</div>
<%--*************************受益人3信息************************--%>
<table class=common align=center>
	<tr> 
		<td class=titleImg align=center>【受益人3信息】</td>
	</tr>  	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif"
						  	  style="cursor:hand;" OnClick="showPage(this,divBnflist3);" ></td>
	</tr>
</table> 
<div id="divBnflist3" style="display: 'none'"> 

<table class=common align=center> 
	<TR class=common>
		<td class=title>受益人类型</td>
		<TD class=input><select name="Type3" id="BnfReadOnly21" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
				<option value="0"> 生存受益人</option>
			    <option value="1"> 身故受益人</option>
			</select></TD>
		<td class=title>姓名</td>
		<TD class=input>
			<Input class=input name=Name3 id="BnfReadOnly22">
			</TD>		
	</TR> 
	
	<TR class=common> 
		<td class=title>性别</td>
		<TD class=input>
			<select name="Sex3" id="BnfReadOnly23" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
				<option value="2"> 不详</option>
			</select></TD>
			
		<td class=title>出生日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=Birthday3 id="BnfReadOnly24"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>身份证件类型</td>
		<TD class=input><Input class=codeno name=IDType3 id='BnfReadOnly25'
							ondblclick="return ShowIDTypeCode3()"
							onkeyup="return ShowIDTypeCode3()">
        	<input  name="IDTypeName3" readonly="readonly" type="text"  size="11"/>
			</TD>
		<td class=title>身份证件号码 </td>
		<TD class=input><Input class=input name=IDNo3 id="BnfReadOnly26">
		</TD>
	</tr>
		
	<TR class=common>
		<td class=title>与被保险人关系</td>
		<TD class=input>
			<input class="codeno" name="RelationToInsured3" id="BnfReadOnly27"
							ondblclick="return ShowRelationToInsured1()"
							onkeyup="return ShowRelaToInsured()">
        	<input  name="RelationToInsuredName3" readonly="readonly" type="text"  size="10" /></TD>
        	<td class=title>地址</td>
		<TD class=input><Input class=input name=Address3 id="BnfReadOnly28"></TD>
	</tr>
	
	<TR class=common>
		<td class=title>受益顺序 </td>
		<TD class=input><Input class=input name=BnfGrade3 id="BnfReadOnly29"></TD>
		<td class=title>受益比例</td>
		<TD class=input><Input class=input name=BnfLot3 id="BnfReadOnly30">%</TD>		
	</tr>
	
</table>
</div>
<hr>

<%--*************主险信息*************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【主险信息】</td>
	</tr> 

	<tr class=common2>
	<td class=title>险种代码</td>
		<td bgColor="#F7F7F7">
		<select name="Code"  style="background-color: #D7E1F6">
				<option value=""></option>
            	<option value="231201">中韩BB两全保险（分红型）A款</option>
                <option value="231202">中韩BB两全保险（分红型）B款</option>
                <option value="231203">中韩CC两全保险（分红型）</option>
            </select>
			</td>
		<!-- <td class=title>险种密码</td>
		<TD class=input><Input class=input name=RiskPassword></TD>
		 -->
		 <td class=title>保险费 </td>
		<TD class=input><Input class=input name=Prem ></TD>
	</tr>
	
	<TR class=common>
		
		<td class=title>保额</td>
		<TD class=input><Input class=input name=Amnt ></TD>		
		<td class=title>起保日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=CValiDate ></TD>	
	</tr>
	
	<TR class=common>
		<!-- <td class=title>费率或交费标准 </td>
		<TD class=input><Input class=input name=Rate >%</TD>
		 -->
			
		<td class=title>投保份数 </td>
		<TD class=input><Input class=input name=Mult ></TD>
		<td class=title>交费方式</td>
		<TD class=input><select name="PayIntv" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 趸交 </option>
                <option value="2"> 月交 </option>
                <option value="3"> 季交 </option>
                <option value="4"> 半年交 </option>
                <option value="5"> 年交 </option>
                <option value="6"> 不定期 </option>
             </select></TD>	
	</tr>
	
	<TR class=common>
		
		<td class=title>交费年期年龄标志</td>
		<TD class=input><select name="PayEndYearFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
             </select></TD>		
             <td class=title>交费年期/年龄 </td>
		<TD class=input><Input class=input name=PayEndYear ></TD>
	</tr>
		
	<TR class=common>
		
		<td class=title>保障年期类型</td>
		<TD class=input><select name="InsuYearFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
                <option value="5"> 保终身 </option>
             </select></TD>		
             <td class=title>保障年期 </td>
		<TD class=input><Input class=input name=InsuYear ></TD>
	</tr>
	
	 
	<TR class=common>
		<td class=title>领取年期年龄标志</td>
		<TD class=input><select name="GetYearFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
             </select></TD>		
             <td class=title>领取年期/年龄 </td>
		<TD class=input><Input class=input name=GetYear ></TD>
	</tr>
	 
	<TR class=common>
		<!-- 
		<td class=title>自动垫交标记</td>
		<TD class=input><select name="AutoPayFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 不垫交 </option>
                <option value="1"> 垫交 </option>
             </select></TD>		
              -->
             <td class=title>红利领取方式 </td>
		<TD class=input>
		<select name="BonusGetMode" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 无 </option>
                <option value="1"> 累积生息 </option>
                <option value="2"> 领取现金 </option>
                <option value="3"> 抵交保费 </option>
                <option value="4"> 其他 </option>
                <option value="5"> 增额交清 </option>
             </select></TD>
              <td class=title>满期保险金领取方式</td>
		<TD class=input><select name="FullBonusGetMode" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 一次统一给付 </option>
                <option value="2"> 按年金方式领取 </option>
             </select></TD>
	</tr>
	<!-- 
	<TR class=common>
		
		<td class=title>红利分配标记 </td>
		<TD class=input><select name="BonusPayMode" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 红利分配 </option>
                <option value="1"> 无红利分配 </option>
             </select></TD>
         
		<td class=title>减额交清标记</td>
		<TD class=input><select name="SubFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 未减额交清 </option>
                <option value="1"> 减额交清 </option>
             </select></TD>		
	</tr>
	
	<TR class=common>
		
		<td class=title>是否同意现金价值自动垫交保险费</td>
		<TD class=input><select name="IsCashAutoPay" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>		
             <td class=title>犹豫期内的资金是否参与投资 </td>
		<TD class=input><select name="IsCashJoinInvest" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>
	</tr>
	-->
	<TR class=common>
		 <td class=title>保险期间 </td>
		<TD class=input><Input class=input name=Years ></TD>
		<td class=title>健康告知标志</td>
		<TD class=input><select name="HealthFlag" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>		
            
	</tr>
	
	<!-- 
	<TR class=common>
		
		
		<td class=title>初始费用率</td>
		<TD class=input><Input class="input"  name=FirstRate ></TD>	
		 	
	</tr>
	-->
	<!-- 
	<TR class=common>
		<td class=title>保证利率 </td>
		<TD class=input><Input class=input name=SureRate ></TD>
		<td class=title>保单止期</td>
		<TD class=input><Input class="input"  name=EndDate ></TD>		
	</tr>
	 
	<TR class=common>
		<td class=title>扣款间隔 </td>
		<TD class=input><Input class=input name=PayYear ></TD>
		<td class=title>扣款时间</td>
		<TD class=input><Input class="input"  name=PayDate ></TD>		
	</tr>
	-->
</table>
	

<%--*************附加险1*************--%>	
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


	<tr class=common2>
	<td class=title>险种代码</td>
		<td bgColor="#F7F7F7">
		<select name="Code1"  style="background-color: #D7E1F6">
					<option value=""></option>
            	<option value="001">中韩金满仓两全保险（分红型）</option>
                <option value="002">中韩金如意两全保险（分红型）</option>
                <option value="003">暂未增加</option>
                <option value="004">暂未增加 </option>
            </select></td>
            <!--  
		<td class=title>险种密码</td>
		<TD class=input><Input class=input name=Password1></TD>
		-->
		<td class=title>保险费 </td>
		<TD class=input><Input class=input name=Prem1 ></TD>
	</tr>
	
	<TR class=common>
		
		<td class=title>保额</td>
		<TD class=input><Input class=input name=Amnt1 ></TD>	
		<td class=title>起保日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=CValiDate1 ></TD>		
	</tr>
	
	<TR class=common>
		<!-- <td class=title>费率或交费标准 </td>
		<TD class=input><Input class=input name=Rate1 >%</TD>
		 -->
			
		<td class=title>投保份数 </td>
		<TD class=input><Input class=input name=Mult1 ></TD>
		<td class=title>交费方式</td>
		<TD class=input><select name="PayIntv1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 趸交 </option>
                <option value="2"> 月交 </option>
                <option value="3"> 季交 </option>
                <option value="4"> 半年交 </option>
                <option value="5"> 年交 </option>
                <option value="6"> 不定期 </option>
             </select></TD>	
	</tr>
	
	<TR class=common>
		
		<td class=title>交费年期年龄标志</td>
		<TD class=input><select name="PayEndYearFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
             </select></TD>		
             <td class=title>交费年期/年龄 </td>
		<TD class=input><Input class=input name=PayEndYear1 ></TD>
	</tr>
		
	<TR class=common>
		
		<td class=title>保障年期类型</td>
		<TD class=input><select name="InsuYearFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
                <option value="5"> 保终身 </option>
             </select></TD>		
             <td class=title>保障年期 </td>
		<TD class=input><Input class=input name=InsuYear1 ></TD>
	</tr>
	
	 
	<TR class=common>
		
		<td class=title>领取年期年龄标志</td>
		<TD class=input><select name="GetYearFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
             </select></TD>		
             <td class=title>领取年期/年龄 </td>
		<TD class=input><Input class=input name=GetYear1 ></TD>
	</tr>
	 
	 
	<TR class=common>
		<!--
		<td class=title>自动垫交标记</td>
		<TD class=input><select name="AutoPayFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 不垫交 </option>
                <option value="1"> 垫交 </option>
             </select></TD>	
             -->	
             <td class=title>红利领取方式 </td>
		<TD class=input>
		<select name="BonusGetMode1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 无 </option>
                <option value="1"> 累积生息 </option>
                <option value="2"> 领取现金 </option>
                <option value="3"> 抵交保费 </option>
                <option value="4"> 其他 </option>
                <option value="5"> 增额交清 </option>
             </select></TD>
             <td class=title>满期保险金领取方式</td>
		<TD class=input><select name="FullBonusGetMode1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 一次统一给付 </option>
                <option value="2"> 按年金方式领取 </option>
             </select></TD>
	</tr>
	<!-- 
	<TR class=common>
		
		<td class=title>红利分配标记 </td>
		<TD class=input><select name="BonusPayMode1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 红利分配 </option>
                <option value="1"> 无红利分配 </option>
             </select></TD>
         
		<td class=title>减额交清标记</td>
		<TD class=input><select name="SubFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 未减额交清 </option>
                <option value="1"> 减额交清 </option>
             </select></TD>		
	</tr>
	
	<TR class=common>
		
		<td class=title>是否同意现金价值自动垫交保险费</td>
		<TD class=input><select name="IsCashAutoPay1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>		
             <td class=title>犹豫期内的资金是否参与投资 </td>
		<TD class=input><select name="IsCashJoinInvest1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>
	</tr>
	-->
	<TR class=common>
		 <td class=title>保险期间 </td>
		<TD class=input><Input class=input name=Years1 ></TD>
		<td class=title>健康告知标志</td>
		<TD class=input><select name="HealthFlag1" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>		
             
	</tr>
	
	<TR class=common>
		
		<!-- 
		<td class=title>初始费用率</td>
		<TD class=input><Input class="input"  name=FirstRate1 ></TD>	
		 -->	
	</tr>
	<!-- 
	<TR class=common>
		<td class=title>保证利率 </td>
		<TD class=input><Input class=input name=SureRate1 ></TD>
		<td class=title>保单止期</td>
		<TD class=input><Input class="input"  name=EndDate1 ></TD>		
	</tr>
	 
	<TR class=common>
		<td class=title>扣款间隔 </td>
		<TD class=input><Input class=input name=PayYear1 ></TD>
		<td class=title>扣款时间</td>
		<TD class=input><Input class="input"  name=PayDate1 ></TD>		
	</tr>
	-->
</table>
</div>




<%--*************附加险2*************--%>	

<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【附加险2】</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="fRiskCode2"
						  	  style="cursor:hand;" OnClick="showPage(this,divRiskList2);" ></td>
	</tr>
</table> 

<div id="divRiskList2" style="display: 'none'"> 
<table class=common align=center>
	
	<tr class=common2>
	<td class=title>险种代码</td>
		<td bgColor="#F7F7F7">
		<select name="Code2"  style="background-color: #D7E1F6">
					<option value=""></option>
            	<option value="001">中韩金满仓两全保险（分红型）</option>
                <option value="002">中韩金如意两全保险（分红型）</option>
                <option value="003">暂未增加</option>
                <option value="004">暂未增加 </option>
            </select></td>
            <!-- 
		<td class=title>险种密码</td>
		<TD class=input><Input class=input name=Password2></TD>
		 -->
		 <td class=title>保险费 </td>
		<TD class=input><Input class=input name=Prem2 ></TD>
	</tr>
	
	<TR class=common>
		
		<td class=title>保额</td>
		<TD class=input><Input class=input name=Amnt2 ></TD>		
		<td class=title>起保日期</td>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name=CValiDate2 ></TD>		
	</tr>
	
	<TR class=common>
		<!-- <td class=title>费率或交费标准 </td>
		<TD class=input><Input class=input name=Rate2 >%</TD>
		 -->
		
		<td class=title>投保份数 </td>
		<TD class=input><Input class=input name=Mult2 ></TD>
		<td class=title>交费方式</td>
		<TD class=input><select name="PayIntv2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 趸交 </option>
                <option value="2"> 月交 </option>
                <option value="3"> 季交 </option>
                <option value="4"> 半年交 </option>
                <option value="5"> 年交 </option>
                <option value="6"> 不定期 </option>
             </select></TD>	
	</tr>
	
	<TR class=common>
		
		<td class=title>交费年期年龄标志</td>
		<TD class=input><select name="PayEndYearFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
             </select></TD>		
             <td class=title>交费年期/年龄 </td>
		<TD class=input><Input class=input name=PayEndYear2 ></TD>
	</tr>
		
	<TR class=common>
		
		<td class=title>保障年期类型</td>
		<TD class=input><select name="InsuYearFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
                <option value="5"> 保终身 </option>
             </select></TD>		
             <td class=title>保障年期 </td>
		<TD class=input><Input class=input name=InsuYear2 ></TD>
	</tr>
	
	
	<TR class=common>
		
		<td class=title>领取年期年龄标志</td>
		<TD class=input><select name="GetYearFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 年龄 </option>
                <option value="2"> 月 </option>
                <option value="3"> 日</option>
                <option value="4"> 年 </option>
             </select></TD>		
             <td class=title>领取年期/年龄 </td>
		<TD class=input><Input class=input name=GetYear2 ></TD>
	</tr>
	 
	<TR class=common>
		<!-- 
		<td class=title>自动垫交标记</td>
		<TD class=input><select name="AutoPayFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 不垫交 </option>
                <option value="1"> 垫交 </option>
             </select></TD>		
              -->
             <td class=title>红利领取方式 </td>
		<TD class=input>
		<select name="BonusGetMode2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 无 </option>
                <option value="1"> 累积生息 </option>
                <option value="2"> 领取现金 </option>
                <option value="3"> 抵交保费 </option>
                <option value="4"> 其他 </option>
                <option value="5"> 增额交清 </option>
             </select></TD>
             <td class=title>满期保险金领取方式</td>
		<TD class=input><select name="FullBonusGetMode2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="1"> 一次统一给付 </option>
                <option value="2"> 按年金方式领取 </option>
             </select></TD>
	</tr>
	<!-- 
	<TR class=common>
		
		<td class=title>红利分配标记 </td>
		<TD class=input><select name="BonusPayMode2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 红利分配 </option>
                <option value="1"> 无红利分配 </option>
             </select></TD>
         
		<td class=title>减额交清标记</td>
		<TD class=input><select name="SubFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 未减额交清 </option>
                <option value="1"> 减额交清 </option>
             </select></TD>		
	</tr>
	
	<TR class=common>
		
		<td class=title>是否同意现金价值自动垫交保险费</td>
		<TD class=input><select name="IsCashAutoPay2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>		
             <td class=title>犹豫期内的资金是否参与投资 </td>
		<TD class=input><select name="IsCashJoinInvest2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>
	</tr>
	-->
	
	<TR class=common>
		 <td class=title>保险期间 </td>
		<TD class=input><Input class=input name=Years2 ></TD>
		<td class=title>健康告知标志</td>
		<TD class=input><select name="HealthFlag2" class="common1" style="background-color: #D7E1F6;width: 90px;">
				<option value=""></option>
                <option value="0"> 否 </option>
                <option value="1"> 是 </option>
             </select></TD>		
             
	</tr>
	
	<!-- 
	<TR class=common>
		
		
		<td class=title>初始费用率</td>
		<TD class=input><Input class="input"  name=FirstRate2 ></TD>	
		 	
	</tr>
	-->
	<!-- 
	<TR class=common>
		<td class=title>保证利率 </td>
		<TD class=input><Input class=input name=SureRate2 ></TD>
		<td class=title>保单止期</td>
		<TD class=input><Input class="input"  name=EndDate2 ></TD>		
	</tr>
	 
	<TR class=common>
		<td class=title>扣款间隔 </td>
		<TD class=input><Input class=input name=PayYear2 ></TD>
		<td class=title>扣款时间</td>
		<TD class=input><Input class="input"  name=PayDate2 ></TD>		
	</tr>
	-->
</table>
</div>
<!-- 
<hr/>
<table class=common align=center>

	<tr> 
		<td class=titleImg align=center>【贷款信息】</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="AccountFlag"
						  	  style="cursor:hand;" OnClick="showPage(this,divLoanList);"></td>
	</tr>	 
</table> 
<div id="divLoanList" style="display: 'none'"> 
<table class=common align=center>
	<TR class=common>
		<td class=title>贷款合同号 </td>
		<TD class=input><Input class=input name=LoanNo ></TD>
		<td class=title>贷款机构</td>
		<TD class=input><Input class="input"  name=LoanBank ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款日期 </td>
		<TD class=input><Input class=input name=LoanDate ></TD>
		<td class=title>贷款到期日</td>
		<TD class=input><Input class="input"  name=LoanEndDate ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款种类 </td>
		<TD class=input><Input class=input name=LoanType ></TD>
		<td class=title>贷款账号</td>
		<TD class=input><Input class="input"  name=LoanAccNo ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款金额 </td>
		<TD class=input><Input class=input name=LoanPrem ></TD>
		<td class=title>保险起始日</td>
		<TD class=input><Input class="input"  name=InsuDate ></TD>		
	</tr>
	<TR class=common>
		<td class=title>保险期满日 </td>
		<TD class=input><Input class=input name=InsuEndDate ></TD>		
	</tr>
</table>
</div>	

	
<table class=common align=center>

	<tr> 
		<td class=titleImg align=center>【投资账户】</td>	
	</tr> 	
	
	<tr>	
		<td colspan="2" ><IMG src="../common/images/butCollapse.gif" id="AccountFlag"
						  	  style="cursor:hand;" OnClick="showPage(this,divAccList);"></td>
	</tr>	 
</table> 
<div id="divAccList" style="display: 'none'"> 
<table class=common align=center>
	<tr class=common>
		<td text-align: left colSpan=1>
			<span id="spanBnfList"></span></td>
	</tr>
	<tr>
		<td>投资帐户编号</td>
		<td>投资帐户金额</td>
		<td>投资帐户比率</td>
	</tr>

 
<%--*************投资账户一*************--%>	
  
	<tr class=common2>
		<td bgColor="#F7F7F7"><input class="codeno" name="AccNo1"
							ondblclick="return showAccCode1();">
			<input class="codename" name="AccCodeName1" readonly="true"/></td>
		<td bgColor="#F7F7F7"><input name="AccFee1" type="text"  /></td>
		<td bgColor="#F7F7F7"><input name="AccRate1" type="text" />%</td>
	</tr>	
	
	<tr class=common2>
		<td bgColor="#F7F7F7"><input class="codeno" name="AccNo2"
							ondblclick="return showAccCode2();">
			<input class="codename" name="AccCodeName2" readonly="true"/></td>
		<td bgColor="#F7F7F7"><input name="AccFee2" type="text"   /></td>
		<td bgColor="#F7F7F7"><input name="AccRate2" type="text"  />%</td>
	</tr>	
	
	<tr class=common2>
		<td bgColor="#F7F7F7"><input class="codeno" name="AccNo3"
							ondblclick="return showAccCode3();">
			<input class="codename" name="AccCodeName3" readonly="true"/></td>
		<td bgColor="#F7F7F7"><input name="AccFee3" type="text"  /></td>
		<td bgColor="#F7F7F7"><input name="AccRate3" type="text"  />%</td>
	</tr>	
</table>
</div>	

<hr>
 -->

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
