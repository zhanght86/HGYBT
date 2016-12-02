package com.sinosoft.midplat.newabc.util;

import com.sinosoft.midplat.common.SysInfo;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

public class RSAUtil_old {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	private static final int MAX_ENCRYPT_BLOCK = 128;
	private static final int MAX_DECRYPT_BLOCK = 128;
	private static PublicKey pbKey;

	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		SecureRandom secrand = new SecureRandom();
		secrand.setSeed("10260d1b4385194a".getBytes());

		keyPairGen.initialize(512, secrand);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map keyMap = new HashMap(2);
		keyMap.put("RSAPublicKey", publicKey);
		keyMap.put("RSAPrivateKey", privateKey);
		return keyMap;
	}

	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateK);
		signature.update(data);
		return Base64Utils.encode(signature.sign());
	}

	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Utils.decode(sign));
	}

	public static byte[] decryptByPrivateKey(byte[] encryptedData,
			String privateKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(2, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;

		int i = 0;

		while (inputLen - offSet > 0) {
			byte[] cache;
			if (inputLen - offSet > 128)
				cache = cipher.doFinal(encryptedData, offSet, 128);
			else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			++i;
			offSet = i * 128;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	public static byte[] decryptByPublicKey(byte[] encryptedData,
			PublicKey publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(2, pbKey);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;

		int i = 0;

		while (inputLen - offSet > 0) {
			byte[] cache;
			if (inputLen - offSet > 128)
				cache = cipher.doFinal(encryptedData, offSet, 128);
			else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			++i;
			offSet = i * 128;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey)
			throws Exception {
//		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding",new org.bouncycastle.jce.provider.BouncyCastleProvider());
	    cipher.init(1, publicKey);
	    int inputLen = data.length;
	    System.err.println("inputLen:" + inputLen);
	    
	    
	    byte[] encryptedData = cipher.doFinal(data);

	    return encryptedData;
	    
	}

	public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(1, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;

		int i = 0;

		while (inputLen - offSet > 0) {
			byte[] cache;
			if (inputLen - offSet > 128)
				cache = cipher.doFinal(data, offSet, 128);
			else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			++i;
			offSet = i * 128;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get("RSAPrivateKey");
		return Base64.encode(key.getEncoded());
	}

	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get("RSAPublicKey");
		return Base64.encode(key.getEncoded());
	}

	public static void readCRT() throws Exception {
		CertificateFactory certificatefactory = CertificateFactory
				.getInstance("X.509");
		String mFilePath = SysInfo.cHome + "key/cacert.crt";

		System.out.println("mFilePath====111==" + mFilePath);
		FileInputStream bais = new FileInputStream(mFilePath);
		
		X509Certificate cert = (X509Certificate) certificatefactory
				.generateCertificate(bais);
		pbKey = cert.getPublicKey();
	}

	public static String getAESKey(String key) {
		String vvkey = "";
		try {
			readCRT();
			key = rpad(key, 128, ' ');
			byte[] encodedData = encryptByPublicKey(key.getBytes(), pbKey);
			vvkey = AES.byte2hex(encodedData);
		} catch (Exception e) {
			System.out.println("读取证书异常!");
			e.printStackTrace();
		}
		return vvkey;
	}

	public static String lpad(String strValue, int intLength, char tmp) {
		int strLen = strValue.getBytes().length;
		String strReturn = "";
		if (strLen > intLength) {
			strReturn = strValue.substring(0, intLength);
		} else {
			strReturn = strValue;
			for (int i = strLen; i < intLength; ++i)
				strReturn = tmp + strReturn;
		}
		return new String(strReturn);
	}

	public static String rpad(String strValue, int intLength, char tmp) {
		int strLen = strValue.getBytes().length;
		String strReturn = "";
		if (strLen > intLength) {
			strReturn = strValue.substring(0, intLength);
		} else {
			strReturn = strValue;
			for (int i = strLen; i < intLength; ++i)
				strReturn = strReturn + tmp;
		}
		return new String(strReturn);
	}

	public static void main(String[] args) throws Exception {
		
		String b = "05alGeUCGezTDReD";
		readCRT();
		System.out.println("pbKey==" + pbKey);
		System.err.println("公钥加密——私钥解密");
		String source = rpad(b, 128, ' ');
	    System.out.println("\r加密前文字：\r\n" + source);
	    byte[] data = source.getBytes();
	    System.out.println("转为16进制:" + AES.byte2hex(data));
	    byte[] encodedData = encryptByPublicKey(data, pbKey);
	    System.out.println("加密后的16进制文字:" + AES.byte2hex(encodedData));
		
	    
	}
}