package com.sinosoft.midplat.newabc.bat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;

public class KeyReset extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	//������Ϣ�ڵ�
	protected String cResultMsg;
	protected Element cConfigEle;
	private static String cCurDate = "";
	private String publicKey;
	private String newAESRSAKey;
	private String oldAESRSAKey;
	private AES2RSAEnCipher aesTorsa;

	public KeyReset() {
	}

	public void run() {
		cLogger.info("Into KeyReset.run()...");
		cResultMsg = null;
		
		try{
			cConfigEle = BatUtils.getConfigEle("1001");
			System.out.println("quzhi:"+cConfigEle.getChildText("startTime"));		
		}catch(Exception e){
			cLogger.info("��ȡ�����ļ��쳣������·���Ƿ���ȷ���ļ��Ƿ����......");
			e.getStackTrace();
		}
		
		if ("".equals(cCurDate)) {
			cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		
		String mLocalPort = cConfigEle.getChildTextTrim("LocalPort");
		cLogger.info("�õ������·����"+cConfigEle.getChildText("FilePath")+"/");
				
		String mKeyPath =SysInfo.cHome+"key/";
		//�°�ȫ֤��
		String publicNewKeyPath = mKeyPath  +"cacert.crt";
		long mOldTimeMillis = System.currentTimeMillis();
		
		String mTransNo = "1001" + cCurDate + new SimpleDateFormat("HHmmss").format(new Date());
		try {
			
			//�ϰ�ȫ֤��
			String publicKeyPath = mKeyPath + mLocalPort+"_pub.cer";
			cLogger.info(publicKeyPath);
			publicKey = new String(IOTrans.toBytes( new FileInputStream(publicKeyPath)));
			
			aesTorsa = new AES2RSAEnCipher();
			try {
				aesTorsa.createAESCode(mKeyPath);
				Thread.sleep(2000);
				
				String newAESKeyPath = mKeyPath +"newABCKey.dat";
				String newAESKey = new String(IOTrans.toBytes( new FileInputStream(newAESKeyPath)));
				
				cLogger.info("RSA����ǰ���¹�����Կ��"+ newAESKey);
				cLogger.info("RSA����ǰ�Ĺ�Կ��"+ publicKey);
								
				newAESRSAKey = RSAUtils.encryptAESKey(publicNewKeyPath, newAESKey);  			
				
				cLogger.info("RSA���ܺ���¹�����Կ��"+ newAESRSAKey);
				
				String oldAESKeyPath = mKeyPath +"oldABCKey.dat";     //�ϵ���Կ
				String oldAESKey = new String(IOTrans.toBytes( new FileInputStream(oldAESKeyPath)));
				cLogger.info("RSA����ǰ���Ϲ�����Կ��"+ oldAESKey);
								
				oldAESRSAKey = RSAUtils.encryptAESKey(publicKeyPath, oldAESKey);  //  ����֤���������Կ				
				 
				cLogger.info("RSA���ܺ���Ϲ�����Կ��"+ oldAESRSAKey);      
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			cLogger.info("�쳣��"+ e1.getMessage());   
		}

		//��ȡ����IP��ַ
		String mIp = cConfigEle.getChildText("ip").trim();
		//��ȡ���ж˿�
		int mPort = Integer.valueOf(cConfigEle.getChildTextTrim("ABCport")).intValue();
		
		try {
			//ƴ���ļ��ϴ����������� 
			//��֯���ڵ��Լ�Header�ڵ����� 
			Element mABCB2I = new Element("ABCB2I");
			
			//ҵ���ͷ
			Element mHeader = new Element("Header");
			
			//���չ�˾��ˮ��
			Element mInsuSerial = new Element("InsuSerial");
			mInsuSerial.setText(mTransNo);
			
			//��ǰ���ڶ���
			Date date = new Date();
			//��������
			Element mTransDate = new Element("TransDate");
			mTransDate.setText(cCurDate);
			
			//����ʱ��
			Element mTransTime = new Element("TransTime");
			mTransTime.setText(new SimpleDateFormat("HHmmss").format(date));
			
			//���д���
			Element mBankCode = new Element("BankCode");
			mBankCode.setText("03");
			
			//���չ�˾����
			Element mCorpNo = new Element("CorpNo");
			mCorpNo.setText(cConfigEle.getChildTextTrim("ComCode"));
			
			//���ױ���
			Element mTransCode = new Element("TransCode");
			mTransCode.setText("1001");
			
			//���׷���
			Element mTransSide = new Element("TransSide");
			mTransSide.setText("0");
			
			//ί�з�ʽ
			Element mEntrustWay = new Element("EntrustWay");
			
			// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_
			mHeader.addContent(mInsuSerial).addContent(mTransDate)
			.addContent(mTransTime).addContent(mBankCode)
			.addContent(mCorpNo).addContent(mTransCode)
			.addContent(mTransSide).addContent(mEntrustWay);
			// ^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_^_

			//��֯App�ڵ����� 
			//ҵ�����
			Element mApp = new Element("App");
			
			//������
			Element mReq = new Element("Req");
			
			//���ܷ�ʽ[01: Ĭ�Ϸ�ʽRSA+AES��ʽ]
			Element mEncType = new Element("EncType");
			mEncType.setText("01");
			
			//����Կ[16�����ַ���]
			Element mPriKey = new Element("PriKey");
			mPriKey.setText(newAESRSAKey);
			
			//ԭ��Կ[16�����ַ���]
			Element mOrgKey = new Element("OrgKey");
			mOrgKey.setText(oldAESRSAKey);
			
			mReq.addContent(mEncType).addContent(mPriKey)
			.addContent(mOrgKey);
			mApp.addContent(mReq);

			mABCB2I.addContent(mHeader);
			mABCB2I.addContent(mApp);

			//�����ڵ�ת��ΪGBK������ֽ����飬����ԭ��ʽ
			byte[] mOutBodyBytes = JdomUtil.toBytes(mABCB2I);
			cLogger.info("IP���˿ڣ�" + mIp + mPort);
			Socket mSocket = new Socket(mIp, mPort);
			cLogger.info("���ӳɹ�");
			//��ȡ����׷�ӱ���ͷ�ֽ��������������ֽ�����
			byte[] mInTotalBytes = new KeyReset().appendHeadBytes(mOutBodyBytes);
			System.out.println("���͵������ģ�" + new String(mInTotalBytes));
			cLogger.info("���������ģ�");
			
			//�������ݸ����з����������ֽ�����
			OutputStream os = mSocket.getOutputStream();
			os.write(mInTotalBytes);
			os.flush();
			
			/** ���´����ر��� ************************/
			//�������ݸ�����ͨ����Ӧ���������ֽ���
			InputStream mSocketIs = mSocket.getInputStream();
			//�ر������
			mSocket.shutdownOutput();
			//��ǰʱ�������
			long mCurTimeMillis = System.currentTimeMillis();
			//�ͻ�����������ϣ���ʱ��(��ǰʱ�������-��ʼʱ�������)/1000.0s
			cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)/ 1000.0 + "s");
			cLogger.info("�յ����ر��ģ�");

			// ������ͷ
			byte[] mOutHeadBytes = new byte[73];
			//�������ֽ����ж�ȡ73���ֽڵ����ݣ�����������ֽ����飬���ر���
			IOTrans.readFull(mOutHeadBytes, mSocketIs);
			// byte[] mOutHeadBytes = IOTrans.toBytes(mSocketIs);
			//�յ����ر���ͷ������ͷ�ַ���
			cLogger.info("�յ����ر���ͷ��" + new String(mOutHeadBytes));
			//��ȡ������[����]����
			int mInBodyLength = Integer.parseInt(new String(mOutHeadBytes, 4, 8).trim());
			//�յ����ر��ĳ���Ϊ��������[����]����
			cLogger.info("�յ����ر��ĳ���Ϊ��" + mInBodyLength);

			//���ر������ֽ�����
			byte[] nnOutBodyBytes = new byte[mInBodyLength];
			//�������ֽ����ж�ȡ�����峤�ȸ��ֽڵ����ݣ�����������ֽ����飬���ر���
			IOTrans.readFull(nnOutBodyBytes, mSocketIs);
			//��ȡUTF-8����ķ��ر������ַ���
			String mOutBodyStr = new String(nnOutBodyBytes, "UTF-8");
			//���з��طǱ�׼���ģ����ر������ַ���
			cLogger.info("���з��طǱ�׼���ģ�" + mOutBodyStr);

			//����GBK���빹��һ���Ǳ�׼������ģ����Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)
			Document mOutNoStd = JdomUtil.build(mOutBodyStr.getBytes());
			
			// ũ����Կ�Ѿ����ز����浽����
			if ("000000".equals(mOutNoStd.getRootElement().getChild("Header").getChildText("RetCode"))) {// ���׳ɹ�
				//ũ�д�����Կ���³ɹ������б�����Կ����
				cLogger.info("ũ�д�����Կ���³ɹ������б�����Կ����");
				//������ͬ����Կ���׳ɹ�֮�󣬽������ɵ���Կ�滻�ϵ���Կ����������Կ����
				aesTorsa.synchAESCode(mKeyPath, Integer.parseInt(mLocalPort));
				//������ͬ����Կ���׳ɹ�֮�󣬽������صİ�ȫ֤���滻�ϵİ�ȫ֤�飬�����ϰ�ȫ֤�鱸��
				aesTorsa.synchRSACode(mKeyPath, Integer.parseInt(mLocalPort));
				//������Կ�������...
				cLogger.info("������Կ�������!");
			} else {
				//ũ�д�����Կ���½���ʧ�ܣ������и��±�����Կ����...
				cLogger.error("ũ�д�����Կ���½���ʧ�ܣ������и��±�����Կ����!");
			}
			if (os != null)
				os.close();
			if (mSocketIs != null)
				mSocketIs.close();
			if (mSocket != null)
				mSocket.close();
			cResultMsg = mOutNoStd.getRootElement().getChild("Header").getChildText("RetMsg");
		} catch (UnknownHostException e) {
			cResultMsg = e.toString();
			e.printStackTrace();
		} catch (IOException e) {
			cResultMsg = e.toString();
			e.printStackTrace();
		}

		cLogger.info("Out KeyReset.run()!");
	}

	/**
	 * @Title: appendHeadBytes
	 * @Description: ����׷�ӱ���ͷ�ֽ�����
	 * @param mBodyBytes �������ֽ�����
	 * @return ����ͷ+������			�ֽ�����
	 * @return ����ͷ+������			byte[]
	 * @throws
	 */
	public byte[] appendHeadBytes(byte[] mBodyBytes) {

		// ����xmlͷ��֮������
		String mBodyStr = new String(mBodyBytes);
		String tPackHeadLengthStr = String.valueOf(mBodyStr.length());

		/*
		 * ���ݰ�����[N8:0x(8-���峤��)+���峤��]������ĳ��Ȳ��ܳ���50K�ֽڡ�
		 * ������ܣ�ָ���ܺ�ĳ��ȣ����ѹ����ָ��ѹ����ĳ��ȡ�
		 */
		for (int i = tPackHeadLengthStr.length(); i < 8; i++)
			//ǰ��(8-���峤��)��0
			tPackHeadLengthStr = "0" + tPackHeadLengthStr;

		//ժҪ[C40:40x' ']
		String des = "";
		for (int i = 0; i < 40; i++) {
			//����40���ո�
			des += " ";
		}
		/*	[C1:�̶�"X",C3:1.0,N8:���ݰ�����,C8:���з��䱣�չ�˾����,C1:0-������1-�ؼ����ݼ���2-�����������,C1:�����㷨
		 *		��������+�汾��+���ݰ�����[���峤��]+��˾����+���ܱ�ʾ+�����㷨
		 */
		String pack = "X1.0" + tPackHeadLengthStr + "1147    " + "0" + "0"
		/*
		 * C1:0-��ѹ��1-ѹ��,C1:0,C1:0,C40:40x' ',C8:�̶�ֵ00000000]
		 * ����ѹ����־+����ѹ���㷨+ժҪ�㷨+ժҪ+Ԥ���ֶ�[�̶�ֵC8:00000000]
		 */
								+ "0" + "0" + "0" + des + "00000000";
		
		/*
		 * 			��ͷ+XML�ṹҵ�����	
		 * ����	����ͷ+������			�ֽ�����
		 */
		return (pack + mBodyStr).getBytes();
	}

	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger=Logger.getLogger("com.sinosoft.midplat.newabc.bat.KeyReset.main");
		mLogger.info("��ũ����Կ���ó�������...");
		
		KeyReset abcAES = new KeyReset();
		
		// ���ڲ����ˣ����ò��������� 
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
		
			/**
			 * �ϸ�����У���������ʽ��\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 * 
			 * ������У���������ʽ��\\d{4}-\\d{2}-\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}
		abcAES.run();
		mLogger.info("��ũ����Կ���ó������!");
	}

}
