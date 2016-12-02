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
// ֻ����ǰ���õķ���,��̨��YBTFun.java��ǰ�û��Ĳ�һ��.. modify by hwf
public class YBTFun {
	private static Logger mLogger = Logger.getLogger(YBTFun.class);

	// У�����֤��ʱ����Ҫ�������� zhangd 20071019
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

	// ���ر����е�����
	public static String getBackDes(String tStr, String type) {
		String tSql = "SELECT Code_Des FROM BankAndInsurerCodeMapping WHERE Insurer_Code = '"
				+ tStr + "' AND CodeType = '" + type + "'";
		ExeSQL tExeSQL = new ExeSQL();
		String tDes = tExeSQL.getOneValue(tSql);
		return tDes;
	}

	/**
	 * ����ӳ����е����Ľ���
	 * 
	 * @param tStr
	 * @param type
	 *            ����
	 * @param tBankCode
	 *            ����
	 * @return ��������
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
		// modify by hwf at 2008��10��23��
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
			name = "����";
		}
		if (bankcode.equals("02")) {
			name = "����";
		}
		if (bankcode.equals("03")) {
			name = "��ҵ����";
		}
		if (bankcode.equals("04")) {
			name = "�ʴ�";
		}
		if (bankcode.equals("05")) {
			name = "ũ��";
		}
		return name;
	}

	// �����Ŵ��ڴ��������ĵ�λ"��"ת��Ϊ�п���ϵͳ�е�"Ԫ"
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

	// ���п���ϵͳ�е�"Ԫ"ת��Ϊ���Ŵ��ڵ�λ"��"
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
	 * ��Service_CodeӰ�䵽Service_Id,��Ӱ�䵽sub_service_id,�����������Ӧ��Document
	 * 
	 * @param pTXLifeRequest
	 *            ����Bean
	 * @param pDocument
	 *            ԭʼ�ļ�
	 * @param psub_service_order
	 *            �ǵ����ӷ����˳��
	 * @return String sub_service
	 */
	//
	// public static String tran_FunctionFlag(TXLifeRequest pTXLifeRequest,
	// String pOldFunctionFlag, String psub_service_order) {
	//
	// //
	// ��ѯ"����ͨ�������ñ�tab_midplat_service_config"��"�������tab_midplat_service",��ȡ�ӷ������
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
	// ���������ּ۵���ݺ�����Ϲ̶��ִ�(��������:�µ��б�)
	public static String addRemark(String year, String Remark) {
		String note = "";
		if (Remark.equals("")) {
			Remark = " ��ĩ";
		}
		note = year + Remark;
		return note;
	}

	public static String addRemark(String year) {
		String note = "";
		note = year + " ��ĩ";
		return note;
	}

	public static String getCurrentDateTime() {
		String curDate = PubFun.getCurrentDate();
		String curTime = PubFun.getCurrentTime();
		return curDate + curTime;
	}

	/**
	 * �õ���ǰϵͳ���� author: YT
	 * 
	 * @return ��ǰ���ڵĸ�ʽ�ַ���,���ڸ�ʽΪ"yyyyMMdd"
	 */
	public static String getCurrentDate2() {
		String pattern = "yyyyMMdd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * �õ���ǰϵͳʱ�� author: YT
	 * 
	 * @return ��ǰʱ��ĸ�ʽ�ַ�����ʱ���ʽΪ"HHmmss"
	 */
	public static String getCurrentTime2() {
		String pattern = "HHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * �õ���ǰϵͳʱ�� author: YT
	 * 
	 * @return ��ǰʱ��ĸ�ʽ�ַ�����ʱ���ʽΪ"HH:mm:ss"
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
	// paydatechn = YBTFun.getYear(paydate) + "��"
	// + YBTFun.getMonth(paydate) + "��" +
	// // ���ɷ�����ǰ��һ��(�����»�����ͨ�����޸�)
	// YBTFun.getDay(paydate) + "��";
	// return paydatechn;
	// } else {
	// return " ";
	// }
	// }
	/**
	 * ͨ�����չ�˾�������ѯ���з�����
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
	 * ͨ�����չ�˾�������ѯ���з�����
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
	 * ͨ�����з������ѯ���չ�˾������
	 * 
	 * @param tbank_code
	 *            String
	 * @param tcodetype
	 *            String
	 * @return String
	 */
	public static String getinsurer_Code(String tbank_code, String tcodetype,
			String tBankID) {
		// ���û���򷵻�""
		String minsurer_Code = "";
		String tSql = "select insurer_Code from BankAndInsurerCodeMapping "
				+ "where bank_code ='" + tbank_code + "' and codetype ='"
				+ tcodetype + "' and BANKID = '" + tBankID + "'";
		// �������� ExeSQL.getOneValue();ռ��ʱ��400+ms
		// ExeSQL.execSQL();ռ��ʱ��10+ms

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
	 * ͨ�����з���ϵ�����ѯ���չ�˾����ϵ����
	 * @param relation	��ϵ����
	 * @param banksex	Ͷ�����Ա�	
	 * @param insuresex	�������Ա�
	 * @param tBankID	���д���
	 * @return
	 */
	public static String getRela_insu_Code(String relation, String banksex,
			String insuresex, String tBankID) {
		// ���û���򷵻�""
		String minsurer_Code = "";
		String tSql = "select insure_code from relation "
				+ "where bank_code ='" + relation + "'and bank_sex='" + banksex
				+ "' and insure_sex ='" + insuresex + "' and BANKID = '"
				+ tBankID + "'";
		// �������� ExeSQL.getOneValue();ռ��ʱ��400+ms
		// ExeSQL.execSQL();ռ��ʱ��10+ms

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
	 *  ͨ�����չ�˾��ϵ�����ѯ���з���ϵ����
	 * @param relation ��ϵ����
	 * @param banksex	Ͷ�����Ա�
	 * @param insusex	�������Ա�
	 * @param tBankID	���д���
	 * @return
	 */
	public static String getRela_bank_Code(String relation, String banksex,
			String insusex, String tBankID) {
		// ���û���򷵻�""
		String minsurer_Code = "";
		String tSql = "select bank_code from relation "
				+ "where insure_code ='" + relation + "' and bank_sex ='"
				+ banksex + "'and insure_sex='" + insusex + "' and BANKID = '"
				+ tBankID + "'";
		// �������� ExeSQL.getOneValue();ռ��ʱ��400+ms
		// ExeSQL.execSQL();ռ��ʱ��10+ms

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
	 * ͨ�������Ų�ѯ�µ��б�ʱ����ˮ��
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
	 * ���ظ�ICBC�ı�������ʾ����
	 * 
	 * @param tContNo
	 *            String
	 * @return String
	 */
	public static String getInsuYear(String tInsuYearFlag, String tInsuYear) {
		String mInsuYear;
		if (tInsuYearFlag.equals("5")) {
			mInsuYear = "����";
		} else {
			mInsuYear = tInsuYear;
		}

		return mInsuYear;
	}

	/**
	 * ���ּӶ��ŷָ��� �� 1234567.01 --> 1,234,567.01
	 * 
	 * @param tStr
	 * @return
	 */
	public static String NumberFormat(String tStr) {
		// ��toԪ
		// tStr = tran_FenToYuan(tStr);
		double d = java.lang.Double.parseDouble(tStr);
		System.out.println(d);
		// �õ����ص�ȱʡ��ʽ
		NumberFormat nf1 = NumberFormat.getInstance();
		nf1.setMinimumFractionDigits(2);
		nf1.setMaximumFractionDigits(2);
		String tStra = nf1.format(d);
		if (tStra.indexOf(".") == -1)
			tStra = tStra + ".00";
		return tStra;
	}

	public static String NumberFormatFen(String tStr) {
		// ��toԪ
		double d = java.lang.Double.parseDouble(tStr);
		System.out.println(d);
		// �õ����ص�ȱʡ��ʽ
		DecimalFormat df = new DecimalFormat("#.##");
		String tStra = df.format(d);
		if (tStra.indexOf(".") == -1)
			tStra = tStra + ".00";
		return tStra;
	}

	/**
	 * ���ظ�ICBC�ı�������ʾ����
	 * 
	 * @param tContNo
	 *            String
	 * @return String
	 */
	public static String getInsuYear(String tInsuYearFlag, String tInsuYear,
			String bankID) {
		String mInsuYear;
		if (tInsuYearFlag.equals("5")) {
			mInsuYear = "����";
		} else {
			mInsuYear = tInsuYear;
		}

		return mInsuYear;
	}

	/**
	 * ͨ��Ͷ���ʻ����룬��ȡ�ʻ�����
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
	// * ͨ�����з���������ѯ���չ�˾������
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
	 * ��һ����ת��double,��С������һ�������ƶ�λ��
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

	// ������ǰ��һ��
	// public static String tranBeforDate(String Date) {
	// String BeforDate = PubFun.calDate(Date, -1, "D", "");
	// return BeforDate;
	// }

	/**
	 * ���ڸ�ʽת������(����:20050817)ת��Ϊ(����:2005-08-17)
	 * 
	 * @param strDate
	 *            (�����ַ�������ʽ:yyyyMMdd)
	 * @return String (�����ַ�������ʽ:yyyy-MM-dd)
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
				System.out.println("���ڸ�ʽ����ȷ��");
			}
		}
		return tString;
	}

	/**
	 * ��yyyy-mm-dd��ʽת��Ϊyyyymmdd ��2008-1-19 -> 20080119
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

	// ��Сд���ת���ɴ�д���
	public static String getChnMoney(String money) {
		if (money.equals("") || money == null) {
			money = "0.00";
		}
		double Money = Double.parseDouble(money);
		return com.sinosoft.lis.pubfun.PubFun.getChnMoney(Money);
	}

	/**
	 * �������Ϊ�֣���ɴ�д�Ľ�� ��10000 Ϊ100Ԫ��
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
	// paydatechn = YBTFun.getYear(strBeginDate) + "��"
	// + YBTFun.getMonth(strBeginDate) + "��"
	// + YBTFun.getDay(strBeginDate) + "��";
	// payenddate = PubFun.calDate(payenddate, -1, "D", ""); //$NON-NLS-2$
	// paydatechn += " �� " + YBTFun.getYear(payenddate) + "��" //$NON-NLS-2$
	// + YBTFun.getMonth(payenddate) + "��"
	// + YBTFun.getDay(payenddate) + "��";
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

			paydatechn = "�ɷ���" + payendyear + "����";

			return paydatechn;
		} else {
			return " ";
		}
	}

	/**
	 * ��ȡ����ֵ�е����
	 * 
	 * @param strDate
	 *            String ���ڣ���/��/�գ�
	 * @return String ��
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
	 * ��ȡ����ֵ�е��·�
	 * 
	 * @param strDate
	 *            String ����
	 * @return String ��
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
	 * ��ȡ��������ֵ�е���
	 * 
	 * @param strDate
	 *            String ����
	 * @return String ��
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
	 * ��ȡ�Ӵ��������г��ֵ� n �ε�λ��
	 * 
	 * @param strMain
	 *            String ���ַ���
	 * @param strSub
	 *            String ���ַ���
	 * @param intTimes
	 *            int ���ִ���
	 * @return int λ��ֵ,����Ӵ���������û�г���ָ������,�򷵻�-1
	 */
	public static int getPos(String strMain, String strSub, int intTimes) {
		int intCounter = 0; // ѭ������
		int intPosition = 0; // λ�ü�¼
		int intLength = strSub.length(); // �Ӵ�����

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
	 * ���ڽ�������ת���ֽ�����ķ���
	 * 
	 * @param position
	 *            ���ݵ�λ��
	 * @param inputstream
	 *            ������
	 * @param fields
	 *            ���ڴ��������н���������Ϣ��HashMap
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
			// ��������쳣,�����̼�������ȥ,ֻ����־�б�������¼
			mLogger.error("�������ݵĹ����з����쳣��" + e1);
			return null;
		}

	}

	/**
	 * ���ڲ��������������ӡ�����Ƚ��׵ļ򵥷��ر���
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
			// ������׼XML�ĵ���"DATA"��Ϊ���ڵ�
			ElementLis root = new ElementLis("TranData");
			doc = new Document(root);

			// �������ݰ�
			ElementLis retData = new ElementLis(XML_RETDATA); // <!-- �������ݰ�
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
	 * ���ڲ�����һ��ũ����Ҫ�ļ򵥷��ر��� @return inputstream ֱ�Ӵ�ǰ�û����ش���ʱ ʹ��
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
	// // �������ݰ�
	// ElementLis tHeader = new ElementLis(XML_HEADER);
	//
	// ElementLis tRetCode = new ElementLis(XML_RETCODE); // ��������1012
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
	// mLogger.info("-------------���ǰ��ҵ���ͷ---------------");
	// int c = 0;
	// ByteArrayOutputStream vTemp = new ByteArrayOutputStream();
	// while ((c = input.read()) != -1) {
	// vTemp.write(c);
	// }
	// byte[] bPack = vTemp.toByteArray();
	// String xmlStr = new String(bPack); //ȥ��XMLͷ <?xml version="1.0"
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
	 * ���ڲ�����һ��ũ����Ҫ�ļ򵥷��ر��� @return inputstream �Ӻ��û����ش���ʱ ʹ��
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

			// �������ݰ�
			ElementLis tHeader = new ElementLis(XML_HEADER);

			ElementLis tRetCode = new ElementLis(XML_RETCODE); // ��������1012
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
	 * ũ�и����д�ӡ��20130903����ʱ��Ҫ��ֻ���µ��ķ��ؽڵ�仯
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
			// ������׼XML�ĵ���"DATA"��Ϊ���ڵ�
			ElementLis root = new ElementLis("Ret");
			doc = new Document(root);

			// �������ݰ�
			ElementLis retData = new ElementLis(XML_RETDATA); // <!-- �������ݰ�
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
	 * ���ڲ���������Ҫ�ļ򵥷��ر��� @return inputstream
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
			// ������׼XML�ĵ���"DATA"��Ϊ���ڵ�
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
			// ���б�־ д��CCB
			LiBankID.setText("CCB");
			// ���չ�˾����
			PbInsuId.setText(HeaderIn.getChildTextTrim(XML_PbInsuId));
			// ��ˮ��
			BkPlatSeqNo.setText(HeaderIn.getChildTextTrim(XML_BkPlatSeqNo));
			// ������
			BkTxCode.setText(HeaderIn.getChildTextTrim(XML_BkTxCode));
			// ����
			BkChnlNo.setText(HeaderIn.getChildTextTrim(XML_BkChnlNo));
			// ����
			BkBrchNo.setText(HeaderIn.getChildTextTrim(XML_BkBrchNo));
			// ��Ա��
			BkTellerNo.setText(HeaderIn.getChildTextTrim(XML_BkTellerNo));
			// ��������
			BkPlatDate.setText(HeaderIn.getChildTextTrim(XML_BkPlatDate));
			// ����ʱ��
			BkPlatTime.setText(HeaderIn.getChildTextTrim(XML_BkPlatTime));

			// ��ˮ��
			BkOthSeq.setText(HeaderIn.getChildTextTrim(XML_BkPlatSeqNo));
			// ����
			BkOthDate.setText(HeaderIn.getChildTextTrim(XML_BkPlatDate));

			// ������Ϣ
			BkOthRetMsg.setText(tErrorMsg);
			// ������� 1001
			BkOthRetCode.setText("1001");

			// tInputStream = XmlOperator.getInputStream(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/*
	 * ���ڲ���������Ҫ�ļ򵥷��ر��� @return inputstream
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
			// ������׼XML�ĵ���"DATA"��Ϊ���ڵ�
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
	 * ���ڲ������������Ҫ�ļ򵥷��ر��� @return inputstream
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

			// �������ݰ�

			ElementLis tMAIN = new ElementLis(XML_MAIN);
			ElementLis tRESULTCODE = new ElementLis(XML_RESULTCODE); // ��������1012
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
	 * �����ʴ��Ĵ���ͳһ���ر���
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

			// �������ݰ�

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
	 * ���ڲ���������Ҫ�ļ򵥷��ر��� @return inputstream
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
			// ������׼XML�ĵ���"DATA"��Ϊ���ڵ�
			ElementLis root = new ElementLis("TXLife");
			doc = new Document(root);

			// �������ݰ�
			ElementLis transResult = new ElementLis(XML_TransResult);
			ElementLis retData = new ElementLis(XML_RETDATA); // <!-- �������ݰ�
			// -->
			ElementLis flag = new ElementLis(XML_FLAG); //
			ElementLis info = new ElementLis(XML_Info);
			ElementLis desc = new ElementLis(XML_DESC); //

			ElementLis tTransRefGUID = new ElementLis(XML_TransRefGUID);
			ElementLis tTransType = new ElementLis(XML_TransType); // <!--
			// �������ݰ�
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
	 * ���ڲ��������������ӡ�����Ƚ��׵ļ򵥷��ر���
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
			// ������׼XML�ĵ���"MAIN"��Ϊ���ڵ�
			Document tDoc = JdomUtil.build(new ByteArrayInputStream(input));
			Element reqMain = tDoc.getRootElement().getChild("Main");
			ElementLis root = new ElementLis("InsuRet");
			ElementLis MAIN = new ElementLis("Main");
			doc = new Document(root);

			// �������ݰ�
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
	 * ��������ͳһ���󷵻ر���
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
			// ������׼XML�ĵ���"MAIN"��Ϊ���ڵ�
			Document tDoc = JdomUtil.build(new ByteArrayInputStream(input));
			Element reqMain = tDoc.getRootElement().getChild("MAIN");
			ElementLis root = new ElementLis("RETURN");
			ElementLis MAIN = new ElementLis("MAIN");
			doc = new Document(root);

			// �������ݰ�
			// -->
			ElementLis flag = new ElementLis(XML_FLAG);
			ElementLis desc = new ElementLis(XML_DESC);
			ElementLis applyNO = new ElementLis(XML_APPLYNO);

			flag.setText("0");
			desc.setText(pDesc);
			applyNO.setText(reqMain.getChildTextTrim("APPLYNO"));

			MAIN.addContent(flag);
			MAIN.addContent(desc);

			if (pService_id.equals("12")) { // ���ճ���
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
	 * ��������� ���ļ�
	 */

	public void printInputStream(InputStream p_inputstream) { // ��������
		InputStream v_inputstream = p_inputstream;
		try {
			FileOutputStream fos = new FileOutputStream(new File(
					"E:\\�Ŷ�\\����ͨ\\���ر���\\" + "_" + YBTFun.getCurrentDate2()
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
	 * ��������� ������̨
	 */
	public static void printConsoleInputStream(InputStream p_inputstream) {
		// �������� InputStream v_inputstream = p_inputstream;
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
	 * ��1
	 */
	public static String getBeforeNum(String p_input) {
		// �������� InputStream v_inputstream = p_inputstream;
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
	 * ��ȡ�����ŵĺ���
	 * <p>
	 * 
	 * @return ��ȡ�����ţ��������ʧ�ܣ����ؿ��ַ���""
	 */
	public static String getContNo(String ManageCom) {
		System.out.println("---Start Save---");
		String tContNo = "";

		// �������ݿ�����
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@������
			System.out.println("���ݿ�����ʧ��");

			return "";
		}

		try {
			// ��ʼ��������
			conn.setAutoCommit(false);
			String tSQLQuery = "select ContNo from tab_ContNo  where Status='0' and ManageCom = '"
					+ ManageCom + "' order by ContNo";
			ExeSQL tExeSQL = new ExeSQL(conn);
			// System.out.println("ִ��SQL���:" + tSQL);
			if (tExeSQL.getOneValue(tSQLQuery) == null) {
				System.out.println("ִ�в�ѯ�����������ʧ�ܻ�û�п��õı������룬���ڵ�֤�����������µı������������");
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
					System.out.println("ִ�и������ʧ��");
					conn.rollback();
					conn.close();
					return "";
				}

			}

			conn.commit();
			conn.close();
			System.out.println("---End Committed---");
		} catch (Exception e) {
			// @@������
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
	 * ��ȡ��������ֵ�е���һ�������� @param strDate String ���� @return String ��
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
	// boolean flag = true;// �Ƿ������жϱ�־
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
	 * �ж��ַ������ַ��Ƿ������֣��ǵĻ�����true����Ļ�����false�� �Ŷ� 20071019
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
	 * java HASH�㷨 SHA1 �㷨
	 * 
	 * @param data
	 *            ����������
	 * @return ����20���ֽڳ��ȵ�SHA���,����16���Ʊ���
	 * @throws Exception
	 */
	public static byte[] SHA1Algo(byte[] data) throws Exception {
		Digest digest = new SHA1Digest();
		byte[] result = new byte[digest.getDigestSize()];
		try {
			digest.update(data, 0, data.length);
			digest.doFinal(result, 0);
		} catch (Exception e) {
			throw new Exception("SHA1�����.");
		}
		// System.out.println("len["+result);
		return result;
	}

	/**
	 * �ϸ�У�����֤���� �Ŷ� 2007-10-19 ����common.js �е�checkIdCard ������ݺ�������������룬
	 * ��ʮ��λ���ֱ������һλ����У������ɡ� ����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬
	 * ��λ����˳�����һλ����У���롣˳����������ָ����ԣ�ż���ָ�Ů�ԡ�
	 * У�����Ǹ���ǰ��ʮ��λ�����룬����ISO7064:1983.MOD11-2У�����������ļ����롣
	 */
	public static boolean checkIdCard(String idCard) {
		String SystemDate = YBTFun.getCurrentDate2();
		int year = Integer.parseInt(SystemDate.substring(0, 4));
		int month = Integer.parseInt(SystemDate.substring(4, 6)) + 1;
		int day = Integer.parseInt(SystemDate.substring(6));
		int yyyy; // ��
		int mm; // ��
		int dd; // ��
		// String birthday; // ����
		// String sex; // �Ա�
		int id_length = idCard.length();
		if (id_length == 0) {
			mLogger.info("���������֤����!");
			return false;
		}
		if (id_length != 15 && id_length != 18) {
			mLogger.info("���֤�ų���ӦΪ15λ��18λ��");
			return false;
		}
		if (id_length == 15) {
			char[] tCode = idCard.toUpperCase().toCharArray();
			int tASC;
			for (int i = 0; i < id_length; i++) {
				// ͨ��ASC������ʶ��
				tASC = (int) tCode[i];
				// ���������ֵĶ��ǷǷ��� ����0-9��ASC���λ��48-57֮��
				if (tASC < 48 || tASC > 57) {
					mLogger.info("15λ���֤���в������ַ���");
					return false;
				}
			}
			yyyy = Integer.parseInt("19" + idCard.substring(6, 8));
			mm = Integer.parseInt(idCard.substring(8, 10));
			dd = Integer.parseInt(idCard.substring(10, 12));
			if (mm > 12 || mm <= 0) {
				mLogger.info("���֤���·ݷǷ���");
				return false;
			}
			if (dd > 31 || dd <= 0) {
				mLogger.info("���֤�����ڷǷ���");
				return false;
			}
			// 4,6,9,11�·����ڲ��ܳ���30
			if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && (dd > 30)) {
				mLogger.info("���֤�����ڷǷ���");
				return false;
			}
			// �ж�2�·�
			if (mm == 2) {
				if (isLeapYear(yyyy)) {
					if (dd > 29) {
						mLogger.info("���֤�����ڷǷ���");
						return false;
					}
				} else {
					if (dd > 28) {
						mLogger.info("���֤�����ڷǷ���");
						return false;
					}
				}
			}
		} else {
			char[] tCode = idCard.toUpperCase().toCharArray();
			int tASC;
			for (int i = 0; i < id_length - 1; i++) {
				// ͨ��ASC������ʶ��
				tASC = (int) tCode[i];
				// ���������ֵĶ��ǷǷ��� ����0-9��ASC���λ��48-57֮��
				if (tASC < 48 || tASC > 57) {
					mLogger.info("���֤����ǰ17λ�в������ַ���");
					return false;
				}
			}
			tASC = (int) tCode[17];
			if (tASC < 48 || tASC > 57) {
				if (!idCard.substring(17).equals("X")
						&& !idCard.substring(17).equals("x")) {
					mLogger.info("���֤У������������飡");
					return false;
				}
			}
			if (idCard.indexOf("X") > 0 && idCard.indexOf("X") != 17
					|| idCard.indexOf("x") > 0 && idCard.indexOf("x") != 17) {
				mLogger.info("���֤��\"X\"����λ�ò���ȷ��");
				return false;
			}
			yyyy = Integer.parseInt(idCard.substring(6, 10));
			if (yyyy > year || yyyy < 1900) {
				mLogger.info("���֤����ȷǷ���");
				return false;
			}
			mm = Integer.parseInt(idCard.substring(10, 12));
			if (mm > 12 || mm <= 0) {
				mLogger.info("���֤���·ݷǷ���");
				return false;
			}
			if (yyyy == year && mm > month) {
				mLogger.info("���֤���·ݷǷ���");
				return false;
			}
			dd = Integer.parseInt(idCard.substring(12, 14));
			if (dd > 31 || dd <= 0) {
				mLogger.info("���֤�����ڷǷ���");
				return false;
			}
			// 4,6,9,11�·����ڲ��ܳ���30
			if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && (dd > 30)) {
				mLogger.info("���֤�����ڷǷ���");
				return false;
			}
			// �ж�2�·�
			if (mm == 2) {
				if (isLeapYear(yyyy)) {
					if (dd > 29) {
						mLogger.info("���֤�����ڷǷ���");
						return false;

					}
				} else

				{
					if (dd > 28) {
						mLogger.info("���֤�����ڷǷ���");
						return false;
					}
				}
			}
			if (yyyy == year && mm == month && dd > day) {
				mLogger.info("���֤�����ڷǷ���");
				return false;
			}
			if (idCard.substring(17).equals("x")
					|| idCard.substring(17).equals("X")) {
				if (!GetVerifyBit(idCard).equals("x")
						&& !GetVerifyBit(idCard).equals("X")) {
					mLogger.info("���֤У������������飡");
					return false;
				}
			} else {
				if (!idCard.substring(17).equals(GetVerifyBit(idCard))) {
					mLogger.info("���֤У������������飡");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * �ж��Ƿ�Ϊ���� XinYQ added on 2006-09-25
	 */
	public static boolean isLeapYear(int nYear) {
		boolean ResultLeap = false;
		ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
		return ResultLeap;
	}

	/**
	 * �������֤У���� �Ŷ� 20071019 ����common.js �е�checkIdCard ԭ��: ��(a[i]*W[i]) mod 11 (
	 * i = 2, 3, ..., 18 )(1) "*" ��ʾ�˺� i--------��ʾ���֤����ÿһλ����ţ��������������Ϊ18�����Ҳ�Ϊ1��
	 * a[i]-----��ʾ���֤����� i λ�ϵĺ��� W[i]-----��ʾ�� i λ�ϵ�Ȩֵ W[i] = 2^(i-1) mod 11 ���㹫ʽ
	 * (1) ����Ϊ R �����±��ҳ� R ��Ӧ��У���뼴ΪҪ�����֤�����У����C�� R 0 1 2 3 4 5 6 7 8 9 10 C 1 0
	 * X 9 8 7 6 5 4 3 2 X ���� 10�����������е� 10 ���� X 15λת18λ��,����У��λ�����һλ
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
	 * ������ˮ�ŵĺ���
	 * <p>
	 * 
	 * @param cNoType
	 *            Ϊ��Ҫ���ɺ�������ͣ�����addressno
	 * @param cNoLimit
	 *            Ϊ��Ҫ���ɺ������������,������addressno��ֵ
	 * @return ���ɵķ�����������ˮ�ţ��������ʧ�ܣ����ؿ��ַ���""�������Ϳ��Է���������ˮ��
	 */
	public static String CreateMaxNo(String cNoType, String cNoLimit) {
		// ����Ĳ�������Ϊ�գ����Ϊ�գ���ֱ�ӷ���
		if ((cNoType == null) || (cNoType.trim().length() <= 0)
				|| (cNoLimit == null)) {
			System.out.println("NoType���ȴ������NoLimitΪ��");

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
			// ��ʼ����
			// ��ѯ�����3���� -- added by Fanym
			// ȫ������ֱ��ִ��SQL��䣬ֻҪ���������������˱��У���������NULL
			// ���û����������������������ѯ�õ������UPDATE��û����INSERT
			conn.setAutoCommit(false);
			StringBuffer tSBql = new StringBuffer(256);
			tSBql.append("select MaxNo from LDMaxNo where notype='");
			tSBql.append(cNoType);
			tSBql.append("' and nolimit='");
			tSBql.append(cNoLimit);
			tSBql.append("' for update");
			// ������ݿ�������ORACLE�Ļ�����Ҫ���nowait���ԣ��Է�ֹ���ȴ�
			if (SysConst.DBTYPE.compareTo("ORACLE") == 0) {
				// ȥ��nowait������
				// tSBql.append(" nowait");
			}

			ExeSQL exeSQL = new ExeSQL(conn);
			String result = null;
			result = exeSQL.getOneValue(tSBql.toString());

			// ���Է���bullʱ
			if (exeSQL.mErrors.needDealError()) {
				System.out.println("��ѯLDMaxNo�������Ժ�!");
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
					System.out.println("CreateMaxNo ����ʧ�ܣ�������!");
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
					System.out.println("CreateMaxNo ����ʧ�ܣ�������!");
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
	 * һ�����������ֽ�,һ�д�ӡbytes���ֽ�,Ϊ�˷�ֹ��������,��Ҫ��bytes*n��λ�����Ͽո�.
	 * 
	 * @param tStr
	 *            ��Ҫ��ӡ���ַ�
	 * @param bytes
	 *            һ�д�ӡ���ٸ��ֽ�
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
					System.out.println(i + "[" + tmp + "]" + "Ϊ����");
				}
			}
		}
		return tStr;
	}

	/**
	 * һ�����������ֽ�,һ�д�ӡbytes���ֽ�,Ϊ�˷�ֹ��������,��Ҫ��bytes*n��λ�����ϻ��з�.
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

		// �ӿո��ֹ���ֶ�һ�����Ľ����и�
		for (int i = 0; i < tStr.length(); i++) {
			String tmp = tStr.substring(i, i + 1);
			int j = tStr.substring(0, i).getBytes().length;
			// ���ִ�������Ųһ�ո�,�ո���|��д.
			if (tmp.matches("[\u4e00-\u9fa5]+")) {
				if ((j + 1) % bytes == 0) {
					System.out.println(tStr.getBytes().length);
					String ta = tStr.substring(0, i);
					String tb = tStr.substring(i);
					tStr = ta + " " + tb;
					System.out.println(tStr.getBytes().length);
					System.out.println(i + "[" + tmp + "]" + "Ϊ����");
				}

			}
		}

		Vector<String> tVector = new Vector<String>();
		System.out.println("�ӿո��:[" + tStr + "]");

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

				tStr = tStr + tStra;// ���һ������Ҫ��|
			else
				tStr = tStr + tStra + temp;
		}

		return tStr;
	}

	/**
	 * ���� [ab ] [abc ] �󲹿ո�
	 * 
	 * @param tStr
	 *            �ַ���
	 * @param len
	 *            �̶�����
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
	 * ���� [ab��] [abc��] �󲹿ո񡡲�����ȫ�ǿո�-for ABC ��ӡ
	 * 
	 * @param tStr
	 *            �ַ���
	 * @param len
	 *            �̶�����
	 * @return
	 */
	public static String getForMatChineseStr(String tStr, int len) {
		if (tStr == null || tStr.equals(""))
			return "";
		if (tStr.getBytes().length >= len)
			return tStr;
		int tStrLen = tStr.length();
		for (int i = 0; i < len - tStrLen; i++)
			tStr = tStr + "��";
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
			result = Year + "��" + Month + "��" + Day + "��";
		} else if (type.equals("yyyy-MM-dd")) {
			result = YBTFun.getYear(strDate) + "��" + YBTFun.getMonth(strDate)
					+ "��" + YBTFun.getDay(strDate) + "��";
		}
		return result;
	}

	public static String getForMatLCBnfLot(String str) {
		String result = str;
		if (str.equals("1"))
			result = "��һ˳��";
		else if (str.equals("2"))
			result = "��˳��";
		else if (str.equals("3"))
			result = "����˳��";
		else if (str.equals("4"))
			result = "����˳��";
		return result;
	}

	/**
	 * �����������
	 * 
	 * @param br
	 *            ��ʼ��
	 * @param ccd
	 *            ������
	 * @return
	 */
	public static String calInterval(String br, String ccd) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(br);
		Date endDate = fDate.getDate(ccd);
		return String.valueOf(PubFun.calInterval(startDate, endDate, "Y"));
	}

	/**
	 * ���ֽڽ�ȡ
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
	 * ȡ����������
	 * 
	 * @param tDate
	 *            ����
	 * @param tStr
	 *            ʱ���ʽ
	 * @param i
	 *            ������ǰ ��������
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
	 * ���շָ�õ�����
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
	 * ��HH:mm:SS��ʽת����HHMMSS��ʽ
	 * 
	 * @param time
	 *            ��ʽ HH:mm:SS HH24Сʱ��
	 * @return
	 */
	public static String tranTimeFormat(String time) {
		return time.replaceAll(":", "");
	}

	/**
	 * ��HHMMSS��ʽת����HH:mm:SS��ʽ
	 * 
	 * @param time
	 *            ��ʽ HHMMSS HH24Сʱ��
	 * @return HH:mm:SS
	 */
	public static String tranTimeFormat2(String time) {
		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
				+ time.substring(4, 6);
	}

	/**
	 * ����ֵת���ɴ���λС�����
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
	 * ��ȡֹ���ֽ������ַ��� ��������
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
				// �������ַ���һ������
				i = i + 2;
				if (i > num) {
					// ���num���ý�ȡ��������ֵ�ʱ�������˴�forѭ����
					// ���num���ý�ȡ��һ�������ĺ��ֵ�ʱ�򣬼���ִ�������index++����䡣
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
	 * ��Կ�����㷨 ������Կ
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
	 * ��ȡ�������еĿͻ�������Ϣ
	 * 
	 * @param codetype
	 * @param bank_code
	 *            �ͻ��������
	 * @param bankid
	 * @param colum
	 *            codename ȥ�ͻ����� managerqual �������֤
	 * @return
	 */
	public static String getManagerName(String codetype, String bank_code,
			String bankid, String colum) {
		// ���û���򷵻�""
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
	 * �����ĸ�ʽ�����ڸĳ����ָ�ʽ
	 * 
	 * @param date
	 *            ��2013��10��10��
	 * @param dateType
	 *            ����Ϊyyyy-MM-dd ���� yyyyMMdd
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
	 * Ԫto��ת��
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
	 * ������,���ڵ���
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Thread.sleep(500);
		// String a =
		// "���շѵİٷ�֮��ʮ���ڹ���\"�������ʢ��������ȫ����\"��\"�����������ʢ�����������������(A��)\"��\"�����������ʢ�����������������(B��)\"�����շѵİٷ�֮��ʮ���ڹ���\"�����������ʢ������Ͷ�����ᱣ��\"";
		// // a = "a��д";
		//
		// a = dataformat("ABC��", 5, "|");
		// System.out.println("�ӿո��:[" + a);
		// String source = "123     111111                 ";
		// System.out.println(subByte(source,7,10));

//		System.out.println(getRela_bank_Code("04", "0", "1","02"));
		System.out.println(YBTFun.getBackDes("1200","province","07"));
		
		System.out.println(NumberFormat("2.555444"));
	}

}
