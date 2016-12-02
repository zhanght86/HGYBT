//中行续期缴费查询
package com.sinosoft.midplat.boc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class RenewalPayQuery extends XmlSimpFormat {
	private Element cMain = null;
	
	public RenewalPayQuery(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RenewalPayQuery.noStd2Std()...");
		
		cMain = (Element) pNoStdXml.getRootElement().getChild("Main").clone();
		
		Document mStdXml = 
			RenewalPayQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out RenewalPayQuery.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RenewalPayQuery.std2NoStd()...");
		Document mNoStdXml = 
			RenewalPayQueryOutXsl.newInstance().getCache().transform(pStdXml);

		/*Start-组织返回报文头*/

		Element mTranDate = new Element("TranDate");
		Element mTranTime = new Element("TranTime");
		Element mInsuId = new Element("InsuId");
		Element mZoneNo = new Element("ZoneNo");
		Element mBrNo = new Element("BrNo");
		Element mTellerNo = new Element("TellerNo");
		Element mTransNo = new Element("TransNo");
		Element mTranCode = new Element("TranCode");
		Element mResultCode = new Element("ResultCode");
		Element mResultInfo = new Element("ResultInfo");
		Element mPolicyNo = new Element("PolicyNo");
		Element mRecvDate = new Element("RecvDate");
		Element mRecvAmount = new Element("RecvAmount");
		Element mRecvNum = new Element("RecvNum");
		Element mPayStartDate = new Element("PayStartDate");
		Element mPayEndDate = new Element("PayEndDate");
		Element mAppntName = new Element("AppntName");
		Element mRiskName = new Element("RiskName");
		
		mTranDate.setText(cMain.getChildText("TranDate"));
		mTranTime.setText(cMain.getChildText("TranTime"));
		mInsuId.setText(cMain.getChildText("InsuId"));
		mZoneNo.setText(cMain.getChildText("ZoneNo"));
		mBrNo.setText(cMain.getChildText("BrNo"));
		mTellerNo.setText(cMain.getChildText("TellerNo"));
		mTransNo.setText(cMain.getChildText("TransNo"));
		mTranCode.setText(cMain.getChildText("TranCode"));

		Element dMain = mNoStdXml.getRootElement().getChild("Main");
		String wResultCode = dMain.getChildText("ResultCode");
		String wResultInfo = dMain.getChildText("ResultInfo");
		String wPolicyNo = dMain.getChildText("PolicyNo");
		String wRecvDate = dMain.getChildText("RecvDate");
		String wRecvAmount = dMain.getChildText("RecvAmount");
		String wRecvNum = dMain.getChildText("RecvNum");
		String wPayStartDate = dMain.getChildText("PayStartDate");
		String wPayEndDate = dMain.getChildText("PayEndDate");
		String wAppntName = dMain.getChildText("AppntName");
		String wRiskName = dMain.getChildText("RiskName");
		mResultCode.setText(wResultCode);
		mResultInfo.setText(wResultInfo);
		mPolicyNo.setText(wPolicyNo);
		mRecvDate.setText(wRecvDate);
		mRecvAmount.setText(wRecvAmount);
		mRecvNum.setText(wRecvNum);
		mPayStartDate.setText(wPayStartDate);
		mPayEndDate.setText(wPayEndDate);
		mAppntName.setText(wAppntName);
		mRiskName.setText(wRiskName);
		
		dMain.removeChild("ResultCode");
		dMain.removeChild("ResultInfo");
		dMain.removeChild("PolicyNo");
		dMain.removeChild("RecvDate");
		dMain.removeChild("RecvAmount");
		dMain.removeChild("RecvNum");
		dMain.removeChild("PayStartDate");
		dMain.removeChild("PayEndDate");
		dMain.removeChild("AppntName");
		dMain.removeChild("RiskName");
		
		dMain.addContent(mTranDate);
		dMain.addContent(mTranTime);
		dMain.addContent(mInsuId);
		dMain.addContent(mZoneNo);
		dMain.addContent(mBrNo);
		dMain.addContent(mTellerNo);
		dMain.addContent(mTransNo);
		dMain.addContent(mTranCode);
		
		dMain.addContent(mResultCode);
		dMain.addContent(mResultInfo);
		dMain.addContent(mPolicyNo);
		dMain.addContent(mRecvDate);
		dMain.addContent(mRecvAmount);
		dMain.addContent(mRecvNum);
		dMain.addContent(mPayStartDate);
		dMain.addContent(mPayEndDate);
		dMain.addContent(mAppntName);
		dMain.addContent(mRiskName);
		
		

		
		
		/*End-组织返回报文头*/

		cLogger.info("Out RenewalPayQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		
		String mInFilePath = "E:\\保险公司\\中融\\银保通文档\\中行\\中行提供报文\\交易报文模板\\续期缴费查询请求报文.xml";
		String mOutFilePath = "e:\\boc.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new RenewalPayQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new RenewalPayQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	}
}