package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class TakenQuery extends XmlSimpFormat{

	public TakenQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into TakenQuery.noStd2Std()...");
		
		Document mStdXml = 
			TakenQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out TakenQuery.noStd2Std()!");
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into TakenQuery.std2NoStd()...");
		Document mNoStdXml =   
			TakenQueryOutXsl.newInstance().getCache().transform(pStdXml);
		 
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out TakenQuery.std2NoStd()!");
		return mNoStdXml;
	}

}
