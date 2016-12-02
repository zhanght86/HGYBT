/*
 * ��ԥ���˱���������
 */
package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class YYQTBCancel extends XmlSimpFormat {
	
	private Element cTXLifeRequest = null;
	
	public YYQTBCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into YYQTB.noStd2Std()...");
		
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		
		
		Document mStdXml = 
			YYQTBCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out YYQTBCancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into YYQTBCancel.std2NoStd()...");
		JdomUtil.print(pStdXml);
		Document mNoStdXml = 
			YYQTBCancelOutXsl.newInstance().getCache().transform(pStdXml);
		
		//��֯���ظ����еı���
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransType = new Element("TransType");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		Element mDocumentControlNumber = new Element("DocumentControlNumber");
		Element mAttachmentData = new Element("AttachmentData");
		Element mTransMode = new Element("TransMode");
		Element mRcptId = new Element("RcptId");
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mDocumentControlNumber.setText(cTXLifeRequest.getChildText("DocumentControlNumber"));//��֤����
		mAttachmentData.setText(cTXLifeRequest.getChildText("AttachmentData"));//��������
		mTransMode.setText(cTXLifeRequest.getChildText("TransMode"));//����ģʽ
		mRcptId.setText(cTXLifeRequest.getChildText("RcptId"));
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		mTXLifeResponse.addContent(mDocumentControlNumber);
		mTXLifeResponse.addContent(mAttachmentData);
		mTXLifeResponse.addContent(mTransMode);
		mTXLifeResponse.addContent(mRcptId);
//		mNoStdXml.getRootElement().addContent(mTXLifeResponse);
		
		cLogger.info("Out YYQTBCancel.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

		String mInFilePath = "E:\\���չ�˾\\����\\����\\�������ڱ�ȫ\\����ͨ���������ⲿ�ӿ�\\���ճ���-�ڵ�����(TC=103).xml";
		String mOutFilePath = "e:\\icbc_���ؽ��.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new YYQTBCancel(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new YYQTBCancel(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");
	}
}