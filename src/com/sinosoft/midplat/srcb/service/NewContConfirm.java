package com.sinosoft.midplat.srcb.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.schema.TranLogSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.lis.vschema.TranLogSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class NewContConfirm extends ServiceImpl {
	public NewContConfirm(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContConfirm.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContPrtNo = mBodyEle.getChildText(ContPrtNo);
		
		try {
			//JdomUtil.print(pInXmlDoc);
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			
			cLogger.info("Into NewContInput.service()...-->authority(cInXmlDoc)������Ȩ�� ��Ӵ���");	
			
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300;	//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
//			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=-1")
//				.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
//				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
//				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
//				.toString();
//			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
//				throw new MidplatException("�˱����������ڴ����У����Ժ�");
//			}
			
//			JdomUtil.print(cInXmlDoc);
			
			
			//���졢ͬһ���㣬�ɹ�¼����
			String tSqlStr = new StringBuilder("select * from Cont where Type=").append(AblifeCodeDef.ContType_Bank)
				.append(" and State=").append(AblifeCodeDef.ContState_Input)
				.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
				.append(" and MakeDate=").append(cTranLogDB.getMakeDate())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			cLogger.info(tSqlStr);
			ContSet mContSet = new ContDB().executeQuery(tSqlStr);
			if (1 != mContSet.size()) {
				throw new MidplatException("�ǵ���ͬһ�����������������ܽ��иò�����");
			}
			ContSchema tContSchema = mContSet.get(1);
			
			String tSqlState = new StringBuilder("select state from Cont where ProposalPrtNo=").append(mProposalPrtNo).toString();
			cLogger.info(tSqlState);
			
			if ("2".equals(new ExeSQL().getOneValue(tSqlState))) {
				throw new MidplatException("�������շ�ǩ���������ظ��ò�����");
			}
			
//			//�ͺ���������ʱ��ſ� begin
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContConfirm).call(cInXmlDoc);
			//�ͺ���������ʱ��ſ� end
			
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			//modified by chengqi 20121129
			String mContNo = tOutBodyEle.getChildText(ContNo);
			cTranLogDB.setContNo(mContNo);			
			//end
			
			//��ʱ�Զ�ɾ������
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60;	//Ĭ�ϳ�ʱ����Ϊ1���ӣ����δ���ó�ʱʱ�䣬��ʹ�ø�ֵ��
			try {
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ���ó�ʱ������������ʹ��Ĭ��ֵ(s)��"+tTimeOut, ex);
			}
			if (tUseTime > tTimeOut*1000) {
				cLogger.error("����ʱ��UseTime=" + tUseTime/1000.0 + "s��TimeOut=" + tTimeOut + "s��Ͷ���飺" + mProposalPrtNo);
			//	rollback();	//�ع�ϵͳ����
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}
			
			//���±���״̬
			Element tOutMainRiskEle = tOutBodyEle.getChild(Risk);
			Date tCurDate = new Date();
			tSqlStr = new StringBuilder("update Cont set State=").append(AblifeCodeDef.ContState_Sign)
				.append(", ContNo=").append(mContNo)
				.append(", SignDate=").append(tOutMainRiskEle.getChildText(SignDate))
				.append(", ModifyDate=").append(DateUtil.get8Date(tCurDate))
				.append(", ModifyTime=").append(DateUtil.get6Time(tCurDate))
				.append(" where RecordNo=").append(tContSchema.getRecordNo())
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if (!tExeSQL.execUpdateSQL(tSqlStr)) {
				cLogger.error("���±���״̬(Cont)ʧ�ܣ�" + tExeSQL.mErrors.getFirstError());
			}
		} catch (Exception ex) {
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
		
		cLogger.info("Out NewContConfirm.service()!");
		return cOutXmlDoc;
	}
	
	private void rollback() {
		cLogger.debug("Into NewContConfirm.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent(
				(Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);
		
		try {
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("�ع�����ʧ�ܣ�", ex);
		}
		
		cLogger.debug("Out NewContConfirm.rollback()!");
	}
}
