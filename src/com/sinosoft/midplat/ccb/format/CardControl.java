package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class CardControl extends XmlSimpFormat{
	private Element cTransaction_Header = null;
	public CardControl(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into CardControl.getStdXml()...");
		
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		Document mStdXml = 
			CardControlInXsl.newInstance().getCache().transform(pNoStdXml);

		cLogger.info("Out CardControl.getStdXml()!");
		JdomUtil.print(mStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		//Into CardControl.getNoStdXml()...
		cLogger.info("Into CardControl.getNoStdXml()...");
		
//		InputStream mSheetIs = getClass().getResourceAsStream("CardControlOut.xsl");
//		InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
//		Document pNoStdXml = new XSLTransformer(mSheetIsr).transform(pStdXml);
//		mSheetIsr.close();
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		Document mNoStdXml = 
			CardControlOutXsl.newInstance().getCache().transform(pStdXml);
		/*Start-组织返回报文头*/
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				DateUtil.getCurDate("yyyyMMdd"));
		
		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));
		
		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
	
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
		
		mNoStdXml.getRootElement().addContent(cTransaction_Header);
		
		
		cLogger.info("Out CardControl.std2NoStd()!");
		JdomUtil.print(mNoStdXml);
		return mNoStdXml;
		
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		
		String mInFilePath = "C:\\Documents and Settings\\Administrator\\桌面\\96673200_3_7_outSvc.xml";
		String mOutFilePath = "F:\\中融建行test\\ccb_重控22222.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/cardcontrol/ccb__VCH102_St_Out.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/cardcontrol/ccb__VCH102_No_Out.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new CardControl(null).noStd2Std(mInXmlDoc);
		Document mOutXmlDoc = new CardControl(null).std2NoStd(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	}
}
