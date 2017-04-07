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
				throw new MidplatException("未配置上传ftp的ip！");
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
			
			//重复连接次数
			int mReConn = 5;
			String reconn = "5";
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
			String timeout = "300";
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
			InputStream mBatIs = null;
			for (int i = 1; (i<=mReConn) && (null==mBatIs); i++) {
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
					
					String tFtpPath = pFtpEle.getAttributeValue("path");
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
							mBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
						} else {
							cLogger.warn("ftp下载数据失败！" + mFTPClient.getReplyString());
						}
					} else {
						if (mFTPClient.retrieveFile(pFileName, tLocalFos)) {
							cLogger.info("ftp下载数据成功！" + mLocalPath);
							mBatIs = new FileInputStream(mLocalPath);
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
			
			if (null == mBatIs) {
				throw new MidplatException("未找到对账文件！" + pFileName);
			}
			
			cLogger.info("Out Balance.getFtpFile()!");
			return mBatIs;
		}
	 public static void main(String[] args) throws Exception {
			//获取ftp配置信息
		    Element cConfigEle = BatUtils.getConfigEle("030084");//保全对账
			Element ttFtpEle = cConfigEle.getChild("ftp");
			String mFtpIp = ttFtpEle.getAttributeValue("ip");
			System.out.print("================"+mFtpIp);
			InputStream tt = new BocFileDownload().getFtpFile(ttFtpEle,"baoquan.txt","");
			Document mInXmlDoc = JdomUtil.build(tt);
			JdomUtil.print(mInXmlDoc);
			
	 }
}
