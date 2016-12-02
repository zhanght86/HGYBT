/**
 * ͨ�ü򵥽��״����ࡣ
 * ���౾�����κ�ҵ����������һ��������־��
 * Ŀǰʹ�ô���Ľ����У����г����������̵�
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
		cLogger.info("���汨����ϣ�"+mSaveName);
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "���׳ɹ�");
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append("_Simp_Out.xml");
		SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("���汨����ϣ�"+mSaveName);
		
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out SimpService.service()!");
		return cOutXmlDoc;
	}
}
