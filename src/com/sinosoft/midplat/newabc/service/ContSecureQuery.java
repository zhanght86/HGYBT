package com.sinosoft.midplat.newabc.service;
/**
 * ��ȫ��ѯ
 * 
 */
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

import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;


 
public class ContSecureQuery extends ServiceImpl{

	public ContSecureQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc){
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContSecureQuery.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mContNo = mBodyEle.getChildText(ContNo);
		String busiType = mBodyEle.getChildText("BusiType");
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			
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
				.append(" and ContNo='").append(mContNo).append('\'')
				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
				.toString();
			cLogger.info(new ExeSQL().getOneValue(tSqlStr));
			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
				throw new MidplatException("�˱����������ڴ����У����Ժ�");
			}
			
			JdomUtil.print(cInXmlDoc);
			
			
		//	new RuleParser().check(cInXmlDoc);
			cLogger.info("---------------------------------------------");
			
			if(busiType.equals("07")){//�̳�
				
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NoTakenQuery).call(cInXmlDoc);
			}else if(busiType.equals("09")){//���� ������ʱû��
				
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ManPaymentQuery).call(cInXmlDoc);
			}else if(busiType.equals("10")){//�˱�
				
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_TakenQuery).call(cInXmlDoc);
			}
			
			cLogger.info("---------------------------------------------");
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
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
		cLogger.info("Out ContSecureQuery.service()!");
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}
	
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		JdomUtil.print(pXmlDoc);
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild("Head");
		Element mBodyEle = mTranDataEle.getChild("Body");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("��������" + Thread.currentThread().getName());
		/*����TranLog��9���ֶ�[��׼���뱨��ͷ9���ֶ�]��TranDate��TranTime��ZoneNo��NodeNo��TellerNo[Operator]��TranNo��TranCom��FuncFlag��InNoDoc*/
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		mTranLogDB.setInNoDoc(mHeadEle.getChildText("InNoDoc"));
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		System.out.println("mHeadEle.getChildText" + mHeadEle.getChildText("InNoDoc"));
		if (null != mBodyEle)
		{
			mTranLogDB.setProposalPrtNo(mBodyEle.getChildText(ProposalPrtNo));
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			mTranLogDB.setBak2(mBodyEle.getChildText("OldLogNo"));
			if (null == mTranLogDB.getBak2() || "".equals(mTranLogDB.getBak2()))
			{
				mTranLogDB.setBak2(mBodyEle.getChildText("OldTranNo"));
			}
		}
		//������ΪʵʱͶ�����µ�ȷ�ϣ����׻����������й���������
		//������Ϊ���㽻�׻����µ��б�
		if (("1002".equals(mHeadEle.getChildText(FuncFlag)))// ���к�ũ�е����㽻��
				|| ("1013".equals(mHeadEle.getChildText(FuncFlag)) && String.valueOf(AblifeCodeDef.TranCom_ICBC).endsWith(mHeadEle.getChildText(TranCom))))
		{
			/*����TranLog��5���ֶΣ�AppntName��AppntIDNo��InsuredName��InsuredIDNo��RiskCode[ProductId]*/
			mTranLogDB.setAppntName((mBodyEle.getChild(Appnt).getChildText(Name)));
			mTranLogDB.setAppntIDNo((mBodyEle.getChild(Appnt).getChildText(IDNo)));
			mTranLogDB.setInsuredName((mBodyEle.getChild(Insured).getChildText(Name)));
			mTranLogDB.setInsuredIDNo((mBodyEle.getChild(Insured).getChildText(IDNo)));
			//���׻�������Ϊ�й����С�������
			if (mHeadEle.getChildText(TranCom).equals("03") || mHeadEle.getChildText(TranCom).equals("07"))
			{
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(RiskCode));
			}
			else//���׻�������Ϊ��������
			{
				//���������ִ���
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(MainRiskCode));
			}
		}
		/*����TranLog��7���ֶ�:RCode��UsedTime��Bak1��MakeDate��MakeTime��ModifyDate��ModifyTime*/
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		long mStartMillis = System.currentTimeMillis();
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
	}
	
}
