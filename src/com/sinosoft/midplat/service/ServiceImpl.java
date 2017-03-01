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

	//当前交易配置文件
	protected final Element cThisBusiConf;
	//标准输入报文
	protected Document cInXmlDoc;
	//标准输出报文
	protected Document cOutXmlDoc;
	//交易日志数据库操作类
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
	 * @param pXmlDoc XML文档[标准输入报文]
	 * @return 交易日志对象
	 * @throws MidplatException
	 */
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		//测试
		JdomUtil.print(pXmlDoc);
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...[进入业务处理实现类.插入交易日志...]
		cLogger.debug("Into ServiceImpl.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		//[Element: <TranData/>]
		//[标准输入]报文TranData根节点
		Element mTranDataEle = pXmlDoc.getRootElement();
		//[Element: <Head/>]
		//[标准输入]Head报文头
		Element mHeadEle = mTranDataEle.getChild("Head");
		//[标准输入]Body报文体
		//[Element: <Body/>]
		Element mBodyEle = mTranDataEle.getChild("Body");
		//交易日志数据库操作类实例[不传入连接对象，交易日志生成数据库操作对象]
		TranLogDB mTranLogDB = new TranLogDB();//TranLog数据操作类实例
		//LogNo:2240
		//设置日志号为当前正在执行的线程对象的引用名称
		//设置日志号
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		//进程名：2240
		//进程名：10091
		System.out.println("进程名：" + Thread.currentThread().getName());
		/*设置交易日志数据库操作类实例字段为核心标准输入报文头字段值*/
		/*设置TranLog表9个字段[标准输入报文头9个字段]：TranDate、TranTime、ZoneNo、NodeNo、TellerNo[Operator]、TranNo、TranCom、FuncFlag、InNoDoc*/
		//TranCom:9
		//设置交易单位为[标准输入]报文头交易机构代码子元素文本
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		//ZoneNo:01
		//设置地区代码为[标准输入]报文头省市代码子元素文本
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		//NodeNo:060150001222
		//设置交易网点为[标准输入]报文头网点代码子元素文本
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		//TranNo:2016120800010
		//设置交易流水号为[标准输入]报文头交易流水号子元素文本
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		//Operator:5201300002
		//设置操作员为[标准输入]报文头柜员代码子元素文本
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		//FuncFlag:1012
	   //设置交易类型为[标准输入]报文头交易类型子元素文本
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		//TranDate:20161108
		//设置交易日期为[标准输入]报文头交易日期子元素文本
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		//TranTime:130101
		//设置交易时间为[标准输入]报文头交易时间子元素文本
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		//InNoDoc:2240_3_1012_in.xml
		//设置进入报文为[标准输入]报文头进入报文子元素文本
		mTranLogDB.setInNoDoc(mHeadEle.getChildText("InNoDoc"));
		//trancom:9
		//trancom:13[输出交易单位]
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		//FuncFlag:1012
		//FuncFlag:1012[输出交易类型]
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		//mHeadEle.getChildText10091_3_1012_in.xml
		//mHeadEle.getChildText2240_3_1012_in.xml[输出进入报文]
		System.out.println("mHeadEle.getChildText" + mHeadEle.getChildText("InNoDoc"));
		//<Body> != null
		//[标准输入]报文体非空
		//Body非空
		if (null != mBodyEle)
		{
			/*设置TranLog表4个字段:ProposalPrtNo、ContNo、ContPrtNo、OldLogNo[Bak2]*/
			//ProposalPrtNo:210414132201550
			//设置投保单(印刷)号为[标准输入]报文体投保单(印刷)号子元素文本
			mTranLogDB.setProposalPrtNo(mBodyEle.getChildText(ProposalPrtNo));
			//ContNo:null
			//设置保单号为[标准输入]报文体保险单号子元素文本
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			//OtherNo:""
			//设置其他关联号为[标准输入]报文体保单合同印刷号 (单证) 子元素文本
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			//Bak2:null
			//设置备用2为[标准输入]报文体旧日志号子元素文本
			mTranLogDB.setBak2(mBodyEle.getChildText("OldLogNo"));
			//交易日志数据库操作类实例备用2为空、空字符
			if (null == mTranLogDB.getBak2() || "".equals(mTranLogDB.getBak2()))
			{
				//设置备用2为[标准输入]报文体旧交易流水号子元素文本
				mTranLogDB.setBak2(mBodyEle.getChildText("OldTranNo"));
			}
		}
		//交易码为实时投保、新单确认，交易机构代码是中国工商银行
		//交易码为试算交易或工行新单承保
		if (("1012".equals(mHeadEle.getChildText(FuncFlag)))// 建行和农行的试算交易
				|| ("1013".equals(mHeadEle.getChildText(FuncFlag)) && String.valueOf(AblifeCodeDef.TranCom_ICBC).endsWith(mHeadEle.getChildText(TranCom))))
		{
			/*设置TranLog表5个字段：AppntName、AppntIDNo、InsuredName、InsuredIDNo、RiskCode[ProductId]*/
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
		/*设置TranLog表7个字段:RCode、UsedTime、Bak1、MakeDate、MakeTime、ModifyDate、ModifyTime*/
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
		//插入交易日志对象到交易日志表
		//!false=true插入失败
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
