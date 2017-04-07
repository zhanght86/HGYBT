package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class NewAbcCardBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger
			.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private String mFileData = "";
	 private TranLogDB cTranLogDB;
	private Document cOutXmlDoc;
	public void run() {
		cLogger.info("Into NewAbcCardBlc.run()...");
		try {
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2000"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.

			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date()).replace("-","");
			}
			String mCorNo = cConfigEle.getChildTextTrim("ComCode").trim();
			
			String mFIleName = "POLICY"+mCorNo+"." + cCurDate; // ��ʼ���ļ�����POLICY3002
															// //3002��˾��� + ��ǰʱ��
			// ��֯Document
			if(!new BatUtils().downLoadFile(mFIleName, "02","2000",cCurDate)){
				throw new MidplatException("��ũ�е�֤�����ļ������쳣");
			}
			// �������
			cLogger.info("������ũ�е�֤���˿�ʼ...");
			// �õ������׼����
			//
			String myFilePath = cConfigEle.getChildTextTrim("FilePath")+mFIleName;
//			String myFilePath = "C:\\Users\\chenjinwei\\Desktop\\POLICY1132.20161130";
			System.out.println(myFilePath);
			Document mInStd = parse(myFilePath);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(mInStd);
			String reCode =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
		    cTranLogDB.setRCode(reCode);
        	cTranLogDB.setRText(reMsg);
        	cTranLogDB.setModifyDate(DateUtil.getCur8Date());
        	cTranLogDB.setModifyTime(DateUtil.getCur6Time());
         	cTranLogDB.update();
			cLogger.info("������ũ�е�֤���˽���!");
		} catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name")+ "  �����쳣..."+e.getMessage());
			e.printStackTrace();
		    cTranLogDB.setRCode("1");
        	cTranLogDB.setRText(e.getMessage());
        	cTranLogDB.setModifyDate(DateUtil.getCur8Date());
        	cTranLogDB.setModifyTime(DateUtil.getCur6Time());
         	cTranLogDB.update();
		}finally {
			cCurDate = "";
		}
		cLogger.info("Out NewAbcCardBlc.run()!");
	}
	
	protected TranLogDB insertTranLog() throws MidplatException {
	    this.cLogger.debug("Into NewAbcCardBlc.insertTranLog()...");
	    TranLogDB mTranLogDB = new TranLogDB();
	    mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
	    mTranLogDB.setTranCom("05");
	    mTranLogDB.setNodeNo("0000000");
	    mTranLogDB.setFuncFlag("2000");
	    mTranLogDB.setOperator("YBT");
	    mTranLogDB.setTranNo(NoFactory.nextTranLogNo()+"");
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
	    this.cLogger.debug("Out NewAbcCardBlc.insertTranLog()!");
	    return mTranLogDB;
	  }
	
	protected Document parse(String nFileURL) throws Exception {
		cLogger.info("Into NewAbcCardBlc.parse()...");
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
		
		Element mZoneNo = new Element(ZoneNo);
		mZoneNo.setText(cConfigEle.getChildText("zone"));

		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cConfigEle.getChildText(NodeNo));

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
		mBankCode.setText("0102");

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
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		try{
			/*
			 * �ļ���һ�У���������Ϣ�� ��ʽ�����չ�˾����|�ܼ�¼��|�ܽ��|�ɹ��ܼ�¼��|�ɹ��ܽ�� �ļ��������ݣ�����ϸ��¼��
			 * ��������|���н�����ˮ��|����ʡ�д���|�������|������|���׽��|��������|����״̬
			 */
			String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
			// �ѳɹ��ļ�¼��ȡ������������
			Element mCountEle = new Element(Count);
			mCountEle.setText(mSubMsgs[3].trim());
			Element mBodyEle = new Element(Body);
			mBodyEle.addContent(mCountEle);
	
			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
			{
	
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
				 */
				
				//��֤������(�����š���ȫ�ŵ�)
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[4]);
				
				// ��ȡ����ӡˢ��
				String tContPrtNoSql = "select otherno from tranlog where funcflag='1004' and contno='" + tContNoEle.getText() + "' and trancom='05' and rcode='0' ";
				this.cLogger.info("����ӡˢ��sql:" + tContPrtNoSql);
				String tContPrtNo = new ExeSQL().getOneValue(tContPrtNoSql);
				//�����ѯ��֤ӡˢ��Ϊ�գ����׳��쳣
				if(StringUtils.isEmpty(tContPrtNo))
				{
					throw new MidplatException("��֤����ʧ�ܣ�����" + tContNoEle.getText() + "�ĵ�֤�Ų�ѯʧ��");
				}
				
				//��֤����
				Element tCardTypeEle = new Element("CardType");
				if (!"".equals(tContPrtNo) && tContPrtNo != null)
				{
					tCardTypeEle.setText(tContPrtNo.substring(0, 5));
				}
				
				//��֤�� ����ӡˢ��
				Element tCardNoEle = new Element(CardNo);
				tCardNoEle.setText(tContPrtNo);
							
				//��֤״̬
				Element tCardStateEle = new Element("CardState");
				tCardStateEle.setText("6");
				
				//����[�Ǳ��룬��Щ���д�] 
				String nodeNo = null;
				if (tSubMsgs[2] != null && tSubMsgs[3] != null)
				{
					nodeNo = tSubMsgs[2].trim() + tSubMsgs[3].trim();
				}
				Element tAgentCom = new Element(AgentCom);
				tAgentCom.setText(nodeNo);
				
				//��Ա����[�Ǳ��룬��Щ���д�]
				Element mTellerNoEle = new Element(TellerNo);
				mTellerNoEle.setText(tSubMsgs[3]);
				
				//������ˮ��[�Ǳ��룬��Щ���д�]
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tSubMsgs[1]);
				
	
				/*// ��ȡ��������
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tSubMsgs[0]);*/
	
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tCardTypeEle);// ��֤����
				tDetailEle.addContent(tCardNoEle);// ����ӡˢ��
				tDetailEle.addContent(tCardStateEle);// ��֤״̬
				tDetailEle.addContent(tContNoEle);// ������
				tDetailEle.addContent(tAgentCom);// ����
				tDetailEle.addContent(mTellerNoEle);// ��Ա����
				tDetailEle.addContent(tTranNoEle);// ������ˮ��
				mBodyEle.addContent(tDetailEle);
			}
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // �ر���
		}catch(Exception e){
			e.printStackTrace();
			throw new MidplatException("���������ļ�ʧ�ܣ�"+e.getMessage());
		}
		cLogger.info("Out NewAbcCardBlc.parse()!");
		return new Document(mTranData); 
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NewAbcCardBlc.main");
		mLogger.info("��ũ�е�֤���˳�������...");

		NewAbcCardBlc abcAES = new NewAbcCardBlc();

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
		System.out.println("��ũ�е�֤���˳������!");
	}
	
}
