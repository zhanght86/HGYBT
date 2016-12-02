/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Vector;
import java.text.NumberFormat;
import com.sinosoft.lis.pubfun.FDate;
import java.util.Date;

/**
 *****************************************************************
 *               Program NAME: �ַ�����������
 *                 programmer: Ouyangsheng (Modify)
 *                Create DATE: 2002.04.17
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *                  ���ַ��������Ĺ����ࡣ
 *
 *****************************************************************
 */
public class StrTool
{
    public static final String EQUAL = "=";
    public static final String GREATER = ">";
    public static final String GREATGE_EQUAL = ">=";
    public static final String LESS = "<";
    public static final String LESS_EQUAL = "<=";
    public static final String NOT_EQUAL = "!=";
    public static final String CONTAIN = "*";
    public static final String BETWEEN = ":";
    public static final String DATEDELIMITER = "/";
    public static final String VISADATEDELIMITER = "-";
    public static final String TIMEDELIMITER = ":";
    public static final String ADDRESSDELIMITER = "$$";
    public static final String DELIMITER = "^";
    public static final String PACKAGESPILTER = "|";
    public static final String OR = "~!";
    public static final int LENGTH_OR = 2;
    public static final String BETWEEN_AND = ":";
    public static final String BLANK = "?";
    public static final String DIRECTMODE = "Direct Mode!" + DELIMITER + DELIMITER;

    /**
     * ���ַ�������ָ���ķָ��ַ����в��,���ش�ָ����ŵķָ�����ǰһ���ָ���֮����ַ���
     * @param strMain String ���ַ���
     * @param strDelimiters String �ָ���
     * @param intSerialNo int �ָ������
     * @return String ָ����ŵķָ�����ǰһ���ָ���֮����ַ���,���û���ҵ��򷵻�""
     * ���磺ֵ���������� ֵ1|ֵ2|ֵ3|ֵ4|
     * ��intSerialNo=0 return ""   intSerialNo=1 return "ֵ1"  intSerialNo=5 return ""
     */
    public static String decodeStr(String strMain, String strDelimiters, int intSerialNo)
    {
        int intIndex = 0; /*�ָ������������ַ����е���ʼλ��*/
        int intCount = 0; /*��ɨ�����ַ����Ĺ�����,�ڼ��������ָ����ַ���*/
        String strReturn = ""; /*��Ϊ����ֵ���ַ���*/

        if (strMain.length() < strDelimiters.length())
        {
            return ""; /*�����ַ����ȷָ�������Ҫ�̵Ļ�,�򷵻ؿ��ַ���*/
        }

        intIndex = strMain.indexOf(strDelimiters);
        if (intIndex == -1)
        {
            return ""; /*�����ַ����в����ڷָ���,�򷵻ؿ��ַ���*/
        }

        while (intIndex != -1) /*δ�ҵ��ָ���ʱ�˳�ѭ��,�����ؿ��ַ���*/
        {
            strReturn = strMain.substring(0, intIndex);
            intCount++;
            if (intCount == intSerialNo)
            {
                if (intIndex == 0)
                {
                    return "";
                }
                else
                {
                    return strReturn.trim();
                }
            }
            strMain = strMain.substring(intIndex + 1);
            intIndex = strMain.indexOf(strDelimiters);
        }
        return "";
    }

    /**
     * ��ȡ�Ӵ��������г��ֵ� n �ε�λ��
     * @param strMain String ���ַ���
     * @param strSub String ���ַ���
     * @param intTimes int ���ִ���
     * @return int λ��ֵ,����Ӵ���������û�г���ָ������,�򷵻�-1
     */
    public static int getPos(String strMain, String strSub, int intTimes)
    {
        int intCounter = 0; //ѭ������
        int intPosition = 0; //λ�ü�¼
        int intLength = strSub.length(); //�Ӵ�����

        if (intTimes <= 0)
        {
            return -1;
        }
        while (intCounter < intTimes)
        {
            intPosition = strMain.indexOf(strSub, intPosition);
            if (intPosition == -1)
            {
                return -1;
            }
            intCounter++;
            intPosition += intLength;
        }
        return intPosition - intLength;
    }

    /**
     * ��ȡ��ָ��λ�ÿ�ʼ�Ӵ��������г��ֵ� n �ε�λ��
     * @param strMain String ���ַ���
     * @param strSub String ���ַ���
     * @param intStartIndex int ��ʼλ��
     * @param intTimes int ���ִ���
     * @return int λ��ֵ,�������ʼλ�����Ӵ���������û�г���ָ������,�򷵻�-1
     */
    public static int getPos(String strMain, String strSub, int intStartIndex,
            int intTimes)
    {
        if (strMain.length() - 1 < intStartIndex)
        {
            return -1;
        }
        int intPosition = getPos(strMain.substring(intStartIndex), strSub,
                intTimes);
        if (intPosition != -1)
        {
            intPosition += intStartIndex;
        }
        return intPosition;
    }

    /**
     * ת���ַ�������Ҫ��SQL������������
     * @param strMessage String ��ת�����ַ���
     * (����:<I>�ֶ���^������^�ֶ�ֵ^</I>)
     * �¸�ʽΪ��{[(]�ֶ��� ������ �ֶ�ֵ [)]���ӷ���}�汾��ʽ��^
     * @return String �����ַ���(SQL����е� WHERE �����Ӿ�,�������� 'where'�ؼ���)
     */
    public static String makeCondition(String strMessage)
    {
        String strSegment = "";
        String strField = "";
        String strOperator = "";
        String strValue = "";
        String strRemain = "";
        String strReturn = "1=1"; //��������
        int intPosition = 0;

        if (strMessage.indexOf(StrTool.DELIMITER) < 0)
        {
            return strMessage;
        }
        strRemain = strMessage;

        if (!strRemain.endsWith(StrTool.DIRECTMODE + StrTool.DELIMITER))
        {
            do
            {
                intPosition = getPos(strRemain, StrTool.DELIMITER, 3);
                if (intPosition < 0) //�ֽ����
                {
                    return strReturn;
                }
                strSegment = strRemain.substring(0, intPosition + 1).trim();
                strRemain = strRemain.substring(intPosition + 1);
                if (strSegment.length() < 1) //�ֶν���
                {
                    break;
                }
                strField = decodeStr(strSegment, StrTool.DELIMITER, 1);
                strOperator = decodeStr(strSegment, StrTool.DELIMITER, 2);
                strValue = decodeStr(strSegment, StrTool.DELIMITER, 3);
                if (strValue.equals(BLANK))
                {
                    strValue = " ";
                }
                strReturn = strReturn + " AND " + "(";
                //�жϲ�����
                if (strOperator.equals(BETWEEN))
                {
                    intPosition = strValue.indexOf(BETWEEN_AND);
                    strReturn = strReturn + strField + " BETWEEN '" +
                            strValue.substring(0, intPosition).trim() + "'"
                            + " AND  '" +
                            strValue.substring(intPosition + 1).trim() +
                            "' ";
                }
                else if (strOperator.equals(CONTAIN))
                {
                    strReturn = strReturn + strField + " matches '" + strValue +
                            "*' ";
                }
                else
                {
                    strSegment = "";
                    while (true)
                    {
                        intPosition = strValue.indexOf(OR);
                        if (intPosition < 0)
                        {
                            if (strSegment.equals(""))
                            {
                                strReturn = strReturn + strField + " " +
                                        strOperator + " '" + strValue +
                                        "' ";
                            }
                            else
                            {
                                strReturn = strReturn
                                        + strSegment
                                        + " OR "
                                        + strField
                                        + " " + strOperator
                                        + " '" + strValue.trim() + "' ";
                            }
                            break;
                        }
                        if (!strSegment.equals(""))
                        {
                            strSegment += " OR ";
                        }

                        strSegment = strSegment
                                + strField
                                + " " + strOperator
                                + " '" +
                                strValue.substring(0, intPosition).trim() +
                                "' ";
                        strValue = strValue.substring(intPosition + LENGTH_OR);
                    }
                }
                strReturn += ") ";
            }
            while (true);
        }
        else
        {
            strRemain = strRemain.substring(0,
                    strRemain.length() -
                    StrTool.DIRECTMODE.length() -
                    StrTool.DELIMITER.length());
            if (strRemain.trim().equals(""))
            {
                strRemain = "1=1";
            }
            strReturn = strRemain;
        }
        return strReturn;
    }

    /**
     * ���ַ���ת��ΪGBK�ַ���
     * @param strOriginal String ԭ��
     * @return String ��ԭ����ISO8859_1(Unicode)����ת��ΪGBK����
     */
    public static String unicodeToGBK(String strOriginal)
    {
        if (strOriginal != null)
        {
            try
            {
                //��������ﲻ���κδ���ȫ��ֱ�ӷ��صĻ�������ʲô����
                if (isGBKString(strOriginal))
                {
//                    System.out.println("It's GBK: " + strOriginal);
                    return strOriginal;
                }
                else
                {
//                    System.out.println("It's ISO8859_1: " + strOriginal);
                    return new String(strOriginal.getBytes("ISO8859_1"), "GBK");
                }

            }
            catch (Exception exception)
            {
                System.out.println(exception.getMessage());
                return strOriginal;
            }
        }
        else
        {
            return "";
        }
    }

    /**
     * ���ַ���ת��ΪUnicode�ַ���
     * @param strOriginal String ԭ��
     * @return String ��ԭ����GBK����ת��ΪISO8859_1(Unicode)����
     */
    public static String GBKToUnicode(String strOriginal)
    {
        if (SysConst.CHANGECHARSET)
        {
            if (strOriginal != null)
            {
                try
                {
                    if (isGBKString(strOriginal))
                    {
                        return new String(strOriginal.getBytes("GBK"), "ISO8859_1");
                    }
                    else
                    {
                        return strOriginal;
                    }
                }
                catch (Exception exception)
                {
                    return strOriginal;
                }
            }
            else
            {
                return null;
            }
        }
        else
        {
            //�����������޸�����ķ��أ�������ϵͳ���������������
//            return unicodeToGBK(strOriginal);
//            System.out.println("Don't unicodeToGBK ......");
            if (strOriginal == null)
            {
                return "";
            }
            else
            {
                return strOriginal;
            }
        }
    }

    /**
     * ���ַ���ת��ΪUnicode�ַ���
     * @param strOriginal String ԭ��
     * @param realConvert boolean �Ƿ�ȷ��ת��
     * @return String ��ԭ����GBK����ת��ΪISO8859_1(Unicode)����
     */
    public static String GBKToUnicode(String strOriginal, boolean realConvert)
    {
        if (!realConvert)
        {
            return unicodeToGBK(strOriginal);
        }
        if (strOriginal != null)
        {
            try
            {
                if (isGBKString(strOriginal))
                {
                    return new String(strOriginal.getBytes("GBK"), "ISO8859_1");
                }
                else
                {
                    return strOriginal;
                }
            }
            catch (Exception exception)
            {
                return strOriginal;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * �ж��Ƿ���GBK����
     * @param tStr String
     * @return boolean
     */
    public static boolean isGBKString(String tStr)
    {
        int tlength = tStr.length();
//        Integer t = new Integer(0);
        int t1 = 0;
        for (int i = 0; i < tlength; i++)
        {
            t1 = Integer.parseInt(Integer.toOctalString(tStr.charAt(i)));
            if (t1 > 511)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * �ж��Ƿ���Unicode����
     * @param tStr String
     * @return boolean
     */
    public static boolean isUnicodeString(String tStr)
    {
        int tlength = tStr.length();
        Integer t = new Integer(0);
        int t1 = 0;
        for (int i = 0; i < tlength; i++)
        {
            t1 = Integer.parseInt(Integer.toOctalString(tStr.charAt(i)));
            if (t1 > 511)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * ʹ��ָ�����е�decode()������������ַ���
     * @param strMessage String �ַ���
     * @param intCount int �������
     * @param cl Class ����decode()��������,���Ҵ����к���FIELDNUM����
     * @return Vector ��ÿ������������ɶ�Ӧ����ʵ��,������Щʵ����Ϊ����Vector��Ԫ��
     * @throws Exception �������decode�����������߲���FIELDNUM�ֶγ������߽�����������׳��쳣
     */
    public static Vector stringToVector(String strMessage, int intCount,
            Class cl)
            throws Exception
    {
        int intFieldNum = 0;
        Object object = null;
        Vector vec = new Vector();
        int intPosition = 0;
        Class[] parameters =
                {String.class};
        Method method = null;
        Field field = null;
        String[] therecord =
                {new String()};
        try
        {
            object = cl.newInstance();
            method = cl.getMethod("decode", parameters);
            field = cl.getField("FIELDNUM");
            intFieldNum = field.getInt(object);

            for (int i = 0; i < intCount; i++)
            {
                object = cl.newInstance();
                intPosition = StrTool.getPos(strMessage, StrTool.PACKAGESPILTER,
                        intFieldNum);

                if (intPosition == strMessage.length() - 1)
                {
                    therecord[0] = strMessage;
                    method.invoke(object, therecord);
                    vec.addElement(object);
                    break;
                }
                else
                {
                    therecord[0] = strMessage.substring(0, intPosition + 1);

                    method.invoke(object, therecord);
                    vec.addElement(object);
                    strMessage = strMessage.substring(intPosition + 1);
                }
            }
        }
        catch (Exception exception)
        {
            throw exception;
        }
        finally
        {}
        return vec;
    }

    /**
     * �ַ����滻����
     * @param strMain String ԭ��
     * @param strFind String �����ַ���
     * @param strReplaceWith String �滻�ַ���
     * @return String �滻����ַ��������ԭ��Ϊ�ջ���Ϊ""���򷵻�""
     */
    public static String replace(String strMain, String strFind, String strReplaceWith)
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intStartIndex = 0;
        int intEndIndex = 0;

        if (strMain == null || strMain.equals(""))
        {
            return "";
        }

        while ((intEndIndex = strMain.indexOf(strFind, intStartIndex)) > -1)
        {
//            strReturn = strReturn +
//                    strMain.substring(intStartIndex, intEndIndex) +
//                    strReplaceWith;
            tSBql.append(strMain.substring(intStartIndex, intEndIndex));
            tSBql.append(strReplaceWith);

            intStartIndex = intEndIndex + strFind.length();
        }
//        strReturn += strMain.substring(intStartIndex, strMain.length());
        tSBql.append(strMain.substring(intStartIndex, strMain.length()));

        return tSBql.toString();
    }

    /**
     * �ɸ��������ַ�����ȡ��ʽ����(��/��/��)
     * @param strYear String ��
     * @param strMonth String ��
     * @param strDay String ��
     * @return String ��ʽ���ڣ���/��/��
     */
    public static String getDate(String strYear, String strMonth, String strDay)
    {
        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intYear = 0;
        int intMonth = 0;
        int intDay = 0;
        if ((strYear != null) && (strMonth != null) && (strDay != null)
                && (strYear.trim().length() > 0) && (strMonth.trim().length() > 0)
                && (strDay.trim().length() > 0))
        {
            try
            {
                intYear = new Integer(strYear).intValue();
                intMonth = new Integer(strMonth).intValue();
                intDay = new Integer(strDay).intValue();
            }
            catch (Exception exception)
            {
                return strReturn;
            }

            if ((intYear <= 0) || (intMonth <= 0) || (intMonth > 12) || (intDay <= 0)
                    || (intDay > 31))
            {
                strReturn = "";
            }
            else
            {
                tSBql.append(intYear);
//                strReturn = "" + intYear;
                if (intMonth < 10)
                {
                    tSBql.append(StrTool.DATEDELIMITER);
                    tSBql.append("0");
                    tSBql.append(intMonth);
//                    strReturn += StrTool.DATEDELIMITER + "0" + intMonth;
                }
                else
                {
                    tSBql.append(StrTool.DATEDELIMITER);
                    tSBql.append(intMonth);
//                    strReturn += StrTool.DATEDELIMITER + intMonth;
                }

                if (intDay < 10)
                {
                    tSBql.append(StrTool.DATEDELIMITER);
                    tSBql.append("0");
                    tSBql.append(intDay);
//                    strReturn += StrTool.DATEDELIMITER + "0" + intDay;
                }
                else
                {
                    tSBql.append(StrTool.DATEDELIMITER);
                    tSBql.append(intDay);
//                    strReturn += StrTool.DATEDELIMITER + intDay;
                }
                strReturn = tSBql.toString();
            }
        }
        return strReturn;
    }

    /**
     * ���ϵͳ����(��/��/��)
     * @return String ��ʽ���ڣ���/��/��
     */
    public static String getDate()
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intYear = Calendar.getInstance().get(Calendar.YEAR);
        int intMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int intDate = Calendar.getInstance().get(Calendar.DATE);

        tSBql.append(intYear);
//        strReturn = "" + intYear;

        if (intMonth < 10)
        {
            tSBql.append(StrTool.DATEDELIMITER);
            tSBql.append("0");
            tSBql.append(intMonth);
//            strReturn += StrTool.DATEDELIMITER + "0" + intMonth;
        }
        else
        {
            tSBql.append(StrTool.DATEDELIMITER);
            tSBql.append(intMonth);
//            strReturn += StrTool.DATEDELIMITER + intMonth;
        }

        if (intDate < 10)
        {
            tSBql.append(StrTool.DATEDELIMITER);
            tSBql.append("0");
            tSBql.append(intDate);
//            strReturn += StrTool.DATEDELIMITER + "0" + intDate;
        }
        else
        {
            tSBql.append(StrTool.DATEDELIMITER);
            tSBql.append(intDate);
//            strReturn += StrTool.DATEDELIMITER + intDate;
        }
        return tSBql.toString();
    }

    /**
     * �ɸ��������ַ�����ȡ��ʽ����(��/��)
     * @param strYear String ��
     * @param strMonth String ��
     * @return String ��ʽ���ڣ���/��
     */
    public static String getDate(String strYear, String strMonth)
    {
        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intYear = 0;
        int intMonth = 0;
        if ((strYear != null) && (strMonth != null) && (strYear.trim().length() > 0)
                && (strMonth.trim().length() > 0))
        {
            intYear = new Integer(strYear).intValue();
            intMonth = new Integer(strMonth).intValue();
            if ((intYear <= 0) || (intMonth <= 0) || (intMonth > 12))
            {
                strReturn = "";
            }
            else
            {
                tSBql.append(intYear);
                tSBql.append(StrTool.DATEDELIMITER);
                tSBql.append(intMonth);
//                strReturn = "" + intYear + StrTool.DATEDELIMITER + intMonth;
                strReturn = tSBql.toString();
            }
        }
        return strReturn;
    }

    /**
     * ��ȡ����ֵ�е����
     * @param strDate String ���ڣ���/��/�գ�
     * @return String ��
     */
    public static String getYear(String strDate)
    {
        int intPosition = 0;
        String strReturn = "";
        int intYear = 0;

        if ((strDate != null) && (strDate.trim().length() > 0))
        {
            intPosition = StrTool.getPos(strDate, StrTool.DATEDELIMITER, 1);
            if (intPosition > 0)
            {
                strReturn = strDate.substring(0, intPosition);
                intYear = new Integer(strReturn).intValue();
                if ((intYear <= 0))
                {
                    strReturn = "";
                }
                else
                {
                    strReturn = "" + intYear;
                }

                if ((intYear < 10) && (!strReturn.equals("")))
                {
                    strReturn = "0" + strReturn;
                }
            }
        }
        return strReturn;
    }

    /**
     * ��ȡϵͳ�����е����
     * @return String ��
     */
    public static String getYear()
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intYear = Calendar.getInstance().get(Calendar.YEAR);
        tSBql.append(intYear);
//        strReturn = "" + intYear;
        return tSBql.toString();
    }

    /**
     * ��ȡ����ֵ�е��·�
     * @param strDate String ����
     * @return String ��
     */
    public static String getMonth(String strDate)
    {
        int intPosition1 = 0, intPosition2 = 0;
        String strReturn = "";
        int intMonth = 0;
        if ((strDate != null) && (strDate.trim().length() > 0))
        {
            intPosition1 = StrTool.getPos(strDate, StrTool.DATEDELIMITER, 1);
            intPosition2 = StrTool.getPos(strDate, StrTool.DATEDELIMITER, 2);
            if ((intPosition1 > 0) && intPosition2 > intPosition1)
            {

                strReturn = strDate.substring(intPosition1 + 1, intPosition2);

                intMonth = new Integer(strReturn).intValue();
                if ((intMonth <= 0) || (intMonth > 12))
                {
                    strReturn = "";
                }
                else
                {
                    strReturn = "" + intMonth;
                }

                if ((intMonth < 10) && (!strReturn.equals("")))
                {
                    strReturn = "0" + strReturn;
                }
            }
        }
        return strReturn;
    }

    /**
     * ��ȡϵͳ�����е��·�
     * @return String ��
     */
    public static String getMonth()
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        if (intMonth < 10)
        {
            tSBql.append("0");
            tSBql.append(intMonth);
//            strReturn = "0" + intMonth;
        }
        else
        {
            tSBql.append(intMonth);
//            strReturn = "" + intMonth;
        }
        return tSBql.toString();
    }

    /**
     * ��ȡ��������ֵ�е���
     * @param strDate String ����
     * @return String ��
     */
    public static String getDay(String strDate)
    {
        int intPosition = 0;
        String strReturn = "";
        int intDay = 0;
        if ((strDate != null) && (strDate.trim().length() > 0))
        {
            intPosition = StrTool.getPos(strDate, StrTool.DATEDELIMITER, 2);
            if (intPosition > 0)
            {

                strReturn = strDate.substring(intPosition + 1);

                intDay = new Integer(strReturn).intValue();

                if ((intDay <= 0) || (intDay > 31))
                {
                    strReturn = "";
                }
                else
                {
                    strReturn = "" + intDay;
                }

                if ((intDay < 10) && (!strReturn.equals("")))
                {
                    strReturn = "0" + strReturn;
                }
            }
        }
        return strReturn;
    }

    /**
     * ��ȡϵͳ����ֵ�е���
     * @return String ��
     */
    public static String getDay()
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intDate = Calendar.getInstance().get(Calendar.DATE);
        if (intDate < 10)
        {
            tSBql.append("0");
            tSBql.append(intDate);
//            strReturn = "0" + intDate;
        }
        else
        {
            tSBql.append(intDate);
//            strReturn = "" + intDate;
        }
        return tSBql.toString();
    }

    /**
     * ��ȡϵͳʱ��ֵ
     * @return String Сʱ: ���ӣ���
     */
    public static String getTime()
    {
        StringBuffer tSBql = new StringBuffer();
        tSBql.append(StrTool.getHour());
        tSBql.append(":");
        tSBql.append(StrTool.getMinute());
        tSBql.append(":");
        tSBql.append(StrTool.getSecond());

//        return StrTool.getHour() + ":" + StrTool.getMinute() + ":" +
//                StrTool.getSecond();
        return tSBql.toString();
    }

    /**
     * ��ȡϵͳʱ��ֵ�е�Сʱ
     * @return String Сʱ
     */
    public static String getHour()
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intHour = Calendar.getInstance().get(Calendar.HOUR) +
                (Calendar.HOUR_OF_DAY + 1) *
                Calendar.getInstance().get(Calendar.AM_PM);
        if (intHour < 10)
        {
            tSBql.append("0");
            tSBql.append(intHour);
//            strReturn = "0" + intHour;
        }
        else
        {
            tSBql.append(intHour);
//            strReturn = "" + intHour;
        }

        return tSBql.toString();
    }

    /**
     * ��ȡϵͳʱ��ֵ�еķ���
     * @return String ����
     */
    public static String getMinute()
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intMinute = Calendar.getInstance().get(Calendar.MINUTE);
        if (intMinute < 10)
        {
            tSBql.append("0");
            tSBql.append(intMinute);
//            strReturn = "0" + intMinute;
        }
        else
        {
            tSBql.append(intMinute);
//            strReturn = "" + intMinute;
        }

        return tSBql.toString();
    }

    /**
     * ��ȡϵͳʱ��ֵ������
     * @return String ����
     */
    public static String getSecond()
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int intSecond = Calendar.getInstance().get(Calendar.SECOND);
        if (intSecond < 10)
        {
            tSBql.append("0");
            tSBql.append(intSecond);
//            strReturn = "0" + intSecond;
        }
        else
        {
            tSBql.append(intSecond);
//            strReturn = "" + intSecond;
        }

        return tSBql.toString();
    }

    /**
     * ��ȡ��"-"�ָ�����ֵ�е����
     * @param strDate String ����
     * @return String ��
     */
    public static String getVisaYear(String strDate)
    {
        int intPosition = 0;
        String strReturn = "";
        int intYear = 0;

        if ((strDate != null) && (strDate.trim().length() > 0))
        {
            intPosition = StrTool.getPos(strDate, StrTool.VISADATEDELIMITER, 1);
            if (intPosition > 0)
            {
                strReturn = strDate.substring(0, intPosition);
                intYear = new Integer(strReturn).intValue();
                if ((intYear <= 0))
                {
                    strReturn = "";
                }
                else
                {
                    strReturn = "" + intYear;
                }

                if ((intYear < 10) && (!strReturn.equals("")))
                {
                    strReturn = "0" + strReturn;
                }
            }
        }
        return strReturn;
    }
    /**
    * ��ȡ��"-"�ָ�����ֵ�����ı�ʾ
    * @param strDate String ����
    * @return String YYYY��MM��DD��
    */
   public static String getChnDate(String strDate)
   {
       String strReturn = getVisaYear(strDate)+"��"+getVisaMonth(strDate)+"��"+getVisaDay(strDate)+"��";
       return strReturn;
   }

    /**
     * ��ȡ��"-"�ָ�����ֵ�е��·�
     * @param strDate String ����
     * @return String ��
     */
    public static String getVisaMonth(String strDate)
    {
        int intPosition1 = 0, intPosition2 = 0;
        String strReturn = "";
        int intMonth = 0;
        if ((strDate != null) && (strDate.trim().length() > 0))
        {
            intPosition1 = StrTool.getPos(strDate, StrTool.VISADATEDELIMITER, 1);
            intPosition2 = StrTool.getPos(strDate, StrTool.VISADATEDELIMITER, 2);
            if ((intPosition1 > 0) && intPosition2 > intPosition1)
            {

                strReturn = strDate.substring(intPosition1 + 1, intPosition2);

                intMonth = new Integer(strReturn).intValue();
                if ((intMonth <= 0) || (intMonth > 12))
                {
                    strReturn = "";
                }
                else
                {
                    strReturn = "" + intMonth;
                }

                if ((intMonth < 10) && (!strReturn.equals("")))
                {
                    strReturn = "0" + strReturn;
                }
            }
        }
        return strReturn;
    }

    /**
     * ��ȡ��"-"�ָ�����ֵ�е���
     * @param strDate String ����
     * @return String ��
     */
    public static String getVisaDay(String strDate)
    {
        int intPosition = 0;
        String strReturn = "";
        int intDay = 0;
        if ((strDate != null) && (strDate.trim().length() > 0))
        {
            intPosition = StrTool.getPos(strDate, StrTool.VISADATEDELIMITER, 2);
            if (intPosition > 0)
            {

                strReturn = strDate.substring(intPosition + 1);

                intDay = new Integer(strReturn).intValue();

                if ((intDay <= 0) || (intDay > 31))
                {
                    strReturn = "";
                }
                else
                {
                    strReturn = "" + intDay;
                }

                if ((intDay < 10) && (!strReturn.equals("")))
                {
                    strReturn = "0" + strReturn;
                }
            }
        }
        return strReturn;
    }

    /**
     * �ַ���ת����HTML��ʽ
     * @param strInValue String �����ַ���
     * @return String ת���󷵻�
     */
    public static String toHTMLFormat(String strInValue)
    {
//        String strOutValue = "";
        StringBuffer tSBql = new StringBuffer();
        char c;

        for (int i = 0; i < strInValue.length(); i++)
        {
            c = strInValue.charAt(i);
            switch (c)
            {
                case '<':
                    tSBql.append("&lt;");

//                    strOutValue += "&lt;";
                    break;
                case '>':
                    tSBql.append("&gt;");

//                    strOutValue += "&gt;";
                    break;
                case '\n':
                    tSBql.append("<br>");

//                    strOutValue += "<br>";
                    break;
                case '\r':
                    break;
                case ' ':
                    tSBql.append("&nbsp;");

//                    strOutValue += "&nbsp;";
                    break;
                default:
                    tSBql.append(c);

//                    strOutValue += c;
                    break;
            }
        }
        return tSBql.toString();
    }

    /**
     * �ַ������
     * @param strInValue String
     * @return String
     */
    public static String encode(String strInValue)
    {
//        String strOutValue = "";
        StringBuffer tSBql = new StringBuffer();
        char c;

        for (int i = 0; i < strInValue.length(); i++)
        {
            c = strInValue.charAt(i);
            switch (c)
            {
                case ':':

                    //hardcode ͬCommon.js�� NAMEVALUEDELIMITER   //��������ֵ�ķָ���
                    tSBql.append("��");

//                    strOutValue += "��";
                    break;
                case '|':

                    //hardcode ͬCommon.js�� FIELDDELIMITER       //��֮��ķָ���
                    tSBql.append("��");

//                    strOutValue += "��";
                    break;
                case '\n':
                    tSBql.append("\\n");

//                    strOutValue += "\\n";
                    break;
                case '\r':
                    tSBql.append("\\r");

//                    strOutValue += "\\r";
                    break;
                case '\"':
                    tSBql.append("\\\"");

//                    strOutValue += "\\\"";
                    break;
                case '\'':
                    tSBql.append("\\\'");

//                    strOutValue += "\\\'";
                    break;
                case '\b':
                    tSBql.append("\\b");

//                    strOutValue += "\\b";
                    break;
                case '\t':
                    tSBql.append("\\t");

//                    strOutValue += "\\t";
                    break;
                case '\f':
                    tSBql.append("\\f");

//                    strOutValue += "\\f";
                    break;
                case '\\':
                    tSBql.append("\\\\");

//                    strOutValue += "\\\\";
                    break;
                case '<':
                    tSBql.append("\\<");

//                    strOutValue += "\\<";
                    break;
                case '>':
                    tSBql.append("\\>");

//                    strOutValue += "\\>";
                    break;
                default:
                    tSBql.append(c);

//                    strOutValue += c;
                    break;
            }
        }
        return tSBql.toString();
    }

    /**
     * ����ά����������ݴ��
     * @param arr String[][] �洢���ݵĶ�ά����
     * @return String ���ձ����������ת����õ����ַ���
     */
    public static String encode(String arr[][])
    {
        System.out.println("ʹ��StrTool�µ�encode�������");
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        int rowcount = arr.length; //ȡ�����������
        int colcount = arr[0].length; //ȡ�����������
        int eleCount = rowcount * colcount;

        if (eleCount != 0)
        {
            tSBql.append("0");
            tSBql.append(SysConst.PACKAGESPILTER);
            tSBql.append(String.valueOf(rowcount));
            tSBql.append(SysConst.RECORDSPLITER);
//            strReturn = "0" + SysConst.PACKAGESPILTER + String.valueOf(rowcount) +
//                    SysConst.RECORDSPLITER;
            for (int i = 0; i < rowcount; i++)
            {
                for (int j = 0; j < colcount; j++)
                {
                    if (j != colcount - 1)
                    {
                        tSBql.append(arr[i][j]);
                        tSBql.append(SysConst.PACKAGESPILTER);
//                        strReturn = strReturn + arr[i][j] +
//                                SysConst.PACKAGESPILTER;
                    }
                }
                if (i != rowcount - 1)
                {
                    tSBql.append(SysConst.RECORDSPLITER);
//                    strReturn += SysConst.RECORDSPLITER;
                }
            }
        }
        return tSBql.toString();
    }

    /**
     * ���ɸ������ȵ��ַ���
     * @param intLength int �ַ�������
     * @return String
     */
    public static String space(int intLength)
    {
        StringBuffer strReturn = new StringBuffer();
        for (int i = 0; i < intLength; i++)
        {
            strReturn.append(" ");
        }
        return strReturn.toString();
    }

    /**
     * ��ָ���������ɸ������ȵ��ַ���,�����Կո���,������ȥ
     * @param strValue String ָ������
     * @param intLength int �ַ�������
     * @return String
     */
    public static String space(String strValue, int intLength)
    {
        int strLen = strValue.length();

        StringBuffer strReturn = new StringBuffer();
        if (strLen > intLength)
        {
            strReturn.append(strValue.substring(0, intLength));
        }
        else
        {
            if (strLen == 0)
            {
                strReturn.append(" ");
            }
            else
            {
                strReturn.append(strValue);
            }

            for (int i = strLen; i < intLength; i++)
            {
                strReturn.append(" ");
            }
        }
        return strReturn.toString();
    }

    /**
     * ��ָ���������ɸ������ȵ��ַ���,������ָ���ַ���ָ����ʽ����,������ȥ
     * @param strValue String ָ������
     * @param intLength int �ַ�������
     * @param appendchar char ָ���ַ�
     * @param LRApp char ָ����ʽ L:����  R:�Ҳ���
     * @return String
     */
    public static String getStringWith(String strValue, int intLength,char appendchar,char LRApp)
    {
        int strLen = strValue.length();

        StringBuffer strReturn = new StringBuffer();
        if (strLen > intLength)
        {
            strReturn.append(strValue.substring(0, intLength));
        }
        else
        {
            if (strLen == 0)
            {
                strReturn.append(appendchar);
            }
            else
            {
                if(LRApp == 'R')
                {
                    strReturn.append(strValue);//�Ҳ���
                }
            }

            for (int i = strLen; i < intLength; i++)
            {
                strReturn.append(appendchar);
            }
            if (strLen > 0)
            {
                if(LRApp == 'L')
                {
                    strReturn.append(strValue);//����
                }
            }
        }
        return strReturn.toString();
    }

    /**
     * �õ�ת���ȼ��ֽ�����ĳ���
     * @param strSource String
     * @return int
     */
    public static int getLength(String strSource)
    {
        return strSource.getBytes().length;
    }

    /**
     * �����ļ�
     * @param fromFile String
     * @param toFile String
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     */
    public static void copyFile(String fromFile, String toFile)
            throws
            FileNotFoundException, IOException, Exception
            {
            FileInputStream in = new FileInputStream(fromFile);
            FileOutputStream out = new FileOutputStream(toFile);

            byte b[] = new byte[1024];
            int len;

            while ((len = in.read(b)) != -1)
    {
        out.write(b, 0, len);
    }
    out.close();
            in.close();
    }
            /**
             * ���ִ�д
             * @param intValue int
             * @return String
             */
            public static String toUpper(int intValue)
    {
        String strOutValue = "";
        String[] strTemp =
                {
                "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��"};
        try
        {
            strOutValue = strTemp[intValue];
        }
        catch (Exception exception)
        {
            //Log.printException(exception.toString());
            strOutValue = "";
        }
        return strOutValue;
    }

    /**
     * �õ���λ
     * @param intValue int
     * @return String
     */
    public static String getUnit(int intValue)
    {
        String strOutValue = "";
        String[] strTemp =
                {
                "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��",
                "ʰ", "", "", ""};

        try
        {
            strOutValue = strTemp[intValue];
        }
        catch (Exception exception)
        {
            //Log.printException(exception.toString());
            strOutValue = "";
        }
        return strOutValue;
    }

    /**
     * ����ת��Ϊ��д(���ݲ��ܳ���12λ)
     * @param dblInValue double
     * @return String
     */
    public static String toChinese(double dblInValue)
    {
        String strOutValue = "";
        String strValue = new DecimalFormat("0").format(dblInValue * 100);
        String strTemp = "                 ";
        String strThat = "";
        int i = 0;
        int j = 0;
        int k = 0;

        k = strValue.length();
        if (k > 14)
        {
            //Log.printException("����̫��");
            return "";
        }

        strValue = strTemp.substring(0, 14 - k) + strValue;

        for (i = 14 - k; i < 14; i++)
        {

            j = new Integer(strValue.substring(i, i + 1)).intValue();
            if (j > 0)
            {
                strOutValue = strOutValue + strThat + toUpper(j) + getUnit(i);
                strThat = "";
            }
            else
            {
                if (i == 11)
                {
                    strOutValue += getUnit(i);
                }

                if (i == 7 && !strValue.substring(4, 8).equals("0000"))
                {
                    strOutValue += getUnit(i);
                }

                if (i == 3 && !strValue.substring(0, 4).equals("0000"))
                {
                    strOutValue += getUnit(i);
                }

                if (i < 11)
                {
                    strThat = toUpper(0);
                }

                if (i == 12)
                {
                    strThat = toUpper(0);
                }
            }
        }
        return strOutValue;
    }

    /**
     * ����ת��Ϊ��д
     * @param intInValue int
     * @return String
     */
    public static String toChinese(int intInValue)
    {
        return toChinese((double) intInValue);
    }

    /**
     * ����ת��Ϊ��д
     * @param longInValue long
     * @return String
     */
    public static String toChinese(long longInValue)
    {
        return toChinese((double) longInValue);
    }

    /**
     * ��������ַ�������ת�������Ϊ�գ��򷵻�""��������գ��򷵻ظ��ַ���ȥ��ǰ��ո�
     * @param tStr  �����ַ���
     * @return  ���Ϊ�գ��򷵻�""��������գ��򷵻ظ��ַ���ȥ��ǰ��ո�
     */
    public static String cTrim(String tStr)
    {
        String ttStr = "";
        if (tStr == null)
        {
            ttStr = "";
        }
        else
        {
            ttStr = tStr.trim();
        }
        return ttStr;
    }

    /**
     * ���ַ�������
     * ��sourString��ǰ����cChar����cLen���ȵ��ַ���
     * @param sourString String
     * @param cChar String
     * @param cLen int
     * @return String �ַ���,����ַ�����������������
     */
    public static String LCh(String sourString, String cChar, int cLen)
    {
        int tLen = sourString.length();
        int i, iMax;
        String tReturn = "";
        if (tLen >= cLen)
        {
            return sourString;
        }
        iMax = cLen - tLen;
        for (i = 0; i < iMax; i++)
        {
            tReturn += cChar;
        }
        tReturn = tReturn.trim() + sourString.trim();
        return tReturn;
    }

    /**
     * ���ַ�������
     * ��sourString��ǰ����cChar����cLen���ȵ��ַ���
     * @param sourString String
     * @param cChar String
     * @param cLen int
     * @return String �ַ���,����ַ�����������������
     */
    public static String RCh(String sourString, String cChar, int cLen)
    {
        int tLen = sourString.length();
        int i, iMax;
        String tReturn = "";
        if (tLen >= cLen)
        {
            return sourString;
        }
        iMax = cLen - tLen;
        for (i = 0; i < iMax; i++)
        {
            tReturn += cChar;
        }
        tReturn = tReturn.trim() + sourString.trim();
        return tReturn;
    }

    /**
     * �ú����õ�c_Str�еĵ�c_i����c_Split�ָ���ַ���
     * @param c_Str Ŀ���ַ���
     * @param c_i λ��
     * @param c_Split   �ָ��
     * @return ��������쳣���򷵻ؿ�
     */
    public static String getStr(String c_Str, int c_i, String c_Split)
    {
        String t_Str1 = "", t_Str2 = "", t_strOld = "";
        int i = 0, i_Start = 0, j_End = 0;
        t_Str1 = c_Str;
        t_Str2 = c_Split;
        i = 0;
        try
        {
            while (i < c_i)
            {
                i_Start = t_Str1.indexOf(t_Str2, 0);
                if (i_Start >= 0)
                {
                    i += 1;
                    t_strOld = t_Str1;
                    t_Str1 = t_Str1.substring(i_Start + t_Str2.length(),
                            t_Str1.length());
                }
                else
                {
                    if (i != c_i - 1)
                    {
                        t_Str1 = "";
                    }
                    break;
                }
            }

            if (i_Start >= 0)
            {
                t_Str1 = t_strOld.substring(0, i_Start);
            }
        }
        catch (Exception ex)
        {
            t_Str1 = "";
        }
        return t_Str1;
    }

    /**
     * �ú������ַ����е������ַ�ת��Ϊ�����ַ�
     * ��jsp�ļ���ʹ��application.getRealPath("config//Conversion.config")�õ�����·��
     * @param strIn Դ�ַ���
     * @param pathname �����ļ��ľ���·���ļ���
     * @return �����޸ĺ���ַ���
     */
    public static String Conversion(String strIn, String pathname)
    {
        int i;
        String strOut = "";
        SSRS tSSRS;
        try
        {
            ConfigInfo.SetConfigPath(pathname);
            tSSRS = ConfigInfo.GetValuebyCon();
            for (i = 0; i < tSSRS.MaxRow; i++)
            {
                strOut = replace(strIn, tSSRS.GetText(i + 1, 1), tSSRS.GetText(i + 1, 2));
                if (i != tSSRS.MaxRow - 1)
                {
                    strIn = strOut;
                }
            }
        }
        catch (Exception ex)
        {
            strOut = "";
        }
        // strOut=replace(strIn,String.valueOf((char)10),"@@NewLine");
        // strOut.equals(strIn);
        // strOut=replace(strIn,String.valueOf((char)34),"@@DouQuot");
        // strOut.equals(strIn);
        // strOut=replace(strIn,String.valueOf((char)39),"@@SinQuot");
        /*
             for(i=0;i<strIn.length();i++)
             {
          c=strIn.charAt(i);
          if (c==(char)10)
            strOut+="enter";
          else
            strOut+=c;
             }
         */
        return strOut;
    }

    /**
     * �ַ����滻����
     * @param strMain String ԭ��
     * @param strFind String �����ַ���
     * @param strReplaceWith String �滻�ַ���,���滻ʱ�����ִ�Сд
     * @return String �滻����ַ��������ԭ��Ϊ�ջ���Ϊ""���򷵻�""
     */
    public static String replaceEx(String strMain, String strFind, String strReplaceWith)
    {
//        String strReturn = "";
        StringBuffer tSBql = new StringBuffer();
        String tStrMain = strMain.toLowerCase();
        String tStrFind = strFind.toLowerCase();
        int intStartIndex = 0;
        int intEndIndex = 0;

        if (strMain == null || strMain.equals(""))
        {
            return "";
        }

        while ((intEndIndex = tStrMain.indexOf(tStrFind, intStartIndex)) > -1)
        {
//            strReturn = strReturn +
//                    strMain.substring(intStartIndex, intEndIndex) +
//                    strReplaceWith;
            tSBql.append(strMain.substring(intStartIndex, intEndIndex));
            tSBql.append(strReplaceWith);

            intStartIndex = intEndIndex + strFind.length();
        }
//        strReturn += strMain.substring(intStartIndex, strMain.length());
        tSBql.append(strMain.substring(intStartIndex, strMain.length()));

        return tSBql.toString();
    }

    /**
     * �ַ����ȽϺ���(�����ֵ�����,��null����ַ������)
     * �Ƚ��ַ���a,b
     * @param a String
     * @param b String
     * @return boolean �����ͬ����true,���򷵻�fasle
     */
    public static boolean compareString(String a, String b)
    {
        //����ط��Ƿ���Ҫ�޸ģ�����Ҫ��һ�����ǣ�����޸ĵĻ�������Ҫ��֤null=""�����
        if (StrTool.unicodeToGBK(StrTool.cTrim(a)).equals(StrTool.unicodeToGBK(
                StrTool.cTrim(b))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static NumberFormat format = new DecimalFormat("0.00");

    /**
     * �����ָ�ʽ��Ϊ�����ʽ 0.00
     * @param value
     * @return String
     */
    public static String formatDec(double value)
    {
      try {
        return format.format(value);
      }
      catch (Exception ex) {
        return String.valueOf(value);
      }
    }

    private static FDate fDate = new FDate();

    /**
     * ��ʽ�����ڵ�Ϊһ����ʽ�ļ���÷�.
     * @param date Date the Date object you want to format.
     * @return String format (yyyy-mm-dd)  String
     * @Date 12/21/05
     */
    public static String formatDate(Date date)
    {
      return fDate.getString(date);
    }
	/**
	 * �ַ���ת����HTML��ֵ��ʽ 	 * 
	 * @param strInValue String �����ַ��� 	 * @return String ת���󷵻� 	 * @author Enly
	 */
	public static String toHTMLValueFormat(String strInValue) {
		
		StringBuffer strOutValue = new StringBuffer("");
		
		if (strInValue != null) {
			
			char c;
			for (int i = 0; i < strInValue.length(); i++) {
				
				c = strInValue.charAt(i);
				switch (c) {
					
					case '\'':
						strOutValue.append("\\��");
						break;
					
					case '"':
						strOutValue.append("\\\"");
						break;
						
					case '\\':
						strOutValue.append("\\\\");
						break;
					
					case '\n':
						strOutValue.append("\\n");
						break;
						
					case '\r':
						break;
					
					default:
						strOutValue.append(c);
						break;
				}
			}
		}
		
		return strOutValue.toString();
	}

    /**
     * ���Ժ���
     * @param args String[]
     */
    public static void main(String[] args)
    {
        String r;
//        long a = System.currentTimeMillis();
//        System.out.println(a);
//        r = StrTool.replace("select * from ld where ABCDERewrABCDSFASf", "ABC", "xXx");
        r = StrTool.getStringWith("2341",14,'0','L');
        System.out.println(r);
        r = StrTool.getStringWith("2341",14,'0','R');
        System.out.println(r);
//        System.out.println(System.currentTimeMillis() - a);
    }
}
