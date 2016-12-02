package com.sinosoft.midplat.newabc.util;

import java.io.FileInputStream;
import java.io.PrintStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.SysInfo;

public class AES
{
  private static String sKey = "";

  public static String Encrypt(String sSrc) throws Exception {
	  readKeyFile();
    if (sKey == null) {
      System.out.print("Key为空null");
      return null;
    }

    if (sKey.length() != 16) {
      System.out.print("Key长度不是16位");
      return null;
    }
    System.out.println("密钥======" + sKey);
    byte[] raw = sKey.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
    IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());
    cipher.init(1, skeySpec, iv);

    byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

    return byte2hex(encrypted);
  }

  public static String Decrypt(String sSrc) throws Exception
  {
	  readKeyFile(); 
    try
    {
      if (sKey == null) {
        System.out.print("Key为空null");
        return null;
      }

      if (sKey.length() != 16) {
        System.out.print("Key长度不是16位");
        return null;
      }
      byte[] raw = sKey.getBytes();
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      IvParameterSpec iv = new IvParameterSpec("ABCHINA..ANIHCBA".getBytes());
      cipher.init(2, skeySpec, iv);
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
      System.out.println(ex.toString()); }
    return null;
  }

  public static byte[] hex2byte(String strhex)
  {
    if (strhex == null) {
      return null;
    }
    int l = strhex.length();
    if (l % 2 == 1) {
      return null;
    }
    byte[] b = new byte[l / 2];
    for (int i = 0; i != l / 2; ++i) {
      b[i] = (byte)Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
    }
    return b;
  }

  public static String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; ++n) {
      stmp = Integer.toHexString(b[n] & 0xFF);
      if (stmp.length() == 1)
        hs = hs + "0" + stmp;
      else {
        hs = hs + stmp;
      }
    }
    return hs.toUpperCase();
  }

  public static String rpadEncrypt(String strValue, char tmp)
  {
    int strLen = 0;
    try {
      strLen = strValue.getBytes("UTF-8").length;
      System.out.println("strLen:" + strLen);
    } catch (Exception e) {
      e.printStackTrace();
    }
    int num = strLen % 16;
    int shortLen = 0;
    if (num != 0) {
      shortLen = 16 - num;
    }

    String strReturn = "";

    strReturn = strValue;
    if (shortLen == 0) {
      return strReturn;
    }
    for (int i = strLen; i < strLen + shortLen; ++i) {
      strReturn = strReturn + tmp;
    }

    System.out.println("buqizhihou de changdu==" + new String(strReturn));

    return strReturn; }

  public static String getSKey() {
    return sKey;
  }

  public static void setSKey(String key) {
    sKey = key;
  }

	private static void  readKeyFile(){
		try {
			String cPath="key/newABCKey.dat";
			String mFilePath = SysInfo.cHome + cPath;
			System.out.println("密钥路径:"+mFilePath);
			FileInputStream mKeyIs = new FileInputStream(mFilePath);
			byte[] mKeyHexBytes = new byte[16];
			IOTrans.readFull(mKeyHexBytes, mKeyIs);
			mKeyIs.close();
			System.out.println("密钥：" + new String(mKeyHexBytes));
			sKey=new String(mKeyHexBytes);
		} catch(Exception ex) {
			System.out.println("读取密钥文件有误！");
			ex.printStackTrace();
		}
		 
	}
  
  public static void main(String[] args) throws Exception
  {
    String cKey = "10260d1b4385194a";

    String cSrc = "ABCHINA..ANIHCBA";
    cSrc = rpadEncrypt(cSrc, ' ');
    System.out.println(cSrc);

    String enString = Encrypt(cSrc);
    System.out.println("加密后的字串是：" + enString);

    String DeString = Decrypt(enString);
    System.out.println("解密后的字串是：" + DeString);
    String a = "<?xml version='1.0' encoding='UTF-8'?><ABCB2I><Header><RetCode>000000</RetCode><RetMsg>交易成功</RetMsg><SerialNo></SerialNo><InsuSerial>20140120710100008886</InsuSerial><TransTime>092551</TransTime><TransDate>20140120</TransDate><BankCode>04</BankCode><CorpNo>1108</CorpNo><TransCode>1014</TransCode></Header><App><Ret><RiskCode>510</RiskCode><PolicyNo>210917010069</PolicyNo><PolicyStatus>03</PolicyStatus><ApplyDate>20140120</ApplyDate><ValidDate>20140120</ValidDate><BusinType>01</BusinType><BQStatus>S</BQStatus></Ret></App></ABCB2I>";
    System.out.println("length1:" + a.getBytes("UTF-8").length);
    a = rpadEncrypt(a, ' ');
    System.out.println("length2:" + a.getBytes("UTF-8").length);
    System.out.println("X1.000000378".substring(3, 12));
    String a1 = "<ABCB2I><Header><TransDate>20140225</TransDate><TransTime>163309</TransTime><TransCode>1004</TransCode><SerialNo>710R00003102</SerialNo><!-- 新保交易流水号码 --><CorpNo>1108</CorpNo><BranchNo>0102</BranchNo><ProvCode>11</ProvCode></Header><App><Req><PayAmt>5000.000000</PayAmt><PayAccount>11240100462107855</PayAccount><PolicyNo/><RiskCode>523</RiskCode><ApplySerial>71TT00003103</ApplySerial><!-- 试算交易流水号码 --><AccName>郑新芳</AccName></Req></App></ABCB2I>";
    System.out.println("jiami____" + a1.getBytes("UTF-8").length);
    a1 = rpadEncrypt(a1, ' ');
    String at = Encrypt(a1);
    System.out.println("jiami____" + at);
    String a2 = "A5970E7A96C083692B50DA943B79077EA2FD8C419C5C5D344E27FB7CDC9CBE87D42D7EA0294F3EFE757F9870432932F8A285BB83E57AC420B258F376D81633076F0FE58285378E25906638A1411618371C24ACA77F58C7AFD38F76DF450E7A5089AAC4BBACE4572DD511A814C7AC87204DBC7C3D2C4AC44182F7A1004CE7525EACB63BD8C41750560A8A4E85701C9A3FD309F5E3748E5233AA1F02B4772073D3C834D3E58893F7CB0E629328EDF6E8C2B8FD0E2F555B88AD31B8B3D8E6DA559DBCFA8279516D310567D3825B6DAF4D640C8EDB0F43A0EA1D50A1743C231D9B06504B0B2C282BA80392D707CB783938E0D4013D2471448682847F82881D3C3D79C8CAD26CD2A9BA6DA0D5ACA78A3B8DA2B45E6328C083E10F29758C337E069660C2CA6361426ECCA29022BE97E4BD59D94BA88EB1083090114B6C6C74B8A0AA350153B65A49AA28F1F765C398D2BF7D203C3CF4230E164B372EE038EC23003937837D781723E3E9B3D2F27BEEA31078AC82B83440C7398F2C08002E7582832BC389E262F1ADD865F42CA8ED4E9D7C2746B55BBE0E6AB79AB71A4188020431C948";
    String at1 = Decrypt(a2);
    System.out.println("tttt==" + at1);
  }
}