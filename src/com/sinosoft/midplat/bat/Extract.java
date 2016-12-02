package com.sinosoft.midplat.bat;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.Service;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
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

public abstract class Extract extends TimerTask
  implements XmlTag
{
  protected final Logger cLogger = Logger.getLogger(getClass());
  private final XmlConf cThisConf;
  private final int cFuncFlag;
  protected Date cTranDate;
  protected String cResultMsg;
  protected Element cMidplatRoot = null;
  protected Element cThisConfRoot = null;
  protected Element cThisBusiConf = null;

  public Extract(XmlConf pThisConf, String pFuncFlag) {
    this(pThisConf, Integer.parseInt(pFuncFlag));
  }

  public Extract(XmlConf pThisConf, int pFuncFlag) {
    this.cThisConf = pThisConf;
    this.cFuncFlag = pFuncFlag;
  }

  public void run()
  {
    Thread.currentThread().setName(
      String.valueOf(NoFactory.nextTranLogNo()));
    this.cLogger.info("Into Extract.run()...");

    this.cResultMsg = null;
    try
    {
      if (this.cTranDate == null) {
        this.cTranDate = new Date();
      }

      this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
      this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
      this.cThisBusiConf = ((Element)XPath.selectSingleNode(
        this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));

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
      this.cLogger.info("ҵ����ģ�飺" + tServiceClassName);
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
      Document tOutStdXml = tService.service(tInStdXml);

      this.cResultMsg = tOutStdXml.getRootElement().getChild(
        "Head").getChildText("Desc");

      byte[] tUpBytes = parse(tOutStdXml);

      String tFileName = getFileName();
      this.cLogger.info("FileName = " + tFileName);

      String tLocalPath = null;
      String tLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
      if ((tLocalDir != null) && (!"".equals(tLocalDir))) {
        tLocalDir = tLocalDir.replace('\\', '/');
        if (!tLocalDir.endsWith("/")) {
          tLocalDir = tLocalDir + '/';
        }
        tLocalPath = tLocalDir + tFileName;
      }
      this.cLogger.info("LocalPath = " + tLocalPath);
      FileOutputStream tBakFos = null;
      try {
        tBakFos = new FileOutputStream(tLocalPath);
        tBakFos.write(tUpBytes);
      } catch (Exception ex) {
        this.cLogger.warn("δ��ȷ���ñ��ر���Ŀ¼��ֹͣ���ݲ��� ��", ex);

        if (tBakFos != null)
          try {
            tBakFos.close();
          } catch (Exception ex1) {
            this.cLogger.error("�ر����쳣��", ex1);
          }
      }
      finally
      {
        if (tBakFos != null) {
          try {
            tBakFos.close();
          } catch (Exception ex) {
            this.cLogger.error("�ر����쳣��", ex);
          }
        }

      }

      ftpUpload(tUpBytes, tFileName);

      ending(tOutStdXml);

      if ("01".equals(DateUtil.getDateStr(this.cTranDate, "dd")))
        bakFiles(this.cThisBusiConf.getChildTextTrim("localDir"));
    }
    catch (Throwable ex) {
      this.cLogger.error("���׳���", ex);
      this.cResultMsg = ex.toString();
    }

    this.cTranDate = null;

    this.cLogger.info("Out Extract.run()!");
  }

  protected Element getHead() {
    this.cLogger.info("Into Extract.getHead()...");

    Element mTranDate = new Element("TranDate");
    mTranDate.setText(DateUtil.getDateStr(this.cTranDate, "yyyyMMdd"));

    Element mTranTime = new Element("TranTime");
    mTranTime.setText(DateUtil.getDateStr(this.cTranDate, "HHmmss"));

    Element mTranCom = (Element)this.cThisConfRoot.getChild("TranCom").clone();

    Element mNodeNo = (Element)this.cThisBusiConf.getChild("NodeNo").clone();

    Element mTellerNo = new Element("TellerNo");
    mTellerNo.setText("sys");

    Element mTranNo = new Element("TranNo");
    mTranNo.setText(getFileName());

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

    this.cLogger.info("Out Extract.getHead()!");
    return mHead;
  }

  protected byte[] parse(Document pOutStdXml) throws Exception {
    this.cLogger.info("Into Extract.parse()...");

    String mCharset = this.cThisBusiConf.getChildText("charset");
    if ((mCharset == null) || ("".equals(mCharset))) {
      mCharset = "GBK";
    }

    String mBatDataStr = 
      pOutStdXml.getRootElement().getChildText("Body");

    this.cLogger.info("Out Extract.parse()!");
    return mBatDataStr.getBytes(mCharset);
  }

  private void ftpUpload(byte[] pUpBytes, String pFileName) throws Exception {
    this.cLogger.debug("Into Extract.ftpUpload()...");

    Element mFtpEle = this.cThisBusiConf.getChild("ftp");

    String mFtpIp = mFtpEle.getAttributeValue("ip");
    this.cLogger.debug("ftp.ip = " + mFtpIp);
    if ((mFtpIp == null) || (mFtpIp.equals(""))) {
      throw new MidplatException("δ�����ϴ�ftp��ip��");
    }

    String mFtpPort = mFtpEle.getAttributeValue("port");
    if ((mFtpPort == null) || (mFtpPort.equals(""))) {
      mFtpPort = "21";
    }
    this.cLogger.debug("ftp.port = " + mFtpPort);

    String mFtpUser = mFtpEle.getAttributeValue("user");
    this.cLogger.debug("ftp.user = " + mFtpUser);

    String mFtpPassword = mFtpEle.getAttributeValue("password");
    this.cLogger.debug("ftp.password = " + mFtpPassword);

    int mReConn = 5;
    String mAttrValue = mFtpEle.getAttributeValue("reconn");
    if ((mAttrValue != null) && (!"".equals(mAttrValue))) {
      try {
        mReConn = Integer.parseInt(mAttrValue);
      } catch (Exception ex) {
        this.cLogger.warn("δ��ȷ����ftp����ظ����Ӵ�����������ϵͳĬ��ֵ��");
      }
    }
    this.cLogger.debug("ftp.reconn = " + mReConn);

    int mTimeout = 300;
    mAttrValue = mFtpEle.getAttributeValue("timeout");
    if ((mAttrValue != null) && (!"".equals(mAttrValue))) {
      try {
        mTimeout = Integer.parseInt(mAttrValue);
      } catch (Exception ex) {
        this.cLogger.warn("δ��ȷ����ftp��ʱ������ϵͳĬ��ֵ��");
      }
    }
    this.cLogger.debug("ftp.timeout = " + mTimeout + "s");

    FTPClient mFTPClient = new FTPClient();
    mFTPClient.setDefaultPort(Integer.parseInt(mFtpPort));
    mFTPClient.setDefaultTimeout(mTimeout * 1000);
    boolean mHasUpload = false;
    for (int i = 1; (i <= mReConn) && (!mHasUpload); i++) {
      this.cLogger.info("------" + i + "------------");
      try
      {
        mFTPClient.connect(mFtpIp);
        int tReplyCode = mFTPClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(tReplyCode)) {
          this.cLogger.error("ftp����ʧ�ܣ�" + mFTPClient.getReplyString());
          throw new MidplatException("ftp����ʧ�ܣ�" + mFtpIp + ": " + tReplyCode);
        }
        this.cLogger.info("ftp���ӳɹ���" + mFtpIp);

        if (!mFTPClient.login(mFtpUser, mFtpPassword)) {
          this.cLogger.error("ftp��¼ʧ�ܣ�" + mFTPClient.getReplyString());
          throw new MidplatException("ftp��¼ʧ�ܣ�" + mFtpUser + ":" + mFtpPassword);
        }
        this.cLogger.info("ftp��¼�ɹ���");

        if (mFTPClient.setFileType(2))
          this.cLogger.info("���ö����ƴ��䣡");
        else {
          this.cLogger.warn("���ô���ģʽΪ������ʧ�ܣ�" + mFTPClient.getReplyString());
        }

        String tFtpPath = mFtpEle.getAttributeValue("path");
        if ((tFtpPath != null) && (!"".equals(tFtpPath)) && 
          (!mFTPClient.changeWorkingDirectory(tFtpPath))) {
          this.cLogger.warn("�л�ftp����Ŀ¼ʧ�ܣ�" + tFtpPath + "; " + mFTPClient.getReplyString());
        }

        this.cLogger.debug("CurWorkingDir = " + mFTPClient.printWorkingDirectory());

        mHasUpload = mFTPClient.storeFile(pFileName, new ByteArrayInputStream(pUpBytes));
        if (mHasUpload)
          this.cLogger.info("ftp�ϴ����ݳɹ���");
        else {
          this.cLogger.warn("ftp�ϴ�����ʧ�ܣ�" + mFTPClient.getReplyString());
        }

        mFTPClient.logout();
        this.cLogger.info("ftp�˳��ɹ���");
      } catch (SocketTimeoutException ex) {
        this.cLogger.warn("ftp��������Ӧ��ʱ�������������ӣ�");

        if (mFTPClient.isConnected())
          try {
            mFTPClient.disconnect();
            this.cLogger.info("ftp���ӶϿ���");
          } catch (IOException ex1) {
            this.cLogger.warn("����������ѶϿ���", ex1);
          }
      }
      finally
      {
        if (mFTPClient.isConnected()) {
          try {
            mFTPClient.disconnect();
            this.cLogger.info("ftp���ӶϿ���");
          } catch (IOException ex) {
            this.cLogger.warn("����������ѶϿ���", ex);
          }
        }
      }
    }

    if (!mHasUpload) {
      throw new MidplatException("ftp�ϴ�����ʧ�ܣ�" + pFileName);
    }

    this.cLogger.debug("Out Extract.ftpUpload()!");
  }

  private void bakFiles(String pFileDir) {
    this.cLogger.info("Into Extract.bakFiles()...");

    if ((pFileDir == null) || ("".equals(pFileDir))) {
      this.cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
      return;
    }
    File mDirFile = new File(pFileDir);
    if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
      this.cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
      return;
    }

    File[] mOldFiles = mDirFile.listFiles(
      new FileFilter() {
      public boolean accept(File pFile) {
        if (!pFile.isFile()) {
          return false;
        }

        Calendar tCurCalendar = Calendar.getInstance();
        tCurCalendar.setTime(Extract.this.cTranDate);

        Calendar tFileCalendar = Calendar.getInstance();
        tFileCalendar.setTime(new Date(pFile.lastModified()));

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
        this.cLogger.error(tFile.getAbsoluteFile() + "����ʧ�ܣ�", ex);
      }
    }

    this.cLogger.info("Out Extract.bakFiles()!");
  }

  public final void setDate(Date pDate) {
    this.cTranDate = pDate;
  }

  public final void setDate(String p8DateStr) {
    this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
  }

  protected abstract String getFileName();

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