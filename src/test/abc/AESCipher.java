package test.abc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.SysInfo;

public class AESCipher {
	
	// 密文
	private InputStream cipherStream = null;

	// 明文
	private InputStream clearStream = null;

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
//		String mKeyPath = "d://aesABC.dat";
//		FileInputStream mKeyIs = new FileInputStream(mKeyPath);
		String mKeyPath = SysInfo.cHome + "key/";
		 cLogger.info("path:"+mKeyPath);
		 FileInputStream mKeyIs = new FileInputStream(mKeyPath +"abcKey.dat");
		byte[] mKeyHexBytes = new byte[128];
		mKeyIs.read(mKeyHexBytes);
		mKeyIs.close();
		cLogger.debug("密钥：" + new String(mKeyHexBytes).trim());

		cKey = new String(mKeyHexBytes).trim();
	}
	
	/**
	 * 获取密文的方法
	 * 
	 * @return
	 */
	public InputStream getCipherStream() {
		return this.cipherStream;
	}

	/**
	 * 获取明文的方法
	 * 
	 * @return
	 */
	public InputStream getClearStream() {
		return this.clearStream;
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
		byte[] raw = cKey.getBytes("ASCII");    
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"   
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度   
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); 
		byte[] encrypted = cipher.doFinal(content); 
		return encrypted;
	}

	
	/**
	 * 解密报文
	 * 
	 * @param pInputStream
	 * @param pHashBaseInfoBean
	 * @return
	 */
	public boolean decryption(InputStream pInputStream) {
		ByteArrayOutputStream ins = new ByteArrayOutputStream();
		int x = 0;
		try {
			
			cLogger.info("---------准备对报文解密------------");
			while ((x = pInputStream.read()) != -1) {
				ins.write(x);
			}
			byte[] bPack = ins.toByteArray();
			System.out.println(new String(bPack));
			bPack = CodeUtil.hex2byte(new String(bPack));
			bPack = decrypt(bPack, cKey.getBytes());
			bPack = new String(bPack,"UTF-8").getBytes();
			this.clearStream = new ByteArrayInputStream(bPack, 0, bPack.length);
			cLogger.info("---------报文解密成功------------");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info("---------报文解密失败------------");
			return false;
		}
	}
	
	/**
	 * 加密报文
	 * 
	 * @param pInputStream
	 * @param pHashBaseInfoBean
	 * @return
	 */
	public boolean encryption(InputStream pInputStream) {
		int c = 0;
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		try {
			cLogger.info("准备对报文加密");
			while ((c = pInputStream.read()) != -1) {
				bous.write(c);
			}
			String temp = bous.toString();
			
			byte[] temp_utf8 = temp.getBytes("UTF-8");
			
			//把报文补全长度为16的整数倍
			String temp1 = CodeUtil.compStr(new String(temp_utf8,"UTF-8"));
			byte[] bPack = temp1.getBytes("UTF-8");
			
			bPack = encrypt(bPack, cKey.getBytes());
			
			String desc = CodeUtil.byte2hex(bPack);
			cLogger.info("----密文-------"+desc);
			this.cipherStream = new ByteArrayInputStream(desc.getBytes(), 0, desc.length());
			cLogger.info("报文加密成功");
			return true;
		} catch (Exception e) {

			cLogger.info("报文加密失败");

			return false;
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
	public static byte[] encrypt(byte[] content, byte[] password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, key,iv);
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
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
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");    
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


	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, byte[] password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, key,iv);
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}
}
