package com.sinosoft.midplat.newabc.util;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.SysInfo;

/**
* RSA�ǶԳ��ͼ��ܵĹ�Կ��˽Կ
*/
public class RSA_old {
  private KeyPairGenerator kpg = null;
  private KeyPair kp = null;
  private PublicKey public_key = null;
  private PrivateKey private_key = null;

 
  /**
   * ���캯��
   * @param in ָ���ܳ׳��ȣ�ȡֵ��Χ��512��2048��
 * @throws Exception 
   */
  public RSA_old(String address) throws Exception {
//            kpg = KeyPairGenerator.getInstance("RSA"); //�������ܳ׶ԡ�������
//            kpg.initialize(in); //ָ���ܳ׳��ȣ�ȡֵ��Χ��512��2048��
//            kp = kpg.genKeyPair(); //���ɡ��ܳ׶ԡ������а�����һ�����׺�һ��˽�׵���Ϣ
//            public_key = kp.getPublic(); //��ù���
//            private_key = kp.getPrivate(); //���˽��
	        readCRT_publicKey();
            sun.misc.BASE64Encoder  b64 =  new sun.misc.BASE64Encoder();
            String pkStr = b64.encode(public_key.getEncoded());
//          String prStr = b64.encode(private_key.getEncoded());
            System.out.println("��Կlength:" + pkStr.length());
            System.out.println("��Կ����   :");
            System.out.println(pkStr);
            
            FileWriter  fw2 = new FileWriter(address + "/public_key.key"); 
            fw2.write(pkStr);
            fw2.close();        
     }


   /**
    *���ܵķ���
    */
   private  static String encrypt(String source) throws Exception{
    
	   /** ���ļ��еĹ�Կ������� */
	   FileReader  fr = new FileReader("E://public_key.key");
	   BufferedReader br=new BufferedReader(fr);//����BufferedReader���󣬲�ʵ����Ϊbr
	   String getPbKey = "";
	   while(true){
	       String aLine = br.readLine();
	       if(aLine==null)break;
	       getPbKey += aLine;
	   }
	   System.out.println( "myBuilderStr :  length: " +  getPbKey.length() +" "+getPbKey ); 
       BASE64Decoder   b64d = new  BASE64Decoder();
       byte [] keyByte =  b64d.decodeBuffer(getPbKey); 
       X509EncodedKeySpec  x509ek  = new X509EncodedKeySpec(keyByte);
       KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
       PublicKey  publicKey = keyFactory.generatePublic(x509ek);
      
       Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding"); 
       cipher.init(Cipher.ENCRYPT_MODE,publicKey); 
       byte[] sbt = source.getBytes();
       byte [] epByte = cipher.doFinal(sbt); 
       BASE64Encoder encoder = new BASE64Encoder();
       String epStr =  encoder.encode(epByte);
       return epStr;

   }
   
   /**
    *���ܵķ���
    */
   public static String decrypt(String cryptograph) throws Exception{ 
    
	   FileReader  fr = new FileReader("E://private_key.key");
	   BufferedReader br = new BufferedReader(fr);//����BufferedReader���󣬲�ʵ����Ϊbr
	   String getPvKey = "";
       while(true){
	        String aLine = br.readLine();
	        if(aLine==null)break;
	        getPvKey += aLine;
       }
       BASE64Decoder   b64d = new  BASE64Decoder();
       byte [] keyByte =  b64d.decodeBuffer(getPvKey); 
       PKCS8EncodedKeySpec  s8ek  = new PKCS8EncodedKeySpec(keyByte);
       KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
       PrivateKey  privateKey = keyFactory.generatePrivate(s8ek);
     
       
       /** �õ�Cipher��������ù�Կ���ܵ����ݽ���RSA���� */
       Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
       cipher.init(Cipher.DECRYPT_MODE, privateKey);
       BASE64Decoder decoder = new BASE64Decoder();
       byte[] b1 = decoder.decodeBuffer(cryptograph);
       /** ִ�н��ܲ��� */
       byte[] b = cipher.doFinal(b1);
       return new String(b);
   }

   /**
    * ��ȡ.crt֤���ļ��еĹ�Կ
    * @return 
    * @return
    * @throws Exception
    */
   public  void readCRT_publicKey() throws Exception{	
	    String publicKeyPath = SysInfo.cHome+"key/";
		String publicKey = new String(IOTrans.toBytes( new FileInputStream(publicKeyPath)));
		System.out.println("publicKey======="+publicKey);
	}
   
  public static void main(String[] args) {
     try { 
    	  new RSA_old(SysInfo.cHome+"key/52004_pub.cer");      //��Կ
//        System.out.println("");
//        String content = "681B28268ECF03D0206F148ACB71C1B5";
//        String getEptStr =  encrypt(content);
//        System.out.println("getEptStr:"+getEptStr);
//        String  drpStr = decrypt(getEptStr);
//        System.out.println("drpStr:"+drpStr);
    } catch (Exception ex) {
    	ex.printStackTrace();
    }
  }
  
   

}
