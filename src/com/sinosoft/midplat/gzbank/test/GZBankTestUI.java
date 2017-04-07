package com.sinosoft.midplat.gzbank.test;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class GZBankTestUI {
	private final static Logger cLogger = Logger.getLogger(GZBankTestUI.class);

	private final String cIP;
	private final int cPort;

	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ...");
		
		String mIP = "127.0.0.1";//����
		int mPort = 9004;
		
		String mFuncFlag = null;
		String mInFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\GZbank\\";
		
		//�µ��˱�
//		mFuncFlag = "9000102";
//		mInFilePath += "������������.xml";
		//�µ��б�
		mFuncFlag = "9000103";
		mInFilePath += "�ɷѳ�������.xml";
		//�����ش�
//		mFuncFlag = "9000801";
//		mInFilePath += "�����ش�.xml";
		//���ճ���
//		mFuncFlag = "9000901";
//		mInFilePath += "���ճ���.xml";
		
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
		GZBankTestUI mTestUI = new GZBankTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);
		JdomUtil.print(JdomUtil.build(mOutBytes));
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		mFos.write(mOutBytes);

		System.out.println("�ɹ�������");
	}

	public GZBankTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInBodyBytes = IOTrans.toBytes(pInputStream);

		byte[] mInTotalBytes = new byte[mInBodyBytes.length + 19];

		//�����峤��
		String mInBodyLengthStr = String.valueOf(mInBodyBytes.length);
		cLogger.info("�������峤��1��" + mInBodyLengthStr.length());
		int length = mInBodyLengthStr.length();
		for(int i=0;i<6-length;i++){
			mInBodyLengthStr=mInBodyLengthStr+" ";
		}
		cLogger.info("�������峤�ȣ�" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();

		//������
		byte[] InsuBytes = "006    ".getBytes();
		System.arraycopy(InsuBytes, 0, mInTotalBytes, 13, InsuBytes.length);
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);

		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		System.arraycopy(mInBodyBytes, 0, mInTotalBytes, 19, mInBodyBytes.length);
		//�����峤�ȣ�6λ��+���״��루7λ��+Ŀ�걣�չ�˾���루6λ��+������
		cLogger.info("���������ģ�"+new String(mInTotalBytes));
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		/**���´����ر���************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");

		//������ͷ
		byte[] mOutHeadBytes = new byte[19];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		cLogger.info("���ر���ͷ��" + new String(mOutHeadBytes));
		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 0, 6).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutBodyLengthInt);
		cLogger.info("���״��룺" + new String(mOutHeadBytes, 6, 7).trim());

		//��������
		byte[] mOutBodyBytes = new byte[mOutBodyLengthInt];
		for (int tReadSize = 0; tReadSize < mOutBodyBytes.length;) {
			int tRead = mSocketIs.read(mOutBodyBytes, tReadSize, mOutBodyBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ���������ʵ�ʶ��볤��Ϊ��" + tReadSize);
			}
			tReadSize += tRead;
		}
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");

		return mOutBodyBytes;
	}
}

