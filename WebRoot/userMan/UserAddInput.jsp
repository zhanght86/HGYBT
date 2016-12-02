<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%
//程序名称：
//程序功能：
//创建日期：
//创建人 ：CrtHtml程序创建   
//更新记录： 更新人  更新日期   更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--%@include file="../common/jsp/AccessCheck.jsp"%-->
<%
GlobalInput tG11 =new GlobalInput();
tG11=(GlobalInput)session.getValue("GI");
String Operator =tG11.Operator;
String operatorComCode =tG11.ComCode;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="UserAdd.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<script src="../menumang/treeMenu.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./UserAddInit.jsp"%>
<title>用户管理 </title>
</head>
<body onload="initForm();">
	<form action="./userAddMan.jsp" method=post name=fm target="fraSubmit">
		<table class="common">
			<TR class=common>
				<TD class=title>用户编码</TD>
				<TD class=input id="tdUserCode">
					<Input class=common name=UserCode maxlength="20">
				</TD>
				<TD class=input style="display:none" id="tdUserCodeReadOnly">
					<Input class=common name=UserCodeReadOnly readonly maxlength="20">
				</TD>
				<TD class=title>用户姓名</TD>
				<TD class=input id="tdUserName">
					<Input class=common name=UserName maxlength="20">
				</TD>
				<TD class=input style="display:none" id="tdUserNameReadOnly">
					<Input class=common name=UserNameReadOnly readonly maxlength="20">
				</TD>
			</TR>
			<TR class=common>
				<TD class=title>机构编码</TD>
					<TD class=input>
						<input class=codeno name=ComCode ondblclick=" showCodeList('ComCode',[this,ComCodeName],[0,1]);" onkeyup="return showCodeListKey('ComCode',[this,ComCodeName],[0,1]);"><input class=codename name=ComCodeName readonly=true>
					</TD>
           <TD  class= title>
            代理机构
          </TD>
          <TD  class= input>
            <!--Input class="code" name=AgentCom elementtype=nacessary  ondblclick="return showCodeList('AgentCom',[this],null,null, null, 'ManageCom');" onkeyup="return showCodeListKey('AgentCom',[this],null,null, null, 'ManageCom');"-->
          	<Input class="common" name=AgentCom maxlength="20">
          </TD>
			</TR>
		</table>
		<div id="divHideInput", style="display:none">
			<table class="common">
				<TR class=common id=passwordTR style="display:''">
					<TD class=title>密码</TD>
					<td class=input>
						<input class=common type="Password" name=Password maxlength="8">
					</TD>
					<TD class=title>密码确认</TD>
					<TD class=input>
						<Input class=common type="Password" name=PasswordConfirm maxlength="8">
					</TD>
				</TR>
				<TR class=common>
					<TD class=title>用户状态</TD>
					<TD class=input>
						<Input class=codeno name=UserState ondblclick=" showCodeList('UserState',[this,UserStateName],[0,1]);" onkeyup="return showCodeListKey('UserState',[this,UserStateName],[0,1]);"><input class=codename name=UserStateName readonly=true>
					</TD>
				</TR>
				<tr>
					<td class=title>用户描述</td>
					<td class=input>
						<Input class=common name=UserDescription>
					</td>
					
					<TD class=title>保全权限</TD>
					<TD class=input>
						<Input class=codeno name=EdorPopedom ondblclick=" showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1]);" onkeyup="return showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1]);" disabled><input class=codename name=EdorPopedomName readonly=true disabled>
					</TD>
					
				</tr>
				<TR class=common>
					
					
					<TD class=title>核保权限</TD>
					<TD class=input>
						<Input class=codeno name=UWPopedom ondblclick="showCodeList('UWPopedom',[this,UWPopedomName],[0,1]);" onkeyup="return showCodeListKey('UWPopedom',[this,UWPopedomName],[0,1]);" disabled><input class=codename name=UWPopedomName readonly=true disabled>
					</TD>
					
					<TD class=title>核赔权限</TD>
					<TD class=input>
						<Input class=codeno name=ClaimPopedom ondblclick=" showCodeList('ClaimPopedom',[this,ClaimPopedomName],[0,1]);" onkeyup="return showCodeListKey('ClaimPopedom',[this,ClaimPopedomName],[0,1]);" disabled><input class=codename name=ClaimPopedomName readonly=true disabled>
					</TD>
				</TR>
				<TR class=common>
					<TD class=title>其它权限</TD>
					<TD class=input>
						<Input class=codeno name=OtherPopedom ondblclick=" showCodeList('OtherPopedom',[this,OtherPopedomName],[0,1]);" onkeyup="return showCodeListKey('OtherPopedom',[this,OtherPopedomName],[0,1]);" disabled><input class=codename name=OtherPopedomName readonly=true>
					</TD>
					<TD class=title>首席核保标志</TD>
					<TD class=input>
						<Input class=common name=PopUWFlag disabled>
					</TD>
				</TR>
				
				<TR class=common>
					<TD class=title>超级权限标志</TD>
					<TD class=input>
						<Input class=codeno name=SuperPopedomFlag ondblclick=" showCodeList('SuperPopedomFlag',[this,SuperPopedomFlagName],[0,1]);" onkeyup="return showCodeListKey('SuperPopedomFlag',[this,SuperPopedomFlagName],[0,1]);"><input class=codename name=SuperPopedomFlagName readonly=true>
					</TD>
					<TD class=title>操作员</TD>
					<TD class=input>
						<Input class=common name=Operator readonly>
					</TD>
					<TD class=input style="display:none">
						<Input class=common value=<%=operatorComCode%> name=OperatorComCode style="display:none">
					</TD>
					<TD class=input style="display:none">
						<Input class=common value=<%=Operator%> name=OperatorCode style="display:none">
					</TD>
				</TR>
			</table>
		</div>
		<table class="common">
			<TR class=common style="display:none">
				<TD class=title>入机日期</TD>
				<TD class=input>
					<Input class="readonly" name=MakeDate>
				</TD>
				<TD class=title>入机时间</TD>
				<TD class=input>
					<Input class="readonly" name=MakeTime>
				</TD>
			</TR>
			<TR class=common id ="validTR">
				<TD class=title>有效开始日期</TD>
				<TD class=input>
					<Input class="coolDatePicker" verify="有效开始日期|DATE" dateFormat="short" name=ValidStartDate>
				</TD>
				<TD class=title>有效结束日期</TD>
				<TD class=input>
					<Input class="coolDatePicker" verify="有效结束日期|DATE" dateFormat="short" name=ValidEndDate>
				</TD>
				<TD class=input style="display: none">
					<Input class=common name=HideInitTag>
				</TD>
			</TR>
				</table>
		<div id="divCmdButton", style="display:''">
			<input value="用户查询" type=button onclick="queryClick()" class="cssButton">
			<INPUT VALUE="用户增加" TYPE=button onclick="insertClick()" class="cssButton">
			<INPUT VALUE="用户更新" TYPE=button onclick="updateClick()" class="cssButton">
			<INPUT VALUE="用户删除" TYPE=button onclick="deleteClick()" class="cssButton">
		</div>
		<Div id="divUserGrid" style="display: ''">
			<table>
				<tr>
					<td class=titleImg>用户表结果</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanUserGrid"></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" TYPE=button onclick="userFirstPage()" class="cssButton">
			<INPUT VALUE="上一页" TYPE=button onclick="userPageUp()" class="cssButton">
			<INPUT VALUE="下一页" TYPE=button onclick="userPageDown()" class="cssButton">
			<INPUT VALUE="尾  页" TYPE=button onclick="userLastPage()" class="cssButton">
		</div>
		<div id="hide" style="display: none">
			<table class=common>
				<tr>
					<TD class=input>
						<Input class=common name=Action>
					</TD>
				</tr>
			</table>
		</div>
		<Div id="divMenuGrpGrid" style="display: none">
			<table class=common>
				<tr>
					<td class=titleImg>用户拥有的菜单组</td>
					<td class=titleImg>用户未拥有的菜单组</td>
				</tr>
			</table>
			<input value="用户菜单组浏览" type=button onclick="showMenuGrp()" style="display:none" class="cssButton">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSelectMenuGrpGrid"></span>
					</td>
					<td text-align: left colSpan=1>
						<span id="spanUnselectMenuGrpGrid"></span>
					</td>
					<td text-align: left colSpan=1>
						<span id="spanHideMenuGrpGrid1" style="display: none"></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" TYPE=button onclick="selectFirstPage()" class="cssButton">
			<INPUT VALUE="上一页" TYPE=button onclick="selectPageUp()" class="cssButton">
			<INPUT VALUE="下一页" TYPE=button onclick="selectPageDown()" class="cssButton">
			<INPUT VALUE="尾  页" TYPE=button onclick="selectLastPage()" class="cssButton">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT VALUE=">" TYPE=button onclick="removeMenus()" class="cssButton">
			<INPUT VALUE="<" TYPE=button onclick="addMenus()" class="cssButton">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT VALUE="首  页" TYPE=button onclick="unselectFirstPage()" class="cssButton">
			<INPUT VALUE="上一页" TYPE=button onclick="unselectPageUp()" class="cssButton">
			<INPUT VALUE="下一页" TYPE=button onclick="unselectPageDown()" class="cssButton">
			<INPUT VALUE="尾  页" TYPE=button onclick="unselectLastPage()" class="cssButton">
			<table class=common>
				<tr>
					<td class=titleImg>菜单组节点明细表</td>
				</tr>
				<tr>
					<td text-align: left colSpan=1>
						<span id="spanMenuTree"></span>
					</td>
				</tr>
			</table>
		</div>
		<div id="divSubCmdButton" style="display: none">
			<INPUT VALUE="确  定" TYPE=button onclick="okClick()" class="cssButton">
			<INPUT VALUE="退  出" TYPE=button onclick="cancelClick()" class="cssButton">
		</div>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>