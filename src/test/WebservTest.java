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
 * Web���������
 * @author yuantongxin
 */
public class WebservTest implements XmlTag
{
	private final static Logger cLogger = Logger.getLogger(WebservTest.class);//ΪWeb��������������־

	private final String cServiceId;//����Id
	private final Element cConfEle;//Ԫ��
	
	/**
	 *  Web����������вι���
	 * @param pServiceId ����Id
	 * @throws JDOMException java�ĵ�����ģ��
	 */
		public WebservTest(String pServiceId) throws JDOMException
	{
		cServiceId = pServiceId;//����Id
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

		String mInXmlStr = JdomUtil.toString(pInXmlDoc);// ��websphere ��ʱ�������������
		cLogger.info("������ı��ģ�"+JdomUtil.toStringFmt(pInXmlDoc));
		byte[] mInXmlByte = mInXmlStr.getBytes("GBK");// ��websphere��ʱ�������

		// byte[] mInXmlByte=JdomUtil.toBytes(pInXmlDoc,"GBK");//�ڱ��ز��ʱ�������
		System.out.println("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		cLogger.info("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		long mStartMillis = System.currentTimeMillis();
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
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(cConfEle.getAttributeValue(name) + "���񷵻ؽ���쳣��");
		}

		cLogger.info("Out CallWebsvcAtomSvc.service()!");
		return mOutXmlDoc;
	}
	
	public static void main(String[] args) throws Exception {
		
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\¼���˱�_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�շ�ǩ��_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�µ��ع�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�����ش�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���ճ���_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�µ��ս�_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��֤����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\������ԥ���˱���ѯ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\������ԥ���˱�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\������ԥ���˱�����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�����˱���ѯ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�����˱�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�����˱�����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ȫ����_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ʵʱ���ն���_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ʵʱ��ʱ����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ʵʱ����ļ�����_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ʵʱͶ����״̬����ļ�����_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���б���״̬����ļ�����_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�½������ڶ���_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���в�ѯ������ʷ�䶯_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ӡͶ����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ѯ���ɱ�����Ϣ_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ӡ����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�ؿպ˶�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\ȷ�����ڽɷ�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\ȷ��ȡ�����ڽɷ�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�����޸ı���������Ϣ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���в�ѯ�ͻ�����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���в�ѯ�������飨���գ�_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���л�ȡ��������ȡ��_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���л�ȡ���������ѯ_inSvc.xml";--������
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���в�ѯ���չ�˾Ѳ��Ա��Ϣ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�������㱣�ղ�Ʒ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\���������������˱�����_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ����������ѯ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ�б�����ѯ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ�б�ȫ����״̬��ѯ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ����ԥ���˱�����ļ�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ�з�ʵʱ��������ļ�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ���ֹ�����������ļ�_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ�з�ʵʱ/�ֹ�������������ϸ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�½����˱�����_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�½����˱�ȷ��_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�½��д������ۺ����Ѳ�ѯ_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�½��д������ۺ�����ȡ��_inSvc.xml";
		String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�½�������Ǽ�̨��_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�½���ȷ�ϵǼ�̨��_inSvc.xml";
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\��ũ�б��������ѯ_inSvc.xml";-->δ��
		//String mInFilePathName = "D:\\picchchenjw\\�찲\\����\\����\\���ı���\\�������㱣�ղ�Ʒ�����գ�_inSvc.xml";-->δ��
		mInFilePathName="D:/work/04�µ�����.xml";
		String serviceid = "";
		InputStream mIs = new FileInputStream(mInFilePathName);

		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		serviceid = XmlDoc.getRootElement().getChild("Head").getChildText(ServiceId);
		
		WebservTest tWebservTest = new WebservTest(serviceid);
		
		Document mOutXml = tWebservTest.call(XmlDoc);
		
		cLogger.info("���ķ������ݣ�"+JdomUtil.toStringFmt(mOutXml));
	}
}
