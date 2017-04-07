package com.sinosoft.midplat.gzbank.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class PolicyContTrial extends XmlSimpFormat {
	public PolicyContTrial(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	Document cInNoStd = null;
	@Override
	public Document noStd2Std(Document pInNoStd) throws Exception {
		cLogger.info("Into PolicyContTrial.noStd2Std()...");
		this.cInNoStd = pInNoStd;
		Document mStdXml = PolicyContTrialInXsl.newInstance().getCache()
				.transform(pInNoStd);
		JdomUtil.print(mStdXml);
		cLogger.info("Out PolicyContTrial.noStd2Std()!");
		return mStdXml;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document std2NoStd(Document pOutStd) throws Exception {
		cLogger.info("Into PolicyContTrial.std2NoStd()...");
		Document mNoStdXml = PolicyContTrialOutXsl.newInstance().getCache()
				.transform(pOutStd);
		Element mRoot = mNoStdXml.getRootElement();
		
		Element mNoStdRoot = this.cInNoStd.getRootElement();

		String mResultCode = mNoStdXml.getRootElement().getChildText("ResultCode");

		if ("00".equals(mResultCode)) {
			Element mPolicyHolder = (Element) mNoStdRoot.getChild("PolicyHolder")
					.clone();
			Element mInsured = (Element) mNoStdRoot.getChild("Insured").clone();
			Element mBeneficiaryIndicator = (Element) mNoStdRoot.getChild(
					"BeneficiaryIndicator").clone();
			Element mBeneficiaryCount = (Element) mNoStdRoot.getChild(
					"BeneficiaryCount").clone();
			mRoot.addContent(mPolicyHolder).addContent(mInsured);

			mRoot.addContent(mBeneficiaryIndicator).addContent(
					mBeneficiaryCount);
			String mBnfCount = mBeneficiaryCount.getText();
			if (Integer.parseInt(mBnfCount) > 0) {
				for (int i = 1; i <= Integer.parseInt(mBnfCount); i++) {
					Element mBeneficiary = (Element) mNoStdRoot.getChild(
							"Beneficiary" + i).clone();
					mRoot.addContent(mBeneficiary);
				}
			}
			Element mHOAppFormNumber = (Element) mNoStdRoot.getChild(
					"HOAppFormNumber").clone();
			Element mPaymentAmt = new Element("PaymentAmt").setText(
					NumberUtil.fenToYuan(pOutStd.getRootElement().getChild("Body").getChildText(Prem))
					);
			Element mProductCode = (Element) mNoStdRoot.getChild("ProductCode")
					.clone();

			mRoot.addContent(mHOAppFormNumber).addContent(mPaymentAmt)
					.addContent(mProductCode);

			String mMainRiskCode = pOutStd.getRootElement().getChild("Body")
					.getChild(Risk).getChildText("MainRiskCode");
			List mLisRisk = pOutStd.getRootElement().getChild("Body")
					.getChildren(Risk);
			Element mMainRiskEle = null;
			if (mLisRisk.size() == 1) {
				mMainRiskEle = (Element) mLisRisk.get(0);
			} else {
				for (int i = 0; i < mLisRisk.size(); i++) {
					mMainRiskEle = (Element) mLisRisk.get(i);
					if (mMainRiskEle.getChild("RiskCode").equals(mMainRiskCode)) {
						break;
					}
				}
			}
			Element mModalPremAmt = new Element("ModalPremAmt")
					.setText(NumberUtil.fenToYuan(mMainRiskEle.getChildText(Prem)));
			Element mPlanName = new Element("PlanName").setText(mMainRiskEle
					.getChildText(RiskName));
			Element mSubmissionDate = (Element) mNoStdRoot
					.getChild("SubmissionDate").clone();
			Element mIntialNumberOfUnits = (Element) mNoStdRoot.getChild(
					"IntialNumberOfUnits").clone();
			Element mPaymentMode = new Element("PaymentMode")
					.setText(transpaymentmode(
							mMainRiskEle.getChildText(PayIntv),
							mMainRiskEle.getChildText(PayEndYearFlag)));
			Element mPaymentModeName = new Element("PaymentModeName")
					.setText(transPaymentModeName(
							mMainRiskEle.getChildText(PayIntv),
							mMainRiskEle.getChildText(PayEndYearFlag)));
			Element mPaymentDuration = new Element("PaymentDuration")
					.setText(mMainRiskEle.getChildText(PayEndYear));
			Element mTCpicTeller = new Element("TCpicTeller");
			Element mPayoutStart = new Element("PayoutStart")
					.setText(mMainRiskEle.getChildText(GetYear));
			Element mDivType = new Element("DivType");
			Element mDivTypeName = new Element("DivTypeName");
			Element mBenefitMode = new Element("BenefitMode");
			Element mBenefitModeName = new Element("BenefitModeName");
			Element mFirstPayOutDate = new Element("FirstPayOutDate");
			Element mBasePreCount = new Element("BasePreCount");
			Element mBasePre = new Element("BasePre").addContent(
					new Element("BasePerName")).addContent(
					new Element("BasePeramount"));
			Element mFeeStd = new Element("FeeStd");
			Element mFeeCon = new Element("FeeCon");
			Element mFeePro = new Element("FeePro");
			Element mPaymentEndAge = new Element("PaymentEndAge");
			Element mPaymentDueDate = new Element("PaymentDueDate");
			Element mFinalPaymentDate = new Element("FinalPaymentDate");
			Element mEffDate = new Element("EffDate");
			Element mTermDate = new Element("TermDate");
			Element mCoverageCount = new Element("CoverageCount");
			mRoot.addContent(mModalPremAmt).addContent(mPlanName).addContent(mSubmissionDate).addContent(mIntialNumberOfUnits).addContent(mPaymentMode)
			.addContent(mPaymentModeName).addContent(mPaymentDuration).addContent(mTCpicTeller).addContent(mPayoutStart).addContent(mDivType)
			.addContent(mDivTypeName).addContent(mBenefitMode).addContent(mBenefitModeName).addContent(mFirstPayOutDate).addContent(mBasePreCount)
			.addContent(mBasePre).addContent(mFeeStd).addContent(mFeeCon).addContent(mFeePro).addContent(mPaymentEndAge).addContent(mPaymentDueDate)
			.addContent(mFinalPaymentDate).addContent(mEffDate).addContent(mTermDate).addContent(mCoverageCount);
		}
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out PolicyContTrial.std2NoStd()!");
		return mNoStdXml;
	}

	private static String transpaymentmode(String payintv, String payendyearflag) {
		String result = "";
		if ("0".equals(payintv))
			result = "1";
		else if ("1".equals(payintv))
			result = "4";
		else if ("3".equals(payintv))
			result = "3";
		else if ("6".equals(payintv))
			result = "2";
		else if ("12".equals(payintv))
			result = "5";
		else if ("-1".equals(payintv))
			result = "6";
		else
			result = "6";

		if ("12".equals(payintv) && "A".equals(payendyearflag))
			result = "7";
		/**
		 * 
		 * 1 趸交 0 2 半年交 6 3 季交 3 4 月缴 1 5 年交 12 6 不定期 -1 7 缴至某确定年龄 8 终生缴费
		 */

		return result;
	}

	private static String transPaymentModeName(String payintv,
			String payendyearflag) {
		String result = "";
		if ("0".equals(payintv))
			result = "趸交";
		else if ("1".equals(payintv))
			result = "月缴";
		else if ("3".equals(payintv))
			result = "季交";
		else if ("6".equals(payintv))
			result = "半年交";
		else if ("12".equals(payintv))
			result = "年交";
		else if ("-1".equals(payintv))
			result = "不定期";
		else
			result = "不定期";

		if ("12".equals(payintv) && "A".equals(payendyearflag))
			result = "缴至某确定年龄";
		/**
		 * 
		 * 1 趸交 0 2 半年交 6 3 季交 3 4 月缴 1 5 年交 12 6 不定期 -1 7 缴至某确定年龄 8 终生缴费
		 */

		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\GZbank\\保费试算请求.xml";
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
//		Document mOutXmlDoc = new PolicyContTrial(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new PolicyContTrial(null).noStd2Std(mInXmlDoc);
//		new RuleParser().check(mOutXmlDoc);
		JdomUtil.print(mOutXmlDoc);
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		System.out.println("成功结束！");
	}
}
