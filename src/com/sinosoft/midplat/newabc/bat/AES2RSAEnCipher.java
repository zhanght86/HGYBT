package com.sinosoft.midplat.newabc.bat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;


public class AES2RSAEnCipher {

	private Logger cLogger = Logger.getLogger(getClass());
	
	/***
	 * 生成AES 工作密钥
	 */
	public void createAESCode(String nKeyPath,int pPort) throws Exception{
		cLogger.info("开始生成AES工作密钥...");
		String mKeyPath = nKeyPath;
		FileWriter mKeyIs = new FileWriter(mKeyPath + pPort + "_AESTemp.dat");
		
		String[] mAESCode = {"q","1","w","2","e","3","r","4","t","5","y","6","u","7","i","8","o","9","o","0","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
		String tempAESCode = "";
		for (int i = 0; i < 16; i++) {
			tempAESCode += mAESCode[((int)(Math.random()*36))];
		}
		
		mKeyIs.write(tempAESCode);
		mKeyIs.flush();
		mKeyIs.close();
		
		cLogger.info("生成AES工作密钥完毕..."+ tempAESCode);
	}
		
	/***
	 * 在于银行同步密钥交易成功之后，将新生成的密钥替换老的密钥，并将老密钥备份。
	 * @param args
	 */
	public void synchAESCode (String nKeyPath,int pPort) {
		String mKeyPath = nKeyPath;
		cLogger.info("开始备份old AES工作密钥...");
		String aesFilePath = mKeyPath + pPort + ".dat";   //老密钥
		try {
			InputStream mIs = new FileInputStream(aesFilePath);
			String contAES = new String(IOTrans.toBytes(mIs));
			
			File fileBak = new File(mKeyPath + contAES +".dat");
			if(!fileBak.exists()){
				fileBak.createNewFile();
			}
			FileWriter fwBak = new FileWriter(fileBak);
			fwBak.write(contAES);
			fwBak.flush();
			fwBak.close();
			mIs.close();
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("备份AES工作密钥异常...");
		}
		
		cLogger.info("备份old AES工作密钥完毕...");
		
		cLogger.info("开始同步AES工作密钥...");
		try {
			String aesTempFilePath = mKeyPath + pPort + "_AESTemp.dat";
			InputStream mAESSynchIs = new FileInputStream(aesTempFilePath);
			String contAES = new String(IOTrans.toBytes(mAESSynchIs));
			
			FileWriter fwBak = new FileWriter(mKeyPath + pPort + ".dat");
			fwBak.write(contAES);
			fwBak.flush();
			fwBak.close();
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("同步AES工作密钥异常...");
		}
		cLogger.info("同步AES工作密钥完毕...");
	}
	
	/***
	 * 在于银行同步密钥交易成功之后，将新下载的安全证书替换老的安全证书，并将老安全证书备份。
	 * @param args
	 */
	public void synchRSACode (String mKeyPath,int pPort) {
//		String mKeyPath = getClass().getResource(pPort + "/").getPath();
		cLogger.info("开始备份old RSA工作密钥...");
		String aesFilePath = mKeyPath + "pubRSA/" + pPort + "_pub.cer";
		try {
			InputStream mIs = new FileInputStream(aesFilePath);
			String contAES = new String(IOTrans.toBytes(mIs));
			
			File fileBak = new File(mKeyPath + "pubRSA/" + DateUtil.getCur8Date() +"_pub.cer");
			if(!fileBak.exists()){
				fileBak.createNewFile();
			}
			FileWriter fwBak = new FileWriter(fileBak);
			fwBak.write(contAES);
			fwBak.flush();
			fwBak.close();
			mIs.close();
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("备份RSA工作密钥异常...");
		}
		
		cLogger.info("备份old RSA工作密钥完毕...");
		
		cLogger.info("开始同步RSA工作密钥...");
		try {
			String aesTempFilePath = mKeyPath + "pubRSA/" + "cacert.crt";   //新证书
			InputStream mAESSynchIs = new FileInputStream(aesTempFilePath);
			String contAES = new String(IOTrans.toBytes(mAESSynchIs));
			FileWriter fwBak = new FileWriter(mKeyPath + "pubRSA/" + pPort + "_pub.cer");   //老证书
			fwBak.write(contAES);
			fwBak.flush();
			fwBak.close();
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("同步RSA工作密钥异常...");
		}
		cLogger.info("同步RSA工作密钥完毕...");
	}
	public static void main(String[] args) {
		
		try {
//			new AES2RSAEnCipher().createAESCode(52004);
//			new AES2RSAEnCipher().synchAESCode(52004);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
