package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class SecuTradAppDoc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	private Document cOutXmlDoc;
	private TranLogDB cTranLogDB;
	String cAppType = null;// 48-��������

	public void run() {
		cLogger.info("Into SecuTradAppDoc.run()...");
		try {

			cConfigEle = BatUtils.getConfigEle("2003"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}

			String mCorNo = cConfigEle.getChildTextTrim("ComCode");
			String mFIleName = "BQAPPLY" + mCorNo + "." + cCurDate; // ��ʼ���ļ�����POLICY3002
			// //3002��˾��� + ��ǰʱ��
			// // ��֯Document
			 if(!new BatUtils().downLoadFile(mFIleName,"02","2003",cCurDate)){
				 throw new MidplatException("��ȫ������������ļ�����ʧ��!");
			 }
			// �������
			cLogger.info("����ȫ������������ļ���ʼ...");
			// �õ������׼����
			//
			cTranLogDB = insertTranLog();
			 String myFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
//			String myFilePath = "D:/YBT_SAVE_XML/ZHH/newabc/BQAPPLY010079.20170405";
			Document mInStd = parse(myFilePath, "YBT");

			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ȫ���˽�����룺" + reCode + "      ���˵����" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
			// =========================��ȫ������������=========================
			/*Thread.sleep(5000);
			cTranLogDB = insertTranLog();
			mInStd = parseWY(myFilePath, "ABCWEB");
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ȫ���˽�����룺" + reCode + "      ���˵����" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();*/
			// =========================��ȫ�����������˽���=========================
			// =========================��ȫ�����ն���������=========================
			/*Thread.sleep(5000);
			cTranLogDB = insertTranLog();
			mInStd = parseZD(myFilePath, "ABCWEB");
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ȫ���˽�����룺" + reCode + "      ���˵����" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();*/
			// =========================��ȫ�����ն��������˽���=========================
			// =========================��ȫ�ֻ�������������============================
			/*Thread.sleep(5000);
			cTranLogDB = insertTranLog();
			mInStd = parseSJ(myFilePath, "ABCWEB");
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_BQContBlc).call(mInStd);
			reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ȫ���˽�����룺" + reCode + "      ���˵����" + reMsg);
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();*/
			// =========================��ȫ�ֻ������������˽���===========================
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  ���˴����쳣...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();

		} finally {
			cCurDate = "";
		}
		cLogger.info("����ȫ������������ļ�����!");
		cLogger.info("Out SecuTradAppDoc.run()!");
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		this.cLogger.debug("Into SecuTradAppDoc.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2003");
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
		this.cLogger.debug("Out SecuTradAppDoc.insertTranLog()!");
		return mTranLogDB;
	}

	// ����������ȫ����
	protected Document parse(String nFileURL, String channel) throws Exception {
		cLogger.info("Into SecuTradAppDoc.parse()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�BaseInfo�ڵ�����
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		Date cTranDate = new Date();
		cLogger.info(" ��������Ϊ..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" ��ǰ����Ϊ..."+mCurrDate);
		
		// ���ֹ����ˣ���tBalanceFlag��־��Ϊ1 �����ն�����Ϊ0 modify by liuq 2010-11-11
		if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
			tBalanceFlag = "1";
		}
		
		//��������
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		//����ʱ��
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		//���׻�������(����/ũ����/������˾)
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("tranCom"));
		
		//���д���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0102");
		
		//��������
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cConfigEle.getChildText("zone"));
		
		//��������
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText("node"));
		
		//��Ա����
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		//������ˮ��
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		//��������
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate).addContent(mTranTime)
		.addContent(mTranCom).addContent(mBankCode)
		.addContent(mZoneNo).addContent(mNodeNo)
		.addContent(mTellerNo).addContent(mTranNo)
		.addContent(mFuncFlag).addContent(mBalanceFlag);
		mTranData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);
		// ��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|�̳��ܼ�¼��|�̳��ܽ��|�����ܼ�¼��|�����ܽ��|�˱��ܼ�¼��|�˱��ܽ��|
		//�ļ��������ݣ�����ϸ��¼��
		// ��˾����|ҵ�����|��������|������|֤������|֤������|����������|����|����״̬|ʡ�д���|�����|Ͷ������|��ȡ���|
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));
		
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
			
			//ҵ�����
			Element tBusiType = new Element("BusiType");
			if("01".equals(tSubMsgs[1])){//�̳�
				tBusiType.setText("07");
			}
			if("02".equals(tSubMsgs[1])){//����
				tBusiType.setText("09");
			}
			if("03".equals(tSubMsgs[1])){//�˱�
				tBusiType.setText("11");
			}
			
			//��������
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[2]);
			
			//������
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[3]);
			
			//����������
			Element tAppntNameEle=new Element("AppntName");
			
			//Ͷ����֤������
			Element tIDTypeEle=new Element(IDType);
			
			//֤������
			Element tIDNoEle=new Element(IDNo);
			
			//Ͷ������
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[11]);
			
			//���
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[12]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			//ʡ����
			Element tZoneNo=new Element("ZoneNo");
			tZoneNo.setText(tSubMsgs[9].trim());
			
			//�������
			String nodeNo=null;
			if(tSubMsgs[9]!=null&&tSubMsgs[10]!=null){
				nodeNo=tSubMsgs[9].trim()+tSubMsgs[10].trim();
			}
			Element tNodeNo=new Element("NodeNo");
			tNodeNo.setText(nodeNo);
			
			
			Element tAgentCom=new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			
			/*Element tContTypeEle = new Element("ContType");
			if (!(tSubMsgs[8].trim()).endsWith("88")) {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Group));
			} else {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Bank));
			}*/
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tBusiType);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tAppntNameEle);
			tDetailEle.addContent(tIDTypeEle);
			tDetailEle.addContent(tIDNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tPremEle);
			tDetailEle.addContent(tZoneNo);
			tDetailEle.addContent(tNodeNo);
			
			tDetailEle.addContent(tAgentCom);
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close();	//�ر���

		mTranData.addContent(mBodyEle);
		// TransData.addContent(getHead());
		cLogger.info("Out SecuTradAppDoc.parse()!");
		return new Document(mTranData);
	}

	// ����������ȫ����
	protected Document parseWY(String nFileURL, String channel)
			throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�BaseInfo�ڵ�����
		Element TransData = new Element("TransData");
		Element BaseInfo = new Element("BaseInfo");
		Element TransType = new Element("TransType");
		TransType.setText("02");
		Element TransCode = new Element("TransCode");
		TransCode.setText("020019");
		Element SubTransCode = new Element("SubTransCode");
		SubTransCode.setText("1");
		Element TransDate = new Element("TransDate");
		TransDate.setText(DateUtil.get10Date(new Date()));
		Element TransTime = new Element("TransTime");
		TransTime.setText(DateUtil.get8Time(new Date()));
		Element TransSeq = new Element("TransSeq");
		TransSeq.setText(new Date().getTime() + "");
		Element Operator = new Element("Operator");
		// Operator.setText("YBT");
		Operator.setText(channel);
		Element PageFlag = new Element("PageFlag");
		Element TotalRowNum = new Element("TotalRowNum");
		Element RowNumStart = new Element("RowNumStart");
		RowNumStart.setText("1");
		Element PageRowNum = new Element("PageRowNum");
		PageRowNum.setText("1000");
		Element ResultCode = new Element("ResultCode");
		Element ResultMsg = new Element("ResultMsg");
		Element OrderFlag = new Element("OrderFlag");
		Element OrderField = new Element("OrderField");
		Element PayTypeFlag = new Element("PayTypeFlag");
		PayTypeFlag.setText("1");
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		BaseInfo.addContent(TransType).addContent(TransCode)
				.addContent(SubTransCode).addContent(TransDate)
				.addContent(TransTime).addContent(TransSeq)
				.addContent(Operator).addContent(PageFlag)
				.addContent(TotalRowNum).addContent(RowNumStart)
				.addContent(PageRowNum).addContent(ResultCode)
				.addContent(ResultMsg).addContent(OrderFlag)
				.addContent(OrderField).addContent(PayTypeFlag);
		TransData.addContent(BaseInfo);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));

		Element mInputData = new Element("InputData");
		Element mAppType = new Element("AppType");
		Element mSumGetMoney = new Element("SumGetMoney");
		Element mSumPayMoney = new Element("SumPayMoney");
		Element mBalanceDate = new Element("BalanceDate");

		Element mWebEdorBalance = new Element("WebEdorBalance");
		String tLineMsg = mBufReader.readLine(); // �ӵ�һ�ж�ȡ
		String[] str = tLineMsg.split("\\|");
		// mSumGetMoney.setText("-" + str[3]);
		mSumPayMoney.setText("0.00");
		double premSum = 0;
		mAppType.setText("41");// ��������
		mBalanceDate.setText(DateUtil.getCur10Date());
		mInputData.addContent(mAppType);
		mInputData.addContent(mSumPayMoney);
		mInputData.addContent(mSumGetMoney);
		// �ӵڶ��ж�ȡ
		for (; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info("���ڶ�ȡ�ļ�###################");
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|");
			// ���ݱ����Ų�ѯ��ȫ���뷽ʽ
			String mSql = "select Bak4 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-����,020011-�˱�
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info("��ѯSQL��䣺" + mSql);
			SSRS sr = new ExeSQL().execSQL(mSql);
			if (sr.MaxRow != 0) {
				cAppType = sr.GetText(1, 1);
			} else {
				cAppType = cAppType + "";
			}
			if (cAppType.equals("21") || cAppType.equals("48")
					|| cAppType.equals("65")) {// 21-����;41-����;48-�����ն�;65-�ֻ�����;
				cLogger.warn("����������ȫ���ˣ����桢�����նˡ��ֻ�������������ֱ��������������һ����");
				continue;
			}
			cLogger.info("@@@@@@@@@@@@@@@@@@@@" + tSubMsgs.length);
			Element mEdorType = new Element("EdorType");
			String ywlb = tSubMsgs[1];

			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // ����
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // ���ڸ���
				break;
			} else if (ywlb.equals("03")) { // �˱�
				mEdorType.setText("CT");
			}
			Element mDetail = new Element("Detail");
			Element mEdorTransSeq = new Element("EdorTransSeq");

			// String toubaoNo = tSubMsgs[11]; // ���ݱ����Ų�ѯ��ȫ��ˮ��
			String sql = "select t.TranNo , Bak3 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-����,020011-�˱�
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info(sql + "!!!!!!!!!!!!!!!!!!!");
			sr = new ExeSQL().execSQL(sql);
			if (sr.MaxRow != 0) {
				mEdorTransSeq.setText(sr.GetText(1, 1));
				if (!sr.GetText(1, 2).equals(channel)) {
					continue;
				}
			}
			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // ����
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // ���ڸ���
			} else if (ywlb.equals("03")) { // �˱�
				mEdorType.setText("CT");
			}
			Element mMoney = new Element("Money"); // ����
			cLogger.info("���" + tSubMsgs[12]);
			mMoney.setText("-" + tSubMsgs[12]);
			String premStr = tSubMsgs[12];
			premSum += Double.parseDouble(premStr);
			Element mEdorState = new Element("EdorState"); // ��ȫ״̬
			mEdorState.setText("0" + tSubMsgs[8]);
			Element mBankCode = new Element("BankCode"); // ���д���
			// mBankCode.setText("0411");
			String sql_Type = "select ManageCom from Cont where ContNo = '"
					+ tSubMsgs[3] + "'";
			cLogger.info("��ѯSQL��䣺" + sql_Type);
			ExeSQL es = new ExeSQL();
			SSRS mmSSRS = es.execSQL(sql_Type);
			if (mmSSRS.getMaxRow() >= 1) {
				if (!mmSSRS.GetText(1, 1).equals("")
						&& mmSSRS.GetText(1, 1) != null) {
					if (mmSSRS.GetText(1, 1).substring(0, 4).equals("8611")) {// ����
						mBankCode.setText("0411");
					} else if (mmSSRS.GetText(1, 1).substring(0, 4)
							.equals("8641")) {// ����
						mBankCode.setText("0441");
					}
				}
			}
			mDetail.addContent(mEdorTransSeq);
			mDetail.addContent(mEdorType);
			mDetail.addContent(mMoney);
			mDetail.addContent(mEdorState);
			mDetail.addContent(mBankCode);
			mWebEdorBalance.addContent(mDetail);
			mBalanceDate.setText(DateUtil.date8to10(tSubMsgs[2]));
		}
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// ��ʽ������
		mSumGetMoney.setText("-" + decimalFormat.format(premSum));
		mWebEdorBalance.addContent(mBalanceDate);
		mInputData.addContent(mWebEdorBalance);

		mBufReader.close();

		TransData.addContent(mInputData);
		// TransData.addContent(getHead());
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(TransData);
	}

	// �����ն�������ȫ����
	protected Document parseZD(String nFileURL, String channel)
			throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�BaseInfo�ڵ�����
		Element TransData = new Element("TransData");
		Element BaseInfo = new Element("BaseInfo");
		Element TransType = new Element("TransType");
		TransType.setText("02");
		Element TransCode = new Element("TransCode");
		TransCode.setText("020019");
		Element SubTransCode = new Element("SubTransCode");
		SubTransCode.setText("1");
		Element TransDate = new Element("TransDate");
		TransDate.setText(DateUtil.get10Date(new Date()));
		Element TransTime = new Element("TransTime");
		TransTime.setText(DateUtil.get8Time(new Date()));
		Element TransSeq = new Element("TransSeq");
		TransSeq.setText(new Date().getTime() + "");
		Element Operator = new Element("Operator");
		Operator.setText(channel);
		Element PageFlag = new Element("PageFlag");
		Element TotalRowNum = new Element("TotalRowNum");
		Element RowNumStart = new Element("RowNumStart");
		RowNumStart.setText("1");
		Element PageRowNum = new Element("PageRowNum");
		PageRowNum.setText("1000");
		Element ResultCode = new Element("ResultCode");
		Element ResultMsg = new Element("ResultMsg");
		Element OrderFlag = new Element("OrderFlag");
		Element OrderField = new Element("OrderField");
		Element PayTypeFlag = new Element("PayTypeFlag");
		PayTypeFlag.setText("1");
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		BaseInfo.addContent(TransType).addContent(TransCode)
				.addContent(SubTransCode).addContent(TransDate)
				.addContent(TransTime).addContent(TransSeq)
				.addContent(Operator).addContent(PageFlag)
				.addContent(TotalRowNum).addContent(RowNumStart)
				.addContent(PageRowNum).addContent(ResultCode)
				.addContent(ResultMsg).addContent(OrderFlag)
				.addContent(OrderField).addContent(PayTypeFlag);
		TransData.addContent(BaseInfo);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));

		Element mInputData = new Element("InputData");
		Element mAppType = new Element("AppType");
		Element mSumGetMoney = new Element("SumGetMoney");
		Element mSumPayMoney = new Element("SumPayMoney");
		Element mBalanceDate = new Element("BalanceDate");

		Element mWebEdorBalance = new Element("WebEdorBalance");
		String tLineMsg = mBufReader.readLine(); // �ӵ�һ�ж�ȡ
		String[] str = tLineMsg.split("\\|");
		// mSumGetMoney.setText("-" + str[3]);
		mSumPayMoney.setText("0.00");
		double premSum = 0;
		mAppType.setText("48");// �����ն�����
		channel = "ABCWEB";
		mBalanceDate.setText(DateUtil.getCur10Date());
		mInputData.addContent(mAppType);
		mInputData.addContent(mSumPayMoney);
		mInputData.addContent(mSumGetMoney);

		// �ӵڶ��ж�ȡ
		for (; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info("���ڶ�ȡ�ļ�###################");
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|");
			// ���ݱ����Ų�ѯ��ȫ���뷽ʽ
			String mSql = "select Bak4 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-����,020011-�˱�
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info("��ѯSQL��䣺" + mSql);
			SSRS sr = new ExeSQL().execSQL(mSql);
			if (sr.MaxRow != 0) {
				cAppType = sr.GetText(1, 1);
			} else {
				cAppType = cAppType + "";
			}
			if (cAppType.equals("21") || cAppType.equals("41")
					|| cAppType.equals("65")) {// 21-����;41-����;48-�����ն�;65-�ֻ�����
				cLogger.warn("�����ն�������ȫ���ˣ����桢�����������ֻ���������ֱ��������������һ����");
				continue;
			}
			cLogger.info("@@@@@@@@@@@@@@@@@@@@" + tSubMsgs.length);
			Element mEdorType = new Element("EdorType");
			String ywlb = tSubMsgs[1];

			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // ����
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // ���ڸ���
				break;
			} else if (ywlb.equals("03")) { // �˱�
				mEdorType.setText("CT");
			}
			Element mDetail = new Element("Detail");
			Element mEdorTransSeq = new Element("EdorTransSeq");

			// String toubaoNo = tSubMsgs[11]; // ���ݱ����Ų�ѯ��ȫ��ˮ��
			String sql = "select t.TranNo , Bak3 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-����,020011-�˱�
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info(sql + "!!!!!!!!!!!!!!!!!!!");
			sr = new ExeSQL().execSQL(sql);
			if (sr.MaxRow != 0) {
				mEdorTransSeq.setText(sr.GetText(1, 1));
				if (!sr.GetText(1, 2).equals(channel)) {
					continue;
				}
			}
			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // ����
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // ���ڸ���
			} else if (ywlb.equals("03")) { // �˱�
				mEdorType.setText("CT");
			}
			Element mMoney = new Element("Money"); // ����
			cLogger.info("���" + tSubMsgs[12]);
			mMoney.setText("-" + tSubMsgs[12]);
			String premStr = tSubMsgs[12];
			premSum += Double.parseDouble(premStr);
			Element mEdorState = new Element("EdorState"); // ��ȫ״̬
			mEdorState.setText("0" + tSubMsgs[8]);
			Element mBankCode = new Element("BankCode"); // ���д���
			// mBankCode.setText("0411");
			String sql_Type = "select ManageCom from Cont where ContNo = '"
					+ tSubMsgs[3] + "'";
			cLogger.info("��ѯSQL��䣺" + sql_Type);
			ExeSQL es = new ExeSQL();
			SSRS mmSSRS = es.execSQL(sql_Type);
			if (mmSSRS.getMaxRow() >= 1) {
				if (!mmSSRS.GetText(1, 1).equals("")
						&& mmSSRS.GetText(1, 1) != null) {
					if (mmSSRS.GetText(1, 1).substring(0, 4).equals("8611")) {// ����
						mBankCode.setText("0411");
					} else if (mmSSRS.GetText(1, 1).substring(0, 4)
							.equals("8641")) {// ����
						mBankCode.setText("0441");
					}
				}
			}
			mDetail.addContent(mEdorTransSeq);
			mDetail.addContent(mEdorType);
			mDetail.addContent(mMoney);
			mDetail.addContent(mEdorState);
			mDetail.addContent(mBankCode);
			mWebEdorBalance.addContent(mDetail);
			mBalanceDate.setText(DateUtil.date8to10(tSubMsgs[2]));
		}
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// ��ʽ������
		mSumGetMoney.setText("-" + decimalFormat.format(premSum));
		mWebEdorBalance.addContent(mBalanceDate);
		mInputData.addContent(mWebEdorBalance);

		mBufReader.close();

		TransData.addContent(mInputData);
		// TransData.addContent(getHead());
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(TransData);
	}

	// �ֻ�����������ȫ����
	protected Document parseSJ(String nFileURL, String channel)
			throws Exception {
		cLogger.info("Into ABCBalance.getAbcDetails()...");
		String mCharset = "";
		// ��֯���ڵ��Լ�BaseInfo�ڵ�����
		Element TransData = new Element("TransData");
		Element BaseInfo = new Element("BaseInfo");
		Element TransType = new Element("TransType");
		TransType.setText("02");
		Element TransCode = new Element("TransCode");
		TransCode.setText("020019");
		Element SubTransCode = new Element("SubTransCode");
		SubTransCode.setText("1");
		Element TransDate = new Element("TransDate");
		TransDate.setText(DateUtil.get10Date(new Date()));
		Element TransTime = new Element("TransTime");
		TransTime.setText(DateUtil.get8Time(new Date()));
		Element TransSeq = new Element("TransSeq");
		TransSeq.setText(new Date().getTime() + "");
		Element Operator = new Element("Operator");
		Operator.setText(channel);
		Element PageFlag = new Element("PageFlag");
		Element TotalRowNum = new Element("TotalRowNum");
		Element RowNumStart = new Element("RowNumStart");
		RowNumStart.setText("1");
		Element PageRowNum = new Element("PageRowNum");
		PageRowNum.setText("1000");
		Element ResultCode = new Element("ResultCode");
		Element ResultMsg = new Element("ResultMsg");
		Element OrderFlag = new Element("OrderFlag");
		Element OrderField = new Element("OrderField");
		Element PayTypeFlag = new Element("PayTypeFlag");
		PayTypeFlag.setText("1");
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		BaseInfo.addContent(TransType).addContent(TransCode)
				.addContent(SubTransCode).addContent(TransDate)
				.addContent(TransTime).addContent(TransSeq)
				.addContent(Operator).addContent(PageFlag)
				.addContent(TotalRowNum).addContent(RowNumStart)
				.addContent(PageRowNum).addContent(ResultCode)
				.addContent(ResultMsg).addContent(OrderFlag)
				.addContent(OrderField).addContent(PayTypeFlag);
		TransData.addContent(BaseInfo);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		// ExeSQL exe = new ExeSQL();
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		InputStream pBatIs = new FileInputStream(nFileURL);

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));

		Element mInputData = new Element("InputData");
		Element mAppType = new Element("AppType");
		Element mSumGetMoney = new Element("SumGetMoney");
		Element mSumPayMoney = new Element("SumPayMoney");
		Element mBalanceDate = new Element("BalanceDate");

		Element mWebEdorBalance = new Element("WebEdorBalance");
		String tLineMsg = mBufReader.readLine(); // �ӵ�һ�ж�ȡ
		String[] str = tLineMsg.split("\\|");
		// mSumGetMoney.setText("-" + str[3]);
		mSumPayMoney.setText("0.00");
		double premSum = 0;
		mAppType.setText("65");// �ֻ���������
		mBalanceDate.setText(DateUtil.getCur10Date());
		mInputData.addContent(mAppType);
		mInputData.addContent(mSumPayMoney);
		mInputData.addContent(mSumGetMoney);

		// �ӵڶ��ж�ȡ
		for (; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info("���ڶ�ȡ�ļ�###################");
			cLogger.info(tLineMsg);
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|");
			// ���ݱ����Ų�ѯ��ȫ���뷽ʽ
			String mSql = "select Bak4 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-����,020011-�˱�
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info("��ѯSQL��䣺" + mSql);
			SSRS sr = new ExeSQL().execSQL(mSql);
			if (sr.MaxRow != 0) {
				cAppType = sr.GetText(1, 1);
			} else {
				cAppType = cAppType + "";
			}
			if (cAppType.equals("21") || cAppType.equals("41")
					|| cAppType.equals("48")) {// 21-����;41-����;48-�����ն�;65-�ֻ�����
				cLogger.warn("�ֻ�����������ȫ���ˣ����桢�����������ն���������ֱ��������������һ����");
				continue;
			}
			cLogger.info("@@@@@@@@@@@@@@@@@@@@" + tSubMsgs.length);
			Element mEdorType = new Element("EdorType");
			String ywlb = tSubMsgs[1];

			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // ����
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // ���ڸ���
				break;
			} else if (ywlb.equals("03")) { // �˱�
				mEdorType.setText("CT");
			}
			Element mDetail = new Element("Detail");
			Element mEdorTransSeq = new Element("EdorTransSeq");

			// String toubaoNo = tSubMsgs[11]; // ���ݱ����Ų�ѯ��ȫ��ˮ��
			String sql = "select t.TranNo , Bak3 from TranLog t where RCode=0 and t.FuncFlag='"
					+ (tSubMsgs[1].equals("01") ? "20010" : "20011")// 20010-����,020011-�˱�
					+ "' and t.ContNo='" + tSubMsgs[3] + "'";
			cLogger.info(sql + "!!!!!!!!!!!!!!!!!!!");
			sr = new ExeSQL().execSQL(sql);
			if (sr.MaxRow != 0) {
				mEdorTransSeq.setText(sr.GetText(1, 1));
				if (!sr.GetText(1, 2).equals(channel)) {
					continue;
				}
			}
			if (ywlb.equals("01")) {
				mEdorType.setText("WT"); // ����
			} else if (ywlb.equals("02")) {
				mEdorType.setText("AG"); // ���ڸ���
			} else if (ywlb.equals("03")) { // �˱�
				mEdorType.setText("CT");
			}
			Element mMoney = new Element("Money"); // ����
			cLogger.info("���" + tSubMsgs[12]);
			mMoney.setText("-" + tSubMsgs[12]);
			String premStr = tSubMsgs[12];
			premSum += Double.parseDouble(premStr);
			Element mEdorState = new Element("EdorState"); // ��ȫ״̬
			mEdorState.setText("0" + tSubMsgs[8]);
			Element mBankCode = new Element("BankCode"); // ���д���
			// mBankCode.setText("0411");
			String sql_Type = "select ManageCom from Cont where ContNo = '"
					+ tSubMsgs[3] + "'";
			cLogger.info("��ѯSQL��䣺" + sql_Type);
			ExeSQL es = new ExeSQL();
			SSRS mmSSRS = es.execSQL(sql_Type);
			if (mmSSRS.getMaxRow() >= 1) {
				if (!mmSSRS.GetText(1, 1).equals("")
						&& mmSSRS.GetText(1, 1) != null) {
					if (mmSSRS.GetText(1, 1).substring(0, 4).equals("8611")) {// ����
						mBankCode.setText("0411");
					} else if (mmSSRS.GetText(1, 1).substring(0, 4)
							.equals("8641")) {// ����
						mBankCode.setText("0441");
					}
				}
			}
			mDetail.addContent(mEdorTransSeq);
			mDetail.addContent(mEdorType);
			mDetail.addContent(mMoney);
			mDetail.addContent(mEdorState);
			mDetail.addContent(mBankCode);
			mWebEdorBalance.addContent(mDetail);
			mBalanceDate.setText(DateUtil.date8to10(tSubMsgs[2]));
		}
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// ��ʽ������
		mSumGetMoney.setText("-" + decimalFormat.format(premSum));
		mWebEdorBalance.addContent(mBalanceDate);
		mInputData.addContent(mWebEdorBalance);

		mBufReader.close();

		TransData.addContent(mInputData);
		// TransData.addContent(getHead());
		cLogger.info("Out ABCBalance.getAbcDetails()!");
		return new Document(TransData);
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.SecuTradAppDoc.main");
		mLogger.info("��ũ�б�ȫ���������ļ����˳�������...");

		SecuTradAppDoc abcAES = new SecuTradAppDoc();

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
		System.out.println("��ũ�б�ȫ���������ļ����˳������!");
	}

}
