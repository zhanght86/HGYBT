package com.sinosoft.midplat.newabc.service;


import java.util.Date;

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
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
/**
 * @ClassName: ContRollback
 * @Description: 保单回滚 - 回滚后，就仿佛该单重来没有发生过保单回滚 - 回滚后，就仿佛该单重来没有发生过
 * @author sinosoft
 * @date 2017-3-31 上午11:37:10
 */
public class ContRollback extends ServiceImpl {
	public ContRollback(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	/**
	 * 业务处理
	 * @param pInXmlDoc 输入XML文档 
	 * @return cOutXmlDoc 输出XML文档
	 */
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContRollback.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mContNo = mBodyEle.getChildText(ContNo);
		String mOldTranNo = mBodyEle.getChildText(OldTranNo);
		
		String mProposalPrtNo = "";
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			/** 检查签单的交易流水号的合法性  */
			String checkTranNoSQL = new StringBuilder(
			"select * from Tranlog where ")
			.append(" FuncFlag=").append("1004")
			.append(" and tranno ='").append(mOldTranNo).append('\'')
			.append(" and MakeDate=").append(cTranLogDB.getMakeDate())
			.append(" and TranCom=").append(cTranLogDB.getTranCom())
			.append(" and ZoneNo='").append(cTranLogDB.getZoneNo())
			.append('\'').append(" and NodeNo='")
			.append(cTranLogDB.getNodeNo()).append('\'').toString();
			cLogger.info(checkTranNoSQL);
			TranLogSet sTranLogSet = new TranLogDB().executeQuery(checkTranNoSQL);
			if (1 != sTranLogSet.size()) {
				throw new MidplatException("当日的交易流水号" + mOldTranNo + "不存在，请确认！");
			}
			TranLogSchema tTranLogSchema = sTranLogSet.get(1);
			mProposalPrtNo = tTranLogSchema.getProposalPrtNo();
			
			//校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300;	//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			}
			//当天、同一网点，成功出过单
			String tSqlStr = new StringBuilder("select * from Cont where Type=").append(AblifeCodeDef.ContType_Bank)
//				.append(" and State=").append(AblifeCodeDef.ContState_Sign)// 可能是签单失败后的冲正，也可能是签单成功后的冲正
//				.append(" and ContNo='").append(mContNo).append('\'')//所以此处的保单号可能存在，也可能不存在（只有签单成功后，才能生成相应的保单号）
				.append(" and ProposalPrtNo = '").append(mProposalPrtNo).append('\'')
				.append(" and MakeDate=").append(cTranLogDB.getMakeDate())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			cLogger.info(tSqlStr);
			ContSet mContSet = new ContDB().executeQuery(tSqlStr);
			if (1 != mContSet.size()) {
				cLogger.info("非当日同一网点所出保单，不能进行该操作！");//为了处理农行冲正会连续冲正发来几次的情况
			}
			ContSchema tContSchema = mContSet.get(1);
			
			mBodyEle.getChild(ProposalPrtNo).setText(mProposalPrtNo);
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContRollback).call(cInXmlDoc);			
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			if(tContSchema!=null){
			//删除保单数据
//			tSqlStr = new StringBuilder("delete from  Cont  where RecordNo=").append(tContSchema.getRecordNo())
//				.toString();
				
				tSqlStr = new StringBuilder("update Cont set State=  "+AblifeCodeDef.ContState_Input)//置为试算成功未签订状态
				.append(", ModifyDate=").append(DateUtil.getCur8Date())
				.append(", ModifyTime=").append(DateUtil.getCur6Time())
				.append(" where RecordNo=").append(tContSchema.getRecordNo())
				.toString();
				
			ExeSQL tExeSQL = new ExeSQL();
			if (!tExeSQL.execUpdateSQL(tSqlStr)) {
				cLogger.error("更新保单状态(Cont)失败！" + tExeSQL.mErrors.getFirstError());
			}
			}
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			
			cTranLogDB.setContNo(mContNo);
			cTranLogDB.setProposalPrtNo(mProposalPrtNo);
			
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out ContRollback.service()!");
		return cOutXmlDoc;
	}
	
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		cLogger.debug("Into ServiceImpl.insertTranLog()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("进程名：" + Thread.currentThread().getName());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
//		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setTranNo(DateUtil.getCur8Date() + ""+ DateUtil.getCur6Time());
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
			throw new MidplatException("插入日志失败！");
		}

		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
	}
}
