///��ʵʱ��������ļ�-���д���������
package com.sinosoft.midplat.newabc.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContBlcDtlDB;
import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class NonReaTimeIssResDocBankDeal extends ServiceImpl
{
	public NonReaTimeIssResDocBankDeal(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc)
	{
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NonReaTimeIssResDocBankDeal.service()...");
		cInXmlDoc = pInXmlDoc;
		JdomUtil.print(cInXmlDoc);

		try
		{
			cTranLogDB = insertTranLog(cInXmlDoc);

			// ����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr)
			{
				throw new MidplatException(tErrorStr);
			}

			JdomUtil.print(cInXmlDoc);

			// ���������ϸ
			saveDetails(cInXmlDoc);

			Element tTranData = new Element(TranData);

			Element tHead = new Element(Head);
			Element tFlag = new Element(Flag);
			tFlag.setText("0");
			Element tDesc = new Element(Desc);
			tDesc.setText("��ʵʱ�������̽��׳ɹ�");
			Element tBody = new Element(Body);

			tTranData.addContent(tHead);
			tTranData.addContent(tBody);

			tHead.addContent(tFlag);
			tHead.addContent(tDesc);

			cOutXmlDoc = new Document(tTranData);

			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // ����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		}
		catch (MidplatException ex)
		{
			cLogger.info(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		catch (Exception ex)
		{
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}

		if (null != cTranLogDB)
		{ // ������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag)); // -1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
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

		cLogger.info("Out NonReaTimeIssResDocBankDeal.service()!");
		return cOutXmlDoc;
	}

	/**
	 * ���������ϸ�����ر������ϸ����(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception
	{
		cLogger.debug("Into NonReaTimeIssResDocBankDeal.saveDetails()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);

		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();

		for (Element tDetailEle : mDetailList)
		{
			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();

			//���κ�
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			
			//������
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			
			//Ͷ������
			tContBlcDtlSchema.setProposalPrtNo(""); // ��Щ���д�
			
			// �˱���������Ϊ24����ʵʱ������������Ϊ25
			tContBlcDtlSchema.setType("25"); 
			
			//��������
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			
			//��ȡ���
			tContBlcDtlSchema.setPrem(tDetailEle.getChildText(Prem));
			
			//������
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			
			tContBlcDtlSchema.setNodeNo("");
			
			//Ͷ��������
			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName")); 
			
			//����������
			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); 
			
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			
			//����Ա
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			//����״̬
			tContBlcDtlSchema.setBak5(tDetailEle.getChildText("State"));
		
			//������Ϣ
			tContBlcDtlSchema.setBak4(tDetailEle.getChildText("Msg"));

			mContBlcDtlSet.add(tContBlcDtlSchema);
		}

		//��ʵʱ����������л��̱���
		cLogger.info("��ʵʱ����������л�����ϸ����(DtlSet)Ϊ��" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			cLogger.error("�����ʵʱ����������ϸʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("�����ʵʱ����������ϸʧ�ܣ�");
		}

		cLogger.debug("Out NonReaTimeIssResDocBankDeal.saveDetails()!");
		return mContBlcDtlSet;
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

		Element mAgentCom = new Element("AgentCom");
		mHeadEle.addContent(mAgentCom);
		// Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");

		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		// String tSqlStr3 = new
		// StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		// .append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		// String sAgentCode = new ExeSQL().getOneValue(tSqlStr3);
		cLogger.info("authority-->" + sAgentCom);
		// cLogger.info("authority-->"+sAgentCode);
		// / if (((""==sAgentCom)||(sAgentCom==null)) &&
		// ((""==sAgentCode)||(sAgentCode==null))){
		if (("" == sAgentCom) || (sAgentCom == null))
		{
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}
		mAgentCom.setText(sAgentCom);
		// mAgentCode.setText(sAgentCode);
		return mInXmlDoc;

	}
}
