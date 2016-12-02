/*
 * 犹豫期退保冲正交易
 */
package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class ADDPREMCancel extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public ADDPREMCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ADDPREM.noStd2Std()...");
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			ADDPREMCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out ADDPREMCancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ADDPREMCancel.std2NoStd()...");
		
		Document mNoStdXml = 
			ADDPREMCancelOutXsl.newInstance().getCache().transform(pStdXml);
		

		//组织返回给银行的报文
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransType = new Element("TransType");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		
		Element mBankCode = new Element("BankCode");
		Element mRegionCode = new Element("RegionCode");
		Element mBranch = new Element("Branch");
		Element mTeller = new Element("Teller");
		Element mCarrierCode = new Element("CarrierCode");
		Element mInsuranceType = new Element("InsuranceType");
		Element mSquenceNo = new Element("SquenceNo");
		Element mFinActivityGrossAmt = new Element("FinActivityGrossAmt");
		Element mFormName = new Element("FormName");
		Element mProviderFormNumber = new Element("ProviderFormNumber");
		Element mSourceType = new Element("SourceType");
		

		mBankCode.setText(cTXLifeRequest.getChildText("BankCode"));
		mRegionCode.setText(cTXLifeRequest.getChildText("RegionCode"));
		mBranch.setText(cTXLifeRequest.getChildText("Branch"));
		mTeller.setText(cTXLifeRequest.getChildText("Teller"));
		mCarrierCode.setText(cTXLifeRequest.getChildText("CarrierCode"));
		mInsuranceType.setText(cTXLifeRequest.getChildText("InsuranceType"));
		mSquenceNo.setText(cTXLifeRequest.getChildText("SquenceNo"));
		mFinActivityGrossAmt.setText(cTXLifeRequest.getChildText("FinActivityGrossAmt"));
		mFormName.setText(cTXLifeRequest.getChildText("FormName"));
		mProviderFormNumber.setText(cTXLifeRequest.getChildText("ProviderFormNumber"));
		mSourceType.setText(cTXLifeRequest.getChildText("SourceType"));
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		
		mTXLifeResponse.addContent(mBankCode);
		mTXLifeResponse.addContent(mRegionCode);
		mTXLifeResponse.addContent(mBranch);
		mTXLifeResponse.addContent(mTeller);
		mTXLifeResponse.addContent(mCarrierCode);
		mTXLifeResponse.addContent(mInsuranceType);
		mTXLifeResponse.addContent(mSquenceNo);
		mTXLifeResponse.addContent(mFinActivityGrossAmt);
		mTXLifeResponse.addContent(mFormName);
		mTXLifeResponse.addContent(mProviderFormNumber);
		mTXLifeResponse.addContent(mSourceType);	
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		
		cLogger.info("Out ADDPREMCancel.std2NoStd()!");
		return mNoStdXml;
	}
}