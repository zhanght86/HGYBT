//新农行心跳交易报文转换
package com.sinosoft.midplat.newabc.format;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class GreenTest extends XmlSimpFormat {
	private Element header=null;
	private String reserve="";
	public GreenTest(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into GreenTest.noStd2Std()...");
		
		JdomUtil.print(pNoStdXml);
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
//		reserve=pNoStdXml.getRootElement().getChild("App").getChild("Req").getChildText("Reserve");
		Document mStdXml = 
			GreenTestInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		cLogger.info("Out GreenTest.noStd2Std()!");
		
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into GreenTest.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Element tInsuTime  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/InsuTime");
		Document mNoStdXml = 
			GreenTestOutXsl.newInstance().getCache().transform(pStdXml);
		Element  RetCode=new Element("RetCode");
		Element  RetMsg = new Element("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("交易成功=========");
		   RetCode.setText("000000");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("交易失败=========失败信息:"+RetMsg.getText());
			RetCode.setText("009999");
		}
		header.addContent(RetCode);
		header.addContent(RetMsg);
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("InsuTime").setText(tInsuTime.getText());
		mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("Reserve").setText(reserve);
		mNoStdXml.getRootElement().addContent(header);
		cLogger.info("Out GreenTest.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
	System.out.println("程序开始…");

	String mInFilePath = "D:/File/task/20170214/newabc/ybt_test/1000in_noStd.xml";
	String mOutFilePath = "D:/File/task/20170214/newabc/ybt_test/1000in_Std.xml";

	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();

//	Document mOutXmlDoc = new GreenTest(null).std2NoStd(mInXmlDoc);
	Document mOutXmlDoc = new GreenTest(null).noStd2Std(mInXmlDoc);

	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("成功结束！");

}
}