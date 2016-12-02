package com.sinosoft.lis.encrypt;

import java.util.Random;

/***
 * 利用md5算法加密
 * @version 2006/09/09
 *
 */
public class WebPrintAcitveXEncode {
    public WebPrintAcitveXEncode() {
    }

    public final static String getMD5String(String arg0)
            throws java.security.NoSuchAlgorithmException {
        java.security.MessageDigest md;
        md = java.security.MessageDigest.getInstance("MD5");
        md.update(arg0.getBytes());
        byte[] byteDigest = md.digest();
        String strMD5 = String.valueOf(bytesToHex(byteDigest));
//        String rnd = String.valueOf(com.sinosoft.lis.pubfun.PubFun.getIntRnd(8));
        String rnd = String.valueOf(getIntRnd(8));
        String result=strMD5.substring(0,11) + rnd + strMD5.substring(11);
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(getMD5String("wellhi"));

        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    public static String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(byteToHex(data[i]).toUpperCase());
        }
        return (buf.toString());
    }

    public static String byteToHex(byte data) {
        StringBuffer buf = new StringBuffer();
        buf.append(toHexChar((data >>> 4) & 0x0F));
        buf.append(toHexChar(data & 0x0F));
        return buf.toString();
    }

    public static char toHexChar(int i) {
        if ((0 <= i) && (i <= 9)) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + (i - 10));
        }
    }

    /**
    * 获取指定位数的随机整数
    * @param len int：随机数的位数
    * @return int 随机整数
    * created by wellhi 2006-09-19
    */
   public static int getIntRnd(int len)
   {
       Random rd = new Random();
       int rnd = 0;
       do
       {
           //休眠100ms防止取到重复的随机数
           try
           {
               Thread.sleep(100);
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
           rnd = (int) (rd.nextDouble() * (Math.pow(10, len)));
           // System.out.println(Math.pow(10,(len-1)));
           // System.out.println("rnd************" + rnd);
       }
       while (rnd < (Math.pow(10, (len - 1))));
       return rnd;
   }

}
