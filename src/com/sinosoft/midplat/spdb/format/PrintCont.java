package com.sinosoft.midplat.spdb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class PrintCont extends XmlSimpFormat {

	String tranNo;
	
	public PrintCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into PrintCont.noStd2Std()...");
		Document mStdXml = PrintContInXsl.newInstance().getCache().transform(pNoStdXml);
		/* ��¼��������ˮ�ţ����ر���ʱ�á� */
		tranNo = pNoStdXml.getRootElement().getChild("BUSI").getChildTextTrim("TRANS");
		cLogger.info("Out PrintCont.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into PrintCont.std2NoStd()...");
		Document mNoStdXml =PrintContOutXsl.newInstance().getCache().transform(pStdXml);
		// ���ظ��ַ��ı���������ˮ��
		mNoStdXml.getRootElement().getChild("BUSI").getChild("TRANS").setText(tranNo);
		cLogger.info("Out PrintCont.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception{
	
		System.out.println("����ʼ��");
		String mInFilePath = "D:/File/task/20170420/spdb/unit_test/23465_33_29_outSvc.xml";
		String mOutFilePath = "D:/File/task/20170420/spdb/unit_test/23465_36_1004_out.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		Document mOutXmlDoc = new PrintCont(null).std2NoStd(mInXmlDoc);
		// Document mOutXmlDoc = new PrintCont(null).noStd2Std(mInXmlDoc);
		JdomUtil.print(mOutXmlDoc);
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("�ɹ�������");
	
	}
	
}
