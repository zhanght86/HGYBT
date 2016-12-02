package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NoRealBlc extends XmlSimpFormat {
	public NoRealBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NoRealBlc.noStd2Std()...");
		
		JdomUtil.print(pNoStdXml);
		
		Document mStdXml = 
			NoRealInXsl.newInstance().getCache().transform(pNoStdXml);

	        cLogger.info("Out NoRealBlc.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NoRealBlc.std2NoStd()...");
		
		Document mNoStdXml =   
			NoRealOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out NoRealBlc.std2NoStd()!");
		return mNoStdXml;
	}
}