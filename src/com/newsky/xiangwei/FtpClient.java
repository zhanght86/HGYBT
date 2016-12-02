package com.newsky.xiangwei;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.newsky.xiangwei.PrintLog;
/**
 * �ļ�����Э��ͻ�����
 * @author yuantongxin
 */
public class FtpClient {

	private final static Logger cLogger = Logger.getLogger(FtpClient.class);//������־����Ϊ�ļ�����Э��ͻ����������־
	private Socket clientSocket;//�ͻ����׽���
	private String ipAddress;//IP��ַ
	private int port;//�˿�
	private int timeout;//��ʱ

	/**
	 * �ļ�����Э��ͻ������޲ι���
	 */
	public FtpClient() {
		try {
			loadConfig();//��������
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/* ���������ļ�����ȡԶ�ˣƣԣз�������������Ϣ */
	/**
	 * ��������
	 * @return �������
	 * @throws UnknownHostException δ֪�����쳣
	 */
	public boolean loadConfig() throws UnknownHostException {
		FtpProperty fp = new FtpProperty();//
		ipAddress = fp.pps.getProperty("ncpai-ip");
		port = Integer.valueOf(fp.pps.getProperty("ncpai-port"));
		timeout = Integer.valueOf(fp.pps.getProperty("ncpai-timeout"));

		return true;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	public final void close() {
		try {
			clientSocket.close();
		} catch (IOException ex) {
			cLogger.debug("Socket�����ѹرգ�", ex);
		}
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean ncpaiFileInterface(FileTransCmd ftc) {

		return true;
	}

	/* �������� */
	public boolean createSocketClient() {

		cLogger.info("start to connect to the host...");
		cLogger.info("IP:"+ipAddress +"  port: "+port);
		try {
			clientSocket = new Socket(ipAddress, port);
			clientSocket.setKeepAlive(true);
			clientSocket.setTcpNoDelay(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cLogger.info("connect to the host successed!");
		return true;

	}

	public boolean closeSocketClient(Socket clientSocket) {
		PrintLog.printLog("close the connect from the host...");
		try {
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintLog.printLog("close the connect successed!");

		return true;

	}

	public boolean isConnected() {
		if (clientSocket.isConnected()) {
			PrintLog.printLog("the socket is connecting ");
		} else {
			PrintLog.printLog("the socket has been shutdown");
			if (createSocketClient()) {
				PrintLog.printLog(" reconnect sucess!!!");
			} else {
				return false;
			}
		}
		return true;
	}

	/* �������ݱ��ĵ�SERVER */
	public boolean sendDataToSocket(Socket clientSocket, byte[] sendData,
			int timeout) throws IOException {
		DataOutputStream dops = null;
		try {
			if (isConnected()) {
				cLogger.info("start to send data[" + sendData.length
						+ "] to the host... port is:"+clientSocket.getInetAddress()+":"+clientSocket.getPort());
				clientSocket.setSoTimeout(timeout * 1000);
				dops = new DataOutputStream(clientSocket.getOutputStream());
				cLogger.info("SendDate:  "+new String(sendData));
				dops.write(sendData, 0, sendData.length);
				if (dops.size() != sendData.length) {
					cLogger.info("send data not finished!!");

				}
				dops.flush();
				cLogger.info("send data finished");
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * �������ݱ��Ĵ�SERVER
	 */

	public byte[] recvDataFromSocket(Socket clientSocket, int l, int timeout)
			throws IOException {
		String result = null;
		StringBuffer sb = new StringBuffer();
		DataInputStream dips = null;
		byte[] byTotal = null;

		try {
			if (isConnected()) {
				cLogger.info("start to recive Data from Host...");
				clientSocket.setSoTimeout(timeout * 1000);
				dips = new DataInputStream(clientSocket.getInputStream());
				int i = 0;

				byte by;
				byTotal = new byte[(int) l];
				while ((by = dips.readByte()) != -1) {
					byTotal[i] = by;
					i++;
					if (i == l) {
						break;
					}
				}

				result = new String(byTotal);
			} else {
				cLogger.info("connecting is failed!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byTotal;
	}

	/* �������HOST */

	public boolean sendCmdToSocket(Socket clientSocket, FileTransCmd ftc,
			int timeout) {
		String sendStr = null;
		StringBuffer sb = new StringBuffer(300);
		for (int i = 0; i < 300; i++) {
			sb.insert(i, "\0");
		}

		sb.insert(0, ftc.getVs_outsys_code());
		sb.insert(7, ftc.getVs_req_flag());
		sb.insert(8, ftc.getVs_send_recv_flag());
		sb.insert(9, ftc.getVs_local_file_dir());

		sb.insert(73, ftc.getVs_local_file_name());
		sb.insert(137, ftc.getVs_remote_file_dir());
		sb.insert(201, ftc.getVs_remote_file_name());

		if (ftc.getVl_file_size() == 0) {
			sb.insert(265, "00000000");
		} else {
			String tmpStr = replaceStrWithInteger(ftc.getVl_file_size(), 8);
			sb.insert(265, tmpStr);
		}
		if (ftc.getVl_file_start_positon() == 0) {
			sb.insert(273, "00000000");
		} else {
			String tmpStr = replaceStrWithInteger(
					ftc.getVl_file_start_positon(), 8);
			sb.insert(273, tmpStr);
		}
		if (ftc.getVl_trans_size() == 0) {
			sb.insert(281, "00000000");
		} else {
			String tmpStr = replaceStrWithInteger(ftc.getVl_trans_size(), 8);
			sb.insert(281, tmpStr);
		}

		if (ftc.getVs_retcode() == null) {
			sb.insert(289, "00");
		} else {
			sb.insert(289, ftc.getVs_retcode());
		}
		cLogger.info("ssss is:"+sb.toString());
		sendStr = sb.substring(0, FileTransCmd.CMDSTREAMLEN).toString();
		cLogger.info("sendStr is:" + sendStr);

		try {
			if (sendDataToSocket(clientSocket, sendStr.getBytes(), timeout)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		cLogger.info("end send data");
		return true;
	}

	/*
	 * ���������HOST
	 */

	public FileTransCmd recvCmdFromSocket(Socket clientSocket, int timeout) {
		cLogger.info("��ʼ����host����");
		FileTransCmd ftc = new FileTransCmd();
		String str = new String();
		byte[] bytStr = null;
		try {
			bytStr = recvDataFromSocket(clientSocket,
					FileTransCmd.CMDSTREAMLEN, timeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		str = new String(bytStr);
		cLogger.info("recvStr is:" + str);
		cLogger.info("recvStr length is:" + str.length());

		ftc.setVs_outsys_code(str.substring(0, 7));
		ftc.setVs_req_flag(str.substring(7, 8));
		ftc.setVs_send_recv_flag(str.substring(8, 9));
		ftc.setVs_local_file_dir(str.substring(9, 73));
		ftc.setVs_local_file_name(str.substring(73, 137));
		ftc.setVs_remote_file_dir(str.substring(137, 201));
		ftc.setVs_remote_file_name(str.substring(201, 265));
		ftc.setVl_file_size(Integer.valueOf(str.substring(265, 273)));
		ftc.setVl_file_start_positon(Integer.valueOf(str.substring(273, 281)));
		ftc.setVl_trans_size(Integer.valueOf(str.substring(281, 289)));
		ftc.setVs_retcode(str.substring(289, 291));

		// ftc.displayFileCmd();

		return ftc;
	}

	/* ������Ӧ����Է� */
	public boolean sendRetCode(Socket clientSocket, FileTransCmd ftc,
			String ret_code) {
		ftc.setVs_retcode(ret_code);
		PrintLog.printLog("start to send ret_code to Host...");
		// ftc.displayFileCmd();
		if (sendCmdToSocket(clientSocket, ftc, timeout)) {
			PrintLog.printLog("sendRetCode Success");
		} else {
			PrintLog.printLog("sendRetCode Error");
			return false;
		}
		return true;
	}

	/* �ӷ����������ļ� */

	public boolean recvFileFromHost(FileTransCmd ftc) throws IOException {
		FileTransCmd ftcRcev = new FileTransCmd();
		String fileName = null;
		StringBuffer sb = new StringBuffer();
		File fi = null;
		FileOutputStream fops = null;
		/* ���������ļ����� */
		if (!sendCmdToSocket(clientSocket, ftc, timeout)) {
			PrintLog.printLog("0:sendCmdToSocket error");
			return false;
		}
		PrintLog.printLog("0:�������HOST�ɹ�!");

		if ((ftcRcev = recvCmdFromSocket(clientSocket, timeout)) == null) {
			PrintLog.printLog("1:recvCmdFromSocket error");
			return false;
		}
		PrintLog.printLog("1: ��HOST��������ɹ�!");

		if (ftcRcev.getVs_retcode().compareTo(FileTransCmd.TRANSFER_OK) != 0) {
			PrintLog.printLog("�����ļ�:" + ftc.getVs_remote_file_name() + "����:"
					+ ftcRcev.getVs_retcode());
			return false;
		}

		/* ���ôӷ������˷��ص��ļ���С */
		ftc.setVl_file_size(ftcRcev.getVl_file_size());
		if (ftc.getVl_file_size() < 0) {
			PrintLog.printLog("�����ļ���С���Ϸ�:" + ftc.getVl_file_size());
			return false;
		}

		sb.append(ftc.getVs_remote_file_dir());
		sb.append(ftc.getVs_remote_file_name());
		fileName = sb.toString();

		/* ���������ļ� */
		fi = new File(fileName);
		if (fi.exists()) {
			PrintLog.printLog("Warning: local file :" + fileName + " exists");
		}
		try {
			fops = new FileOutputStream(fi); /* ���ļ������ */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/* ��������������һ��һ���Ľ��������ı� */
		String recvStr = null;
		while (true) {
			PrintLog.printLog("ѭ������HOST����...!");
			if ((ftcRcev = recvCmdFromSocket(clientSocket, timeout)) == null) {
				PrintLog.printLog("ѭ������HOST����ʧ�ܣ�");
				fops.close();
				return false;
			}
			// ftcRcev.displayFileCmd();
			if (ftcRcev.getVl_trans_size() < 0) {
				PrintLog.printLog("ѭ�������ļ������ֽ������Ϸ�!"
						+ ftcRcev.getVl_trans_size());
				fops.close();
				if (!sendRetCode(clientSocket, ftc, FileTransCmd.ERR_ERRORMSG)) {
					PrintLog.printLog("���ͽ����ļ�ʧ���ź�ʧ��!");
				}
				return false;
			}

			/* ���������ı� */
			byte[] bytStr = null;
			if ((bytStr = recvDataFromSocket(clientSocket,
					ftcRcev.getVl_trans_size(), timeout)) == null) {
				PrintLog.printLog("�����ı��ļ�ʧ��");
				fops.close();
				if (!sendRetCode(clientSocket, ftc, FileTransCmd.ERR_FILETRANS)) {
					PrintLog.printLog("���ͽ����ļ�ʧ���ź�ʧ��!");
				}
				return false;
			}
			// PrintLog.printLog("��������:"+recvStr);
			// ftcRcev.displayFileCmd();
			/* �������ı�д���ļ� */
			fops.write(bytStr);

			PrintLog.printLog("�����ļ����ȣ�" + ftcRcev.getVl_trans_size());
			PrintLog.printLog("�����ļ��ܳ���:" + ftcRcev.getVl_file_size());

			/* �ж��Ƿ���������ļ���� */
			if (ftcRcev.getVl_file_start_positon() + ftcRcev.getVl_trans_size() >= ftcRcev
					.getVl_file_size()) {
				PrintLog.printLog("�����ļ����!,�ļ��ܳ���:" + ftcRcev.getVl_file_size());
				if (!sendRetCode(clientSocket, ftc, FileTransCmd.TRANSFER_OK)) {
					PrintLog.printLog("���ͽ����ļ��ɹ��ź�ʧ��!");
				}
				fops.close();
				return true;
			}
			if (!sendRetCode(clientSocket, ftcRcev, FileTransCmd.TRANSFER_OK)) {
				PrintLog.printLog("���ͽ����ļ��ɹ��ź�ʧ��!");
			}

			PrintLog.printLog("�����ļ�Ƭ�γɹ�");
		}
	}

	/* �����ļ���HOST */

	public boolean sendFileToHost(FileTransCmd ftc) throws IOException {
		cLogger.info("into FtpClient.sendFileToHost()");
		FileInputStream fis = null;
		String fileName = null;
		StringBuffer sb = new StringBuffer();
		FileTransCmd ftcRecv = new FileTransCmd();

		sb.append(ftc.getVs_remote_file_dir());
		sb.append(ftc.getVs_remote_file_name());
		cLogger.info("filename:"+sb.toString());
		fileName = sb.toString();

		fis = new FileInputStream(fileName);
		cLogger.info("�ļ���С��" + String.valueOf(fis.available()));

		if (fis.available() <= 0) {
			PrintLog.printLog("�ļ��ֽ������Ϸ�:" + fis.available());
			fis.close();
			return false;
		}

		ftc.setVl_file_size(fis.available());
		ftc.setVl_file_start_positon(0);

		/* ������������������� */
		cLogger.info("0:�����������ʼ...");
		if (!sendCmdToSocket(clientSocket, ftc, timeout)) {
			cLogger.info("������������ʧ��:");
			fis.close();
			return false;
		}

		cLogger.info("1:������������ɹ�...");

		/* ����HOST���� */

		if ((ftcRecv = recvCmdFromSocket(clientSocket, timeout)) == null) {
			cLogger.info("����HOST����ʧ�ܣ�");
			fis.close();
			return false;
		}
		cLogger.info("2:����HOST����ɹ�...");

		if (ftcRecv.getVs_retcode().compareTo("00") != 0) {
			cLogger.info("HOSTû��׼����:" + ftcRecv.getVs_retcode());
			fis.close();
			return false;
		}

		byte[] byt = new byte[FileTransCmd.FILEBUFSIZE];
		int len = 0;
		cLogger.info("byt:" + new String(byt));
		while ((len = fis.read(byt, 0, FileTransCmd.FILEBUFSIZE)) != -1) {
			ftc.setVl_trans_size(len);
			/* �������HOST */
			if (!sendCmdToSocket(clientSocket, ftc, timeout)) {
				PrintLog.printLog("send cmd to Host failed!");
				fis.close();
				return false;
			}
			/* �������ݵ�Host */

			 cLogger.info("sendData len:"+len );
			 cLogger.info("send Data is:"+ new String(byt));
			 cLogger.info("sendData len :"+ (new String(byt).length()));

			if (!sendDataToSocket(clientSocket, byt, timeout)) {
				PrintLog.printLog("send data to Host failed!");
				fis.close();
				return false;
			}
			ftc.setVl_file_start_positon(ftc.getVl_file_start_positon() + len);

			if (ftc.getVl_file_start_positon() >= ftc.getVl_file_size()) {
				cLogger.info("�����ļ����!");
				fis.close();
				return false;

			}

			/* ����HOSTȷ����Ϣ */

			if ((ftcRecv = recvCmdFromSocket(clientSocket, timeout)) == null) {
				cLogger.info("recive cmd from HOSt failed!");
				fis.close();
				return false;
			}

			if (ftcRecv.getVs_retcode().compareTo(FileTransCmd.TRANSFER_OK) != 0) {
				cLogger.info("Host recive data failed!");
				fis.close();
				return false;
			}

		}

		return true;

	}

	/*
	 * �滻����Ϊ�ַ���
	 */

	public String replaceStrWithInteger(int l, int len) {
		byte[] by = new byte[len];
		for (int ii = 0; ii < len; ii++) {
			by[ii] = '0';
		}
		String st = new String(by);

		String st1 = String.valueOf(l);
		StringBuffer sbTwo = new StringBuffer(st);
		int end = 0;
		end = len - st1.length();
		sbTwo.replace(end, len, st1);

		// System.out.println("haha is:"+ sbTwo.toString());

		return sbTwo.toString();
	}

	/**
	 * @param args
	 * @param ftpProperty.xml����˵����ncpi-ip ���� ����ϵͳ��FTP������IP��ַ��ncpai-port ����
	 *        ����ϵͳFTP�������Ķ˿ڣ� ncpai-timeout�����뱣��ϵͳ�ĳ�ʱʱ�䣬һ��Ϊ60��
	 *        �ļ�����Ŀ¼�У������չ�˾����Ŀ¼�����ļ�����˵�����еĸ����չ�˾��������
	 * 
	 * @return
	 */
	public static void main(String[] args) {

		final boolean send_recv = false;

		FtpClient fc = new FtpClient();

		FileTransCmd ftc = new FileTransCmd();

		/* ����ϵͳ�����ļ� */
		if (send_recv) {
			ftc.setVs_outsys_code("9940001"); /* �������ϵͳ���룬�̶� */
			ftc.setVs_req_flag("0"); /* �����־���̶� */
			ftc.setVs_send_recv_flag("1"); /* ����ϵͳ�����ļ���־���̶� */
			ftc.setVs_local_file_dir("insu/0001"); /*
													 * ������ϵͳ�ļ�Ŀ¼��insu�̶���0001
													 * Ϊ�����չ�˾����
													 */
			ftc.setVs_local_file_name("test1.txt"); /* �����ļ��� */
			ftc.setVs_remote_file_dir("F:\\tmp\\"); /* ���ر��ر����ļ�·��,��ȫ·�� */
			ftc.setVs_remote_file_name("test1.txt"); /* �����ļ��� */
		} else /* ����ϵͳ�����ļ� */
		{
			ftc.setVs_outsys_code("9940001"); /* �������ϵͳ���룬�̶� */
			ftc.setVs_req_flag("0"); /* �����־���̶� */
			ftc.setVs_send_recv_flag("0"); /* ����ϵͳ�����ļ���־,�̶� */
			ftc.setVs_local_file_dir("insu/0001"); /*
													 * ����ϵͳ�ļ�����Ŀ¼,insu�̶���0001
													 * Ϊ�����չ�˾����
													 */
			ftc.setVs_local_file_name("ULOG.062201"); /* ����ϵͳ�����ļ��� */
			ftc.setVs_remote_file_dir("F:\\tmp\\"); /* �ϴ��ļ�����·������ȫ·�� */
			ftc.setVs_remote_file_name("test1.txt"); /* �ϴ��ļ��� */
		}

		fc.createSocketClient();
		try {

			if (send_recv) {
				fc.recvFileFromHost(ftc);
			} else {
				fc.sendFileToHost(ftc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		fc.closeSocketClient(fc.clientSocket);
	}

}
