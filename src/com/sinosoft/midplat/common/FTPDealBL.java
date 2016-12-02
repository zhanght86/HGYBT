package com.sinosoft.midplat.common;

import java.io.*;

import ind.crf.free.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import com.sinosoft.utility.CError;
import java.util.Calendar;
import java.net.SocketException;


/**
 * 提供银保通前置机与银行的FTP操作
 * @company sinosoft
 * @version 1.0
 * @author sinosoft
 * */
public class FTPDealBL 
{

	public static FTPClient ftpClient =  null;

	public static FTP ftp = null;

	private static String Host = null; // ftp 主机Host

	private static String UserName = null;

	private static String Password = null;

	private static int Port = 21;

	public FTPDealBL()
	{
		System.out.println("begin");
	}

	/**
	 * 初始化
	 * @param ip FTP服务器地址
	 * @param user 服务名
	 * @param password 密码
	 * @param port 端口号
	 */
	public FTPDealBL(String host,String username,String password,int port)
	{
		this.Host= host;
		this.UserName= username;
		this.Password=password;
		this.Port= port;
		System.out.println("host:"+ host + " username:"+ username+ " password:"+ password+ " port:"+ port);
	}


	/**
	 *  创建FTP连接 
	 */
	public boolean connectServer() {
		boolean flag = true;
		ftpClient = new FTPClient();
		int reply;
		try {
			ftpClient.connect(Host);
			ftpClient.login(UserName, Password);

			System.out.println("Connected to " + Host );
			System.out.print(ftpClient.getReplyString());
			reply = ftpClient.getReplyCode();
//			 将文件传输类型设置为二进制
		    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		    //防止server超时断开
			ftpClient.setDataTimeout(120000);
			if (!FTPReply.isPositiveCompletion(reply)){
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				flag = false;
			}
		}catch (SocketException e){
			flag = false;
			e.printStackTrace();
			System.err.println("登录ftp服务器 " + Host + " 失败,连接超时！");

		}catch (IOException e){
			flag = false;
			e.printStackTrace();
			System.err.println("登录ftp服务器 " + Host + " 失败，FTP服务器无法打开！");
		}

		System.out.println("登陆ftp服务器成功"+Host);
		return flag;
	}

	/**
	 * 关闭连接
	 */
	private  void closeConnect() {
		try {
			if (ftpClient != null) {

				ftpClient.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 参数说明：
	 * localStr：本地目录+文件名，都用绝对路径
	 * remoteStr = 远程目录+文件名，如果要取的文件直接在登陆后的默认目录下，这里远程目录可以不要 remoteStr = /文件名；
	 */
	public boolean ApacheFTPDownFile(String localStr, String remoteStr)
	{
		try {
			FileOutputStream fos = null;
			Calendar cCalendar = Calendar.getInstance();
			long currTime = cCalendar.getTimeInMillis();

			if(connectServer()){
				/*得到相应的目录和远程文件*/
				int i =0;
				i = remoteStr.lastIndexOf("/");
				String   currDir = remoteStr.substring(0, i);
				String   Remotefile = remoteStr.substring(i + 1);

				if (remoteStr.equals("")) {
					System.out.println("请输入正确的文件名！");
					return false;
				}
				if(!checkRemoteFile(currDir,Remotefile)){
					return false;
				}
				// 若本地路径不存在则生成路径
				if(!checkLocalDir(localStr)){
					return false;
				}
				//下载文件到本地
				File file = null;
				file = new File(localStr);
				fos = new FileOutputStream(file);
				ftpClient.retrieveFile(Remotefile, fos);
				System.out.println(ftpClient.getReplyString());
				fos.flush();
				fos.close();
				closeConnect();

				cCalendar = Calendar.getInstance();
				long currTime2 = cCalendar.getTimeInMillis();
				long diff2 = currTime2 - currTime;
				System.out.println("所用时间为=" + diff2);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("下载文件失败！！！！");
			return false; 
		}
		return true;

	}

	private boolean checkLocalDir(String localStr )
	{

		File formatPath = null;
		int j = localStr.lastIndexOf("\\");
		String localDir = localStr.substring(0, j);
		formatPath = new File(localDir);
		if (!formatPath.exists()) {
			formatPath.mkdirs();
		}
		// TODO Auto-generated method stub
		return true;
	}


	/*
	 * 对远程文件进行分析
	 * */
	private boolean checkRemoteFile(String cFileDir,String cFileName)
	{
		try{
			boolean tFlag = false;
			System.out.println("1111111"+cFileDir);
			System.out.println("2222222"+cFileName);

			tFlag = ftpClient.changeWorkingDirectory(cFileDir);
			if (!tFlag) {
				System.out.println("路径出错");
				CError tError = new CError();
				tError.moduleName = "ApacheFTP";
				tError.functionName = "dealData";
				tError.errorMessage = "请检查:[" + cFileDir + "]路径是否正确";
				return false;
			}

			System.out.println(ftpClient.printWorkingDirectory());
			System.out.println(ftpClient.getReplyString());

			FTPFile ff[] = ftpClient.listFiles();
			boolean fileExist = false;
			if (ff == null || ff.length == 0) {
				System.out.println("该目录下无任何文件！");
				return false;
			} else {
				System.out.println("该目录下有文件！length:"+ff.length);
				for(int j = 0;j<ff.length;j++){
					if(ff[j].getName().equals(cFileName)){
						System.out.println("在FTP目录上找到了文件！");
						fileExist = true;
						break;           			
					}            		
				}
			}
			if(!fileExist){

				System.out.println("在FTP目录下没有读取到文件");
				return false;                        	
			}
			return true;      
		}
		catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (ftpClient != null) {
					ftpClient.disconnect();
				}
			} catch (Exception e) {
			}
			return false;
		}
		// TODO Auto-generated method stub
	}


	/**
	 * 获取文件
	 * 
	 * @param localStr
	 *            本地文件全路径名
	 * @param remoteStr
	 *            远程文件全路径名
	 */
	public boolean FTPGetFile(String localStr, String remoteStr, String address,
			String user, String pass) 
	{
		Host = address;
		UserName = user;
		Password = pass;
		Port = 21;

		if (!ftpLogin()) {
			System.out.println("FTP登陆失败，Host:" + Host + " Port:" + Port
					+ " UserName:" + UserName + " Password:" + Password);
			return false;
		}
		System.out.println("下载文件:" + localStr + "," + remoteStr);
		String Remotefile = "";
		String currDir = "";
		int i = 0;
		ftp.setTransfer(FTP.TRANSFER_PASV);  //模式
		ftp.setMode(FTP.MODE_AUTO);
		/* 对远程文件进行分析 */
		if (remoteStr.equals("")) {
			System.out.println("请输入正确的文件名！");
			return false;
		}
		/* 得到相应的目录和远程文件 */
		i = remoteStr.lastIndexOf("/");
		currDir = remoteStr.substring(0, i);
		Remotefile = remoteStr.substring(i + 1);
		System.out.println("CurrDir is  :" + currDir);
		System.out.println("FileName is :" + Remotefile);
		ftp.cd(currDir);
		System.out.print(ftp.lastReply());
		System.out.println("FTP.CODE_TRANSFER_OK:" + FTP.CODE_TRANSFER_OK);
		System.out.println("ftp.lastCode()111:" + ftp.lastCode());
		if (ftp.lastCode() != FTP.CODE_CD_OK) {
			System.out.println("the remote directory does not exist.");
			ftp.disconnect();
			return false;
		}
		/**
		 * 没有路径生成路径
		 */
		int j = localStr.lastIndexOf("\\");
		String localDir = localStr.substring(0, j);
		File file = new File(localDir);
		if (!file.exists()) {
			System.out.println("the local directory does not exist.");
			file.mkdirs();
		}
		System.out.println("ftp.lastCode()222:" + ftp.lastCode());
		ftp.download(Remotefile, localStr);
		System.out.println(ftp.lastReply());
		System.out.println("ftp.lastCode()333:" + ftp.lastCode());
		if (ftp.lastCode() != FTP.CODE_TRANSFER_OK) {
			System.out.println("error while downloading");
			ftp.disconnect();
			File f = new File(localStr);
			long taille = f.length();
			System.out.println("taille:::"+taille);
			ftpLogin();
			ftp.cd(currDir);
			long rollback = 512;
			ftp.download(Remotefile, localStr, taille - rollback);
		}
		ftp.disconnect();

		return true;
	}

	public boolean ftpLogin() 
	{
		ftp = new FTP(Host, Port);
		ftp.connect();
		System.out.print(ftp.lastReply());
		if (ftp.lastCode() != FTP.CODE_CONNECT_OK) {
			System.out.println("Connection failed.");
			return false;
		}
		ftp.login(UserName, Password);
		System.out.print(ftp.lastReply());
		if (ftp.lastCode() != FTP.CODE_LOGGEDIN) {
			ftp.disconnect();
			return false;
		}
		return true;
	}
	public void upFile(String path, String filename, String localFilePath)
	{
		try {
			this.connectServer();
			FileInputStream in=new FileInputStream(new File(localFilePath));
			ftpClient.changeWorkingDirectory(path);
			ftpClient.storeFile(filename, in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}  

	}

	/**
	 * 上传文件
	 * @param file 文件
	 * @param path 上传服务器路径
	 * @return
	 */
	public boolean ApacheFTPUploadFile(File file,String path){
		try
		{
			System.out.println(file);
			System.out.println("1");
			if (!file.isFile()) {
				System.out.println("不是文件");
				return false;
			}else if(!fileSize(file)){
				//控制文件大小
				return false;
			}
			System.out.println("2");
			if (!connectServer()) {
				return false;
			}
			//转到path路径
			path=this.pathAddress(path);

			ftpClient.makeDirectory(path);

			System.out.println(path);
			//进入服务器目录
			if(!ftpClient.changeWorkingDirectory(path)){
				System.out.println("上载路径出错");
				return false;
			}

			//给文件加上时间撮,防止服务器文件重复
			String fileName=file.getName();
			/*
			 * sl add 2008年12月27日22:53:25
			 * 加上时间撮是合理的，但邮储要求文件格式必须要满足他们的要求
			 * 可以跟他们沟通让他们的格式要求中增加时间撮，我们的程序就不主动给它添加时间撮了
			 * 这里先注掉它！
			 */
//			int lastIndex=fileName.lastIndexOf(".");
//			fileName=fileName.substring(0,lastIndex)+"_"+System.currentTimeMillis()+fileName.substring(lastIndex);
			System.out.println(fileName);
			try {
				FileInputStream fis=new FileInputStream(file);
				if(!ftpClient.storeFile(fileName, fis)){
					System.out.println("closeConnect()");
					closeConnect();
					fis.close();
					return false;
				}
				fis.close();
				System.out.println("fis.close();");
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}finally{
				closeConnect();
				
			}

		}
		catch(IOException e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 上传文件
	 * @param file 文件
	 * @param path 上传服务器路径   工行的特殊处理
	 * @return
	 */
	public boolean ICBCApacheFTPUploadFile(File file,String path){
		try
		{
			System.out.println(file);
			System.out.println("1");
			if (!file.isFile()) {
				System.out.println("不是文件");
				return false;
			}else if(!fileSize(file)){
				//控制文件大小
				return false;
			}
			System.out.println("2");
			if (!connectServer()) {
				return false;
			}
			//转到path路径
			path=this.pathAddress(path);

//			ftpClient.makeDirectory(path);

			System.out.println(path);
			//进入服务器目录
			/*
			 * --工行金盛的生产上是把下面切换路径的四行注释掉的，测试时先不注释掉，测试切换目录的情况对不对.
			 * 已恢复20130410
			 */
			System.out.println("切换成目录:"+path);
			if(!ftpClient.changeWorkingDirectory(path)){
				System.out.println("上载路径出错");
				return false;
			}
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("UTF-8");
			//给文件加上时间撮,防止服务器文件重复
			String fileName=file.getName();
			/*
			 * sl add 2008年12月27日22:53:25
			 * 加上时间撮是合理的，但邮储要求文件格式必须要满足他们的要求
			 * 可以跟他们沟通让他们的格式要求中增加时间撮，我们的程序就不主动给它添加时间撮了
			 * 这里先注掉它！
			 */
//			int lastIndex=fileName.lastIndexOf(".");
//			fileName=fileName.substring(0,lastIndex)+"_"+System.currentTimeMillis()+fileName.substring(lastIndex);
			System.out.println(fileName);
			try {
				FileInputStream fis=new FileInputStream(file);
				if(!ftpClient.storeFile(new String(fileName.getBytes("UTF-8"),"iso-8859-1"), fis)){
					System.out.println("closeConnect()");
					closeConnect();
					fis.close();
					return false;
				}
				fis.close();
				System.out.println("fis.close();");
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}finally{
				closeConnect();
				
			}

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 控制文件的大小,默认为5M
	 * @param file_in 文件
	 */
	private boolean fileSize(File file_in) {

		return this.fileSize(file_in,5);
	}

	/**
	 * 控制文件的大小
	 * @param file_in 文件
	 * @param size 文件大小,单位为M
	 */
	private boolean fileSize(File file_in,int size) {
		if (file_in == null || file_in.length() == 0) {
			//文件为空
			return false;
		} else {
			if (file_in.length() > (1024 * 1024 * size)){
				//文件大小不能大与size
				return false;
			}
		}
		return true;
	}
	/**
	 * 格式化文件路径 检查path最后有没有分隔符'\'
	 * @param path
	 * @return
	 */
	public String pathAddress(String path){

		if (path==null || path.length()<1) {
			return "";
		}
		String temp_path=path.substring(path.length()-1);
		if (!temp_path.equals("/") && !temp_path.equals("\\")) {
			temp_path=path+File.separator;
		}else{
			temp_path=path;
		}
		return temp_path;

	}

	public static String getHost() {
		return Host;
	}
	public static void setHost(String cHost) {
		Host = cHost;
	}
	public static String getUser() {
		return UserName;
	}
	public static void setUser(String user) {
		UserName = user;
	}
	public static String getPassword() {
		return Password;
	}
	public static void setPassword(String password) {
		Password = password;
	}
	public static int getPort() {
		return Port;
	}
	public static void setPort(int port) {
		Port = port;
	}

	public static void main(String[] args) 
	{
		String ip="127.0.0.1";
		String user="zhongrong";
		String password="zhongrong";
		int port=21;
		
		FTPDealBL tFTPDealBL = new FTPDealBL(ip,user,password,21);
		File file = new File("D:\\YBT_WORKSPACE\\中融\\报文\\ENY042012011102303.txt");
		
		System.out.println(tFTPDealBL.ApacheFTPUploadFile(file, "\\YBTNoRealTime"));
						
	}
}





