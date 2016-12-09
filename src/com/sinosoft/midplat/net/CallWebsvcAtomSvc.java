package com.sinosoft.midplat.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;

public class CallWebsvcAtomSvc implements XmlTag
{
	private final static Logger cLogger = Logger.getLogger(CallWebsvcAtomSvc.class);

	private final String cServiceId;
	private final Element cConfEle;

	public CallWebsvcAtomSvc(String pServiceId) throws JDOMException
	{
		cServiceId = pServiceId;//0
		// /child::midplat/child::atomservices/child::service[(attribute::id = "0")]
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
		//[Element: <service/>]
		cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
	}

	public Document call(Document pInXmlDoc) throws Exception
	{
		//Into CallWebsvcAtomSvc.service()...
		cLogger.info("Into CallWebsvcAtomSvc.service()...");
		
		String mServAddress = cConfEle.getAttributeValue(address);
		String mServMethod = cConfEle.getAttributeValue(method);

		Element mHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		Element mBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String mPrtNo = mBodyEle.getChildText(ProposalPrtNo);
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
//		options.setTimeOutInMilliSeconds(600000);
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

		cLogger.info("Out CallWebsvcAtomSvc.service()!");
		return mOutXmlDoc;
	}
	
	public static void main(String[] args) throws Exception {
		
		String filename = "¼���˱�_inSvc.xml";
		filename="9000102out_Std.xml";
		
		String outfilename =filename.substring(0, filename.indexOf("_"));
		System.out.println(outfilename);
		
		String mInFilePathName = "F:\\����\\�к�\\���ı���\\";
		mInFilePathName="D:/task/20161124/";
		String mOutFilePathName = "F:\\����\\�к�\\���ķ��ر���\\"+outfilename+"_outSvc.xml";
		mOutFilePathName="D:/task/20161124/"+outfilename+"_outSvc.xml";
		
		String serviceid = "";
		
		InputStream mIs = new FileInputStream(mInFilePathName+filename);

		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		serviceid = XmlDoc.getRootElement().getChild("Head").getChildText(ServiceId);
		
		CallWebsvcAtomSvc tCallWebsvcAtomSvc = new CallWebsvcAtomSvc(serviceid);
		
		Document mOutXml = tCallWebsvcAtomSvc.call(XmlDoc);
		
		JdomUtil.print(mOutXml);
		OutputStream mFos = new FileOutputStream(mOutFilePathName);
		
		JdomUtil.output(mOutXml, mFos);
		mFos.flush();
		mFos.close();
		
	}
}
