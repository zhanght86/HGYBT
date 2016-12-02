package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatRequest extends ServiceImpl {

	
	public ContBatRequest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {

		cLogger.info("Into ContBatRequest()...");
		long mStartMillis = System.currentTimeMillis();
		JdomUtil.print(pInXmlDoc);
		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		String tNodeNo = tHeadEle.getChildText(NodeNo);
		
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
		String tType = tBodyEle.getChildText("Type");
		String tLocalFilePathSnd = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathSnd");
		String sFilePathSnd= NewCcbConf.newInstance().getConf().getRootElement().getChildText("FilePathSnd");
		try {
			// 1:��¼��־
			cTranLogDB = insertTranLog(pInXmlDoc);

			String tSubBankNo = tNodeNo.substring(0, 3);
			cTranLogDB.setNodeNo(tSubBankNo);
			
			File tFile = new File(tLocalFilePathSnd+tFileName+"_SOURCE.xml");
			if(!tFile.exists()){
				throw new MidplatException("���ļ�������:"+tFileName);
			}
			Document tReturnDoc = JdomUtil.build(new FileInputStream(tFile));
			String tSumNum = tReturnDoc.getRootElement().getChild(Head).getChildText("Cur_Btch_Dtl_TDnum");
			String tSumAmt = tReturnDoc.getRootElement().getChild(Head).getChildText("Cur_Btch_Dtl_TAmt");
			
			Element eBody = new Element (Body);
			
			Element eFileName = new Element("FileName");
			eFileName.setText(tFileName);
			eBody.addContent(eFileName);
			
			Element eNum = new Element("Num");
			eNum.setText(String.valueOf(tSumNum));
			eBody.addContent(eNum);
			
			Element eSumAmt = new Element("SumAmt");
			eSumAmt.setText(tSumAmt);
			eBody.addContent(eSumAmt);
			
			Element eFilePath = new Element("FilePath");
			eFilePath.setText(sFilePathSnd);
			eBody.addContent(eFilePath);
			
			Element eType = new Element("Type");
			eType.setText(tType);
			eBody.addContent(eType);
		
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			cOutXmlDoc.getRootElement().addContent(eBody);
			
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setRText("����ȡ�̳ɹ�");
			cTranLogDB.setRCode(0);
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);

			if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
				cTranLogDB.setRCode(1); // -1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),
						150, MidplatConf.newInstance().getDBCharset()));
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(1, ex.getMessage());
		}
		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�"
						+ cTranLogDB.mErrors.getFirstError());
			}
		}
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}

	public static void main(String[] args) throws Exception {

//		Element cThisBusiConf = (Element)XPath.selectSingleNode(CcbConf.newInstance().getConf().getRootElement(), (new StringBuilder("business[funcFlag='")).append("803").append("']").toString());
//		ContBatRequest batch = new ContBatRequest(cThisBusiConf);
//		String mFilePath = "D:\\MyEclipseWorkSpace\\LIAN\\src\\test\\doc\\ccb\\803_inSvc.xml";
//		InputStream mIs = new FileInputStream(mFilePath);
//		Document pInXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(batch.service(pInXmlDoc));
		
	
	}
}
