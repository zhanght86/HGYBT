package com.sinosoft.midplat.boc.service;

import java.util.Calendar;
import java.util.Date;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
/**
 * �˱�����
 * @author liuzk
 *
 */
public class PolicyCancelConfirmBoc extends ServiceImpl{

	public PolicyCancelConfirmBoc(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc){
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContSecureApply.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mContNo = mBodyEle.getChildText(ContNo);
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300;	//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
//			Calendar tCurCalendar = Calendar.getInstance();
//			tCurCalendar.add(Calendar.SECOND, -tLockTime);
//			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
//				.append(" and ContNo='").append(mContNo).append('\'')
//				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
//				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
//				.toString();
//			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
//				throw new MidplatException("�˱����������ڴ����У����Ժ�");
//			}
			JdomUtil.print(cInXmlDoc);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_RefundFullPayment).call(cInXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		}catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out ContSecureApply.service()!");
		JdomUtil.print(cOutXmlDoc);
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
			mTranLogDB.setProposalPrtNo(mBodyEle.getChildText(ProposalPrtNo));
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			mTranLogDB.setBak2(mBodyEle.getChildText("OldLogNo"));
			if (null==mTranLogDB.getBak2() || "".equals(mTranLogDB.getBak2())) {
				mTranLogDB.setBak2(mBodyEle.getChildText("OldTranNo"));
			}
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
			throw new MidplatException("������־ʧ�ܣ�");
		}
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
	}
}
