package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class Query extends XmlSimpFormat {
	public Query(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Query.noStd2Std()...");
		
		Document mStdXml = 
			QueryInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out Query.noStd2Std()!");
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Query.std2NoStd()...");
		
		//查询和新单返回报文基本完全一样，所以直接调用
		//Document mNoStdXml = new NewCont(cThisBusiConf).std2NoStd(pStdXml);
		Document mNoStdXml =   
			QueryOutXsl.newInstance().getCache().transform(pStdXml);
		    
		Element mTranDataEle = pStdXml.getRootElement();
		String mFlagStr = mTranDataEle.getChild(Head).getChildText(Flag);
		/*if (CodeDef.RCode_OK == Integer.parseInt(mFlagStr)) {
			String tPath = "/TXLife/TXLifeResponse/OLifE/Holding/Policy/PaymentAmt";
			Element tPaymentAmt = (Element) XPath.selectSingleNode(mNoStdXml, tPath);
			tPaymentAmt.setText(
					NumberUtil.fenToYuan(tPaymentAmt.getText()));
		}*/
		
		cLogger.info("Out Query.std2NoStd()!");
		return mNoStdXml;
	}
}