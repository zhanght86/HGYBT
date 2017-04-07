package com.sinosoft.midplat.newabc.bat;

import java.io.UnsupportedEncodingException;

public class CodeUtil {

	/**
	 * 字节码转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] buf) {
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < buf.length; i++) { 
		String hex = Integer.toHexString(buf[i] & 0xFF); 
		if (hex.length() == 1) { 
		hex = '0' + hex; 
		} 
		sb.append(hex.toUpperCase()); 
		} 
		return sb.toString(); 

	}

	/**
	 * 16进制字符串转换成字节码
	 * 
	 * @param h
	 * @return
	 */
	public static byte[] hex2byte(String h) {
		byte[] ret = new byte[h.length() / 2];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = Integer.decode("#" + h.substring(2 * i, 2 * i + 2))
					.byteValue();
		}
		return ret;
	}
	
	/**
	 * 把字符串长度变为16的整数倍，字符串后加空格
	 * @param data
	 * @return
	 */
	public static String compStr(String data){
		try {	
			if(data == null || "".equals(data)){
				return null;
			}
			byte[] temp = data.getBytes("UTF-8");
			
			int len = temp.length;
			int yu = len%16;
			if(yu != 0){
				int queLen = 16-yu;
				for(int i = 0;i<queLen;i++){
					data += " ";
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public static void main(String[] args) {
		String temp = compStr("1234567889bjkgbyukvgkvjvgv                  fewr2rw     ewrfwrfq    324324fdwffsfewrfw2r42425r4gtrgerbgtbrtg34t54rrefrge4rt4gtrg  ");
		System.out.println(temp.length());
	}
}
