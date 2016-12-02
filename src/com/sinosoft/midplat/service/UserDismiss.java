/**
 * �����ع� - �ع��󣬾ͷ·�õ�����û�з�����
 */

package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.CheckAgentCom;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class UserDismiss extends ServiceImpl {
	public UserDismiss(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into UserDismiss.service()...");
		cInXmlDoc = pInXmlDoc;
		long mUsedContConfirm= 0;
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		//������
		String mContNo = mBodyEle.getChildText(ContNo);
		//�ͻ�����
		String mAppntName = mBodyEle.getChildText("AppntName");
		//�������۱�־
		String mBatType = mBodyEle.getChildText("BatType");
		//����ӡˢ��
		String mGrpNo = mBodyEle.getChildText("GrpNo");
		String mTranNo=mRootEle.getChild(Head).getChildText(TranNo);
		try {
			
			cTranLogDB = insertTranLog(pInXmlDoc);
			     
			cInXmlDoc = CheckAgentCom.authority(cInXmlDoc); 
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300;	//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
			long mStartContConfirm = System.currentTimeMillis();
			//���µ�TranData�ڵ�Ϊ����ʹ�ã��ͺ�������ʱ��ע��
//			Element iTranData=new Element("TranData");
//			Element iHead=new Element("Head");
//			Element iFlag=new Element("Flag");
//			Element iDesc=new Element("Desc");
//			iFlag.setText("0");
//			iDesc.setText("�ͻ���Լ���׳ɹ�");
//			iHead.addContent(iFlag);
//			iHead.addContent(iDesc);
//			iTranData.addContent(iHead);
//			Element BodyEle = new Element("Body");
//			Element iRiskName=new Element("RiskName");
//			Element iCvalidate=new Element("Cvalidate");
//			iRiskName.setText("����������ȫ���գ��ֺ��ͣ�B��");
//			iCvalidate.setText("20120725");
//			BodyEle.addContent(iRiskName);
//			BodyEle.addContent(iCvalidate);
//			iTranData.addContent(BodyEle);
//			cOutXmlDoc=new Document(iTranData);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_UserDismiss).call(cInXmlDoc);
			 mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			cLogger.info("----------Timekeeping---------->���ú��û�����ʱ��Ϊ:"+String.valueOf(mUsedContConfirm));
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			Element tContNo= new Element("ContNo");
			tContNo.setText(mContNo);
			Element tAppntName= new Element("AppntName");
			tAppntName.setText(mAppntName);
			Element tBatType= new Element("BatType");
			tBatType.setText(mBatType);
			Element tGrpNo= new Element("GrpNo");
			tGrpNo.setText(mGrpNo);
			Element tTranNo= new Element(TranNo);
			tTranNo.setText(mTranNo);
			Element tRootEle = cOutXmlDoc.getRootElement();
			Element tBodyEle = tRootEle.getChild(Body);
			tBodyEle.addContent(tContNo);
			tBodyEle.addContent(tAppntName);
			tBodyEle.addContent(tBatType);
			tBodyEle.addContent(tGrpNo);
			tBodyEle.addContent(tTranNo);
			JdomUtil.print(cOutXmlDoc);
			
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			if((ex.getMessage()!=null)){
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			}
		}
		
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element iHeadEle = cInXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setAgentComName(iHeadEle.getChildText("AgentComName"));
			cTranLogDB.setAgentName(iHeadEle.getChildText("AgentName"));
			cTranLogDB.setAgentCodeGrade(iHeadEle.getChildText("AgentCodeGrade"));
			cTranLogDB.setUnitCode(iHeadEle.getChildText("UnitCode"));
			if(iHeadEle.getChildText("ManageCom") == null || "".equals(iHeadEle.getChildText("ManageCom"))){
				cTranLogDB.setManageCom("86");
			}else{
				cTranLogDB.setManageCom(iHeadEle.getChildText("ManageCom"));
			}
			cTranLogDB.setAgentCom(iHeadEle.getChildText("AgentCom"));
			cTranLogDB.setAgentCode(iHeadEle.getChildText("AgentCode"));
			cTranLogDB.setAgentGrade(iHeadEle.getChildText("AgentGrade"));
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			
			cTranLogDB.setOutDoc(tHeadEle.getChildText("OutDoc"));
			cTranLogDB.setInDoc(tHeadEle.getChildText("InDoc"));
			cTranLogDB.setInNoDoc(iHeadEle.getChildText("InNoDoc"));
			cTranLogDB.setOutNoDoc("");
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			cTranLogDB.setBak3(String.valueOf((tCurMillis-mStartMillis)/1000.0));
			cTranLogDB.setBak4(String.valueOf(mUsedContConfirm/1000.0));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out UserDismiss.service()!");
		return cOutXmlDoc;
	}
}
