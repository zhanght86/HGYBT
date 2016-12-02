package com.newsky.xiangwei;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrintLog {
	private static String logFile = null;
	
	
	
	public static String getLogFile()
	{
		return logFile;
	}



	public static void setLogFile(String logFile)
	{
		PrintLog.logFile = logFile;
	}



	public static void printLog(String s)
	{
		Date dat = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("<<yyyy/MM/dd  hh:mm:ss>> ", Locale.US);
		System.out.println(sdf.format(dat)+s);
	}
}
