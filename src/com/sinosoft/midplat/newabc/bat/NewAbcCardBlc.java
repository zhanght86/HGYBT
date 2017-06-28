package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class NewAbcCardBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());
	
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private TranLogDB cTranLogDB;
	private Document cOutXmlDoc;
	
	public void run() {
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NewAbcCardBlc.run()...");
		try {
			cResultMsg = null;
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("2000"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date()).replace("-","");
			}
			String mCorNo = cConfigEle.getChildTextTrim("ComCode").trim();
			// ��ʼ���ļ�����		POLICY  +��˾��� 	+.	 	+ ��ǰʱ��
			String mFIleName = "POLICY"+mCorNo+"." + cCurDate; 
			if(!new BatUtils().downLoadFile(mFIleName, "02","2000",cCurDate)){
				throw new MidplatException("��ũ�е�֤�����ļ������쳣");
			}
			// �������
			cLogger.info("������ũ�е�֤���˿�ʼ...");
			String myFilePath = cConfigEle.getChildTextTrim("FilePath")+mFIleName;
//			String myFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\POLICY1147.20170607";
			cLogger.info(myFilePath);
			// �õ������׼����
			Document cInXmlDoc = parse(myFilePath);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(cInXmlDoc);
			String reCode =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg =	cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
		    cTranLogDB.setRCode(reCode);
        	cTranLogDB.setRText(reMsg);
        	cTranLogDB.setModifyDate(DateUtil.getCur8Date());
        	cTranLogDB.setModifyTime(DateUtil.getCur6Time());
         	cTranLogDB.update();
         	
	        // ÿ��1�ձ������µĶ����ļ�
			if ("01".equals(DateUtil.getDateStr(new Date(), "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"));
			}
         	
         	cResultMsg = reMsg;
		} catch (Exception e) {
			cResultMsg = e.toString();
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
		cLogger.info("������ũ�е�֤���˽���!");
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
		// ��֯���ڵ��Լ�Head�ڵ�����
		Element mTranData = new Element("TranData");
		String tBalanceFlag = "0";
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(cCurDate);
		String cTranDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" ��������Ϊ..." + cCurDate);
		cLogger.info(" ��ǰ����Ϊ..." + cTranDate);

		// ���ֹ����ˣ���tBalanceFlag��־��Ϊ1 �����ն�����Ϊ0
		if (!cCurDate.equals(cTranDate)){
			tBalanceFlag = "1";
		}
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(new Date(), "HHmmss"));

		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("com"));
		
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
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		try{
			/*
			 * �����߼������������������ɹ��ĵ�֤���ˣ����ճ������ݵ�֤���ˣ��������ײ�����֤����
			 * ��������Ϣ���� ���չ�˾����|�ܼ�¼��|�ܽ��|�ɹ��ܼ�¼��|�ɹ��ܽ�� 
			 * ����ϸ��¼���� ��������|���н�����ˮ��|����ʡ�д���|�������|������|���׽��|��������|����״̬
			 */
			@SuppressWarnings("unused")
			String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
			// �ѳɹ��ļ�¼��ȡ������������
			Element mCountEle = new Element(Count);
			Element mBodyEle = new Element(Body);
			int count=0;
			for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());){
				// ���У�ֱ������
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg)){
					cLogger.warn("���У�ֱ��������������һ����");
					continue;
				}
				String[] tSubMsgs = tLineMsg.split("\\|", -1);
				if (!"01".equals(tSubMsgs[6])){
					cLogger.warn("�ǳб�������ֱ��������������һ����");
					continue;
				}
				String tSqlStr = new StringBuffer("select bak5 from cont where contNo='")
					.append(tSubMsgs[4]).append('\'').append(" and tranCom=").append(05)
					.append(" and state in(").append(AblifeCodeDef.ContState_Cancel).append(",")
					.append(AblifeCodeDef.ContState_Sign).append(")").toString();
				cLogger.info(tSqlStr);
				SSRS ssrs = new ExeSQL().execSQL(tSqlStr);
				//�ж��Ƿ��ǵ��ճ������ݣ��ǣ���֤���ˣ��񣺲�����
				if(ssrs.getMaxRow()>0){
					//�ǹ�����������
					if(!ssrs.GetText(1, 1).equals("0")){
						cLogger.warn("�����������ݣ�ֱ��������������һ����");
						continue;
					}else{
						cLogger.info("������������...");
					}
				}else{
					cLogger.warn("�ǵ��ճ���or�ǳб����ݣ�ֱ��������������һ����");
					continue;
				}
				//������
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tSubMsgs[4]);
				// ����־���в�ѯ����ӡˢ��
				String tContPrtNoSql = new StringBuffer("select otherno from tranlog where funcflag=")
					.append(1004).append(" and contno='").append(tContNoEle.getText()).append('\'')
					.append(" and trancom=").append(05).append(" and rcode=").append(0).toString();
				this.cLogger.info("����ӡˢ��sql:" + tContPrtNoSql);
				String tContPrtNo = new ExeSQL().getOneValue(tContPrtNoSql);
				//�����ѯ��֤ӡˢ��Ϊ�գ����׳��쳣
				if(StringUtils.isEmpty(tContPrtNo)){
					throw new MidplatException("��֤����ʧ�ܣ�����" + tContNoEle.getText() + "�ĵ�֤�Ų�ѯʧ��");
				}
				//��֤����
				Element tCardTypeEle = new Element("CardType");
				if (!"".equals(tContPrtNo) && tContPrtNo != null){
					tCardTypeEle.setText(tContPrtNo.substring(0, 5));
				}
				//����ӡˢ��
				Element tCardNoEle = new Element(CardNo);
				tCardNoEle.setText(tContPrtNo);
				//���ĵ�֤״̬��4��������6��ʹ�����ϣ�9��ֹͣ����
				Element tCardStateEle = new Element("CardState");
				tCardStateEle.setText("4");
				Element tAgentCom = new Element(AgentCom);
				Element mTellerNoEle = new Element(TellerNo);
				Element tTranNoEle = new Element(TranNo);
				count++;
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
			mCountEle.setText(count+"");
			mBodyEle.addContent(mCountEle);
			mTranData.addContent(mBodyEle);
			mBufReader.close(); // �ر���
		}catch(Exception e){
			e.printStackTrace();
			throw new MidplatException("���������ļ�ʧ�ܣ�"+e.getMessage());
		}
		cLogger.info("Out NewAbcCardBlc.parse()!");
		return new Document(mTranData); 
	}

	// �������ļ�������yyyyMM01���գ�ϵͳ����Ŀ¼localDir ����/yyyy/yyyyMM��
	// Ȼ��������ļ��ƶ�����Ŀ¼������yyyyMM01���ļ�����
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into NewAbcCardBlc.bakFiles...");

		if (null == pFileDir || "".equals(pFileDir))
		{
			cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory())
		{
			cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
			return;
		}

		File[] mOldFiles = mDirFile.listFiles(new FileFilter()
		{
			public boolean accept(File pFile)
			{
				if (!pFile.isFile())
				{
					return false;
				}

				Calendar tCurCalendar = Calendar.getInstance();
				tCurCalendar.setTime(new Date());

				Calendar tFileCalendar = Calendar.getInstance();
				tFileCalendar.setTime(new Date(pFile.lastModified()));

				return tFileCalendar.before(tCurCalendar);
			}
		});

		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles)
		{
			try
			{
				String fileName_date = tFile.getName().substring(tFile.getName().length() - 8);
				Date date = new Date();
				if (!fileName_date.equals(String.valueOf(DateUtil.get8Date(date))))
				{
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
			}
			catch (IOException ex)
			{
				cLogger.error(tFile.getAbsoluteFile() + "����ʧ�ܣ�", ex);
			}
		}

		cLogger.info("Out NewAbcCardBlc.bakFiles!");
	}
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
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
