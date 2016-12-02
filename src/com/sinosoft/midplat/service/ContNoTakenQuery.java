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

public class ContNoTakenQuery extends ServiceImpl{

	public ContNoTakenQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc){
		long mStartMillis = System.currentTimeMillis();
		
		cLogger.info("Into ContNoTakenQuery.service()");
		cInXmlDoc = pInXmlDoc;
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element)mRootEle.getChild(Head).clone();
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			cLogger.info("Into ContNoTaken.service()...-->authorityCheck.submitData(mHeadEle)����Ȩ��");
			/*AuthorityCheck  authorityCheck = new AuthorityCheck();
			if(!authorityCheck.submitData(mHeadEle)){
				throw new MidplatException("��������Ȩ��"); 
			}*/
			//������Ȩ�����
			//cInXmlDoc = authority(cInXmlDoc);
			
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NoTakenQuery).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if(CodeDef.RCode_ERROR == Integer.parseInt(tHeadEle.getChildText(Flag))){
				throw new MidplatException(tHeadEle.getChildText(Desc));
			}
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(Name)+"����ʧ��");
			cOutXmlDoc  = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} catch (Exception ex) {
			cLogger.info(cThisBusiConf.getChildText(Name)+"����ʧ��");
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
		
		cLogger.info("Out ContNoTakenQuery.service()!");
		return cOutXmlDoc;
	}
	
	/**
	 * ����Ȩ�����У�鷽��
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
		
		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom)
		.append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		tSqlStr2 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr2);
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);
		if(("" == sAgentCom || null == sAgentCom) && ("" == sAgentCode || null == sAgentCode)){
			throw new MidplatException("�����㲻����");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return pInXmlDoc;
	}

}
