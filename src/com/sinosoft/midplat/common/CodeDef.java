package com.sinosoft.midplat.common;

public interface CodeDef {
	/** 交易挂起，未返回 */
	int RCode_NULL = -1;
	/** 交易成功返回 */
	int RCode_OK = 0;
	/** 交易失败返回 */
	int RCode_ERROR = 1;
	/** 转人工核保 */
	int RCode_RenHe = 2;
	
	String SYS = "sys";
}
