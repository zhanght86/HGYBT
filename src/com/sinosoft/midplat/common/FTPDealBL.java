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
 * �ṩ����ͨǰ�û������е�FTP����
 * @company sinosoft
 * @version 1.0
 * @author sinosoft
 * */
public class FTPDealBL 
{

	public static FTPClient ftpClient =  null;

	public static FTP ftp = null;

	private static String Host = null; // ftp ����Host

	private static String UserName = null;

	private static String Password = null;

	private static int Port = 21;

	public FTPDealBL()
	{
		System.out.println("begin");
	}

	/**
	 * ��ʼ��
	 * @param ip FTP��������ַ
	 * @param user ������
	 * @param password ����
	 * @param port �˿ں�
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
	 *  ����FTP���� 
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
//			 ���ļ�������������Ϊ������
		    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		    //��ֹserver��ʱ�Ͽ�
			ftpClient.setDataTimeout(120000);
			if (!FTPReply.isPositiveCompletion(reply)){
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				flag = false;
			}
		}catch (SocketException e){
			flag = false;
			e.printStackTrace();
			System.err.println("��¼ftp������ " + Host + " ʧ��,���ӳ�ʱ��");

		}catch (IOException e){
			flag = false;
			e.printStackTrace();
			System.err.println("��¼ftp������ " + Host + " ʧ�ܣ�FTP�������޷��򿪣�");
		}

		System.out.println("��½ftp�������ɹ�"+Host);
		return flag;
	}

	/**
	 * �ر�����
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
	 * ����˵����
	 * localStr������Ŀ¼+�ļ��������þ���·��
	 * remoteStr = Զ��Ŀ¼+�ļ��������Ҫȡ���ļ�ֱ���ڵ�½���Ĭ��Ŀ¼�£�����Զ��Ŀ¼���Բ�Ҫ remoteStr = /�ļ�����
	 */
	public boolean ApacheFTPDownFile(String localStr, String remoteStr)
	{
		try {
			FileOutputStream fos = null;
			Calendar cCalendar = Calendar.getInstance();
			long currTime = cCalendar.getTimeInMillis();

			if(connectServer()){
				/*�õ���Ӧ��Ŀ¼��Զ���ļ�*/
				int i =0;
				i = remoteStr.lastIndexOf("/");
				String   currDir = remoteStr.substring(0, i);
				String   Remotefile = remoteStr.substring(i + 1);

				if (remoteStr.equals("")) {
					System.out.println("��������ȷ���ļ�����");
					return false;
				}
				if(!checkRemoteFile(currDir,Remotefile)){
					return false;
				}
				// ������·��������������·��
				if(!checkLocalDir(localStr)){
					return false;
				}
				//�����ļ�������
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
				System.out.println("����ʱ��Ϊ=" + diff2);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("�����ļ�ʧ�ܣ�������");
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
	 * ��Զ���ļ����з���
	 * */
	private boolean checkRemoteFile(String cFileDir,String cFileName)
	{
		try{
			boolean tFlag = false;
			System.out.println("1111111"+cFileDir);
			System.out.println("2222222"+cFileName);

			tFlag = ftpClient.changeWorkingDirectory(cFileDir);
			if (!tFlag) {
				System.out.println("·������");
				CError tError = new CError();
				tError.moduleName = "ApacheFTP";
				tError.functionName = "dealData";
				tError.errorMessage = "����:[" + cFileDir + "]·���Ƿ���ȷ";
				return false;
			}

			System.out.println(ftpClient.printWorkingDirectory());
			System.out.println(ftpClient.getReplyString());

			FTPFile ff[] = ftpClient.listFiles();
			boolean fileExist = false;
			if (ff == null || ff.length == 0) {
				System.out.println("��Ŀ¼�����κ��ļ���");
				return false;
			} else {
				System.out.println("��Ŀ¼�����ļ���length:"+ff.length);
				for(int j = 0;j<ff.length;j++){
					if(ff[j].getName().equals(cFileName)){
						System.out.println("��FTPĿ¼���ҵ����ļ���");
						fileExist = true;
						break;           			
					}            		
				}
			}
			if(!fileExist){

				System.out.println("��FTPĿ¼��û�ж�ȡ���ļ�");
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
	 * ��ȡ�ļ�
	 * 
	 * @param localStr
	 *            �����ļ�ȫ·����
	 * @param remoteStr
	 *            Զ���ļ�ȫ·����
	 */
	public boolean FTPGetFile(String localStr, String remoteStr, String address,
			String user, String pass) 
	{
		Host = address;
		UserName = user;
		Password = pass;
		Port = 21;

		if (!ftpLogin()) {
			System.out.println("FTP��½ʧ�ܣ�Host:" + Host + " Port:" + Port
					+ " UserName:" + UserName + " Password:" + Password);
			return false;
		}
		System.out.println("�����ļ�:" + localStr + "," + remoteStr);
		String Remotefile = "";
		String currDir = "";
		int i = 0;
		ftp.setTransfer(FTP.TRANSFER_PASV);  //ģʽ
		ftp.setMode(FTP.MODE_AUTO);
		/* ��Զ���ļ����з��� */
		if (remoteStr.equals("")) {
			System.out.println("��������ȷ���ļ�����");
			return false;
		}
		/* �õ���Ӧ��Ŀ¼��Զ���ļ� */
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
		 * û��·������·��
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
	 * �ϴ��ļ�
	 * @param file �ļ�
	 * @param path �ϴ�������·��
	 * @return
	 */
	public boolean ApacheFTPUploadFile(File file,String path){
		try
		{
			System.out.println(file);
			System.out.println("1");
			if (!file.isFile()) {
				System.out.println("�����ļ�");
				return false;
			}else if(!fileSize(file)){
				//�����ļ���С
				return false;
			}
			System.out.println("2");
			if (!connectServer()) {
				return false;
			}
			//ת��path·��
			path=this.pathAddress(path);

			ftpClient.makeDirectory(path);

			System.out.println(path);
			//���������Ŀ¼
			if(!ftpClient.changeWorkingDirectory(path)){
				System.out.println("����·������");
				return false;
			}

			//���ļ�����ʱ���,��ֹ�������ļ��ظ�
			String fileName=file.getName();
			/*
			 * sl add 2008��12��27��22:53:25
			 * ����ʱ����Ǻ���ģ����ʴ�Ҫ���ļ���ʽ����Ҫ�������ǵ�Ҫ��
			 * ���Ը����ǹ�ͨ�����ǵĸ�ʽҪ��������ʱ��飬���ǵĳ���Ͳ������������ʱ�����
			 * ������ע������
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
	 * �ϴ��ļ�
	 * @param file �ļ�
	 * @param path �ϴ�������·��   ���е����⴦��
	 * @return
	 */
	public boolean ICBCApacheFTPUploadFile(File file,String path){
		try
		{
			System.out.println(file);
			System.out.println("1");
			if (!file.isFile()) {
				System.out.println("�����ļ�");
				return false;
			}else if(!fileSize(file)){
				//�����ļ���С
				return false;
			}
			System.out.println("2");
			if (!connectServer()) {
				return false;
			}
			//ת��path·��
			path=this.pathAddress(path);

//			ftpClient.makeDirectory(path);

			System.out.println(path);
			//���������Ŀ¼
			/*
			 * --���н�ʢ���������ǰ������л�·��������ע�͵��ģ�����ʱ�Ȳ�ע�͵��������л�Ŀ¼������Բ���.
			 * �ѻָ�20130410
			 */
			System.out.println("�л���Ŀ¼:"+path);
			if(!ftpClient.changeWorkingDirectory(path)){
				System.out.println("����·������");
				return false;
			}
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("UTF-8");
			//���ļ�����ʱ���,��ֹ�������ļ��ظ�
			String fileName=file.getName();
			/*
			 * sl add 2008��12��27��22:53:25
			 * ����ʱ����Ǻ���ģ����ʴ�Ҫ���ļ���ʽ����Ҫ�������ǵ�Ҫ��
			 * ���Ը����ǹ�ͨ�����ǵĸ�ʽҪ��������ʱ��飬���ǵĳ���Ͳ������������ʱ�����
			 * ������ע������
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
	 * �����ļ��Ĵ�С,Ĭ��Ϊ5M
	 * @param file_in �ļ�
	 */
	private boolean fileSize(File file_in) {

		return this.fileSize(file_in,5);
	}

	/**
	 * �����ļ��Ĵ�С
	 * @param file_in �ļ�
	 * @param size �ļ���С,��λΪM
	 */
	private boolean fileSize(File file_in,int size) {
		if (file_in == null || file_in.length() == 0) {
			//�ļ�Ϊ��
			return false;
		} else {
			if (file_in.length() > (1024 * 1024 * size)){
				//�ļ���С���ܴ���size
				return false;
			}
		}
		return true;
	}
	/**
	 * ��ʽ���ļ�·�� ���path�����û�зָ���'\'
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
		File file = new File("D:\\YBT_WORKSPACE\\����\\����\\ENY042012011102303.txt");
		
		System.out.println(tFTPDealBL.ApacheFTPUploadFile(file, "\\YBTNoRealTime"));
						
	}
}





