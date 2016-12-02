package com.sinosoft.midplat.abc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.abc.format.NewContInXsl;
import com.sinosoft.midplat.abc.format.NewContOutXsl;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NewCont extends XmlSimpFormat {
	
	public NewCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
 
		Document mStdXml = 
			NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		//处理投保人和被保人地址的问题
		mStdXml  = dealAddress(mStdXml);
		
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");

		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);

		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
	
	private Document dealAddress(Document mStdXml){
		Element AppntEle = mStdXml.getRootElement().getChild(Body).getChild(Appnt);
		Element appAddressEle = AppntEle.getChild(Address);
		
		StringBuffer appSb = new StringBuffer();
		String appProv = AppntEle.getChildText("Prov");
		String appCity = AppntEle.getChildText("City");
		String appZone = AppntEle.getChildText("Zone");
		
		if(!"".equals(appProv)){
			appSb = appSb.append(appProv).append("省(直辖市)");
		}
		if(!"".equals(appCity)){
			appSb = appSb.append(appCity).append("市");
		}
		if(!"".equals(appZone)){
			appSb = appSb.append(appZone).append("区(县)");
		}
		
		appAddressEle.setText(appSb.append(appAddressEle.getText()).toString());
	    
		Element InsuredEle = mStdXml.getRootElement().getChild(Body).getChild(Insured);
		Element insuAddressEle = InsuredEle.getChild(Address);	
		
		StringBuffer insuSb = new StringBuffer();
		String insuProv = InsuredEle.getChildText("Prov");
		String insuCity = InsuredEle.getChildText("City");
		String insuZone = InsuredEle.getChildText("Zone");
		
		if(!"".equals(insuProv)){
			insuSb = insuSb.append(insuProv).append("省(直辖市)");
		}
		if(!"".equals(insuCity)){
			insuSb = insuSb.append(insuCity).append("市");
		}
		if(!"".equals(insuZone)){
			insuSb = insuSb.append(insuZone).append("区(县)");
		}
		
		insuAddressEle.setText(insuSb.append(insuAddressEle.getText()).toString());
		
		return mStdXml;
	}
}