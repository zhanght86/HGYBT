package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class DocumentBlcCcb extends ServiceImpl {

	public DocumentBlcCcb(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into DocumentBlcCcb.service()...");
		cInXmlDoc = pInXmlDoc;
	
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		long mUsedContConfirm =0;
		try { 
			//建行单证信息传递交易 直接组织报文返回给银行   
			cTranLogDB = insertTranLog(pInXmlDoc);
			//cInXmlDoc = authority(cInXmlDoc);
			cLogger.info("Into DocumentBlcCcb.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
//			AuthorityCheck authorityCheck = new AuthorityCheck();
//			if(!authorityCheck.submitData(mHeadEle)){ 
//				throw new MidplatException("该网点无权限！");
//			} 
			
//			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
//			.append(" and TranDate=").append(cTranLogDB.getTranDate())
//			.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
//			.append(" and TranCom=").append(cTranLogDB.getTranCom())
//			.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
//			.toString();
//		ExeSQL tExeSQL = new ExeSQL();
//		if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
//			throw new MidplatException("已成功做过单证对账，不能重复操作！");
//		} else if (tExeSQL.mErrors.needDealError()) {
//			throw new MidplatException("查询历史对账信息异常！");
//		}
		//cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc1).call(cInXmlDoc);
		long mStartContConfirm = System.currentTimeMillis();
		cLogger.info("Into GreenTest.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
		mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
		cOutXmlDoc = cInXmlDoc;
		Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
		Element tFlag = new Element(Flag);
		tFlag.setText("0");
		Element tDesc = new Element(Desc);
		tDesc.setText("单证对账交易成功！");
		tOutHeadEle.addContent(tFlag);
		tOutHeadEle.addContent(tDesc);
		if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
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
			Element iHeadEle = cInXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setAgentComName(iHeadEle.getChildText("AgentComName"));
			cTranLogDB.setAgentName(iHeadEle.getChildText("AgentName"));
			cTranLogDB.setAgentCodeGrade(iHeadEle.getChildText("AgentCodeGrade"));
			cTranLogDB.setUnitCode(iHeadEle.getChildText("UnitCode"));
			if(iHeadEle.getChildText("ManageCom") == null || "".equals(iHeadEle.getChildText("ManageCom"))){
				cTranLogDB.setManageCom("86");
			}else{
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
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			cTranLogDB.setBak3(String.valueOf((tCurMillis-mStartMillis)/1000.0));
			cTranLogDB.setBak4(String.valueOf(mUsedContConfirm/1000.0));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out DocumentBlcCcb.service()!");
		return cOutXmlDoc;
		
	} 
	
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
