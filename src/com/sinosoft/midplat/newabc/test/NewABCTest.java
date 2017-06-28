/**
 * ũ�����в��Գ���������
 * 
 * ChenGB(�¹�) 2008.12.10
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
		 * 1000 	��������      1000   HeartBeat
		 * 1002 	�µ�����
		 * 1004 	�µ��ɷ�
		 * 1006 	��ʵʱ��������
		 * 1007		���ڽɷѲ�ѯ
		 * 1008 	���ڽɷ�
		 * 1009 	ȡ������
		 * 1010 	���ճ���
		 * 1016 	������ѯ
		 * 1017 	�ļ�����
		 * 1018 	�����ش�
		 * 1020 	��ʵʱ���������ѯ
		 * 3013          ��ȫ����
		 * */
		
		String mFuncFlag = "";
		String mInFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\abc\\";
		
		//�µ�����
//		mFuncFlag = "1002";
//		mInFilePath += "�µ�����.xml";
		
		//�µ��˱�-��������
//		mFuncFlag = "1002";
//		mInFilePath += "�µ��˱�-��������.xml";
		
		//�µ��б�
//		mFuncFlag = "1004";
//		mInFilePath += "�µ��б�.xml";
		
		//�µ�����
//		mFuncFlag = "1010";
//		mInFilePath += "�µ��б�.xml";
		
		//ȡ������
//		mFuncFlag = "1009";
//		mInFilePath += "ȡ������.xml";
		
		//���ڲ�ѯ
		mFuncFlag = "1007";
		mInFilePath += "���ڲ�ѯ.xml";
		
		String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		String mABCB2IS=JdomUtil.toString(JdomUtil.build(mIs));
		mABCB2IS=mABCB2IS.substring(mABCB2IS.indexOf("<ABCB2I>"));
		byte[] mABCB2IB=mABCB2IS.getBytes();
		NewABCTest mTestUI = new NewABCTest(mIP, mPort);
		Document mOutXmlDoc = mTestUI.sendRequest(mFuncFlag, mABCB2IB);
		System.out.println("ʵ�ʷ��ر���Ϊ��");
		JdomUtil.print(mOutXmlDoc);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
		System.out.println("�ɹ�������");
	}
	
	public NewABCTest(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	public NewABCTest() {
	}
	
	public Document sendRequest(String pFuncFlag, byte[] doc) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		String cInsuID="1147";   //�����չ�˾����
		String endxmlStr=AES.Encrypt(AES.rpadEncrypt(new String(doc),' '));
		

		byte[] outBytes=endxmlStr.getBytes("UTF-8");
		System.out.println("xml�ַ����ı��ĳ��ȣ�"+outBytes.length);
        String   cSInsuID=AbcMidplatUtil.rpad(cInsuID, 8, ' ');
		
		String sHeadBytes =AbcMidplatUtil.lpad(String.valueOf(outBytes.length), 8, '0');
		sHeadBytes = "X1.0"+sHeadBytes+cSInsuID+"00000                                       000000000";
		byte array[] = sHeadBytes.getBytes();//new byte[sHeadBytes.getBytes().length];
		
		
		cLogger.info("���ͱ���ͷ��"+new String(array,"UTF-8"));
		mSocket.getOutputStream().write(array);
		mSocket.getOutputStream().write(outBytes);
		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		System.out.println();
		
		
		/**���´����ر���************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");
		
		//������ͷ
		//��ͷ73λ
		byte[] returnmHeadBytes = new byte[73];
		IOTrans.readFull(returnmHeadBytes, mSocketIs);
		String package_head = new String(returnmHeadBytes);
		//4-12 λ�Ǳ����峤��
		cLogger.info("package_head:"+package_head);
		int returnmBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //���峤��
		cLogger.info("mBodyLen:"+returnmBodyLen);
		byte[] mReturnBodyBytes = new byte[returnmBodyLen]; //���е�body�ֽڲ���xml����
		IOTrans.readFull(mReturnBodyBytes, mSocketIs);
		
		/**********��������****************/
		cLogger.info("���ܿ�ʼ");
		String axx = AES.Decrypt(new String(mReturnBodyBytes,"UTF-8"));
		cLogger.info("�������");
		/**********�������****************/
		
		System.out.println("���ܺ�ı���:============"+axx);
		Document mXmlDoc_bank = JdomUtil.build(axx.toString());
		cLogger.info("UTF-8 ũ�еı���: ");
		JdomUtil.print(mXmlDoc_bank);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mXmlDoc_bank;
	}
}

