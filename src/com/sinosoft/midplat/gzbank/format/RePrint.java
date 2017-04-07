package com.sinosoft.midplat.gzbank.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat {

	private String mOldDanNo = "";
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		mOldDanNo = pNoStdXml.getRootElement().getChildText("OldDanNo");
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		//�ش���µ����ر��Ļ�����ȫһ��������ֱ�ӵ���
		Document mNoStdXml =ContConfirmOutXsl.newInstance().getCache().transform(pStdXml);
		if(pStdXml.getRootElement().getChild("Head").getChildText("Flag").equals("0")){
			mNoStdXml.getRootElement().getChild("OldDanNo").setText(mOldDanNo);
		}
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out RePrint.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception {
	System.out.println("����ʼ��");

	String mInFilePath = "F:\\959391_21_1011_in.xml";
	String mOutFilePath = "F:\\33333.xml";

	InputStream mIs = new FileInputStream(mInFilePath);
	Document mInXmlDoc = JdomUtil.build(mIs);
	mIs.close();

//	Document mOutXmlDoc = new RePrint(null).std2NoStd(mInXmlDoc);
	Document mOutXmlDoc = new RePrint(null).noStd2Std(mInXmlDoc);

	JdomUtil.print(mOutXmlDoc);

	OutputStream mOs = new FileOutputStream(mOutFilePath);
	JdomUtil.output(mOutXmlDoc, mOs);
	mOs.flush();
	mOs.close();

	System.out.println("�ɹ�������");
}
}
