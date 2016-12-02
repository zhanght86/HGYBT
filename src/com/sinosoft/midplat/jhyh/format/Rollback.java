package com.sinosoft.midplat.jhyh.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class Rollback extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	public Rollback(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Rollback.noStd2Std()...");

		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		Document mStdXml = 
			RollbackInXsl.newInstance().getCache().transform(pNoStdXml);
        
		cLogger.info("Out Rollback.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Rollback.std2NoStd()...");

		Document mNoStdXml = 
			RollbackOutXsl.newInstance().getCache().transform(pStdXml);
		/*Start-组织返回报文头*/
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));

		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mBkOthRetCode.setText("00000");
			mBkOthRetMsg.setText("交易成功！");
		} else {	//交易失败
			mBkOthRetCode.setText("11111");
			mBkOthRetMsg.setText(
					mRetData.getChildText(Desc));
		}

		Element mTran_Response = new Element("Tran_Response");
		mTran_Response.addContent(mBkOthDate);
		mTran_Response.addContent(mBkOthSeq);
		mTran_Response.addContent(mBkOthRetCode);
		mTran_Response.addContent(mBkOthRetMsg);

//		cTransaction_Header.addContent(mTran_Response);
		mNoStdXml.getRootElement().addContent(mTran_Response);

		mNoStdXml.getRootElement().addContent(cTransaction_Header);
		/*End-组织返回报文头*/
		cLogger.info("Out Rollback.std2NoStd()!");
		return mNoStdXml;
	}
}
