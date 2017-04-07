/**
 * ���в��Գ���������
 */
package com.sinosoft.midplat.boc.test;


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

public class BOCTestUI {
	public BOCTestUI() {
	};

	private Logger cLogger = Logger.getLogger(BOCTestUI.class);
	private String cIP = null;
	private int cPort = 0;

	public BOCTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ...");

		String mIP = "127.0.0.1";
		int mPort = 9001;

		String mFuncFlag = "";
		String mInFilePath="C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\boc\\";
		
		//����
		mFuncFlag = "1001";
		mInFilePath += "��������1001.xml";
		//�б�
//		mFuncFlag = "1002";
//		mInFilePath += "�ɷѳ���1002.xml";
		//�����ش�
//		mFuncFlag = "1003";
//		mInFilePath += "�����ش�1003.xml";
		//���ճ���
//		mFuncFlag = "1004";
//		mInFilePath += "��������1004.xml";
		//���ڲ�ѯ
//		mFuncFlag = "1005";
//		mInFilePath += "���ڽɷѲ�ѯ����.xml";
		//���ڽɷ�
//		mFuncFlag = "1006";
//		mInFilePath += "���ڽɷ�����.xml";
		//�˱�����
//		mFuncFlag = "1007";
//		mInFilePath += "�˱����ڸ�������1007.xml";
		//�˱�ȷ��
//		mFuncFlag = "1008";
//		mInFilePath += "�˱����ڸ���ȷ��1008.xml";
		//���ֹ�����
//		mFuncFlag = "1011";
//		mInFilePath += "���д��ֹ���������.xml";
		
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";

		BOCTestUI mTest = new BOCTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTest.sendRequest(mFuncFlag, mIs);

		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
		
		System.out.println("�ɹ�������");
	}
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream)
			throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		System.out.println("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 8];

		// �����峤��
		String mInCipherBodyLengthStr = String
				.valueOf(mInClearBodyBytes.length);
		cLogger.info("�������峤��1��" + mInCipherBodyLengthStr.length());
		int length = mInCipherBodyLengthStr.length();
		for(int i=0;i<8-length;i++){
			mInCipherBodyLengthStr="0"+mInCipherBodyLengthStr;
		}
		cLogger.info("�������峤�ȣ�" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);

		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 8,
				mInClearBodyBytes.length);

		cLogger.info("���������ģ�");
		mSocket.getOutputStream().write(mInTotalBytes);
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");		
		

		/** ���´����ر��� ************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");

		// ������ͷ
		byte[] mOutHeadBytes = new byte[8];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		cLogger.info("���ر���ͷ��" + new String(mOutHeadBytes));
		int mOutCipherBodyLengthInt = Integer.parseInt(new String(
				mOutHeadBytes, 0, 8).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutCipherBodyLengthInt);
		// ��������
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
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)
				/ 1000.0 + "s");

		return mOutCipherBodyBytes;
	}

}
