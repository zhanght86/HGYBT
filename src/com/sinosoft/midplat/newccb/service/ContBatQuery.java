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

import org.apache.commons.net.ftp.FTPFile;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.FTPFILEMAPDao;
import com.sinosoft.midplat.newccb.util.RegisterFtp;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

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
		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		String tTranCom=tHeadEle.getChildText("TranCom");
		Element FTPEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("FTP");
		String ip=FTPEle.getAttributeValue("ip");
		String user=FTPEle.getAttributeValue("user");
		String password=FTPEle.getAttributeValue("password");
		String remoteStr=FTPEle.getAttributeValue("remoteStr");
		try {
			// 1:��¼��־
			cTranLogDB = insertTranLog(pInXmlDoc);
			String checkRemoteFilePath=remoteStr+new SimpleDateFormat("/yyyy-MM-dd").format(new Date());
			RegisterFtp ftp=new RegisterFtp(ip, user, password);
			boolean loginFlag=ftp.loginFTP();
			FTPFile[] fileList=null;
			if(!loginFlag){
				cLogger.info("FTP��¼ʧ��!");
			}else{
				//�������Ƿ����ϴ������ļ�
				fileList=ftp.checkRemoteFile(checkRemoteFilePath);
			}
			String dateFmt=new SimpleDateFormat("yyyyMMdd").format(new Date());
			//���۰�����
			int withholdFileSum=0;
			//����������
			int paymentFileSum=0;
			if(fileList!=null&&fileList.length>0){
				cLogger.info("�������ո���������Ϊ:"+fileList.length);
				ExeSQL exeSql=new ExeSQL();
				for (int i = 0; i < fileList.length; i++) {
					String fileName=fileList[i].getName();
					//��ѯ֮ǰ��ȡ�̳ɹ��Ĵ����¼���������������û�����¼
					String sql="select count(1) from FTPFILEMAP where localname='"+fileName+"' and disposeflag='0' and bankflag='"+tTranCom+"'";
					cLogger.info(sql);
					String val=exeSql.getOneValue(sql);
					if(Integer.parseInt(val)==1){
						continue;
					}
					if(fileName.contains("S")){
						//���հ�
						++withholdFileSum;
					}else if(fileName.contains("F")){
						//������
						++paymentFileSum;
					}
				}
			}
			if(loginFlag==true){
				//�ر�FTP����
				ftp.closeConnect();
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
			cTranLogDB.setRText("����������ѯ�ɹ��������ļ�����"+withholdFileSum + " �����ļ�����"+paymentFileSum);
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
		String[][] st=new String[1][1];
		st[0][0]="sss";
		System.out.println(st[0][0]);
	}
}
