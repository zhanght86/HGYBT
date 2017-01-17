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
		cLogger.info("����FTP�ϴ��ļ�·��:"+coreUploadFilePath);
		try {
			// 1:��¼��־
			cTranLogDB = insertTranLog(pInXmlDoc);
			File file=new File(coreUploadFilePath);
			if(!file.exists()){
				throw new MidplatException("����FTP�ϴ��ļ�·��������:"+coreUploadFilePath);
			}
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
			final String currentDateStr=simpleDateFormat.format(new Date());
			cLogger.info("���մ������ļ���ƥ���������ʽΪ:"+"^\\w{10,15}_[SF]02"+currentDateStr+"_\\d{5}.zip$");
			cLogger.info("�ļ�����Ϊ:"+file.listFiles().length);
			File[] fileList=file.listFiles(new FileFilter() {
				Pattern pattern=Pattern.compile("^\\w{10,15}_[SF]02"+currentDateStr+"_\\d{5}.zip$");
				@Override
				public boolean accept(File filePath) {
					String fileName=filePath.getName();
					Matcher matcher=pattern.matcher(fileName);
					return matcher.matches();
				}
			});
			cLogger.info("���մ�����������Ϊ:"+fileList.length);
			//���۰�����
			int withholdFileSum=0;
			//����������
			int paymentFileSum=0;
			for (File file1 : fileList) {
				String fileName=file1.getName();
				if(fileName.contains("S")){
					//���۰�
					withholdFileSum++;
				}else if(fileName.contains("F")){
					//������
					paymentFileSum++;
				}
			}
			cLogger.info("���۰��ĸ���Ϊ:"+withholdFileSum);
			cLogger.info("�������ĸ���Ϊ:"+paymentFileSum);
			if(withholdFileSum==0){
				withholdFileSum=-1;
				cLogger.info("���۰�δ����");
			}
			if(paymentFileSum==0){
				paymentFileSum=-1;
				cLogger.info("������δ����");
			}
			Element eBody = new Element (Body);
			Element BatIncome = new Element("BatIncome");// �����ļ�����
			BatIncome.setText(""+withholdFileSum);
			eBody.addContent(BatIncome);
			Element BatPay = new Element("BatPay");// ����
			BatPay.setText(""+paymentFileSum);
			eBody.addContent(BatPay);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			cOutXmlDoc.getRootElement().addContent(eBody);
			cTranLogDB.setRCode(0);
			cTranLogDB.setBak2(""+withholdFileSum);
			cTranLogDB.setBak3(""+paymentFileSum);
			cTranLogDB.setRText("���в�ѯ�ɹ��������ļ�����"+withholdFileSum + " �����ļ�����"+paymentFileSum);
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
				cTranLogDB.setRCode(1); // -1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),150, MidplatConf.newInstance().getDBCharset()));
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(1, ex.getMessage());
		}
		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�"+ cTranLogDB.mErrors.getFirstError());
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
