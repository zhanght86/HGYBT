/**
 * �������в��Գ���������
 * 
 * ChenGB(�¹�) 2008.12.10
 */
 
 
package test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;


import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

import test.security.DESCipher;
   
public class ICBCTestUI {
	public  ICBCTestUI(){};
	private Logger cLogger = Logger.getLogger(getClass());
	 private InputStream mInputStream = null; 
 
	    private Document resultDocument = null;
	    private String functionFlag = "";
	    private String tOutMsgFilePath = null;
	private String cIP = null; 
	private int cPort = 0; 
	  
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ...");
		
		//���� 
		String mIP = "127.0.0.1"; 
		//UAT
//		String mIP = "10.0.4.14"; 
		int mPort = 35000;
  
   
		//��������--����Ŷ
		 
		//DAT���� 
		
		String mFuncFlag = null;	
		 
		//1010-��ѯ��1011-�ش�1013-�µ��б���1015-����,0001-��Կ��,213-��ԥ���˱���ѯ
		 //105-��ԥ���˱���ѯ106��ԥ���˱�107��ԥ���˱�����108���ڽɷѲ�ѯ
		//109���ڽɷ�201���ڽɷѳ���202�˱���ѯ203�˱�204�˱�����
		//205���ڸ�����ѯ206���ڸ���207���ڸ�������
		
		//�ش�
//		mFuncFlag = "1011";  
//		String mInFilePath = "D:/test/ZHH/����/162771_217_1011_in.xml";
//		String mOutFilePath = "D:/test/ZHH/����/162771_217_1011_in.xmlOut.xml";
		
		//�µ�
		mFuncFlag = "1013";  
//		String mInFilePath = "H:/��·/�����к�����/���ķ��ر���/����Ͷ���볷��/�������гб�_1206_2117_1013_in.xml";
//		String mOutFilePath = "H:/��·/�����к�����/���ķ��ر���/����Ͷ���볷��/���гб�����.xml";
		String mInFilePath = "H:/1047162_63_1013_in.xml";
//		String mInFilePath = "H:/1047162_63_1013_in.xml";
//		String mOutFilePath = "H:/����.xml";
		String mOutFilePath = "H:/�б�.xml";
	
		ICBCTestUI mTestUI = new ICBCTestUI(mIP, mPort);
		InputStream mIs = new FileInputStream(mInFilePath);
		System.out.println(mFuncFlag);
		byte[] mOutBytes = mTestUI.sendRequest(mFuncFlag, mIs);
		
		Document mOutXmlDoc = JdomUtil.build(mOutBytes);
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mFos);
		mFos.flush();
		mFos.close();
//		mFos.write(mOutBytes);
		
		JdomUtil.print(mOutXmlDoc);
		
		System.out.println("�ɹ�������");
	}
	
	public ICBCTestUI(String pIP, int pPort) {
		cIP = pIP;
		cPort = pPort;
	}
	
	public byte[] sendRequestUI(String pFuncFlag, Document document) throws Exception {
		cLogger.info("sendRequestUI��Socket����" + cIP + ":" + cPort);
		Socket mSocket = new Socket(cIP, cPort);
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = JdomUtil.toBytes(document);
		
		//����
		DESCipher mDESCipher = new DESCipher(cPort);
		byte[] mInCipherBodyBytes = mDESCipher.encode(mInClearBodyBytes);
		cLogger.info("�����ļ�����ɣ�");
		
		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + 16];
		
		//�����峤��
		String mInCipherBodyLengthStr = String.valueOf(mInCipherBodyBytes.length);
		cLogger.info("�����ĳ��ȣ�" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		
		//���״���
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);
		
		//��˾����
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10, mInsuIDBytes.length);
		
		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 16, mInCipherBodyBytes.length);
		
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
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		//��Կ�����������⴦��
		if (pFuncFlag.equals("0001")) {
			try {
				keyChange(mInClearBodyBytes, mOutCipherBodyBytes);	//���±�����Կ�ļ�
				mDESCipher = new DESCipher(cPort);
			} catch (Exception ex) {
				cLogger.debug("��Կ����ʧ�ܣ�", ex);
			}
		}		
		
		return mDESCipher.decode(mOutCipherBodyBytes);
	}
	 
	public byte[] sendRequest(String pFuncFlag, InputStream pInputStream) throws Exception {
		cLogger.info("sendRequest��Socket����" + cIP + ":" + cPort);

		Socket mSocket = new Socket(cIP, cPort);
		
		long mOldTimeMillis = System.currentTimeMillis();
		long mCurTimeMillis = mOldTimeMillis;
		
		byte[] mInClearBodyBytes = IOTrans.toBytes(pInputStream);
		
		//����
		DESCipher mDESCipher = new DESCipher(cPort);
		byte[] mInCipherBodyBytes = mDESCipher.encode(mInClearBodyBytes);
		cLogger.info("�����ļ�����ɣ�");
		
		byte[] mInTotalBytes = new byte[mInCipherBodyBytes.length + 16];
		
		//�����峤��
		String mInCipherBodyLengthStr = String.valueOf(mInCipherBodyBytes.length);
		cLogger.info("�����ĳ��ȣ�" + mInCipherBodyLengthStr);
		byte[] mInLengthBytes = mInCipherBodyLengthStr.getBytes();
		System.arraycopy(mInLengthBytes, 0, mInTotalBytes, 0, mInLengthBytes.length);
		
		//���״���
		byte[] mFuncFlagBytes = pFuncFlag.getBytes();
		System.arraycopy(mFuncFlagBytes, 0, mInTotalBytes, 6, mFuncFlagBytes.length);
		
		//��˾����
		byte[] mInsuIDBytes = "001".getBytes();
		System.arraycopy(mInsuIDBytes, 0, mInTotalBytes, 10, mInsuIDBytes.length);
		
		System.arraycopy(mInCipherBodyBytes, 0, mInTotalBytes, 16, mInCipherBodyBytes.length);
		
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
		IOTrans.readFull(mOutCipherBodyBytes, mSocketIs);
		mSocket.close();
		mCurTimeMillis = System.currentTimeMillis();
		cLogger.info("�ͻ��˽��շ��ر�����ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/1000.0 + "s");
		
		//��Կ�����������⴦��
		if (pFuncFlag.equals("0001")) {
			try {
				keyChange(mInClearBodyBytes, mOutCipherBodyBytes);	//���±�����Կ�ļ�
				mDESCipher = new DESCipher(cPort);
			} catch (Exception ex) {
				cLogger.debug("��Կ����ʧ�ܣ�", ex);
			}
		}		
		
		return mDESCipher.decode(mOutCipherBodyBytes);
	}
	
	/**
	 * �ж���Կ���������Ƿ�ɹ������ɹ������������Կ�ļ���
	 */
	private void keyChange(byte[] pInClearBytes, byte[] pOutCipherBytes) throws Exception {
		cLogger.debug("Into ICBCControl.keyChange()...");
		
		//����Կ�������׵��������л�ȡ��Կ
		ByteArrayInputStream mBais = new ByteArrayInputStream(pInClearBytes);
		InputStreamReader mIsr = new InputStreamReader(mBais, "GBK");
		SAXBuilder mSAXBuilder = new SAXBuilder();
		mSAXBuilder.setIgnoringBoundaryWhitespace(true);
		Document mInXmlDoc = mSAXBuilder.build(mIsr);
		String mDesKey = mInXmlDoc.getRootElement().getChildTextTrim("DesKey");
		if(16 != mDesKey.length()) {
			throw new Exception("��Կ���Ȳ���16��" + mDesKey);
		}
		cLogger.debug("��Կ��" + mDesKey);
		
		//���ܷ��ر���
		byte[] mKeyBytes = new byte[8];
		for (int i = 0; i < mKeyBytes.length; i++) {
			mKeyBytes[i] = (byte) Integer.parseInt(mDesKey.substring(i*2, i*2+2), 16);
		}		
		SecretKeySpec mKey = new SecretKeySpec(mKeyBytes, "DES");
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, mKey);
		byte[] mOutClearBytes = mCipher.doFinal(pOutCipherBytes);
		
		//������Կ
		mBais = new ByteArrayInputStream(mOutClearBytes);
		mIsr = new InputStreamReader(mBais, "GBK");
		Document mOutXmlDoc = mSAXBuilder.build(mIsr);
		String mResultCode = mOutXmlDoc.getRootElement()
		.getChild("TransResult").getChildText("ResultCode");
		if (mResultCode.equals("0000")) {
			String mKeyPath = getClass().getResource("security/").getPath();
			FileOutputStream mKeyFos = new FileOutputStream(mKeyPath + cPort + ".dat");
			mKeyFos.write(mDesKey.getBytes());
			mKeyFos.close();
			cLogger.info("��Կ���³ɹ���");
		}
		
		cLogger.debug("Out ICBCControl.keyChange()!");
	}
	
	
	
	 public Document sendRequestUI(String tFunctionFlag) {
		
	    	functionFlag = tFunctionFlag;

	        try {
	        	
	        	System.out.println("1.��֯���Ա�����Ϣ");

	            Socket socket = new Socket(cIP, cPort);
	            
	            OutputStream outputstream = socket.getOutputStream();
	            InputStream inputstream = socket.getInputStream();
	            InputStream fis = mInputStream;

	            byte array[] = new byte[24]; 
	            int m = 1;
//	          ���ļ�����Ϊ�ֽ�����
	            byte[] bytes = new byte[1];
		      	  ByteArrayOutputStream vInNoSTDByteArrayOutputStream = new ByteArrayOutputStream();
		      	  while (fis.read(bytes) != -1)
		      	  {
		      			vInNoSTDByteArrayOutputStream.write(bytes);
		      	  }
		      	  vInNoSTDByteArrayOutputStream.flush();
		      	  vInNoSTDByteArrayOutputStream.close();
		      	  byte[] tInNoSTDbytes = vInNoSTDByteArrayOutputStream.toByteArray();
		      	  
	      	  
	            //��ͷ
	            int fileLength = 0;
	            InputStream d = new ByteArrayInputStream(tInNoSTDbytes);
	            for(fileLength = 0; d.read() != -1; fileLength++);
	            
	            System.out.println("  �������ܳ��ȣ�" + fileLength);
	            int tPackHeadLength = fileLength;
	            String tPackHeadLengthStr = (new StringBuffer(String.valueOf(tPackHeadLength))).toString();
	            
//	          ���мӽ��ܱ�־λ
	            String Flag = "0";
	            byte FlagLength[] = Flag.getBytes();
	            array[0] = FlagLength[0];
	            
	            String BankInfo = "BANK&&COMM";
	            byte BankInfoLength[] = BankInfo.getBytes();
	            for(int i = 0; i < 10; i++)
	            {
	          	 if(i < BankInfoLength.length)
	          	 {
	                 array[m] = BankInfoLength[i];
	          	 }       
	          	 m++;               
	            }              
	            
//	          ��������Ϣ
	            byte arrayPackFunctionFlag[] = functionFlag.getBytes();
	            for(int i = 0; i < arrayPackFunctionFlag.length; i++)
	            {
	               array[m] = arrayPackFunctionFlag[i];
	               m++;
	            }   
//	          �����峤����Ϣ
	            byte arrayPackHeadLength[] = tPackHeadLengthStr.getBytes();
	            for(int i = 0; i < 8-arrayPackHeadLength.length; i++)
	            {
	            	array[m] = "0".getBytes()[0];     
	            	m++;
	            }
	            for(int i = 0; i < arrayPackHeadLength.length; i++)
	            {
	              array[m] = arrayPackHeadLength[i];       
	          	  m++;               
	            }
	            
	            
	            String test = new String (array);
	            System.out.println("  ����ı���ͷ��Ϣ:"+test);
	            String fileLengthString = fileLength + "";           
	            outputstream.write(array);
	            
	            
	            //����
	            System.out.println("2.��ʼ������");
	            
	            InputStream InNoSTD = new ByteArrayInputStream(tInNoSTDbytes);
	            InputStream InNoSTD2 = new ByteArrayInputStream(tInNoSTDbytes);
	            for(; InNoSTD.read(bytes) != -1; outputstream.write(bytes)); 
	            System.out.println("jiaoyihou");

	    
	            
	       
	           
	            
	            outputstream.flush();
	            socket.shutdownOutput();

	           
	           
	            /** ������صĽ�� */
	            System.out.println("�ͻ��˵õ����ؽ����" + inputstream);
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            // ��ȡ����ǰ24���ֽڣ���ñ��ĳ���
	            String tPackHead = "";
	            String lengStr = "";
	            int x = 0;
	            for (int i = 0; i < 24; i++) {
	                x = inputstream.read();
	                if (x == -1) {
	                    throw new RuntimeException("�������ֽ�����С��16");
	                } 
	                tPackHead += (char) x;
	            } 
	            
	            System.out.println("  ���صİ�ͷ:"+tPackHead);
	        	int length = 0;
	            // ��ȡ�������岿��

	        	x = inputstream.read();
	            for(length = 0; x!= -1; length++){
	            	baos.write(x);
	                x = inputstream.read();
	            }
	            System.out.println("  ���ر��ĳ���Ϊ��" + length);
	            baos.flush();
	            baos.close();
	            InputStream bais = new ByteArrayInputStream(baos
	                    .toByteArray());
	            
	            /** ������صĽ�� */
	            System.out.println("3.���׽��� !");
	            //������
	            try { 
	            	//��ǰ�û�������Ϣ�������ļ���
	            //	YBTFun.savaStream(bais,"4",tOutMsgFilePath+".xml");
	            	
	            	
//	      		  FileOutputStream fos = new FileOutputStream(new File(tOutMsgFilePath));
//	      	      byte[] rbytes = new byte[1];
//	      	      while (bais.read(rbytes) != -1)
//	      	      {	  
//	      		    fos.write(rbytes); 
//	      		  }
//	      	      fos.flush();
//	      	      fos.close();
	      		  System.out.println("4.���׽���ǣ�"); 
	      	   } catch (Exception e) {
	      		  e.printStackTrace(); 
	      	   } 
	            
	            

	            
	            socket.close();

	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }

	        return this.resultDocument;
 
	    }
	 public Document getXmlFromLis(){ 
//		add by zhj ���ڲ���
//			String mInStr = "D:\\��\\��ѯ_out.xml";
			String mInStr = "D:\\��\\ǩ��_outSvc.xml";
//			String mInStr = "D:\\��\\�ش�_out.xml";
//			String mInStr = "D:\\��\\�ڵ�_out.xml";
//		 String mInStr = "D:\\��\\����_outSvc.xml";
//			String mInStr = "D:\\��\\��ԥ���˱���ѯ_outSvc.xml";
//		 String mInStr = "D:\\��\\��ԥ���˱�_outSvc.xml";
//			String mInStr = "D:\\��\\��ԥ���˱�����_outSvc.xml";
//			String mInStr = "D:\\��\\���ڽɷѲ�ѯ_out.xml";
//			String mInStr = "D:\\��\\���ڽɷ�_outSvc.xml";
//			String mInStr = "D:\\��\\���ڽɷѳ���_outSvc.xml";
//			String mInStr = "D:\\��\\���ڸ�����ѯ_outSvc.xml";
//			String mInStr = "D:\\��\\���ڸ���_outSvc.xml";
//			String mInStr = "D:\\��\\���ڸ�������_out.xml";
//			String mInStr = "D:\\��\\�˱���ѯ_outSvc.xml";
//			String mInStr = "D:\\��\\�˱�_outSvc.xml";
//			String mInStr = "D:\\��\\�˱�����_outSvc.xml";
			InputStream mIs = null;  
			try { 
				mIs = new FileInputStream(mInStr); 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
			Document mOutXmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
			System.out.println( "��ʱtestUI.getXmlFromLis()�м���"+mInStr);
			//JdomUtil.print(mOutXmlDoc);
	return mOutXmlDoc;
	 }
}
