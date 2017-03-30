package com.sinosoft.midplat.newccb.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.net.ftp.FTPFile;
import org.jdom.Document;
import org.jdom.Element;


import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.FTPFILEMAPDao;
import com.sinosoft.midplat.newccb.util.RegisterFtp;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.ExeSQL;
/**
 * �������ո�ȡ�̽���
 * @author anico
 *
 */
public class ContBatRequest extends ServiceImpl {

	
	public ContBatRequest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {
		cLogger.info("Into ContBatRequest()...");
		Element FTPEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("FTP");
		String ip=FTPEle.getAttributeValue("ip");
		String user=FTPEle.getAttributeValue("user");
		String password=FTPEle.getAttributeValue("password");
		String remoteStr=FTPEle.getAttributeValue("remoteStr");
		//����FTP���������ش洢·��
		Element LocalDownloadZipFileEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("LocalDownloadZipFile");
		String localDownloadZipPath=LocalDownloadZipFileEle.getText();
		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		String tTranCom=tHeadEle.getChildText("TranCom");
		long mStartMillis = System.currentTimeMillis();
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		cLogger.info("���ж˰�ȫ�ڵ��:"+remoteID);
		cLogger.info("���չ�˾�˰�ȫ�ڵ��:"+localID);
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
		String tType = tBodyEle.getChildText("Type");
		String coreType=tType.equals("0")?"S":"F";
		String tOrderNo=tBodyEle.getChildText("OrderNo");
		final String dateStr=tFileName.substring(9,17);
		cLogger.info("�ļ���Ϊ:"+tFileName);
		cLogger.info("�ļ����:"+tOrderNo);
		cLogger.info("�ļ�����:"+dateStr);
		cLogger.info("�ļ�����:"+tType+"|"+coreType);
		String dateFmt=new SimpleDateFormat("/yyyy-MM-dd").format(new Date());
		String checkRemoteFilePath=remoteStr+dateFmt;
		try {
			// 1:��¼��־
			cTranLogDB = insertTranLog(pInXmlDoc);
			//��Ӧ�����ļ�����ǰ·��
			String LocalDir=this.cThisBusiConf.getChildText("LocalDir");
			File fileLocalDir=new File(LocalDir.endsWith("/")?LocalDir:LocalDir+"/");
			if(!fileLocalDir.exists()){
				if(!fileLocalDir.mkdirs())cLogger.info("�����ļ�·��ʧ�ܣ�"+LocalDir);
			}
			//��Ӧ�����ļ����ܺ�·��
			String ccbLocalDir=this.cThisBusiConf.getChildText("ccbLocalDir");
			//FTP��¼
			RegisterFtp ftp=new RegisterFtp(ip, user, password);
			boolean loginFlag=ftp.loginFTP();
			FTPFile[] fileList=null;
			if(!loginFlag){
				throw new MidplatException("FTP��¼ʧ��!");
			}
			ExeSQL exeSql=new ExeSQL();
			//��ѯ�Ƿ��Ѿ��ɹ����ɶ�Ӧ�����ļ�����ȡ���ļ����п�������������ʱ����ȡ�̽���ʧ�ܣ���ȡ���ļ��Ѿ�����
			String sql="select count(1) from FTPFILEMAP where bankname='"+tFileName+"' and disposeflag='0' and bankflag='"+tTranCom+"'";
			String val=exeSql.getOneValue(sql);
			if(Integer.parseInt(val)!=1){
				//��ȡ�����ϴ���������
				fileList=ftp.checkRemoteFile(checkRemoteFilePath);
				String dateStr1=new SimpleDateFormat("yyyy/MM/dd").format(new Date());
				//�������ļ��ͺ����ļ���ӳ��
				FTPFile file1=null;
				String fileName=null;
				//�����ļ��ͺ����ļ��Ѿ�����ӳ�䣬������δ�ɹ��Ͳ�������ӳ��
				sql="select localname from FTPFILEMAP where bankname='"+tFileName+"' and disposeflag='1' and bankflag='"+tTranCom+"'";
				val=exeSql.getOneValue(sql);
				if(!val.contains("zip")){
					for (int i = 0; i < fileList.length; i++) {
					    file1=fileList[i];
					    fileName=file1.getName();
					    //�鿴�����ļ��Ƿ��ѳɹ�����ȡ�̴��������ѳɹ��Ĳ���������
					    sql="select count(1) from FTPFILEMAP where localname='"+fileName+"' and disposeflag='0' and bankflag='"+tTranCom+"'";
						String val1=exeSql.getOneValue(sql);
						if(Integer.parseInt(val1)==1||!fileName.contains(coreType)){
							//��������ļ�����ӳ���Ҵ���ɹ���������ѭ��
							continue;
						}
						sql="insert into FTPFILEMAP values('"+tFileName+"','"+fileName+"','1','"+dateStr1+"','"+tTranCom+"')";
					    FTPFILEMAPDao.insert(sql);
						break;
					}
				}else{
					fileName=val;
				}
			    cLogger.info("��ȡ���Ĵ��ո����ļ���Ϊ:"+fileName);
			    //�ж��ļ��Ƿ��Ѿ����ص�����
			    if(!(new File(localDownloadZipPath+fileName).exists())){
			    	//�����ļ�������
			    	boolean downloadFlag=ftp.downloadFile(checkRemoteFilePath,fileName,localDownloadZipPath);
			    	//�ر�FTP����
				    ftp.closeConnect();
				    if(!downloadFlag){
			    		throw new MidplatException("FTP�����ļ�ʧ��!");
			    	}
			    }
			    File file0=new File(localDownloadZipPath+fileName.substring(0,fileName.lastIndexOf("."))+".txt");
			    if(!file0.exists()){
				    //����ļ�δ��ѹ���Ƚ�ѹ�ļ�
				    uncompressZipFile (localDownloadZipPath,fileName);
				}
			    cLogger.info(fileName+"�ļ��ѽ�ѹ��"+"��ѹ·��Ϊ:"+file0.getPath());
			    //�ڱ���������Ӧ����δ�����ļ�
				generateBankFile(LocalDir,tFileName,file0.getPath());
				//�����ļ�
			    cLogger.info("�����ļ����localID:"+localID+",remoteID:"+remoteID
							 +",����ǰ�ļ�:"+LocalDir+tFileName+"_SOURCE.xml"
						     +",���ܺ��ļ�:"+ccbLocalDir+tFileName+"_SOURCE.xml");
			   SecAPI.fileEnvelop(localID, remoteID,LocalDir+tFileName+"_SOURCE.xml",ccbLocalDir+tFileName+"_SOURCE.xml");
			}
		   
			Document tReturnDoc = JdomUtil.build(new FileInputStream(LocalDir+tFileName+"_SOURCE.xml"),"UTF-8");
			String tSumNum = tReturnDoc.getRootElement().getChild(Head).getChildText("Cur_Btch_Dtl_TDnum");
			String tSumAmt = tReturnDoc.getRootElement().getChild(Head).getChildText("Cur_Btch_Dtl_TAmt");
			Element eBody = new Element (Body);
			
			Element eFileName = new Element("FileName");
			eFileName.setText(tFileName);
			eBody.addContent(eFileName);
			
			Element eNum = new Element("Num");
			eNum.setText(tSumNum);
			eBody.addContent(eNum);
			
			Element eSumAmt = new Element("SumAmt");
			eSumAmt.setText(tSumAmt);
			eBody.addContent(eSumAmt);
			
			Element eFilePath = new Element("FilePath");
			eFilePath.setText(ccbLocalDir.substring(0,ccbLocalDir.length()-1));
			eBody.addContent(eFilePath);
		
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			cOutXmlDoc.getRootElement().addContent(eBody);
			
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setRText("����ȡ�̳ɹ�");
			cTranLogDB.setRCode(0);
			//ȡ�̳ɹ����������ݿ⴦��״̬
			sql="update  FTPFILEMAP set disposeflag='0' where bankname='"+tFileName+"'";
			exeSql.execUpdateSQL(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
				cTranLogDB.setRCode(1); // -1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
				cTranLogDB.setRText(ex.getMessage());
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
	/**
	 * ������Ӧ����δ�����ļ�
	 * @param LocalDir ���ش洢�ļ�·��
	 * @param localFileName ���ش洢�ļ���
	 * @param fileName ��ȡ�ļ���
	 * @throws Exception
	 */
	 public void generateBankFile(String LocalDir,String localFileName,String unzipPath) throws Exception{
		 FileInputStream fileInputStream=null;
         InputStreamReader inputStreamReader=null;
         BufferedReader bufferedReader=null;
         FileOutputStream fileOutputStream=null;
         BufferedOutputStream bufferedOutputStream=null;
		 try {
			 fileInputStream=new FileInputStream(unzipPath);
			 inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
			 bufferedReader=new BufferedReader(inputStreamReader);
			 String oneLineData=null;
			 Element rootEle=new Element("Root");
			 Element HeadEle=new Element("Head");
			 rootEle.addContent(HeadEle);
			 Element AgIns_BtchBag_NmEle=new Element("AgIns_BtchBag_Nm");//����������������
			 AgIns_BtchBag_NmEle.setText(localFileName+"_SOURCE.xml");
			 Element Cur_Btch_Dtl_TDnumEle=new Element("Cur_Btch_Dtl_TDnum");//��ǰ����ϸ����
			 Element CCur_Btch_Dtl_TAmtEle=new Element("Cur_Btch_Dtl_TAmt");//��ǰ����ϸ�ܽ��
			 Element AgIns_BtchBag_TpCdEle=new Element("AgIns_BtchBag_TpCd");//���������������ʹ���
			 HeadEle.addContent(AgIns_BtchBag_NmEle);
			 HeadEle.addContent(Cur_Btch_Dtl_TDnumEle);
			 HeadEle.addContent(CCur_Btch_Dtl_TAmtEle);
			 HeadEle.addContent(AgIns_BtchBag_TpCdEle);
			 Element Detail_ListEle=new Element("Detail_List");
			 rootEle.addContent(Detail_ListEle);
			 String codeSF=null;
//			 while ((oneLineData=bufferedReader.readLine())!=null&&!"".equals(oneLineData)) {
			 while ((oneLineData=bufferedReader.readLine())!=null) {
				 if("".equals(oneLineData)){
					continue; 
				 }
				 String[] strs=oneLineData.split(",");
				 if(strs.length==6){
					 Cur_Btch_Dtl_TDnumEle.setText(strs[3]);
					 CCur_Btch_Dtl_TAmtEle.setText(NumberUtil.fenToYuan(strs[4]));
					 codeSF=strs[5];//���ո�ҵ�����
				 }
				 if(strs.length>6){
					Element DetailEle=new Element("Detail");
					Detail_ListEle.addContent(DetailEle);
					Element Cst_NmEle=new Element("Cst_Nm");//�ͻ�����
					Cst_NmEle.setText(strs[5]);
					Element Cst_AccNoEle=new Element("Cst_AccNo");//�ͻ��˺�
					Cst_AccNoEle.setText(strs[4]);
					Element CcyCdEle=new Element("CcyCd");//���ִ���
					CcyCdEle.setText("156");//����ֻ��һ�ֱ���156:�����
					Element CshEx_CdEle=new Element("CshEx_Cd");//�������
					CshEx_CdEle.setText("1");
					Element Btch_Dtl_SNEle=new Element("Btch_Dtl_SN");//������ϸ���
					Btch_Dtl_SNEle.setText(Integer.valueOf(strs[0])+"");
					Element Ins_Co_SRP_Bsn_CtCdEle=new Element("Ins_Co_SRP_Bsn_CtCd");//���չ�˾���ո�ҵ���������
					//���д��ո���������(01:���ڽɷ�  02:���ڽɷ�  03:�������� 12:���� 13:��������  15:��������)
					Ins_Co_SRP_Bsn_CtCdEle.setText(codeSF.equals("10600")?"01":codeSF.equals("10601")?"02":codeSF.equals("00600")?"12":codeSF.equals("00601")?"13":codeSF.startsWith("1")?"03":"15");
					Element InsPolcy_NoEle=new Element("InsPolcy_No");//��������
					if(codeSF.equals("10601")){//���ڽɷ�
					   //���չ�˾���ո�ҵ���������Ϊ�����ڽɷѡ�����׷�ӱ��ѡ������˱�����������֧ȡ��ʱ����˾�������͸��ո�ҵ��ı�������
					   //���ĵ���������ʱδ�ṩ������,����ȷ�ϲ������ɡ����ڽɷѡ�����׷�ӱ��ѡ������˱�����������֧ȡ������
					   InsPolcy_NoEle.setText("");
					}
					Element AmtEle=new Element("Amt");//���
					AmtEle.setText(NumberUtil.fenToYuan(strs[10]));
					Element Rnew_Pbl_Prd_NumEle=new Element("Rnew_Pbl_Prd_Num");//����Ӧ������
					Element Rsrv_Fld1_InfEle=new Element("Rsrv_Fld1_Inf");//�����ֶ�1��Ϣ
					Element Rsrv_Fld2_InfEle=new Element("Rsrv_Fld2_Inf");//�����ֶ�2��Ϣ
					Element Rsrv_Fld3_InfEle=new Element("Rsrv_Fld3_Inf");//�����ֶ�3��Ϣ
					DetailEle.addContent(Cst_NmEle).addContent(Cst_AccNoEle).addContent(CcyCdEle)
					.addContent(CshEx_CdEle).addContent(Btch_Dtl_SNEle).addContent(Ins_Co_SRP_Bsn_CtCdEle)
					.addContent(InsPolcy_NoEle).addContent(AmtEle).addContent(Rnew_Pbl_Prd_NumEle)
					.addContent(Rsrv_Fld1_InfEle).addContent(Rsrv_Fld2_InfEle).addContent(Rsrv_Fld3_InfEle);
				 }
			}
			Document rootDoc=new Document(rootEle);
			fileOutputStream=new FileOutputStream(LocalDir+localFileName+"_SOURCE.xml");
			bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(JdomUtil.toStringFmt(rootDoc,"UTF-8").getBytes("UTF-8"));
			cLogger.info("��������δ�����ļ��ɹ���"+LocalDir+localFileName+"_SOURCE.xml");
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info("��������δ�����ļ�ʧ�ܣ�");
			throw  new MidplatException("ȡ�̽���ʧ��!"+e.getMessage());
		}finally{
			if(bufferedReader!=null){
				bufferedReader.close();
			}
			if(inputStreamReader!=null){
				inputStreamReader.close();
			}
			if(fileInputStream!=null){
				fileInputStream.close();
			}
			if(bufferedOutputStream!=null){
				bufferedOutputStream.close();
			}
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
		}
		
	 }
	 public  void uncompressZipFile (String zipFilePath,String zipFileName) throws Exception{
	    	FileInputStream fileInputStream=null;
	    	ZipInputStream zipInputStream=null;
	    	FileOutputStream fileOutputStream=null;
	    	try {
				fileInputStream=new FileInputStream(zipFilePath+zipFileName);
				zipInputStream=new ZipInputStream(fileInputStream);
				ZipEntry zipEntry=null;
				byte[] bytes=new byte[1024];//1KB
				while ((zipEntry=zipInputStream.getNextEntry())!=null) {
					cLogger.info(zipFilePath+zipEntry.getName());
					if(!zipEntry.isDirectory()){
						fileOutputStream=new FileOutputStream(zipFilePath+zipEntry.getName());
						int i=-1;
						while ((i=zipInputStream.read(bytes))!=-1) {
							fileOutputStream.write(bytes,0,i);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				cLogger.info(zipFileName+"�ļ���ѹʧ��!");
				throw new MidplatException("�ļ���ѹʧ��!");
			}finally{
				if(zipInputStream!=null){
					zipInputStream.close();
				}
				if(fileInputStream!=null){
					   fileInputStream.close();
					}
				if(fileOutputStream!=null){
				   fileOutputStream.close();
				}
			}
	    }
	public static void main(String[] args) throws Exception {

//		Element cThisBusiConf = (Element)XPath.selectSingleNode(CcbConf.newInstance().getConf().getRootElement(), (new StringBuilder("business[funcFlag='")).append("803").append("']").toString());
//		ContBatRequest batch = new ContBatRequest(cThisBusiConf);
//		String mFilePath = "D:\\MyEclipseWorkSpace\\LIAN\\src\\test\\doc\\ccb\\803_inSvc.xml";
//		InputStream mIs = new FileInputStream(mFilePath);
//		Document pInXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(batch.service(pInXmlDoc));
//		String[] strs=new String[1];
//		strs[0]="CNY";
//		System.out.println(strs[0].equals("")?"156":strs[0].equals("CNY")?"156":strs[0]);
		String codeSF="10600";
		System.out.println(codeSF.startsWith("1") ? "03" : codeSF.equals("00601") ? "13" : codeSF.equals("00600") ? "12" : codeSF.equals("10601") ? "02" : codeSF.equals("10600") ? "01" : "15");
	}
}
