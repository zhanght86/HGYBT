package com.sinosoft.midplat.newabc.key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class AbcAES {

	/**
	 * 农行加密方法,密钥文件abcKey.dat,返回16进制字符串
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public static String Encrypt(String pStr) throws Exception {
		String tStr = fillWithB16(pStr);
		return byte2hex(Encrypt(tStr.getBytes("UTF-8")));
	}
	
	public static byte[] Encrypt(byte[] pClearBytes) throws Exception {
		pClearBytes = fillWithB16(pClearBytes);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, AbcKeyCache.newInstance().getKey(), iv);
		byte[] encryptBytes = cipher.doFinal(pClearBytes);
		return encryptBytes;
	}
	

	/**
	 * 农行解密方法,密钥文件abcKey.dat
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public static String Decrypt(String pSrc) throws Exception {
		String tStr = fillWithB16(pSrc);
		byte[] tEncryptBytes = hex2byte(tStr);
		return new String(Decrypt(tEncryptBytes), "UTF-8");
	}
	
	public static byte[] Decrypt(byte[] pEncryptBytes) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec(
					"ABCHINA..ANIHCBA".getBytes());
			cipher.init(Cipher.DECRYPT_MODE,
					AbcKeyCache.newInstance().getKey(), iv);
			byte[] tClearBytes = cipher.doFinal(pEncryptBytes);
			return fillWithB16(tClearBytes);
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 16进制字符串转换为字节数字
	 * 
	 * @param String
	 *            pHexStr
	 * @return byte[] tBytes
	 */
	public static byte[] hex2byte(String pHexStr) {

		if (pHexStr == null) {
			return null;
		}
		int l = pHexStr.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] tBytes = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			tBytes[i] = (byte) Integer.parseInt(
					pHexStr.substring(i * 2, i * 2 + 2), 16);
		}
		return tBytes;
	}

	/**
	 * 字节数字转换为16进制字符串
	 * 
	 * @param byte[] pBytes
	 * @return String tHexStr
	 */
	public static String byte2hex(byte[] pBytes) {
		String tHexStr = "";
		String tStr = "";
		for (int n = 0; n < pBytes.length; n++) {
			tStr = (java.lang.Integer.toHexString(pBytes[n] & 0XFF));
			if (tStr.length() == 1) {
				tHexStr = tHexStr + "0" + tStr;
			} else {
				tHexStr = tHexStr + tStr;
			}
		}
		return tHexStr.toUpperCase();
	}

	/**
	 * 字符串长度不为16的倍数补传进来的值
	 * 
	 * @param String strValue, char tmp
	 * @return String tHexStr
	 */
	public static String fillWithB16(String pStr)  throws Exception {
		return new String(fillWithB16(pStr.getBytes("UTF-8")),"UTF-8");
	}
	
	public static byte[] fillWithB16(byte[] pStr) {
		int strLen = pStr.length;
		int num = strLen % 16;
		int shortLen = 0;
		if (num != 0) {
			shortLen = 16 - num;
		}
		byte[] rBytes = new byte[strLen + shortLen];
		System.arraycopy(pStr, 0, rBytes, 0, pStr.length);
		if (shortLen == 0) {
			return pStr;
		} else {
			for (int i = strLen; i < strLen + shortLen; i++) {
				rBytes[i] = ' ';
			}
		}
		return rBytes;
	}

	public static void main(String[] args) throws Exception {
		
		

		String cSrc = "ABCHINA..ANIHC";
		System.out.println(new String(fillWithB16(cSrc.getBytes())));
		
		System.out.println("需要加密的字符串:"+cSrc);
		// 加密
		String enString = Encrypt(cSrc);
		System.out.println("加密后的字串是：" + enString);
		// 解密
		String DeString = Decrypt(enString);
		System.out.println("解密后的字串是：" + DeString);
		System.out.println("");

		String cDoc = "<ABCB2I><Header><TransDate>20140317</TransDate><TransTime>165745</TransTime><TransCode>1004</TransCode><SerialNo>710T00024683</SerialNo><CorpNo>1118</CorpNo><BranchNo>0312</BranchNo><ProvCode>09</ProvCode><Tlid>a292</Tlid></Header><App><Req><PayAmt>50000.00</PayAmt><PayAccount>9559940010100655643</PayAccount><PolicyNo></PolicyNo><ApplySerial>710T00024682</ApplySerial><AccName>孙新义</AccName><RiskCode>523</RiskCode></Req></App></ABCB2I>";
		System.out.println("需要加密的报文:"+cDoc);

		String enDoc = Encrypt(cDoc);
		System.out.println("加密后的报文:" + enDoc);
		String a2 = "A5970E7A96C083692B50DA943B79077EA2FD8C419C5C5D344E27FB7CDC9CBE871CAB1BF03C743F6DE81F8FF932A66BCC10B97D1906245E725658DB19959F3AD3E702E3557BBAF92B18A0D76444C0ACA72F834D2AD83EA5B194B65F5E43616B6FD02723827BA68594B9DEC7400C64059D1C0B85E4C9787240E11EFFEC355C53A02CC7A769B1F6C81B780E25086068FB56767C2C24523AB9DB75E6D9B5346CDF79731AE32E00A8F5488F3609E20ECAA956C8F7278A4BB65F76EA5004A9DCF58F0ECDF54B51F13345C21DE5A8B92280BA9A318CF7E501EB5F14FD9C3B1408F7A0D00B29B5F1823BBD5AAF1B1413C3738032EAEAE7A3B25967353E5C50C6BFDB7B356CEEC5CEE835CEFA62C94617A8988C89C419B4750E3F38E888E20AA130FFEE74C25AA07BC1CA502D369FA7630BA9FB4E2DB5B59B08BA7FDAD575F69A4879161F6FD49BF65C89C512DAB0C27E59AECF989B910645FB8643F1B1F99883C4C7F786";
		String atDoc = Decrypt(enDoc);
		System.out.println("解密后的报文:" + atDoc);
	}

}
