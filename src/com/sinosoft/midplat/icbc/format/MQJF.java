/*
 * �˱�����
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

public class MQJF extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public MQJF(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into MQJF.noStd2Std()...");
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			MQJFInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out MQJF.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into MQJF.std2NoStd()...");
		
		Document mNoStdXml = 
			MQJFOutXsl.newInstance().getCache().transform(pStdXml);
		
		//��֯���ظ����еı���
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransType = new Element("TransType");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		Element mFormName = new Element("FormName");
		Element mDocumentControlNumber = new Element("DocumentControlNumber");
		Element mAttachmentData = new Element("AttachmentData");
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));//������
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mFormName.setText(cTXLifeRequest.getChildText("FormName"));//��֤����
		mDocumentControlNumber.setText(cTXLifeRequest.getChildText("DocumentControlNumber"));//������
		mAttachmentData.setText(cTXLifeRequest.getChildText("AttachmentData"));//��������
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		mTXLifeResponse.addContent(mFormName);
		mTXLifeResponse.addContent(mDocumentControlNumber);
		mTXLifeResponse.addContent(mAttachmentData);

		
		
		cLogger.info("Out MQJF.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

		String mInFilePath = "E:\\���չ�˾\\����\\����\\�������ڱ�ȫ\\����\\��ԥ�ڳ������˱�����(TC=105).xml";
		String mOutFilePath = "e:\\icbc_���ؽ��.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new MQJF(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new MQJF(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");
	}
}