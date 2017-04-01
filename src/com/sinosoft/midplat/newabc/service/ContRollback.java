package com.sinosoft.midplat.newabc.service;


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
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
/**
 * @ClassName: ContRollback
 * @Description: �����ع� - �ع��󣬾ͷ·�õ�����û�з����������ع� - �ع��󣬾ͷ·�õ�����û�з�����
 * @author sinosoft
 * @date 2017-3-31 ����11:37:10
 */
public class ContRollback extends ServiceImpl {
	public ContRollback(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	/**
	 * ҵ����
	 * @param pInXmlDoc ����XML�ĵ� 
	 * @return cOutXmlDoc ���XML�ĵ�
	 */
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContRollback.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mContNo = mBodyEle.getChildText(ContNo);
		String mOldTranNo = mBodyEle.getChildText(OldTranNo);
		
		String mProposalPrtNo = "";
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			/** ���ǩ���Ľ�����ˮ�ŵĺϷ���  */
			String checkTranNoSQL = new StringBuilder(
			"select * from Tranlog where ")
			.append(" FuncFlag=").append("1004")
			.append(" and tranno ='").append(mOldTranNo).append('\'')
			.append(" and MakeDate=").append(cTranLogDB.getMakeDate())
			.append(" and TranCom=").append(cTranLogDB.getTranCom())
			.append(" and ZoneNo='").append(cTranLogDB.getZoneNo())
			.append('\'').append(" and NodeNo='")
			.append(cTranLogDB.getNodeNo()).append('\'').toString();
			cLogger.info(checkTranNoSQL);
			TranLogSet sTranLogSet = new TranLogDB().executeQuery(checkTranNoSQL);
			if (1 != sTranLogSet.size()) {
				throw new MidplatException("���յĽ�����ˮ��" + mOldTranNo + "�����ڣ���ȷ�ϣ�");
			}
			TranLogSchema tTranLogSchema = sTranLogSet.get(1);
			mProposalPrtNo = tTranLogSchema.getProposalPrtNo();
			
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�������δ����
			int tLockTime = 300;	//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
				
			
			
			//���졢ͬһ���㣬�ɹ�������
			String tSqlStr = new StringBuilder("select * from Cont where Type=").append(AblifeCodeDef.ContType_Bank)
//				.append(" and State=").append(AblifeCodeDef.ContState_Sign)// ������ǩ��ʧ�ܺ�ĳ�����Ҳ������ǩ���ɹ���ĳ���
//				.append(" and ContNo='").append(mContNo).append('\'')//���Դ˴��ı����ſ��ܴ��ڣ�Ҳ���ܲ����ڣ�ֻ��ǩ���ɹ��󣬲���������Ӧ�ı����ţ�
				.append(" and ProposalPrtNo = '").append(mProposalPrtNo).append('\'')
				.append(" and MakeDate=").append(cTranLogDB.getMakeDate())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			cLogger.info(tSqlStr);
			ContSet mContSet = new ContDB().executeQuery(tSqlStr);
			if (1 != mContSet.size()) {
				cLogger.info("�ǵ���ͬһ�����������������ܽ��иò�����");//Ϊ�˴���ũ�г��������������������ε����
			}
			ContSchema tContSchema = mContSet.get(1);
			
			mBodyEle.getChild(ProposalPrtNo).setText(mProposalPrtNo);
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContRollback).call(cInXmlDoc);			
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			if(tContSchema!=null){
			//ɾ����������
//			tSqlStr = new StringBuilder("delete from  Cont  where RecordNo=").append(tContSchema.getRecordNo())
//				.toString();
				
				tSqlStr = new StringBuilder("update Cont set State=  "+AblifeCodeDef.ContState_Input)//��Ϊ����ɹ�δǩ��״̬
				.append(", ModifyDate=").append(DateUtil.getCur8Date())
				.append(", ModifyTime=").append(DateUtil.getCur6Time())
				.append(" where RecordNo=").append(tContSchema.getRecordNo())
				.toString();
				
			ExeSQL tExeSQL = new ExeSQL();
			if (!tExeSQL.execUpdateSQL(tSqlStr)) {
				cLogger.error("���±���״̬(Cont)ʧ�ܣ�" + tExeSQL.mErrors.getFirstError());
			}
			}
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			
			cTranLogDB.setContNo(mContNo);
			cTranLogDB.setProposalPrtNo(mProposalPrtNo);
			
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out ContRollback.service()!");
		return cOutXmlDoc;
	}
}