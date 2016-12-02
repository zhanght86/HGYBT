package com.sinosoft.midplat.icbc.format;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.icbc.format.ICBCNewContInXsl;
import com.sinosoft.midplat.icbc.format.ICBCNewContOutXsl;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class ICBCNewCont1126 extends XmlSimpFormat {
	
	public ICBCNewCont1126(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ICBCNewCont.noStd2Std()...");
		
		Document mStdXml = 
			ICBCNewContInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out ICBCNewCont.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ICBCNewContInput.std2NoStd()...==�������㷵��");
		
		Document mNoStdXml =   
			ICBCNewContOutXsl141126.newInstance().getCache().transform(pStdXml);
		
        JdomUtil.print(mNoStdXml);
		
		cLogger.info("Out ICBCNewContInput.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//	System.out.println("����ʼ��");
//
//	String mInFilePath = "H:/��·/�����к�����/���ķ��ر���/��������/956304_51_1012_in.xml";
//	String mOutFilePath = "H:/��·/�����к�����/���ķ��ر���/��������/������������.xml";
//
//	InputStream mIs = new FileInputStream(mInFilePath);
//	Document mInXmlDoc = JdomUtil.build(mIs);
//	mIs.close();
//
////	Document mOutXmlDoc = new ICBCNewCont(null).std2NoStd(mInXmlDoc);
//	Document mOutXmlDoc = new ICBCNewCont(null).noStd2Std(mInXmlDoc);
//
//	JdomUtil.print(mOutXmlDoc);
//
//	OutputStream mOs = new FileOutputStream(mOutFilePath);
//	JdomUtil.output(mOutXmlDoc, mOs);
//	mOs.flush();
//	mOs.close();
//
//	System.out.println("�ɹ�������");
//
//}

}