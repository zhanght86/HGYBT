package com.sinosoft.midplat.service;

import org.jdom.Document;
import org.jdom.Element;

public class GreenLightCcb extends ServiceImpl {
	public GreenLightCcb(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		cLogger.info("In GreenLightCcb.service()!");
		Document mOutStdXml = null;
		Element tFlag = new Element("Flag");
		tFlag.setText("0");	//0-³É¹¦ 1-Ê§°Ü
		
		Element tDesc = new Element("Desc");
		tDesc.setText("sucess!"); 

		Element tRetData = new Element("RetData");
		tRetData.addContent(tFlag);
		tRetData.addContent(tDesc);
		
		Element tTranData = new Element("TranData");
		tTranData.addContent(tRetData);

		mOutStdXml = new Document(tTranData);
		
		cOutXmlDoc = mOutStdXml;
		
		cLogger.info("Out GreenLightCcb.service()!");
		return cOutXmlDoc;
	}
}
