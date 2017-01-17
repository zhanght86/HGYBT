package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
//		JdomUtil.print(pInXmlDoc);
//		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
//		String tTranDate = tHeadEle.getChildText(TranDate);
//		String tNodeNo = tHeadEle.getChildText(NodeNo);
//		String tBatchFTPPaht4LIS = NewCcbConf.newInstance().getConf().getRootElement().getChildText("BatchFTPPaht4LIS");
//		String tLocalFilePathSnd = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathSnd");
		String coreUploadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
		cLogger.info("核心FTP上传文件路径:"+coreUploadFilePath);
		try {
			// 1:记录日志
			cTranLogDB = insertTranLog(pInXmlDoc);
			File file=new File(coreUploadFilePath);
			if(!file.exists()){
				throw new MidplatException("核心FTP上传文件路径不存在:"+coreUploadFilePath);
			}
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
			final String currentDateStr=simpleDateFormat.format(new Date());
			cLogger.info("代收代付包文件名匹配的正则表达式为:"+"^\\w{10,15}_[SF]02"+currentDateStr+"_\\d{5}.zip$");
			cLogger.info("文件总数为:"+file.listFiles().length);
			File[] fileList=file.listFiles(new FileFilter() {
				Pattern pattern=Pattern.compile("^\\w{10,15}_[SF]02"+currentDateStr+"_\\d{5}.zip$");
				@Override
				public boolean accept(File filePath) {
					String fileName=filePath.getName();
					Matcher matcher=pattern.matcher(fileName);
					return matcher.matches();
				}
			});
			cLogger.info("代收代付包的总数为:"+fileList.length);
			//代扣包个数
			int withholdFileSum=0;
			//代付包个数
			int paymentFileSum=0;
			for (File file1 : fileList) {
				String fileName=file1.getName();
				if(fileName.contains("S")){
					//代扣包
					withholdFileSum++;
				}else if(fileName.contains("F")){
					//代付包
					paymentFileSum++;
				}
			}
			cLogger.info("代扣包的个数为:"+withholdFileSum);
			cLogger.info("代付包的个数为:"+paymentFileSum);
			if(withholdFileSum==0){
				withholdFileSum=-1;
				cLogger.info("代扣包未生成");
			}
			if(paymentFileSum==0){
				paymentFileSum=-1;
				cLogger.info("代付包未生成");
			}
			Element eBody = new Element (Body);
			Element BatIncome = new Element("BatIncome");// 代收文件个数
			BatIncome.setText(""+withholdFileSum);
			eBody.addContent(BatIncome);
			Element BatPay = new Element("BatPay");// 代付
			BatPay.setText(""+paymentFileSum);
			eBody.addContent(BatPay);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			cOutXmlDoc.getRootElement().addContent(eBody);
			cTranLogDB.setRCode(0);
			cTranLogDB.setBak2(""+withholdFileSum);
			cTranLogDB.setBak3(""+paymentFileSum);
			cTranLogDB.setRText("建行查询成功。代收文件数："+withholdFileSum + " 代付文件数："+paymentFileSum);
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
				cTranLogDB.setRCode(1); // -1-未返回；0-交易成功，返回；1-交易失败，返回
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),150, MidplatConf.newInstance().getDBCharset()));
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(1, ex.getMessage());
		}
		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！"+ cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
		return cOutXmlDoc;
	}
	public static void main(String[] args) throws Exception {
//		uncompressZipFile ("C:/Users/anico/Desktop/test/","test.zip");
//		System.exit(0);
		File file=new File("C:/Users/anico/Desktop/test/test.zip");
		System.out.println(file.getPath().substring(0,file.getPath().lastIndexOf(".")));
	}
}
