package com.sinosoft.midplat.newccb.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CError;

public class RegisterFtp {
	protected final Logger cLogger = Logger.getLogger(getClass());
	public static FTPClient ftpClient =  null;
	private static String Host = null; 
	private static String UserName = null;
	private static String Password = null;
	private static int Port = 21;
	public RegisterFtp(String host,String username,String password)
	{
		this.Host= host;
		this.UserName= username;
		this.Password=password;
	}
	/**
	 *  ��¼FTP 
	 */
	public boolean loginFTP() {
		boolean flag = true;
		ftpClient = new FTPClient();
		ftpClient.setDefaultPort(21);
		int reply;
		try {
			ftpClient.connect(Host);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)){
				ftpClient.disconnect();
				throw new MidplatException("FTP����������ʧ��!");
			}
			boolean loginFlag=ftpClient.login(UserName, Password);
            if(!loginFlag){
            	throw new MidplatException("FTP��������¼ʧ��!");
            }
//			 ���ļ�������������Ϊ������
		    if(!(ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE))){
		    	throw new MidplatException("FTP�������ö����ƴ���ʧ��!");
		    }
		    
		}catch (Exception e){
			flag = false;
			e.printStackTrace();
		}
		cLogger.info("��½ftp�������ɹ�"+Host);
		return flag;
	}
	/*
	 * ��Զ���ļ����з���
	 * */
	public FTPFile[] checkRemoteFile(String cFileDir)
	{  
		FTPFile[] files=null;
		try{
			cLogger.info("����Զ���ļ��У�"+cFileDir);
			boolean tFlag = ftpClient.changeWorkingDirectory(cFileDir);
			if (!tFlag) {
				throw new MidplatException("����Զ���ļ��в�����!");
			}
			files = ftpClient.listFiles();
		}catch(Exception e){
			e.printStackTrace();
			cLogger.info("���Զ���ļ��г����쳣!"+e.getMessage());
		}finally{
			closeConnect();
		}
		return files;
	}
	/**
	 * �ر�����
	 */
	public  void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.disconnect();
				ftpClient=null;
				cLogger.info("�ɹ��ر�FTP����!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean downloadFile(String remoteFilePath,String fileName,String localFilePath){
		boolean downloadFileFlag=true;
		try {
			boolean tFlag = ftpClient.changeWorkingDirectory(remoteFilePath);
			if(!tFlag){
				throw new MidplatException("�л�����Ŀ¼ʧ��!");
			}
			FileOutputStream fileOutputStream=new FileOutputStream(localFilePath+fileName);
			if(!ftpClient.retrieveFile(fileName,fileOutputStream)){
				throw new MidplatException("�����ļ�ʧ��!");
			}
			cLogger.info("�����ļ�"+fileName+"�ɹ�");
		} catch (Exception e) {
			downloadFileFlag=false;
			e.printStackTrace();
			cLogger.info("�����ļ�"+fileName+"�����쳣!"+e.getMessage());
		}
		return downloadFileFlag;
	}
	public boolean uploadFile(String remoteFilePath,String fileNamePath,String fileName){
		boolean uploadFileFlag=true;
		try {
			boolean tFlag = ftpClient.changeWorkingDirectory(remoteFilePath);
			if(!tFlag){
				throw new MidplatException("�л��ϴ�Ŀ¼ʧ��!");
			}
			FileInputStream fileInputStream=new FileInputStream(fileNamePath);
			if(!ftpClient.storeFile(fileName, fileInputStream)){
				throw new MidplatException("�ϴ��ļ�ʧ��:"+fileName);
			}
			cLogger.info("�ϴ��ļ��ɹ�:"+fileName);
		} catch (Exception e) {
			uploadFileFlag=false;
			e.printStackTrace();
			cLogger.info("�ϴ��ļ�"+fileName+"�����쳣!"+e.getMessage());
		}finally{
			//�ر�FTP����
			closeConnect();
		}
		return uploadFileFlag;
	}
	public static void main(String[] args) {
		RegisterFtp ftp=new RegisterFtp("10.1.3.67","lisftp","lisftp_3132");
		ftp.loginFTP();
//		ftp.downloadFile("/uploadfile/2017-03-11", "200331000003945_F0220170119_00258.zip","G:\\");
		ftp.uploadFile("/downloadfile", "G:\\zip\\200331000003945_F0220170119_00258.zip","200331000003945_F0220170119_00258.zip");
	
	}
     public static void main1(String[] args)  {
		   FTPClient ftpClient=new FTPClient();
		   ftpClient.setDefaultPort(21);
//		   ftpClient.setDefaultTimeout(1000*12);
		   try {
			ftpClient.connect("10.2.0.31");
			int replycode=ftpClient.getReplyCode();
			System.out.println("replycode:"+replycode);
			if(!FTPReply.isPositiveCompletion(replycode)){
				System.out.println("��¼10.2.0.31������ftpʧ�ܣ�");
			}
			System.out.println("����10.2.0.31������ftp�ɹ���");
			Boolean flag=null;
			if(!(flag=ftpClient.login("gzb","gzb1234"))){
		      System.out.println("��¼10.2.0.31������ftpʧ�ܣ�");
			}
			System.out.println("��¼10.2.0.31������ftp�ɹ���"+flag);
			if(ftpClient.setFileType(FTP.BINARY_FILE_TYPE)){
				System.out.println("���ö����ƴ���ɹ�"+ftpClient.getReplyString());
			}else{
				System.out.println("���ö����ƴ���ʧ��");
			}
			if(ftpClient.changeWorkingDirectory("/downloadfile")){
				System.out.println("�л�/downloadfile����Ŀ¼�ɹ���");
			}else{
				System.out.println("�л�/downloadfile����Ŀ¼ʧ�ܣ�");
			}
//			ftpClient.enterLocalActiveMode();
//			InputStream in=ftpClient.retrieveFileStream("11914_632_1_outSvc.xml");
//			System.out.println(in);
			String[] names=ftpClient.listNames();
			for (String name : names) {
				System.out.println("file--"+name);
			}
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			if(ftpClient.retrieveFile("200331000003945_F0220161108_00032.zip",byteArrayOutputStream)){
				System.out.println("ftp�������ݳɹ���");
			}else{
				System.out.println("ftp��������ʧ�ܣ�");
			}
			String path="C:\\Users\\anico\\Desktop\\midplat\\200331000003945_F0220161108_00032.zip";
			FileOutputStream out=new FileOutputStream(path);
			out.write(byteArrayOutputStream.toByteArray());
			out.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
