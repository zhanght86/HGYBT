/**
 * �������в��Գ���������
 * 
 * ChenGB(�¹�) 2008.12.10
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
		System.out.println("����ʼ...");
	
		//����
		String mIP = "127.0.0.1";
		int mPort = 35001;
		
		String mFuncFlag = null;	//01-�±����㣬02-�±����ѣ�03-���ճ���, 04-�Զ�����
		
		mFuncFlag = "01";
		String mInFilePathName = "";
		String mOutFilePathName = "";
			 mInFilePathName = "E:/17466_8_1_in-utf.xml";
			 mOutFilePathName = "E:/17466_8_1_in-utf����.xml";
		
//		 mInFilePathName = "D:/test/ZHH/ũ��/temp/29601_725_1012_in.xml";
//		 mOutFilePathName = "D:/test/ZHH/ũ��/temp/29601_725_1012_Out.xml";
		
		
		NewYD_ABCTestUI mTestUI = new NewYD_ABCTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePathName);
		byte[] mBodyBytes = mTestUI.sendRequest(mFuncFlag, mIs);
		System.out.println(new String(mBodyBytes));
		OutputStream mOs = new FileOutputStream(mOutFilePathName);
		Document mOutXmlDoc = JdomUtil.build(mBodyBytes,"UTF-8");
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("�ɹ�������");
	}
	
	public NewYD_ABCTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	public NewYD_ABCTestUI() {
	}
	
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		
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
		byte[] mOutHeadBytes = new byte[6];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutCipherBodyLengthInt);
//		cLogger.info("���״��룺" + new String(mOutHeadBytes, 6, 4).trim());
//		cLogger.info("���չ�˾���룺" + new String(mOutHeadBytes, 10, 6).trim());
		
		//��������
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		for (int tReadSize = 0; tReadSize < mOutCipherBodyBytes.length;) {
			int tRead = mSocketIs.read(mOutCipherBodyBytes, tReadSize, mOutCipherBodyBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ���������ʵ�ʶ��볤��Ϊ��" + tReadSize);
			}
			tReadSize += tRead;
		}
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mOutCipherBodyBytes;
	}
	
	
	
	
	public byte[] sendRequestUI(String pFuncFlag, Document document) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);
		
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
		byte[] mOutHeadBytes = new byte[6];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutCipherBodyLengthInt);
//		cLogger.info("���״��룺" + new String(mOutHeadBytes, 6, 4).trim());
//		cLogger.info("���չ�˾���룺" + new String(mOutHeadBytes, 10, 6).trim());
		
		//��������
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		for (int tReadSize = 0; tReadSize < mOutCipherBodyBytes.length;) {
			int tRead = mSocketIs.read(mOutCipherBodyBytes, tReadSize, mOutCipherBodyBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ���������ʵ�ʶ��볤��Ϊ��" + tReadSize);
			}
			tReadSize += tRead;
		}
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mOutCipherBodyBytes;
	}
	
	
	
	
	
	
	public byte[] sendRequest(String pFuncFlag, Document document)
	throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		System.out.println("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);

		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 16];
		
		// �����峤��
		String mInCipherBodyLengthStr = String
				.valueOf(mInClearBodyBytes.length);
		cLogger.info("�����ĳ��ȣ�" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);
		
		// ���״���
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6,
				mFuncFlagBytes.length);
		
		// ��˾����
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10,
				mInsuIDBytes.length);
		
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 16,
				mInClearBodyBytes.length);
		
		cLogger.info("���������ģ�");
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
		byte[] mOutHeadBytes = new byte[16];
		IOTrans.readFull(mOutHeadBytes, mSocketIs);
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(
				mOutHeadBytes, 0, 6).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutCipherBodyLengthInt);
		cLogger.info("���״��룺" + new String(mOutHeadBytes, 6, 4).trim());
		cLogger.info("���չ�˾���룺" + new String(mOutHeadBytes, 10, 6).trim());
		
		// ��������
		byte[] mOutCipherBodyBytes = new byte[mOutCipherBodyLengthInt];
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");

		return mOutCipherBodyBytes;
	}	
	
//	public Document getXmlFromLis() {
//		// add by zhj ���ڲ���
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

