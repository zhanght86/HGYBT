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

	protected final Element cThisBusiConf;

	protected Document cInXmlDoc;
	protected Document cOutXmlDoc;
	protected TranLogDB cTranLogDB;
	protected NodeMapSchema cNodeMapSchema;

	public ServiceImpl(Element pThisBusiConf)
	{
		cThisBusiConf = pThisBusiConf;
	}

	public Document service(Document pInXmlDoc) throws Exception
	{
		cLogger.info("Into ServiceImpl.service()...");

		cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "交易成功！");

		cLogger.info("Out ServiceImpl.service()!");
		return cOutXmlDoc;
	}

	/**
	 * 插入交易日志
	 * @param pXmlDoc XML文档
	 * @return 交易日志对象
	 * @throws MidplatException
	 */
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		cLogger.debug("Into ServiceImpl.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("进程名：" + Thread.currentThread().getName());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
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
		if (("1012".equals(mHeadEle.getChildText(FuncFlag)))// 建行和农行的试算交易
				|| ("1013".equals(mHeadEle.getChildText(FuncFlag)) && String.valueOf(AblifeCodeDef.TranCom_ICBC).endsWith(mHeadEle.getChildText(TranCom))))
		{
			mTranLogDB.setAppntName((mBodyEle.getChild(Appnt).getChildText(Name)));
			mTranLogDB.setAppntIDNo((mBodyEle.getChild(Appnt).getChildText(IDNo)));
			mTranLogDB.setInsuredName((mBodyEle.getChild(Insured).getChildText(Name)));
			mTranLogDB.setInsuredIDNo((mBodyEle.getChild(Insured).getChildText(IDNo)));
			if (mHeadEle.getChildText(TranCom).equals("03") || mHeadEle.getChildText(TranCom).equals("07"))
			{
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(RiskCode));
			}
			else
			{
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(MainRiskCode));
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
		long mStartMillis = System.currentTimeMillis();
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}

		cLogger.debug("Out ServiceImpl.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}
}
