package com.sinosoft.midplat.common;

import java.io.PrintStream;
import java.text.DecimalFormat;

public class CalculateUtil
{
  public static String BaseAmnt(String pMult, String pAmnt)
  {
    int baseAmnt = 0;
    if ((pMult != null) && (!"".equals(pMult)) && (pAmnt != null) && (!"".equals(pAmnt))) {
      int pIntMult = Integer.parseInt(pMult);
      int pIntAmnt = Integer.parseInt(pAmnt);
      if (pIntMult != 0) {
        baseAmnt = pIntAmnt / pIntMult;
      }
    }
    return String.valueOf(baseAmnt);
  }

  public static String yuanToWYuan(String yearSalary)
  {
    double yearSalaryD = NumberUtil.yuanToFen(yearSalary);
    String yearSalaryDs = fenToWYuan(String.valueOf(yearSalaryD));
    return yearSalaryDs;
  }

  public static String fenToWYuan(String yearSalary)
  {
	System.out.println("sdfsdfsdfewre==="+yearSalary);
    double yearSalaryD = 0.0D;
    if ((yearSalary != null) && (!"".equals(yearSalary))) {
      yearSalaryD = Double.valueOf(yearSalary).doubleValue() / 1000000.0D;
    }
    return String.valueOf(yearSalaryD);
  }
  public static void main(String[] args) {
    String s = "21341.24";
    System.out.println(yuanToWYuan(s));
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
   * ��ʽ���ַ�
   * @param sIn String
   * @return String
   */
  private static String formatStr(String sIn) {
      int n = sIn.length();
      String sOut = sIn;
//      int i = n % 4;

      for (int k = 1; k <= 12 - n; k++) {
          sOut = "0" + sOut;
      }
      return sOut;
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
  
}