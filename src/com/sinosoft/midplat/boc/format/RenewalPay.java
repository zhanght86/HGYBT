//中行续期缴费交易
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

public class RenewalPay extends XmlSimpFormat {
	private Element cMain = null;
	
	public RenewalPay(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RenewalPay.noStd2Std()...");
		cLogger.info("第三方请求报文:"+JdomUtil.toStringFmt(pNoStdXml));
		
		cMain = (Element) pNoStdXml.getRootElement().getChild("Main").clone();
		
		Document mStdXml = 
			RenewalPayInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("请求核心报文:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out RenewalPay.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RenewalPay.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pStdXml));
		
		Document mNoStdXml = 
			RenewalPayOutXsl.newInstance().getCache().transform(pStdXml);
		
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
		mResultCode.setText(wResultCode);
		mResultInfo.setText(wResultInfo);
		dMain.removeChild("ResultCode");
		dMain.removeChild("ResultInfo");
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

		/*End-组织返回报文头*/

		cLogger.info("返回给第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out RenewalPay.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		
		String mInFilePath = "E:\\保险公司\\中融\\银保通文档\\中行\\中行提供报文\\交易报文模板\\续期缴费查询请求报文.xml";
		String mOutFilePath = "F:\\中融中行test\\boc.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new RenewalPay(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new RenewalPay(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	}
}