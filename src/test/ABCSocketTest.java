/**
 * ũ�����в��Գ�������
 * ChenGB(�¹�) 2008.12.10
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
		System.out.println("����ʼ..."); 
		
		//����
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
		
		//��������  ����
//		String mIP = "10.1.19.2";
//		int mPort = 52004;

		String mFuncFlag = null;
		
		//�µ�Ͷ��
		mFuncFlag = "01";
		String mInFilePath = "C:\\Users\\star\\Desktop\\abcin.xml";
		String mOutFilePath = "E:\\abc.xml";
		
//		mFuncFlag = "02";
//		String mInFilePath = "C:\\Users\\lmt\\Desktop\\1767576_33046_101_in.xml" ;
//		String mOutFilePath = "E\\abc.xml";
		
//		mFuncFlag = "03";
//		String mInFilePath = "E:\\���չ�˾\\����\\����\\ũ��\\���ճ���.xml" ;
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
		
		System.out.println("�ɹ�������");
	}
	
	public ABCSocketTest(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	 
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
//		System.out.println("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		
		//����
		
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 16];
		
		//�����峤��
		String mInCipherBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		cLogger.info("�����ĳ��ȣ�" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		
		//���״���
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);
		
		//��˾����
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10, mInsuIDBytes.length);
		
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 16, mInClearBodyBytes.length);
		
		cLogger.info("���������ģ�");
		mSocket.getOutputStream().write(mInTotalBytes);
//		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		
		
		/**���´����ر���************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");
		
		//������ͷ
		byte[] mOutHeadBytes = new byte[16];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutCipherBodyLengthInt);
		cLogger.info("���״��룺" + new String(mOutHeadBytes, 6, 4).trim());
		cLogger.info("���չ�˾���룺" + new String(mOutHeadBytes, 10, 6).trim());
		
		//��������
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
//		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
//		mSocket.close();
//		mCurTimeMillis = System.currentTimeMillis();
//		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
			
		
		return mOutCipherBodyBytes;
	}
	
}
