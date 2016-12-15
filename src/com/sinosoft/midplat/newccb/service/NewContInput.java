package com.sinosoft.midplat.newccb.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;

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
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Schema;

//ʵʱͶ��[¼���Ժ�]
public class NewContInput extends ServiceImpl
{
	public NewContInput(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc)
	{
		long mStartMillis = System.currentTimeMillis();
		//Into NewContInput.service()...
		cLogger.info("Into NewContInput.service()...");
		cInXmlDoc = pInXmlDoc;
		//Java�ĵ�����ģ�͹��߽������׼���Ĵ�ӡ������̨[GBK���룬����3�ո�]
		JdomUtil.print(cInXmlDoc);//[Element:<TranData/>]
		Element mRootEle = cInXmlDoc.getRootElement();//����¼���Ժ������ĸ��ڵ�[Element: <TranData/>]
		Element mBodyEle = mRootEle.getChild(Body);//����¼���Ժ������ı�����[Element: <Body/>]
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);//Ͷ����(ӡˢ)�� 210414131200027
		// String mContPrtNo = mBodyEle.getChildText(ContPrtNo);

		try
		{
			// System.out.println("--------------------------------------------------------------------------------------------------------");
			// JdomUtil.print(cInXmlDoc);
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			// У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300; // Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try
			{
				//��ȡ����ʱ��
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			}
			catch (Exception ex)
			{ // ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��" + tLockTime, ex);
			}
			//��ȡ��ǰ��������
			//java.util.GregorianCalendar[time=1480406848055,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=19,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2016,MONTH=10,WEEK_OF_YEAR=49,WEEK_OF_MONTH=5,DAY_OF_MONTH=29,DAY_OF_YEAR=334,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=5,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=7,SECOND=28,MILLISECOND=55,ZONE_OFFSET=28800000,DST_OFFSET=0]
			Calendar tCurCalendar = Calendar.getInstance();
			//���������������
			tCurCalendar.add(Calendar.SECOND, -tLockTime);//13��200
			//ƴ�ӽṹ����ѯ���[��ѯ������־���н��׽��Ϊ���׹���δ���أ�Ͷ����ӡˢ��Ϊ]
			//select count(1) from TranLog where RCode=-1 and ProposalPrtNo='210414131200027' and MakeDate>=20161129 and MakeTime>=160408
			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL).append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'').append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar)).append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar)).toString();
			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr)))
			{
				throw new MidplatException("�˱����������ڴ����У����Ժ�");
			}

			JdomUtil.print(cInXmlDoc);//��ӡ����¼���Ժ�������

			// lilu20150305У�������ϵ����
			String mApptRelationShip = mBodyEle.getChild("Appnt").getChildText("RelaToInsured");//01
			if (!"".equals(mApptRelationShip))
			{
				if ("--".equals(mApptRelationShip))
				{
					throw new MidplatException("Ͷ�����뱻���˹�ϵ����");
				}
			}
			
			
			List<Element> tBnf = mBodyEle.getChildren("Bnf");//[[Element: <Bnf/>], [Element: <Bnf/>]]
			//									2
			for (int i = 0; i < tBnf.size(); i++)
			{
				String mBnfRelationShip = tBnf.get(i).getChildText("RelaToInsured");
				if (!"".equals(mBnfRelationShip))
				{
					if ("--".equals(mBnfRelationShip))
					{
						throw new MidplatException("�������뱻���˹�ϵ����");
					}
				}
			}
			

			new RuleParser().check(cInXmlDoc);//������¼���Ժ�������

			
			cLogger.info("------�����������У��20140807------");//16:19:44,892 INFO service.NewContInput(106) - ------�����������У��20140807------
			YBTDataVerification verification = new YBTDataVerification();
			//					true
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);//ͬһ����˳����������֤����¼���Ժ�������
			if (GradeFlag == false)
			{
				throw new MidplatException("ͬһ����˳������ݶ�֮�Ͳ�����1����ȷ��");
			}

			try
			{
				//																								����¼���˱�					����¼���Ժ�������
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			//16:35:47,535 INFO common.Log4jPrint(20) - -----------------------------------------------
			System.out.println("-----------------------------------------------");
			//16:37:03,149 INFO service.NewContInput(124) - hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
			cLogger.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{
				String desc = tOutHeadEle.getChildText(Desc);
				cLogger.info("�����������ȣ�" + desc.length());
				if (desc.length() > 90)
				{
					desc = desc.substring(0, 60) + "......)";
					cLogger.info("��������������" + desc);
				}
				throw new MidplatException(desc);
			}

			// ���Ĳ��汣��ӡˢ�ţ��������Ķ�Ӧֵ����
			// tOutBodyEle.getChild(ProposalPrtNo).setText(mContPrtNo);

			// ���Ŀ��ܽ�һ����Ʒ���������ֶ�����Ϊ���գ�����������Ϊһ��һ���������б���Ϊ׼�����Ǻ��ļ�¼
			// �½���û��MainRiskCodeֻ��RiskCode
//			if (cInXmlDoc.getRootElement().getChild("Head").getChildText("TranCom").equals("03"))
//			{
//				String tRiskCode2 = mBodyEle.getChild(Risk).getChildText(RiskCode);
//				List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
//				int tSize = tRiskList.size();
//				for (int i = 0; i < tSize; i++)
//				{
//					Element ttRiskEle = tRiskList.get(i);
//					ttRiskEle.getChild(RiskCode).setText(tRiskCode2);
//
//					if (tRiskCode2.equals(ttRiskEle.getChildText(RiskCode)))
//					{
//						tRiskList.add(0, tRiskList.remove(i)); // �����յ�������ǰ��
//					}
//				}
//			}
//			else
//			{
//				String tMainRiskCode = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
//				List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
//				int tSize = tRiskList.size();
//				for (int i = 0; i < tSize; i++)
//				{
//					Element ttRiskEle = tRiskList.get(i);
//					ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
//
//					if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode)))
//					{
//						tRiskList.add(0, tRiskList.remove(i)); // �����յ�������ǰ��
//					}
//				}
//			}

			// ��ʱ�Զ�ɾ������
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60; // Ĭ�ϳ�ʱ����Ϊ1���ӣ����δ���ó�ʱʱ�䣬��ʹ�ø�ֵ��
			try
			{
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			}
			catch (Exception ex)
			{ // ʹ��Ĭ��ֵ
				cLogger.debug("δ���ó�ʱ������������ʹ��Ĭ��ֵ(s)��" + tTimeOut, ex);
			}
			if (tUseTime > tTimeOut * 1000)
			{
				cLogger.error("����ʱ��UseTime=" + tUseTime / 1000.0 + "s��TimeOut=" + tTimeOut + "s��Ͷ���飺" + mProposalPrtNo);
				rollback(); // �ع�ϵͳ����
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}

			// ���汣����Ϣ
			ContDB tContDB = getContDB();
			Date tCurDate = new Date();
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			tContDB.setModifyDate(tContDB.getMakeDate());
			tContDB.setModifyTime(tContDB.getMakeTime());


			if (!tContDB.insert())
			{
				cLogger.error("������Ϣ(Cont)���ʧ�ܣ�" + tContDB.mErrors.getFirstError());
			}
			// cTranLogDB.setContNo(tContDB.getContNo());
			cTranLogDB.setManageCom(tContDB.getManageCom());
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
		}
		catch (Exception ex)
		{
			//16:39:20,776 ERROR service.NewContInput(217) - ʵʱͶ������ʧ�ܣ�
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);

			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}

		if (null != cTranLogDB)
		{ // ������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		//Out NewContInput.service()!
		cLogger.info("Out NewContInput.service()!");
		return cOutXmlDoc;
	}

	private ContDB getContDB()
	{
		cLogger.debug("Into NewContInput.getContDB()...");

		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
		Element mInRiskEle = mInBodyEle.getChild(Risk);
		Element inAppntEle = mInBodyEle.getChild(Appnt);
		Element inBnf = mInBodyEle.getChild(Bnf);
		

		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mOutAppntEle = mOutBodyEle.getChild(Appnt);
		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk); // ǰ���Ѿ��������ˣ���һ���ڵ����������Ϣ

		ContDB mContDB = new ContDB();
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
		// System.out.println("mOutBodyEle.getChildText(ProposalPrtNo):"+mOutBodyEle.getChildText(ContNo));
		// JdomUtil.print(cOutXmlDoc);
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
		mContDB.setBak1(mOutMainRiskEle.getChildText(MainRiskCode));
		mContDB.setBak2(inAppntEle.getChildText("RelaToInsured2"));// ��������Ͷ���˹�ϵ�����д���
		cLogger.info(mOutBodyEle.getChildText(ProposalPrtNo) + ",��������Ͷ���˹�ϵ,���д���:" + mOutAppntEle.getChildText("RelaToInsured2"));
		
		//���汣���ɷ��˺�
		mContDB.setBak4(mInBodyEle.getChildText(AccNo));
		
		if (inBnf != null)
		{
			mContDB.setBak3(inBnf.getChildText("RelaToInsured3"));// �������뱻���˹�ϵ�����д���
		}
		System.out.println("һ�����кţ�" + mInBodyEle.getChildText("Lv1BrNo") + "  " + "�ײͱ�ţ�" + mInRiskEle.getChildText("AgInsPkgID"));
		mContDB.setBak10(mInBodyEle.getChildText("Lv1BrNo"));
		mContDB.setBak9(mInRiskEle.getChildText("AgInsPkgID"));
		mContDB.setOperator(CodeDef.SYS);

		cLogger.debug("Out NewContInput.getContDB()!");
		return mContDB;
	}
	

	private void rollback()
	{
		cLogger.debug("Into NewContInput.rollback()...");

		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent((Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent((Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent((Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);

		try
		{
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		}
		catch (Exception ex)
		{
			cLogger.error("�ع�����ʧ�ܣ�", ex);
		}

		cLogger.debug("Out NewContInput.rollback()!");
	}

	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 *             create by zhj 2010 11 05 ���� Ȩ�� ���У�鷽��
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException
	{

		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");

		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3);
		cLogger.info("authority-->" + sAgentCom);
		cLogger.info("authority-->" + sAgentCode);
		if ((("" == sAgentCom) || (sAgentCom == null)) && (("" == sAgentCode) || (sAgentCode == null)))
		{
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
	}
}
