//新农行非实时出单
package com.sinosoft.midplat.newabc.format;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NonRealTimeCont extends XmlSimpFormat {
	private Element header=null;
	private String reserve=null;
	private String name=null;
	private String idtype=null;
	private String idno=null;
	private String proposalprtno=null;
	private String riskcode=null;
	private String prodcode=null;
	private String tranno=null;
	private String trandate=null;
	private String trantime=null;
	private String bankcode=null;
	public NonRealTimeCont(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NonRealTimeCont.noStd2Std()...");
		
		JdomUtil.print(pNoStdXml);
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
//		reserve=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("Reserve");
		tranno=header.getChildText("SerialNo");
		bankcode=header.getChildText("BranchNo");
		name=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChild("Appl").getChildText("Name");
		idtype=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChild("Appl").getChildText("IDKind");
		idno=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChild("Appl").getChildText("IDCode");
		proposalprtno=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("PolicyApplyNo");
		riskcode=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("RiskCode");
		prodcode=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("ProdCode");
		
		Document mStdXml = 
			NonRealTimeContInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		cLogger.info("Out NonRealTimeCont.noStd2Std()!");
		
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NonRealTimeCont.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
//		Element tInsuTime  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/InsuTime");
		
		Document mNoStdXml = 
			NonRealTimeContOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("交易成功=========");
			mNoStdXml.getRootElement().getChild("Header").getChild("RetCode").setText("000000");
			mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg").setText("交易成功");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("交易失败=========失败信息:"+ttDesc.getText());
			mNoStdXml.getRootElement().getChild("Header").getChild("RetCode").setText("009999");
			mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg").setText(ttDesc.getText());
		}
		
		mNoStdXml.getRootElement().getChild("Header").getChild("SerialNo").setText(tranno);
		mNoStdXml.getRootElement().getChild("Header").getChild("InsuSerial").setText(tranno);
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(bankcode);
		Date date =new Date();
		mNoStdXml.getRootElement().getChild("Header").getChild("TransDate").setText(String.valueOf(DateUtil.get8Date(date)));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransTime").setText(String.valueOf(DateUtil.get6Time(date)));

		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Appl").getChild("Name").setText(name);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Appl").getChild("IDKind").setText(idtype);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Appl").getChild("IDCode").setText(idno);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("PolicyApplyNo").setText(proposalprtno);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("RiskCode").setText(riskcode);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("ProdCode").setText(prodcode);
		
//		mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg").setText(ttDesc.getText());
//		mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg").setText(ttDesc.getText());
//		if (ttFlag.getValue().equals("0")){
//		   cLogger.info("交易成功=========");
//		   RetCode.setText("000000");
//		}
//		if (ttFlag.getValue().equals("1")){
//			cLogger.info("交易失败=========失败信息:"+RetMsg.getText());
//			RetCode.setText("009999");
//		}
//		header.addContent(RetCode);
//		header.addContent(RetMsg);
//		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("InsuTime").setText(tInsuTime.getText());
//		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Reserve").setText(reserve);
//		mNoStdXml.getRootElement().addContent(header);
		cLogger.info("Out NonRealTimeCont.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//	System.out.println("程序开始…");
//
//	String mInFilePath = "E:/55555.xml";
//	String mOutFilePath = "E:/66666.xml";
//
//	InputStream mIs = new FileInputStream(mInFilePath);
//	Document mInXmlDoc = JdomUtil.build(mIs);
//	mIs.close();
//
////	Document mOutXmlDoc = new NonRealTimeCont(null).std2NoStd(mInXmlDoc);
//	Document mOutXmlDoc = new NonRealTimeCont(null).noStd2Std(mInXmlDoc);
//
//	JdomUtil.print(mOutXmlDoc);
//
//	OutputStream mOs = new FileOutputStream(mOutFilePath);
//	JdomUtil.output(mOutXmlDoc, mOs);
//	mOs.flush();
//	mOs.close();
//
//	System.out.println("成功结束！");
//
//}
}