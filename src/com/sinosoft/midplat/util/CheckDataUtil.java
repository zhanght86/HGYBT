package com.sinosoft.midplat.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;

/**
 * 各种校验函数集合
 * 
 * @author star
 * 
 */
public class CheckDataUtil {

	private static Logger mLogger = Logger.getLogger(YBTFun.class);

	// 校验身份证的时候需要错误处理类 zhangd 20071019
	public CErrors mErrors = new CErrors();

	public static final String VISADATEDELIMITER = "-";

	public CheckDataUtil() {
	}

	/**
	 * 身份证校验
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
	 * 计算身份证校验码 ∑(a[i]*W[i]) mod 11 ( i = 2, 3, ..., 18 )(1) "*" 表示乘号
	 * i--------表示身份证号码每一位的序号，从右至左，最左侧为18，最右侧为1。 a[i]-----表示身份证号码第 i 位上的号码
	 * W[i]-----表示第 i 位上的权值 W[i] = 2^(i-1) mod 11 计算公式 (1) 令结果为 R 根据下表找出 R
	 * 对应的校验码即为要求身份证号码的校验码C。 R 0 1 2 3 4 5 6 7 8 9 10 C 1 0 X 9 8 7 6 5 4 3 2 X
	 * 就是 10，罗马数字中的 10 就是 X 15位转18位中,计算校验位即最后一位
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
	 * 判断是否为闰年
	 * 
	 */
	public static boolean isLeapYear(int nYear) {
		boolean ResultLeap = false;
		ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
		return ResultLeap;
	}

	/**
	 * 姓名大于两个字符
	 */
	public static boolean isName(String nName) {
		boolean flag = false;
		String check = "^\\S{2,}";
		Pattern regex = Pattern.compile(check);
		Matcher macther = regex.matcher(nName);
		flag = macther.matches();
		return flag;
	}

	/**
	 * 是否是合法手机号
	 * 
	 * @param nPhoneNo
	 * @return
	 */
	public static boolean isPhoneNumber(String nPhoneNo) {
		boolean flag = false;
		String check = "^((1[3,4,5,7,8])\\d{9}$)";
		Pattern regex = Pattern.compile(check);
		Matcher macther = regex.matcher(nPhoneNo);
		flag = macther.matches();
		return flag;
	}

	/**
	 * 证件号码校验
	 * 
	 * @param idType
	 * @param IDNo
	 * @return
	 */
	public static boolean checkIdNoLis(String idType, String nIDNo) {
		boolean flag = false;
		String passport = "1";
		String bornCard = "7";
		String taiwanCard = "E";
		String junGuanCard = "2";
		String shibingCard = "D";
		String hukouCard = "4";
		String check = "";
		if (idType.equals(passport) || idType.equals(bornCard)
				|| idType.equals(hukouCard)) {
			check = "^\\S{3,}";
			Pattern regex = Pattern.compile(check);
			Matcher macther = regex.matcher(nIDNo);
			flag = macther.matches();
		} else if (idType.equals(taiwanCard)) {
			check = "^\\S{8,}";
			Pattern regex = Pattern.compile(check);
			Matcher macther = regex.matcher(nIDNo);
			flag = macther.matches();
		} else if (idType.equals(shibingCard) || idType.equals(junGuanCard)) {
			check = "^\\S{10,18}";
			Pattern regex = Pattern.compile(check);
			Matcher macther = regex.matcher(nIDNo);
			flag = macther.matches();
		} else
			flag = true;
		return flag;
	}

	/**
	 * 投保日期早于证件有效期
	 * 
	 * @param mCurDate
	 * @param idValidate
	 * @return
	 */
	public static boolean checkBeforeDate(String mCurDate, String idValidate) {

		try {
			mCurDate = StrTool.cTrim(mCurDate);
			idValidate = StrTool.cTrim(idValidate);
			if (mCurDate.equals("")) {
				return true;
			}
			if (idValidate.equals("")) {
				return false;
			}
			FDate fd = new FDate();
			Date d1 = fd.getDate(mCurDate);
			Date d2 = fd.getDate(idValidate);
			if (d2.before(d1)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean checkAddress(String mAddress) {
		boolean flag = false;
		if (mAddress.length() < 3)
			return flag;
		String checkChinese = "[\u4e00-\u9fa5]";
		String checkdata = "[0-9]";
		String checkdataCHN = "[\uFF10-\uFF19]";
		Pattern regex1 = Pattern.compile(checkChinese);
		Pattern regex2 = Pattern.compile(checkdata);
		Pattern regex3 = Pattern.compile(checkdataCHN);
		Matcher macther1 = regex1.matcher(mAddress);
		Matcher macther2 = regex2.matcher(mAddress);
		Matcher macther3 = regex3.matcher(mAddress);
		flag = (macther1.find() && (macther2.find()||macther3.find()));
		return flag;
	}

	public static boolean checkPhone(String mPhone) {
		boolean flag = false;
		String phone = "[0-9]{11}";
		Pattern regex1 = Pattern.compile(phone);
		Matcher macther1 = regex1.matcher(mPhone);
		flag = macther1.matches();
		return flag;
	}
	
	public static boolean checkNumber(String mNumber , String length) {
		boolean flag = false;
		String mNumberMatch = "[0-9]{"+length+"}";
		Pattern regex1 = Pattern.compile(mNumberMatch);
		Matcher macther1 = regex1.matcher(mNumber);
		flag = macther1.matches();
		return flag;
	}
	
	

	public static void main(String[] args) {
		String name = "３３３３2啊";
		String idtype = "20150429３啊";
		System.out.println(checkAddress(idtype));
		System.out.println(checkIdNoLis("4","120225199010131773"));
		System.out.println(checkNumber("278163111782681s", "16"));
	}
}
