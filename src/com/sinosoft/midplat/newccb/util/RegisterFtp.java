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
	 *  登录FTP 
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
				throw new MidplatException("FTP服务器连接失败!");
			}
			boolean loginFlag=ftpClient.login(UserName, Password);
            if(!loginFlag){
            	throw new MidplatException("FTP服务器登录失败!");
            }
//			 将文件传输类型设置为二进制
		    if(!(ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE))){
		    	throw new MidplatException("FTP连接设置二进制传输失败!");
		    }
		    
		}catch (Exception e){
			flag = false;
			e.printStackTrace();
		}
		cLogger.info("登陆ftp服务器成功"+Host);
		return flag;
	}
	/*
	 * 对远程文件进行分析
	 * */
	public FTPFile[] checkRemoteFile(String cFileDir)
	{  
		FTPFile[] files=null;
		try{
			cLogger.info("检查的远程文件夹："+cFileDir);
			boolean tFlag = ftpClient.changeWorkingDirectory(cFileDir);
			if (!tFlag) {
				throw new MidplatException("检查的远程文件夹不存在!");
			}
			files = ftpClient.listFiles();
		}catch(Exception e){
			e.printStackTrace();
			cLogger.info("检查远程文件夹出现异常!"+e.getMessage());
		}finally{
			closeConnect();
		}
		return files;
	}
	/**
	 * 关闭连接
	 */
	public  void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.disconnect();
				ftpClient=null;
				cLogger.info("成功关闭FTP连接!");
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
				throw new MidplatException("切换下载目录失败!");
			}
			FileOutputStream fileOutputStream=new FileOutputStream(localFilePath+fileName);
			if(!ftpClient.retrieveFile(fileName,fileOutputStream)){
				throw new MidplatException("下载文件失败!");
			}
			cLogger.info("下载文件"+fileName+"成功");
		} catch (Exception e) {
			downloadFileFlag=false;
			e.printStackTrace();
			cLogger.info("下载文件"+fileName+"出现异常!"+e.getMessage());
		}
		return downloadFileFlag;
	}
	public boolean uploadFile(String remoteFilePath,String fileNamePath,String fileName){
		boolean uploadFileFlag=true;
		try {
			boolean tFlag = ftpClient.changeWorkingDirectory(remoteFilePath);
			if(!tFlag){
				throw new MidplatException("切换上传目录失败!");
			}
			FileInputStream fileInputStream=new FileInputStream(fileNamePath);
			if(!ftpClient.storeFile(fileName, fileInputStream)){
				throw new MidplatException("上传文件失败:"+fileName);
			}
			cLogger.info("上传文件成功:"+fileName);
		} catch (Exception e) {
			uploadFileFlag=false;
			e.printStackTrace();
			cLogger.info("上传文件"+fileName+"出现异常!"+e.getMessage());
		}finally{
			//关闭FTP连接
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
				System.out.println("登录10.2.0.31服务器ftp失败！");
			}
			System.out.println("连接10.2.0.31服务器ftp成功！");
			Boolean flag=null;
			if(!(flag=ftpClient.login("gzb","gzb1234"))){
		      System.out.println("登录10.2.0.31服务器ftp失败！");
			}
			System.out.println("登录10.2.0.31服务器ftp成功！"+flag);
			if(ftpClient.setFileType(FTP.BINARY_FILE_TYPE)){
				System.out.println("设置二进制传输成功"+ftpClient.getReplyString());
			}else{
				System.out.println("设置二进制传输失败");
			}
			if(ftpClient.changeWorkingDirectory("/downloadfile")){
				System.out.println("切换/downloadfile工作目录成功！");
			}else{
				System.out.println("切换/downloadfile工作目录失败！");
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
				System.out.println("ftp下载数据成功！");
			}else{
				System.out.println("ftp下载数据失败！");
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
