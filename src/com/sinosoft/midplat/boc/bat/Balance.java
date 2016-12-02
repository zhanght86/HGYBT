/*
 * ������ftp�϶�ȡ�����ļ�
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
	private final int cFuncFlag;	//���״���
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	protected Date cTranDate;
	
//	protected String cResultMsg;
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ���³�ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���������ļ��Զ����ص�Ӱ�졣Ҳ����˵�����ζ�ʱ����һ��������
	 * ��������ļ����޸Ľ�������һ������ʱ��Ч����Ӱ�챾�Ρ�
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
	 * Ϊ��֤����Timer������Ϊĳ���һ���쳣����ֹ��������벶��run()�е������쳣��
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		Thread.currentThread().setName(
				String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into Balance.run()...");
		
		//�����һ�ν����Ϣ
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
				if (null != ttFtpEle) {	//δ����localDir�����ftp�����ã�ͨ��ftpȡ�ļ�
					ttBatIns = getFtpFile(ttFtpEle, ttFileName, ttLocalDir);
				} else if (null!=ttLocalDir && !ttLocalDir.equals("")) {	//���localDir�����ã�����ȡ�����ļ�
					ttBatIns = getLocalFile(ttLocalDir, ttFileName);
				} else {	//localDir��ftp��δ���ã�����
					throw new MidplatException("������������");
				}
				
			} catch (Exception ex) {
				cLogger.error("down�ļ�����", ex);
			}
			
			//����ҵ������ȡ��׼���ر���
//			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
//			//��midplat.xml���зǿ�Ĭ�����ã����ø�����
//			String tServiceValue = cMidplatRoot.getChildText(service);
//			if (null!=tServiceValue && !"".equals(tServiceValue)) {
//				tServiceClassName = tServiceValue;
//			}
//			//����ϵͳ�ĸ��Ի������ļ����зǿ�Ĭ�����ã����ø�����
//			tServiceValue = cThisConfRoot.getChildText(service);
//			if (null!=tServiceValue && !"".equals(tServiceValue)) {
//				tServiceClassName = tServiceValue;
//			}
//			tServiceValue = cThisBusiConf.getChildText(service);
//			if (null!=tServiceValue && !"".equals(tServiceValue)) {
//				tServiceClassName = tServiceValue;
//			}
//			cLogger.info("ҵ����ģ�飺" + tServiceClassName);
//			Constructor<Service> tServiceConstructor = (Constructor<Service>) Class.forName(
//					tServiceClassName).getConstructor(new Class[]{Element.class});
//			Service tService = tServiceConstructor.newInstance(new Object[]{cThisBusiConf});
			
			
			//ÿ��1�ձ������µĶ����ļ�
//			if ("01".equals(DateUtil.getDateStr(cTranDate, "dd"))) {
//				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
//			}
		} catch (Throwable ex) {
			cLogger.error("���׳���", ex);
		}
		
		cTranDate = null;	//ÿ�����꣬�������
		
		cLogger.info("Out Balance.run()!");
	}
	
	private InputStream getFtpFile(Element pFtpEle, String pFileName, String pLocalDir) throws Exception {
		cLogger.info("Into Balance.getFtpFile()...");
		
		String mFtpIp = pFtpEle.getAttributeValue(ip);
		cLogger.debug("ftp.ip = " + mFtpIp);
		if (null==mFtpIp || mFtpIp.equals("")) {
			throw new MidplatException("δ�����ϴ�ftp��ip��");
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
		
		//�ظ����Ӵ���
		int mReConn = 5;
		String mAttrValue = pFtpEle.getAttributeValue(reconn);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mReConn = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("δ��ȷ����ftp����ظ����Ӵ�����������ϵͳĬ��ֵ��");
			}
		}
		cLogger.debug("ftp.reconn = " + mReConn);
		
		//���ӳ�ʱ��Ĭ��Ϊ5����
		int mTimeout = 5 * 60;
		mAttrValue = pFtpEle.getAttributeValue(timeout);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mTimeout = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("δ��ȷ����ftp��ʱ������ϵͳĬ��ֵ��");
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
		
		//ftp������
		FTPClient mFTPClient = new FTPClient();
		mFTPClient.setDefaultPort(Integer.parseInt(mFtpPort));
		mFTPClient.setDefaultTimeout(mTimeout * 1000);	//���ó�ʱ
		InputStream pBatIs = null;
		for (int i = 1; (i<=mReConn) && (null==pBatIs); i++) {
			cLogger.info("------" + i + "------------");
			try {
				//����ftp����
				mFTPClient.connect(mFtpIp);
				int tReplyCode = mFTPClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(tReplyCode)) {
					cLogger.error("ftp����ʧ�ܣ�" + mFTPClient.getReplyString());
					throw new MidplatException("ftp����ʧ�ܣ�" + mFtpIp + ": " + tReplyCode);
				}
				cLogger.info("ftp���ӳɹ���" + mFtpIp);
				
				//��¼
				if (!mFTPClient.login(mFtpUser, mFtpPassword)) {
					cLogger.error("ftp��¼ʧ�ܣ�" + mFTPClient.getReplyString());
					throw new MidplatException("ftp��¼ʧ�ܣ�" + mFtpUser + ":" + mFtpPassword);
				}
				cLogger.info("ftp��¼�ɹ���");
				
				//�����ƴ���
				if (mFTPClient.setFileType(FTP.BINARY_FILE_TYPE)) {
					cLogger.info("���ö����ƴ��䣡");
				} else {
					cLogger.warn("���ô���ģʽΪ������ʧ�ܣ�" + mFTPClient.getReplyString());
				}
				
				String tFtpPath = pFtpEle.getAttributeValue(path);
				if (null!=tFtpPath && !"".equals(tFtpPath)) {
					if (!mFTPClient.changeWorkingDirectory(tFtpPath)) {
						cLogger.warn("�л�ftp����Ŀ¼ʧ�ܣ�" + tFtpPath + "; " + mFTPClient.getReplyString());
					}
				}
				cLogger.debug("CurWorkingDir = " + mFTPClient.printWorkingDirectory());
				
				//��������
				FileOutputStream tLocalFos = null;
				try {
					tLocalFos = new FileOutputStream(mLocalPath);
				} catch (Exception ex) {
					cLogger.warn("δ��ȷ����ftp�ļ����ر���Ŀ¼����ֹͣ���ݲ�����ֱ�ӽ��ж��� ��", ex);
				}
				if (null == tLocalFos) {	//δ��ȷ���ñ���Ŀ¼��ֱ��ʹ��ftp�����ж���
					ByteArrayOutputStream ttBaos = new ByteArrayOutputStream();
					if (mFTPClient.retrieveFile(pFileName, ttBaos)) {
						cLogger.info("ftp�������ݳɹ���");
						pBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
					} else {
						cLogger.warn("ftp��������ʧ�ܣ�" + mFTPClient.getReplyString());
					}
				} else {
					if (mFTPClient.retrieveFile(pFileName, tLocalFos)) {
						cLogger.info("ftp�������ݳɹ���" + mLocalPath);
						pBatIs = new FileInputStream(mLocalPath);
					} else {
						cLogger.warn("ftp��������ʧ�ܣ�" + mFTPClient.getReplyString());
					}
					tLocalFos.close();
				}
				
				//�˳���½
				mFTPClient.logout();
				cLogger.info("ftp�˳��ɹ���");
			} catch (SocketTimeoutException ex) {	//��ʱ����������
				cLogger.warn("ftp��������Ӧ��ʱ�������������ӣ�");
			} finally {
				if (mFTPClient.isConnected()) {
					try {
						mFTPClient.disconnect();
						cLogger.info("ftp���ӶϿ���");
					} catch(IOException ex) {
						cLogger.warn("����������ѶϿ���", ex);
					}
				}
			}
		}
		
		if (null == pBatIs) {
			throw new MidplatException("δ�ҵ������ļ���" + pFileName);
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
			//δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ������ļ���" + mPathName);
		}
		
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}
	
	private void bakFiles(String pFileDir) {
		cLogger.info("Into Balance.bakFiles()...");
		
		if (null==pFileDir || "".equals(pFileDir)) {
			cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory()) {
			cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
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
				cLogger.error(tFile.getAbsoluteFile()+"����ʧ�ܣ�", ex);
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
	 * ���ݶ����ļ������������ɵ�������ļ���
	 */
	protected abstract String getFileName();
	
	/**
	 * �÷������ڵ������̨����֮�󱻵��á�
	 * Ĭ���ǿ�ʵ�֣���һЩ���⽻���п���������һЩ���Ի��Ķ����������
	 * ��������������з�ʵʱ�˱����ˣ��ڵ������̨�󣬽����ļ��ϴ������������ڴ˷�����ʵ�֡�
	 * @param Document pOutStdXml: ��̨���صı�׼���ġ�
	 */
	protected void ending(Document pOutStdXml) throws Exception {
		cLogger.info("Into Balance.ending()...");
		
		cLogger.info("do nothing, just out!");
		
		cLogger.info("Out Balance.ending()!");
	}
	
}
