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
<SCRIPT src="NewAbcPEdor.js"></SCRIPT>
<script type="text/javascript">


</script>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

		<%@include file="NewAbcPEdorInit.jsp"%>  

		<title>银保通测试</title>
	</head>
<body onload="initElementtype();initForm();">
<form action="./NewAbcPEdorSave.jsp" method=post name=fm target="fraSubmit">


<%--*************************交易信息*************************--%>
<table class=common align=center>

	<tr>
		<td class=titleImg align=center>【交易信息】</td>
	</tr> 
	<TR class=common>
		<TD class=title>交易银行</TD> 
		 <TD class=input><Input class="codeno" name=TranCom ondblclick="return showTranCom()"
							onkeyup="return showTranCom()">
							<input  name="TranComName" readonly="readonly" type="text"  size="20"/></TD>
		<TD class=title>银行端交易日期</TD>
		<TD class=input><Input class="coolDatePicker" dateFormat = "short" name="TRANSDATE" /></TD>
	</TR>
	<TR class=common>
		<TD class=title>银行交易流水号</TD>
		<TD class=input><Input class=input name="SERIALNO"/></TD>
		<TD class=title>柜员</TD>
		<TD class=input><Input class=input name="TLID"/></TD>
		
	</TR>
	<TR class=common>
		<TD class=title>地区代码</TD>
		<TD class=input><Input class=input name="PROVCODE"/></TD>
		<TD class=title>网点代码</TD>
		<TD class=input><Input class=input name="BRANCHNO"/></TD>
		
	</TR>	
	<TR>
		
	</TR>
	<TR class=common>
		<TD class=title>保单号</TD>
		<TD class=input><Input class=input name="PolicyNo"/></TD>
		<TD class=title>保单印刷号</TD>
		<TD class=input><Input class=input name="PrintCode"/></TD>
		
	</TR>
	
	<TR class=common>
		<TD class=title>申请人姓名</TD>
		<TD class=input><Input class=input name="ClientName"/></TD>
		<TD class=title>险种代码</TD>
		<td class="input">
            <select name="Riskcode"  style="background-color: #D7E1F6" > 
                <option value="211902">中韩安赢借款人意外伤害保险A款</option>
            	  <option value="231204">中韩智赢财富两全保险（分红型）C款</option>
            	  <option value="231201">中韩智赢财富两全保险（分红型）A款</option>
                <option value="231202">中韩智赢财富两全保险（分红型）B款</option>
                <option value="231203">中韩卓越财富两全保险（分红型）</option>
                <option value="211901">中韩安赢借款人意外伤害保险</option>
                <option value="221201">中韩保驾护航两全保险A款</option>
            </select> 
		</td>
	</TR>
	<TR class=common>
		<TD class=title>投保人证件类型</TD>
		<TD class=input>
			<select name="IdKind" style="background-color: #D7E1F6" >
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
		<TD class=input><Input class=input name="IdCode"/></TD>
	</TR>
	
	
	<TR class=common>
		<TD class=title>领款人姓名</TD>
		<TD class=input><Input class=input name="PayeetName"/></TD>
		<TD class=title>领款人卡号</TD>
		<TD class=input><Input class=input name="PayAcc"/></TD>
		
	</TR>
	<TR class=common>
		<TD class=title>领款人证件类型</TD>
		<TD class=input>
			<select name="PayeeIdKind" style="background-color: #D7E1F6" >
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
		<TD class=title>领款人证件号码</TD>
		<TD class=input><Input class=input name="PayeeIdCode"/></TD>
	</TR>
	<TR class=common>
		<TD class=title>保费</TD>
		<TD class=input><Input class=input name="Amt"/></TD>
		<TD class=title>业务类型</TD>
		<TD class=input>
			<select name="BusiType" style="background-color: #D7E1F6" >
				<option value="01">犹撤</option>
				<option value="02">满期给付</option>
				<option value="03">退保</option>
			</select>
		</TD>
		
	</TR>
	
	
</table>
		
<%--*************页面设定*************--%>				
<table class=common align=center>				
					
	<TR> 
		<td><input class="cssButton" type=Button onClick="initBox()" name="Submit3" value=" 清空信息 " /></td>
		<td colspan="3"><input class="cssButton" type="button" name="Submit2" value=" 自 动 填 数 " onClick="initInputBox()" /></td>
	</TR>

</table>

<hr/>  
<table class=common align=center>
	<tr>
		<td class=titleImg align=center>【发送选项】</td>
	</tr> 
	
	<TR>
		<td class=title>交易码</td>
		
		<TD class=input><select name="TRANSCODE" style="background-color: #D7E1F6">
			    <option value="1012">保全查询</option>
			    <option value="1013">保全申请</option>
			    <option value="1014">保全申请状态查询</option>
			</select></TD>
		<td class=title>接收报文ip地址</td>
			<td class=input><Input class=input name="ip"></td> 
		<td class=title>端口</td>
		<td class=input><Input class=input name="port"></td>
		
	</tr> 
	 
	<TR class=common>
		<TD class=input width="26%"><input class=cssbutton type=Button 
		value="发送交易申请" onclick="submitForm();"></TD> 
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
