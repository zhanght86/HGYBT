package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RenewPaymentCancel extends XmlSimpFormat{

	public RenewPaymentCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RenewPaymentCancel.noStd2Std()...");
		
		Document mStdXml = 
			RenewPaymentCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RenewPaymentCancel.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RenewPaymentCancel.std2NoStd()...");
		Document mNoStdXml =   
			RenewPaymentCancelOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out RenewPaymentCancel.std2NoStd()!");
		return mNoStdXml;
	}
}
