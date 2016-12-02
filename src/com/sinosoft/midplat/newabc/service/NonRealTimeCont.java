//农行非实时出单
package com.sinosoft.midplat.newabc.service;

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

public class NonRealTimeCont extends ServiceImpl {
	public NonRealTimeCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NonRealTimeCont.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		
		try {
//			System.out.println("--------------------------------------------------------------------------------------------------------");
//			JdomUtil.print(cInXmlDoc);
			cTranLogDB = insertTranLog(cInXmlDoc);
			
//			cLogger.info("Into NewContInput.service()...-->authority(cInXmlDoc)网点与权限 添加代理");	
			//add by zhj 网点与权限 添加代理
//			cInXmlDoc = authority(cInXmlDoc); 	
			//add by zhj 网点与权限 添加代理end 
			
			//校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300;	//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			}
			
			JdomUtil.print(cInXmlDoc);
			
//			new RuleParser().check(cInXmlDoc);
			
//			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);

//			假交易，返回成功
			Element tTranData=new Element(TranData);
			
			Element tHead=new Element(Head);
			Element tFlag=new Element(Flag);
			
			Element tDesc=new Element(Desc);
			Element tBody=new Element(Body);
			
			tTranData.addContent(tHead);
			tTranData.addContent(tBody);
			
			tHead.addContent(tFlag);
			tHead.addContent(tDesc);

			
			//判断此投保单号试算时核心是否返回 需非实时出单
			String tRcodeSql = new StringBuilder("select count(1) from TranLog where funcflag='").append("1012'")
			.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
			.append(" and rcode='").append("2'")
			.toString();

			//
			if(("1").equals(new ExeSQL().getOneValue(tRcodeSql))){
				tFlag.setText("0");
				tDesc.setText("交易成功");
			}else{
				tFlag.setText("1");
				tDesc.setText("请先做新单试算！");
			}
			cOutXmlDoc=new Document(tTranData);
			
			System.out.println("-----------------------------------------------");
			cLogger.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
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
//			ContDB tContDB = getContDB();
//			Date tCurDate = new Date();
//			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
//			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
//			tContDB.setModifyDate(tContDB.getMakeDate());
//			tContDB.setModifyTime(tContDB.getMakeTime());
			
			
//			if (!tContDB.insert()) {
//				cLogger.error("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
//			}
//			cTranLogDB.setContNo(tContDB.getContNo());
//			cTranLogDB.setManageCom(tContDB.getManageCom());
//			cTranLogDB.setAgentCom(tContDB.getAgentCom());
//			cTranLogDB.setAgentCode(tContDB.getAgentCode());
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
		
		cLogger.info("Out NonRealTimeCont.service()!");
		return cOutXmlDoc;
	}
	
	private ContDB getContDB() {
		cLogger.debug("Into NonRealTimeCont.getContDB()...");
		
		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
//		Element mInRiskEle = mInBodyEle.getChild(Risk);
		
		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mInAppntEle = mInBodyEle.getChild(Appnt);
//		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
//		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk);	//前面已经做排序了，第一个节点就是主险信息
		
		ContDB mContDB = new ContDB();
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
//System.out.println("mOutBodyEle.getChildText(ProposalPrtNo):"+mOutBodyEle.getChildText(ContNo));
//JdomUtil.print(cOutXmlDoc);
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
		mContDB.setProductId(mInBodyEle.getChildText(ProductId));
		mContDB.setTranCom(cTranLogDB.getTranCom());
		mContDB.setNodeNo(cTranLogDB.getNodeNo());
		mContDB.setAgentCom(mOutBodyEle.getChildText(AgentCom));
		mContDB.setAgentComName(mOutBodyEle.getChildText(AgentComName));
		mContDB.setAgentCode(mOutBodyEle.getChildText(AgentCode));
		mContDB.setAgentName(mOutBodyEle.getChildText(AgentName));
		mContDB.setManageCom(mOutBodyEle.getChildText(ComCode));
//		mContDB.setAppntNo(mOutAppntEle.getChildText(CustomerNo));
		mContDB.setAppntName(mInAppntEle.getChildText(Name));
		mContDB.setAppntSex(mInAppntEle.getChildText(Sex));
		mContDB.setAppntBirthday(mInAppntEle.getChildText(Birthday));
		mContDB.setAppntIDType(mInAppntEle.getChildText(IDType));
		mContDB.setAppntIDNo(mInAppntEle.getChildText(IDNo));
//		mContDB.setInsuredNo(mOutInsuredEle.getChildText(CustomerNo));
//		mContDB.setInsuredName(mOutInsuredEle.getChildText(Name));
//		mContDB.setInsuredSex(mOutInsuredEle.getChildText(Sex));
//		mContDB.setInsuredBirthday(mOutInsuredEle.getChildText(Birthday));
//		mContDB.setInsuredIDType(mOutInsuredEle.getChildText(IDType));
//		mContDB.setInsuredIDNo(mOutInsuredEle.getChildText(IDNo));
		mContDB.setTranDate(cTranLogDB.getTranDate());
//		mContDB.setPolApplyDate(mOutMainRiskEle.getChildText(PolApplyDate));
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		mContDB.setState(AblifeCodeDef.ContState_Input);
//		mContDB.setBak1(mInRiskEle.getChildText(MainRiskCode));
		mContDB.setBak8(mInBodyEle.getChildText("ApplyNo"));
		mContDB.setOperator(CodeDef.SYS);
		
		cLogger.debug("Out NonRealTimeCont.getContDB()!");
		return mContDB;
	}
	
	private void rollback() {
		cLogger.debug("Into NonRealTimeCont.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
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
		
		try {
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("回滚数据失败！", ex);
		}
		
		cLogger.debug("Out NonRealTimeCont.rollback()!");
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
