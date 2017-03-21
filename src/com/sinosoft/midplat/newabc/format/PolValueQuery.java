package com.sinosoft.midplat.newabc.format;


import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
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
		Element mRetCode=new Element("RetCode");
		Element mRetMsg=new Element("RetMsg");
		if(tFlag.getText().equals("0")){
			cLogger.info("交易成功====");
			mRetCode.setText("000000");
			dealPrint(mNoStdXml.getRootElement().getChild("App").getChild("Ret"));
		}else if(tFlag.getText().equals("1")){
			cLogger.info("交易失败====错误信息:"+tDesc.getText());
			mRetCode.setText("009999");
		}
		mRetMsg.setText(tDesc.getText());
		
		header.addContent(mRetCode).addContent(mRetMsg);
		mNoStdXml.getRootElement().addContent(header);
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out PolValueQuery.std2NoStd()!");
		return mNoStdXml;
	}

	private void dealPrint(Element mPrnts) {
		@SuppressWarnings("unchecked")
		List<Element> mPrntList=mPrnts.getChildren("Prnt");
		mPrnts.getChild("PrntCount").setText(String.valueOf(mPrntList.size()));
		System.out.println(mPrnts.getChildText("PrntCount"));
		int i=1;
		for (Element e : mPrntList) {
			e.setName("Prnt"+i);
			i++;
		}
	}
	
}
