package com.sinosoft.midplat.spdb.test;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.log4j.Logger;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class SPDBTestUI {
	
	private Logger cLogger = Logger.getLogger(SPDBTestUI.class);
	private String cIP = null;
	private int cPort = 0;
	
	public SPDBTestUI() {
	}
	
	public SPDBTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("程序开始...");

		String mIP = "127.0.0.1";
		int mPort = 9003;

		String mFuncFlag = "";
		String mInFilePath="D:/File/task/20170424/spdb/ybt_test/";
		
		//绿灯交易
//		mFuncFlag = "1000";
//		mInFilePath += "1000in_noStd.xml";
		//新契约试算
//		mFuncFlag = "1001";
//		mInFilePath += "1001in_noStd.xml";
		//新契约投保
//		mFuncFlag = "1002";
//		mInFilePath += "1002in_noStd.xml";
		//当日反交易
//		mFuncFlag = "1003";
//		mInFilePath += "1003in_noStd.xml";
		//保单打印
//		mFuncFlag = "1004";
//		mInFilePath += "1004in_noStd.xml";
		//保单补打印
		mFuncFlag = "1005";
		mInFilePath += "1005in_noStd.xml";
		
		String mOutFilePath = "D:/File/task/20170424/spdb/ybt_test/1005out_noStd.xml";

		SPDBTestUI mTest = new SPDBTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTest.sendRequest(mFuncFlag, mIs);
		JdomUtil.print(JdomUtil.build(mOutBytes));
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		mFos.write(mOutBytes);
		mFos.close();
		System.out.println("成功结束！");
	}
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {

		cLogger.info("Socket连接" + cIP + ":" + cPort);
		System.out.println("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInBodyBytes = IOTrans.toBytes(pInputStream);
		byte[] mInTotalBytes = new byte[mInBodyBytes.length + 16];

		// 报文体长度
		String mInBodyLengthStr = String.valueOf(mInBodyBytes.length);
		cLogger.info("请求报文体长度1：" + mInBodyLengthStr.length());
		int length = mInBodyLengthStr.length();
		for(int i=0;i<8-length;i++){
			mInBodyLengthStr=mInBodyLengthStr+" ";
		}
		cLogger.info("请求报文体长度：" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
		
		//包头
		byte[] InsuBytes = "INSU8000".getBytes();
		System.arraycopy(InsuBytes, 0, mInTotalBytes, 0, InsuBytes.length);
		//包长
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 8,mInLengthBytes.length);
		//包体	
		System.arraycopy(mInBodyBytes, 0, mInTotalBytes, 16,mInBodyBytes.length);
		//包头(INSU8000)+包长(8位)+包体
		cLogger.info("发送请求报文！"+new String(mInTotalBytes));
		mSocket.getOutputStream().write(mInTotalBytes);
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/ 1000.0 + "s");		
		
		/** 以下处理返回报文 ************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");

		// 处理报文头
		byte[] mOutHeadBytes = new byte[16];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		cLogger.info("返回报文头：" + new String(mOutHeadBytes));
		cLogger.info("返回报文包头：" + new String(mOutHeadBytes, 0, 8).trim());
		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 8, 8).trim());
		cLogger.info("返回报文长度：" + mOutBodyLengthInt);
		// 处理报文体
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
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/ 1000.0 + "s");
		
		return mOutBodyBytes;
	}
	
	
}
