package com.sinosoft.midplat.newabc.util;


import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.utility.ExeSQL;

public class AbcUtil implements XmlTag {

	private final static Logger cLogger = Logger.getLogger(AbcUtil.class);
	
	/**
	 * 根据pFlag和pMessage，生成简单的标准返回报文。
	 */
	public static Document getSimpOutXml(int pFlag, String pMessage,String pTranNo) {
		Element mFlag = new Element(Flag);
		mFlag.addContent(String.valueOf(pFlag));

		Element mDesc = new Element(Desc);
		mDesc.addContent(pMessage);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.addContent(pTranNo);

		Element mHead = new Element("Header");
		mHead.addContent(mFlag);
		mHead.addContent(mDesc);
		mHead.addContent(mTranNo);

		Element mTranData = new Element(TranData);
		mTranData.addContent(mHead);

		return new Document(mTranData);
	}
	
	public static String getTransCode(String pCodeType,String pIDType){
		
		String tSql = "SELECT INSU_CODE FROM BANKANDINSUMAP WHERE TRANCOM='04' and CODETYPE='"+pCodeType+"' AND TRAN_CODE='"+pIDType+"'";
		String tCode = new ExeSQL().getOneValue(tSql);
		if(tCode == null){
			tCode = "";
		}
		return tCode;
	}
	
	public static void main(String args[]){
		System.out.println(AbcUtil.getTransCode("idtype", "110004"));
	}
}