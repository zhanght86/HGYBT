package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RenewPaymentQuery extends XmlSimpFormat{

	public RenewPaymentQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RenewPaymentQuery.noStd2Std()...");
		
		Document mStdXml = 
			RenewPaymentQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RenewPaymentQuery.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RenewPaymentQuery.std2NoStd()...");
		Document mNoStdXml =   
			RenewPaymentQueryOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out RenewPaymentQuery.std2NoStd()!");
		return mNoStdXml;
	}

}
