package com.sinosoft.midplat.gzbank.format;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
	
	//贵州银行非标准输入报文
	Document cInNoStd = null;
	
	/**
	 *  贵州银行非标准报文转为核心标准报文
	 *  @param pInNoStd 贵州银行非标准输入报文
	 *  @return mStdXml 核心标准输入报文
	 */
	@Override
	public Document noStd2Std(Document pInNoStd) throws Exception {
		//Into PolicyContTrial.noStd2Std()...
		cLogger.info("Into PolicyContTrial.noStd2Std()...");
		//为贵州银行非标准输入报文成员变量赋值
		this.cInNoStd = pInNoStd;

//		int BeneficiaryCount = Integer.parseInt(pInNoStd.getRootElement()
//				.getChildText("BeneficiaryCount"));
//		for (int i = 1; i < BeneficiaryCount + 1; i++) {
//			pInNoStd.getRootElement().getChild("Beneficiary" + i)
//					.setName("Bnf");
//		}
//		int CoverageCount = Integer.parseInt(pInNoStd.getRootElement()
//				.getChildText("CoverageCount"));
//		for (int i = 1; i < CoverageCount + 1; i++) {
//			pInNoStd.getRootElement().getChild("Extension" + i)
//					.setName("Extension");
//		}
		//新单试算核心标准输入报文扩展样式表获取单一实例得到端口-处理类配置将贵州银行非标准输入报文转换为核心标准输入报文
		Document mStdXml = PolicyContTrialInXsl.newInstance().getCache()
				.transform(pInNoStd);
//		mStdXml.getRootElement()
//				.getChild("Head")
//				.getChild("TranCom")
//				.setText(
//						cThisBusiConf.getParentElement()
//								.getChildText("TranCom"));
		//Out PolicyContTrial.noStd2Std()!
		cLogger.info("Out PolicyContTrial.noStd2Std()!");
		//返回核心标准输入报文
		return mStdXml;
	}

	/**
	 * 核心标准报文转为贵州银行非标准报文
	 * @param pOutStd 核心标准输出报文
	 * @return mNoStdXml 贵州银行非标准输出报文
	 */
	@Override
	public Document std2NoStd(Document pOutStd) throws Exception {
		//Into PolicyContTrial.std2NoStd()...
		cLogger.info("Into PolicyContTrial.std2NoStd()...");
		//新单试算输出贵州银行应答报文扩展样式表获取单一实例得到端口-处理类配置将核心标准输出报文转换为贵州银行非标准输出报文
		Document mNoStdXml = PolicyContTrialOutXsl.newInstance().getCache()
				.transform(pOutStd);
		//获取贵州银行非标准输出报文根节点
		Element mRoot = mNoStdXml.getRootElement();
		//获取贵州银行非标准输入报文根节点
		Element mNoStdRoot = this.cInNoStd.getRootElement();
		//获取贵州银行非标准输出报文交易返回码
		String mResultCode = mNoStdXml.getRootElement().getChildText("ResultCode");
		//贵州银行非标准输出报文交易返回码为00[成功]
		if ("00".equals(mResultCode)) {
			//投保人信息
			Element mPolicyHolder = (Element) mNoStdRoot.getChild("PolicyHolder")
					.clone();
			//被保人信息
			Element mInsured = (Element) mNoStdRoot.getChild("Insured").clone();
			//身故受益人是否为法定标志
			Element mBeneficiaryIndicator = (Element) mNoStdRoot.getChild(
					"BeneficiaryIndicator").clone();
			//身故受益人个数
			Element mBeneficiaryCount = (Element) mNoStdRoot.getChild(
					"BeneficiaryCount").clone();
			//根节点加入被保人信息元素
			mRoot.addContent(mPolicyHolder).addContent(mInsured);
			//根节点加入身故受益人是否为法定标志
			mRoot.addContent(mBeneficiaryIndicator).addContent(
					mBeneficiaryCount);
			//获取身故受益人个数文本
			String mBnfCount = mBeneficiaryCount.getText();
			//身故受益人个数非0[有身故受益人]
			if (Integer.parseInt(mBnfCount) > 0) {
				//遍历身故受益人列表
				for (int i = 1; i <= Integer.parseInt(mBnfCount); i++) {
					//复制当前身故受益人元素
					Element mBeneficiary = (Element) mNoStdRoot.getChild(
							"Beneficiary" + i).clone();
					//根节点加入当前身故受益人元素
					mRoot.addContent(mBeneficiary);
				}
			}
			//投保单号
			Element mHOAppFormNumber = (Element) mNoStdRoot.getChild(
					"HOAppFormNumber").clone();
			//合计保费
			Element mPaymentAmt = new Element("PaymentAmt").setText(
					NumberUtil.fenToYuan(pOutStd.getRootElement().getChild("Body").getChildText(Prem))
					);
			//险种代码
			Element mProductCode = (Element) mNoStdRoot.getChild("ProductCode")
					.clone();
			//根节点加入投保单号、合计保费、险种代码
			mRoot.addContent(mHOAppFormNumber).addContent(mPaymentAmt)
					.addContent(mProductCode);
			//核心输出标准报文获取/报文体/险种/主险代码
			String mMainRiskCode = pOutStd.getRootElement().getChild("Body")
					.getChild(Risk).getChildText("MainRiskCode");
			//核心输出标准报文获取/报文体/险种列表
			List mLisRisk = pOutStd.getRootElement().getChild("Body")
					.getChildren(Risk);
			//主险元素
			Element mMainRiskEle = null;
			//险种列表元素数为1
			if (mLisRisk.size() == 1) {
				//获取险种
				mMainRiskEle = (Element) mLisRisk.get(0);
			//险种列表元素数非1
			} else {
				//遍历险种列表
				for (int i = 0; i < mLisRisk.size(); i++) {
					//获取当前险种
					mMainRiskEle = (Element) mLisRisk.get(i);
					//当前险种险种代码为主险代码
					if (mMainRiskEle.getChild("RiskCode").equals(mMainRiskCode)) {
						//结束遍历
						break;
					}
				}
			}
			//主险保费
			Element mModalPremAmt = new Element("ModalPremAmt")
					.setText(NumberUtil.fenToYuan(mMainRiskEle.getChildText(Prem)));
			//险种名称
			Element mPlanName = new Element("PlanName").setText(mMainRiskEle
					.getChildText(RiskName));
			//投保日期
			Element mSubmissionDate = (Element) mNoStdRoot
					.getChild("SubmissionDate").clone();
			//投保份数
			Element mIntialNumberOfUnits = (Element) mNoStdRoot.getChild(
					"IntialNumberOfUnits").clone();
			//缴费方式码
			Element mPaymentMode = new Element("PaymentMode")
					.setText(transpaymentmode(
							mMainRiskEle.getChildText(PayIntv),
							mMainRiskEle.getChildText(PayEndYearFlag)));
			//缴费方式名称
			Element mPaymentModeName = new Element("PaymentModeName")
					.setText(transPaymentModeName(
							mMainRiskEle.getChildText(PayIntv),
							mMainRiskEle.getChildText(PayEndYearFlag)));
			//缴费年限
			Element mPaymentDuration = new Element("PaymentDuration")
					.setText(mMainRiskEle.getChildText(PayEndYear));
			//保险公司业务员代码
			Element mTCpicTeller = new Element("TCpicTeller");
			//领取年龄
			Element mPayoutStart = new Element("PayoutStart")
					.setText(mMainRiskEle.getChildText(GetYear));
			//红利领取方式代码
			Element mDivType = new Element("DivType");
			//红利领取方式名称
			Element mDivTypeName = new Element("DivTypeName");
			//领取/给付方式代码
			Element mBenefitMode = new Element("BenefitMode");
			//领取/给付方式名称
			Element mBenefitModeName = new Element("BenefitModeName");
			//首期年金领取日期
			Element mFirstPayOutDate = new Element("FirstPayOutDate");
			//保额责任个数
			Element mBasePreCount = new Element("BasePreCount");
			//保额责任[结构循环]
			Element mBasePre = new Element("BasePre").addContent(
					new Element("BasePerName")).addContent(//保额责任名称
					new Element("BasePeramount"));//保额数值
			//缴费标准
			Element mFeeStd = new Element("FeeStd");
			//综合加费标准
			Element mFeeCon = new Element("FeeCon");
			//职业加费标准
			Element mFeePro = new Element("FeePro");
			//缴费终止年龄
			Element mPaymentEndAge = new Element("PaymentEndAge");
			//缴费起始日期
			Element mPaymentDueDate = new Element("PaymentDueDate");
			//缴费终止日期
			Element mFinalPaymentDate = new Element("FinalPaymentDate");
			//责任起始日期
			Element mEffDate = new Element("EffDate");
			//责任终止日期
			Element mTermDate = new Element("TermDate");
			//附加险个数
			Element mCoverageCount = new Element("CoverageCount");
			/*根节点加入主险保费、险种名称、投保日期、投保份数、缴费方式码、
			 * 缴费方式名称、缴费年限、保险公司业务员代码、领取年龄、红利领取方式代码、
			 * 红利领取方式名称、领取/给付方式代码、领取/给付方式名称、首期年金领取日期、保额责任个数、
			 * 保额责任[结构循环]、缴费标准、综合加费标准、职业加费标准、缴费终止年龄、缴费起始日期、
			 * 缴费终止日期、责任起始日期、责任终止日期、附加险个数*/
			mRoot.addContent(mModalPremAmt).addContent(mPlanName).addContent(mSubmissionDate).addContent(mIntialNumberOfUnits).addContent(mPaymentMode)
			.addContent(mPaymentModeName).addContent(mPaymentDuration).addContent(mTCpicTeller).addContent(mPayoutStart).addContent(mDivType)
			.addContent(mDivTypeName).addContent(mBenefitMode).addContent(mBenefitModeName).addContent(mFirstPayOutDate).addContent(mBasePreCount)
			.addContent(mBasePre).addContent(mFeeStd).addContent(mFeeCon).addContent(mFeePro).addContent(mPaymentEndAge).addContent(mPaymentDueDate)
			.addContent(mFinalPaymentDate).addContent(mEffDate).addContent(mTermDate).addContent(mCoverageCount);
		}
		
		//Out PolicyContTrial.std2NoStd()!
		cLogger.info("Out PolicyContTrial.std2NoStd()!");
		//返回贵州银行非标准报文
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
		
		InputStream mIms = new FileInputStream("f://xml/HG//录单核保_outSvc.xml");
		
		Document doc = JdomUtil.build(mIms);
		
		PolicyContTrial tPolicyContTrial = new PolicyContTrial(null);
		
		Document result = tPolicyContTrial.std2NoStd(doc);
		
		JdomUtil.print(result);
		
		
		
	}

}
