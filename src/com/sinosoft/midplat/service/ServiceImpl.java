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
		//Into ServiceImpl.service()...
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
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...
		cLogger.debug("Into ServiceImpl.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		//[Element: <TranData/>]
		Element mTranDataEle = pXmlDoc.getRootElement();
		//[Element: <Head/>]
		Element mHeadEle = mTranDataEle.getChild(Head);
		//[Element: <Body/>]
		Element mBodyEle = mTranDataEle.getChild(Body);

		TranLogDB mTranLogDB = new TranLogDB();
		//LogNo:2240
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		//进程名：2240
		System.out.println("进程名：" + Thread.currentThread().getName());
		//TranCom:9
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		//ZoneNo:01
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		//NodeNo:060150001222
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		//TranNo:2016120800010
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		//Operator:5201300002
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		//FuncFlag:1012
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		//TranDate:20161108
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		//TranTime:130101
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		//InNoDoc:2240_3_1012_in.xml
		mTranLogDB.setInNoDoc(mHeadEle.getChildText("InNoDoc"));
		//trancom:9
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		//FuncFlag:1012
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		//mHeadEle.getChildText2240_3_1012_in.xml
		System.out.println("mHeadEle.getChildText" + mHeadEle.getChildText("InNoDoc"));
		if (null != mBodyEle)
		{
			//ProposalPrtNo:210414132201550
			mTranLogDB.setProposalPrtNo(mBodyEle.getChildText(ProposalPrtNo));
			//ContNo:null
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			//OtherNo:""
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			//Bak2:null
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
			//ORA-00001: 违反唯一约束条件 (YBT.PK_TRANLOG) 
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		//Out ServiceImpl.insertTranLog()!
		cLogger.debug("Out ServiceImpl.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}
}
