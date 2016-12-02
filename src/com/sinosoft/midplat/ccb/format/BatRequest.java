package com.sinosoft.midplat.ccb.format;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class BatRequest extends XmlSimpFormat {
	public BatRequest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	private Element cTransaction_Header = null;
	private Element cBkFileName = null;

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into BatRequest.noStd2Std()...");
		JdomUtil.print(pNoStdXml);
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		cBkFileName = (Element)pNoStdXml.getRootElement().getChild("Transaction_Body").getChild("BkFileName").clone();
		InputStream mSheetIs = getClass().getResourceAsStream("BatRequestIn.xsl");
		InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
		Document mStdXml = new XSLTransformer(mSheetIsr).transform(pNoStdXml);
		mSheetIsr.close();

		cLogger.info("Out BatRequest.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into BatRequest.std2NoStd()...");
		
		InputStream mSheetIs = getClass().getResourceAsStream("BatRequestOut.xsl");
		InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
		Document pNoStdXml = new XSLTransformer(mSheetIsr).transform(pStdXml);
		mSheetIsr.close();

		/*Start-��֯���ر���ͷ*/
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				DateUtil.getCurDate("yyyyMMdd"));

		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mBkOthRetCode.setText("00000");
			mBkOthRetMsg.setText("���׳ɹ���");
		} else {	//����ʧ��
			mBkOthRetCode.setText("11111");
			mBkOthRetMsg.setText(
					mRetData.getChildText(Desc));
		}
		Element mTran_Response = new Element("Tran_Response");
		mTran_Response.addContent(mBkOthDate);
		mTran_Response.addContent(mBkOthSeq);
		mTran_Response.addContent(mBkOthRetCode);
		mTran_Response.addContent(mBkOthRetMsg);
		cTransaction_Header.addContent(mTran_Response);

		pNoStdXml.getRootElement().getChild("Transaction_Body").addContent(0, cBkFileName);
		pNoStdXml.getRootElement().addContent(0, cTransaction_Header);
		/*End-��֯���ر���ͷ*/

		cLogger.info("Out BatRequest.std2NoStd()!");
		return pNoStdXml;
	}
}
