//建行犹豫期退保申请
package com.sinosoft.midplat.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.CheckAgentCom;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class NewContCancelBlc extends ServiceImpl
{
	public NewContCancelBlc(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc)
	{
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContCancelBlc.service()...");
		long mUsedContConfirm = 0;
		cInXmlDoc = pInXmlDoc;
		try
		{
			cTranLogDB = insertTranLog(cInXmlDoc);

			// 处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr)
			{
				throw new MidplatException(tErrorStr);
			}

			long mStartContConfirm = System.currentTimeMillis();
			// 核心未开发此交易，故此处不掉核心，直接封装(没有业务数据)报文返回给银行 --modify by fzg 20130328
			// cOutXmlDoc=CancelBlcStdxml.call(cInXmlDoc);
			// 封装开始
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "交易成功！");
			// 封装结束

			mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			cLogger.info("----------Timekeeping---------->调用后置机花费时间为:" + String.valueOf(mUsedContConfirm));

			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // 交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		}
		catch (MidplatException ex)
		{
			cLogger.info(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			if ((ex.getMessage() != null))
			{
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			}
		}
		catch (Exception ex)
		{
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB)
		{ // 插入日志失败时cTranLogDB=null
			Element iHeadEle = cInXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setAgentComName(iHeadEle.getChildText("AgentComName"));
			cTranLogDB.setAgentName(iHeadEle.getChildText("AgentName"));
			cTranLogDB.setAgentCodeGrade(iHeadEle.getChildText("AgentCodeGrade"));
			cTranLogDB.setUnitCode(iHeadEle.getChildText("UnitCode"));
			if (iHeadEle.getChildText("ManageCom") == null || "".equals(iHeadEle.getChildText("ManageCom")))
			{
				cTranLogDB.setManageCom("86");
			}
			else
			{
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
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			cTranLogDB.setBak3(String.valueOf((tCurMillis - mStartMillis) / 1000.0));
			cTranLogDB.setBak4(String.valueOf(mUsedContConfirm / 1000.0));
			if (!cTranLogDB.update())
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewContCancelBlc.service()!");
		return cOutXmlDoc;
	}

	/**
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception
	{
		cLogger.debug("Into NewContCancelBlc.saveDetails()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);

		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
		// long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		if (mDetailList.size() != mCount)
		{
			throw new MidplatException("汇总笔数与明细笔数不符！" + mCount + "!=" + mDetailList.size());
		}
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList)
		{
			// mSumDtlPrem += Integer.parseInt(tDetailEle.getChildText(Prem));
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));

			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo)); // 有些银行传
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName")); // 有些银行传
			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); // 有些银行传
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);

			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		if (mSumPrem != mSumDtlPrem)
		{
			throw new MidplatException("汇总金额与明细总金额不符！" + mSumPrem + "!=" + mSumDtlPrem);
		}

		/**
		 * 将银行发过来的对账明细存储到对账明细表(ContBlcDtl)中
		 */
		cLogger.info("对账明细总数(DtlSet)为：" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			cLogger.error("保存对账明细失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("保存对账明细失败！");
		}

		cLogger.debug("Out NewContCancelBlc.saveDetails()!");
		return mContBlcDtlSet;
	}

}
