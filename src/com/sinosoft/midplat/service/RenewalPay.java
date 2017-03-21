package com.sinosoft.midplat.service;

import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;

public class RenewalPay extends ServiceImpl {
	public RenewalPay(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into RenewalPay.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		@SuppressWarnings("unused")
		Element mHeadEle = mRootEle.getChild(Head);
		Element mBodyEle = mRootEle.getChild(Body);
		String mProposalPrtNo=mBodyEle.getChildText(ProposalPrtNo);
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			cOutXmlDoc=new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_RenewalPay).call(cInXmlDoc);
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			long tUsedTime=System.currentTimeMillis()-mStartMillis;
			int tTimeOut=60;
			try {
				tTimeOut=Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception e) {
				cLogger.error("未配置超时时间,或配置有误,使用默认超时时间:"+tTimeOut+"s",e);
			}
			if(tUsedTime>tTimeOut*1000){
				cLogger.error("处理超时,tUsedTime="+tUsedTime/1000.0+"s; tTimeOut="+tTimeOut+"s; 投保单号="+mProposalPrtNo);
				rollback();
				throw new MidplatException("系统异常，请稍后再试!");
			}
			if(CodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag))){
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
			}
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB) {
			Element tMainaEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tMainaEle.getChildText(Flag));
			cTranLogDB.setRText(tMainaEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out RenewalPay.service()!");
		return cOutXmlDoc;
	}

	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException {
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		if (null != mBodyEle) {
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
		}
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert()) {
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
	}
	
	private void rollback() {
		cLogger.info("Into RenewalPay.rollback()...");
		Element tRootEle=cInXmlDoc.getRootElement();
		Element tHeadEle=(Element) tRootEle.getChild(Head).clone();
		Element tBodyEle=tRootEle.getChild(Body);
		tHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle=new Element(Body);
		mBodyEle.addContent((Element)tBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent((Element)tBodyEle.getChild(ContNo).clone());
		Element mRootEle=new Element(TranData);
		mRootEle.addContent(tHeadEle);
		mRootEle.addContent(mBodyEle);
		Document mInXmlDoc=new Document(mRootEle);
		try {
			new CallWebsvcAtomSvc(tHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception e) {
			cLogger.error("回滚数据异常!",e);
		}
		cLogger.info("Out RenewalPay.rollback()!");
	}
	
}
