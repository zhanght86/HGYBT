package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ManPaymentQuery extends XmlSimpFormat{

	public ManPaymentQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ManPaymentQuery.noStd2Std()...");
		
		Document mStdXml = 
			ManPaymentQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out ManPaymentQuery.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ManPaymentQuery.std2NoStd()...");
		Document mNoStdXml =   
			ManPaymentQueryOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out ManPaymentQuery.std2NoStd()!");
		return mNoStdXml;
	}
}
