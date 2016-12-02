/*
 * 犹豫期退保查交易
 */
package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.ccb.format.NewCont;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class XQPAY extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public XQPAY(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into XQPAY.noStd2Std()...");
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			XQPAYInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out XQPAY.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into XQPAY.std2NoStd()...");
		
		Document mNoStdXml = 
			XQPAYOutXsl.newInstance().getCache().transform(pStdXml);
		
		//组织返回给银行的报文
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransNo = new Element("TransNo");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		mTransNo.setText(cTXLifeRequest.getChildText("TransNo"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mTXLifeResponse.addContent(mTransNo);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		
		cLogger.info("Out XQPAY.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "E:\\保险公司\\中融\\报文\\工行三期保全\\寿险\\犹豫期撤保及退保请求(TC=105).xml";
		String mOutFilePath = "e:\\icbc_返回结果.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new XQPAY(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new XQPAY(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}
}