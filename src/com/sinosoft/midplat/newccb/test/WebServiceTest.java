package com.sinosoft.midplat.newccb.test;

import java.io.FileInputStream;
import java.math.BigDecimal;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;

public class WebServiceTest {
    public static void main(String[] args) {
    	String xmlPath="C:\\Users\\anico\\Desktop\\71499_3_6_inSvc.xml";
    	//¹Ø·åip  192.168.199.227  10.2.0.41
		//String wsdl="http://10.2.0.41:8001/ui/services/ServiceForBankInterfaceService?wsdl";
    	String wsdl="http://10.1.0.11:8001/ui/services/ServiceForBankInterfaceService?wsdl";
		try {
			Document doc=JdomUtil.build(new FileInputStream(xmlPath),"GBK");
//			String ProposalPrtNo=doc.getRootElement().getChild("Body").getChildText("ProposalPrtNo");
//			ProposalPrtNo=NumberUtil.no13To15(ProposalPrtNo);
//			doc.getRootElement().getChild("Body").getChild("ProposalPrtNo").setText(ProposalPrtNo);
//			System.out.println(JdomUtil.toStringFmt(doc));
			byte[] bytes=JdomUtil.toBytes(doc,"GBK");
			RPCServiceClient rpcServiceClient = new RPCServiceClient();
			Options options=rpcServiceClient.getOptions();
			options.setTimeOutInMilliSeconds(60*1000);
			EndpointReference endpointReference=new EndpointReference(wsdl);
			options.setTo(endpointReference);
			Object[] objects=new Object[]{bytes};
			QName opAddEntry = new QName("http://kernel.ablinkbank.sinosoft.com","service");
			bytes=(byte[]) rpcServiceClient.invokeBlocking(opAddEntry, objects, new Class[]{byte[].class})[0];
			System.out.println(JdomUtil.toStringFmt(JdomUtil.build(bytes,"GBK")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
