package com.sinosoft.midplat.gzbank.format;


import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ContConfirm extends XmlSimpFormat{
	public ContConfirm(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}
	@Override
	public Document noStd2Std(Document pInNoStd) throws Exception {
		cLogger.info("Into PolicyContTrial.noStd2Std()...");
		Document mStdXml=ContConfirmInXsl.newInstance().getCache().transform(pInNoStd);
		JdomUtil.print(mStdXml);
		cLogger.info("Out PolicyContTrial.noStd2Std()!");
		return mStdXml;
	}

	@Override
	public Document std2NoStd(Document pOutStd) throws Exception {
		cLogger.info("Into PolicyContTrial.std2NoStd()...");
        Document mNoStdXml =ContConfirmOutXsl.newInstance().getCache().transform(pOutStd);
        JdomUtil.print(mNoStdXml);
		cLogger.info("Out PolicyContTrial.std2NoStd()!");
		return mNoStdXml;
	}

}
