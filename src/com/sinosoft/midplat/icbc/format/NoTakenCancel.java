package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NoTakenCancel extends XmlSimpFormat{

	public NoTakenCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NoTakenCancel.noStd2Std()...");
		
		Document mStdXml = 
			NoTakenCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out NoTakenCancel.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NoTakenCancel.std2NoStd()...");
		
		Document mNoStdXml =   
			NoTakenCancelOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out NOTakenCancel.std2NoStd()!");
		return mNoStdXml;
	}

}
