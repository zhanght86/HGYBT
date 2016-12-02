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

public class NonReaTimeIssResDocBankDeal0323 extends ServiceImpl {
	public NonReaTimeIssResDocBankDeal0323(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NonReaTimeIssResDocBankDeal.service()...");
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
			saveDetails(cInXmlDoc);
			 

				
			//add by zhj 网点与权限 添加代理   
			//cInXmlDoc = authority(cInXmlDoc);
			//add by zhj 网点与权限 添加代理end 	 	
			
			//20140911lilu回盘交易 是去取银行端sftp的回盘文件，前置机写入tranlog，置状态为成功，表示成功接受银行回盘文件，不需要经过核心
//			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(cInXmlDoc);
			//假交易，返回成功
			
			Element tTranData=new Element(TranData);
			
			Element tHead=new Element(Head);
			Element tFlag=new Element(Flag);
			tFlag.setText("0");
			Element tDesc=new Element(Desc);
			tDesc.setText("非实时出单回盘交易成功");
			Element tBody=new Element(Body);
			
			tTranData.addContent(tHead);
			tTranData.addContent(tBody);
			
			tHead.addContent(tFlag);
			tHead.addContent(tDesc);
			
			cOutXmlDoc=new Document(tTranData);
			
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
		
		cLogger.info("Out NonReaTimeIssResDocBankDeal.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into NonReaTimeIssResDocBankDeal.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
	//	long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		if (mDetailList.size() != mCount) {
			throw new MidplatException("汇总笔数与明细笔数不符！"+ mCount + "!=" + mDetailList.size());
		}
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList) {
		//	mSumDtlPrem += Integer.parseInt(tDetailEle.getChildText(Prem));
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));
			
			String applyno=tDetailEle.getChildText("ApplyNo");
			//非实时对账时，bak1存放试算顺序号，在收到银行回盘处理时，将保单号更新到ContBlcDtl表
			String tSqlblc = new StringBuilder("select * from ContBlcDtl where bak1='").append(applyno).append('\'').toString();
			cLogger.info(tSqlblc);
			ContBlcDtlSet tContBlcDtlSet = new ContBlcDtlDB().executeQuery(tSqlblc);
			if (1 != tContBlcDtlSet.size()) {
				throw new MidplatException("查无此申请顺序号对应的投保单！");
			}
			ContBlcDtlSchema tContBlcDtlSchema = tContBlcDtlSet.get(1);
			
//			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
//			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
//			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));	//有些银行传
//			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
//			tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
//			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
//			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
//			tContBlcDtlSchema.setAppntName(tDetailEle.getChildText("AppntName"));	//有些银行传
//			tContBlcDtlSchema.setInsuredName(tDetailEle.getChildText("InsuredName")); //有些银行传
//			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
//			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setModifyTime(cTranLogDB.getMakeTime());
//			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		if (mSumPrem != mSumDtlPrem) {
			throw new MidplatException("汇总金额与明细总金额不符！"+ mSumPrem + "!=" + mSumDtlPrem);
		}
		
		/** 
		 * 将银行发过来的对账明细存储到对账明细表(ContBlcDtl)中
		 */
		cLogger.info("对账明细总数(DtlSet)为：" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "UPDATE");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			cLogger.error("更新非实时出单对账明细失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("更新非实时出单对账明细失败！");
		}
		
		cLogger.debug("Out NonReaTimeIssResDocBankDeal.saveDetails()!");
		return mContBlcDtlSet;
	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		
		Element mAgentCom = new Element("AgentCom");
		mHeadEle.addContent(mAgentCom);
	//	Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
	//	String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
//		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
	//	String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
	//	cLogger.info("authority-->"+sAgentCode);    
	///	if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
		if ((""==sAgentCom)||(sAgentCom==null)){ 
			throw new MidplatException("此网点不存在，请确认！");
		}   
		mAgentCom.setText(sAgentCom);
//		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
		
	}
}
