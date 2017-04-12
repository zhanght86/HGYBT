/**
 * 新农行总行测试程序主方类
 * @author sinosoft
 */

package test;

import java.io.FileInputStream;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

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
//		String mIP="10.2.0.31";
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
		
		String mFuncFlag = "1002";
		mFuncFlag = "1004";
//		mFuncFlag = "1009";//取消交易
//		mFuncFlag = "1010"; //撤单
		mFuncFlag = "1016";//保单查询
//		mFuncFlag = "1018";//重打
//		mFuncFlag = "1012";//保全查询
//		mFuncFlag = "1000";//心跳交易
//		mFuncFlag = "1006";//非实时出单申请
//		mFuncFlag = "1007";//续期缴费信息查询
//		mFuncFlag = "1008";//续期缴费
//		mFuncFlag = "1021";//保单详情查询
		mFuncFlag = "1014";//保全申请状态查询
//		mFuncFlag = "1013";//保全申请（支持犹撤申请、满期给付申请、退保申请）
//		mFuncFlag = "1005";//新单试算结果查询
//		mFuncFlag = "1011";//账户变更
//		mFuncFlag = "1019";//保单价值查询
		
		
		String mInFilePath = "f://xml/ABC1//"+mFuncFlag+".xml";
		//新单试算
		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1002in_noStd.xml";
		//新单缴费
		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1004in_noStd.xml";
		//保单重打
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1018in_noStd.xml";
		//当日撤单
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1010in_noStd.xml";
		//取消交易
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1009in_noStd.xml";
		//保全查询（支持犹撤申请、满期给付申请、退保申请查询）
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1012in_noStd.xml";
		//心跳交易
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1000in_noStd.xml";
		//非实时出单申请
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1006in_noStd.xml";
		//续期缴费信息查询
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1007in_noStd.xml";
		//续期缴费
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1008in_noStd.xml";
		//保单详情查询
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1021in_noStd.xml";
		//保单查询
		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1016in_noStd.xml";
		//保全申请状态查询
		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1014in_noStd.xml";
		//保全申请（支持犹撤申请、满期给付申请、退保申请）
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1013in_noStd.xml";
		//新单试算结果查询
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1005in_noStd.xml";
		//账户变更
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1011in_noStd.xml";
		//保单价值查询
//		mInFilePath="D:/File/task/20170411/newabc/ybt_test/1019in_noStd.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		
		String mABCB2IS=JdomUtil.toString(JdomUtil.build(mIs));
		mABCB2IS=mABCB2IS.substring(mABCB2IS.indexOf("<ABCB2I>"));
		byte[] mABCB2IB=mABCB2IS.getBytes();
		
		//cLogger.info(new String(IOTrans.toBytes(tInNoStdDoc)));
		NewABCTest mTestUI = new NewABCTest(mIP, mPort);
		Document mOutXmlDoc = mTestUI.sendRequest(mFuncFlag, mABCB2IB);
		//cLogger.info(new String(mOutBytes,"UTF-8"));
//		JdomUtil.print(mOutXmlDoc);
//		OutputStream mFos = new FileOutputStream(ABCDocUtil.getOutNoStdPath(mFuncFlag));
//		mFos.flush();
//		mFos.close();
//		OutputStream pOs = new FileOutputStream("D:/task/20161124/test/newabc/"+mFuncFlag+"_out.xml");
		OutputStream pOs = new FileOutputStream("D:/File/task/20170411/newabc/ybt_test/1014out_noStd.xml");
		JdomUtil.output(mOutXmlDoc, pOs);
		pOs.flush();
		pOs.close();
		System.out.println("返回结果---"+mOutXmlDoc.getRootElement()
				.getChild("Header").getChildText("RetMsg"));
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
		
		
		
//		byte[] mHeadBytes=new byte[73];
//		
//		//报文类型
//		byte[] C1Type="X".getBytes();
//		System.arraycopy(C1Type, 0, mHeadBytes, 0, C1Type.length);
//		//版本
//		byte[] C3="1.0".getBytes();
//		System.arraycopy(C3, 0, mHeadBytes, 1, C3.length);
//		//数据包长度
//		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);  
//		String mBody=AES.Encrypt(AES.rpadEncrypt(new String(mInClearBodyBytes),' '));
//		byte[] mInLengthBytes = mBody.getBytes();
//		cLogger.info("报文长度="+mInLengthBytes.length);
//		String mInCipherBodyLengthStr = String.valueOf(mInLengthBytes.length);
//		byte[] mInLengthBytess = mInCipherBodyLengthStr.getBytes();
//		byte[] dateLenth=new byte[8];
//		System.arraycopy(mInLengthBytess, 0, dateLenth, 0, mInLengthBytess.length);                          
//		System.arraycopy(dateLenth, 0, mHeadBytes, 4, dateLenth.length);
//		
//		cLogger.info("发送报文头23423423！==="+new String(dateLenth,"UTF-8")+"=====");
//		//公司代码
//		byte[] C1TypeT="4518    ".getBytes();
//		System.arraycopy(C1TypeT, 0, mHeadBytes, 12, C1TypeT.length);
//		//加密标示
//		byte[] C1TypeK="1".getBytes();
//		System.arraycopy(C1TypeK, 0, mHeadBytes, 20, C1TypeK.length);
//		//加密算法
//		byte[] C1TypeM=" ".getBytes();
//		System.arraycopy(C1TypeM, 0, mHeadBytes, 21, C1TypeM.length);
//		//数据压缩标志
//		byte[] C1TypeH=" ".getBytes();
//		System.arraycopy(C1TypeH, 0, mHeadBytes, 22, C1TypeH.length);
//		//数据压缩算法
//		byte[] C1TypeY=" ".getBytes();
//		System.arraycopy(C1TypeY, 0, mHeadBytes, 23, C1TypeY.length);
//		//摘要算法
//		byte[] C1TypeZ=" ".getBytes();
//		System.arraycopy(C1TypeZ, 0, mHeadBytes, 23, C1TypeZ.length);
//		//摘要
//		StringBuffer sp=new StringBuffer();;
//		for(int i=0;i<=39;i++){
//			sp.append(" ");
//		}
//		
//		byte[] C2TypeZ=sp.toString().getBytes();
//		System.out.println(C2TypeZ.length);
//		System.arraycopy(C2TypeZ, 0, mHeadBytes, 24, C2TypeZ.length);
//		
//		//预留字段00000000
//		byte[] temp="00000000".getBytes();
//		System.arraycopy(temp, 0, mHeadBytes, 65, temp.length);
//		for(int i=0;i<mHeadBytes.length;i++){
//			System.out.print("="+mHeadBytes[i]+"=");
//		}
		String cInsuID="3103";   //中韩人寿保险公司代码
		String endxmlStr=AES.Encrypt(AES.rpadEncrypt(new String(doc),' '));
		

		byte[] outBytes=endxmlStr.getBytes("UTF-8");
		System.out.println("xml字符串的报文长度："+outBytes.length);
        String   cSInsuID=AbcMidplatUtil.rpad(cInsuID, 8, ' ');
		
		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');//00011072
		sHeadBytes = "X1.0"+sHeadBytes+cSInsuID+"00000                                       000000000";//X1.00001107200000                                       000000000
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
//		StringBuffer abc_xml = new StringBuffer();
//		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//		abc_xml.append("\n");
//		abc_xml.append(axx.getBytes());
//		System.out.println("返回的报文:"+abc_xml.toString());
//		byte[] all_xml = abc_xml.toString().getBytes("UTF-8");//#
//		
//		Document mXmlDoc_bank = JdomUtil.build(all_xml,"UTF-8"); //#
		Document mXmlDoc_bank = JdomUtil.build(axx.toString());
		cLogger.info("UTF-8 农行的报文: ");
		JdomUtil.print(mXmlDoc_bank);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mXmlDoc_bank;
	}
}

