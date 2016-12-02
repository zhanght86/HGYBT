package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ManPayment extends XmlSimpFormat{

	public ManPayment(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ManPayment.noStd2Std()...");
		
		Document mStdXml = 
			ManPaymentInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out ManPayment.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ManPayment.std2NoStd()...");
		Document mNoStdXml =   
			ManPaymentOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out ManPayment.std2NoStd()!");
		return mNoStdXml;
	}

}
