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
	 * ��ָ��Ԫ -#0.00��
	 */
	
	public static String yuanToDouble(String pYuan) {
		if (null==pYuan || "".equals(pYuan)) {
			return "0.00";
		}

		long pFen= Math.round(Double.parseDouble(pYuan)*100);
		return new DecimalFormat("0.00").format(pFen/100.0);
	}
	
	/**
	 * ��ָ������ת����ָ�������ַ���������λ��'0'��
	 */
	public static String fillWith0(int pNumber, int pLength) {
		return fillStrWith0(String.valueOf(pNumber), pLength);
	}
	
	/**
	 * ��ָ������ת����ָ�������ַ���������λ�Ҳ��ո�(' ')��
	 */
	public static String fillWith_(int pNumber, int pLength) {
		return fillWith_(pNumber, pLength, false);
	}
	
	/**
	 * ��ָ������ת����ָ�������ַ���������λ���ո�(' ')����䷽����pLeftAddָ����
	 * @param pNumber : Դ����
	 * @param pLength : Ҫ���䵽�ĳ���
	 * @param pLeftAdd : ��䷽���ʾ��true-�����; false-�����
	 */
	public static String fillWith_(int pNumber, int pLength, boolean pLeftAdd) {
		return fillStrWith_(String.valueOf(pNumber), pLength, pLeftAdd);
	}
	

	
	/**
	 * ��ָ���ַ������䵽ָ���ֽڳ��ȣ�GBK����(һ�����������ֽڳ���)������λ�Ҳ��ո�(' ')��
	 * @param pSrcStr : Դ�ַ���
	 * @param pLength : Ҫ���䵽�ĳ���
	 */
	public static String fillStrWith_(String pSrcStr, int pLength) {
		return fillStrWith_(pSrcStr, pLength, false);
	}
	
	/**
	 * ��ָ���ַ������䵽ָ���ֽڳ��ȣ�GBK����(һ�����������ֽڳ���)������λ���ո�(' ')����䷽����pLeftAddָ����
	 * @param pSrcStr : Դ�ַ���
	 * @param pLength : Ҫ���䵽�ĳ���
	 * @param pLeftAdd : ��䷽���ʾ��true-�����; false-�����
	 */
	public static String fillStrWith_(String pSrcStr, int pLength, boolean pLeftAdd) {
		return fillStr(pSrcStr, pLength, ' ', pLeftAdd);
	}
	
	/**
	 * ��ָ���ַ������䵽ָ���ֽڳ��ȣ�GBK����(һ�����������ֽڳ���)������λ��'0'��
	 * @param pSrcStr : Դ�ַ���
	 * @param pLength : Ҫ���䵽�ĳ���
	 */
	public static String fillStrWith0(String pSrcStr, int pLength) {
		return fillStrWith0(pSrcStr, pLength, true);
	}
	
	/**
	 * ��ָ���ַ������䵽ָ���ֽڳ��ȣ�GBK����(һ�����������ֽڳ���)������λ��'0'����䷽����pLeftAddָ����
	 * @param pSrcStr : Դ�ַ���
	 * @param pLength : Ҫ���䵽�ĳ���
	 * @param pLeftAdd : ��䷽���ʾ��true-�����; false-�����
	 */
	public static String fillStrWith0(String pSrcStr, int pLength, boolean pLeftAdd) {
		return fillStr(pSrcStr, pLength, '0', pLeftAdd);
	}
	
	/**
	 * ��ָ���ַ�����ָ���ַ����䵽ָ���ֽڳ��ȣ�GBK����(һ�����������ֽڳ���)��
	 * @param pSrcStr : Դ�ַ���
	 * @param pLength : Ҫ���䵽�ĳ���
	 * @param pFillChar : ����ַ�
	 * @param pLeftAdd : ��䷽���ʾ��true-�����; false-�����
	 */
	public static String fillStr(String pSrcStr, int pLength, char pFillChar, boolean pLeftAdd) {
		return fillStr(pSrcStr, pLength, pFillChar, pLeftAdd, "GBK");
	}
	
	/**
	 * ��ָ���ַ�����ָ���ַ����䵽ָ���ֽڳ��ȡ�
	 * @param pSrcStr : Դ�ַ���
	 * @param pLength : Ҫ���䵽�ĳ���
	 * @param pFillChar : ����ַ�
	 * @param pLeftAdd : ��䷽���ʾ��true-�����; false-�����
	 * @param pCharset : �����ַ���
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
	 * ���ַ�������GBK��ȡ�����ظ��ֽڳ�������󴮡�
	 * @param pSrcStr : Դ�ַ���
	 * @param pByteLen : ��ȡ�������ֽڳ���
	 */
	public static String cutStrByGBK(String pSrcStr, int pByteLen) {
		return cutStrByByte(pSrcStr, pByteLen, "GBK");
	}
	
	/**
	 * ���ַ�������UTF-8��ȡ�����ظ��ֽڳ�������󴮡�
	 * @param pSrcStr : Դ�ַ���
	 * @param pByteLen : ��ȡ�������ֽڳ���
	 */
	public static String cutStrByUTF8(String pSrcStr, int pByteLen) {
		return cutStrByByte(pSrcStr, pByteLen, "UTF-8");
	}
	
	/**
	 * ���ַ�������ָ���ַ�����ȡ�����ظ��ֽڳ�������󴮡�
	 * @param pSrcStr : Դ�ַ���
	 * @param pByteLen : ��ȡ�������ֽڳ���
	 * @param pCharset : �ַ���
	 */
	public static String cutStrByByte(String pSrcStr, int pByteLen, String pCharset) {
		if (pSrcStr.length() <= pByteLen/3) {	//�������ܣ��ַ������롢������cup�ܼ��Ͳ��������pSrcStrԶС��pByteLen��ֱ�ӷ���
			return pSrcStr;
		}
		
		byte[] mBytes = null;
		try {
			mBytes = pSrcStr.getBytes(pCharset);
		} catch (UnsupportedEncodingException ex) {
			cLogger.error(pCharset+"�ַ�����֧�֣�", ex);
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
     * ��ʽ���ַ�
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
     * ��ȡ���������ֺ��������ֵĶ�Ӧ��ϵ
     * @param value String
     * @return String
     */
    private static String getNum(String value) {
        String sNum = "";
        Integer I = new Integer(value);
        int iValue = I.intValue();
        switch (iValue) {
        case 0:
            sNum = "��";
            break;
        case 1:
            sNum = "Ҽ";
            break;
        case 2:
            sNum = "��";
            break;
        case 3:
            sNum = "��";
            break;
        case 4:
            sNum = "��";
            break;
        case 5:
            sNum = "��";
            break;
        case 6:
            sNum = "½";
            break;
        case 7:
            sNum = "��";
            break;
        case 8:
            sNum = "��";
            break;
        case 9:
            sNum = "��";
            break;
        }
        return sNum;
    }

    
    /**
     * ���Ǫ���ۡ�ʰ�ȵ�λ��Ϣ
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
            sMoney += getNum(strUnit.substring(0, 1)) + "Ǫ";
        } else {
            sMoney += "0";
            flag = true;
        }
        if (!strUnit.substring(1, 2).equals("0")) {
            sMoney += getNum(strUnit.substring(1, 2)) + "��";
            flag = false;
        } else {
            if (flag == false) {
                sMoney += "0";
                flag = true;
            }
        }
        if (!strUnit.substring(2, 3).equals("0")) {
            sMoney += getNum(strUnit.substring(2, 3)) + "ʰ";
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
     * �õ�money�ĽǷ���Ϣ
     * @param sIn String
     * @return String
     */
    private static String getDotM(String sIn) {
        String sMoney = "";
        if (!sIn.substring(0, 1).equals("0")) {
            sMoney += getNum(sIn.substring(0, 1)) + "��";
        } else {
            sMoney += "0";
        }
        if (!sIn.substring(1, 2).equals("0")) {
            sMoney += getNum(sIn.substring(1, 2)) + "��";
        } else {
            sMoney += "0";
        }

        return sMoney;
    }

    /**
     * �����ֽ��ת��Ϊ���Ĵ�д��� author: HST
     * @param money ���ֽ��(double)
     * @return ���Ĵ�д���(String)
     */
    public static String getChnMoney(String strmoney) {
    	if (strmoney.equals("") || strmoney == null) {
    		strmoney = "0.00";
		}
		double money = Double.parseDouble(strmoney);
		
        String ChnMoney = "";
        String s0 = "";

        // ��ԭ���汾�ĳ����У�getChnMoney(585.30)�õ���������585.29��

        if (money == 0.0) {
            ChnMoney = "��Ԫ��";
            return ChnMoney;
        }

        if (money < 0) {
            s0 = "��";
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

        String s1 = getChnM(sFormatStr.substring(0, 4), "��");

        String s2 = getChnM(sFormatStr.substring(4, 8), "��");

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

        ChnMoney = s0 + s1 + s2 + s3 + "Ԫ" + s4;
        if (ChnMoney.substring(0, 1).equals("0")) {
            ChnMoney = ChnMoney.substring(1,
                                          ChnMoney.length());
        }
        for (int i = 0; i < ChnMoney.length(); i++) {
            if (ChnMoney.substring(i, i + 1).equals("0")) {
                ChnMoney = ChnMoney.substring(0, i) + "��" +
                           ChnMoney.substring(i + 1, ChnMoney.length());
            }
        }

        if (sDot.substring(1, 2).equals("0")) {
            ChnMoney += "��";
        }

        return ChnMoney;
    }
    
    /**
	 * ��ָ���ַ�����ָ���ַ����䵽ָ���ֽڳ��ȡ�
	 * @param pSrcStr : Դ�ַ���
	 * @param pLength : Ҫ���䵽�ĳ���
	 * @param pFillChar : ����ַ�
	 * @param pLeftAdd : ��䷽���ʾ��true-�����; false-�����
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
     * ��ȡС�������λ�������������룬������ author: ZHJ
     * @param money ���ֽ��(double)
     * @return (String)
     */
    public static double ret2Double4 (double money){
    	double retMoney = 0.00;
    	BigDecimal tAddBigDecimal = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP);
    	retMoney = tAddBigDecimal.doubleValue();
    	return retMoney;
    } 
    
    /**
     * ��ȡС�������λ���������������� author: ZHJ
     * @param money ���ֽ��(double)
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
		System.out.println("����ʼ...");
		
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

		System.out.println("�ɹ�������");*/
		System.out.println(NumberUtil.fillStrWith_("aaaaa", 10)+"b");
	}
}
