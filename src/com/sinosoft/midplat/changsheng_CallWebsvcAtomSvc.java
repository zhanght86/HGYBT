package com.sinosoft.midplat;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.XmlTag;

public class changsheng_CallWebsvcAtomSvc implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(changsheng_CallWebsvcAtomSvc.class);
	
	private final String cServiceId;
	private final Element cConfEle;
	
	public changsheng_CallWebsvcAtomSvc(String pServiceId) throws JDOMException {
		cServiceId = pServiceId;
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
		cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
	}
	
	public Document call(Document pInXmlDoc) throws Exception {
		cLogger.info("Into CallWebsvcAtomSvc.service()...");
		 
		//JdomUtil.print(pInXmlDoc);
		
		String mServAddress = cConfEle.getAttributeValue("address");
		String mServMethod = cConfEle.getAttributeValue("method");
		XPath mXPathSecurity = XPath.newInstance("/midplat/atomservices/service[@id='88']");
		Element mSecurity = (Element) mXPathSecurity.selectSingleNode(MidplatConf.newInstance().getConf());
		String mUserName = mSecurity.getAttributeValue("user");
		String mPassWord = mSecurity.getAttributeValue("pass");
		
		String mInXmlStr = JdomUtil.toString(pInXmlDoc);
		String mOutStr="";
		cLogger.debug(mInXmlStr);
		
		cLogger.info("========================开始调用webservice： " + cConfEle.getAttributeValue("name") + "(" + mServAddress + "." +  ")...===========================");
         long startWebService = System.currentTimeMillis();
		  SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
          SOAPConnection connection = soapConnFactory.createConnection();
          MessageFactory messageFactory = MessageFactory.newInstance();
          SOAPMessage message = messageFactory.createMessage();
          // Create objects for the message parts
          SOAPPart soapPart = message.getSOAPPart();
          SOAPEnvelope envelope = soapPart.getEnvelope();
//          SOAPBody body = envelope.getBody();
          StreamSource xmlSource1 = new StreamSource(changsheng_MsgUtil.addSOAPMessage(pInXmlDoc,mUserName,mPassWord));
          soapPart.setContent(xmlSource1);
          // Save the message
          message.saveChanges();
          // Check the input
          message.writeTo(System.out);
 
          SOAPMessage reply = connection.call(message, mServAddress);
          TransformerFactory transformerFactory = TransformerFactory.newInstance();
          Transformer transformer = transformerFactory.newTransformer();
          // Extract the content of the reply
          Source sourceContent = reply.getSOAPPart().getContent();
          
          
          StreamResult result = new StreamResult(new ByteArrayOutputStream());
          transformer.transform(sourceContent, result);
//          String stResult= ((ByteArrayOutputStream)result.getOutputStream()).toString();
          String stResult= ((ByteArrayOutputStream)result.getOutputStream()).toString("utf-8");
          
          cLogger.info("看看返回"+stResult);
          // Close the connection
          connection.close();
 
          
		  Document mOutXmlDoc=null; 
		  mOutXmlDoc = changsheng_MsgUtil.getOutICBC(stResult);
          if(mOutXmlDoc == null){
        	  //没有YbtUtil这个类，只能注释掉20141210
//        	  mOutXmlDoc = YbtUtil.getICBCSimpOutXml(pInXmlDoc.getRootElement().getChild("TXLifeRequest").getChildText("TransRefGUID"), pInXmlDoc.getRootElement().getChild("TXLifeRequest").getChild("OLifEExtension").getChildText("TransNo"), "节点有误,请联系保险公司");
        	  cLogger.info("========================调用webservice结束--有异常！调用时间为:"+(System.currentTimeMillis()-startWebService)+"(.ms)=================");
        	  return mOutXmlDoc;
          }
 
          cLogger.info("========================调用webservice结束--正常！调用时间为:"+(System.currentTimeMillis()-startWebService)+"(.ms)=================");
 
		StringBuffer mSaveName2 = new StringBuffer("ICC"+pInXmlDoc.getRootElement().getChild("TXLifeRequest").getChildText("TransRefGUID"))
			.append('_').append(PubFun1.CreateMaxNo("OutSvc", 8))
			.append('_').append(cServiceId)
			.append("_outSvc.xml");
		SaveMessage.save(mOutXmlDoc, mSaveName2.toString(), "OutWebSv");
		cLogger.info("返回的webservice保存完毕，文件名为"+ mSaveName2.toString());
		Element mRootEle = mOutXmlDoc.getRootElement();
 
		cLogger.info("Out CallWebsvcAtomSvc.service()!");
		return mOutXmlDoc;
	}
	
}
