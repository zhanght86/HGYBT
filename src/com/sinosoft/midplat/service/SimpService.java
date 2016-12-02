/**
 * 通用简单交易处理类。
 * 此类本身不做任何业务处理，仅插入一条交易日志。
 * 目前使用此类的交易有：中行冲正、建行绿灯
 */

package com.sinosoft.midplat.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;

public class SimpService extends ServiceImpl {
	public SimpService(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into SimpService.service()...");
		cInXmlDoc = pInXmlDoc;
		
		String mTranCom = cInXmlDoc.getRootElement().getChild(Head).getChildText(TranCom);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append("_Simp_in.xml");
		SaveMessage.save(cInXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "交易成功");
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append("_Simp_Out.xml");
		SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-未返回；0-交易成功，返回；1-交易失败，返回
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out SimpService.service()!");
		return cOutXmlDoc;
	}
}
