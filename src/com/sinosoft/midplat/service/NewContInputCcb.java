package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class NewContInputCcb extends ServiceImpl {
	public NewContInputCcb(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewContInputCcb.service()...");
		cInXmlDoc = pInXmlDoc;
		cLogger.info(cInXmlDoc);	
		Element mRootEle = cInXmlDoc.getRootElement();		
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		Element mBodyEle = mRootEle.getChild(Body);
		String mTellerNo = mHeadEle.getChildText(TellerNo); 
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContPrtNo = mBodyEle.getChildText(ContPrtNo);
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			/**
			 * 在此遍历数据库中配置的停售产品。 start 
			 */
			String  stopriskcode = mBodyEle.getChild("Risk").getChildText("RiskCode");
			if(stopriskcode.equals("")||stopriskcode==null)
			{
				throw new MidplatException("产品编码不能为空！");
			}
			String stopsql = "select riskcode from stopproduct";
			SSRS sSSRS = new SSRS();
			ExeSQL sExeSQL = new ExeSQL();
			sSSRS = sExeSQL.execSQL(stopsql);
			for(int i = 1;i<=sSSRS.MaxRow;i++)
			{
				if(stopriskcode.equals(sSSRS.GetText(i, 1)))
				{
					throw new MidplatException("该产品已停售！");
				}
			}  
			/**
			 * 在此遍历数据库中配置的停售产品。 end
			 */
			/**
			 * 建行切换专线（总对总）模式，但是有一款产品银行配置参数有误。
			 * （定制产品模板--红利领取方式，银行将其录入框默认为非必录项，但是核心有校验，此产品红利领取方式必须为累计生息）
			 * 在此设置成默认值--累积生息
			 * start modify
			 */
			if(stopriskcode.equals("313050"))
			{
				mBodyEle.getChild("Risk").getChild("BonusGetMode").setText("1");//核心1表示累积生息
			}
			/**
			 * end modify
			 */
			cLogger.info("Into NewContInputCcb.service()...-->authority(cInXmlDoc)网点与权限 添加代理");	
			//add by zhj 网点与权限 添加代理
			cInXmlDoc = authority(cInXmlDoc); 	
			//add by zhj 网点与权限 添加代理end 
			cLogger.info("Into NewContInputCcb.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
			AuthorityCheck authorityCheck = new AuthorityCheck();
			if(!authorityCheck.submitData(mHeadEle)){
				throw new MidplatException("该网点无权限！");
			} 
			//校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300;	//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
				.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
				.toString();
			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
				throw new MidplatException("此保单数据正在处理中，请稍候！");
			}
			
			new RuleParser().check(cInXmlDoc);
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);
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
			ContDB tContDB = getContDB(mTellerNo);
			Date tCurDate = new Date();
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			tContDB.setModifyDate(tContDB.getMakeDate());
			tContDB.setModifyTime(tContDB.getMakeTime());
			if (!tContDB.insert()) {
				cLogger.error("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
			}
			cTranLogDB.setContNo(tContDB.getContNo());
			cTranLogDB.setManageCom(tContDB.getManageCom());
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
		}
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}catch (Exception ex) {
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
		
		cLogger.info("Out NewContInputCcb.service()!");
		return cOutXmlDoc;
	}
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException {
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
//		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
//		mTranLogDB.setLogNo(new Integer(new Random().nextInt(90000000))+10000000);
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		if (null != mBodyEle) {
			mTranLogDB.setProposalPrtNo(mBodyEle.getChildText(ProposalPrtNo));
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			mTranLogDB.setBak2(mBodyEle.getChildText("OldLogNo"));
			if (null==mTranLogDB.getBak2() || "".equals(mTranLogDB.getBak2())) {
				mTranLogDB.setBak2(mBodyEle.getChildText("OldTranNo"));
			}
		}
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert()) {
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("插入日志失败！");
		}
		
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
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
		Element mBodyEle = (Element) mRootEle.getChild(Body);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		String mBkRckrNo = (String)mBodyEle.getChild("Risk").getChildTextTrim("BkRckrNo");
		 
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
//		else
//		{
//			mAgentCom.setText(sAgentCom);
//			if((mBkRckrNo==""||mBkRckrNo==null)||(mBkRckrNo.equals(sAgentCode)))
//			{
			    mAgentCom.setText(sAgentCom);
			    mAgentCode.setText(sAgentCode); 
//			}
//			else
//			{
//				throw new MidplatException("代理人录入错误，请确认！");
//			}
//		}
		
		return mInXmlDoc;
	}
	private ContDB getContDB(String mTellerNo) {
		cLogger.debug("Into NewContInput.getContDB()...");
		
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
		mContDB.setProductId(mInRiskEle.getChildText(MainRiskCode));
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
		mContDB.setBak1(mInRiskEle.getChildText(MainRiskCode));
		mContDB.setOperator(CodeDef.SYS);
		if(mTellerNo!=""||(!mTellerNo.equals(""))){
			mContDB.setBak2(mTellerNo);
		}
		
		cLogger.debug("Out NewContInput.getContDB()!");
		return mContDB;
	}
	
	private void rollback() {
		cLogger.debug("Into NewContInput.rollback()...");
		
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
		
		cLogger.debug("Out NewContInput.rollback()!");
	}
}
