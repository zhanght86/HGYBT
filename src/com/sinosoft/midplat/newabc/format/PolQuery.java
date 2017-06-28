package com.sinosoft.midplat.newabc.format;

import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * ������ѯ ����ת��
 * @author Liuzk
 *
 */
public class PolQuery extends XmlSimpFormat {
	private Element header=null;

	public PolQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into PolQuery.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Document mStdXml = PolQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into PolQuery.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Document mNoStdXml = PolQueryOutXsl.newInstance().getCache().transform(pStdXml);
		
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		//Ϊ����ҵ����ͷ��Ϣ���뷵����ͷ�����Ϣ.�������ҵ����ͷ���뵽���ر����з��ظ����С�
		Element  RetCode= mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element  RetMsg = mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("���׳ɹ�=========");
		   RetCode.setText("000000");
		   RetMsg.setText("���׳ɹ�");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("����ʧ��=========ʧ����Ϣ:"+RetMsg.getText());
			RetCode.setText("009999");
			RetMsg.setText("����ʧ��");
		}
		
		cLogger.info(RetCode.getText());
		cLogger.info(RetMsg.getText());
		
		mNoStdXml.getRootElement().getChild("Header").getChild("SerialNo").setText(header.getChildText("SerialNo"));
		mNoStdXml.getRootElement().getChild("Header").getChild("InsuSerial").setText(header.getChildText("InsuSerial"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransTime").setText(header.getChildText("TransTime"));
		mNoStdXml.getRootElement().getChild("Header").getChild("TransDate").setText(header.getChildText("TransDate"));
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		mNoStdXml.getRootElement().getChild("Header").getChild("CorpNo").setText(header.getChildText("CorpNo"));
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out PolQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");
//		String mInFilePath = "E:/Java/����/��ũ�б�����ѯ����.xml";
//		String mOutFilePath = "E:/Java/����/��ũ�б�����ѯ����.xml";

		String mInFilePath = "H:/998700_11_45_outSvc.xml";
//		String mOutFilePath = "E:\\ccb_20140723Nostd.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

		Document mOutXmlDoc = new PolQuery(null).std2NoStd(mInXmlDoc);
//		Document mOutXmlDoc = new PolQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
		System.out.println("�ɹ�������");
	}
}
