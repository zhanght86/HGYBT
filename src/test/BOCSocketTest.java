/**
 * 中行总行测试程序主类
 * ChenGB(陈贵菠) 2008.12.10
 */

package test;

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

public class BOCSocketTest {
	private final static Logger cLogger = Logger.getLogger(BOCSocketTest.class);

	private final String cIP;
	private final int cPort;

	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");
		
		//本地
		String mIP = "127.0.0.1";
		int mPort = 52004;
		//uat
//		String mIP = "10.1.1.116";
//		int mPort = 52004;
		
		//dat
//		String mIP = "10.1.1.216";
//		int mPort = 52004;
		
		//VIR
//		String mIP = "10.9.3.124";
//		int mPort = 52004;
		
		//生产环境  慎用
//		String mIP = "10.1.19.2";
//		int mPort = 52004;
		/**
		 * 6000112-新单投保
		 * 6000113-承保收费
		 * 6000801-保单重打
		 * 6000901-当日撤单
		 */
		String mFuncFlag = null;
		
		//新单投保
		mFuncFlag = "1001";
		String mInFilePath = "E:\\保险公司（银保通）\\中融人寿\\测试报文\\zrS+Q\\zhonghang\\2477694_7642_201_in.xml";
		mInFilePath="D:/task/20161129/boc/1001in_noStd.xml";
		String mOutFilePath = "E:\\boc.xml";
		mOutFilePath="D:/task/20161129/boc/1001out_noStd.xml";
		
//		mFuncFlag = "1002";
//		String mInFilePath = "E:\\保险公司（银保通）\\中融人寿\\测试报文\\zrS+Q\\zhonghang\\2477700_7646_101_in.xml" ;
//		String mOutFilePath = "E:\\bo.xml";
		
//		mFuncFlag = "1003";
//		String mInFilePath = "E:\\保险公司\\中融\\报文\\中行提供报文\\交易报文模板\\保单重打请求报文.xml" ;
//		String mOutFilePath = "E\\boc_返回结果.xml";
		
//		mFuncFlag = "1004";
//		String mInFilePath = "E:\\保险公司\\中融\\报文\\中行提供报文\\交易报文模板\\当日契撤请求报文.xml";
//		String mOutFilePath = "E:\\boc.xml";
		
//		mFuncFlag = "1005";
//		String mInFilePath = "C:\\Documents and Settings\\Administrator\\桌面\\1000031388_25_441_in.xml";
//		String mOutFilePath = "E\\boc_返回结果.xml";
//		mFuncFlag = "1006";
//		String mInFilePath = "C:\\Documents and Settings\\Administrator\\桌面\\1000031394_27_442_in.xml";
//		String mOutFilePath = "E\\boc_返回结果.xml";
		
		BOCSocketTest mTestUI = new BOCSocketTest(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);

		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
//		mFos.write(mOutBytes);
		
		System.out.println("成功结束！");
	}
	
	public BOCSocketTest(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInBodyBytes = IOTrans.toBytes(pInputStream);

		byte[] mInTotalBytes = new byte[mInBodyBytes.length + 8];

		//报文体长度
		String mInBodyLengthStr = String.valueOf(mInBodyBytes.length);
		cLogger.info("请求报文长度：" + mInBodyLengthStr);
		
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		System.out.println("length = "+mInLengthBytes.length);
		//交易代码
		System.arraycopy(mInBodyBytes, 0, mInTotalBytes, 8, mInBodyBytes.length);
		cLogger.info("发送请求报文！");
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();


		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");

		//处理报文头
		byte[] mOutHeadBytes = new byte[8];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("获取报文头出错！实际读入：" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("返回报文长度：" + mOutBodyLengthInt);

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
