package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class TBJYXX extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	
	public TBJYXX(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into TBJYXX.noStd2Std()...");

		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			TBJYXXInXsl.newInstance().getCache().transform(pNoStdXml);
			
		cLogger.info("Out TBJYXX.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into TBJYXX.std2NoStd()...");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		pStdXml = 
			TBJYXXOutXsl.newInstance().getCache().transform(pStdXml);
		/*Start-组织返回报文头*/
		Element Transaction = pStdXml.getRootElement();
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));

		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
//		Element mRetData = pStdXml.getRootElement().getChild("Head");
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

		Transaction.addContent(cTransaction_Header);
		/*End-组织返回报文头*/

		JdomUtil.print(pStdXml);
		cLogger.info("Out TBJYXX.std2NoStd()!");
		return pStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		long amnt = 10000;
		
		long change = com.sinosoft.midplat.common.NumberUtil.yuanToFen(amnt);
		
		String str =  String.valueOf(change);
		
		System.out.println(str);
		
//		String mInFile = "F:\\中融建行test\\ccb_日终对账.xml";
//		String mOutFile = "F:\\中融建行test\\ccb_ybtccb.xml";
		String mInFile = "C:\\Documents and Settings\\Administrator\\桌面\\90853284_3_7_outSvc.xml";
		String mOutFile = "F:\\中融建行test\\ccb_ybtccb.xml";

		Document mInXmlDoc = JdomUtil.build(new FileInputStream(mInFile));
		
		JdomUtil.print(mInXmlDoc);
		
		System.out.println("-----------------------------------------------------------");
		Element w =	new Element("e");
//		Document mOutXmlDoc = new BusiBlc(w).noStd2Std(mInXmlDoc);
		Document mOutXmlDoc = new BusiBlc(w).std2NoStd(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		JdomUtil.output(mOutXmlDoc, new FileOutputStream(mOutFile));
		
		System.out.println("成功结束！");
	}
}