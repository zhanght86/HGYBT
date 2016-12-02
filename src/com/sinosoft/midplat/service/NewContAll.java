/**
 * 录单核保+收费签单，针对工行新契约
 */

package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;


import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
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
import com.sinosoft.utility.ExeSQL;

public class NewContAll extends ServiceImpl {
	public NewContAll(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {

		//mStartMillis 计算NewContAll起始时间
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContAll.service()...");
		cInXmlDoc = pInXmlDoc;  
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		Element mBodyEle = mRootEle.getChild(Body); 
		   
		String mTellerNo = mHeadEle.getChildText(TellerNo);  
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContPrtNo = mBodyEle.getChildText(ContPrtNo);
		String mTranCom = mHeadEle.getChildText("TranCom");
		String mSaleChannel = mBodyEle.getChildText("SaleChannel");
		String mMult = mBodyEle.getChild("Risk").getChildText("Mult");
		String mRiskCode = mBodyEle.getChild("Risk").getChildText("RiskCode");
		cLogger.info("投保单(印刷)号为:"+mProposalPrtNo);
		cLogger.info("单证印刷号为:"+mContPrtNo);
		 	
		try {
			cLogger.info("Into NewContAll.service()...-->insertTranLog(cInXmlDoc)插入日志");	
			JdomUtil.print(cInXmlDoc);
			cTranLogDB = insertTranLog(cInXmlDoc); 
			
		cLogger.info("Into NewContAll.service()...-->authority(cInXmlDoc)网点与权限 添加代理");	
		//add by zhj 网点与权限 添加代理
//		cInXmlDoc = authority(cInXmlDoc); 	
		//add by zhj 网点与权限 添加代理end 
			

		cLogger.info("Into NewContAll.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
		AuthorityCheck authorityCheck = new AuthorityCheck();
		if(!authorityCheck.submitData(mHeadEle)){
			throw new MidplatException("该网点无权限！");
		} 
		 
		//20150226 处理非柜台智赢C的份数错误提示
		if(!"0".equals(mSaleChannel)&&"0".equals(mMult)&&"231204".equals(mRiskCode)){
			throw new MidplatException("智赢C产品投保份数需大于0！");
		} 
			 
			//校验系统中是否有相同保单正在处理，尚未返回
			//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			int tLockTime = 300;	
			try { 
				cLogger.info("-----配置的锁定时间为:"+Integer.parseInt(cThisBusiConf.getChildText(locktime)));
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			} 
			
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			
			if("0".equals(mSaleChannel)){//柜台
		
				cLogger.info("查询这个印刷号保单的状态");
	            cLogger.info(tCurCalendar.getTime());
				String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
					.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
					//.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
					.append(" and TranDate=").append(DateUtil.get8Date(tCurCalendar))
					.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
					.toString();
				if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
					throw new MidplatException("此保单数据正在处理中，请稍候！");
				}
			}
			//JdomUtil.print(cInXmlDoc);
			cLogger.info("Into NewContAll.service()...-->RuleParser().check(cInXmlDoc)校验");
			long mStartRuleParser = System.currentTimeMillis();
			new RuleParser().check(cInXmlDoc);
			long mUsedRuleParser = (System.currentTimeMillis() - mStartRuleParser);
			cLogger.info("----------Timekeeping---------->RuleParser().check(cInXmlDoc)校验时间为:"+String.valueOf(mUsedRuleParser));
			cTranLogDB.setBak1(String.valueOf(mUsedRuleParser/1000.0));
			//add by zhanghj   
			long mStartYBTDataVerification = System.currentTimeMillis();
			
			String riskCode  = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
				
			if(!("211901".equals(riskCode)||("211902".equals(riskCode)))){
				YBTDataVerification verification = new YBTDataVerification();
				boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);
				if(GradeFlag==false){
					throw new MidplatException("同一受益顺序受益份额之和不等于1！请确认");
				}
				boolean digitFlag = verification.digitBnfVerification(cInXmlDoc);
				if(digitFlag==false){ 
					throw new MidplatException("受益顺序不能跳号！请确认");
				}  
			}
			
			//20141107对于工行插入渠道标志，0柜面，1网银，8自助终端
			if(mTranCom.equals("01")){
				cTranLogDB.setBak5(mBodyEle.getChildText("SaleChannel"));
			}
			
			long mUsedYBTDataVerification = (System.currentTimeMillis() - mStartYBTDataVerification);
			cLogger.info("----------Timekeeping---------->YBTDataVerification校验时间为:"+String.valueOf(mUsedYBTDataVerification));
			cTranLogDB.setBak2(String.valueOf(mUsedYBTDataVerification/1000.0));
			//add end 			   
						 
			cLogger.info("Into NewContAll.service()...-->入单核保CallWebsvcAtomSvc");	
			long mStartContInput = System.currentTimeMillis();
		    cOutXmlDoc= new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);
			JdomUtil.print(cOutXmlDoc);
			long mUsedContInput = (System.currentTimeMillis() - mStartContInput);
			cLogger.info("----------Timekeeping---------->入单核保CallWebsvcAtomSvc花费时间为:"+String.valueOf(mUsedContInput));
			cTranLogDB.setBak3(String.valueOf(mUsedContInput/1000.0));
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
					
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			} 
			 
			/*
			 * 是否是非实时返回---Flag为2,是0表示是实时返回，是成功的，继续调用签单的服务，else，返回Flag为2的非实时报文。
			 */
			if (CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
							
			//调用收费签单接口  
			Element tBodyEle = new Element(Body);
			tBodyEle.addContent(
					(Element) mBodyEle.getChild(ProposalPrtNo).clone());
			tBodyEle.addContent(
					(Element) mBodyEle.getChild(ContPrtNo).clone());
			tBodyEle.addContent(
					(Element) tOutBodyEle.getChild(ContNo).clone());
			
			Element tTranDataEle = new Element(TranData);
					
			 
			tTranDataEle.addContent(mHeadEle);
			tTranDataEle.addContent(tBodyEle);
			 
			cLogger.info("Into NewContAll.service()...-->收费签单CallWebsvcAtomSvc");		
			long mStartContConfirm = System.currentTimeMillis();
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContConfirm).call(new Document(tTranDataEle));
			long mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			cLogger.info("----------Timekeeping---------->收费签单CallWebsvcAtomSvc花费时间为:"+String.valueOf(mUsedContConfirm));
			cTranLogDB.setBak4(String.valueOf(mUsedContConfirm/1000.0));
			tOutRootEle = cOutXmlDoc.getRootElement();
			tOutHeadEle = tOutRootEle.getChild(Head); 
			tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			} 
			
			//核心不存保单印刷号，用请求报文对应值覆盖
			//tOutBodyEle.getChild(ContPrtNo).setText(mContPrtNo);
			
			//核心可能将一个产品的两个险种都定义为主险，而银行则认为一主一附，以银行报文为准，覆盖核心记录?
			/*String tMainRiskCode = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
			List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			int tSize = tRiskList.size();
			for (int i = 0; i < tSize; i++) {
				Element ttRiskEle = tRiskList.get(i);
				ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
				
				if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode))) {
					tRiskList.add(0, tRiskList.remove(i));	//将主险调整到最前面
				}
			}*/
			
			//超时自动删除数据
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60;	//默认超时设置为1分钟；如果未配置超时时间，则使用该值。
			try {   
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置超时，或配置有误，使用默认值(s)："+tTimeOut, ex);
			}
			if (tUseTime > tTimeOut*1000) {
				cLogger.error("处理超时！UseTime=" + tUseTime/1000.0 + "s；TimeOut=" + tTimeOut + "s；投保书：" + mProposalPrtNo);
				rollback();	//回滚系统数据
				throw new MidplatException("系统繁忙，请稍后再试！");
			}
			 
			//保存保单信息
			ContDB tContDB = getContDB(mTellerNo);
			Date tCurDate = new Date();
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			tContDB.setModifyDate(tContDB.getMakeDate());
			tContDB.setModifyTime(tContDB.getMakeTime());
			if (!tContDB.insert()) {
				cLogger.error("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
				throw new MidplatException("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
			} 
			cTranLogDB.setContNo(tContDB.getContNo());
			cTranLogDB.setManageCom(tContDB.getManageCom());
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
			}else{//非实时返回的报文
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_RenHe, tOutHeadEle.getChildText(Desc));
			}
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out NewContAll.service()!");
		return cOutXmlDoc;
	}
	
	private ContDB getContDB(String mTellerNo) {
		cLogger.debug("Into NewContAll.getContDB()...");
		
		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
		Element mInRiskEle = mInBodyEle.getChild(Risk);
		
		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mOutAppntEle = mOutBodyEle.getChild(Appnt);
		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk);	//前面已经做排序了，第一个节点就是主险信息
		
		ContDB mContDB = new ContDB();
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
//		mContDB.setProductId(mInRiskEle.getChildText(MainRiskCode));
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
		mContDB.setSignDate(mOutMainRiskEle.getChildText(SignDate));
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		mContDB.setState(AblifeCodeDef.ContState_Sign);
		mContDB.setBak1(mInRiskEle.getChildText(MainRiskCode));
		if(mTellerNo!=""||(!mTellerNo.equals(""))){
			mContDB.setBak2(mTellerNo);
		}
		mContDB.setOperator(CodeDef.SYS);
		
		if("01".equals(mContDB.getTranCom())){//20150204lilu 
			mContDB.setBak10(cTranLogDB.getBak5());
		}
		
		cLogger.debug("Out NewContAll.getContDB()!");
		return mContDB;
	}
	
	private void rollback() {
		cLogger.debug("Into NewContAll.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.removeChild(ServiceId);
		//mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContCancel);
		Element mBodyEle = new Element(Body); 
		mBodyEle.addContent( 
				(Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent(
				(Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);
//		JdomUtil.print(mInXmlDoc);
		try {
			
			new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCancel).call(mInXmlDoc);
//			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("回滚数据失败！", ex);
		}
		
		cLogger.debug("Out NewContAll.getContDB()!");
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
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);   
		if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
			throw new MidplatException("此网点不存在，请确认！");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode); 
		return mInXmlDoc;
	}
}
