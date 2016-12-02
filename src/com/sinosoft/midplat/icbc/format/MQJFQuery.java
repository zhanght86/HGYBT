/*
 * 犹豫期退保查询交易
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

public class MQJFQuery extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public MQJFQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into MQJFQuery.noStd2Std()...");
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			MQJFQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out MQJFQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into MQJFQuery.std2NoStd()...");
		
		Document mNoStdXml = 
			MQJFQueryOutXsl.newInstance().getCache().transform(pStdXml);
		//组织返回给银行的报文
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransType = new Element("TransType");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		Element mPolicyStatus = new Element("PolicyStatus");
		Element mFinActivityGrossAmt = new Element("FinActivityGrossAmt");
		Element mBenefitMode = new Element("BenefitMode");
		Element mBonusAmnt = new Element("BonusAmnt");
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mPolicyStatus.setText(cTXLifeRequest.getChildText("PolicyStatus"));
		mFinActivityGrossAmt.setText(cTXLifeRequest.getChildText("FinActivityGrossAmt"));
		mBenefitMode.setText(cTXLifeRequest.getChildText("BenefitMode"));
		mBonusAmnt.setText(cTXLifeRequest.getChildText("BonusAmnt"));
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		mTXLifeResponse.addContent(mPolicyStatus);
		mTXLifeResponse.addContent(mFinActivityGrossAmt);
		mTXLifeResponse.addContent(mBenefitMode);
		mTXLifeResponse.addContent(mBonusAmnt);
//		mNoStdXml.getRootElement().addContent(mTXLifeResponse);

		
		cLogger.info("Out MQJFQuery.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "C:\\Documents and Settings\\Administrator\\桌面\\icbc\\满期给付查询请求(TC=213).xml";
		String mOutFilePath = "e:\\icbc_返回结果.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new MQJFQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new MQJFQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}

}