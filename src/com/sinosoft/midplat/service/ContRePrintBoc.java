package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class ContRePrintBoc extends ServiceImpl {
	public ContRePrintBoc(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContRePrintBoc.service()...");
		cInXmlDoc = pInXmlDoc;
		 
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();

		Element ProposalPrtNo = new Element("ProposalPrtNo");
		Element OldContPrtNo = new Element("OldContPrtNo");
		String mTranCom = mHeadEle.getChildText("TranCom");
		String mContNo = mBodyEle.getChildText(ContNo);
		String sql = "select ProposalPrtNo from cont where trancom = '"+mTranCom+"' and contno = '"+mContNo+"'";
		ExeSQL dExeSQL = new ExeSQL();
		String mProposalPrtNo = dExeSQL.getOneValue(sql);
		//如果冲重打过此保单，那么第二次重打则取第一次重打时候的单证号
		String sql1 = "select otherno from tranlog where trancom = '"+mTranCom+"' and ProposalPrtNo = '"+mProposalPrtNo+"' and  funcflag = '102' and rtext= '交易成功！'";
		String motherno1 = dExeSQL.getOneValue(sql1);
		cLogger.info("第一次重打的单证号:"+motherno1);
		//如果motherno2为空 则说明没有重打过，则取签单时候的单证号
		if(motherno1.equals("")){
			String sql2 = "select otherno from tranlog where trancom = '"+mTranCom+"' and ProposalPrtNo = '"+mProposalPrtNo+"' and funcflag = '1014' and rtext= '交易成功！'";
			String motherno = dExeSQL.getOneValue(sql2);
			cLogger.info("没有重打过此保单，签单的单证号:"+motherno);
			mBodyEle.addContent(OldContPrtNo.setText(motherno));
		}
		else 
		{
			mBodyEle.addContent(OldContPrtNo.setText(motherno1));
		}
		mBodyEle.addContent(ProposalPrtNo.setText(mProposalPrtNo));
//	    String sql1 = "select ProposalPrtNo from cont where contno = '"+mContNo+"' and trancom = '3'";
//	    ExeSQL dExeSQL = new ExeSQL();
//	    String mProposalPrtNo = dExeSQL.getOneValue(sql1);
//	    String sql2 = "select otherno from tranlog where ProposalPrtNo = '"+mProposalPrtNo+"' and trancom = '3' and funcflag = '101'";
//		String mOldContPrtNo = dExeSQL.getOneValue(sql2);
//		cLogger.info("投保单号为:"+mProposalPrtNo);
//		cLogger.info("元旦正好为:"+mOldContPrtNo);
//		mBodyEle.addContent(ProposalPrtNo.setText(mProposalPrtNo));
//		mBodyEle.addContent(OldContPrtNo.setText(mOldContPrtNo));
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			cLogger.info("Into ContRePrintBoc.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
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
//			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
//			.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
//				.append(" and ContNo='").append(mContNo).append('\'')
//				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar)) 
//				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
//				.toString();
//			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
//				throw new MidplatException("此保单数据正在处理中，请稍候！");
//			}
			
 
			
			//当天、同一网点，成功出过单
			String tSqlStr = new StringBuilder("select * from Cont where Type=").append(AblifeCodeDef.ContType_Bank)
				.append(" and State=").append(AblifeCodeDef.ContState_Sign)
				.append(" and ContNo='").append(mContNo).append('\'')
				.append(" and MakeDate=").append(cTranLogDB.getMakeDate())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			cLogger.info(tSqlStr);
			ContSet mContSet = new ContDB().executeQuery(tSqlStr);
			if (1 != mContSet.size()) {
				throw new MidplatException("非当日同一网点所出保单，不能进行该操作！");
			}
			ContSchema tContSchema = mContSet.get(1);
			
			//add by zhj 网点与权限 添加代理   
//			cInXmlDoc = authority(cInXmlDoc);
			//add by zhj 网点与权限 添加代理end 
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContRePrint).call(cInXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);  
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//核心不存保单印刷号，用请求报文对应值覆盖 
			//tOutBodyEle.getChild(ContPrtNo).setText(mContPrtNo);
			
			//核心可能将一个产品的两个险种都定义为主险，而银行则认为一主一附，以银行报文为准，覆盖核心记录
			String tMainRiskCode = tContSchema.getBak1();
			List<Element> tRiskList = tOutBodyEle.getChildren(Risk);
			int tSize = tRiskList.size();
			for (int i = 0; i < tSize; i++) {
				Element ttRiskEle = tRiskList.get(i);
				ttRiskEle.getChild(MainRiskCode).setText(tMainRiskCode);
				
				if (tMainRiskCode.equals(ttRiskEle.getChildText(RiskCode))) {
					tRiskList.add(0, tRiskList.remove(i));	//将主险调整到最前面
				}
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
		
		cLogger.info("Out ContRePrintBoc.service()!");
		return cOutXmlDoc;
	}
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException {
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
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
