package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat{

	public RePrint(Element pThisBusiConf) {
		super(pThisBusiConf);
		// TODO Auto-generated constructor stub
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		  
//		FormatXsl cFormatXsl = new FormatXsl ("RePrintIn.xsl");		
//		Document mStdXml = cFormatXsl.getCache().transform(pNoStdXml);
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		
		//�ش���µ�ȷ�Ϸ��ر��Ļ�����ȫһ��������ֱ�ӵ���		
		Document mNoStdXml = new NewCont(cThisBusiConf).std2NoStd(pStdXml);		
		Element tTransType  = (Element) XPath.selectSingleNode(mNoStdXml.getRootElement(), "/TXLife/TXLifeResponse/TransType");
		tTransType.setText("1011");
//        JdomUtil.print(mNoStdXml);
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}

}
