package com.sinosoft.midplat.spdb.format;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NewCont extends XmlSimpFormat {

	String tranNo;
	
	public NewCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
		Document mStdXml = NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		/*记录请求交易流水号，返回报文时用。*/
		tranNo = pNoStdXml.getRootElement().getChild("BUSI").getChildTextTrim("TRANS");
		JdomUtil.print(mStdXml);
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");
		Document mNoStdXml = NewContOutXsl.newInstance().getCache().transform(pStdXml);
		//返回给浦发的报文增加流水号
		mNoStdXml.getRootElement().getChild("BUSI").getChild("TRANS").setText(tranNo);
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception{
	
		System.out.println("程序开始…");
		String mInFilePath = "D:/File/task/20170420/spdb/unit_test/23483_30_0_outSvc.xml";
		String mOutFilePath = "D:/File/task/20170420/spdb/unit_test/23483_33_1001_out.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
		// Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);
		JdomUtil.print(mOutXmlDoc);
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	
	}
	
}
