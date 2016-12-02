package com.sinosoft.midplat.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PSBCSocketClient {

	public Socket clientSocket;

	/** 邮储ip */
	public String ipAddress;

	/** 邮储端口 */
	public int port;

	/** 超时设置 */
	public int timeout;

	public PSBCSocketClient(String cIp, int cPort, int cTimeOut) {

		ipAddress = cIp;
		port = cPort;
		timeout = cTimeOut;

	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean ncpaiFileInterface(PSBCFileTransCmd ftc) {

		return true;
	}

	/* 建立连接 */
	public boolean createSocketClient(String ip, int port) {

		System.out.println("start to connect to the host...");
		try {
			clientSocket = new Socket(ip, port);
			clientSocket.setKeepAlive(true);
			clientSocket.setTcpNoDelay(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("connect to the host successed!");
		return true;

	}

	public boolean closeSocketClient(Socket clientSocket) {
		System.out.println("close the connect from the host...");
		try {
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("close the connect successed!");

		return true;

	}

	public boolean isConnected() {
		if (clientSocket.isConnected()) {
			System.out.println("the socket is connecting ");
		} else {
			System.out.println("the socket has been shutdown");
			if (createSocketClient(ipAddress, port)) {
				System.out.println(" reconnect sucess!!!");
			} else {
				return false;
			}
		}
		return true;
	}

	/* 发送数据报文到SERVER */
	public boolean sendDataToSocket(Socket clientSocket, byte[] sendData,
			int timeout) throws IOException {
		DataOutputStream dops = null;
		try {
			if (isConnected()) {
				System.out.println("start to send data[" + sendData.length
						+ "] to the host...");
				clientSocket.setSoTimeout(timeout * 1000);
				dops = new DataOutputStream(clientSocket.getOutputStream());
				dops.write(sendData, 0, sendData.length);
				if (dops.size() != sendData.length) {
					System.out.println("send data not finished!!");

				}
				dops.flush();

			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * 接收数据报文从SERVER
	 */

	public byte[] recvDataFromSocket(Socket clientSocket, int l, int timeout)
			throws IOException {
		String result = null;
		StringBuffer sb = new StringBuffer();
		DataInputStream dips = null;
		byte[] byTotal = null;

		try {
			if (isConnected()) {
				System.out.println("start to recive Data from Host...");
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
				System.out.println("connecting is failed!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byTotal;
	}

	/* 发送命令到HOST */

	public boolean sendCmdToSocket(Socket clientSocket, PSBCFileTransCmd ftc,
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
			String tmpStr = replaceStrWithInteger(ftc
					.getVl_file_start_positon(), 8);
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

		sendStr = sb.substring(0, PSBCFileTransCmd.CMDSTREAMLEN).toString();
		System.out.println("sendStr is:" + sendStr);

		try {
			if (sendDataToSocket(clientSocket, sendStr.getBytes(), timeout)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/*
	 * 接收命令从HOST
	 */

	public PSBCFileTransCmd recvCmdFromSocket(Socket clientSocket, int timeout) {
		PSBCFileTransCmd ftc = new PSBCFileTransCmd();
		String str = new String();
		byte[] bytStr = null;
		try {
			bytStr = recvDataFromSocket(clientSocket,
					PSBCFileTransCmd.CMDSTREAMLEN, timeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		str = new String(bytStr);
		System.out.println("recvStr is:" + str);
		System.out.println("recvStr length is:" + str.length());

		ftc.setVs_outsys_code(str.substring(0, 7));
		ftc.setVs_req_flag(str.substring(7, 8));
		ftc.setVs_send_recv_flag(str.substring(8, 9));
		ftc.setVs_local_file_dir(str.substring(9, 73));
		ftc.setVs_local_file_name(str.substring(73, 137));
		ftc.setVs_remote_file_dir(str.substring(137, 201));
		ftc.setVs_remote_file_name(str.substring(201, 265));
		ftc.setVl_file_size(Integer.parseInt(str.substring(265, 273)));
		ftc.setVl_file_start_positon(Integer.parseInt(str.substring(273, 281)));
		ftc.setVl_trans_size(Integer.parseInt((str.substring(281, 289))));
		ftc.setVs_retcode(str.substring(289, 291));

		// ftc.displayFileCmd();

		return ftc;
	}

	/* 发送响应码给对方 */
	public boolean sendRetCode(Socket clientSocket, PSBCFileTransCmd ftc,
			String ret_code) {
		ftc.setVs_retcode(ret_code);
		System.out.println("start to send ret_code to Host...");
		// ftc.displayFileCmd();
		if (sendCmdToSocket(clientSocket, ftc, timeout)) {
			System.out.println("sendRetCode Success");
		} else {
			System.out.println("sendRetCode Error");
			return false;
		}
		return true;
	}

	/* 从服务器接收文件 */

	public boolean recvFileFromHost(Socket clientSocket, PSBCFileTransCmd ftc)
			throws IOException {
		PSBCFileTransCmd ftcRcev = new PSBCFileTransCmd();
		String fileName = null;
		StringBuffer sb = new StringBuffer();
		File fi = null;
		FileOutputStream fops = null;
		/* 发送请求文件命令 */
		if (!sendCmdToSocket(clientSocket, ftc, timeout)) {
			System.out.println("0:sendCmdToSocket error");
			return false;
		}
		System.out.println("0:发送命令到HOST成功!");

		if ((ftcRcev = recvCmdFromSocket(clientSocket, timeout)) == null) {
			System.out.println("1:recvCmdFromSocket error");
			return false;
		}
		System.out.println("1: 从HOST接收命令成功!");

		if (ftcRcev.getVs_retcode().compareTo(PSBCFileTransCmd.TRANSFER_OK) != 0) {
			System.out.println("接收文件:" + ftc.getVs_remote_file_name() + "错误:"
					+ ftcRcev.getVs_retcode());
			return false;
		}

		/* 设置从服务器端返回的文件大小 */
		ftc.setVl_file_size(ftcRcev.getVl_file_size());
		if (ftc.getVl_file_size() < 0) {
			System.out.println("接收文件大小不合法:" + ftc.getVl_file_size());
			return false;
		}

		sb.append(ftc.getVs_remote_file_dir());
		sb.append("/");
		sb.append(ftc.getVs_remote_file_name());
		fileName = sb.toString();

		/* 建立本地文件 */
		fi = new File(fileName);
		if (fi.exists()) {
			System.out.println("local file :" + fileName + " exists");
		}
		try {
			fops = new FileOutputStream(fi); /* 打开文件输出流 */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/* 接收命令，根据命令，一个一个的接收数据文本 */
		String recvStr = null;
		while (true) {
			System.out.println("循环接收HOST命令...!");
			if ((ftcRcev = recvCmdFromSocket(clientSocket, timeout)) == null) {
				System.out.println("循环接收HOST命令失败！");
				fops.close();
				return false;
			}
			// ftcRcev.displayFileCmd();
			if (ftcRcev.getVl_trans_size() < 0) {
				System.out.println("循环接收文件传送字节流不合法!"
						+ ftcRcev.getVl_trans_size());
				fops.close();
				if (!sendRetCode(clientSocket, ftc,
						PSBCFileTransCmd.ERR_ERRORMSG)) {
					System.out.println("发送接收文件失败信号失败!");
				}
				return false;
			}

			/* 接收数据文本 */
			byte[] bytStr = null;
			if ((bytStr = recvDataFromSocket(clientSocket, ftcRcev
					.getVl_trans_size(), timeout)) == null) {
				System.out.println("接收文本文件失败");
				fops.close();
				if (!sendRetCode(clientSocket, ftc,
						PSBCFileTransCmd.ERR_FILETRANS)) {
					System.out.println("发送接收文件失败信号失败!");
				}
				return false;
			}
			// System.out.println("接收数字:"+recvStr);
			// ftcRcev.displayFileCmd();
			/* 将接受文本写入文件 */
			fops.write(bytStr);

			System.out.println("接收文件长度：" + ftcRcev.getVl_trans_size());
			System.out.println("接收文件总长度:" + ftcRcev.getVl_file_size());

			/* 判断是否接受整个文件完毕 */
			if (ftcRcev.getVl_file_start_positon() + ftcRcev.getVl_trans_size() >= ftcRcev
					.getVl_file_size()) {
				System.out
						.println("接收文件完毕!,文件总长度:" + ftcRcev.getVl_file_size());
				if (!sendRetCode(clientSocket, ftc,
						PSBCFileTransCmd.TRANSFER_OK)) {
					System.out.println("发送接收文件成功信号失败!");
				}
				fops.close();
				return true;
			}
			if (!sendRetCode(clientSocket, ftcRcev,
					PSBCFileTransCmd.TRANSFER_OK)) {
				System.out.println("发送接收文件成功信号失败!");
			}

			System.out.println("接收文件片段成功");
		}
	}

	/* 发送文件到HOST */

	public boolean sendFileToHost(Socket clientSocket, PSBCFileTransCmd ftc)
			throws IOException {
		FileInputStream fis = null;
		String fileName = null;
		StringBuffer sb = new StringBuffer();
		PSBCFileTransCmd ftcRecv = new PSBCFileTransCmd();

		sb.append(ftc.getVs_remote_file_dir());
		if (!ftc.getVs_remote_file_dir().endsWith("/"))
			sb.append("/");
		sb.append(ftc.getVs_remote_file_name());
		fileName = sb.toString();

		fis = new FileInputStream(fileName);
		System.out.println("文件大小：" + String.valueOf(fis.available()));

		if (fis.available() <= 0) {
			System.out.println("文件字节数不合法:" + fis.available());
			fis.close();
			return false;
		}

		ftc.setVl_file_size(fis.available());
		ftc.setVl_file_start_positon(0);

		/* 发送请求发送命令到服务器 */
		System.out.println("0:发送请求命令开始...");
		if (!sendCmdToSocket(clientSocket, ftc, timeout)) {
			System.out.println("发送请求命令失败:");
			fis.close();
			return false;
		}

		System.out.println("1:发送请求命令成功...");

		/* 接收HOST命令 */

		if ((ftcRecv = recvCmdFromSocket(clientSocket, timeout)) == null) {
			System.out.println("接收HOST命令失败：");
			fis.close();
			return false;
		}
		System.out.println("2:接收HOST命令成功...");

		if (ftcRecv.getVs_retcode().compareTo("00") != 0) {
			System.out.println("HOST没有准备好:" + ftcRecv.getVs_retcode());
			fis.close();
			return false;
		}

		byte[] byt = new byte[PSBCFileTransCmd.FILEBUFSIZE];
		int len = 0;
		while ((len = fis.read(byt, 0, PSBCFileTransCmd.FILEBUFSIZE)) != -1) {
			ftc.setVl_trans_size(len);
			/* 发送命令到HOST */
			if (!sendCmdToSocket(clientSocket, ftc, timeout)) {
				System.out.println("send cmd to Host failed!");
				fis.close();
				return false;
			}
			/* 发送数据到Host */

			// System.out.println("sendData len:"+len );
			// System.out.println("send Data is:"+ new String(byt));
			// System.out.println("sendData len :"+ (new String(byt).length()));
			if (!sendDataToSocket(clientSocket, byt, timeout)) {
				System.out.println("send data to Host failed!");
				fis.close();
				return false;
			}
			ftc.setVl_file_start_positon(ftc.getVl_file_start_positon() + len);

			if (ftc.getVl_file_start_positon() >= ftc.getVl_file_size()) {
				System.out.println("发送文件完毕!");
				fis.close();
				return false;

			}

			/* 接收HOST确认信息 */

			if ((ftcRecv = recvCmdFromSocket(clientSocket, timeout)) == null) {
				System.out.println("recive cmd from HOSt failed!");
				fis.close();
				return false;
			}

			if (ftcRecv.getVs_retcode().compareTo(PSBCFileTransCmd.TRANSFER_OK) != 0) {
				System.out.println("Host recive data failed!");
				fis.close();
				return false;
			}

		}

		return true;

	}

	/*
	 * 替换整形为字符串
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
	 * @param ftpProperty.xml配置说明，ncpi-ip
	 *            配置 保险系统的FTP服务器IP地址，ncpai-port 配置 保险系统FTP服务器的端口，
	 *            ncpai-timeout配置与保险系统的超时时间，一般为60秒
	 *            文件传输目录中，各保险公司代码目录参照文件传输说明书中的各保险公司命名规则
	 * 
	 * @return
	 */
	public static void main(String[] args) {

		final boolean send_recv = true;

		PSBCSocketClient fc = new PSBCSocketClient("127.0.0.1", 7014, 60);

		PSBCFileTransCmd ftc = new PSBCFileTransCmd();

		/* 向保险系统接收文件 */
		if (send_recv) {
			ftc.setVs_outsys_code("9940001"); /* 请求接收系统代码，固定 */
			ftc.setVs_req_flag("0"); /* 请求标志，固定 */
			ftc.setVs_send_recv_flag("1"); /* 向保险系统接收文件标志，固定 */
			ftc.setVs_local_file_dir("insu/0001"); /* 请求保险系统文件目录，insu固定，0001为各保险公司代码 */
			ftc.setVs_local_file_name("ULOG.062203"); /* 请求文件名 */
			ftc.setVs_remote_file_dir("d:/1.txt"); /* 下载本地保存文件路径 */
			ftc.setVs_remote_file_name("test1.txt"); /* 本地文件名 */
		} else /* 向保险系统发送文件 */
		{
			ftc.setVs_outsys_code("9940001"); /* 请求接收系统代码，固定 */
			ftc.setVs_req_flag("0"); /* 请求标志，固定 */
			ftc.setVs_send_recv_flag("0"); /* 向保险系统发送文件标志,固定 */
			ftc.setVs_local_file_dir("insu/0001"); /* 保险系统文件保存目录,insu固定，0001为各保险公司代码 */
			ftc.setVs_local_file_name("ULOG.062201"); /* 保险系统接受文件名 */
			ftc.setVs_remote_file_dir("tmp"); /* 上传文件本地路径 */
			ftc.setVs_remote_file_name("test1.txt"); /* 上传文件名 */
		}

		fc.createSocketClient(fc.ipAddress, fc.port);
		try {
			if (send_recv) {
				fc.recvFileFromHost(fc.clientSocket, ftc);
			} else {
				fc.sendFileToHost(fc.clientSocket, ftc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		fc.closeSocketClient(fc.clientSocket);
	}

}
