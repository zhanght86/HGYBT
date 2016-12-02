package com.sinosoft.midplat.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class KeyUtil implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(KeyUtil.class);
	
	public static void sqlExecTime(String sSqlName,long mStartMillis) {
		double mExecTime = (double) ((System.currentTimeMillis()-mStartMillis)/1000.0);
		//cLogger.info(sSqlName+"耗时:"+mExecTime+"秒");
		if(mExecTime>0.5){
			cLogger.info(sSqlName+":执行时间过长");
		}
	}

	
	public static byte[] decode(byte[] mBodyBytes,SecretKeySpec keySpec)throws Exception {
		byte[] mRetBytes = null;
		try{
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, keySpec);
		mRetBytes = mCipher.doFinal(mBodyBytes);
		}catch (Exception e) {
			e.printStackTrace();
			cLogger.error("解密时出现异常!");
		}
		return mRetBytes;
	}
	
	public static InputStream decode(InputStream mInputStream,SecretKeySpec keySpec)throws Exception {
		InputStream mRetInputStream = null;
		byte[] mRetBytes = null;
		byte[] mBodyBytes = null;
		try{
			mBodyBytes =IOTrans.toBytes(mInputStream);
			mRetBytes = decode(mBodyBytes,keySpec);
			mRetInputStream = new ByteArrayInputStream(mRetBytes);
		}catch (Exception e) {
			e.printStackTrace();
			cLogger.error("解密时出现异常!");
		}
		return mRetInputStream;
	}
	
	public static byte[] encode(byte[] mBodyBytes,SecretKeySpec keySpec) throws Exception{
		byte[] mRetBytes = null;
		try{
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.ENCRYPT_MODE, keySpec);
		mRetBytes = mCipher.doFinal(mBodyBytes);
		}catch (Exception e) {
			e.printStackTrace();
			cLogger.error("加密时出现异常!");
		}
		return mRetBytes;
	}
	
	public static InputStream encode(InputStream mInputStream,SecretKeySpec keySpec)throws Exception {
		InputStream mRetInputStream = null;
		byte[] mRetBytes = null;
		byte[] mBodyBytes = null;
		try{
			mBodyBytes =IOTrans.toBytes(mInputStream);
			mRetBytes = encode(mBodyBytes,keySpec);
			mRetInputStream = new ByteArrayInputStream(mRetBytes);
		}catch (Exception e) {
			e.printStackTrace();
			cLogger.error("解密时出现异常!");
		}
		return mRetInputStream;
	}	
	
	
	public static void main(String[] args) throws InterruptedException {
			long mStartMillis = System.currentTimeMillis();
			Thread.sleep(50);
			KeyUtil.sqlExecTime("ContDB.info()",mStartMillis);
	}
}
