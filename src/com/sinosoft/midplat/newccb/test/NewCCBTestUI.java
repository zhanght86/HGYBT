package com.sinosoft.midplat.newccb.test;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class NewCCBTestUI {
	private  Logger cLogger = Logger.getLogger(getClass());

	private String cIP = null;
	private int cPort = 0;

	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ...");
//    	String mIP = "10.2.0.31";//���Ի���
	    String mIP = "127.0.0.1";
//		String mIP = "10.1.3.62";//��������IP
		int mPort =39871;
		
		
		String funcflag = "";
		String mInFilePath = "C:\\Users\\PengYF\\Desktop\\";
		
		funcflag="P53819182";
		mInFilePath +="P53819182_��ӡ����.xml";
		
		       
		       
		       
	    String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
	    
		NewCCBTestUI mTestUI = new NewCCBTestUI(mIP,mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
	//	byte[] mOutBytes = mTestUI.sendRequest(funcflag,mIs);
		Document document = JdomUtil.build(mIs,"UTF-8");
		System.out.println("-----"+JdomUtil.toStringFmt(document));
		byte[] mOutBytes = mTestUI.sendRequest(funcflag,document);
		Document mOutXmlDoc = JdomUtil.build(mOutBytes , "UTF-8");
		System.out.println("ʵ�ʷ��ر���Ϊ��");
		JdomUtil.print(mOutXmlDoc);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
		System.out.println("�ɹ�������");
	}

	public NewCCBTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}

	public byte[] sendRequest(String funcflag,InputStream pInputStream) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		mSocket.getOutputStream().write(mInClearBodyBytes);
		mSocket.getOutputStream().flush();
		mSocket.shutdownOutput();
		return mInClearBodyBytes;
//		Document document = JdomUtil.build(pInputStream);
//		return sendRequest(funcflag,document);
//		Socket mSocket = new Socket(cIP, cPort);
//
//		long mOldTimeMillis = System.currentTimeMillis();
//		long mCurTimeMillis = mOldTimeMillis;
//
//		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
//		Document mDoc = JdomUtil.build(mInClearBodyBytes ,"UTF-8");
//		Element eSYS_TX_CODE = mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE");
//		String mSYS_TX_CODE = eSYS_TX_CODE.getText();
//		
//		mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE").setText("P5"+mSYS_TX_CODE.substring(2, mSYS_TX_CODE.length()));
////		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText("CCB000000000111111");
////		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("CCB_EmpID").setText("��������");
//		mInClearBodyBytes = JdomUtil.toBytes(mDoc ,"UTF-8");
//		//		mInClearBodyBytes = JdomUtil.toBytes(JdomUtil.build(mInClearBodyBytes , "UTF-8"),"UTF-8");
//		
//		//���� 
//		
//		//��ӱ���ͷ
////		String resHeadPath = "F:/innostd_head.xml"; 
////		byte[] mInResHeadBytes = IOTrans.toBytes(new FileInputStream(resHeadPath));
////		System.out.println("��ȫ����ͷ��"+ new String(mInResHeadBytes) +"over!");
//		
//		String mHeadStr = "POST / HTTP/1.1"+"\r\n"
//				+"Host: 128.192.154.4:13010"+"\r\n"
//				+"Server: BIP 1.0"+"\r\n"
//				+"Date: Wed Mar 26 11:12:49 2014 GMT"+"\r\n"
//				+"Content-Type: application/octet-stream; charset=UTF-8"+"\r\n"
//				+"Content-Length:"+mInClearBodyBytes.length+"\r\n"
//				+"Connection: keep-alive"+"\r\n\r\n" +
//						"SEC_ERROR_CODE:00000000000" +"\r\n"+
//						"SEC_IS_MAC:1"+"\r\n" +
//						"SEC_IS_CONTEXT:1"+"\r\n" +
//						"SEC_IS_ENC:1"+"\r\n" +
//						"SEC_MAC:345789gh98rh3r9f8u3r"+"\r\n" +
//						"SEC_CONTEXT:23rjf3o4ijofijhiourhfwikjfiodsfjuirhfkj"+"\r\n" +
//						"SEC_ID1:408002"+"\r\n" +
//						"SEC_ID2:408003"+"\r\n" +
//						"SEC_TRACE_ID:1143445435"+"\r\n" +
//						"SEC_TX_CODE:A2343423"+"\r\n" +
//						"SEC_TX_TYPE:00000"+"\r\n" +
//						"SEC_RESP_CODE:"+"\r\n" +
//						"SEC_LEN:001498"+"\r\n\r\n";
//		byte[] mInResHeadBytes = mHeadStr.getBytes();
//		
//		byte[] mInCipherBodyBytes = mInClearBodyBytes;
////		byte[] mInCipherBodyBytes = SecAPI.pkgEncrypt( "613001" ,"613001",mInClearBodyBytes);
//		cLogger.info( new String(mInCipherBodyBytes));
//
//		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + mInResHeadBytes.length];
//
//		System.arraycopy(mInResHeadBytes, 0, mInTotalBytes, 0, mInResHeadBytes.length);
////		System.arraycopy("\r\n".getBytes(), 0, mInTotalBytes, 0, "\r\n".getBytes().length);
//		
//		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, mInResHeadBytes.length, mInClearBodyBytes.length);
//		
//		
//		String mInBodyLengthStr = String.valueOf(mInResHeadBytes.length);
//		cLogger.info("�����ĳ��ȣ�" + mInBodyLengthStr);
//		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
////		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
//
////		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 0, mInCipherBodyBytes.length);
//		System.out.println("���屨�ģ�"+ new String(mInTotalBytes));
//		cLogger.info("���������ĳ��ȣ�" + new String(mInTotalBytes).length());
//		cLogger.info("���������ģ�");
//		mSocket.getOutputStream().write(mInTotalBytes);
//		mSocket.getOutputStream().flush();
////		mSocket.shutdownOutput();
//		mCurTimeMillis = System.currentTimeMillis();
//		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
//
//		/**���´����ر���************************/
//		InputStream mSocketIs = mSocket.getInputStream();
//		cLogger.info("�յ����ر��ģ�");
//
//		//��ȡ���ر���
//		byte[] mOutHeadBytes = new byte[1024*50];
//		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
//			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
//			if (-1 == tRead) {
//				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
//			}else if(-1 != tRead){
//				break;
//			}
//			tReadSize += tRead;
//		}
////		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes).trim());
//		cLogger.info("���ر��ĳ��ȣ�" + mOutHeadBytes.length);
//
//		
//		String mDataStr = new String(mOutHeadBytes).trim();
//		int index_SEC_LEN = mDataStr.indexOf("SEC_LEN:");
//		int index_Body_SEP = mDataStr.indexOf("\r\n", index_SEC_LEN);
//		
//		String mHeadDataStr = mDataStr.substring(0,index_Body_SEP);
//		
//		System.out.println("��ȫ����ͷ��\r\n"+mHeadDataStr);
//		
//		
//		String mBodyDataStr = mDataStr.substring(index_Body_SEP,mDataStr.length()).trim();
//		System.out.println(mBodyDataStr);
//
//		mSocket.close();
//		mCurTimeMillis = System.currentTimeMillis();
//		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��"+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
//		
////		return mCCBCipher.decode(mOutCipherBodyBytes);  //����
//		return mBodyDataStr.getBytes();
	}
	
	public byte[] sendRequest(String pFuncFlag, Element document) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);
		
		Document mDoc = JdomUtil.build(mInClearBodyBytes ,"UTF-8");
		Element eSYS_TX_CODE = mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE");
		String mSYS_TX_CODE = eSYS_TX_CODE.getText();
		
		mDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_CODE").setText("P5"+mSYS_TX_CODE.substring(2, mSYS_TX_CODE.length()));
//		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText("CCB000000000111111");
//		mDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("CCB_EmpID").setText("��������");
		mInClearBodyBytes = JdomUtil.toBytes(mDoc ,"UTF-8");
		//		mInClearBodyBytes = JdomUtil.toBytes(JdomUtil.build(mInClearBodyBytes , "UTF-8"),"UTF-8");
		
		//���� 
		
		//��ӱ���ͷ
//		String resHeadPath = "F:/innostd_head.xml"; 
//		byte[] mInResHeadBytes = IOTrans.toBytes(new FileInputStream(resHeadPath));
//		System.out.println("��ȫ����ͷ��"+ new String(mInResHeadBytes) +"over!");
		
		String mHeadStr = "POST / HTTP/1.1"+"\r\n"
				+"Host: 128.192.154.4:13010"+"\r\n"
				+"Server: BIP 1.0"+"\r\n"
				+"Date: Wed Mar 26 11:12:49 2014 GMT"+"\r\n"
				+"Content-Type: application/octet-stream; charset=UTF-8"+"\r\n"
				+"Content-Length:"+mInClearBodyBytes.length+"\r\n"
				+"Connection: keep-alive"+"\r\n\r\n"+
				"SEC_ERROR_CODE:00000000000" +"\r\n"+
				"SEC_IS_MAC:1"+"\r\n" +
				"SEC_IS_CONTEXT:1"+"\r\n" +
				"SEC_IS_ENC:1"+"\r\n" +
				"SEC_MAC:345789gh98rh3r9f8u3r"+"\r\n" +
				"SEC_CONTEXT:23rjf3o4ijofijhiourhfwikjfiodsfjuirhfkj"+"\r\n" +
				"SEC_ID1:408002"+"\r\n" +
				"SEC_ID2:408003"+"\r\n" +
				"SEC_TRACE_ID:1143445435"+"\r\n" +
				"SEC_TX_CODE:A2343423"+"\r\n" +
				"SEC_TX_TYPE:00000"+"\r\n" +
				"SEC_RESP_CODE:"+"\r\n" +
				"SEC_LEN:001498"+"\r\n\r\n";;
				byte[] mInResHeadBytes = mHeadStr.getBytes();
		
		byte[] mInCipherBodyBytes = mInClearBodyBytes;
//		byte[] mInCipherBodyBytes = SecAPI.pkgEncrypt( "613001" ,"613001",mInClearBodyBytes);
		cLogger.info( new String(mInCipherBodyBytes));

		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + mInResHeadBytes.length];

		System.arraycopy(mInResHeadBytes, 0, mInTotalBytes, 0, mInResHeadBytes.length);
//		System.arraycopy("\r\n".getBytes(), 0, mInTotalBytes, 0, "\r\n".getBytes().length);
		
		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, mInResHeadBytes.length, mInClearBodyBytes.length);
		
		
		String mInBodyLengthStr = String.valueOf(mInResHeadBytes.length);
		cLogger.info("�����ĳ��ȣ�" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
//		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);

//		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 0, mInCipherBodyBytes.length);
		System.out.println("���屨�ģ�"+ new String(mInTotalBytes));
		cLogger.info("���������ĳ��ȣ�" + new String(mInTotalBytes).length());
		cLogger.info("���������ģ�");
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.getOutputStream().flush();
//		mSocket.shutdownOutput();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");

		/**���´����ر���************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");

		//��ȡ���ر���
		byte[] mOutHeadBytes = new byte[1024*50];
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}else if(-1 != tRead){
				break;
			}
			tReadSize += tRead;
		}
//		int mOutBodyLengthInt = Integer.parseInt(new String(mOutHeadBytes).trim());
		cLogger.info("���ر��ĳ��ȣ�" + mOutHeadBytes.length);

		
		String mDataStr = new String(mOutHeadBytes).trim();
		int index_SEC_LEN = mDataStr.indexOf("SEC_LEN:");
		int index_Body_SEP = mDataStr.indexOf("\r\n", index_SEC_LEN);
		
		String mHeadDataStr = mDataStr.substring(0,index_Body_SEP);
		
		System.out.println("��ȫ����ͷ��\r\n"+mHeadDataStr);
		
		
		String mBodyDataStr = mDataStr.substring(index_Body_SEP,mDataStr.length()).trim();
		System.out.println(mBodyDataStr);

		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��"+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
		
//		return mCCBCipher.decode(mOutCipherBodyBytes);  //����
		return mBodyDataStr.getBytes();
	}
	
	public byte[] sendRequest(String pFuncFlag, Document document) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);

		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;

		byte[] mInClearBodyBytes = JdomUtil.toBytes(document,"UTF-8");
		
		byte[] mInTotalBytes = new byte[mInClearBodyBytes.length + 6];

		// �����峤��
		String mInBodyLengthStr = String.valueOf(mInClearBodyBytes.length);
		cLogger.info("�����ĳ��ȣ�" + mInBodyLengthStr);
		byte[] mInLengthBytes = mInBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0,
				mInLengthBytes.length);

		System.arraycopy(mInClearBodyBytes, 0, mInTotalBytes, 6,
				mInClearBodyBytes.length);
		
		
		mSocket.getOutputStream().write(mInTotalBytes);
		mSocket.getOutputStream().flush();
		
		/**���´����ر���************************/
		InputStream mSocketIs = mSocket.getInputStream();
		cLogger.info("�յ����ر��ģ�");

		//��ȡ���ر���
		byte[] mOutHeadBytes = new byte[1024*50];
		int tRead=0;
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
		    tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}else if(-1 != tRead){
				break;
			}
			tReadSize += tRead;
		}
		String mBodyDataStr = new String(mOutHeadBytes,0,tRead,"UTF-8").trim();
		cLogger.info("���ر��ĳ��ȣ�" + mBodyDataStr.length());

		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��"+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
		
//		return mCCBCipher.decode(mOutCipherBodyBytes);  //����
		return mBodyDataStr.getBytes("UTF-8");
	}
}
