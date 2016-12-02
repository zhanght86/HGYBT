//�����ش���
package com.sinosoft.midplat.boc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat {
	private Element cMain = null;
	
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		
		cMain =(Element) pNoStdXml.getRootElement().getChild("Main").clone();
		
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");

		//�ش���µ����ر��Ļ�����ȫһ��������ֱ�ӵ���
		Element rr = new Element("cThisConf");
		ContConfirm mContConfirm = new ContConfirm(rr);
		mContConfirm.setHeader(cMain);
		Document mNoStdXml = mContConfirm.std2NoStd(pStdXml);
//		Document mNoStdXml = new ContConfirm(cThisBusiConf).std2NoStd(pStdXml);

		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");
		
		String mInFilePath = "F:\\���ڽ���test\\ccb_�ش�.xml";
		String mOutFilePath = "F:\\���ڽ���test\\ccb_���ؽ��.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/rePrint/SPE002_Response.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/rePrint/SPE002_Response_NST.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
		Document mOutXmlDoc = new RePrint(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("�ɹ�������");
	}
}
