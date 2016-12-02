package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class BaoQuanBack extends XmlSimpFormat {
	public BaoQuanBack(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		return pNoStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into BaoQuanBack.std2NoStd()...");
		
		Document mNoStdXml =   
			BaoQuanBackOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out BaoQuanBack.std2NoStd()!");
		return mNoStdXml;
	}
}