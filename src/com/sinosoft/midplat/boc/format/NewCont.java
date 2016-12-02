package com.sinosoft.midplat.boc.format;

import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * 保费试算转换
 * @author anico
 *
 */
public class NewCont extends XmlSimpFormat {
	String InsuId="";//保险公司代码
	String ZoneNo="";//地区代码
	String BrNo="";//网点代码
	String TellerNo="";//柜员代码
	String TransNo="";//交易流水号
	String TranCode="";//交易码
	public NewCont(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
		cLogger.info("第三方请求报文:"+JdomUtil.toStringFmt(pNoStdXml));
		Element mainEle=pNoStdXml.getRootElement().getChild("Main");
		InsuId=mainEle.getChildText("InsuId");
		ZoneNo=mainEle.getChildText("ZoneNo");
		BrNo=mainEle.getChildText("BrNo");
		TellerNo=mainEle.getChildText("TellerNo");
		TransNo=mainEle.getChildText("TransNo");
		TranCode=mainEle.getChildText("TranCode");
		Document mStdXml =NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("请求核心报文:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pStdXml));
		Document mNoStdXml = NewContOutXsl.newInstance().getCache().transform(pStdXml);
		Element mainEle=mNoStdXml.getRootElement().getChild("Main");
		mainEle.getChild("InsuId").setText(InsuId);
		mainEle.getChild("ZoneNo").setText(ZoneNo);
		mainEle.getChild("BrNo").setText(BrNo);
		mainEle.getChild("TellerNo").setText(TellerNo);
		mainEle.getChild("TransNo").setText(TransNo);
		mainEle.getChild("TranCode").setText(TranCode);
		cLogger.info("返回给第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
}