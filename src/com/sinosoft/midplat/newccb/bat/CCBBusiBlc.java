package com.sinosoft.midplat.newccb.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ElementLis;

/**
 * �µ��������
 * 
 * @author Administrator
 */
public class CCBBusiBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger
			.getLogger(getClass());
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private Document cOutXmlDoc;
	private TranLogDB cTranLogDB;
//	private TranLogDB cTranLogDB_XQ;
//	private List<Element> cBusiXQList = null;
	
	public void run() {
		cLogger.info("Into CCBBusiBlc.run()...");
		try {
			this.cResultMsg = null;
//			cBusiXQList = new ArrayList<Element>();
			cTranLogDB = insertTranLog("�µ�");
			cConfigEle = ((Element) XPath.selectSingleNode(BatConf.newInstance().getConf().getRootElement(), 
					"batch[funcFlag='" + 20031 + "']"));


			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}

			String mCorNo = cConfigEle.getChildTextTrim("ComCode");
			//String mBranchCode = cConfigEle.getChildTextTrim("BranchCode");
			String mFIleName = "RcnclFile_"+cCurDate+"_"+mCorNo+"_";//�����ļ���ʡ��˳��ż���׺������Ҫ���ܶ��ܣ���
			cLogger.info("��ȡƥ���ļ���Ϊ��"+mFIleName);
			//��ָ��·���»�ȡ���ܺ�Ķ����ļ����ֶ��ܣ���
			String mFilePath = cConfigEle.getChildTextTrim("FilePath");
			List<File> mFileList = getFileList(mFilePath, mFIleName, ".xml");//�õ����ж����ļ�����
			if(mFileList.size() <= 0){
				cLogger.debug("δ�ҵ���������ļ���������Ľ���");
				throw new Exception("δ�ҵ���������ļ�");
			}else if(mFileList.size()<1){
				String tq="";
				String tq1="";
				String  ExceptionName = "";
				for (int i = 0; i < mFileList.size(); i++) {
					File mFileBuis = mFileList.get(i);
					String t = mFileBuis.getAbsolutePath();
					tq = t.substring(t.lastIndexOf("_")-9, t.lastIndexOf("_"));
					tq1 = tq1 +tq;
					tq = "";
				}
				if(!tq1.contains("520000000")){
					cLogger.debug("δ�ҵ����ݵ�����������ļ���������Ľ���");
					ExceptionName = "δ�ҵ����ݵ�����������ļ���";
				}
				throw new Exception(ExceptionName);
			}

			// �������
			cLogger.info("������˿�ʼ...");
			// �õ������׼����
			//==========================��������Լ����===========================
			Document mInStd = parseNewCont(mFileList);
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(
					AblifeCodeDef.SID_Bank_NewContBlc).call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("����Լ���˽����Flag="+reCode+"  Desc="+reMsg);
			
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
			//=========================����Լ���˽���============================
//			//==========================�������������===========================
//			Thread.sleep(5000);//�ӳ�5S�ٽ������ڶ���
//			cTranLogDB_XQ = insertTranLog("����-��������");
//			mInStd = parseXUQI(cBusiXQList);
//			JdomUtil.print(mInStd);
//			cOutXmlDoc = new CallWebsvcAtomSvc(
//					AblifeCodeDef.SID_Icbc_XQRenewalBlc).call(mInStd);
//			String reCodeXQ = cOutXmlDoc.getRootElement().getChild("BaseInfo").getChildText("ResultCode");
//			String reMsgXQ = cOutXmlDoc.getRootElement().getChild("BaseInfo").getChildText("ResultMsg");
//			cLogger.info("���ڶ��˽����ResultCode="+reCodeXQ+"  ResultMsg="+reMsgXQ);
//			
//			cTranLogDB_XQ.setRCode(reCodeXQ);
//			cTranLogDB_XQ.setRText(reMsgXQ);
//			cTranLogDB_XQ.setModifyDate(DateUtil.getCur8Date());
//			cTranLogDB_XQ.setModifyTime(DateUtil.getCur6Time());
//			cTranLogDB_XQ.update();
			//==========================������������===========================
			//==========================���ݶ����ļ�===========================
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    Date date = sdf.parse(cCurDate);
	        if ("01".equals(DateUtil.getDateStr(date, "dd"))) {
	        	bakFiles(cConfigEle.getChildTextTrim("FilePath"), date);
	        }
	        this.cResultMsg = reMsg;
		} catch (Exception e) {
			this.cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name")
					+ "  ���˴����쳣...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();

		}finally {
			cCurDate = "";
//			cBusiXQList = null;
		}
		cLogger.info("������˽���...");
		cLogger.info("Out CCBBusiBlc.run()...");
	}

	protected TranLogDB insertTranLog(String str) throws MidplatException {
		this.cLogger.debug("Into CCBBusiBlc.insertTranLog()...");

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("03");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("20031");
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
		mTranLogDB.setBak4(str);
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!(mTranLogDB.insert())) {
			this.cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}
		return mTranLogDB;
	}
	/**
	 * @param nFileURL
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected Document parseNewCont(List<File> mFileList) throws Exception {
		cLogger.info("Into CCBBusiBlc.parseNewCont()...");
		cLogger.info("ȡ���ļ����µ����ݣ���������Լ����...");
		String mCharset = "";
		Element TransData = new Element("TranData");
		Element mHead = new Element(Head);
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(cCurDate);
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.get6Time(new Date())+"");
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cConfigEle.getChildText("com"));
		Element mNodeNo = new Element(NodeNo);
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(new Date().getTime() + "");
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cConfigEle.getChildText(funcFlag));
		// ����ͷ������Ӻ��ĵ����б���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(cConfigEle.getChildText("BankCode"));
		
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
		mHead.addContent(mTranDate).addContent(mTranTime).addContent(
				mTranCom).addContent(mNodeNo)
				.addContent(mTellerNo).addContent(mTranNo).addContent(mFuncFlag)
				.addContent(mBankCode);
		TransData.addContent(mHead);
		// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "UTF-8";
		}
		
		Element mBodyEle = new Element(Body);
		Element mCount = new Element(Count);
		Element mPrem = new Element(Prem);
		mBodyEle.addContent(mCount);
		mBodyEle.addContent(mPrem);
		
		int index = 0;
		double premSum = 0;
		for (int i = 0; i < mFileList.size(); i++) {
			File mFileBuis = mFileList.get(i);
			cLogger.info("�����ļ���"+ mFileBuis.getAbsolutePath());
			if(!mFileBuis.isFile()){
				cLogger.error("�����ļ���" + mFileBuis.getAbsolutePath());
				continue;
			}
			InputStream pBatIs = new FileInputStream(mFileBuis.getAbsolutePath());
			Document pBuisDoc = JdomUtil.build(pBatIs , mCharset);
			JdomUtil.print(pBuisDoc);
			Element pDetail_List = pBuisDoc.getRootElement().getChild("Detail_List");
			if(pDetail_List!=null){
			List<Element> pBuisDetails = pDetail_List.getChildren("Detail");
			if(pBuisDetails.size() <= 0){
				cLogger.error("������ϸΪ0");
				continue;
			}
			for (int j = 0; j < pBuisDetails.size(); j++) {
				Element pBuisDetail = pBuisDetails.get(j);
				if("P53819156".equals(pBuisDetail.getChildText("ORG_TX_ID"))){
					cLogger.info("�˱�������Ϊ�������ݣ�������["+pBuisDetail.getChildText("InsPolcy_No")+"] ���˽�����["+pBuisDetail.getChildText("ORG_TX_ID")+"]" );
//					cBusiXQList.add(pBuisDetail);//��������
					continue;
				}
				if(!"P53819152".equals(pBuisDetail.getChildText("ORG_TX_ID"))){
					cLogger.info("�˱������ݲ�������Լ���ݣ�������["+pBuisDetail.getChildText("InsPolcy_No")+"] ���˽�����["+pBuisDetail.getChildText("ORG_TX_ID")+"]" );
					continue;
				}
				Element mDetail = new Element("Detail");
				
				index ++;
				String premStr = pBuisDetail.getChildText("TxnAmt");
				premSum += Double.parseDouble(premStr);
				
				Element mmContNo = new Element("ContNo");
				mmContNo.setText(pBuisDetail.getChildText("InsPolcy_No"));
				Element mmPrem = new Element("Prem");
				int premInt = (int)(Double.valueOf(premStr) *100);
				mmPrem.setText(String.valueOf(premInt));
				Element mmAgentCom = new Element("AgentCom");
				Element mmProposalPrtNo = new Element("ProposalPrtNo");
				Element AppntName = new ElementLis("AppntName");
				Element InsuredName = new ElementLis("InsuredName");
				
				mDetail.addContent(mmContNo);
				mDetail.addContent(mmPrem);
				mDetail.addContent(mmAgentCom);
				mDetail.addContent(mmProposalPrtNo);
				mDetail.addContent(AppntName);
				mDetail.addContent(InsuredName);
				
				mBodyEle.addContent(mDetail);
			}
			}
		}

		mCount.setText(String.valueOf(index));
		DecimalFormat decimalFormat = new DecimalFormat("#0");//��ʽ������  
		mPrem.setText(decimalFormat.format(premSum * 100));
		TransData.addContent(mBodyEle);
		cLogger.info("Out CCBBusiBlc.parseNewCont()!");
		return new Document(TransData);
	}
	
	
	
	public List<File> getFileList(String mFilePath , String mFileName , String mFileSuffix){
		File[] mFiles = new File(mFilePath).listFiles();
		
		List<File> mNewFileList = new ArrayList<File>();
		for (int i = 0; i < mFiles.length; i++) {
			File tFile = mFiles[i];
			if(tFile.getName().indexOf(mFileName) == 0  &&  tFile.getName().lastIndexOf(mFileSuffix) == (tFile.getName().length()-mFileSuffix.length())){
				cLogger.info("�ҵ������ļ���"+tFile.getAbsolutePath());
				mNewFileList.add(tFile);
			}
		}
		
		return mNewFileList;
	}
	
	/*****************�����ļ�********************/
	public void bakFiles(String pFileDir, final Date date) {
	    System.out.println("Into Balance.bakFiles()...");
	    
	    if ((pFileDir == null) || ("".equals(pFileDir))) {
	      System.out.println("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
	      return;
	    }
	    File mDirFile = new File(pFileDir);
	    if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
	      System.out.println("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
	      return;
	    }

	    File[] mOldFiles = mDirFile.listFiles(
	      new FileFilter()
	    {
	      public boolean accept(File pFile)
	      {
	        if (!pFile.isFile()) {
	          return false;
	        }

	        Calendar tCurCalendar = Calendar.getInstance();
	        tCurCalendar.setTime(date);
	        tCurCalendar.set(11, 8);

	        Calendar tFileCalendar = Calendar.getInstance();
	        tFileCalendar.setTimeInMillis(pFile.lastModified());

	        return tFileCalendar.before(tCurCalendar);
	      }
	    });
	    Calendar mCalendar = Calendar.getInstance();
	    mCalendar.add(2, -1);
	    File mNewDir = 
	      new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
	    for (File tFile : mOldFiles) {
	      System.out.println(tFile.getAbsoluteFile() + " start move...");
	      try {
	        IOTrans.fileMove(tFile, mNewDir);
	        System.out.println(tFile.getAbsoluteFile() + " end move!");
	      } catch (IOException ex) {
	        System.out.printf(tFile.getAbsoluteFile() + "����ʧ�ܣ�", ex);
	      }
	    }

	    System.out.println("Out Balance.bakFiles()!");
	  }
	
	/*
	 * ���ö�������
	 */
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr;
	}
	public String getResultMsg(){
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger
				.getLogger("com.sinosoft.midplat.newccb.bat.CCBBusiBlc.main");
		mLogger.info("���ж��˳�������������");

		CCBBusiBlc ccbAES = new CCBBusiBlc();

		// ���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))��
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
//		cCurDate = "20160309";
		ccbAES.run();
		System.out.println("���ж��˳�����ɡ�����");
	}

}
