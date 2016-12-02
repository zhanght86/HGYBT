package test.security;

import com.sinosoft.lis.encrypt.security.IDEA;
import java.io.PrintStream;

public final class LisIDEA
{
  final String mCipherKeyStr = "12dc427f09a81e293d43db3b2378491d";
  final int mStrLen = 8;

  public String encryptString(String plainStr)
  {
    if ((plainStr == null) || (plainStr.equals("")))
    {
      return "";
    }

    String tplainStr = "";
    int len = plainStr.length();
    if (len <= 8)
    {
      for (int i = 0; i < len; i++)
      {
        tplainStr = tplainStr + plainStr.charAt(i);
      }
      for (int i = 0; i < 8 - len; i++)
      {
        tplainStr = tplainStr + " ";
      }
    }
    else
    {
      for (int i = 0; i < 8; i++)
      {
        tplainStr = tplainStr + plainStr.charAt(i);
      }
    }

    String hexPlainStr = stringToHexString(tplainStr);
    byte[] key = fromString("12dc427f09a81e293d43db3b2378491d");
    byte[] plain = fromString(hexPlainStr);
    IDEA idea = new IDEA(key);
    byte[] encP = new byte[plain.length];

    idea.encrypt(plain, encP);

    String hexEncryptString = toString(encP);
    return hexEncryptString;
  }

  public String decryptString_pre(String encryptStr)
  {
    if ((encryptStr == null) || (encryptStr.equals("")))
    {
      return "";
    }

    String tencryptStr = "";
    int len = encryptStr.length();
    if (len <= 8)
    {
      for (int i = 0; i < len; i++)
      {
        tencryptStr = tencryptStr + encryptStr.charAt(i);
      }

      for (int i = 1; i < 8 - len; i++)
      {
        tencryptStr = tencryptStr + " ";
      }
    }
    else
    {
      for (int i = 0; i < 8; i++)
      {
        tencryptStr = tencryptStr + encryptStr.charAt(i);
      }
    }

    String hexEncryptStr = stringToHexString(tencryptStr);
    byte[] key = fromString("12dc427f09a81e293d43db3b2378491d");
    IDEA idea = new IDEA(key);
    byte[] encP = fromString(hexEncryptStr);
    byte[] decC = new byte[encP.length];
    idea.decrypt(encP, decC);
    String hexDecryptStr = toString(decC);
    return hexStringToString(hexDecryptStr);
  }

  public String decryptString(String encryptStr)
  {
    if ((encryptStr == null) || (encryptStr.equals("")))
    {
      return "";
    }

    String hexEncryptStr = encryptStr;
    byte[] key = fromString("12dc427f09a81e293d43db3b2378491d");
    IDEA idea = new IDEA(key);
    byte[] encP = fromString(hexEncryptStr);
    byte[] decC = new byte[encP.length];
    idea.decrypt(encP, decC);
    String hexDecryptStr = toString(decC);
    return hexStringToString(hexDecryptStr);
  }

  private static String stringToHexString(String srcString)
  {
    String resultString = "";
    int srcLen = srcString.length();
    for (int pos = 0; pos < srcLen; pos++)
    {
      byte b = (byte)srcString.charAt(pos);
      int hexValue = b & 0xF;
      resultString = resultString + hexToAscii(hexValue);
      hexValue = b >> 4 & 0xF;
      resultString = resultString + hexToAscii(hexValue);
    }

    return resultString;
  }

  private static String hexStringToString(String hexString)
  {
    String resultString = "";
    int hexLen = hexString.length();
    for (int pos = 0; pos < hexLen; pos += 2)
    {
      char c1 = hexString.charAt(pos);
      char c2 = hexString.charAt(pos + 1);
      int hexvalue1 = asciiToHex(c1);
      int hexvalue2 = asciiToHex(c2);
      char c = (char)(hexvalue1 | hexvalue2 << 4);
      resultString = resultString + c;
    }

    return resultString.trim();
  }

  private static byte[] fromString(String inHex)
  {
    int len = inHex.length();
    int pos = 0;
    byte[] buffer = new byte[(len + 1) / 2];
    if (len % 2 == 1)
    {
      buffer[0] = (byte)asciiToHex(inHex.charAt(0));
      pos = 1;
      len--;
    }

    for (int ptr = pos; len > 0; len -= 2)
    {
      buffer[(pos++)] = (byte)(
        asciiToHex(inHex.charAt(ptr++)) << 4 | 
        asciiToHex(inHex.charAt(ptr++)));
    }

    return buffer;
  }

  private static String toString(byte[] buffer)
  {
    StringBuffer returnBuffer = new StringBuffer();
    int pos = 0; for (int len = buffer.length; pos < len; pos++)
    {
      returnBuffer.append(hexToAscii(buffer[pos] >>> 4 & 0xF))
        .append(hexToAscii(buffer[pos] & 0xF));
    }
    return returnBuffer.toString();
  }

  private static int asciiToHex(char c)
  {
    if ((c >= 'a') && (c <= 'f'))
    {
      return c - 'a' + 10;
    }
    if ((c >= 'A') && (c <= 'F'))
    {
      return c - 'A' + 10;
    }
    if ((c >= '0') && (c <= '9'))
    {
      return c - '0';
    }
    throw new Error("ascii to hex failed");
  }

  private static char hexToAscii(int h)
  {
    if ((h >= 10) && (h <= 15))
    {
      return (char)(65 + (h - 10));
    }
    if ((h >= 0) && (h <= 9))
    {
      return (char)(48 + h);
    }
    throw new Error("hex to ascii failed");
  }

  public static void main(String[] argv)
  {
    String plainStr = "001";
    String ensryptStr = "3D5AA576F0DF6C61";
    LisIDEA tLisIdea = new LisIDEA();
    String plaintoencryptStr = tLisIdea.encryptString(plainStr);
    System.out.println("plaintoencryptStr = " + plaintoencryptStr);
    
    System.out.println("½âÃÜºóµÄ×Ö·û£º"+tLisIdea.decryptString(ensryptStr));
  }
}