//试算交易
package com.sinosoft.midplat.srcb.format;

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

public class NewCont extends XmlSimpFormat {
	private Element cHeader = null;
	
	public NewCont(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，有时候会用到
		cHeader =
			(Element) pNoStdXml.getRootElement().getChild("Header").clone();
		
//		JdomUtil.print(cTransaction_Header);
		
		Document mStdXml = 
			NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");
		
		Document mNoStdXml = 
			NewContOutXsl.newInstance().getCache().transform(pStdXml);

		/*Start-组织返回报文头*/

		Element mRetCode = new Element("Flag");
		Element mRetMsg = new Element("Desc");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//交易成功
			mRetCode.setText("0");
			mRetMsg.setText("交易成功！");
		} else {	//交易失败
			mRetCode.setText("1");
			mRetMsg.setText(mRetData.getChildText(Desc));
		}

		Element mHeader = new Element("Header");
		mHeader.addContent(mRetCode);
		mHeader.addContent(mRetMsg);
		mNoStdXml.getRootElement().addContent(mHeader);
		
		/*End-组织返回报文头*/
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");

		String mInFilePath = "D:/msg/04/2015/201501/20150128/143_3_1012_in.xml";
		String mOutFilePath = "H:/1111.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}
}