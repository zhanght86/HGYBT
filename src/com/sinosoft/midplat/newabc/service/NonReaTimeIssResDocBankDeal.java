///非实时出单结果文件-银行处理结果回盘
package com.sinosoft.midplat.newabc.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContBlcDtlDB;
import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class NonReaTimeIssResDocBankDeal extends ServiceImpl
{
	public NonReaTimeIssResDocBankDeal(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc)
	{
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NonReaTimeIssResDocBankDeal.service()...");
		cInXmlDoc = pInXmlDoc;
		JdomUtil.print(cInXmlDoc);

		try
		{
			cTranLogDB = insertTranLog(cInXmlDoc);

			// 处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr)
			{
				throw new MidplatException(tErrorStr);
			}

			JdomUtil.print(cInXmlDoc);

			// 保存对账明细
			saveDetails(cInXmlDoc);

			Element tTranData = new Element(TranData);

			Element tHead = new Element(Head);
			Element tFlag = new Element(Flag);
			tFlag.setText("0");
			Element tDesc = new Element(Desc);
			tDesc.setText("非实时出单回盘交易成功");
			Element tBody = new Element(Body);

			tTranData.addContent(tHead);
			tTranData.addContent(tBody);

			tHead.addContent(tFlag);
			tHead.addContent(tDesc);

			cOutXmlDoc = new Document(tTranData);

			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // 交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		}
		catch (MidplatException ex)
		{
			cLogger.info(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		catch (Exception ex)
		{
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}

		if (null != cTranLogDB)
		{ // 插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag)); // -1-未返回；0-交易成功，返回；1-交易失败，返回
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

		cLogger.info("Out NonReaTimeIssResDocBankDeal.service()!");
		return cOutXmlDoc;
	}

	/**
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception
	{
		cLogger.debug("Into NonReaTimeIssResDocBankDeal.saveDetails()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);

		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();

		for (Element tDetailEle : mDetailList)
		{
			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();

			//批次号
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			
			//保单号
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			
			//投保单号
			tContBlcDtlSchema.setProposalPrtNo(""); // 有些银行传
			
			// 退保回盘设置为24，非实时回盘类型设置为25
			tContBlcDtlSchema.setType("25"); 
			
			//交易日期
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			
			//领取金额
			tContBlcDtlSchema.setPrem(tDetailEle.getChildText(Prem));
			
			//机构码
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			
			tContBlcDtlSchema.setNodeNo("");
			
			//投保人姓名
			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName")); 
			
			//被保人姓名
			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); 
			
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			
			//操作员
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			//处理状态
			tContBlcDtlSchema.setBak5(tDetailEle.getChildText("State"));
		
			//描述信息
			tContBlcDtlSchema.setBak4(tDetailEle.getChildText("Msg"));

			mContBlcDtlSet.add(tContBlcDtlSchema);
		}

		//非实时出单结果银行回盘保存
		cLogger.info("非实时出单结果银行回盘明细总数(DtlSet)为：" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			cLogger.error("保存非实时出单对账明细失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("保存非实时出单对账明细失败！");
		}

		cLogger.debug("Out NonReaTimeIssResDocBankDeal.saveDetails()!");
		return mContBlcDtlSet;
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

		Element mAgentCom = new Element("AgentCom");
		mHeadEle.addContent(mAgentCom);
		// Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");

		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		// String tSqlStr3 = new
		// StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		// .append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		// String sAgentCode = new ExeSQL().getOneValue(tSqlStr3);
		cLogger.info("authority-->" + sAgentCom);
		// cLogger.info("authority-->"+sAgentCode);
		// / if (((""==sAgentCom)||(sAgentCom==null)) &&
		// ((""==sAgentCode)||(sAgentCode==null))){
		if (("" == sAgentCom) || (sAgentCom == null))
		{
			throw new MidplatException("此网点不存在，请确认！");
		}
		mAgentCom.setText(sAgentCom);
		// mAgentCode.setText(sAgentCode);
		return mInXmlDoc;

	}
}
