
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%@page import="java.util.HashMap"%>

<%@page import="org.apache.log4j.Logger"%>
<%@page import="org.jdom.Element"%>
<%@page import="org.jdom.xpath.XPath"%>

<%@page import="com.sinosoft.midplat.bat.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.newccb.bat.*"%>
<%@page import="com.sinosoft.midplat.newabc.bat.*" %>

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
	if (null == mClassName || "".equals(mClassName)){
		mResultMsg = "暂不支持该银行！";
	}else{
		if ("01".equals(mTranCom)){
			if ("ToBankBalance".equals(mExtendClass)){
				ToBankBalance tBalance = (ToBankBalance) Class.forName(mClassName).newInstance();
				tBalance.setDate(mTranDate);
				tBalance.run();
				mResultMsg = tBalance.getResultMsg();
			}else{
				Balance tBalance = (Balance) Class.forName(mClassName).newInstance();
				tBalance.setDate(mTranDate);
				tBalance.run();
				mResultMsg = tBalance.getResultMsg();
			}
		}
		
		/**建行对账**/
		if ("03".equals(mTranCom) && "20031".equals(mFuncFlag)){//建行账务对账
			CCBBusiBlc tCCBBusiBlc = new CCBBusiBlc();
			tCCBBusiBlc.setDate(mTranDate);
			tCCBBusiBlc.run();
			mResultMsg = tCCBBusiBlc.getResultMsg();
		}
		if ("03".equals(mTranCom) && "20032".equals(mFuncFlag)){//建行保全对账
			CCB_BQBusiBlc tCCB_BQBusiBlc = new CCB_BQBusiBlc();
			tCCB_BQBusiBlc.setDate(mTranDate);
			tCCB_BQBusiBlc.run();
			mResultMsg = tCCB_BQBusiBlc.getResultMsg();
		}
		if ("03".equals(mTranCom) && "20033".equals(mFuncFlag)){//建行单证补对账
			NewCcbBuBalance tNewCcbBuBalance= new NewCcbBuBalance();
			tNewCcbBuBalance.setDate(mTranDate);
			tNewCcbBuBalance.run();
			mResultMsg = tNewCcbBuBalance.getResultMsg();
		} 
		
		/**农行对账**/
		if ("05".equals(mTranCom) && "2000".equals(mFuncFlag)){//农行凭证对账
			NewAbcCardBlc tNewAbcCardBlc= new NewAbcCardBlc();
			tNewAbcCardBlc.setDate(mTranDate);
			tNewAbcCardBlc.run();
			mResultMsg = tNewAbcCardBlc.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2001".equals(mFuncFlag)){//农行新单对账
			NewAbcBusiBlc tNewAbcBusiBlc= new NewAbcBusiBlc();
			tNewAbcBusiBlc.setDate(mTranDate);
			tNewAbcBusiBlc.run();
			mResultMsg = tNewAbcBusiBlc.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2002".equals(mFuncFlag)){//农行非实时日终对账
			NonReaTimeIssWatDetail tNonReaTimeIssWatDetail= new NonReaTimeIssWatDetail();
			tNonReaTimeIssWatDetail.setDate(mTranDate);
			tNonReaTimeIssWatDetail.run();
			mResultMsg = tNonReaTimeIssWatDetail.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2003".equals(mFuncFlag)){//农行保全对账
			SecuTradAppDoc tSecuTradAppDoc= new SecuTradAppDoc();
			tSecuTradAppDoc.setDate(mTranDate);
			tSecuTradAppDoc.run();
			mResultMsg = tSecuTradAppDoc.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2004".equals(mFuncFlag)){//农行退保犹撤对账文件
			NoTakenBalanceRst tNoTakenBalanceRst= new NoTakenBalanceRst();
			tNoTakenBalanceRst.setDate(mTranDate);
			tNoTakenBalanceRst.run();
			mResultMsg = tNoTakenBalanceRst.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2005".equals(mFuncFlag)){//农行非实时出单结果明细文件
			NonRealTimeContRstDetail tNonRealTimeContRstDetail= new NonRealTimeContRstDetail();
			tNonRealTimeContRstDetail.setDate(mTranDate);
			tNonRealTimeContRstDetail.run();
			mResultMsg = tNonRealTimeContRstDetail.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2006".equals(mFuncFlag)){//农行退保犹撤数据回盘文件
			SurRetrDataDocBankDeal tSurRetrDataDocBankDeal= new SurRetrDataDocBankDeal();
			tSurRetrDataDocBankDeal.setDate(mTranDate);
			tSurRetrDataDocBankDeal.run();
			mResultMsg = tSurRetrDataDocBankDeal.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2007".equals(mFuncFlag)){//农行非实时出单结果回盘文件
			NonReaTimeIssResDocBankDeal tNonReaTimeIssResDocBankDeal= new NonReaTimeIssResDocBankDeal();
			tNonReaTimeIssResDocBankDeal.setDate(mTranDate);
			tNonReaTimeIssResDocBankDeal.run();
			mResultMsg = tNonReaTimeIssResDocBankDeal.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2008".equals(mFuncFlag)){//农行非实时出单结果文件
			NonRealTimeContRst tNonRealTimeContRst= new NonRealTimeContRst();
			tNonRealTimeContRst.setDate(mTranDate);
			tNonRealTimeContRst.run();
			mResultMsg = tNonRealTimeContRst.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2010".equals(mFuncFlag)){//农行测试上传10M文件
			ceshi tceshi= new ceshi();
			tceshi.setDate(mTranDate);
			tceshi.run();
			mResultMsg=tceshi.getResultMsg();
		}
		if ("05".equals(mTranCom) && "2011".equals(mFuncFlag)){//农行下载大文件
			DownloadLargeFile tDownloadLargeFile= new DownloadLargeFile();
			tDownloadLargeFile.setDate(mTranDate);
			tDownloadLargeFile.run();
			mResultMsg=tDownloadLargeFile.getResultMsg();
		}
		if ("05".equals(mTranCom) && "1031".equals(mFuncFlag)){//农行证书下载
			CertificateDownload tCertificateDownload= new CertificateDownload();
			tCertificateDownload.setDate(mTranDate);
			tCertificateDownload.run();
			mResultMsg=tCertificateDownload.getResultMsg();
		}
		if ("05".equals(mTranCom) && "1001".equals(mFuncFlag)){//农行密钥重置交易
			KeyReset tKeyReset= new KeyReset();
			tKeyReset.setDate(mTranDate);
			tKeyReset.run();
			mResultMsg=tKeyReset.getResultMsg();
		}
				
	}

	cLogger.info("out ManualBalanceSave.jsp...");
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=mResultMsg%>");
</script>
</html>