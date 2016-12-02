package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil; 
import com.sinosoft.midplat.format.XmlSimpFormat;
 
public class Trail extends XmlSimpFormat {
	public Trail(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	private static String  type  = ""; 
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Trail.noStd2Std()...");
		
		Document mStdXml = 
			NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		type = pNoStdXml.getRootElement().getChild("TXLifeRequest").getChild("OLifEExtension").getChildText("SourceType");
		//工行网银不传投保单号，与核心约定默认写成1003090000000000
		if(type.equals("1"))
		{
			Element tproposalprtno = mStdXml.getRootElement().getChild("Body").getChild(ProposalPrtNo);
			tproposalprtno.setText("1003990000000000");
		}
		cLogger.info("Out Trail.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Trail.std2NoStd()...");
		  
		Document mNoStdXml =  
			TrailOutXsl.newInstance().getCache().transform(pStdXml);
		cLogger.info("Out Trail.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		
		String mInFilePath = "D:/instd.xml";
		String mOutFilePath = "D:/trailOut.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
		Document mOutXmlDoc = new Trail(null).std2NoStd(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	}
}