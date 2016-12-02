//当日撤单
package com.sinosoft.midplat.srcb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class WriteOff extends XmlSimpFormat {
	private Element cHeader = null;
	private Element cBody = null;
	
	public WriteOff(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into WriteOff.noStd2Std()...");
		
		cHeader =
			(Element) pNoStdXml.getRootElement().getChild("Header").clone();
		cBody =
			(Element) pNoStdXml.getRootElement().getChild("Body").clone();
		
		Document mStdXml = 
			WriteOffInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out WriteOff.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into WriteOff.std2NoStd()...");
		
		Document mNoStdXml = 
			WriteOffOutXsl.newInstance().getCache().transform(pStdXml);
		
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
		
		cLogger.info("Out WriteOff.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		
		String mInFilePath = "D:/msg/04/2015/201501/20150128/162_3_1015_in.xml";
		String mOutFilePath = "H:/3333.xml";
	
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new WriteOff(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new WriteOff(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("成功结束！");
	}
}