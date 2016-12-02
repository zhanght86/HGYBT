package test;

/**
 * ��ũ�����в��Գ�������
 * ChenGB(�¹�) 2014.08.10
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
		System.out.println("����ʼ...");

		// ����
		String mIP = "127.0.0.1";
		// mIP = "123.151.19.115";
//		mIP = "10.3.1.204";
//		mIP = "10.11.2.59";
//		mIP = "10.3.40.18";
		// dat
		// String mIP = "10.1.1.216";
		// uat
		// String mIP = "10.9.3.116";
		// VIR����
		// String mIP = "10.9.3.124";
		// �������� ����
		// String mIP = "10.1.19.2";

		int mPort = 8899;
		
//		mPort =8101;
		aesC = new AESCipher(mPort);

		String mFuncFlag = null;

		// �µ�Ͷ��
		mFuncFlag = "1002"; // ����
//		 mFuncFlag="1004"; //ǩ��
		// mFuncFlag="1018"; //�ش�
		// mFuncFlag="1016"; //��ѯ
		// mFuncFlag="1010"; //���ճ���
//		 mFuncFlag="1000"; //��������
		// mFuncFlag="1012"; //��ȫ��ѯ
		// mFuncFlag="1013"; //��ȫ����
		// mFuncFlag="1014"; //��ȫ״̬��ѯ
		// mFuncFlag="1007"; //���ڽɷѲ�ѯ
		// mFuncFlag="1008"; //���ڽɷ�
		// mFuncFlag="1009";
		// mFuncFlag="1019";
		// mFuncFlag="1011";
		// mFuncFlag = "1006";

		String filename = mFuncFlag + ".xml";
		// String mInFilePath =
		// "E:\\���չ�˾������ͨ��\\��������\\���Ա���\\zrS+Q\\nonghang\\2474451_4778_23_in.xml";
		// String mInFilePath =
		// "E:\\���չ�˾������ͨ��\\��������\\���Ա���\\zrS+Q\\nonghang\\2474466_4794_101_in.xml";
		// String mInFilePath =
		// "E:\\���չ�˾������ͨ��\\��������\\����ͨ�ĵ�\\ũ��\\��ũ��\\��ũ�в��Ա���\\ABC\\��ȫ��ѯ.xml";
		String mInFilePath = "C:\\Users\\star\\Desktop\\abcin.xml";
//		mInFilePath = "F:\\xml\\ABC\\" + filename;
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_ȡ��_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_����_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_bq��ѯ_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_bq����_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_���ڲ�ѯ_inNostd.xml";
		// String mInFilePath =
		// "C:/Users/XiaoLong/Desktop/newabc/abc_���ڽɷ�_inNostd.xml";
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

		System.out.println("�ɹ�������");
	}

	public ABCNewTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream)
			throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		try {
			aes = new AESCipher(cPort);
		} catch (IOException e) {
			e.printStackTrace();
			cLogger.debug("��Կ����ʧ��...");
		}

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);

		// ���ܱ����峤��
		ByteArrayInputStream tempoutputStream = new ByteArrayInputStream(
				mInClearBodyBytes);

		// ���ļ��ܿ�ʼ...
		aes.encryption(tempoutputStream);
		InputStream outputs = aes.getCipherStream();
		// ���ļ������...
		ByteArrayOutputStream outputs1 = new ByteArrayOutputStream();
		int x = 0;
		while ((x = outputs.read()) != -1) {
			outputs1.write(x);
		}
		mInClearBodyBytes = outputs1.toByteArray();

		// ԭ���ܳ���
		// mInClearBodyBytes =
		// ABCNewTestUI.bytesTo16String(aesC.aES128cbcEnCrypt(mInClearBodyBytes)).getBytes();

		String mInCipherBodyLengthStr = String
				.valueOf(mInClearBodyBytes.length);
		cLogger.info("�����ĳ��ȣ�" + mInCipherBodyLengthStr);
		// byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		String xDocBodyStr = "X1.0"
				+ ("00000000".substring(0, 8 - mInCipherBodyLengthStr.length()) + mInCipherBodyLengthStr)
				+ "    113012345                                      000000000";

		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 73];
		System.arraycopy(xDocBodyStr.getBytes(), 0, mInTotalBytes, 0,
				xDocBodyStr.length());

		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 73,
				mInClearBodyBytes.length);

		// ����
		cLogger.info("mInClearBodyBytes:" + mInClearBodyBytes.length);

		// byte[] xAESDocBodyStr =
		// aesC.aES128cbcEnCrypt(xDocBodyStr.getBytes());
		// System.out.println();

		// ����ͷ
		// String xResLengthStr = mInClearBodyBytes.length + "";//���ܱ����峤��
		//
		// System.arraycopy(xResLengthStr.getBytes(), 0, mInTotalBytes, 0,
		// xResLengthStr.length());
		//
		// System.arraycopy("AES128".getBytes(), 0, mInTotalBytes, 8,
		// "AES128".length());//�㷨
		//
		// System.arraycopy("123123".getBytes(), 0, mInTotalBytes, 16,
		// "123123".length());//��˾����
		//
		// System.arraycopy("1002".getBytes(), 0, mInTotalBytes, 22,
		// "1002".length());//��������
		//
		// byte[] spotCode =
		// aesC.aES128cbcEnCrypt("ABCHINA..ANIHCBA".getBytes());
		// System.arraycopy(spotCode, 0, mInTotalBytes, 26,
		// spotCode.length);//ʶ����
		//
		// System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 58,
		// mInClearBodyBytes.length);

		// //���״���
		// byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		// System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6,
		// mFuncFlagBytes.length);
		//
		// //��˾����
		// byte[] mInsuIDBytes = "001".getBytes();
		// System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10,
		// mInsuIDBytes.length);

		// System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 204,
		// mInClearBodyBytes.length);

		cLogger.info("���������ģ�");
		System.out.println(new String(mInTotalBytes));

		mSocket.getOutputStream().write(mInTotalBytes);
		// mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");
		System.out.println();

		/** ���´����ر��� ************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");

		// ������ͷ
		byte[] mOutHeadBytes = new byte[73];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);

		cLogger.info("�������ı���tou��" + new String(mOutHeadBytes));

		int mOutCipherBodyLengthInt = Integer.parseInt(new String(
				mOutHeadBytes, 4, 8).trim());
		cLogger.info("���ؼ��ܱ������峤�ȣ�" + mOutCipherBodyLengthInt);

		// ��������
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");

		ByteArrayInputStream tempinputStream = new ByteArrayInputStream(
				mOutCipherBodyBytes);
		// ���Ľ��ܿ�ʼ...
		aes.decryption(tempinputStream);
		InputStream pIs2 = aes.getClearStream();
		// ���Ľ��ܽ���...
		ByteArrayOutputStream ins = new ByteArrayOutputStream();
		x = 0;
		while ((x = pIs2.read()) != -1) {
			ins.write(x);
		}
		mOutCipherBodyBytes = ins.toByteArray();

		// ԭ���ܳ���
		// mOutCipherBodyBytes = ABCNewTestUI.hex16StringToByte(new
		// String(mOutCipherBodyBytes));
		// mOutCipherBodyBytes = aesC.aES128cbcDeCrypt(mOutCipherBodyBytes);
		cLogger.info("����ҵ����ܱ����壺" + new String(mOutCipherBodyBytes));

		String mDocBodyStr = new String(mOutCipherBodyBytes);

		cLogger.info("����ҵ����ti��" + mDocBodyStr);

		return mDocBodyStr.getBytes();
	}

	/** */
	/**
	 * ���ֽ�����ת����16�����ַ���
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
	 * ��16�����ַ���ת�����ֽ�����
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
