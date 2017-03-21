//中行重打交易
package com.sinosoft.midplat.boc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.f1j.mvc.el;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat {
	private Element cMain = null;
	
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		cLogger.info("第三方请求报文:"+JdomUtil.toStringFmt(pNoStdXml));
		
		cMain =(Element) pNoStdXml.getRootElement().getChild("Main").clone();
		
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("请求核心报文:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pStdXml));
		
		//重打和新单返回报文基本完全一样，所以直接调用
//		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(pStdXml);
		Document mNoStdXml = new ContConfirm(cThisBusiConf).std2NoStd(pStdXml);
		Element mMainEle=mNoStdXml.getRootElement().getChild("Main");
		mMainEle.getChild("InsuId").setText(cMain.getChildText("InsuId"));
		mMainEle.getChild("ZoneNo").setText(cMain.getChildText("ZoneNo"));
		mMainEle.getChild("BrNo").setText(cMain.getChildText("BrNo"));
		mMainEle.getChild("TellerNo").setText(cMain.getChildText("TellerNo"));
		mMainEle.getChild("TransNo").setText(cMain.getChildText("TransNo"));
		mMainEle.getChild("TranCode").setText(cMain.getChildText("TranCode"));
		
//		cLogger.info("返回第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		
		String mInFilePath = "F:\\中融建行test\\ccb_重打.xml";
		String mOutFilePath = "F:\\中融建行test\\ccb_返回结果.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/rePrint/SPE002_Response.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/rePrint/SPE002_Response_NST.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
		Document mOutXmlDoc = new RePrint(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	}
}
