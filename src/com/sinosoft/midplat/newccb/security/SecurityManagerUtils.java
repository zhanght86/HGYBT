package com.sinosoft.midplat.newccb.security;

import cn.ccb.secapi.SecAPI;
import cn.ccb.secapi.SecException;
import java.nio.charset.Charset;
import org.springframework.beans.factory.InitializingBean;
import org.apache.commons.lang3.StringUtils;
public class SecurityManagerUtils
  implements InitializingBean
{
 // private static final Log log = LogFactory.getLog(SecurityManagerUtils.class);
  public static final String log001 = "服务{0}获取远程安全节点号为{1}";
  public static final String log002 = "动态服务查找开关为打开或者是未配置SeekManager";
  public static final String log003 = "{0}";
  public static final Charset CHARSET = getCharset("UTF-8");
  private static String secNodeId;
  //private static OutboundServiceRegistry outboundServiceRegistry;
  private static final String SEEK_TYPE = "remote";
  //private static SeekManager seekManager;
  //private static ILoadBalancer loadBalancer;
  static int seq = 0;

  public static SecurityPolicy getSecPolicy(String secNodeId, String rmtSecNodeId)
  {
    byte[] policyBytes = null;
    try {
    	System.out.println("secNodeId:"+secNodeId  +"  secNodeId.length:"+secNodeId.length());
    	System.out.println("rmtSecNodeId:"+rmtSecNodeId  +"  rmtSecNodeId.length:"+rmtSecNodeId.length());
    	System.out.println("开始获取安全策略......");
    	policyBytes = SecAPI.getSecPolicy(secNodeId, rmtSecNodeId);
    	System.out.println("结束获取安全策略......");
//    	policyBytes = com.sinosoft.midplat.newccb.security.SecAPI.getSecPolicy(secNodeId, rmtSecNodeId); 
    } catch (Throwable t) {
      handleException(t);
    }

    SecurityPolicy securityPolicy = new SecurityPolicy();
    if ((policyBytes != null) && (policyBytes.length != 0)) {
      securityPolicy.setEnc(isTrue(policyBytes[0]));
      securityPolicy.setMac(isTrue(policyBytes[1]));
      securityPolicy.setContext(isTrue(policyBytes[2]));
    }

    return securityPolicy;
  }

  public static byte[] mac(String secNodeId, String rmtSecNodeId, byte[] inData)
  {
    try {
      return SecAPI.mac(secNodeId, rmtSecNodeId, inData);
    } catch (Throwable t) {
      handleException(t);
    }
    return null;
  }

  public static void macVerify(String secNodeId, String rmtSecNodeId, byte[] inData, byte[] macData)
  {
    try {
      SecAPI.macVerify(secNodeId, rmtSecNodeId, inData, macData);
    } catch (Throwable t) {
      handleException(t);
    }
  }

  public static String initSecContext(String secNodeId, String rmtSecNodeId, String evtTraceId, String userId)
  {
    try {
      return SecAPI.initSecContext(secNodeId, rmtSecNodeId, evtTraceId, userId);
    }
    catch (Throwable t) {
      handleDrivingInvokeException(t);
    }
    return null;
  }

  public static String genSecContext(String secNodeId, String rmtSecNodeId, String evtTraceId, String oldContext)
  {
    try {
      return SecAPI.genSecContext(secNodeId, rmtSecNodeId, evtTraceId, oldContext);
    }
    catch (Throwable t) {
      handleException(t);
    }
    return null;
  }

  public static void checkSecContext(String secNodeId, String rmtSecNodeId, String evtTraceId, String context)
  {
    try {
      SecAPI.checkSecContext(secNodeId, rmtSecNodeId, evtTraceId, context);
    } catch (Throwable t) {
      handleException(t);
    }
  }

  public static void signVerify(String secNodeId, byte[] messageData, byte[] signBuf)
  {
    try {
      SecAPI.signVerify(secNodeId, messageData, signBuf);
    } catch (Throwable t) {
      handleException(t);
    }
  }
  
  public static byte[] pinDecrypt(String secNodeId, String rmtSecNodeId, byte[] cipherData)
  {
    try {
      return SecAPI.pinDecrypt(secNodeId, rmtSecNodeId, cipherData);
    } catch (Throwable t) {
      handleException(t);
    }
    return null;
  }
  
  public static byte[] genSign(String rmtSecNodeId, byte[] plainData)
  {
    try {
      return SecAPI.genSign(rmtSecNodeId, plainData);
    } catch (Throwable t) {
      handleException(t);
    }
    return null;
  }
  
  public static byte[] pkgEncrypt(String secNodeId, String rmtSecNodeId, byte[] plainData)
  {
    try {
      return SecAPI.pkgEncrypt(secNodeId, rmtSecNodeId, plainData);
    } catch (Throwable t) {
      handleException(t);
    }
    return null;
  }

  public static byte[] pkgDecrypt(String secNodeId, String rmtSecNodeId, byte[] cipherData)
  {
    try {
      return SecAPI.pkgDecrypt(secNodeId, rmtSecNodeId, cipherData);
    } catch (Throwable t) {
      handleException(t);
    }
    return null;
  }

  public static String generalGlobalSerialNo() {
	  return generalGlobalSerialNo(null);
  }
  public static String generalGlobalSerialNo(String hostNo)
  {

    if (StringUtils.isEmpty(hostNo))
      hostNo = "000";
    if (StringUtils.length(hostNo) > 3) {
      hostNo = hostNo.substring(0, 3);
    }
    StringBuffer sb = new StringBuffer();
    sb.append(StringUtils.leftPad(secNodeId, 6, '0'));
    sb.append(StringUtils.leftPad(hostNo, 3, '0'));
    String time = String.valueOf(System.currentTimeMillis() / 1000L);
    if (time.length() < 10) {
      sb.append(StringUtils.leftPad(time, 10, '0'));
    } else {
      int beginIndex = time.length() - 10;
      sb.append(time.substring(beginIndex, time.length()));
    }
    String tempSeq = StringUtils.leftPad(String.valueOf(seq), 6, '0');
    sb.append(StringUtils.substring(tempSeq, tempSeq.length()-3, tempSeq.length()));
    return sb.toString();
  }

  public static void handleException(Throwable t)
  {
    if ((t instanceof SecException)) {
      SecException se = (SecException)t;

      //SwapAreaUtils.setSecErrorCode(se.getMessage());
      throw new SecurityException("XSSF301500AB", se, new Object[] { se.getLocalizedMessage() });
    }

    //SwapAreaUtils.setSecErrorCode("XSSF301500AC");
    throw new SecurityException("XSSF301500AC", t, new Object[] { t.getLocalizedMessage() });
  }

  public static void handleDrivingInvokeException(Throwable t)
  {
    if ((t instanceof SecException)) {
      SecException se = (SecException)t;

      throw new SecurityException("XSSF301500AB", se, new Object[] { se.getLocalizedMessage() });
    }

    throw new SecurityException("XSSF301500AC", t, new Object[] { t.getLocalizedMessage() });
  }

  private static boolean isTrue(byte flag)
  {
    return 49 == flag;
  }

  public static String getSecNodeId() {
    return secNodeId;
  }

  public static void setSecNodeId(String secNodeId2) {
    secNodeId = secNodeId2;
  }

  public void afterPropertiesSet() throws Exception
  {
    
  }
  
  public static Charset getCharset(String charsetName)
  {
    if (StringUtils.isEmpty(charsetName)) {
      return null;
    }

    charsetName = charsetName.toUpperCase();
    Charset targetCharset = null;
      try {
        targetCharset = Charset.forName(charsetName);
      } catch (Exception e) {
        //log.error("log001", e, new Object[] { charsetName });
    	  e.printStackTrace();
      }

    return targetCharset;
  }
}