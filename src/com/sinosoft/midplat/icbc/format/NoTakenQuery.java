package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NoTakenQuery extends XmlSimpFormat{
	public NoTakenQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NoTakenQuery.noStd2Std()...");
		
		Document mStdXml = 
			NoTakenQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out NoTakenQuery.noStd2Std()!");
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NoTakenQuery.std2NoStd()...");
		Document mNoStdXml =   
			NoTakenQueryOutXsl.newInstance().getCache().transform(pStdXml);
		 
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out NoTakenQuery.std2NoStd()!");
		return mNoStdXml;
	}
}
