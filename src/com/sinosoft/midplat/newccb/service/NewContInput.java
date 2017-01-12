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

	/**
	 * ��׼���뱨��ҵ����
	 * @param pInXmlDoc ��׼���뱨��
	 * @return ��׼�������
	 */
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc)
	{
		//��ʼ������
		long mStartMillis = System.currentTimeMillis();
		//Into NewContInput.service()...
		cLogger.info("Into NewContInput.service()...");
		//��ȡ��׼���뱨��
		cInXmlDoc = pInXmlDoc;
		//Java�ĵ�����ģ�͹��߽������׼���Ĵ�ӡ������̨[GBK���룬����3�ո�]
		//��ӡ��׼���뱨��
		JdomUtil.print(cInXmlDoc);//[Element:<TranData/>]
		//��׼���뱨��TranData���ڵ�
		Element mRootEle = cInXmlDoc.getRootElement();//����¼���Ժ������ĸ��ڵ�[Element: <TranData/>]
		//��׼���뱨��Body������ڵ�
		Element mBodyEle = mRootEle.getChild(Body);//����¼���Ժ������ı�����[Element: <Body/>]
		//Body��Ͷ����(ӡˢ)���ӽڵ��ı�
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);//Ͷ����(ӡˢ)�� 210414131200027
		// String mContPrtNo = mBodyEle.getChildText(ContPrtNo);

		try
		{
			// System.out.println("--------------------------------------------------------------------------------------------------------");
			// JdomUtil.print(cInXmlDoc);
			//����׼���뱨�Ĳ��뽻����־��
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
			//Ͷ�����뱻���˹�ϵ�ǿ�
			if (!"".equals(mApptRelationShip))
			{
				//Ͷ�����뱻���˹�ϵΪ--[��ӳ��]
				if ("--".equals(mApptRelationShip))
				{
					//�׳��м�ƽ̨�쳣
					throw new MidplatException("Ͷ�����뱻���˹�ϵ����");
				}
			}
			
			//�������б�
			List<Element> tBnf = mBodyEle.getChildren("Bnf");//[[Element: <Bnf/>], [Element: <Bnf/>]]
			//�����������б�							2
			for (int i = 0; i < tBnf.size(); i++)
			{
				//�������뱻���˹�ϵ
				String mBnfRelationShip = tBnf.get(i).getChildText("RelaToInsured");
				//�������뱻���˹�ϵ�ǿ�
				if (!"".equals(mBnfRelationShip))
				{
					//�������뱻���˹�ϵΪ--[��ӳ��]
					if ("--".equals(mBnfRelationShip))
					{
						//�׳��м�ƽ̨�쳣
						throw new MidplatException("�������뱻���˹�ϵ����");
					}
				}
			}
			
			// ����������๹��ʵ�������׼���뱨��
			new RuleParser().check(cInXmlDoc);//������¼���Ժ�������

			
			cLogger.info("------�����������У��20140807------");//16:19:44,892 INFO service.NewContInput(106) - ------�����������У��20140807------
			//YBT���ݼ���ʵ��
			YBTDataVerification verification = new YBTDataVerification();
			//					true
			//��׼���뱨��ͬһ����˳����������֤�Ƿ�Ϸ�
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);//ͬһ����˳����������֤����¼���Ժ�������
			//ͬһ����˳�������˷Ƿ�
			if (GradeFlag == false)
			{
				//�׳��м�ƽ̨�쳣
				throw new MidplatException("ͬһ����˳������ݶ�֮�Ͳ�����1����ȷ��");
			}
			
			try
			{
				//																								����¼���˱�					����¼���Ժ�������
				//����WebService[����¼���˱�]��׼���뱨��ԭ�ӷ���
				//����WebService������¼���˱�ԭ�ӷ������׼���뱨�ķ��ر�׼�������
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
			//����׼������Ĵ�ӡ������̨[ GBK���룬����3�ո�]
			JdomUtil.print(cOutXmlDoc);
			//��׼������ĸ��ڵ�
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			//��׼������ı���ͷ
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			//��׼������ı�����
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			//���׽��Ϊʧ��
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{
				//��ȡ���׽������
				String desc = tOutHeadEle.getChildText(Desc);
				//�����������ȣ����׽����������
				cLogger.info("�����������ȣ�" + desc.length());
				//���׽���������ȳ���90���ַ�
				if (desc.length() > 90)
				{
					//��ȡǰ60���ַ�ƴ����......
					desc = desc.substring(0, 60) + "......)";
					//�����������������׽������
					cLogger.info("��������������" + desc);
				}
				//�׳��м�ƽ̨�쳣
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
			//ʹ��ʱ��[��ǰʱ�������-��ʼʱ�������]
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			//��ʱʱ��[Ĭ��60��]
			int tTimeOut = 60; // Ĭ�ϳ�ʱ����Ϊ1���ӣ����δ���ó�ʱʱ�䣬��ʹ�ø�ֵ��
			try
			{
				//��ȡmidplat.xml�Ľ���/��ʱʱ��:200[�޵�λ]
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			}
			catch (Exception ex)
			{ 	
				// ʹ��Ĭ��ֵ
				//��midplat.xml�Ľ���/��ʱʱ��ڵ�,������ָ���쳣��ʹ��Ĭ��ֵ:60��
				cLogger.debug("δ���ó�ʱ������������ʹ��Ĭ��ֵ(s)��" + tTimeOut, ex);
			}
			//ʹ��ʱ�䳬����ʱʱ��[��]
			if (tUseTime > tTimeOut * 1000)
			{
				//�׳���ʱ�쳣
				//����ʱ��UseTime=ʹ��ʱ��[��]s��TimeOut=��ʱʱ��s��Ͷ���飺Ͷ����ӡˢ��
				cLogger.error("����ʱ��UseTime=" + tUseTime / 1000.0 + "s��TimeOut=" + tTimeOut + "s��Ͷ���飺" + mProposalPrtNo);
				//�ع�
				rollback(); // �ع�ϵͳ����
				//�׳��м�ƽ̨�쳣
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}
			
			// ���汣����Ϣ
			//��ȡ�������ݿ������ʵ��
			ContDB tContDB = getContDB();
			//��ǰ����
			Date tCurDate = new Date();
			//�����������Ϊ8λ����
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			//�������ʱ��Ϊ6λʱ��
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			//��������޸�����Ϊ�������
			tContDB.setModifyDate(tContDB.getMakeDate());
			//��������޸�ʱ��Ϊ���ʱ��
			tContDB.setModifyTime(tContDB.getMakeTime());
			
			//ִ�б������ݿ��������뷽��ʧ��
			if (!tContDB.insert())
			{
				//������Ϣ(Cont)���ʧ�ܣ�������Ϣ�����ȡ�׸�����
				cLogger.error("������Ϣ(Cont)���ʧ�ܣ�" + tContDB.mErrors.getFirstError());
			}
			// cTranLogDB.setContNo(tContDB.getContNo());
			//���ý�����־���ݿ�������ֶ�
			//�������
			cTranLogDB.setManageCom(tContDB.getManageCom());
			//�������
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			//������
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
		}
		catch (Exception ex)
		{
			//16:39:20,776 ERROR service.NewContInput(217) - ʵʱͶ������ʧ�ܣ�
			//[Element:business/name]
			//[����/������]����ʧ�ܣ�
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			//���ݽ��׽��[ʧ�ܴ���]���쳣��Ϣ�����ɼ򵥵ı�׼���ر���
			//��׼�������
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		//������־���ݿ������ʵ���ǿ�
		if (null != cTranLogDB)
		{ // ������־ʧ��ʱcTranLogDB=null
			//��׼������ı���ͷ
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			//���ý�����־���ݿ�������ֶ�
			//���׽��
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			//�������
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			//��ǰʱ�������
			long tCurMillis = System.currentTimeMillis();
			//�����ʱ[��ǰʱ�������-��ʼʱ�������][s]
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			//����޸�����[8λ����]
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			//����޸�ʱ��[6λʱ��]
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			//���½�����־��¼ʧ��[!false=true]
			//ִ�н�����־���ݿ��������·���ʧ��
			if (!cTranLogDB.update())
			{
				//������־��Ϣʧ�ܣ� ������Ϣ�����ȡ�׸�����
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		//Out NewContInput.service()!
		cLogger.info("Out NewContInput.service()!");
		//���ر�׼�������
		return cOutXmlDoc;
	}

	/**
	 * @Title: getContDB
	 * @Description: ��ȡ�������ݿ������ʵ��
	 * @return �������ݿ������ʵ��
	 * @return ContDB �������ݿ������ʵ��
	 * @throws �쳣
	 */
	private ContDB getContDB()
	{
		cLogger.debug("Into NewContInput.getContDB()...");
		
		//��ȡ��׼���뱨�Ľڵ�
		//������
		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
		//����
		Element mInRiskEle = mInBodyEle.getChild(Risk);
		//Ͷ����
		Element inAppntEle = mInBodyEle.getChild(Appnt);
		//������
		Element inBnf = mInBodyEle.getChild(Bnf);
		
		//��ȡ��׼������Ľڵ�
		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mOutAppntEle = mOutBodyEle.getChild(Appnt);
		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk); // ǰ���Ѿ��������ˣ���һ���ڵ����������Ϣ
		
		//�����������ݿ������ʵ��
		ContDB mContDB = new ContDB();
		//���ñ������ݿ������ʵ���ֶ�ֵΪԪ���ı�����
		//��¼��
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		//��������
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		//������
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
		// System.out.println("mOutBodyEle.getChildText(ProposalPrtNo):"+mOutBodyEle.getChildText(ContNo));
		// JdomUtil.print(cOutXmlDoc);
		//Ͷ����(ӡˢ)��
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
		// ��Ʒ��
		mContDB.setProductId(mInBodyEle.getChildText(ProductId));
		//���׻���
		mContDB.setTranCom(cTranLogDB.getTranCom());
		//��������
		mContDB.setNodeNo(cTranLogDB.getNodeNo());
		//�������
		mContDB.setAgentCom(mOutBodyEle.getChildText(AgentCom));
		//����������� 
		mContDB.setAgentComName(mOutBodyEle.getChildText(AgentComName));
		//������
		mContDB.setAgentCode(mOutBodyEle.getChildText(AgentCode));
		//����������
		mContDB.setAgentName(mOutBodyEle.getChildText(AgentName));
		//������� 
		mContDB.setManageCom(mOutBodyEle.getChildText(ComCode));
		//Ͷ���˿ͻ���
		mContDB.setAppntNo(mOutAppntEle.getChildText(CustomerNo));
		//Ͷ��������
		mContDB.setAppntName(mOutAppntEle.getChildText(Name));
		//Ͷ�����Ա�
		mContDB.setAppntSex(mOutAppntEle.getChildText(Sex));
		//Ͷ���˳�������
		mContDB.setAppntBirthday(mOutAppntEle.getChildText(Birthday));
		//Ͷ����֤������
		mContDB.setAppntIDType(mOutAppntEle.getChildText(IDType));
		//Ͷ����֤������
		mContDB.setAppntIDNo(mOutAppntEle.getChildText(IDNo));
		//�����˿ͻ���
		mContDB.setInsuredNo(mOutInsuredEle.getChildText(CustomerNo));
		//����������
		mContDB.setInsuredName(mOutInsuredEle.getChildText(Name));
		//�������Ա�
		mContDB.setInsuredSex(mOutInsuredEle.getChildText(Sex));
		//�����˳�������
		mContDB.setInsuredBirthday(mOutInsuredEle.getChildText(Birthday));
		//������֤������
		mContDB.setInsuredIDType(mOutInsuredEle.getChildText(IDType));
		//������֤������
		mContDB.setInsuredIDNo(mOutInsuredEle.getChildText(IDNo));
		//��������
		mContDB.setTranDate(cTranLogDB.getTranDate());
		//Ͷ������
		mContDB.setPolApplyDate(mOutMainRiskEle.getChildText(PolApplyDate));
		//����[��]
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		//����
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		//����״̬
		mContDB.setState(AblifeCodeDef.ContState_Input);
		//����1
		mContDB.setBak1(mOutMainRiskEle.getChildText(MainRiskCode));
		//����2
		mContDB.setBak2(inAppntEle.getChildText("RelaToInsured2"));// ��������Ͷ���˹�ϵ�����д���
		//��׼����������ȡͶ����ӡˢ���ı�����,��������Ͷ���˹�ϵ,���д���:Ͷ�����뱻���˹�ϵ2
		cLogger.info(mOutBodyEle.getChildText(ProposalPrtNo) + ",��������Ͷ���˹�ϵ,���д���:" + mOutAppntEle.getChildText("RelaToInsured2"));
		
		//���汣���ɷ��˺�
		//����4
		mContDB.setBak4(mInBodyEle.getChildText(AccNo));
		
		//�����˷ǿ�
		if (inBnf != null)
		{
			//���ñ���3Ϊ�������뱻���˹�ϵ3
			mContDB.setBak3(inBnf.getChildText("RelaToInsured3"));// �������뱻���˹�ϵ�����д���
		}
		//һ�����кţ�[] �ײͱ�ţ�[]
		System.out.println("һ�����кţ�" + mInBodyEle.getChildText("Lv1BrNo") + "  " + "�ײͱ�ţ�" + mInRiskEle.getChildText("AgInsPkgID"));
		//���ñ���10Ϊһ�����к�
		mContDB.setBak10(mInBodyEle.getChildText("Lv1BrNo"));
		//���ñ���9Ϊ�ײͱ��
		mContDB.setBak9(mInRiskEle.getChildText("AgInsPkgID"));
		//���ò���ԱΪsys
		mContDB.setOperator(CodeDef.SYS);
		//Out NewContInput.getContDB()!
		cLogger.debug("Out NewContInput.getContDB()!");
		//���ر������ݿ������ʵ��
		return mContDB;
	}
	
	/**
	 * @Title: rollback
	 * @Description: �ع�
	 * @return void
	 * @throws �쳣
	 */
	private void rollback()
	{
		//Into NewContInput.rollback()...
		cLogger.debug("Into NewContInput.rollback()...");
		
		//��׼���뱨�ĸ��ڵ�
		Element mInRootEle = cInXmlDoc.getRootElement();
		//������
		Element mInBodyEle = mInRootEle.getChild(Body);
		//
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
