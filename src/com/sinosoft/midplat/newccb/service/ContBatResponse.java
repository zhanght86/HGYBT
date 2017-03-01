package com.sinosoft.midplat.newccb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.f1j.data.source.InputStream;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

/**
 * @ClassName: ContBatResponse 
 * @Description: �������մ�������ҵ������
 * @author yuantongxin
 * @date 2017-1-17 ����7:19:17
 */
public class ContBatResponse extends ServiceImpl {

	/**
	 * <p>Title: ContBatResponse</p>
	 * <p>Description: �������մ�������ҵ�����๹�캯��</p>
	 * @param pThisBusiConf ��ǰ���������ļ�
	 */
	public ContBatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	/**
	 * ��׼���뱨�Ĵ���Ϊ��׼�������
	 * @param pInXmlDoc ��׼���뱨��
	 * @return ��׼�������
	 */
	public Document service(Document pInXmlDoc) throws Exception {
		//Into ContBatRequResult()...
		cLogger.info("Into ContBatRequResult()...");
		long mStartMillis = System.currentTimeMillis();
		// ��׼���뱨������3�ո񣬺��������еı���
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//���л����ļ������ļ�·��
		String localDir=this.cThisBusiConf.getChildText("LocalDir");
		//���ж˰�ȫ�ڵ��
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		//���չ�˾�˰�ȫ�ڵ��
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		//���ж˰�ȫ�ڵ��:null
		cLogger.info("���ж˰�ȫ�ڵ��:"+remoteID);
		//���չ�˾�˰�ȫ�ڵ��:null
		cLogger.info("���չ�˾�˰�ȫ�ڵ��:"+localID);
		//�����ļ�·��
		String filePath="/home/ap/fserver2/rcv/";//���������ļ�·��
		//��׼���뱨����
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		//����������������
		String tFileName = tBodyEle.getChildText("FileName");
		//���л����ļ�·��Ϊ:�����ļ�·��+����������������
	    cLogger.info("���л����ļ�·��Ϊ:"+filePath+tFileName);
	    //����������������״̬����
		int tBatFlag = Integer.parseInt(tBodyEle.getChildText("BatFlag"));
		//��ǰ����ϸ�ܱ���
		String tNum = tBodyEle.getChildText("Num");
		//��ǰ����ϸ�ܽ��
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		try { 
			// 1:��¼��־
			/**���뽻����־,����[����1:����������������,����2:����������������״̬����,����3:��ǰ����ϸ�ܱ���,����4:��ǰ����ϸ�ܽ��]*/
			cTranLogDB = insertTranLog(pInXmlDoc);
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBodyEle.getChildText("BatFlag"));
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			//�����ַ���
			String descStr=null;
			//����������������״̬����������ɹ�������ʧ��
			if(!"00".equals(tBatFlag)||!"16".equals(tBatFlag)){
				//00:��������ɹ���16����������ʧ��
				/**ƥ�����������������״̬�����Ӧ״̬*/
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
				//�׳��쳣�������ַ���
				throw new MidplatException(descStr);
			}
			//�����ļ����localID:���ж˰�ȫ�ڵ��,remoteID:���չ�˾�˰�ȫ�ڵ��,����ǰ�ļ�:�����ļ�·��+����������������,���ܺ��ļ�:���л����ļ������ļ�·��+����������������
			cLogger.info("�����ļ����localID:"+localID+",remoteID:"+remoteID +",����ǰ�ļ�:"+filePath+tFileName
				         +",���ܺ��ļ�:"+localDir+tFileName);
		    //�����������̽����ļ�
			//���������ļ�·�������������������л����ļ������ļ�·��
			SecAPI.fileUnEnvelop(localID, remoteID,filePath+tFileName,localDir+tFileName);
			//���л����ļ������ļ�·��+���������������ɺ���ѹ���ļ�
			String fileName=generateCoreZipFile(localDir+tFileName);
			//���ݽ��׽���ͽ��׽�����������ɼ򵥱�׼�������
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			//������־���ý��׽��
			cTranLogDB.setRCode(0);
			//������־���ý������[���ɺ��Ļ����ļ��ɹ�:������������ѹ���ļ���]
			cTranLogDB.setRText("���ɺ��Ļ����ļ��ɹ�:"+fileName);
		} catch (Exception ex) {
			//�������ƽ���ʧ�ܣ�
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			//������־�ǿ�
			if (null != cTranLogDB) { 
				//���ý��׽��
				cTranLogDB.setRCode(1); 
				//���ý������
				cTranLogDB.setRText(ex.getMessage());
			}
			//���ݽ��׽���ͽ�����������ɼ򵥱�׼������ġ�
			cOutXmlDoc = MidplatUtil.getSimpOutXml(1,ex.getMessage());
		}
		//������־�ǿ�
		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			//��ǰʱ�������
			long tCurMillis = System.currentTimeMillis();
			//������־�����ʱ[��ǰʱ�������-��ʼʱ�������]
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			//������־����޸�����[��ǰʱ�������ǰ8λ]
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			//������־����޸�ʱ��[��ǰʱ���������6λ]
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			//���½�����־
			if (!cTranLogDB.update()) {
				//������־��Ϣʧ�ܣ�������Ϣ
				cLogger.error("������־��Ϣʧ�ܣ�"+ cTranLogDB.mErrors.getFirstError());
			}
		}
		//��׼�������[����3�ո񣬺��������еı���]
		cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
		//���ر�׼�������
		return cOutXmlDoc;
	}
	
	/**
	 * @Title: generateCoreZipFile
	 * @Description: ���ɺ���ѹ���ļ�
	 * @param localFile ���л����ļ������ļ�·��+������������
	 * @return ѹ���ļ���
	 * @throws Exception �쳣
	 * @return String ѹ���ļ���
	 * @throws ѹ���ļ���
	 */
	public String generateCoreZipFile(String localFile) throws Exception{
		//�ļ������
		FileOutputStream fileOutputStream=null;
		//ѹ�������
		ZipOutputStream zipOutputStream=null;
		//�ļ�������
		FileInputStream fileInputStream=null;
		//��������ȡ
		InputStreamReader inputStreamReader=null;
		//��������ȡ
		BufferedReader BufferedReader=null;
		//�ļ�������1
		FileInputStream fileInputStream1=null;
		//����ȡ�����ļ�·��
		//��������������·��
		String coreDownloadFilePath = NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreDownloadFilePath");
		try {
			//�����ϴ�������·��
			String fileUploadStr=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
			//�����ϴ�������·��+��׺/
			if(!fileUploadStr.endsWith("/"))fileUploadStr+="/";
			//�����ϴ�������·���ļ�����
			File fileUpload=new File(fileUploadStr);
			//���л����ļ������ļ�·��+�������������ļ�����
			File file=new File(localFile);
			//����������������
			String fileName=file.getName();
			//���մ�����־[AL?3100192017011101_RESULT.xml]
			final String SFflag=fileName.substring(2,3).equals("0")?"S":"F";
			//�ύ����[AL0310019????????01_RESULT.xml]
			final String dateStr=fileName.substring(9,17);
			//�������κ�[AL031001920170111??_RESULT.xml]
			String orderNo=fileName.substring(17,19);
			//�����ļ��������λ������5λǰ�油0
			//�������κŲ���5λ
			if(orderNo.length()<5){
				//��0����
				int num=5-orderNo.length();
				//�������κ�ǰ����0
				for (int i = 0; i <num; i++) {
					//��0
					orderNo="0"+orderNo;
				}
			}
			//�������κų���
			final String tOrderNo=orderNo;
			//���ɰ��ı�־Ϊ:���մ�����־
			cLogger.info("���ɰ��ı�־Ϊ:"+SFflag);
			//���ո������ļ�ƥ��������ʽ:^\\w{10,15}_���մ�����־02�ύ����_�������κ�.txt$
			cLogger.info("���ո������ļ�ƥ��������ʽ:"+"^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+tOrderNo+".txt$");
	        //�����ϴ�������·���ļ����󷵻ش˳���·��������ʾĿ¼�е��ļ���Ŀ¼�ĳ���·�������飬��Щ·���������ض�������
			File[] fileList=fileUpload.listFiles(new FileFilter() {
				//����������ʽ
			   Pattern pattern=Pattern.compile("^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+tOrderNo+".txt$");
			   /**
			    * @Title: accept
			    * @Description: ����
			    * @param filePath ���л����ļ������ļ�·��+�������������ļ�����
			    * @return ƥ����
			    * @return boolean true:�ɹ���falseʧ��
			    * @throws
			    */
			   @Override
			   public boolean accept(File filePath) {
				   //����������������
			      String fileName=filePath.getName();
			      //ģʽƥ�����������������
				  Matcher matcher=pattern.matcher(fileName);
				  //����������ʽ������ƥ������
				  return matcher.matches();
			   }
			});
			//�����ϴ�������·���ļ�����û���ļ�
	        if(fileList.length==0){
	        	throw new MidplatException("ƥ��ȡ��txt�ļ�ʧ�ܣ���˶ԣ�");
	        }
	        //�����ϴ�������·���ļ������ļ�����1���ļ�
	        if(fileList.length>1){
	        	throw new MidplatException("ƥ��ȡ��txt�ļ���������1����˶ԣ�");
	        }
	        //ƥ��ȡ�̵��ļ���Ϊ:�ļ�·����
	        cLogger.info("ƥ��ȡ�̵��ļ���Ϊ:"+fileList[0].getPath());
	        //�ļ�·����
	        String fileNamePath=fileList[0].getPath();
	        //�����ļ���[��������������·��+�ļ�·����ǰ׺.zip]
	        String downloadFileName=coreDownloadFilePath+fileNamePath.substring(0,fileNamePath.lastIndexOf("."))+".zip";
	        //�����ļ������
	        fileOutputStream=new FileOutputStream(downloadFileName);
	        //�ļ�·��������ZIP ��Ŀ
	        ZipEntry zipEntry=new ZipEntry(fileNamePath);
	        //ѹ�������
	        zipOutputStream=new ZipOutputStream(fileOutputStream);
	        //д��ZIP�ļ���Ŀ��������λ����Ŀ���ݿ�ʼ��
	        zipOutputStream.putNextEntry(zipEntry);
	        //�ļ�·�����ļ�������
	        fileInputStream=new FileInputStream(fileNamePath);
	        //��������ȡ�ļ�·�����ļ�������
	        inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
	        //�ļ�·������������ȡ���뻺����
	        BufferedReader=new BufferedReader(inputStreamReader);
	        //���л����ļ������ļ�·��+�������������ļ������ļ�������
	        fileInputStream1=new FileInputStream(file);
	        //���̴������������ļ�����һ���ĵ������ȡ���ڵ�
	        Element docRoot=JdomUtil.build(fileInputStream1,"UTF-8").getRootElement();//�����������ļ�������document����ȡ���ڵ�
	        //�ı���
	        String dataStr=null;
	        //��ȡһ���ı��зǿ�
	        while((dataStr=BufferedReader.readLine())!=null||!"".equals(dataStr)){
	        	//����,������ı���
	        	String[] strs=dataStr.split(",");
	        	//�ı������鳤��Ϊ6
	        	if(strs.length==6){
	        		//��ǰ����ϸ�ܱ����Ǻ����ϴ�������·���ļ���ϸ�ܱ���
	        		if(!docRoot.getChildText("Cur_Btch_Dtl_TDnum").equals(strs[3])){
	        			//�����ļ���ȡ���ļ������ݲ�һ�£�
	        		   throw new MidplatException("�����ļ���ȡ���ļ������ݲ�һ�£�");
	        		}
	        		//�ı����ֽ�����д��ѹ�������
	        		zipOutputStream.write(dataStr.getBytes("GBK"));
	        		//����
	        		continue;
	        	}
	        	//��ȡ�����˺Ÿ��������˺Ż�ȡ���д��������Ӧ�Ľڵ�
	        	//�ͻ��˺�
	        	String account=strs[4];
	        	//����һ���µ�XPath���󣬱���ָ����XPath���ʽ
	        	XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='"+account+"']");
	        	//���ݰ�װ��XPath���ʽ����ѡ�нڵ��б�ĵ�һ��
	        	Element Detail=(Element) tXPath.selectSingleNode(docRoot);
	        	//��ϸΪ��
	        	if(Detail==null){
	        		//����������ȡ�����ݲ�һ��,��˶ԣ�
	        		throw new MidplatException("����������ȡ�����ݲ�һ��,��˶ԣ�");
	        	}
	        	//��ȡ�ı����ַ���
	        	dataStr=dataStr.substring(0,dataStr.lastIndexOf(","));
	        	//������Ӧ����
	        	String responseCode=Detail.getChildText("Hst_Rsp_Cd");//������Ӧ��
	        	//������Ӧ��Ϣ
	        	String responseStr=Detail.getChildText("Hst_Rsp_Inf");//������Ӧ����
	        	//�����˺�Ϊ:�ͻ��˺Ž������ݵ���Ӧ��Ϊ:������Ӧ������Ӧ����Ϊ:������Ӧ��Ϣ
	        	cLogger.info("�����˺�Ϊ:"+account+"�������ݵ���Ӧ��Ϊ:"+responseCode+"��Ӧ����Ϊ:"+responseStr);
	        	//���ֽ�����д��ѹ�������
	        	zipOutputStream.write((dataStr+responseCodeTransfer(responseCode)+","+responseStr).getBytes("GBK"));
	        }
	        //���ɺ��Ļ����ļ�·��Ϊ�������ļ���
	        cLogger.info("���ɺ��Ļ����ļ�·��Ϊ��"+downloadFileName);
	        //�����ļ���
	        return downloadFileName;//���������ļ�·��
		} catch (Exception e) {
			//�׳��쳣,��ʾ��Ϣ
			throw new MidplatException(e.getMessage());
		}finally{
			//ѹ��������ǿ�
			if(zipOutputStream!=null){
				//�ر�ѹ�������
				zipOutputStream.close();
			}
			//�ļ�������ǿ�
			if(fileOutputStream!=null){
				//�ر��ļ������
				fileOutputStream.close();
			}
			//��������ȡ�ǿ�
			if(BufferedReader!=null){
				//�رջ�������ȡ
				BufferedReader.close();
			}
			//��������ȡ�ǿ�
			if(inputStreamReader!=null){
				//�ر���������ȡ
				inputStreamReader.close();
			}
			//�ļ��������ǿ�
			if(fileInputStream!=null){
				//�ر��ļ�������
				fileInputStream.close();
			}
			//�ļ�������1�ǿ�
			if(fileInputStream1!=null){
				//�ر��ļ�������1
				fileInputStream1.close();
			}
		}
		
	}
	//������Ӧ��ת���ɺ�����Ӧ��
	/**
	 * @Title: responseCodeTransfer
	 * @Description: ��Ӧ����ת��
	 * @param code ������Ӧ����
	 * @return ������
	 * @return String ������
	 * @throws �쳣
	 */
	public String responseCodeTransfer(String code){
		if("E8201".equals(code)){return "3039";}//�ʻ�������
		else if("E3150".equals(code)){return "3009";}//�ʻ�������
		else if("E5502".equals(code)){return "3059";}//�����ѹ�ʧ
		else if("E4501".equals(code)){return "2005";}//�ʻ�����״̬���������
		else if("E3001".equals(code)){return "3032";}//�ұ�򳮻���𲻴���
		else if("E4500".equals(code)){return "2006";}//�ʻ�����״̬������֧ȡ
		else if("E5000".equals(code)){return "3016";}//�����ѹ�ʧ
		else if("E3551".equals(code)){return "3017";}//�ʻ��Ѷ���
		else if("E4500".equals(code)){return "3017";}//���򶳽�״̬������֧ȡ
		else if("E5002".equals(code)){return "3006";}//���ѹ�ʧ
		else if("E7102".equals(code)){return "3008";}//����
		else if("E3266".equals(code)){return "3065";}//�ͻ����Ƽ��ʧ��
		else if("E1408".equals(code)){return "3057";}//�ʺ����Ͳ���
		else if("E8301".equals(code)){return "3999";}//ר���˻�����ժҪ�������
		else if("E8301".equals(code)){return "3039";}//�ÿ�������
		else if("E1085".equals(code)){return "3999";}//��ʧ��־����ȷ
		else if("E3540".equals(code)){return "3999";}//ע��״̬����ȷ
		else if("E3556".equals(code)){return "3999";}//��״̬������
		else if("SHK02".equals(code)){return "3999";}//ʹ�÷Ƿ��˺ŵ���
		else if("SDB01".equals(code)){return "3999";}//ʹ�÷Ƿ��˺ŵ���
		else if("E9999".equals(code)){return "3999";}//�����ڲ��������
		else{return "";}
	}
	public static void main(String[] args) throws Exception {

//     FileInputStream in=new FileInputStream(new File("C:\\Users\\anico\\Desktop\\���в��Ա���\\CCB���ո��ļ�20170111\\CCB���ո��ļ�20170111\\AL03100192017011101_RESULT.XML"));
//     Element doc=JdomUtil.build(in,"UTF-8").getRootElement();
//     //System.out.println(JdomUtil.toStringFmt(doc));
//     XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='1216129980110291838']");
//     Element e=(Element) tXPath.selectSingleNode(doc);
//	 System.out.println(JdomUtil.toStringFmt(e));
		String str="232493u,934924,24023,234,,";
		str=str.substring(0,str.lastIndexOf(","))+"2222"+","+"8888";
		System.out.println(str);
	}
}
