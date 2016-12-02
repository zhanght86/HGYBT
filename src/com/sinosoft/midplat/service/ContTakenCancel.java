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

public class ContTakenCancel extends ServiceImpl{

	public ContTakenCancel(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document service(Document pInXmlDoc){
		long mStartMillis = System.currentTimeMillis();
		cInXmlDoc = pInXmlDoc;
		cLogger.info("Into ContTakenCancel.service()");
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
		
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_TakenCancel).call(cInXmlDoc);
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
		cLogger.info("Out ContTakenCancel.service()");
		return cOutXmlDoc;
	}

}
