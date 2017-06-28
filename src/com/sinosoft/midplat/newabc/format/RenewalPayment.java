package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RenewalPayment extends XmlSimpFormat {

	private Element header=null;
	private Element req=null;
	
	public RenewalPayment(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception{
		cLogger.info("Into RenewalPayment.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		req=(Element)pNoStdXml.getRootElement().getChild("App").getChild("Req").clone();
		Document mStdXml=RenewalPaymentInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Into RenewalPayment.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception{
		cLogger.info("Into RenewalPayment.std2NoStd()...");
		Element tFlag=(Element)XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element tDesc=(Element)XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Document mNoStdXml=RenewalPaymentOutXsl.newInstance().getCache().transform(pStdXml);
		Element mRetCode=mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element mRetMsg=mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		if(tFlag.getText().equals("0")){
			cLogger.info("交易成功====");
			mRetCode.setText("000000");
		}else if(tFlag.getText().equals("1")){
			cLogger.info("交易失败====");
			mRetCode.setText("009999");
		}
		mRetMsg.setText(tDesc.getText());
		
		mNoStdXml.getRootElement().getChild("Header").getChild("SerialNo").setText(header.getChildText("SerialNo"));
		mNoStdXml.getRootElement().getChild("Header").getChild("InsuSerial").setText(header.getChildText("InsuSerial"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransTime").setText(header.getChildText("TransTime"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransDate").setText(header.getChildText("TransDate"));
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		mNoStdXml.getRootElement().getChild("Header").getChild("CorpNo").setText(header.getChildText("CorpNo"));
		
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("RiskCode").setText(req.getChildText("RiskCode"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("ProdCode").setText(req.getChildText("ProdCode"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("PolicyNo").setText(req.getChildText("PolicyNo"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Appl").getChild("IDKind").setText(req.getChild("Appl").getChildText("IDKind"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Appl").getChild("IDCode").setText(req.getChild("Appl").getChildText("IDCode"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Appl").getChild("Name").setText(req.getChild("Appl").getChildText("Name"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("PayAcc").setText(req.getChildText("PayAcc"));
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("PayAmt").setText(req.getChildText("PayAmt"));
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out RenewalPayment.std2NoStd()!");
		return mNoStdXml;
	}
	
}
