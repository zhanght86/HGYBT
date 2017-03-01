package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class AccountChange extends XmlSimpFormat {

	private Element header=null;
	
	public AccountChange(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception{
		cLogger.info("Into AccountChange.noStd2Std()...");
		header=(Element) pNoStdXml.getRootElement().clone();
		Document mStdXml=AccountChangeInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out AccountChange.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception{
		cLogger.info("Into AccountChange.std2NoStd()...");
		Element tFlag=pStdXml.getRootElement().getChild(Flag);
		Element tDesc=pStdXml.getRootElement().getChild(Desc);
		Document mNoStdXml=AccountChangeOutXsl.newInstance().getCache().transform(pStdXml);
		Element mRetCode=new Element("RetCode");
		Element mRetMsg=new Element("RetMsg");
		if(tFlag.getText().equals("0")){
			cLogger.info("交易成功===");
			mRetCode.setText("000000");
		}else if(tFlag.getText().equals("1")){
			cLogger.info("交易失败===失败信息:"+mRetMsg.getText());
			mRetCode.setText("009999");
		}
		mRetMsg.setText(tDesc.getText());
		header.addContent(mRetCode);
		header.addContent(mRetMsg);
		mNoStdXml.getRootElement().addContent(header);
		cLogger.info("Out AccountChange.std2NoStd()!");
		return mNoStdXml;
	}
	
}
