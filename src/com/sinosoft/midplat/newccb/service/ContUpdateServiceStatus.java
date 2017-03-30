package com.sinosoft.midplat.newccb.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

/**
 * @ClassName: ContUpdateServiceStatus
 * @Description: 
 * @author yuantongxin
 * @date 2017-1-4 ����10:56:43
 */
public class ContUpdateServiceStatus extends ServiceImpl {

	public ContUpdateServiceStatus(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContUpdateServiceStatus.service()...");
		cInXmlDoc = pInXmlDoc;
		
		//Java�ĵ�����ģ�͹��߽������׼���Ĵ�ӡ������̨[GBK���룬����3�ո�]
		//��ӡ��׼���뱨��
//		JdomUtil.print(cInXmlDoc);//[Element:<TranData/>]
		cLogger.info(JdomUtil.toStringFmt(cInXmlDoc));
		Element mRootEle = cInXmlDoc.getRootElement();
		//��׼���뱨��Body������ڵ�
		Element mLCConts = mRootEle.getChild("LCConts");
		Element mLCCont = mLCConts.getChild("LCCont");
//		String mProposalContNo = mLCCont.getChildText("ProposalContNo");
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);

			// У��ϵͳ���Ƿ�����ͬ�������ڴ�����δ����
			int tLockTime = 300; // Ĭ�ϳ�ʱ����Ϊ5����(300s)�����δ��������ʱ�䣬��ʹ�ø�ֵ��
			try {
				tLockTime = Integer.parseInt(cThisBusiConf
						.getChildText(locktime));
			} catch (Exception ex) { // ʹ��Ĭ��ֵ
				cLogger.debug("δ��������ʱ�䣬����������ʹ��Ĭ��ֵ(s)��" + tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);

			cOutXmlDoc=call(pInXmlDoc);
			// �ͺ���������ʱ��ſ� end

			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("RetData");
			Element tOutBodyEle = tOutRootEle.getChild("LCConts");
//			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle
			if (CodeDef.RCode_ERROR  != Integer.parseInt(tOutHeadEle
					.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			// modified by chengqi 20121129
			String mContNo = tOutBodyEle.getChildText(ContNo);
			cTranLogDB.setContNo(mContNo);

			// ��ʱ�Զ�ɾ������
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60; // Ĭ�ϳ�ʱ����Ϊ1���ӣ����δ���ó�ʱʱ�䣬��ʹ�ø�ֵ��
			try {
				tTimeOut = Integer
						.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) { // ʹ��Ĭ��ֵ
				cLogger.debug("δ���ó�ʱ������������ʹ��Ĭ��ֵ(s)��" + tTimeOut, ex);
			}
			if (tUseTime > tTimeOut * 1000) {
				cLogger.error("����ʱ��UseTime=" + tUseTime / 1000.0
						+ "s��TimeOut=" + tTimeOut);
				throw new MidplatException("ϵͳ��æ�����Ժ����ԣ�");
			}

		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);

			cOutXmlDoc = getSimpOutXml(CodeDef.RCode_OK,
					ex.getMessage());
		}

		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild("RetData");
			String flag=tHeadEle.getChildText(Flag).equals("1")?"0":"1";
			cTranLogDB.setRCode(flag);
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}

		cLogger.info("Out ContUpdateServiceStatus.service()!");
		return cOutXmlDoc;
	}
	
	/**
	 * ���뽻����־
	 * @param pXmlDoc XML�ĵ�[��׼���뱨��]
	 * @return ������־����
	 * @throws MidplatException
	 */
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException{
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...[����ҵ����ʵ����.���뽻����־...]
		cLogger.debug("Into ContUpdateServiceStatus.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		//[Element: <TranData/>]
		//[��׼����]����TranData���ڵ�
		Element mTranDataEle = pXmlDoc.getRootElement();
		//[Element: <Head/>]
		//[��׼����]Head����ͷ
		Element mHeadEle = mTranDataEle.getChild("BaseInfo");
		//[��׼����]Body������
		//[Element: <Body/>]
		Element mBodyEle = mTranDataEle.getChild("LCConts");
		//������־���ݿ������ʵ��[���������Ӷ��󣬽�����־�������ݿ��������]
		TranLogDB mTranLogDB = new TranLogDB();//TranLog���ݲ�����ʵ��
		//LogNo:2240
		//������־��Ϊ��ǰ����ִ�е��̶߳������������
		//������־��
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		//��������2240
		//��������10091
		System.out.println("��������" + Thread.currentThread().getName());
		/*���ý�����־���ݿ������ʵ���ֶ�Ϊ���ı�׼���뱨��ͷ�ֶ�ֵ*/
		/*����TranLog��9���ֶ�[��׼���뱨��ͷ9���ֶ�]��TranDate��TranTime��ZoneNo��NodeNo��TellerNo[Operator]��TranNo��TranCom��FuncFlag��InNoDoc*/
		//TranCom:9
		//���ý��׵�λΪ[��׼����]����ͷ���׻���������Ԫ���ı�
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		//ZoneNo:01
		//���õ�������Ϊ[��׼����]����ͷʡ�д�����Ԫ���ı�
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		//NodeNo:060150001222
		//���ý�������Ϊ[��׼����]����ͷ���������Ԫ���ı�
		mTranLogDB.setNodeNo(mHeadEle.getChildText("BankCode"));
		//TranNo:2016120800010
		//���ý�����ˮ��Ϊ[��׼����]����ͷ������ˮ����Ԫ���ı�
		mTranLogDB.setTranNo(mHeadEle.getChildText("TransrNo"));
		//Operator:5201300002
		//���ò���ԱΪ[��׼����]����ͷ��Ա������Ԫ���ı�
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		//FuncFlag:1012
	   //���ý�������Ϊ[��׼����]����ͷ����������Ԫ���ı�
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		//TranDate:20161108
		//���ý�������Ϊ[��׼����]����ͷ����������Ԫ���ı�
		mTranLogDB.setTranDate(mHeadEle.getChildText("BankDate"));
		//TranTime:130101
		//���ý���ʱ��Ϊ[��׼����]����ͷ����ʱ����Ԫ���ı�
		mTranLogDB.setTranTime(mHeadEle.getChildText("BankTime"));
		//InNoDoc:2240_3_1012_in.xml
		//���ý��뱨��Ϊ[��׼����]����ͷ���뱨����Ԫ���ı�
		mTranLogDB.setInNoDoc(mHeadEle.getChildText("InNoDoc"));
		//trancom:9
		//trancom:13[������׵�λ]
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		//FuncFlag:1012
		//FuncFlag:1012[�����������]
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		//mHeadEle.getChildText10091_3_1012_in.xml
		//mHeadEle.getChildText2240_3_1012_in.xml[������뱨��]
		System.out.println("mHeadEle.getChildText" + mHeadEle.getChildText("InNoDoc"));
		//<Body> != null
		//[��׼����]������ǿ�
		//Body�ǿ�
		if (null != mBodyEle)
		{
			/*����TranLog��4���ֶ�:ProposalPrtNo��ContNo��ContPrtNo��OldLogNo[Bak2]*/
			//ProposalPrtNo:210414132201550
			//����Ͷ����(ӡˢ)��Ϊ[��׼����]������Ͷ����(ӡˢ)����Ԫ���ı�
			mTranLogDB.setProposalPrtNo(mBodyEle.getChild("LCCont").getChildText("ProposalContNo"));
			//ContNo:null
			//���ñ�����Ϊ[��׼����]�����屣�յ�����Ԫ���ı�
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			//OtherNo:""
			//��������������Ϊ[��׼����]�����屣����ͬӡˢ�� (��֤) ��Ԫ���ı�
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			//Bak2:null
			//���ñ���2Ϊ[��׼����]���������־����Ԫ���ı�
			mTranLogDB.setBak2(mBodyEle.getChildText("OldLogNo"));
			//������־���ݿ������ʵ������2Ϊ�ա����ַ�
			if (null == mTranLogDB.getBak2() || "".equals(mTranLogDB.getBak2()))
			{
				//���ñ���2Ϊ[��׼����]������ɽ�����ˮ����Ԫ���ı�
				mTranLogDB.setBak2(mBodyEle.getChildText("OldTranNo"));
			}
		}
		
		/*����TranLog��7���ֶ�:RCode��UsedTime��Bak1��MakeDate��MakeTime��ModifyDate��ModifyTime*/
		//���ý��׽��Ϊ���׹���δ���� 
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
//		mTranLogDB.setRText(mHeadEle.getChild("RetData").getChildText("Desc"));
		//���÷����ʱΪ-1
		mTranLogDB.setUsedTime(-1);
		//���ñ���1Ϊ���ж�IP
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		//��ȡ��ǰ���ڶ���
		Date mCurDate = new Date();
		//�����������[��ǰ���ڶ���ǰ��λ
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		//�������ʱ��[��ǰ���ڶ������λ]
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		//��������޸�����Ϊ�������
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		//��������޸�ʱ��Ϊ���ʱ��
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		//������־���ݿ��������ʧ��
		//���뽻����־���󵽽�����־��
		//!false=true����ʧ��
		if (!mTranLogDB.insert())
		{
			//ORA-00001: Υ��ΨһԼ������ (YBT.PK_TRANLOG) 
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			/*com.sinosoft.midplat.exception.MidplatException: ������־ʧ�ܣ�
				at com.sinosoft.midplat.service.ServiceImpl.insertTranLog(ServiceImpl.java:139)
				at com.sinosoft.midplat.newccb.service.NewContInput.service(NewContInput.java:57)
				at com.sinosoft.midplat.Ybt4Socket.run(Ybt4Socket.java:124)*/
			throw new MidplatException("������־ʧ�ܣ�");
		}
		//Out ServiceImpl.insertTranLog()!
		//Out ServiceImpl.insertTranLog()!
		cLogger.debug("Out ContUpdateServiceStatus.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}

	public Document call(Document pInXmlDoc) throws Exception{
		cLogger.info("Into ContUpdateServiceStatus.call()...");
		String cServiceId=AblifeCodeDef.SID_UpdateServiceStatus;
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
		Element cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		String mServAddress = cConfEle.getAttributeValue(address);
		String mServMethod = cConfEle.getAttributeValue(method);
		
		Element mHeadEle = pInXmlDoc.getRootElement().getChild("BaseInfo");
		Element mBodyEle = pInXmlDoc.getRootElement().getChild("LCConts");
		List<Element> mLCContList = mBodyEle.getChildren("LCCont");
		String mPrtNo = "";//mBodyEle.getChild("LCCont").getChildText("ProposalContNo");
		for (int i = 0; i < mLCContList.size(); i++) {
			mPrtNo+=mLCContList.get(i).getChildText("ProposalContNo");
			if(i!=mLCContList.size()-1) mPrtNo+=",";
		}
		Element mServiceIdEle = new Element(ServiceId);
		mServiceIdEle.setText(cServiceId);
		mHeadEle.addContent(mServiceIdEle);

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
		options.setTimeOutInMilliSeconds(800000);
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
