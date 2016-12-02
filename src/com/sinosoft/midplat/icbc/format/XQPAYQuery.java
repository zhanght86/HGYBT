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

public class XQPAYQuery extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public XQPAYQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into XQPAYQuery.noStd2Std()...");
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			XQPAYQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out XQPAYQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into XQPAYQuery.std2NoStd()...");
		
		Document mNoStdXml = 
			XQPAYQueryOutXsl.newInstance().getCache().transform(pStdXml);
		
		//组织返回给银行的报文
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransNo = new Element("TransNo");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		Element mFullName = new Element("FullName");
		Element mGovtIDTC = new Element("GovtIDTC");
		Element mGovtID = new Element("GovtID");
		Element mOriginatingObjectType = new Element("OriginatingObjectType");
		Element mRelatedObjectType = new Element("RelatedObjectType");
		Element mRelationRoleCode = new Element("RelationRoleCode");
		Element mProductCode = new Element("ProductCode");
		Element mPlanName = new Element("PlanName");
		Element mPaymentStartDate = new Element("PaymentStartDate");
		Element mPaymentEndDate = new Element("PaymentEndDate");
		Element mPaymentTimes = new Element("PaymentTimes");
		Element mPayItm = new Element("PayItm");
		Element mFinActivityGrossAmt = new Element("FinActivityGrossAmt");
		Element mPayedTimes = new Element("PayedTimes");
		Element mPaymentOrdere = new Element("PaymentOrder");
		Element mFinEffDate = new Element("FinEffDate");
		Element mNextPayAmt = new Element("NextPayAmt");
		Element mPaymentState = new Element("PaymentState");
		Element mPaymentDate = new Element("PaymentDate");
		Element mRemark = new Element("Remark");
		Element mPaymentYears = new Element("PaymentYears");
		Element mACCCODE = new Element("ACCCODE");
		
		mTransNo.setText(cTXLifeRequest.getChildText("TransNo"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));	
				
		mTXLifeResponse.addContent(mTransNo);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		mTXLifeResponse.addContent(mGovtIDTC);
		mTXLifeResponse.addContent(mFullName);
		mTXLifeResponse.addContent(mGovtID);
		mTXLifeResponse.addContent(mOriginatingObjectType);
		mTXLifeResponse.addContent(mRelatedObjectType);
		mTXLifeResponse.addContent(mRelationRoleCode);
		mTXLifeResponse.addContent(mProductCode);
		mTXLifeResponse.addContent(mPlanName);
		mTXLifeResponse.addContent(mPaymentStartDate);
		mTXLifeResponse.addContent(mPaymentEndDate);
		mTXLifeResponse.addContent(mPaymentTimes);
		mTXLifeResponse.addContent(mPayItm);
		mTXLifeResponse.addContent(mFinActivityGrossAmt);
		mTXLifeResponse.addContent(mPayedTimes);
		mTXLifeResponse.addContent(mPaymentOrdere);
		mTXLifeResponse.addContent(mFinEffDate);
		mTXLifeResponse.addContent(mNextPayAmt);
		mTXLifeResponse.addContent(mPaymentState);
		mTXLifeResponse.addContent(mPaymentDate);
		mTXLifeResponse.addContent(mRemark);
		mTXLifeResponse.addContent(mPaymentYears);
		mTXLifeResponse.addContent(mACCCODE);
				
		cLogger.info("Out XQPAYQuery.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "C:\\Documents and Settings\\Administrator\\桌面\\icbc\\当日撤单-悔单请求.xml";
		String mOutFilePath = "e:\\icbc_返回结果.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new XQPAYQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new XQPAYQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}

}