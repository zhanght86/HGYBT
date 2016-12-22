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
		JdomUtil.print(cInXmlDoc);//[Element:<TranData/>]
		Element mRootEle = cInXmlDoc.getRootElement();//核心录单自核请求报文根节点[Element: <TranData/>]
		Element mBodyEle = mRootEle.getChild(Body);//核心录单自核请求报文报文体[Element: <Body/>]
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);//投保单(印刷)号 210414131200027
		// String mContPrtNo = mBodyEle.getChildText(ContPrtNo);

		try
		{
			// System.out.println("--------------------------------------------------------------------------------------------------------");
			// JdomUtil.print(cInXmlDoc);
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
			if (!"".equals(mApptRelationShip))
			{
				if ("--".equals(mApptRelationShip))
				{
					throw new MidplatException("投保人与被保人关系有误！");
				}
			}
			
			
			List<Element> tBnf = mBodyEle.getChildren("Bnf");//[[Element: <Bnf/>], [Element: <Bnf/>]]
			//									2
			for (int i = 0; i < tBnf.size(); i++)
			{
				String mBnfRelationShip = tBnf.get(i).getChildText("RelaToInsured");
				if (!"".equals(mBnfRelationShip))
				{
					if ("--".equals(mBnfRelationShip))
					{
						throw new MidplatException("受益人与被保人关系有误！");
					}
				}
			}
			

			new RuleParser().check(cInXmlDoc);//检查核心录单自核请求报文

			
			cLogger.info("------进入收益比例校验20140807------");//16:19:44,892 INFO service.NewContInput(106) - ------进入收益比例校验20140807------
			YBTDataVerification verification = new YBTDataVerification();
			//					true
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);//同一受益顺序受益人验证核心录单自核请求报文
			if (GradeFlag == false)
			{
				throw new MidplatException("同一受益顺序受益份额之和不等于1！请确认");
			}

			try
			{
				//																								银保录单核保					核心录单自核请求报文
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
			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{
				String desc = tOutHeadEle.getChildText(Desc);
				cLogger.info("错误描述长度：" + desc.length());
				if (desc.length() > 90)
				{
					desc = desc.substring(0, 60) + "......)";
					cLogger.info("处理后错误描述：" + desc);
				}
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
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60; // 默认超时设置为1分钟；如果未配置超时时间，则使用该值。
			try
			{
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			}
			catch (Exception ex)
			{ // 使用默认值
				cLogger.debug("未配置超时，或配置有误，使用默认值(s)：" + tTimeOut, ex);
			}
			if (tUseTime > tTimeOut * 1000)
			{
				cLogger.error("处理超时！UseTime=" + tUseTime / 1000.0 + "s；TimeOut=" + tTimeOut + "s；投保书：" + mProposalPrtNo);
				rollback(); // 回滚系统数据
				throw new MidplatException("系统繁忙，请稍后再试！");
			}

			// 保存保单信息
			ContDB tContDB = getContDB();
			Date tCurDate = new Date();
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			tContDB.setModifyDate(tContDB.getMakeDate());
			tContDB.setModifyTime(tContDB.getMakeTime());


			if (!tContDB.insert())
			{
				cLogger.error("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
			}
			// cTranLogDB.setContNo(tContDB.getContNo());
			cTranLogDB.setManageCom(tContDB.getManageCom());
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
		}
		catch (Exception ex)
		{
			//16:39:20,776 ERROR service.NewContInput(217) - 实时投保交易失败！
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);

			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}

		if (null != cTranLogDB)
		{ // 插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		//Out NewContInput.service()!
		cLogger.info("Out NewContInput.service()!");
		return cOutXmlDoc;
	}

	private ContDB getContDB()
	{
		cLogger.debug("Into NewContInput.getContDB()...");

		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
		Element mInRiskEle = mInBodyEle.getChild(Risk);
		Element inAppntEle = mInBodyEle.getChild(Appnt);
		Element inBnf = mInBodyEle.getChild(Bnf);
		

		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mOutAppntEle = mOutBodyEle.getChild(Appnt);
		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk); // 前面已经做排序了，第一个节点就是主险信息

		ContDB mContDB = new ContDB();
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
		// System.out.println("mOutBodyEle.getChildText(ProposalPrtNo):"+mOutBodyEle.getChildText(ContNo));
		// JdomUtil.print(cOutXmlDoc);
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
		mContDB.setProductId(mInBodyEle.getChildText(ProductId));
		mContDB.setTranCom(cTranLogDB.getTranCom());
		mContDB.setNodeNo(cTranLogDB.getNodeNo());
		mContDB.setAgentCom(mOutBodyEle.getChildText(AgentCom));
		mContDB.setAgentComName(mOutBodyEle.getChildText(AgentComName));
		mContDB.setAgentCode(mOutBodyEle.getChildText(AgentCode));
		mContDB.setAgentName(mOutBodyEle.getChildText(AgentName));
		mContDB.setManageCom(mOutBodyEle.getChildText(ComCode));
		mContDB.setAppntNo(mOutAppntEle.getChildText(CustomerNo));
		mContDB.setAppntName(mOutAppntEle.getChildText(Name));
		mContDB.setAppntSex(mOutAppntEle.getChildText(Sex));
		mContDB.setAppntBirthday(mOutAppntEle.getChildText(Birthday));
		mContDB.setAppntIDType(mOutAppntEle.getChildText(IDType));
		mContDB.setAppntIDNo(mOutAppntEle.getChildText(IDNo));
		mContDB.setInsuredNo(mOutInsuredEle.getChildText(CustomerNo));
		mContDB.setInsuredName(mOutInsuredEle.getChildText(Name));
		mContDB.setInsuredSex(mOutInsuredEle.getChildText(Sex));
		mContDB.setInsuredBirthday(mOutInsuredEle.getChildText(Birthday));
		mContDB.setInsuredIDType(mOutInsuredEle.getChildText(IDType));
		mContDB.setInsuredIDNo(mOutInsuredEle.getChildText(IDNo));
		mContDB.setTranDate(cTranLogDB.getTranDate());
		mContDB.setPolApplyDate(mOutMainRiskEle.getChildText(PolApplyDate));
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		mContDB.setState(AblifeCodeDef.ContState_Input);
		mContDB.setBak1(mOutMainRiskEle.getChildText(MainRiskCode));
		mContDB.setBak2(inAppntEle.getChildText("RelaToInsured2"));// 被保人与投保人关系，银行代码
		cLogger.info(mOutBodyEle.getChildText(ProposalPrtNo) + ",被保人与投保人关系,银行代码:" + mOutAppntEle.getChildText("RelaToInsured2"));
		
		//储存保单缴费账号
		mContDB.setBak4(mInBodyEle.getChildText(AccNo));
		
		if (inBnf != null)
		{
			mContDB.setBak3(inBnf.getChildText("RelaToInsured3"));// 受益人与被保人关系，银行代码
		}
		System.out.println("一级分行号：" + mInBodyEle.getChildText("Lv1BrNo") + "  " + "套餐编号：" + mInRiskEle.getChildText("AgInsPkgID"));
		mContDB.setBak10(mInBodyEle.getChildText("Lv1BrNo"));
		mContDB.setBak9(mInRiskEle.getChildText("AgInsPkgID"));
		mContDB.setOperator(CodeDef.SYS);

		cLogger.debug("Out NewContInput.getContDB()!");
		return mContDB;
	}
	

	private void rollback()
	{
		cLogger.debug("Into NewContInput.rollback()...");

		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
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
