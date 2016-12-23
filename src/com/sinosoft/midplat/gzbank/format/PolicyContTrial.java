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
	
	//�������зǱ�׼���뱨��
	Document cInNoStd = null;
	
	/**
	 *  �������зǱ�׼����תΪ���ı�׼����
	 *  @param pInNoStd �������зǱ�׼���뱨��
	 *  @return mStdXml ���ı�׼���뱨��
	 */
	@Override
	public Document noStd2Std(Document pInNoStd) throws Exception {
		//Into PolicyContTrial.noStd2Std()...
		cLogger.info("Into PolicyContTrial.noStd2Std()...");
		//Ϊ�������зǱ�׼���뱨�ĳ�Ա������ֵ
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
		//�µ�������ı�׼���뱨����չ��ʽ���ȡ��һʵ���õ��˿�-���������ý��������зǱ�׼���뱨��ת��Ϊ���ı�׼���뱨��
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
		//���غ��ı�׼���뱨��
		return mStdXml;
	}

	/**
	 * ���ı�׼����תΪ�������зǱ�׼����
	 * @param pOutStd ���ı�׼�������
	 * @return mNoStdXml �������зǱ�׼�������
	 */
	@Override
	public Document std2NoStd(Document pOutStd) throws Exception {
		//Into PolicyContTrial.std2NoStd()...
		cLogger.info("Into PolicyContTrial.std2NoStd()...");
		//�µ����������������Ӧ������չ��ʽ���ȡ��һʵ���õ��˿�-���������ý����ı�׼�������ת��Ϊ�������зǱ�׼�������
		Document mNoStdXml = PolicyContTrialOutXsl.newInstance().getCache()
				.transform(pOutStd);
		//��ȡ�������зǱ�׼������ĸ��ڵ�
		Element mRoot = mNoStdXml.getRootElement();
		//��ȡ�������зǱ�׼���뱨�ĸ��ڵ�
		Element mNoStdRoot = this.cInNoStd.getRootElement();
		//��ȡ�������зǱ�׼������Ľ��׷�����
		String mResultCode = mNoStdXml.getRootElement().getChildText("ResultCode");
		//�������зǱ�׼������Ľ��׷�����Ϊ00[�ɹ�]
		if ("00".equals(mResultCode)) {
			//Ͷ������Ϣ
			Element mPolicyHolder = (Element) mNoStdRoot.getChild("PolicyHolder")
					.clone();
			//��������Ϣ
			Element mInsured = (Element) mNoStdRoot.getChild("Insured").clone();
			//����������Ƿ�Ϊ������־
			Element mBeneficiaryIndicator = (Element) mNoStdRoot.getChild(
					"BeneficiaryIndicator").clone();
			//��������˸���
			Element mBeneficiaryCount = (Element) mNoStdRoot.getChild(
					"BeneficiaryCount").clone();
			//���ڵ���뱻������ϢԪ��
			mRoot.addContent(mPolicyHolder).addContent(mInsured);
			//���ڵ��������������Ƿ�Ϊ������־
			mRoot.addContent(mBeneficiaryIndicator).addContent(
					mBeneficiaryCount);
			//��ȡ��������˸����ı�
			String mBnfCount = mBeneficiaryCount.getText();
			//��������˸�����0[�����������]
			if (Integer.parseInt(mBnfCount) > 0) {
				//��������������б�
				for (int i = 1; i <= Integer.parseInt(mBnfCount); i++) {
					//���Ƶ�ǰ���������Ԫ��
					Element mBeneficiary = (Element) mNoStdRoot.getChild(
							"Beneficiary" + i).clone();
					//���ڵ���뵱ǰ���������Ԫ��
					mRoot.addContent(mBeneficiary);
				}
			}
			//Ͷ������
			Element mHOAppFormNumber = (Element) mNoStdRoot.getChild(
					"HOAppFormNumber").clone();
			//�ϼƱ���
			Element mPaymentAmt = new Element("PaymentAmt").setText(
					NumberUtil.fenToYuan(pOutStd.getRootElement().getChild("Body").getChildText(Prem))
					);
			//���ִ���
			Element mProductCode = (Element) mNoStdRoot.getChild("ProductCode")
					.clone();
			//���ڵ����Ͷ�����š��ϼƱ��ѡ����ִ���
			mRoot.addContent(mHOAppFormNumber).addContent(mPaymentAmt)
					.addContent(mProductCode);
			//���������׼���Ļ�ȡ/������/����/���մ���
			String mMainRiskCode = pOutStd.getRootElement().getChild("Body")
					.getChild(Risk).getChildText("MainRiskCode");
			//���������׼���Ļ�ȡ/������/�����б�
			List mLisRisk = pOutStd.getRootElement().getChild("Body")
					.getChildren(Risk);
			//����Ԫ��
			Element mMainRiskEle = null;
			//�����б�Ԫ����Ϊ1
			if (mLisRisk.size() == 1) {
				//��ȡ����
				mMainRiskEle = (Element) mLisRisk.get(0);
			//�����б�Ԫ������1
			} else {
				//���������б�
				for (int i = 0; i < mLisRisk.size(); i++) {
					//��ȡ��ǰ����
					mMainRiskEle = (Element) mLisRisk.get(i);
					//��ǰ�������ִ���Ϊ���մ���
					if (mMainRiskEle.getChild("RiskCode").equals(mMainRiskCode)) {
						//��������
						break;
					}
				}
			}
			//���ձ���
			Element mModalPremAmt = new Element("ModalPremAmt")
					.setText(NumberUtil.fenToYuan(mMainRiskEle.getChildText(Prem)));
			//��������
			Element mPlanName = new Element("PlanName").setText(mMainRiskEle
					.getChildText(RiskName));
			//Ͷ������
			Element mSubmissionDate = (Element) mNoStdRoot
					.getChild("SubmissionDate").clone();
			//Ͷ������
			Element mIntialNumberOfUnits = (Element) mNoStdRoot.getChild(
					"IntialNumberOfUnits").clone();
			//�ɷѷ�ʽ��
			Element mPaymentMode = new Element("PaymentMode")
					.setText(transpaymentmode(
							mMainRiskEle.getChildText(PayIntv),
							mMainRiskEle.getChildText(PayEndYearFlag)));
			//�ɷѷ�ʽ����
			Element mPaymentModeName = new Element("PaymentModeName")
					.setText(transPaymentModeName(
							mMainRiskEle.getChildText(PayIntv),
							mMainRiskEle.getChildText(PayEndYearFlag)));
			//�ɷ�����
			Element mPaymentDuration = new Element("PaymentDuration")
					.setText(mMainRiskEle.getChildText(PayEndYear));
			//���չ�˾ҵ��Ա����
			Element mTCpicTeller = new Element("TCpicTeller");
			//��ȡ����
			Element mPayoutStart = new Element("PayoutStart")
					.setText(mMainRiskEle.getChildText(GetYear));
			//������ȡ��ʽ����
			Element mDivType = new Element("DivType");
			//������ȡ��ʽ����
			Element mDivTypeName = new Element("DivTypeName");
			//��ȡ/������ʽ����
			Element mBenefitMode = new Element("BenefitMode");
			//��ȡ/������ʽ����
			Element mBenefitModeName = new Element("BenefitModeName");
			//���������ȡ����
			Element mFirstPayOutDate = new Element("FirstPayOutDate");
			//�������θ���
			Element mBasePreCount = new Element("BasePreCount");
			//��������[�ṹѭ��]
			Element mBasePre = new Element("BasePre").addContent(
					new Element("BasePerName")).addContent(//������������
					new Element("BasePeramount"));//������ֵ
			//�ɷѱ�׼
			Element mFeeStd = new Element("FeeStd");
			//�ۺϼӷѱ�׼
			Element mFeeCon = new Element("FeeCon");
			//ְҵ�ӷѱ�׼
			Element mFeePro = new Element("FeePro");
			//�ɷ���ֹ����
			Element mPaymentEndAge = new Element("PaymentEndAge");
			//�ɷ���ʼ����
			Element mPaymentDueDate = new Element("PaymentDueDate");
			//�ɷ���ֹ����
			Element mFinalPaymentDate = new Element("FinalPaymentDate");
			//������ʼ����
			Element mEffDate = new Element("EffDate");
			//������ֹ����
			Element mTermDate = new Element("TermDate");
			//�����ո���
			Element mCoverageCount = new Element("CoverageCount");
			/*���ڵ�������ձ��ѡ��������ơ�Ͷ�����ڡ�Ͷ���������ɷѷ�ʽ�롢
			 * �ɷѷ�ʽ���ơ��ɷ����ޡ����չ�˾ҵ��Ա���롢��ȡ���䡢������ȡ��ʽ���롢
			 * ������ȡ��ʽ���ơ���ȡ/������ʽ���롢��ȡ/������ʽ���ơ����������ȡ���ڡ��������θ�����
			 * ��������[�ṹѭ��]���ɷѱ�׼���ۺϼӷѱ�׼��ְҵ�ӷѱ�׼���ɷ���ֹ���䡢�ɷ���ʼ���ڡ�
			 * �ɷ���ֹ���ڡ�������ʼ���ڡ�������ֹ���ڡ������ո���*/
			mRoot.addContent(mModalPremAmt).addContent(mPlanName).addContent(mSubmissionDate).addContent(mIntialNumberOfUnits).addContent(mPaymentMode)
			.addContent(mPaymentModeName).addContent(mPaymentDuration).addContent(mTCpicTeller).addContent(mPayoutStart).addContent(mDivType)
			.addContent(mDivTypeName).addContent(mBenefitMode).addContent(mBenefitModeName).addContent(mFirstPayOutDate).addContent(mBasePreCount)
			.addContent(mBasePre).addContent(mFeeStd).addContent(mFeeCon).addContent(mFeePro).addContent(mPaymentEndAge).addContent(mPaymentDueDate)
			.addContent(mFinalPaymentDate).addContent(mEffDate).addContent(mTermDate).addContent(mCoverageCount);
		}
		
		//Out PolicyContTrial.std2NoStd()!
		cLogger.info("Out PolicyContTrial.std2NoStd()!");
		//���ع������зǱ�׼����
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
		 * 1 ���� 0 2 ���꽻 6 3 ���� 3 4 �½� 1 5 �꽻 12 6 ������ -1 7 ����ĳȷ������ 8 �����ɷ�
		 */

		return result;
	}

	private static String transPaymentModeName(String payintv,
			String payendyearflag) {
		String result = "";
		if ("0".equals(payintv))
			result = "����";
		else if ("1".equals(payintv))
			result = "�½�";
		else if ("3".equals(payintv))
			result = "����";
		else if ("6".equals(payintv))
			result = "���꽻";
		else if ("12".equals(payintv))
			result = "�꽻";
		else if ("-1".equals(payintv))
			result = "������";
		else
			result = "������";

		if ("12".equals(payintv) && "A".equals(payendyearflag))
			result = "����ĳȷ������";
		/**
		 * 
		 * 1 ���� 0 2 ���꽻 6 3 ���� 3 4 �½� 1 5 �꽻 12 6 ������ -1 7 ����ĳȷ������ 8 �����ɷ�
		 */

		return result;
	}
	
	public static void main(String[] args) throws Exception {
		
		InputStream mIms = new FileInputStream("f://xml/HG//¼���˱�_outSvc.xml");
		
		Document doc = JdomUtil.build(mIms);
		
		PolicyContTrial tPolicyContTrial = new PolicyContTrial(null);
		
		Document result = tPolicyContTrial.std2NoStd(doc);
		
		JdomUtil.print(result);
		
		
		
	}

}
