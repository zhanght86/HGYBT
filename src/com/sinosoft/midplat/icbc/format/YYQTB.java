/*
 * ��ԥ���˱��齻��
 */
package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.ccb.format.NewCont;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class YYQTB extends XmlSimpFormat {
	private Element cTXLifeRequest = null;
	public YYQTB(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into YYQTB.noStd2Std()...");
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTXLifeRequest =
			(Element) pNoStdXml.getRootElement().getChild("TXLifeRequest").clone();
		Document mStdXml = 
			YYQTBInXsl.newInstance().getCache().transform(pNoStdXml);
		//����������ն˽���(atm)����ô�ڴ����⴦�����ױ���Ҳ��һ����
//		Element mtrandate = mStdXml.getRootElement();
//		Element mhead = mtrandate.getChild("Head");
//		Element mBody = mtrandate.getChild("Body");
//		String mSourceType = mhead.getChildText("SourceType");
//		if(mSourceType.equals("atm"))
//		{
//			mhead.getChild(FuncFlag).setText("63");
//			mhead.removeChild("FuncFlagDetail");
//			mBody.removeChild("ProviderFormNumber");
//			mBody.removeChild("AccountNumber");
//			mBody.removeChild("AcctHolderName");
//		}
		cLogger.info("Out YYQTB.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into YYQTB.std2NoStd()...");
		JdomUtil.print(pStdXml);
		Document mNoStdXml = 
			YYQTBOutXsl.newInstance().getCache().transform(pStdXml);
		
		//��֯���ظ����еı���
		Element mRootEle = mNoStdXml.getRootElement();
		Element mTXLifeResponse = mRootEle.getChild("TXLifeResponse");
		Element mTransType = new Element("TransType");
		Element mTransExeDate = new Element("TransExeDate");
		Element mTransExeTime = new Element("TransExeTime");
		Element mTransRefGUID = new Element("TransRefGUID");
		mTransType.setText(cTXLifeRequest.getChildText("TransType"));
		mTransExeDate.setText(cTXLifeRequest.getChildText("TransExeDate"));
		mTransExeTime.setText(cTXLifeRequest.getChildText("TransExeTime"));
		mTransRefGUID.setText(cTXLifeRequest.getChildText("TransRefGUID"));
		mTXLifeResponse.addContent(mTransType);
		mTXLifeResponse.addContent(mTransExeDate);
		mTXLifeResponse.addContent(mTransExeTime);
		mTXLifeResponse.addContent(mTransRefGUID);
		cLogger.info("Out YYQTB.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

		String mInFilePath = "C:\\Users\\lmt\\Desktop\\WY��ATM���Ա���\\��ԥ�ڳ������˱�����(TC=105).xml";
		String mOutFilePath = "e:\\icbc_���ؽ��.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/newCont/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

//		Document mOutXmlDoc = new YYQTB(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new YYQTB(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");
	}
}