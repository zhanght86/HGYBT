package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class ContCancel extends ServiceImpl {
	public ContCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("null")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContCancel.service()...");
		cInXmlDoc = pInXmlDoc;
	//	JdomUtil.print(cInXmlDoc);
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		Element mBodyEle = mRootEle.getChild(Body);
		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
		String mContNo = mBodyEle.getChildText(ContNo);
		
		
		/**
		 * 建行的根据原出单交易流水号到日志表获取到投保单号,再进行相关处理
		 */
		if("03".equals(mHeadEle.getChildText(TranCom)) || "09".equals(mHeadEle.getChildText(TranCom))){
		String oldTransNo = mBodyEle.getChildText("TransNo");
		Element ProposalPrtNoEle = new Element("ProposalPrtNo");
		if(oldTransNo!=null&&!oldTransNo.equals("")){
			
			String getoldoldTransnoSQL = "select bak2 from tranlog where TranNo = '"+oldTransNo+"' and contno = '"+
			mContNo+"'";
			String oldoldTransno= new ExeSQL().getOneValue(getoldoldTransnoSQL);
			String getProposalPrtNoSQL ="select ProposalPrtNo from tranlog where tranno = '"+oldoldTransno+"'";
			mProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
			ProposalPrtNoEle.setText(mProposalPrtNo);
		}else{
			String getproposalprtno ="select proposalprtno from cont where contno ='"+mContNo+"' and state='2'";
			cLogger.info(getproposalprtno);
			mProposalPrtNo =   new ExeSQL().getOneValue(getproposalprtno);
			ProposalPrtNoEle.setText(mProposalPrtNo);
		}
		mBodyEle.addContent(ProposalPrtNoEle);
		}
		
		JdomUtil.print(pInXmlDoc);

		try { 
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			cLogger.info("Into ContCancel.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
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
			
			//当天、同一网点，成功出过单
			tSqlStr = new StringBuilder("select * from Cont where Type=").append(AblifeCodeDef.ContType_Bank)
				.append(" and State=").append(AblifeCodeDef.ContState_Sign)
				//.append(" and ContNo='").append(mContNo).append('\'')
				.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
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
			//cInXmlDoc = authority(cInXmlDoc);
			//add by zhj 网点与权限 添加代理end 			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCancel).call(cInXmlDoc);
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//置为撤单状态
			Date tCurDate = new Date();
			tSqlStr = new StringBuilder("update Cont set State=").append(AblifeCodeDef.ContState_Cancel)
				.append(", ModifyDate=").append(DateUtil.get8Date(tCurDate))
				.append(", ModifyTime=").append(DateUtil.get6Time(tCurDate))
				.append(" where RecordNo=").append(tContSchema.getRecordNo())
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if (!tExeSQL.execUpdateSQL(tSqlStr)) {
				cLogger.error("更新保单状态(Cont)失败！" + tExeSQL.mErrors.getFirstError());
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
		
		cLogger.info("Out ContCancel.service()!");
		return cOutXmlDoc;
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
