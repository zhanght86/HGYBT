/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.midplat.util;

//import java.lang.reflect.*;
import java.text.*;
import java.util.*;

//import com.sinosoft.lis.db.*;
//import com.sinosoft.lis.schema.LCAppntSchema;
//import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:ҵ��ϵͳ�Ĺ���ҵ������
 * �����������ҵ�����еĹ�������������ǰϵͳ�е�funpub.4gl
 * �ļ����Ӧ����������У����еĺ���������Static�����ͣ�������Ҫ�����ݶ���
 * ͨ����������ģ��ڱ����в�����ͨ�����Դ������ݵķ����� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 */
public class PubFun
{

    public PubFun() {}

    /**
     * �ж��Ƿ�Ϊ����
     * XinYQ added on 2006-09-25
     */
    public static boolean isLeapYear(int nYear)
    {
        boolean ResultLeap = false;
        ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
        return ResultLeap;
    }

    /**
     * �������ڵĺ��� author: HST
     * ��������ָ���������½������ڵļ����ʱ�򣬲ο������ڣ����������������2002-03-31
     * <p><b>Example: </b><p>
     * <p>FDate tD=new FDate();<p>
     * <p>Date baseDate =new Date();<p>
     * <p>baseDate=tD.getDate("2000-02-29");<p>
     * <p>Date comDate =new Date();<p>
     * <p>comDate=tD.getDate("1999-12-31");<p>
     * <p>int inteval=1;<p>
     * <p>String tUnit="M";<p>
     * <p>Date tDate =new Date();<p>
     * <p>tDate=PubFun.calDate(baseDate,inteval,tUnit,comDate);<p>
     * <p>System.out.println(tDate.toString());<p>
     * @param baseDate ��ʼ����
     * @param interval ʱ����
     * @param unit ʱ������λ
     * @param compareDate ��������
     * @return Date���ͱ���
     */
    public static Date calDate(Date baseDate, int interval, String unit, Date compareDate)
    {
        Date returnDate = null;

        GregorianCalendar tBaseCalendar = new GregorianCalendar();
        tBaseCalendar.setTime(baseDate);

        if (unit.equals("Y"))
        {
            tBaseCalendar.add(Calendar.YEAR, interval);
        }
        if (unit.equals("M"))
        {
            tBaseCalendar.add(Calendar.MONTH, interval);
        }
        if (unit.equals("D"))
        {
            tBaseCalendar.add(Calendar.DATE, interval);
        }

        if (compareDate != null)
        {
            GregorianCalendar tCompCalendar = new GregorianCalendar();
            tCompCalendar.setTime(compareDate);
            int nBaseYears = tBaseCalendar.get(Calendar.YEAR);
            int nBaseMonths = tBaseCalendar.get(Calendar.MONTH);
            int nCompMonths = tCompCalendar.get(Calendar.MONTH);
            int nCompDays = tCompCalendar.get(Calendar.DATE);

            if (unit.equals("Y"))
            {
                tCompCalendar.set(nBaseYears, nCompMonths, nCompDays);
                if (tCompCalendar.before(tBaseCalendar))
                {
                    tBaseCalendar.set(nBaseYears + 1, nCompMonths, nCompDays);
                    returnDate = tBaseCalendar.getTime();
                }
                else
                {
                    returnDate = tCompCalendar.getTime();
                }
            }
            if (unit.equals("M"))
            {
                tCompCalendar.set(nBaseYears, nBaseMonths, nCompDays);
                if (tCompCalendar.before(tBaseCalendar))
                {
                    tBaseCalendar.set(nBaseYears, nBaseMonths + 1, nCompDays);
                    returnDate = tBaseCalendar.getTime();
                }
                else
                {
                    returnDate = tCompCalendar.getTime();
                }
            }
            if (unit.equals("D"))
            {
                returnDate = tBaseCalendar.getTime();
            }
            tCompCalendar = null;
        }
        else
        {
            returnDate = tBaseCalendar.getTime();

            //XinYQ added on 2006-09-25 : �����������º��µ�����,��Oracle����һ��
            GregorianCalendar tLeapCalendar = new GregorianCalendar();
            tLeapCalendar.setTime(returnDate);
            int arrComnYearEndDate[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int arrLeapYearEndDate[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int nOldYear = 1900 + baseDate.getYear();
            int nOldMonth = baseDate.getMonth();
            int nOldDate = baseDate.getDate();
            int nNewYear = tLeapCalendar.get(Calendar.YEAR);
            int nNewMonth = tLeapCalendar.get(Calendar.MONTH);

            if ((isLeapYear(nOldYear) && nOldDate == arrLeapYearEndDate[nOldMonth]) ||
                (!isLeapYear(nOldYear) && nOldDate == arrComnYearEndDate[nOldMonth]))
            {
                if (unit != null && (unit.equalsIgnoreCase("Y") || unit.equalsIgnoreCase("M")))
                {
                    if (isLeapYear(nNewYear))
                    {
                        returnDate.setDate(arrLeapYearEndDate[nNewMonth]);
                    }
                    else
                    {
                        returnDate.setDate(arrComnYearEndDate[nNewMonth]);
                    }
                }
            }
            tLeapCalendar = null;
        }
        tBaseCalendar = null;

        return returnDate;
    }

    /**
     * ���ؼ������ڣ�������¥�ϣ�add by Minim
     * @param baseDate String
     * @param interval int
     * @param unit String
     * @param compareDate String
     * @return String
     */
//    public static String calDate(String baseDate, int interval, String unit,
//                                 String compareDate)
//    {
//        try
//        {
//            FDate tFDate = new FDate();
//            Date bDate = tFDate.getDate(baseDate);
//            Date cDate = tFDate.getDate(compareDate);
//            return tFDate.getString(calDate(bDate, interval, unit, cDate));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            return null;
//        }
//    }

//    public static String calOFDate(String baseDate, int interval, String unit, String compareDate)
//    {
//        try
//        {
//            FDate tFDate = new FDate();
//            Date bDate = tFDate.getDate(baseDate);
//            Date cDate = tFDate.getDate(compareDate);
//            return tFDate.getString(calOFDate(bDate, interval, unit, cDate));
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            return null;
//        }
//    }


    public static Date calOFDate(Date baseDate, int interval, String unit, Date compareDate)
    {
        Date returnDate = null;

        GregorianCalendar mCalendar = new GregorianCalendar();
        //������ʼ���ڸ�ʽ
        mCalendar.setTime(baseDate);
        if (unit.equals("Y"))
        {
            mCalendar.add(Calendar.YEAR, interval);
        }
        if (unit.equals("M"))
        {
            //ִ���·�����
            mCalendar.add(Calendar.MONTH, interval);
        }
        if (unit.equals("D"))
        {
            mCalendar.add(Calendar.DATE, interval);
        }

        if (compareDate != null)
        {
            GregorianCalendar cCalendar = new GregorianCalendar();
            //������������
            cCalendar.setTime(compareDate);

            int mYears = mCalendar.get(Calendar.YEAR);
            int mMonths = mCalendar.get(Calendar.MONTH);
            int cMonths = cCalendar.get(Calendar.MONTH);
            int cDays = cCalendar.get(Calendar.DATE);

            if (unit.equals("Y"))
            {
                cCalendar.set(mYears, cMonths, cDays);
                if (mMonths < cCalendar.get(Calendar.MONTH))
                {
                    cCalendar.set(mYears, mMonths + 1, 0);
                }
                if (cCalendar.before(mCalendar))
                {
                    mCalendar.set(mYears + 1, cMonths, cDays);
                    returnDate = mCalendar.getTime();
                }
                else
                {
                    returnDate = cCalendar.getTime();
                }
            }
            if (unit.equals("M"))
            {
                cCalendar.set(mYears, mMonths, cDays);

                if (mMonths < cCalendar.get(Calendar.MONTH))
                {
                    //ȡ��ǰ�µ����һ������
                    cCalendar.set(mYears, mMonths + 1, 0);
                }
                if (cCalendar.before(mCalendar))
                {
                    mCalendar.set(mYears, mMonths + 1, cDays);
                    returnDate = mCalendar.getTime();
                }
                else
                {
                    returnDate = cCalendar.getTime();
                }
            }
            if (unit.equals("D"))
            {
                returnDate = mCalendar.getTime();
            }
        }
        else
        {
            returnDate = mCalendar.getTime();
        }

        return returnDate;
    }

    /**
     * ���ɸ��ѷ�ʽ��add by GaoHT
     * @param SSRS nSSRS
     * @return String
     * ���շѷ�ʽ���ɸñ��˷ѵ��˷ѷ�ʽ
     */

    public static String PayModeTransform(SSRS nSSRS)
    {
        try
        {
            int Cash = 0; //�ֽ�
            int Cheque = 0; //֧Ʊ
            int Bank = 0; //���л���
            int Pos = 0; //Pos�տ�
            String tOutPayMode = "";
            for (int i = 1; i <= nSSRS.getMaxRow(); i++)
            {
                String tPayMode = nSSRS.GetText(i, 1);
                if (tPayMode.equals("3") || tPayMode.equals("4"))
                {
                    Cheque++;
                }
                else if (tPayMode.equals("6"))
                {
                    Pos++;
                }
                else if (tPayMode.equals("7"))
                {
                    Bank++;
                }
                else
                {
                    Cash++;
                }
            }
            System.out.println("��ʱ���ѷ�ʽ�� �ֽ�:::::::::��" + Cash + "�� ��");
            System.out.println("��ʱ���ѷ�ʽ�� ֧Ʊ:::::::::��" + Cheque + "�� ��");
            System.out.println("��ʱ���ѷ�ʽ�� POS:::::::::��" + Pos + "�� ��");
            System.out.println("��ʱ���ѷ�ʽ�� ����:::::::::��" + Bank + "�� ��");
            System.out.println(
                    "---------------------�������ȼ��������ѷ�ʽ--------------------");

            if (Cheque > 0) //֧Ʊ���ȼ����
            {
                tOutPayMode = "3";
            }
            else if (Bank > 0) //���д������
            {
                tOutPayMode = "4";
            }
            else if (Cash > 0) //����Ϊ�ֽ�
            {
                tOutPayMode = "1";
            }
            else if (Pos > 0) //ֻ��һ��pos
            {
                tOutPayMode = "6";
            }

            return tOutPayMode;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * ͨ����ʼ���ں���ֹ���ڼ�����ʱ������λΪ������׼��ʱ���� author: HST
     * <p><b>Example: </b><p>
     * <p>����calInterval(String  cstartDate, String  cendDate, String unit)��ǰ����������Ϊ�����ͼ���<p>
     * @param startDate ��ʼ���ڣ�Date����
     * @param endDate ��ֹ���ڣ�Date����
     * @param unit ʱ������λ������ֵ("Y"--�� "M"--�� "D"--��)
     * @return ʱ����,���α���int
     */
    public static int calInterval(Date startDate, Date endDate, String unit)
    {
        int interval = 0;

        GregorianCalendar sCalendar = new GregorianCalendar();
        sCalendar.setTime(startDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar eCalendar = new GregorianCalendar();
        eCalendar.setTime(endDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

        if (unit.equals("Y"))
        {
            interval = eYears - sYears;
            if (eMonths < sMonths)
            {
                interval--;
            }
            else
            {
                if (eMonths == sMonths && eDays < sDays)
                {
                    interval--;
                    if (eMonths == 1)
                    { //���ͬ��2�£�У����������
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //�����ʼ�������꣬��ֹ�겻������
                            if (eDays == 28)
                            { //�����ֹ�겻�����꣬��2�µ����һ��28�գ���ô��һ
                                interval++;
                            }
                        }
                    }
                }
            }
        }
        if (unit.equals("M"))
        {
            interval = eYears - sYears;
//            interval = interval * 12;
            interval *= 12;

//            interval = eMonths - sMonths + interval;
            interval += eMonths - sMonths;
            if (eDays < sDays)
            {
                interval--;
                //eDays�������ĩ������Ϊ����һ����
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if (unit.equals("D"))
        {
//====del===liuxs===2006-09-09=====�޸����ڼ������©��================BGN=========
//            interval = eYears - sYears;
////            interval = interval * 365;
//            interval *= 365;
////            interval = eDaysOfYear - sDaysOfYear + interval;
//            interval += eDaysOfYear - sDaysOfYear;
//
//            // ��������
//            int n = 0;
//            eYears--;
//            if (eYears > sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    sYears++;
//                    n++;
//                }
//                int j = (eYears) % 4;
//                if (j == 0)
//                {
//                    eYears--;
//                    n++;
//                }
//                n += (eYears - sYears) / 4;
//            }
//            if (eYears == sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    n++;
//                }
//            }
//            interval += n;
//====del===liuxs===2006-09-09=====�޸����ڼ������©��================END=========
//====add===liuxs===2006-09-09=====�޸����ڼ������©��================BGN=========
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
//====add===liuxs===2006-09-09=====�޸����ڼ������©��================END=========
        }
        return interval;
    }

    /**
     * ͨ����ʼ���ں���ֹ���ڼ�����ʱ������λΪ������׼��ʱ������������ author: HST
     * ��ʼ���ڣ�(String,��ʽ��"YYYY-MM-DD")
     * @param cstartDate String
     * ��ֹ���ڣ�(String,��ʽ��"YYYY-MM-DD")
     * @param cendDate String
     * ʱ������λ������ֵ("Y"--�� "M"--�� "D"--��)
     * @param unit String
     * ʱ����,���α���int
     * @return int
     */
    public static int calInterval(String cstartDate, String cendDate,
                                  String unit)
    {
        FDate fDate = new FDate();
        Date startDate = fDate.getDate(cstartDate);
        Date endDate = fDate.getDate(cendDate);
        if (fDate.mErrors.needDealError())
        {
            return 0;
        }

        int interval = 0;

        GregorianCalendar sCalendar = new GregorianCalendar();
        sCalendar.setTime(startDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar eCalendar = new GregorianCalendar();
        eCalendar.setTime(endDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

        if (StrTool.cTrim(unit).equals("Y"))
        {
            interval = eYears - sYears;

            if (eMonths < sMonths)
            {
                interval--;
            }
            else
            {
                if (eMonths == sMonths && eDays < sDays)
                {
                    interval--;
                    if (eMonths == 1)
                    { //���ͬ��2�£�У����������
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //�����ʼ�������꣬��ֹ�겻������
                            if (eDays == 28)
                            { //�����ֹ�겻�����꣬��2�µ����һ��28�գ���ô��һ
                                interval++;
                            }
                        }
                    }
                }
            }
        }
        if (StrTool.cTrim(unit).equals("M"))
        {
            interval = eYears - sYears;
//            interval = interval * 12;
            interval *= 12;
//            interval = eMonths - sMonths + interval;
            interval += eMonths - sMonths;

            if (eDays < sDays)
            {
                interval--;
                //eDays�������ĩ������Ϊ����һ����
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if (StrTool.cTrim(unit).equals("D"))
        {
//====del===liuxs===2006-09-09=====�޸����ڼ������©��================BGN=========
//            interval = eYears - sYears;
////            interval = interval * 365;
//            interval *= 365;
////            interval = eDaysOfYear - sDaysOfYear + interval;
//            interval += eDaysOfYear - sDaysOfYear;
//
//            // ��������
//            int n = 0;
//            eYears--;
//            if (eYears > sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    sYears++;
//                    n++;
//                }
//                int j = (eYears) % 4;
//                if (j == 0)
//                {
//                    eYears--;
//                    n++;
//                }
//                n += (eYears - sYears) / 4;
//            }
//            if (eYears == sYears)
//            {
//                int i = sYears % 4;
//                if (i == 0)
//                {
//                    n++;
//                }
//            }
//            interval += n;
//====del===liuxs===2006-09-09=====�޸����ڼ������©��================END=========
//====add===liuxs===2006-09-09=====�޸����ڼ������©��================BGN=========
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
//====add===liuxs===2006-09-09=====�޸����ڼ������©��================END=========

        }
        return interval;
    }

    /**
     * ͨ����ʼ���ں���ֹ���ڼ�����ʱ������λΪ������׼��ʱ������Լ���� author: YangZhao��Minim
     * ��ʼ���ڣ�(String,��ʽ��"YYYY-MM-DD")
     * @param cstartDate String
     * ��ֹ���ڣ�(String,��ʽ��"YYYY-MM-DD")
     * @param cendDate String
     * ʱ������λ������ֵ("Y"--�� "M"--�� "D"--��)
     * @param unit String
     * ʱ����,���α���int
     * @return int
     */
//    public static int calInterval2(String cstartDate, String cendDate,
//                                   String unit)
//    {
//        FDate fDate = new FDate();
//        Date startDate = fDate.getDate(cstartDate);
//        Date endDate = fDate.getDate(cendDate);
//        if (fDate.mErrors.needDealError())
//        {
//            return 0;
//        }
//
//        int interval = 0;
//
//        GregorianCalendar sCalendar = new GregorianCalendar();
//        sCalendar.setTime(startDate);
//        int sYears = sCalendar.get(Calendar.YEAR);
//        int sMonths = sCalendar.get(Calendar.MONTH);
//        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
//
//        GregorianCalendar eCalendar = new GregorianCalendar();
//        eCalendar.setTime(endDate);
//        int eYears = eCalendar.get(Calendar.YEAR);
//        int eMonths = eCalendar.get(Calendar.MONTH);
//        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
//
//        if (StrTool.cTrim(unit).equals("Y"))
//        {
//            interval = eYears - sYears;
//
//            if (eMonths > sMonths)
//            {
//                interval++;
//            }
//            else
//            {
//                if (eMonths == sMonths && eDays > sDays)
//                {
//                    interval++;
//                    if (eMonths == 1)
//                    { //���ͬ��2�£�У����������
//                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
//                        { //�����ʼ�������꣬��ֹ�겻������
//                            if (eDays == 28)
//                            { //�����ֹ�겻�����꣬��2�µ����һ��28�գ���ô��һ
//                                interval--;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (StrTool.cTrim(unit).equals("M"))
//        {
//            interval = eYears - sYears;
////            interval = interval * 12;
//            interval *= 12;
////            interval = eMonths - sMonths + interval;
//            interval += eMonths - sMonths;
//
//            if (eDays > sDays)
//            {
//                interval++;
//                //sDays  �� eDays���������ĩ������Ϊ����һ���� //modified by zhangrong
//                int maxsDate = sCalendar.getActualMaximum(Calendar.DATE);
//                int maxeDate = eCalendar.getActualMaximum(Calendar.DATE);
//                if (sDays == maxsDate && eDays == maxeDate)
//                {
//                    interval--;
//                }
//            }
//        }
//        if (StrTool.cTrim(unit).equals("D"))
//        {
////====del===liuxs===2006-09-09=====�޸����ڼ������©��================BGN=========
////            interval = eYears - sYears;
//////            interval = interval * 365;
////            interval *= 365;
//////            interval = eDaysOfYear - sDaysOfYear + interval;
////            interval += eDaysOfYear - sDaysOfYear;
////
////            // ��������
////            int n = 0;
////            eYears--;
////            if (eYears > sYears)
////            {
////                int i = sYears % 4;
////                if (i == 0)
////                {
////                    sYears++;
////                    n++;
////                }
////                int j = (eYears) % 4;
////                if (j == 0)
////                {
////                    eYears--;
////                    n++;
////                }
////                n += (eYears - sYears) / 4;
////            }
////            if (eYears == sYears)
////            {
////                int i = sYears % 4;
////                if (i == 0)
////                {
////                    n++;
////                }
////            }
////            interval += n;
////====del===liuxs===2006-09-09=====�޸����ڼ������©��================END=========
////====add===liuxs===2006-09-09=====�޸����ڼ������©��================BGN=========
//            sCalendar.set(sYears, sMonths, sDays);
//            eCalendar.set(eYears, eMonths, eDays);
//            long lInterval = (eCalendar.getTime().getTime() -
//                              sCalendar.getTime().getTime()) / 86400000; //24*60*60*1000 һ���Ӧ�ĺ�����
//            interval = (int) lInterval;
////====add===liuxs===2006-09-09=====�޸����ڼ������©��================END=========
//        }
//        return interval;
//    }

    /**
     * ͨ����������ڿ��Եõ������µĵ�һ������һ������� author: LH
     * ���ڣ�(String,��ʽ��"YYYY-MM-DD")
     * @param tDate String
     * ���¿�ʼ�ͽ������ڣ�����String[2]
     * @return String[]
     */
//    public static String[] calFLDate(String tDate)
//    {
//        String MonDate[] = new String[2];
//        FDate fDate = new FDate();
//        Date CurDate = fDate.getDate(tDate);
//        GregorianCalendar mCalendar = new GregorianCalendar();
//        mCalendar.setTime(CurDate);
//        int Years = mCalendar.get(Calendar.YEAR);
//        int Months = mCalendar.get(Calendar.MONTH);
//        int FirstDay = mCalendar.getActualMinimum(Calendar.DAY_OF_MONTH);
//        int LastDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        mCalendar.set(Years, Months, FirstDay);
//        MonDate[0] = fDate.getString(mCalendar.getTime());
//        mCalendar.set(Years, Months, LastDay);
//        MonDate[1] = fDate.getString(mCalendar.getTime());
//        return MonDate;
//    }

    /**
     * ͨ����������ڿ��Եõ�������ֹ��(�����ӳ�������)
     * ��ʼ���ڣ�(String,��ʽ��"YYYY-MM-DD")
     * @param startDate String
     * @param strRiskCode String
     * @return String
     */
//    public static String calLapseDate(String startDate, String strRiskCode)
//    {
//        String returnDate = "";
////        Date tLapseDate = null;
//        FDate tFDate = new FDate();
//        int nDates;
//        int nExtendLapseDates;
//
//        //�����������������
//        if ((startDate == null) || startDate.trim().equals(""))
//        {
//            System.out.println("û����ʼ����,�������ֹ��ʧ��!");
//            return returnDate;
//        }
//
//        //��ȡ���ֽ���ʧЧ����
//        LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
//        tLMRiskPayDB.setRiskCode(strRiskCode);
//        LMRiskPaySet tLMRiskPaySet = tLMRiskPayDB.query();
//
//        if (tLMRiskPaySet.size() > 0)
//        {
//            if ((tLMRiskPaySet.get(1).getGracePeriodUnit() == null)
//                || tLMRiskPaySet.get(1).getGracePeriodUnit().equals(""))
//            {
//                //���ÿ�����ΪĬ��ֵ
//                System.out.println("ȱ�����ֽ���ʧЧ����!��Ĭ��ֵ����");
//                nDates = 60;
//                returnDate = calDate(startDate, nDates, "D", null);
//            }
//            else
//            {
//                //ȡ��ָ��������
//                nDates = tLMRiskPaySet.get(1).getGracePeriod();
//                returnDate = calDate(startDate, nDates,
//                                     tLMRiskPaySet.get(1).getGracePeriodUnit(), null);
//                //jdk1.4�Դ��ķ��������ݣ�����ַ���������
////                String[] tDate = returnDate.split("-");
//                //���½�λ�������վ���
//                //���ӿ�ֵ�ж� 2005-8-5 ����
//                if (tLMRiskPaySet.get(1).getGraceDateCalMode() != null)
//                {
//                    if (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("1"))
//                    {
////                    tLapseDate = tFDate.getDate(returnDate);
////                    tLapseDate.setMonth(tLapseDate.getMonth() + 1);
////                    tLapseDate.setDate(1);
////                    returnDate = tFDate.getString(tLapseDate);
//
//                        //�����ڵĲ��������ʹ��Calendar����
//                        GregorianCalendar tCalendar = new GregorianCalendar();
//                        tCalendar.setTime(tFDate.getDate(returnDate));
//                        //�·ݽ�λ�������վ���
//                        tCalendar.set(tCalendar.get(Calendar.YEAR),
//                                      tCalendar.get(Calendar.MONTH) + 1, 1);
//                        returnDate = tFDate.getString(tCalendar.getTime());
//                    }
//
//                    //�����λ��ֻ�������վ��ȣ��������¾���
//                    if (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("2"))
//                    {
////                    tLapseDate = tFDate.getDate(returnDate);
////                    tLapseDate.setYear(tLapseDate.getYear() + 1);
////                    tLapseDate.setDate(1);
////                    returnDate = tFDate.getString(tLapseDate);
//
//                        //�����ڵĲ��������ʹ��Calendar����
//                        GregorianCalendar tCalendar = new GregorianCalendar();
//                        tCalendar.setTime(tFDate.getDate(returnDate));
//                        //��ݽ�λ�������վ��ȣ��������¾���
//                        tCalendar.set(tCalendar.get(Calendar.YEAR) + 1,
//                                      tCalendar.get(Calendar.MONTH), 1);
//                        returnDate = tFDate.getString(tCalendar.getTime());
//                    }
//                }
//            }
//        }
//        else
//        {
//            //���ÿ�����ΪĬ��ֵ
//            System.out.println("û�����ֽ���ʧЧ����!��Ĭ��ֵ����");
//            nDates = 60;
//            returnDate = calDate(startDate, nDates, "D", null);
//        }
//
//        //ȡ�ÿ������ӳ���
//        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
//        tLDSysVarDB.setSysVar("ExtendLapseDates");
//        if (!tLDSysVarDB.getInfo())
//        {
//            nExtendLapseDates = 0;
//        }
//        else
//        {
//            nExtendLapseDates = Integer.parseInt(tLDSysVarDB.getSchema().
//                                                 getSysVarValue());
//            returnDate = calDate(returnDate, nExtendLapseDates, "D", null);
//        }
//
//        return returnDate;
//    }

    /**
     * �õ�Ĭ�ϵ�JDBCUrl
     * @return JDBCUrl
     */
    public static JdbcUrl getDefaultUrl()
    {
        JdbcUrl tUrl = new JdbcUrl();
        return tUrl;
    }

    /**
     * ���ַ�������,��sourString��<br>����</br>��cChar����cLen���ȵ��ַ���,����ַ�����������������
     * <p><b>Example: </b><p>
     * <p>RCh("Minim", "0", 10) returns "Minim00000"<p>
     * @param sourString Դ�ַ���
     * @param cChar �����õ��ַ�
     * @param cLen �ַ�����Ŀ�곤��
     * @return �ַ���
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
        tReturn = sourString.trim() + tReturn.trim();
        return tReturn;
    }

    /**
     * ���ַ�������,��sourString��<br>ǰ��</br>��cChar����cLen���ȵ��ַ���,����ַ�����������������
     * <p><b>Example: </b><p>
     * <p>LCh("Minim", "0", 10) returns "00000Minim"<p>
     * @param sourString Դ�ַ���
     * @param cChar �����õ��ַ�
     * @param cLen �ַ�����Ŀ�곤��
     * @return �ַ���
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
     * �Ƚϻ�ȡ�����нϺ��һ��
     * @param date1 String
     * @param date2 String
     * @return String
     */
//    public static String getLaterDate(String date1, String date2)
//    {
//        try
//        {
//            date1 = StrTool.cTrim(date1);
//            date2 = StrTool.cTrim(date2);
//            if (date1.equals(""))
//            {
//                return date2;
//            }
//            if (date2.equals(""))
//            {
//                return date1;
//            }
//            FDate fd = new FDate();
//            Date d1 = fd.getDate(date1);
//            Date d2 = fd.getDate(date2);
//            if (d1.after(d2))
//            {
//                return date1;
//            }
//            return date2;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return "";
//        }
//
//    }

    /**
     * �Ƚϻ�ȡ�����н����һ��
     * @param date1 String
     * @param date2 String
     * @return String
     */
//    public static String getBeforeDate(String date1, String date2)
//    {
//        try
//        {
//            date1 = StrTool.cTrim(date1);
//            date2 = StrTool.cTrim(date2);
//            if (date1.equals(""))
//            {
//                return date2;
//            }
//            if (date2.equals(""))
//            {
//                return date1;
//            }
//            FDate fd = new FDate();
//            Date d1 = fd.getDate(date1);
//            Date d2 = fd.getDate(date2);
//            if (d1.before(d2))
//            {
//                return date1;
//            }
//            return date2;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return "";
//        }
//
//    }

    /**
     * �õ���ǰϵͳ���� author: YT
     * @return ��ǰ���ڵĸ�ʽ�ַ���,���ڸ�ʽΪ"yyyy-MM-dd"
     */
    public static String getCurrentDate()
    {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date today = new Date();
        String tString = df.format(today);
        return tString;
    }

    /**
     * �õ���ǰϵͳʱ�� author: YT
     * @return ��ǰʱ��ĸ�ʽ�ַ�����ʱ���ʽΪ"HH:mm:ss"
     */
    public static String getCurrentTime()
    {
        String pattern = "HH:mm:ss";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date today = new Date();
        String tString = df.format(today);
        return tString;
    }

    /**
     * �õ���ˮ��ǰ�� author: YT
     * @param comCode ��������
     * @return ��ˮ�ŵ�ǰ���ַ���
     */
    public static String getNoLimit(String comCode)
    {
        comCode = comCode.trim();
        int tLen = comCode.length();
        if (tLen > 6)
        {
            comCode = comCode.substring(0, 6);
        }
        if (tLen < 6)
        {
            comCode = RCh(comCode, "0", 6);
        }
        String tString = "";
        tString = comCode + getCurrentDate().substring(0, 4);
        return tString;
    }

    /**
     * picc��ȡ�����������ȡ�������ĵ�3-6λ����������+����������
     * �ټ������ڱ������λ����λ������ 052203
     * @param comCode String
     * @return String
     */
    public static String getPiccNoLimit(String comCode)
    {
        comCode = comCode.trim();
        System.out.println("comCode :" + comCode);
        int tLen = comCode.length();
        if (tLen == 8)
        {
            comCode = comCode.substring(2, 6);
        }
        if (tLen == 4)
        {
            comCode = comCode.substring(2, 4) + "00";
        }
        System.out.println("SubComCode :" + comCode);
        String tString = "";
        tString = comCode + getCurrentDate().substring(2, 4)
                  + getCurrentDate().substring(5, 7)
                  + getCurrentDate().substring(8, 10);
        System.out.println("PubFun getPiccNoLimit : " + tString);
        return tString;
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
        int i = 0, i_Start = 0;
//        int j_End = 0;
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
     * �����ֽ��ת��Ϊ���Ĵ�д��� author: HST
     * @param money ���ֽ��(double)
     * @return ���Ĵ�д���(String)
     */
    public static String getChnMoney(double money)
    {
        String ChnMoney = "";
        String s0 = "";

        // ��ԭ���汾�ĳ����У�getChnMoney(585.30)�õ���������585.29��

        if (money == 0.0)
        {
            ChnMoney = "��Ԫ��";
            return ChnMoney;
        }

        if (money < 0)
        {
            s0 = "��";
            money *= ( -1);
        }

        String sMoney = new DecimalFormat("0").format(money * 100);

        int nLen = sMoney.length();
        String sInteger;
        String sDot;
        if (nLen < 2)
        {
            //add by JL at 2004-9-14
            sInteger = "";
            if (nLen == 1)
            {
                sDot = "0" + sMoney.substring(nLen - 1, nLen);
            }
            else
            {
                sDot = "0";
            }
        }
        else
        {
            sInteger = sMoney.substring(0, nLen - 2);
            sDot = sMoney.substring(nLen - 2, nLen);
        }

        String sFormatStr = PubFun.formatStr(sInteger);

        String s1 = PubFun.getChnM(sFormatStr.substring(0, 4), "��");

        String s2 = PubFun.getChnM(sFormatStr.substring(4, 8), "��");

        String s3 = PubFun.getChnM(sFormatStr.substring(8, 12), "");

        String s4 = PubFun.getDotM(sDot);

        if (s1.length() > 0 && s1.substring(0, 1).equals("0"))
        {
            s1 = s1.substring(1,
                              s1.length());
        }
        if (s1.length() > 0 &&
            s1.substring(s1.length() - 1, s1.length()).equals("0")
            && s2.length() > 0 && s2.substring(0, 1).equals("0"))
        {
            s1 = s1.substring(0, s1.length() - 1);
        }
        if (s2.length() > 0 &&
            s2.substring(s2.length() - 1, s2.length()).equals("0")
            && s3.length() > 0 && s3.substring(0, 1).equals("0"))
        {
            s2 = s2.substring(0, s2.length() - 1);
        }
        if (s4.equals("00"))
        {
            s4 = "";
            if (s3.length() > 0 &&
                s3.substring(s3.length() - 1, s3.length()).equals("0"))
            {
                s3 = s3.substring(0, s3.length() - 1);
            }
        }
        if (s3.length() > 0 &&
            s3.substring(s3.length() - 1, s3.length()).equals("0")
            && s4.length() > 0 && s4.substring(0, 1).equals("0"))
        {
            s3 = s3.substring(0, s3.length() - 1);
        }
        if (s4.length() > 0 &&
            s4.substring(s4.length() - 1, s4.length()).equals("0"))
        {
            s4 = s4.substring(0, s4.length() - 1);
        }
        if (s3.equals("0"))
        {
            s3 = "";
            s4 = "0" + s4;
        }

        ChnMoney = s0 + s1 + s2 + s3 + "Ԫ" + s4;
        if (ChnMoney.substring(0, 1).equals("0"))
        {
            ChnMoney = ChnMoney.substring(1,
                                          ChnMoney.length());
        }
        for (int i = 0; i < ChnMoney.length(); i++)
        {
            if (ChnMoney.substring(i, i + 1).equals("0"))
            {
                ChnMoney = ChnMoney.substring(0, i) + "��" +
                           ChnMoney.substring(i + 1, ChnMoney.length());
            }
        }

        if (sDot.substring(1, 2).equals("0"))
        {
            ChnMoney += "��";
        }

        return ChnMoney;
    }

    /**
     * �õ�money�ĽǷ���Ϣ
     * @param sIn String
     * @return String
     */
    private static String getDotM(String sIn)
    {
        String sMoney = "";
        if (!sIn.substring(0, 1).equals("0"))
        {
            sMoney += getNum(sIn.substring(0, 1)) + "��";
        }
        else
        {
            sMoney += "0";
        }
        if (!sIn.substring(1, 2).equals("0"))
        {
            sMoney += getNum(sIn.substring(1, 2)) + "��";
        }
        else
        {
            sMoney += "0";
        }

        return sMoney;
    }

    /**
     * ���Ǫ���ۡ�ʰ�ȵ�λ��Ϣ
     * @param strUnit String
     * @param digit String
     * @return String
     */
    private static String getChnM(String strUnit, String digit)
    {
        String sMoney = "";
        boolean flag = false;

        if (strUnit.equals("0000"))
        {
            sMoney += "0";
            return sMoney;
        }
        if (!strUnit.substring(0, 1).equals("0"))
        {
            sMoney += getNum(strUnit.substring(0, 1)) + "Ǫ";
        }
        else
        {
            sMoney += "0";
            flag = true;
        }
        if (!strUnit.substring(1, 2).equals("0"))
        {
            sMoney += getNum(strUnit.substring(1, 2)) + "��";
            flag = false;
        }
        else
        {
            if (!flag)
            {
                sMoney += "0";
                flag = true;
            }
        }
        if (!strUnit.substring(2, 3).equals("0"))
        {
            sMoney += getNum(strUnit.substring(2, 3)) + "ʰ";
            flag = false;
        }
        else
        {
            if (!flag)
            {
                sMoney += "0";
                flag = true;
            }
        }
        if (!strUnit.substring(3, 4).equals("0"))
        {
            sMoney += getNum(strUnit.substring(3, 4));
        }
        else
        {
            if (!flag)
            {
                sMoney += "0";
                flag = true;
            }
        }

        if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0"))
        {
            sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim() +
                     "0";
        }
        else
        {
            sMoney += digit.trim();
        }
        return sMoney;
    }

    /**
     * ��ʽ���ַ�
     * @param sIn String
     * @return String
     */
    private static String formatStr(String sIn)
    {
        int n = sIn.length();
        String sOut = sIn;
//        int i = n % 4;

        for (int k = 1; k <= 12 - n; k++)
        {
            sOut = "0" + sOut;
        }
        return sOut;
    }

    /**
     * ��ȡ���������ֺ��������ֵĶ�Ӧ��ϵ
     * @param value String
     * @return String
     */
    private static String getNum(String value)
    {
        String sNum = "";
        Integer I = new Integer(value);
        int iValue = I.intValue();
        switch (iValue)
        {
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
     * ���һ���ַ���������С�����ȫΪ�㣬��ȥ��С���㼰��
     * @param Value String
     * @return String
     */
    public static String getInt(String Value)
    {
        if (Value == null)
        {
            return null;
        }
        String result = "";
        boolean mflag = true;
        int m = 0;
        m = Value.lastIndexOf(".");
        if (m == -1)
        {
            result = Value;
        }
        else
        {
            for (int i = m + 1; i <= Value.length() - 1; i++)
            {
                if (Value.charAt(i) != '0')
                {
                    result = Value;
                    mflag = false;
                    break;
                }
            }
            if (mflag)
            {
                result = Value.substring(0, m);
            }
        }
        return result;
    }

    /**
     * �õ�����ֵ
     * @param aValue double
     * @return double
     */
    public static double getApproximation(double aValue)
    {
        if (java.lang.Math.abs(aValue) <= 0.01)
        {
            aValue = 0;
        }
        return aValue;
    }

    /**
     * ����������Ϊ������ţ�����ַ���
     * @param strMain String
     * @param strDelimiters String
     * ʧ�ܷ���NULL
     * @return String[]
     */
    public static String[] split(String strMain, String strDelimiters)
    {
        int i;
        int intIndex = 0; //��¼�ָ���λ�ã���ȡ���Ӵ�
        Vector vResult = new Vector(); //�洢�Ӵ�������
        String strSub = ""; //����Ӵ����м����

        strMain = strMain.trim();

        //�����ַ����ȷָ�������Ҫ�̵Ļ�,�򷵻ؿ��ַ���
        if (strMain.length() <= strDelimiters.length())
        {
            System.out.println("�ָ��������ȴ��ڵ������ַ������ȣ����ܽ��в�֣�");
            return null;
        }

        //ȡ����һ���ָ����������е�λ��
        intIndex = strMain.indexOf(strDelimiters);

        //���������Ҳ����ָ���
        if (intIndex == -1)
        {
            String[] arrResult =
                    {
                    strMain};
            return arrResult;
        }

        //�ָ�������������
        while (intIndex != -1)
        {
            strSub = strMain.substring(0, intIndex);
            if (intIndex != 0)
            {
                vResult.add(strSub);
            }
            else
            {
                //break;
                vResult.add("");
            }

            strMain = strMain.substring(intIndex + strDelimiters.length()).trim();
            intIndex = strMain.indexOf(strDelimiters);
        }

        //�����ĩ���Ƿָ�����ȡ�����ַ���
//        if (!strMain.equals("") && strMain != null)
        if (!strMain.equals(""))
        {
            vResult.add(strMain);
        }

        String[] arrResult = new String[vResult.size()];
        for (i = 0; i < vResult.size(); i++)
        {
            arrResult[i] = (String) vResult.get(i);
        }

        return arrResult;
    }

    /**
     * �������־���
     * ��Ҫ��ʽ��������
     * @param value float
     * ����������0.00��ʾ��ȷ��С�������λ��
     * @param precision String
     * @return double
     */
    public static double setPrecision(float value, String precision)
    {
        return Float.parseFloat(new DecimalFormat(precision).format(value));
    }

    /**
     * �������־���
     * ��Ҫ��ʽ��������
     * @param value double
     * ����������0.00��ʾ��ȷ��С�������λ��
     * @param precision String
     * @return double
     */
    public static double setPrecision(double value, String precision)
    {
        return Double.parseDouble(new DecimalFormat(precision).format(value));
    }

    /**
     * ��Դ Schema �����е����ݿ�����Ŀ�� Schema ������
     * ��ȫ C��P ����ʱ�����õ�
     * @param     Schema
     * @param     Schema
     */
//    public static void copySchema(Schema destSchema, Schema srcSchema)
//    {
//        if (destSchema != null && srcSchema != null)
//        {
//            try
//            {
//                Reflections tReflections = new Reflections();
//                tReflections.transFields(destSchema, srcSchema);
//                tReflections = null;
//            }
//            catch (Exception ex)
//            {
//                System.out.println("\t@> PubFun.copySchema() : Reflections ��������ʧ�ܣ�");
//                ex.printStackTrace();
//            }
//        }
//    }

    /**
     * ��schemaset���󿽱�һ�ݷ���
     * @param srcSet SchemaSet
     * @return SchemaSet
     */
//    public static SchemaSet copySchemaSet(SchemaSet srcSet)
//    {
//        try
//        {
//            if (srcSet != null && srcSet.size() > 0)
//            {
//                if (srcSet.getObj(1) == null)
//                {
//                    return null;
//                }
//                Class cls = srcSet.getClass();
//                Schema schema = (Schema) srcSet.getObj(1).getClass().
//                                newInstance();
//                SchemaSet obj = (SchemaSet) cls.newInstance();
//                obj.add(schema);
//                Reflections tReflections = new Reflections();
//                tReflections.transFields(obj, srcSet);
//                tReflections = null;
//                return obj;
//            }
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    /**
     * ����LP��LC������
     * @param source Schema
     * @param object Schema
     * @return boolean
     */
//    public static boolean exchangeSchema(Schema source, Schema object)
//    {
//        try
//        {
//            //��LP������ݴ��ݵ�LC��
//            Reflections tReflections = new Reflections();
//            tReflections.transFields(object, source);
//
//            //��ȡһ�����ݿ�����DB
//            Method m = object.getClass().getMethod("getDB", null);
//            Schema schemaDB = (Schema) m.invoke(object, null);
//            //��ΪLP����LC��ֻ��EdorNo��EdorType�����ؼ��ֵĲ�����Կ���Ψһ��ȡLC���Ӧ��¼
//            m = schemaDB.getClass().getMethod("getInfo", null);
//            m.invoke(schemaDB, null);
//            m = schemaDB.getClass().getMethod("getSchema", null);
//            object = (Schema) m.invoke(schemaDB, null);
//
//            //��LC�����ݱ��ݵ���ʱ��
//            m = object.getClass().getMethod("getSchema", null);
//            Schema tSchema = (Schema) m.invoke(object, null);
//
//            //����LP��LC������
//            tReflections.transFields(object, source);
//            tReflections.transFields(source, tSchema);
//
//            return true;
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            return false;
//        }
//    }

    /**
     * ���ɸ��µ�sql�б�
     * @param tables String[]
     * @param condition String
     * @param wherepart String
     * @return Vector
     */
    public static Vector formUpdateSql(String[] tables, String condition,
                                       String wherepart)
    {
        Vector sqlVec = new Vector();
        for (int i = 0; i < tables.length; i++)
        {
            sqlVec.add("update " + tables[i] + " set " + condition + " where " +
                       wherepart);
        }
        return sqlVec;
    }

    /**
     * ���˺�ǰ��0ȥ��
     * @param sIn String
     * @return String
     */
    public static String DeleteZero(String sIn)
    {
        int n = sIn.length();
        String sOut = sIn;

        while (sOut.substring(0, 1).equals("0") && n > 1)
        {
            sOut = sOut.substring(1, n);
            n = sOut.length();
            System.out.println(sOut);
        }

        if (sOut.equals("0"))
        {
            return "";
        }
        else
        {
            return sOut;
        }
    }

    /**
     * ת��JavaScript�������˵������ַ�
     * @param s String
     * @return String
     */
    public static String changForJavaScript(String s)
    {
        char[] arr = s.toCharArray();
        s = "";
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == '"' || arr[i] == '\'' || arr[i] == '\n')
            {
                s += "\\";
            }

            s += arr[i];
        }

        return s;
    }

    /**
     * ת��JavaScript�������˵������ַ�
     * @param s String
     * @return String
     */
    public static String changForHTML(String s)
    {
        char[] arr = s.toCharArray();
        s = "";

        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == '"' || arr[i] == '\'')
            {
                s += "\\";
            }

            if (arr[i] == '\n')
            {
                s += "<br>";
                continue;
            }

            s += arr[i];
        }

        return s;
    }

    public static String getClassFileName(Object o)
    {
        String fileName = o.getClass().getName();
        fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileName;
    }

    public static void out(Object o, String s)
    {
        System.out.println(PubFun.getClassFileName(o) + " : " + s);
    }

    /**
     * ���㱣�����
     * @param cstartDate String
     * @param cendDate String
     * @return int
     */
//    public static int calPolYear(String cstartDate, String cendDate)
//    {
//        FDate fDate = new FDate();
//        Date startDate = fDate.getDate(cstartDate);
//        Date endDate = fDate.getDate(cendDate);
//        if (fDate.mErrors.needDealError())
//        {
//            return 0;
//        }
//
//        int interval = 0;
//
//        GregorianCalendar sCalendar = new GregorianCalendar();
//        sCalendar.setTime(startDate);
//        int sYears = sCalendar.get(Calendar.YEAR);
////        int sMonths = sCalendar.get(Calendar.MONTH);
////        int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
//        int sDaysOfYear = sCalendar.get(Calendar.DAY_OF_YEAR);
//
//        GregorianCalendar eCalendar = new GregorianCalendar();
//        eCalendar.setTime(endDate);
//        int eYears = eCalendar.get(Calendar.YEAR);
////        int eMonths = eCalendar.get(Calendar.MONTH);
////        int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
//        int eDaysOfYear = eCalendar.get(Calendar.DAY_OF_YEAR);
//
//        interval = eYears - sYears;
////        interval = interval * 365;
//        interval *= 365;
////        interval = eDaysOfYear - sDaysOfYear + interval;
//        interval += eDaysOfYear - sDaysOfYear;
//
//        // ��������
//        int n = 0;
//        eYears--;
//        if (eYears > sYears)
//        {
//            int i = sYears % 4;
//            if (i == 0)
//            {
//                sYears++;
//                n++;
//            }
//            int j = (eYears) % 4;
//            if (j == 0)
//            {
//                eYears--;
//                n++;
//            }
//            n += (eYears - sYears) / 4;
//        }
//        if (eYears == sYears)
//        {
//            int i = sYears % 4;
//            if (i == 0)
//            {
//                n++;
//            }
//        }
//        interval += n;
//
//        int x = 365;
//        int PolYear = 1;
//        while (x < interval)
//        {
////            x = x + 365;
//            x += 365;
////            PolYear = PolYear + 1;
//            PolYear += 1;
//        }
//
//        return PolYear;
//    }

    /**
     * ͨ�����֤�źŻ�ȡ��������
     * @param IdNo String
     * @return String
     */
    public static String getBirthdayFromId(String IdNo)
    {
        String tIdNo = StrTool.cTrim(IdNo);
        String birthday = "";
        if (tIdNo.length() != 15 && tIdNo.length() != 18)
        {
            return "";
        }
        if (tIdNo.length() == 18)
        {
            birthday = tIdNo.substring(6, 14);
            birthday = birthday.substring(0, 4) + "-" + birthday.substring(4, 6) +
                       "-" + birthday.substring(6);
        }
        if (tIdNo.length() == 15)
        {
            birthday = tIdNo.substring(6, 12);
            birthday = birthday.substring(0, 2) + "-" + birthday.substring(2, 4) +
                       "-" + birthday.substring(4);
            birthday = "19" + birthday;
        }
        return birthday;

    }

    /**
     * ͨ�����֤�Ż�ȡ�Ա�
     * @param IdNo String
     * @return String
     */
    public static String getSexFromId(String IdNo)
    {
        String tIdNo = StrTool.cTrim(IdNo);
        if (tIdNo.length() != 15 && tIdNo.length() != 18)
        {
            return "";
        }
        String sex = "";
        if (tIdNo.length() == 15)
        {
            sex = tIdNo.substring(14, 15);
        }
        else
        {
            sex = tIdNo.substring(16, 17);
        }
        try
        {
            int iSex = Integer.parseInt(sex);
//            iSex = iSex % 2;
            iSex %= 2;
            if (iSex == 0)
            {
                return "1";
            }
            if (iSex == 1)
            {
                return "0";
            }
        }
        catch (Exception ex)
        {
            return "";
        }
        return "";
    }

    /**
     * У��¼��Ĺ�������Ƿ��ǲ���Ա��½����ͬ�����¼�������Ϊ�շ���false
     * @param cGlobalInput GlobalInput
     * @param RunScript String
     * @return boolean
     */
    public static boolean checkManageCom(String InputManageCom,
                                         String UserManageCom)
    {
        if (InputManageCom == null || InputManageCom.trim().equals("") ||
            UserManageCom == null || UserManageCom.trim().equals(""))
            return false;
        String sqlcheck =
                "select nvl((select 'Y' from ldsysvar where sysvar = 'onerow' and trim(" +
                InputManageCom + ") like trim(" + UserManageCom +
                ")||'%'),'N') from ldsysvar where sysvar = 'onerow'";
        ExeSQL tExeSQL = new ExeSQL();
        String result = tExeSQL.getOneValue(sqlcheck);
        if (result.equals("Y"))
            return true;
        else
            return false;
    }

    /**
     * �û�ҳ��Ȩ���ж�
     * @param cGlobalInput GlobalInput
     * @param RunScript String
     * @return boolean
     */
//    public static boolean canIDo(GlobalInput cGlobalInput, String RunScript)
//    {
//        String Operator = cGlobalInput.Operator;
////        String ComCode = cGlobalInput.ComCode;
////        String ManageCom = cGlobalInput.ManageCom;
//        //ͨ���û������ѯ�û�ҳ��Ȩ�޼���,NodeSign = 2Ϊ�û�ҳ��Ȩ�޲˵���־
//        String sqlStr = "select * from LDMenu";
//        sqlStr = sqlStr +
//                 "where NodeSign = 2 and RunScript = '" + RunScript + "' ";
//        sqlStr = sqlStr +
//                 "and NodeCode in ( select NodeCode from LDMenuGrpToMenu ";
//        sqlStr = sqlStr +
//                 "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ";
//        sqlStr = sqlStr +
//                 "where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = '";
//        sqlStr = sqlStr + Operator;
//        sqlStr = sqlStr + "') ) ) order by nodeorder";
//
//        ExeSQL tExeSQL = new ExeSQL();
//        SSRS tSSRS = tExeSQL.execSQL(sqlStr);
//
//        if (tSSRS == null || tSSRS.equals(""))
//        {
//            return false;
//        }
//        return true;
//    }


    /*************************************************************************
       ���������ܣ������������Զ����ж�У�顣ǰ���� ���뽫У���SQL�ִ��Ѿ����� LMCalMode����
       ���������
               CalCode----------��ȡУ��SQL�Ĵ���;
               TransferData-----������У��ʱУ���ִ��������
       ���ز�����True--У��ɹ�[У���ִ�����ֵΪ��1��] ��False---У��ʧ�ܣ�
       �����ж�ҵ��Ա�������ˣ��Ƿ��������ʸ�,�����˴���Ϊ��02281075
          У���ִ�:  select case salequaf when 'N' then '0' else '1' end from laagent where agentcode='?AgentCode?'
          ��Ҫ����Ĳ���:
               (1)��CalCode=��AgSale����
               (2)�������˴���<AgentCode>,���轫�����˴������ TransferData
          ���÷���;
          String tCalCode ="AgSale"; //�����ִ��� lmcalmode ���ȡ У���SQL�ִ�
          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("AgentCode","02281075");//У���ִ��������
          if(PubFun.userDefinedCheck(tCalCode,tTransferData)==false)
          {
               System.out.println("У��ʧ��");
               return false;
          }
     **************************************************************************/
//    public static boolean userDefinedCheck(String mCalCode,
//                                           TransferData mTransferData)
//    {
//        //
//        String tCalCode = ""; //��ȡ����� CalCode ���룬���ڵõ�У���SQL���
//        TransferData tTransferData = new TransferData();
//        tTransferData = mTransferData; //��ȡУ���������<>
//        //�жϴ��������Ƿ���ȷ
//        if (mCalCode == null || mCalCode.equals(""))
//        {
//            return false;
//        }
//        else
//        {
//            tCalCode = mCalCode.trim();
//        }
//        //�жϴ���TransferData<����У���������>�Ƿ���ȷ
//        if (tTransferData == null)
//        {
//            return false;
//        }
//        //���µ��� Calculator �࣬��У���ж�
//        Calculator mCalculator = new Calculator();
//        mCalculator.setCalCode(tCalCode);
//        Vector tVector = new Vector();
//        tVector = tTransferData.getValueNames();
//        if (tVector.size() > 0)
//        {
//            //���Ҫ�����Ʋ���ֵ��Calculator����
//            String mValue = "";
//            for (int i = 0; i < tVector.size(); i++)
//            {
//                String tFactorName = new String();
//                String tFactorValue = new String();
//                tFactorName = (String) tVector.get(i);
//                tFactorValue = (String) tTransferData.getValueByName(
//                        tFactorName);
//                mCalculator.addBasicFactor(tFactorName, tFactorValue);
//            }
//            mValue = mCalculator.calculate();
//            if (mValue != null && mValue.equals("1"))
//            {
//                return true;
//            }
//            else
//            {
//                return false;
//            }
//        }
//        else
//        {
//            return false;
//        }
//    }

    /**
     * ȡ��sequenceno��ֵ
     * @param type String ���к�����
     * @return String ���к�,���������"-1"
     */
    public static String getSeqNo(String type)
    {
        String ret = "-1";

        ExeSQL es = new ExeSQL();

        ret = es.getOneValue("select " + type + ".nextval from dual");
        return ret;
    }


    /**
     * ��������������
     * @param args String[]
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
//        String t="1112000*-";
//        System.out.println(t.toUpperCase());
//        PubFun tPunFun = new PubFun();
//        String test = tPunFun.calLapseDate("2004-02-02", "111502");
//	ExeSQL es = new ExeSQL();
//	
    	System.out.println(calInterval("1995-05-29","2015-05-28","Y"));
//
//		String ret = es
//				.getOneValue("select 1 from BANKANDINSURERCODEMAPPING ");
//		 System.out.println(ret);
//		 
//		 LCAppntSchema mLCAppntSchema = new LCAppntSchema();
//		 mLCAppntSchema.setContNo("2");
//	        //mLCAppntSchema.setAppntNo(mLCContSchema.getAppntNo());
//	        LCAppntDB tLCAppntDB = new LCAppntDB();
//	        tLCAppntDB.setSchema(mLCAppntSchema);
//	        tLCAppntDB.getInfo();
//	        if (tLCAppntDB.mErrors.needDealError()) {
//	            CError tError = new CError();
//	            tError.moduleName = "YBTContBL";
//	            tError.functionName = "getAppntInfo";
//	            tError.errorMessage = "��ѯͶ��������ʧ��!";
//	         
//
//	            	        }
//	        mLCAppntSchema.setSchema(tLCAppntDB);
//	        System.out.println ("Ͷ���˿ͻ��ţ�" + mLCAppntSchema.getAppntNo());
//		 
//		 
//		 new
    }
}
