package com.sinosoft.midplat.newabc.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.SysInfo;

public class Getkey_file {
	/**
	 * @param args
	 */
	protected static final Logger cLogger = Logger.getLogger(Getkey_file.class);
	private static final String  newkey="key/newABCKey.dat";
	private static final String  oldkey="key/oldABCKey.dat";
	public static String getKeyFile(String flag){
		String mFilePath = "";
		if(flag.equals("newkey")){
			mFilePath = SysInfo.cHome + newkey;
		}else if(flag.equals("oldkey")){
			mFilePath = SysInfo.cHome + oldkey;
		}
		System.out.println("mFilePath=="+mFilePath);
		File keyfile = new File(mFilePath);
		if (!keyfile.exists()) {
            System.out.println("查找密钥文件失败!");
            return "";
		}
		byte[] bEncryptKey = new byte[16];
		FileInputStream fis;
		try {
			fis = new FileInputStream(keyfile);
			fis.read(bEncryptKey);
			fis.close();
		} catch (FileNotFoundException e) {
			cLogger.info("没有找到文件："+mFilePath);
			e.printStackTrace();
		} catch (IOException e) {
			cLogger.info("读取文件发生异常："+mFilePath);
			e.printStackTrace();
		}
		
		String key =new String(bEncryptKey);
		cLogger.info("读取出的旧密钥为：" +key);
		return key;
	}
	public static void setKeyFile(byte[] pBytes,String flag){
		String mFilePath = "";
		if(flag.equals("newkey")){
			mFilePath = SysInfo.cHome + newkey;
		}else if(flag.equals("oldkey")){
			mFilePath = SysInfo.cHome + oldkey;
		}
		FileOutputStream tFos;
		try {
			tFos = new FileOutputStream(mFilePath);
			tFos.write(pBytes);                                                        
			tFos.close();     
		} catch (FileNotFoundException e) {
			cLogger.info("没有找到文件："+mFilePath);
			e.printStackTrace();
		} catch (IOException e) {
			cLogger.info("读取文件发生异常："+mFilePath);
			e.printStackTrace();
		}
		 
		
	}
	/**
	 * 对比文件更改的时间
	 * @return
	 */
	public static String filechangetime(){
		String mFilePath = SysInfo.cHome + newkey;
		File file = new File(mFilePath);
		long time =file.lastModified();
		long t_time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String filedate=sdf.format(date);
		String today = sdf.format(new Date());
		System.out.println("+__xxx__+:"+sdf.format(date));
		System.out.println("+____+:"+time);
		System.out.println("+__*__+:"+System.currentTimeMillis());
		return "";
		
	}
	/**
	 * 生成密钥
	 * @return
	 */
	public static String getKey(){
    	String str="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//    	String str="0123456789abcdefghijklmnopqrstuvwxyz";
    	char[] code=str.toCharArray();
    	Random a = new Random();
    	StringBuffer strbuf=new StringBuffer();
    	for(int i=0;i<16;i++){
    		int t = a.nextInt(61);
    		strbuf.append(code[t]);
    	}
    	System.out.println("生成的密钥是："+strbuf);
    	return strbuf.toString();
    }
	/**
	 * 保存密钥
	 * @param random_key 当时生成的key
	 */
	public static void saveKey(String random_key){
		String old_key=getKeyFile("newkey");
		setKeyFile(old_key.getBytes(),"oldkey");
		setKeyFile(random_key.getBytes(),"newkey");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		setOldKey("abcccdsfds".getBytes());
		System.out.println(getKey());
		String key=getKeyFile("newkey");
		setKeyFile(key.getBytes(),"oldkey");
		String now="1111111111111111";
		setKeyFile(now.getBytes(),"newkey");
		
		
		filechangetime();

	}

}
