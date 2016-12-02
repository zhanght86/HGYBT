package com.sinosoft.midplat.srcb.format;

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
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;

public class BusiBlc extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	
	public BusiBlc(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into BusiBlc.noStd2Std()...");

		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			BusiBlcInXsl.newInstance().getCache().transform(pNoStdXml);
		
//		InputStream mSheetIs = getClass().getResourceAsStream("BusiBlcIn.xsl");
//		InputStreamReader mSheetIsr = new InputStreamReader(mSheetIs, "GBK");
//		mStdXml = new XSLTransformer(mSheetIsr).transform(mStdXml);	
		Element tPrem = new Element(Prem);
		List tDetail = mStdXml.getRootElement().getChild("Body").getChildren("Detail");
		DecimalFormat tDF = new DecimalFormat("0.00");
		double mSumPrem = 0.00;
		for(int i = 0;i<tDetail.size();i++){
			Element dElement = (Element)tDetail.get(i);
			String tPremFen = dElement.getChild(Prem).getText();
			System.out.println(tPremFen);
			double tlPrem = Double.parseDouble(tPremFen);
			String calPrm = tDF.format(tlPrem);
			mSumPrem += Double.parseDouble(calPrm); 
		}
		tPrem.setText(tDF.format(mSumPrem));
		mStdXml.getRootElement().getChild("Body").addContent(tPrem);
		mStdXml.getRootElement().getChild("Body").getChild("Count").setText(String.valueOf(tDetail.size()));
		
		cLogger.info("Out BusiBlc.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into BusiBlc.std2NoStd()...");

//		Element mBkOthDate = new Element("BkOthDate");
//		mBkOthDate.setText(
//				String.valueOf(DateUtil.getCur8Date()));
//		Element mFlag = new Element("Flag");
//		Element mDesc = new Element("Desc");
//		Element mRetData = pStdXml.getRootElement().getChild("Head");
//		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
//			mFlag.setText("0");
//			mDesc.setText("交易成功！");
//		} else {	//交易失败
//			mFlag.setText("11111");
//			mDesc.setText(
//					mRetData.getChildText(Desc));
//		}

//		Element Transaction = new Element("Transaction");
//		Element TranData = new Element("TranData");
		//Element Head = new Element("Head");
//		Transaction.addContent(Head);
//		TranData.addContent(TranData);
//		Head.addContent(mFlag);
//		Head.addContent(mDesc);
		
		/*Start-组织返回报文头*/
		Element Transaction = new Element("Transaction");
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));
		//保险公司流水号
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

		Transaction.addContent(cTransaction_Header);
		/*End-组织返回报文头*/

		
		cLogger.info("Out BusiBlc.std2NoStd()!");
		return new Document(Transaction);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		long amnt = 10000;
		
		long change = com.sinosoft.midplat.common.NumberUtil.yuanToFen(amnt);
		
		String str =  String.valueOf(change);
		
		System.out.println(str);
		
//		String mInFile = "F:\\中融建行test\\ccb_日终对账.xml";
//		String mOutFile = "F:\\中融建行test\\ccb_ybtccb.xml";
		String mInFile = "H:/李路/日终对账_in.xml";
		String mOutFile = "H:/李路/日终对账_inSvc2.xml";

		Document mInXmlDoc = JdomUtil.build(new FileInputStream(mInFile));
		
		JdomUtil.print(mInXmlDoc);
		
		System.out.println("-----------------------------------------------------------");
		Element w =	new Element("e");
		Document mOutXmlDoc2 = new BusiBlc(w).noStd2Std(mInXmlDoc);
		Document mOutXmlDoc = new CallWebsvcAtomSvc("6").call(mOutXmlDoc2);
//		Document mOutXmlDoc = new BusiBlc(w).std2NoStd(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		JdomUtil.output(mOutXmlDoc, new FileOutputStream(mOutFile));
		
		System.out.println("成功结束！");
	}
}