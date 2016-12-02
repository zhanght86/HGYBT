//�˱��̳������ļ�-���д���������
package com.sinosoft.midplat.newabc.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
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

public class SurRetrDataDocBankDeal_2 extends ServiceImpl {
	public SurRetrDataDocBankDeal_2(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into SurRetrDataDocBankDeal.service()...");
		cInXmlDoc = pInXmlDoc; 
	   JdomUtil.print(cInXmlDoc);
		
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			
//			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
//				.append(" and TranDate=").append(cTranLogDB.getTranDate())
//				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
//				.append(" and TranCom=").append(cTranLogDB.getTranCom())
//				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
//				.toString();
//			ExeSQL tExeSQL = new ExeSQL();
//			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
//				throw new MidplatException("�ѳɹ������±��б��������ˣ������ظ�������");
//			} else if (tExeSQL.mErrors.needDealError()) {
//				throw new MidplatException("��ѯ��ʷ������Ϣ�쳣��");
//			} 
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			JdomUtil.print(cInXmlDoc);
			
			//���������ϸ
//			saveDetails(cInXmlDoc);
			 
			//add by zhj ������Ȩ�� ��Ӵ���   
			//cInXmlDoc = authority(cInXmlDoc);
			//add by zhj ������Ȩ�� ��Ӵ���end 	 		
			
			//20140911lilu���̽��� ��ȥȡ���ж�sftp�Ļ����ļ���ǰ�û�д��tranlog����״̬Ϊ�ɹ�����ʾ�ɹ��������л����ļ�������Ҫ��������
			//20150323lilu���̽��� ��Ҫ�����ķ��񣬸��ߺ�����Щ��¼���Ĵ���ɹ������д���ʧ�ܣ�Ҫ������ڴ����˱����˽���ļ����������¼�ӽ�ȥ
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NoTakenBlcBankRst).call(cInXmlDoc);
			
			//�ٽ��ף����سɹ�
//			Element tTranData=new Element(TranData);
//			
//			Element tHead=new Element(Head);
//			Element tFlag=new Element(Flag);
//			tFlag.setText("0");
//			Element tDesc=new Element(Desc);
//			tDesc.setText("�̳����̽��׳ɹ�");
//			Element tBody=new Element(Body);
//			
//			tTranData.addContent(tHead);
//			tTranData.addContent(tBody);
//			
//			tHead.addContent(tFlag);
//			tHead.addContent(tDesc);
//			
//			cOutXmlDoc=new Document(tTranData);
			
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
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
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out SurRetrDataDocBankDeal.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * ���������ϸ�����ر������ϸ����(ContBlcDtlSet)
	 */
//	@SuppressWarnings("unchecked")
//	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception {
//		cLogger.debug("Into SurRetrDataDocBankDeal.saveDetails()...");
//		
//		Element mTranDataEle = pXmlDoc.getRootElement();
//		Element mBodyEle = mTranDataEle.getChild(Body);
//		
//		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
//	//	long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
//		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
//		List<Element> mDetailList = mBodyEle.getChildren(Detail);
//		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
//		if (mDetailList.size() != mCount) {
//			throw new MidplatException("���ܱ�������ϸ����������"+ mCount + "!=" + mDetailList.size());
//		}
//		double mSumDtlPrem = 0;
//		for (Element tDetailEle : mDetailList) {
//		//	mSumDtlPrem += Integer.parseInt(tDetailEle.getChildText(Prem));
//			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));
//			
//			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
//			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
//			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
//			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));	//��Щ���д�
//			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
//			tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
//			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
//			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
//			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName"));	//��Щ���д�
//			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); //��Щ���д�
//			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
//			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
//			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
//			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
//			tContBlcDtlSchema.setOperator(CodeDef.SYS);
//			
//			mContBlcDtlSet.add(tContBlcDtlSchema);
//		}
//		if (mSumPrem != mSumDtlPrem) {
//			throw new MidplatException("���ܽ������ϸ�ܽ�����"+ mSumPrem + "!=" + mSumDtlPrem);
//		}
//		
//		/** 
//		 * �����з������Ķ�����ϸ�洢��������ϸ��(ContBlcDtl)��
//		 */
//		cLogger.info("������ϸ����(DtlSet)Ϊ��" + mContBlcDtlSet.size());
//		MMap mSubmitMMap = new MMap();
//		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
//		VData mSubmitVData = new VData();
//		mSubmitVData.add(mSubmitMMap);
//		PubSubmit mPubSubmit = new PubSubmit();
//		if (!mPubSubmit.submitData(mSubmitVData, "")) {
//			cLogger.error("���������ϸʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
//			throw new MidplatException("���������ϸʧ�ܣ�");
//		}
//		
//		cLogger.debug("Out SurRetrDataDocBankDeal.saveDetails()!");
//		return mContBlcDtlSet;
//	}
	
}
