/*
 * 客户资料变更交易
 */
package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ContInfoChange extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public ContInfoChange(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContInfoChange.noStd2Std()...");
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			ContInfoChangeInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out ContInfoChange.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContInfoChange.std2NoStd()...");
		
		Document mNoStdXml = 
			ContInfoChangeOutXsl.newInstance().getCache().transform(pStdXml);
		

		//组织返回给银行的报文
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransType = new Element("TransType");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		
		cLogger.info("Out ContInfoChange.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "C:\\Users\\lmt\\Desktop\\WY和ATM测试报文\\保单基本信息变更请求.xml";
		String mOutFilePath = "E:/测试返回报文/44.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new ContInfoChange(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new ContInfoChange(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}
}