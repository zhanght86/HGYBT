package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class PolValueQuery extends XmlSimpFormat {

	@SuppressWarnings("unused")
	private Element header=null;
	
	public PolValueQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception{
		cLogger.info("Into PolValueQuery.noStd2Std()...");
		header=(Element) pNoStdXml.getRootElement().getChild("Header").clone();
		Document mStdXml=PolValueQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out PolValueQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception{
		cLogger.info("Into PolValueQuery.std2NoStd()...");
		Element tFlag=pStdXml.getRootElement().getChild(Head).getChild(Flag);
		Element tDesc=pStdXml.getRootElement().getChild(Head).getChild(Desc);
		Document mNoStdXml=PolValueQueryOutXsl.newInstance().getCache().transform(pStdXml);
		Element mRetCode=mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element mRetMsg=mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		if(tFlag.getText().equals("0")){
			cLogger.info("交易成功====");
			mRetCode.setText("000000");
		}else if(tFlag.getText().equals("1")){
			cLogger.info("交易失败====错误信息:"+tDesc.getText());
			mRetCode.setText("009999");
		}
		mRetMsg.setText(tDesc.getText());
		cLogger.info("Out PolValueQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
}
