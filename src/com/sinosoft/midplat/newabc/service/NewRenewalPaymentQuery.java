package com.sinosoft.midplat.newabc.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
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
		long mStartMillis=System.currentTimeMillis();
		cInXmlDoc=pInXmlDoc;
		try {
			cTranLogDB=insertTranLog(cInXmlDoc);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "���׳ɹ�");
			JdomUtil.print(cOutXmlDoc);
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			if(AblifeCodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag))){
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
			}
//			long tUseTime=System.currentTimeMillis()-mStartMillis;
//			int timeOut=60;
//			try {
//				timeOut=Integer.parseInt(cThisBusiConf.getChildText(timeout));
//			} catch (Exception e) {
//				cLogger.error("δ���ó�ʱʱ��,����������!ʹ��Ĭ��ֵ:"+timeOut+"s",e);
//			}
//			if(tUseTime>timeOut*1000){
//				cLogger.info("����ʱ! UseTime="+tUseTime/1000.0+"s; TimeOut="+timeOut+"s; Ͷ����(ӡˢ)��:"+mProposalPrtNo);
//				rollback();
//				throw new MidplatException("ϵͳ��æ�����Ժ�����!");
//			}
		} catch (Exception e) {
			cLogger.error(cThisBusiConf.getChild(name)+"����ʧ�ܣ�",e);
			cOutXmlDoc=MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, e.getMessage());
		}
		//������־�ǿ�
		if(cTranLogDB!=null){
			Element mHeadEle=cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(mHeadEle.getChildText(Flag));
			cTranLogDB.setRText(mHeadEle.getChildText(Desc));
			long mCurrMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mCurrMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurrMillis));
			cTranLogDB.setMakeTime(DateUtil.get6Time(mCurrMillis));
			if(!cTranLogDB.update()) {
				cLogger.error("���½�����־��Ϣʧ��!"+cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewRenewalPaymentQuery.service()!"); 
		return cOutXmlDoc;
	}
	
	@SuppressWarnings("unused")
	private void rollback(){
		cLogger.debug("Into NewRenewalPaymentQuery.rollback()... ");
		Element tRootEle=cInXmlDoc.getRootElement();
		Element tBody=tRootEle.getChild(Body);
		Element tHead=(Element) tRootEle.getChild(Head).clone();
		tHead.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBody=new Element(Body);
		mBody.addContent((Element)tBody.getChild(ProposalPrtNo).clone());
		mBody.addContent((Element)tBody.getChild(ContPrtNo).clone());
		mBody.addContent((Element)cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mRootEle=new Element(TranData);
		mRootEle.addContent(tHead);
		mRootEle.addContent(mBody);
		Document mInXmlDoc=new Document(mRootEle);
		try {
			new CallWebsvcAtomSvc(tHead.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception e) {
			cLogger.error("�ع�����ʧ��!",e);
		}
		cLogger.debug("Out NewRenewalPaymentQuery.rollback()!");
	}
	
}
;