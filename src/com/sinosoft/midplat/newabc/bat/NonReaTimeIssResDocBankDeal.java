package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;

public class NonReaTimeIssResDocBankDeal extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	@SuppressWarnings("unused")
	private Document cOutXmlDoc;
	private TranLogDB cTranLogDB;

	public void run() {
		cLogger.info("Into NonReaTimeIssResDocBankDeal.run()...");
		try {
			cConfigEle = BatUtils.getConfigEle("2007"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			String mComCode = cConfigEle.getChildTextTrim("ComCode");
			String mFIleName = "FRESULT.BANK" + mComCode + "." + cCurDate; // ��ʼ���ļ�����POLICY3002
			// //3002��˾��� + ��ǰʱ��
			if (!new BatUtils()
					.downLoadFile(mFIleName, "02", "2007", cCurDate)) {
				cTranLogDB.setRCode("1");
				cTranLogDB.setRText("��ʵʱ��������ļ�-���д����������ļ�����ʧ��");
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
				throw new MidplatException("��ʵʱ��������ļ�-���д����������ļ�����ʧ��");
			}
			// �������
			cLogger.info("�����ʵʱ��������ļ�-���д��������̿�ʼ...");
			// �õ������׼����
			cTranLogDB = insertTranLog();
			/*String myFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
//			String myFilePath = "C:\\Users\\chenjinwei\\Desktop\\BQAPPLY1132.20161130";
			Document mInStd = parse(myFilePath);
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc("").call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("BaseInfo")
					.getChildText("ResultCode");
			String reMsg = cOutXmlDoc.getRootElement().getChild("BaseInfo")
					.getChildText("ResultMsg");
			cLogger.info("��ȫ���˽�����룺"+reCode+"      ���˵����"+reMsg);*/
			cTranLogDB.setRCode("0");
			cTranLogDB.setRText("���׳ɹ�");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  �����쳣...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText("����ʧ��");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		cLogger.info("�����ʵʱ��������ļ�-���д��������̽���...");
		cLogger.info("Out onReaTimeIssResDocBankDeal.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into NonReaTimeIssResDocBankDeal.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2007");
		mTranLogDB.setOperator("YBT");
		mTranLogDB.setTranNo(NoFactory.nextTranLogNo() + "");
		mTranLogDB.setTranDate(DateUtil.getCur8Date());
		mTranLogDB.setTranTime(DateUtil.getCur6Time());
		Date mCurDate = new Date();
		mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setRCode(0);
		mTranLogDB.setUsedTime(0);
		mTranLogDB.setBak1("");
		mTranLogDB.setRCode("-1");
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!(mTranLogDB.insert())) {
			this.cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}
		this.cLogger.debug("Out NonReaTimeIssResDocBankDeal.insertTranLog()!");
		return mTranLogDB;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NonReaTimeIssResDocBankDeal.parse()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�BaseInfo�ڵ�����
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		Date cTranDate = new Date();
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" ��������Ϊ..." + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" ��ǰ����Ϊ..." + mCurrDate);

		// ���ֹ����ˣ���tBalanceFlag��־��Ϊ1 �����ն�����Ϊ0 modify by liuq 2010-11-11
		if (!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate))
		{
			tBalanceFlag = "1";
		}

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));

		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText("NodeNo"));

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));

		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);

		// ����ͷ������Ӻ��ĵ����б���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(NewAbcConf.newInstance().getConf().getRootElement().getChildText("BankCode"));

		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mBankCode).addContent(mTranCom)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag).addContent(mBalanceFlag);
		mTranData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		
		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);
		
		// ��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|
		// �ļ��������ݣ�����ϸ��¼��
		// ���չ�˾����|��������|��������˳���|Ͷ��������|Ͷ����֤������|Ͷ����֤������|���ֱ���|��Ʒ����|������|�б�����|
		// Ͷ�����˹�ϵ|����������|������֤������|������֤������|����|����|�ɷ��˻�|�ɷѷ�ʽ|�ɷ�����|����������|Ͷ������|
		// ���Ի�����|����ӡˢ��|������|������Ϣ|��ע1|��ע2|
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		// �ѳɹ��ļ�¼��ȡ������������
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[2].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[3].trim())));

		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
		{
			cLogger.info(tLineMsg);

			String[] tSubMsgs = tLineMsg.split("\\|", -1);

			if (!"3103".equals(tSubMsgs[0]))
			{// ���������ȷ������Ϊ��¼�������ʱ��ڶ���Ϊ������Ϣ

				// ���У�ֱ������
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg))
				{
					cLogger.warn("���У�ֱ��������������һ����");
					continue;
				}
				
				Element tDetailEle = new Element(Detail);

				//����������ִ������Ϊ��������
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				tDetailEle.addContent(tTranDateEle);

				// ������
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[8]);
				tDetailEle.addContent(tContNoEle);
				
				//��������˳���
				Element tApplyNo = new Element("ApplyNo");
				tApplyNo.setText(tSubMsgs[2]);
				tDetailEle.addContent(tApplyNo);

				//����
				Element tPremEle = new Element(Prem);
				long tPremFen = NumberUtil.yuanToFen(tSubMsgs[14]);
				tPremEle.setText(String.valueOf(tPremFen));
				tDetailEle.addContent(tPremEle);
				
				//Ͷ�������� 
				Element appntName = new Element("AppntName");
				appntName.setText(tSubMsgs[3]);
				tDetailEle.addContent(appntName);
				
				//���������� 
				Element insuredName = new Element("InsuredName");
				insuredName.setText(tSubMsgs[11]);
				tDetailEle.addContent(insuredName);
				
				//������
				Element dealCode = new Element("State");
				dealCode.setText(tSubMsgs[23]);
				tDetailEle.addContent(dealCode);
				
				//������Ϣ
				Element msg = new Element("Msg");
				msg.setText(tSubMsgs[24]);
				tDetailEle.addContent(msg);
				
				mBodyEle.addContent(tDetailEle);
			}
			else
			{
				mCountEle.setText("0");
				mSumPremEle.setText("0");
			}

		}
		mTranData.addContent(mBodyEle);
		mBufReader.close(); // �ر���

		cLogger.info("Out NonReaTimeIssResDocBankDeal.parse()!");
		return new Document(mTranData);
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NonReaTimeIssResDocBankDeal.main");
		mLogger.info("��ũ�з�ʵʱ��������ļ�-���д��������̶��˳�������...");

		NonReaTimeIssResDocBankDeal abcAES = new NonReaTimeIssResDocBankDeal();

		// ���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 * 
			 * ������У���������ʽ��\\d{4}-\\d{2}-\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}
		abcAES.run();
		mLogger.info("��ũ�з�ʵʱ��������ļ�-���д��������̶��˳������!");
	}

}
