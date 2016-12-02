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

public class PsbcbatchTestUI {
	private final static Logger cLogger = Logger.getLogger(PsbcbatchTestUI.class);

	private final String cIP;
	private final int cPort;

	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ...");

		//����
//		String mIP = "127.0.0.1";
//		int mPort = 52003;

		//����DAT
//		String mIP = "10.1.1.216";
//		int mPort = 52003;
        //����UAT
//		String mIP = "10.1.1.116";
//		int mPort = 52003;
        //����VIR
		String mIP = "10.9.3.124";
		int mPort = 52003;

		/**
		 * 1021-�µ�Ͷ��
		 * 1022-�б��շ�
		 * 1013-�����ش�
		 * 1002-���ճ���
		 */
		String mFuncFlag = null;

		//����
		mFuncFlag = "1021";
		String mInFilePath = "E:\\���չ�˾������ͨ��\\��������\\���Ա���\\zrS+Q\\youchu\\2478549_8428_201_in.xml";
		String mOutFilePath = "E:\\innostd.xml";
		//ǩ��ȷ��
//		mFuncFlag = "1022";
//		String mInFilePath = "C:\\Users\\lmt\\Desktop\\zr\\youchu\\2478579_8432_101_in.xml";
//		String mOutFilePath = "E:\\innostd.xml";
		//�ش�
//		mFuncFlag = "1013";
//		String mInFilePath = "E:\\���չ�˾\\����\\�ʴ�����\\16_3639778_chongda_innostd.xml";
//		String mOutFilePath = "E:\\innostd.xml";
		//����
//		mFuncFlag = "1002";
//		String mInFilePath = "E:\\���չ�˾\\����\\�ʴ�����\\16_3327507_chongzheng_innostd.xml";
//		String mOutFilePath = "E:\\innostd.xml";

		PsbcbatchTestUI mTestUI = new PsbcbatchTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
//		Document pXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(pXmlDoc);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);

		//Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		//JdomUtil.output(mOutXmlDoc, mFos);
		//mFos.flush();
		//mFos.close();
		mFos.write(mOutBytes);
//		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
//		JdomUtil.print(mOutXmlDoc);
		System.out.println("�ɹ�������");
	}

	public PsbcbatchTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInBodyBytes = IOTrans.toBytes(pInputStream);

		byte[] mInTotalBytes = new byte[mInBodyBytes.length + 16];

		//�����峤��
		String mInBodyLengthStr = String.valueOf(mInBodyBytes.length);
		cLogger.info("�������峤��1��" + mInBodyLengthStr.length());
		int length = mInBodyLengthStr.length();
		//putongjiaoyi
		for(int i=0;i<8-length;i++){
			mInBodyLengthStr="0"+mInBodyLengthStr;
		}
		//post
//		for(int i=0;i<6-length;i++){
//			mInBodyLengthStr="0"+mInBodyLengthStr;
//		}
		cLogger.info("�������峤�ȣ�" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
//		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 10, mInLengthBytes.length);

		//���״��� ��ͨ����
		byte[] InsuBytes = "INSU".getBytes();
		System.arraycopy(InsuBytes, 0, mInTotalBytes, 0, InsuBytes.length);
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 4, mFuncFlagBytes.length);
		//post
//		byte[] mInsuIDBytes = "POST".getBytes();
//		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 0, mInsuIDBytes.length);
//		//���״���
//		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
//		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 4, mFuncFlagBytes.length);
//		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
		//��ͨ����
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 8, mInLengthBytes.length);
		System.arraycopy(mInBodyBytes, 0, mInTotalBytes, 16, mInBodyBytes.length);
		//post
//		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 10, mInLengthBytes.length);
//		System.arraycopy(mInBodyBytes, 0, mInTotalBytes, 16, mInBodyBytes.length);

		cLogger.info("���������ģ�");
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();


		/**���´����ر���************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");

		//������ͷ
		byte[] mOutHeadBytes = new byte[16];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}
			tReadSize += tRead;
		}
		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes, 10, 6).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutBodyLengthInt);
		cLogger.info("���״��룺" + new String(mOutHeadBytes, 4, 6).trim());

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

