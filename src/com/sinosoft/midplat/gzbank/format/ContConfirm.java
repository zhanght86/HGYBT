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
		cLogger.info("第三方请求报文:"+JdomUtil.toStringFmt(pInNoStd));
//		int BeneficiaryCount=Integer.parseInt(pInNoStd.getRootElement().getChildText("BeneficiaryCount"));
//		for (int i = 1; i <BeneficiaryCount+1; i++) {
//			pInNoStd.getRootElement().getChild("Beneficiary"+i).setName("Bnf");
//		}
//		int CoverageCount=Integer.parseInt(pInNoStd.getRootElement().getChildText("CoverageCount"));
//		for (int i = 1; i <CoverageCount+1; i++) {
//			pInNoStd.getRootElement().getChild("Extension"+i).setName("Extension");
//		}
		Document mStdXml=ContConfirmInXsl.newInstance().getCache().transform(pInNoStd);
//		mStdXml.getRootElement().getChild("Head").getChild("TranCom").setText(
//				cThisBusiConf.getParentElement().getChildText("TranCom"));
		cLogger.info("请求核心报文："+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out PolicyContTrial.noStd2Std()!");
		return mStdXml;
	}

	@Override
	public Document std2NoStd(Document pOutStd) throws Exception {
		cLogger.info("Into PolicyContTrial.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pOutStd));
        Document mNoStdXml =ContConfirmOutXsl.newInstance().getCache().transform(pOutStd);
		//cLogger.info("返回给第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out PolicyContTrial.std2NoStd()!");
		return mNoStdXml;
	}

}
