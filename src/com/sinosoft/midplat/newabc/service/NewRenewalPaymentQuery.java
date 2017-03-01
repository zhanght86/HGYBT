package com.sinosoft.midplat.newabc.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;

public class NewRenewalPaymentQuery extends ServiceImpl {

	public NewRenewalPaymentQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception{
		cLogger.info("Into NewRenewalPaymentQuery.service()..."); 
		//��ʼ������
		long mStartMillis=System.currentTimeMillis();
		//��Ա��׼���뱨��
		cInXmlDoc=pInXmlDoc;
		//��׼���뱨�ĸ��ڵ�
		Element mRootEle=cInXmlDoc.getRootElement();
		//������ڵ�
		Element mBodyEle=mRootEle.getChild(Body);
		//Ͷ����(ӡˢ)��
		String mProposalPrtNo=mBodyEle.getChildText(ProposalPrtNo);
		//����쳣
		try {
			//���뽻����־
			insertTranLog(cInXmlDoc);
			//����WebServiceԭ�ӷ���[��ѯ(����)����]���ر�׼�������
			cOutXmlDoc=new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_QueryPrem).call(cInXmlDoc);
			//����׼������Ĵ�ӡ������̨�� GBK���룬����3�ո�
			JdomUtil.print(cOutXmlDoc);
			//��׼������ĸ��ڵ�
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			//����ͷ�ڵ�
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			//������ڵ�
			@SuppressWarnings("unused")
			Element mOutBodyEle=mOutRootEle.getChild(Body);
			//���׽��Ϊ����ʧ�ܷ����� �׳��쳣[���׽������]
			if(AblifeCodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag)))
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
			//ʹ��ʱ�������
			long tUseTime=(System.currentTimeMillis()-mStartMillis)/1000;
			//Ĭ�ϳ�ʱ1����
			int timeOut=60;
			//�������ó�ʱʱ��
			timeOut=Integer.parseInt(cThisBusiConf.getChildText(timeout));
			//ʹ��ʱ�������>��ʱʱ������� ����ʱ �ع�ϵͳ���� �׳��쳣[ϵͳ��æ]
			if(tUseTime>timeOut*1000){
				cLogger.info("����ʱ! tUseTime:"+tUseTime/1000+"s; timeOut:"+timeOut+"s; Ͷ����(ӡˢ)��:"+mProposalPrtNo);
				rollback();
				throw new MidplatException("ϵͳ��æ�����Ժ�����!");
			}
		//�����쳣
		} catch (Exception e) {
			//�������ý���ʧ�� ���ݽ��׽���ͽ��׽�����������ɼ򵥵ı�׼������ġ�
			cLogger.info(cThisBusiConf.getChild(name)+"����ʧ�ܣ�",e);
			MidplatUtil.getSimpOutXml(AblifeCodeDef.RCode_ERROR, e.getMessage());
		}
		
		//������־�ǿ�
		if(cTranLogDB!=null){
			//��׼�������ͷ
			Element mHeadEle=cOutXmlDoc.getRootElement().getChild(Head);
			//���ý�����־:���׽������������������ʱ[��:(��ǰ������-��ʼ������)]������޸����ڡ�����޸�ʱ��
			cTranLogDB.setRCode(mHeadEle.getChildText(Flag));
			cTranLogDB.setRText(mHeadEle.getChildText(Desc));
			long mCurrMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mCurrMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurrMillis));
			cTranLogDB.setMakeTime(DateUtil.get6Time(mCurrMillis));
			//���½�����־ʧ�� ��һ��������Ϣ
			if(!cTranLogDB.update()) {
				cLogger.error("���½�����־��Ϣʧ��!"+cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewRenewalPaymentQuery.service()!"); 
		return cOutXmlDoc;
	}
	
	private void rollback(){
		cLogger.debug("Into NewRenewalPaymentQuery.rollback()... ");
		//��׼���뱨�ĸ��ڵ�
		Element tRootEle=cInXmlDoc.getRootElement();
		//������ڵ�
		Element tBody=tRootEle.getChild(Body);
		//����ͷ�ڵ�[��¡]
		Element tHead=(Element) tRootEle.getChild(Head).clone();
		//����ͷ�ڵ����÷���idΪ�����µ��ع���
		tHead.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		//�½�������ڵ�
		Element mBody=new Element(Body);
		//�����׼���뱨��:Ͷ����(ӡˢ)��[��¡]��������ͬӡˢ��[��¡]����׼���������:���յ���[��¡] �ӽڵ�
		mBody.addContent(tBody.getChild(ProposalPrtNo));
		mBody.addContent(tBody.getChildText(ContPrtNo));
		mBody.addContent(cOutXmlDoc.getRootElement().getChild(Body).getChildText(ContNo));
		//�½���׼���뱨�ĸ��ڵ�
		Element mRootEle=new Element(TranData);
		//�����׼���뱨��ͷ�ڵ㡢�½�������ڵ�
		mRootEle.addContent(tHead);
		mRootEle.addContent(mBody);
		//�½���׼���뱨��
		Document mInXmlDoc=new Document(mRootEle);
		//����쳣
		//����WebServiceԭ�ӷ���[�����µ��ع�]
		try {
			new CallWebsvcAtomSvc(tHead.getChildText(ServiceId)).call(mInXmlDoc);
		//�����쳣
		} catch (Exception e) {
			//�ع�ʧ�� �쳣
			cLogger.error("�ع�����ʧ��!",e);
		}
		cLogger.debug("Out NewRenewalPaymentQuery.rollback()!");
	}
	
}
;