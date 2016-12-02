package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class BatResponse extends XmlSimpFormat {
	public BatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
		// TODO Auto-generated constructor stub
	}

	private Element cTransaction_Header = null;
	

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into BatResponse.getStdXml()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		InputStream mSheetIs = getClass().getResourceAsStream("BatResponseIn.xsl");
		InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
		Document mStdXml = new XSLTransformer(mSheetIsr).transform(pNoStdXml);
		mSheetIsr.close();

		cLogger.info("Out BatResponse.getStdXml()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into BatResponse.std2NoStd()...");
		
		Document pNoStdXml = new Document();
		Element mTransaction = new Element("Transaction");
		pNoStdXml.addContent(mTransaction);
		
		/*Start-组织返回报文头*/
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				DateUtil.getCurDate("yyyyMMdd"));

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

		cTransaction_Header.addContent(mTran_Response);

		pNoStdXml.getRootElement().addContent(cTransaction_Header);
		
		/*End-组织返回报文头*/

		cLogger.info("Out BatResponse.std2NoStd()!");
		return pNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "D:\\YBT_SAVE_XML\\DONGWU\\jianhang\\CCBBatResponse.xml";
		String mOutFilePath = "D:\\YBT_SAVE_XML\\DONGWU\\jianhang\\CCBBatResponse_instd.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/GreenTest/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/GreenTest/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

		Document mOutXmlDoc = new BatResponse(null).noStd2Std(mInXmlDoc);
//		Document mOutXmlDoc = new GreenTest(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}
}
