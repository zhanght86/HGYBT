package com.sinosoft.midplat.gzbank.bat;
 
 import com.sinosoft.midplat.MidplatConf;
 import com.sinosoft.midplat.common.DateUtil;
 import com.sinosoft.midplat.common.IOTrans;
 import com.sinosoft.midplat.common.NoFactory;
 import com.sinosoft.midplat.common.XmlConf;
 import com.sinosoft.midplat.common.XmlTag;
 import com.sinosoft.midplat.exception.MidplatException;
 import com.sinosoft.midplat.service.Service;
 import java.io.BufferedReader;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileFilter;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.lang.reflect.Constructor;
 import java.net.SocketTimeoutException;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.TimerTask;
 import org.apache.commons.net.ftp.FTPClient;
 import org.apache.commons.net.ftp.FTPReply;
 import org.apache.log4j.Logger;
 import org.jdom.Document;
 import org.jdom.Element;
import org.jdom.xpath.XPath;
 
 public abstract class Balance extends TimerTask
   implements XmlTag
 {
      protected final Logger cLogger = Logger.getLogger(getClass());
   protected final XmlConf cThisConf;
   protected final int cFuncFlag;
   protected Date cTranDate;
   protected String cResultMsg;
      protected Element cMidplatRoot = null;
      protected Element cThisConfRoot = null;
      protected Element cThisBusiConf = null;
 
   public Balance(XmlConf pThisConf, String pFuncFlag) {
        this(pThisConf, Integer.parseInt(pFuncFlag));
   }
 
   public Balance(XmlConf pThisConf, int pFuncFlag) {
        this.cThisConf = pThisConf;
        this.cFuncFlag = pFuncFlag;
   }
 
   public void run()
   {
        Thread.currentThread().setName(
          String.valueOf(NoFactory.nextTranLogNo()));
        this.cLogger.info("Into Balance.run()...");
 
        this.cResultMsg = null;
     try
     {
         this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
         this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
         this.cThisBusiConf = ((Element)XPath.selectSingleNode(
           this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
 
         String nextDate = this.cThisBusiConf.getChildText("nextDate");
 
         if (this.cTranDate == null)
           if ((nextDate != null) && ("Y".equals(nextDate))) {
             this.cTranDate = new Date();
             this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
      } else {
             this.cTranDate = new Date();
      }
         Element tTranData = new Element("TranData");
         Document tInStdXml = new Document(tTranData);
 
         Element tHeadEle = getHead();
         tTranData.addContent(tHeadEle);
 
         String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
 
         String tServiceValue = this.cMidplatRoot.getChildText("service");
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
           tServiceClassName = tServiceValue;
    }
 
         tServiceValue = this.cThisConfRoot.getChildText("service");
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
           tServiceClassName = tServiceValue;
    }
         tServiceValue = this.cThisBusiConf.getChildText("service");
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
           tServiceClassName = tServiceValue;
    }
         this.cLogger.info("业务处理模块" + tServiceClassName);
         Constructor tServiceConstructor = Class.forName(
           tServiceClassName).getConstructor(new Class[] { Element.class });
         Service tService = (Service)tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
         Document tOutStdXml = tService.service(tInStdXml);
 
         this.cResultMsg = tOutStdXml.getRootElement().getChild(
           "Head").getChildText("Desc");
 
         ending(tOutStdXml);
 
         if ("01".equals(DateUtil.getDateStr(this.cTranDate, "dd")))
           bakFiles(this.cThisBusiConf.getChildTextTrim("localDir"));
  }
  catch (Throwable ex) {
         this.cLogger.error("交易出错", ex);
         this.cResultMsg = ex.toString();
  }
 
       this.cTranDate = null;
 
       this.cLogger.info("Out Balance.run()!");
   }
 
   protected Element getHead()throws MidplatException {
       this.cLogger.info("Into Balance.getHead()...");
 
       Element mTranDate = new Element("TranDate");
       mTranDate.setText(DateUtil.getDateStr(this.cTranDate, "yyyyMMdd"));
 
       Element mTranTime = new Element("TranTime");
       mTranTime.setText(DateUtil.getDateStr(this.cTranDate, "HHmmss"));
 
       Element mTranCom = (Element)this.cThisConfRoot.getChild("TranCom").clone();
 
       Element mNodeNo = (Element)this.cThisBusiConf.getChild("NodeNo").clone();
 
       Element mTellerNo = new Element("TellerNo");
       mTellerNo.setText("sys");
 
       Element mTranNo = new Element("TranNo");
       try {
		  mTranNo.setText(getFileName());
	   } catch (Exception e) {
		throw new  MidplatException(e.getMessage()); 
	  }
 
       Element mFuncFlag = new Element("FuncFlag");
       mFuncFlag.setText(this.cThisBusiConf.getChildText("funcFlag"));
 
       Element mHead = new Element("Head");
       mHead.addContent(mTranDate);
       mHead.addContent(mTranTime);
       mHead.addContent(mTranCom);
       mHead.addContent(mNodeNo);
       mHead.addContent(mTellerNo);
       mHead.addContent(mTranNo);
       mHead.addContent(mFuncFlag);
 
       this.cLogger.info("Out Balance.getHead()!");
       return mHead;
   }
 

     protected Element parse(InputStream pBatIs) throws Exception {
       this.cLogger.info("Into Balance.parse()...");

       String mCharset = this.cThisBusiConf.getChildText("charset");
       if ((mCharset == null) || ("".equals(mCharset))) {
         mCharset = "GBK";
       }

       BufferedReader mBufReader = new BufferedReader(
         new InputStreamReader(pBatIs, mCharset));

       Element mBodyEle = new Element("Body");
       String tLineMsg;
       while ((tLineMsg = mBufReader.readLine()) != null) {
         this.cLogger.info(tLineMsg);

         tLineMsg = tLineMsg.trim();
         if ("".equals(tLineMsg)) {
           this.cLogger.warn("空行，直接跳过，继续下一条！");
         }
         else
         {
           String[] tSubMsgs = tLineMsg.split("\\|", -1);

           Element tTranDate = new Element("TranDate");
           tTranDate.setText(tSubMsgs[1]);

           Element tNodeNo = new Element("NodeNo");
           tNodeNo.setText(tSubMsgs[3]);

           Element tTranNo = new Element("TranNo");
           tTranNo.setText(tSubMsgs[5]);

           Element tContNo = new Element("ContNo");
           tContNo.setText(tSubMsgs[6]);

           Element tPrem = new Element("Prem");
           tPrem.setText(tSubMsgs[7]);

           Element tDetail = new Element("Detail");
           tDetail.addContent(tTranDate);
           tDetail.addContent(tNodeNo);
           tDetail.addContent(tTranNo);
           tDetail.addContent(tContNo);
           tDetail.addContent(tPrem);

           mBodyEle.addContent(tDetail);
         }
       }
       mBufReader.close();

       this.cLogger.info("Out Balance.parse()!");
       return mBodyEle;
     }

     private void bakFiles(String pFileDir) {
       this.cLogger.info("Into Balance.bakFiles()...");

       if ((pFileDir == null) || ("".equals(pFileDir))) {
         this.cLogger.warn("本地文件目录为空，不进行备份操作！");
         return;
       }
       File mDirFile = new File(pFileDir);
       if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
         this.cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
         return;
       }

       File[] mOldFiles = mDirFile.listFiles(
         new FileFilter() {
         public boolean accept(File pFile) {
           if (!pFile.isFile()) {
             return false;
           }

           Calendar tCurCalendar = Calendar.getInstance();
           tCurCalendar.setTime(Balance.this.cTranDate);
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
         this.cLogger.info(tFile.getAbsoluteFile() + " start move...");
         try {
           IOTrans.fileMove(tFile, mNewDir);
           this.cLogger.info(tFile.getAbsoluteFile() + " end move!");
         } catch (IOException ex) {
           this.cLogger.error(tFile.getAbsoluteFile() + "备份失败！", ex);
         }
       }

       this.cLogger.info("Out Balance.bakFiles()!");
     }

     public final void setDate(Date pDate) {
       this.cTranDate = pDate;
     }

     public final void setDate(String p8DateStr) {
       this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
     }

     protected abstract String getFileName()throws Exception;

     protected void ending(Document pOutStdXml)
       throws Exception
     {
       this.cLogger.info("Into Balance.ending()...");

       this.cLogger.info("do nothing, just out!");

       this.cLogger.info("Out Balance.ending()!");
     }

     public String getResultMsg() {
       return this.cResultMsg;
     }
   }
