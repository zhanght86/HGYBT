package com.sinosoft.midplat.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class RenewalPay extends ServiceImpl {
	public RenewalPay(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into RenewalPay.service()...");
		cInXmlDoc = pInXmlDoc;
		
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			Element mRootEle = cInXmlDoc.getRootElement();
			Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
			Element mBodyEle = mRootEle.getChild(Body);
			
			//�������ڽɷѽ��� ֱ����֯���ķ��ظ�����   
			
			Document mOutStdXml = null;			
			Element tInsut = new Element("Insut");
			Element tMaina = new Element("Maina");
			Element tResultCode = new Element("ResultCode");
			Element tResultInfo = new Element("ResultInfo");
			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
			.append(" and TranDate=").append(cTranLogDB.getTranDate())
			.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
			.append(" and ContNo=").append(cTranLogDB.getContNo())
			.append(" and TranCom='").append(cTranLogDB.getTranCom()).append('\'')
			.toString();
			ExeSQL tExeSQL = new ExeSQL();
		
			cLogger.info("״̬Ϊ��"+tExeSQL.getOneValue(tSqlStr));
			if ("1".equals(tExeSQL.getOneValue(tSqlStr)))
			{
				tMaina.addContent(tResultCode.setText("0001"));
				tMaina.addContent(tResultInfo.setText("�Ѿ��ɹ��������ڽɷѽ��ף������ظ�����"));
			}
			else{
				tMaina.addContent(tResultCode.setText("0000"));
				tMaina.addContent(tResultInfo.setText("���׳ɹ�"));
			}
			tInsut.addContent(tMaina);
			mOutStdXml = new Document(tInsut);
			cOutXmlDoc = mOutStdXml;
			
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB) {
			Element tMainaEle = cOutXmlDoc.getRootElement().getChild("Maina");
			cTranLogDB.setRCode(tMainaEle.getChildText("ResultCode"));
			cTranLogDB.setRText(tMainaEle.getChildText("ResultInfo"));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out RenewalPay.service()!");
		return cOutXmlDoc;
	}
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException {
		cLogger.debug("Into ServiceImpl.insertTranLog()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));
		if (null != mBodyEle) {
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
		}
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert()) {
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}
		
		cLogger.debug("Out ServiceImpl.insertTranLog()!");
		return mTranLogDB;
	}
}
