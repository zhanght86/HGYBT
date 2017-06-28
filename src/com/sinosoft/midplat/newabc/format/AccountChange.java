package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class AccountChange extends XmlSimpFormat {
	
	public AccountChange(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception{
		cLogger.info("Into AccountChange.noStd2Std()...");
		Document mStdXml=pNoStdXml;
		cLogger.info("Out AccountChange.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception{
		cLogger.info("Into AccountChange.std2NoStd()...");
		Document mNoStdXml=pStdXml;
		cLogger.info("Out AccountChange.std2NoStd()!");
		return mNoStdXml;
	}
	
}
