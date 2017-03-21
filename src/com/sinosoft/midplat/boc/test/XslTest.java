package com.sinosoft.midplat.boc.test;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.boc.format.ContConfirmOutXsl;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;

public class XslTest {
   public static void main(String[] args) {
	try {
		//D:\picchchenjw\天安\需求\贵州\中国银行和贵州银行\中国银行\保费试算1001.xml
		String pathXml="E:\\middleware\\user_projects\\domains\\base_domain\\autodeploy\\HGLIFE\\WEB-INF\\msg\\11\\OutStd\\2016\\201611\\20161108\\2016101410002904_1014_113327.xml";
		//String pathXsl=SysInfo.cBasePath+"com/sinosoft/midplat/boc/format/ContConfirmOut.xsl";
		String pathXsl="E:/middleware/user_projects/domains/base_domain/autodeploy/HGLIFE/WEB-INF/classes/com/sinosoft/midplat/boc/format/ContConfirmOut.xsl";
		System.out.println(pathXsl);
		InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(pathXsl),"GBK");
		//XSLTransformer xslTransformer=
			//new XSLTransformer(inputStreamReader);
		Document xmlDoc=JdomUtil.build(new FileInputStream(pathXml));
//	    Element TranDataEle=new Element("TranData");
//		Element HeadEle=new Element("Head");
//		Element FlagEle=new Element("Flag");
//		FlagEle.setText("0");
//		HeadEle.addContent(FlagEle);
//		TranDataEle.addContent(HeadEle);
		Document doc=ContConfirmOutXsl.newInstance().getCache().transform(xmlDoc);
			//xslTransformer.transform(xmlDoc);
		System.out.println(JdomUtil.toStringFmt(doc));
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
