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
 * <p>Title: Web业务系统</p>
 * <p>Description:业务系统的公共业务处理函数
 * 该类包含所有业务处理中的公共函数，和以前系统中的funpub.4gl
 * 文件相对应。在这个类中，所有的函数都采用Static的类型，所有需要的数据都是
 * 通过参数传入的，在本类中不采用通过属性传递数据的方法。 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 */
public class PubFun
{

    public PubFun() {}

    /**
     * 判断是否为闰年
     * XinYQ added on 2006-09-25
     */
    public static boolean isLeapYear(int nYear)
    {
        boolean ResultLeap = false;
        ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
        return ResultLeap;
    }

    /**
     * 计算日期的函数 author: HST
     * 参照日期指当按照年月进行日期的计算的时候，参考的日期，如下例，结果返回2002-03-31
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
     * @param baseDate 起始日期
     * @param interval 时间间隔
     * @param unit 时间间隔单位
     * @param compareDate 参照日期
     * @return Date类型变量
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

            //XinYQ added on 2006-09-25 : 修正闰年闰月和月底天数,和Oracle保持一致
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
     * 重载计算日期，参数见楼上，add by Minim
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
        //设置起始日期格式
        mCalendar.setTime(baseDate);
        if (unit.equals("Y"))
        {
            mCalendar.add(Calendar.YEAR, interval);
        }
        if (unit.equals("M"))
        {
            //执行月份增减
            mCalendar.add(Calendar.MONTH, interval);
        }
        if (unit.equals("D"))
        {
            mCalendar.add(Calendar.DATE, interval);
        }

        if (compareDate != null)
        {
            GregorianCalendar cCalendar = new GregorianCalendar();
            //设置坐标日期
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
                    //取当前月的最后一天日期
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
     * 生成付费方式，add by GaoHT
     * @param SSRS nSSRS
     * @return String
     * 按收费方式生成该笔退费的退费方式
     */

    public static String PayModeTransform(SSRS nSSRS)
    {
        try
        {
            int Cash = 0; //现金
            int Cheque = 0; //支票
            int Bank = 0; //银行划款
            int Pos = 0; //Pos收款
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
            System.out.println("临时付费方式中 现金:::::::::（" + Cash + "） 笔");
            System.out.println("临时付费方式中 支票:::::::::（" + Cheque + "） 笔");
            System.out.println("临时付费方式中 POS:::::::::（" + Pos + "） 笔");
            System.out.println("临时付费方式中 代收:::::::::（" + Bank + "） 笔");
            System.out.println(
                    "---------------------根据优先级决定付费方式--------------------");

            if (Cheque > 0) //支票优先级最高
            {
                tOutPayMode = "3";
            }
            else if (Bank > 0) //银行代付其次
            {
                tOutPayMode = "4";
            }
            else if (Cash > 0) //其他为现金
            {
                tOutPayMode = "1";
            }
            else if (Pos > 0) //只有一个pos
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
     * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
     * <p><b>Example: </b><p>
     * <p>参照calInterval(String  cstartDate, String  cendDate, String unit)，前两个变量改为日期型即可<p>
     * @param startDate 起始日期，Date变量
     * @param endDate 终止日期，Date变量
     * @param unit 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
     * @return 时间间隔,整形变量int
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
                    { //如果同是2月，校验润年问题
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //如果起始年是润年，终止年不是润年
                            if (eDays == 28)
                            { //如果终止年不是润年，且2月的最后一天28日，那么补一
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
                //eDays如果是月末，则认为是满一个月
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if (unit.equals("D"))
        {
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
//            interval = eYears - sYears;
////            interval = interval * 365;
//            interval *= 365;
////            interval = eDaysOfYear - sDaysOfYear + interval;
//            interval += eDaysOfYear - sDaysOfYear;
//
//            // 处理润年
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
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
        }
        return interval;
    }

    /**
     * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，舍弃法 author: HST
     * 起始日期，(String,格式："YYYY-MM-DD")
     * @param cstartDate String
     * 终止日期，(String,格式："YYYY-MM-DD")
     * @param cendDate String
     * 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
     * @param unit String
     * 时间间隔,整形变量int
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
                    { //如果同是2月，校验润年问题
                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
                        { //如果起始年是润年，终止年不是润年
                            if (eDays == 28)
                            { //如果终止年不是润年，且2月的最后一天28日，那么补一
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
                //eDays如果是月末，则认为是满一个月
                int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
                if (eDays == maxDate)
                {
                    interval++;
                }
            }
        }
        if (StrTool.cTrim(unit).equals("D"))
        {
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
//            interval = eYears - sYears;
////            interval = interval * 365;
//            interval *= 365;
////            interval = eDaysOfYear - sDaysOfYear + interval;
//            interval += eDaysOfYear - sDaysOfYear;
//
//            // 处理润年
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
//====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
            sCalendar.set(sYears, sMonths, sDays);
            eCalendar.set(eYears, eMonths, eDays);
            long lInterval = (eCalendar.getTime().getTime() -
                              sCalendar.getTime().getTime()) / 86400000;
            interval = (int) lInterval;
//====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========

        }
        return interval;
    }

    /**
     * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，约进法 author: YangZhao，Minim
     * 起始日期，(String,格式："YYYY-MM-DD")
     * @param cstartDate String
     * 终止日期，(String,格式："YYYY-MM-DD")
     * @param cendDate String
     * 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
     * @param unit String
     * 时间间隔,整形变量int
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
//                    { //如果同是2月，校验润年问题
//                        if ((sYears % 4) == 0 && (eYears % 4) != 0)
//                        { //如果起始年是润年，终止年不是润年
//                            if (eDays == 28)
//                            { //如果终止年不是润年，且2月的最后一天28日，那么减一
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
//                //sDays  和 eDays如果均是月末，则认为是满一个月 //modified by zhangrong
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
////====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
////            interval = eYears - sYears;
//////            interval = interval * 365;
////            interval *= 365;
//////            interval = eDaysOfYear - sDaysOfYear + interval;
////            interval += eDaysOfYear - sDaysOfYear;
////
////            // 处理润年
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
////====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
////====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
//            sCalendar.set(sYears, sMonths, sDays);
//            eCalendar.set(eYears, eMonths, eDays);
//            long lInterval = (eCalendar.getTime().getTime() -
//                              sCalendar.getTime().getTime()) / 86400000; //24*60*60*1000 一天对应的毫秒数
//            interval = (int) lInterval;
////====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
//        }
//        return interval;
//    }

    /**
     * 通过传入的日期可以得到所在月的第一天和最后一天的日期 author: LH
     * 日期，(String,格式："YYYY-MM-DD")
     * @param tDate String
     * 本月开始和结束日期，返回String[2]
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
     * 通过传入的日期可以得到宽限期止期(包括延长宽限期)
     * 起始日期，(String,格式："YYYY-MM-DD")
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
//        //检查输入数据完整性
//        if ((startDate == null) || startDate.trim().equals(""))
//        {
//            System.out.println("没有起始日期,计算宽限止期失败!");
//            return returnDate;
//        }
//
//        //获取险种交费失效描述
//        LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
//        tLMRiskPayDB.setRiskCode(strRiskCode);
//        LMRiskPaySet tLMRiskPaySet = tLMRiskPayDB.query();
//
//        if (tLMRiskPaySet.size() > 0)
//        {
//            if ((tLMRiskPaySet.get(1).getGracePeriodUnit() == null)
//                || tLMRiskPaySet.get(1).getGracePeriodUnit().equals(""))
//            {
//                //设置宽限期为默认值
//                System.out.println("缺少险种交费失效描述!按默认值计算");
//                nDates = 60;
//                returnDate = calDate(startDate, nDates, "D", null);
//            }
//            else
//            {
//                //取得指定宽限期
//                nDates = tLMRiskPaySet.get(1).getGracePeriod();
//                returnDate = calDate(startDate, nDates,
//                                     tLMRiskPaySet.get(1).getGracePeriodUnit(), null);
//                //jdk1.4自带的方法，根据－拆分字符串到数组
////                String[] tDate = returnDate.split("-");
//                //按月进位，舍弃日精度
//                //增加空值判断 2005-8-5 周磊
//                if (tLMRiskPaySet.get(1).getGraceDateCalMode() != null)
//                {
//                    if (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("1"))
//                    {
////                    tLapseDate = tFDate.getDate(returnDate);
////                    tLapseDate.setMonth(tLapseDate.getMonth() + 1);
////                    tLapseDate.setDate(1);
////                    returnDate = tFDate.getString(tLapseDate);
//
//                        //对日期的操作，最好使用Calendar方法
//                        GregorianCalendar tCalendar = new GregorianCalendar();
//                        tCalendar.setTime(tFDate.getDate(returnDate));
//                        //月份进位，舍弃日精度
//                        tCalendar.set(tCalendar.get(Calendar.YEAR),
//                                      tCalendar.get(Calendar.MONTH) + 1, 1);
//                        returnDate = tFDate.getString(tCalendar.getTime());
//                    }
//
//                    //按年进位，只舍弃了日精度，不舍弃月精度
//                    if (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("2"))
//                    {
////                    tLapseDate = tFDate.getDate(returnDate);
////                    tLapseDate.setYear(tLapseDate.getYear() + 1);
////                    tLapseDate.setDate(1);
////                    returnDate = tFDate.getString(tLapseDate);
//
//                        //对日期的操作，最好使用Calendar方法
//                        GregorianCalendar tCalendar = new GregorianCalendar();
//                        tCalendar.setTime(tFDate.getDate(returnDate));
//                        //年份进位，舍弃日精度，不舍弃月精度
//                        tCalendar.set(tCalendar.get(Calendar.YEAR) + 1,
//                                      tCalendar.get(Calendar.MONTH), 1);
//                        returnDate = tFDate.getString(tCalendar.getTime());
//                    }
//                }
//            }
//        }
//        else
//        {
//            //设置宽限期为默认值
//            System.out.println("没有险种交费失效描述!按默认值计算");
//            nDates = 60;
//            returnDate = calDate(startDate, nDates, "D", null);
//        }
//
//        //取得宽限期延长期
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
     * 得到默认的JDBCUrl
     * @return JDBCUrl
     */
    public static JdbcUrl getDefaultUrl()
    {
        JdbcUrl tUrl = new JdbcUrl();
        return tUrl;
    }

    /**
     * 将字符串补数,将sourString的<br>后面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
     * <p><b>Example: </b><p>
     * <p>RCh("Minim", "0", 10) returns "Minim00000"<p>
     * @param sourString 源字符串
     * @param cChar 补数用的字符
     * @param cLen 字符串的目标长度
     * @return 字符串
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
     * 将字符串补数,将sourString的<br>前面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
     * <p><b>Example: </b><p>
     * <p>LCh("Minim", "0", 10) returns "00000Minim"<p>
     * @param sourString 源字符串
     * @param cChar 补数用的字符
     * @param cLen 字符串的目标长度
     * @return 字符串
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
     * 比较获取两天中较后的一天
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
     * 比较获取两天中较早的一天
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
     * 得到当前系统日期 author: YT
     * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
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
     * 得到当前系统时间 author: YT
     * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
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
     * 得到流水号前导 author: YT
     * @param comCode 机构代码
     * @return 流水号的前导字符串
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
     * picc获取管理机构，截取管理代码的第3-6位（二级机构+三级机构）
     * 再加上日期编码的两位年两位月日日 052203
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
     * 该函数得到c_Str中的第c_i个以c_Split分割的字符串
     * @param c_Str 目标字符串
     * @param c_i 位置
     * @param c_Split   分割符
     * @return 如果发生异常，则返回空
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
     * 把数字金额转换为中文大写金额 author: HST
     * @param money 数字金额(double)
     * @return 中文大写金额(String)
     */
    public static String getChnMoney(double money)
    {
        String ChnMoney = "";
        String s0 = "";

        // 在原来版本的程序中，getChnMoney(585.30)得到的数据是585.29。

        if (money == 0.0)
        {
            ChnMoney = "零元整";
            return ChnMoney;
        }

        if (money < 0)
        {
            s0 = "负";
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

        String s1 = PubFun.getChnM(sFormatStr.substring(0, 4), "亿");

        String s2 = PubFun.getChnM(sFormatStr.substring(4, 8), "万");

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

        ChnMoney = s0 + s1 + s2 + s3 + "元" + s4;
        if (ChnMoney.substring(0, 1).equals("0"))
        {
            ChnMoney = ChnMoney.substring(1,
                                          ChnMoney.length());
        }
        for (int i = 0; i < ChnMoney.length(); i++)
        {
            if (ChnMoney.substring(i, i + 1).equals("0"))
            {
                ChnMoney = ChnMoney.substring(0, i) + "零" +
                           ChnMoney.substring(i + 1, ChnMoney.length());
            }
        }

        if (sDot.substring(1, 2).equals("0"))
        {
            ChnMoney += "整";
        }

        return ChnMoney;
    }

    /**
     * 得到money的角分信息
     * @param sIn String
     * @return String
     */
    private static String getDotM(String sIn)
    {
        String sMoney = "";
        if (!sIn.substring(0, 1).equals("0"))
        {
            sMoney += getNum(sIn.substring(0, 1)) + "角";
        }
        else
        {
            sMoney += "0";
        }
        if (!sIn.substring(1, 2).equals("0"))
        {
            sMoney += getNum(sIn.substring(1, 2)) + "分";
        }
        else
        {
            sMoney += "0";
        }

        return sMoney;
    }

    /**
     * 添加仟、佰、拾等单位信息
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
            sMoney += getNum(strUnit.substring(0, 1)) + "仟";
        }
        else
        {
            sMoney += "0";
            flag = true;
        }
        if (!strUnit.substring(1, 2).equals("0"))
        {
            sMoney += getNum(strUnit.substring(1, 2)) + "佰";
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
            sMoney += getNum(strUnit.substring(2, 3)) + "拾";
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
     * 格式化字符
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
     * 获取阿拉伯数字和中文数字的对应关系
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
     * 如果一个字符串数字中小数点后全为零，则去掉小数点及零
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
     * 得到近似值
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
     * 根据输入标记为间隔符号，拆分字符串
     * @param strMain String
     * @param strDelimiters String
     * 失败返回NULL
     * @return String[]
     */
    public static String[] split(String strMain, String strDelimiters)
    {
        int i;
        int intIndex = 0; //记录分隔符位置，以取出子串
        Vector vResult = new Vector(); //存储子串的数组
        String strSub = ""; //存放子串的中间变量

        strMain = strMain.trim();

        //若主字符串比分隔符串还要短的话,则返回空字符串
        if (strMain.length() <= strDelimiters.length())
        {
            System.out.println("分隔符串长度大于等于主字符串长度，不能进行拆分！");
            return null;
        }

        //取出第一个分隔符在主串中的位置
        intIndex = strMain.indexOf(strDelimiters);

        //在主串中找不到分隔符
        if (intIndex == -1)
        {
            String[] arrResult =
                    {
                    strMain};
            return arrResult;
        }

        //分割主串到数组中
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

        //如果最末不是分隔符，取最后的字符串
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
     * 设置数字精度
     * 需要格式化的数据
     * @param value float
     * 精度描述（0.00表示精确到小数点后两位）
     * @param precision String
     * @return double
     */
    public static double setPrecision(float value, String precision)
    {
        return Float.parseFloat(new DecimalFormat(precision).format(value));
    }

    /**
     * 设置数字精度
     * 需要格式化的数据
     * @param value double
     * 精度描述（0.00表示精确到小数点后两位）
     * @param precision String
     * @return double
     */
    public static double setPrecision(double value, String precision)
    {
        return Double.parseDouble(new DecimalFormat(precision).format(value));
    }

    /**
     * 将源 Schema 对象中的数据拷贝至目标 Schema 对象中
     * 保全 C、P 互换时经常用到
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
//                System.out.println("\t@> PubFun.copySchema() : Reflections 拷贝数据失败！");
//                ex.printStackTrace();
//            }
//        }
//    }

    /**
     * 把schemaset对象拷贝一份返回
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
     * 互换LP和LC表数据
     * @param source Schema
     * @param object Schema
     * @return boolean
     */
//    public static boolean exchangeSchema(Schema source, Schema object)
//    {
//        try
//        {
//            //把LP表的数据传递到LC表
//            Reflections tReflections = new Reflections();
//            tReflections.transFields(object, source);
//
//            //获取一个数据库连接DB
//            Method m = object.getClass().getMethod("getDB", null);
//            Schema schemaDB = (Schema) m.invoke(object, null);
//            //因为LP表与LC表只有EdorNo和EdorType两个关键字的差别，所以可以唯一获取LC表对应记录
//            m = schemaDB.getClass().getMethod("getInfo", null);
//            m.invoke(schemaDB, null);
//            m = schemaDB.getClass().getMethod("getSchema", null);
//            object = (Schema) m.invoke(schemaDB, null);
//
//            //把LC表数据备份到临时表
//            m = object.getClass().getMethod("getSchema", null);
//            Schema tSchema = (Schema) m.invoke(object, null);
//
//            //互换LP和LC表数据
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
     * 生成更新的sql列表
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
     * 将账号前的0去掉
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
     * 转换JavaScript解析不了的特殊字符
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
     * 转换JavaScript解析不了的特殊字符
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
     * 计算保单年度
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
//        // 处理润年
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
     * 通过身份证号号获取生日日期
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
     * 通过身份证号获取性别
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
     * 校验录入的管理机构是否是操作员登陆机构同级或下级，参数为空返回false
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
     * 用户页面权限判断
     * @param cGlobalInput GlobalInput
     * @param RunScript String
     * @return boolean
     */
//    public static boolean canIDo(GlobalInput cGlobalInput, String RunScript)
//    {
//        String Operator = cGlobalInput.Operator;
////        String ComCode = cGlobalInput.ComCode;
////        String ManageCom = cGlobalInput.ManageCom;
//        //通过用户编码查询用户页面权限集合,NodeSign = 2为用户页面权限菜单标志
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
       本函数功能：用于作各种自定义判断校验。前提是 必须将校验的SQL字串已经存入 LMCalMode表里
       所需参数：
               CalCode----------获取校验SQL的代码;
               TransferData-----包含作校验时校验字串所需参数
       返回参数：True--校验成功[校验字串返回值为“1”] ；False---校验失败；
       例：判断业务员（代理人）是否有销售资格,代理人代码为：02281075
          校验字串:  select case salequaf when 'N' then '0' else '1' end from laagent where agentcode='?AgentCode?'
          需要传入的参数:
               (1)、CalCode=“AgSale”；
               (2)、代理人代码<AgentCode>,则需将代理人代码放入 TransferData
          调用方法;
          String tCalCode ="AgSale"; //根据字串从 lmcalmode 里获取 校验的SQL字串
          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("AgentCode","02281075");//校验字串所需参数
          if(PubFun.userDefinedCheck(tCalCode,tTransferData)==false)
          {
               System.out.println("校验失败");
               return false;
          }
     **************************************************************************/
//    public static boolean userDefinedCheck(String mCalCode,
//                                           TransferData mTransferData)
//    {
//        //
//        String tCalCode = ""; //获取传入的 CalCode 代码，用于得到校验的SQL语句
//        TransferData tTransferData = new TransferData();
//        tTransferData = mTransferData; //获取校验所需参数<>
//        //判断传入代码的是否正确
//        if (mCalCode == null || mCalCode.equals(""))
//        {
//            return false;
//        }
//        else
//        {
//            tCalCode = mCalCode.trim();
//        }
//        //判断传入TransferData<包含校验所需参数>是否正确
//        if (tTransferData == null)
//        {
//            return false;
//        }
//        //以下调用 Calculator 类，作校验判断
//        Calculator mCalculator = new Calculator();
//        mCalculator.setCalCode(tCalCode);
//        Vector tVector = new Vector();
//        tVector = tTransferData.getValueNames();
//        if (tVector.size() > 0)
//        {
//            //获得要素名称并赋值到Calculator类中
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
     * 取得sequenceno的值
     * @param type String 序列号类型
     * @return String 序列号,如果出错返回"-1"
     */
    public static String getSeqNo(String type)
    {
        String ret = "-1";

        ExeSQL es = new ExeSQL();

        ret = es.getOneValue("select " + type + ".nextval from dual");
        return ret;
    }


    /**
     * 主函数，测试用
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
//	            tError.errorMessage = "查询投保人数据失败!";
//	         
//
//	            	        }
//	        mLCAppntSchema.setSchema(tLCAppntDB);
//	        System.out.println ("投保人客户号：" + mLCAppntSchema.getAppntNo());
//		 
//		 
//		 new
    }
}
