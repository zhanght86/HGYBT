package com.sinosoft.midplat.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyUtil {

	public static String fenToYuan(String pFen) {
		if (null==pFen || "".equals(pFen)) {
			return "";
		}
		
		return new DecimalFormat("0").format(
				Long.parseLong(pFen)/100.0D);
	}
	
	
	public static String yuanToQian(String pFen) {
		if (null==pFen || "".equals(pFen)) {
			return "0.00";
		}
		String yuan = NumberUtil.fenToYuan(pFen);
		NumberFormat numfmt = NumberFormat.getInstance();
		double num = Double.valueOf(yuan);
		String result=numfmt.format(num);
	
		int z = 0;

		for(int i=0;i<result.length();i++)
		{
		
			if(".".equals(String.valueOf(result.charAt(i)))){
				z=result.length() - i -1;
			}
		}
		//System.out.println(z);
		if(z == 0){
			result +=".00";
		}else if(z == 1){
			result +="0";
		}
		return result;
		}
	public static void main(String[] args) {
		System.out.println(yuanToQian(""));
	}
}
