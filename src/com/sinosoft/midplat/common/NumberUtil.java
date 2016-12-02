package com.sinosoft.midplat.common;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class NumberUtil {
	private final static Logger cLogger = Logger.getLogger(NumberUtil.class);
	
	public static String byteToHex(byte[] pByte) {
		StringBuilder mOutStrBuilder = new StringBuilder();
		for (int i = 0; i < pByte.length; i++) {
			String tTmpStr = Integer.toHexString(pByte[i] & 0XFF);
			if (tTmpStr.length() == 1) {
				mOutStrBuilder.append('0');
			}
			
			mOutStrBuilder.append(tTmpStr);
		}
		
		return mOutStrBuilder.toString().toUpperCase();
	}
	
	public static String fenToYuan(String pFen) {
		if (null==pFen || "".equals(pFen)) {
			return pFen;
		}
		
		return new DecimalFormat("0.00").format(
				Long.parseLong(pFen)/100.0D);
	}
	
	public static String fenToYuan0(String pFen) {
		if (null==pFen || "".equals(pFen)) {
			return "0.00";
		}
		
		return new DecimalFormat("0.00").format(
				Long.parseLong(pFen)/100.0D);
	}
	
	public static String fenToYuan(long pFen) {
		return new DecimalFormat("0.00").format(pFen/100.0);
	}
	
	public static long yuanToFen(String pYuan) {
		if (null==pYuan || "".equals(pYuan)) {
			return 0;
		}
		
		return Math.round(Double.parseDouble(pYuan)*100);
	}
	
	public static long yuanToFen(double pYuan) {
		return Math.round(pYuan*100);
	}
	/**
	 * 将指定元 -#0.00。
	 */
	
	public static String yuanToDouble(String pYuan) {
		if (null==pYuan || "".equals(pYuan)) {
			return "0.00";
		}

		long pFen= Math.round(Double.parseDouble(pYuan)*100);
		return new DecimalFormat("0.00").format(pFen/100.0);
	}
	
	/**
	 * 将指定数字转换成指定长度字符串，不足位左补'0'。
	 */
	public static String fillWith0(int pNumber, int pLength) {
		return fillStrWith0(String.valueOf(pNumber), pLength);
	}
	
	/**
	 * 将指定数字转换成指定长度字符串，不足位右补空格(' ')。
	 */
	public static String fillWith_(int pNumber, int pLength) {
		return fillWith_(pNumber, pLength, false);
	}
	
	/**
	 * 将指定数字转换成指定长度字符串，不足位补空格(' ')，填充方向由pLeftAdd指定。
	 * @param pNumber : 源数字
	 * @param pLength : 要扩充到的长度
	 * @param pLeftAdd : 填充方向标示。true-左填充; false-右填充
	 */
	public static String fillWith_(int pNumber, int pLength, boolean pLeftAdd) {
		return fillStrWith_(String.valueOf(pNumber), pLength, pLeftAdd);
	}
	

	
	/**
	 * 将指定字符串扩充到指定字节长度，GBK编码(一个汉字两个字节长度)，不足位右补空格(' ')。
	 * @param pSrcStr : 源字符串
	 * @param pLength : 要扩充到的长度
	 */
	public static String fillStrWith_(String pSrcStr, int pLength) {
		return fillStrWith_(pSrcStr, pLength, false);
	}
	
	/**
	 * 将指定字符串扩充到指定字节长度，GBK编码(一个汉字两个字节长度)，不足位补空格(' ')，填充方向由pLeftAdd指定。
	 * @param pSrcStr : 源字符串
	 * @param pLength : 要扩充到的长度
	 * @param pLeftAdd : 填充方向标示。true-左填充; false-右填充
	 */
	public static String fillStrWith_(String pSrcStr, int pLength, boolean pLeftAdd) {
		return fillStr(pSrcStr, pLength, ' ', pLeftAdd);
	}
	
	/**
	 * 将指定字符串扩充到指定字节长度，GBK编码(一个汉字两个字节长度)，不足位左补'0'。
	 * @param pSrcStr : 源字符串
	 * @param pLength : 要扩充到的长度
	 */
	public static String fillStrWith0(String pSrcStr, int pLength) {
		return fillStrWith0(pSrcStr, pLength, true);
	}
	
	/**
	 * 将指定字符串扩充到指定字节长度，GBK编码(一个汉字两个字节长度)，不足位补'0'，填充方向由pLeftAdd指定。
	 * @param pSrcStr : 源字符串
	 * @param pLength : 要扩充到的长度
	 * @param pLeftAdd : 填充方向标示。true-左填充; false-右填充
	 */
	public static String fillStrWith0(String pSrcStr, int pLength, boolean pLeftAdd) {
		return fillStr(pSrcStr, pLength, '0', pLeftAdd);
	}
	
	/**
	 * 将指定字符串用指定字符扩充到指定字节长度，GBK编码(一个汉字两个字节长度)。
	 * @param pSrcStr : 源字符串
	 * @param pLength : 要扩充到的长度
	 * @param pFillChar : 填充字符
	 * @param pLeftAdd : 填充方向标示。true-左填充; false-右填充
	 */
	public static String fillStr(String pSrcStr, int pLength, char pFillChar, boolean pLeftAdd) {
		return fillStr(pSrcStr, pLength, pFillChar, pLeftAdd, "GBK");
	}
	
	/**
	 * 将指定字符串用指定字符扩充到指定字节长度。
	 * @param pSrcStr : 源字符串
	 * @param pLength : 要扩充到的长度
	 * @param pFillChar : 填充字符
	 * @param pLeftAdd : 填充方向标示。true-左填充; false-右填充
	 * @param pCharset : 解码字符集
	 */
	public static String fillStr(String pSrcStr, int pLength, char pFillChar, boolean pLeftAdd, String pCharset) {
		if (null == pSrcStr) {
			return null;
		}
		
		int mSrcBytesLen = 0;
		try {
			mSrcBytesLen = pSrcStr.getBytes(pCharset).length;
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		
		StringBuilder mStrBuilder = new StringBuilder();
		if (pLeftAdd) {
			for (int i = mSrcBytesLen; i < pLength; i++) {
				mStrBuilder.append(pFillChar);
			}
			mStrBuilder.append(pSrcStr);
		} else {
			mStrBuilder.append(pSrcStr);
			for (int i = mSrcBytesLen; i < pLength; i++) {
				mStrBuilder.append(pFillChar);
			}
		}
		
		return mStrBuilder.toString();
	}
	
	/**
	 * 将字符串按照GBK截取，返回该字节长度下最大串。
	 * @param pSrcStr : 源字符串
	 * @param pByteLen : 截取后的最大字节长度
	 */
	public static String cutStrByGBK(String pSrcStr, int pByteLen) {
		return cutStrByByte(pSrcStr, pByteLen, "GBK");
	}
	
	/**
	 * 将字符串按照UTF-8截取，返回该字节长度下最大串。
	 * @param pSrcStr : 源字符串
	 * @param pByteLen : 截取后的最大字节长度
	 */
	public static String cutStrByUTF8(String pSrcStr, int pByteLen) {
		return cutStrByByte(pSrcStr, pByteLen, "UTF-8");
	}
	
	/**
	 * 将字符串按照指定字符集截取，返回该字节长度下最大串。
	 * @param pSrcStr : 源字符串
	 * @param pByteLen : 截取后的最大字节长度
	 * @param pCharset : 字符集
	 */
	public static String cutStrByByte(String pSrcStr, int pByteLen, String pCharset) {
		if (pSrcStr.length() <= pByteLen/3) {	//提升性能：字符串编码、解码属cup密集型操作，如果pSrcStr远小于pByteLen，直接返回
			return pSrcStr;
		}
		
		byte[] mBytes = null;
		try {
			mBytes = pSrcStr.getBytes(pCharset);
		} catch (UnsupportedEncodingException ex) {
			cLogger.error(pCharset+"字符集不支持！", ex);
			return pSrcStr;
		}
		if (mBytes.length <= pByteLen) {
			return pSrcStr;
		}
		
		String mOutStr = null;
		try {
			mOutStr = new String(mBytes, 0, pByteLen, pCharset);
		} catch (UnsupportedEncodingException ex) {}
//		for (; pZnLen > 0; pZnLen--) {
//			mOutStr = new String(mBytes, 0, pByteLen--);
//			cLogger.debug(mOutStr);
//			if (pSrcStr.startsWith(mOutStr)) {
//				break;
//			}
//		}
		
		return mOutStr;
	}
	
    /**
     * 格式化字符
     * @param sIn String
     * @return String
     */
    private static String formatStr(String sIn) {
        int n = sIn.length();
        String sOut = sIn;
//        int i = n % 4;

        for (int k = 1; k <= 12 - n; k++) {
            sOut = "0" + sOut;
        }
        return sOut;
    }
    
    /**
     * 获取阿拉伯数字和中文数字的对应关系
     * @param value String
     * @return String
     */
    private static String getNum(String value) {
        String sNum = "";
        Integer I = new Integer(value);
        int iValue = I.intValue();
        switch (iValue) {
        case 0:
            sNum = "零";
            break;
        case 1:
            sNum = "壹";
            break;
        case 2:
            sNum = "贰";
            break;
        case 3:
            sNum = "叁";
            break;
        case 4:
            sNum = "肆";
            break;
        case 5:
            sNum = "伍";
            break;
        case 6:
            sNum = "陆";
            break;
        case 7:
            sNum = "柒";
            break;
        case 8:
            sNum = "捌";
            break;
        case 9:
            sNum = "玖";
            break;
        }
        return sNum;
    }

    
    /**
     * 添加仟、佰、拾等单位信息
     * @param strUnit String
     * @param digit String
     * @return String
     */
    private static String getChnM(String strUnit, String digit) {
        String sMoney = "";
        boolean flag = false;

        if (strUnit.equals("0000")) {
            sMoney += "0";
            return sMoney;
        }
        if (!strUnit.substring(0, 1).equals("0")) {
            sMoney += getNum(strUnit.substring(0, 1)) + "仟";
        } else {
            sMoney += "0";
            flag = true;
        }
        if (!strUnit.substring(1, 2).equals("0")) {
            sMoney += getNum(strUnit.substring(1, 2)) + "佰";
            flag = false;
        } else {
            if (flag == false) {
                sMoney += "0";
                flag = true;
            }
        }
        if (!strUnit.substring(2, 3).equals("0")) {
            sMoney += getNum(strUnit.substring(2, 3)) + "拾";
            flag = false;
        } else {
            if (flag == false) {
                sMoney += "0";
                flag = true;
            }
        }
        if (!strUnit.substring(3, 4).equals("0")) {
            sMoney += getNum(strUnit.substring(3, 4));
        } else {
            if (flag == false) {
                sMoney += "0";
                flag = true;
            }
        }

        if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0")) {
            sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim() +
                     "0";
        } else {
            sMoney += digit.trim();
        }
        return sMoney;
    }

    /**
     * 得到money的角分信息
     * @param sIn String
     * @return String
     */
    private static String getDotM(String sIn) {
        String sMoney = "";
        if (!sIn.substring(0, 1).equals("0")) {
            sMoney += getNum(sIn.substring(0, 1)) + "角";
        } else {
            sMoney += "0";
        }
        if (!sIn.substring(1, 2).equals("0")) {
            sMoney += getNum(sIn.substring(1, 2)) + "分";
        } else {
            sMoney += "0";
        }

        return sMoney;
    }

    /**
     * 把数字金额转换为中文大写金额 author: HST
     * @param money 数字金额(double)
     * @return 中文大写金额(String)
     */
    public static String getChnMoney(String strmoney) {
    	if (strmoney.equals("") || strmoney == null) {
    		strmoney = "0.00";
		}
		double money = Double.parseDouble(strmoney);
		
        String ChnMoney = "";
        String s0 = "";

        // 在原来版本的程序中，getChnMoney(585.30)得到的数据是585.29。

        if (money == 0.0) {
            ChnMoney = "零元整";
            return ChnMoney;
        }

        if (money < 0) {
            s0 = "负";
            money = money * ( -1);
        }

        String sMoney = new DecimalFormat("0").format(money * 100);

        int nLen = sMoney.length();
        String sInteger;
        String sDot;
        if (nLen < 2) {
            //add by JL at 2004-9-14
            sInteger = "";
            if (nLen == 1) {
                sDot = "0" + sMoney.substring(nLen - 1, nLen);
            } else {
                sDot = "0";
            }
        } else {
            sInteger = sMoney.substring(0, nLen - 2);
            sDot = sMoney.substring(nLen - 2, nLen);
        }

        String sFormatStr = formatStr(sInteger);

        String s1 = getChnM(sFormatStr.substring(0, 4), "亿");

        String s2 = getChnM(sFormatStr.substring(4, 8), "万");

        String s3 = getChnM(sFormatStr.substring(8, 12), "");

        String s4 = getDotM(sDot);

        if (s1.length() > 0 && s1.substring(0, 1).equals("0")) {
            s1 = s1.substring(1,
                              s1.length());
        }
        if (s1.length() > 0 &&
            s1.substring(s1.length() - 1, s1.length()).equals("0")
            && s2.length() > 0 && s2.substring(0, 1).equals("0")) {
            s1 = s1.substring(0, s1.length() - 1);
        }
        if (s2.length() > 0 &&
            s2.substring(s2.length() - 1, s2.length()).equals("0")
            && s3.length() > 0 && s3.substring(0, 1).equals("0")) {
            s2 = s2.substring(0, s2.length() - 1);
        }
        if (s4.equals("00")) {
            s4 = "";
            if (s3.length() > 0 &&
                s3.substring(s3.length() - 1, s3.length()).equals("0")) {
                s3 = s3.substring(0, s3.length() - 1);
            }
        }
        if (s3.length() > 0 &&
            s3.substring(s3.length() - 1, s3.length()).equals("0")
            && s4.length() > 0 && s4.substring(0, 1).equals("0")) {
            s3 = s3.substring(0, s3.length() - 1);
        }
        if (s4.length() > 0 &&
            s4.substring(s4.length() - 1, s4.length()).equals("0")) {
            s4 = s4.substring(0, s4.length() - 1);
        }
        if (s3.equals("0")) {
            s3 = "";
            s4 = "0" + s4;
        }

        ChnMoney = s0 + s1 + s2 + s3 + "元" + s4;
        if (ChnMoney.substring(0, 1).equals("0")) {
            ChnMoney = ChnMoney.substring(1,
                                          ChnMoney.length());
        }
        for (int i = 0; i < ChnMoney.length(); i++) {
            if (ChnMoney.substring(i, i + 1).equals("0")) {
                ChnMoney = ChnMoney.substring(0, i) + "零" +
                           ChnMoney.substring(i + 1, ChnMoney.length());
            }
        }

        if (sDot.substring(1, 2).equals("0")) {
            ChnMoney += "整";
        }

        return ChnMoney;
    }
    
    /**
	 * 将指定字符串用指定字符扩充到指定字节长度。
	 * @param pSrcStr : 源字符串
	 * @param pLength : 要扩充到的长度
	 * @param pFillChar : 填充字符
	 * @param pLeftAdd : 填充方向标示。true-左填充; false-右填充
	 */
	public static String fillStrAxaCash(String pSrcStr, int pLength,String cashFlag ) {
		char pFillChar = ' ';
		String pCharset = "GBK";
		boolean pLeftAdd = false;
		
		if(cashFlag == null || "".equals(cashFlag)){
			pSrcStr = "";
		}
		
		if (null == pSrcStr) {
			return null;
		}
		
		int mSrcBytesLen = 0;
		try {
			mSrcBytesLen = pSrcStr.getBytes(pCharset).length;
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		
		StringBuilder mStrBuilder = new StringBuilder();
		if (pLeftAdd) {
			for (int i = mSrcBytesLen; i < pLength; i++) {
				mStrBuilder.append(pFillChar);
			}
			mStrBuilder.append(pSrcStr);
		} else {
			mStrBuilder.append(pSrcStr);
			for (int i = mSrcBytesLen; i < pLength; i++) {
				mStrBuilder.append(pFillChar);
			}
		}
		
		return mStrBuilder.toString();
	}
	
	/**
     * 截取小数点后两位，进行四舍五入，五算入 author: ZHJ
     * @param money 数字金额(double)
     * @return (String)
     */
    public static double ret2Double4 (double money){
    	double retMoney = 0.00;
    	BigDecimal tAddBigDecimal = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP);
    	retMoney = tAddBigDecimal.doubleValue();
    	return retMoney;
    } 
    
    /**
     * 截取小数点后两位，不进行四舍五入 author: ZHJ
     * @param money 数字金额(double)
     * @return (String)
     */
    public static double ret2Double (double money){

    	DecimalFormat formater = new DecimalFormat();

    	 formater.setMaximumFractionDigits(2);
    	 formater.setGroupingSize(0);
    	 formater.setRoundingMode(RoundingMode.FLOOR);
    	return Double.valueOf(formater.format(money));
    } 
    
    
	public static void main(String[] args) {
		System.out.println("程序开始...");
		
//		double mTestNum = 1235656.2;
//		
//		DecimalFormat mDecimalFormat = (DecimalFormat) DecimalFormat.getNumberInstance();
//		mDecimalFormat.setMaximumFractionDigits(2);
//		System.out.println(
//				mDecimalFormat.format(mTestNum));
//		
/*		String s ="13457800.23";

		 String a=NumberUtil.getChnMoney(s);
		System.out.println("a:"+a);

		System.out.println("成功结束！");*/
		System.out.println(NumberUtil.fillStrWith_("aaaaa", 10)+"b");
	}
}
