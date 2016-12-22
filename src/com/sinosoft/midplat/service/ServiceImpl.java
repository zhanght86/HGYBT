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
	//输入标准报文
	protected Document cInXmlDoc;
	//输出标准报文
	protected Document cOutXmlDoc;
	protected TranLogDB cTranLogDB;
	protected NodeMapSchema cNodeMapSchema;
	
	/**
	 * 初始化服务实现类
	 * @param pThisBusiConf 交易元素
	 */
	public ServiceImpl(Element pThisBusiConf)
	{
		cThisBusiConf = pThisBusiConf;//成员交易配置文件初始化
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
		//进程名：10091
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
		//trancom:13
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		//FuncFlag:1012
		//FuncFlag:1012
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		//mHeadEle.getChildText10091_3_1012_in.xml
		//mHeadEle.getChildText2240_3_1012_in.xml
		System.out.println("mHeadEle.getChildText" + mHeadEle.getChildText("InNoDoc"));
		//<Body> != null
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
		//交易码为实时投保、新单确认，交易机构代码是中国工商银行
		if (("1012".equals(mHeadEle.getChildText(FuncFlag)))// 建行和农行的试算交易
				|| ("1013".equals(mHeadEle.getChildText(FuncFlag)) && String.valueOf(AblifeCodeDef.TranCom_ICBC).endsWith(mHeadEle.getChildText(TranCom))))
		{
			//设置投保人名称
			mTranLogDB.setAppntName((mBodyEle.getChild(Appnt).getChildText(Name)));
			//设置投保人证件号码
			mTranLogDB.setAppntIDNo((mBodyEle.getChild(Appnt).getChildText(IDNo)));
			//设置被保人名称
			mTranLogDB.setInsuredName((mBodyEle.getChild(Insured).getChildText(Name)));
			//设置被保人证件号码
			mTranLogDB.setInsuredIDNo((mBodyEle.getChild(Insured).getChildText(IDNo)));
			//交易机构代码为中国银行、金华银行
			if (mHeadEle.getChildText(TranCom).equals("03") || mHeadEle.getChildText(TranCom).equals("07"))
			{
				//设置险种代码
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(RiskCode));
			}
			else//交易机构代码为其他银行
			{
				//设置主险种代码
				mTranLogDB.setProductId(mBodyEle.getChild(Risk).getChildText(MainRiskCode));
			}
		}
		//设置交易结果为交易挂起，未返回 
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		//设置服务耗时为-1
		mTranLogDB.setUsedTime(-1);
		//设置备用1为银行端IP
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		//获取当前日期对象
		Date mCurDate = new Date();
		//设置入库日期[当前日期对象前八位
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		//设置入库时间[当前日期对象后六位]
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		//设置最后修改日期为入库日期
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		//设置最后修改时间为入库时间
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		//获取当前毫秒数
		long mStartMillis = System.currentTimeMillis();
		//交易日志数据库操作插入失败
		if (!mTranLogDB.insert())
		{
			//ORA-00001: 违反唯一约束条件 (YBT.PK_TRANLOG) 
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			/*com.sinosoft.midplat.exception.MidplatException: 插入日志失败！
				at com.sinosoft.midplat.service.ServiceImpl.insertTranLog(ServiceImpl.java:139)
				at com.sinosoft.midplat.newccb.service.NewContInput.service(NewContInput.java:57)
				at com.sinosoft.midplat.Ybt4Socket.run(Ybt4Socket.java:124)*/
			throw new MidplatException("插入日志失败！");
		}
		//Out ServiceImpl.insertTranLog()!
		//Out ServiceImpl.insertTranLog()!
		cLogger.debug("Out ServiceImpl.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}
}
