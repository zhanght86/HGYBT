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
/**
 * 保全申请状态查询 报文转换
 * @author Liuzk
 *
 */
public class SecureStatusQuery extends XmlSimpFormat {
	//报文头
	private Element header=null;
	//险种代码
	private String riskcode=null;
	//业务类别
	private String busitype=null;

	public SecureStatusQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into SecureStatusQuery.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Document mStdXml = 
			SecureStatusQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		riskcode=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("RiskCode");
		busitype=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("BusinType");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into SecureStatusQuery.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		
//		String mInFilePath2 = "G:/1002891_501_3014_in.xml";
//		InputStream mIs2 = new FileInputStream(mInFilePath2);
//		Document mInXmlDoc = JdomUtil.build(mIs2);
//		header=(Element)mInXmlDoc.getRootElement().getChild("Header").clone();
//		riskcode=mInXmlDoc.getRootElement().getChild("App").getChild("Req").getChildText("RiskCode");
//		busitype=mInXmlDoc.getRootElement().getChild("App").getChild("Req").getChildText("BusinType");
//		mIs2.close();
		
		Document mNoStdXml = 
			SecureStatusQueryOutXsl.newInstance().getCache().transform(pStdXml);
		Date date=new Date();
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		mNoStdXml.getRootElement().getChild("Header").getChild("CorpNo").setText(header.getChildText("CorpNo"));
		mNoStdXml.getRootElement().getChild("Header").getChild("InsuSerial").setText(header.getChildText("SerialNo"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransDate").setText(header.getChildText("TransDate"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransTime").setText(String.valueOf(DateUtil.get6Time(date)));
		mNoStdXml.getRootElement().getChild("Header").getChild("SerialNo").setText(header.getChildText("SerialNo"));
		//为请求业务报文头信息加入返回码和返回信息.把请求的业务报文头加入到返回报文中返回给银行。
		Element  RetCode= mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element  RetMsg = mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("RiskCode").setText(riskcode);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("BusinType").setText(busitype);
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
		
		
		cLogger.info("Out SecureStatusQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("程序开始…");
////		String mInFilePath = "C:/Users/Administrator/Desktop/报文/新农行保全申请状态查询请求.xml";
////		String mOutFilePath = "C:/Users/Administrator/Desktop/报文/新农行保全申请状态查询返回.xml";
//
//		String mInFilePath = "G:/1002891_503_46_outSvc.xml";
//		String mOutFilePath = "G:/1.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new SecureStatusQuery(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new SecureStatusQuery(null).noStd2Std(mInXmlDoc);
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
