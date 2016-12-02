package com.sinosoft.midplat.newccb.service;

import java.io.File;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatQuery extends ServiceImpl {

	int tPayNum = 0;
	int tIncomeNum = 0;
	
	public ContBatQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
		// TODO Auto-generated constructor stub
	}

	public Document service(Document pInXmlDoc) throws Exception {

		cLogger.info("Into ContBatQuery()...");
		long mStartMillis = System.currentTimeMillis();
		JdomUtil.print(pInXmlDoc);
		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		String tTranDate = tHeadEle.getChildText(TranDate);
		String tNodeNo = tHeadEle.getChildText(NodeNo);
		String tBatchFTPPaht4LIS = NewCcbConf.newInstance().getConf().getRootElement().getChildText("BatchFTPPaht4LIS");
		String tLocalFilePathSnd = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathSnd");
		
		try {
			// 1:记录日志
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			String tSubBankNo = tNodeNo.substring(0, 3);
			cTranLogDB.setNodeNo(tSubBankNo);
			
			File tDateFile = new File(tBatchFTPPaht4LIS);
			if(!tDateFile.exists()){
				throw new MidplatException("路径未找到:"+tBatchFTPPaht4LIS);
			}
			
			File file = new File(tBatchFTPPaht4LIS);
			File[] list=file.listFiles();
			
			for (int i = 0; i <list.length; i++) {
				String tFileName = list[i].getName();
				cLogger.info("tFileName:"+tFileName);
				if(tFileName.length()<30){
					continue;
				}
				String tFileSubBankNo = tFileName.substring(3, 6);
				if(!tFileSubBankNo.equals(tSubBankNo)){
					continue;
				}
				
				String tFileDate = tFileName.substring(9, 17);
				if(!tTranDate.equals(tFileDate)){
					continue;
				}
				String tFileBatFlag = tFileName.substring(2, 3);
				if(tFileBatFlag.equals("0")){
					tIncomeNum++;
				}
				if(tFileBatFlag.equals("1")){
					tPayNum++;
				}
				
				File tFile = new File(tBatchFTPPaht4LIS+tFileName);
				File tMoveFile = new File(tLocalFilePathSnd);
				if(!tMoveFile.exists()){
					tMoveFile.mkdirs();
				}
				if(!tFile.renameTo(new File(tLocalFilePathSnd+tFileName))){
					throw new MidplatException("移动文件失败"+tFileName);
				}
			}
			if(tIncomeNum==0){
				tIncomeNum=-1;
			}
			if(tPayNum==0){
				tPayNum=-1;
			}

			Element eBody = new Element (Body);
			Element BatIncome = new Element("BatIncome");// 代收文件个数
			BatIncome.setText(""+tIncomeNum);
			eBody.addContent(BatIncome);
			Element BatPay = new Element("BatPay");// 代付
			BatPay.setText(""+tPayNum);
			eBody.addContent(BatPay);

			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			cOutXmlDoc.getRootElement().addContent(eBody);
			cTranLogDB.setRCode(0);
			cTranLogDB.setBak2(""+tIncomeNum);
			cTranLogDB.setBak3(""+tPayNum);
			cTranLogDB.setRText("建行查询成功。代收文件数："+tIncomeNum + " 代付文件数："+tPayNum);
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);

			if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
				cTranLogDB.setRCode(1); // -1-未返回；0-交易成功，返回；1-交易失败，返回
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),
						150, MidplatConf.newInstance().getDBCharset()));
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(1, ex.getMessage());
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
		return cOutXmlDoc;
	}

	public static void main(String[] args) throws Exception {

//		Element cThisBusiConf = (Element)XPath.selectSingleNode(CcbConf.newInstance().getConf().getRootElement(), (new StringBuilder("business[funcFlag='")).append("802").append("']").toString());
//		ContBatQuery batch = new ContBatQuery(cThisBusiConf);
//		String mFilePath = "D:\\MyEclipseWorkSpace\\LIAN\\src\\test\\doc\\ccb\\802_inSvc.xml";
//		InputStream mIs = new FileInputStream(mFilePath);
//		Document pInXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(batch.service(pInXmlDoc));
		
		String tFileName = "AL03200702015032601_SOURCE.xml";
		System.out.println(tFileName.substring(9, 17));
	}
}
