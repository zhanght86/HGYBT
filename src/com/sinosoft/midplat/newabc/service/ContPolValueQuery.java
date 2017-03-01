package com.sinosoft.midplat.newabc.service;

import java.util.Calendar;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;


public class ContPolValueQuery extends ServiceImpl {

	public ContPolValueQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) throws Exception{
		cLogger.info("Into ContPolValueQuery.service()...");
		long mStartMillis=System.currentTimeMillis();
		cInXmlDoc=pInXmlDoc;
		Element mInRootEle=pInXmlDoc.getRootElement();
		@SuppressWarnings("unused")
		Element mInHeadEle=mInRootEle.getChild(Head);
		Element mInBodyEle=mInRootEle.getChild(Body);
		String mContNo=mInBodyEle.getChildText(ContNo);
		
		try {
			cTranLogDB=insertTranLog(pInXmlDoc);
			int tLocktime=300;
			try {
				tLocktime=Integer.valueOf(cThisBusiConf.getChildText(locktime));
			} catch (Exception e) {
				cLogger.error("未配置锁定时间,或配置有误,使用默认值:"+tLocktime+"s",e);
			}
			Calendar tCalendar=Calendar.getInstance();
			tCalendar.add(Calendar.SECOND, -tLocktime);
			String tSql=new StringBuffer("select count(1) from tranLog where RCode=").append(CodeDef.RCode_NULL).append(" and ContNo='").append(mContNo).append("\'").append(" and MakeDate>=").append(DateUtil.get8Date(tCalendar)).append(" and MakeTime>='").append(DateUtil.get6Time(tCalendar)).toString();
			if(!"1".equals(new ExeSQL().getOneValue(tSql))){
				throw new MidplatException("此保单正在处理中,请稍后!");
			}
			JdomUtil.print(cInXmlDoc);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_PolValueQuery).call(cInXmlDoc);
			JdomUtil.print(cOutXmlDoc);
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			@SuppressWarnings("unused")
			Element mOutBodyEle=mOutRootEle.getChild(Body);
			if(AblifeCodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag)))
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
		} catch (Exception e) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败!",e);
			cOutXmlDoc=MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, e.getMessage());
		}
		
		if(cTranLogDB!=null){
			Element tHeadEle=cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Head));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long mCurrMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mCurrMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurrMillis));
			cTranLogDB.setMakeTime(DateUtil.get6Time(mCurrMillis));
			if(!cTranLogDB.update())
				cLogger.error("更新日志信息失败!"+cTranLogDB.mErrors.getFirstError());
		}
		cLogger.info("Out ContPolValueQuery.service()!");
		return cOutXmlDoc;
	}

}
