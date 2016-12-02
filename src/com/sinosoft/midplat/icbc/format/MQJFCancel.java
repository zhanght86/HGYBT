/*
 * �˱���������
 */
package com.sinosoft.midplat.icbc.format;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;

public class MQJFCancel extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public MQJFCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into MQJFCabcel.noStd2Std()...");
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			MQJFCancelInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out MQJFCancel.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into MQJFCancel.std2NoStd()...");
		
		Document mNoStdXml = 
			MQJFCancelOutXsl.newInstance().getCache().transform(pStdXml);
		
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
		Element mFormName = new Element("FormName");
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mDocumentControlNumber.setText(cTXLifeRequest.getChildText("DocumentControlNumber"));//��֤����
		mAttachmentData.setText(cTXLifeRequest.getChildText("AttachmentData"));//��������
		mTransMode.setText(cTXLifeRequest.getChildText("TransMode"));//����ģʽ
		mRcptId.setText(cTXLifeRequest.getChildText("RcptId"));//ƽ��ǰ����ˮ��
		mFormName.setText(cTXLifeRequest.getChildText("FormName"));//��֤����
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		mTXLifeResponse.addContent(mDocumentControlNumber);
		mTXLifeResponse.addContent(mAttachmentData);
		mTXLifeResponse.addContent(mTransMode);
		mTXLifeResponse.addContent(mRcptId);
		mTXLifeResponse.addContent(mFormName);
		
		cLogger.info("Out MQJFCancel.std2NoStd()!");
		return mNoStdXml;
	}
}