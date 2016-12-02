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
		 * ����ֱ��ʹ�� InputStream mKeyIs = getClass().getResourceAsStream(pPort +
		 * "/" + pPort + ".dat");
		 * ��ΪgetResourceAsStream�����ClassLoader������Կ�ļ�����ΪServletContext��
		 * ������������Զ���������Ϊtrue��ÿ������Կ�������׶��ᵼ�·��������� ���⣬getResourceAsStreamʹ�÷��������棬
		 * ������Կ����������getResourceAsStream��ȡ��ֵ����ı䡣
		 */
		String mKeyPath = "D:/workspace/zhongrong/src/test/security/abcKey.dat";
		FileInputStream mKeyIs = new FileInputStream(mKeyPath);
//		 String mKeyPath = getClass().getResource(pPort + "/").getPath();
//		 FileInputStream mKeyIs = new FileInputStream(mKeyPath + pPort + ".dat");
		byte[] mKeyHexBytes = new byte[128];
		mKeyIs.read(mKeyHexBytes);
		mKeyIs.close();
		cLogger.debug("��Կ��" + new String(mKeyHexBytes));

		cKey = new String(mKeyHexBytes).trim();
	}

	/**
	 * AES-128 CBC ����
	 * 
	 * @param content
	 *            ��Ҫ���ܵ�����
	 * @param password
	 *            ��������
	 * @return
	 */
	public byte[] aES128cbcEnCrypt(byte[] content) throws Exception{
		if (cKey == null) { 
			System.out.print("KeyΪ��null"); 
			return null; 
		} 
        // �ж�Key�Ƿ�Ϊ16λ 
        if (cKey.length() != 16) { 
        	System.out.print("Key���Ȳ���16λ"); 
			return null; 
		} 
		byte[] raw = cKey.getBytes(); 
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "�㷨/ģʽ/���뷽ʽ"   
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��   
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); 
		byte[] encrypted = cipher.doFinal(content); 
		return encrypted;
	}

	/**
	 * AES-128 CBC ����
	 * 
	 * @param content
	 *            ����������
	 * @param password
	 *            ������Կ
	 * @return
	 */
	public byte[] aES128cbcDeCrypt(byte[] content) {
		try { 
			// �ж�Key�Ƿ���ȷ    
			if (cKey == null) {     
				System.out.print("KeyΪ��null");     
				return null;    
			}    
			// �ж�Key�Ƿ�Ϊ16λ    
			if (cKey.length() != 16) { 
				System.out.print("Key���Ȳ���16λ");     
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
	 * ����
	 * 
	 * @param content
	 *            ��Ҫ���ܵ�����
	 * @param password
	 *            ��������
	 * @return
	 */
	public byte[] encrypt(byte[] content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(cKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// ����������
			byte[] byteContent = content;
			cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��
			byte[] result = cipher.doFinal(byteContent);
			return result; // ����
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("���ļ����쳣...");
		}
		return null;
	}

	/**
	 * ����
	 * 
	 * @param content
	 *            ����������
	 * @param password
	 *            ������Կ
	 * @return
	 */
	public byte[] decrypt(byte[] content) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(cKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// ����������
			cipher.init(Cipher.DECRYPT_MODE, key);// ��ʼ��
			byte[] result = cipher.doFinal(content);
			return result; // ����

		} catch (Exception e) {
			e.printStackTrace();
			cLogger.debug("���Ľ����쳣...");
		}
		return null;
	}

}
