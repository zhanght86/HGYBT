package com.sinosoft.midplat.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.ccb.service.ContBatResponse;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.CheckAgentCom;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class BatResponse extends ServiceImpl {
	public BatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into BatResponse.service()...");
		long mUsedContConfirm= 0;
		cInXmlDoc = pInXmlDoc; 
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			//处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			JdomUtil.print(cInXmlDoc);

			//add by zhj 网点与权限 添加代理   
			//cInXmlDoc = CheckAgentCom.authority(cInXmlDoc);
			long mStartContConfirm = System.currentTimeMillis();
			cOutXmlDoc = ContBatResponse.call(cInXmlDoc);
			 mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			cLogger.info("----------Timekeeping---------->调用后置机花费时间为:"+String.valueOf(mUsedContConfirm));
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			if((ex.getMessage()!=null)){
				cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			}
		} 
		catch (Exception ex) {
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
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setBak3(String.valueOf((tCurMillis-mStartMillis)/1000.0));
			cTranLogDB.setBak4(String.valueOf(mUsedContConfirm/1000.0));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out BatResponse.service()!");
		return cOutXmlDoc;
	}
	
	
}
