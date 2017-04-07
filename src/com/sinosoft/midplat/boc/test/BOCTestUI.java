/**
 * 中行测试程序主方类
 */
package com.sinosoft.midplat.boc.test;


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class BOCTestUI {
	public BOCTestUI() {
	};

	private Logger cLogger = Logger.getLogger(BOCTestUI.class);
	private String cIP = null;
	private int cPort = 0;

	public BOCTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");

		String mIP = "127.0.0.1";
		int mPort = 9001;

		String mFuncFlag = "";
		String mInFilePath="C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\boc\\";
		
		//试算
		mFuncFlag = "1001";
		mInFilePath += "保费试算1001.xml";
		//承保
//		mFuncFlag = "1002";
//		mInFilePath += "缴费出单1002.xml";
		//保单重打
//		mFuncFlag = "1003";
//		mInFilePath += "保单重打1003.xml";
		//当日撤单
//		mFuncFlag = "1004";
//		mInFilePath += "当日契撤1004.xml";
		//续期查询
//		mFuncFlag = "1005";
//		mInFilePath += "续期缴费查询请求.xml";
		//续期缴费
//		mFuncFlag = "1006";
//		mInFilePath += "续期缴费请求.xml";
		//退保试算
//		mFuncFlag = "1007";
//		mInFilePath += "退保满期给付试算1007.xml";
		//退保确认
//		mFuncFlag = "1008";
//		mInFilePath += "退保满期给付确认1008.xml";
		//纯手工出单
//		mFuncFlag = "1011";
//		mInFilePath += "中行纯手工出单请求.xml";
		
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";

		BOCTestUI mTest = new BOCTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTest.sendRequest(mFuncFlag, mIs);

		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
		
		System.out.println("成功结束！");
	}
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream)
			throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		System.out.println("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 8];

		// 报文体长度
		String mInCipherBodyLengthStr = String
				.valueOf(mInClearBodyBytes.length);
		cLogger.info("请求报文体长度1：" + mInCipherBodyLengthStr.length());
		int length = mInCipherBodyLengthStr.length();
		for(int i=0;i<8-length;i++){
			mInCipherBodyLengthStr="0"+mInCipherBodyLengthStr;
		}
		cLogger.info("请求报文体长度：" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);

		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 8,
				mInClearBodyBytes.length);

		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");		
		

		/** 以下处理返回报文 ************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");

		// 处理报文头
		byte[] mOutHeadBytes = new byte[8];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		cLogger.info("返回报文头：" + new String(mOutHeadBytes));
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(
				mOutHeadBytes, 0, 8).trim());
		cLogger.info("返回报文长度：" + mOutCipherBodyLengthInt);
		// 处理报文体
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		for (int tReadSize = 0; tReadSize < mOutCipherBodyBytes.length;) {
			int tRead = mSocketIs.read(mOutCipherBodyBytes, tReadSize, mOutCipherBodyBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文体出错！实际读入长度为：" + tReadSize);
			}
			tReadSize += tRead;
		}
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");

		return mOutCipherBodyBytes;
	}

}
