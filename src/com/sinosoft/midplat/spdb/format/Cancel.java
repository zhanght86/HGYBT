package com.sinosoft.midplat.spdb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class Cancel extends XmlSimpFormat {

	String tranNo;
	
	public Cancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Cancel.noStd2Std()...");
		/*记录请求交易流水号，返回报文时用。*/
		tranNo = pNoStdXml.getRootElement().getChild("BUSI").getChildTextTrim("TRANS");
		Document mStdXml = CancelInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out Cancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Cancel.std2NoStd()...");
		Document mNoStdXml = CancelOutXsl.newInstance().getCache().transform(pStdXml);
		/*返回给浦发的报文增加流水号*/
		mNoStdXml.getRootElement().getChild("BUSI").getChild("TRANS").setText(tranNo);
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out Cancel.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args)  throws Exception {
	
		System.out.println("程序开始…");
		String mInFilePath = "D:/File/task/20170420/spdb/unit_test/23843_36_5_outSvc.xml";
		String mOutFilePath = "D:/File/task/20170420/spdb/unit_test/23843_39_1003_out.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		Document mOutXmlDoc = new Cancel(null).std2NoStd(mInXmlDoc);
		// Document mOutXmlDoc = new Cancel(null).noStd2Std(mInXmlDoc);
		JdomUtil.print(mOutXmlDoc);
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	
	}
	
}
