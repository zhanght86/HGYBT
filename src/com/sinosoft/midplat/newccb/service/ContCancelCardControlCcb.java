package com.sinosoft.midplat.newccb.service;

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
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class ContCancelCardControlCcb extends ServiceImpl {

	public ContCancelCardControlCcb(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContCancelCardControlCcb.service()...");
		cInXmlDoc = pInXmlDoc;
	
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		Element mBodyEle = mRootEle.getChild(Body);
//		String mProposalPrtNo = mBodyEle.getChildText(ProposalPrtNo);
//		String mContNo = mBodyEle.getChildText(ContNo);
		long mUsedContConfirm =0;
		try { 
			cTranLogDB = insertTranLog(pInXmlDoc);
			long mStartContConfirm = System.currentTimeMillis();
			cLogger.info("Into ContCancelCardControlCcb.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
			 mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			//原来工行不调核心
//			 cOutXmlDoc = cInXmlDoc;
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_CardControl).call(cInXmlDoc);
//			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
//			Element tFlag = new Element(Flag);
//			tFlag.setText("0");
//			Element tDesc = new Element(Desc);
//			tDesc.setText("重空核对交易成功！");
//			tOutHeadEle.addContent(tFlag);
//			tOutHeadEle.addContent(tDesc);
//			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
//				throw new MidplatException(tOutHeadEle.getChildText(Desc));
//			}
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
		
		cLogger.info("Out ContCancelCardControlCcb.service()!");
		return cOutXmlDoc;
		
	} 

	
}
