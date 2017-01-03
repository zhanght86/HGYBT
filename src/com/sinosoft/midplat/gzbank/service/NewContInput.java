package com.sinosoft.midplat.gzbank.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.gzbank.util.SaveMessage;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class NewContInput extends ServiceImpl {
	public NewContInput(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	/**
	 * ҵ����
	 * @param pInXmlDoc ����XML�ļ�
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) throws Exception {
		//��ʼ������
		long mStartMillis = System.currentTimeMillis();
		//Into NewContInput.service()...
		cLogger.info("Into NewContInput.service()...");
		//����XML�ļ����ڵ�
		Element mRootEle = pInXmlDoc.getRootElement();
		//����XML�ļ�����ͷ
		Element mHeadEle=mRootEle.getChild("Head");
		//����XML�ļ�������
		Element mBodyEle = mRootEle.getChild(Body);
		
		try {
			//������ı���[������ˮ��_��������_������ʱ����.xml]
			StringBuffer mSaveName = new StringBuffer(mHeadEle.getChildText("TranNo"))
			.append('_').append(mHeadEle.getChildText("FuncFlag"))
			.append('_').append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
			.append(".xml");
			//��ǰ���������ļ��õ���Ԫ��[gzbank]�Ľ��׻�������
			String TranCom=cThisBusiConf.getParentElement().getChildText("TranCom");
			//
		    SaveMessage.save(JdomUtil.toBytes(pInXmlDoc),TranCom,"InStd",mSaveName.toString());
		    cLogger.info("������ı�����ϣ�"+mSaveName);
		    pInXmlDoc.getRootElement().getChild("Head").getChild("InNoDoc").setText(mSaveName.toString());
		    
			cTranLogDB = insertTranLog(pInXmlDoc);
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			int tLockTime = 300;	
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			//��ǰ��������δ����3,��δ��ʱ�ı�������
			String sql="select count(1) from TranLog where rcode=3 and contno='"
				+pInXmlDoc.getRootElement().getChild("Body").getChildText("ProposalPrtNo")
				+"' and MakeDate<="+DateUtil.get8Date(tCurCalendar)
				+" and MakeTime<="+DateUtil.get6Time(tCurCalendar); 
			cLogger.info("��������δ����3,��δ��ʱ�ı�������sql:"+sql);
			if ("1".equals(new ExeSQL().getOneValue(sql))) {
				cLogger.info("�˱����������ڴ���!");
				throw new MidplatException("�˱����������ڴ����У����Ժ�!");
			}
			//����У��
//			new RuleParser().gzBankCheck(cInXmlDoc);
			
			Element tRiskCode  = (Element) XPath.selectSingleNode(cInXmlDoc.getRootElement(), "/TranData/Body/Risk/RiskCode");
			if("211901".equals(tRiskCode.getTextTrim())||"211902".equals(tRiskCode.getTextTrim())){//ũ�еĽ���������ָ�ϸ����У�鷽ʽ
				YBTDataVerification verification0 = new YBTDataVerification();
				boolean GradeFlag = verification0.SameGradeTypeBnfVerification(cInXmlDoc);
				if(GradeFlag==false){
					throw new MidplatException("ͬһ˳��ͬһ���͵��������֮�Ͳ�����1����ȷ�ϣ�");
				}
			}else{
			YBTDataVerification verification = new YBTDataVerification();
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);
			if(GradeFlag==false){
				throw new MidplatException("ͬһ����˳������ݶ�֮�Ͳ�����1����ȷ��");
			}
			}
			
			
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);
			System.out.println("-----------------------------------------------");
			cLogger.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//���Ĳ��汣��ӡˢ�ţ��������Ķ�Ӧֵ����
//			tOutBodyEle.getChild(ProposalPrtNo).setText(mContPrtNo);
			
			//���Ŀ��ܽ�һ����Ʒ���������ֶ�����Ϊ���գ�����������Ϊһ��һ���������б���Ϊ׼�����Ǻ��ļ�¼
			String tMainRiskCode = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
			List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			int tSize = tRiskList.size();
			for (int i = 0; i < tSize; i++) {
				Element ttRiskEle = tRiskList.get(i);
				ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
				
				if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode))) {
					tRiskList.add(0, tRiskList.remove(i));	//�����յ�������ǰ��
				}
			}
			
			//��ʱ�Զ�ɾ������
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60;	//Ĭ�ϳ�ʱ����Ϊ1���ӣ����δ���ó�ʱʱ�䣬��ʹ�ø�ֵ��
			try {
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ���ó�ʱ������������ʹ��Ĭ��ֵ(s)��"+tTimeOut, ex);
			}
			if (tUseTime > tTimeOut*1000) {
				//cLogger.error("����ʱ��UseTime=" + tUseTime/1000.0 + "s��TimeOut=" + tTimeOut + "s��Ͷ���飺" + mProposalPrtNo);
				rollback();	//�ع�ϵͳ����
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}
			
			//���汣����Ϣ
			ContDB tContDB = getContDB();
			Date tCurDate = new Date();
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			tContDB.setModifyDate(tContDB.getMakeDate());
			tContDB.setModifyTime(tContDB.getMakeTime());
			
			/**
			 * ũ�г�����Cont������⴦����Ϊǩ����ʱ���жԵ����Ƿ���ڵ�У��
			 * ����������·���������Ҫ��֮ǰ����ĵ�������ɾ��
			 */
//			if("05".equals(mRootEle.getChild(Head).getChildText(TranCom))){//ũ�еĵ���
//				String getContSql = "select 1 from cont where ProposalPrtno = '"+ mProposalPrtNo +"'";
//				if ("1".equals(new ExeSQL().getOneValue(getContSql))) {//��Ͷ����֮ǰ�������Ѿ�����
//					String deleteContSQL = "delete from cont where ProposalPrtno = '"+ mProposalPrtNo +"'";
//					if(new ExeSQL().execUpdateSQL(deleteContSQL)){
//					cLogger.info("ũ�г������Ͷ����������¼����ɾ��֮ǰ�������ݳɹ�:"+mProposalPrtNo);
//					}
//				}
//			}
			
			if (!tContDB.insert()) {
				cLogger.error("������Ϣ(Cont)���ʧ�ܣ�" + tContDB.mErrors.getFirstError());
			}
//			cTranLogDB.setContNo(tContDB.getContNo());
			cTranLogDB.setManageCom(tContDB.getManageCom());
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
			cTranLogDB.setRCode(cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag"));
			cTranLogDB.setRText(cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc"));
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			if (null != cTranLogDB) {
				cTranLogDB.setRCode(CodeDef.RCode_ERROR);
				cTranLogDB.setRText(ex.getMessage());
				
			}
			cOutXmlDoc=MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB) {
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewContInput.service()!");
		return cOutXmlDoc;
	}
	
	private ContDB getContDB() {
		cLogger.debug("Into NewContInput.getContDB()...");
		
		
		
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
//System.out.println("mOutBodyEle.getChildText(ProposalPrtNo):"+mOutBodyEle.getChildText(ContNo));
//JdomUtil.print(cOutXmlDoc);
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
		mContDB.setProductId(mInBodyEle.getChildText(ProductId));
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
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		mContDB.setState(AblifeCodeDef.ContState_Input);
		mContDB.setBak1(mInRiskEle.getChildText(MainRiskCode));
		mContDB.setOperator(CodeDef.SYS);
		
		cLogger.debug("Out NewContInput.getContDB()!");
		return mContDB;
	}
	
	private void rollback() {
		cLogger.debug("Into NewContInput.rollback()...");
		
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
		
		cLogger.debug("Out NewContInput.rollback()!");
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
