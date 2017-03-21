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
		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1001in_noStd.xml";
		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1001out_noStd.xml";
		
		//缴费出单
//		mFuncFlag = "1002";
//		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1002in_noStd.xml" ;
//		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1002out_noStd.xml";
		
		//保单重打
//		mFuncFlag = "1003";
//		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1003in_noStd.xml" ;
//		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1003out_noStd.xml";
		
		//当日契撤
//		mFuncFlag = "1004";
//		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1004in_noStd.xml";
//		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1004out_noStd.xml";
		
		//续期缴费查询
//		mFuncFlag = "1005";
//		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1005in_noStd.xml";
//		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1005out_noStd.xml";
		
		//续期缴费
//		mFuncFlag = "1006";
//		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1006in_noStd.xml";
//		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1006out_noStd.xml";
		
		//退保\满期给付试算
//		mFuncFlag = "1007";
//		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1007in_noStd.xml";
//		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1007out_noStd.xml";
		
		//退保\满期给付确认
//		mFuncFlag = "1008";
//		String mInFilePath = "D:/File/task/20170320/boc/ybt_test/1008in_noStd.xml";
//		String mOutFilePath = "D:/File/task/20170320/boc/ybt_test/1008out_noStd.xml";
		
		BOCSocketTest mTestUI = new BOCSocketTest(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);

		//测试
		/*Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();*/
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
