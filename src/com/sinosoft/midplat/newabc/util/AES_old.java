package com.sinosoft.midplat.newabc.util;

import java.io.FileInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.SysInfo;

public class AES_old {
	private static String sKey = null;
//	private static String sKey = "1111111111111111";
	// ����     
	public static String Encrypt(String sSrc) throws Exception {    
		readKeyFile();
		if (sKey == null) {             
			System.out.print("KeyΪ��null");
			return null;         
		}         
		// �ж�Key�Ƿ�Ϊ16λ         
		if (sKey.length() != 16) {             
			System.out.print("Key���Ȳ���16λ");             
			return null;         
		}         
		byte[] raw = sKey.getBytes();   
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
		return byte2hex(encrypted);     
	}       
	
	// ����     
	public static String Decrypt(String sSrc) throws Exception {
		readKeyFile();
		try {             
			// �ж�Key�Ƿ���ȷ             
			if (sKey == null) {
				System.out.print("KeyΪ��null");
				return null;             
			}             
			// �ж�Key�Ƿ�Ϊ16λ             
			if (sKey.length() != 16) {
				System.out.print("Key���Ȳ���16λ");
				return null;             
			}             
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);             
			byte[] encrypted1 = hex2byte(sSrc);             
			try {                 
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "UTF-8");
				return originalString;             
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;             
			}         
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;         
		}     
	}       
	
	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {             
			return null;         
		}         
		int l = strhex.length();
		if (l % 2 == 1) {             
			return null;         
		}         
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}         
		return b;     
	}       
	
	public static String byte2hex(byte[] b) {
		String hs = "";         
		String stmp = "";         
		for (int n = 0; n < b.length; n++) { 
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); 
			if (stmp.length() == 1) {                
				hs = hs + "0" + stmp;  
			} else {   
				hs = hs + stmp;  
			}         
		}        
		return hs.toUpperCase();     
	}     
	
	public static String rpadEncrypt(String strValue, char tmp) {

		int strLen = 0;
		try {
			strLen = strValue.getBytes("UTF-8").length;
			System.out.println("strLen:" + strLen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int num = strLen%16;
		int shortLen = 0;
		if (num != 0) {
			shortLen = 16 - num;
		}
		
		String strReturn = "";
		
		strReturn = strValue;
		if (shortLen == 0) {
			return strReturn;
		} else {
			for (int i = strLen; i < strLen + shortLen; i++) {
				strReturn = strReturn + tmp;
			}	
		}
		System.out.println("buqizhihou de changdu=="+new String(strReturn));
//		return new String(strReturn);
		return strReturn;
	}
	
	private static void  readKeyFile(){
		try {
			String cPath="key/newABCKey.dat";
			String mFilePath = SysInfo.cHome + cPath;
			System.out.println("��Կ·��:"+mFilePath);
			FileInputStream mKeyIs = new FileInputStream(mFilePath);
			byte[] mKeyHexBytes = new byte[16];
			IOTrans.readFull(mKeyHexBytes, mKeyIs);
			mKeyIs.close();
			System.out.println("��Կ��" + new String(mKeyHexBytes));
			sKey=new String(mKeyHexBytes);
		} catch(Exception ex) {
			System.out.println("��ȡ��Կ�ļ�����");
			ex.printStackTrace();
		}
		 
	}
	public static void main(String[] args) throws Exception {
		/*          * �����õ�Key ������26����ĸ��������ɣ���ò�Ҫ�ñ����ַ�����Ȼ�����������ô�þ������˿��������          */
		String cKey = "10260d1b4385194a";         
		// ��Ҫ���ܵ��ִ�   
		String cSrc = "ABCHINA..ANIHCBA";   
		cSrc = rpadEncrypt(cSrc, ' ');
		System.out.println(cSrc);         
		// ����         
		String enString = Encrypt(cSrc);
		System.out.println("���ܺ���ִ��ǣ�" + enString); 
		// ����          
		String DeString = Decrypt(enString);  
		System.out.println("���ܺ���ִ��ǣ�" + DeString);  
		String a = "X1.000000542                                                             <?xml version='1.0' encoding='UTF-8'?><ABCB2I><Header><RetCode>000000</RetCode><RetMsg>���׳ɹ�</RetMsg><SerialNo></SerialNo><InsuSerial>20140120710100008886</InsuSerial><TransTime>092551</TransTime><TransDate>20140120</TransDate><BankCode>04</BankCode><CorpNo>1108</CorpNo><TransCode>1014</TransCode></Header><App><Ret><RiskCode>510</RiskCode><PolicyNo>210917010069</PolicyNo><PolicyStatus>03</PolicyStatus><ApplyDate>20140120</ApplyDate><ValidDate>20140120</ValidDate><BusinType>01</BusinType><BQStatus>S</BQStatus></Ret></App></ABCB2I>";
		System.out.println("length1:" + a.getBytes("UTF-8").length);
		a = rpadEncrypt(a, ' ');
		System.out.println("length2:" + a.getBytes("UTF-8").length);
		
	} 
}
