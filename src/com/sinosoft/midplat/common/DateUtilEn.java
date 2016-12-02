package com.sinosoft.midplat.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilEn {

	public static Integer enDateTo8Date(String enDate){
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"dd/MM/yy", java.util.Locale.US);
		java.util.Date d = new java.util.Date();
		try{
			d = sdf.parse(enDate);
		}catch(Exception e){			
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String mDateTime = formatter.format(d);
		return Integer.parseInt(mDateTime);
	}
	public static String dateChange(String inDate){
		//26/七月/11
		String[] str = new String[3];
		str = inDate.split("/");
		if(str[1].equals("一月")){
			str[1]="01";
		}
		if(str[1].equals("二月")){
			str[1]="02";
		}
		if(str[1].equals("三月")){
			str[1]="03";
		}
		if(str[1].equals("四月")){
			str[1]="04";
		}
		if(str[1].equals("五月")){
			str[1]="05";
		}
		if(str[1].equals("六月")){
			str[1]="06";
		}
		if(str[1].equals("七月")){
			str[1]="07";
		}
		if(str[1].equals("八月")){
			str[1]="08";
		}
		if(str[1].equals("九月")){
			str[1]="09";
		}
		if(str[1].equals("十月")){
			str[1]="10";
		}
		if(str[1].equals("十一月")){
			str[1]="11";
		}
		if(str[1].equals("十二月")){
			str[1]="12";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(str[0]).append("/").append(str[1]).append("/").append(str[2]);
		return sb.toString();
	}
	
	public static Integer dateToTomorrow(Integer date){
		Date startDate   =   DateUtil.parseDate(date.toString(), "yyyyMMdd"); 
		  Calendar cal = Calendar.getInstance();  
		  cal.setTime(startDate);  
		  cal.add(Calendar.DATE, 1); 
		  Date cdate= cal.getTime();
		  return DateUtil.get8Date(cdate);
	}
	public static void main(String[] args) {
//		dd/MMM/yy
//		System.out.println(enDateTo8Date("05/Jul/12"));
//		System.out.println(enDateTo8Date("15-Jul-11"));
////		System.out.println(enDateTo8Date(null));
//		System.out.println(enDateTo8Date(""));
//		System.out.println(enDateTo8Date("yhewuyrhj"));
//		System.out.println(enDateTo8Date("02-03-11"));
//		System.out.println(dateChange("26/十二月/11"));
//		
//		System.out.println(dateChange("w"));
		
		System.out.println(dateToTomorrow(20120324));

	}
}
