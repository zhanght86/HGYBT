package com.sinosoft.midplat.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class ContNoTakenCancel extends ServiceImpl{

	public ContNoTakenCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc){
		long mStartMillis = System.currentTimeMillis();
		cInXmlDoc = pInXmlDoc;
		cLogger.info("Into ContNoTakenCancel.service()");
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element)mRootEle.getChild(Head).clone();
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			//权限校验
			/*AuthorityCheck authorityCheck = new AuthorityCheck();
			if(!authorityCheck.submitData(mHeadEle)){
				throw new MidplatException("该网点无权限");
			}*/
			//网点权限添加代理
			//cInXmlDoc = authority(cInXmlDoc);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NoTakenCancel).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if(CodeDef.RCode_ERROR == Integer.parseInt(tHeadEle.getChildText(Flag))){
				throw new MidplatException(tHeadEle.getChildText(Desc));
			}
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(Name)+"交易失败");
			MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}catch(Exception ex){
			cLogger.info(cThisBusiConf.getChildText(Name)+"交易失败");
			MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if(null != cTranLogDB){
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurrMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)((tCurrMillis-mStartMillis)/1000));
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurrMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurrMillis));
			if(!cTranLogDB.update()){
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		return cOutXmlDoc;
	}
	
	/**
	 * 网点权限添加校验方法
	 * @param pInXmlDoc
	 * @return
	 * @throws MidplatException
	 */
	private Document authority(Document pInXmlDoc) throws MidplatException{
		Element mRootEle = pInXmlDoc.getRootElement();
		Element mHeadEle = mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sTranCom = mHeadEle.getChildTextTrim("TranCom");
		String sNodeNo = mHeadEle.getChildTextTrim("NodeNo");
		
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom)
		.append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		tSqlStr2 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr2);
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);
		if(("" == sAgentCom || null == sAgentCom) && ("" == sAgentCode || null == sAgentCode)){
			throw new MidplatException("此网点不存在");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return pInXmlDoc;
	}

}
