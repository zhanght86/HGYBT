package com.sinosoft.midplat.service;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class ContNoTaken extends ServiceImpl{

	public ContNoTaken(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc){
		long mStartMills = System.currentTimeMillis();
		cLogger.info("Into ContNoTaken.service()........");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mHeadEle = (Element)mRootEle.getChild(Head).clone();
		try {
			
			cTranLogDB = insertTranLog(cInXmlDoc);
			cLogger.info("Into ContNoTaken.service()...-->authorityCheck.submitData(mHeadEle)����Ȩ��");	
			/*AuthorityCheck authorityCheck = new AuthorityCheck();
			if(!authorityCheck.submitData(mHeadEle)){
				throw new MidplatException("��������Ȩ�ޣ�");
			}*/
			//������Ȩ����Ӵ���
		//	cInXmlDoc = authority(cInXmlDoc);
			cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NoTaken).call(pInXmlDoc);
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if(CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))){
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ��",ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} catch (Exception ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ��",ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if(cTranLogDB != null){
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMills = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMills-mStartMills)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMills));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMills));
			if(!cTranLogDB.update()){
				cLogger.info("������־��Ϣʧ��"+ cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out ContNoTaken.service()........");
		return cOutXmlDoc;
	}
	
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * ���� Ȩ�� ���У�鷽��
	 */
	/*		private Document authority(Document mInXmlDoc) throws MidplatException{
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom = (String)mHeadEle.getChildTextTrim("TranCom");
		cLogger.info("ͨ�����У�����������Ų�ѯ��������ţ������");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom ='"+sTranCom).append('\'').append(" and NodeNo='")
		                 .append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where Trancom ='"+sTranCom).append('\'').append(" and NodeNo='")
		.append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3);
		cLogger.info("authority--->" + sAgentCom);
		cLogger.info("authority--->" + sAgentCode);
		if((""==sAgentCom || sAgentCom == null) && (""==sAgentCode || sAgentCode == null)){
			throw new MidplatException("�����㲻���ڣ���ȷ��");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
	} 
*/
}
