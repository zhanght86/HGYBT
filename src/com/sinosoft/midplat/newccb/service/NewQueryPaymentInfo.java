package com.sinosoft.midplat.newccb.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.service.ServiceImpl;

public class NewQueryPaymentInfo extends ServiceImpl {

	public NewQueryPaymentInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		
		cLogger.info("Into NewQueryPaymentInfo.service()...");
		return pInXmlDoc;
	}

}
