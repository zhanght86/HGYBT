package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * 保全查询 报文转换
 * @author Liuzk
 *
 */
public class SecureQuery extends XmlSimpFormat {
	private Element header=null;
	
	public SecureQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into SecureQuery.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Document mStdXml = 
			SecureQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into SecureQuery.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		
		Document mNoStdXml = SecureQueryOutXsl.newInstance().getCache().transform(pStdXml);
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		//为请求业务报文头信息加入返回码和返回信息.把请求的业务报文头加入到返回报文中返回给银行。
		Element  RetCode= mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element  RetMsg = mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("交易成功=========");
		   RetCode.setText("000000");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("交易失败=========失败信息:"+RetMsg.getText());
			RetCode.setText("009999");
		}
		
		cLogger.info(RetCode.getText());
		cLogger.info(RetMsg.getText());
		
		
		cLogger.info("Out SecureQuery.std2NoStd()!");
		return mNoStdXml;
		
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
////		String mInFilePath = "C:/Users/Administrator/Desktop/报文/新农行保全查询请求.xml";
////		String mOutFilePath = "C:/Users/Administrator/Desktop/报文/新农行保全查询返回.xml";
//
//		String mInFilePath = "H:/1001430_25_10_outSvc.xml";
//		String mOutFilePath = "H:/保全查询.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new SecureQuery(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new SecureQuery(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
////
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//		System.out.println("成功结束！");
//	}
}
