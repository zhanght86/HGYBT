/**
 * 农行总行测试程序主类
 * ChenGB(陈贵菠) 2008.12.10
 */

package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class ABCSocketTest {
	private final static Logger cLogger = Logger.getLogger(ABCSocketTest.class);

	private final String cIP;
	private final int cPort;

	public static void main(String[] args) throws Exception {
		System.out.println("程序开始..."); 
		
		//本地
		String mIP = "127.0.0.1";
		int mPort = 8899;
		//uat
//		String mIP = "10.1.1.116";
//		int mPort = 52005;
		
		//dat
//		String mIP = "10.1.1.216";
//		int mPort = 52005;
		
		//VIR
//		String mIP = "10.9.3.124";
//		int mPort = 52005;
		
		//生产环境  慎用
//		String mIP = "10.1.19.2";
//		int mPort = 52004;

		String mFuncFlag = null;
		
		//新单投保
		mFuncFlag = "01";
		String mInFilePath = "C:\\Users\\star\\Desktop\\abcin.xml";
		String mOutFilePath = "E:\\abc.xml";
		
//		mFuncFlag = "02";
//		String mInFilePath = "C:\\Users\\lmt\\Desktop\\1767576_33046_101_in.xml" ;
//		String mOutFilePath = "E\\abc.xml";
		
//		mFuncFlag = "03";
//		String mInFilePath = "E:\\保险公司\\中融\\报文\\农行\\当日撤单.xml" ;
//		String mOutFilePath = "E\\abc.xml";

		
		ABCSocketTest mTestUI = new ABCSocketTest(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);

		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
		mFos.write(mOutBytes);
		
		System.out.println("成功结束！");
	}
	
	public ABCSocketTest(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	 
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
//		System.out.println("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		
		//加密
		
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
		byte[] mOutHeadBytes = new byte[16];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutCipherBodyLengthInt);
		cLogger.info("交易代码：" + new String(mOutHeadBytes, 6, 4).trim());
		cLogger.info("保险公司代码：" + new String(mOutHeadBytes, 10, 6).trim());
		
		//处理报文体
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
//		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
//		mSocket.close();
//		mCurTimeMillis = System.currentTimeMillis();
//		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
			
		
		return mOutCipherBodyBytes;
	}
	
}
