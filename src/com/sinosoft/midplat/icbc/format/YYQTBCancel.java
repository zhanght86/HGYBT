/*
 * 犹豫期退保冲正交易
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

public class YYQTBCancel extends XmlSimpFormat {
	
	private Element cTXLifeRequest = null;
	
	public YYQTBCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into YYQTB.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		
		
		Document mStdXml = 
			YYQTBCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out YYQTBCancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into YYQTBCancel.std2NoStd()...");
		JdomUtil.print(pStdXml);
		Document mNoStdXml = 
			YYQTBCancelOutXsl.newInstance().getCache().transform(pStdXml);
		
		//组织返回给银行的报文
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransType = new Element("TransType");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		Element mDocumentControlNumber = new Element("DocumentControlNumber");
		Element mAttachmentData = new Element("AttachmentData");
		Element mTransMode = new Element("TransMode");
		Element mRcptId = new Element("RcptId");
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mDocumentControlNumber.setText(cTXLifeRequest.getChildText("DocumentControlNumber"));//单证号码
		mAttachmentData.setText(cTXLifeRequest.getChildText("AttachmentData"));//批单内容
		mTransMode.setText(cTXLifeRequest.getChildText("TransMode"));//交易模式
		mRcptId.setText(cTXLifeRequest.getChildText("RcptId"));
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		mTXLifeResponse.addContent(mDocumentControlNumber);
		mTXLifeResponse.addContent(mAttachmentData);
		mTXLifeResponse.addContent(mTransMode);
		mTXLifeResponse.addContent(mRcptId);
//		mNoStdXml.getRootElement().addContent(mTXLifeResponse);
		
		cLogger.info("Out YYQTBCancel.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "E:\\保险公司\\中融\\报文\\工行三期保全\\银保通三期寿险外部接口\\当日撤单-悔单请求(TC=103).xml";
		String mOutFilePath = "e:\\icbc_返回结果.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new YYQTBCancel(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new YYQTBCancel(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}
}