package com.sinosoft.midplat.boc.bat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
public class BocFileDownload {
	 protected final Logger cLogger = Logger.getLogger(getClass());
	 InputStream getFtpFile(Element pFtpEle, String pFileName, String pLocalDir) throws Exception {
			cLogger.info("Into Balance.getFtpFile()...");
			
			String mFtpIp = pFtpEle.getAttributeValue("ip");
			cLogger.debug("ftp.ip = " + mFtpIp);
			if (null==mFtpIp || mFtpIp.equals("")) {
				throw new MidplatException("δ�����ϴ�ftp��ip��");
			}
			
			String mFtpPort = pFtpEle.getAttributeValue("port");
			if (null==mFtpPort || mFtpPort.equals("")) {
				mFtpPort = "21";
			}
			cLogger.debug("ftp.port = " + mFtpPort);
			
			String mFtpUser = pFtpEle.getAttributeValue("user");
			cLogger.debug("ftp.user = " + mFtpUser);
			
			String mFtpPassword = pFtpEle.getAttributeValue("password");
			cLogger.debug("ftp.password = " + mFtpPassword);
			
			//�ظ����Ӵ���
			int mReConn = 5;
			String reconn = "5";
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
			String timeout = "300";
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
			InputStream mBatIs = null;
			for (int i = 1; (i<=mReConn) && (null==mBatIs); i++) {
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
					
					String tFtpPath = pFtpEle.getAttributeValue("path");
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
							mBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
						} else {
							cLogger.warn("ftp��������ʧ�ܣ�" + mFTPClient.getReplyString());
						}
					} else {
						if (mFTPClient.retrieveFile(pFileName, tLocalFos)) {
							cLogger.info("ftp�������ݳɹ���" + mLocalPath);
							mBatIs = new FileInputStream(mLocalPath);
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
			
			if (null == mBatIs) {
				throw new MidplatException("δ�ҵ������ļ���" + pFileName);
			}
			
			cLogger.info("Out Balance.getFtpFile()!");
			return mBatIs;
		}
	 public static void main(String[] args) throws Exception {
			//��ȡftp������Ϣ
		    Element cConfigEle = BatUtils.getConfigEle("030084");//��ȫ����
			Element ttFtpEle = cConfigEle.getChild("ftp");
			String mFtpIp = ttFtpEle.getAttributeValue("ip");
			System.out.print("================"+mFtpIp);
			InputStream tt = new BocFileDownload().getFtpFile(ttFtpEle,"baoquan.txt","");
			Document mInXmlDoc = JdomUtil.build(tt);
			JdomUtil.print(mInXmlDoc);
			
	 }
}
