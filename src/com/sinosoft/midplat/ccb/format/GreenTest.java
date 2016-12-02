//建行试算交易
package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class GreenTest extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	private String  tPbInsuId = null;
	
	public GreenTest(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into GreenTest.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		tPbInsuId=cTransaction_Header.getChildText("tPbInsuId");
		Document mStdXml = 
			GreenTestInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out GreenTest.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into GreenTest.std2NoStd()...");
		JdomUtil.print(pStdXml);
		Document mNoStdXml = 
			GreenTestOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		/*Start-组织返回报文头*/
		cTransaction_Header=mNoStdXml.getRootElement().getChild("Transaction_Header");
		cTransaction_Header.getChild("tPbInsuId").setText(tPbInsuId);
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));

		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		System.out.println("结果标志："+mRetData.getChildText(Flag));
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

		//mNoStdXml.getRootElement().addContent(cTransaction_Header);
		/*End-组织返回报文头*/

		cLogger.info("Out GreenTest.std2NoStd()!");
		return mNoStdXml;
	}

//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
//
//		String mInFilePath = "C:\\Documents and Settings\\Administrator\\桌面\\90853014_3_0_outSvc.xml";
//		String mOutFilePath = "F:\\中融建行test\\ccb_返回结果.xml";
//
////		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/GreenTest/ccb_01_NST_In.xml";
////		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/GreenTest/ccb_01_out_230701.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new GreenTest(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new GreenTest(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//
//		System.out.println("成功结束！");
//	}
}