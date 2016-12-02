package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;

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
import com.sinosoft.utility.ExeSQL;

public class ContCancel extends ServiceImpl {
	public ContCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("null")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContCancel.service()...");
		cInXmlDoc = pInXmlDoc;
	//	JdomUtil.print(cInXmlDoc);
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		Element mBodyEle = mRootEle.getChild(Body);
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContNo = mBodyEle.getChildText(ContNo);
		
		
		/**
		 * ���еĸ���ԭ����������ˮ�ŵ���־���ȡ��Ͷ������,�ٽ�����ش���
		 */
		if("03".equals(mHeadEle.getChildText(TranCom)) || "09".equals(mHeadEle.getChildText(TranCom))){
		String oldTransNo = mBodyEle.getChildText("TransNo");
		Element ProposalPrtNoEle = new Element("ProposalPrtNo");
		if(oldTransNo!=null&&!oldTransNo.equals("")){
			
			String getoldoldTransnoSQL = "select bak2 from tranlog where TranNo = '"+oldTransNo+"' and contno = '"+
			mContNo+"'";
			String oldoldTransno= new ExeSQL().getOneValue(getoldoldTransnoSQL);
			String getProposalPrtNoSQL ="select ProposalPrtNo from tranlog where tranno = '"+oldoldTransno+"'";
			mProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
			ProposalPrtNoEle.setText(mProposalPrtNo);
		}else{
			String getproposalprtno ="select proposalprtno from cont where contno ='"+mContNo+"' and state='2'";
			cLogger.info(getproposalprtno);
			mProposalPrtNo =   new ExeSQL().getOneValue(getproposalprtno);
			ProposalPrtNoEle.setText(mProposalPrtNo);
		}
		mBodyEle.addContent(ProposalPrtNoEle);
		}
		
		JdomUtil.print(pInXmlDoc);

		try { 
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			cLogger.info("Into ContCancel.service()...-->authorityCheck.submitData(mHeadEle)����Ȩ��");	
			AuthorityCheck authorityCheck = new AuthorityCheck();
			if(!authorityCheck.submitData(mHeadEle)){ 
				throw new MidplatException("��������Ȩ�ޣ�");
			} 
			
			
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300;	//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
				.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
				.toString();
			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
				throw new MidplatException("�˱����������ڴ����У����Ժ�");
			}
			
			//���졢ͬһ���㣬�ɹ�������
			tSqlStr = new StringBuilder("select * from Cont where Type=").append(AblifeCodeDef.ContType_Bank)
				.append(" and State=").append(AblifeCodeDef.ContState_Sign)
				//.append(" and ContNo='").append(mContNo).append('\'')
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
			

			//add by zhj ������Ȩ�� ��Ӵ���   
			//cInXmlDoc = authority(cInXmlDoc);
			//add by zhj ������Ȩ�� ��Ӵ���end 			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCancel).call(cInXmlDoc);
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//��Ϊ����״̬
			Date tCurDate = new Date();
			tSqlStr = new StringBuilder("update Cont set State=").append(AblifeCodeDef.ContState_Cancel)
				.append(", ModifyDate=").append(DateUtil.get8Date(tCurDate))
				.append(", ModifyTime=").append(DateUtil.get6Time(tCurDate))
				.append(" where RecordNo=").append(tContSchema.getRecordNo())
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if (!tExeSQL.execUpdateSQL(tSqlStr)) {
				cLogger.error("���±���״̬(Cont)ʧ�ܣ�" + tExeSQL.mErrors.getFirstError());
			}
		}
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
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
		
		cLogger.info("Out ContCancel.service()!");
		return cOutXmlDoc;
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
