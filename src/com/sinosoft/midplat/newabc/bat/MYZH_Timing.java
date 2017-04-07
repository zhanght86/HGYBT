package com.sinosoft.midplat.newabc.bat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import com.sinosoft.midplat.common.XmlTag;

public class MYZH_Timing extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected Element cConfigEle;
	private String publicKey;
	private String newAESRSAKey;
	private String oldAESRSAKey;
	private AES2RSAEnCipher aesTorsa;

	public MYZH_Timing() {
	}

	public void run() {
		cLogger.info("Into MYZH_Timing.run()...");

		try {
			cConfigEle = BatUtils.getConfigEle("1001");
			System.out.println("quzhi:" + cConfigEle.getChildText("startTime"));
		} catch (Exception e) {
			cLogger.info("��ȡ�����ļ��쳣������·���Ƿ���ȷ���ļ��Ƿ����......");
			e.getStackTrace();
		}

		String mLocalPort = cConfigEle.getChildTextTrim("LocalPort");
		cLogger.info("�õ������·����" + cConfigEle.getChildText("FilePath")+ mLocalPort + "/");
		
		String mKeyPath = cConfigEle.getChildText("FilePath") + mLocalPort+ "/";
				
		String publicNewKeyPath = mKeyPath + "pubRSA/" + "cacert.crt";
		long mOldTimeMillis = System.currentTimeMillis();

		String mTransNo = "1001"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				
		try {

			// ��ü���AES������Կ�� RSA��Կ
			String publicKeyPath = mKeyPath + "pubRSA/" + mLocalPort+ "_pub.cer";
					
			publicKey = new String(IOTrans.toBytes(new FileInputStream(publicKeyPath)));
			
			aesTorsa = new AES2RSAEnCipher();
			try {
				aesTorsa.createAESCode(mKeyPath, Integer.parseInt(mLocalPort));
				Thread.sleep(2000);

				String newAESKeyPath = mKeyPath + mLocalPort + "_AESTemp.dat";
				String newAESKey = new String(IOTrans.toBytes(new FileInputStream(newAESKeyPath)));
				
				cLogger.info("RSA����ǰ���¹�����Կ��" + newAESKey);
				cLogger.info("RSA����ǰ�Ĺ�Կ��" + publicKey);

				newAESRSAKey = RSAUtils.encryptAESKey(publicNewKeyPath,newAESKey);
				
				cLogger.info("RSA���ܺ���¹�����Կ��" + newAESRSAKey);

				String oldAESKeyPath = mKeyPath + mLocalPort + ".dat"; // �ϵ���Կ
				String oldAESKey = new String(IOTrans.toBytes(new FileInputStream(oldAESKeyPath)));
						
				cLogger.info("RSA����ǰ���Ϲ�����Կ��" + oldAESKey);

				oldAESRSAKey = RSAUtils.encryptAESKey(publicKeyPath, oldAESKey); // ����֤���������Կ

				cLogger.info("RSA���ܺ���Ϲ�����Կ��" + oldAESRSAKey);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			cLogger.info("�쳣��" + e1.getMessage());
		}

		String mIp = cConfigEle.getChildText("ip").trim();
		int mPort = Integer.valueOf(cConfigEle.getChildTextTrim("ABCport")).intValue();
		
		try {
			// ƴ��������
			Element mABCB2I = new Element("ABCB2I");

			Element mHeader = new Element("Header");

			Date date = new Date();
			Element mTransDate = new Element("TransDate");// ��������
			mTransDate.setText(new SimpleDateFormat("yyyyMMdd").format(date));
			Element mTransTime = new Element("TransTime");// ����ʱ��
			mTransTime.setText(new SimpleDateFormat("HHmmss").format(date));
			Element mTransCode = new Element("TransCode");// ���ױ���
			mTransCode.setText("1001");
			Element mInsuSerial = new Element("InsuSerial");// ���չ�˾��ˮ��
			mInsuSerial.setText(mTransNo);
			Element mBankCode = new Element("BankCode");// ���д���
			mBankCode.setText("03");
			Element mCorpNo = new Element("CorpNo");// ���չ�˾����
			mCorpNo.setText(cConfigEle.getChildTextTrim("ComCode"));
			Element mTransSide = new Element("TransSide");// ���׷���
			mTransSide.setText("0");
			Element mEntrustWay = new Element("EntrustWay");// ί�з�ʽ

			mHeader.addContent(mTransDate);
			mHeader.addContent(mTransTime);
			mHeader.addContent(mTransCode);
			mHeader.addContent(mInsuSerial);
			mHeader.addContent(mBankCode);
			mHeader.addContent(mCorpNo);
			mHeader.addContent(mTransSide);
			mHeader.addContent(mEntrustWay);

			Element mApp = new Element("App");

			Element mReq = new Element("Req");

			Element mEncType = new Element("EncType");// ���ܷ�ʽ 01: Ĭ�Ϸ�ʽRSA+AES��ʽ
			mEncType.setText("01");
			Element mPriKey = new Element("PriKey");// ����Կ
			mPriKey.setText(newAESRSAKey);
			Element mOrgKey = new Element("OrgKey");// ԭ��Կ
			mOrgKey.setText(oldAESRSAKey);

			mReq.addContent(mEncType);
			mReq.addContent(mPriKey);
			mReq.addContent(mOrgKey);
			mApp.addContent(mReq);

			mABCB2I.addContent(mHeader);
			mABCB2I.addContent(mApp);

			byte[] mOutBodyBytes = JdomUtil.toBytes(mABCB2I);
			cLogger.info("IP���˿ڣ�" + mIp + mPort);
			Socket mSocket = new Socket(mIp, mPort);
			cLogger.info("���ӳɹ�");
			byte[] mInTotalBytes = new MYZH_Timing()
					.appendHeadBytes(mOutBodyBytes);
			System.out.println("���͵������ģ�" + new String(mInTotalBytes));
			cLogger.info("���������ģ�");

			mSocket.getOutputStream().write(mInTotalBytes);
			mSocket.shutdownOutput();
			long mCurTimeMillis = System.currentTimeMillis();
			cLogger.info("�ͻ�����������ϣ���ʱ��" + (mCurTimeMillis - mOldTimeMillis)
					/ 1000.0 + "s");

			/** ���´����ر��� ************************/
			InputStream mSocketIs = mSocket.getInputStream();
			cLogger.info("�յ����ر��ģ�");

			// ������ͷ
			byte[] mOutHeadBytes = new byte[73];
			IOTrans.readFull(mOutHeadBytes, mSocketIs);
			// byte[] mOutHeadBytes = IOTrans.toBytes(mSocketIs);
			cLogger.info("�յ����ر���ͷ��" + new String(mOutHeadBytes));
			int mInBodyLength = Integer
					.parseInt(new String(mOutHeadBytes, 4, 8).trim());
			cLogger.info("�յ����ر��ĳ���Ϊ��" + mInBodyLength);

			byte[] nnOutBodyBytes = new byte[mInBodyLength];
			IOTrans.readFull(nnOutBodyBytes, mSocketIs);
			String mOutBodyStr = new String(nnOutBodyBytes, "UTF-8");
			cLogger.info("���з��طǱ�׼���ģ�" + mOutBodyStr);

			Document mOutNoStd = JdomUtil.build(mOutBodyStr.getBytes());

			// ũ����Կ�Ѿ����ز����浽����

			if ("000000".equals(mOutNoStd.getRootElement().getChild("Header")
					.getChildText("RetCode"))) {// ���׳ɹ�
				cLogger.info("ũ�д�����Կ���³ɹ������б�����Կ����");
				aesTorsa.synchAESCode(mKeyPath, Integer.parseInt(mLocalPort));
				aesTorsa.synchRSACode(mKeyPath, Integer.parseInt(mLocalPort));
				cLogger.info("������Կ�������...");
			} else {
				cLogger.error("ũ�д�����Կ���½���ʧ�ܣ������и��±�����Կ����...");
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		cLogger.info("Out MYZH_Timing.run()!");
	}

	public byte[] appendHeadBytes(byte[] mBodyBytes) {

		// ����xmlͷ��֮������
		String mBodyStr = new String(mBodyBytes);
		String tPackHeadLengthStr = String.valueOf(mBodyStr.length());

		for (int i = tPackHeadLengthStr.length(); i < 8; i++)
			tPackHeadLengthStr = "0" + tPackHeadLengthStr;

		String des = "";
		for (int i = 0; i < 40; i++) {
			des += " ";
		}

		String pack = "X1.0" + tPackHeadLengthStr + "1132    " + "0" + "0"
				+ "0" + "0" + "0" + des + "00000000";

		return (pack + mBodyStr).getBytes();
	}

	public static void main(String[] args) {
		Logger cLogger=Logger.getLogger("com.sinosoft.midplat.newabc.bat.MYZH_Timing.main");
		cLogger.info("��ũ����Կ���³�������...");
		MYZH_Timing abcAES = new MYZH_Timing();
		abcAES.run();
		cLogger.info("��ũ����Կ���³������!");
	}

}
