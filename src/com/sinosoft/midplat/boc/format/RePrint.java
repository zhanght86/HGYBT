//�����ش���
package com.sinosoft.midplat.boc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.f1j.mvc.el;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class RePrint extends XmlSimpFormat {
	private Element cMain = null;
	
	public RePrint(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RePrint.noStd2Std()...");
		cLogger.info("������������:"+JdomUtil.toStringFmt(pNoStdXml));
		
		cMain =(Element) pNoStdXml.getRootElement().getChild("Main").clone();
		
		Document mStdXml = 
			RePrintInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("������ı���:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out RePrint.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RePrint.std2NoStd()...");
		cLogger.info("���ķ��ر���:"+JdomUtil.toStringFmt(pStdXml));
		
		//�ش���µ����ر��Ļ�����ȫһ��������ֱ�ӵ���
//		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(pStdXml);
		Document mNoStdXml = new ContConfirm(cThisBusiConf).std2NoStd(pStdXml);
		Element mMainEle=mNoStdXml.getRootElement().getChild("Main");
		mMainEle.getChild("InsuId").setText(cMain.getChildText("InsuId"));
		mMainEle.getChild("ZoneNo").setText(cMain.getChildText("ZoneNo"));
		mMainEle.getChild("BrNo").setText(cMain.getChildText("BrNo"));
		mMainEle.getChild("TellerNo").setText(cMain.getChildText("TellerNo"));
		mMainEle.getChild("TransNo").setText(cMain.getChildText("TransNo"));
		mMainEle.getChild("TranCode").setText(cMain.getChildText("TranCode"));
		
//		cLogger.info("���ص���������:"+JdomUtil.toStringFmt(mNoStdXml));
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
