package com.sinosoft.midplat.boc.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.jcraft.jsch.jce.Random;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.gzbank.test.GZBankTest;

public class BocTest {
	private static  Logger cLogger = Logger.getLogger(GZBankTest.class);
//	public static void main(String[] args) {
//		String str="aaabbb";
//		//HmacMD5����ʵ��
//		//������Կ����
//		try {
//			KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacMD5");
//			SecretKey secretKey=keyGenerator.generateKey();
//			Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//			System.out.println("�㷨Ϊ:"+secretKey.getAlgorithm());
//			String key=new String(secretKey.getEncoded());
//			System.out.println("��ԿΪ:"+key);
//			mac.init(secretKey);
//			byte[] bs=mac.doFinal(str.getBytes());
//			for (byte b : bs) {
//				System.out.print(b+" ");
//			}
//			System.out.println("================================");
//			SecretKey secretKey1=new SecretKeySpec(key.getBytes(),"HmacMD5");
//			Mac mac1=Mac.getInstance("HmacMD5");
//			mac1.init(secretKey1);
//			byte[] bs1=mac.doFinal(str.getBytes());
//			for (byte b : bs1) {
//				System.out.print(b+" ");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
   public static void main(String[] args) {
	   //String ip="192.168.197.1";//����ip��ַ
	   String ip="10.2.0.31";//���Ի���ip��ַ
		int port=35009;
		String path="D:\\picchchenjw\\�찲\\����\\����\\�й����к͹�������\\�й�����\\��������1001.xml";
		//String path="D:\\picchchenjw\\�찲\\����\\����\\�й����к͹�������\\�й�����\\�ɷѳ���1002.xml";
		//String path="D:\\picchchenjw\\�찲\\����\\����\\�й����к͹�������\\�й�����\\��������1004.xml";
		//String path="D:\\picchchenjw\\�찲\\����\\����\\�й����к͹�������\\�й�����\\�˱����ڸ�������1007.xml";
		//String path="D:\\picchchenjw\\�찲\\����\\����\\�й����к͹�������\\�й�����\\�˱����ڸ���ȷ��1008.xml";
		try {
			FileInputStream input=new FileInputStream(path);
			byte[] bytes=IOTrans.toBytes(input);
			Socket socket=new Socket(ip,port);
			cLogger.info("�������������ģ�"+JdomUtil.toStringFmt(JdomUtil.build(bytes,"GBK")));
			cLogger.info("�����ĳ���Ϊ��"+bytes.length+"���ֽ�");
			String head="";
			String byteLength=bytes.length+"";
			int count=8-byteLength.length();
			if(byteLength.length()<8){
			    for (int i = 0; i <count; i++) {
			    	byteLength="0"+byteLength;
				}
			}
			head=byteLength;
			cLogger.info("��Ϣͷ����Ϊ:"+head.getBytes().length);
			long oldTimeMillis=System.currentTimeMillis();
			OutputStream out=socket.getOutputStream();
			out.write(head.getBytes());
			out.write(bytes);
			out.flush();
			socket.shutdownOutput();
			InputStream in=socket.getInputStream();
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			byte[] byteArray=new byte[1024*3];
			int size=0;
			while ((size=in.read(byteArray))!=-1) {
				byteArrayOutputStream.write(byteArray,0,size);
			}
			socket.shutdownInput();
			
			byte[] byteArrays=byteArrayOutputStream.toByteArray();
			String length=new String(Arrays.copyOfRange(byteArrays,0,8)).trim();
		    cLogger.info("---------------------------------------------------");
			cLogger.info("�����ֽ� ����Ϊ:"+length);
			long newTimeMillis=System.currentTimeMillis();
			cLogger.info("���׽��������ķѣ�"+(newTimeMillis-oldTimeMillis)/1000.0+"��,"+(newTimeMillis-oldTimeMillis)+"����");
			cLogger.info("���ظ��������б��ģ�"+JdomUtil.toStringFmt(JdomUtil.build(Arrays.copyOfRange(byteArrays,19,byteArrays.length))));
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
}
}
