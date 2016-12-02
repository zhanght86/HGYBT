package com.sinosoft.midplat.newccb.test;

import java.io.BufferedOutputStream;
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
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

/**
 * �������в������û�����
 * @author yuantongxin
 */
public class NewCCBTestUI_new {
	//��ʶ��ǰ�����־��������¼��ǰ����ܷ������쳣
	private Logger cLogger = Logger.getLogger(getClass());
	//����֮�以����Э��
	private String cIP = null;
	//�˿�
	private int cPort = 0;
	/**
	 * javaӦ�ó������ڷ���
	 * @param args 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//����ʼ...
		System.out.println("����ʼ...");
		//��������֮�以����Э�鲢��ֵ
		
		String mIP = "127.0.0.1";
		//����˿ںŲ���ֵ
		int mPort = 39871;
		//������
		String funcflag = "P53819113";
//		funcflag="P53819152";
//		funcflag="P53819188";
//		funcflag="P53819151";
//		funcflag="P53819184";
//		funcflag="P53819182";
//		funcflag="P53819171";
//		funcflag="P538191A2";
//		funcflag="P53818152";
//		funcflag="P53818154";//X
//		funcflag="P53819156";//X
//		funcflag="P53819161";
		//�����ļ�·��
		String mInFilePath = "D:/task/20161130/newccb/P53819113_ʵʱͶ��(����).xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53819152_�µ�ȷ��.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53819188_��ӡͶ����.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53819151_��ѯ���ɱ�����Ϣ.xml";
//		      mInFilePath = "D:/task/20161129/newccbP53819184_�ش��ϱ�.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53819182_��ӡ����.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53819171_��ѯ��������.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P538191A2_�ؿغ˶�.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53818152_�̵Ʋ���.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53818154_�Զ�����.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53819156_ȷ�����ڽɷ�.xml";
//		      mInFilePath = "D:/task/20161129/newccb/P53819161_�޸ı���������Ϣ.xml";
			//����ļ�·��
		    String mOutFilePath = "D:/task/20161130/newccb/"+funcflag+"_out.xml";
		    //�������в������û�������ͨ������֮�以����Э�顢�˿ں�ʵ����
			NewCCBTestUI_new mTestUI = new NewCCBTestUI_new(mIP,	mPort);
			//ͨ���ļ�������ʵ����������
			InputStream mIs = new FileInputStream(mInFilePath);
			//�����û����淢������(�����룬������)
			byte[] mOutBytes = mTestUI.sendRequest(funcflag,mIs);
			//����UTF-8�ַ������빹��һ���ĵ����󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)�� ����ʧ�ܣ�����null
			Document mOutXmlDoc = JdomUtil.build(mOutBytes , "UTF-8");
			System.out.println("ʵ�ʷ��ر���Ϊ��");//ʵ�ʷ��ر���Ϊ��
			JdomUtil.print(mOutXmlDoc);//��Document��ӡ������̨�� GBK���룬����3�ո�
			//�⽫����һ������ļ���д�뵽����ָ�����Ƶ��ļ���
			OutputStream mFos = new FileOutputStream(mOutFilePath);
			//Ϊ����ļ����ṩ�����幦�ܡ�
		    BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(mFos);
		    //�õ�Ư���ĸ�ʽ
		    Format format=Format.getPrettyFormat();//��������xml�ĵ��ĸ�ʽ  
		    //XMLOutputter[omitDeclaration = false, encoding = UTF-8, omitEncoding = false, indent = '  ', expandEmptyElements = false, lineSeparator = '\r\n', textMode = TRIM]
		    XMLOutputter xmlOutputter=new XMLOutputter(format);//������� ,��Ԫ�غ��У�ÿһ��Ԫ�������ĸ�
		    //bufferedOutputStream.write(mOutBytes);
		    //���������ĵ����󵽻��������
		    xmlOutputter.output(mOutXmlDoc, bufferedOutputStream);
		    //ˢ�»��������ֽ�д�뵽���������
		    bufferedOutputStream.flush();
		    //�رջ��������
		    bufferedOutputStream.close();
		    //�ر������
		    mFos.close();
		System.out.println("�ɹ�������");
	}

	public NewCCBTestUI_new(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	/**
	 * ��������
	 * @param funcflag ������
	 * @param pInputStream ������
	 * @return 
	 * @throws Exception
	 */
	public byte[] sendRequest(String funcflag,InputStream pInputStream) throws Exception {
		cLogger.info("Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);//Socket[addr=/127.0.0.1,port=39871,localport=53963]
		//[60, 63, 120, 109, 108, 32, 118, 101, 114, 115, 105, 111, 110, 61, 34, 49, 46, 48, 34, 32, 101, 110, 99, 111, 100, 105, 110, 103, 61, 34, 85, 84, 70, 45, 56, 34, 63, 62, 10, 60, 84, 88, 62, 10, 9, 60, 84, 88, 95, 72, 69, 65, 68, 69, 82, 62, 10, 9, 9, 60, 33, 45, 45, -27, -115, -113, -24, -82, -82, -26, -118, -91, -26, -106, -121, -27, -92, -76, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 72, 68, 82, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 72, 68, 82, 95, 76, 69, 78, 62, 60, 33, 45, 45, -27, -115, -113, -24, -82, -82, -26, -118, -91, -26, -106, -121, -27, -92, -76, -23, -107, -65, -27, -70, -90, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 80, 75, 71, 95, 86, 82, 83, 78, 62, 48, 49, 60, 47, 83, 89, 83, 95, 80, 75, 71, 95, 86, 82, 83, 78, 62, 60, 33, 45, 45, -26, -100, -84, -26, -118, -91, -26, -106, -121, -24, -89, -124, -24, -116, -125, -26, -119, -128, -28, -67, -65, -25, -108, -88, -25, -102, -124, -27, -115, -113, -24, -82, -82, -25, -119, -120, -26, -100, -84, -28, -72, -70, -30, -128, -100, 48, 49, -30, -128, -99, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 84, 84, 76, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 84, 84, 76, 95, 76, 69, 78, 62, 10, 9, 9, 60, 83, 89, 83, 95, 82, 69, 81, 95, 83, 69, 67, 95, 73, 68, 62, 49, 48, 50, 48, 48, 49, 60, 47, 83, 89, 83, 95, 82, 69, 81, 95, 83, 69, 67, 95, 73, 68, 62, 60, 33, 45, 45, -27, -113, -111, -24, -75, -73, -26, -106, -71, -27, -82, -119, -27, -123, -88, -24, -118, -126, -25, -126, -71, -25, -68, -106, -27, -113, -73, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 67, 95, 73, 68, 62, 49, 48, 56, 48, 49, 49, 60, 47, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 67, 95, 73, 68, 62, 60, 33, 45, 45, -27, -113, -111, -23, -128, -127, -26, -106, -71, -27, -82, -119, -27, -123, -88, -24, -118, -126, -25, -126, -71, -25, -68, -106, -27, -113, -73, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 84, 88, 95, 67, 79, 68, 69, 62, 80, 53, 51, 56, 49, 57, 49, 49, 51, 60, 47, 83, 89, 83, 95, 84, 88, 95, 67, 79, 68, 69, 62, 60, 33, 45, 45, -26, -100, -115, -27, -118, -95, -25, -96, -127, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 84, 88, 95, 86, 82, 83, 78, 62, 48, 49, 60, 47, 83, 89, 83, 95, 84, 88, 95, 86, 82, 83, 78, 62, 60, 33, 45, 45, -26, -100, -115, -27, -118, -95, -25, -119, -120, -26, -100, -84, -27, -113, -73, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 84, 88, 95, 84, 89, 80, 69, 62, 48, 50, 48, 48, 48, 48, 60, 47, 83, 89, 83, 95, 84, 88, 95, 84, 89, 80, 69, 62, 60, 33, 45, 45, -26, -100, -115, -27, -118, -95, -25, -89, -115, -25, -79, -69, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 82, 69, 83, 69, 82, 86, 69, 68, 62, 60, 47, 83, 89, 83, 95, 82, 69, 83, 69, 82, 86, 69, 68, 62, 10, 9, 9, 60, 83, 89, 83, 95, 69, 86, 84, 95, 84, 82, 65, 67, 69, 95, 73, 68, 62, 49, 48, 50, 48, 48, 49, 48, 55, 48, 49, 48, 48, 48, 48, 49, 60, 47, 83, 89, 83, 95, 69, 86, 84, 95, 84, 82, 65, 67, 69, 95, 73, 68, 62, 60, 33, 45, 45, -27, -123, -88, -27, -79, -128, -28, -70, -117, -28, -69, -74, -24, -73, -97, -24, -72, -86, -27, -113, -73, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 82, 73, 65, 76, 95, 78, 79, 62, 52, 48, 48, 48, 48, 48, 48, 48, 48, 48, 60, 47, 83, 89, 83, 95, 83, 78, 68, 95, 83, 69, 82, 73, 65, 76, 95, 78, 79, 62, 60, 33, 45, 45, -27, -83, -112, -28, -70, -92, -26, -104, -109, -27, -70, -113, -27, -113, -73, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 80, 75, 71, 95, 84, 89, 80, 69, 62, 49, 60, 47, 83, 89, 83, 95, 80, 75, 71, 95, 84, 89, 80, 69, 62, 10, 9, 9, 60, 83, 89, 83, 95, 77, 83, 71, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 77, 83, 71, 95, 76, 69, 78, 62, 10, 9, 9, 60, 33, 45, 45, -27, -70, -108, -25, -108, -88, -26, -118, -91, -26, -106, -121, -26, -104, -81, -27, -112, -90, -27, -118, -96, -27, -81, -122, 44, 48, -17, -68, -102, -27, -70, -108, -25, -108, -88, -26, -118, -91, -26, -106, -121, -26, -100, -86, -27, -118, -96, -27, -81, -122, 44, 49, -17, -68, -102, -25, -108, -88, -26, -118, -91, -26, -106, -121, -28, -72, -70, -27, -123, -88, -26, -106, -121, -27, -113, -118, -27, -123, -77, -23, -108, -82, -27, -83, -105, -26, -82, -75, -27, -113, -116, -23, -121, -115, -27, -118, -96, -27, -81, -122, 44, 50, 58, -27, -70, -108, -25, -108, -88, -26, -118, -91, -26, -106, -121, -28, -69, -123, -26, -104, -81, -27, -123, -77, -23, -108, -82, -27, -83, -105, -26, -82, -75, -27, -118, -96, -27, -81, -122, 44, 51, -17, -68, -102, -27, -70, -108, -25, -108, -88, -26, -118, -91, -26, -106, -121, -28, -69, -123, -28, -72, -70, -27, -123, -88, -26, -106, -121, -27, -118, -96, -27, -81, -122, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 73, 83, 95, 69, 78, 67, 82, 89, 80, 84, 69, 68, 62, 51, 60, 47, 83, 89, 83, 95, 73, 83, 95, 69, 78, 67, 82, 89, 80, 84, 69, 68, 62, 10, 9, 9, 60, 83, 89, 83, 95, 69, 78, 67, 82, 89, 80, 84, 95, 84, 89, 80, 69, 62, 51, 60, 47, 83, 89, 83, 95, 69, 78, 67, 82, 89, 80, 84, 95, 84, 89, 80, 69, 62, 60, 33, 45, 45, -27, -70, -108, -25, -108, -88, -26, -118, -91, -26, -106, -121, -27, -118, -96, -27, -81, -122, -26, -106, -71, -27, -68, -113, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 67, 79, 77, 80, 82, 69, 83, 83, 95, 84, 89, 80, 69, 62, 48, 60, 47, 83, 89, 83, 95, 67, 79, 77, 80, 82, 69, 83, 83, 95, 84, 89, 80, 69, 62, 60, 33, 45, 45, -27, -70, -108, -25, -108, -88, -26, -118, -91, -26, -106, -121, -27, -114, -117, -25, -68, -87, -26, -106, -71, -27, -68, -113, 44, 48, 58, -26, -100, -86, -27, -114, -117, -25, -68, -87, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 69, 77, 66, 95, 77, 83, 71, 95, 76, 69, 78, 62, 48, 60, 47, 83, 89, 83, 95, 69, 77, 66, 95, 77, 83, 71, 95, 76, 69, 78, 62, 10, 9, 9, 60, 83, 89, 83, 95, 82, 69, 81, 95, 84, 73, 77, 69, 62, 50, 48, 49, 54, 49, 49, 50, 57, 49, 52, 53, 50, 48, 53, 57, 57, 51, 60, 47, 83, 89, 83, 95, 82, 69, 81, 95, 84, 73, 77, 69, 62, 60, 33, 45, 45, -27, -113, -111, -24, -75, -73, -26, -106, -71, -28, -70, -92, -26, -104, -109, -26, -105, -74, -23, -105, -76, 40, 121, 121, 121, 121, 77, 77, 100, 100, 72, 72, 109, 109, 115, 115, 78, 78, 78, 41, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 84, 73, 77, 69, 95, 76, 69, 70, 84, 62, 48, 48, 48, 49, 49, 56, 57, 57, 54, 60, 47, 83, 89, 83, 95, 84, 73, 77, 69, 95, 76, 69, 70, 84, 62, 60, 33, 45, 45, -28, -70, -92, -26, -104, -109, -27, -119, -87, -28, -67, -103, -27, -92, -124, -25, -112, -122, -26, -105, -74, -23, -105, -76, 45, 45, 62, 10, 9, 9, 60, 83, 89, 83, 95, 80, 75, 71, 95, 83, 84, 83, 95, 84, 89, 80, 69, 62, 48, 48, 60, 47, 83, 89, 83, 95, 80, 75, 71, 95, 83, 84, 83, 95, 84, 89, 80, 69, 62, 60, 33, 45, 45, -26, -118, -91, -26, -106, -121, -25, -118, -74, -26, -128, -127, -25, -79, -69, -27, -98, -117, 44, 48, 48, 58, -24, -81, -73, -26, -79, -126, -26, -118, -91, -26, -106, -121, 45, 45, 62, 10, 9, 60, 47, 84, 88, 95, 72, 69, 65, 68, 69, 82, 62, 10, 9, 60, 84, 88, 95, 66, 79, 68, 89, 62, 60, 33, 45, 45, -27, -70, -108, -25, -108, -88, -26, -118, -91, -26, -106, -121, 45, 45, 62, 10, 9, 9, 60, 67, 79, 77, 77, 79, 78, 62, 60, 33, 45, 45, -23, -128, -102, -25, -108, -88, -27, -97, -97, 45, 45, 62, 10, 9, 9, 9, 60, 70, 73, 76, 69, 95, 76, 73, 83, 84, 95, 80, 65, 67, 75, 62, 10, 9, 9, 9, 9, 60, 70, 73, 76, 69, 95, 78, 85, 77, 62, 48, 60, 47, 70, 73, 76, 69, 95, 78, 85, 77, 62, 10, 9, 9, 9, 9, 60, 70, 73, 76, 69, 95, 77, 79, 68, 69, 62, 48, 60, 47, 70, 73, 76, 69, 95, 77, 79, 68, 69, 62, 10, 9, 9, 9, 9, 60, 70, 73, 76, 69, 95, 78, 79, 68, 69, 32, 47, 62, 10, 9, 9, 9, 9, 60, 70, 73, 76, 69, 95, 78, 65, 77, 69, 95, 80, 65, 67, 75, 32, 47, 62, 10, 9, 9, 9, 9, 60, 70, 73, 76, 69, 95, 80, 65, 84, 72, 95, 80, 65, 67, 75, 32, 47, 62, 10, 9, 9, 9, 60, 47, 70, 73, 76, 69, 95, 76, 73, 83, 84, 95, 80, 65, 67, 75, 62, 10, 9, 9, 60, 47, 67, 79, 77, 77, 79, 78, 62, 10, 9, 9, 60, 69, 78, 84, 73, 84, 89, 62, 60, 33, 45, 45, -27, -82, -98, -28, -67, -109, -27, -97, -97, 45, 45, 62, 10, 9, 9, 9, 60, 67, 79, 77, 95, 69, 78, 84, 73, 84, 89, 62, 60, 33, 45, 45, -27, -123, -84, -27, -123, -79, -27, -97, -97, 45, 45, 62, 10, 9, 9, 9, 9, 60, 73, 110, 115, 116, 95, 69, 110, 103, 95, 83, 104, 114, 116, 78, 109, 62, 67, 67, 66, 60, 47, 73, 110, 115, 116, 95, 69, 110, 103, 95, 83, 104, 114, 116, 78, 109, 62, 10, 9, 9, 9, 9, 60, 73, 110, 115, 95, 67, 111, 95, 73, 68, 62, 48, 49, 48, 48, 49, 57, 60, 47, 73, 110, 115, 95, 67, 111, 95, 73, 68, 62, 60, 33, 45, 45, -28, -65, -99, -23, -103, -87, -27, -123, -84, -27, -113, -72, -25, -68, -106, -27, -113, -73, 45, 45, 62, 10, 9, 9, 9, 9, 60, 83, 118, 80, 116, 95, 74, 114, 110, 108, 95, 78, 111, 62, 100, 101, 109, 97, 120, 105, 121, 97, 50, 48, 48, 49, 54, 52, 60, 47, 83, 118, 80, 116, 95, 74, 114, 110, 108, 95, 78, 111, 62, 60, 33, 45, 45, -26, -100, -115, -27, -118, -95, -26, -106, -71, -26, -75, -127, -26, -80, -76, -27, -113, -73, 45, 45, 62, 10, 9, 9, 9, 9, 60, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 73, 68, 62, 48, 48, 50, 57, 49, 49, 48, 51, 55, 56, 51, 48, 48, 48, 48, 60, 47, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 73, 68, 62, 60, 33, 45, 45, -28, -70, -92, -26, -104, -109, -27, -113, -111, -24, -75, -73, -26, -72, -96, -23, -127, -109, -25, -68, -106, -27, -113, -73, 45, 45, 62, 10, 9, 9, 9, 9, 60, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 67, 71, 89, 95, 67, 79, 68, 69, 62, 57, 49, 49, 55, 49, 49, 60, 47, 84, 88, 78, 95, 73, 84, 84, 95, 67, 72, 78, 76, 95, 67, 71, 89, 95, 67, 79, 68, 69, 62, 60, 33, 45, 45, -28, -70, -92, -26, -104, -109, -27, -113, -111, -24, -75, -73, -26, -72, -96, -23, -127, -109, -25, -79, -69, -27, -120, -85, 45, 45, 62, 10, 9, 9, 9, 9, 60, 67, 67, 66, 73, 110, 115, 95, 73, 68, 62, 49, 51, 48, 57, 57, 60, 47, 67, 67, 66, 73, 110, 115, 95, 73, 68, 62, 60, 33, 45, 45, -27, -69, -70, -24, -95, -116, -26, -100, -70, -26, -98, -124, -25, -68, -106, -27, -113, -73, 38, -25, -67, -111, -25, -126, -71, -28, -69, -93, -25, -96, -127, 45, 45, 62, 10, 9, 9, 9, 9, 60, 67, 67, 66, 95, 69, 109, 112, 73, 68, 62, 53, 50, 48, 49, 51, 48, 48, 48, 48, 52, 60, 47, 67, 67, 66, 95, 69, 109, 112, 73, 68, 62, 60, 33, 45, 45, -27, -69, -70, -24, -95, -116, -27, -111, -104, -27, ...
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		int length=mInClearBodyBytes.length;//11026
		String lengthStr=String.valueOf(length);//11026
		if(lengthStr.length()<6){//5<6
			int count=6-lengthStr.length();//6-5=1
			for (int i = 0; i <count; i++) {
				lengthStr="0"+lengthStr;//0+11026=011026
			}
		}
		System.out.println("------------"+lengthStr);//------------011026
		//�õ��������ָ�����ֽ�����д��b.length���ֽڵ����ļ��������
		mSocket.getOutputStream().write(lengthStr.getBytes());
		mSocket.getOutputStream().write(mInClearBodyBytes);
		mSocket.getOutputStream().flush();
		mSocket.shutdownOutput();
		InputStream inputStream=mSocket.getInputStream();
		return new IOTrans().toBytes(inputStream);
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
		String resHeadPath = "F:/innostd_head.xml"; 
		byte[] mInResHeadBytes = IOTrans.toBytes(new FileInputStream(resHeadPath));
		System.out.println("��ȫ����ͷ��"+ new String(mInResHeadBytes) +"over!");
		
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
		mInResHeadBytes = mHeadStr.getBytes();
		
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

		byte[] mInClearBodyBytes = JdomUtil.toBytes(document,"GBK");
		
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
		for (int tReadSize = 0; tReadSize < mOutHeadBytes.length;) {
			int tRead = mSocketIs.read(mOutHeadBytes, tReadSize, mOutHeadBytes.length-tReadSize);
			if (-1 == tRead) {
				throw new EOFException("��ȡ����ͷ����ʵ�ʶ��룺" + new String(mOutHeadBytes));
			}else if(-1 != tRead){
				break;
			}
			tReadSize += tRead;
		}
		String mBodyDataStr = new String(mOutHeadBytes).trim();
		cLogger.info("���ر��ĳ��ȣ�" + mBodyDataStr.length());

		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��"+(mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");		
		
//		return mCCBCipher.decode(mOutCipherBodyBytes);  //����
		return mBodyDataStr.getBytes("UTF-8");
	}
}
