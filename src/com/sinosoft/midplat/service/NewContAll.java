/**
 * ¼���˱�+�շ�ǩ������Թ�������Լ
 */

package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;


import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class NewContAll extends ServiceImpl {
	public NewContAll(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {

		//mStartMillis ����NewContAll��ʼʱ��
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContAll.service()...");
		cInXmlDoc = pInXmlDoc;  
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		Element mBodyEle = mRootEle.getChild(Body); 
		   
		String mTellerNo = mHeadEle.getChildText(TellerNo);  
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContPrtNo = mBodyEle.getChildText(ContPrtNo);
		String mTranCom = mHeadEle.getChildText("TranCom");
		String mSaleChannel = mBodyEle.getChildText("SaleChannel");
		String mMult = mBodyEle.getChild("Risk").getChildText("Mult");
		String mRiskCode = mBodyEle.getChild("Risk").getChildText("RiskCode");
		cLogger.info("Ͷ����(ӡˢ)��Ϊ:"+mProposalPrtNo);
		cLogger.info("��֤ӡˢ��Ϊ:"+mContPrtNo);
		 	
		try {
			cLogger.info("Into NewContAll.service()...-->insertTranLog(cInXmlDoc)������־");	
			JdomUtil.print(cInXmlDoc);
			cTranLogDB = insertTranLog(cInXmlDoc); 
			
		cLogger.info("Into NewContAll.service()...-->authority(cInXmlDoc)������Ȩ�� ��Ӵ���");	
		//add by zhj ������Ȩ�� ��Ӵ���
//		cInXmlDoc = authority(cInXmlDoc); 	
		//add by zhj ������Ȩ�� ��Ӵ���end 
			

		cLogger.info("Into NewContAll.service()...-->authorityCheck.submitData(mHeadEle)����Ȩ��");	
		AuthorityCheck authorityCheck = new AuthorityCheck();
		if(!authorityCheck.submitData(mHeadEle)){
			throw new MidplatException("��������Ȩ�ޣ�");
		} 
		 
		//20150226 ����ǹ�̨��ӮC�ķ���������ʾ
		if(!"0".equals(mSaleChannel)&&"0".equals(mMult)&&"231204".equals(mRiskCode)){
			throw new MidplatException("��ӮC��ƷͶ�����������0��");
		} 
			 
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			int tLockTime = 300;	
			try { 
				cLogger.info("-----���õ�����ʱ��Ϊ:"+Integer.parseInt(cThisBusiConf.getChildText(locktime)));
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			} 
			
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			
			if("0".equals(mSaleChannel)){//��̨
		
				cLogger.info("��ѯ���ӡˢ�ű�����״̬");
	            cLogger.info(tCurCalendar.getTime());
				String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
					.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
					//.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
					.append(" and TranDate=").append(DateUtil.get8Date(tCurCalendar))
					.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
					.toString();
				if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
					throw new MidplatException("�˱����������ڴ����У����Ժ�");
				}
			}
			//JdomUtil.print(cInXmlDoc);
			cLogger.info("Into NewContAll.service()...-->RuleParser().check(cInXmlDoc)У��");
			long mStartRuleParser = System.currentTimeMillis();
			new RuleParser().check(cInXmlDoc);
			long mUsedRuleParser = (System.currentTimeMillis() - mStartRuleParser);
			cLogger.info("----------Timekeeping---------->RuleParser().check(cInXmlDoc)У��ʱ��Ϊ:"+String.valueOf(mUsedRuleParser));
			cTranLogDB.setBak1(String.valueOf(mUsedRuleParser/1000.0));
			//add by zhanghj   
			long mStartYBTDataVerification = System.currentTimeMillis();
			
			String riskCode  = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
				
			if(!("211901".equals(riskCode)||("211902".equals(riskCode)))){
				YBTDataVerification verification = new YBTDataVerification();
				boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);
				if(GradeFlag==false){
					throw new MidplatException("ͬһ����˳������ݶ�֮�Ͳ�����1����ȷ��");
				}
				boolean digitFlag = verification.digitBnfVerification(cInXmlDoc);
				if(digitFlag==false){ 
					throw new MidplatException("����˳�������ţ���ȷ��");
				}  
			}
			
			//20141107���ڹ��в���������־��0���棬1������8�����ն�
			if(mTranCom.equals("01")){
				cTranLogDB.setBak5(mBodyEle.getChildText("SaleChannel"));
			}
			
			long mUsedYBTDataVerification = (System.currentTimeMillis() - mStartYBTDataVerification);
			cLogger.info("----------Timekeeping---------->YBTDataVerificationУ��ʱ��Ϊ:"+String.valueOf(mUsedYBTDataVerification));
			cTranLogDB.setBak2(String.valueOf(mUsedYBTDataVerification/1000.0));
			//add end 			   
						 
			cLogger.info("Into NewContAll.service()...-->�뵥�˱�CallWebsvcAtomSvc");	
			long mStartContInput = System.currentTimeMillis();
		    cOutXmlDoc= new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);
			JdomUtil.print(cOutXmlDoc);
			long mUsedContInput = (System.currentTimeMillis() - mStartContInput);
			cLogger.info("----------Timekeeping---------->�뵥�˱�CallWebsvcAtomSvc����ʱ��Ϊ:"+String.valueOf(mUsedContInput));
			cTranLogDB.setBak3(String.valueOf(mUsedContInput/1000.0));
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
					
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			} 
			 
			/*
			 * �Ƿ��Ƿ�ʵʱ����---FlagΪ2,��0��ʾ��ʵʱ���أ��ǳɹ��ģ���������ǩ���ķ���else������FlagΪ2�ķ�ʵʱ���ġ�
			 */
			if (CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
							
			//�����շ�ǩ���ӿ�  
			Element tBodyEle = new Element(Body);
			tBodyEle.addContent(
					(Element) mBodyEle.getChild(ProposalPrtNo).clone());
			tBodyEle.addContent(
					(Element) mBodyEle.getChild(ContPrtNo).clone());
			tBodyEle.addContent(
					(Element) tOutBodyEle.getChild(ContNo).clone());
			
			Element tTranDataEle = new Element(TranData);
					
			 
			tTranDataEle.addContent(mHeadEle);
			tTranDataEle.addContent(tBodyEle);
			 
			cLogger.info("Into NewContAll.service()...-->�շ�ǩ��CallWebsvcAtomSvc");		
			long mStartContConfirm = System.currentTimeMillis();
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContConfirm).call(new Document(tTranDataEle));
			long mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			cLogger.info("----------Timekeeping---------->�շ�ǩ��CallWebsvcAtomSvc����ʱ��Ϊ:"+String.valueOf(mUsedContConfirm));
			cTranLogDB.setBak4(String.valueOf(mUsedContConfirm/1000.0));
			tOutRootEle = cOutXmlDoc.getRootElement();
			tOutHeadEle = tOutRootEle.getChild(Head); 
			tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			} 
			
			//���Ĳ��汣��ӡˢ�ţ��������Ķ�Ӧֵ����
			//tOutBodyEle.getChild(ContPrtNo).setText(mContPrtNo);
			
			//���Ŀ��ܽ�һ����Ʒ���������ֶ�����Ϊ���գ�����������Ϊһ��һ���������б���Ϊ׼�����Ǻ��ļ�¼?
			/*String tMainRiskCode = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
			List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			int tSize = tRiskList.size();
			for (int i = 0; i < tSize; i++) {
				Element ttRiskEle = tRiskList.get(i);
				ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
				
				if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode))) {
					tRiskList.add(0, tRiskList.remove(i));	//�����յ�������ǰ��
				}
			}*/
			
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
				rollback();	//�ع�ϵͳ����
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}
			 
			//���汣����Ϣ
			ContDB tContDB = getContDB(mTellerNo);
			Date tCurDate = new Date();
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			tContDB.setModifyDate(tContDB.getMakeDate());
			tContDB.setModifyTime(tContDB.getMakeTime());
			if (!tContDB.insert()) {
				cLogger.error("������Ϣ(Cont)���ʧ�ܣ�" + tContDB.mErrors.getFirstError());
				throw new MidplatException("������Ϣ(Cont)���ʧ�ܣ�" + tContDB.mErrors.getFirstError());
			} 
			cTranLogDB.setContNo(tContDB.getContNo());
			cTranLogDB.setManageCom(tContDB.getManageCom());
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
			}else{//��ʵʱ���صı���
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_RenHe, tOutHeadEle.getChildText(Desc));
			}
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
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
		
		cLogger.info("Out NewContAll.service()!");
		return cOutXmlDoc;
	}
	
	private ContDB getContDB(String mTellerNo) {
		cLogger.debug("Into NewContAll.getContDB()...");
		
		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
		Element mInRiskEle = mInBodyEle.getChild(Risk);
		
		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mOutAppntEle = mOutBodyEle.getChild(Appnt);
		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk);	//ǰ���Ѿ��������ˣ���һ���ڵ����������Ϣ
		
		ContDB mContDB = new ContDB();
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
//		mContDB.setProductId(mInRiskEle.getChildText(MainRiskCode));
		mContDB.setTranCom(cTranLogDB.getTranCom());
		mContDB.setNodeNo(cTranLogDB.getNodeNo());
		mContDB.setAgentCom(mOutBodyEle.getChildText(AgentCom));
		mContDB.setAgentComName(mOutBodyEle.getChildText(AgentComName));
		mContDB.setAgentCode(mOutBodyEle.getChildText(AgentCode));
		mContDB.setAgentName(mOutBodyEle.getChildText(AgentName));
		mContDB.setManageCom(mOutBodyEle.getChildText(ComCode));
		mContDB.setAppntNo(mOutAppntEle.getChildText(CustomerNo));
		mContDB.setAppntName(mOutAppntEle.getChildText(Name));
		mContDB.setAppntSex(mOutAppntEle.getChildText(Sex));
		mContDB.setAppntBirthday(mOutAppntEle.getChildText(Birthday));
		mContDB.setAppntIDType(mOutAppntEle.getChildText(IDType));
		mContDB.setAppntIDNo(mOutAppntEle.getChildText(IDNo));
		mContDB.setInsuredNo(mOutInsuredEle.getChildText(CustomerNo));
		mContDB.setInsuredName(mOutInsuredEle.getChildText(Name));
		mContDB.setInsuredSex(mOutInsuredEle.getChildText(Sex));
		mContDB.setInsuredBirthday(mOutInsuredEle.getChildText(Birthday));
		mContDB.setInsuredIDType(mOutInsuredEle.getChildText(IDType));
		mContDB.setInsuredIDNo(mOutInsuredEle.getChildText(IDNo));
		mContDB.setTranDate(cTranLogDB.getTranDate());
		mContDB.setPolApplyDate(mOutMainRiskEle.getChildText(PolApplyDate));
		mContDB.setSignDate(mOutMainRiskEle.getChildText(SignDate));
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		mContDB.setState(AblifeCodeDef.ContState_Sign);
		mContDB.setBak1(mInRiskEle.getChildText(MainRiskCode));
		if(mTellerNo!=""||(!mTellerNo.equals(""))){
			mContDB.setBak2(mTellerNo);
		}
		mContDB.setOperator(CodeDef.SYS);
		
		if("01".equals(mContDB.getTranCom())){//20150204lilu 
			mContDB.setBak10(cTranLogDB.getBak5());
		}
		
		cLogger.debug("Out NewContAll.getContDB()!");
		return mContDB;
	}
	
	private void rollback() {
		cLogger.debug("Into NewContAll.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.removeChild(ServiceId);
		//mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContCancel);
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
//		JdomUtil.print(mInXmlDoc);
		try {
			
			new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCancel).call(mInXmlDoc);
//			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("�ع�����ʧ�ܣ�", ex);
		}
		
		cLogger.debug("Out NewContAll.getContDB()!");
	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * ���� Ȩ�� ���У�鷽��
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);   
		if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode); 
		return mInXmlDoc;
	}
}
