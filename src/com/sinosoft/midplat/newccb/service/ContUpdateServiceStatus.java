package com.sinosoft.midplat.newccb.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.service.ServiceImpl;

public class ContUpdateServiceStatus extends ServiceImpl {

	public ContUpdateServiceStatus(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		cLogger.info("Into ContUpdateServiceStatus.service()...");
		
		return pInXmlDoc;
	}
	
}
