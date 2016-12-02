package com.sinosoft.midplat.bat;

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

public abstract class Balance extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());
	private final XmlConf cThisConf;
	private final int cFuncFlag;
	protected Date cTranDate;
	protected String cResultMsg;
	protected Element cMidplatRoot = null;
	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;

	public Balance(XmlConf pThisConf, String pFuncFlag)
	{
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}

	public Balance(XmlConf pThisConf, int pFuncFlag)
	{
		this.cThisConf = pThisConf;
		this.cFuncFlag = pFuncFlag;
	}

	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		this.cLogger.info("Into Balance.run()...");

		this.cResultMsg = null;
		try
		{
			this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));

			String nextDate = this.cThisBusiConf.getChildText("nextDate");

			if (this.cTranDate == null)
				if ((nextDate != null) && ("Y".equals(nextDate)))
				{
					this.cTranDate = new Date();
					this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
				}
				else
				{
					this.cTranDate = new Date();
				}
			Element tTranData = new Element("TranData");
			Document tInStdXml = new Document(tTranData);

			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			try
			{
				String ttFileName = getFileName();
				this.cLogger.info("FileName = " + ttFileName);
				String ttLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
				Element ttFtpEle = this.cThisBusiConf.getChild("ftp");
				InputStream ttBatIns = null;
				if (ttFtpEle != null)
					ttBatIns = getFtpFile(ttFtpEle, ttFileName, ttLocalDir);
				else if ((ttLocalDir != null) && (!ttLocalDir.equals("")))
					ttBatIns = getLocalFile(ttLocalDir, ttFileName);
				else
				{
					throw new MidplatException("对账配置有误！");
				}

				Element ttBodyEle = parse(ttBatIns);
				tTranData.addContent(ttBodyEle);
			}
			catch (Exception ex)
			{
				this.cLogger.error("生成标准对账报文出错！", ex);

				Element ttError = new Element("Error");
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}

			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";

			String tServiceValue = this.cMidplatRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}

			tServiceValue = this.cThisConfRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			tServiceValue = this.cThisBusiConf.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			this.cLogger.info("业务处理模块：" + tServiceClassName);
			Constructor tServiceConstructor = Class.forName(tServiceClassName).getConstructor(new Class[] { Element.class });
			Service tService = (Service) tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);

			this.cResultMsg = tOutStdXml.getRootElement().getChild("Head").getChildText("Desc");

			ending(tOutStdXml);

			if ("01".equals(DateUtil.getDateStr(this.cTranDate, "dd")))
				bakFiles(this.cThisBusiConf.getChildTextTrim("localDir"));
		}
		catch (Throwable ex)
		{
			this.cLogger.error("交易出错！", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out Balance.run()!");
	}

	protected Element getHead()
	{
		this.cLogger.info("Into Balance.getHead()...");

		Element mTranDate = new Element("TranDate");
		mTranDate.setText(DateUtil.getDateStr(this.cTranDate, "yyyyMMdd"));

		Element mTranTime = new Element("TranTime");
		mTranTime.setText(DateUtil.getDateStr(this.cTranDate, "HHmmss"));

		Element mTranCom = (Element) this.cThisConfRoot.getChild("TranCom").clone();

		Element mNodeNo = (Element) this.cThisBusiConf.getChild("NodeNo").clone();

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

		this.cLogger.info("Out Balance.getHead()!");
		return mHead;
	}

	private InputStream getFtpFile(Element pFtpEle, String pFileName, String pLocalDir) throws Exception
	{
		this.cLogger.info("Into Balance.getFtpFile()...");

		String mFtpIp = pFtpEle.getAttributeValue("ip");
		this.cLogger.debug("ftp.ip = " + mFtpIp);
		if ((mFtpIp == null) || (mFtpIp.equals("")))
		{
			throw new MidplatException("未配置上传ftp的ip！");
		}

		String mFtpPort = pFtpEle.getAttributeValue("port");
		if ((mFtpPort == null) || (mFtpPort.equals("")))
		{
			mFtpPort = "21";
		}
		this.cLogger.debug("ftp.port = " + mFtpPort);

		String mFtpUser = pFtpEle.getAttributeValue("user");
		this.cLogger.debug("ftp.user = " + mFtpUser);

		String mFtpPassword = pFtpEle.getAttributeValue("password");
		this.cLogger.debug("ftp.password = " + mFtpPassword);

		int mReConn = 5;
		String mAttrValue = pFtpEle.getAttributeValue("reconn");
		if ((mAttrValue != null) && (!"".equals(mAttrValue)))
		{
			try
			{
				mReConn = Integer.parseInt(mAttrValue);
			}
			catch (Exception ex)
			{
				this.cLogger.warn("未正确配置ftp最大重复连接次数，将采用系统默认值！");
			}
		}
		this.cLogger.debug("ftp.reconn = " + mReConn);

		int mTimeout = 300;
		mAttrValue = pFtpEle.getAttributeValue("timeout");
		if ((mAttrValue != null) && (!"".equals(mAttrValue)))
		{
			try
			{
				mTimeout = Integer.parseInt(mAttrValue);
			}
			catch (Exception ex)
			{
				this.cLogger.warn("未正确配置ftp超时，采用系统默认值！");
			}
		}
		this.cLogger.debug("ftp.timeout = " + mTimeout + "s");

		String mLocalPath = null;
		if ((pLocalDir != null) && (!"".equals(pLocalDir)))
		{
			pLocalDir = pLocalDir.replace('\\', '/');
			if (!pLocalDir.endsWith("/"))
			{
				pLocalDir = pLocalDir + '/';
			}
			mLocalPath = pLocalDir + pFileName;
		}
		this.cLogger.info("LocalPath = " + mLocalPath);

		FTPClient mFTPClient = new FTPClient();
		mFTPClient.setDefaultPort(Integer.parseInt(mFtpPort));
		mFTPClient.setDefaultTimeout(mTimeout * 1000);
		InputStream mBatIs = null;
		for (int i = 1; (i <= mReConn) && (mBatIs == null); i++)
		{
			this.cLogger.info("------" + i + "------------");
			try
			{
				mFTPClient.connect(mFtpIp);
				int tReplyCode = mFTPClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(tReplyCode))
				{
					this.cLogger.error("ftp连接失败！" + mFTPClient.getReplyString());
					throw new MidplatException("ftp连接失败！" + mFtpIp + ": " + tReplyCode);
				}
				this.cLogger.info("ftp连接成功！" + mFtpIp);

				if (!mFTPClient.login(mFtpUser, mFtpPassword))
				{
					this.cLogger.error("ftp登录失败！" + mFTPClient.getReplyString());
					throw new MidplatException("ftp登录失败！" + mFtpUser + ":" + mFtpPassword);
				}
				this.cLogger.info("ftp登录成功！");

				if (mFTPClient.setFileType(2))
					this.cLogger.info("采用二进制传输！");
				else
				{
					this.cLogger.warn("设置传输模式为二进制失败！" + mFTPClient.getReplyString());
				}

				String tFtpPath = pFtpEle.getAttributeValue("path");
				if ((tFtpPath != null) && (!"".equals(tFtpPath)) && (!mFTPClient.changeWorkingDirectory(tFtpPath)))
				{
					this.cLogger.warn("切换ftp工作目录失败！" + tFtpPath + "; " + mFTPClient.getReplyString());
				}

				this.cLogger.debug("CurWorkingDir = " + mFTPClient.printWorkingDirectory());

				FileOutputStream tLocalFos = null;
				try
				{
					tLocalFos = new FileOutputStream(mLocalPath);
				}
				catch (Exception ex)
				{
					this.cLogger.warn("未正确配置ftp文件本地备份目录，将停止备份操作，直接进行对账 ！", ex);
				}
				if (tLocalFos == null)
				{
					ByteArrayOutputStream ttBaos = new ByteArrayOutputStream();
					if (mFTPClient.retrieveFile(pFileName, ttBaos))
					{
						this.cLogger.info("ftp下载数据成功！");
						mBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
					}
					else
					{
						this.cLogger.warn("ftp下载数据失败！" + mFTPClient.getReplyString());
					}
				}
				else
				{
					if (mFTPClient.retrieveFile(pFileName, tLocalFos))
					{
						this.cLogger.info("ftp下载数据成功！" + mLocalPath);
						mBatIs = new FileInputStream(mLocalPath);
					}
					else
					{
						this.cLogger.warn("ftp下载数据失败！" + mFTPClient.getReplyString());
					}
					tLocalFos.close();
				}

				mFTPClient.logout();
				this.cLogger.info("ftp退出成功！");
			}
			catch (SocketTimeoutException ex)
			{
				this.cLogger.warn("ftp服务器响应超时，尝试重新连接！");

				if (mFTPClient.isConnected())
					try
					{
						mFTPClient.disconnect();
						this.cLogger.info("ftp连接断开！");
					}
					catch (IOException ex1)
					{
						this.cLogger.warn("服务端连接已断开！", ex1);
					}
			}
			finally
			{
				if (mFTPClient.isConnected())
				{
					try
					{
						mFTPClient.disconnect();
						this.cLogger.info("ftp连接断开！");
					}
					catch (IOException ex)
					{
						this.cLogger.warn("服务端连接已断开！", ex);
					}
				}
			}
		}

		if (mBatIs == null)
		{
			throw new MidplatException("未找到对账文件！" + pFileName);
		}

		this.cLogger.info("Out Balance.getFtpFile()!");
		return mBatIs;
	}

	private InputStream getLocalFile(String pDir, String pName) throws MidplatException
	{
		this.cLogger.info("Into Balance.getLocalFile()...");

		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/"))
		{
			pDir = pDir + '/';
		}
		String mPathName = pDir + pName;
		this.cLogger.info("LocalPath = " + mPathName);

		InputStream mIns = null;
		try
		{
			mIns = new FileInputStream(mPathName);
		}
		catch (IOException ex)
		{
			throw new MidplatException("未找到对账文件！" + mPathName);
		}

		this.cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}

	protected Element parse(InputStream pBatIs) throws Exception
	{
		this.cLogger.info("Into Balance.parse()...");

		String mCharset = this.cThisBusiConf.getChildText("charset");
		if ((mCharset == null) || ("".equals(mCharset)))
		{
			mCharset = "GBK";
		}

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		Element mBodyEle = new Element("Body");
		String tLineMsg = "";
		while ((tLineMsg = mBufReader.readLine()) != null)
		{
			this.cLogger.info(tLineMsg);

			String tLineMsg2 = tLineMsg.trim();
			if ("".equals(tLineMsg2))
			{
				this.cLogger.warn("空行，直接跳过，继续下一条！");
			}
			else
			{
				String[] tSubMsgs = tLineMsg2.split("\\|", -1);

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

	private void bakFiles(String pFileDir)
	{
		this.cLogger.info("Into Balance.bakFiles()...");

		if ((pFileDir == null) || ("".equals(pFileDir)))
		{
			this.cLogger.warn("本地文件目录为空，不进行备份操作！");
			return;
		}
		File mDirFile = new File(pFileDir);
		if ((!mDirFile.exists()) || (!mDirFile.isDirectory()))
		{
			this.cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
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
				tCurCalendar.setTime(Balance.this.cTranDate);
				tCurCalendar.set(11, 8);

				Calendar tFileCalendar = Calendar.getInstance();
				tFileCalendar.setTimeInMillis(pFile.lastModified());

				return tFileCalendar.before(tCurCalendar);
			}
		});
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(2, -1);
		File mNewDir = new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles)
		{
			this.cLogger.info(tFile.getAbsoluteFile() + " start move...");
			try
			{
				IOTrans.fileMove(tFile, mNewDir);
				this.cLogger.info(tFile.getAbsoluteFile() + " end move!");
			}
			catch (IOException ex)
			{
				this.cLogger.error(tFile.getAbsoluteFile() + "备份失败！", ex);
			}
		}

		this.cLogger.info("Out Balance.bakFiles()!");
	}

	public final void setDate(Date pDate)
	{
		this.cTranDate = pDate;
	}

	public final void setDate(String p8DateStr)
	{
		this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}

	protected abstract String getFileName();

	protected void ending(Document pOutStdXml) throws Exception
	{
		this.cLogger.info("Into Balance.ending()...");

		this.cLogger.info("do nothing, just out!");

		this.cLogger.info("Out Balance.ending()!");
	}

	public String getResultMsg()
	{
		return this.cResultMsg;
	}
}