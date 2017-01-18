package com.sinosoft.midplat.newccb.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sun.xml.internal.bind.util.Which;

/**
 * @ClassName: ContBatRequest
 * @Description: �������մ���ȡ��ҵ������
 * @author yuantongxin
 * @date 2017-1-18 ����6:18:23
 */
public class ContBatRequest extends ServiceImpl {

	/**
	 * <p>Title: ContBatRequest</p>
	 * <p>Description: �������մ���ȡ��ҵ�����๹�캯��</p>
	 * @param pThisBusiConf ��ǰҵ�������ļ�
	 */
	public ContBatRequest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	/**
	 * ��׼���뱨�Ĵ���Ϊ��׼�������
	 * @param pInXmlDoc ��׼���뱨��
	 * @return ��׼�������
	 */
	public Document service(Document pInXmlDoc) throws Exception {
		//Into ContBatRequest()...
		cLogger.info("Into ContBatRequest()...");
		//��ʼʱ�������
		long mStartMillis = System.currentTimeMillis();
		//��׼���뱨��[����3�ո񣬺��������еı���]
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//���ذ�ȫ�ڵ��
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		//���а�ȫ�ڵ��
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		//���ж˰�ȫ�ڵ��:���а�ȫ�ڵ��
		cLogger.info("���ж˰�ȫ�ڵ��:"+remoteID);
		//���չ�˾�˰�ȫ�ڵ��:���ذ�ȫ�ڵ��
		cLogger.info("���չ�˾�˰�ȫ�ڵ��:"+localID);
		//��׼���뱨����
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		//����������������
		String tFileName = tBodyEle.getChildText("FileName");
		//���մ�����־
		String tType = tBodyEle.getChildText("Type");
		//�������κ�
		String tOrderNo=tBodyEle.getChildText("OrderNo");
		//�ύ����
		final String dateStr=tFileName.substring(9,17);
		//�ļ���Ϊ:����������������
		cLogger.info("�ļ���Ϊ:"+tFileName);
		//�ļ����:�������κ�
		cLogger.info("�ļ����:"+tOrderNo);
		//�ļ�����:�ύ����
		cLogger.info("�ļ�����:"+dateStr);
		//�ļ�����:���մ�����־
		cLogger.info("�ļ�����:"+tType+(tType.equals("0")?"����":"����"));
		String coreUploadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
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
			File file=new File(coreUploadFilePath);
			if(!file.exists()){
				throw new MidplatException("����FTP�ϴ��ļ�·��������:"+coreUploadFilePath);
			}
			//ȡ�ļ��������λ������5λǰ�油0
			if(tOrderNo.length()<5){
				int num=5-tOrderNo.length();
				for (int i = 0; i <num; i++) {
					tOrderNo="0"+tOrderNo;
				}
			}
			final String ttOrderNo=tOrderNo;
			//S:���� F:����
	        final String SFflag="0".equals(tType)?"S":"F";
	        cLogger.info("���ո������ļ�ƥ��������ʽ:"+"^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+ttOrderNo+".zip$");
	        File[] fileList=file.listFiles(new FileFilter() {
			   Pattern pattern=Pattern.compile("^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+ttOrderNo+".zip$");
			   @Override
			   public boolean accept(File filePath) {
			      String fileName=filePath.getName();
				  Matcher matcher=pattern.matcher(fileName);
				  return matcher.matches();
			   }
			});
		    if(fileList.length>1){
		       cLogger.info("���Ϲ�����ļ�����Ϊ:"+fileList.length+"!��˶�");
		       throw new MidplatException("����ʧ��");
		    }
		    if(fileList.length==0){
			       cLogger.info("���Ϲ�����ļ�����Ϊ:"+fileList.length+"!��˶�");
			       throw new MidplatException("����ʧ��");
			    }
		    cLogger.info("��ȡ���Ĵ��հ��ļ���Ϊ:"+fileList[0].getPath());
		    File file0=new File(fileList[0].getPath().substring(0,fileList[0].getPath().lastIndexOf("."))+".txt");
		    if(!file0.exists()){
		        //����ļ�δ��ѹ���Ƚ�ѹ�ļ�
		        uncompressZipFile (coreUploadFilePath,fileList[0].getName());
		    }
		    cLogger.info(fileList[0].getName()+"�ļ��ѽ�ѹ��"+"��ѹ·��Ϊ:"+file0.getPath());
		    //�ڱ���������Ӧ����δ�����ļ�
		    generateBankFile(LocalDir,tFileName,file0.getName());
		    //�����ļ�
			cLogger.info("�����ļ����localID:"+localID+",remoteID:"+remoteID
						     +",����ǰ�ļ�:"+LocalDir+tFileName+"_SOURCE.xml"
						     +",���ܺ��ļ�:"+ccbLocalDir+tFileName+"_SOURCE.xml");
			SecAPI.fileEnvelop(localID, remoteID,LocalDir+tFileName+"_SOURCE.xml",ccbLocalDir+tFileName+"_SOURCE.xml");
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
			eFilePath.setText(ccbLocalDir);
			eBody.addContent(eFilePath);
		
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			cOutXmlDoc.getRootElement().addContent(eBody);
			
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setRText("����ȡ�̳ɹ�");
			cTranLogDB.setRCode(0);
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
	 public void generateBankFile(String LocalDir,String localFileName,String fileName) throws Exception{
		 String coreUploadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
		 File file=new File(coreUploadFilePath+fileName);
		 if(!file.exists()){
			 throw new MidplatException(file.getPath()+"�ļ������ڣ�");
		 }
		 FileInputStream fileInputStream=null;
         InputStreamReader inputStreamReader=null;
         BufferedReader bufferedReader=null;
         FileOutputStream fileOutputStream=null;
         BufferedOutputStream bufferedOutputStream=null;
		 try {
			 fileInputStream=new FileInputStream(file);
			 inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
			 bufferedReader=new BufferedReader(inputStreamReader);
			 String oneLineData=null;
			 Element rootEle=new Element("root");
			 Element HeadEle=new Element("Head");
			 rootEle.addContent(HeadEle);
			 Element AgIns_BtchBag_NmEle=new Element("AgIns_BtchBag_Nm");//����������������
			 AgIns_BtchBag_NmEle.setText(localFileName);
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
			 while ((oneLineData=bufferedReader.readLine())!=null&&!"".equals(oneLineData)) {
				 String[] strs=oneLineData.split(",");
				 if(strs.length==6){
					 Cur_Btch_Dtl_TDnumEle.setText(strs[3]);
					 CCur_Btch_Dtl_TAmtEle.setText(NumberUtil.fenToYuan(strs[4]));
					 codeSF=strs[5];//����ҵ�����
				 }
				 if(strs.length>6){
					Element DetailEle=new Element("Detail");
					Detail_ListEle.addContent(DetailEle);
					Element Cst_NmEle=new Element("Cst_Nm");//�ͻ�����
					Cst_NmEle.setText(strs[5]);
					Element Cst_AccNoEle=new Element("Cst_AccNo");//�ͻ��˺�
					Cst_AccNoEle.setText(strs[4]);
					Element CcyCdEle=new Element("CcyCd");//���ִ���
					CcyCdEle.setText(strs[11].equals("")?"CNY":strs[11]);//���Ϊ��Ĭ��ΪCNY�����
					Element CshEx_CdEle=new Element("CshEx_Cd");//�������
					CshEx_CdEle.setText("1");
					Element Btch_Dtl_SNEle=new Element("Btch_Dtl_SN");//������ϸ���
					Btch_Dtl_SNEle.setText(Integer.valueOf(strs[0])+"");
					Element Ins_Co_SRP_Bsn_CtCdEle=new Element("Ins_Co_SRP_Bsn_CtCd");//���չ�˾���ո�ҵ���������
					Ins_Co_SRP_Bsn_CtCdEle.setText(codeSF.equals("10600")?"01":codeSF.equals("10601")?"02":codeSF.equals("00600")?"12":codeSF.equals("00601")?"13":"--");
					Element InsPolcy_NoEle=new Element("InsPolcy_No");//��������
					if(codeSF.equals("10600")){
					   //���չ�˾���ո�ҵ���������Ϊ�����ڽɷѡ�����׷�ӱ��ѡ������˱�����������֧ȡ��ʱ����˾�������͸��ո�ҵ��ı�������
					   InsPolcy_NoEle.setText("����");
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
			throw  new MidplatException(e.getMessage());
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
					//cLogger.info(zipEntry.getName());
					System.out.println(zipEntry.getName());
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
	}
}
