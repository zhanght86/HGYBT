package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class RenewalPayment extends XmlSimpFormat {

	@SuppressWarnings("unused")
	private Element header=null;
	
	public RenewalPayment(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception{
		cLogger.info("Into RenewalPayment.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
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
		cLogger.info("Out RenewalPayment.std2NoStd()!");
		return mNoStdXml;
	}
	
}
