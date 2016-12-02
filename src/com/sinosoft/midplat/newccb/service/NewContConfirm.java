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

			cLogger.info("Into NewContInput.service()...-->authority(cInXmlDoc)网点与权限 添加代理");

			// 校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300; // 默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try {
				tLockTime = Integer.parseInt(cThisBusiConf
						.getChildText(locktime));
			} catch (Exception ex) { // 使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)：" + tLockTime, ex);
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
			// throw new MidplatException("此保单数据正在处理中，请稍候！");
			// }

			// 建行此处特殊处理，根据保费试算的交易流水号，到交易日志表中查询出投保单号（mProposalPrtNo)
			if ("13".equals(mRootEle.getChild(Head).getChildText(TranCom))) {// 建行的交易
				String getProposalPrtNoSQL = "select ProposalPrtNo from TranLog where TranDate = "
						+ DateUtil.getCur8Date()
						+ " and TranNo = '"
						+ mBodyEle.getChildText(TranNo) + "' and RCode=0 ";

				cLogger.info(getProposalPrtNoSQL);

				// mProposalPrtNo = new
				// ExeSQL().getOneValue(getProposalPrtNoSQL);
				// Element tProposalPrtNo = mBodyEle.getChild(ProposalPrtNo);
				// tProposalPrtNo.setText(mProposalPrtNo);

				// 获取录单核保的保单印刷号
				String getoldPrtNo = "select otherNo from TranLog where TranDate = "
						+ DateUtil.getCur8Date()
						+ " and TranNo = '"
						+ mBodyEle.getChildText(TranNo) + "' and RCode=0 ";
				cLogger.info(getoldPrtNo);
				String oldPrtNo = new ExeSQL().getOneValue(getoldPrtNo);

				cTranLogDB.setOtherNo(oldPrtNo);
				// cTranLogDB.setProposalPrtNo(mProposalPrtNo);

				// 把核保的保单印刷号给核心传过去
				Element tContPrtNo = mBodyEle.getChild(ContPrtNo);
				tContPrtNo.setText(oldPrtNo);

			}

			// //农行传保单印刷号给核心
			// if("05".equals(mRootEle.getChild(Head).getChildText(TranCom))){//农行的交易
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
			// cLogger.info("取最新的保单印刷号给核心传过去:"+prtNo);
			// }
			// }

			// JdomUtil.print(cInXmlDoc);

			// 当天、同一网点，成功录过单
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
			// throw new MidplatException("非当日同一网点所出保单，不能进行该操作！");
			// }
			ContSchema tContSchema = mContSet.get(1);

			// //和核心联调的时候放开 begin
			cOutXmlDoc = new CallWebsvcAtomSvc(
					AblifeCodeDef.SID_Bank_ContConfirm).call(cInXmlDoc);
			// 和核心联调的时候放开 end

			// 和核心联调的时候注视掉begin
			// String mInStr = "G:/test/11019_55_1_outSvc.xml";
			// InputStream mIs = null;
			// try {
			// mIs = new FileInputStream(mInStr);
			// } catch (FileNotFoundException e) {
			// e.printStackTrace();
			// }
			// byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
			// cOutXmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
			// 和核心联调的时候注视掉end

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
			// 核心不存保单印刷号，用请求报文对应值覆盖---核心保存了，再说了这个是投保单号，呜呜...
			// 这是以前谁维护的？不保存就不保存呗，这么矫情啊！
			// tOutBodyEle.getChild(ProposalPrtNo).setText(mContPrtNo);

			// 核心可能将一个产品的两个险种都定义为主险，而银行则认为一主一附，以银行报文为准，覆盖核心记录
			// 新建行没有MainRiskCode只有RiskCode
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
			// tRiskList.add(0, tRiskList.remove(i)); // 将主险调整到最前面
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
			// tRiskList.add(0, tRiskList.remove(i)); // 将主险调整到最前面
			// }
			// }
			// }

			// 单证处理(投保单核销、保单核销)

			// 超时自动删除数据
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60; // 默认超时设置为1分钟；如果未配置超时时间，则使用该值。
			try {
				tTimeOut = Integer
						.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) { // 使用默认值
				cLogger.debug("未配置超时，或配置有误，使用默认值(s)：" + tTimeOut, ex);
			}
			if (tUseTime > tTimeOut * 1000) {
				// cLogger.error("处理超时！UseTime=" + tUseTime/1000.0 +
				// "s；TimeOut=" + tTimeOut + "s；投保书：" + mProposalPrtNo);
				cLogger.error("处理超时！UseTime=" + tUseTime / 1000.0
						+ "s；TimeOut=" + tTimeOut);
				// rollback(); //回滚系统数据
				throw new MidplatException("系统繁忙，请稍后再试！");
			}

			// 更新保单状态
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
				cLogger.error("更新保单状态(Cont)失败！"
						+ tExeSQL.mErrors.getFirstError());
			}
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);

			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR,
					ex.getMessage());
		}

		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
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
			cLogger.error("回滚数据失败！", ex);
		}

		cLogger.debug("Out NewContConfirm.rollback()!");
	}
}
