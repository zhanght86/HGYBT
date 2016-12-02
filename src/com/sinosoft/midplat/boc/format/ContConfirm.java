//��������Լȷ�Ͻ���
package com.sinosoft.midplat.boc.format;

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

import com.sinosoft.midplat.format.XmlSimpFormat;

public class ContConfirm extends XmlSimpFormat {
	private Element cMain = null;
	
	public ContConfirm(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		
		cMain =(Element) pNoStdXml.getRootElement().getChild("Main").clone();
		
		Document mStdXml = 
			ContConfirmInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	}
	
	/**
	 * ���ش�Ͳ�ѯʹ�á�
	 */
	void setHeader(Element pMain) {
		cMain = pMain;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");
		
		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(pStdXml);	

		/*Start-��֯���ر���ͷ*/
		Element mResultCode = new Element("ResultCode");
		Element mResultInfo = new Element("ResultInfo");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mResultCode.setText("0000");
			mResultInfo.setText("���׳ɹ���");
		} else {	//����ʧ��
			mResultCode.setText("0001");
			mResultInfo.setText(
					mRetData.getChildText(Desc));
		}
		Element dMain = new Element("Main");
		Element mTranDate = new Element("TranDate");
		Element mTranTime = new Element("TranTime");
		Element mInsuId = new Element("InsuId");
		Element mZoneNo = new Element("ZoneNo");
		Element mBrNo = new Element("BrNo");
		Element mTellerNo = new Element("TellerNo");
		Element mTransNo = new Element("TransNo");
		Element mTranCode = new Element("TranCode");
		dMain.addContent(mTranDate.setText(cMain.getChildText("TranDate")));
		dMain.addContent(mTranTime.setText(cMain.getChildText("TranTime")));
		dMain.addContent(mInsuId.setText(cMain.getChildText("InsuId")));
		dMain.addContent(mZoneNo.setText(cMain.getChildText("ZoneNo")));
		dMain.addContent(mBrNo.setText(cMain.getChildText("BrNo")));
		dMain.addContent(mTellerNo.setText(cMain.getChildText("TellerNo")));
		dMain.addContent(mTransNo.setText(cMain.getChildText("TransNo")));
		dMain.addContent(mTranCode.setText(cMain.getChildText("TranCode")));

		dMain.addContent(mResultCode);
		dMain.addContent(mResultInfo);
		Element mPrintNo = mNoStdXml.getRootElement().getChild("Policy").getChild("PrintNo");
		mPrintNo.setText(cMain.getChildText("PrintNo"));
		mNoStdXml.getRootElement().addContent(dMain);
		
		/*End-��֯���ر���ͷ*/
		
		List<Element> tPageContentList = XPath.selectNodes(mNoStdXml.getRootElement(), "/InsuRet/Print/Paper/PageContent");
		Element tPrintEle = (Element) XPath.selectSingleNode(mNoStdXml.getRootElement(), "/InsuRet/Print/Paper");
		Element tRowCount = null;
		Element tPageCount = tPrintEle.getChild("PageCount");
		tPageCount.setText(String.valueOf(tPageContentList.size()));//ƾ֤����
		//���ÿһҳ��������
		for(int i=0;i< tPageContentList.size();i++){
		List<Element> tRowList = tPageContentList.get(i).getChild("Details").getChildren("Row");
		int cRow = tRowList.size();
		tRowCount = tPageContentList.get(i).getChild("RowCount");
		tRowCount.setText(String.valueOf(cRow));
		} 

		
		cLogger.info("Out ContConfirm.std2NoStd()!");
		return mNoStdXml;
	}

	

	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ��");

//		String mInFilePath = "C:\\Documents and Settings\\Administrator\\My Documents\\boc_0000000000006575_01_165016_outstd.xml";
		String mInFilePath = "";//"C:\\Documents and Settings\\Administrator\\����\\9006_84065220130004201_01_100110.xml";
//		mInFilePath="D:/task/20161108/9006out.xml";
		mInFilePath="D:/task/20161108/06out.xml";
		String mOutFilePath = "";//"E:\\boc.xml";
//		mOutFilePath="D:/task/20161108/9006noout.xml";
		mOutFilePath="D:/task/20161108/06noout.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
		Document mOutXmlDoc = new ContConfirm(null).std2NoStd(mInXmlDoc);
//		Document mOutXmlDoc = new ContConfirm(null).noStd2Std(mInXmlDoc);
		
		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("�ɹ�������");
	}
}
