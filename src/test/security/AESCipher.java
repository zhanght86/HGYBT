package test.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class AESCipher {

	private String cKey = null;

	private Logger cLogger = Logger.getLogger(getClass());

	public AESCipher(int pPort) throws IOException {
		/**
		 * 不可直接使用 InputStream mKeyIs = getClass().getResourceAsStream(pPort +
		 * "/" + pPort + ".dat");
		 * 因为getResourceAsStream会调用ClassLoader，将密钥文件加载为ServletContext，
		 * 如果服务器的自动加载设置为true，每次做密钥更换交易都会导致服务重启！ 此外，getResourceAsStream使用服务器缓存，
		 * 更新密钥后不重启服务getResourceAsStream获取的值不会改变。
		 */
		String mKeyPath = "D:/workspace/zhongrong/src/test/security/abcKey.dat";
		FileInputStream mKeyIs = new FileInputStream(mKeyPath);
//		 String mKeyPath = getClass().getResource(pPort + "/").getPath();
//		 FileInputStream mKeyIs = new FileInputStream(mKeyPath + pPort + ".dat");
		byte[] mKeyHexBytes = new byte[128];
		mKeyIs.read(mKeyHexBytes);
		mKeyIs.close();
		cLogger.debug("密钥：" + new String(mKeyHexBytes));

		cKey = new String(mKeyHexBytes).trim();
	}

	/**
	 * AES-128 CBC 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public byte[] aES128cbcEnCrypt(byte[] content) throws Exception{
		if (cKey == null) { 
			System.out.print("Key为空null"); 
			return null; 
		} 
        // 判断Key是否为16位 
        if (cKey.length() != 16) { 
        	System.out.print("Key长度不是16位"); 
			return null; 
		} 
		byte[] raw = cKey.getBytes(); 
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"   
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度   
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); 
		byte[] encrypted = cipher.doFinal(content); 
		return encrypted;
	}

	/**
	 * AES-128 CBC 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public byte[] aES128cbcDeCrypt(byte[] content) {
		try { 
			// 判断Key是否正确    
			if (cKey == null) {     
				System.out.print("Key为空null");     
				return null;    
			}    
			// 判断Key是否为16位    
			if (cKey.length() != 16) { 
				System.out.print("Key长度不是16位");     
				return null;    
			}    
			byte[] raw = cKey.getBytes("ASCII");    
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");    
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");    
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());    
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);    
			byte[] encrypted1 = content;
			try {     
				byte[] original = cipher.doFinal(encrypted1);     
				return original;    
			} catch (Exception e) {     
				System.out.println(e.toString());     
				return null;    
			}   
		} catch (Exception ex) {    
			System.out.println(ex.toString());    
			return null;   
		}
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public byte[] encrypt(byte[] content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(cKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content;
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("报文加密异常...");
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public byte[] decrypt(byte[] content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(cKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密

		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("报文解密异常...");
		}
		return null;
	}

}
