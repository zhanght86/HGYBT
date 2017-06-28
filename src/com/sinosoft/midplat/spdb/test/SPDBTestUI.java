package com.sinosoft.midplat.spdb.test;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.log4j.Logger;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class SPDBTestUI {
	
	private Logger cLogger = Logger.getLogger(SPDBTestUI.class);
	private String cIP = null;
	private int cPort = 0;
	
	public SPDBTestUI() {
	}
	
	public SPDBTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("����ʼ...");

		String mIP = "127.0.0.1";
		int mPort = 9003;

		String mFuncFlag = "";
		String mInFilePath="D:/File/task/20170424/spdb/ybt_test/";
		
		//�̵ƽ���
//		mFuncFlag = "1000";
//		mInFilePath += "1000in_noStd.xml";
		//����Լ����
//		mFuncFlag = "1001";
//		mInFilePath += "1001in_noStd.xml";
		//����ԼͶ��
//		mFuncFlag = "1002";
//		mInFilePath += "1002in_noStd.xml";
		//���շ�����
//		mFuncFlag = "1003";
//		mInFilePath += "1003in_noStd.xml";
		//������ӡ
//		mFuncFlag = "1004";
//		mInFilePath += "1004in_noStd.xml";
		//��������ӡ
		mFuncFlag = "1005";
		mInFilePath += "1005in_noStd.xml";
		
		String mOutFilePath = "D:/File/task/20170424/spdb/ybt_test/1005out_noStd.xml";

		SPDBTestUI mTest = new SPDBTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		byte[] mOutBytes = mTest.sendRequest(mFuncFlag, mIs);
		JdomUtil.print(JdomUtil.build(mOutBytes));
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		mFos.write(mOutBytes);
		mFos.close();
		System.out.println("�ɹ�������");
	}
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {

		cLogger.info("Socket����" + cIP + ":" + cPort);
		System.out.println("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInBodyBytes = IOTrans.toBytes(pInputStream);
		byte[] mInTotalBytes = new byte[mInBodyBytes.length + 16];

		// �����峤��
		String mInBodyLengthStr = String.valueOf(mInBodyBytes.length);
		cLogger.info("�������峤��1��" + mInBodyLengthStr.length());
		int length = mInBodyLengthStr.length();
		for(int i=0;i<8-length;i++){
			mInBodyLengthStr=mInBodyLengthStr+" ";
		}
		cLogger.info("�������峤�ȣ�" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
		
		//��ͷ
		byte[] InsuBytes = "INSU8000".getBytes();
		System.arraycopy(InsuBytes, 0, mInTotalBytes, 0, InsuBytes.length);
		//����
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 8,mInLengthBytes.length);
		//����	
		System.arraycopy(mInBodyBytes, 0, mInTotalBytes, 16,mInBodyBytes.length);
		//��ͷ(INSU8000)+����(8λ)+����
		cLogger.info("���������ģ�"+new String(mInTotalBytes));
		mSocket.getOutputStream().write(mInTotalBytes);
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/ 1000.0 + "s");		
		
		/** ���´����ر��� ************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");

		// ������ͷ
		byte[] mOutHeadBytes = new byte[16];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		cLogger.info("���ر���ͷ��" + new String(mOutHeadBytes));
		cLogger.info("���ر��İ�ͷ��" + new String(mOutHeadBytes, 0, 8).trim());
		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 8, 8).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutBodyLengthInt);
		// ��������
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
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/ 1000.0 + "s");
		
		return mOutBodyBytes;
	}
	
	
}
