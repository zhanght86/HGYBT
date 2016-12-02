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
* RSA非对称型加密的公钥和私钥
*/
public class RSA_old {
  private KeyPairGenerator kpg = null;
  private KeyPair kp = null;
  private PublicKey public_key = null;
  private PrivateKey private_key = null;

 
  /**
   * 构造函数
   * @param in 指定密匙长度（取值范围：512～2048）
 * @throws Exception 
   */
  public RSA_old(String address) throws Exception {
//            kpg = KeyPairGenerator.getInstance("RSA"); //创建‘密匙对’生成器
//            kpg.initialize(in); //指定密匙长度（取值范围：512～2048）
//            kp = kpg.genKeyPair(); //生成‘密匙对’，其中包含着一个公匙和一个私匙的信息
//            public_key = kp.getPublic(); //获得公匙
//            private_key = kp.getPrivate(); //获得私匙
	        readCRT_publicKey();
            sun.misc.BASE64Encoder  b64 =  new sun.misc.BASE64Encoder();
            String pkStr = b64.encode(public_key.getEncoded());
//          String prStr = b64.encode(private_key.getEncoded());
            System.out.println("公钥length:" + pkStr.length());
            System.out.println("公钥内容   :");
            System.out.println(pkStr);
            
            FileWriter  fw2 = new FileWriter(address + "/public_key.key"); 
            fw2.write(pkStr);
            fw2.close();        
     }


   /**
    *加密的方法
    */
   private  static String encrypt(String source) throws Exception{
    
	   /** 将文件中的公钥对象读出 */
	   FileReader  fr = new FileReader("E://public_key.key");
	   BufferedReader br=new BufferedReader(fr);//建立BufferedReader对象，并实例化为br
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
    *解密的方法
    */
   public static String decrypt(String cryptograph) throws Exception{ 
    
	   FileReader  fr = new FileReader("E://private_key.key");
	   BufferedReader br = new BufferedReader(fr);//建立BufferedReader对象，并实例化为br
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
     
       
       /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
       Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
       cipher.init(Cipher.DECRYPT_MODE, privateKey);
       BASE64Decoder decoder = new BASE64Decoder();
       byte[] b1 = decoder.decodeBuffer(cryptograph);
       /** 执行解密操作 */
       byte[] b = cipher.doFinal(b1);
       return new String(b);
   }

   /**
    * 读取.crt证书文件中的公钥
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
    	  new RSA_old(SysInfo.cHome+"key/52004_pub.cer");      //公钥
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
