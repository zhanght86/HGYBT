package com.sinosoft.midplat.newabc.service;

import java.util.Date;

import org.jdom.Document;

import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.ServiceImpl;

public class Heartbeat extends ServiceImpl {
	public Heartbeat(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		//开始毫秒数
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into Heartbeat.service()...");
		//成员标准输入报文
		cInXmlDoc = pInXmlDoc;
		//交易耗时
		Element tInsuTime = new Element("InsuTime");
		//新单确认使用毫秒数
		long mUsedContConfirm =0;
		//当前毫秒
		long tCurMillis=0;
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			//开始新单确认毫秒数
			long mStartContConfirm = System.currentTimeMillis();
			//新单确认使用毫秒数
			mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			//成员标准输出报文
			cOutXmlDoc = cInXmlDoc;
			//报文头
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			//交易结果
			Element tFlag = new Element(Flag);
			tFlag.setText("0");
			//交易结果描述
			Element tDesc = new Element(Desc);
			tDesc.setText("心跳交易成功！");
			//报文头加入交易结果、交易结果描述子节点
			tOutHeadEle.addContent(tFlag);
			tOutHeadEle.addContent(tDesc);
			
			//交易结果返回失败
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				//异常:结果描述
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"心跳交易失败！", ex);
			//获取简单输出报文
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
		Element iHeadEle = cInXmlDoc.getRootElement().getChild(Head);
		cTranLogDB.setAgentComName(iHeadEle.getChildText("AgentComName"));
		cTranLogDB.setAgentName(iHeadEle.getChildText("AgentName"));
		cTranLogDB.setAgentCodeGrade(iHeadEle.getChildText("AgentCodeGrade"));
		cTranLogDB.setUnitCode(iHeadEle.getChildText("UnitCode"));
		if(iHeadEle.getChildText("ManageCom") == null || "".equals(iHeadEle.getChildText("ManageCom"))){
			cTranLogDB.setManageCom("86");
		}else{
			cTranLogDB.setManageCom(iHeadEle.getChildText("ManageCom"));
		}
		cTranLogDB.setAgentCom(iHeadEle.getChildText("AgentCom"));
		cTranLogDB.setAgentCode(iHeadEle.getChildText("AgentCode"));
		cTranLogDB.setAgentGrade(iHeadEle.getChildText("AgentGrade"));
		Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
		
		cTranLogDB.setOutDoc(tHeadEle.getChildText("OutDoc"));
		cTranLogDB.setInDoc(tHeadEle.getChildText("InDoc"));
		cTranLogDB.setInNoDoc(iHeadEle.getChildText("InNoDoc"));
		cTranLogDB.setOutNoDoc("");
		cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
		cTranLogDB.setRText(tHeadEle.getChildText(Desc));
		tCurMillis = System.currentTimeMillis();
		cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
		cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
		cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
		cTranLogDB.setBak3(String.valueOf((tCurMillis-mStartMillis)/1000.0));
		cTranLogDB.setBak4(String.valueOf(mUsedContConfirm/1000.0));
		if (!cTranLogDB.update()) {
			cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
		}
	}
		//设置交易耗时[当前毫秒-开始毫秒数]
		tInsuTime.setText(String.valueOf(tCurMillis-mStartMillis));
		//报文头加入交易耗时节点
		cOutXmlDoc.getRootElement().getChild(Head).addContent(tInsuTime);
		cLogger.info("Out GreenTest.service()!");
		return cOutXmlDoc;
	}
	
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		//测试
		JdomUtil.print(pXmlDoc);
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild("Head");
		Element mBodyEle = mTranDataEle.getChild("Body");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("进程名：" + Thread.currentThread().getName());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo("NEWABC");
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(CodeDef.SYS);
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
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
	}
	
}
