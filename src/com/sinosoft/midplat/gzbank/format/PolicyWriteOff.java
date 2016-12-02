package com.sinosoft.midplat.gzbank.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class PolicyWriteOff extends XmlSimpFormat {
	public PolicyWriteOff(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	Document cInNoStd = null;

	public Document noStd2Std(Document pInNoStd) throws Exception {
		cLogger.info("Into PolicyWriteOff.noStd2Std()...");
		cLogger.info("第三方请求报文:" + JdomUtil.toStringFmt(pInNoStd));

		this.cInNoStd = pInNoStd;
		Document mStdXml = PolicyWriteOffInXsl.newInstance().getCache()
				.transform(pInNoStd);
		cLogger.info("请求核心报文：" + JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out PolicyWriteOff.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pOutStd) throws Exception {
		cLogger.info("Into PolicyWriteOff.std2NoStd()...");
		cLogger.info("核心返回报文:" + JdomUtil.toStringFmt(pOutStd));
		
		String mOldTransRefGUID = cInNoStd.getRootElement().getChildText("OldTransRefGUID");
		String mOldTransCpicID = cInNoStd.getRootElement().getChildText("OldTransCpicID");
		String mOldTransNo = cInNoStd.getRootElement().getChildText("OldTransNo");
		String mOldTransExeDate = cInNoStd.getRootElement().getChildText("OldTransExeDate");
		String mOldTransExeTime = cInNoStd.getRootElement().getChildText("OldTransExeTime");
		
		Document mNoStdXml = PolicyWriteOffOutXsl.newInstance().getCache()
				.transform(pOutStd);
		
		mNoStdXml.getRootElement().getChild("OldTransExeTime").setText(mOldTransExeTime);
		mNoStdXml.getRootElement().getChild("OldTransCpicID").setText(mOldTransCpicID);
		mNoStdXml.getRootElement().getChild("OldTransNo").setText(mOldTransNo);
		mNoStdXml.getRootElement().getChild("OldTransExeDate").setText(mOldTransExeDate);
		mNoStdXml.getRootElement().getChild("OldTransRefGUID").setText(mOldTransRefGUID);
		cLogger.info("返回给第三方报文:" + JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out PolicyWriteOff.std2NoStd()!");
		return mNoStdXml;
	}

}
