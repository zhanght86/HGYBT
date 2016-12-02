package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ManPaymentCancel extends XmlSimpFormat{
	public ManPaymentCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ManPaymentCancel.noStd2Std()...");
		
		Document mStdXml = 
			ManPaymentCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out ManPaymentCancel.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ManPaymentCancel.std2NoStd()...");
		
		Document mNoStdXml =   
			ManPaymentCancelOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out ManPaymentCancel.std2NoStd()!");
		return mNoStdXml;
	}

}
