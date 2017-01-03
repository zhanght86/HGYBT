package com.sinosoft.midplat.gzbank.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.sinosoft.midplat.gzbank.util.SaveMessage;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class NewContInput extends ServiceImpl {
	public NewContInput(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	/**
	 * 业务处理
	 * @param pInXmlDoc 输入XML文件
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) throws Exception {
		//开始毫秒数
		long mStartMillis = System.currentTimeMillis();
		//Into NewContInput.service()...
		cLogger.info("Into NewContInput.service()...");
		//输入XML文件根节点
		Element mRootEle = pInXmlDoc.getRootElement();
		//输入XML文件报文头
		Element mHeadEle=mRootEle.getChild("Head");
		//输入XML文件报文体
		Element mBodyEle = mRootEle.getChild(Body);
		
		try {
			//保存核心报文[交易流水号_交易类型_年月日时分秒.xml]
			StringBuffer mSaveName = new StringBuffer(mHeadEle.getChildText("TranNo"))
			.append('_').append(mHeadEle.getChildText("FuncFlag"))
			.append('_').append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
			.append(".xml");
			//当前交易配置文件得到父元素[gzbank]的交易机构代码
			String TranCom=cThisBusiConf.getParentElement().getChildText("TranCom");
			//
		    SaveMessage.save(JdomUtil.toBytes(pInXmlDoc),TranCom,"InStd",mSaveName.toString());
		    cLogger.info("保存核心报文完毕！"+mSaveName);
		    pInXmlDoc.getRootElement().getChild("Head").getChild("InNoDoc").setText(mSaveName.toString());
		    
			cTranLogDB = insertTranLog(pInXmlDoc);
			//校验系统中是否有相同保单正在处理，尚未返回
			//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			int tLockTime = 300;	
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			//当前保单号有未处理3,且未超时的保费试算
			String sql="select count(1) from TranLog where rcode=3 and contno='"
				+pInXmlDoc.getRootElement().getChild("Body").getChildText("ProposalPrtNo")
				+"' and MakeDate<="+DateUtil.get8Date(tCurCalendar)
				+" and MakeTime<="+DateUtil.get6Time(tCurCalendar); 
			cLogger.info("保单号有未处理3,且未超时的保费试算sql:"+sql);
			if ("1".equals(new ExeSQL().getOneValue(sql))) {
				cLogger.info("此保单数据正在处理!");
				throw new MidplatException("此保单数据正在处理中，请稍候!");
			}
			//规则校验
//			new RuleParser().gzBankCheck(cInXmlDoc);
			
			Element tRiskCode  = (Element) XPath.selectSingleNode(cInXmlDoc.getRootElement(), "/TranData/Body/Risk/RiskCode");
			if("211901".equals(tRiskCode.getTextTrim())||"211902".equals(tRiskCode.getTextTrim())){//农行的借贷险用这种更细化的校验方式
				YBTDataVerification verification0 = new YBTDataVerification();
				boolean GradeFlag = verification0.SameGradeTypeBnfVerification(cInXmlDoc);
				if(GradeFlag==false){
					throw new MidplatException("同一顺序，同一类型的受益比例之和不等于1，请确认！");
				}
			}else{
			YBTDataVerification verification = new YBTDataVerification();
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);
			if(GradeFlag==false){
				throw new MidplatException("同一受益顺序受益份额之和不等于1！请确认");
			}
			}
			
			
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContInput).call(cInXmlDoc);
			System.out.println("-----------------------------------------------");
			cLogger.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//核心不存保单印刷号，用请求报文对应值覆盖
//			tOutBodyEle.getChild(ProposalPrtNo).setText(mContPrtNo);
			
			//核心可能将一个产品的两个险种都定义为主险，而银行则认为一主一附，以银行报文为准，覆盖核心记录
			String tMainRiskCode = mBodyEle.getChild(Risk).getChildText(MainRiskCode);
			List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			int tSize = tRiskList.size();
			for (int i = 0; i < tSize; i++) {
				Element ttRiskEle = tRiskList.get(i);
				ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
				
				if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode))) {
					tRiskList.add(0, tRiskList.remove(i));	//将主险调整到最前面
				}
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
				//cLogger.error("处理超时！UseTime=" + tUseTime/1000.0 + "s；TimeOut=" + tTimeOut + "s；投保书：" + mProposalPrtNo);
				rollback();	//回滚系统数据
				throw new MidplatException("系统繁忙，请稍后再试！");
			}
			
			//保存保单信息
			ContDB tContDB = getContDB();
			Date tCurDate = new Date();
			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
			tContDB.setModifyDate(tContDB.getMakeDate());
			tContDB.setModifyTime(tContDB.getMakeTime());
			
			/**
			 * 农行冲正对Cont表的特殊处理，因为签单的时候有对单子是否存在的校验
			 * 冲正后重新路单的情况，要把之前保存的单子数据删除
			 */
//			if("05".equals(mRootEle.getChild(Head).getChildText(TranCom))){//农行的单子
//				String getContSql = "select 1 from cont where ProposalPrtno = '"+ mProposalPrtNo +"'";
//				if ("1".equals(new ExeSQL().getOneValue(getContSql))) {//该投保单之前的数据已经存在
//					String deleteContSQL = "delete from cont where ProposalPrtno = '"+ mProposalPrtNo +"'";
//					if(new ExeSQL().execUpdateSQL(deleteContSQL)){
//					cLogger.info("农行冲正后的投保单又重新录单，删除之前单子数据成功:"+mProposalPrtNo);
//					}
//				}
//			}
			
			if (!tContDB.insert()) {
				cLogger.error("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
			}
//			cTranLogDB.setContNo(tContDB.getContNo());
			cTranLogDB.setManageCom(tContDB.getManageCom());
			cTranLogDB.setAgentCom(tContDB.getAgentCom());
			cTranLogDB.setAgentCode(tContDB.getAgentCode());
			cTranLogDB.setRCode(cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag"));
			cTranLogDB.setRText(cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc"));
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			if (null != cTranLogDB) {
				cTranLogDB.setRCode(CodeDef.RCode_ERROR);
				cTranLogDB.setRText(ex.getMessage());
				
			}
			cOutXmlDoc=MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB) {
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewContInput.service()!");
		return cOutXmlDoc;
	}
	
	private ContDB getContDB() {
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
