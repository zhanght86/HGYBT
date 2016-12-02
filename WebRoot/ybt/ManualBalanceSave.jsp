
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%@page import="java.util.HashMap"%>

<%@page import="org.apache.log4j.Logger"%>
<%@page import="org.jdom.Element"%>
<%@page import="org.jdom.xpath.XPath"%>

<%@page import="com.sinosoft.midplat.bat.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.newabc.bat.NewAbcBusiBlc"%>
<%@page import="com.sinosoft.midplat.newabc.bat.NewAbcCardBlc" %>
<%@page import="com.sinosoft.midplat.nbcb.bat.NbcbBusiBlc"%>
<%@page import="com.sinosoft.midplat.nbcb.bat.NbcbCardBlc" %>
<%@page import="com.sinosoft.midplat.jhyh.bat.JhyhBusiBlc"%>
<%@page import="com.sinosoft.midplat.jhyh.bat.JhyhCardBlc" %>
<%@page import="com.sinosoft.midplat.newccb.bat.NewCcbBQBlc"%>
<%@page import="com.sinosoft.midplat.newccb.bat.NewCcbZWBlc"%>
<%@page import="com.sinosoft.midplat.newccb.bat.NewCcbBuBalance"%>
<%@page import="com.sinosoft.midplat.newabc.bat.HandCont" %>
<%@page import="com.sinosoft.midplat.newabc.bat.HandContRiskDtl" %>

<%
	Logger cLogger = Logger.getLogger(getClass());
	cLogger.info("into ManualBalanceSave.jsp...");
	System.out.println("00000000000");
	GlobalInput cGlobalInput = (GlobalInput) session.getAttribute("GI");

	String mTranCom = request.getParameter("TranCom");
	String mFuncFlag = request.getParameter("FuncFlag");
	String mTranDate = DateUtil.date10to8(request.getParameter("TranDate"));
	System.out.println(mTranCom);
	System.out.println(mFuncFlag);
	System.out.println(mTranDate);
	String mClassName = XPath.newInstance("//batch[com='" + mTranCom + "' and funcFlag='" + mFuncFlag + "']/class").valueOf(BatConf.newInstance().getConf());

	String mExtendClass = XPath.newInstance("//batch[com='" + mTranCom + "' and funcFlag='" + mFuncFlag + "']/extendClass").valueOf(BatConf.newInstance().getConf());

	System.out.println(mClassName + ": " + mTranCom + "-" + mFuncFlag + "-" + mTranDate + " - mExtendClass:" + mExtendClass);

	String mResultMsg = null;
	if (null == mClassName || "".equals(mClassName) && !"03".equals(mTranCom)&&!"3009".equals(mFuncFlag))
	{
		mResultMsg = "暂不支持该银行！";
	}
	else
	{
		if ("01".equals(mTranCom))
		{
			if ("ToBankBalance".equals(mExtendClass))
			{
				ToBankBalance tBalance = (ToBankBalance) Class.forName(mClassName).newInstance();
				tBalance.setDate(mTranDate);
				tBalance.run();
				mResultMsg = tBalance.getResultMsg();
			}
			else
			{
				Balance tBalance = (Balance) Class.forName(mClassName).newInstance();
				tBalance.setDate(mTranDate);
				tBalance.run();
				mResultMsg = tBalance.getResultMsg();
			}
		}
		if ("05".equals(mTranCom))
		{
			//新单对账
			if("2001".equals(mFuncFlag))
			{
				NewAbcBusiBlc tNewAbcBlc = new NewAbcBusiBlc();
				tNewAbcBlc.setDate(mTranDate);
				tNewAbcBlc.run();
				mResultMsg = tNewAbcBlc.getResultMsg();
			}
			//单证对账
			else if("2000".equals(mFuncFlag))
			{
				NewAbcCardBlc tNewAbcBlc = new NewAbcCardBlc();
				tNewAbcBlc.setDate(mTranDate);
				tNewAbcBlc.run();
				mResultMsg = tNewAbcBlc.getResultMsg();
			}else if("2010".equals(mFuncFlag))
			{
				HandCont tHandCont = new HandCont();
				tHandCont.setDate(mTranDate);
				tHandCont.run();
				mResultMsg = tHandCont.getResultMsg();
			}else if("2011".equals(mFuncFlag))
			{
			    HandContRiskDtl tHandContRiskDt = new HandContRiskDtl();
				tHandContRiskDt.setDate(mTranDate);
				tHandContRiskDt.run();
				mResultMsg = tHandContRiskDt.getResultMsg();
			}
			
		}
		if ("06".equals(mTranCom))
		{
			//新单对账
			if("4005".equals(mFuncFlag))
			{
				NbcbBusiBlc tNbcbBlc = new NbcbBusiBlc();
				tNbcbBlc.setDate(mTranDate);
				tNbcbBlc.run();
				mResultMsg = tNbcbBlc.getResultMsg();
			}
			//单证对账
			else if("4006".equals(mFuncFlag))
			{
				NbcbCardBlc tNbcbBlc = new NbcbCardBlc();
				tNbcbBlc.setDate(mTranDate);
				tNbcbBlc.run();
				mResultMsg = tNbcbBlc.getResultMsg();
			}
			
		}
		if ("07".equals(mTranCom))
		{
			//新单对账
			if("5005".equals(mFuncFlag))
			{
				JhyhBusiBlc tJhyhBlc = new JhyhBusiBlc();
				tJhyhBlc.setDate(mTranDate);
				tJhyhBlc.run();
				mResultMsg = tJhyhBlc.getResultMsg();
			}
			//单证对账
			else if("5006".equals(mFuncFlag))
			{
				JhyhCardBlc tJhyhBlc = new JhyhCardBlc();
				tJhyhBlc.setDate(mTranDate);
				tJhyhBlc.run();
				mResultMsg = tJhyhBlc.getResultMsg();
			}
		}
		if ("03".equals(mTranCom) && "3005".equals(mFuncFlag))
		{//建行账务对账
			NewCcbZWBlc tNewCcbZWBlc = new NewCcbZWBlc();
			tNewCcbZWBlc.setDate(mTranDate);
			tNewCcbZWBlc.run();
			mResultMsg = tNewCcbZWBlc.getResultMsg();
		}
		if ("03".equals(mTranCom) && "1048".equals(mFuncFlag))
		{//建行保全对账
			NewCcbBQBlc tNewCcbBQBlc = new NewCcbBQBlc();
			tNewCcbBQBlc.setDate(mTranDate);
			tNewCcbBQBlc.run();
			mResultMsg = tNewCcbBQBlc.getResultMsg();
		}
		if ("03".equals(mTranCom) && "3009".equals(mFuncFlag))
		{//建行单证补对账
			NewCcbBuBalance tNewCcbBuBalance= new NewCcbBuBalance();
			tNewCcbBuBalance.setDate(mTranDate);
			tNewCcbBuBalance.run();
			mResultMsg = tNewCcbBuBalance.getResultMsg();
		}

	}

	cLogger.info("out ManualBalanceSave.jsp...");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=mResultMsg%>");
</script>
</html>