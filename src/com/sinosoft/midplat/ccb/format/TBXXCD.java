package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class TBXXCD extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	
	public TBXXCD(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into TBXXCD.noStd2Std()...");

		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			TBXXCDInXsl.newInstance().getCache().transform(pNoStdXml);
			
		cLogger.info("Out TBXXCD.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into TBXXCD.std2NoStd()...");
		
		Document mStdXml = 
			TBXXCDOutXsl.newInstance().getCache().transform(pStdXml);
		List tDetail_List = mStdXml.getRootElement().getChild("Transaction_Body").getChildren("Detail_List");
		for (int i=0;i<tDetail_List.size();i++){
			Element ttDetail_List = (Element)tDetail_List.get(i);
			List tDetail = ttDetail_List.getChildren("Detail");
			for (int j=0;j<tDetail.size();j++){
				Element ttDetail = (Element)tDetail.get(j);
				String tBkDetail1 = ttDetail.getChildText("BkActBrch");
				String mSQL = "select nodeno from nodemap where agentcom = '"+tBkDetail1+"' and trancom = '4'";
				ExeSQL ExeSQL = new ExeSQL();
				String mm = ExeSQL.getOneValue(mSQL);
				ttDetail.getChild("BkActBrch").setText(mm);
			}
		}
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

		cTransaction_Header.addContent(mTran_Response);
		mStdXml.getRootElement().addContent(cTransaction_Header);
		/*End-组织返回报文头*/

		
		cLogger.info("Out TBXXCD.std2NoStd()!");
		return mStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		long amnt = 10000;
		
		long change = com.sinosoft.midplat.common.NumberUtil.yuanToFen(amnt);
		
		String str =  String.valueOf(change);
		
		System.out.println(str);
		
//		String mInFile = "F:\\中融建行test\\ccb_日终对账.xml";
//		String mOutFile = "F:\\中融建行test\\ccb_ybtccb.xml";
		String mInFile = "C:\\Users\\lmt\\Desktop\\1000184355_3_18_outSvc.xml";
		String mOutFile = "e:\\ccb_ybtccb.xml";

		Document mInXmlDoc = JdomUtil.build(new FileInputStream(mInFile));
		
		JdomUtil.print(mInXmlDoc);
		
		System.out.println("-----------------------------------------------------------");
		Element w =	new Element("e");
//		Document mOutXmlDoc = new TBXXCD(w).noStd2Std(mInXmlDoc);
		Document mOutXmlDoc = new TBXXCD(w).std2NoStd(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		JdomUtil.output(mOutXmlDoc, new FileOutputStream(mOutFile));
		
		System.out.println("成功结束！");
	}
}