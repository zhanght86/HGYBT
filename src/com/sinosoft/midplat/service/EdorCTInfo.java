package com.sinosoft.midplat.service;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class EdorCTInfo extends ServiceImpl {
	public EdorCTInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into EdorCTInfo.service()...");
		cInXmlDoc = pInXmlDoc;
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_EdorCTInfo).call(cInXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			Element tOutBodyEle = tOutRootEle.getChild(Body);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//���Ŀ��ܽ�һ����Ʒ���������ֶ�����Ϊ���գ�����������Ϊһ��һ���������б���Ϊ׼�����Ǻ��ļ�¼
			List<Element> tDetailList = tOutBodyEle.getChildren(Detail);
			for (Element tDetailEle : tDetailList) {
				String ttSqlStr = "select Bak1 from Cont where ContNo='"+tDetailEle.getChildText(ContNo)+"'";
				tDetailEle.getChild(RiskCode).setText(
						new ExeSQL().getOneValue(ttSqlStr));
			}
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out EdorCTInfo.service()!");
		return cOutXmlDoc;
	}
}
