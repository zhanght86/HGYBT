package com.sinosoft.midplat.newccb.service;

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
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatRequestResult extends ServiceImpl {

	public ContBatRequestResult(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {

		cLogger.info("Into ContBatRequResult()...");
		long mStartMillis = System.currentTimeMillis();
		JdomUtil.print(pInXmlDoc);
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
		String tBatchNo = tBodyEle.getChildText("BatchNo");
		String tErr_ID = tBodyEle.getChildText("Err_ID");
		String tERR_DSC = tBodyEle.getChildText("ERR_DSC");
		
		try {
			// 1:记录日志
			cTranLogDB = insertTranLog(pInXmlDoc);
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBatchNo);
			cTranLogDB.setBak3(tErr_ID);
			cTranLogDB.setBak4(tERR_DSC);
			if(!"00000".equals(tErr_ID)){
				throw new MidplatException(tERR_DSC);
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			cTranLogDB.setRCode(0);
			cTranLogDB.setRText(tERR_DSC);
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);

			if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
				cTranLogDB.setRCode(1); // -1-未返回；0-交易成功，返回；1-交易失败，返回
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),
						150, MidplatConf.newInstance().getDBCharset()));
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
		}
		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！"
						+ cTranLogDB.mErrors.getFirstError());
			}
		}
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}

	public static void main(String[] args) throws Exception {

//		Element cThisBusiConf = (Element)XPath.selectSingleNode(CcbConf.newInstance().getConf().getRootElement(), (new StringBuilder("business[funcFlag='")).append("803").append("']").toString());
//		ContBatRequestResult batch = new ContBatRequestResult(cThisBusiConf);
//		String mFilePath = "D:\\MyEclipseWorkSpace\\LIAN\\src\\test\\doc\\ccb\\803_inSvc.xml";
//		InputStream mIs = new FileInputStream(mFilePath);
//		Document pInXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(batch.service(pInXmlDoc));
		
	
	}
}
