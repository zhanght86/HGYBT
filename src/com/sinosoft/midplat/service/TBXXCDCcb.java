package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class TBXXCDCcb extends ServiceImpl {
	public TBXXCDCcb(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into TBXXCDCcb.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();

		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			//add by zhj 网点与权限 添加代理
			cInXmlDoc = authority(cInXmlDoc); 	
			//add by zhj 网点与权限 添加代理end 
			cLogger.info("Into TBXXCDCcb.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
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
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_EdorCTInfo).call(cInXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}			
			
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
		
		cLogger.info("Out TBXXCDCcb.service()!");
		return cOutXmlDoc;
	}
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException {
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
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
		String sTranCom = (String)mHeadEle.getChildTextTrim("TranCom");
		 
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
