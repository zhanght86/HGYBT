package com.sinosoft.midplat.newabc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * �µ���������ѯ ����ת��
 * @author Liuzk
 *
 */
public class NewContQuery extends XmlSimpFormat {
	private Element header=null;
	

	public NewContQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NewContQuery.noStd2Std()...");
		header=(Element)pNoStdXml.getRootElement().getChild("Header").clone();
		Document mStdXml = NewContQueryInXsl.newInstance().getCache().transform(pNoStdXml);
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NewContQuery.std2NoStd()...");
		Element ttFlag  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Flag");
		Element ttDesc  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Head/Desc");
		Element ttContNo  = (Element) XPath.selectSingleNode(pStdXml.getRootElement(), "/TranData/Body/ContNo");
		Document mNoStdXml = 
			NewContQueryOutXsl.newInstance().getCache().transform(pStdXml);
		//Ϊ����ҵ����ͷ��Ϣ���뷵����ͷ�����Ϣ.�������ҵ����ͷ���뵽���ر����з��ظ����С�
		Element  RetCode= mNoStdXml.getRootElement().getChild("Header").getChild("RetCode");
		Element  RetMsg = mNoStdXml.getRootElement().getChild("Header").getChild("RetMsg");
		//�˱����
		Element  AppResult = mNoStdXml.getRootElement().getChild("App").getChild("Ret").getChild("AppResult");
		
		mNoStdXml.getRootElement().getChild("Header").getChild("BankCode").setText(header.getChildText("BankCode"));
		
		RetMsg.setText(ttDesc.getText());
		if (ttFlag.getValue().equals("0")){
		   cLogger.info("���׳ɹ�=========");
		   RetCode.setText("000000");
		   RetMsg.setText("���׳ɹ�");
		   if(ttContNo.getText()==null)
			   AppResult.setText("A");
		   else
			   AppResult.setText("0");
		}
		if (ttFlag.getValue().equals("1")){
			cLogger.info("����ʧ��=========ʧ����Ϣ:"+RetMsg.getText());
			RetCode.setText("009999");
			RetMsg.setText("����ʧ��");
			AppResult.setText("1");
		}
		
		cLogger.info(RetCode.getText());
		cLogger.info(RetMsg.getText());
		
		JdomUtil.print(mNoStdXml);
		cLogger.info("Out NewContQuery.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");
		String mInFilePath = "C:/Users/Administrator/Desktop/����/��ũ���µ���������ѯ����.xml";
		String mOutFilePath = "C:/Users/Administrator/Desktop/����/��ũ���µ���������ѯ����.xml";

//		String mInFilePath = "C:/Users/Administrator/Desktop/����/��ũ���µ���������ѯ����.xml";
//		String mOutFilePath = "E:\\ccb_20140723Nostd.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

	//	Document mOutXmlDoc = new NewContQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new NewContQuery(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		System.out.println("�ɹ�������");
	}
}
