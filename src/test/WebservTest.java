package test;

import java.io.FileInputStream;
import java.io.InputStream;

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
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
/**
 * Web服务测试类
 * @author yuantongxin
 */
public class WebservTest implements XmlTag
{
	private final static Logger cLogger = Logger.getLogger(WebservTest.class);//为Web服务测试类加入日志

	private final String cServiceId;//服务Id
	private final Element cConfEle;//元素
	
	/**
	 *  Web服务测试类有参构造
	 * @param pServiceId 服务Id
	 * @throws JDOMException java文档对象模型
	 */
		public WebservTest(String pServiceId) throws JDOMException
	{
		cServiceId = pServiceId;//服务Id
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");//
		cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		cLogger.info(JdomUtil.toStringFmt(cConfEle));
	}

	public Document call(Document pInXmlDoc) throws Exception
	{
		cLogger.info("Into CallWebsvcAtomSvc.service()...");

		String mServAddress = cConfEle.getAttributeValue(address);
		String mServMethod = cConfEle.getAttributeValue(method);

		Element mHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		Element mBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String mPrtNo = mBodyEle.getChildText(ProposalPrtNo);

		String mInXmlStr = JdomUtil.toString(pInXmlDoc);// 上websphere 的时候用这个及下行
		cLogger.info("请求核心报文："+JdomUtil.toStringFmt(pInXmlDoc));
		byte[] mInXmlByte = mInXmlStr.getBytes("GBK");// 上websphere的时候用这个

		// byte[] mInXmlByte=JdomUtil.toBytes(pInXmlDoc,"GBK");//在本地测的时候用这个
		System.out.println("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		cLogger.info("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		long mStartMillis = System.currentTimeMillis();
		RPCServiceClient serviceClient = new RPCServiceClient();

		Options options = serviceClient.getOptions();
		// 设置超时时间
		options.setTimeOutInMilliSeconds(60000);

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
		cLogger.info("投保单号" + mPrtNo + cConfEle.getAttributeValue(name) + "(" + mServMethod + ")耗时：" + (System.currentTimeMillis() - mStartMillis) / 1000.0 + "s");
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(cConfEle.getAttributeValue(name) + "服务返回结果异常！");
		}

		cLogger.info("Out CallWebsvcAtomSvc.service()!");
		return mOutXmlDoc;
	}
	
	public static void main(String[] args) throws Exception {
		
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\录单核保_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\收费签单_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新单回滚_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\保单重打_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\当日撤单_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新单日结_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\单证对账_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\银保犹豫期退保查询_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\银保犹豫期退保_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\银保犹豫期退保冲正_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\银保退保查询_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\银保退保_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\银保退保冲正_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\保全对账_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\非实时日终对账_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\非实时超时对账_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\非实时结果文件返回_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\非实时投保单状态变更文件返回_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\工行保单状态变更文件返回_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新建行续期对账_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行查询保单历史变动_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\打印投保单_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\查询缴纳保费信息_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\打印保单_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\重空核对_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\确认续期缴费_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\确认取消续期缴费_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行修改保单基本信息_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行查询客户保单_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行查询保单详情（寿险）_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行获取保单详情取数_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行获取保单详情查询_inSvc.xml";--？？？
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行查询保险公司巡逻员信息_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行试算保险产品_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\宁波银行银保核退保交易_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行试算结果查询_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行保单查询_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行保全申请状态查询_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行犹豫期退保结果文件_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行非实时出单结果文件_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行手工单出单结果文件_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行非实时/手工单出单险种明细_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新建行退保申请_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新建行退保确认_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新建行代理保险售后提醒查询_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新建行代理保险售后提醒取数_inSvc.xml";
		String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新建行申请登记台账_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新建行确认登记台账_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\新农行保单详情查询_inSvc.xml";-->未测
		//String mInFilePathName = "D:\\picchchenjw\\天安\\需求\\贵州\\核心报文\\建行试算保险产品（寿险）_inSvc.xml";-->未测
		mInFilePathName="D:/work/04新单撤单.xml";
		String serviceid = "";
		InputStream mIs = new FileInputStream(mInFilePathName);

		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		serviceid = XmlDoc.getRootElement().getChild("Head").getChildText(ServiceId);
		
		WebservTest tWebservTest = new WebservTest(serviceid);
		
		Document mOutXml = tWebservTest.call(XmlDoc);
		
		cLogger.info("核心返回数据："+JdomUtil.toStringFmt(mOutXml));
	}
}
