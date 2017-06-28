package com.sinosoft.midplat.newabc.service;


import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.service.ServiceImpl;

public class NewAccountChange extends ServiceImpl {

	public NewAccountChange(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		cLogger.info("Into Heartbeat.service()...");
		//��Ա��׼���뱨��
		cInXmlDoc = pInXmlDoc;
		
		cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�");
		//��ȡ���������
		cOutXmlDoc = getSimpOutXml(cInXmlDoc);
		
		JdomUtil.print(cOutXmlDoc);
		
		cLogger.info("Out GreenTest.service()!");
		return cOutXmlDoc;
	}
	
	/**
	 * ����pFlag��pMessage�����ɼ򵥵ı�׼���ر��ġ�
	 * @param pFlag ���׽��
	 * @param pMessage ���׽������
	 */
	public static Document getSimpOutXml(Document pInXmlDoc) {
		
		Element mRetCode=new Element("RetCode");
		mRetCode.setText("009999");
		Element mRetMsg=new Element("RetMsg");
		mRetMsg.setText("�뵽���չ�˾��������ҵ��");
		
		Element mHeader=(Element) pInXmlDoc.getRootElement().getChild("Header").clone();
		mHeader.addContent(mRetCode);
		mHeader.addContent(mRetMsg);
		
		Element mReq=pInXmlDoc.getRootElement().getChild("App").getChild("Req");
		Element mRiskCode = (Element) mReq.getChild(RiskCode).clone();
		Element mPolicyNo = (Element) mReq.getChild("PolicyNo").clone();
		
		Element mRet=new Element("Ret");
		mRet.addContent(mRiskCode);
		mRet.addContent(mPolicyNo);
		
		Element mApp=new Element("App");
		mApp.addContent(mRet);
		
		Element mABCB2I=new Element("ABCB2I");
		mABCB2I.addContent(mHeader);
		mABCB2I.addContent(mApp);
		
		//���ؼ򵥱�׼�������
		return new Document(mABCB2I);
	}
	
}
