package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RenewalPaymentQuery extends XmlSimpFormat {

	private Element header=null;
	private String riskCode=null;
	private String policyNo=null;
	
	public RenewalPaymentQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdxml) throws Exception{
		cLogger.info("Into RenewalPaymentQuery.noStd2Std()...");
		//复制银行请求报文信息
		header=pNoStdxml.getRootElement().getChild("Header");
		riskCode=pNoStdxml.getRootElement().getChild("App").getChild("Req").getChildText("RiskCode");
		policyNo=pNoStdxml.getRootElement().getChild("App").getChild("Req").getChildText("PolicyNo");
		Document mStdXml=
			RenewalPaymentQueryInXsl.newInstance().getCache().transform(pNoStdxml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RenewalPaymentQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception{
		cLogger.info("Into RenewalPaymentQuery.std2NoStd()...");
		Document mNoStdXml=
			RenewalPaymentQueryOutXsl.newInstance().getCache().transform(pStdXml);
		//返回银行报文头
		mNoStdXml.getRootElement().getChild("Header").getChild("SerialNo").setText(header.getChildText("SerialNo"));
		mNoStdXml.getRootElement().getChild("Header").getChild("InsuSerial").setText(header.getChildText("InsuSerial"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransDate").setText(header.getChildText("TransDate"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransTime").setText(header.getChildText("TransTime"));
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		mNoStdXml.getRootElement().getChild("Header").getChild("CorpNo").setText(header.getChildText("CorpNo"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("RiskCode").setText(riskCode);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("PolicyNo").setText(policyNo);
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out RenewalPaymentQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
}
