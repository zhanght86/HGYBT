package com.sinosoft.midplat.common;

public class DateUtilZR {
	public static String date8to11(String pDate) {
		if (null==pDate || "".equals(pDate)) {
			return pDate;
		}
		
		char[] mChars = pDate.toCharArray();
		
		return new StringBuilder()
			.append(mChars, 0, 4).append('Äê')
			.append(mChars, 4, 2).append('ÔÂ')
			.append(mChars, 6, 2).append('ÈÕ').toString();
	}
}
