package com.sinosoft.midplat.common;

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
import java.util.Random;

import javax.crypto.Cipher;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.SysInfo;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class RSA {
	/** *//**
     * �����㷨RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    
    /** *//**
     * ǩ���㷨
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** *//**
     * ��ȡ��Կ��key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    
    /** *//**
     * ��ȡ˽Կ��key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    
    /** *//**
     * RSA���������Ĵ�С
     */
    private static final int MAX_ENCRYPT_BLOCK = 128;
    
    /** *//**
     * RSA���������Ĵ�С
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    
    /**
     * ֤���ļ��еĹ�Կ
     */
    private static PublicKey pbKey;

    /** *//**
     * <p>
     * ������Կ��(��Կ��˽Կ)
     * </p>
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("10260d1b4385194a".getBytes()); // ��ʼ�����������

        keyPairGen.initialize(512, secrand);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
    
    /** *//**
     * <p>
     * ��˽Կ����Ϣ��������ǩ��
     * </p>
     * 
     * @param data �Ѽ�������
     * @param privateKey ˽Կ(BASE64����)
     * 
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /** *//**
     * <p>
     * У������ǩ��
     * </p>
     * 
     * @param data �Ѽ�������
     * @param publicKey ��Կ(BASE64����)
     * @param sign ����ǩ��
     * 
     * @return
     * @throws Exception
     * 
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /** *//**
     * <P>
     * ˽Կ����
     * </p>
     * 
     * @param encryptedData �Ѽ�������
     * @param privateKey ˽Կ(BASE64����)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // �����ݷֶν���
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** *//**
     * <p>
     * ��Կ����
     * </p>
     * 
     * @param encryptedData �Ѽ�������
     * @param publicKey ��Կ(BASE64����)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // �����ݷֶν���
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** *//**
     * <p>
     * ��Կ����
     * </p>
     * 
     * @param data Դ����
     * @param publicKey ��Կ(BASE64����)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey)
            throws Exception {
//        byte[] keyBytes = Base64Utils.decode(publicKey);//ȡ��
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // �����ݼ���
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        System.err.println("inputLen:" + inputLen);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        
        int i = 0;
        // �����ݷֶμ���
//        while (inputLen - offSet > 0) {
//            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
//                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
//            } else {
//                cache = cipher.doFinal(data, offSet, inputLen - offSet);
//            }
//            out.write(cache, 0, cache.length);
//            i++;
//            offSet = i * MAX_ENCRYPT_BLOCK;
//        }
//        byte[] encryptedData = out.toByteArray();
        byte[] encryptedData = cipher.doFinal(data);
//        out.close();
        return encryptedData;
    }

    /** *//**
     * <p>
     * ˽Կ����
     * </p>
     * 
     * @param data Դ����
     * @param privateKey ˽Կ(BASE64����)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // �����ݷֶμ���
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** *//**
     * <p>
     * ��ȡ˽Կ
     * </p>
     * 
     * @param keyMap ��Կ��
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encode(key.getEncoded());
    }

    /** *//**
     * <p>
     * ��ȡ��Կ
     * </p>
     * 
     * @param keyMap ��Կ��
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encode(key.getEncoded());
    }
    
    /**
     * ��ȡ.crt֤���ļ��еĹ�Կ
     * @return 
     * @return
     * @throws Exception
     */
    public static void readCRT() throws Exception{	
		CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
		String keyPath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("KeyPath");    
		String mFilePath = keyPath+"key/cacert.crt";
//		String mFilePath = SysInfo.cHome+"key/cacert.crt";
		FileInputStream bais = new FileInputStream(mFilePath);
		X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
		pbKey = cert.getPublicKey();
		
	}
    
    /**
     * ��������Ҫ�õ���RSA���ܵ�����Կ
     * @return
     */
    public static String getAESKey(String key){
		String vvkey = "";
		try {
			readCRT();
//			key = PgMidplatUtil.rpad(key, 128, ' ');
			byte[] encodedData = encryptByPublicKey(key.getBytes(), pbKey);
			vvkey = AES.byte2hex(encodedData);
		} catch (Exception e) {
			System.out.println("��ȡ֤���쳣!");
			e.printStackTrace();
		}// ��ȡ��Կ��
		return vvkey;
    }
    public static void main(String args[]) throws Exception{
    	readCRT();
    	System.out.println("pbKey=="+pbKey);
    	System.err.println("��Կ���ܡ���˽Կ����");
        String source = "1111111111111111                                                                                                                ";
        String sourc1 = "10260d1b4385194a                                                                                                                ";
        System.out.println("\r����ǰ���֣�\r\n" + source);
        byte[] data = source.getBytes();
        System.out.println("תΪ16����:" + AES.byte2hex(data));
        byte[] encodedData = encryptByPublicKey(data, pbKey);
        System.out.println("���ܺ��16��������:" + AES.byte2hex(encodedData));
        System.out.println("���ܺ����֣�\r\n" + new String(encodedData));
    	
    	String at=getAESKey("10260d1b4385194a");
    	System.out.println("__DD__"+at);
    }
}
