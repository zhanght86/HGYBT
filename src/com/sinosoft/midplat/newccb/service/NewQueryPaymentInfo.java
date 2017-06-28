package com.sinosoft.midplat.newccb.service;

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

public class NewQueryPaymentInfo extends ServiceImpl {

	public NewQueryPaymentInfo(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewQueryPaymentInfo.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("LCCont");
		String mProposalPrtNo = mBodyEle.getChildText("PrtNo");
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			JdomUtil.print(cInXmlDoc);
			cOutXmlDoc=call(pInXmlDoc);
			System.out.println("-----------------------------------------------");
			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("RetData");
			if (CodeDef.RCode_ERROR != Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
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
			JdomUtil.print(cOutXmlDoc);
			cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
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
		cLogger.info("Out NewQueryPaymentInfo.service()!");
		return cOutXmlDoc;
	}
	private void rollback() {
		cLogger.debug("Into NewQueryPaymentInfo.rollback()...");
		
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
		
		cLogger.debug("Out NewQueryPaymentInfo.rollback()!");
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
		cLogger.debug("Into NewQueryPaymentInfo.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		//[Element: <TranData/>]
		//[��׼����]����TranData���ڵ�
		Element mTranDataEle = pXmlDoc.getRootElement();
		//[Element: <Head/>]
		//[��׼����]Head����ͷ
		Element mHeadEle = mTranDataEle.getChild("BaseInfo");
		//[��׼����]Body������
		//[Element: <Body/>]
		Element mBodyEle = mTranDataEle.getChild("LCCont");
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
			mTranLogDB.setProposalPrtNo(mBodyEle.getChildText("PrtNo"));
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
		cLogger.debug("Out NewQueryPaymentInfo.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}
	
	public Document call(Document pInXmlDoc) throws Exception{
		cLogger.info("Into NewQueryPaymentInfo.call()...");
		String cServiceId=AblifeCodeDef.SID_QueryPaymentInfo;
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
		Element cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		String mServAddress = cConfEle.getAttributeValue(address);
		String mServMethod = cConfEle.getAttributeValue(method);
		
		Element mHeadEle = pInXmlDoc.getRootElement().getChild("BaseInfo");
		Element mBodyEle = pInXmlDoc.getRootElement().getChild("LCCont");
		String mPrtNo = mBodyEle.getChildText("PrtNo");
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

		// byte[] mInXmlByte=JdomUtil.toBytes(pInXmlDoc,"GBK");//�ڱ��ز��ʱ�������
		//start call ¼���˱�(http://10.0.1.160:8080/ui/services/ServiceForBankInterfaceService.service)...
		//start call �ؿպ˶�(http://10.2.0.33:8001/ui/services/ServiceForBankInterfaceService.service)...
		System.out.println("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");//���нӿڷ���
		//16:25:47,101 INFO net.CallWebsvcAtomSvc(66) - start call ¼���˱�(http://10.0.1.160:8080/ui/services/ServiceForBankInterfaceService.service)...
		//start call �ؿպ˶�(http://10.2.0.33:8001/ui/services/ServiceForBankInterfaceService.service)...
		cLogger.info("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		// Call mCall = new Call(mServAddress);
		// mCall.addParameter("p", Constants.XSD_STRING, ParameterMode.IN);
		// mCall.setOperationName(new
		// QName("http://kernel.ablinkbank.sinosoft.com",mServMethod));
		// mCall.setReturnType(Constants.XSD_STRING);
		long mStartMillis = System.currentTimeMillis();
		// System.out.println(mInXmlStr);
		JdomUtil.print(pInXmlDoc);
		//
		// //����ʱע��
		// String mOutStr;
		// try {
		// mOutStr = (String) mCall.invoke(new String[]{mInXmlStr});
		// } catch (AxisFault ex) {
		// throw new MidplatException(cConfEle.getAttributeValue(name)+"�����쳣��",
		// ex);
		// }
		// ʹ��RPC��ʽ����WebService
		RPCServiceClient serviceClient = new RPCServiceClient();

		Options options = serviceClient.getOptions();
		// ���ó�ʱʱ��
//				options.setTimeOutInMilliSeconds(600000);
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
		// String mOutStr = (String) serviceClient.invokeBlocking(opAddEntry,
		// opAddEntryArgs, classes)[0];
		// String str=new String(mOutStr,"utf-8");
		// System.out.println("sdfdsfsdfdsf==="+str+"����:"+mOutStr.length);
		cLogger.info("Ͷ������" + mPrtNo + cConfEle.getAttributeValue(name) + "(" + mServMethod + ")��ʱ��" + (System.currentTimeMillis() - mStartMillis) / 1000.0 + "s");
		// cLogger.debug(mOutStr);
		//����������:
		// System.out.println("���ķ��صģ�"+new String(mOutStr));
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		//�ؿպ˶�in_Std.xml
		 JdomUtil.print(mOutXmlDoc);
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(cConfEle.getAttributeValue(name) + "���񷵻ؽ���쳣��");
		}

		// �޷����ʺ���ʱ ���ڲ���

		// ICBCTestUI testUI = new ICBCTestUI();
		// Document mOutXmlDoc = testUI.getXmlFromLis();
		// Document mOutXmlDoc = null;

		mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_outSvc.xml");
		SaveMessage.save(mOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("���汨����ϣ�" + mSaveName);

		cLogger.info("Out NewQueryPaymentInfo.service()!");
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
