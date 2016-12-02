package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.icbc.format.QueryInXsl;
import com.sinosoft.midplat.icbc.format.QueryOutXsl;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ContQuery extends XmlSimpFormat{

	public ContQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
		// TODO Auto-generated constructor stub
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContQuery.noStd2Std()...");
		  
//		FormatXsl cFormatXsl = new FormatXsl ("QueryIn.xsl");		
//		Document mStdXml = cFormatXsl.getCache().transform(pNoStdXml);
		Document mStdXml = 
			QueryInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		cLogger.info("Out ContQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContQuery.std2NoStd()...");
		
		Document mNoStdXml =   
			QueryOutXsl.newInstance().getCache().transform(pStdXml);
	    
		Element mTranDataEle = pStdXml.getRootElement();
		String mFlagStr = mTranDataEle.getChild(Head).getChildText(Flag);

        JdomUtil.print(mNoStdXml);
		cLogger.info("Out ContQuery.std2NoStd()!");
		return mNoStdXml;
	}

}
