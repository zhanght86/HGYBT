/**
 * �������в��Գ���������
 * 
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
import com.sinosoft.midplat.newabc.util.*;;
public class NewABCTest1010jiami {
	private Logger cLogger = Logger.getLogger(getClass());
	
	private String cIP = null; 
	private int cPort = 0;
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ...");
	
		//����
		String mIP = "127.0.0.1";
		int mPort = 35006;
		
		String mFuncFlag = null;	//1002-�±����㣬1004-�±����ѣ�1010-���ճ���, 04-�Զ�����
		
		mFuncFlag = "1005";
		String mInFilePathName = "";
		String mOutFilePathName = "";
//			 mInFilePathName = "D:/msg/05/2014/201408/20140828/1359_2_1014_in.xml";
//			 mInFilePathName = "E:/995295_27_1005_in.xml";
//			 mOutFilePathName = "E:/555555.xml";
			 mInFilePathName = "F:/995325_17_1005_in.xml";
			 mOutFilePathName = "F:/22222.xml";
//			 mInFilePathName = "H:/��·/�����к�����/ũ�нӿ�/���Ա���/��ũ���µ���������ѯ����in.xml";
//			 mOutFilePathName = "H:/��·/�����к�����/ũ�нӿ�/���Ա���/��ũ���µ���������ѯ����out.xml";
		NewABCTest1010jiami mTestUI = new NewABCTest1010jiami(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePathName);
		byte[] mIsB=IOTrans.toBytes(mIs);
		Document mOutXmlDoc = mTestUI.sendRequest(mFuncFlag, mIsB);
		OutputStream mOs = new FileOutputStream(mOutFilePathName);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		System.out.println("�ɹ�������");
	}
	
	public NewABCTest1010jiami(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	public NewABCTest1010jiami() {
	}
	
	public Document sendRequest(String pFuncFlag, byte[] doc) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		
		
//		byte[] mHeadBytes=new byte[73];
//		
//		//��������
//		byte[] C1Type="X".getBytes();
//		System.arraycopy(C1Type, 0, mHeadBytes, 0, C1Type.length);
//		//�汾
//		byte[] C3="1.0".getBytes();
//		System.arraycopy(C3, 0, mHeadBytes, 1, C3.length);
//		//���ݰ�����
//		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);  
//		String mBody=AES.Encrypt(AES.rpadEncrypt(new String(mInClearBodyBytes),' '));
//		byte[] mInLengthBytes = mBody.getBytes();
//		cLogger.info("���ĳ���="+mInLengthBytes.length);
//		String mInCipherBodyLengthStr = String.valueOf(mInLengthBytes.length);
//		byte[] mInLengthBytess = mInCipherBodyLengthStr.getBytes();
//		byte[] dateLenth=new byte[8];
//		System.arraycopy(mInLengthBytess, 0, dateLenth, 0, mInLengthBytess.length);                          
//		System.arraycopy(dateLenth, 0, mHeadBytes, 4, dateLenth.length);
//		
//		cLogger.info("���ͱ���ͷ23423423��==="+new String(dateLenth,"UTF-8")+"=====");
//		//��˾����
//		byte[] C1TypeT="4518    ".getBytes();
//		System.arraycopy(C1TypeT, 0, mHeadBytes, 12, C1TypeT.length);
//		//���ܱ�ʾ
//		byte[] C1TypeK="1".getBytes();
//		System.arraycopy(C1TypeK, 0, mHeadBytes, 20, C1TypeK.length);
//		//�����㷨
//		byte[] C1TypeM=" ".getBytes();
//		System.arraycopy(C1TypeM, 0, mHeadBytes, 21, C1TypeM.length);
//		//����ѹ����־
//		byte[] C1TypeH=" ".getBytes();
//		System.arraycopy(C1TypeH, 0, mHeadBytes, 22, C1TypeH.length);
//		//����ѹ���㷨
//		byte[] C1TypeY=" ".getBytes();
//		System.arraycopy(C1TypeY, 0, mHeadBytes, 23, C1TypeY.length);
//		//ժҪ�㷨
//		byte[] C1TypeZ=" ".getBytes();
//		System.arraycopy(C1TypeZ, 0, mHeadBytes, 23, C1TypeZ.length);
//		//ժҪ
//		StringBuffer sp=new StringBuffer();;
//		for(int i=0;i<=39;i++){
//			sp.append(" ");
//		}
//		
//		byte[] C2TypeZ=sp.toString().getBytes();
//		System.out.println(C2TypeZ.length);
//		System.arraycopy(C2TypeZ, 0, mHeadBytes, 24, C2TypeZ.length);
//		
//		//Ԥ���ֶ�00000000
//		byte[] temp="00000000".getBytes();
//		System.arraycopy(temp, 0, mHeadBytes, 65, temp.length);
//		for(int i=0;i<mHeadBytes.length;i++){
//			System.out.print("="+mHeadBytes[i]+"=");
//		}
		String cInsuID="3103";   //�к����ٱ��չ�˾����
		String endxmlStr=AES.Encrypt(AES.rpadEncrypt(new String(doc),' '));
		System.out.println("������û��:"+endxmlStr);

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
		StringBuffer abc_xml = new StringBuffer();
		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		abc_xml.append("\n");
		abc_xml.append(axx);
		System.out.println("���صı���:"+abc_xml.toString());
		byte[] all_xml = abc_xml.toString().getBytes("UTF-8");//#
		Document mXmlDoc_bank = JdomUtil.build(all_xml,"UTF-8"); //#
		cLogger.info("UTF-8 ũ�еı���: ");
		JdomUtil.print(mXmlDoc_bank);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		return mXmlDoc_bank;
	}
}

