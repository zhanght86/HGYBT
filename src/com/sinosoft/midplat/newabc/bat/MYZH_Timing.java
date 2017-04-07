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
			cLogger.info("读取配置文件异常，请检查路径是否正确及文件是否存在......");
			e.getStackTrace();
		}

		String mLocalPort = cConfigEle.getChildTextTrim("LocalPort");
		cLogger.info("得到类绝对路径：" + cConfigEle.getChildText("FilePath")+ mLocalPort + "/");
		
		String mKeyPath = cConfigEle.getChildText("FilePath") + mLocalPort+ "/";
				
		String publicNewKeyPath = mKeyPath + "pubRSA/" + "cacert.crt";
		long mOldTimeMillis = System.currentTimeMillis();

		String mTransNo = "1001"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				
		try {

			// 获得加密AES工作密钥的 RSA公钥
			String publicKeyPath = mKeyPath + "pubRSA/" + mLocalPort+ "_pub.cer";
					
			publicKey = new String(IOTrans.toBytes(new FileInputStream(publicKeyPath)));
			
			aesTorsa = new AES2RSAEnCipher();
			try {
				aesTorsa.createAESCode(mKeyPath, Integer.parseInt(mLocalPort));
				Thread.sleep(2000);

				String newAESKeyPath = mKeyPath + mLocalPort + "_AESTemp.dat";
				String newAESKey = new String(IOTrans.toBytes(new FileInputStream(newAESKeyPath)));
				
				cLogger.info("RSA加密前的新工作密钥：" + newAESKey);
				cLogger.info("RSA加密前的公钥：" + publicKey);

				newAESRSAKey = RSAUtils.encryptAESKey(publicNewKeyPath,newAESKey);
				
				cLogger.info("RSA加密后的新工作密钥：" + newAESRSAKey);

				String oldAESKeyPath = mKeyPath + mLocalPort + ".dat"; // 老的密钥
				String oldAESKey = new String(IOTrans.toBytes(new FileInputStream(oldAESKeyPath)));
						
				cLogger.info("RSA加密前的老工作密钥：" + oldAESKey);

				oldAESRSAKey = RSAUtils.encryptAESKey(publicKeyPath, oldAESKey); // 用老证书加密老密钥

				cLogger.info("RSA加密后的老工作密钥：" + oldAESRSAKey);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			cLogger.info("异常：" + e1.getMessage());
		}

		String mIp = cConfigEle.getChildText("ip").trim();
		int mPort = Integer.valueOf(cConfigEle.getChildTextTrim("ABCport")).intValue();
		
		try {
			// 拼接请求报文
			Element mABCB2I = new Element("ABCB2I");

			Element mHeader = new Element("Header");

			Date date = new Date();
			Element mTransDate = new Element("TransDate");// 交易日期
			mTransDate.setText(new SimpleDateFormat("yyyyMMdd").format(date));
			Element mTransTime = new Element("TransTime");// 交易时间
			mTransTime.setText(new SimpleDateFormat("HHmmss").format(date));
			Element mTransCode = new Element("TransCode");// 交易编码
			mTransCode.setText("1001");
			Element mInsuSerial = new Element("InsuSerial");// 保险公司流水号
			mInsuSerial.setText(mTransNo);
			Element mBankCode = new Element("BankCode");// 银行代码
			mBankCode.setText("03");
			Element mCorpNo = new Element("CorpNo");// 保险公司代码
			mCorpNo.setText(cConfigEle.getChildTextTrim("ComCode"));
			Element mTransSide = new Element("TransSide");// 交易发起方
			mTransSide.setText("0");
			Element mEntrustWay = new Element("EntrustWay");// 委托方式

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

			Element mEncType = new Element("EncType");// 加密方式 01: 默认方式RSA+AES方式
			mEncType.setText("01");
			Element mPriKey = new Element("PriKey");// 新密钥
			mPriKey.setText(newAESRSAKey);
			Element mOrgKey = new Element("OrgKey");// 原密钥
			mOrgKey.setText(oldAESRSAKey);

			mReq.addContent(mEncType);
			mReq.addContent(mPriKey);
			mReq.addContent(mOrgKey);
			mApp.addContent(mReq);

			mABCB2I.addContent(mHeader);
			mABCB2I.addContent(mApp);

			byte[] mOutBodyBytes = JdomUtil.toBytes(mABCB2I);
			cLogger.info("IP、端口：" + mIp + mPort);
			Socket mSocket = new Socket(mIp, mPort);
			cLogger.info("连接成功");
			byte[] mInTotalBytes = new MYZH_Timing()
					.appendHeadBytes(mOutBodyBytes);
			System.out.println("发送的请求报文：" + new String(mInTotalBytes));
			cLogger.info("发送请求报文！");

			mSocket.getOutputStream().write(mInTotalBytes);
			mSocket.shutdownOutput();
			long mCurTimeMillis = System.currentTimeMillis();
			cLogger.info("客户端请求发送完毕！耗时：" + (mCurTimeMillis - mOldTimeMillis)
					/ 1000.0 + "s");

			/** 以下处理返回报文 ************************/
			InputStream mSocketIs = mSocket.getInputStream();
			cLogger.info("收到返回报文！");

			// 处理报文头
			byte[] mOutHeadBytes = new byte[73];
			IOTrans.readFull(mOutHeadBytes, mSocketIs);
			// byte[] mOutHeadBytes = IOTrans.toBytes(mSocketIs);
			cLogger.info("收到返回报文头：" + new String(mOutHeadBytes));
			int mInBodyLength = Integer
					.parseInt(new String(mOutHeadBytes, 4, 8).trim());
			cLogger.info("收到返回报文长度为：" + mInBodyLength);

			byte[] nnOutBodyBytes = new byte[mInBodyLength];
			IOTrans.readFull(nnOutBodyBytes, mSocketIs);
			String mOutBodyStr = new String(nnOutBodyBytes, "UTF-8");
			cLogger.info("银行返回非标准报文！" + mOutBodyStr);

			Document mOutNoStd = JdomUtil.build(mOutBodyStr.getBytes());

			// 农行密钥已经下载并保存到本地

			if ("000000".equals(mOutNoStd.getRootElement().getChild("Header")
					.getChildText("RetCode"))) {// 交易成功
				cLogger.info("农行处理密钥更新成功，进行本地密钥更换");
				aesTorsa.synchAESCode(mKeyPath, Integer.parseInt(mLocalPort));
				aesTorsa.synchRSACode(mKeyPath, Integer.parseInt(mLocalPort));
				cLogger.info("本地密钥更换完成...");
			} else {
				cLogger.error("农行处理密钥更新交易失败，不进行更新本地密钥处理...");
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		cLogger.info("Out MYZH_Timing.run()!");
	}

	public byte[] appendHeadBytes(byte[] mBodyBytes) {

		// 剪切xml头部之后报文体
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
		cLogger.info("新农行密钥更新程序启动...");
		MYZH_Timing abcAES = new MYZH_Timing();
		abcAES.run();
		cLogger.info("新农行密钥更新程序完成!");
	}

}
