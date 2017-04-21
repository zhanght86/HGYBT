package com.sinosoft.midplat.spdb.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat {

	String tranNo;
	
	public RePrint(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		Document mStdXml = RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		/* 记录请求交易流水号，返回报文时用。 */
		tranNo = pNoStdXml.getRootElement().getChild("BUSI").getChildTextTrim("TRANS");
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		Document mNoStdXml =PrintContOutXsl.newInstance().getCache().transform(pStdXml);
		// 返回给浦发的报文增加流水号
		mNoStdXml.getRootElement().getChild("BUSI").getChild("TRANS").setText(tranNo);
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}
	
}
