package com.sinosoft.midplat.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class DigestMaker {
	private final static Logger cLogger = Logger.getLogger(DigestMaker.class);
	
	public static String getMD5HexDigest(byte[] pBytes) {
		cLogger.info("Into DigestMaker.getMD5HexDigest()...");
		
		MessageDigest mMessageDigest = null;
		try {
			mMessageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			cLogger.error("不支持MD5算法！", ex);
			return null;
		}
		byte[] mDigestBytes = mMessageDigest.digest(pBytes);
		
		cLogger.info("Out DigestMaker.getMD5HexDigest()!");
		return NumberUtil.byteToHex(mDigestBytes);
	}
	
	public static String getSHA1HexDigest(byte[] pBytes) {
		cLogger.info("Into DigestMaker.getSHA1HexDigest()...");
		
		MessageDigest mMessageDigest = null;
		try {
			mMessageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException ex) {
			cLogger.error("不支持SHA-1算法！", ex);
			return null;
		}
		byte[] mDigestBytes = mMessageDigest.digest(pBytes);
		
		cLogger.info("Out DigestMaker.getSHA1HexDigest()!");
		return NumberUtil.byteToHex(mDigestBytes);
	}
}
