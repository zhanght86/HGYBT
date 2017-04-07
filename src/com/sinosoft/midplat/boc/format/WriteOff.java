//中行撤单交易
package com.sinosoft.midplat.boc.format;


import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class WriteOff extends XmlSimpFormat {
	private Element cMain = null;
	public WriteOff(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into WriteOff.noStd2Std()...");
		cMain =(Element) pNoStdXml.getRootElement().getChild("Main").clone();
		Document mStdXml =WriteOffInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("请求核心报文:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out WriteOff.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into WriteOff.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pStdXml));
		Document mNoStdXml = WriteOffOutXsl.newInstance().getCache().transform(pStdXml);
		Element mainEle=mNoStdXml.getRootElement().getChild("Main");
		mainEle.getChild("InsuId").setText(cMain.getChildText("InsuId"));
		mainEle.getChild("ZoneNo").setText(cMain.getChildText("ZoneNo"));
		mainEle.getChild("BrNo").setText(cMain.getChildText("BrNo"));
		mainEle.getChild("TellerNo").setText(cMain.getChildText("TellerNo"));
		mainEle.getChild("TransNo").setText(cMain.getChildText("TransNo"));
		mainEle.getChild("TranCode").setText(cMain.getChildText("TranCode"));
		cLogger.info("返回给第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out WriteOff.std2NoStd()!");
		return mNoStdXml;
	}
}