package com.sinosoft.midplat.gzbank.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class PolicyWriteOff extends XmlSimpFormat {
	public PolicyWriteOff(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	//复制银行请求报文中的节点
	Element mOldTransRefGUID = null;
	Element mOldTransCpicID = null;
	Element mOldTransNo = null;
	Element mOldTransExeDate = null;
	Element mOldTransExeTime = null;
	public Document noStd2Std(Document pInNoStd) throws Exception {
		cLogger.info("Into PolicyWriteOff.noStd2Std()...");
		//复制银行请求报文中的节点
		 mOldTransRefGUID = (Element) pInNoStd.getRootElement().getChild("OldTransRefGUID").clone();
		 mOldTransCpicID = (Element) pInNoStd.getRootElement().getChild("OldTransCpicID").clone();
		 mOldTransNo = (Element) pInNoStd.getRootElement().getChild("OldTransNo").clone();
		 mOldTransExeDate = (Element) pInNoStd.getRootElement().getChild("OldTransExeDate").clone();
		 mOldTransExeTime = (Element) pInNoStd.getRootElement().getChild("OldTransExeTime").clone();
		
		Document mStdXml = PolicyWriteOffInXsl.newInstance().getCache()
				.transform(pInNoStd);
		JdomUtil.print(mStdXml);
		cLogger.info("Out PolicyWriteOff.noStd2Std()!");
		return mStdXml;
	}
	public Document std2NoStd(Document pOutStd) throws Exception {
		cLogger.info("Into PolicyWriteOff.std2NoStd()...");
		Document mNoStdXml = PolicyWriteOffOutXsl.newInstance().getCache()
				.transform(pOutStd);
		//把请求报文中的节点返回给银行
		mNoStdXml.getRootElement().addContent(mOldTransRefGUID).addContent(mOldTransCpicID).addContent(mOldTransNo)
		.addContent(mOldTransExeDate).addContent(mOldTransExeTime);
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out PolicyWriteOff.std2NoStd()!");
		return mNoStdXml;
	}

}
