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
		//保存报文完毕！1550_6_0_inSvc.xml
		//保存[核心]报文完毕！1561_12_30_inSvc.xml
		cLogger.info("保存报文完毕！" + mSaveName);
		
		String mInXmlStr = JdomUtil.toString(pInXmlDoc);// 上websphere 的时候用这个及下行
		byte[] mInXmlByte = mInXmlStr.getBytes("GBK");// 上websphere的时候用这个

		// byte[] mInXmlByte=JdomUtil.toBytes(pInXmlDoc,"GBK");//在本地测的时候用这个
		//start call 录单核保(http://10.0.1.160:8080/ui/services/ServiceForBankInterfaceService.service)...
		//start call 重空核对(http://10.2.0.33:8001/ui/services/ServiceForBankInterfaceService.service)...
		System.out.println("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");//银行接口服务
		//16:25:47,101 INFO net.CallWebsvcAtomSvc(66) - start call 录单核保(http://10.0.1.160:8080/ui/services/ServiceForBankInterfaceService.service)...
		//start call 重空核对(http://10.2.0.33:8001/ui/services/ServiceForBankInterfaceService.service)...
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
		// //测试时注释
		// String mOutStr;
		// try {
		// mOutStr = (String) mCall.invoke(new String[]{mInXmlStr});
		// } catch (AxisFault ex) {
		// throw new MidplatException(cConfEle.getAttributeValue(name)+"服务异常！",
		// ex);
		// }
		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();

		Options options = serviceClient.getOptions();
		// 设置超时时间
//		options.setTimeOutInMilliSeconds(600000);
		options.setTimeOutInMilliSeconds(800000);
		// 指定调用WebService的URL
		String servicePath = mServAddress + "?wsdl";
		EndpointReference targetEPR = new EndpointReference(servicePath);

		options.setTo(targetEPR);
		// 指定sayHelloToPerson方法的参数值
		Object[] opAddEntryArgs = new Object[] { mInXmlByte };
		// 指定sayHelloToPerson方法返回值的数据类型的Class对象
		Class[] classes = new Class[] { byte[].class };
		// Class[] classes = new Class[] { String.class };
		// 指定要调用的sayHelloToPerson方法及WSDL文件的命名空间
		QName opAddEntry = new QName("http://kernel.ablinkbank.sinosoft.com", mServMethod);
		// 调用sayHelloToPerson方法并输出该方法的返回值
		byte[] mOutStr = (byte[]) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
		// String mOutStr = (String) serviceClient.invokeBlocking(opAddEntry,
		// opAddEntryArgs, classes)[0];
		// String str=new String(mOutStr,"utf-8");
		// System.out.println("sdfdsfsdfdsf==="+str+"长度:"+mOutStr.length);
		cLogger.info("投保单号" + mPrtNo + cConfEle.getAttributeValue(name) + "(" + mServMethod + ")耗时：" + (System.currentTimeMillis() - mStartMillis) / 1000.0 + "s");
		// cLogger.debug(mOutStr);
		//核心请求报文:
		// System.out.println("核心返回的："+new String(mOutStr));
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		//重空核对in_Std.xml
		 JdomUtil.print(mOutXmlDoc);
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(cConfEle.getAttributeValue(name) + "服务返回结果异常！");
		}

		// 无法访问核心时 用于测试

		// ICBCTestUI testUI = new ICBCTestUI();
		// Document mOutXmlDoc = testUI.getXmlFromLis();
		// Document mOutXmlDoc = null;

		mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_outSvc.xml");
		SaveMessage.save(mOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("保存报文完毕！" + mSaveName);

		cLogger.info("Out CallWebsvcAtomSvc.service()!");
		return mOutXmlDoc;
	}
	
	public static void main(String[] args) throws Exception {
		
		String filename = "录单核保_inSvc.xml";
		filename="9000102out_Std.xml";
		
		String outfilename =filename.substring(0, filename.indexOf("_"));
		System.out.println(outfilename);
		
		String mInFilePathName = "F:\\华贵\\中韩\\核心报文\\";
		mInFilePathName="D:/task/20161124/";
		String mOutFilePathName = "F:\\华贵\\中韩\\核心返回报文\\"+outfilename+"_outSvc.xml";
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
