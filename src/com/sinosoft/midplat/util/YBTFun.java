package com.sinosoft.midplat.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
// 只保留前置用的方法,后台的YBTFun.java与前置机的不一样.. modify by hwf
public class YBTFun {
	private static Logger mLogger = Logger.getLogger(YBTFun.class);

	// 校验身份证的时候需要错误处理类 zhangd 20071019
	public CErrors mErrors = new CErrors();

	public static final String VISADATEDELIMITER = "-";

	public YBTFun() {
	}

	public static String tranPrtno(String applyno, String bankcode) {
		String prtno = applyno;
		if (applyno.length() < 14 && bankcode.equals("04")) {

			prtno = StrTool.LCh(applyno, "0", 13);
			prtno = StrTool.LCh(prtno, "8", 14);
		}
		return prtno;

	}

	// 返回报文中的描述
	public static String getBackDes(String tStr, String type) {
		String tSql = "SELECT Code_Des FROM BankAndInsurerCodeMapping WHERE Insurer_Code = '"
				+ tStr + "' AND CodeType = '" + type + "'";
		ExeSQL tExeSQL = new ExeSQL();
		String tDes = tExeSQL.getOneValue(tSql);
		return tDes;
	}

	/**
	 * 返回映射表中的中文解析
	 * 
	 * @param tStr
	 * @param type
	 *            类型
	 * @param tBankCode
	 *            银行
	 * @return 中文描述
	 */
	public static String getBackDes(String tStr, String type, String tBankCode) {
		mLogger.info("0");
		String tSql = "SELECT Code_Des FROM BankAndInsurerCodeMapping WHERE Insurer_Code = '"
				+ tStr
				+ "' AND CodeType = '"
				+ type
				+ "' AND BankID = '"
				+ tBankCode + "'";
		// ExeSQL tExeSQL = new ExeSQL();
		// String tDes = tExeSQL.getOneValue(tSql);
		// modify by hwf at 2008年10月23日
		String tDes = "";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.MaxRow > 0)
			tDes = tSSRS.GetText(1, 1);
		mLogger.info("1");
		return tDes;
	}

	public static String tranProposalno(String applyno) {
		String proposalno = applyno;
		if (applyno.substring(0, 6).equals("800000") && applyno.length() > 8) {
			proposalno = applyno.substring(applyno.length() - 8);
		}
		return proposalno;
	}

	public static String tran_CodetoName(String bankcode) {
		String name = bankcode;
		if (bankcode.equals("01")) {
			name = "工行";
		}
		if (bankcode.equals("02")) {
			name = "建行";
		}
		if (bankcode.equals("03")) {
			name = "商业银行";
		}
		if (bankcode.equals("04")) {
			name = "邮储";
		}
		if (bankcode.equals("05")) {
			name = "农行";
		}
		return name;
	}

	// 将信雅达借口传过来金额的单位"分"转化为中科软系统中的"元"
	public static String tran_FenToYuan(String cent) {
		if (cent.equals("") || cent == null) {
			return null;
		}
		Double fen = new Double(cent);
		DecimalFormat df = new DecimalFormat("0.00");
		String yuan = df.format(fen.doubleValue() / 100);
		System.out.println("yuan:" + yuan);
		return yuan;
	}

	// 将中科软系统中的"元"转化为信雅达借口单位"分"
	public static String tran_YuanToFen(String doll) {
		if (doll.equals("") || doll == null) {
			return null;
		}
		Double Yuan = new Double(doll);
		DecimalFormat df = new DecimalFormat("0");

		String Fen = df.format(Yuan.doubleValue() * 100);
		System.out.println("Fen:" + Fen);
		return Fen;
	}

	/**
	 * 将Service_Code影射到Service_Id,再影射到sub_service_id,最后生成所对应的Document
	 * 
	 * @param pTXLifeRequest
	 *            生产Bean
	 * @param pDocument
	 *            原始文件
	 * @param psub_service_order
	 *            是调用子服务的顺序
	 * @return String sub_service
	 */
	//
	// public static String tran_FunctionFlag(TXLifeRequest pTXLifeRequest,
	// String pOldFunctionFlag, String psub_service_order) {
	//
	// //
	// 查询"银保通服务配置表tab_midplat_service_config"和"服务定义表tab_midplat_service",获取子服务编码
	// String sql = "select sub_service_id from tab_midplat_service
	// a,tab_midplat_service_config b "
	// + "where a.service_id = b.service_id "
	// + " and b.BankCode = '"
	// + pTXLifeRequest.getBankCode()
	// + "' and b.ISSUE_WAY = '"
	// + pTXLifeRequest.getISSUE_WAY()
	// + "' and b.service_code='"
	// + pOldFunctionFlag
	// + "' and a.sub_service_order = '"
	// + psub_service_order + "'";
	//
	// System.out.println("tsql:" + sql);
	// ExeSQL tExeSQL = new ExeSQL();
	// String result = tExeSQL.getOneValue(sql);
	// return result;
	//
	// }
	// 将红利和现价的年份后面加上固定字串(现在用在:新单承保)
	public static String addRemark(String year, String Remark) {
		String note = "";
		if (Remark.equals("")) {
			Remark = " 年末";
		}
		note = year + Remark;
		return note;
	}

	public static String addRemark(String year) {
		String note = "";
		note = year + " 年末";
		return note;
	}

	public static String getCurrentDateTime() {
		String curDate = PubFun.getCurrentDate();
		String curTime = PubFun.getCurrentTime();
		return curDate + curTime;
	}

	/**
	 * 得到当前系统日期 author: YT
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyyMMdd"
	 */
	public static String getCurrentDate2() {
		String pattern = "yyyyMMdd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: YT
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HHmmss"
	 */
	public static String getCurrentTime2() {
		String pattern = "HHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: YT
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	// public static String getDataBaseTime() {
	// ExeSQL tExeSQL;
	// // String strSQL="select getdate()";
	// String strSQL = "select sysdate from dual";
	// tExeSQL = new ExeSQL();
	// FDate fDate = new FDate();
	// String tDataBaseTime = tExeSQL.getOneValue(strSQL);
	// String pattern2 = "yyyyMMdd HH:mm:ss";
	// SimpleDateFormat df2;
	// df2 = new SimpleDateFormat(pattern2);
	// Date start_timetemp = fDate.getDate(tDataBaseTime);
	// tDataBaseTime = df2.format(start_timetemp);
	// return tDataBaseTime;
	// }
	// public static String getPayDateChn(String payintv, String paydate) {
	// String paydatechn = "";
	// if (payintv != null && payintv.trim().equals("0") && paydate != null) {
	// // paydate = PubFun.calDate(paydate, -1, "D", "");
	// paydatechn = YBTFun.getYear(paydate) + "年"
	// + YBTFun.getMonth(paydate) + "月" +
	// // 将缴费日期前置一天(根据新华银保通测试修改)
	// YBTFun.getDay(paydate) + "日";
	// return paydatechn;
	// } else {
	// return " ";
	// }
	// }
	/**
	 * 通过保险公司方代码查询银行方代码
	 * 
	 * @param tinsurer_code
	 *            String
	 * @param tcodetype
	 *            String
	 * @return String
	 */
	public static String getbank_Code(String tinsurer_code, String tcodetype) {
		String mbank_Code;
		String tSql1 = "select bank_Code from BankAndInsurerCodeMapping "
				+ "where insurer_code ='" + tinsurer_code + "' and codetype ='"
				+ tcodetype + "'";
		ExeSQL ttExeSQL = new ExeSQL();
		mbank_Code = ttExeSQL.getOneValue(tSql1);

		return mbank_Code;
	}

	/**
	 * 通过保险公司方代码查询银行方代码
	 * 
	 * @param tinsurer_code
	 *            String
	 * @param tcodetype
	 *            String
	 * @return String
	 */
	public static String getbank_Code(String tinsurer_code, String tcodetype,
			String tBankID) {
		String mbank_Code = "";
		String tSql = "select bank_Code from BankAndInsurerCodeMapping "
				+ "where insurer_code ='" + tinsurer_code + "' and codetype ='"
				+ tcodetype + "' and BANKID = '" + tBankID + "'";
		// ExeSQL ttExeSQL = new ExeSQL();
		// mbank_Code = ttExeSQL.getOneValue(tSql1);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.MaxRow > 0)
			mbank_Code = tSSRS.GetText(1, 1);

		return mbank_Code;
	}

	/**
	 * 通过银行方代码查询保险公司方代码
	 * 
	 * @param tbank_code
	 *            String
	 * @param tcodetype
	 *            String
	 * @return String
	 */
	public static String getinsurer_Code(String tbank_code, String tcodetype,
			String tBankID) {
		// 如果没有则返回""
		String minsurer_Code = "";
		String tSql = "select insurer_Code from BankAndInsurerCodeMapping "
				+ "where bank_code ='" + tbank_code + "' and codetype ='"
				+ tcodetype + "' and BANKID = '" + tBankID + "'";
		// 经过测试 ExeSQL.getOneValue();占用时间400+ms
		// ExeSQL.execSQL();占用时间10+ms

		// ExeSQL ttExeSQL = new ExeSQL();
		// minsurer_Code = ttExeSQL.getOneValue(tSql2);

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.MaxRow > 0)
			minsurer_Code = tSSRS.GetText(1, 1);

		return minsurer_Code;
	}

	/**
	 * 通过银行方关系代码查询保险公司方关系代码
	 * @param relation	关系代码
	 * @param banksex	投保人性别	
	 * @param insuresex	被保人性别
	 * @param tBankID	银行代码
	 * @return
	 */
	public static String getRela_insu_Code(String relation, String banksex,
			String insuresex, String tBankID) {
		// 如果没有则返回""
		String minsurer_Code = "";
		String tSql = "select insure_code from relation "
				+ "where bank_code ='" + relation + "'and bank_sex='" + banksex
				+ "' and insure_sex ='" + insuresex + "' and BANKID = '"
				+ tBankID + "'";
		// 经过测试 ExeSQL.getOneValue();占用时间400+ms
		// ExeSQL.execSQL();占用时间10+ms

		// ExeSQL ttExeSQL = new ExeSQL();
		// minsurer_Code = ttExeSQL.getOneValue(tSql2);

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.MaxRow > 0)
			minsurer_Code = tSSRS.GetText(1, 1);

		return minsurer_Code;
	}

	/***
	 *  通过保险公司关系代码查询银行方关系代码
	 * @param relation 关系代码
	 * @param banksex	投保人性别
	 * @param insusex	被保人性别
	 * @param tBankID	银行代码
	 * @return
	 */
	public static String getRela_bank_Code(String relation, String banksex,
			String insusex, String tBankID) {
		// 如果没有则返回""
		String minsurer_Code = "";
		String tSql = "select bank_code from relation "
				+ "where insure_code ='" + relation + "' and bank_sex ='"
				+ banksex + "'and insure_sex='" + insusex + "' and BANKID = '"
				+ tBankID + "'";
		// 经过测试 ExeSQL.getOneValue();占用时间400+ms
		// ExeSQL.execSQL();占用时间10+ms

		// ExeSQL ttExeSQL = new ExeSQL();
		// minsurer_Code = ttExeSQL.getOneValue(tSql2);

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.MaxRow > 0)
			minsurer_Code = tSSRS.GetText(1, 1);

		return minsurer_Code;
	}
	
	
	public static String getPsbcRela(String bankcode , String appBirth , String insuBirth ,String bankID){
		String mInsuCode = "";
		
		
		return mInsuCode;
	}

	/**
	 * 通过保单号查询新单承保时的流水号
	 * 
	 * @param tContNo
	 *            String
	 * @return String
	 */
	public static String getTransno(String tContNo) {
		String mtransno;
		String tSql3 = "select transno from lktransstatus where " + "polno = '"
				+ tContNo + "' and rcode = '1' and transstatus = '2'";
		ExeSQL ttExeSQL = new ExeSQL();
		mtransno = ttExeSQL.getOneValue(tSql3);

		return mtransno;
	}

	/**
	 * 返回给ICBC的报文中显示中文
	 * 
	 * @param tContNo
	 *            String
	 * @return String
	 */
	public static String getInsuYear(String tInsuYearFlag, String tInsuYear) {
		String mInsuYear;
		if (tInsuYearFlag.equals("5")) {
			mInsuYear = "终身";
		} else {
			mInsuYear = tInsuYear;
		}

		return mInsuYear;
	}

	/**
	 * 数字加逗号分隔符 如 1234567.01 --> 1,234,567.01
	 * 
	 * @param tStr
	 * @return
	 */
	public static String NumberFormat(String tStr) {
		// 分to元
		// tStr = tran_FenToYuan(tStr);
		double d = java.lang.Double.parseDouble(tStr);
		System.out.println(d);
		// 得到本地的缺省格式
		NumberFormat nf1 = NumberFormat.getInstance();
		nf1.setMinimumFractionDigits(2);
		nf1.setMaximumFractionDigits(2);
		String tStra = nf1.format(d);
		if (tStra.indexOf(".") == -1)
			tStra = tStra + ".00";
		return tStra;
	}

	public static String NumberFormatFen(String tStr) {
		// 分to元
		double d = java.lang.Double.parseDouble(tStr);
		System.out.println(d);
		// 得到本地的缺省格式
		DecimalFormat df = new DecimalFormat("#.##");
		String tStra = df.format(d);
		if (tStra.indexOf(".") == -1)
			tStra = tStra + ".00";
		return tStra;
	}

	/**
	 * 返回给ICBC的报文中显示中文
	 * 
	 * @param tContNo
	 *            String
	 * @return String
	 */
	public static String getInsuYear(String tInsuYearFlag, String tInsuYear,
			String bankID) {
		String mInsuYear;
		if (tInsuYearFlag.equals("5")) {
			mInsuYear = "终身";
		} else {
			mInsuYear = tInsuYear;
		}

		return mInsuYear;
	}

	/**
	 * 通过投资帐户代码，获取帐户名称
	 * 
	 * @param tbank_code
	 *            String
	 * @param tcodetype
	 *            String
	 * @return String
	 */
	public static String getAccountName(String pAccountCode, String tcodetype) {
		String tAccountName;
		String tSql2 = "select Code_Des from BankAndInsurerCodeMapping "
				+ "where insurer_Code ='" + pAccountCode + "' and codetype ='"
				+ tcodetype + "'";
		ExeSQL ttExeSQL = new ExeSQL();
		tAccountName = ttExeSQL.getOneValue(tSql2);

		return tAccountName;
	}

	// /**
	// * 通过银行方网点代码查询保险公司方代码
	// * @param tbank_code String
	// * @param tcodetype String
	// * @return String
	// */
	// public static String getAgentCom(String tbank_code, String tcodetype)
	// {
	// String minsurer_Code ;
	// String tSql2 = "select insurer_Code from BankAndInsurerCodeMapping "
	// + "where bank_code ='" + tbank_code + "' and codetype ='" + tcodetype
	// +"'";
	// ExeSQL ttExeSQL = new ExeSQL();
	// minsurer_Code = ttExeSQL.getOneValue(tSql2);
	//
	// return minsurer_Code;
	// }

	/**
	 * 把一个串转成double,再小数点向一个方向移动位数
	 * 
	 * @param tbank_code
	 *            String
	 * @param tcodetype
	 *            String
	 * @return String
	 */
	public static String trans_point(String pSRCData, String pPointNum) {
		String ret = "";
		double tSTDData = 0.00;
		if (pSRCData != null && !pSRCData.equals("") && pPointNum != null
				&& !pPointNum.equals("")) {
			Double tDSRCDouble = new Double(pSRCData);
			double tDSRCdouble = tDSRCDouble.doubleValue();
			Double tPointNumDouble = new Double(pPointNum);
			double tPointNumdouble = tPointNumDouble.doubleValue();
			tSTDData = tDSRCdouble * tPointNumdouble;
			// int y = (int)Math.round(tSTDData);
			// Integer z = new Integer(y);
			ret = (new Double(tSTDData)).toString();
			return ret;
		} else {
			return null;
		}

	}

	public static String tranDate(String Year_Month_Day) {
		String Date = YBTFun.getYear(Year_Month_Day)
				+ YBTFun.getMonth(Year_Month_Day)
				+ YBTFun.getDay(Year_Month_Day);
		return Date;
	}

	// 将日期前置一天
	// public static String tranBeforDate(String Date) {
	// String BeforDate = PubFun.calDate(Date, -1, "D", "");
	// return BeforDate;
	// }

	/**
	 * 日期格式转换：将(例如:20050817)转化为(例如:2005-08-17)
	 * 
	 * @param strDate
	 *            (日期字符串，格式:yyyyMMdd)
	 * @return String (日期字符串，格式:yyyy-MM-dd)
	 */
	public static String tranOutDate(String strDate) {
		String tString = null;
		String Date = strDate.trim();
		if (Date != "" && Date != null) {
			if (Date.length() == 8) {
				String Year = strDate.substring(0, 4);
				String Month = strDate.substring(4, 6);
				String Day = strDate.substring(6, 8);
				tString = Year + "-" + Month + "-" + Day;
			} else {
				System.out.println("日期格式不正确！");
			}
		}
		return tString;
	}

	/**
	 * 将yyyy-mm-dd格式转换为yyyymmdd 如2008-1-19 -> 20080119
	 * 
	 * @param Year_Month_Day
	 *            yyyy-mm-dd
	 * @return yyyymmdd
	 */
	public static String tranEndDate(String Year_Month_Day) {
		String Date = "";

		Date = YBTFun.getYear(Year_Month_Day) + YBTFun.getMonth(Year_Month_Day)
				+ YBTFun.getDay(Year_Month_Day);

		return Date;
	}

	// 将小写金额转换成大写金额
	public static String getChnMoney(String money) {
		if (money.equals("") || money == null) {
			money = "0.00";
		}
		double Money = Double.parseDouble(money);
		return com.sinosoft.lis.pubfun.PubFun.getChnMoney(Money);
	}

	/**
	 * 输出参数为分，变成大写的金额 如10000 为100元整
	 * 
	 * @param money
	 * @return
	 */
	public static String getChnMoneyForFen(String money) {
		money = YBTFun.tran_FenToYuan(money);
		if (money.equals("") || money == null) {
			money = "0.00";
		}
		double Money = Double.parseDouble(money);
		return com.sinosoft.lis.pubfun.PubFun.getChnMoney(Money);
	}

	// public static String getPayseDateChn(String payintv, String firstpaydate,
	// String payenddate) {
	// String paydatechn = "";
	//
	// if (payintv != null && payintv.trim().equals("12")
	// && firstpaydate != null && payenddate != null) {
	// String strBeginDate = PubFun.calDate(firstpaydate, 1, "D", null);
	//
	// paydatechn = YBTFun.getYear(strBeginDate) + "年"
	// + YBTFun.getMonth(strBeginDate) + "月"
	// + YBTFun.getDay(strBeginDate) + "日";
	// payenddate = PubFun.calDate(payenddate, -1, "D", ""); //$NON-NLS-2$
	// paydatechn += " 至 " + YBTFun.getYear(payenddate) + "年" //$NON-NLS-2$
	// + YBTFun.getMonth(payenddate) + "月"
	// + YBTFun.getDay(payenddate) + "日";
	//
	// return paydatechn;
	// } else {
	// return " ";
	// }
	// }

	public static String getPayseDateChn1(String payintv, String payendyear) {
		String paydatechn = "";

		if (payintv != null && payintv.trim().equals("12")
				&& payendyear != null) {

			paydatechn = "缴费至" + payendyear + "周岁";

			return paydatechn;
		} else {
			return " ";
		}
	}

	/**
	 * 获取日期值中的年份
	 * 
	 * @param strDate
	 *            String 日期（年/月/日）
	 * @return String 年
	 */
	public static String getYear(String strDate) {
		int intPosition = 0;
		String strReturn = "";
		int intYear = 0;

		if ((strDate != null) && (strDate.trim().length() > 0)) {
			intPosition = YBTFun.getPos(strDate, YBTFun.VISADATEDELIMITER, 1);
			if (intPosition > 0) {
				strReturn = strDate.substring(0, intPosition);
				intYear = new Integer(strReturn).intValue();
				if ((intYear <= 0)) {
					strReturn = "";
				} else {
					strReturn = "" + intYear;
				}

				if ((intYear < 10) && (!strReturn.equals(""))) {
					strReturn = "0" + strReturn;
				}
			}
		}
		return strReturn;
	}

	/**
	 * @param strMain
	 *            String
	 * @param strSub
	 *            String
	 * @param intTimes
	 *            int
	 * @return int
	 */
	public static String getTransCode(String strCodeType, String strCode) {
		String mCode = strCode;
		if (strCodeType != null && strCodeType.trim().length() > 0
				&& strCode != null && strCode.trim().length() > 0) {
			String tSql = "select codename from ldcode where codetype ='"
					+ strCodeType + "' and code ='" + strCode + "'";
			ExeSQL ttExeSQL = new ExeSQL();
			mCode = ttExeSQL.getOneValue(tSql);
		}
		return mCode;
	}

	/**
	 * 获取日期值中的月份
	 * 
	 * @param strDate
	 *            String 日期
	 * @return String 月
	 */
	public static String getMonth(String strDate) {
		int intPosition1 = 0, intPosition2 = 0;
		String strReturn = "";
		int intMonth = 0;
		if ((strDate != null) && (strDate.trim().length() > 0)) {
			intPosition1 = YBTFun.getPos(strDate, YBTFun.VISADATEDELIMITER, 1);
			intPosition2 = YBTFun.getPos(strDate, YBTFun.VISADATEDELIMITER, 2);
			if ((intPosition1 > 0) && intPosition2 > intPosition1) {

				strReturn = strDate.substring(intPosition1 + 1, intPosition2);

				intMonth = new Integer(strReturn).intValue();
				if ((intMonth <= 0) || (intMonth > 12)) {
					strReturn = "";
				} else {
					strReturn = "" + intMonth;
				}

				if ((intMonth < 10) && (!strReturn.equals(""))) {
					strReturn = "0" + strReturn;
				}
			}
		}
		return strReturn;
	}

	/**
	 * 获取给定日期值中的天
	 * 
	 * @param strDate
	 *            String 日期
	 * @return String 天
	 */
	public static String getDay(String strDate) {
		int intPosition = 0;
		String strReturn = "";
		int intDay = 0;
		if ((strDate != null) && (strDate.trim().length() > 0)) {
			intPosition = YBTFun.getPos(strDate, YBTFun.VISADATEDELIMITER, 2);
			if (intPosition > 0) {

				strReturn = strDate.substring(intPosition + 1);

				intDay = new Integer(strReturn).intValue();

				if ((intDay <= 0) || (intDay > 31)) {
					strReturn = "";
				} else {
					strReturn = "" + intDay;
				}

				if ((intDay < 10) && (!strReturn.equals(""))) {
					strReturn = "0" + strReturn;
				}
			}
		}
		return strReturn;
	}

	/**
	 * 获取子串在主串中出现第 n 次的位置
	 * 
	 * @param strMain
	 *            String 主字符串
	 * @param strSub
	 *            String 子字符串
	 * @param intTimes
	 *            int 出现次数
	 * @return int 位置值,如果子串在主串中没有出现指定次数,则返回-1
	 */
	public static int getPos(String strMain, String strSub, int intTimes) {
		int intCounter = 0; // 循环记数
		int intPosition = 0; // 位置记录
		int intLength = strSub.length(); // 子串长度

		if (intTimes <= 0) {
			return -1;
		}
		while (intCounter < intTimes) {
			intPosition = strMain.indexOf(strSub, intPosition);
			if (intPosition == -1) {
				return -1;
			}
			intCounter++;
			intPosition += intLength;
		}
		return intPosition - intLength;
	}

	/**
	 * 用于将输入流转成字节数组的方法
	 * 
	 * @param position
	 *            备份的位置
	 * @param inputstream
	 *            输入流
	 * @param fields
	 *            用于从输入流中解析备份信息的HashMap
	 */
	public static byte[] INSToByteArray(InputStream pInputStream) {

		try {

			byte[] bytes = new byte[1];
			ByteArrayOutputStream vInNoSTDByteArrayOutputStream = new ByteArrayOutputStream();
			if (pInputStream == null) {
				return null;
			} else {
				while (pInputStream.read(bytes) != -1) {
					vInNoSTDByteArrayOutputStream.write(bytes);
				}
				vInNoSTDByteArrayOutputStream.flush();
				vInNoSTDByteArrayOutputStream.close();
				byte[] tInNoSTDbytes = vInNoSTDByteArrayOutputStream
						.toByteArray();
				return tInNoSTDbytes;
			}
		} catch (Exception e1) {
			// 如果发生异常,让流程继续走下去,只在日志中保存错误记录
			mLogger.error("将流备份的过程中发生异常：" + e1);
			return null;
		}

	}

	/**
	 * 用于产生例如冲正，打印冲正等交易的简单返回报文
	 * 
	 * @return inputstream
	 */
	public static Document getSimpleReponse(String pFlag, String pDesc) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_RETDATA = "RetData";
		String XML_FLAG = "Flag";
		String XML_DESC = "Desc";
		try {
			// 创建标准XML文档，"DATA"作为根节点
			ElementLis root = new ElementLis("TranData");
			doc = new Document(root);

			// 返回数据包
			ElementLis retData = new ElementLis(XML_RETDATA); // <!-- 返回数据包
			// -->
			ElementLis flag = new ElementLis(XML_FLAG); //
			ElementLis desc = new ElementLis(XML_DESC); //
			flag.setText(pFlag);
			desc.setText(pDesc);
			retData.addContent(flag);
			retData.addContent(desc);
			root.addContent(retData);
			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/*
	 * 用于产生新一代农行需要的简单返回报文 @return inputstream 直接从前置机返回错误时 使用
	 */
	// public static InputStream getSimpleABC1Response(String pFlag,
	// String pDesc, String pTransrNo, String pService_id) {
	// InputStream tInputStream = null;
	// Document doc = null;
	// String XML_ROOT = "ABCB2I";
	//
	// String XML_HEADER = "Header";
	//
	// String XML_RETCODE = "RetCode";
	// String XML_RETMSG = "RetMsg";
	// String XML_SERIALNO = "SerialNo";
	// String XML_INSUSERIAL = "InsuSerial";
	// String XML_TRANSDATE = "TransDate";
	// String XML_TRANSTIME ="TransTime";
	// String XML_BANKCODE = "BankCode";
	// String XML_CORPNO ="CorpNo";
	// String XML_TRANSCODE = "TransCode";
	//
	// try {
	//
	// ElementLis root = new ElementLis(XML_ROOT);
	// doc = new Document(root);
	//
	// // 返回数据包
	// ElementLis tHeader = new ElementLis(XML_HEADER);
	//
	// ElementLis tRetCode = new ElementLis(XML_RETCODE); // 返回码是1012
	// ElementLis tRetMsg = new ElementLis(XML_RETMSG);
	// ElementLis tSerialNo = new ElementLis(XML_SERIALNO);
	// ElementLis tInsuSerial = new ElementLis(XML_INSUSERIAL);
	// ElementLis tTransDate = new ElementLis(XML_TRANSDATE);
	// ElementLis tTransTime = new ElementLis(XML_TRANSTIME);
	// ElementLis tBankCode = new ElementLis(XML_BANKCODE);
	// ElementLis tCorpNo = new ElementLis(XML_CORPNO);
	// ElementLis tTransCode = new ElementLis(XML_TRANSCODE);
	//
	// if (pTransrNo != null) {
	// tSerialNo.setText(pTransrNo);
	// tInsuSerial.setText(pTransrNo);
	// }
	// if (pService_id != null) {
	// tTransCode.setText(pService_id);
	// }
	//
	// tRetMsg.setText(pDesc);
	// tRetCode.setText(pFlag);
	// tTransDate.setText(YBTFun.getCurrentDate2());
	// tTransTime.setText(YBTFun.getCurrentTime2());
	//
	//
	// tHeader.addContent(tRetCode);
	// tHeader.addContent(tRetMsg);
	// tHeader.addContent(tSerialNo);
	// tHeader.addContent(tInsuSerial);
	// tHeader.addContent(tTransDate);
	// tHeader.addContent(tTransTime);
	// tHeader.addContent(tBankCode);
	// tHeader.addContent(tCorpNo);
	// tHeader.addContent(tTransCode);
	//
	// root.addContent(tHeader);
	// // tInputStream = XmlOperator.getInputStream(doc);
	// InputStream input = XmlOperator.getInputStream(doc);
	// mLogger.info("-------------添加前面业务包头---------------");
	// int c = 0;
	// ByteArrayOutputStream vTemp = new ByteArrayOutputStream();
	// while ((c = input.read()) != -1) {
	// vTemp.write(c);
	// }
	// byte[] bPack = vTemp.toByteArray();
	// String xmlStr = new String(bPack); //去掉XML头 <?xml version="1.0"
	// encoding="GBK"?>
	// xmlStr = xmlStr.substring(36);
	//
	// // String len = xmlStr.length()+"";
	// // for (int i = len.length(); i < 8; i++){
	// // len = "0" + len;
	// // }
	// // String des = "";
	// // for(int i = 0; i < 40; i++){
	// // des += " ";
	// // }
	// // String pack = "X1.0"
	// +len+"4518    "+"2"+"0"+"0"+"0"+"0"+des+"00000000";
	// //
	// // String data = pack+xmlStr;
	// mLogger.info(xmlStr);
	// tInputStream = new ByteArrayInputStream(xmlStr.getBytes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return tInputStream;
	// }

	/*
	 * 用于产生新一代农行需要的简单返回报文 @return inputstream 从后置机返回错误时 使用
	 */
	public static Document getSimpleABCResponse(String pFlag, String pDesc,
			String pTransrNo, String pService_id) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_ROOT = "ABCB2I";

		String XML_HEADER = "Header";

		String XML_RETCODE = "RetCode";
		String XML_RETMSG = "RetMsg";
		String XML_SERIALNO = "SerialNo";
		String XML_INSUSERIAL = "InsuSerial";
		String XML_TRANSDATE = "TransDate";
		String XML_TRANSTIME = "TransTime";
		String XML_BANKCODE = "BankCode";
		String XML_CORPNO = "CorpNo";
		String XML_TRANSCODE = "TransCode";

		try {

			ElementLis root = new ElementLis(XML_ROOT);
			doc = new Document(root);

			// 返回数据包
			ElementLis tHeader = new ElementLis(XML_HEADER);

			ElementLis tRetCode = new ElementLis(XML_RETCODE); // 返回码是1012
			ElementLis tRetMsg = new ElementLis(XML_RETMSG);
			ElementLis tSerialNo = new ElementLis(XML_SERIALNO);
			ElementLis tInsuSerial = new ElementLis(XML_INSUSERIAL);
			ElementLis tTransDate = new ElementLis(XML_TRANSDATE);
			ElementLis tTransTime = new ElementLis(XML_TRANSTIME);
			ElementLis tBankCode = new ElementLis(XML_BANKCODE);
			ElementLis tCorpNo = new ElementLis(XML_CORPNO);
			ElementLis tTransCode = new ElementLis(XML_TRANSCODE);

			if (pTransrNo != null) {
				tSerialNo.setText(pTransrNo);
				tInsuSerial.setText(pTransrNo);
			}
			if (pService_id != null) {
				tTransCode.setText(pService_id);
			}

			tRetMsg.setText(pDesc);
			tRetCode.setText(pFlag);
			tTransDate.setText(YBTFun.getCurrentDate2());
			tTransTime.setText(YBTFun.getCurrentTime2());

			tHeader.addContent(tRetCode);
			tHeader.addContent(tRetMsg);
			tHeader.addContent(tSerialNo);
			tHeader.addContent(tInsuSerial);
			tHeader.addContent(tTransDate);
			tHeader.addContent(tTransTime);
			tHeader.addContent(tBankCode);
			tHeader.addContent(tCorpNo);
			tHeader.addContent(tTransCode);

			root.addContent(tHeader);
			// tInputStream = XmlOperator.getInputStream(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 农行改逐行打印（20130903）的时候要求只有新单的返回节点变化
	 * 
	 * @return inputstream
	 */
	public static Document getABCSimpleReponse(String pFlag, String pDesc) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_RETDATA = "RetData";
		String XML_FLAG = "Flag";
		String XML_DESC = "Mesg";
		try {
			// 创建标准XML文档，"DATA"作为根节点
			ElementLis root = new ElementLis("Ret");
			doc = new Document(root);

			// 返回数据包
			ElementLis retData = new ElementLis(XML_RETDATA); // <!-- 返回数据包
			// -->
			ElementLis flag = new ElementLis(XML_FLAG); //
			ElementLis desc = new ElementLis(XML_DESC); //
			flag.setText(pFlag);
			desc.setText(pDesc);
			retData.addContent(flag);
			retData.addContent(desc);
			root.addContent(retData);
			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/*
	 * 用于产生建行需要的简单返回报文 @return inputstream
	 */
	public static Document getSimpleCCBReponse(byte[] input, String tErrorMsg) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_Transaction_Header = "Transaction_Header";
		String XML_LiBankID = "LiBankID";
		String XML_PbInsuId = "PbInsuId";
		String XML_BkPlatSeqNo = "BkPlatSeqNo";
		String XML_BkTxCode = "BkTxCode";
		String XML_BkChnlNo = "BkChnlNo";
		String XML_BkBrchNo = "BkBrchNo";
		String XML_BkTellerNo = "BkTellerNo";
		String XML_BkPlatDate = "BkPlatDate";
		String XML_BkPlatTime = "BkPlatTime";
		String XML_Tran_Response = "Tran_Response";
		String XML_BkOthDate = "BkOthDate";
		String XML_BkOthSeq = "BkOthSeq";
		String XML_BkOthRetCode = "BkOthRetCode";
		String XML_BkOthRetMsg = "BkOthRetMsg";
		String XML_Transaction_Body = "Transaction_Body";
		try {
			Document tDoc = JdomUtil.build(new ByteArrayInputStream(input));
			Element rootIn = tDoc.getRootElement();
			// 创建标准XML文档，"DATA"作为根节点
			ElementLis root = new ElementLis("Transaction");
			doc = new Document(root);
			ElementLis Transaction_Header = new ElementLis(
					XML_Transaction_Header);
			ElementLis LiBankID = new ElementLis(XML_LiBankID);
			ElementLis PbInsuId = new ElementLis(XML_PbInsuId);
			ElementLis BkPlatSeqNo = new ElementLis(XML_BkPlatSeqNo);
			ElementLis BkTxCode = new ElementLis(XML_BkTxCode);
			ElementLis BkChnlNo = new ElementLis(XML_BkChnlNo);
			ElementLis BkBrchNo = new ElementLis(XML_BkBrchNo);
			ElementLis BkTellerNo = new ElementLis(XML_BkTellerNo);
			ElementLis BkPlatDate = new ElementLis(XML_BkPlatDate);
			ElementLis BkPlatTime = new ElementLis(XML_BkPlatTime);
			ElementLis Tran_Response = new ElementLis(XML_Tran_Response);
			ElementLis BkOthDate = new ElementLis(XML_BkOthDate);
			ElementLis BkOthSeq = new ElementLis(XML_BkOthSeq);
			ElementLis BkOthRetCode = new ElementLis(XML_BkOthRetCode);
			ElementLis BkOthRetMsg = new ElementLis(XML_BkOthRetMsg);
			ElementLis Transaction_Body = new ElementLis(XML_Transaction_Body);
			root.addContent(Transaction_Header);
			root.addContent(Transaction_Body);
			Transaction_Header.addContent(LiBankID);
			Transaction_Header.addContent(PbInsuId);
			Transaction_Header.addContent(BkPlatSeqNo);
			Transaction_Header.addContent(BkTxCode);
			Transaction_Header.addContent(BkChnlNo);
			Transaction_Header.addContent(BkBrchNo);
			Transaction_Header.addContent(BkTellerNo);
			Transaction_Header.addContent(BkPlatDate);
			Transaction_Header.addContent(BkPlatTime);
			Transaction_Header.addContent(Tran_Response);
			Tran_Response.addContent(BkOthDate);
			Tran_Response.addContent(BkOthSeq);
			Tran_Response.addContent(BkOthRetCode);
			Tran_Response.addContent(BkOthRetMsg);
			Element HeaderIn = rootIn.getChild(XML_Transaction_Header);
			// 建行标志 写死CCB
			LiBankID.setText("CCB");
			// 保险公司编码
			PbInsuId.setText(HeaderIn.getChildTextTrim(XML_PbInsuId));
			// 流水号
			BkPlatSeqNo.setText(HeaderIn.getChildTextTrim(XML_BkPlatSeqNo));
			// 交易码
			BkTxCode.setText(HeaderIn.getChildTextTrim(XML_BkTxCode));
			// 渠道
			BkChnlNo.setText(HeaderIn.getChildTextTrim(XML_BkChnlNo));
			// 机构
			BkBrchNo.setText(HeaderIn.getChildTextTrim(XML_BkBrchNo));
			// 柜员号
			BkTellerNo.setText(HeaderIn.getChildTextTrim(XML_BkTellerNo));
			// 交易日期
			BkPlatDate.setText(HeaderIn.getChildTextTrim(XML_BkPlatDate));
			// 交易时间
			BkPlatTime.setText(HeaderIn.getChildTextTrim(XML_BkPlatTime));

			// 流水号
			BkOthSeq.setText(HeaderIn.getChildTextTrim(XML_BkPlatSeqNo));
			// 日期
			BkOthDate.setText(HeaderIn.getChildTextTrim(XML_BkPlatDate));

			// 错误信息
			BkOthRetMsg.setText(tErrorMsg);
			// 错误编码 1001
			BkOthRetCode.setText("1001");

			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/*
	 * 用于产生交行需要的简单返回报文 @return inputstream
	 */
	public static Document getSimpleBCMReponse(String pFlag, String pDesc,
			String pTransrNo, String pService_id) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_K_TrList = "K_TrList";
		String XML_KR_TrDate = "KR_TrDate";
		String XML_KR_TrTime = "KR_TrTime";
		String XML_K_RetCode = "K_RetCode";
		String XML_K_RetMsg = "K_RetMsg";
		String XML_KR_EntSeq = "KR_EntSeq";

		try {
			// 创建标准XML文档，"DATA"作为根节点
			ElementLis root = new ElementLis("RMBP");
			doc = new Document(root);
			// -->
			ElementLis flag = new ElementLis(XML_K_RetCode); //
			ElementLis desc = new ElementLis(XML_K_RetMsg); //

			ElementLis tK_TrList = new ElementLis(XML_K_TrList);
			ElementLis tKR_EntSeq = new ElementLis(XML_KR_EntSeq);
			ElementLis tTransExeDate = new ElementLis(XML_KR_TrDate); //
			ElementLis tTransExeTime = new ElementLis(XML_KR_TrTime); //

			if (pTransrNo != null) {
				tKR_EntSeq.setText(pTransrNo);
			}

			tTransExeDate.setText(YBTFun.getCurrentDate2());
			tTransExeTime.setText(YBTFun.getCurrentTime2());
			flag.setText(pFlag);
			desc.setText(pDesc);

			tK_TrList.addContent(tKR_EntSeq);
			tK_TrList.addContent(tTransExeDate);
			tK_TrList.addContent(tTransExeTime);

			root.addContent(flag);
			root.addContent(desc);
			root.addContent(tK_TrList);
			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/*
	 * 用于产生光大银行需要的简单返回报文 @return inputstream
	 */
	public static Document getSimpleGDBankResponse(String pFlag, String pDesc,
			String pTransrNo, String pService_id) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_MAIN = "MAIN";
		String XML_RESULTCODE = "RESULTCODE";
		String XML_TRANSRNO = "TRANSRNO";
		String XML_ERR_INFO = "ERR_INFO";

		try {

			ElementLis root = new ElementLis("RETROOT");
			doc = new Document(root);

			// 返回数据包

			ElementLis tMAIN = new ElementLis(XML_MAIN);
			ElementLis tRESULTCODE = new ElementLis(XML_RESULTCODE); // 返回码是1012
			ElementLis tTRANSRNO = new ElementLis(XML_TRANSRNO);
			ElementLis tERR_INFO = new ElementLis(XML_ERR_INFO); //

			if (pTransrNo != null) {
				tTRANSRNO.setText(pTransrNo);
			}
			if (pService_id != null) {
				tRESULTCODE.setText(pService_id);
			}

			tERR_INFO.setText(pDesc);
			tMAIN.addContent(tTRANSRNO);
			tMAIN.addContent(tRESULTCODE);
			tMAIN.addContent(tERR_INFO);

			root.addContent(tMAIN);
			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 产生邮储的错误统一返回报文
	 * 
	 * @param pFlag
	 * @param pDesc
	 * @param pTransrNo
	 * @param pService_id
	 * @return
	 */
	public static Document getSimplePSBCResponse(String pFlag, String pDesc,
			String pTransrNo, String pService_id) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_MAIN = "MAIN";
		String XML_RESULTCODE = "RESULTCODE";
		String XML_TRANSRNO = "TRANSRNO";
		String XML_ERR_INFO = "ERR_INFO";

		try {

			ElementLis root = new ElementLis("RETURN");
			doc = new Document(root);

			// 返回数据包

			ElementLis tMAIN = new ElementLis(XML_MAIN);
			ElementLis tRESULTCODE = new ElementLis(XML_RESULTCODE);
			ElementLis tTRANSRNO = new ElementLis(XML_TRANSRNO);
			ElementLis tERR_INFO = new ElementLis(XML_ERR_INFO); //

			if (pTransrNo != null) {
				tTRANSRNO.setText(pTransrNo);
			}

			tRESULTCODE.setText(pFlag);

			tERR_INFO.setText(pDesc);
			tMAIN.addContent(tRESULTCODE);
			tMAIN.addContent(tERR_INFO);
			tMAIN.addContent(tTRANSRNO);

			root.addContent(tMAIN);
			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/*
	 * 用于产生工行需要的简单返回报文 @return inputstream
	 */
	public static Document getSimpleICBCReponse(String pFlag, String pDesc,
			String pTransrNo, String pService_id) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_RETDATA = "TXLifeResponse";
		String XML_TransResult = "TransResult";
		String XML_FLAG = "ResultCode";
		String XML_Info = "ResultInfo";
		String XML_DESC = "ResultInfoDesc";

		String XML_TransRefGUID = "TransRefGUID";
		String XML_TransType = "TransType";
		String XML_TransExeDate = "TransExeDate";
		String XML_TransExeTime = "TransExeTime";
		try {
			// 创建标准XML文档，"DATA"作为根节点
			ElementLis root = new ElementLis("TXLife");
			doc = new Document(root);

			// 返回数据包
			ElementLis transResult = new ElementLis(XML_TransResult);
			ElementLis retData = new ElementLis(XML_RETDATA); // <!-- 返回数据包
			// -->
			ElementLis flag = new ElementLis(XML_FLAG); //
			ElementLis info = new ElementLis(XML_Info);
			ElementLis desc = new ElementLis(XML_DESC); //

			ElementLis tTransRefGUID = new ElementLis(XML_TransRefGUID);
			ElementLis tTransType = new ElementLis(XML_TransType); // <!--
			// 返回数据包
			// -->
			ElementLis tTransExeDate = new ElementLis(XML_TransExeDate); //
			ElementLis tTransExeTime = new ElementLis(XML_TransExeTime); //

			if (pTransrNo != null) {
				tTransRefGUID.setText(pTransrNo);
			}
			if (pService_id != null) {
				tTransType.setText(pService_id);
			}
			tTransExeDate.setText(PubFun.getCurrentDate());
			tTransExeTime.setText(PubFun.getCurrentTime());
			flag.setText(pFlag);
			desc.setText(pDesc);
			info.addContent(desc);
			transResult.addContent(flag);
			transResult.addContent(info);
			retData.addContent(tTransRefGUID);
			retData.addContent(tTransType);
			retData.addContent(tTransExeDate);
			retData.addContent(tTransExeTime);

			retData.addContent(transResult);
			root.addContent(retData);
			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 用于产生例如冲正，打印冲正等交易的简单返回报文
	 * 
	 * @return inputstream
	 */
	public static Document getSimpleBOCReponse(byte[] input, String pDesc) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_FLAG = "ResultCode";
		String XML_DESC = "ResultInfo";
		String TranDate = "TranDate";
		String TranTime = "TranTime";
		String InsuId = "InsuId";
		String ZoneNo = "ZoneNo";
		String BrNo = "BrNo";
		String TellerNo = "TellerNo";
		String TranCode = "TranCode";
		String TransNo = "TransNo";
		try {
			// 创建标准XML文档，"MAIN"作为根节点
			Document tDoc = JdomUtil.build(new ByteArrayInputStream(input));
			Element reqMain = tDoc.getRootElement().getChild("Main");
			ElementLis root = new ElementLis("InsuRet");
			ElementLis MAIN = new ElementLis("Main");
			doc = new Document(root);

			// 返回数据包
			// -->
			ElementLis ELETranDate = new ElementLis(TranDate);
			ElementLis ELETranTime = new ElementLis(TranTime);
			ElementLis ELEInsuId = new ElementLis(InsuId);
			ElementLis ELEZoneNo = new ElementLis(ZoneNo);
			ElementLis ELEBrNo = new ElementLis(BrNo);
			ElementLis ELETellerNo = new ElementLis(TellerNo);
			ElementLis ELETransNo = new ElementLis(TransNo);
			ElementLis ELETranCode = new ElementLis(TranCode);
			ElementLis flag = new ElementLis(XML_FLAG);
			ElementLis desc = new ElementLis(XML_DESC);

			MAIN.addContent(ELETranDate);
			MAIN.addContent(ELETranTime);
			MAIN.addContent(ELEInsuId);
			MAIN.addContent(ELEZoneNo);
			MAIN.addContent(ELEBrNo);
			MAIN.addContent(ELETellerNo);
			MAIN.addContent(ELETransNo);
			MAIN.addContent(ELETranCode);
			MAIN.addContent(flag);
			MAIN.addContent(desc);

			ELETranDate.setText(reqMain.getChildTextTrim("TranDate"));
			ELETranTime.setText(reqMain.getChildTextTrim("TranTime"));
			ELEInsuId.setText(reqMain.getChildTextTrim("InsuId"));
			ELEZoneNo.setText(reqMain.getChildTextTrim("ZoneNo"));
			ELEBrNo.setText(reqMain.getChildTextTrim("BrNo"));
			ELETellerNo.setText(reqMain.getChildTextTrim("TellerNo"));
			ELETransNo.setText(reqMain.getChildTextTrim("TransNo"));
			ELETranCode.setText(reqMain.getChildTextTrim("TranCode"));
			flag.setText("0001");
			desc.setText(pDesc);

			root.addContent(MAIN);

			XMLOutputter outputter = null;
			ByteArrayOutputStream tWebserviceOS = new ByteArrayOutputStream();
			// outputter = new XMLOutputter("  ", true, "GBK");
			// outputter.output(doc, tWebserviceOS);

			// tInputStream = new
			// ByteArrayInputStream(tWebserviceOS.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 华夏银行统一错误返回报文
	 * 
	 * @return inputstream
	 */
	public static Document getSimpleHXBankResponse(byte[] input, String pDesc,
			String pService_id) {
		InputStream tInputStream = null;
		Document doc = null;
		String XML_FLAG = "OKFLAG";
		String XML_DESC = "FAILDETAIL";
		String XML_APPLYNO = "APPLYNO";

		String XML_INSURNO = "INSURNO";
		try {
			// 创建标准XML文档，"MAIN"作为根节点
			Document tDoc = JdomUtil.build(new ByteArrayInputStream(input));
			Element reqMain = tDoc.getRootElement().getChild("MAIN");
			ElementLis root = new ElementLis("RETURN");
			ElementLis MAIN = new ElementLis("MAIN");
			doc = new Document(root);

			// 返回数据包
			// -->
			ElementLis flag = new ElementLis(XML_FLAG);
			ElementLis desc = new ElementLis(XML_DESC);
			ElementLis applyNO = new ElementLis(XML_APPLYNO);

			flag.setText("0");
			desc.setText(pDesc);
			applyNO.setText(reqMain.getChildTextTrim("APPLYNO"));

			MAIN.addContent(flag);
			MAIN.addContent(desc);

			if (pService_id.equals("12")) { // 当日撤单
				ElementLis insurNo = new ElementLis(XML_INSURNO);
				insurNo.setText(reqMain.getChildTextTrim("INSURNO"));
				MAIN.addContent(insurNo);
			}
			root.addContent(MAIN);

			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;

	}

	/**
	 * 输出数据流 到文件
	 */

	public void printInputStream(InputStream p_inputstream) { // 拷贝参数
		InputStream v_inputstream = p_inputstream;
		try {
			FileOutputStream fos = new FileOutputStream(new File(
					"E:\\张东\\银保通\\返回报文\\" + "_" + YBTFun.getCurrentDate2()
							+ "_" + YBTFun.getCurrentTime2() + ".xml"));
			byte[] bytes = new byte[1];
			while (v_inputstream.read(bytes) != -1) {

				fos.write(bytes);
			}
			fos.flush();
			fos.close();
			System.out.println("finish!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出数据流 到控制台
	 */
	public static void printConsoleInputStream(InputStream p_inputstream) {
		// 拷贝参数 InputStream v_inputstream = p_inputstream;
		try {
			StringBuffer sb = new StringBuffer();
			byte[] bytes = new byte[1];
			while (p_inputstream.read(bytes) != -1) {
				String str = new String(bytes);
				sb.append(str);
			}
			System.out.println("print to console :\n" + sb.toString());
			System.out.println("finish!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 减1
	 */
	public static String getBeforeNum(String p_input) {
		// 拷贝参数 InputStream v_inputstream = p_inputstream;
		if (p_input == null || p_input.equals("") || !p_input.matches("[0-9]")) {
			System.out.println("int int ");
			return "0";
		}
		int x = Integer.parseInt(p_input);
		x = x - 1;
		return (new Integer(x)).toString();

	}

	/**
	 * <p>
	 * 获取保单号的函数
	 * <p>
	 * 
	 * @return 获取保单号，如果生成失败，返回空字符串""
	 */
	public static String getContNo(String ManageCom) {
		System.out.println("---Start Save---");
		String tContNo = "";

		// 建立数据库连接
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			System.out.println("数据库连接失败");

			return "";
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);
			String tSQLQuery = "select ContNo from tab_ContNo  where Status='0' and ManageCom = '"
					+ ManageCom + "' order by ContNo";
			ExeSQL tExeSQL = new ExeSQL(conn);
			// System.out.println("执行SQL语句:" + tSQL);
			if (tExeSQL.getOneValue(tSQLQuery) == null) {
				System.out.println("执行查询保单号码语句失败或没有可用的保单号码，请在单证管理中增加新的保单号码后重试");
				conn.rollback();
				conn.close();
				return "";
			} else {
				tContNo = tExeSQL.getOneValue(tSQLQuery);
			}
			if (tContNo != null && !tContNo.equals("")) {
				String tSQLUpdate = "update  tab_ContNo  set Status='1' where ContNo='"
						+ tContNo + "' and ManageCom = '" + ManageCom + "' ";

				if (!tExeSQL.execUpdateSQL(tSQLUpdate)) {
					System.out.println("执行更新语句失败");
					conn.rollback();
					conn.close();
					return "";
				}

			}

			conn.commit();
			conn.close();
			System.out.println("---End Committed---");
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			System.out.println(e.getMessage());

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			return "";
		}

		return tContNo;
	}

	/*
	 * 获取给定日期值中的下一个工作日 @param strDate String 日期 @return String 天
	 */
	// public static String getNextWorkDay(String strDate) {
	// SSRS ssrs = null;
	// ExeSQL exeSQL = new ExeSQL();
	// String isWorkDate = null;
	// String tNextDay = null;
	// Date day = null;
	// FDate tFDate = null;
	//
	// String ret = "-1";
	// boolean flag = true;// 是否工作日判断标志
	// if (strDate == null || strDate.equals("")) {
	// return null;
	// }
	//
	// while (flag) {
	// tFDate = new FDate();
	// day = tFDate.getDate(strDate);
	// day.setDate(day.getDate() + 1);
	// tNextDay = tFDate.getString(day);
	// ssrs = exeSQL
	// .execSQL("select 1 from tab_holiday where status = '1' and EVENT_DATE='"
	// + tNextDay + "'");
	// if (ssrs == null) {
	// return null;
	// } else if (ssrs != null && ssrs.getMaxRow() < 1) {
	// ret = tNextDay;
	// flag = false;
	// }
	// strDate = tNextDay;
	// }
	//
	// return ret;
	// }
	/**
	 * 判断字符串中字符是否是数字，是的话返回true，否的话返回false。 张东 20071019
	 */
	public boolean isNumer(String str) {
		Pattern pattern = Pattern.compile("[0-9]");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * java HASH算法 SHA1 算法
	 * 
	 * @param data
	 *            进来的数据
	 * @return 返回20个字节长度的SHA输出,经过16进制编码
	 * @throws Exception
	 */
	public static byte[] SHA1Algo(byte[] data) throws Exception {
		Digest digest = new SHA1Digest();
		byte[] result = new byte[digest.getDigestSize()];
		try {
			digest.update(data, 0, data.length);
			digest.doFinal(result, 0);
		} catch (Exception e) {
			throw new Exception("SHA1运算错.");
		}
		// System.out.println("len["+result);
		return result;
	}

	/**
	 * 严格校验身份证号码 张东 2007-10-19 参照common.js 中的checkIdCard 公民身份号码是特征组合码，
	 * 由十七位数字本体码和一位数字校验码组成。 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，
	 * 三位数字顺序码和一位数字校验码。顺序码的奇数分给男性，偶数分给女性。
	 * 校验码是根据前面十七位数字码，按照ISO7064:1983.MOD11-2校验码计算出来的检验码。
	 */
	public static boolean checkIdCard(String idCard) {
		String SystemDate = YBTFun.getCurrentDate2();
		int year = Integer.parseInt(SystemDate.substring(0, 4));
		int month = Integer.parseInt(SystemDate.substring(4, 6)) + 1;
		int day = Integer.parseInt(SystemDate.substring(6));
		int yyyy; // 年
		int mm; // 月
		int dd; // 日
		// String birthday; // 生日
		// String sex; // 性别
		int id_length = idCard.length();
		if (id_length == 0) {
			mLogger.info("请输入身份证号码!");
			return false;
		}
		if (id_length != 15 && id_length != 18) {
			mLogger.info("身份证号长度应为15位或18位！");
			return false;
		}
		if (id_length == 15) {
			char[] tCode = idCard.toUpperCase().toCharArray();
			int tASC;
			for (int i = 0; i < id_length; i++) {
				// 通过ASC码表进行识别
				tASC = (int) tCode[i];
				// 不属于数字的都是非法的 数字0-9的ASC码表位于48-57之间
				if (tASC < 48 || tASC > 57) {
					mLogger.info("15位身份证号中不能有字符！");
					return false;
				}
			}
			yyyy = Integer.parseInt("19" + idCard.substring(6, 8));
			mm = Integer.parseInt(idCard.substring(8, 10));
			dd = Integer.parseInt(idCard.substring(10, 12));
			if (mm > 12 || mm <= 0) {
				mLogger.info("身份证号月份非法！");
				return false;
			}
			if (dd > 31 || dd <= 0) {
				mLogger.info("身份证号日期非法！");
				return false;
			}
			// 4,6,9,11月份日期不能超过30
			if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && (dd > 30)) {
				mLogger.info("身份证号日期非法！");
				return false;
			}
			// 判断2月份
			if (mm == 2) {
				if (isLeapYear(yyyy)) {
					if (dd > 29) {
						mLogger.info("身份证号日期非法！");
						return false;
					}
				} else {
					if (dd > 28) {
						mLogger.info("身份证号日期非法！");
						return false;
					}
				}
			}
		} else {
			char[] tCode = idCard.toUpperCase().toCharArray();
			int tASC;
			for (int i = 0; i < id_length - 1; i++) {
				// 通过ASC码表进行识别
				tASC = (int) tCode[i];
				// 不属于数字的都是非法的 数字0-9的ASC码表位于48-57之间
				if (tASC < 48 || tASC > 57) {
					mLogger.info("身份证号中前17位中不能有字符！");
					return false;
				}
			}
			tASC = (int) tCode[17];
			if (tASC < 48 || tASC > 57) {
				if (!idCard.substring(17).equals("X")
						&& !idCard.substring(17).equals("x")) {
					mLogger.info("身份证校验错误，请认真检查！");
					return false;
				}
			}
			if (idCard.indexOf("X") > 0 && idCard.indexOf("X") != 17
					|| idCard.indexOf("x") > 0 && idCard.indexOf("x") != 17) {
				mLogger.info("身份证中\"X\"输入位置不正确！");
				return false;
			}
			yyyy = Integer.parseInt(idCard.substring(6, 10));
			if (yyyy > year || yyyy < 1900) {
				mLogger.info("身份证号年度非法！");
				return false;
			}
			mm = Integer.parseInt(idCard.substring(10, 12));
			if (mm > 12 || mm <= 0) {
				mLogger.info("身份证号月份非法！");
				return false;
			}
			if (yyyy == year && mm > month) {
				mLogger.info("身份证号月份非法！");
				return false;
			}
			dd = Integer.parseInt(idCard.substring(12, 14));
			if (dd > 31 || dd <= 0) {
				mLogger.info("身份证号日期非法！");
				return false;
			}
			// 4,6,9,11月份日期不能超过30
			if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && (dd > 30)) {
				mLogger.info("身份证号日期非法！");
				return false;
			}
			// 判断2月份
			if (mm == 2) {
				if (isLeapYear(yyyy)) {
					if (dd > 29) {
						mLogger.info("身份证号日期非法！");
						return false;

					}
				} else

				{
					if (dd > 28) {
						mLogger.info("身份证号日期非法！");
						return false;
					}
				}
			}
			if (yyyy == year && mm == month && dd > day) {
				mLogger.info("身份证号日期非法！");
				return false;
			}
			if (idCard.substring(17).equals("x")
					|| idCard.substring(17).equals("X")) {
				if (!GetVerifyBit(idCard).equals("x")
						&& !GetVerifyBit(idCard).equals("X")) {
					mLogger.info("身份证校验错误，请认真检查！");
					return false;
				}
			} else {
				if (!idCard.substring(17).equals(GetVerifyBit(idCard))) {
					mLogger.info("身份证校验错误，请认真检查！");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断是否为闰年 XinYQ added on 2006-09-25
	 */
	public static boolean isLeapYear(int nYear) {
		boolean ResultLeap = false;
		ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
		return ResultLeap;
	}

	/**
	 * 计算身份证校验码 张东 20071019 参照common.js 中的checkIdCard 原理: ∑(a[i]*W[i]) mod 11 (
	 * i = 2, 3, ..., 18 )(1) "*" 表示乘号 i--------表示身份证号码每一位的序号，从右至左，最左侧为18，最右侧为1。
	 * a[i]-----表示身份证号码第 i 位上的号码 W[i]-----表示第 i 位上的权值 W[i] = 2^(i-1) mod 11 计算公式
	 * (1) 令结果为 R 根据下表找出 R 对应的校验码即为要求身份证号码的校验码C。 R 0 1 2 3 4 5 6 7 8 9 10 C 1 0
	 * X 9 8 7 6 5 4 3 2 X 就是 10，罗马数字中的 10 就是 X 15位转18位中,计算校验位即最后一位
	 */
	public static String GetVerifyBit(String id) {
		String result = null;

		int nNum = Integer.parseInt(id.substring(0, 1)) * 7
				+ Integer.parseInt(id.substring(1, 2)) * 9
				+ Integer.parseInt(id.substring(2, 3)) * 10
				+ Integer.parseInt(id.substring(3, 4)) * 5
				+ Integer.parseInt(id.substring(4, 5)) * 8
				+ Integer.parseInt(id.substring(5, 6)) * 4
				+ Integer.parseInt(id.substring(6, 7)) * 2
				+ Integer.parseInt(id.substring(7, 8)) * 1
				+ Integer.parseInt(id.substring(8, 9)) * 6
				+ Integer.parseInt(id.substring(9, 10)) * 3
				+ Integer.parseInt(id.substring(10, 11)) * 7
				+ Integer.parseInt(id.substring(11, 12)) * 9
				+ Integer.parseInt(id.substring(12, 13)) * 10
				+ Integer.parseInt(id.substring(13, 14)) * 5
				+ Integer.parseInt(id.substring(14, 15)) * 8
				+ Integer.parseInt(id.substring(15, 16)) * 4
				+ Integer.parseInt(id.substring(16, 17)) * 2;
		nNum = nNum % 11;
		switch (nNum) {
		case 0:
			result = "1";
			break;
		case 1:
			result = "0";
			break;
		case 2:
			result = "X";
			break;
		case 3:
			result = "9";
			break;
		case 4:
			result = "8";
			break;
		case 5:
			result = "7";
			break;
		case 6:
			result = "6";
			break;
		case 7:
			result = "5";
			break;
		case 8:
			result = "4";
			break;
		case 9:
			result = "3";
			break;
		case 10:
			result = "2";
			break;
		}
		return result;
	}

	/**
	 * <p>
	 * 生成流水号的函数
	 * <p>
	 * 
	 * @param cNoType
	 *            为需要生成号码的类型，例如addressno
	 * @param cNoLimit
	 *            为需要生成号码的限制条件,例如是addressno的值
	 * @return 生成的符合条件的流水号，如果生成失败，返回空字符串""，这样就可以分组生成流水号
	 */
	public static String CreateMaxNo(String cNoType, String cNoLimit) {
		// 传入的参数不能为空，如果为空，则直接返回
		if ((cNoType == null) || (cNoType.trim().length() <= 0)
				|| (cNoLimit == null)) {
			System.out.println("NoType长度错误或者NoLimit为空");

			return null;
		}
		String tReturn = null;

		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			System.out.println("CreateMaxNo : fail to get db connection");

			return tReturn;
		}

		int tMaxNo = 0;

		try {
			// 开始事务
			// 查询结果有3个： -- added by Fanym
			// 全部采用直接执行SQL语句，只要有其他事务锁定了本行，立即返回NULL
			// 如果没有锁定，则本事务锁定，查询得到结果则UPDATE，没有则INSERT
			conn.setAutoCommit(false);
			StringBuffer tSBql = new StringBuffer(256);
			tSBql.append("select MaxNo from LDMaxNo where notype='");
			tSBql.append(cNoType);
			tSBql.append("' and nolimit='");
			tSBql.append(cNoLimit);
			tSBql.append("' for update");
			// 如果数据库类型是ORACLE的话，需要添加nowait属性，以防止锁等待
			if (SysConst.DBTYPE.compareTo("ORACLE") == 0) {
				// 去掉nowait的限制
				// tSBql.append(" nowait");
			}

			ExeSQL exeSQL = new ExeSQL(conn);
			String result = null;
			result = exeSQL.getOneValue(tSBql.toString());

			// 测试返回bull时
			if (exeSQL.mErrors.needDealError()) {
				System.out.println("查询LDMaxNo出错，请稍后!");
				conn.rollback();
				conn.close();

				return null;
			}

			if ((result == null) || result.equals("")) {
				tSBql = new StringBuffer(256);
				tSBql.append("insert into ldmaxno(notype, nolimit, maxno) values('");
				tSBql.append(cNoType);
				tSBql.append("', '");
				tSBql.append(cNoLimit);
				tSBql.append("', 1)");

				exeSQL = new ExeSQL(conn);
				if (!exeSQL.execUpdateSQL(tSBql.toString())) {
					System.out.println("CreateMaxNo 插入失败，请重试!");
					conn.rollback();
					conn.close();

					return null;
				} else {
					tMaxNo = 1;
				}
			} else {
				tSBql = new StringBuffer(256);
				tSBql.append("update ldmaxno set maxno = maxno + 1 where notype = '");
				tSBql.append(cNoType);
				tSBql.append("' and nolimit = '");
				tSBql.append(cNoLimit);
				tSBql.append("'");

				exeSQL = new ExeSQL(conn);
				if (!exeSQL.execUpdateSQL(tSBql.toString())) {
					System.out.println("CreateMaxNo 更新失败，请重试!");
					conn.rollback();
					conn.close();

					return null;
				} else {
					tMaxNo = Integer.parseInt(result) + 1;
				}
			}

			conn.commit();
			conn.close();
		} catch (Exception Ex) {
			try {
				conn.rollback();
				conn.close();

				return null;
			} catch (Exception e1) {
				e1.printStackTrace();

				return null;
			}
		}

		String tStr = String.valueOf(tMaxNo);
		tReturn = tStr.trim();
		System.out.println("------tReturn:" + tReturn);
		return tReturn;
	}

	/**
	 * 一个中文两个字节,一行打印bytes个字节,为了防止出现乱码,需要在bytes*n的位置填上空格.
	 * 
	 * @param tStr
	 *            需要打印的字符
	 * @param bytes
	 *            一行打印多少个字节
	 * @return
	 */
	public static String dataformat(String tStr, int bytes) {
		if (tStr == null || tStr.equals(""))
			return tStr;
		if (bytes <= 1)
			return tStr;
		for (int i = 0; i < tStr.length(); i++) {
			String tmp = tStr.substring(i, i + 1);
			int j = tStr.substring(0, i).getBytes().length;
			if (tmp.matches("[\u4e00-\u9fa5]+")) {
				if ((j + 1) % bytes == 0) {
					System.out.println(tStr.getBytes().length);
					String ta = tStr.substring(0, i);
					String tb = tStr.substring(i);
					tStr = ta + " " + tb;
					System.out.println(tStr.getBytes().length);
					System.out.println(i + "[" + tmp + "]" + "为汉字");
				}
			}
		}
		return tStr;
	}

	/**
	 * 一个中文两个字节,一行打印bytes个字节,为了防止出现乱码,需要在bytes*n的位置填上换行符.
	 * 
	 * @param tStr
	 * @param bytes
	 * @param temp
	 * @return
	 */
	public static String dataformat(String tStr, int bytes, String temp) {
		if (tStr == null || tStr.equals(""))
			return tStr;
		if (bytes <= 1)
			return tStr;

		// 加空格防止出现对一个中文进行切割
		for (int i = 0; i < tStr.length(); i++) {
			String tmp = tStr.substring(i, i + 1);
			int j = tStr.substring(0, i).getBytes().length;
			// 汉字处理往后挪一空格,空格用|填写.
			if (tmp.matches("[\u4e00-\u9fa5]+")) {
				if ((j + 1) % bytes == 0) {
					System.out.println(tStr.getBytes().length);
					String ta = tStr.substring(0, i);
					String tb = tStr.substring(i);
					tStr = ta + " " + tb;
					System.out.println(tStr.getBytes().length);
					System.out.println(i + "[" + tmp + "]" + "为汉字");
				}

			}
		}

		Vector<String> tVector = new Vector<String>();
		System.out.println("加空格后:[" + tStr + "]");

		int[] k = new int[tStr.length()];
		int m = 1;
		k[0] = 0;
		int Strlen = tStr.getBytes().length;

		if (Strlen <= bytes)
			return tStr;
		for (int i = 0; i < tStr.length(); i++) {

			if (i == 0)
				continue;
			int j = tStr.substring(0, i).getBytes().length;

			if (j % bytes == 0) {

				k[m] = i;
				String tStra = tStr.substring(k[m - 1], i);

				tVector.add(tStra);
				m++;

			}

			if (j == (Strlen - Strlen % bytes)) {
				tVector.add(tStr.substring(k[m - 1]));

				break;
			}

		}

		tStr = "";
		for (int i = 0; i < tVector.size(); i++) {
			String tStra = tVector.get(i);

			if (i + 1 == tVector.size())

				tStr = tStr + tStra;// 最后一个不需要加|
			else
				tStr = tStr + tStra + temp;
		}

		return tStr;
	}

	/**
	 * 例如 [ab ] [abc ] 后补空格
	 * 
	 * @param tStr
	 *            字符串
	 * @param len
	 *            固定长度
	 * @return
	 */
	public static String getForMatStr(String tStr, int len) {
		if (tStr == null || tStr.equals(""))
			return "";
		if (tStr.getBytes().length >= len)
			return tStr;
		int tStrLen = tStr.getBytes().length;
		for (int i = 0; i < len - tStrLen; i++)
			tStr = tStr + " ";
		return tStr;
	}

	/**
	 * 例如 [ab　] [abc　] 后补空格　补中文全角空格-for ABC 打印
	 * 
	 * @param tStr
	 *            字符串
	 * @param len
	 *            固定长度
	 * @return
	 */
	public static String getForMatChineseStr(String tStr, int len) {
		if (tStr == null || tStr.equals(""))
			return "";
		if (tStr.getBytes().length >= len)
			return tStr;
		int tStrLen = tStr.length();
		for (int i = 0; i < len - tStrLen; i++)
			tStr = tStr + "　";
		return tStr;
	}

	public static String getForMatDate(String strDate, String type) {
		if (strDate == null)
			return "";
		if (strDate.equals(""))
			return "";
		String result = "";
		if (type.equals("yyyyMMdd")) {
			String Year = strDate.substring(0, 4);
			String Month = strDate.substring(4, 6);
			String Day = strDate.substring(6, 8);
			result = Year + "年" + Month + "月" + Day + "日";
		} else if (type.equals("yyyy-MM-dd")) {
			result = YBTFun.getYear(strDate) + "年" + YBTFun.getMonth(strDate)
					+ "月" + YBTFun.getDay(strDate) + "日";
		}
		return result;
	}

	public static String getForMatLCBnfLot(String str) {
		String result = str;
		if (str.equals("1"))
			result = "第一顺序";
		else if (str.equals("2"))
			result = "次顺序";
		else if (str.equals("3"))
			result = "第三顺序";
		else if (str.equals("4"))
			result = "第四顺序";
		return result;
	}

	/**
	 * 计算隔多少年
	 * 
	 * @param br
	 *            起始日
	 * @param ccd
	 *            结束日
	 * @return
	 */
	public static String calInterval(String br, String ccd) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(br);
		Date endDate = fDate.getDate(ccd);
		return String.valueOf(PubFun.calInterval(startDate, endDate, "Y"));
	}

	/**
	 * 按字节截取
	 * 
	 * @param source
	 * @param start
	 * @param num
	 * @return
	 */
	public static String subByte(String source, int start, int num) {
		byte[] bytes = source.getBytes();
		byte[] descs = new byte[num];
		for (int i = 0; i < num; i++) {
			descs[i] = bytes[start + i - 1];
		}
		return new String(descs);
	}

	/**
	 * 取其他的日期
	 * 
	 * @param tDate
	 *            今天
	 * @param tStr
	 *            时间格式
	 * @param i
	 *            正数往前 负数往后
	 * @return
	 */
	public static String getOtherDate(String tDate, String tStr, int i) {
		String tYestoday = "";
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(tStr);
			Date date;
			date = sdf.parse(tDate);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, i);
			date = cal.getTime();
			Format formatter = new SimpleDateFormat(tStr);
			tYestoday = formatter.format(date);
			System.out.println(tYestoday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tYestoday;
	}

	/**
	 * 按照分割得到数量
	 * 
	 * @param total
	 * @param size
	 * @return
	 */
	public static int returnCount(int total, int size) {
		if (total % size == 0) {
			return total / size;
		} else {
			return total / size + 1;
		}
	}

	/**
	 * 把HH:mm:SS格式转换成HHMMSS格式
	 * 
	 * @param time
	 *            格式 HH:mm:SS HH24小时制
	 * @return
	 */
	public static String tranTimeFormat(String time) {
		return time.replaceAll(":", "");
	}

	/**
	 * 把HHMMSS格式转换成HH:mm:SS格式
	 * 
	 * @param time
	 *            格式 HHMMSS HH24小时制
	 * @return HH:mm:SS
	 */
	public static String tranTimeFormat2(String time) {
		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
				+ time.substring(4, 6);
	}

	/**
	 * 把数值转换成带两位小数点的
	 * 
	 * @param prem
	 * @return
	 */
	public static String numberFormat(String prem) {
		if (prem.equals("") || prem == null) {
			return null;
		}
		Double num = new Double(prem);
		DecimalFormat df = new DecimalFormat("0.00");
		String number = df.format(num);
		return number;
	}

	public static String strToUpp(String str) {
		if (str == null || "".equals(str)) {
			return "";
		} else {
			return str.toUpperCase();
		}
	}

	/**
	 * 截取止定字节数的字符串 包括中文
	 * 
	 * @param tStr
	 * @param bytes
	 * @return
	 */
	public static String subStringformat(String str, int num) {
		int len = str.getBytes().length;
		if (len < num) {
			return str;
		}
		char[] charArray = str.toCharArray();
		String subStr = "";
		int index = 0;
		for (int i = 0, j = 0; i < num;) {
			char tempchar = charArray[j];
			String temp = String.valueOf(tempchar);
			if (Character.getType(tempchar) == Character.OTHER_LETTER
					|| temp.getBytes().length == 2) {
				// 如果这个字符是一个汉字
				i = i + 2;
				if (i > num) {
					// 如果num正好截取到半个汉字的时候，跳过此次for循环。
					// 如果num正好截取到一个完整的汉字的时候，继续执行下面的index++等语句。
					if (i == (num + 1)) {
						continue;
					}
				}
			} else {
				i++;
			}
			index++;
			j++;
		}
		subStr = str.substring(0, index);
		System.out.println("the result is : " + subStr + "    " + num);
		return subStr;
	}

	/**
	 * 密钥生成算法 返回密钥
	 * 
	 * @return
	 */
	public static String getKey() {
		String key = "";
		String strKey = "1234567890abcdef";
		java.util.Random random = new java.util.Random();
		for (int i = 0; i < 16; i++) {
			int in = random.nextInt(16);
			key += strKey.charAt(in);
		}
		return key;
	}

	/**
	 * 获取华夏银行的客户经理信息
	 * 
	 * @param codetype
	 * @param bank_code
	 *            客户经理代码
	 * @param bankid
	 * @param colum
	 *            codename 去客户姓名 managerqual 销售许可证
	 * @return
	 */
	public static String getManagerName(String codetype, String bank_code,
			String bankid, String colum) {
		// 如果没有则返回""
		String minsurer_Code = "";
		String tSql = "select " + colum + " from Lkhxmapping "
				+ "where bank_code ='" + bank_code + "' and codetype ='"
				+ codetype + "' and BANKID = '" + bankid + "'";

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS.MaxRow > 0)
			minsurer_Code = tSSRS.GetText(1, 1);

		return minsurer_Code;
	}

	/**
	 * 把中文格式的日期改成数字格式
	 * 
	 * @param date
	 *            如2013年10月10日
	 * @param dateType
	 *            可以为yyyy-MM-dd 或者 yyyyMMdd
	 * @return
	 */
	public static String tranDateChnToNum(String date, String dateType) {
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		String dateStr = "";
		if ("yyyy-MM-dd".equals(dateType)) {
			dateStr = year + "-" + month + "-" + day;
		} else if ("yyyyMMdd".equals(dateType)) {
			dateStr = year + month + day;
		}
		return dateStr;
	}

	/**
	 * 元to万转换
	 * 
	 * @param cent
	 * @return
	 */
	public static String tran_YuanToWan(String yuan) {
		if (yuan.equals("") || yuan == null) {
			return null;
		}
		Double fen = new Double(yuan);
		DecimalFormat df = new DecimalFormat("0.00");
		String wan = df.format(fen.doubleValue() / 10000);
		System.out.println("wan:" + wan);
		return wan;
	}

	/**
	 * 主函数,用于调试
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Thread.sleep(500);
		// String a =
		// "保险费的百分之四十用于购买\"光大永明盛世阳光两全保险\"、\"光大永明附加盛世阳光防癌疾病保险(A款)\"和\"光大永明附加盛世阳光防癌疾病保险(B款)\"，保险费的百分之六十用于购买\"光大永明附加盛世阳光投资连结保险\"";
		// // a = "a大写";
		//
		// a = dataformat("ABC我", 5, "|");
		// System.out.println("加空格后:[" + a);
		// String source = "123     111111                 ";
		// System.out.println(subByte(source,7,10));

//		System.out.println(getRela_bank_Code("04", "0", "1","02"));
		System.out.println(YBTFun.getBackDes("1200","province","07"));
		
		System.out.println(NumberFormat("2.555444"));
	}

}
