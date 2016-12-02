package com.sinosoft.midplat.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContBlcDtlDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class CcbNewContBlc extends NewContBlc {
	public CcbNewContBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into CcbNewContBlc.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mTranDataEle = cInXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			cNodeMapSchema = MidplatUtil.getNodeMap(
					AblifeCodeDef.NodeType_Bank_Bat,
					cTranLogDB.getTranCom(),
					cTranLogDB.getNodeNo());
			cTranLogDB.setManageCom(cNodeMapSchema.getManageCom());
			cTranLogDB.setAgentCom(cNodeMapSchema.getAgentCom());
			cTranLogDB.setAgentCode(cNodeMapSchema.getAgentCode());
			Element tAgentComEle = new Element(AgentCom);
			tAgentComEle.setText(cNodeMapSchema.getAgentCom());
			mHeadEle.addContent(tAgentComEle);
			
			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
				.append(" and TranDate=").append(cTranLogDB.getTranDate())
				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
				throw new MidplatException("已成功做过新单对账，不能重复操作！");
			} else if (tExeSQL.mErrors.needDealError()) {
				cLogger.error(tExeSQL.mErrors.getFirstError());
				throw new MidplatException("查询历史对账信息异常！");
			}
			
			//保存对账明细
			saveDetails(cInXmlDoc);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "交易成功");
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-未返回；0-交易成功，返回；1-交易失败，返回
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		//检查是否所有对账网点对账文件都已成功接收完毕
		String mSqlStr = "select count(1) from NodeMap where Type="+AblifeCodeDef.NodeType_Bank_Bat+" and TranCom="+cTranLogDB.getTranCom();
		String mBatNodeCount = new ExeSQL().getOneValue(mSqlStr);
		mSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_OK)
			.append(" and TranDate=").append(cTranLogDB.getTranDate())
			.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
			.append(" and TranCom=").append(cTranLogDB.getTranCom())
			.toString();
		String mOkBlcCount = new ExeSQL().getOneValue(mSqlStr);
		if (mOkBlcCount.equals(mBatNodeCount)) {
			try{
				BalanceThread.newInstance().setHead(mHeadEle);
				BalanceThread.newInstance().start();
			} catch (IllegalThreadStateException ex) {
				cLogger.warn("对账线程启动失败，很可能该线程已经被启动！");
			}
		}
		
		cLogger.info("Out CcbNewContBlc.service()!");
		return cOutXmlDoc;
	}
}

class BalanceThread extends Thread implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(BalanceThread.class);
	
	private static BalanceThread cThisIns = new BalanceThread();
	
	private Element cHeadEle;
	
	/**
	 * 设计为单例模式，避免最后两个对账网点同时到来引发两个对账线程
	 */
	private BalanceThread() {}
	
	public static BalanceThread newInstance() {
		return cThisIns;
	}
	
	public void run() {
		long mStartMillis = System.currentTimeMillis();
		int mLogNo = NoFactory.nextTranLogNo();
		setName(String.valueOf(mLogNo));
		cLogger.info("Into BalanceThread.run()...");
		
		Document mOutXmlDoc = null;
		
		TranLogDB mTranLogDB = new TranLogDB();
		try {
			mTranLogDB.setLogNo(mLogNo);
			mTranLogDB.setTranCom(cHeadEle.getChildText(TranCom));
			mTranLogDB.setNodeNo("86");
			mTranLogDB.setTranNo(cHeadEle.getChildText(TranNo));
			mTranLogDB.setOperator(CodeDef.SYS);
			mTranLogDB.setFuncFlag(cHeadEle.getChildText(FuncFlag));
			mTranLogDB.setTranDate(cHeadEle.getChildText(TranDate));
			mTranLogDB.setTranTime(cHeadEle.getChildText(TranTime));
			mTranLogDB.setAgentCom(cHeadEle.getChildText(AgentCom));
			mTranLogDB.setManageCom("86");
			mTranLogDB.setRCode(CodeDef.RCode_NULL);
			mTranLogDB.setUsedTime(-1);
			Date tCurDate = new Date();
			mTranLogDB.setMakeDate(DateUtil.get8Date(tCurDate));
			mTranLogDB.setMakeTime(DateUtil.get6Time(tCurDate));
			mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
			mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
			if (!mTranLogDB.insert()) {
				cLogger.error(mTranLogDB.mErrors.getFirstError());
				mTranLogDB = null;
				throw new MidplatException("插入日志失败！");
			}
			
			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
				.append(" and TranDate=").append(mTranLogDB.getTranDate())
				.append(" and FuncFlag=").append(mTranLogDB.getFuncFlag())
				.append(" and TranCom=").append(mTranLogDB.getTranCom())
				.append(" and NodeNo='").append(mTranLogDB.getNodeNo()).append('\'')
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
				throw new MidplatException("已成功做过新单对账，不能重复操作！");
			} else if (tExeSQL.mErrors.needDealError()) {
				throw new MidplatException("查询历史对账信息异常！");
			}
			
			tSqlStr = new StringBuilder("select * from ContBlcDtl where TranDate=").append(mTranLogDB.getTranDate())
				.append(" and TranCom=").append(mTranLogDB.getTranCom())
				.toString();
			cLogger.info("sql: " +  tSqlStr);
			ContBlcDtlDB tContBlcDtlDB = new ContBlcDtlDB();
			ContBlcDtlSet tContBlcDtlSet = tContBlcDtlDB.executeQuery(tSqlStr);
			if (tContBlcDtlDB.mErrors.needDealError()) {
				cLogger.error(tContBlcDtlDB.mErrors.getFirstError());
				throw new MidplatException("查询新单对账明细失败！");
			}
			int tCount = tContBlcDtlSet.size();
			long tSumPrem = 0;
			Element tCountEle = new Element(Count);
			tCountEle.setText(String.valueOf(tCount));
			Element tPremEle = new Element(Prem);
			Element tBodyEle = new Element(Body);
			tBodyEle.addContent(tCountEle);
			tBodyEle.addContent(tPremEle);
			for (int i = 1; i <= tCount; i++) {
				ContBlcDtlSchema ttContBlcDtlSchema = tContBlcDtlSet.get(i);
				
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(ttContBlcDtlSchema.getContNo());
				Element ttProposalPrtNoEle = new Element(ProposalPrtNo);
				ttProposalPrtNoEle.setText(ttContBlcDtlSchema.getProposalPrtNo());
				Element ttPremEle = new Element(Prem);
				ttPremEle.setText(String.valueOf(ttContBlcDtlSchema.getPrem()));
				Element ttAgentComEle = new Element(AgentCom);
				ttAgentComEle.setText(ttContBlcDtlSchema.getAgentCom());
				Element ttAppntNameEle = new Element("AppntName");
				ttAppntNameEle.setText(ttContBlcDtlSchema.getAppntName());
				Element ttInsuredNameEle = new Element("InsuredName");
				ttInsuredNameEle.setText(ttContBlcDtlSchema.getInsuredName());
				
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(ttProposalPrtNoEle);
				tDetailEle.addContent(ttPremEle);
				tDetailEle.addContent(ttAgentComEle);
				tDetailEle.addContent(ttAppntNameEle);
				tDetailEle.addContent(ttInsuredNameEle);
				
				tBodyEle.addContent(tDetailEle);
				
				tSumPrem += ttContBlcDtlSchema.getPrem();
			}
			tPremEle.setText(String.valueOf(tSumPrem));
			
			Element tTranDataEle = new Element(TranData);
			tTranDataEle.addContent(cHeadEle);
			tTranDataEle.addContent(tBodyEle);
			
			mOutXmlDoc = new CallWebsvcAtomSvc(
					AblifeCodeDef.SID_Bank_NewContBlc).call(new Document(tTranDataEle));
			Element tOutHeadEle = mOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} catch (Exception ex) {
			cLogger.error("建行新单汇总对账失败！", ex);
			
			mOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != mTranLogDB) {	//插入日志失败时mTranLogDB=null
			Element tHeadEle = mOutXmlDoc.getRootElement().getChild(Head);
			mTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-未返回；0-交易成功，返回；1-交易失败，返回
			mTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			mTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			mTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			mTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!mTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + mTranLogDB.mErrors.getFirstError());
			}
		}
		
		/**
		 * 多次启动一个线程是非法的。特别是当线程已经结束执行后，不能再重新启动。 
		 * 故，在每次跑完后需要重新生成一个线程，供下次对账使用。
		 */
		cThisIns = new BalanceThread();
		
		cLogger.info("Out BalanceThread.run()!");
	}
	
	public synchronized void setHead(Element pHeadEle) {
		if (null == cHeadEle) {	//一旦被set值，就不允许重新被赋值
			cHeadEle = (Element) pHeadEle.clone();
			Element tAgentComEle = cHeadEle.getChild(AgentCom);
			tAgentComEle.setText(tAgentComEle.getText().substring(0, 8));
		}
	}
}