/**
 * 农行总行测试程序主方类
 * 
 * ChenGB(陈贵菠) 2008.12.10
 */

package com.sinosoft.midplat.newabc.test;

import java.io.FileInputStream;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.jdom.Document;


import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.newabc.ABCDocUtil;
import com.sinosoft.midplat.newabc.util.*;;
public class NewABCTest {
	private Logger cLogger = Logger.getLogger(getClass());
	
	private String cIP = null; 
	private int cPort = 0;
	public static void main(String[] args) throws Exception {
		String mIP = "127.0.0.1";
		int mPort = 9002;
		/**
		 * 1000 	心跳交易      1000   HeartBeat
		 * 1002 	新单试算
		 * 1004 	新单缴费
		 * 1006 	非实时出单申请
		 * 1007		续期缴费查询
		 * 1008 	续期缴费
		 * 1009 	取消交易
		 * 1010 	当日撤单
		 * 1016 	保单查询
		 * 1017 	文件下载
		 * 1018 	保单重打
		 * 1020 	非实时出单申请查询
		 * 3013          保全申请
		 * */
		
		String mFuncFlag = "";
		String mInFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\abc\\";
		
		//新单试算
//		mFuncFlag = "1002";
//		mInFilePath += "新单试算.xml";
		
		//新单核保-网银渠道
//		mFuncFlag = "1002";
//		mInFilePath += "新单核保-网银渠道.xml";
		
		//新单承保
//		mFuncFlag = "1004";
//		mInFilePath += "新单承保.xml";
		
		//新单撤单
//		mFuncFlag = "1010";
//		mInFilePath += "新单承保.xml";
		
		//取消交易
//		mFuncFlag = "1009";
//		mInFilePath += "取消交易.xml";
		
		//续期查询
		mFuncFlag = "1007";
		mInFilePath += "续期查询.xml";
		
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		String mABCB2IS=JdomUtil.toString(JdomUtil.build(mIs));
		mABCB2IS=mABCB2IS.substring(mABCB2IS.indexOf("<ABCB2I>"));
		byte[] mABCB2IB=mABCB2IS.getBytes();
		NewABCTest mTestUI = new NewABCTest(mIP, mPort);
		Document mOutXmlDoc = mTestUI.sendRequest(mFuncFlag, mABCB2IB);
		System.out.println("实际返回报文为：");
		JdomUtil.print(mOutXmlDoc);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
		System.out.println("成功结束！");
	}
	
	public NewABCTest(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	public NewABCTest() {
	}
	
	public Document sendRequest(String pFuncFlag, byte[] doc) throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		String cInsuID="1147";   //华贵保险公司代码
		String endxmlStr=AES.Encrypt(AES.rpadEncrypt(new String(doc),' '));
		

		byte[] outBytes=endxmlStr.getBytes("UTF-8");
		System.out.println("xml字符串的报文长度："+outBytes.length);
        String   cSInsuID=AbcMidplatUtil.rpad(cInsuID, 8, ' ');
		
		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		sHeadBytes = "X1.0"+sHeadBytes+cSInsuID+"00000                                       000000000";
		byte array[] = sHeadBytes.getBytes();//new byte[sHeadBytes.getBytes().length];
		
		
		cLogger.info("发送报文头！"+new String(array,"UTF-8"));
		mSocket.getOutputStream().write(array);
		mSocket.getOutputStream().write(outBytes);
		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		
		
		/**以下处理返回报文************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("收到返回报文！");
		
		//处理报文头
		//包头73位
		byte[] returnmHeadBytes = new byte[73];
		IOTrans.readFull(returnmHeadBytes, mSocketIs);
		String package_head = new String(returnmHeadBytes);
		//4-12 位是报文体长度
		cLogger.info("package_head:"+package_head);
		int returnmBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //包体长度
		cLogger.info("mBodyLen:"+returnmBodyLen);
		byte[] mReturnBodyBytes = new byte[returnmBodyLen]; //所有的body字节不带xml声明
		IOTrans.readFull(mReturnBodyBytes, mSocketIs);
		
		/**********解密请求****************/
		cLogger.info("解密开始");
		String axx = AES.Decrypt(new String(mReturnBodyBytes,"UTF-8"));
		cLogger.info("解密完成");
		/**********解密完成****************/
		
		System.out.println("解密后的报文:============"+axx);
		Document mXmlDoc_bank = JdomUtil.build(axx.toString());
		cLogger.info("UTF-8 农行的报文: ");
		JdomUtil.print(mXmlDoc_bank);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mXmlDoc_bank;
	}
}

