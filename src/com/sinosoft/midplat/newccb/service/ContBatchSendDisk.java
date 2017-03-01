package com.sinosoft.midplat.newccb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import org.jdom.Document;
import org.jdom.Element;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatchSendDisk extends ServiceImpl {

	public ContBatchSendDisk(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) throws Exception{
		cLogger.info("Into ContBatchSendDisk.service()...");
		long mStartMillis=System.currentTimeMillis();
		//��׼���뱨��
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//���������ļ����ܴ洢·��
		String localDir=this.cThisBusiConf.getChildText("LocalDir");
		//���ж˰�ȫ�ڵ��
		String localID=pInXmlDoc.getRootElement().getChild(Head).getChildText("LocalID");
		//���չ�˾�˰�ȫ�ڵ��
		String remoteID=pInXmlDoc.getRootElement().getChild(Head).getChildText("remoteID");
		//���з�PUT�ļ����ļ�·�������չ�˾���գ�
		String filePath="/home/ap/fserver2/rcv/";
		Element tBody=pInXmlDoc.getRootElement().getChild(Body);
		//����������������
		String tFileName=tBody.getChildText("FileName");
		//���л����ļ�·��
		cLogger.info("���л����ļ�·��:"+tFileName+tFileName);
		//����������������״̬����
		int tBatFlag=Integer.parseInt(tBody.getChildText("BatFlag"));
		//��ǰ����ϸ�ܱ���
		String tNum=tBody.getChildText("Num");
		//��ǰ����ϸ�ܽ��
		String tSumAmt=tBody.getChildText("SumAmt");
		/**���뽻����־,����[����1:����������������,����2:����������������״̬����,����3:��ǰ����ϸ�ܱ���,����4:��ǰ����ϸ�ܽ��]*/
		try {
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBody.getChildText("BatFlag"));
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			String descStr=null;
			if(!"00".equals(tBatFlag)||!"16".equals(tBatFlag)){
				switch (tBatFlag) {
					case 1:descStr="δ�յ��˰�";break;
					case 2:descStr="������ϸ�ܱ�����ʵ����ϸ�������ܲ���";break;
					case 3:descStr="������ϸ�ܽ����ʵ����ϸ�����ܲ���";break;
					case 4:descStr="����������������";break;
					case 5:descStr="����ϸ���ڸ����";break;
					case 6:descStr="���չ�˾�������������Ʋ������򷵻�������";break;
					case 7:descStr="����ϸ��Ϊ0";break;
					case 8:descStr="������У����ȷ";break;
					case 9:descStr="������δ����";break;
					case 10:descStr="������������";break;
					case 11:descStr="�����������ظ��ύ,�ظ�������Ϊx";break;
					case 12:descStr="������������Ӧ�ļ�ʧ��";break;
					case 13:descStr="���չ�˾�˻����㣬����ʧ��";break;
					case 14:descStr="������ϸ����ظ�";break;
					case 15:descStr="�������ļ���ʽ����";break;
					case 99:descStr="�����ڲ����������Ҫ�˹���ʵ���";break;
				}
				throw new MidplatException(descStr);
			}
			cLogger.info("���ж˰�ȫ�ڵ��:"+localID+"���չ�˾�˰�ȫ�ڵ��:"+remoteID+"����ǰ·��:"+ filePath+tFileName+"���ܺ�·��:"+localDir+tFileName);
			//����
			SecAPI.fileUnEnvelop(localID, remoteID, filePath+tFileName, localDir+tFileName);
			//�򵥱���
			MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			if(cTranLogDB!=null){
				cTranLogDB.setRCode(0);
				cTranLogDB.setRText("���׳ɹ�");
			}
		} catch (Exception e) {
			cLogger.info(cThisBusiConf.getChildText("name")+"����ʧ��!");
			//�򵥱���
			MidplatUtil.getSimpOutXml(1, e.getMessage());
			cTranLogDB.setRCode(1);
			cTranLogDB.setRText(e.getMessage());
		}
		if(cTranLogDB!=null){
			long mCurrMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mStartMillis-mCurrMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurrMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(mCurrMillis));
		}
		if(!cTranLogDB.update()){
			throw new MidplatException("����TranLogDB��ʧ��!");
		}
		cLogger.info("Out ContBatchSendDisk.service()!");
		return cOutXmlDoc;
	}
	
	public String generateCoreZipFile(String localFile) throws Exception{
		FileOutputStream fileOutputStream=null;
		ZipOutputStream zipOutputStream=null;
		FileInputStream fileInputStream=null;
		InputStreamReader inputStreamReader=null;
		BufferedReader bufferedReader=null;
		FileInputStream fileInputStream1=null;
		String coreUploadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
		String coreDownloadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreDownloadFilePath");
		if(!coreUploadFilePath.endsWith("/")) coreUploadFilePath+="/";
		File coreUploadFile=new File(coreUploadFilePath);
		File file=new File(localFile);
		String filename=file.getName();
		//���ո���־[AL03100192017011101_RESULT.XML]
		final String tfFlag=filename.substring(2,3).equals("0")?"S":"F";
		final String tSubmitDate=filename.substring(9,17);
		String batchNo=filename.substring(17,19);
		if(batchNo.length()<5){
			int num=5-batchNo.length();
			for (int i = 0; i <num; i++) {
				batchNo="0"+batchNo;
			}
		}
		final String tBatchNo=batchNo;
		cLogger.info("���մ�����־Ϊ:"+tBatchNo);
		cLogger.info("�ύ����Ϊ:"+tSubmitDate);
		cLogger.info("�������κ�Ϊ:"+tBatchNo);
		cLogger.info("���ո��ļ�ƥ��������ʽ:"+"^\\w{10,15}_"+tfFlag+"02"+tSubmitDate+"_"+tBatchNo+".txt$");
		File[]  files=coreUploadFile.listFiles(new FileFilter() {
			Pattern pattern=Pattern.compile("^\\w{10,15}_"+tfFlag+"02"+tSubmitDate+"_"+tBatchNo+".txt$");
			@Override
			public boolean accept(File filePath) {
				String fileName=filePath.getName();
				Matcher matcher=pattern.matcher(fileName);
				return matcher.matches();
			}
		});
		if(files.length==0){
			throw new MidplatException("ƥ��ȡ��txt�ļ�ʧ�ܣ���˶ԣ�");
		}else if(files.length>1){
			throw new MidplatException("ƥ��ȡ��txt�ļ���������1����˶ԣ�");
		}
		cLogger.info("ƥ��ȡ�̵��ļ���Ϊ:"+files[0].getPath());
		String filenamePath=files[0].getPath();
		
		return null;
	}

}
