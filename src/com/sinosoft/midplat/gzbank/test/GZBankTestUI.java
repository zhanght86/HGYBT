package com.sinosoft.midplat.gzbank.test;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class GZBankTestUI {
	private final static Logger cLogger = Logger.getLogger(GZBankTestUI.class);

	private final String cIP;
	private final int cPort;

	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");
		
		String mIP = "127.0.0.1";//本地
		int mPort = 9004;
		
		String mFuncFlag = null;
		String mInFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\GZbank\\";
		
		//新单核保
//		mFuncFlag = "9000102";
//		mInFilePath += "保费试算请求.xml";
		//新单承保
		mFuncFlag = "9000103";
		mInFilePath += "缴费出单请求.xml";
		//保单重打
//		mFuncFlag = "9000801";
//		mInFilePath += "保单重打.xml";
		//当日撤单
//		mFuncFlag = "9000901";
//		mInFilePath += "当日撤单.xml";
		
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
		GZBankTestUI mTestUI = new GZBankTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);
		JdomUtil.print(JdomUtil.build(mOutBytes));
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		mFos.write(mOutBytes);

		System.out.println("成功结束！");
	}

	public GZBankTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInBodyBytes = IOTrans.toBytes(pInputStream);

		byte[] mInTotalBytes = new byte[mInBodyBytes.length + 19];

		//报文体长度
		String mInBodyLengthStr = String.valueOf(mInBodyBytes.length);
		cLogger.info("请求报文体长度1：" + mInBodyLengthStr.length());
		int length = mInBodyLengthStr.length();
		for(int i=0;i<6-length;i++){
			mInBodyLengthStr=mInBodyLengthStr+" ";
		}
		cLogger.info("请求报文体长度：" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();

		//交易码
		byte[] InsuBytes = "006    ".getBytes();
		System.arraycopy(InsuBytes, 0, mInTotalBytes, 13, InsuBytes.length);
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);

		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		System.arraycopy(mInBodyBytes, 0, mInTotalBytes, 19, mInBodyBytes.length);
		//报文体长度（6位）+交易代码（7位）+目标保险公司代码（6位）+报文体
		cLogger.info("发送请求报文！"+new String(mInTotalBytes));
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");

		//处理报文头
		byte[] mOutHeadBytes = new byte[19];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		cLogger.info("返回报文头：" + new String(mOutHeadBytes));
		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutBodyLengthInt);
		cLogger.info("交易代码：" + new String(mOutHeadBytes, 6, 7).trim());

		//处理报文体
		byte[] mOutBodyBytes = new byte[mOutBodyLengthInt];
		for (int tReadSize = 0; tReadSize < mOutBodyBytes.length;) {
			int tRead = mSocketIs.read(mOutBodyBytes, tReadSize, mOutBodyBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文体出错！实际读入长度为：" + tReadSize);
			}
			tReadSize += tRead;
		}
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");

		return mOutBodyBytes;
	}
}

