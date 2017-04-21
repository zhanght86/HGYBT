package com.sinosoft.midplat.spdb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * �̵ƽӿ�
 * @author PengYF
 */
public class GreenTest extends XmlSimpFormat
{
	String tranNo;
	
	public GreenTest(Element pThisConf)
	{
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception
	{
		cLogger.info("Into GreenTest.noStd2Std()...");
		
		/*��¼��������ˮ�ţ����ر���ʱ�á�*/
		tranNo = pNoStdXml.getRootElement().getChild("BUSI").getChildTextTrim("TRANS");
		
		Document mStdXml = GreenTestInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out GreenTest.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception
	{
		cLogger.info("Into GreenTest.std2NoStd()...");
		
		JdomUtil.print(pStdXml);
		Document mNoStdXml = GreenTestOutXsl.newInstance().getCache().transform(pStdXml);
		
		/*���ظ��ַ��ı���������ˮ��*/
		mNoStdXml.getRootElement().getChild("BUSI").getChild("TRANS").setText(tranNo);
		JdomUtil.print(mNoStdXml);
		
		cLogger.info("Out GreenTest.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception{
		
		System.out.println("����ʼ��");
		String mInFilePath = "D:/File/task/20170420/spdb/unit_test/23483_30_0_outSvc.xml";
		String mOutFilePath = "D:/File/task/20170420/spdb/unit_test/23483_33_1001_out.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		Document mOutXmlDoc = new NewCont(null).std2NoStd(mInXmlDoc);
		// Document mOutXmlDoc = new NewCont(null).noStd2Std(mInXmlDoc);
		JdomUtil.print(mOutXmlDoc);
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("�ɹ�������");
	
	}
}