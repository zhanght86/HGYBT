package com.sinosoft.midplat.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;

public class ServiceImpl implements Service, XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	//��ǰ���������ļ�
	protected final Element cThisBusiConf;
	//��׼���뱨��
	protected Document cInXmlDoc;
	//��׼�������
	protected Document cOutXmlDoc;
	//������־���ݿ������
	protected TranLogDB cTranLogDB;
	protected NodeMapSchema cNodeMapSchema;
	
	/**
	 * ��ʼ������ʵ����
	 * @param pThisBusiConf ����Ԫ��
	 */
	public ServiceImpl(Element pThisBusiConf)
	{
		cThisBusiConf = pThisBusiConf;//��Ա���������ļ���ʼ��
	}

	public Document service(Document pInXmlDoc) throws Exception
	{
		//Into ServiceImpl.service()...
		cLogger.info("Into ServiceImpl.service()...");
		
		cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "���׳ɹ���");

		cLogger.info("Out ServiceImpl.service()!");
		return cOutXmlDoc;
	}

	/**
	 * ���뽻����־
	 * @param pXmlDoc XML�ĵ�[��׼���뱨��]
	 * @return ������־����
	 * @throws MidplatException
	 */
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		//����
		JdomUtil.print(pXmlDoc);
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...[����ҵ����ʵ����.���뽻����־...]
		cLogger.debug("Into ServiceImpl.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		//[Element: <TranData/>]
		//[��׼����]����TranData���ڵ�
		Element mTranDataEle = pXmlDoc.getRootElement();
		//[Element: <Head/>]
		//[��׼����]Head����ͷ
		Element mHeadEle = mTranDataEle.getChild("Head");
		//[��׼����]Body������
		//[Element: <Body/>]
		Element mBodyEle = mTranDataEle.getChild("Body");
		//������־���ݿ������ʵ��[���������Ӷ��󣬽�����־�������ݿ��������]
		TranLogDB mTranLogDB = new TranLogDB();//TranLog���ݲ�����ʵ��
		//LogNo:2240
		//������־��Ϊ��ǰ����ִ�е��̶߳������������
		//������־��
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		//��������2240
		//��������10091
		System.out.println("��������" + Thread.currentThread().getName());
		/*���ý�����־���ݿ������ʵ���ֶ�Ϊ���ı�׼���뱨��ͷ�ֶ�ֵ*/
		/*����TranLog��9���ֶ�[��׼���뱨��ͷ9���ֶ�]��TranDate��TranTime��ZoneNo��NodeNo��TellerNo[Operator]��TranNo��TranCom��FuncFlag��InNoDoc*/
		//TranCom:9
		//���ý��׵�λΪ[��׼����]����ͷ���׻���������Ԫ���ı�
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		//ZoneNo:01
		//���õ�������Ϊ[��׼����]����ͷʡ�д�����Ԫ���ı�
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		//NodeNo:060150001222
		//���ý�������Ϊ[��׼����]����ͷ���������Ԫ���ı�
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		//TranNo:2016120800010
		//���ý�����ˮ��Ϊ[��׼����]����ͷ������ˮ����Ԫ���ı�
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		//Operator:5201300002
		//���ò���ԱΪ[��׼����]����ͷ��Ա������Ԫ���ı�
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		//FuncFlag:1012
	   //���ý�������Ϊ[��׼����]����ͷ����������Ԫ���ı�
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		//TranDate:20161108
		//���ý�������Ϊ[��׼����]����ͷ����������Ԫ���ı�
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		//TranTime:130101
		//���ý���ʱ��Ϊ[��׼����]����ͷ����ʱ����Ԫ���ı�
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		//InNoDoc:2240_3_1012_in.xml
		//���ý��뱨��Ϊ[��׼����]����ͷ���뱨����Ԫ���ı�
		mTranLogDB.setInNoDoc(mHeadEle.getChildText("InNoDoc"));
		//trancom:9
		//trancom:13[������׵�λ]
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		//FuncFlag:1012
		//FuncFlag:1012[�����������]
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		//mHeadEle.getChildText10091_3_1012_in.xml
		//mHeadEle.getChildText2240_3_1012_in.xml[������뱨��]
		System.out.println("mHeadEle.getChildText" + mHeadEle.getChildText("InNoDoc"));
		//<Body> != null
		//[��׼����]������ǿ�
		//Body�ǿ�
		if (null != mBodyEle)
		{
			/*����TranLog��4���ֶ�:ProposalPrtNo��ContNo��ContPrtNo��OldLogNo[Bak2]*/
			//ProposalPrtNo:210414132201550
			//����Ͷ����(ӡˢ)��Ϊ[��׼����]������Ͷ����(ӡˢ)����Ԫ���ı�
			mTranLogDB.setProposalPrtNo(mBodyEle.getChildText(ProposalPrtNo));
			//ContNo:null
			//���ñ�����Ϊ[��׼����]�����屣�յ�����Ԫ���ı�
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			//OtherNo:""
			//��������������Ϊ[��׼����]�����屣����ͬӡˢ�� (��֤) ��Ԫ���ı�
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			//Bak2:null
			//���ñ���2Ϊ[��׼����]���������־����Ԫ���ı�
			mTranLogDB.setBak2(mBodyEle.getChildText("OldLogNo"));
			//������־���ݿ������ʵ������2Ϊ�ա����ַ�
			if (null == mTranLogDB.getBak2() || "".equals(mTranLogDB.getBak2()))
			{
				//���ñ���2Ϊ[��׼����]������ɽ�����ˮ����Ԫ���ı�
				mTranLogDB.setBak2(mBodyEle.getChildText("OldTranNo"));
			}
		}
		//������ΪʵʱͶ�����µ�ȷ�ϣ����׻����������й���������
		//������Ϊ���㽻�׻����µ��б�
		if (("1012".equals(mHeadEle.getChildText(FuncFlag)))// ���к�ũ�е����㽻��
				|| ("1013".equals(mHeadEle.getChildText(FuncFlag)) && String.valueOf(AblifeCodeDef.TranCom_ICBC).endsWith(mHeadEle.getChildText(TranCom))))
		{
			/*����TranLog��5���ֶΣ�AppntName��AppntIDNo��InsuredName��InsuredIDNo��RiskCode[ProductId]*/
			//����Ͷ��������
			mTranLogDB.setAppntName((mBodyEle.getChild(Appnt).getChildText(Name)));
			//����Ͷ����֤������
			mTranLogDB.setAppntIDNo((mBodyEle.getChild(Appnt).getChildText(IDNo)));
			//���ñ���������
			mTranLogDB.setInsuredName((mBodyEle.getChild(Insured).getChildText(Name)));
			//���ñ�����֤������
			mTranLogDB.setInsuredIDNo((mBodyEle.getChild(Insured).getChildText(IDNo)));
			//���׻�������Ϊ�й����С�������
			if (mHeadEle.getChildText(TranCom).equals("03") || mHeadEle.getChildText(TranCom).equals("07"))
			{
				//�������ִ���
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(RiskCode));
			}
			else//���׻�������Ϊ��������
			{
				//���������ִ���
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(MainRiskCode));
			}
		}
		/*����TranLog��7���ֶ�:RCode��UsedTime��Bak1��MakeDate��MakeTime��ModifyDate��ModifyTime*/
		//���ý��׽��Ϊ���׹���δ���� 
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		//���÷����ʱΪ-1
		mTranLogDB.setUsedTime(-1);
		//���ñ���1Ϊ���ж�IP
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		//��ȡ��ǰ���ڶ���
		Date mCurDate = new Date();
		//�����������[��ǰ���ڶ���ǰ��λ
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		//�������ʱ��[��ǰ���ڶ������λ]
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		//��������޸�����Ϊ�������
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		//��������޸�ʱ��Ϊ���ʱ��
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		//��ȡ��ǰ������
		long mStartMillis = System.currentTimeMillis();
		//������־���ݿ��������ʧ��
		//���뽻����־���󵽽�����־��
		//!false=true����ʧ��
		if (!mTranLogDB.insert())
		{
			//ORA-00001: Υ��ΨһԼ������ (YBT.PK_TRANLOG) 
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			/*com.sinosoft.midplat.exception.MidplatException: ������־ʧ�ܣ�
				at com.sinosoft.midplat.service.ServiceImpl.insertTranLog(ServiceImpl.java:139)
				at com.sinosoft.midplat.newccb.service.NewContInput.service(NewContInput.java:57)
				at com.sinosoft.midplat.Ybt4Socket.run(Ybt4Socket.java:124)*/
			throw new MidplatException("������־ʧ�ܣ�");
		}
		//Out ServiceImpl.insertTranLog()!
		//Out ServiceImpl.insertTranLog()!
		cLogger.debug("Out ServiceImpl.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}
}
