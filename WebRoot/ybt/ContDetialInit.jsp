<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.ybt.queryCont"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="org.jdom.*"%>
<%@page import="com.sinosoft.midplat.MidplatConf"%>
<%
	//程序名称：TestYBTContInit.jsp
	//程序功能：会计确认 
	//创建日期：2010-01-20 16:43:36
	//创建人  ：刘张海军
	//更新记录：  更新人    更新日期     更新原因/内容
	//2012-2-21 by fzg 新增查询的结果信息，包括附加险、投资账户部分
	//2012-3-8 by fzg 新增查询的结果信息的投资日期标志
%>
<%
	String sContNo = request.getParameter("ContNo");
	queryCont cont = new queryCont(sContNo);
	System.out.println("BBBBB" + cont.sInsuredBirthDay);
	String divCount = "none";
	String divCountImg = "butCollapse.gif";
	//下面 update 投资账户的判断信息了，新增两个账户的判断， by fzg 2012-2-21
	if (!"".equals(cont.sU1ZY) || !"".equals(cont.sU2WJ)
			|| !"".equals(cont.sU3AX)||!"".equals(cont.sU6JQ)
			|| !"".equals(cont.sU8HX)) {
		System.out.println(cont.sU1ZY);
		System.out.println(cont.sU2WJ);
		System.out.println(cont.sU3AX);		
		divCount = "";
		divCountImg = "butExpand.gif";
	}
	//附加险的节点的值的显示问题
    String divLCRisk1 = "none";
	String divLCRisk1Img = "butCollapse.gif";
	if ("Y".equals(cont.riskFlag1)) {
		divLCRisk1 = "";
		divLCRisk1Img = "butExpand.gif";
	}
	
	String divLCRisk2 = "none";
	String divLCRisk2Img = "butCollapse.gif";
	if ("Y".equals(cont.riskFlag2)) {
		divLCRisk2 = "";
		divLCRisk2Img = "butExpand.gif";
	}

	String divLCBnf1 = "none";
	String divLCBnf1Img = "butCollapse.gif";
	if ("Y".equals(cont.bnfFlag1)) {
		divLCBnf1 = "";
		divLCBnf1Img = "butExpand.gif";
	}

	String divLCBnf2 = "none";
	String divLCBnf2Img = "butCollapse.gif";
	if ("Y".equals(cont.bnfFlag2)) {
		divLCBnf2 = "";
		divLCBnf2Img = "butExpand.gif";
	}

	String divLCBnf3 = "none";
	String divLCBnf3Img = "butCollapse.gif";
	if ("Y".equals(cont.bnfFlag3)) {
		divLCBnf3 = "";
		divLCBnf3Img = "butExpand.gif";
	}
%>
<script language="JavaScript">
function initForm() {
	try {
		initInputBox();
	} catch (re) {
		alert("TestYBTContInit.jsp-->InitForm 函数中发生异常:初始化界面错误!");
	}
}

function initInputBox() {
	try {

		//保单信息
		fm.ProposalContNo.value = "<%=cont.sProposalContNo%>";
		fm.ContNo.value = '<%=cont.sContNo%>';
		fm.PrtNo.value = '<%=cont.sPrtNo%>';
		fm.ContState.value = '<%=cont.sContState%>';
		fm.BalanceState.value = '<%=cont.sBalanceState%>';
		fm.TranNo.value = '<%=cont.sTranNo%>';
		fm.TranCom.value = '<%=cont.sTranCom%>';
		fm.TranComName.value = '<%=cont.sTranComName%>';
		fm.ManageCom.value = '<%=cont.sManageCom%>';
		fm.ManageComName.value = '<%=cont.sManageComName%>';
		fm.BankNode.value = '<%=cont.sBankNode%>';
		fm.Operator.value = '<%=cont.sOperator%>';
		//代理人信息
		
		fm.ManageCom.value = '<%=cont.sManageCom%>';
		fm.ManageComName.value = '<%=cont.sManageComName%>';
		fm.AgentCom.value = '<%=cont.sAgentCom%>';
		fm.AgentComName.value = '<%=cont.sAgentComName%>';
		fm.AgentCode.value = '<%=cont.sAgentCode%>';
		fm.AgentCodeName.value = '<%=cont.sAgentCodeName%>';
		
		//被保人信息
		fm.InsuredName.value = '<%=cont.sInsuredName%>';
		fm.InsuredSex.value = '<%=cont.sInsuredSex%>';
		fm.InsuredBirthDay.value = '<%=cont.sInsuredBirthDay%>';
		fm.InsuredAge.value = '<%=cont.sInsuredAge%>';
		fm.InsuredIDType.value = '<%=cont.sInsuredIDType%>';
		fm.InsuredIDTypeName.value = '<%=cont.sInsuredIDTypeName%>';
		fm.InsuredIDNo.value = '<%=cont.sInsuredIDNo%>';
		fm.InsuredPhone.value = '<%=cont.sInsuredPhone%>';
		fm.InsuredMobile.value = '<%=cont.sInsuredMobile%>';
		fm.InsuredEMail.value = '<%=cont.sInsuredEMail%>';
		fm.InsuredAddress.value = '<%=cont.sInsuredAddress%>';
		fm.InsuredZipCode.value = '<%=cont.sInsuredZipCode%>';
		fm.InsuredJobeCode.value = '<%=cont.sInsuredJobeCode%>';
		//投保人信息
		fm.AppntName.value = '<%=cont.sAppntName%>';
		fm.AppntSex.value = '<%=cont.sAppntSex%>';
		fm.AppntBirthDay.value = '<%=cont.sAppntBirthDay%>';
		fm.AppntAge.value = '<%=cont.sAppntAge%>';
		fm.AppntIDType.value = '<%=cont.sAppntIDType%>';
		fm.AppntIDTypeName.value = '<%=cont.sAppntIDTypeName%>';
		fm.AppntIDNo.value = '<%=cont.sAppntIDNo%>';
		fm.AppntPhone.value = '<%=cont.sAppntPhone%>';
		fm.AppntMobile.value = '<%=cont.sAppntMobile%>';
		fm.AppntEMail.value = '<%=cont.sAppntEMail%>';
		fm.AppntAddress.value = '<%=cont.sAppntAddress%>';
		fm.AppntZipCode.value = '<%=cont.sAppntZipCode%>';
		fm.AppntJobeCode.value = '<%=cont.sAppntJobeCode%>';
		fm.AppntRealToInsured.value = '<%=cont.sAppntRealToInsured%>';

		//险种信息 
		fm.RiskCode.value = '<%=cont.sRiskCode%>';
		fm.RiskCodeName.value = '<%=cont.sRiskCodeName%>';
		fm.PayIntvName.value = '<%=cont.sPayIntvName%>';
		fm.InsuYearFlagName.value = '<%=cont.sInsuYearFlagName%>';
		fm.InsuYear.value = '<%=cont.sInsuYear%>';
		fm.PayEndYearFlagName.value = '<%=cont.sPayEndYearFlagName%>';
		fm.PayEndYear.value = '<%=cont.sPayEndYear%>';
		fm.Mult.value = '<%=cont.sMult%>';
		fm.Amnt.value = '<%=cont.sAmnt%>';
		fm.Prem.value = '<%=cont.sPrem%>';
		fm.MainPrem.value = '<%=cont.sMainPrem%>';
		fm.AddPrem.value = '<%=cont.sAddPrem%>';
		fm.FirstAddPrem.value = '<%=cont.sFirstAddPrem%>';
		
		//add by fzg 2012-2-21
		//附加险信息1
		fm.RiskCode1.value = '<%=cont.sRiskCode1%>';
		fm.RiskCodeName1.value = '<%=cont.sRiskCodeName1%>';
		fm.PayEndYearFlagName1.value = '<%=cont.sPayEndYearFlagName1%>';
		fm.PayEndYear1.value = '<%=cont.sPayEndYear1%>';
		fm.InsuYearFlagName1.value = '<%=cont.sInsuYearFlagName1%>';
		fm.InsuYear1.value = '<%=cont.sInsuYear1%>';
		fm.Amnt1.value = '<%=cont.sAmnt1%>';
		fm.Prem1.value = '<%=cont.sPrem1%>';
		//附加险信息2
		fm.RiskCode2.value = '<%=cont.sRiskCode2%>';
		fm.RiskCodeName2.value = '<%=cont.sRiskCodeName2%>';
		fm.PayEndYearFlagName2.value = '<%=cont.sPayEndYearFlagName2%>';
		fm.PayEndYear2.value = '<%=cont.sPayEndYear2%>';
		fm.InsuYearFlagName2.value = '<%=cont.sInsuYearFlagName2%>';
		fm.InsuYear2.value = '<%=cont.sInsuYear2%>';
		fm.Amnt2.value = '<%=cont.sAmnt2%>';
		fm.Prem2.value = '<%=cont.sPrem2%>';
		

		//投资账户信息 
		fm.U1ZY.value = '<%=cont.sU1ZY%>';
		fm.U2WJ.value = '<%=cont.sU2WJ%>';
		fm.U3AX.value = '<%=cont.sU3AX%>';
		//add by fzg 2012-2-21
		fm.U6JQ.value = '<%=cont.sU6JQ%>';
		fm.U8HX.value = '<%=cont.sU8HX%>';
		fm.AccTimeFlag.value = '<%= cont.sAccTimeFlag%>';

		//账户信息
		fm.AccName.value = '<%=cont.sAccName%>';
		fm.AccNo.value = '<%=cont.sAccNo%>';

		//受益人信息1 
		fm.BnfName.value = '<%=cont.sBnfName%>';
		fm.BnfSex.value = '<%=cont.sBnfSex%>';
		fm.BnfBirthDay.value = '<%=cont.sBnfBirthDay%>';
		fm.BnfAge.value = '<%=cont.sBnfAge%>';
		fm.BnfIDTypeName.value = '<%=cont.sBnfIDTypeName%>';
		fm.BnfIDNo.value = '<%=cont.sBnfIDNo%>';
		fm.BnfGrade.value = '<%=cont.sBnfGrade%>';
		fm.BnfLot.value = '<%=cont.sBnfLot%>';
		fm.BnfRealToInsured.value = '<%=cont.sBnfRealToInsured%>';

		//受益人信息2
		fm.BnfName2.value = '<%=cont.sBnfName2%>';
		fm.BnfSex2.value = '<%=cont.sBnfSex2%>';
		fm.BnfBirthDay2.value = '<%=cont.sBnfBirthDay2%>';
		fm.BnfAge2.value = '<%=cont.sBnfAge2%>';
		fm.BnfIDType2.value = '<%=cont.sBnfIDType2%>';
		fm.BnfIDTypeName2.value = '<%=cont.sBnfIDTypeName2%>';
		fm.BnfIDNo2.value = '<%=cont.sBnfIDNo2%>';
		fm.BnfGrade2.value = '<%=cont.sBnfGrade2%>';
		fm.BnfLot2.value = '<%=cont.sBnfLot2%>';
		fm.BnfRealToInsured2.value = '<%=cont.sBnfRealToInsured2%>';

		//受益人信息3
		fm.BnfName3.value = '<%=cont.sBnfName3%>';
		fm.BnfSex3.value = '<%=cont.sBnfSex3%>';
		fm.BnfBirthDay3.value = '<%=cont.sBnfBirthDay3%>';
		fm.BnfAge3.value = '<%=cont.sBnfAge3%>';
		fm.BnfIDType3.value = '<%=cont.sBnfIDType3%>';
		fm.BnfIDTypeName3.value = '<%=cont.sBnfIDTypeName3%>';
		fm.BnfIDNo3.value = '<%=cont.sBnfIDNo3%>';
		fm.BnfGrade3.value = '<%=cont.sBnfGrade3%>';
		fm.BnfLot3.value = '<%=cont.sBnfLot3%>';
		fm.BnfRealToInsured3.value = '<%=cont.sBnfRealToInsured3%>';
	} catch (re) {
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}
</script>
