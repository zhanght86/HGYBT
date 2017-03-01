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

public class NewRenewalPayment extends ServiceImpl {

	public NewRenewalPayment(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) throws Exception{
		cLogger.info("Into NewRenewalPayment.service()...");
		//��ʼ������
		long mStartMillis=System.currentTimeMillis();
		//��Ա��׼���뱨��
		cInXmlDoc=pInXmlDoc;
		//����׼���뱨�Ĵ�ӡ������̨�� GBK���룬����3�ո�
		JdomUtil.print(cInXmlDoc);
		//��׼���뱨�ĸ��ڵ�
		Element mInRootEle=cInXmlDoc.getRootElement();
		//������ڵ�
		@SuppressWarnings("unused")
		Element mInBodyEle=mInRootEle.getChild(Body);
		//��׼���뱨��ͷ������ˮ�Žڵ�
		Element tranNo=mInRootEle.getChild(Head).getChild(TranNo);
		//����쳣
		try {
			//���뽻����־
			cTranLogDB=insertTranLog(cInXmlDoc);
			// ����׼���뱨�Ĵ�ӡ������̨�� GBK���룬����3�ո�
			JdomUtil.print(cInXmlDoc);
			//����WebServiceԭ�ӷ���[���ڽɷ�]���ر�׼�������
			cOutXmlDoc=new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_RenewalPay).call(cInXmlDoc);
			//����׼������Ĵ�ӡ������̨�� GBK���룬����3�ո�
			JdomUtil.print(cOutXmlDoc);
			//��׼������ĸ��ڵ�
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			//����ͷ�ڵ�
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			//������ڵ�
			Element mOutBodyEle=mOutRootEle.getChild(Body);
			//��׼�������ͷ���׽��Ϊ����ʧ�ܷ�����
			if(AblifeCodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag)))
				//�׳��쳣 ���׽������
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
			
			//ʹ��ʱ�������
			long mUsedMillis=System.currentTimeMillis()-mStartMillis;
			//Ĭ�ϳ�ʱʱ��1����
			int mTimeOut=60;
			//�������ó�ʱʱ��
			try {
				mTimeOut=Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception e) {
				cLogger.error("δ���ó�ʱʱ��,ʹ��Ĭ�ϳ�ʱʱ��:"+mTimeOut+"s",e);
			}
			//ʹ��ʱ�������>��ʱʱ�������
			if(mUsedMillis>mTimeOut*1000){
				//����ʱ
				cLogger.info("����ʱ!ʹ��ʱ��:"+mUsedMillis/1000.0+"s; ��ʱʱ��:"+mTimeOut+"s; Ͷ����(ӡˢ)��:"+mOutBodyEle.getChildText(ProposalPrtNo)+"; ������ˮ��:"+tranNo);
				//�ع�ϵͳ����
				rollback();
			//�׳��쳣 ϵͳ��æ
				throw new MidplatException("ϵͳ��æ,���Ժ�����!");
			}
		//�����쳣
		} catch (Exception e) {
			//�������ý���������ʧ��
			cLogger.error(cThisBusiConf.getChild(name)+"����ʧ��!",e);
			//���ݽ��׽���ͽ��׽�����������ɼ򵥵ı�׼������ġ�
			MidplatUtil.getSimpOutXml(AblifeCodeDef.RCode_ERROR, e.getMessage());
		}
		
		//������־�ǿ�
		if(cTranLogDB!=null){
			//��׼�������ͷ�ڵ�
			Element mRootEle=cOutXmlDoc.getRootElement().getChild(Head);
			//���ý�����־:���׽������������������ʱ[��:(��ǰ������-��ʼ������)]������޸����ڡ�����޸�ʱ��
			cTranLogDB.setRCode(mRootEle.getChildText(Flag));
			cTranLogDB.setRText(mRootEle.getChildText(Desc));
			long mCurMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurMillis));
			cTranLogDB.setMakeTime(DateUtil.get6Time(mCurMillis));
			//���½�����־ʧ�� ��һ��������Ϣ
			if(!cTranLogDB.update())
				cLogger.error("���½�����־��Ϣʧ��!"+cTranLogDB.mErrors.getFirstError());
		}
		cLogger.info("Out NewRenewalPayment.service()!");
		return cOutXmlDoc;
	}
	
	private void rollback(){
		cLogger.info("Into NewRenewalPayment.rollback()...");
		//��׼���뱨�ĸ��ڵ�
		Element mInRootEle=cInXmlDoc.getRootElement();
		//������ڵ�
		Element mInBodyEle=mInRootEle.getChild(Body);
		//����ͷ�ڵ�[��¡]
		Element mInHeadEle=(Element) mInRootEle.getChild(Head).clone();
		//����ͷ�ڵ����÷���idΪ�����µ��ع���
		mInHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		//�½�������ڵ�
		Element mBodyEle=new Element(Body);
		//������ڵ�����׼���뱨����:Ͷ����(ӡˢ)��[��¡]��������ͬӡˢ��[��¡]����׼��������屣�յ���[��¡]
		mBodyEle.addContent((Element)mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent((Element)mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent((Element)mInBodyEle.getChild(cOutXmlDoc.getRootElement().getChild(Body).getChildText(ContNo)).clone());
		//�½���׼���뱨�ĸ��ڵ�
		Element mRootEle=new Element(TranData);
		//�����׼���뱨��ͷ�ڵ㡢�½�������ڵ�
		mRootEle.addContent(mInHeadEle);
		mRootEle.addContent(mBodyEle);
		//�½���׼���뱨��
		Document mInXmlDoc=new Document(mRootEle);
		//����쳣
		try {
			//����WebServiceԭ�ӷ���[�����µ��ع�]
			new CallWebsvcAtomSvc(mInHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
			//�����쳣 
		} catch (Exception e) {
			//�ع�ʧ�� �쳣
			cLogger.error("�ع�����ʧ��!",e);
		}
		cLogger.info("Out NewRenewalPayment.rollback()!");
	}
	
}
