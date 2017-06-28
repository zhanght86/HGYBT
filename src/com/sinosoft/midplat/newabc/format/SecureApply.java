package com.sinosoft.midplat.newabc.format;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * ��ȫ���� ����ת��
 * @author Liuzk
 *
 */
public class SecureApply extends XmlSimpFormat {
	private Element header=null;

	public SecureApply(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into SecureApply.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Document mStdXml = SecureApplyInXsl.newInstance().getCache().transform(pNoStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into SecureApply.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Document mNoStdXml = SecureApplyOutXsl.newInstance().getCache().transform(pStdXml);
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		//Ϊ����ҵ����ͷ��Ϣ���뷵����ͷ�����Ϣ.�������ҵ����ͷ���뵽���ر����з��ظ����С�
		Element  RetCode= mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element  RetMsg = mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("���׳ɹ�=========");
		   RetCode.setText("000000");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("����ʧ��=========ʧ����Ϣ:"+RetMsg.getText());
			RetCode.setText("009999");
		}
		
		cLogger.info(RetCode.getText());
		cLogger.info(RetMsg.getText());
		
		cLogger.info("Out SecureApply.std2NoStd()!");
		return mNoStdXml;
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println("����ʼ��");
////		String mInFilePath = "C:/Users/Administrator/Desktop/����/��ũ�б�ȫ��������.xml";
////		String mOutFilePath = "C:/Users/Administrator/Desktop/����/��ũ�б�ȫ���뷵��.xml";
//
//		String mInFilePath = "C:/Users/Administrator/Desktop/����/��ũ�б�ȫ���뷵��.xml";
////		String mOutFilePath = "E:\\ccb_20140723Nostd.xml";
//
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//
//		Document mOutXmlDoc = new SecureApply(null).std2NoStd(mInXmlDoc);
////		Document mOutXmlDoc = new SecureApply(null).noStd2Std(mInXmlDoc);
//
//		JdomUtil.print(mOutXmlDoc);
//
////		OutputStream mOs = new FileOutputStream(mOutFilePath);
////		JdomUtil.output(mOutXmlDoc, mOs);
////		mOs.flush();
////		mOs.close();
//		System.out.println("�ɹ�������");
//	}
}
