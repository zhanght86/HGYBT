package com.sinosoft.midplat.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;

/**
 * ����У�麯������
 * 
 * @author star
 * 
 */
public class CheckDataUtil {

	private static Logger mLogger = Logger.getLogger(YBTFun.class);

	// У�����֤��ʱ����Ҫ�������� zhangd 20071019
	public CErrors mErrors = new CErrors();

	public static final String VISADATEDELIMITER = "-";

	public CheckDataUtil() {
	}

	/**
	 * ���֤У��
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
	 * �������֤У���� ��(a[i]*W[i]) mod 11 ( i = 2, 3, ..., 18 )(1) "*" ��ʾ�˺�
	 * i--------��ʾ���֤����ÿһλ����ţ��������������Ϊ18�����Ҳ�Ϊ1�� a[i]-----��ʾ���֤����� i λ�ϵĺ���
	 * W[i]-----��ʾ�� i λ�ϵ�Ȩֵ W[i] = 2^(i-1) mod 11 ���㹫ʽ (1) ����Ϊ R �����±��ҳ� R
	 * ��Ӧ��У���뼴ΪҪ�����֤�����У����C�� R 0 1 2 3 4 5 6 7 8 9 10 C 1 0 X 9 8 7 6 5 4 3 2 X
	 * ���� 10�����������е� 10 ���� X 15λת18λ��,����У��λ�����һλ
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
	 * �ж��Ƿ�Ϊ����
	 * 
	 */
	public static boolean isLeapYear(int nYear) {
		boolean ResultLeap = false;
		ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
		return ResultLeap;
	}

	/**
	 * �������������ַ�
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
	 * �Ƿ��ǺϷ��ֻ���
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
	 * ֤������У��
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
	 * Ͷ����������֤����Ч��
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
		String name = "��������2��";
		String idtype = "20150429����";
		System.out.println(checkAddress(idtype));
		System.out.println(checkIdNoLis("4","120225199010131773"));
		System.out.println(checkNumber("278163111782681s", "16"));
	}
}
