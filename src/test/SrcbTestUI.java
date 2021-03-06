/**
 * 上海农商银行测试程序主类
 * 
 */

package test;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;


public class SrcbTestUI {
	private Logger cLogger = Logger.getLogger(getClass());

	private String cIP = null;
	private int cPort = 0;

	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");

		 String mIP ="127.0.0.1";
		 int mPort= 35004;
		 
		String mInFilePath = "H:/李路/犹豫期退保确认_in.xml";
		String mOutFilePath = "H:/李路/犹豫期退保确认_outNoStd.xml";

		
		SrcbTestUI mTestUI = new SrcbTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTestUI.sendRequest(mIs);
//		 JdomUtil.build(mIs);
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		
		JdomUtil.print(mOutXmlDoc);
		mFos.flush();
		mFos.close();
		// mFos.write(mOutBytes);

		System.out.println("成功结束！");
	}

	public SrcbTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(InputStream pInputStream) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);

		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 8];

		// 报文体长度
		String mInBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		cLogger.info("请求报文长度：" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);

		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 8,
				mInClearBodyBytes.length);

		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
		// mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");
		System.out.println();

		/** 以下处理返回报文*********************** */
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");

		// 处理报文头
		byte[] mOutHeadBytes = new byte[6];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize,
					mOutHeadBytes.length - tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入："
						+ new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes)
				.trim());
		cLogger.info("请求报文长度：" + mOutBodyLengthInt);

		// 处理报文体
		byte[] mOutCipherBodyBytes = new byte[mOutBodyLengthInt];
		for (int tReadSize = 0; tReadSize < mOutCipherBodyBytes.length;) {
			int tRead = mSocketIs.read(mOutCipherBodyBytes, tReadSize,
					mOutCipherBodyBytes.length - tReadSize);
			if (-1 == tRead) {
				throw new IOException("获取报文体出错！实际读入长度为：" + tReadSize);
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
