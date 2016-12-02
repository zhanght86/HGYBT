package com.sinosoft.midplat.newccb.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.schema.TranLogSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.lis.vschema.TranLogSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class NewContConfirm extends ServiceImpl {

	public NewContConfirm(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContConfirm.service()...");
		cInXmlDoc = pInXmlDoc;

		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContPrtNo = mBodyEle.getChildText(ContPrtNo);

		try {
			cTranLogDB = insertTranLog(pInXmlDoc);

			cLogger.info("Into NewContInput.service()...-->authority(cInXmlDoc)������Ȩ�� ��Ӵ���");

			// У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300; // Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf
						.getChildText(locktime));
			} catch (Exception ex) { // ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��" + tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			// String tSqlStr = new
			// StringBuilder("select count(1) from TranLog where RCode=-1")
			// .append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
			// .append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
			// .append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
			// .toString();
			// if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
			// throw new MidplatException("�˱����������ڴ����У����Ժ�");
			// }

			// ���д˴����⴦�����ݱ�������Ľ�����ˮ�ţ���������־���в�ѯ��Ͷ�����ţ�mProposalPrtNo)
			if ("13".equals(mRootEle.getChild(Head).getChildText(TranCom))) {// ���еĽ���
				String getProposalPrtNoSQL = "select ProposalPrtNo from TranLog where TranDate = "
						+ DateUtil.getCur8Date()
						+ " and TranNo = '"
						+ mBodyEle.getChildText(TranNo) + "' and RCode=0 ";

				cLogger.info(getProposalPrtNoSQL);

				// mProposalPrtNo = new
				// ExeSQL().getOneValue(getProposalPrtNoSQL);
				// Element tProposalPrtNo = mBodyEle.getChild(ProposalPrtNo);
				// tProposalPrtNo.setText(mProposalPrtNo);

				// ��ȡ¼���˱��ı���ӡˢ��
				String getoldPrtNo = "select otherNo from TranLog where TranDate = "
						+ DateUtil.getCur8Date()
						+ " and TranNo = '"
						+ mBodyEle.getChildText(TranNo) + "' and RCode=0 ";
				cLogger.info(getoldPrtNo);
				String oldPrtNo = new ExeSQL().getOneValue(getoldPrtNo);

				cTranLogDB.setOtherNo(oldPrtNo);
				// cTranLogDB.setProposalPrtNo(mProposalPrtNo);

				// �Ѻ˱��ı���ӡˢ�Ÿ����Ĵ���ȥ
				Element tContPrtNo = mBodyEle.getChild(ContPrtNo);
				tContPrtNo.setText(oldPrtNo);

			}

			// //ũ�д�����ӡˢ�Ÿ�����
			// if("05".equals(mRootEle.getChild(Head).getChildText(TranCom))){//ũ�еĽ���
			// String tSqlStr =
			// "select * from TranLog where ProposalPrtNo = '"+mProposalPrtNo+"' "
			// +
			// " and funcflag =1012 and trancom=5 and rcode=0 order by logno desc";
			// cLogger.info(tSqlStr);
			// TranLogSet mTranLogSet = new TranLogDB().executeQuery(tSqlStr);
			// cLogger.info("mTranLogSet.size():"+mTranLogSet.size());
			// if(mTranLogSet.size()!=0){
			// TranLogSchema tTranLogSchema = mTranLogSet.get(1);
			//
			// String prtNo = tTranLogSchema.getOtherNo();
			// cTranLogDB.setOtherNo(prtNo);
			//
			// Element tContPrtNo = mBodyEle.getChild(ContPrtNo);
			// tContPrtNo.setText(prtNo);
			//
			// cLogger.info("ȡ���µı���ӡˢ�Ÿ����Ĵ���ȥ:"+prtNo);
			// }
			// }

			// JdomUtil.print(cInXmlDoc);

			// ���졢ͬһ���㣬�ɹ�¼����
			String tSqlStr = new StringBuilder("select * from Cont where Type=")
					.append(AblifeCodeDef.ContType_Bank).append(" and State=")
					.append(AblifeCodeDef.ContState_Input)
					.append(" and ProposalPrtNo='").append(mProposalPrtNo)
					.append('\'').append(" and MakeDate=")
					.append(cTranLogDB.getMakeDate()).append(" and TranCom=")
					.append(cTranLogDB.getTranCom()).append(" and NodeNo='")
					.append(cTranLogDB.getNodeNo()).append('\'').toString();
			cLogger.info(tSqlStr);
			ContSet mContSet = new ContDB().executeQuery(tSqlStr);
			// if (1 != mContSet.size()) {
			// throw new MidplatException("�ǵ���ͬһ�����������������ܽ��иò�����");
			// }
			ContSchema tContSchema = mContSet.get(1);

			// //�ͺ���������ʱ��ſ� begin
			cOutXmlDoc = new CallWebsvcAtomSvc(
					AblifeCodeDef.SID_Bank_ContConfirm).call(cInXmlDoc);
			// �ͺ���������ʱ��ſ� end

			// �ͺ���������ʱ��ע�ӵ�begin
			// String mInStr = "G:/test/11019_55_1_outSvc.xml";
			// InputStream mIs = null;
			// try {
			// mIs = new FileInputStream(mInStr);
			// } catch (FileNotFoundException e) {
			// e.printStackTrace();
			// }
			// byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
			// cOutXmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
			// �ͺ���������ʱ��ע�ӵ�end

			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle
					.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			// modified by chengqi 20121129
			String mContNo = tOutBodyEle.getChildText(ContNo);
			cTranLogDB.setContNo(mContNo);
			// end
			// ���Ĳ��汣��ӡˢ�ţ��������Ķ�Ӧֵ����---���ı����ˣ���˵�������Ͷ�����ţ�����...
			// ������ǰ˭ά���ģ�������Ͳ������£���ô���鰡��
			// tOutBodyEle.getChild(ProposalPrtNo).setText(mContPrtNo);

			// ���Ŀ��ܽ�һ����Ʒ���������ֶ�����Ϊ���գ�����������Ϊһ��һ���������б���Ϊ׼�����Ǻ��ļ�¼
			// �½���û��MainRiskCodeֻ��RiskCode
			// if
			// (cInXmlDoc.getRootElement().getChild("Head").getChildText("TranCom").equals("03"))
			// {
			// String tRiskCode =
			// tOutBodyEle.getChild(Risk).getChildText(RiskCode);
			// List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			// int tSize = tRiskList.size();
			// for (int i = 0; i < tSize; i++)
			// {
			// Element ttRiskEle = tRiskList.get(i);
			// ttRiskEle.getChild(RiskCode).setText(tRiskCode);
			//
			// if (tRiskCode.equals(ttRiskEle.getChildText(RiskCode)))
			// {
			// tRiskList.add(0, tRiskList.remove(i)); // �����յ�������ǰ��
			// }
			// }
			// }
			// else
			// {
			// String tMainRiskCode = tContSchema.getBak1();
			// List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			// int tSize = tRiskList.size();
			// for (int i = 0; i < tSize; i++)
			// {
			// Element ttRiskEle = tRiskList.get(i);
			// ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
			//
			// if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode)))
			// {
			// tRiskList.add(0, tRiskList.remove(i)); // �����յ�������ǰ��
			// }
			// }
			// }

			// ��֤����(Ͷ������������������)

			// ��ʱ�Զ�ɾ������
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60; // Ĭ�ϳ�ʱ����Ϊ1���ӣ����δ���ó�ʱʱ�䣬��ʹ�ø�ֵ��
			try {
				tTimeOut = Integer
						.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) { // ʹ��Ĭ��ֵ
				cLogger.debug("δ���ó�ʱ������������ʹ��Ĭ��ֵ(s)��" + tTimeOut, ex);
			}
			if (tUseTime > tTimeOut * 1000) {
				// cLogger.error("����ʱ��UseTime=" + tUseTime/1000.0 +
				// "s��TimeOut=" + tTimeOut + "s��Ͷ���飺" + mProposalPrtNo);
				cLogger.error("����ʱ��UseTime=" + tUseTime / 1000.0
						+ "s��TimeOut=" + tTimeOut);
				// rollback(); //�ع�ϵͳ����
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}

			// ���±���״̬
			Date tCurDate = new Date();
			Element tOutMainRiskEle = tOutBodyEle.getChild(Risk);
			JdomUtil.print(tOutMainRiskEle);
			cLogger.info(tOutMainRiskEle.getChildText(SignDate)
					+"=---="+DateUtil.get8Date(tCurDate)
					+"=---="+DateUtil.get8Date(tCurDate)
					+"=---="+tContSchema.getRecordNo()
					+"=---="+mContNo);
			tSqlStr = new StringBuilder("update Cont set State=")
					.append(AblifeCodeDef.ContState_Sign).append(", ContNo=")
					.append(mContNo).append(", SignDate=")
					.append(tOutMainRiskEle.getChildText(SignDate))
					.append(", ModifyDate=")
					.append(DateUtil.get8Date(tCurDate))
					.append(", ModifyTime=")
					.append(DateUtil.get6Time(tCurDate))
					.append(" where RecordNo=")
					.append(tContSchema.getRecordNo()).toString();
			ExeSQL tExeSQL = new ExeSQL();
			if (!tExeSQL.execUpdateSQL(tSqlStr)) {
				cLogger.error("���±���״̬(Cont)ʧ�ܣ�"
						+ tExeSQL.mErrors.getFirstError());
			}
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);

			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR,
					ex.getMessage());
		}

		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}

		cLogger.info("Out NewContConfirm.service()!");
		return cOutXmlDoc;
	}

	private void rollback() {
		cLogger.debug("Into NewContConfirm.rollback()...");

		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(
				AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent((Element) mInBodyEle.getChild(ProposalPrtNo)
				.clone());
		mBodyEle.addContent((Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent((Element) cOutXmlDoc.getRootElement()
				.getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);

		try {
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId))
					.call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("�ع�����ʧ�ܣ�", ex);
		}

		cLogger.debug("Out NewContConfirm.rollback()!");
	}
}
