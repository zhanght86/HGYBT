package com.sinosoft.midplat.newccb.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Schema;

//实时投保[录单自核]
public class NewContInput extends ServiceImpl
{
	public NewContInput(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	/**
	 * 标准输入报文业务处理
	 * @param pInXmlDoc 标准输入报文
	 * @return 标准输出报文
	 */
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc)
	{
		//开始毫秒数
		long mStartMillis = System.currentTimeMillis();
		//Into NewContInput.service()...
		cLogger.info("Into NewContInput.service()...");
		//获取标准输入报文
		cInXmlDoc = pInXmlDoc;
		//Java文档对象模型工具将输入标准报文打印到控制台[GBK编码，缩进3空格]
		//打印标准输入报文
		JdomUtil.print(cInXmlDoc);//[Element:<TranData/>]
		//标准输入报文TranData根节点
		Element mRootEle = cInXmlDoc.getRootElement();//核心录单自核请求报文根节点[Element: <TranData/>]
		//标准输入报文Body报文体节点
		Element mBodyEle = mRootEle.getChild(Body);//核心录单自核请求报文报文体[Element: <Body/>]
		//Body的投保单(印刷)号子节点文本
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);//投保单(印刷)号 210414131200027
		// String mContPrtNo = mBodyEle.getChildText(ContPrtNo);

		try
		{
			// System.out.println("--------------------------------------------------------------------------------------------------------");
			// JdomUtil.print(cInXmlDoc);
			//将标准输入报文插入交易日志表
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			// 校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300; // 默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try
			{
				//获取锁定时间
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			}
			catch (Exception ex)
			{ // 使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)：" + tLockTime, ex);
			}
			//获取当前日历对象
			//java.util.GregorianCalendar[time=1480406848055,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=19,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2016,MONTH=10,WEEK_OF_YEAR=49,WEEK_OF_MONTH=5,DAY_OF_MONTH=29,DAY_OF_YEAR=334,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=5,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=7,SECOND=28,MILLISECOND=55,ZONE_OFFSET=28800000,DST_OFFSET=0]
			Calendar tCurCalendar = Calendar.getInstance();
			//日历对象加入秒数
			tCurCalendar.add(Calendar.SECOND, -tLockTime);//13，200
			//拼接结构化查询语句[查询交易日志表中交易结果为交易挂起，未返回，投保单印刷号为]
			//select count(1) from TranLog where RCode=-1 and ProposalPrtNo='210414131200027' and MakeDate>=20161129 and MakeTime>=160408
			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL).append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'').append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar)).append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar)).toString();
			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr)))
			{
				throw new MidplatException("此保单数据正在处理中，请稍候！");
			}

			JdomUtil.print(cInXmlDoc);//打印核心录单自核请求报文

			// lilu20150305校验人物关系代码
			String mApptRelationShip = mBodyEle.getChild("Appnt").getChildText("RelaToInsured");//01
			//投保人与被保人关系非空
			if (!"".equals(mApptRelationShip))
			{
				//投保人与被保人关系为--[无映射]
				if ("--".equals(mApptRelationShip))
				{
					//抛出中间平台异常
					throw new MidplatException("投保人与被保人关系有误！");
				}
			}
			
			//受益人列表
			List<Element> tBnf = mBodyEle.getChildren("Bnf");//[[Element: <Bnf/>], [Element: <Bnf/>]]
			//遍历受益人列表							2
			for (int i = 0; i < tBnf.size(); i++)
			{
				//受益人与被保人关系
				String mBnfRelationShip = tBnf.get(i).getChildText("RelaToInsured");
				//受益人与被保人关系非空
				if (!"".equals(mBnfRelationShip))
				{
					//受益人与被保人关系为--[无映射]
					if ("--".equals(mBnfRelationShip))
					{
						//抛出中间平台异常
						throw new MidplatException("受益人与被保人关系有误！");
					}
				}
			}
			
			// 规则解析器类构造实例检验标准输入报文
			new RuleParser().check(cInXmlDoc);//检查核心录单自核请求报文

			
			cLogger.info("------进入收益比例校验20140807------");//16:19:44,892 INFO service.NewContInput(106) - ------进入收益比例校验20140807------
			//YBT数据检验实例
			YBTDataVerification verification = new YBTDataVerification();
			//					true
			//标准输入报文同一受益顺序受益人验证是否合法
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);//同一受益顺序受益人验证核心录单自核请求报文
			//同一受益顺序受益人非法
			if (GradeFlag == false)
			{
				//抛出中间平台异常
				throw new MidplatException("同一受益顺序受益份额之和不等于1！请确认");
			}
			
			try
			{
				//																								银保录单核保					核心录单自核请求报文
				//调用WebService[银保录单核保]标准输入报文原子服务
				//调用WebService的银保录单核保原子服务处理标准输入报文返回标准输出报文
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			//16:35:47,535 INFO common.Log4jPrint(20) - -----------------------------------------------
			System.out.println("-----------------------------------------------");
			//16:37:03,149 INFO service.NewContInput(124) - hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
			cLogger.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			//将标准输出报文打印到控制台[ GBK编码，缩进3空格]
			JdomUtil.print(cOutXmlDoc);
			//标准输出报文根节点
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			//标准输出报文报文头
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			//标准输出报文报文体
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			//交易结果为失败
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{
				//获取交易结果描述
				String desc = tOutHeadEle.getChildText(Desc);
				//错误描述长度：交易结果描述长度
				cLogger.info("错误描述长度：" + desc.length());
				//交易结果描述长度超出90个字符
				if (desc.length() > 90)
				{
					//截取前60个字符拼接上......
					desc = desc.substring(0, 60) + "......)";
					//处理后错误描述：交易结果描述
					cLogger.info("处理后错误描述：" + desc);
				}
				//抛出中间平台异常
				throw new MidplatException(desc);
			}

			// 核心不存保单印刷号，用请求报文对应值覆盖
			// tOutBodyEle.getChild(ProposalPrtNo).setText(mContPrtNo);

			// 核心可能将一个产品的两个险种都定义为主险，而银行则认为一主一附，以银行报文为准，覆盖核心记录
			// 新建行没有MainRiskCode只有RiskCode
//			if (cInXmlDoc.getRootElement().getChild("Head").getChildText("TranCom").equals("03"))
//			{
//				String tRiskCode2 = mBodyEle.getChild(Risk).getChildText(RiskCode);
//				List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
//				int tSize = tRiskList.size();
//				for (int i = 0; i < tSize; i++)
//				{
//					Element ttRiskEle = tRiskList.get(i);
//					ttRiskEle.getChild(RiskCode).setText(tRiskCode2);
//
//					if (tRiskCode2.equals(ttRiskEle.getChildText(RiskCode)))
//					{
//						tRiskList.add(0, tRiskList.remove(i)); // 将主险调整到最前面
//					}
//				}
//			}
//			else
//			{
//				String tMainRiskCode = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
//				List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
//				int tSize = tRiskList.size();
//				for (int i = 0; i < tSize; i++)
//				{
//					Element ttRiskEle = tRiskList.get(i);
//					ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
//
//					if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode)))
//					{
//						tRiskList.add(0, tRiskList.remove(i)); // 将主险调整到最前面
//					}
//				}
//			}

			// 超时自动删除数据
			//使用时间[当前时间毫秒数-开始时间毫秒数]
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			//超时时间[默认60秒]
			int tTimeOut = 60; // 默认超时设置为1分钟；如果未配置超时时间，则使用该值。
			try
			{
				//获取midplat.xml的交易/超时时间:200[无单位]
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			}
			catch (Exception ex)
			{ 	
				// 使用默认值
				//无midplat.xml的交易/超时时间节点,发生空指针异常，使用默认值:60秒
				cLogger.debug("未配置超时，或配置有误，使用默认值(s)：" + tTimeOut, ex);
			}
			//使用时间超出超时时间[秒]
			if (tUseTime > tTimeOut * 1000)
			{
				//抛出超时异常
				//处理超时！UseTime=使用时间[秒]s；TimeOut=超时时间s；投保书：投保单印刷号
				cLogger.error("处理超时！UseTime=" + tUseTime / 1000.0 + "s；TimeOut=" + tTimeOut + "s；投保书：" + mProposalPrtNo);
				//回滚
				rollback(); // 回滚系统数据
				//抛出中间平台异常
				throw new MidplatException("系统繁忙，请稍后再试！");
			}
			
			// 保存保单信息
			//获取保单数据库操作类实例
			ContDB tContDB = getContDB();
			//当前日期
			Date tCurDate = new Date();
			//设置入库日期为8位日期
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			//设置入库时间为6位时间
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			//设置最后修改日期为入库日期
			tContDB.setModifyDate(tContDB.getMakeDate());
			//设置最后修改时间为入库时间
			tContDB.setModifyTime(tContDB.getMakeTime());
			
			//执行保单数据库操作类插入方法失败
			if (!tContDB.insert())
			{
				//保单信息(Cont)入库失败！错误信息对象获取首个错误
				cLogger.error("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
			}
			// cTranLogDB.setContNo(tContDB.getContNo());
			//设置交易日志数据库操作类字段
			//管理机构
			cTranLogDB.setManageCom(tContDB.getManageCom());
			//代理机构
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			//代理人
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
		}
		catch (Exception ex)
		{
			//16:39:20,776 ERROR service.NewContInput(217) - 实时投保交易失败！
			//[Element:business/name]
			//[交易/交易名]交易失败！
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			//根据交易结果[失败代码]和异常信息，生成简单的标准返回报文
			//标准输出报文
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		//交易日志数据库操作类实例非空
		if (null != cTranLogDB)
		{ // 插入日志失败时cTranLogDB=null
			//标准输出报文报文头
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			//设置交易日志数据库操作类字段
			//交易结果
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			//结果描述
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			//当前时间毫秒数
			long tCurMillis = System.currentTimeMillis();
			//服务耗时[当前时间毫秒数-开始时间毫秒数][s]
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			//最后修改日期[8位日期]
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			//最后修改时间[6位时间]
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			//更新交易日志记录失败[!false=true]
			//执行交易日志数据库操作类更新方法失败
			if (!cTranLogDB.update())
			{
				//更新日志信息失败！ 错误信息对象获取首个错误
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		//Out NewContInput.service()!
		cLogger.info("Out NewContInput.service()!");
		//返回标准输出报文
		return cOutXmlDoc;
	}

	/**
	 * @Title: getContDB
	 * @Description: 获取保单数据库操作类实例
	 * @return 保单数据库操作类实例
	 * @return ContDB 保单数据库操作类实例
	 * @throws 异常
	 */
	private ContDB getContDB()
	{
		cLogger.debug("Into NewContInput.getContDB()...");
		
		//获取标准输入报文节点
		//报文体
		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
		//险种
		Element mInRiskEle = mInBodyEle.getChild(Risk);
		//投保人
		Element inAppntEle = mInBodyEle.getChild(Appnt);
		//受益人
		Element inBnf = mInBodyEle.getChild(Bnf);
		
		//获取标准输出报文节点
		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mOutAppntEle = mOutBodyEle.getChild(Appnt);
		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk); // 前面已经做排序了，第一个节点就是主险信息
		
		//构建保单数据库操作类实例
		ContDB mContDB = new ContDB();
		//设置保单数据库操作类实例字段值为元素文本内容
		//记录号
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		//保单类型
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		//保单号
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
		// System.out.println("mOutBodyEle.getChildText(ProposalPrtNo):"+mOutBodyEle.getChildText(ContNo));
		// JdomUtil.print(cOutXmlDoc);
		//投保单(印刷)号
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
		// 产品号
		mContDB.setProductId(mInBodyEle.getChildText(ProductId));
		//交易机构
		mContDB.setTranCom(cTranLogDB.getTranCom());
		//交易网点
		mContDB.setNodeNo(cTranLogDB.getNodeNo());
		//代理机构
		mContDB.setAgentCom(mOutBodyEle.getChildText(AgentCom));
		//代理机构名称 
		mContDB.setAgentComName(mOutBodyEle.getChildText(AgentComName));
		//代理人
		mContDB.setAgentCode(mOutBodyEle.getChildText(AgentCode));
		//代理人姓名
		mContDB.setAgentName(mOutBodyEle.getChildText(AgentName));
		//管理机构 
		mContDB.setManageCom(mOutBodyEle.getChildText(ComCode));
		//投保人客户号
		mContDB.setAppntNo(mOutAppntEle.getChildText(CustomerNo));
		//投保人姓名
		mContDB.setAppntName(mOutAppntEle.getChildText(Name));
		//投保人性别
		mContDB.setAppntSex(mOutAppntEle.getChildText(Sex));
		//投保人出生日期
		mContDB.setAppntBirthday(mOutAppntEle.getChildText(Birthday));
		//投保人证件类型
		mContDB.setAppntIDType(mOutAppntEle.getChildText(IDType));
		//投保人证件号码
		mContDB.setAppntIDNo(mOutAppntEle.getChildText(IDNo));
		//被保人客户号
		mContDB.setInsuredNo(mOutInsuredEle.getChildText(CustomerNo));
		//被保人姓名
		mContDB.setInsuredName(mOutInsuredEle.getChildText(Name));
		//被保人性别
		mContDB.setInsuredSex(mOutInsuredEle.getChildText(Sex));
		//被保人出生日期
		mContDB.setInsuredBirthday(mOutInsuredEle.getChildText(Birthday));
		//被保人证件类型
		mContDB.setInsuredIDType(mOutInsuredEle.getChildText(IDType));
		//被保人证件号码
		mContDB.setInsuredIDNo(mOutInsuredEle.getChildText(IDNo));
		//交易日期
		mContDB.setTranDate(cTranLogDB.getTranDate());
		//投保日期
		mContDB.setPolApplyDate(mOutMainRiskEle.getChildText(PolApplyDate));
		//保费[分]
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		//保额
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		//保单状态
		mContDB.setState(AblifeCodeDef.ContState_Input);
		//备用1
		mContDB.setBak1(mOutMainRiskEle.getChildText(MainRiskCode));
		//备用2
		mContDB.setBak2(inAppntEle.getChildText("RelaToInsured2"));// 被保人与投保人关系，银行代码
		//标准输出报文体获取投保单印刷号文本内容,被保人与投保人关系,银行代码:投保人与被保人关系2
		cLogger.info(mOutBodyEle.getChildText(ProposalPrtNo) + ",被保人与投保人关系,银行代码:" + mOutAppntEle.getChildText("RelaToInsured2"));
		
		//储存保单缴费账号
		//备用4
		mContDB.setBak4(mInBodyEle.getChildText(AccNo));
		
		//受益人非空
		if (inBnf != null)
		{
			//设置备用3为受益人与被保人关系3
			mContDB.setBak3(inBnf.getChildText("RelaToInsured3"));// 受益人与被保人关系，银行代码
		}
		//一级分行号：[] 套餐编号：[]
		System.out.println("一级分行号：" + mInBodyEle.getChildText("Lv1BrNo") + "  " + "套餐编号：" + mInRiskEle.getChildText("AgInsPkgID"));
		//设置备用10为一级分行号
		mContDB.setBak10(mInBodyEle.getChildText("Lv1BrNo"));
		//设置备用9为套餐编号
		mContDB.setBak9(mInRiskEle.getChildText("AgInsPkgID"));
		//设置操作员为sys
		mContDB.setOperator(CodeDef.SYS);
		//Out NewContInput.getContDB()!
		cLogger.debug("Out NewContInput.getContDB()!");
		//返回保单数据库操作类实例
		return mContDB;
	}
	
	/**
	 * @Title: rollback
	 * @Description: 回滚
	 * @return void
	 * @throws 异常
	 */
	private void rollback()
	{
		//Into NewContInput.rollback()...
		cLogger.debug("Into NewContInput.rollback()...");
		
		//标准输入报文根节点
		Element mInRootEle = cInXmlDoc.getRootElement();
		//报文体
		Element mInBodyEle = mInRootEle.getChild(Body);
		//
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent((Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent((Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent((Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);

		try
		{
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		}
		catch (Exception ex)
		{
			cLogger.error("回滚数据失败！", ex);
		}

		cLogger.debug("Out NewContInput.rollback()!");
	}

	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 *             create by zhj 2010 11 05 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException
	{

		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");

		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3);
		cLogger.info("authority-->" + sAgentCom);
		cLogger.info("authority-->" + sAgentCode);
		if ((("" == sAgentCom) || (sAgentCom == null)) && (("" == sAgentCode) || (sAgentCode == null)))
		{
			throw new MidplatException("此网点不存在，请确认！");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
	}
}
