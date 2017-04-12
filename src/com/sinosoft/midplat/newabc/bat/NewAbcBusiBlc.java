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
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;

/**
 * @ClassName: NewAbcBusiBlc
 * @Description: ��ũ���µ�����
 * @author sinosoft
 * @date 2017-4-7 ����12:07:19
 */
public class NewAbcBusiBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private static String cCurTime="";
	@SuppressWarnings("unused")
	private String mFileData = "";
	private TranLogDB cTranLogDB;
	private Document cOutXmlDoc;

	public void run() {
		cLogger.info("Into NewAbcBusiBlc.run()...");
		try {
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2001"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				cCurTime=new SimpleDateFormat("HHmmss").format(new Date());
			}
			String mCorNo = cConfigEle.getChildTextTrim("ComCode").trim();
			// ��ʼ���ļ�����		POLICY 	+��˾���	+.		+��ǰʱ��
			String mFIleName = "POLICY" + mCorNo + "." + cCurDate; 
			if (!new BatUtils().downLoadFile(mFIleName, "02", "2001", cCurDate)) {
				throw new MidplatException("ũ���±��б����������ļ������쳣");
			}
			// �������
			cLogger.info("����ũ���µ����˿�ʼ...");
			// �õ������׼����
			String myFilePath =cConfigEle.getChildTextTrim("FilePath")+mFIleName;
//			String myFilePath = "D:/YBT_SAVE_XML/ZHH/newabc/POLICY010079.20170405";
			System.out.println(myFilePath);
			Document mInStd = parse(myFilePath);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
			cLogger.info("����ũ���µ����˽���!");

		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  �����쳣..."
					+ e.getMessage());
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		cLogger.info("Out NewAbcBusiBlc.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into NewAbcBusiBlc.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2001");
		mTranLogDB.setOperator("YBT");
		mTranLogDB.setTranNo(NoFactory.nextTranLogNo() + "");
		mTranLogDB.setTranDate(DateUtil.getCur8Date());
		mTranLogDB.setTranTime(DateUtil.getCur6Time());
		Date mCurDate = new Date();
		mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
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
		this.cLogger.debug("Out NewAbcBusiBlc.insertTranLog()!");
		return mTranLogDB;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NewAbcBusiBlc.parse()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�Head�ڵ�����
		Element mTranData = new Element("TranData");
		//���˱�־
		String tBalanceFlag = "0";
		Date cTranDate = new Date();
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
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
		//�½�ʡ�д���ڵ�
		Element mZoneNo = new Element("ZoneNo");
		//�����ı�Ϊ��ǰ�������ýڵ���ʡ�д����ӽڵ��ı�
		mZoneNo.setText(cConfigEle.getChildText("zone"));

		//�½���������ڵ�
		Element mNodeNo = new Element(NodeNo);
		//�����ı�Ϊ��ǰ�������ýڵ������������ӽڵ��ı�
		mNodeNo.setText(cConfigEle.getChildText("node"));

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
		//�����ı�Ϊ��ʱ����
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));

		//�½����˱�־�ڵ�
		Element mBalanceFlag = new Element("BalanceFlag");
		//�����ı�Ϊ���˱�־
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

		//����GBK�ַ����� InputStreamReader,ʹ��Ĭ�ϴ�С���뻺�����Ļ����ַ�������
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		try{
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
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // �ر���		
			
			cLogger.info("Out NewAbcBusiBlc.parse()!");
			return new Document(mTranData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("���������ļ�ʧ�ܣ�" + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NewAbcBusiBlc.main");
		mLogger.info("��ũ���µ����˳�������...");

		NewAbcBusiBlc abcAES = new NewAbcBusiBlc();

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
		System.out.println("��ũ���µ����˳������!");
	}

}
