package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.VData;

/**
 * @ClassName: NonReaTimeIssWatDetail
 * @Description: ��ʵʱ������ˮ��ϸ
 * @author sinosoft
 * @date 2017-4-7 ����2:09:59
 */
public class NonReaTimeIssWatDetail extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	private Document cOutXmlDoc;
	private TranLogDB cTranLogDB;

	public void run() {
		cLogger.info("Into NonReaTimeIssWatDetail.run()...");
		try {
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2002"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			String mComCode = cConfigEle.getChildTextTrim("ComCode").trim();

			String mFIleName = "FAPPLY" + mComCode + "." + cCurDate; // ��ʼ���ļ�����
			if (!new BatUtils().downLoadFile(mFIleName, "02", "2002", cCurDate)) {
				cTranLogDB.setRCode("1");
				cTranLogDB.setRText("��ʵʱ������ˮ��ϸ�ļ������쳣");
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
				throw new MidplatException("��ʵʱ������ˮ��ϸ�ļ������쳣");
			}

			//�������
			cLogger.info("�����ʵʱ������ˮ��ϸ�ļ���ʼ...");
			String myFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
//			String myFilePath = "D:/YBT_SAVE_XML/ZHH/newabc/BQAPPLY010079.20170405";
			cLogger.info(myFilePath);
			// �õ������׼����
			Document cInXmlDoc = parse(myFilePath);
			JdomUtil.print(cInXmlDoc);
			//���������ϸ
			saveDetails(cInXmlDoc);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContBlc).call(cInXmlDoc);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ʵʱ������ˮ��ϸ���˽�����룺" + reCode + "      ���˵����" + reMsg);
			if (cTranLogDB != null) {
				cTranLogDB.setRCode(reCode);
				cTranLogDB.setRText(reMsg);
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
			}
			
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  ���˴����쳣!");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText("��ʵʱ������ˮ��ϸ�ļ�����ʧ��");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		} finally {
			cCurDate = "";
		}
		cLogger.info("�����ʵʱ������ˮ��ϸ�ļ�����!");
		cLogger.info("Out NonReaTimeIssWatDetail.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into NonReaTimeIssWatDetail.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2002");
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
		this.cLogger.debug("Out NonReaTimeIssWatDetail.insertTranLog()!");
		return mTranLogDB;
	}

	public byte[] appendHeadBytes(byte[] mBodyBytes) {

		byte[] mInTotalBytes = new byte[mBodyBytes.length + 73];

		// ����xmlͷ��֮������
		String mBodyStr = new String(mBodyBytes);
		byte[] xBodyBytes = mBodyStr.substring(mBodyStr.indexOf("<ABCB2I>"),
				mBodyStr.length()).getBytes();
		String xBodyBytesLength = String.valueOf(xBodyBytes.length);
		System.arraycopy("X".getBytes(), 0, mInTotalBytes, 0,
				"X".getBytes().length);// ��������
		System.arraycopy("1.0".getBytes(), 0, mInTotalBytes, 1,
				"1.0".getBytes().length);// �汾��
		System.arraycopy(xBodyBytesLength.getBytes(), 0, mInTotalBytes, 4,
				xBodyBytesLength.length());// �����峤��
		byte[] mComCode = cConfigEle.getChildText("ComCode").trim().getBytes();
		System.arraycopy(mComCode, 0, mInTotalBytes, 12, mComCode.length);// ��˾����
		System.arraycopy("1".getBytes(), 0, mInTotalBytes, 20,
				"1".getBytes().length);// ���ܱ�ʾ 0-�����ܣ�1-�ؼ����ݼ��ܣ�2-����������ܡ�
		System.arraycopy("RSA".getBytes(), 0, mInTotalBytes, 21,
				"RSA".getBytes().length);// �����㷨
		System.arraycopy("0".getBytes(), 0, mInTotalBytes, 22,
				"0".getBytes().length);// ����ѹ����־ 0-��ѹ����1-ѹ��
		System.arraycopy(" ".getBytes(), 0, mInTotalBytes, 23,
				" ".getBytes().length);// ����ѹ���㷨
		System.arraycopy(" ".getBytes(), 0, mInTotalBytes, 24,
				" ".getBytes().length);// ժҪ�㷨
		System.arraycopy(" ".getBytes(), 0, mInTotalBytes, 25,
				" ".getBytes().length);// ժҪ
		System.arraycopy("00000000".getBytes(), 0, mInTotalBytes, 65,
				"00000000".getBytes().length);// Ԥ���ֶ�

		System.arraycopy(xBodyBytes, 0, mInTotalBytes, 73, xBodyBytes.length);// Ԥ���ֶ�
		return mInTotalBytes;
	}

	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NonReaTimeIssWatDetail.parse()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�BaseInfo�ڵ�����
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		Date cTranDate = new Date();
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" ��������Ϊ..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" ��ǰ����Ϊ..."+mCurrDate);
		
		// ���ֹ����ˣ���tBalanceFlag��־��Ϊ1 �����ն�����Ϊ0 modify by liuq 2010-11-11
		if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
			tBalanceFlag = "1";
		}
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));
		
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText("node"));
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(funcFlag);
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		//����ͷ������Ӻ��ĵ����б���
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
		
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);
		//��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|
		//�ļ��������ݣ�����ϸ��¼��
		//��������|��������˳���|Ͷ��������|Ͷ����֤������|Ͷ����֤������|���ֱ���|��Ʒ����|Ͷ������|����|���Ի�����|�˺�|�绰����|�ֻ�����|��ַ|��������|����|ʡ�д���|�����|
		System.out.println(pBatIs);
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[2].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[3].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
//			if (!"01".equals(tSubMsgs[10])) {
//				cLogger.warn("�ǳб�������ֱ��������������һ����");
//				continue;
//			}
//			if (!("01".equals(tSubMsgs[11])&&("0000".equals(tSubMsgs[12])))) {
//				cLogger.warn("�б������������򳷵��ĵ��ӣ���ֱ��������������һ����");
//				continue;
//			}
			
			/*
			 * ũ�е�ʵʱ�ĵ�������4λ�ģ�����ʡ�д���+2λ�����룩�����˵ĵ�������2λ�ģ����Զ��˵ĵ����뻹Ҫƴ��ʡ�����д��벿��
			 *    
			 * ������ʱ���ũ�е���Աȷ�ϵģ�20130403
			 * 
			 * ��������|���н�����ˮ��|����ʡ�д���|�������|������|���׽��|��������|����״̬
			 */
			
			//�����������
			Element tApplyNoEle = new Element("ApplyNo");
			tApplyNoEle.setText(tSubMsgs[1]);
			
			//Ͷ��������
			Element tAppNameEle = new Element("AppntName");
			tAppNameEle.setText(tSubMsgs[2]);
			
			//Ͷ����֤������
			Element tIDTypeEle=new Element(IDType);
			tIDTypeEle.setText(tSubMsgs[3]);
			
			//֤������
			Element tIDNoEle=new Element(IDNo);
			tIDNoEle.setText(tSubMsgs[4]);
			
			//���ִ���-
			Element tRiskCodeEle=new Element(RiskCode);
			tRiskCodeEle.setText(tSubMsgs[5]);
			
			//��Ʒ����
			Element tProdCodeEle =new Element("ProdCode");
			tProdCodeEle.setText(tSubMsgs[6]);
			
			//Ͷ������
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[7]);
			
			//����
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[8]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			//����
			Element tSpecialRateEle= new Element("SpecialRate");
			tSpecialRateEle.setText(tSubMsgs[9]);
			
			//�˺�
			Element tAccNoEle=new Element("AccNo");
			tAccNoEle.setText(tSubMsgs[10]);
			
			//�绰����
			Element tPhoneEle=new Element("Phone");
			tPhoneEle.setText(tSubMsgs[11]);
			
			//�ֻ�����
			Element tMobileEle=new Element("Mobile");
			tMobileEle.setText(tSubMsgs[12]);
			
			//��������
			String nodeNo=null;
			if(tSubMsgs[16]!=null&&tSubMsgs[17]!=null){
				nodeNo=tSubMsgs[16].trim()+tSubMsgs[17].trim();
			}
			Element tNodeNo = new Element("NodeNo");
			tNodeNo.setText(nodeNo);
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);
			
			//��ʵʱ��������ͨ��֪�������ţ�����ڲ��������ϸ���ʱ����Ͷ�����Ų��뱣����20141012
 			Element tContNo=new Element(ContNo);
 			tContNo.setText(tSubMsgs[7]);
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tApplyNoEle);
			tDetailEle.addContent(tAppNameEle);
			tDetailEle.addContent(tIDTypeEle);
			tDetailEle.addContent(tIDNoEle);
			tDetailEle.addContent(tRiskCodeEle);
			tDetailEle.addContent(tProdCodeEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tPremEle);
			tDetailEle.addContent(tSpecialRateEle);
			tDetailEle.addContent(tAccNoEle);
			tDetailEle.addContent(tPhoneEle);
			tDetailEle.addContent(tMobileEle);
			
			tDetailEle.addContent(tNodeNo);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tContNo);
			mBodyEle.addContent(tDetailEle);
		}
		mTranData.addContent(mBodyEle);
		mBufReader.close();	//�ر���

		cLogger.info("Out NonReaTimeIssWatDetail.parse()!");
		return new Document(mTranData);
	}

	/** 
	 * ���������ϸ�����ر������ϸ����(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into NonReaTimeIssWatDetail.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
	//	long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		if (mDetailList.size() != mCount) {
			throw new MidplatException("���ܱ�������ϸ����������"+ mCount + "!=" + mDetailList.size());
		}
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList) {
		//	mSumDtlPrem += Integer.parseInt(tDetailEle.getChildText(Prem));
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));
			
			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));	//��Щ���д�
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			tContBlcDtlSchema.setPrem(tDetailEle.getChildText(Prem));
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName"));	//��Щ���д�
			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); //��Щ���д�
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			tContBlcDtlSchema.setBak1(tDetailEle.getChildText("ApplyNo"));
			
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		if (mSumPrem != mSumDtlPrem) {
			throw new MidplatException("���ܽ������ϸ�ܽ�����"+ mSumPrem + "!=" + mSumDtlPrem);
		}
		
		/** 
		 * �����з������Ķ�����ϸ�洢��������ϸ��(ContBlcDtl)��
		 */
		cLogger.info("������ϸ����(DtlSet)Ϊ��" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			cLogger.error("���������ϸʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("���������ϸʧ�ܣ�");
		}
		
		cLogger.debug("Out NonReaTimeIssWatDetail.saveDetails()!");
		return mContBlcDtlSet;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NonReaTimeIssWatDetail.main");
		mLogger.info("��ũ�з�ʵʱ������ˮ��ϸ���˳�������...");

		NonReaTimeIssWatDetail abcAES = new NonReaTimeIssWatDetail();

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
		System.out.println("��ũ�з�ʵʱ������ˮ��ϸ���˳������!");
	}

}
