//退保犹撤数据文件-银行处理结果回盘
package com.sinosoft.midplat.newabc.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
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

public class SurRetrDataDocBankDeal_2 extends ServiceImpl {
	public SurRetrDataDocBankDeal_2(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into SurRetrDataDocBankDeal.service()...");
		cInXmlDoc = pInXmlDoc; 
	   JdomUtil.print(cInXmlDoc);
		
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			
//			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
//				.append(" and TranDate=").append(cTranLogDB.getTranDate())
//				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
//				.append(" and TranCom=").append(cTranLogDB.getTranCom())
//				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
//				.toString();
//			ExeSQL tExeSQL = new ExeSQL();
//			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
//				throw new MidplatException("已成功做过新保承保保单对账，不能重复操作！");
//			} else if (tExeSQL.mErrors.needDealError()) {
//				throw new MidplatException("查询历史对账信息异常！");
//			} 
			
			//处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			JdomUtil.print(cInXmlDoc);
			
			//保存对账明细
//			saveDetails(cInXmlDoc);
			 
			//add by zhj 网点与权限 添加代理   
			//cInXmlDoc = authority(cInXmlDoc);
			//add by zhj 网点与权限 添加代理end 	 		
			
			//20140911lilu回盘交易 是去取银行端sftp的回盘文件，前置机写入tranlog，置状态为成功，表示成功接受银行回盘文件，不需要经过核心
			//20150323lilu回盘交易 需要调核心服务，告诉核心哪些记录核心处理成功而银行处理失败，要求核心在次日退保犹退结果文件里把这条记录加进去
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NoTakenBlcBankRst).call(cInXmlDoc);
			
			//假交易，返回成功
//			Element tTranData=new Element(TranData);
//			
//			Element tHead=new Element(Head);
//			Element tFlag=new Element(Flag);
//			tFlag.setText("0");
//			Element tDesc=new Element(Desc);
//			tDesc.setText("犹撤回盘交易成功");
//			Element tBody=new Element(Body);
//			
//			tTranData.addContent(tHead);
//			tTranData.addContent(tBody);
//			
//			tHead.addContent(tFlag);
//			tHead.addContent(tDesc);
//			
//			cOutXmlDoc=new Document(tTranData);
			
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		catch (Exception ex) {
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
		
		cLogger.info("Out SurRetrDataDocBankDeal.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
//	@SuppressWarnings("unchecked")
//	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception {
//		cLogger.debug("Into SurRetrDataDocBankDeal.saveDetails()...");
//		
//		Element mTranDataEle = pXmlDoc.getRootElement();
//		Element mBodyEle = mTranDataEle.getChild(Body);
//		
//		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
//	//	long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
//		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
//		List<Element> mDetailList = mBodyEle.getChildren(Detail);
//		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
//		if (mDetailList.size() != mCount) {
//			throw new MidplatException("汇总笔数与明细笔数不符！"+ mCount + "!=" + mDetailList.size());
//		}
//		double mSumDtlPrem = 0;
//		for (Element tDetailEle : mDetailList) {
//		//	mSumDtlPrem += Integer.parseInt(tDetailEle.getChildText(Prem));
//			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));
//			
//			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
//			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
//			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
//			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));	//有些银行传
//			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
//			tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
//			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
//			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
//			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName"));	//有些银行传
//			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); //有些银行传
//			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
//			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
//			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
//			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
//			tContBlcDtlSchema.setOperator(CodeDef.SYS);
//			
//			mContBlcDtlSet.add(tContBlcDtlSchema);
//		}
//		if (mSumPrem != mSumDtlPrem) {
//			throw new MidplatException("汇总金额与明细总金额不符！"+ mSumPrem + "!=" + mSumDtlPrem);
//		}
//		
//		/** 
//		 * 将银行发过来的对账明细存储到对账明细表(ContBlcDtl)中
//		 */
//		cLogger.info("对账明细总数(DtlSet)为：" + mContBlcDtlSet.size());
//		MMap mSubmitMMap = new MMap();
//		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
//		VData mSubmitVData = new VData();
//		mSubmitVData.add(mSubmitMMap);
//		PubSubmit mPubSubmit = new PubSubmit();
//		if (!mPubSubmit.submitData(mSubmitVData, "")) {
//			cLogger.error("保存对账明细失败！" + mPubSubmit.mErrors.getFirstError());
//			throw new MidplatException("保存对账明细失败！");
//		}
//		
//		cLogger.debug("Out SurRetrDataDocBankDeal.saveDetails()!");
//		return mContBlcDtlSet;
//	}
	
}
