package com.sinosoft.midplat;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;

public class changsheng_MsgUtil {

	
//	public static  InputStream readMsgAsStream(String path) throws IOException{
//		
//        FileInputStream mFis = new FileInputStream(path);
////		ele.addContent(ele1);
//        
//        
//		Document doc = JdomUtil.build(mFis);
//		 
////		String abc = JdomUtil.toString(doc);
////		InputStream fis = new ByteArrayInputStream(abc.getBytes());
//		
//		InputStream fis = addSOAPMessage(doc);
//		
//		
//		return fis;
//		
//	}
	
	public static InputStream addSOAPMessage(Document doc,String user,String pass) throws IOException{
//		String SOAPEnv ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://ACORD.org/Standards/Life/2\">\n<soapenv:Header></soapenv:Header><soapenv:Body>";
		
		String SOAPEnv = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://ACORD.org/Standards/Life/2\">\n<soapenv:Header>"
			+ "<soapenv:Header>" +
					"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
					"<wsse:UsernameToken wsu:Id=\"UsernameToken-32870670\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" +
					"<wsse:Username>" +user+
					"</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">" 
					+pass+
					"</wsse:Password>" +
					"<wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">LX4gh+njbEtCNAtkwWkXDYA==</wsse:Nonce>" +
					"<wsu:Created>2013-03-12T06:53:37.842Z</wsu:Created></wsse:UsernameToken>" +
					"</wsse:Security>"
			+ "</soapenv:Header>" + "</soapenv:Header><soapenv:Body>";
		
		String abc = JdomUtil.toString(doc);
		abc =abc.substring(38);
//		System.out.println(abc);
		SOAPEnv = SOAPEnv + abc ;
		SOAPEnv =SOAPEnv+"</soapenv:Body></soapenv:Envelope>";
		
		InputStream fis = new ByteArrayInputStream(SOAPEnv.getBytes("utf-8"));
		
		return fis;
		
	}
	
	
	public static Document getOutICBC(String str) throws FileNotFoundException{    
	    Document doc =JdomUtil.build(str);
	    Element ele = doc.getRootElement();
		 System.out.println(ele.getName());
		 List<Element> soapList = ele.getChildren();
		 Element soapBody = soapList.get(1);
		 
		 Element soapTest =soapBody.getChild("TXLife");
		 if(soapTest==null){
			 return null;
		 }
		 System.out.println(soapBody.getChild("TXLife"));
		 Element TXLife = (Element) soapBody.getChild("TXLife").clone();
		 Document doc1  = new Document(TXLife);
		 return doc1;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Document doc = getOutICBC("1");
		 Element ele = doc.getRootElement();
		 
		 System.out.println(ele.getName());
		 
		 List<Element> soapList = ele.getChildren();
		 
		 Element soapBody = soapList.get(1);
		 System.out.println(soapBody.getChild("TXLife"));
		 
		 Element TXLife = (Element) soapBody.getChild("TXLife").clone();
		 
		 Document doc1  = new Document(TXLife);
		 
		 JdomUtil.print(doc1);
		 
		 
		 
	}
}
