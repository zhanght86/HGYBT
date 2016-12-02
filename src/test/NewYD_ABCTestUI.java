/**
 * 工行总行测试程序主方类
 * 
 * ChenGB(陈贵菠) 2008.12.10
 */

package test;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.jdom.Document;

import test.security.DESCipher;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class NewYD_ABCTestUI {
	private Logger cLogger = Logger.getLogger(getClass());
	
	private String cIP = null; 
	private int cPort = 0;
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");
	
		//本地
		String mIP = "127.0.0.1";
		int mPort = 35001;
		
		String mFuncFlag = null;	//01-新保试算，02-新保交费，03-当日撤单, 04-自动冲正
		
		mFuncFlag = "01";
		String mInFilePathName = "";
		String mOutFilePathName = "";
			 mInFilePathName = "E:/17466_8_1_in-utf.xml";
			 mOutFilePathName = "E:/17466_8_1_in-utf返回.xml";
		
//		 mInFilePathName = "D:/test/ZHH/农行/temp/29601_725_1012_in.xml";
//		 mOutFilePathName = "D:/test/ZHH/农行/temp/29601_725_1012_Out.xml";
		
		
		NewYD_ABCTestUI mTestUI = new NewYD_ABCTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePathName);
		byte[] mBodyBytes = mTestUI.sendRequest(mFuncFlag, mIs);
		System.out.println(new String(mBodyBytes));
		OutputStream mOs = new FileOutputStream(mOutFilePathName);
		Document mOutXmlDoc = JdomUtil.build(mBodyBytes,"UTF-8");
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	}
	
	public NewYD_ABCTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	public NewYD_ABCTestUI() {
	}
	
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 16];
		
		//报文体长度
		String mInCipherBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		cLogger.info("请求报文长度：" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		
		//交易代码
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);
		
		//公司代码
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10, mInsuIDBytes.length);
		
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 16, mInClearBodyBytes.length);
		
		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
//		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		
		
		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");
		
		//处理报文头
		byte[] mOutHeadBytes = new byte[6];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutCipherBodyLengthInt);
//		cLogger.info("交易代码：" + new String(mOutHeadBytes, 6, 4).trim());
//		cLogger.info("保险公司代码：" + new String(mOutHeadBytes, 10, 6).trim());
		
		//处理报文体
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
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mOutCipherBodyBytes;
	}
	
	
	
	
	public byte[] sendRequestUI(String pFuncFlag, Document document) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);
		
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 16];
		
		//报文体长度
		String mInCipherBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		cLogger.info("请求报文长度：" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		
		//交易代码
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);
		
		//公司代码
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10, mInsuIDBytes.length);
		
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 16, mInClearBodyBytes.length);
		
		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
//		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		
		
		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");
		
		//处理报文头
		byte[] mOutHeadBytes = new byte[6];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutCipherBodyLengthInt);
//		cLogger.info("交易代码：" + new String(mOutHeadBytes, 6, 4).trim());
//		cLogger.info("保险公司代码：" + new String(mOutHeadBytes, 10, 6).trim());
		
		//处理报文体
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
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mOutCipherBodyBytes;
	}
	
	
	
	
	
	
	public byte[] sendRequest(String pFuncFlag, Document document)
	throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		System.out.println("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);

		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 16];
		
		// 报文体长度
		String mInCipherBodyLengthStr = String
				.valueOf(mInClearBodyBytes.length);
		cLogger.info("请求报文长度：" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);
		
		// 交易代码
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6,
				mFuncFlagBytes.length);
		
		// 公司代码
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10,
				mInsuIDBytes.length);
		
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 16,
				mInClearBodyBytes.length);
		
		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
		// mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");
		System.out.println();
		
		/** 以下处理返回报文 ************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");
		
		// 处理报文头
		byte[] mOutHeadBytes = new byte[16];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(
				mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutCipherBodyLengthInt);
		cLogger.info("交易代码：" + new String(mOutHeadBytes, 6, 4).trim());
		cLogger.info("保险公司代码：" + new String(mOutHeadBytes, 10, 6).trim());
		
		// 处理报文体
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");

		return mOutCipherBodyBytes;
	}	
	
//	public Document getXmlFromLis() {
//		// add by zhj 用于测试
//		
//		InputStream mIs = null;
//		try {
//			mIs = new FileInputStream(mInStr);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
//		Document mOutXmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
//		//JdomUtil.print(mOutXmlDoc);
//		return mOutXmlDoc;
//	}
}

