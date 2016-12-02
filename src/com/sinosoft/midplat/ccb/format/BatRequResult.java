//�������㽻��
package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class BatRequResult extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	
	public BatRequResult(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into BatRequResult.noStd2Std()...");
		
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			BatRequResultInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out BatRequResult.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into BatRequResult.std2NoStd()...");
		JdomUtil.print(pStdXml);
		Document mNoStdXml = 
			BatRequResultOutXsl.newInstance().getCache().transform(pStdXml);
		JdomUtil.print(mNoStdXml);
		/*Start-��֯���ر���ͷ*/
//		cTransaction_Header=mNoStdXml.getRootElement().getChild("Transaction_Header");
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));

		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		System.out.println("�����־��"+mRetData.getChildText(Flag));
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mBkOthRetCode.setText("00000");
			mBkOthRetMsg.setText("����ȡ�̽��֪ͨ���׳ɹ���");
		} else {	//����ʧ��
			mBkOthRetCode.setText("11111");
			mBkOthRetMsg.setText(
					mRetData.getChildText(Desc));
		}

		Element mTran_Response = new Element("Tran_Response");
		mTran_Response.addContent(mBkOthDate);
		mTran_Response.addContent(mBkOthSeq);
		mTran_Response.addContent(mBkOthRetCode);
		mTran_Response.addContent(mBkOthRetMsg);

		cTransaction_Header.addContent(mTran_Response);

		mNoStdXml.getRootElement().addContent(cTransaction_Header);
		/*End-��֯���ر���ͷ*/

		cLogger.info("Out BatRequResult.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

		String mInFilePath = "C:\\Documents and Settings\\Administrator\\����\\90853014_3_0_outSvc.xml";
		String mOutFilePath = "F:\\���ڽ���test\\ccb_���ؽ��.xml";

//		String mInFilePath = "E:/Test-haoqt/picch/ccb/testXml/BatRequResult/ccb_01_NST_In.xml";
//		String mOutFilePath = "E:/Test-haoqt/picch/ccb/testXml/BatRequResult/ccb_01_out_230701.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

		Document mOutXmlDoc = new BatRequResult(null).std2NoStd(mInXmlDoc);
//		Document mOutXmlDoc = new BatRequResult(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");
	}
}