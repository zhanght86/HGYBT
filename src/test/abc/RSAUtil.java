package test.abc;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * RSA�㷨��ʵ�����ݵļ��ܽ��ܡ�
 * 
 * @author ShaoJiang
 * 
 */
public class RSAUtil {

	/**
	 * RSA���������Ĵ�С
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
	public static final String KEY_ALGORITHM = "RSA";
	private static Cipher cipher;
	/** */
	/**
	 * RSA���������Ĵ�С
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	static {
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �õ���Կ
	 * 
	 * @param key
	 *            ��Կ�ַ���������base64���룩
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * �õ�˽Կ
	 * 
	 * @param key
	 *            ��Կ�ַ���������base64���룩
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * ʹ�ù�Կ�����Ľ��м��ܣ�����16���Ʊ�����ַ���
	 * 
	 * @param publicKey
	 * @param plainText
	 * @return
	 */
	public static String encrypt(PublicKey publicKey, String plainText) {
		try {
			// Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encryptedData = cipher.doFinal(plainText.getBytes());
			return CodeUtil.byte2hex(encryptedData);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ʹ��˽Կ���������Ľ��н���
	 * 
	 * @param privateKey
	 * @param enStr
	 * @return
	 */
	public static String decrypt(PrivateKey privateKey, String enStr) {
		try {
			byte[] miData = CodeUtil.hex2byte(enStr);
			// byte[] miData = (new BASE64Decoder()).decodeBuffer(new
			// String(en));
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			int inputLen = miData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// �����ݷֶν���
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(miData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(miData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return new String(decryptedData);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ʹ�ù�Կ�����Ľ��н���
	 * 
	 * @param PublicKey
	 *            ��Կ
	 * @param enStr
	 *            ����
	 * @return
	 */
	public static String decrypt(PublicKey publicKey, String enStr) {
		try {
			byte[] miData = CodeUtil.hex2byte(enStr);
			// byte[] miData = (new BASE64Decoder()).decodeBuffer(new
			// String(en));
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			int inputLen = miData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// �����ݷֶν���
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(miData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(miData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return new String(decryptedData);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��X509֤�� ����AES��Կ ���ص���16���Ʊ���
	 * 
	 * @param crtPath
	 *            x509֤��·��
	 * @param key
	 *            ��Կ
	 * @return
	 */
	public static String encryptAESKey(String crtPath, String key) {
		try {
			key = getForMatStr(key, 128);
			FileInputStream inStream = new FileInputStream(crtPath);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf
					.generateCertificate(inStream);
			cert.checkValidity(new Date()); // ��֤֤���Ƿ����
			return encrypt(cert.getPublicKey(), key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateExpiredException e) {
			System.out.println("֤�����֤����Ч���ѹ���");
			e.printStackTrace();
		} catch (CertificateNotYetValidException e) {
			System.out.println("֤�����֤����Ч��δ��Ч");
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ��X509֤�� ����AES��Կ
	 * 
	 * @param crtPath
	 *            x509֤��·��
	 * @param key
	 *            ��Կ
	 * @return
	 */
	public static String decryptAESKey(String crtPath, String key) {
		try {
			FileInputStream inStream = new FileInputStream(crtPath);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf
					.generateCertificate(inStream);
			cert.checkValidity(new Date()); // ��֤֤���Ƿ����
			return decrypt(cert.getPublicKey(), key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateExpiredException e) {
			System.out.println("֤�����֤����Ч���ѹ���");
			e.printStackTrace();
		} catch (CertificateNotYetValidException e) {
			System.out.println("֤�����֤����Ч��δ��Ч");
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return "";
	}

	
	
	
	

	/**
	 * ������Կ��
	 * 
	 * @param filePath
	 *            ������Կ��·��
	 * @return
	 */
	public static Map<String, String> generateKeyPair(String filePath) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// ��Կλ��
			keyPairGen.initialize(1024);
			// ��Կ��
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// ��Կ
			PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// ˽Կ
			PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			BASE64Encoder base64 = new BASE64Encoder();
			// �õ���Կ�ַ���
			String publicKeyString = base64.encode((publicKey.getEncoded()));
			// �õ�˽Կ�ַ���
			String privateKeyString = base64.encode(privateKey.getEncoded());
			// ����Կ��д�뵽�ļ�
			FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
			FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
			BufferedWriter pubbw = new BufferedWriter(pubfw);
			BufferedWriter pribw = new BufferedWriter(prifw);
			pubbw.write(publicKeyString);
			pribw.write(privateKeyString);
			pubbw.flush();
			pubbw.close();
			pubfw.close();
			pribw.flush();
			pribw.close();
			prifw.close();
			// �����ɵ���Կ�Է���
			Map<String, String> map = new HashMap<String, String>();
			map.put("publicKey", publicKeyString);
			map.put("privateKey", privateKeyString);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���� [ab ] [abc ] �󲹿ո�
	 * 
	 * @param tStr
	 *            �ַ���
	 * @param len
	 *            �̶�����
	 * @return
	 */
	public static String getForMatStr(String tStr, int len) {
		if (tStr == null || tStr.equals(""))
			return "";
		if (tStr.getBytes().length >= len)
			return tStr;
		int tStrLen = tStr.getBytes().length;
		for (int i = 0; i < len - tStrLen; i++)
			tStr = tStr + " ";
		return tStr;
	}

	public static void main(String[] args) throws FileNotFoundException,
			CertificateException {
		try {
			String path = "d://cacert.crt";
			String str = "mlz0hnrpenqogfhj";
			System.out.println(encryptAESKey(path, str));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}