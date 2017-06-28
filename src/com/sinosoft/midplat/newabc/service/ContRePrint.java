package com.sinosoft.midplat.newabc.service;

import java.util.Calendar;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.AuthorityCheck;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class ContRePrint extends ServiceImpl {
	public ContRePrint(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) throws MidplatException {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContRePrint.service()...");
		cInXmlDoc = pInXmlDoc;
		 
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();

		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContPrtNo = mBodyEle.getChildText(ContPrtNo);
		String mContNo = mBodyEle.getChildText(ContNo);

		
		try {
			System.out.println(mBodyEle.getChildText("ContPrtNo"));
			String mSqlStr = "select ContNo from Cont where Type=0 and ProposalPrtNo ='" + mBodyEle.getChildText("ProposalPrtNo") + "' order by makedate,maketime desc ";
			SSRS mSSRS = new ExeSQL().execSQL(mSqlStr);
			if (1 != mSSRS.MaxRow) {
				throw new MidplatException("δ�鵽��ӦͶ�����ŵı�������˶�Ͷ�����ţ�");
			}
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			cLogger.info("Into ContRePrint.service()...-->authorityCheck.submitData(mHeadEle)����Ȩ��");	
			AuthorityCheck authorityCheck = new AuthorityCheck();
			if(!authorityCheck.submitData(mHeadEle)){ 
				throw new MidplatException("��������Ȩ�ޣ�");
			} 
			
			mBodyEle.getChild(ContNo).setText(mSSRS.GetText(1, 1));
			
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300;	//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			String tSqlStr=null;
//			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
//			/*.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')*/
//				.append(" and ContNo='").append(mContNo).append('\'')
//				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar)) 
//				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
//				.toString();
//			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
//				throw new MidplatException("�˱����������ڴ����У����Ժ�");
//			}
			
			
			
			//���졢ͬһ���㣬�ɹ�������
			tSqlStr = new StringBuilder("select * from Cont where Type=").append(AblifeCodeDef.ContType_Bank)
				.append(" and State=").append(AblifeCodeDef.ContState_Sign)
				.append(" and ContNo='").append(mContNo).append('\'')
				.append(" and MakeDate=").append(cTranLogDB.getMakeDate())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			cLogger.info(tSqlStr);
			ContSet mContSet = new ContDB().executeQuery(tSqlStr);
			if (1 != mContSet.size()) {
				throw new MidplatException("�ǵ����������������ܽ��иò�����");
			}
			ContSchema tContSchema = mContSet.get(1);
			
			//�ж��Ƿ��ط�20141010
			cLogger.info("�ж��Ƿ��ط���1Ϊ�ط����ط�Body����ResendFlag�ڵ�");
			String tResendSql = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_OK)
			.append(" and contno='").append(mContNo).append('\'')
			.append(" and funcflag='").append("1011").append('\'')
			.append(" and MakeDate =").append(DateUtil.get8Date(tCurCalendar))
			.toString();
			if ("1".equals(new ExeSQL().getOneValue(tResendSql))) {
				Element tResendFlag=new ElementLis("ResendFlag", "1",mBodyEle);
				cLogger.info("�ط���־��"+tResendFlag.getText());
			}
			
			//add by zhj ������Ȩ�� ��Ӵ���   
			//cInXmlDoc = authority(cInXmlDoc);
			//add by zhj ������Ȩ�� ��Ӵ���end 
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContRePrint).call(cInXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);  
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//���Ĳ��汣��ӡˢ�ţ��������Ķ�Ӧֵ���� 
			//tOutBodyEle.getChild(ContPrtNo).setText(mContPrtNo);
			
			//���Ŀ��ܽ�һ����Ʒ���������ֶ�����Ϊ���գ�����������Ϊһ��һ���������б���Ϊ׼�����Ǻ��ļ�¼
			String tMainRiskCode = tContSchema.getBak1();
			List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			int tSize = tRiskList.size();
			for (int i = 0; i < tSize; i++) {
				Element ttRiskEle = tRiskList.get(i);
				ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
				
				if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode))) {
					tRiskList.add(0, tRiskList.remove(i));	//�����յ�������ǰ��
				}
			}
		} 
		catch (Exception ex) {
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
		
		cLogger.info("Out ContRePrint.service()!");
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * ���� Ȩ�� ���У�鷽��
	 */
	@SuppressWarnings("unused")
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
