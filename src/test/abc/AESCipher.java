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
	
	// ����
	private InputStream cipherStream = null;

	// ����
	private InputStream clearStream = null;

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
//		String mKeyPath = "d://aesABC.dat";
//		FileInputStream mKeyIs = new FileInputStream(mKeyPath);
		String mKeyPath = SysInfo.cHome + "key/";
		 cLogger.info("path:"+mKeyPath);
		 FileInputStream mKeyIs = new FileInputStream(mKeyPath +"abcKey.dat");
		byte[] mKeyHexBytes = new byte[128];
		mKeyIs.read(mKeyHexBytes);
		mKeyIs.close();
		cLogger.debug("��Կ��" + new String(mKeyHexBytes).trim());

		cKey = new String(mKeyHexBytes).trim();
	}
	
	/**
	 * ��ȡ���ĵķ���
	 * 
	 * @return
	 */
	public InputStream getCipherStream() {
		return this.cipherStream;
	}

	/**
	 * ��ȡ���ĵķ���
	 * 
	 * @return
	 */
	public InputStream getClearStream() {
		return this.clearStream;
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
		byte[] raw = cKey.getBytes("ASCII");    
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "�㷨/ģʽ/���뷽ʽ"   
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��   
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); 
		byte[] encrypted = cipher.doFinal(content); 
		return encrypted;
	}

	
	/**
	 * ���ܱ���
	 * 
	 * @param pInputStream
	 * @param pHashBaseInfoBean
	 * @return
	 */
	public boolean decryption(InputStream pInputStream) {
		ByteArrayOutputStream ins = new ByteArrayOutputStream();
		int x = 0;
		try {
			
			cLogger.info("---------׼���Ա��Ľ���------------");
			while ((x = pInputStream.read()) != -1) {
				ins.write(x);
			}
			byte[] bPack = ins.toByteArray();
			System.out.println(new String(bPack));
			bPack = CodeUtil.hex2byte(new String(bPack));
			bPack = decrypt(bPack, cKey.getBytes());
			bPack = new String(bPack,"UTF-8").getBytes();
			this.clearStream = new ByteArrayInputStream(bPack, 0, bPack.length);
			cLogger.info("---------���Ľ��ܳɹ�------------");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info("---------���Ľ���ʧ��------------");
			return false;
		}
	}
	
	/**
	 * ���ܱ���
	 * 
	 * @param pInputStream
	 * @param pHashBaseInfoBean
	 * @return
	 */
	public boolean encryption(InputStream pInputStream) {
		int c = 0;
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		try {
			cLogger.info("׼���Ա��ļ���");
			while ((c = pInputStream.read()) != -1) {
				bous.write(c);
			}
			String temp = bous.toString();
			
			byte[] temp_utf8 = temp.getBytes("UTF-8");
			
			//�ѱ��Ĳ�ȫ����Ϊ16��������
			String temp1 = CodeUtil.compStr(new String(temp_utf8,"UTF-8"));
			byte[] bPack = temp1.getBytes("UTF-8");
			
			bPack = encrypt(bPack, cKey.getBytes());
			
			String desc = CodeUtil.byte2hex(bPack);
			cLogger.info("----����-------"+desc);
			this.cipherStream = new ByteArrayInputStream(desc.getBytes(), 0, desc.length());
			cLogger.info("���ļ��ܳɹ�");
			return true;
		} catch (Exception e) {

			cLogger.info("���ļ���ʧ��");

			return false;
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
	public static byte[] encrypt(byte[] content, byte[] password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());//ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��
			cipher.init(Cipher.ENCRYPT_MODE, key,iv);
			byte[] result = cipher.doFinal(content);
			return result; // ����
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


	/**
	 * ����
	 * 
	 * @param content
	 *            ����������
	 * @param password
	 *            ������Կ
	 * @return
	 */
	public static byte[] decrypt(byte[] content, byte[] password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, key,iv);
			byte[] result = cipher.doFinal(content);
			return result; // ����
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
