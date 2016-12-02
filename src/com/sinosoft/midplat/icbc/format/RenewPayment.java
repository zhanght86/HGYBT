package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RenewPayment extends XmlSimpFormat{

	public RenewPayment(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RenewPayment.noStd2Std()...");
		
		Document mStdXml = 
			RenewPaymentInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RenewPayment.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RenewPayment.std2NoStd()...");
		Document mNoStdXml =   
			RenewPaymentOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out RenewPayment.std2NoStd()!");
		return mNoStdXml;
	}

}
