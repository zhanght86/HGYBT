//�������ڽɷѽ���
package com.sinosoft.midplat.boc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class RenewalPay extends XmlSimpFormat {
	private Element cMain = null;
	
	public RenewalPay(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into RenewalPay.noStd2Std()...");
		cLogger.info("������������:"+JdomUtil.toStringFmt(pNoStdXml));
		
		cMain = (Element) pNoStdXml.getRootElement().getChild("Main").clone();
		
		Document mStdXml = 
			RenewalPayInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("������ı���:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out RenewalPay.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into RenewalPay.std2NoStd()...");
		cLogger.info("���ķ��ر���:"+JdomUtil.toStringFmt(pStdXml));
		
		Document mNoStdXml = 
			RenewalPayOutXsl.newInstance().getCache().transform(pStdXml);
		
		/*Start-��֯���ر���ͷ*/
		Element mTranDate = new Element("TranDate");
		Element mTranTime = new Element("TranTime");
		Element mInsuId = new Element("InsuId");
		Element mZoneNo = new Element("ZoneNo");
		Element mBrNo = new Element("BrNo");
		Element mTellerNo = new Element("TellerNo");
		Element mTransNo = new Element("TransNo");
		Element mTranCode = new Element("TranCode");
		Element mResultCode = new Element("ResultCode");
		Element mResultInfo = new Element("ResultInfo");
		mTranDate.setText(cMain.getChildText("TranDate"));
		mTranTime.setText(cMain.getChildText("TranTime"));
		mInsuId.setText(cMain.getChildText("InsuId"));
		mZoneNo.setText(cMain.getChildText("ZoneNo"));
		mBrNo.setText(cMain.getChildText("BrNo"));
		mTellerNo.setText(cMain.getChildText("TellerNo"));
		mTransNo.setText(cMain.getChildText("TransNo"));
		mTranCode.setText(cMain.getChildText("TranCode"));
		Element dMain = mNoStdXml.getRootElement().getChild("Main");
		String wResultCode = dMain.getChildText("ResultCode");
		String wResultInfo = dMain.getChildText("ResultInfo");
		mResultCode.setText(wResultCode);
		mResultInfo.setText(wResultInfo);
		dMain.removeChild("ResultCode");
		dMain.removeChild("ResultInfo");
		dMain.addContent(mTranDate);
		dMain.addContent(mTranTime);
		dMain.addContent(mInsuId);
		dMain.addContent(mZoneNo);
		dMain.addContent(mBrNo);
		dMain.addContent(mTellerNo);
		dMain.addContent(mTransNo);
		dMain.addContent(mTranCode);
		dMain.addContent(mResultCode);
		dMain.addContent(mResultInfo);

		/*End-��֯���ر���ͷ*/

		cLogger.info("���ظ�����������:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out RenewalPay.std2NoStd()!");
		return mNoStdXml;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");
		
		String mInFilePath = "E:\\���չ�˾\\����\\����ͨ�ĵ�\\����\\�����ṩ����\\���ױ���ģ��\\���ڽɷѲ�ѯ������.xml";
		String mOutFilePath = "F:\\��������test\\boc.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new RenewalPay(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new RenewalPay(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();
		
		System.out.println("�ɹ�������");
	}
}