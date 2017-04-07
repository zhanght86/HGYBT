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
	 * ����AES ������Կ
	 */
	public void createAESCode(String nKeyPath,int pPort) throws Exception{
		cLogger.info("��ʼ����AES������Կ...");
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
		
		cLogger.info("����AES������Կ���..."+ tempAESCode);
	}
		
	/***
	 * ��������ͬ����Կ���׳ɹ�֮�󣬽������ɵ���Կ�滻�ϵ���Կ����������Կ���ݡ�
	 * @param args
	 */
	public void synchAESCode (String nKeyPath,int pPort) {
		String mKeyPath = nKeyPath;
		cLogger.info("��ʼ����old AES������Կ...");
		String aesFilePath = mKeyPath + pPort + ".dat";   //����Կ
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
			cLogger.debug("����AES������Կ�쳣...");
		}
		
		cLogger.info("����old AES������Կ���...");
		
		cLogger.info("��ʼͬ��AES������Կ...");
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
			cLogger.debug("ͬ��AES������Կ�쳣...");
		}
		cLogger.info("ͬ��AES������Կ���...");
	}
	
	/***
	 * ��������ͬ����Կ���׳ɹ�֮�󣬽������صİ�ȫ֤���滻�ϵİ�ȫ֤�飬�����ϰ�ȫ֤�鱸�ݡ�
	 * @param args
	 */
	public void synchRSACode (String mKeyPath,int pPort) {
//		String mKeyPath = getClass().getResource(pPort + "/").getPath();
		cLogger.info("��ʼ����old RSA������Կ...");
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
			cLogger.debug("����RSA������Կ�쳣...");
		}
		
		cLogger.info("����old RSA������Կ���...");
		
		cLogger.info("��ʼͬ��RSA������Կ...");
		try {
			String aesTempFilePath = mKeyPath + "pubRSA/" + "cacert.crt";   //��֤��
			InputStream mAESSynchIs = new FileInputStream(aesTempFilePath);
			String contAES = new String(IOTrans.toBytes(mAESSynchIs));
			FileWriter fwBak = new FileWriter(mKeyPath + "pubRSA/" + pPort + "_pub.cer");   //��֤��
			fwBak.write(contAES);
			fwBak.flush();
			fwBak.close();
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("ͬ��RSA������Կ�쳣...");
		}
		cLogger.info("ͬ��RSA������Կ���...");
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
