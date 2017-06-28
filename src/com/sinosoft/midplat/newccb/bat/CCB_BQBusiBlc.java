package com.sinosoft.midplat.newccb.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

/**
 * ��ȫ�������
 * 
 * @author Administrator
 */
public class CCB_BQBusiBlc extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger
			.getLogger(getClass());
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private Document cOutXmlDoc;
	private List<Element> cBusiTBList = null;
	private TranLogDB cTranLogDB;
	
	public void run() {
		cLogger.info("Into CCB_BQBusiBlc.run()...");
		this.cResultMsg = null;
		try {
			cBusiTBList = new ArrayList<Element>();
			cConfigEle = ((Element) XPath.selectSingleNode(BatConf.newInstance().getConf().getRootElement(), 
					"batch[funcFlag='" + 20032 + "']"));
			
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}

			String mCorNo = cConfigEle.getChildTextTrim("ComCode");
			String mFIleName = "PrervtRcnclFile_"+cCurDate+"_"+mCorNo;//�����ļ���ʡ��˳��ż���׺��
			cLogger.info("��ȡ���ļ���Ϊ��"+ mFIleName);
			//��ָ��·���»�ȡ���ܺ�Ķ����ļ����ֶ��ܣ���
			String mFilePath = cConfigEle.getChildTextTrim("FilePath");
			cLogger.info("��ȡ���ļ�·��Ϊ��"+ mFilePath);
			List<File> mFileList = getFileList(mFilePath, mFIleName, ".xml");//�õ����ж����ļ�����
			cLogger.info("������"+ mFileList.size());
			if(mFileList.size() <= 0){
				cLogger.debug("δ�ҵ���ȫ�����ļ���������Ľ���");
				throw new Exception("δ�ҵ���ȫ�����ļ�");
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
			dealFile(mFileList);
			//==========================���б�ȫ�����===========================
			cTranLogDB = insertTranLog("����" , "20032");
			String reCodeBQ = "";
			String reMsgBQ = "";
			Document mInStd = parseBAOQUAN();
//			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(
					AblifeCodeDef.SID_BQContBlc).call(mInStd);
			JdomUtil.print(cOutXmlDoc);
			reCodeBQ = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			reMsgBQ = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ȫ���˽����Flag="+reCodeBQ+"  Desc="+reMsgBQ);
			
			cTranLogDB.setRCode(reCodeBQ);
			cTranLogDB.setRText(reMsgBQ);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
			//=========================��ȫ���˽���=========================
			//==========================���ݶ����ļ�===========================
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    Date date = sdf.parse(cCurDate);
	        if ("01".equals(DateUtil.getDateStr(date, "dd"))) {
	        	bakFiles(cConfigEle.getChildTextTrim("FilePath"), date);
	        }
	        this.cResultMsg = reMsgBQ;
		} catch (Exception e) {
			cResultMsg= e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name")
					+ "  ���˴����쳣...");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
		}finally {
			cCurDate = "";
		}
		cLogger.info("������˽���...");
		cLogger.info("Out CCB_BQBusiBlc.run()...");
	}

	protected TranLogDB insertTranLog(String str , String mFuncflag) throws MidplatException {
		this.cLogger.debug("Into CCB_BQBusiBlc.insertTranLog()...");

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("03");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag(mFuncflag);
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
	protected void dealFile(List<File> mFileList) throws Exception {
		cLogger.info("Into CCB_BQBusiBlc.dealFile()...");
		String mCharset = "";
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "UTF-8";
		}
//		ExeSQL es = new ExeSQL();
//		SSRS mmSSRS = null;
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
//				String mSql = "select Bak3 from TranLog where ContNo= '"+ pBuisDetail.getChildText("InsPolcy_No") +"' and funcflag='020030'";
//				cLogger.info("��ѯSQL��䣺"+mSql);
//				mmSSRS = es.execSQL(mSql);
//				if(mmSSRS.getMaxRow() >= 1){
//					cAppType = mmSSRS.GetText(1, 1);
//				}else{
//					cAppType=cAppType+"";
//				}
				//ȷ���˱�
				if("P53819144".equals(pBuisDetail.getChildText("ORG_TX_ID"))){
					cLogger.info("�˱�������Ϊ��ȫ�������ݣ�������["+pBuisDetail.getChildText("InsPolcy_No")+"] ���˽�����["+pBuisDetail.getChildText("ORG_TX_ID")+"]" );
					cBusiTBList.add(pBuisDetail);
					continue;
				}else if("P538191D2".equals(pBuisDetail.getChildText("ORG_TX_ID"))){//�Ǽ�ǩ������
					continue;
				}else if("P53819182".equals(pBuisDetail.getChildText("ORG_TX_ID"))){//��ӡ���������ˣ�������
					continue;
				}else if("P53819146".equals(pBuisDetail.getChildText("ORG_TX_ID"))){//ȷ�����������ˣ�������
				    continue;
				}else if("P538191B3".equals(pBuisDetail.getChildText("ORG_TX_ID"))){//�Ǽ�̨�ˣ����ˣ�������
					cLogger.info("�˱�������Ϊ�Ǽ�̨�ˣ����˸����ݲ�����");
				    continue;
				}
//				else{
//					cLogger.info("�˱�������Ϊ��ȫ�ǲ������ݣ�������["+pBuisDetail.getChildText("InsPolcy_No")+"] ���˽�����["+pBuisDetail.getChildText("ORG_TX_ID")+"]" );
//					cBusiList.add(pBuisDetail);
//				}
				
			}
		}
	}
		cLogger.info("Out CCB_BQBusiBlc.dealFile()!");
	}
	protected Document parseBAOQUAN() throws Exception {
		cLogger.info("Into CCBBusiBlc.parseBAOQUAN()...");
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
		
		Element mBodyEle = new Element("Body");
		Element mCountEle = new Element(Count);
		Element mPremEle = new Element(Prem);
		
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mPremEle);
		int index = 0;
		for (int i = 0; i < cBusiTBList.size(); i++) {
			Element pBuisDetail = cBusiTBList.get(i);
			Element mDetail = new Element("Detail");
			index++;
			Element tTranDateEle = new Element(TranDate);
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(pBuisDetail.getChildText("InsPolcy_No"));
			Element tAgentCom=new Element(AgentCom);
			Element tPremEle = new Element(Prem);
			Element tTranNo = new Element(TranNo);
			
			mDetail.addContent(tTranDateEle);
			mDetail.addContent(tProposalPrtNoEle);
			mDetail.addContent(tContNoEle);
			mDetail.addContent(tAgentCom);
			mDetail.addContent(tPremEle);
			mDetail.addContent(tTranNo);
			
			mBodyEle.addContent(mDetail);
		}
		cLogger.info("�ܱ���:"+index);
		mCountEle.setText(index+"");
		TransData.addContent(mBodyEle);
		cLogger.info("Out CCBBusiBlc.parseBAOQUAN()!");
		JdomUtil.print(TransData);
		return new Document(TransData);
	}
	
	public List<File> getFileList(String mFilePath , String mFileName , String mFileSuffix){
		File[] mFiles = new File(mFilePath).listFiles();
		System.out.println("·�����ļ�������"+mFiles.length );
		List<File> mNewFileList = new ArrayList<File>();
		for (int i = 0; i < mFiles.length; i++) {
			File tFile = mFiles[i];
			if(tFile.getName().indexOf(mFileName) >= 0  &&  tFile.getName().lastIndexOf(mFileSuffix) == (tFile.getName().length()-mFileSuffix.length())){
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
				.getLogger("com.sinosoft.midplat.newccb.bat.CCB_BQBusiBlc.main");
		mLogger.info("���ж��˳�������������");

		CCB_BQBusiBlc ccbAES = new CCB_BQBusiBlc();

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
		ccbAES.run();
		System.out.println("���ж��˳�����ɡ�����");
	}

}
