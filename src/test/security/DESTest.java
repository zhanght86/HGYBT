package test.security;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DESTest {
	private final SecretKeySpec cKey;
	
	public DESTest(String pKeyStr) throws IOException {
		byte[] mKeyHexBytes = pKeyStr.getBytes();
		
		byte[] mKeyBytes = new byte[8];
		for (int i = 0; i < mKeyBytes.length; i++) {
			mKeyBytes[i] = (byte) Integer.parseInt(new String(mKeyHexBytes, i*2, 2), 16);
		}
		
		cKey = new SecretKeySpec(mKeyBytes, "DES");
	}
	
	public DESTest(byte[] pKeyBytes) throws IOException {
		cKey = new SecretKeySpec(pKeyBytes, "DES");
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
		Cipher mCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		mCipher.init(Cipher.DECRYPT_MODE, cKey);
		
		return mCipher.doFinal(pBytes);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");
		
		String mInFilePath = "D:/YBT_SAVE_XML/ZHH/非实时/ENY050012013051303.txt";
		String mOutFilePath = "D:/YBT_SAVE_XML/ZHH/非实时/ENY050012013051303.txt.des";
		
		//这样可以解开
//		String mKeyStr = "9d322658";
//		DESTest mDESTest = new DESTest(mKeyStr.getBytes());
		
		//我们的传统程序，解不开
//		String mKeyStr = "3DCBCD3762FEA21F";
//		String mKeyStr = "DCBCD201308211545";
		String mKeyStr = "affff00010008003";
		DESTest mDESTest = new DESTest(mKeyStr);
		
		byte[] mInBytes = IsToBytes(
				new FileInputStream(mInFilePath));
		
		//解密
//		byte[] mOutBytes = mDESTest.decode(mInBytes);
		
		//加密
		byte[] mOutBytes = mDESTest.encode(mInBytes);
		
		OutputStream mFos = new FileOutputStream(mOutFilePath);
		mFos.write(mOutBytes);
		mFos.close();
		
		System.out.println("成功结束！");
	}
	
	/**
	 * InputStream --> byte[]
	 */
	public static byte[] IsToBytes(InputStream pIns) {
		ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			byte[] tBytes = new byte[8*1024];
			for (int tReadSize; -1 != (tReadSize=pIns.read(tBytes)); ) {
				mByteArrayOutputStream.write(tBytes, 0, tReadSize);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				pIns.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return mByteArrayOutputStream.toByteArray();
	}
}
