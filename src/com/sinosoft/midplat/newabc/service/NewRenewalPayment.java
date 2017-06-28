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
		long mStartMillis=System.currentTimeMillis();
		cInXmlDoc=pInXmlDoc;
		JdomUtil.print(cInXmlDoc);
		Element mInRootEle=cInXmlDoc.getRootElement();
		@SuppressWarnings("unused")
		Element mInBodyEle=mInRootEle.getChild(Body);
		Element tranNo=mInRootEle.getChild(Head).getChild(TranNo);
		try {
			cTranLogDB=insertTranLog(cInXmlDoc);
			JdomUtil.print(cInXmlDoc);
			cOutXmlDoc=MidplatUtil.getSimpOutXml(AblifeCodeDef.RCode_OK, "���׳ɹ�!");
			JdomUtil.print(cOutXmlDoc);
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			Element mOutBodyEle=mOutRootEle.getChild(Body);
			if(AblifeCodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag))){
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
			}
//			long mUsedMillis=System.currentTimeMillis()-mStartMillis;
//			int mTimeOut=60;
//			try {
//				mTimeOut=Integer.parseInt(cThisBusiConf.getChildText(timeout));
//			} catch (Exception e) {
//				cLogger.error("δ���ó�ʱʱ��,ʹ��Ĭ�ϳ�ʱʱ��:"+mTimeOut+"s",e);
//			}
//			if(mUsedMillis>mTimeOut*1000){
//				cLogger.info("����ʱ!ʹ��ʱ��:"+mUsedMillis/1000.0+"s; ��ʱʱ��:"+mTimeOut+"s; Ͷ����(ӡˢ)��:"+mOutBodyEle.getChildText(ProposalPrtNo)+"; ������ˮ��:"+tranNo);
//				rollback();
//				throw new MidplatException("ϵͳ��æ,���Ժ�����!");
//			}
		} catch (Exception e) {
			cLogger.error(cThisBusiConf.getChild(name)+"����ʧ��!",e);
			MidplatUtil.getSimpOutXml(AblifeCodeDef.RCode_ERROR, e.getMessage());
		}
		
		//������־�ǿ�
		if(cTranLogDB!=null){
			Element mRootEle=cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(mRootEle.getChildText(Flag));
			cTranLogDB.setRText(mRootEle.getChildText(Desc));
			long mCurMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurMillis));
			cTranLogDB.setMakeTime(DateUtil.get6Time(mCurMillis));
				cLogger.error("���½�����־��Ϣʧ��!"+cTranLogDB.mErrors.getFirstError());
		}
		cLogger.info("Out NewRenewalPayment.service()!");
		return cOutXmlDoc;
	}
	
	@SuppressWarnings("unused")
	private void rollback(){
		cLogger.info("Into NewRenewalPayment.rollback()...");
		Element mInRootEle=cInXmlDoc.getRootElement();
		Element mInBodyEle=mInRootEle.getChild(Body);
		Element mInHeadEle=(Element) mInRootEle.getChild(Head).clone();
		mInHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle=new Element(Body);
		mBodyEle.addContent((Element)mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent((Element)mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent((Element)mInBodyEle.getChild(cOutXmlDoc.getRootElement().getChild(Body).getChildText(ContNo)).clone());
		Element mRootEle=new Element(TranData);
		mRootEle.addContent(mInHeadEle);
		mRootEle.addContent(mBodyEle);
		Document mInXmlDoc=new Document(mRootEle);
		try {
			new CallWebsvcAtomSvc(mInHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception e) {
			cLogger.error("�ع�����ʧ��!",e);
		}
		cLogger.info("Out NewRenewalPayment.rollback()!");
	}
	
}
