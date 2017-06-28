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
 * 新单申请对账
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
			cTranLogDB = insertTranLog("新单");
			cConfigEle = ((Element) XPath.selectSingleNode(BatConf.newInstance().getConf().getRootElement(), 
					"batch[funcFlag='" + 20031 + "']"));


			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}

			String mCorNo = cConfigEle.getChildTextTrim("ComCode");
			//String mBranchCode = cConfigEle.getChildTextTrim("BranchCode");
			String mFIleName = "RcnclFile_"+cCurDate+"_"+mCorNo+"_";//定义文件名省略顺序号及后缀（核心要求总对总）。
			cLogger.info("获取匹配文件名为："+mFIleName);
			//在指定路径下获取解密后的对账文件（分对总）。
			String mFilePath = cConfigEle.getChildTextTrim("FilePath");
			List<File> mFileList = getFileList(mFilePath, mFIleName, ".xml");//得到分行对账文件集合
			if(mFileList.size() <= 0){
				cLogger.debug("未找到财务对账文件，不与核心交互");
				throw new Exception("未找到财务对账文件");
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
					cLogger.debug("未找到贵州地区财务对账文件，不与核心交互");
					ExceptionName = "未找到贵州地区财务对账文件。";
				}
				throw new Exception(ExceptionName);
			}

			// 处理对账
			cLogger.info("处理对账开始...");
			// 得到请求标准报文
			//==========================进行新契约对账===========================
			Document mInStd = parseNewCont(mFileList);
			JdomUtil.print(mInStd);
			cOutXmlDoc = new CallWebsvcAtomSvc(
					AblifeCodeDef.SID_Bank_NewContBlc).call(mInStd);
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("新契约对账结果：Flag="+reCode+"  Desc="+reMsg);
			
			cTranLogDB.setRCode(reCode);
			cTranLogDB.setRText(reMsg);
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
			//=========================新契约对账结束============================
//			//==========================进行续期类对账===========================
//			Thread.sleep(5000);//延迟5S再进行续期对账
//			cTranLogDB_XQ = insertTranLog("续期-柜面渠道");
//			mInStd = parseXUQI(cBusiXQList);
//			JdomUtil.print(mInStd);
//			cOutXmlDoc = new CallWebsvcAtomSvc(
//					AblifeCodeDef.SID_Icbc_XQRenewalBlc).call(mInStd);
//			String reCodeXQ = cOutXmlDoc.getRootElement().getChild("BaseInfo").getChildText("ResultCode");
//			String reMsgXQ = cOutXmlDoc.getRootElement().getChild("BaseInfo").getChildText("ResultMsg");
//			cLogger.info("续期对账结果：ResultCode="+reCodeXQ+"  ResultMsg="+reMsgXQ);
//			
//			cTranLogDB_XQ.setRCode(reCodeXQ);
//			cTranLogDB_XQ.setRText(reMsgXQ);
//			cTranLogDB_XQ.setModifyDate(DateUtil.getCur8Date());
//			cTranLogDB_XQ.setModifyTime(DateUtil.getCur6Time());
//			cTranLogDB_XQ.update();
			//==========================续期类对账完成===========================
			//==========================备份对账文件===========================
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    Date date = sdf.parse(cCurDate);
	        if ("01".equals(DateUtil.getDateStr(date, "dd"))) {
	        	bakFiles(cConfigEle.getChildTextTrim("FilePath"), date);
	        }
	        this.cResultMsg = reMsg;
		} catch (Exception e) {
			this.cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name")
					+ "  对账处理异常...");
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
		cLogger.info("处理对账结束...");
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
			throw new MidplatException("插入日志失败！");
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
		cLogger.info("取出文件中新单数据，进行新契约对账...");
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
		// 报文头结点增加核心的银行编码
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
			cLogger.info("处理文件："+ mFileBuis.getAbsolutePath());
			if(!mFileBuis.isFile()){
				cLogger.error("不是文件：" + mFileBuis.getAbsolutePath());
				continue;
			}
			InputStream pBatIs = new FileInputStream(mFileBuis.getAbsolutePath());
			Document pBuisDoc = JdomUtil.build(pBatIs , mCharset);
			JdomUtil.print(pBuisDoc);
			Element pDetail_List = pBuisDoc.getRootElement().getChild("Detail_List");
			if(pDetail_List!=null){
			List<Element> pBuisDetails = pDetail_List.getChildren("Detail");
			if(pBuisDetails.size() <= 0){
				cLogger.error("对账明细为0");
				continue;
			}
			for (int j = 0; j < pBuisDetails.size(); j++) {
				Element pBuisDetail = pBuisDetails.get(j);
				if("P53819156".equals(pBuisDetail.getChildText("ORG_TX_ID"))){
					cLogger.info("此保单数据为续期数据：保单号["+pBuisDetail.getChildText("InsPolcy_No")+"] 对账交易码["+pBuisDetail.getChildText("ORG_TX_ID")+"]" );
//					cBusiXQList.add(pBuisDetail);//柜面渠道
					continue;
				}
				if(!"P53819152".equals(pBuisDetail.getChildText("ORG_TX_ID"))){
					cLogger.info("此保单数据不是新契约数据：保单号["+pBuisDetail.getChildText("InsPolcy_No")+"] 对账交易码["+pBuisDetail.getChildText("ORG_TX_ID")+"]" );
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
		DecimalFormat decimalFormat = new DecimalFormat("#0");//格式化设置  
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
				cLogger.info("找到对账文件："+tFile.getAbsolutePath());
				mNewFileList.add(tFile);
			}
		}
		
		return mNewFileList;
	}
	
	/*****************备份文件********************/
	public void bakFiles(String pFileDir, final Date date) {
	    System.out.println("Into Balance.bakFiles()...");
	    
	    if ((pFileDir == null) || ("".equals(pFileDir))) {
	      System.out.println("本地文件目录为空，不进行备份操作！");
	      return;
	    }
	    File mDirFile = new File(pFileDir);
	    if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
	      System.out.println("本地文件目录不存在，不进行备份操作！" + mDirFile);
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
	        System.out.printf(tFile.getAbsoluteFile() + "备份失败！", ex);
	      }
	    }

	    System.out.println("Out Balance.bakFiles()!");
	  }
	
	/*
	 * 设置对账日期
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
		mLogger.info("建行对账程序启动。。。");

		CCBBusiBlc ccbAES = new CCBBusiBlc();

		// 用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * 严格日期校验的正则表达式：\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}-\\d{2}-\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
//		cCurDate = "20160309";
		ccbAES.run();
		System.out.println("建行对账程序完成。。。");
	}

}
