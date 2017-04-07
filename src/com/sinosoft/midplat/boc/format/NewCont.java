package com.sinosoft.midplat.boc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * 保费试算转换
 * @author anico
 *
 */
public class NewCont extends XmlSimpFormat {
	private Element cMain = null;
	public NewCont(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewCont.noStd2Std()...");
		cMain =(Element) pNoStdXml.getRootElement().getChild("Main").clone();
		Document mStdXml =NewContInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("请求核心报文:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out NewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewCont.std2NoStd()...");
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(pStdXml));
		Document mNoStdXml = NewContOutXsl.newInstance().getCache().transform(pStdXml);
		Element mainEle=mNoStdXml.getRootElement().getChild("Main");
		mainEle.getChild("InsuId").setText(cMain.getChildText("InsuId"));
		mainEle.getChild("ZoneNo").setText(cMain.getChildText("ZoneNo"));
		mainEle.getChild("BrNo").setText(cMain.getChildText("BrNo"));
		mainEle.getChild("TellerNo").setText(cMain.getChildText("TellerNo"));
		mainEle.getChild("TransNo").setText(cMain.getChildText("TransNo"));
		mainEle.getChild("TranCode").setText(cMain.getChildText("TranCode"));
		cLogger.info("返回给第三方报文:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out NewCont.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String args[])throws Exception{
    String mInFilePath = "C:\\Users\\PengYF\\Desktop\\sinosoft\\HG\\boc\\保费试算1001.xml";
    String mOutFilePath = "C:\\Users\\PengYF\\Desktop\\test.xml";
    InputStream mIs = new FileInputStream(mInFilePath);
    Document mInXmlDoc = JdomUtil.build(mIs);
    mIs.close();
    Document mOutXmlDoc = (new NewCont(null)).noStd2Std(mInXmlDoc);
//    Document mOutXmlDoc = (new NewCont(null)).std2NoStd(mInXmlDoc);
    JdomUtil.print(mOutXmlDoc);
    OutputStream mOs = new FileOutputStream(mOutFilePath);
    JdomUtil.output(mOutXmlDoc, mOs);
    mOs.flush();
    mOs.close();
}
}