package com.sinosoft.midplat.common;

import org.apache.log4j.Logger;

public class ControlUtil implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(ControlUtil.class);
	
	public static void sqlExecTime(String sSqlName,long mStartMillis) {
		double mExecTime = (double) ((System.currentTimeMillis()-mStartMillis)/1000.0);
		//cLogger.info(sSqlName+"耗时:"+mExecTime+"秒");
		if(mExecTime>0.5){
			cLogger.info(sSqlName+":执行时间过长");
		}
	}

	public static void main(String[] args) throws InterruptedException {
			long mStartMillis = System.currentTimeMillis();
			Thread.sleep(50);
			ControlUtil.sqlExecTime("ContDB.info()",mStartMillis);
	}
}
