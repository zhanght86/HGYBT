package com.sinosoft.midplat.common;

public interface ICCodeDef extends CodeDef {
	/*IC卡开卡*/
	String ICOpen = "7010";
	
	/*IC卡初始化*/
	String ICInit = "7000";  
	
	/*IC卡激活*/
	String ICActive = "7001";
	
	/*IC卡状态变更*/
	String ICStateChange = "7013";
	
	/*IC卡换卡*/
	String 	ICChange = "7002";
	
	/*当日反交易*/
	String ICClosed = "7019";
	 	 
	/*IC卡状态变更文件(对账)*/ 	
	String ICBlcStateChange = "7101";
	
	/*换卡文件(对账)*/
	String ICBlcChange = "7102";	
}
