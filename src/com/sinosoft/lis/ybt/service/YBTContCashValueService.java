package com.sinosoft.lis.ybt.service;
/**
 * 
 * @author 张岩岩
 * @version 1.0
 */
public interface YBTContCashValueService {
	/**
	 * 银保现金价值查询
	 * @param xmlStr 报文字符串
	 * @return 返回报文字符串
	 */
	public String getYBTContCashValueService(String xmlStr);
}
