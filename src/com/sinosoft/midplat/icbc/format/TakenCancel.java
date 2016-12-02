package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class TakenCancel extends XmlSimpFormat{

	public TakenCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into TakenCancel.noStd2Std()...");
		
		Document mStdXml = 
			TakenCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out TakenCancel.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into TakenCancel.std2NoStd()...");
		
		Document mNoStdXml =   
			TakenCancelOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out TakenCancel.std2NoStd()!");
		return mNoStdXml;
	}
}
