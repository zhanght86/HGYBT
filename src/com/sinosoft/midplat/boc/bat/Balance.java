/*
 * 从总行ftp上读取对账文件
 */
package com.sinosoft.midplat.boc.bat;

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

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.boc.BocConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.Service;

public abstract class Balance extends TimerTask implements XmlTag {
	protected final Logger cLogger = Logger.getLogger(getClass());

	private final XmlConf cThisConf;
	private final int cFuncFlag;	//交易代码
	
	/**
	 * 提供一个全局访问点，只在每次对账开始时初始化，
	 * 确保在该次对账处理的整个过程中日期一致性，
	 * 不受跨天(前面的处理在0点前，后面的在0点后)的影响。
	 */
	protected Date cTranDate;
	
//	protected String cResultMsg;
	
	/**
	 * 提供一个全局访问点，只在每次对账开始时重新初始化，
	 * 确保在该次对账处理的整个过程中配置一致性，
	 * 不受配置文件自动加载的影响。也就是说，本次定时任务一旦启动，
	 * 其后配置文件的修改将会在下一次批跑时生效，不影响本次。
	 */
	protected Element cMidplatRoot = null;
	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	
	public Balance(XmlConf pThisConf, String pFuncFlag) {
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}
	
	public Balance(XmlConf pThisConf, int pFuncFlag) {
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}
	
	/**
	 * 为保证对账Timer不会因为某天的一次异常而终止，这里必须捕获run()中的所有异常。
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		Thread.currentThread().setName(
				String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into Balance.run()...");
		
		//清空上一次结果信息
//		cResultMsg = null;
		
		try {
			if (null == cTranDate) {
				cTranDate = new Date();
			}
			
			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			cThisConfRoot = cThisConf.getConf().getRootElement();
			cThisBusiConf = (Element) XPath.selectSingleNode(
					cThisConfRoot, "business[funcFlag='"+cFuncFlag+"']");
			
			try {
				String ttFileName = getFileName();
				cLogger.info("FileName = "+ttFileName);
				String ttLocalDir = cThisBusiConf.getChildTextTrim(localDir);
				Element ttFtpEle = cThisBusiConf.getChild(ftp);
				InputStream ttBatIns = null;
				if (null != ttFtpEle) {	//未配置localDir，如果ftp有配置，通过ftp取文件
					ttBatIns = getFtpFile(ttFtpEle, ttFileName, ttLocalDir);
				} else if (null!=ttLocalDir && !ttLocalDir.equals("")) {	//如果localDir有配置，优先取本地文件
					ttBatIns = getLocalFile(ttLocalDir, ttFileName);
				} else {	//localDir和ftp都未配置，报错
					throw new MidplatException("对账配置有误！");
				}
				
			} catch (Exception ex) {
				cLogger.error("down文件出错！", ex);
			}
			
			//调用业务处理，获取标准返回报文
//			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
//			//若midplat.xml中有非空默认配置，采用该配置
//			String tServiceValue = cMidplatRoot.getChildText(service);
//			if (null!=tServiceValue && !"".equals(tServiceValue)) {
//				tServiceClassName = tServiceValue;
//			}
//			//若子系统的个性化配置文件中有非空默认配置，采用该配置
//			tServiceValue = cThisConfRoot.getChildText(service);
//			if (null!=tServiceValue && !"".equals(tServiceValue)) {
//				tServiceClassName = tServiceValue;
//			}
//			tServiceValue = cThisBusiConf.getChildText(service);
//			if (null!=tServiceValue && !"".equals(tServiceValue)) {
//				tServiceClassName = tServiceValue;
//			}
//			cLogger.info("业务处理模块：" + tServiceClassName);
//			Constructor<Service> tServiceConstructor = (Constructor<Service>) Class.forName(
//					tServiceClassName).getConstructor(new Class[]{Element.class});
//			Service tService = tServiceConstructor.newInstance(new Object[]{cThisBusiConf});
			
			
			//每月1日备份上月的对账文件
//			if ("01".equals(DateUtil.getDateStr(cTranDate, "dd"))) {
//				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
//			}
		} catch (Throwable ex) {
			cLogger.error("交易出错！", ex);
		}
		
		cTranDate = null;	//每次跑完，清空日期
		
		cLogger.info("Out Balance.run()!");
	}
	
	private InputStream getFtpFile(Element pFtpEle, String pFileName, String pLocalDir) throws Exception {
		cLogger.info("Into Balance.getFtpFile()...");
		
		String mFtpIp = pFtpEle.getAttributeValue(ip);
		cLogger.debug("ftp.ip = " + mFtpIp);
		if (null==mFtpIp || mFtpIp.equals("")) {
			throw new MidplatException("未配置上传ftp的ip！");
		}
		
		String mFtpPort = pFtpEle.getAttributeValue(port);
		if (null==mFtpPort || mFtpPort.equals("")) {
			mFtpPort = "21";
		}
		cLogger.debug("ftp.port = " + mFtpPort);
		
		String mFtpUser = pFtpEle.getAttributeValue(user);
		cLogger.debug("ftp.user = " + mFtpUser);
		
		String mFtpPassword = pFtpEle.getAttributeValue(password);
		cLogger.debug("ftp.password = " + mFtpPassword);
		
		//重复连接次数
		int mReConn = 5;
		String mAttrValue = pFtpEle.getAttributeValue(reconn);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mReConn = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("未正确配置ftp最大重复连接次数，将采用系统默认值！");
			}
		}
		cLogger.debug("ftp.reconn = " + mReConn);
		
		//连接超时，默认为5分钟
		int mTimeout = 5 * 60;
		mAttrValue = pFtpEle.getAttributeValue(timeout);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mTimeout = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("未正确配置ftp超时，采用系统默认值！");
			}
		}
		cLogger.debug("ftp.timeout = " + mTimeout + "s");
		
		String mLocalPath = null;
		if (null!=pLocalDir && !"".equals(pLocalDir)) {
			pLocalDir = pLocalDir.replace('\\', '/');
			if (!pLocalDir.endsWith("/")) {
				pLocalDir += '/';
			}
			mLocalPath = pLocalDir + pFileName;
		}
		cLogger.info("LocalPath = " + mLocalPath);
		
		//ftp到银行
		FTPClient mFTPClient = new FTPClient();
		mFTPClient.setDefaultPort(Integer.parseInt(mFtpPort));
		mFTPClient.setDefaultTimeout(mTimeout * 1000);	//设置超时
		InputStream pBatIs = null;
		for (int i = 1; (i<=mReConn) && (null==pBatIs); i++) {
			cLogger.info("------" + i + "------------");
			try {
				//建立ftp连接
				mFTPClient.connect(mFtpIp);
				int tReplyCode = mFTPClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(tReplyCode)) {
					cLogger.error("ftp连接失败！" + mFTPClient.getReplyString());
					throw new MidplatException("ftp连接失败！" + mFtpIp + ": " + tReplyCode);
				}
				cLogger.info("ftp连接成功！" + mFtpIp);
				
				//登录
				if (!mFTPClient.login(mFtpUser, mFtpPassword)) {
					cLogger.error("ftp登录失败！" + mFTPClient.getReplyString());
					throw new MidplatException("ftp登录失败！" + mFtpUser + ":" + mFtpPassword);
				}
				cLogger.info("ftp登录成功！");
				
				//二进制传输
				if (mFTPClient.setFileType(FTP.BINARY_FILE_TYPE)) {
					cLogger.info("采用二进制传输！");
				} else {
					cLogger.warn("设置传输模式为二进制失败！" + mFTPClient.getReplyString());
				}
				
				String tFtpPath = pFtpEle.getAttributeValue(path);
				if (null!=tFtpPath && !"".equals(tFtpPath)) {
					if (!mFTPClient.changeWorkingDirectory(tFtpPath)) {
						cLogger.warn("切换ftp工作目录失败！" + tFtpPath + "; " + mFTPClient.getReplyString());
					}
				}
				cLogger.debug("CurWorkingDir = " + mFTPClient.printWorkingDirectory());
				
				//下载数据
				FileOutputStream tLocalFos = null;
				try {
					tLocalFos = new FileOutputStream(mLocalPath);
				} catch (Exception ex) {
					cLogger.warn("未正确配置ftp文件本地备份目录，将停止备份操作，直接进行对账 ！", ex);
				}
				if (null == tLocalFos) {	//未正确设置备份目录，直接使用ftp流进行对账
					ByteArrayOutputStream ttBaos = new ByteArrayOutputStream();
					if (mFTPClient.retrieveFile(pFileName, ttBaos)) {
						cLogger.info("ftp下载数据成功！");
						pBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
					} else {
						cLogger.warn("ftp下载数据失败！" + mFTPClient.getReplyString());
					}
				} else {
					if (mFTPClient.retrieveFile(pFileName, tLocalFos)) {
						cLogger.info("ftp下载数据成功！" + mLocalPath);
						pBatIs = new FileInputStream(mLocalPath);
					} else {
						cLogger.warn("ftp下载数据失败！" + mFTPClient.getReplyString());
					}
					tLocalFos.close();
				}
				
				//退出登陆
				mFTPClient.logout();
				cLogger.info("ftp退出成功！");
			} catch (SocketTimeoutException ex) {	//超时，重新连接
				cLogger.warn("ftp服务器响应超时，尝试重新连接！");
			} finally {
				if (mFTPClient.isConnected()) {
					try {
						mFTPClient.disconnect();
						cLogger.info("ftp连接断开！");
					} catch(IOException ex) {
						cLogger.warn("服务端连接已断开！", ex);
					}
				}
			}
		}
		
		if (null == pBatIs) {
			throw new MidplatException("未找到对账文件！" + pFileName);
		}
		
		cLogger.info("Out Balance.getFtpFile()!");
		return pBatIs;
	}
	
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException {
		cLogger.info("Into Balance.getLocalFile()...");
		
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")) {
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);
		
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(mPathName);
		} catch (IOException ex) {
			//未找到对账文件
			throw new MidplatException("未找到对账文件！" + mPathName);
		}
		
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}
	
	private void bakFiles(String pFileDir) {
		cLogger.info("Into Balance.bakFiles()...");
		
		if (null==pFileDir || "".equals(pFileDir)) {
			cLogger.warn("本地文件目录为空，不进行备份操作！");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory()) {
			cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
			return;
		}
		
		File[] mOldFiles = mDirFile.listFiles(
				new FileFilter(){
					public boolean accept(File pFile) {
						if (!pFile.isFile()) {
							return false;
						}
						
						Calendar tCurCalendar = Calendar.getInstance();
						tCurCalendar.setTime(cTranDate);
						
						Calendar tFileCalendar = Calendar.getInstance();
						tFileCalendar.setTime(new Date(pFile.lastModified()));
						
						return tFileCalendar.before(tCurCalendar);
					}
				});
		
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = 
			new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles) {
			cLogger.info(tFile.getAbsoluteFile() + " start move...");
			try {
				IOTrans.fileMove(tFile, mNewDir);
				cLogger.info(tFile.getAbsoluteFile() + " end move!");
			} catch (IOException ex) {
				cLogger.error(tFile.getAbsoluteFile()+"备份失败！", ex);
			}
		}
		
		cLogger.info("Out Balance.bakFiles()!");
	}
	
	public final void setDate(Date pDate) {
		cTranDate = pDate;
	}
	
	public final void setDate(String p8DateStr) {
		cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}
	
	/**
	 * 根据对账文件命名规则，生成当天对账文件名
	 */
	protected abstract String getFileName();
	
	/**
	 * 该方法会在调用完后台服务之后被调用。
	 * 默认是空实现，在一些特殊交易中可以用来做一些个性化的额外后续处理，
	 * 常见的情况：工行非实时核保对账，在调用完后台后，进行文件上传动作，即可在此方法中实现。
	 * @param Document pOutStdXml: 后台返回的标准报文。
	 */
	protected void ending(Document pOutStdXml) throws Exception {
		cLogger.info("Into Balance.ending()...");
		
		cLogger.info("do nothing, just out!");
		
		cLogger.info("Out Balance.ending()!");
	}
	
}
