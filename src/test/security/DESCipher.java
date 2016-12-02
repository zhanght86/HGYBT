package test.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class DESCipher {
	private SecretKeySpec cKey = null;
	
	private Logger cLogger = Logger.getLogger(getClass());
	
	public DESCipher(int pPort) throws IOException {
		InputStream mKeyIs = getClass().getResourceAsStream(pPort + ".dat");
		cLogger.info("密钥文件名："+pPort+".dat");
		byte[] mKeyHexBytes = new byte[16];
		mKeyIs.read(mKeyHexBytes);
		mKeyIs.close();
		cLogger.debug("密钥：" + new String(mKeyHexBytes));
		
		byte[] mKeyBytes = new byte[8];
		for (int i = 0; i < mKeyBytes.length; i++) {
			mKeyBytes[i] = (byte) Integer.parseInt(new String(mKeyHexBytes, i*2, 2), 16);
		}
		
		cKey = new SecretKeySpec(mKeyBytes, "DES");
	}

	/**
	 * 加密
	 */
	public InputStream encode(InputStream pIns) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.ENCRYPT_MODE, cKey);
		
		return new CipherInputStream(pIns, mCipher);
	}
	
	/**
	 * 解密
	 */
	public OutputStream decode(OutputStream pOuts) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, cKey);
		
		return new CipherOutputStream(pOuts, mCipher);
	}
	
	/**
	 * 加密
	 */
	public byte[] encode(byte[] pBytes) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.ENCRYPT_MODE, cKey);
		
		return mCipher.doFinal(pBytes);
	}
	
	/**
	 * 解密
	 */
	public byte[] decode(byte[] pBytes) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, cKey);
		
		return mCipher.doFinal(pBytes);
	}
}
