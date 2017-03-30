/**
 * ũ������Լ����
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.service.Service;

/**
 * @ClassName: NewAbcBusiBlc
 * @Description: ��ũ���µ�����
 * @author sinosoft
 * @date 2017-2-27 ����10:39:03
 */
public class NewAbcBusiBlc extends Balance
{

	//����һ���������־����
	protected final Logger cLogger = Logger.getLogger(getClass());

	/**
	 * <p>Title: ��ũ���µ����������޲ι���</p>
	 * <p>Description: </p>
	 */
	public NewAbcBusiBlc()
	{
		super(NewAbcConf.newInstance(), "2001");
	}

	/**
	 * ��ȡ�ϴ������ļ���
	 * @return 
	 */
	protected String getFileName() throws Exception
	{
		//��ȡ��ǰ���н��������ļ����ڵ��������ӽڵ�
		Element mBankEle = cThisConfRoot.getChild("bank");
		//�½��ļ���������ʵ��[��ǰ�������ýڵ�,������,8λ���������ַ���,���нڵ�insu����ֵ]
		File_download f = new File_download(cThisBusiConf, "RZDZ", DateUtil.getDateStr(cTranDate, "yyyyMMdd"), mBankEle.getAttributeValue("insu"));
		//��ȡ�ļ���[POLICY���չ�˾����.8λ���������ַ���]
		String fileName = "POLICY" + mBankEle.getAttributeValue("insu") + "." + DateUtil.getDateStr(cTranDate, "yyyyMMdd");
		try
		{
			//�ϴ����������ļ�
			f.bank_dz_file();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new MidplatException(ex.getMessage());
		}
		//�����ļ���[POLICY���չ�˾����.8λ���������ַ���]
		return fileName;
	}

	/**
	 * ����
	 */
	public void run()
	{
		//���õ�ǰ�߳�����Ϊ��һ��������־�� 
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		//Into NewAbcBusiBlc.run()...
		this.cLogger.info("Into NewAbcBusiBlc.run()...");
		
		//������Ϣ��Ϊ��
		this.cResultMsg = null;
		try
		{
			//��ȡ�м�ƽ̨�����ļ����ڵ�
			this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			//��ȡ��ǰ���н��������ļ����ڵ�
			this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
			//��ȡ��ǰ�������ýڵ�[ѡ��ǰ���н��������ļ����ڵ���business�ӽڵ�funcFlag����Ϊ ������ĵ����ڵ�]
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
			//��ȡ��һ����[��ǰ�������ýڵ���nextDate�ӽڵ��ı�]
			String nextDate = this.cThisBusiConf.getChildText("nextDate");
			
			//��������Ϊ��
			if (this.cTranDate == null)
				//��һ���ڷǿ���ΪY
				if ((nextDate != null) && ("Y".equals(nextDate)))
				{
					//�������ڸ�ֵΪ��ǰ���ڶ���
					this.cTranDate = new Date();
					//�������ڸ�ֵΪ����
					this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
				}
				else
				{
					//�������ڸ�ֵΪ��ǰ���ڶ���
					this.cTranDate = new Date();
				}
			//�½����ڵ�
			Element tTranData = new Element("TranData");
			//�½���׼���뱨��
			Document tInStdXml = new Document(tTranData);
			
			//��ȡ��׼���뱨��ͷ
			Element tHeadEle = getHead();
			//���ڵ�����׼���뱨��ͷ
			tTranData.addContent(tHeadEle);
			try
			{
				//��ȡ�ϴ������ļ���
				String ttFileName = getFileName();
				//FileName = �ϴ������ļ���
				this.cLogger.info("FileName = " + ttFileName);
				//����Ŀ¼�ַ���
				String ttLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
				//localDir = ����Ŀ¼�ַ���
				this.cLogger.info("localDir = " + ttLocalDir);
				//�����ֽ���
				InputStream ttBatIns = null;
				//����Ŀ¼�ϴ������ļ�������
				ttBatIns = new FileInputStream(ttLocalDir + ttFileName);
				
				//
				Element ttBodyEle = parse(ttBatIns);
				tTranData.addContent(ttBodyEle);
			}
			catch (Exception ex)
			{
				this.cLogger.error("���ɱ�׼���˱��ĳ���!", ex);

				Element ttError = new Element("Error");
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}

			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";

			String tServiceValue = this.cMidplatRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}

			tServiceValue = this.cThisConfRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			tServiceValue = this.cThisBusiConf.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			this.cLogger.info("ҵ����ģ��" + tServiceClassName);
			@SuppressWarnings("rawtypes")
			Constructor tServiceConstructor = Class.forName(tServiceClassName).getConstructor(new Class[] { Element.class });
			Service tService = (Service) tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);

			this.cResultMsg = tOutStdXml.getRootElement().getChild("Head").getChildText("Desc");

		}
		catch (Throwable ex)
		{
			this.cLogger.error("���׳���", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out NewAbcBusiBlc.run()!");
	}

	/**
	 * ת���ļ�������Ϊ������
	 * @return pBatIs ����Ŀ¼�ϴ������ļ�������
	 * @return ��׼���뱨����
	 */
	protected Element parse(InputStream pBatIs) throws Exception
	{
		//����NewAbcBusiBlcת���ļ�������Ϊ�����巽��...
		cLogger.info("Into NewAbcBusiBlc.parse()...");
		
		//�ַ���[��ǰ�������ýڵ����ַ����ӽڵ��ı�]
		String mCharset = cThisBusiConf.getChildText(charset);
		//�ַ����ǿա����ַ���
		if (null == mCharset || "".equals(mCharset))
		{
			//��ΪGBK
			mCharset = "GBK";
		}
		// ��ʽ�����չ�˾����|�ܼ�¼��|�ܽ��|�ɹ��ܼ�¼��|�ɹ��ܽ��
		// �ļ��������ݣ�����ϸ��¼��
		// ��������|���н�����ˮ��|����ʡ�д���|�������|������|���׽��|��������|����״̬
		//�������Ŀ¼�ϴ������ļ�������
		System.out.println(pBatIs);
		//����GBK�ַ����� InputStreamReader,ʹ��Ĭ�ϴ�С���뻺�����Ļ����ַ�������
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		//�����ַ���������ȡһ�и���ƥ�������������ʽ����ִ��ַ�����
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		// �ѳɹ��ļ�¼��ȡ������������
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[3].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[4].trim())));

		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
		{
			// cLogger.info("���Ǹ�ô������"+tLineMsg);

			// ���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg))
			{
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}

			String[] tSubMsgs = tLineMsg.split("\\|", -1);

			if (!"01".equals(tSubMsgs[6]))
			{
				cLogger.warn("�ǳб�������ֱ��������������һ����");
				continue;
			}
			if (!("01".equals(tSubMsgs[7])))
			{
				cLogger.warn("�б������������򳷵��ĵ��ӣ���ֱ��������������һ����");
				continue;
			}

			/*
			 * ũ�е�ʵʱ�ĵ�������4λ�ģ�����ʡ�д���+2λ�����룩�����˵ĵ�������2λ�ģ����Զ��˵ĵ����뻹Ҫƴ��ʡ�����д��벿��
			 * 
			 * ������ʱ���ũ�е���Աȷ�ϵģ�20130403
			 * 
			 * ��������|���н�����ˮ��|����ʡ�д���|�������|������|���׽��|��������|����״̬
			 */
			String nodeNo = null;
			if (tSubMsgs[2] != null && tSubMsgs[3] != null)
			{
				nodeNo = tSubMsgs[2].trim() + tSubMsgs[3].trim();
			}

			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);

			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[1]);

			//���յ���
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[4]);
			
			//����(��)
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[5]);
			tPremEle.setText(String.valueOf(tPremFen));

			//�������
			Element tAgentComEle = new Element(AgentCom);
			tAgentComEle.setText(nodeNo);

			//Ͷ����(ӡˢ)��[�Ǳ���]
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			
			//Ͷ��������[�Ǳ��룬��Щ���д�]
			Element tAppntNameEle=new Element("AppntName");
			
			//����������[�Ǳ���]
			Element tInsuredNameEle=new Element("InsuredName");
			
			/*
			 * Element tContTypeEle = new Element("ContType"); if
			 * (!(tSubMsgs[8].trim()).endsWith("88")) {
			 * tContTypeEle.setText(String
			 * .valueOf(HxlifeCodeDef.ContType_Group)); } else {
			 * tContTypeEle.setText
			 * (String.valueOf(HxlifeCodeDef.ContType_Bank)); }
			 */

			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);
			tDetailEle.addContent(tAgentComEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tAppntNameEle);
			tDetailEle.addContent(tInsuredNameEle);

			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close(); // �ر���

		cLogger.info("Out NewAbcBusiBlc.parse()!");
		return mBodyEle;
	}

	/**
	 * ��ȡ��׼���뱨��ͷ
	 */
	protected Element getHead()
	{
		//����NewAbcBusiBlc��ȡ����ͷ����...
		cLogger.info("Into NewAbcBusiBlc.getHead()...");
		//���˱�־
		String tBalanceFlag = "0";
		//�½��������ڽڵ�
		Element mTranDate = new Element(TranDate);
		//�������ڽڵ������ı�Ϊ8λ�����ַ���
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		//��ȡ��ǰ�����ַ���
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		// ��������Ϊ...8λ���������ַ���
		cLogger.info(" ��������Ϊ..." + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		// ��ǰ����Ϊ...��ǰ�����ַ���
		cLogger.info(" ��ǰ����Ϊ..." + mCurrDate);

		// ���ֹ����ˣ���tBalanceFlag��־��Ϊ1 �����ն�����Ϊ0 modify by liuq 2010-11-11
		//8λ���������ַ����ǵ�ǰ�����ַ���
		if (!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate))
		{
			//���˱�־��Ϊ1[�ֹ�����]
			tBalanceFlag = "1";
		}

		//�½�����ʱ��ڵ�
		Element mTranTime = new Element(TranTime);
		//����ʱ��ڵ������ı�Ϊ6λ����ʱ���ַ���
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		//�½����׻�������ڵ�
		Element mTranCom = new Element(TranCom);
		//���׻�������ڵ������ı�Ϊ��ǰ���н��������ļ����ڵ��½��׻��������ӽڵ��ı�
		mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
		//��ʱ�ַ���Ϊ���׻��������ӽڵ�outcode����ֵ
		String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
		//��ʱ�ַ����ǿա����ַ���
		if (null != tTempStr && !"".equals(tTempStr))
		{
			//���׻�������ڵ���������outcodeΪ�丳ֵΪ��ʱ�ַ���
			mTranCom.setAttribute(outcode, tTempStr);
		}
		
		//�½�ʡ�д���ڵ�
		Element mZoneNo = new Element("ZoneNo");
		//�����ı�Ϊ��ǰ�������ýڵ���ʡ�д����ӽڵ��ı�
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));

		//�½���������ڵ�
		Element mNodeNo = new Element(NodeNo);
		//�����ı�Ϊ��ǰ�������ýڵ������������ӽڵ��ı�
		mNodeNo.setText(cThisBusiConf.getChildText("node"));

		//�½���Ա����ڵ�
		Element mTellerNo = new Element(TellerNo);
		//�����ı�Ϊsys
		mTellerNo.setText("sys");
		
		//�½�������ˮ�Žڵ�
		Element mTranNo = new Element(TranNo);
		//�����ı�Ϊ��ǰ�߳���
		mTranNo.setText(Thread.currentThread().getName());
		
		//�½��������ͽڵ�
		Element mFuncFlag = new Element(FuncFlag);
		//��ʱ����Ϊ��ǰ�������ýڵ��½��������ӽڵ�outcode����ֵ
		tTempStr = cThisBusiConf.getChild(funcFlag).getAttributeValue(outcode);
		//�����ı�Ϊ��ʱ����
		mFuncFlag.setText(tTempStr);

		//�½����˱�־�ڵ�
		Element mBalanceFlag = new Element("BalanceFlag");
		//�����ı�Ϊ���˱�־
		mBalanceFlag.setText(tBalanceFlag);
		
		// ����ͷ������Ӻ��ĵ����б���
		//�½����д���ڵ�
		Element mBankCode = new Element("BankCode");
		//�����ı�Ϊ0102
		mBankCode.setText("0102");
		
		//�½�����ͷ�ڵ�
		Element mHead = new Element(Head);
		/**
		 * ���뽻�����ڡ�����ʱ�䡢���д��롢���׻������롢ʡ�д��롢
		 * �������㡢��Ա���롢������ˮ�š��������͡����˱�־�ڵ�
		 **/
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);

		// ����ͷ������Ӻ��ĵ����б���
		mHead.addContent(mBankCode);

		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBalanceFlag);
		
		//��NewAbcBusiBlc��ȡ����ͷ��������!
		cLogger.info("Out NewAbcBusiBlc.getHead()!");
		return mHead;
	}

	public static void main(String[] args) throws Exception
	{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.NewAbcBusiBlc.main");
		mLogger.info("����ʼ...");

		NewAbcBusiBlc mBatch = new NewAbcBusiBlc();
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
				mBatch.setDate(args[0]);
			}
			else
			{
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}

		mBatch.run();

		mLogger.info("�ɹ�������");
	}
}
