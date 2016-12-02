package test;

/**
 * 新农行总行测试程序主类
 * ChenGB(陈贵菠) 2014.08.10
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;

import test.abc.AESCipher;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class ABCNewTestUI {
	private final static Logger cLogger = Logger.getLogger(ABCNewTestUI.class);

	private AESCipher aes = null;
	private final String cIP;
	private final int cPort;
	private static AESCipher aesC = null;

	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");

		// 本地
		String mIP = "127.0.0.1";
		// mIP = "123.151.19.115";
//		mIP = "10.3.1.204";
//		mIP = "10.11.2.59";
//		mIP = "10.3.40.18";
		// dat
		// String mIP = "10.1.1.216";
		// uat
		// String mIP = "10.9.3.116";
		// VIR环境
		// String mIP = "10.9.3.124";
		// 生产环境 慎用
		// String mIP = "10.1.19.2";

		int mPort = 8899;
		
//		mPort =8101;
		aesC = new AESCipher(mPort);

		String mFuncFlag = null;

		// 新单投保
		mFuncFlag = "1002"; // 试算
//		 mFuncFlag="1004"; //签单
		// mFuncFlag="1018"; //重打
		// mFuncFlag="1016"; //查询
		// mFuncFlag="1010"; //当日撤单
//		 mFuncFlag="1000"; //心跳交易
		// mFuncFlag="1012"; //保全查询
		// mFuncFlag="1013"; //保全申请
		// mFuncFlag="1014"; //保全状态查询
		// mFuncFlag="1007"; //续期缴费查询
		// mFuncFlag="1008"; //续期缴费
		// mFuncFlag="1009";
		// mFuncFlag="1019";
		// mFuncFlag="1011";
		// mFuncFlag = "1006";

		String filename = mFuncFlag + ".xml";
		// String mInFilePath =
		// "E:\\保险公司（银保通）\\中融人寿\\测试报文\\zrS+Q\\nonghang\\2474451_4778_23_in.xml";
		// String mInFilePath =
		// "E:\\保险公司（银保通）\\中融人寿\\测试报文\\zrS+Q\\nonghang\\2474466_4794_101_in.xml";
		// String mInFilePath =
		// "E:\\保险公司（银保通）\\中融人寿\\银保通文档\\农行\\新农行\\新农行测试报文\\ABC\\保全查询.xml";
		String mInFilePath = "C:\\Users\\star\\Desktop\\abcin.xml";
//		mInFilePath = "F:\\xml\\ABC\\" + filename;
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_取消_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_心跳_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_bq查询_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_bq申请_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_续期查询_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_续期缴费_inNostd.xml";
		String outFilename = mFuncFlag + "out.xml";
		String mOutFilePath = "F:\\xml\\ABC\\" + outFilename;
		ABCNewTestUI mTestUI = new ABCNewTestUI(mIP, mPort);

		InputStream mIs = new FileInputStream(mInFilePath);

		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);

		String mOutString = String.valueOf(mOutBytes);
		
		Document mOutXmlDoc = JdomUtil.build(mOutString);
		JdomUtil.print(mOutXmlDoc);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
		// mFos.write(mOutBytes);

		System.out.println("成功结束！");
	}

	public ABCNewTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream)
			throws Exception {
		cLogger.info("Socket连接" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		try {
			aes = new AESCipher(cPort);
		} catch (IOException e) {
			e.printStackTrace();
			cLogger.debug("密钥加载失败...");
		}

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);

		// 加密报文体长度
		ByteArrayInputStream tempoutputStream = new ByteArrayInputStream(
				mInClearBodyBytes);

		// 报文加密开始...
		aes.encryption(tempoutputStream);
		InputStream outputs = aes.getCipherStream();
		// 报文加密完成...
		ByteArrayOutputStream outputs1 = new ByteArrayOutputStream();
		int x = 0;
		while ((x = outputs.read()) != -1) {
			outputs1.write(x);
		}
		mInClearBodyBytes = outputs1.toByteArray();

		// 原加密程序
		// mInClearBodyBytes =
		// ABCNewTestUI.bytesTo16String(aesC.aES128cbcEnCrypt(mInClearBodyBytes)).getBytes();

		String mInCipherBodyLengthStr = String
				.valueOf(mInClearBodyBytes.length);
		cLogger.info("请求报文长度：" + mInCipherBodyLengthStr);
		// byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		String xDocBodyStr = "X1.0"
				+ ("00000000".substring(0, 8 - mInCipherBodyLengthStr.length()) + mInCipherBodyLengthStr)
				+ "    113012345                                      000000000";

		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 73];
		System.arraycopy(xDocBodyStr.getBytes(), 0, mInTotalBytes, 0,
				xDocBodyStr.length());

		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 73,
				mInClearBodyBytes.length);

		// 加密
		cLogger.info("mInClearBodyBytes:" + mInClearBodyBytes.length);

		// byte[] xAESDocBodyStr =
		// aesC.aES128cbcEnCrypt(xDocBodyStr.getBytes());
		// System.out.println();

		// 报文头
		// String xResLengthStr = mInClearBodyBytes.length + "";//加密报文体长度
		//
		// System.arraycopy(xResLengthStr.getBytes(), 0, mInTotalBytes, 0,
		// xResLengthStr.length());
		//
		// System.arraycopy("AES128".getBytes(), 0, mInTotalBytes, 8,
		// "AES128".length());//算法
		//
		// System.arraycopy("123123".getBytes(), 0, mInTotalBytes, 16,
		// "123123".length());//公司编码
		//
		// System.arraycopy("1002".getBytes(), 0, mInTotalBytes, 22,
		// "1002".length());//交易类型
		//
		// byte[] spotCode =
		// aesC.aES128cbcEnCrypt("ABCHINA..ANIHCBA".getBytes());
		// System.arraycopy(spotCode, 0, mInTotalBytes, 26,
		// spotCode.length);//识别码
		//
		// System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 58,
		// mInClearBodyBytes.length);

		// //交易代码
		// byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		// System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6,
		// mFuncFlagBytes.length);
		//
		// //公司代码
		// byte[] mInsuIDBytes = "001".getBytes();
		// System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10,
		// mInsuIDBytes.length);

		// System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 204,
		// mInClearBodyBytes.length);

		cLogger.info("发送请求报文！");
		System.out.println(new String(mInTotalBytes));

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
		byte[] mOutHeadBytes = new byte[73];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);

		cLogger.info("返回明文报文tou：" + new String(mOutHeadBytes));

		int mOutCipherBodyLengthInt = Integer.parseInt(new String(
				mOutHeadBytes, 4, 8).trim());
		cLogger.info("返回加密报文总体长度：" + mOutCipherBodyLengthInt);

		// 处理报文体
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("客户端接收返回报文完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");

		ByteArrayInputStream tempinputStream = new ByteArrayInputStream(
				mOutCipherBodyBytes);
		// 报文解密开始...
		aes.decryption(tempinputStream);
		InputStream pIs2 = aes.getClearStream();
		// 报文解密结束...
		ByteArrayOutputStream ins = new ByteArrayOutputStream();
		x = 0;
		while ((x = pIs2.read()) != -1) {
			ins.write(x);
		}
		mOutCipherBodyBytes = ins.toByteArray();

		// 原解密程序
		// mOutCipherBodyBytes = ABCNewTestUI.hex16StringToByte(new
		// String(mOutCipherBodyBytes));
		// mOutCipherBodyBytes = aesC.aES128cbcDeCrypt(mOutCipherBodyBytes);
		cLogger.info("返回业务加密报文体：" + new String(mOutCipherBodyBytes));

		String mDocBodyStr = new String(mOutCipherBodyBytes);

		cLogger.info("返回业务报文ti：" + mDocBodyStr);

		return mDocBodyStr.getBytes();
	}

	/** */
	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static String bytesTo16String(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/** */
	/**
	 * 把16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex16StringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
}
