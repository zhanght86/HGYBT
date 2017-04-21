package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.spdb.SpdbConf;
import com.sinosoft.utility.ExeSQL;

public class SpdbBusiBlc extends ToBankBalance
{
	public SpdbBusiBlc()
	{
		super(SpdbConf.newInstance(), "3001");
	}

	@Override
	protected String getFileName(Date cDate)
	{
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "DAYCHECK" + "SPDB" + mBankEle.getAttributeValue("insu") + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + ".xml";
	}

	public Document parse(InputStream pBatIs)
	{
		cLogger.info("Into SpdbBusiBlc.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		
		//���ڵ�
		Element mTranData = new Element("TranData");
		//��������
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		//����ʱ��
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		//���׻�������
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		//���д���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(SpdbConf.newInstance().getConf().getRootElement().getChildText("BankCode"));
				
		//ʡ�д���
		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
		
		//��������
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		//��Ա����
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		//������ˮ��
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		//��������
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		//������־��[���]
		Element mTranLogNo = new Element("TranLogNo");
		
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mTranCom).addContent(mBankCode)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag).addContent(mTranLogNo);
		mTranData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		
		if (null == mCharset || "".equals(mCharset))
		{
			mCharset = "GBK";
		}
		
		Document mStdXml = JdomUtil.build(pBatIs);
		cLogger.info("���еķǱ�׼����!");
		JdomUtil.print(mStdXml);
		
		//CHECK�ڵ�
		Element tRoot = mStdXml.getRootElement().getChild("CHECK");
		//������
		Element mBodyEle = new Element(Body);
		//�ܱ���
		Element mCountEle = new Element(Count);
		//�ܽ��(��)
		Element mPremEle = new Element(Prem);
		//����������ܱ����ڵ�
		mBodyEle.addContent(mCountEle);
		//����������ܽ��(��)�ڵ�
		mBodyEle.addContent(mPremEle);
		
		//CHECK�ڵ�ǿ�
		if (tRoot != null)
		{
			// PROJECT=2ʱΪ����Լ����
			if (tRoot.getChildText("PROJECT").equals("2"))
			{
				//�ܱ���
				long mSumPrem = 0;
				//�ܱ���
				int mCount = 0;
				
				//��ȡCHECK�ڵ��½�����ϸ�ӽڵ��½��׽ڵ��б�
				@SuppressWarnings("rawtypes")
				List list = (List) tRoot.getChild("DTLS").getChildren("DTL");
				//�������׽ڵ��б�
				for (int i = 0; i < list.size(); i++)
				{
					//���׽ڵ�
					Element tDTL = (Element) list.get(i);
					//��ϸ�ڵ�
					Element tDetailEle = new Element(Detail);
					
					// �����ݿ��и���Ͷ�����Ų��������
					String tsql = "select c.contno from cont c where c.proposalprtno='" + tDTL.getChildText("APPNO") + "' and state='2'";
					String mContNo = new ExeSQL().getOneValue(tsql);
					Element tContNo = new Element(ContNo);
					tContNo.setText(mContNo);
					
					//����(��)
					String tPremFen=tDTL.getChildText("INSU_IN");
					Element tPremEle = new Element(Prem);
					tPremEle.setText(tPremFen);
					
					//�������
					Element tAgentCom = new Element(AgentCom);
					tAgentCom.setText(tDTL.getChildText("DEPT"));
					
					//Ͷ����(ӡˢ)��[�Ǳ���] 
					Element tProposalPrtNo = new Element(ProposalPrtNo);
					tProposalPrtNo.setText(tDTL.getChildText("APPNO"));
					
					//Ͷ��������[�Ǳ��룬��Щ���д�] 
					Element tAppntName = new Element("AppntName");
					tAppntName.setText(new ExeSQL().getOneValue("select appntname from cont where contno='" + mContNo + "'"));
					
					//����������[�Ǳ���]
					Element tInsuredName = new Element("InsuredName");
					tInsuredName.setText(new ExeSQL().getOneValue("select insuredname from cont where contno='" + mContNo + "'"));
					
					// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
					tDetailEle.addContent(tContNo).addContent(tPremEle)
					.addContent(tAgentCom).addContent(tProposalPrtNo)
					.addContent(tAppntName).addContent(tInsuredName);
					mBodyEle.addContent(tDetailEle);
					// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
					
					mCount++;
					mSumPrem += Long.parseLong(tPremFen);
				}
				mCountEle.setText(String.valueOf(mCount));
				mPremEle.setText(String.valueOf(mSumPrem));
				cLogger.info("�������ڣ�" + DateUtil.getCur8Date() + "�������ܽ�" + NumberUtil.fenToYuan(mSumPrem) + "������������" + String.valueOf(mCount));
			}
		}
		else
		{
			mCountEle.setText("0");
			mPremEle.setText("0");
		}
		mTranData.addContent(mBodyEle);
		
		cLogger.info("Out SpdbBusiBlc.parse()!");
		return new Document(mTranData);
	}

	@Override
	public Document getBalanceFile(Date cTranDate) throws Exception
	{
		String localDir = cThisBusiConf.getChildText("localDir") + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd");
		WtpDownloadFile download = new WtpDownloadFile();
		String downFileName = getFileName(cTranDate);
		int count = 1;
		download.setOverWrite(true); // Ĭ�ϸ���,
		download.setLocalFileDir(localDir);
		while (true)
		{
			if (download.downLoadFile("14db3d025f", downFileName) != 0)
			{
				cLogger.info("WTP����" + downFileName + "�ļ���" + count + "��ʧ��!");
				download.printErrorMsg();
				count++;
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if (count > 10)
				{
					break;
				}
			}
			else
			{
				cLogger.info("WTP����" + downFileName + "�ļ��ɹ���");
				break;
			}
		}

		cLogger.info("Java:TransId=[" + download.getTransId() + "]"); // ����ֵ
		cLogger.info("Java:LocalFileName=[" + download.getLocalFileName() + "]"); // ����ֵ

		InputStream ins = new FileInputStream(new File(localDir + "/" + getFileName(cTranDate)));
		return parse(ins);
	}

	@Override
	public void dealPrivateBusi(String cTranCom, Date cTransDate)
	{
	}

	public static void main(String[] args) throws Exception
	{
		SpdbBusiBlc tBlc = new SpdbBusiBlc();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));

		Logger mLogger = Logger.getLogger(SpdbBusiBlc.class);
		mLogger.info("�ַ����˳���ʼ...");

		// ���ڲ����ˣ����ò���������
		if (0 != args.length)
		{
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 *
			 * ������У���������ʽ��\\d{4}\\d{2}\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))"))
			{
				tBlc.setDate(args[0]);
				tBlc.run();
			}
			else
			{
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}
		mLogger.info("�ַ����˳ɹ�������");
	}
}