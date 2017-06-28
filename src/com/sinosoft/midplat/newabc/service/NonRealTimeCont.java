//ũ�з�ʵʱ����
package com.sinosoft.midplat.newabc.service;

import java.util.Date;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;

public class NonRealTimeCont extends ServiceImpl {
	public NonRealTimeCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NonRealTimeCont.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("LCCont");
		String mProposalPrtNo = mBodyEle.getChildText("ProposalContNo");
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			//У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300;	//Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��"+tLockTime, ex);
			}
			JdomUtil.print(cInXmlDoc);
			cOutXmlDoc = call(cInXmlDoc);

			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("RetData");
			if (CodeDef.RCode_ERROR  != Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//��ʱ�Զ�ɾ������
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60;	//Ĭ�ϳ�ʱ����Ϊ1���ӣ����δ���ó�ʱʱ�䣬��ʹ�ø�ֵ��
			try {
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) {	//ʹ��Ĭ��ֵ
				cLogger.debug("δ���ó�ʱ������������ʹ��Ĭ��ֵ(s)��"+tTimeOut, ex);
			}
			if (tUseTime > tTimeOut*1000) {
				cLogger.error("����ʱ��UseTime=" + tUseTime/1000.0 + "s��TimeOut=" + tTimeOut + "s��Ͷ���飺" + mProposalPrtNo);
				rollback();	//�ع�ϵͳ����
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = getSimpOutXml(CodeDef.RCode_OK, ex.getMessage());
		}
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild("RetData");
			String flag=tHeadEle.getChildText(Flag).equals("1")?"0":"1";
			cTranLogDB.setRCode(flag);
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NonRealTimeCont.service()!");
		return cOutXmlDoc;
	}
	
	private void rollback() {
		cLogger.debug("Into NonRealTimeCont.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent(
				(Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);
		
		try {
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("�ع�����ʧ�ܣ�", ex);
		}
		
		cLogger.debug("Out NonRealTimeCont.rollback()!");
	}
	
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException{
		cLogger.debug("Into NonRealTimeCont.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		Element mTranDataEle = pXmlDoc.getRootElement();
	    Element mBaseInfoEle = mTranDataEle.getChild("BaseInfo");
	    Element mLCContEle = mTranDataEle.getChild("LCCont");
	    Element mHeadEle = mTranDataEle.getChild("Head");

	    TranLogDB mTranLogDB = new TranLogDB();
	    mTranLogDB.setLogNo(Thread.currentThread().getName());
	    System.out.println("��������" + Thread.currentThread().getName());
	    mTranLogDB.setTranCom(mHeadEle.getChildText("TranCom"));
	    mTranLogDB.setZoneNo(mBaseInfoEle.getChildText("ZoneNo"));
	    mTranLogDB.setNodeNo(mBaseInfoEle.getChildText("BrNo"));
	    mTranLogDB.setTranNo(mBaseInfoEle.getChildText("TransrNo"));
	    mTranLogDB.setOperator(mBaseInfoEle.getChildText("TellerNo"));
	    mTranLogDB.setFuncFlag(mBaseInfoEle.getChildText("FunctionFlag"));
	    mTranLogDB.setTranDate(mBaseInfoEle.getChildText("BankDate"));
	    mTranLogDB.setTranTime(mBaseInfoEle.getChildText("BankTime"));
	    mTranLogDB.setInNoDoc(mBaseInfoEle.getChildText("InNoDoc"));
	    System.out.println("trancom:" + mTranLogDB.getTranCom());
	    System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
	    System.out.println("mHeadEle.getChildText" + mBaseInfoEle.getChildText("InNoDoc"));
	    mTranLogDB.setProposalPrtNo(mLCContEle.getChildText("ProposalContNo"));
	    mTranLogDB.setContNo("");
	    mTranLogDB.setOtherNo("");
	    mTranLogDB.setBak2("");
	    mTranLogDB.setAppntName(mLCContEle.getChild("LCAppnt").getChildText("AppntName"));
	    mTranLogDB.setAppntIDNo(mLCContEle.getChild("LCAppnt").getChildText("AppntIDNo"));
	    mTranLogDB.setRCode(-1);
	    mTranLogDB.setUsedTime(-1);
	    mTranLogDB.setRText("������");
	    mTranLogDB.setBak1("");
	    Date mCurDate = new Date();
	    mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
	    mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
	    mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
	    mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
	    if (!(mTranLogDB.insert()))
	    {
	      this.cLogger.error(mTranLogDB.mErrors.getFirstError());
	      throw new MidplatException("������־ʧ�ܣ�");
	    }
		cLogger.debug("Out NonRealTimeCont.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}
	
	@SuppressWarnings("unchecked")
	public Document call(Document pInXmlDoc) throws Exception{
		cLogger.info("Into ContUpdateServiceStatus.call()...");
		String cServiceId=AblifeCodeDef.SID_NonRealTimeApplication;
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
		Element cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		String mServAddress = cConfEle.getAttributeValue(address);
		String mServMethod = cConfEle.getAttributeValue(method);
		
		Element mHeadEle = pInXmlDoc.getRootElement().getChild("Head");
		Element mBodyEle = pInXmlDoc.getRootElement().getChild("LCCont");
		String mPrtNo = mBodyEle.getChildText("ProposalContNo");
		
		String mTranCom = mHeadEle.getChildText(TranCom);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_inSvc.xml");
		SaveMessage.save(pInXmlDoc, mTranCom, mSaveName.toString());
		//���汨����ϣ�1550_6_0_inSvc.xml
		//����[����]������ϣ�1561_12_30_inSvc.xml
		cLogger.info("���汨����ϣ�" + mSaveName);
		
		String mInXmlStr = JdomUtil.toString(pInXmlDoc);// ��websphere ��ʱ�������������
		byte[] mInXmlByte = mInXmlStr.getBytes("GBK");// ��websphere��ʱ�������

		System.out.println("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");//���нӿڷ���
		cLogger.info("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		long mStartMillis = System.currentTimeMillis();
//		JdomUtil.print(pInXmlDoc);
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		// ʹ��RPC��ʽ����WebService
		RPCServiceClient serviceClient = new RPCServiceClient();

		Options options = serviceClient.getOptions();
		// ���ó�ʱʱ��
		options.setTimeOutInMilliSeconds(60000);
		// ָ������WebService��URL
		String servicePath = mServAddress + "?wsdl";
		EndpointReference targetEPR = new EndpointReference(servicePath);

		options.setTo(targetEPR);
		// ָ��sayHelloToPerson�����Ĳ���ֵ
		Object[] opAddEntryArgs = new Object[] { mInXmlByte };
		// ָ��sayHelloToPerson��������ֵ���������͵�Class����
		Class[] classes = new Class[] { byte[].class };
		// Class[] classes = new Class[] { String.class };
		// ָ��Ҫ���õ�sayHelloToPerson������WSDL�ļ��������ռ�
		QName opAddEntry = new QName("http://kernel.ablinkbank.sinosoft.com", mServMethod);
		// ����sayHelloToPerson����������÷����ķ���ֵ
		byte[] mOutStr = (byte[]) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
		cLogger.info("Ͷ������" + mPrtNo + cConfEle.getAttributeValue(name) + "(" + mServMethod + ")��ʱ��" + (System.currentTimeMillis() - mStartMillis) / 1000.0 + "s");
		// cLogger.debug(mOutStr);
		//����������:
		// System.out.println("���ķ��صģ�"+new String(mOutStr));
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		 JdomUtil.print(mOutXmlDoc);
		cLogger.info(JdomUtil.toStringFmt(mOutXmlDoc));
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(cConfEle.getAttributeValue(name) + "���񷵻ؽ���쳣��");
		}

		mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_outSvc.xml");
		SaveMessage.save(mOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("���汨����ϣ�" + mSaveName);

		cLogger.info("Out ContUpdateServiceStatus.service()!");
		return mOutXmlDoc;
	}
	
	/**
	 * ����pFlag��pMessage�����ɼ򵥵ı�׼���ر��ġ�
	 */
	public static Document getSimpOutXml(int pFlag, String pMessage) {
		Element mFlag = new Element(Flag);
		mFlag.addContent(String.valueOf(pFlag));

		Element mDesc = new Element(Desc);
		mDesc.addContent(pMessage);

		Element mHead = new Element("RetData");
		mHead.addContent(mFlag);
		mHead.addContent(mDesc);

		Element mTranData = new Element(TranData);
		mTranData.addContent(mHead);

		return new Document(mTranData);
	}
	
}
