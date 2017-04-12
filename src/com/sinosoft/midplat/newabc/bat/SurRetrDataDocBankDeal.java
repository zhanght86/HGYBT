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

public class SurRetrDataDocBankDeal extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());
	private TranLogDB cTranLogDB;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	@SuppressWarnings("unused")
	private Document cOutXmlDoc;

	public void run() {
		cLogger.info("Into SurRetrDataDocBankDeal.run()...");
		try {
			cConfigEle = BatUtils.getConfigEle("2006"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			String mComCode = cConfigEle.getChildTextTrim("ComCode");
			String mFIleName = "INVALID.BANK" + mComCode + "." + cCurDate; // ��ʼ���ļ�����POLICY3002
			// //3002��˾��� + ��ǰʱ��
			if (!new BatUtils().downLoadFile(mFIleName, "02", "2006", cCurDate)) {
				throw new MidplatException("�˱��̳������ļ�-���д����������ļ�����ʧ��");
			}
			// �������
			cLogger.info("�˱��̳������ļ�-���д��������̿�ʼ...");
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
			cTranLogDB.setRText("�ɹ�");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  �ش����״����쳣...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		cLogger.info("�����˱��̳������ļ�-���д��������̽���!");
		cLogger.info("Out SurRetrDataDocBankDeal.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into SurRetrDataDocBankDeal.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2006");
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
		this.cLogger.debug("Out SurRetrDataDocBankDeal.insertTranLog()!");
		return mTranLogDB;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into SurRetrDataDocBankDeal.parse()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�BaseInfo�ڵ�����
		Element mTranData = new Element("TranData");
		Date cTranDate = new Date();
		String tBalanceFlag = "0";
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
		
		// ��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|�̳��ܼ�¼��|�̳��ܽ��|�����ܼ�¼��|�����ܽ��|�˱��ܼ�¼��|�˱��ܽ��
		// �ļ��������ݣ�����ϸ��¼��
		// ��˾����|ҵ��������|������|Ͷ��������|Ͷ����֤������|Ͷ����֤������|����|�����ļ�����|ҵ�����|��������|
		// ���д�����ˮ��|���չ�˾����״̬|���ж˱���״̬|������|������Ϣ|��ע1|
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		String mHeadMsgs = mBufReader.readLine();
		Element mCountEle = new Element(Count);
		Element mSumPremEle = new Element(Prem);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);

		if (!"3103".equals(mHeadMsgs.substring(0, 4)))
		{// ���������ȷ������Ϊ��¼�������ʱ��Ϊ������Ϣ
			cLogger.info(mHeadMsgs.substring(0, 4));
			String[] tHeadMsgs = mHeadMsgs.split("\\|", -1);
			cLogger.info(tHeadMsgs);
			mCountEle.setText(tHeadMsgs[2].trim());
			mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(tHeadMsgs[3].trim())));

			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
			{
				cLogger.info(tLineMsg);

				// ���У�ֱ������
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg))
				{
					cLogger.warn("���У�ֱ��������������һ����");
					continue;
				}

				String[] tSubMsgs = tLineMsg.split("\\|", -1);

				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tSubMsgs[1]);
				//
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tSubMsgs[10]);

				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[2]);

				Element tAppntName = new Element("AppntName");
				tAppntName.setText(tSubMsgs[3]);

				//������
				Element tHandleState = new Element("State");		
				if ("240000".equals(tSubMsgs[13]))
				{// ��������ɹ�
					tHandleState.setText("0");
				}
				else
				{// ��������ʧ��
					tHandleState.setText("1");
				}
			
				//����������
				Element msg = new Element("Msg");
				msg.setText(tSubMsgs[14]);

				Element tPremEle = new Element(Prem);
				long tPremFen = NumberUtil.yuanToFen(tSubMsgs[6]);
				tPremEle.setText(String.valueOf(tPremFen));

				Element tBusiType = new Element("BusiType");
				if ("01".equals(tSubMsgs[8]))
				{// �̳�
					tBusiType.setText("07");
				}
				else if ("02".equals(tSubMsgs[8]))
				{// ����
					tBusiType.setText("09");
				}
				else if ("03".equals(tSubMsgs[8]))
				{// �˱�
					tBusiType.setText("11");
				}

				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tBusiType);
				tDetailEle.addContent(tTranDateEle);
				tDetailEle.addContent(tAppntName);
				tDetailEle.addContent(tTranNoEle);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tHandleState);
				tDetailEle.addContent(tPremEle);

				mBodyEle.addContent(tDetailEle);
			}
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // �ر���
		}
		else
		{
			mCountEle.setText("0");
			mSumPremEle.setText("0");
		}
		cLogger.info("Out SurRetrDataDocBankDeal.parse()!");
		return new Document(mTranData);
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.SurRetrDataDocBankDeal.main");
		mLogger.info("��ũ���˱��̳������ļ�-���д��������̶��˳�������...");

		SurRetrDataDocBankDeal abcAES = new SurRetrDataDocBankDeal();

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
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyy-MM-dd��" + args[0]);
			}
		}
		abcAES.run();
		System.out.println("��ũ���˱��̳������ļ�-���д��������̶��˳������!");
	}

}
