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
<SCRIPT src="NewAbcCont.js"></SCRIPT>
<script type="text/javascript">
</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="NewAbcContInit.jsp"%>  

		<title>银保通新契约测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./NewAbcSave.jsp" method=post name=fm target="fraSubmit">



<hr/>


<%--*************************交易信息*************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【交易信息】</td>
	</tr> 
	<TR class=common>
		<TD class=title>投保单号</TD>
		<TD class=input><Input class=input name="POLICYAPPLYSERIAL"/></TD>
		<TD class=title>保单印刷号</TD>
		<TD class=input><Input class=input name="VCHNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>交易银行</TD> 
		 <TD class=input><Input class="codeno" name=TranCom value="05" readonly="readonly"></TD>
		 <TD class=title>投保日期</TD>
		 <TD class=input><Input class="coolDatePicker" dateFormat = "short" name="APPLYDATE" /></TD>
	</TR>
	<TR class=common>
		<TD class=title>银行交易流水号</TD>
		<TD class=input><Input class=input name="SERIALNO"/><input type="hidden" name="InputTransrNo"></TD>
		<TD class=title>试算申请顺序号</TD>
		<TD class=input><Input class=input name="APPNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>银行端交易日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat = "short" name="TRANSDATE" /></TD>
	</TR>
	<TR class=common>
		<TD class=title>地区代码</TD>
		<TD class=input><Input class=input name="ProvCode"/></TD>
		<TD class=title>网点代码</TD>
		<TD class=input><Input class=input name="BRANCHNO"/></TD>
	</TR>
	<TR class=common>
	<TD class=title>柜员</TD>
		<TD class=input><Input class=input name="TLID"/></TD>
		</TR>
	<TR class=common>
		<TD class=title>营销员工</TD>
		<TD class=input><Input class=input name="SALER"/></TD>
		<TD class=title>人员资格证编号</TD>
		<TD class=input><Input class=input name="SALERCERTNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>网点兼业代理许可证编号</TD>
		<TD class=input><Input class=input name="BRANCHCERTNO"/></TD>
		<TD class=title>网点名称</TD>
		<TD class=input><Input class=input name="BRANCHNAME"/></TD>
	</TR>
		<TR class=common>
		<TD class=title>续期缴费帐户名称</TD>
		<TD class=input><Input class=input name="CONACCNAME"/></TD>
		<TD class=title>续期缴费账号</TD>
		<TD class=input><Input class=input name="CONACCNO"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>原交易流水号</TD>
		<TD class=input><Input class=input name="ReqsrNo" id="OldTranNo"/></TD>
		<TD class=title>首期缴费帐号</TD>
		<TD class=input><Input class=input name="PayAccount" id="PayAccountID"/><font color="red">(承保时录入)</font></TD>
	</TR>
	</TR>
	<%-- **************贷款信息****** --%>	
</table>



<%--*************************投保人信息************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【投保人信息】</td>
	</tr>
	
	<TR class=common>
		<TD class=title>投保人证件类型</TD>
		<TD class=input>
			<select name="APPLIDKIND" style="background-color: #D7E1F6" >
				<option value="110001">居民身份证                 </option>
				<option value="110002">重号居民身份证             </option>
				<option value="110003">临时居民身份证             </option>
				<option value="110004">重号临时居民身份证         </option>
				<option value="110005">户口簿                     </option>
				<option value="110006">重号户口簿                 </option>
				<option value="110011">离休干部荣誉证             </option>
				<option value="110012">重号离休干部荣誉证         </option>
				<option value="110013">军官退休证                 </option>
				<option value="110014">重号军官退休证             </option>
				<option value="110015">文职干部退休证             </option>
				<option value="110016">重号文职干部退休证         </option>
				<option value="110017">军事院校学员证             </option>
				<option value="110018">重号军事院校学员证         </option>
				<option value="110019">港澳居民往来内地通行证     </option>
				<option value="110020">重号港澳居民往来内地通行证 </option>
				<option value="110021">台湾居民来往大陆通行证     </option>
				<option value="110022">重号台湾居民来往大陆通行证 </option>
				<option value="110023">中华人民共和国护照         </option>
				<option value="110024">重号中华人民共和国护照     </option>
				<option value="110025">外国护照                   </option>
				<option value="110026">重号外国护照               </option>
				<option value="110027">军官证                     </option>
				<option value="110028">重号军官证                 </option>
				<option value="110029">文职干部证                 </option>
				<option value="110030">重号文职干部证             </option>
				<option value="110031">警官证                     </option>
				<option value="110032">重号警官证                 </option>
				<option value="110033">军人士兵证                 </option>
				<option value="110034">重号军人士兵证             </option>
				<option value="110035">武警士兵证                 </option>
				<option value="110036">重号武警士兵证             </option>
				<option value="119998">系统使用的个人证件识别标识 </option>
				<option value="119999">其它个人证件识别标识       </option>
			</select>
		</TD>
		<TD class=title>投保人证件号码</TD>
		<TD class=input><Input class=input name="APPLIDCODE"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>投保人证件生效日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="APPLBEGINDATE"></TD>
		<TD class=title>投保人证件终止日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="APPLINVALIDDATE"></TD>
	</TR>
	<TR class=common>
		<TD class=title>投保人姓名</TD>
		<TD class=input><Input class=input name="APPLNAME"></TD>
		<TD class=title>投保人性别</td>
		<TD class=input>
			<select name="APPLSEX" style="background-color: #D7E1F6">
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
			</select>
		</TD>
	</TR> 
	<TR class=common>
		<TD class=title>投保人出生日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="APPLBIRTHDAY"></TD>
		<TD class=title>投保人国籍</TD>
		<TD class=input>
			<select name="APPLCOUNTRY" style="background-color: #D7E1F6">
				<option value="156">中国</option>
				<option value="344">中国香港</option>
				<option value="158">中国台湾</option>
				<option value="446">中国澳门</option>
			</select>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>投保人通讯地址</TD>
		<TD class=input><Input class=input name="APPLADDRESS"/></TD>
		<TD class=title>省(直辖市)</TD>
		<TD class=input><Input class=input name="APPLPROV"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>投保人邮政编码</TD>
		<TD class=input><Input class=input name="APPLZIPCODE"/></TD>
		<TD class=title>投保人电子邮箱</TD>
		<TD class=input><Input class=input name="APPLEMAIL"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>投保人固定电话</TD>
		<TD class=input><Input class=input name="APPLPHONE"/></TD>
		<TD class=title>投保人移动电话</TD>
		<TD class=input><Input class=input name="APPLMOBILE"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>投保人年收入</TD>
		<TD class=input><Input class=input name="APPLANNUALINCOME"/>元</TD>
		<TD class=title>投保人居民类型</TD>
		<TD class=input><select name="PbDenType" style="background-color: #D7E1F6">
				<option value=""></option>
			    <option value="0">城镇</option>
				<option value="1">农村</option>
			</select></TD>
	</TR>
	<TR class=common>
		<TD class=title>投保人职业代码</TD>
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
	</TR>
	<TR class=common>
		<TD class=title>投保人与被保险人关系</TD>
		<TD class=input>
<!-- 	<select name="APPLRELATOINSURED" style="background-color: #D7E1F6" onchange="WAppFlag(this.options[this.options.selectedIndex].value)"> -->		
	 	<select name="APPLRELATOINSURED" style="background-color: #D7E1F6" onchange="setFlag();"> 
				<option value="00"> 本人</option>
				<option value="02"> 丈夫</option>
				<option value="02"> 妻子</option>
				<option value="01"> 父亲</option>
				<option value="01"> 母亲</option>	
				<option value="03"> 儿子</option>	 
				<option value="03"> 女儿</option>
				<option value="08"> 祖父</option>
				<option value="09"> 祖母</option>
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
		</TD>
	</TR>
</table>



<%--*************************被保人信息************************--%>
<table class=common align=center>

	<TR>
		<td class=titleImg align=center>【被保人信息】</td>
		<td colspan="2"><input type="checkbox" name=InsFlag value="0" onClick="setInsFlag(this);">
		<font color="red">被保人是否为本人</font></td>
	</TR>
	
	<TR class=common>
		<TD class=title>被保人证件类型</TD>
		<TD class=input>
			<select name="INSUIDKIND" style="background-color: #D7E1F6"  id="Ins1">
				<option value="110001">居民身份证                 </option>
				<option value="110002">重号居民身份证             </option>
				<option value="110003">临时居民身份证             </option>
				<option value="110004">重号临时居民身份证         </option>
				<option value="110005">户口簿                     </option>
				<option value="110006">重号户口簿                 </option>
				<option value="110011">离休干部荣誉证             </option>
				<option value="110012">重号离休干部荣誉证         </option>
				<option value="110013">军官退休证                 </option>
				<option value="110014">重号军官退休证             </option>
				<option value="110015">文职干部退休证             </option>
				<option value="110016">重号文职干部退休证         </option>
				<option value="110017">军事院校学员证             </option>
				<option value="110018">重号军事院校学员证         </option>
				<option value="110019">港澳居民往来内地通行证     </option>
				<option value="110020">重号港澳居民往来内地通行证 </option>
				<option value="110021">台湾居民来往大陆通行证     </option>
				<option value="110022">重号台湾居民来往大陆通行证 </option>
				<option value="110023">中华人民共和国护照         </option>
				<option value="110024">重号中华人民共和国护照     </option>
				<option value="110025">外国护照                   </option>
				<option value="110026">重号外国护照               </option>
				<option value="110027">军官证                     </option>
				<option value="110028">重号军官证                 </option>
				<option value="110029">文职干部证                 </option>
				<option value="110030">重号文职干部证             </option>
				<option value="110031">警官证                     </option>
				<option value="110032">重号警官证                 </option>
				<option value="110033">军人士兵证                 </option>
				<option value="110034">重号军人士兵证             </option>
				<option value="110035">武警士兵证                 </option>
				<option value="110036">重号武警士兵证             </option>
				<option value="119998">系统使用的个人证件识别标识 </option>
				<option value="119999">其它个人证件识别标识       </option>
			</select>
		</TD>
		<TD class=title>被保人证件号码</TD>
		<TD class=input><Input class=input name="INSUIDCODE" id="Ins2"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>被保人证件生效日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="INSUBEGINDATE" id="Ins3"></TD>
		<TD class=title>被保人证件终止日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="INSUINVALIDDATE" id="Ins4"></TD>
	</TR>
	<TR class=common>
		<TD class=title>被保人姓名</TD>
		<TD class=input><Input class=input name="INSUNAME" id="Ins5"></TD>
		<TD class=title>被保人性别</td>
		<TD class=input>
			<select name="INSUSEX" style="background-color: #D7E1F6" id="Ins6">
			    <option value="0"> 男性</option>
				<option value="1"> 女性</option>
			</select>
		</TD>
	</TR> 
	<TR class=common>
		<TD class=title>被保人出生日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat="short" name="INSUBIRTHDAY" id="Ins7"/></TD>
		<TD class=title>被保人国籍</TD>
		<TD class=input>
			<select name="INSUCOUNTRY" style="background-color: #D7E1F6" id="Ins8">
				<option value="156">中国</option>
				<option value="344">中国香港</option>
				<option value="158">中国台湾</option>
				<option value="446">中国澳门</option>
			</select>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>被保人通讯地址</TD>
		<TD class=input><Input class=input name="INSUADDRESS" id="Ins9"/></TD>
		<TD class=title>省(直辖市)</TD>
		<TD class=input><Input class=input name="INSUPROV" id="Ins10"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>被保人邮政编码</TD>
		<TD class=input><Input class=input name="INSUZIPCODE" id="Ins11"/></TD>
		<TD class=title>被保人电子邮箱</TD>
		<TD class=input><Input class=input name="INSUEMAIL" id="Ins12"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>被保人固定电话</TD>
		<TD class=input><Input class=input name="INSUPHONE" id="Ins13"/></TD>
		<TD class=title>被保人移动电话</TD>
		<TD class=input><Input class=input name="INSUMOBILE" id="Ins14"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>被保人职业代码</TD>
		<TD class=input>
		<select name="INSUJOBCODE" id="Ins16" style="background-color: #D7E1F6">  
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
	</TR>
	<TR class=common>
		<TD class=title>被保人年收入</TD>
		<TD class=input><Input class=input name="INSUANNUALINCOME" id="Ins17"/>分</TD>
		<TD class=title>被保人告知</TD>
		<TD class=input>
			<select name="INSUNOTICE" style="background-color: #D7E1F6" id="Ins18">
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>被保人是否危险职业</TD>
		<TD class=input>
			<select name="INSUISRISKJOB" style="background-color: #D7E1F6" id="Ins19">
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</TD>
		<TD class=title>被保人健康告知</TD>
		<TD class=input>
			<select name="INSUHEALTHNOTICE" style="background-color: #D7E1F6" id="Ins20">
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</TD>
	</TR>
</table>

<%--*************************受益人信息************************--%>
<table class=common align=center> 
	<TR> 
		<td class=titleImg align=right>【受益人信息】</td>
		<td colspan="2"><input type="checkbox" name=BnfFlag  onClick="setBnfFlag(this);">
		<input type="hidden" name=BeneficiaryIndicator>
		<font color="red">受益人是否为法定(选中为法定)</font></td>
	</TR>    
	<TR >  
		<td>与被保人关系</td>
		<td>受益人类别</td>
		<td>姓名</td>
		<td>性别</td>
		<td>证件类型</td>
		<td>证件号码</td>
		<td>受益人出生日期</td>
		<td>受益比例</td>
		<td>受益顺序</td>
		<td>证件有效期</td>
	</TR>
						
<%--*************受益人1*************--%>
<tr class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured1" id="BnfReadOnly11" style="background-color: rgb(215, 225, 246);" > 
			    <option value="00"> 本身</option>
				<option value="02"> 丈夫</option>
				<option value="02"> 妻子</option>
				<option value="01"> 父亲</option>
				<option value="01"> 母亲</option>
				<option value="03"> 儿子</option>
				<option value="03"> 女儿</option>
				<option value="08"> 祖父</option>
				<option value="09"> 祖母</option>
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
			<select name="BNFTYPE1" id="BnfReadOnly12" style="background-color: #D7E1F6">
				  <option value=""></option>
			    <option value="0">生存受益人</option>
				<option value="1"> 身故受益人</option>
			</select>
		<td><input name="BNFNAME1" type="text" id="BnfReadOnly13" class=common  size="10"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEX1" id="BnfReadOnly14" style="background-color: #D7E1F6">
				<option value="0">男</option>
				<option value="1">女</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BNFIDKIND1" id="BnfReadOnly15" style="background-color: #D7E1F6">
				<option value=""></option>
			  <option value="110001">居民身份证                </option>
				<option value="110005">重号居民身份证            </option>
				<option value="110013">临时居民身份证            </option>
				<option value="110017">重号临时居民身份证        </option>
				<option value="110019">户口簿                    </option>
				<option value="110023">重号户口簿                </option>
				<option value="110025">离休干部荣誉证            </option>
				<option value="110029">重号离休干部荣誉证        </option>
				<option value="110035">军官退休证                </option>
				<option value="110001">重号军官退休证            </option>
				<option value="110005">文职干部退休证            </option>
				<option value="110013">重号文职干部退休证        </option>
				<option value="110017">军事院校学员证            </option>
				<option value="110019">重号军事院校学员证        </option>
				<option value="110023">港澳居民往来内地通行证    </option>
				<option value="110025">重号港澳居民往来内地通行证</option>
				<option value="110029">台湾居民来往大陆通行证    </option>
				<option value="110035">重号台湾居民来往大陆通行证</option>
				<option value="110001">中华人民共和国护照        </option>
				<option value="110005">重号中华人民共和国护照    </option>
				<option value="110013">外国护照                  </option>
				<option value="110017">重号外国护照              </option>
				<option value="110019">军官证                    </option>
				<option value="110023">重号军官证                </option>
				<option value="110025">文职干部证                </option>
				<option value="110029">重号文职干部证            </option>
				<option value="110035">警官证                    </option>
				<option value="110001">重号警官证                </option>
				<option value="110005">军人士兵证                </option>
				<option value="110013">重号军人士兵证            </option>
				<option value="110017">武警士兵证                </option>
				<option value="110019">重号武警士兵证            </option>
				<option value="110023">系统使用的个人证件识别标识</option>
				<option value="110025">其它个人证件识别标识      </option>
			</select></td>
		<td><input name="BNFIDCODE1" type="text" id="BnfReadOnly16" class=common/></td>
		<td><Input class="coolDatePicker" dateFormat="short" name="BNFBIRTHDAY1" id="BnfReadOnly18"></TD>
		<td><input name="BNFPROP1" type="text"  id="BnfReadOnly17" size="8" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEQUENCE1"  id="BnfReadOnly18" style="background-color: #D7E1F6">
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
			<td><input name="BnfValidYear1" type="text" id="BnfReadOnly19"/></td>
	</TR>
				
<%--*************受益人2*************--%>
	<TR class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured2" id="BnfReadOnly20" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="00"> 本身</option>
				<option value="02"> 丈夫</option>
				<option value="02"> 妻子</option>
				<option value="01"> 父亲</option>
				<option value="01"> 母亲</option>
				<option value="03"> 儿子</option>
				<option value="03"> 女儿</option>
				<option value="08"> 祖父</option>
				<option value="09"> 祖母</option>
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
		<td bgColor="#F7F7F7" >
			<select name="BNFTYPE2" id="BnfReadOnly21" style="background-color: rgb(215, 225, 246);">
				<option value=""></option>
				<option value="1">身故受益人</option> 
				<option value="0">生存受益人</option>
			</select></td>
		<td><input name="BNFNAME2" type="text" id="BnfReadOnly22" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEX2" id="BnfReadOnly23" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0">男</option>
				<option value="1">女	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BNFIDKIND2" id="BnfReadOnly24" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="110001">居民身份证                </option>
				<option value="110005">重号居民身份证            </option>
				<option value="110013">临时居民身份证            </option>
				<option value="110017">重号临时居民身份证        </option>
				<option value="110019">户口簿                    </option>
				<option value="110023">重号户口簿                </option>
				<option value="110025">离休干部荣誉证            </option>
				<option value="110029">重号离休干部荣誉证        </option>
				<option value="110035">军官退休证                </option>
				<option value="110001">重号军官退休证            </option>
				<option value="110005">文职干部退休证            </option>
				<option value="110013">重号文职干部退休证        </option>
				<option value="110017">军事院校学员证            </option>
				<option value="110019">重号军事院校学员证        </option>
				<option value="110023">港澳居民往来内地通行证    </option>
				<option value="110025">重号港澳居民往来内地通行证</option>
				<option value="110029">台湾居民来往大陆通行证    </option>
				<option value="110035">重号台湾居民来往大陆通行证</option>
				<option value="110001">中华人民共和国护照        </option>
				<option value="110005">重号中华人民共和国护照    </option>
				<option value="110013">外国护照                  </option>
				<option value="110017">重号外国护照              </option>
				<option value="110019">军官证                    </option>
				<option value="110023">重号军官证                </option>
				<option value="110025">文职干部证                </option>
				<option value="110029">重号文职干部证            </option>
				<option value="110035">警官证                    </option>
				<option value="110001">重号警官证                </option>
				<option value="110005">军人士兵证                </option>
				<option value="110013">重号军人士兵证            </option>
				<option value="110017">武警士兵证                </option>
				<option value="110019">重号武警士兵证            </option>
				<option value="110023">系统使用的个人证件识别标识</option>
				<option value="110025">其它个人证件识别标识      </option>
			</select></td>
		<td><input name="BNFIDCODE2" type="text" id="BnfReadOnly25" class=common/></td>
		
		<td><Input class="coolDatePicker" dateFormat="short" name="BNFBIRTHDAY2" id="BnfReadOnly28" size="10"></TD>
		<td><input name="BNFPROP2" type="text"  id="BnfReadOnly26" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEQUENCE2"  id="BnfReadOnly27" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
		<td><input name="BnfValidYear2" type="text"/></td>
	</TR>
	
<%--*************受益人3*************--%>
	<TR class=common2>
		<td bgColor="#F7F7F7" >
			<select name="BnfRelationToInsured3" id="BnfReadOnly27" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
			    <option value="00"> 本身</option>
				<option value="02"> 丈夫</option>
				<option value="02"> 妻子</option>
				<option value="01"> 父亲</option>
				<option value="01"> 母亲</option>
				<option value="03"> 儿子</option>
				<option value="03"> 女儿</option>
				<option value="08"> 祖父</option>
				<option value="09"> 祖母</option>
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
		<td bgColor="#F7F7F7" >
			<select name="BNFTYPE3" id="BnfReadOnly28" style="background-color: rgb(215, 225, 246);"> 
				<option value=""></option>
				<option value="1">身故受益人</option> 
				<option value="0">生存受益人</option>	   
			</select></td>
		<td><input name="BNFNAME3" type="text" id="BnfReadOnly29" class=common size="15"/></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEX3" id="BnfReadOnly30" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="0">男</option>
				<option value="1">女	</option>
			</select></td>
		<td bgColor="#F7F7F7">
			<select name="BNFIDKIND3" id="BnfReadOnly31" style="background-color: #D7E1F6">
				<option value=""></option>
			<option value="110001">居民身份证                </option>
				<option value="110005">重号居民身份证            </option>
				<option value="110013">临时居民身份证            </option>
				<option value="110017">重号临时居民身份证        </option>
				<option value="110019">户口簿                    </option>
				<option value="110023">重号户口簿                </option>
				<option value="110025">离休干部荣誉证            </option>
				<option value="110029">重号离休干部荣誉证        </option>
				<option value="110035">军官退休证                </option>
				<option value="110001">重号军官退休证            </option>
				<option value="110005">文职干部退休证            </option>
				<option value="110013">重号文职干部退休证        </option>
				<option value="110017">军事院校学员证            </option>
				<option value="110019">重号军事院校学员证        </option>
				<option value="110023">港澳居民往来内地通行证    </option>
				<option value="110025">重号港澳居民往来内地通行证</option>
				<option value="110029">台湾居民来往大陆通行证    </option>
				<option value="110035">重号台湾居民来往大陆通行证</option>
				<option value="110001">中华人民共和国护照        </option>
				<option value="110005">重号中华人民共和国护照    </option>
				<option value="110013">外国护照                  </option>
				<option value="110017">重号外国护照              </option>
				<option value="110019">军官证                    </option>
				<option value="110023">重号军官证                </option>
				<option value="110025">文职干部证                </option>
				<option value="110029">重号文职干部证            </option>
				<option value="110035">警官证                    </option>
				<option value="110001">重号警官证                </option>
				<option value="110005">军人士兵证                </option>
				<option value="110013">重号军人士兵证            </option>
				<option value="110017">武警士兵证                </option>
				<option value="110019">重号武警士兵证            </option>
				<option value="110023">系统使用的个人证件识别标识</option>
				<option value="110025">其它个人证件识别标识      </option>
			</select></td>
		<td><input name="BNFIDCODE3" type="text" id="BnfReadOnly32" class=common/></td>
		
		<td><Input class="coolDatePicker" dateFormat="short" name="BNFBIRTHDAY3" id="BnfReadOnly33" size="10"></TD>
		<td><input name="BNFPROP3" type="text"  id="BnfReadOnly34" size="10" /></td>
		<td bgColor="#F7F7F7"> 
			<select name="BNFSEQUENCE3"  id="BnfReadOnly35" style="background-color: #D7E1F6">
				<option value=""></option>
				<option value="1" >第一受益顺序</option>
				<option value="2" >第二受益顺序</option>
				<option value="3" >第三受益顺序</option>
			</select>					
		</td>
		<td><input name="BnfValidYear3" type="text"/></td>
	</TR>
</table>


<%--*************主险信息*************--%>
<table class=common align=center>

	<TR>
		<td class=titleImg align=center>【主险信息】</td>
	</TR> 
 
	<TR >
		<td>主险代码</td>
		<td><div>份数</div></td>
		<td><div>保费(元)</div></td>
		<td>保额(元)</td>
		<td>缴费方式</td>
		<td>红利领取方式</td>
		<td>保险年期年龄标志</td>
		<td>保险年期年龄</td>
		<td>缴费年期年龄标志</td>
		<td>缴费年期年龄</td> 
	</TR>
 
	<TR class=common2>
		<td bgColor="#F7F7F7">
            <select name="RISKSRISKCODE"  style="background-color: #D7E1F6" onchange="RiskFlag(this.options[this.options.selectedIndex].value)"> 
            	  <option value="231204">中韩智赢财富两全保险（分红型）C款</option>
                <option value="231202">中韩智赢财富两全保险（分红型）B款</option>
                <option value="231203">中韩卓越财富两全保险（分红型）</option>
                <option value="221201">中韩保驾护航两全保险A款</option>
                <option value="231302">中韩永利年年年金保险（分红型）</option>
                <option value="211902">中韩安赢借款人意外伤害保险A款</option>
                 <option value="241201">中韩创赢财富两全保险（万能型）A款</option>
                <option value="221206">中韩优越财富两全保险</option>
            </select> 
        	<input  name="RiskCodeName" readonly="readonly"  type="hidden" size="33"/>
         </td>
		<td><input name="RISKSSHARE"    size="3"/></td>
		<td bgColor="#F7F7F7"><input name="RISKSPREM"   size="10"/></td>
		<td bgColor="#F7F7F7"><input name="RISKSAMNT" type="text"  size="10"/></td>
		<TD><select name="PayType" class="common1">
			         <option value=""></option>
			         <option value="1"> 趸交 </option>
               <option value="2"> 月交 </option>
               <option value="3"> 季交 </option>
               <option value="4"> 半年交 </option>
               <option value="5"> 年交 </option>
               <option value="6"> 年龄 </option>
               <option value="7"> 终身交费 </option>
               <option value="0"> 不定期 </option>
	    </select></TD>
		<TD><select name="BonusGetMode" class="common1">
			   <option value=""></option>
			         <option value="0"> 直接给付 </option>
			         <option value="1"> 抵交保费 </option>
               <option value="2"> 累积生息 </option>
               <option value="3"> 增额交清 </option>
	    </select></TD>
		<td> 
			<select name="RISKSINSUDUETYPE" style="background-color: #D7E1F6">
                <option value="2"> 月保</option> 
                <option value="4"> 年保</option>
                <option value="5"> 保至某确定年龄</option>
                <option value="6"> 保终身</option>
             </select></td>
		<td><input name="RISKSINSUDUEDATE" type="text"  size="3" maxlength="5"/></td>
		
		 <td>
			<select name="RISKSPAYDUETYPE" style="background-color: #D7E1F6">
				<option value="0"> 趸交 </option>
				<option value="2"> 月缴 </option>
                <option value="4"> 年缴 </option>
                 <option value="1"> 缴至某确定年龄 </option>
                <option value="5"> 终生 </option>
             </select></td>
		<td><input name="RISKSPAYDUEDATE" type="text"  size="5" maxlength="5"  /></td> 
	</TR>		
</table>

<%--*************附加险信息*************--%>
<table class=common align=center>

	<TR>
		<td class=titleImg align=center>【附加险信息】</td>
	</TR> 
 
	<TR >
		<td>附加险代码</td>
		<td><div>份数</div></td>
		<td><div>保费(元)</div></td>
		<td>保额(元)</td>
		<td>缴费方式</td>
		<td>红利领取方式</td>
		<td>保险年期年龄标志</td>
		<td>保险年期年龄</td>
		<td>缴费年期年龄标志</td>
		<td>缴费年期年龄</td> 
	</TR>
 
	<TR class=common2>
		<td bgColor="#F7F7F7">
            <select name="ADDTRISKCODE1"  style="background-color: #D7E1F6" >          	
                		<option value=""></option>
                <option value="145201">中韩附加定盈宝两全保险（万能型）</option>
            </select> 
        	<input  name="RiskCodeName" readonly="readonly"  type="hidden" size="33"/>
         </td>
		<td><input name="ADDTSHARE1"    size="3"/></td>
		<td bgColor="#F7F7F7"><input name="ADDTPREM1"   size="10"/></td>
		<td bgColor="#F7F7F7"><input name="ADDTAMNT1" type="text"  size="10"/></td>
		<TD><select name="ADDTPAYTYPE1" class="common1">
			         <option value=""></option>
			         <option value="1"> 趸交 </option>
               <option value="2"> 月交 </option>
               <option value="3"> 季交 </option>
               <option value="4"> 半年交 </option>
               <option value="5"> 年交 </option>
               <option value="6"> 年龄 </option>
               <option value="7"> 终身交费 </option>
               <option value="0"> 不定期 </option>
	    </select></TD>
		<TD>
		<select name="BonusGetMode" class="common1">
			   <option value=""></option>
			         <option value="0"> 直接给付 </option>
			         <option value="1"> 抵交保费 </option>
               <option value="2"> 累积生息 </option>
               <option value="3"> 增额交清 </option>
	    </select>
	    </TD>
		<td> 
			<select name="ADDTINSUDUETYPE1" style="background-color: #D7E1F6">
                <option value="2"> 月保</option> 
                <option value="4"> 年保</option>
                <option value="5"> 保至某确定年龄</option>
                <option value="6"> 保终身</option>
             </select></td>
		<td><input name="ADDTINSUDUEDATE1" type="text"  size="3" maxlength="5"/></td>
		
		 <td>
			<select name="ADDTPAYDUETYPE1" style="background-color: #D7E1F6">
				<option value="0"> 趸交 </option>
				<option value="2"> 月缴 </option>
                <option value="4"> 年缴 </option>
                 <option value="1"> 缴至某确定年龄 </option>
                <option value="5"> 终生 </option>
             </select></td>
		<td><input name="ADDTPAYDUEDATE1" type="text"  size="5" maxlength="5"  /></td> 
	</TR>	
</table>



<%--*************贷款信息*************--%>
<div id="C20001" style="display:'none'">
<table class=common align=center>
<TR>
		<td class=titleImg align=center>【贷款信息】</td>
	</TR> 
<TR class=common>
		<td class=title>贷款机构代码</td>
		<TD class=input><Input class=input name="LoanCom" maxlength="25"></TD>
		<td class=title>贷款合同号</td>
		<TD class=input><Input class=input name="LoanContractNo" maxlength="25"></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款起始日期 </td>
		<TD class=input><Input class=coolDatePicker name="LoanStartDate"></TD>
		<td class=title>贷款终止日期</td>
		<TD class=input><Input class=coolDatePicker name="LoanEndDate" ></TD>		
	</tr>
	
	<TR class=common>
		<td class=title>贷款金额</td>
		<TD class=input><Input class=input name="LoanContractAmt" maxlength="25">分</TD>
		<td class=title>贷款种类</td>
		<TD class=input>
			<select name="LoanType" style="background-color: #D7E1F6">
				<option value="00"> 一般商业贷款 </option>
				<option value="01"> 组合商业贷款 </option>
                <option value="10"> 公积金组合贷款 </option>
                 <option value="11"> 纯公积金贷款 </option>
                <option value="20"> 贴息助学贷款 </option>
             </select></TD>
	</TR>
	<TR class=common>
		<TD class=title>保险起始日期</TD>
			 <TD class=input><Input class="coolDatePicker" dateFormat = "short" name="RISKSBEGINDATE" /></TD>			
		<TD class=title>保险结束日期</TD>
			 <TD class=input><Input class="coolDatePicker" dateFormat = "short" name="RISKSENDDATE" /></TD>	
	</TR>
</table>
</div>
<%--*************页面设定*************--%>				
<table class=common align=center>				
					
	<TR> 
		<td><input class="cssButton" type=Button onClick="initBox()" name="Submit3" value=" 清空信息 " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" 自 动 填 数 " onClick="initInputBox()" /></td>
	</TR>

</table>

 <table class=common align=center>
	<tr>
		<td class=titleImg align=center><br>【发送选项】</td>
	</tr> 
	
	<TR>
		<td class=title>交易码</td>
		
		<TD class=input><select name="TRANSCODE" style="background-color: #D7E1F6"  onchange="TranFlag(this.options[this.options.selectedIndex].value)">
			    <option value="1002">保单试算</option>
			    <option value="1004">保单承保</option>
			</select></TD>
		<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name="ip"></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name="port"></td>
		
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button 
		value="发送投保申请" onclick="submitForm();"></TD> 
	</TR>
</table>
<hr/>  
<table class=common align=center>
	<TR>报文信息</TR>  
	<TR>
		<td>
			<textarea rows="30" cols="100" name="xmlContent" id="xmlContent">
			
			</textarea>
		</td>
	</TR>  
</table>
<input type=hidden name=hiddenBnf id='hiddenBnf' value='0'></form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>

</body>
</html>
