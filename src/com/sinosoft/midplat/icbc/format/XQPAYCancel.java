/*
 * 犹豫期退保冲正交易
 */
package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class XQPAYCancel extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public XQPAYCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into XQPAY.noStd2Std()...");
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			XQPAYCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out XQPAYCancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into XQPAYCancel.std2NoStd()...");
		
		Document mNoStdXml = 
			XQPAYCancelOutXsl.newInstance().getCache().transform(pStdXml);
		
		//组织返回给银行的报文
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransNo = new Element("TransNo");
		Element mTransMode = new Element("TransMode");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		mTransMode.setText(cTXLifeRequest.getChildText("TransMode"));
		mTransNo.setText(cTXLifeRequest.getChildText("TransNo"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mTXLifeResponse.addContent(mTransMode);
		mTXLifeResponse.addContent(mTransNo);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		
		cLogger.info("Out XQPAYCancel.std2NoStd()!");
		return mNoStdXml;
	}
}