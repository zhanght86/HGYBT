package com.sinosoft.midplat.newabc.service;


import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.service.ServiceImpl;

public class NewAccountChange extends ServiceImpl {

	public NewAccountChange(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		cLogger.info("Into Heartbeat.service()...");
		//成员标准输入报文
		cInXmlDoc = pInXmlDoc;
		
		cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！");
		//获取简单输出报文
		cOutXmlDoc = getSimpOutXml(cInXmlDoc);
		
		JdomUtil.print(cOutXmlDoc);
		
		cLogger.info("Out GreenTest.service()!");
		return cOutXmlDoc;
	}
	
	/**
	 * 根据pFlag和pMessage，生成简单的标准返回报文。
	 * @param pFlag 交易结果
	 * @param pMessage 交易结果描述
	 */
	public static Document getSimpOutXml(Document pInXmlDoc) {
		
		Element mRetCode=new Element("RetCode");
		mRetCode.setText("009999");
		Element mRetMsg=new Element("RetMsg");
		mRetMsg.setText("请到保险公司柜面办理此业务！");
		
		Element mHeader=(Element) pInXmlDoc.getRootElement().getChild("Header").clone();
		mHeader.addContent(mRetCode);
		mHeader.addContent(mRetMsg);
		
		Element mReq=pInXmlDoc.getRootElement().getChild("App").getChild("Req");
		Element mRiskCode = (Element) mReq.getChild(RiskCode).clone();
		Element mPolicyNo = (Element) mReq.getChild("PolicyNo").clone();
		
		Element mRet=new Element("Ret");
		mRet.addContent(mRiskCode);
		mRet.addContent(mPolicyNo);
		
		Element mApp=new Element("App");
		mApp.addContent(mRet);
		
		Element mABCB2I=new Element("ABCB2I");
		mABCB2I.addContent(mHeader);
		mABCB2I.addContent(mApp);
		
		//返回简单标准输出报文
		return new Document(mABCB2I);
	}
	
}
